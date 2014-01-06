package com.agme.maptypes;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends SherlockFragmentActivity implements
		OnNavigationListener {
	private static final String[] MAP_TYPES = { "Normal", "Hybrid",
			"Satellite", "Terrain", "None" };
	private ArrayAdapter<String> adapter;
	private SupportMapFragment mapFragment;
	private GoogleMap map;
	private GoogleMapOptions options;
	private CameraPosition cameraPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		options = new GoogleMapOptions();
		LatLng latlng = new LatLng(37.422006, -122.084095);

		// defining Camera position to get a 3D view
		cameraPosition = new CameraPosition(latlng, 18.0f, 30.0f, 135.0f);

		// defining properties of GoogleMapOptions
		options.mapType(GoogleMap.MAP_TYPE_SATELLITE).compassEnabled(true)
				.rotateGesturesEnabled(true).tiltGesturesEnabled(true)
				.camera(cameraPosition);

		// use options to get new instance of MapFragment before adding using
		// FragmentTransaction
		mapFragment = SupportMapFragment.newInstance(options);

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.container, mapFragment, "map");
		ft.commit();
		map = mapFragment.getMap();
		if (map == null) {
			Toast.makeText(getApplicationContext(), "Map is not available yet",
					Toast.LENGTH_LONG).show();
		}

	}

	private boolean isMapFragmentReady() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (map == null) {
			map = mapFragment.getMap();
			// Check if we were successful in obtaining the map.
			if (map != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (isMapFragmentReady()) {
			setUpActionBar();
//			map.getUiSettings().setCompassEnabled(false);
//			map.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(23.34534, 90.23523), 15f, 50.5f, 122.0f)));
			
		}

	}

	private void setUpActionBar() {
		ActionBar actionBar = getSupportActionBar();

		// initalize the adapter object with map types
		adapter = new ArrayAdapter<String>(this,
				R.layout.sherlock_spinner_item, MAP_TYPES);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// Hide the title if you want to get more space
		actionBar.setDisplayShowTitleEnabled(false);

		// set navigation type
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		// set the callback for listening to change
		actionBar.setListNavigationCallbacks(adapter, this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean isMapAvailable() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);

		// check resultcode
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
			// Showing message to user when the problem is not recoverable
			Toast.makeText(
					getApplicationContext(),
					"Unfortuanately Google Maps API V2 is not supported in this device.",
					Toast.LENGTH_LONG).show();
			finish();
		}
		return false;
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		switch (itemPosition) {
		case 0:
			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			return true;
		case 1:
			map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			return true;
		case 2:
			map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			return true;
		case 3:
			map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			return true;
		case 4:
			map.setMapType(GoogleMap.MAP_TYPE_NONE);
			return true;
		default:
			break;
		}
		return false;
	}

	private static final String SELECTED_TYPE = "selected";

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(SELECTED_TYPE, getSupportActionBar()
				.getSelectedNavigationIndex());
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		getSupportActionBar().setSelectedNavigationItem(
				savedInstanceState.getInt(SELECTED_TYPE));
	}

}
