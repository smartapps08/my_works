package com.smartapps.photographr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.smartapps.db.DBHelper;
import com.smartapps.lazylist.Photo;
import com.smartapps.staggeredview.StaggeredAdapter;
import com.smartapps.staggeredview.StaggeredGridView;
import com.smartapps.staggeredview.StaggeredGridView.OnItemClickListener;
import com.smartapps.util.Constants;
import com.smartapps.util.Installation;

public class HomeActivity extends SherlockFragmentActivity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {
	// private static final String STATE_SELECTED_NAVIGATION_ITEM =
	// "selected_navigation_item";
	// update interval in millisecond
	private static final int UPDATE_INTERVAL = 5000;
	// Fastest update interval in millisecond
	private static final int FASTEST_INTERVAL = 1000;
	// request object that defines the quality of service parameters
	private LocationRequest locationRequest;
	private LocationClient locationClient;
	public static Location currentLocation;

	// for Fragments
	// SuggestionListFragment suggestionListFragment;
	SuggestionMapFragment suggestionMapFragment;
	SuggestionGridFragment suggestionGridFragment;
	Fragment visibleFragment;

	// private static final int LIST_MODE = 2;
	private static final int MAP_MODE = 1;
	private static final int GRID_MODE = 0;

	private boolean isStartup = false;
	private boolean isFromNotification = false;
	private int accLevel;
	private SharedPreferences prefs;

	public DBHelper dbHelper;
	// private ProgressDialog pd;
	public static ArrayList<Photo> photoList;
	public static ArrayList<String> imageList;

	// User interface
	private ProgressDialog pd;

	private StaggeredGridView gridView;
	private StaggeredAdapter adapter;

