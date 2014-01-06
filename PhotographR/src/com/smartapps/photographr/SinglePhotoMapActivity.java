package com.smartapps.photographr;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.smartapps.lazylist.Photo;
import com.smartapps.staggeredview.FileCache;
import com.smartapps.util.DirectionsJSONParser;

public class SinglePhotoMapActivity extends SherlockFragmentActivity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {

	private Photo selectedPhoto;
	private int selection;
	// update interval in millisecond
	private static final int UPDATE_INTERVAL = 5000;
	// Fastest update interval in millisecond
	private static final int FASTEST_INTERVAL = 1000;
	// request object that defines the quality of service parameters
	private LocationRequest locationRequest;
	private LocationClient locationClient;
	public Location currentLocation;

	private SupportMapFragment fragment;
	private GoogleMap map;
	private Marker currentLocationMarker;
	private Marker photoMarker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_photo_map);
		// Show the Up button in the action bar.
		setupActionBar();
		selection = getIntent().getIntExtra("selection", 0);
		selectedPhoto = HomeActivity.photoList.get(selection);
		locationClient = new LocationClient(this, this, this);
		locationRequest = LocationRequest.create()
				.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
				.setInterval(UPDATE_INTERVAL)
				.setFastestInterval(FASTEST_INTERVAL);

		fragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.maps);
		map = fragment.getMap();

	}

	@Override
	protected void onStart() {
		super.onStart();
		if (isPlayServicesConnected()) {
			locationClient.connect();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (locationClient.isConnected()) {
			locationClient.requestLocationUpdates(locationRequest, this);
		}
		if (map == null) {
			map = fragment.getMap();
		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		if (locationClient.isConnected()) {
			locationClient.removeLocationUpdates(this);
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		locationClient.disconnect();
	}

	private void setupActionBar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.single_photo_map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onLocationChanged(Location location) {
		currentLocation = location;
		// place marker and direction
		placeMarkerAndDirections();
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnected(Bundle connectionHint) {
		currentLocation = locationClient.getLastLocation();
		// place marker and show direction
		placeMarkerAndDirections();

	}

	private void placeMarkerAndDirections() {

		if (currentLocation != null) {
			if (currentLocationMarker != null) {
				currentLocationMarker.remove();
			}
			if (photoMarker != null) {
				photoMarker.remove();
			}
			map.clear();
			MarkerOptions markerOptions = new MarkerOptions();
			String title = selectedPhoto.getTitle();
			// Getting latitude of the place
			double lat = selectedPhoto.getLat();
			double lng = selectedPhoto.getLng();
			LatLng latLng = new LatLng(lat, lng);
			// Setting the position for the marker
			markerOptions.position(latLng);
			markerOptions.title(title);

			FileCache cache = new FileCache(this);
			File f = cache.getFile(selectedPhoto.getImgUrl());
			Bitmap b = BitmapFactory.decodeFile(f.getAbsolutePath());
			Bitmap out = Bitmap.createScaledBitmap(b, 120, 90, false);
			if (out != null) {

				markerOptions.icon(BitmapDescriptorFactory.fromBitmap(out));
			} else {
				markerOptions.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
			}
			markerOptions.anchor(0.5f, 1);
			photoMarker = map.addMarker(markerOptions);

			MarkerOptions markerOptions1 = new MarkerOptions();
			title = "My Location";
			// Getting latitude of the place

			LatLng latLngCurrent = new LatLng(currentLocation.getLatitude(),
					currentLocation.getLongitude());
			// Setting the position for the marker
			markerOptions1.position(latLngCurrent);
			markerOptions1.title(title);

			markerOptions1.icon(BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_RED));
			currentLocationMarker = map.addMarker(markerOptions1);
			map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngCurrent,
					16));
			// Getting URL to the Google Directions API
			String url = getDirectionsUrl(latLngCurrent, latLng);
			DownloadTask downloadTask = new DownloadTask();

			// Start downloading json data from Google Directions API
			downloadTask.execute(url);
		}

	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub

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

	private String getDirectionsUrl(LatLng origin, LatLng dest) {

		// Origin of route
		String str_origin = "origin=" + origin.latitude + ","
				+ origin.longitude;
		// Destination of route
		String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
		// Sensor enabled
		String sensor = "sensor=false";
		// Building the parameters to the web service
		String parameters = str_origin + "&" + str_dest + "&" + sensor;
		// Output format
		String output = "json";
		// Building the url to the web service
		String url = "https://maps.googleapis.com/maps/api/directions/"
				+ output + "?" + parameters;
		return url;
	}

	/** A method to download json data from url */
	private String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);

			// Creating an http connection to communicate with url
			urlConnection = (HttpURLConnection) url.openConnection();

			// Connecting to url
			urlConnection.connect();

			// Reading data from url
			iStream = urlConnection.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));

			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			data = sb.toString();

			br.close();

		} catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}
		return data;
	}

	// Fetches data from url passed
	private class DownloadTask extends AsyncTask<String, Void, String> {

		// Downloading data in non-ui thread
		@Override
		protected String doInBackground(String... url) {

			// For storing data from web service
			String data = "";

			try {
				// Fetching the data from web service
				data = downloadUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		// Executes in UI thread, after the execution of
		// doInBackground()
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			ParserTask parserTask = new ParserTask();

			// Invokes the thread for parsing the JSON data
			parserTask.execute(result);

		}
	}

	/** A class to parse the Google Places in JSON format */
	private class ParserTask extends
			AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

		// Parsing the data in non-ui thread
		@Override
		protected List<List<HashMap<String, String>>> doInBackground(
				String... jsonData) {

			JSONObject jObject;
			List<List<HashMap<String, String>>> routes = null;

			try {
				jObject = new JSONObject(jsonData[0]);
				DirectionsJSONParser parser = new DirectionsJSONParser();

				// Starts parsing data
				routes = parser.parse(jObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return routes;
		}

		// Executes in UI thread, after the parsing process
		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> result) {
			ArrayList<LatLng> points = null;
			PolylineOptions lineOptions = null;
			// MarkerOptions markerOptions = new MarkerOptions();

			// Traversing through all the routes
			for (int i = 0; i < result.size(); i++) {
				points = new ArrayList<LatLng>();
				lineOptions = new PolylineOptions();

				// Fetching i-th route
				List<HashMap<String, String>> path = result.get(i);

				// Fetching all the points in i-th route
				for (int j = 0; j < path.size(); j++) {
					HashMap<String, String> point = path.get(j);

					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					LatLng position = new LatLng(lat, lng);

					points.add(position);
				}

				// Adding all the points in the route to LineOptions
				lineOptions.addAll(points);
				lineOptions.width(4);
				lineOptions.color(Color.RED);

			}

			// Drawing polyline in the Google Map for the i-th route
			map.addPolyline(lineOptions);
		}
	}
}
