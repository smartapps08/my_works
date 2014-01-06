package com.sa.opportune;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.sa.entity.Music;
import com.sa.entity.Song;
import com.sa.recognizer.ActivityUtils;
import com.sa.recognizer.ActivityUtils.REQUEST_TYPE;
import com.sa.recognizer.DetectionRemover;
import com.sa.recognizer.DetectionRequester;
import com.sa.recognizer.LogFile;
import com.sa.utils.YahooWeather4a.WeatherInfo;
import com.sa.utils.YahooWeather4a.YahooWeatherInfoListener;
import com.sa.utils.YahooWeather4a.YahooWeatherUtils;
import com.sa.weathertools.AsciiUtils;

public class MainActivity extends Activity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener, LocationListener,
		YahooWeatherInfoListener {
	// Some audio may be explicitly marked as not being music
	String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

	String[] projection = { MediaStore.Audio.Media._ID,
			MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.TITLE,
			MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.DISPLAY_NAME,
			MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.COMPOSER,
			MediaStore.Audio.Media.ALBUM };

	private ArrayList<Song> listOfSongs;

	// For activity recognition
	private static final int MAX_LOG_SIZE = 5000;
	private LogFile mLogFile;
	private REQUEST_TYPE mRequestType;
	private ListView mStatusListView;
	private ArrayAdapter<Spanned> mStatusAdapter;

	/*
	 * Intent filter for incoming broadcasts from the IntentService.
	 */
	IntentFilter mBroadcastFilter;
	private LocalBroadcastManager mBroadcastManager;
	private DetectionRequester mDetectionRequester;
	private DetectionRemover mDetectionRemover;

	// for current location
	private LocationClient locationClient;
	private Location currentLocation;
	private static final int UPDATE_INTERVAL = 5000;
	private static final int FASTEST_INTERVAL = 1000;
	private LocationRequest locationRequest;

	// weather
	private YahooWeatherUtils yahooWeatherUtils = YahooWeatherUtils
			.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// listOfSongs = getListOfSongs();
		// for (Song song : listOfSongs) {
		// Toast.makeText(getApplicationContext(), song.toString(),
		// Toast.LENGTH_LONG).show();
		// }

		// Get a handle to the activity update list
		mStatusListView = (ListView) findViewById(R.id.log_listview);
		mStatusAdapter = new ArrayAdapter<Spanned>(this, R.layout.item_layout,
				R.id.log_text);
		mStatusListView.setAdapter(mStatusAdapter);
		mBroadcastManager = LocalBroadcastManager.getInstance(this);
		mBroadcastFilter = new IntentFilter(
				ActivityUtils.ACTION_REFRESH_STATUS_LIST);
		mBroadcastFilter.addCategory(ActivityUtils.CATEGORY_LOCATION_SERVICES);

		mDetectionRequester = new DetectionRequester(this);
		mDetectionRemover = new DetectionRemover(this);

		mLogFile = LogFile.getInstance(this);

		// for location
		locationClient = new LocationClient(this, this, this);
		// creating request object with some of parameters set
		locationRequest = LocationRequest.create()
				.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
				.setInterval(UPDATE_INTERVAL);
	}

	private ArrayList<Song> getListOfSongs() {
		ArrayList<Song> songs = new ArrayList<Song>();
		Cursor cursor = getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection,
				selection, null, null);
		while (cursor.moveToNext()) {
			songs.add(new Song(cursor.getString(cursor
					.getColumnIndex(projection[0])), cursor.getString(cursor
					.getColumnIndex(projection[1])), cursor.getString(cursor
					.getColumnIndex(projection[2])), cursor.getString(cursor
					.getColumnIndex(projection[4])), cursor.getString(cursor
					.getColumnIndex(projection[5])), cursor.getString(cursor
					.getColumnIndex(projection[6])), cursor.getString(cursor
					.getColumnIndex(projection[7])), Integer.parseInt(cursor
					.getString(cursor.getColumnIndex(projection[5])))));
		}

		return songs;
	}

	/*
	 * Handle results returned to this Activity by other Activities started with
	 * startActivityForResult(). In particular, the method onConnectionFailed()
	 * in DetectionRemover and DetectionRequester may call
	 * startResolutionForResult() to start an Activity that handles Google Play
	 * services problems. The result of this call returns here, to
	 * onActivityResult.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		switch (requestCode) {
		case ActivityUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST:
			switch (resultCode) {
			case Activity.RESULT_OK:
				if (ActivityUtils.REQUEST_TYPE.ADD == mRequestType) {
					mDetectionRequester.requestUpdates();
				} else if (ActivityUtils.REQUEST_TYPE.REMOVE == mRequestType) {
					mDetectionRemover.removeUpdates(mDetectionRequester
							.getRequestPendingIntent());
				}
				break;
			default:
				Log.d(ActivityUtils.APPTAG, getString(R.string.no_resolution));
			}
		default:
			Log.d(ActivityUtils.APPTAG,
					getString(R.string.unknown_activity_request_code,
							requestCode));
			break;
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		locationClient.connect();
	}

	/*
	 * Register the broadcast receiver and update the log of activity updates
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mBroadcastManager
				.registerReceiver(updateListReceiver, mBroadcastFilter);
		updateActivityHistory();
	}

	@Override
	protected void onPause() {
		mBroadcastManager.unregisterReceiver(updateListReceiver);
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (locationClient.isConnected()) {
			locationClient.removeLocationUpdates(this);
		}
		locationClient.disconnect();
	}

	public boolean isPlayServicesConnected() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (resultCode == ConnectionResult.SUCCESS) {
			return true;
		} else if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
			// ConnectionResult.SERVICE_MISSING
			// ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED
			// ConnectionResult.SERVICE_DISABLED
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode,
					this, 1);
			dialog.show();
		} else {
			Toast.makeText(
					getApplicationContext(),
					"Unfortuanately Location Services are not supported in this device.",
					Toast.LENGTH_LONG).show();
		}
		return false;
	}

	/**
	 * Verify that Google Play services is available before making a request.
	 * 
	 * @return true if Google Play services is available, otherwise false
	 */
	private boolean servicesConnected() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (ConnectionResult.SUCCESS == resultCode) {
			Log.d(ActivityUtils.APPTAG,
					getString(R.string.play_services_available));
			return true;
		} else {
			GooglePlayServicesUtil.getErrorDialog(resultCode, this, 0).show();
			return false;
		}
	}

	/**
	 * Respond to "Start" button by requesting activity recognition updates.
	 * 
	 * @param view
	 *            The view that triggered this method.
	 */
	public void onStartUpdates(View view) {
		if (!servicesConnected()) {
			return;
		}
		mRequestType = ActivityUtils.REQUEST_TYPE.ADD;
		mDetectionRequester.requestUpdates();
	}

	/**
	 * Respond to "Stop" button by canceling updates.
	 * 
	 * @param view
	 *            The view that triggered this method.
	 */
	public void onStopUpdates(View view) {
		if (!servicesConnected()) {
			return;
		}
		mRequestType = ActivityUtils.REQUEST_TYPE.REMOVE;
		mDetectionRemover.removeUpdates(mDetectionRequester
				.getRequestPendingIntent());
		mDetectionRequester.getRequestPendingIntent().cancel();
	}

	public void getLocation(View v) {
		currentLocation = locationClient.getLastLocation();
		if (currentLocation != null) {
			double latitude = currentLocation.getLatitude();
			double longitude = currentLocation.getLongitude();
			float accuracy = currentLocation.getAccuracy();
			String provider = currentLocation.getProvider();
			Toast.makeText(
					getApplicationContext(),
					"Location: Lat: " + latitude + ", " + " Lng: " + longitude
							+ " Accuracy: " + accuracy + "(m)" + " Provider: "
							+ provider, Toast.LENGTH_LONG).show();
			// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD
			// && Geocoder.isPresent()) {
			new ReverseGeocodingTask().execute(currentLocation);
			// }
		} else {
			Toast.makeText(getApplicationContext(), "Location not available",
					Toast.LENGTH_LONG).show();
		}
	}

	public void getWeather(View v) {
		String convertedlocation = AsciiUtils.convertNonAscii(addressStr);
		yahooWeatherUtils.queryYahooWeather(getApplicationContext(),
				convertedlocation, MainActivity.this);
	}

	public void getSongs(View v) {
		listOfSongs = getListOfSongs();
		for (Song song : listOfSongs) {
			Toast.makeText(getApplicationContext(), song.toString(),
					Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * Display the activity detection history stored in the log file
	 */
	private void updateActivityHistory() {
		try {
			List<Spanned> activityDetectionHistory = mLogFile.loadLogFile();
			mStatusAdapter.clear();
			for (Spanned activity : activityDetectionHistory) {
				mStatusAdapter.add(activity);
			}
			if (mStatusAdapter.getCount() > MAX_LOG_SIZE) {
				if (!mLogFile.removeLogFiles()) {
					Log.e(ActivityUtils.APPTAG,
							getString(R.string.log_file_deletion_error));
				}
			}
			mStatusAdapter.notifyDataSetChanged();
		} catch (IOException e) {
			Log.e(ActivityUtils.APPTAG, e.getMessage(), e);
		}
	}

	/**
	 * Broadcast receiver that receives activity update intents It checks to see
	 * if the ListView contains items. If it doesn't, it pulls in history. This
	 * receiver is local only. It can't read broadcast Intents from other apps.
	 */
	BroadcastReceiver updateListReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			updateActivityHistory();
		}
	};

	@Override
	public void onLocationChanged(Location location) {
		currentLocation = location;
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		float accuracy = location.getAccuracy();
		String provider = location.getProvider();
		Toast.makeText(
				getApplicationContext(),
				"Location: Lat: " + latitude + ", " + " Lng: " + longitude
						+ " Accuracy: " + accuracy + "(m)" + " Provider: "
						+ provider, Toast.LENGTH_LONG).show();
		// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD
		// && Geocoder.isPresent()) {
		new ReverseGeocodingTask().execute(location);
		// }
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnected(Bundle bundle) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub

	}

	private String addressStr;

	private class ReverseGeocodingTask extends
			AsyncTask<Location, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(Location... params) {

			Geocoder geocoder = new Geocoder(MainActivity.this,
					Locale.getDefault());
			// get current location from input parameter list
			Location location = params[0];
			List<Address> addresses = null;
			try {
				/*
				 * Call getFromLocation() method with lat, lng of current
				 * location and return at most 1 result
				 */
				addresses = geocoder.getFromLocation(location.getLatitude(),
						location.getLongitude(), 1);

			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			if (addresses != null && addresses.size() > 0) {
				Address address = addresses.get(0);
				String addressString = address.getMaxAddressLineIndex() > 0 ? address
						.getAddressLine(0) : "";
				addressString = addressString.concat(", "
						+ address.getLocality() + ", "
						+ address.getCountryName());
				addressStr = address.getLocality() + ", "
						+ address.getCountryName();
				return addressString;
			} else {
				return null;
			}

		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				Toast.makeText(getApplicationContext(), "Address: " + result,
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getApplicationContext(), "Address not found...",
						Toast.LENGTH_LONG).show();
			}
		}

	}

	@Override
	public void gotWeatherInfo(WeatherInfo weatherInfo) {
		if (weatherInfo != null) {
			Toast.makeText(
					getApplicationContext(),
					"====== CURRENT ======" + "\n" + "date: "
							+ weatherInfo.getCurrentConditionDate() + "\n"
							+ "weather: " + weatherInfo.getCurrentText() + "\n"
							+ "temperature in ºC: "
							+ weatherInfo.getCurrentTempC() + "\n"
							+ "temperature in ºF: "
							+ weatherInfo.getCurrentTempF() + "\n"
							+ "wind chill in ºF: "
							+ weatherInfo.getWindChill() + "\n"
							+ "wind direction: "
							+ weatherInfo.getWindDirection() + "\n"
							+ "wind speed: " + weatherInfo.getWindSpeed()
							+ "\n" + "Humidity: "
							+ weatherInfo.getAtmosphereHumidity() + "\n"
							+ "Pressure: "
							+ weatherInfo.getAtmospherePressure() + "\n"
							+ "Visibility: "
							+ weatherInfo.getAtmosphereVisibility(),
					Toast.LENGTH_LONG).show();

		} else {

		}
	}

	public void getPlaylist(View v) {
		// Available context data
		// weather
		// location
		// activity
		//

		// form url
		// String url =
		// "http://developer.echonest.com/api/v4/playlist/static?api_key=G7HDL2PWOCE6HMK34&genre=dance+pop&format=json&results=20&type=genre-radio";
		String url = "http://developer.echonest.com/api/v4/playlist/static?api_key=G7HDL2PWOCE6HMK34&genre=dance+pop&format=json&results=20&type=genre-radio&bucket=id:spotify-WW&bucket=tracks&limit=true";

		new PlaylistTask().execute(url);
		// startActivity(new Intent(MainActivity.this, DemoPlayer.class));
	}

	public class PlaylistTask extends AsyncTask<String, Void, ArrayList<Music>> {
		private ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			pd = ProgressDialog.show(MainActivity.this, "", "");
		}

		@Override
		protected ArrayList<Music> doInBackground(String... params) {
			ArrayList<Music> playlist = new ArrayList<Music>();
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet getReq = new HttpGet(params[0]);
			try {
				HttpResponse response = client.execute(getReq);
				if (response != null
						&& response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String jsonResponse = EntityUtils.toString(response
							.getEntity());
					JSONObject obj = new JSONObject(jsonResponse);
					JSONObject respObj = obj.getJSONObject("response");
					JSONArray musicArray = respObj.getJSONArray("songs");
					for (int i = 0; i < musicArray.length(); i++) {
						JSONObject musicObj = musicArray.getJSONObject(i);
						String title = musicObj.getString("title");
						String artist = musicObj.getString("artist_name");
						String id = musicObj.getString("id");
						String artistId = musicObj.getString("artist_id");

						JSONArray fIds = musicObj
								.getJSONArray("artist_foreign_ids");
						JSONObject in = fIds.getJSONObject(0);
						String foreignArtistId = in.getString("foreign_id");

						JSONArray ftIds = musicObj.getJSONArray("tracks");
						JSONObject track = ftIds.getJSONObject(0);
						String trackId = track.getString("foreign_id");

						Music m = new Music(id, artist, artistId, title,
								trackId, foreignArtistId);

						playlist.add(m);
					}

				} else {

				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return playlist;
		}

		@Override
		protected void onPostExecute(ArrayList<Music> result) {
			pd.dismiss();

			if (result != null && result.size() > 0) {
				for (Music music : result) {
					Toast.makeText(getApplicationContext(), music.toString(),
							Toast.LENGTH_SHORT).show();
					Log.e("Spotify link", music.getTrackId());
				}
			}
			String tId = result.get(1).getTrackId();
			String uTrack = tId.substring(tId.lastIndexOf(":"));
			// Intent intent = new Intent(Intent.ACTION_VIEW,
			// Uri.parse("https://embed.spotify.com/?uri=spotify:track:"+uTrack));
			// startActivity(intent);

			String uri = "spotify:track:" + uTrack;
			Intent launcher = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
			startActivity(launcher);
		}
	}
}
