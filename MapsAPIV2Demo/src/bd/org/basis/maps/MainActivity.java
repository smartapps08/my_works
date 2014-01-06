package bd.org.basis.maps;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {
	private MapFragment mapFragment;
	private GoogleMap googleMap;
	private FragmentManager manager;

	private TextView txtLocation, txtAddress;
	private LocationClient locationClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isMapAvailable()) {
			setContentView(R.layout.activity_main);
			txtLocation = (TextView) findViewById(R.id.txtLocation);
			txtAddress = (TextView) findViewById(R.id.txtAddress);

			mapFragment = (MapFragment) getFragmentManager().findFragmentById(
					R.id.maps);
			googleMap = mapFragment.getMap();
//			googleMap.setOnMarkerClickListener(new OnMarkerClickListener() {
//
//				@Override
//				public boolean onMarkerClick(Marker m) {
//					Toast.makeText(
//							getApplicationContext(),
//							"Seected Marker: " + m.getPosition().latitude
//									+ ", " + m.getPosition().longitude,
//							Toast.LENGTH_LONG).show();
//					return false;
//				}
//			});

			googleMap.setOnMarkerDragListener(new OnMarkerDragListener() {

				@Override
				public void onMarkerDragStart(Marker m) {
					Toast.makeText(
							getApplicationContext(),
							"Seected Marker: " + m.getPosition().latitude
									+ ", " + m.getPosition().longitude,
							Toast.LENGTH_LONG).show();
				}

				@Override
				public void onMarkerDragEnd(Marker m) {
					Toast.makeText(
							getApplicationContext(),
							"Seected Marker: " + m.getPosition().latitude
									+ ", " + m.getPosition().longitude,
							Toast.LENGTH_LONG).show();
				}

				@Override
				public void onMarkerDrag(Marker m) {

				}
			});

			locationClient = new LocationClient(this, this, this);

		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		locationClient.connect();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		locationClient.disconnect();
	}

	public void placeMarker(View v) {
		// add marker
		Location currentLocation = locationClient.getLastLocation();
		double lat = currentLocation.getLatitude();
		double lng = currentLocation.getLongitude();
		txtLocation.setText(lat + ", " + lng);

		MarkerOptions options = new MarkerOptions()
				.position(new LatLng(lat, lng))
				.title("I am here")
				.snippet("This is my current location")
				.draggable(true)
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
		if (googleMap != null) {
			googleMap.addMarker(options);
		} else {
			Toast.makeText(getApplicationContext(), "Map is null",
					Toast.LENGTH_LONG).show();
		}

		Geocoder geocoder = new Geocoder(this);

		try {
			ArrayList<Address> addresses = (ArrayList<Address>) geocoder
					.getFromLocation(currentLocation.getLatitude(),
							currentLocation.getLongitude(), 5);
			Address addr = addresses.get(0);
			txtAddress.setText(addr.getAddressLine(0) + "-"
					+ addr.getAdminArea() + "-" + addr.getCountryName());
			// place the marker
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean isMapAvailable() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		// resultCode=ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED;
		if (ConnectionResult.SUCCESS == resultCode) {
			return true;
		} else if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
			// Service Missing
			// Service needs update
			// Service disabled
			Dialog d = GooglePlayServicesUtil.getErrorDialog(resultCode, this,
					1);
			d.show();

		} else {
			Toast.makeText(getApplicationContext(),
					"Google Maps API V2 is not supported in your device!",
					Toast.LENGTH_LONG).show();
			finish();
		}

		return false;
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {

	}

	@Override
	public void onConnected(Bundle connectionHint) {
		Location currentLocation = locationClient.getLastLocation();
		double lat = currentLocation.getLatitude();
		double lng = currentLocation.getLongitude();
		txtLocation.setText(lat + ", " + lng);

		MarkerOptions options = new MarkerOptions()
				.position(new LatLng(lat, lng))
				.title("I am here")
				.snippet("This is my current location")
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
		if (googleMap != null) {
			googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					lat, lng), 14.0f));
			googleMap.addMarker(options);
		} else {
			Toast.makeText(getApplicationContext(), "Map is null",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onDisconnected() {

	}
}
