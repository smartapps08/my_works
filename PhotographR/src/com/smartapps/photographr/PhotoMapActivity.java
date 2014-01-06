package com.smartapps.photographr;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.smartapps.lazylist.Photo;
import com.smartapps.staggeredview.FileCache;
import com.smartapps.staggeredview.ImageLoader;
import com.smartapps.util.Constants;

public class PhotoMapActivity extends SherlockFragmentActivity {
	private SupportMapFragment fragment;
	private GoogleMap map;
	private ArrayList<Marker> markers;
	ImageLoader loader;

	private SharedPreferences pref;
	private int accLevel;
	private int zoomLevel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_map);

		pref = getSharedPreferences(Constants.PREF_FILE_NAME,
				Context.MODE_PRIVATE);
		accLevel = pref.getInt(Constants.PREF_SETTINGS_ACCURACY_FIELD, 0);
		if (accLevel == 0) {
			zoomLevel = 14;
		} else if (accLevel == 1) {
			zoomLevel = 10;
		} else if (accLevel == 2) {
			zoomLevel = 7;
		} else {
			zoomLevel = 3;
		}

		// Show the Up button in the action bar.
		loader = new ImageLoader(this);
		setupActionBar();
		fragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.maps);
		map = fragment.getMap();

		map.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker marker) {
				int position = markers.indexOf(marker);
				Log.e("Selected Marker", "-------------" + position
						+ "-------------");
				Intent selected = new Intent(PhotoMapActivity.this,
						PhotoDetailsActivity.class);
				selected.putExtra("selection", position);
				selected.putExtra("from", PhotoDetailsActivity.FROM_MAP);
				startActivity(selected);
				
				return true;
			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (map == null) {
			map = fragment.getMap();
			map.clear();
		}
		populateMarkers();
	}

	private void populateMarkers() {
		LatLng coord = new LatLng(HomeActivity.currentLocation.getLatitude(),
				HomeActivity.currentLocation.getLongitude());
		// for changing the center of the map
		// map.moveCamera(CameraUpdateFactory.newLatLng(coord));
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(coord, zoomLevel));

		markers = new ArrayList<Marker>();
		for (Photo ph : HomeActivity.photoList) {
			MarkerOptions markerOptions = new MarkerOptions();
			String title = ph.getTitle();
			// Getting latitude of the place
			double lat = ph.getLat();
			double lng = ph.getLng();
			LatLng latLng = new LatLng(lat, lng);
			// Setting the position for the marker
			markerOptions.position(latLng);
			markerOptions.title(title);

			FileCache cache = new FileCache(this);
			File f = cache.getFile(ph.getImgUrl());
			Bitmap b = BitmapFactory.decodeFile(f.getAbsolutePath());
			Bitmap out = Bitmap.createScaledBitmap(b, 120, 90, false);
			if (out != null) {

				markerOptions.icon(BitmapDescriptorFactory.fromBitmap(out));
			} else {
				markerOptions.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
			}
			markerOptions.anchor(0.5f, 1);
			// Placing a marker on the touched position
			Marker marker = map.addMarker(markerOptions);

			markers.add(marker);
		}
	}

	private void setupActionBar() {
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.photo_map, menu);
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

}
