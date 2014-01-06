package com.agme.locationdemo;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {

	private TextView txtConnectionStatus, txtLocation;
	private TextView txtAddress;
	private LocationClient locationClient;
	private Location currentLocation;

	// update interval in millisecond
	private static final int UPDATE_INTERVAL = 5000;

	// Fastest update interval in millisecond
	private static final int FASTEST_INTERVAL = 1000;

	// request object that defines the quality of service parameters
	private LocationRequest locationRequest;

	private SupportMapFragment mapFragment;
	private GoogleMap map;

	private Marker currentLocationMarker;

	private Intent intentForService;
	private PendingIntent pendingIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		txtConnectionStatus = (TextView) findViewById(R.id.txtConnectionStatus);
		txtLocation = (TextView) findViewById(R.id.txtLocation);
		txtAddress = (TextView) findViewById(R.id.txtAddress);

		mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.maps);
		map = mapFragment.getMap();

		locationClient = new LocationClient(this, this, this);

		// creating request object with some of parameters set
		locationRequest = LocationRequest.create()
				.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
				.setInterval(UPDATE_INTERVAL);

	}

	public void requestForPendingIntent(View v) {
		// Define an intent
		intentForService = new Intent(this, LocationService.class);
		// Define the PendingIntent so that LocationService gets notified with
		// this PendingIntent
		pendingIntent = PendingIntent.getService(this, 1, intentForService, 0);
		if (isPlayServicesConnected()) {
			locationClient.requestLocationUpdates(locationRequest,
					pendingIntent);
		}

	}

	public void startUpdates(View v) {
		if (isPlayServicesConnected()) {
			locationClient.requestLocationUpdates(locationRequest, this);
		}
	}

	public void stopUpdates(View v) {
		if (isPlayServicesConnected()) {
			locationClient.removeLocationUpdates(this);
		}
	}

	public void placeMarker(View v) {

		LatLng coord = new LatLng(currentLocation.getLatitude(),
				currentLocation.getLongitude());
		// for changing the center of the map
		map.moveCamera(CameraUpdateFactory.newLatLng(coord));
		MarkerOptions markerOptions = new MarkerOptions()
				.position(coord)
				.title("My Location")
				.snippet(txtAddress.getText().toString())
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
		CircleOptions circleOptions = new CircleOptions().center(coord).radius(
				200.0);

		Circle circle = map.addCircle(circleOptions);

		// adding a marker
		currentLocationMarker = map.addMarker(markerOptions);

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

	/*
	 * This method is called if connection attempts to Location Services fail
	 */
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		Toast.makeText(getApplicationContext(),
				"Connection failed to Location Services", Toast.LENGTH_LONG)
				.show();
		txtConnectionStatus.setText("Connection Status: Failed to Connect");
	}

	/*
	 * This method is called when LocationClient object is successfully
	 * connected to Location Services.
	 */

	@Override
	public void onConnected(Bundle connectionHint) {
		Toast.makeText(getApplicationContext(),
				"Connected to Location Services", Toast.LENGTH_LONG).show();
		txtConnectionStatus.setText("Connection Status: Connected");

		updateLocation();
	}

	/*
	 * This method is called when LocationClient object is disconnected from
	 * Location Services.
	 */
	@Override
	public void onDisconnected() {
		Toast.makeText(getApplicationContext(),
				"Disconnected from Location Services", Toast.LENGTH_LONG)
				.show();
		txtConnectionStatus.setText("Connection Status: Disconnected");
	}

	public void refreshLocation(View v) {
		if (isPlayServicesConnected()) {
			updateLocation();
		}
	}

	@SuppressLint("NewApi")
	public void updateLocation() {
		currentLocation = locationClient.getLastLocation();
		if (currentLocation != null) {
			double latitude = currentLocation.getLatitude();
			double longitude = currentLocation.getLongitude();
			float accuracy = currentLocation.getAccuracy();
			String provider = currentLocation.getProvider();
			txtLocation.setText("Location: Lat: " + latitude + ", " + " Lng: "
					+ longitude + " Accuracy: " + accuracy + "(m)"
					+ " Provider: " + provider);
			// Ensure that a Geocoder services is available
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD
					&& Geocoder.isPresent()) {
				new ReverseGeocodingTask().execute(currentLocation);
			}
		} else {
			txtLocation.setText("Location not available");
			txtAddress.setText("Address not available");
		}
	}

	@SuppressLint("NewApi")
	@Override
	public void onLocationChanged(Location location) {
		currentLocation = location;
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		float accuracy = location.getAccuracy();
		String provider = location.getProvider();
		txtLocation.setText("Location: Lat: " + latitude + ", " + " Lng: "
				+ longitude + " Accuracy: " + accuracy + "(m)" + " Provider: "
				+ provider);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD
				&& Geocoder.isPresent()) {
			new ReverseGeocodingTask().execute(location);
		}
	}

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
				return addressString;
			} else {
				return null;
			}

		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				txtAddress.setText("Address: " + result);
			} else {
				txtAddress.setText("Address not found...");
			}
		}

	}

}
