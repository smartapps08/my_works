package com.agme.nearme;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class PlacesListActivity extends SherlockFragmentActivity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {
	// constants
	private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
	private static final String API_KEY = "AIzaSyC6mmZixNqllZY3a385qFCUahoRRQ-Zq6U";
	private static final String[] SEARCH_KEYWORD = { "restaurant", "bar",
			"cafe", "gas_station", "pharmacy", "shopping_mall" };

	private String category;
	private int categoryIndex;

	private ActionBar actionBar;
	// for location data
	private LocationClient locationClient;
	private Location currentLocation;

	// update interval in millisecond
	private static final int UPDATE_INTERVAL = 5000;
	// Fastest update interval in millisecond
	private static final int FASTEST_INTERVAL = 1000;

	// request object that defines the quality of service parameters
	private LocationRequest locationRequest;

	// For place list
	private ArrayList<Place> places;

	// user interface
	private ListView lvPlaces;
	private TextView txtNoResult;

	// list adapter
	private PlacesListAdapter listAdapter;

	// for Maps
	private SupportMapFragment mapFragment;
	private GoogleMap map;
	private ArrayList<Marker> markers;

	// search area
	private Circle searchCircle;

	// radius
	private int radius;
	private SharedPreferences pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_places);
		lvPlaces = (ListView) findViewById(R.id.lvPlaces);
		txtNoResult = (TextView) findViewById(R.id.txtNoResult);
		mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.maps);
		map = mapFragment.getMap();

		category = getIntent().getStringExtra("category");
		categoryIndex = getIntent().getIntExtra("categoryIndex", 0);

		setupActionBar();

		locationClient = new LocationClient(this, this, this);

		// creating request object with some of parameters set
		locationRequest = LocationRequest.create()
				.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
				.setInterval(UPDATE_INTERVAL)
				.setFastestInterval(FASTEST_INTERVAL).setNumUpdates(1);
		pref = getSharedPreferences("nearme", Context.MODE_PRIVATE);
	}

	@Override
	protected void onStart() {
		super.onStart();
		locationClient.connect();
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
		getSupportMenuInflater().inflate(R.menu.places_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_settings:
			// start settings activity
			startActivity(new Intent(this, SettingsActivity.class));
			break;
		case R.id.action_refresh:
			// refreshing the list
			if (currentLocation != null) {
				if (isConnectedToInternet()) {
					new PlaceSearchQueryTask().execute(createRequestUrl());
				} else {
					Toast.makeText(
							getApplicationContext(),
							"Internet not available. Consider enabling the Internet and try again.",
							Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(
						getApplicationContext(),
						"Location is not available. Starting request for an update",
						Toast.LENGTH_LONG).show();
				startSingleUpdate();
			}
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

	private void setupActionBar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public void onLocationChanged(Location location) {
		currentLocation = location;
		if (isConnectedToInternet()) {
			new PlaceSearchQueryTask().execute(createRequestUrl());
		} else {
			Toast.makeText(
					getApplicationContext(),
					"Internet not available. Consider enabling the Internet and try again.",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		Toast.makeText(getApplicationContext(),
				"Connection failed to Location Services", Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		Toast.makeText(getApplicationContext(),
				"Connected to Location Services", Toast.LENGTH_LONG).show();
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

			// Draw current location marker
			MarkerOptions markerOptions = new MarkerOptions()
					.position(
							new LatLng(currentLocation.getLatitude(),
									currentLocation.getLongitude()))
					.title("My location")
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
			map.addMarker(markerOptions);
			// Draw search circle
			CircleOptions circleOptions = new CircleOptions()
					.center(new LatLng(currentLocation.getLatitude(),
							currentLocation.getLongitude())).radius(radius)
					.strokeColor(Color.BLUE)
					.fillColor(Color.argb(50, 0, 0, 150)).strokeWidth(2)
					.zIndex(1);
			searchCircle = map.addCircle(circleOptions);
			if (places == null) {
				if (isConnectedToInternet()) {
					new PlaceSearchQueryTask().execute(createRequestUrl());
				} else {
					Toast.makeText(
							getApplicationContext(),
							"Internet not available. Consider enabling the Internet and try again.",
							Toast.LENGTH_LONG).show();
				}
			}
		} else {
			Toast.makeText(
					getApplicationContext(),
					"Location is not available. Starting request for an update",
					Toast.LENGTH_LONG).show();
			startSingleUpdate();
		}
	}

	@Override
	public void onDisconnected() {
		Toast.makeText(getApplicationContext(),
				"Disconnected from Location Services", Toast.LENGTH_LONG)
				.show();
	}

	public void startSingleUpdate() {
		if (isPlayServicesConnected()) {
			locationClient.requestLocationUpdates(locationRequest, this);
		}
	}

	public void stopSingleUpdate() {
		if (isPlayServicesConnected()) {
			locationClient.removeLocationUpdates(this);
		}
	}

	private String createRequestUrl() {

		radius = pref.getInt("radius", 1000);
		String url = BASE_URL + "location=" + currentLocation.getLatitude()
				+ "," + currentLocation.getLongitude() + "&radius=" + radius
				+ "&sensor=true&types=" + SEARCH_KEYWORD[categoryIndex]
				+ "&key=" + API_KEY;
		url = url.replace(" ", "%20");
		return url;

	}

	private class PlaceSearchQueryTask extends AsyncTask<String, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		// fetch and parse place data
		@Override
		protected Void doInBackground(String... params) {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet getRequest = new HttpGet(params[0]);
			try {
				HttpResponse response = client.execute(getRequest);
				if (response != null
						&& response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String jsonResponse = EntityUtils.toString(response
							.getEntity());
					JSONObject responseObject = new JSONObject(jsonResponse);
					JSONArray results = responseObject.getJSONArray("results");
					places = new ArrayList<Place>();
					for (int i = 0; i < results.length(); i++) {
						JSONObject result = results.getJSONObject(i);
						String id = result.getString("id");
						String name = result.getString("name");
						String icon = result.getString("icon");
						double rating = 0.0;
						if (result.has("rating")) {
							rating = result.getDouble("rating");
						}
						String vicinity = result.getString("vicinity");
						JSONObject location = result.getJSONObject("geometry")
								.getJSONObject("location");
						double latitude = location.getDouble("lat");
						double longitude = location.getDouble("lng");

						Place place = new Place(id, name, icon, rating,
								vicinity, latitude, longitude);
						Log.e("Place " + i, place.toString());
						places.add(place);
					}

				}

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (places == null || places.size() == 0) {
				txtNoResult.setVisibility(View.VISIBLE);
			} else {
				txtNoResult.setVisibility(View.GONE);
				listAdapter = new PlacesListAdapter(PlacesListActivity.this,
						places);
				lvPlaces.setAdapter(listAdapter);
				// setting camera
				CameraPosition cameraPosition = new CameraPosition.Builder()
						.target(new LatLng(currentLocation.getLatitude(),
								currentLocation.getLongitude())).zoom(14)
						.build();
				map.animateCamera(CameraUpdateFactory
						.newCameraPosition(cameraPosition));
				markers = new ArrayList<Marker>();
				for (int i = 0; i < places.size(); i++) {
					Place place = places.get(i);
					MarkerOptions markerOptions = new MarkerOptions()
							.position(
									new LatLng(place.getLatitude(), place
											.getLongitude()))
							.title(place.getName())
							.snippet(place.getVicinity())
							.icon(BitmapDescriptorFactory
									.defaultMarker(BitmapDescriptorFactory.HUE_RED));
					Marker marker = map.addMarker(markerOptions);
					markers.add(marker);
				}
			}
		}
	}

	private boolean isConnectedToInternet() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
		if (netInfo != null && (netInfo.isAvailable() && netInfo.isConnected())) {
			return true;
		} else {
			return false;
		}
	}

}
