package bd.org.basis.locapi.good;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements LocationListener {

	private TextView txtLat, txtLng, txtAcc, txtPro;
	LocationManager locationManager;
	String bestProvider;
	boolean gpsEnabled;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		txtLat = (TextView) findViewById(R.id.txtLat);
		txtLng = (TextView) findViewById(R.id.txtLng);
		txtAcc = (TextView) findViewById(R.id.txtAcc);
		txtPro = (TextView) findViewById(R.id.txtPro);

		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			gpsEnabled = true;
		} else {
			gpsEnabled = false;
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("Enable GPS");
			alert.setMessage("Do you want to enable?");
			alert.setPositiveButton("Enable GPS",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int arg1) {
							Intent i = new Intent(
									Settings.ACTION_LOCATION_SOURCE_SETTINGS);
							startActivity(i);
							dialog.dismiss();

						}
					});
			alert.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int arg1) {
							dialog.dismiss();

						}
					});
			alert.show();
		}
		setUp();
	}

	public void setUp() {
		Location gpsLocation = null;
		Location netLocation = null;
		locationManager.removeUpdates(this);
		gpsLocation = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		netLocation = locationManager
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		if (gpsLocation != null && netLocation != null) {
			// find the better one
			Location better = getBetterLocation(netLocation, gpsLocation);
			locationManager.requestLocationUpdates(better.getProvider(), 5000,
					10, this);
			txtLat.setText("" + better.getLatitude());
			txtLng.setText("" + better.getLongitude());
			txtAcc.setText("No Data");
			txtPro.setText("No Data");

		} else if (gpsLocation != null) {
			// use net
			txtLat.setText("" + gpsLocation.getLatitude());
			txtLng.setText("" + gpsLocation.getLongitude());
			txtAcc.setText("No Data");
			txtPro.setText("No Data");
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 5000, 10, this);
		} else if (netLocation != null) {
			// use net
			txtLat.setText("" + netLocation.getLatitude());
			txtLng.setText("" + netLocation.getLongitude());
			txtAcc.setText("No Data");
			txtPro.setText("No Data");
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 5000, 10, this);
		} else {
			txtLat.setText("No Data");
			txtLng.setText("No Data");
			txtAcc.setText("No Data");
			txtPro.setText("No Data");
		}

	}

	private Location getBetterLocation(Location newLocation,
			Location currentBestLocation) {
		if (currentBestLocation == null) {
			// A new location is always better than no location
			return newLocation;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = newLocation.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > 60000;
		boolean isSignificantlyOlder = timeDelta < 60000;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use
		// the new location
		// because the user has likely moved.
		if (isSignificantlyNewer) {
			return newLocation;
			// If the new location is more than two minutes older, it must be
			// worse
		} else if (isSignificantlyOlder) {
			return currentBestLocation;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (newLocation.getAccuracy() - currentBestLocation
				.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;

		// Determine location quality using a combination of timeliness and
		// accuracy
		if (isMoreAccurate) {
			return newLocation;
		} else if (isNewer && !isLessAccurate) {
			return newLocation;
		}
		return currentBestLocation;
	}

	@Override
	public void onLocationChanged(Location location) {
		Toast.makeText(
				getApplicationContext(),
				location.getLatitude() + " " + location.getLongitude() + " "
						+ location.getAltitude() + " " + location.getAccuracy(),
				Toast.LENGTH_LONG).show();
		txtLat.setText("" + location.getLatitude());
		txtLat.setText("" + location.getLongitude());

	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		locationManager.removeUpdates(this);
	}

}
