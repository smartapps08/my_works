package com.agme.mapsuserinteractions;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {

	private SupportMapFragment mapFragment;
	private GoogleMap map;

	Marker marker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.maps);
		map = mapFragment.getMap();

		MarkerOptions markerOptions = new MarkerOptions()
				.position(new LatLng(40.9065022, -73.109791))
				.draggable(true)
				.title("My Location")
				.snippet("Stony Brook, NY")
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
		marker = map.addMarker(markerOptions);

		map.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker marker) {
				Log.d("Marker Clicked",
						"Position: " + marker.getPosition().latitude + ", "
								+ marker.getPosition().longitude);
				return false;
			}
		});

		map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
			@Override
			public void onInfoWindowClick(Marker marker) {
				// define necessary action
			}
		});
		map.setOnMarkerDragListener(new OnMarkerDragListener() {
			@Override
			public void onMarkerDragStart(Marker marker) {
				Log.d("Drag Start",
						"Dragging Start: " + marker.getPosition().latitude
								+ ", " + marker.getPosition().longitude);
			}

			@Override
			public void onMarkerDragEnd(Marker marker) {

				Log.d("Drag End",
						"Dragging End: " + marker.getPosition().latitude + ", "
								+ marker.getPosition().longitude);
			}

			@Override
			public void onMarkerDrag(Marker marker) {
				Log.d("Dragging", "Dragging: " + marker.getPosition().latitude
						+ ", " + marker.getPosition().longitude);
			}
		});

		map.setOnMapClickListener(new OnMapClickListener() {
			@Override
			public void onMapClick(LatLng point) {
				// use the point
			}
		});

		map.setOnMapLongClickListener(new OnMapLongClickListener() {
			@Override
			public void onMapLongClick(LatLng point) {
				// use the point

			}
		});
		
		map.setInfoWindowAdapter(new MyInfoWindowAdapter());

	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnected(Bundle connectionHint) {
		// TODO Auto-generated method stub

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

	class MyInfoWindowAdapter implements InfoWindowAdapter {
		private final View myContentsView;

		public MyInfoWindowAdapter() {
			myContentsView = getLayoutInflater().inflate(
					R.layout.layout_info_window, null);
		}

		@Override
		public View getInfoContents(Marker marker) {
			TextView tvTitle = ((TextView) myContentsView
					.findViewById(R.id.txtTitle));
			tvTitle.setText(marker.getTitle());
			TextView tvSnippet = ((TextView) myContentsView
					.findViewById(R.id.txtDetails));
			tvSnippet.setText(marker.getSnippet());

			return myContentsView;
		}

		@Override
		public View getInfoWindow(Marker marker) {
			// TODO Auto-generated method stub
			return null;
		}

	}
}