	TextView txtRefresh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_grid);

		getSupportActionBar().setDisplayShowTitleEnabled(false);
		txtRefresh = (TextView) findViewById(R.id.txtRefresh);
		gridView = (StaggeredGridView) findViewById(R.id.staggeredGridView1);
		gridView.setDrawSelectorOnTop(true);
		gridView.setColumnCount(2);
		int margin = getResources().getDimensionPixelSize(
				R.dimen.activity_horizontal_margin);
		gridView.setItemMargin(margin); // set the GridView margin
		gridView.setPadding(margin, 0, margin, 0); // have the margin on the

		dbHelper = new DBHelper(this);
		String s = getIntent().getStringExtra("from");

		// for settings
		prefs = getSharedPreferences(Constants.PREF_FILE_NAME,
				Context.MODE_PRIVATE);
		isStartup = prefs.getBoolean(Constants.PREF_SETTINGS_STARTUP_FIELD,
				false);
		accLevel = prefs.getInt(Constants.PREF_SETTINGS_ACCURACY_FIELD, 0);

		locationClient = new LocationClient(this, this, this);
		// setUpNavigation();
		// creating request object with some of parameters set
		locationRequest = LocationRequest.create()
				.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
				.setInterval(UPDATE_INTERVAL)
				.setFastestInterval(FASTEST_INTERVAL);

		if (s != null && s.equals("newdata")) {
			pd = ProgressDialog.show(this, "", "Fetching suggestions...");
			new RequestThread(Installation.id(this)).start();
		} else {
			// else use database
			HomeActivity.photoList = dbHelper.getAllPhoto();
			if (HomeActivity.photoList == null
					|| HomeActivity.photoList.size() == 0) {
				txtRefresh.setVisibility(View.VISIBLE);
				gridView.setVisibility(View.GONE);
			} else {
				txtRefresh.setVisibility(View.GONE);
				gridView.setVisibility(View.VISIBLE);
				adapter = new StaggeredAdapter(HomeActivity.this,
						R.layout.row_staggered_demo, HomeActivity.photoList);
				gridView.setAdapter(adapter);
			}
		}

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(StaggeredGridView parent, View view,
					int position, long id) {
				Intent selected = new Intent(HomeActivity.this,
						PhotoDetailsActivity.class);
				selected.putExtra("selection", position);
				selected.putExtra("from", PhotoDetailsActivity.FROM_GRID);
				startActivity(selected);

			}
		});

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		String s = intent.getStringExtra("from");
		if (s != null && s.equals("newdata")) {
			pd = ProgressDialog.show(this, "", "Fetching suggestions...");
			new RequestThread(Installation.id(this)).start();
		} else {
			// else use database
			HomeActivity.photoList = dbHelper.getAllPhoto();
			adapter = new StaggeredAdapter(HomeActivity.this,
					R.layout.row_staggered_demo, HomeActivity.photoList);
			gridView.setAdapter(adapter);
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (isPlayServicesConnected()) {
			locationClient.connect();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (locationClient.isConnected()) {
			locationClient.removeLocationUpdates(this);
		}
		locationClient.disconnect();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_suggest:
			// send location to get update
			if (isPlayServicesConnected()) {
				currentLocation = locationClient.getLastLocation();
				updateLocation(currentLocation);
			}
			break;
		case R.id.action_view_mode:
			// show map
			startActivity(new Intent(this, PhotoMapActivity.class));
			break;

		case R.id.action_settings:
			// show settings
			startActivity(new Intent(this, PreferenceSettingsActivity.class));
			break;
		case R.id.action_help:
			pd = ProgressDialog.show(this, "", "Fetching suggestions...");
			new RequestThread(Installation.id(this)).start();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
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

	@Override
	public void onLocationChanged(Location location) {
		currentLocation = location;
		// if (!isFromNotification) {
		// updateLocation(currentLocation);
		// }
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		Toast.makeText(getApplicationContext(),
				"Connection failed to Location Services", Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		currentLocation = locationClient.getLastLocation();
		// if (isStartup && !isFromNotification) {
		//
		// updateLocation(currentLocation);
		// }

	}

	@Override
	public void onDisconnected() {
		Toast.makeText(getApplicationContext(),
				"Disconnected from Location Services", Toast.LENGTH_LONG)
				.show();
	}

	public void updateLocation(Location loc) {
		if (loc != null) {
			Toast.makeText(
					getApplicationContext(),
					"This may take a while to get the suggestions. You can wait or leave the screen and you'll be notified whenever suggestions are ready for you.",
					Toast.LENGTH_LONG).show();
			new LocationUpdateThread(Installation.id(this), loc).start();
		} else {
			Toast.makeText(getApplicationContext(),
					"Location not available. Waiting for updates",
					Toast.LENGTH_LONG).show();
		}
	}

	public class LocationUpdateThread extends Thread {
		private Location loc;
		private String deviceId;

		public LocationUpdateThread(String deviceId, Location loc) {
			this.loc = loc;
			this.deviceId = deviceId;
		}

		public void run() {
			double latitude = loc.getLatitude();
			double longitude = loc.getLongitude();
			// float accuracy = loc.getAccuracy();
			// String provider = loc.getProvider();
			float speed = loc.getSpeed();
			int accuracy = -1;
			if (accLevel == 0) {
				accuracy = 16;
			} else if (accLevel == 1) {
				accuracy = 11;
			} else if (accLevel == 2) {
				accuracy = 6;
			} else {
				accuracy = 3;
			}

			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost postReq = new HttpPost(Constants.LOCATION_UPDATE_REQUEST);
			try {
				// Add your data
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						5);
				nameValuePairs.add(new BasicNameValuePair("deviceId", ""
						+ deviceId));
				nameValuePairs.add(new BasicNameValuePair("latitude", ""
						+ latitude));
				nameValuePairs.add(new BasicNameValuePair("longitude", ""
						+ longitude));
				nameValuePairs.add(new BasicNameValuePair("accuracy", ""
						+ accuracy));
				nameValuePairs.add(new BasicNameValuePair("speed", "" + speed));

				postReq.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				HttpResponse response = client.execute(postReq);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity entity = response.getEntity();
					String respStr = EntityUtils.toString(entity);
					JSONObject respJson = new JSONObject(respStr);
					String successValue = respJson.getString("success");
					if (successValue.equalsIgnoreCase("1")) {
						locationSubmitHandler
								.sendEmptyMessage(Constants.SUCCESS);
					} else {
						locationSubmitHandler
								.sendEmptyMessage(Constants.FAILURE);
					}

				} else {
					locationSubmitHandler.sendEmptyMessage(Constants.FAILURE);
				}
			} catch (ClientProtocolException e) {
				locationSubmitHandler.sendEmptyMessage(Constants.FAILURE);
			} catch (IOException e) {
				locationSubmitHandler.sendEmptyMessage(Constants.FAILURE);
			} catch (JSONException e) {
				locationSubmitHandler.sendEmptyMessage(Constants.FAILURE);
			}

		}
	}

	Handler locationSubmitHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Constants.SUCCESS:
				// broadcast to activity
				Toast.makeText(getApplicationContext(),
						"Location Update Successful", Toast.LENGTH_LONG).show();
				break;
			case Constants.FAILURE:
				// broadcast to activity
				Toast.makeText(getApplicationContext(),
						"Location Update Failed", Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
		}
	};

	class RequestThread extends Thread {
		private String deviceId;

		public RequestThread(String devId) {
			this.deviceId = devId;
		}

		public void run() {
			HomeActivity.photoList = new ArrayList<Photo>();
			HomeActivity.imageList = new ArrayList<String>();
			String flickrReq = Constants.SEARCH_URL + "deviceId="
					+ this.deviceId;

			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet getReq = new HttpGet(flickrReq);
			try {
				HttpResponse response = client.execute(getReq);
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == 200) {
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						String jsonStr = EntityUtils.toString(entity);

						// JSONObject obj = new JSONObject(jsonStr);

						JSONArray photosArray = new JSONArray(jsonStr);

						for (int i = 0; i < photosArray.length(); i++) {
							JSONObject photoObj = photosArray.getJSONObject(i);
							String id = photoObj.getString("photo_id");
							String owner = photoObj.getString("owner");
							String secret = photoObj.getString("secret");
							String server = photoObj.getString("server");
							String farm = photoObj.getString("farm");
							String title = photoObj.getString("title");
							String ispublic = photoObj.getString("ispublic");
							String isfriend = photoObj.getString("isfriend");
							String isfamily = photoObj.getString("isfamily");
							double lat = 0;
							double lng = 0;
							try {
								lat = Double.parseDouble(photoObj
										.getString("latitude"));
								lng = Double.parseDouble(photoObj
										.getString("longitude"));
							} catch (NumberFormatException e) {
								lat = -1;
								lng = -1;
							}
							String photoUrl = "http://farm" + farm
									+ ".staticflickr.com/" + server + "/" + id
									+ "_" + secret + ".jpg";
							Photo photo = new Photo(id, owner, secret, server,
									farm, title, ispublic, isfriend, isfamily,
									photoUrl, lat, lng);
							HomeActivity.photoList.add(photo);
							HomeActivity.imageList.add(photoUrl);
						}

						int deleted = dbHelper.deleteAll();
						for (Photo ph : HomeActivity.photoList) {
							long inserted = dbHelper.insertPhoto(ph);
						}

						Log.d("FLICKR RESPONSE", jsonStr);
						handler.sendEmptyMessage(0);
					}
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				handler.sendEmptyMessage(1);
			} catch (IOException e) {
				e.printStackTrace();
				handler.sendEmptyMessage(1);
			} catch (JSONException e) {
				e.printStackTrace();
				handler.sendEmptyMessage(1);
			}
			handler.sendEmptyMessage(1);

		}
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				pd.dismiss();
				if (HomeActivity.photoList == null
						|| HomeActivity.photoList.size() == 0) {
					txtRefresh.setVisibility(View.VISIBLE);
					gridView.setVisibility(View.GONE);
				} else {
					txtRefresh.setVisibility(View.GONE);
					gridView.setVisibility(View.VISIBLE);
					adapter = new StaggeredAdapter(HomeActivity.this,
							R.layout.row_staggered_demo, HomeActivity.photoList);

					gridView.setAdapter(adapter);
				}

			} else {
				pd.dismiss();
			}
		}
	};
}
