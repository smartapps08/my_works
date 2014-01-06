package com.agme.geofenceonmap;

import java.util.ArrayList;

import android.app.Dialog;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import com.agme.geofenceonmap.RadiusInputDialog.RadiusInputDialogListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener, LocationListener,
		OnMapLongClickListener, RadiusInputDialogListener {

	private LocationClient locationClient;
	private Location currentLocation;
	// request object that defines the quality of service parameters
	private LocationRequest locationRequest;

	// update interval in millisecond
	private static final int UPDATE_INTERVAL = 5000;
	// Fastest update interval in millisecond
	private static final int FASTEST_INTERVAL = 1000;

	private SupportMapFragment mapFragment;
	private GoogleMap map;

	private Marker currentLocationMarker;
	private LatLng geofenceCenter;

	private GeofenceRequester requester;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.maps);
		map = mapFragment.getMap();
		map.setOnMapLongClickListener(this);

		locationClient = new LocationClient(this, this, this);

		// creating request object with some of parameters set
		locationRequest = LocationRequest.create()
				.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
				.setInterval(UPDATE_INTERVAL)
				.setFastestInterval(FASTEST_INTERVAL);
		requester = new GeofenceRequester(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		locationClient.connect();
	}

	@Override
	protected void onStop() {
		super.onStop();
		locationClient.disconnect();
	}

	@Override
	public void onLocationChanged(Location location) {
		updateLocation(location);
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {

	}

	@Override
	public void onConnected(Bundle connectionHint) {
		currentLocation = locationClient.getLastLocation();
		if (currentLocation != null) {
			updateLocation(currentLocation);
			Log.e("Location", currentLocation.getLatitude()+", "+currentLocation.getLongitude());
		}
	}

	@Override
	public void onDisconnected() {

	}

	public boolean isPlayServicesConnected() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (resultCode == ConnectionResult.SUCCESS) {
			return true;
		} else if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
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

	public void updateLocation(Location location) {
		currentLocation = location;
		double latitude = currentLocation.getLatitude();
		double longitude = currentLocation.getLongitude();
		LatLng coord = new LatLng(latitude, longitude);
		// for changing the center of the map
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(coord, 12.0f));
		MarkerOptions markerOptions = new MarkerOptions()
				.position(coord)
				.title("My Location")
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
		if (currentLocationMarker == null) {
			currentLocationMarker = map.addMarker(markerOptions);
		} else {
			currentLocationMarker.setPosition(coord);
		}
	}

	@Override
	public void onMapLongClick(LatLng point) {
		geofenceCenter = point;
		showRadiusInputDialog();
	}

	private void showRadiusInputDialog() {
		FragmentManager fm = getSupportFragmentManager();
		RadiusInputDialog inputDialog = new RadiusInputDialog();
		inputDialog.show(fm, "fragment_input_radius");
	}

	@Override
	public void onFinishInputRadius(int radius) {
		Toast.makeText(getApplicationContext(), "Radius: " + radius,
				Toast.LENGTH_LONG).show();
		// add new geofence using geofenceCenter and radius
		// add a marker at geofenceCenter and draw the circle with
		// geofenceCenter as center and radius as the radius value
		addAndMarkGeofenceAdded(geofenceCenter, radius);
	}

	private Circle geofenceCircle;
	private Marker geofenceCenterMarker;

	private int lastGeofence = 0;

	private void addAndMarkGeofenceAdded(LatLng center, int radius) {
		// add geofence
		Geofence geofence = new Geofence.Builder()
				.setRequestId(lastGeofence + "")
				.setTransitionTypes(
						Geofence.GEOFENCE_TRANSITION_ENTER
								| Geofence.GEOFENCE_TRANSITION_EXIT)
				.setCircularRegion(center.latitude, center.longitude, radius)
				.setExpirationDuration(Geofence.NEVER_EXPIRE).build();
		lastGeofence++;
		ArrayList<Geofence> geofences = new ArrayList<Geofence>();
		geofences.add(geofence);
		requester.addGeofences(geofences);

		MarkerOptions markerOptions = new MarkerOptions()
				.position(center)
				.title("Geofence")
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
		geofenceCenterMarker = map.addMarker(markerOptions);
		CircleOptions circleOptions = new CircleOptions()
				.center(geofenceCenter).radius(radius).strokeColor(Color.RED)
				.fillColor(Color.argb(90, 155, 0, 0)).strokeWidth(2);
		geofenceCircle = map.addCircle(circleOptions);
	}

}
