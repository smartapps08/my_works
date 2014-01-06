package com.agme.drawingshapes;

import java.util.ArrayList;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends SherlockFragmentActivity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {
	private SupportMapFragment mapFragment;
	private GoogleMap map;
	private Polyline polyline;
	private Polygon polygon;
	private Circle circle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.maps);
		map = mapFragment.getMap();

		// ArrayList<LatLng> polylinePoints = new ArrayList<LatLng>();
		// polylinePoints.add(new LatLng(40.768452, -73.981604));
		// polylinePoints.add(new LatLng(40.800491, -73.958344));
		// polylinePoints.add(new LatLng(40.796853, -73.949417));
		// polylinePoints.add(new LatLng(40.764421, -73.973536));
		// polyline = drawPolyline(polylinePoints);
		//
		// ArrayList<LatLng> polygonPoints = new ArrayList<LatLng>();
		// polygonPoints.add(new LatLng(40.798737, -73.988854));
		// polygonPoints.add(new LatLng(40.830177, -73.946968));
		// polygonPoints.add(new LatLng(40.801336, -73.90268));
		// polygonPoints.add(new LatLng(40.751679, -73.956238));
		// polygonPoints.add(new LatLng(40.75714, -74.014603));
		// polygon = drawPolygon(polygonPoints);
		//
		// circle = drawCircle(new LatLng(40.764161, -73.973021), 500);

		
		map.getUiSettings().setCompassEnabled(true);
		map.getUiSettings().setZoomControlsEnabled(true);
		map.setMyLocationEnabled(true);
		map.getUiSettings().setMyLocationButtonEnabled(true);

		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(new LatLng(40.764161, -73.973021)) 
				.zoom(17) // Sets the zoom
				.bearing(90) // Sets the orientation of the camera to east
				.tilt(30) // Sets the tilt of the camera to 30 degrees
				.build(); // Creates a CameraPosition from the builder
		map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

	}

	private Polyline drawPolyline(ArrayList<LatLng> points) {
		PolylineOptions openBoundaryOptions = new PolylineOptions()
				.addAll(points).color(Color.GREEN).width(5).geodesic(true)
				.zIndex(0).visible(true);
		return map.addPolyline(openBoundaryOptions);
	}

	private Polygon drawPolygon(ArrayList<LatLng> points) {
		PolygonOptions areaOption = new PolygonOptions().addAll(points)
				.strokeColor(Color.RED).fillColor(Color.TRANSPARENT)
				.strokeWidth(8).geodesic(true).zIndex(1).visible(true);
		return map.addPolygon(areaOption);
	}

	private Circle drawCircle(LatLng center, int radius) {
		CircleOptions circleOptions = new CircleOptions().center(center)
				.radius(radius).strokeColor(Color.BLUE)
				.fillColor(Color.argb(90, 0, 0, 150)).strokeWidth(5).zIndex(2);
		return map.addCircle(circleOptions);
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
	}

	@Override
	public void onConnected(Bundle connectionHint) {
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
}
