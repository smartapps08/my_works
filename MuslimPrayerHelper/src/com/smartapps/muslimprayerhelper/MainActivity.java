package com.smartapps.muslimprayerhelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.smartapps.helpers.BijoyFontUtil;
import com.smartapps.helpers.PrayTime;

public class MainActivity extends SherlockFragmentActivity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {
	private TextView txtLabelCaption, txtLabelCaption2, txtLabelCaption3,
			txtFajr, txtFajrTime, txtDuhr, txtDuhrTime, txtAsr, txtAsrTime,
			txtMaghrib, txtMaghribTime, txtIsha, txtIshaTime, txtSunrise,
			txtSunriseTime, txtSunset, txtSunsetTime;

	private TextView txtAmPm1, txtAmPm2, txtAmPm3, txtAmPm4, txtAmPm5,
			txtAmPm6, txtAmPm7;

	private Typeface banglaTypeface;
	private BijoyFontUtil tfUtil;

	private LocationClient locationClient;
	private Location currentLocation;

	// update interval in millisecond
	private static final int UPDATE_INTERVAL = 10000;

	// Fastest update interval in millisecond
	private static final int FASTEST_INTERVAL = 2000;

	// request object that defines the quality of service parameters
	private LocationRequest locationRequest;

	// private SupportMapFragment mapFragment;
	// private GoogleMap map;
	// private Marker currentLocationMarker, qiblaMarker;
	// private Polyline line;

	private float[] aValues = new float[3];
	private float[] mValues = new float[3];
	private CompassView compassView;
	private SensorManager sensorManager;
	private int rotation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getSupportActionBar().setDisplayShowTitleEnabled(false);

		compassView = (CompassView) findViewById(R.id.compassView);
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		// mapFragment = (SupportMapFragment) getSupportFragmentManager()
		// .findFragmentById(R.id.maps);
		// map = mapFragment.getMap();
		locationClient = new LocationClient(this, this, this);

		// creating request object with some of parameters set
		locationRequest = LocationRequest.create()
				.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
				.setInterval(UPDATE_INTERVAL)
				.setFastestInterval(FASTEST_INTERVAL).setNumUpdates(1);

		tfUtil = new BijoyFontUtil();
		banglaTypeface = Typeface.createFromAsset(getAssets(),
				"font/suttony.ttf");
		txtLabelCaption = (TextView) findViewById(R.id.txtLabelCaption);
		txtLabelCaption2 = (TextView) findViewById(R.id.txtLabelCaption2);
		txtLabelCaption3 = (TextView) findViewById(R.id.txtLabelCaption3);
		txtFajr = (TextView) findViewById(R.id.txtFajr);
		txtFajrTime = (TextView) findViewById(R.id.txtFajrTime);
		txtDuhr = (TextView) findViewById(R.id.txtDuhr);
		txtDuhrTime = (TextView) findViewById(R.id.txtDuhrTime);
		txtAsr = (TextView) findViewById(R.id.txtAsr);
		txtAsrTime = (TextView) findViewById(R.id.txtAsrTime);
		txtMaghrib = (TextView) findViewById(R.id.txtMaghrib);
		txtMaghribTime = (TextView) findViewById(R.id.txtMaghribTime);
		txtIsha = (TextView) findViewById(R.id.txtIsha);
		txtIshaTime = (TextView) findViewById(R.id.txtIshaTime);
		txtSunrise = (TextView) findViewById(R.id.txtSunrise);
		txtSunriseTime = (TextView) findViewById(R.id.txtSunriseTime);
		txtSunset = (TextView) findViewById(R.id.txtSunset);
		txtSunsetTime = (TextView) findViewById(R.id.txtSunsetTime);
		txtAmPm1 = (TextView) findViewById(R.id.txtAmPm1);
		txtAmPm2 = (TextView) findViewById(R.id.txtAmPm2);
		txtAmPm3 = (TextView) findViewById(R.id.txtAmPm3);
		txtAmPm4 = (TextView) findViewById(R.id.txtAmPm4);
		txtAmPm5 = (TextView) findViewById(R.id.txtAmPm5);
		txtAmPm6 = (TextView) findViewById(R.id.txtAmPm6);
		txtAmPm7 = (TextView) findViewById(R.id.txtAmPm7);
		setUpUI();
		// if (map != null) {
		// map.setOnMapLongClickListener(new OnMapLongClickListener() {
		//
		// @Override
		// public void onMapLongClick(LatLng point) {
		// if (currentLocation == null) {
		// // currentLocation=Location.
		// currentLocation = new Location("fused");
		// currentLocation.setLatitude(point.latitude);
		// currentLocation.setLongitude(point.longitude);
		// updateLocation(currentLocation);
		// }
		// }
		// });
		// }

	}

	private void setUpUI() {
		String windoSrvc = Context.WINDOW_SERVICE;
		WindowManager wm = ((WindowManager) getSystemService(windoSrvc));
		Display display = wm.getDefaultDisplay();
		rotation = display.getRotation();

		updateOrientation(new float[] { 0, 0, 0 });
		// setup labels
		txtLabelCaption.setTypeface(banglaTypeface);
		String caption = "নামাযের সময়সূচী";
		String captionEncoded = tfUtil.convertUnicode2BijoyString(caption);
		txtLabelCaption.setText(captionEncoded);

		txtLabelCaption2.setTypeface(banglaTypeface);
		String caption2 = "নামাযের কিবলা";
		String captionEncoded2 = tfUtil.convertUnicode2BijoyString(caption2);
		txtLabelCaption2.setText(captionEncoded2);

		txtLabelCaption3.setTypeface(banglaTypeface);
		String caption3 = "(শুধুমাত্র বাংলাদেশের জন্য প্রযোজ্য)";
		String captionEncoded3 = tfUtil.convertUnicode2BijoyString(caption3);
		txtLabelCaption3.setText(captionEncoded3);

		txtFajr.setTypeface(banglaTypeface);
		String label = "ফজর: ";
		String labelEncoded = tfUtil.convertUnicode2BijoyString(label);
		txtFajr.setText(labelEncoded);

		txtDuhr.setTypeface(banglaTypeface);
		label = "যোহর: ";
		labelEncoded = tfUtil.convertUnicode2BijoyString(label);
		txtDuhr.setText(labelEncoded);

		txtAsr.setTypeface(banglaTypeface);
		label = "আসর: ";
		labelEncoded = tfUtil.convertUnicode2BijoyString(label);
		txtAsr.setText(labelEncoded);

		txtMaghrib.setTypeface(banglaTypeface);
		label = "মাগরিব: ";
		labelEncoded = tfUtil.convertUnicode2BijoyString(label);
		txtMaghrib.setText(labelEncoded);

		txtIsha.setTypeface(banglaTypeface);
		label = "এশা: ";
		labelEncoded = tfUtil.convertUnicode2BijoyString(label);
		txtIsha.setText(labelEncoded);

		txtSunrise.setTypeface(banglaTypeface);
		label = "সূর্যোদয়: ";
		labelEncoded = tfUtil.convertUnicode2BijoyString(label);
		txtSunrise.setText(labelEncoded);

		txtSunset.setTypeface(banglaTypeface);
		label = "সূর্যাস্ত: ";
		Log.e("TEST", label);
		labelEncoded = tfUtil.convertUnicode2BijoyString(label);
		Log.e("TEST", labelEncoded);
		txtSunset.setText(labelEncoded);

		txtFajrTime.setTypeface(banglaTypeface);
		txtDuhrTime.setTypeface(banglaTypeface);
		txtAsrTime.setTypeface(banglaTypeface);
		txtMaghribTime.setTypeface(banglaTypeface);
		txtIshaTime.setTypeface(banglaTypeface);
		txtSunriseTime.setTypeface(banglaTypeface);
		txtSunsetTime.setTypeface(banglaTypeface);

	}

	@Override
	protected void onStart() {
		super.onStart();
		if (isPlayServicesConnected()) {
			locationClient.connect();
		}
	}

	private void updateOrientation(float[] values) {
		if (compassView != null) {
			compassView.setBearing(values[0]);
			compassView.setPitch(values[1]);
			compassView.setRoll(-values[2]);
			compassView.invalidate();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		// register sensor
		Sensor accelerometer = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		Sensor magField = sensorManager
				.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

		sensorManager.registerListener(sensorEventListener, accelerometer,
				SensorManager.SENSOR_DELAY_FASTEST);
		sensorManager.registerListener(sensorEventListener, magField,
				SensorManager.SENSOR_DELAY_FASTEST);
	}

	@Override
	protected void onPause() {
		sensorManager.unregisterListener(sensorEventListener);
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (locationClient.isConnected()) {
			locationClient.removeLocationUpdates(this);
		}
		locationClient.disconnect();
	}

	public void startUpdates() {
		if (isPlayServicesConnected()) {
			locationClient.requestLocationUpdates(locationRequest, this);
		}
	}

	@Override
	public void onLocationChanged(Location loc) {
		currentLocation = loc;
		updateLocation(currentLocation);
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		Toast.makeText(getApplicationContext(),
				"Connection failed to Location Services", Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		currentLocation = locationClient.getLastLocation();
		if (currentLocation != null) {
			updateLocation(currentLocation);
		} else {
			currentLocation = new Location("gps");
			currentLocation.setLatitude(23.7000);
			currentLocation.setLongitude(90.3833);
			updateLocation(currentLocation);
		}
		startUpdates();
	}

	private void updateLocation(Location location) {
		// update data
		currentLocation = location;
		if (currentLocation != null) {
			double latitude = currentLocation.getLatitude();
			double longitude = currentLocation.getLongitude();
			double timezone = Calendar.getInstance().getTimeZone()
					.getRawOffset()
					/ (1000 * 60 * 60);
			// .getOffset(Calendar.getInstance().getTimeInMillis()))

			// Toast.makeText(getApplicationContext(), "Timezone: " + timezone,
			// Toast.LENGTH_LONG).show();
			PrayTime prayers = new PrayTime();

			prayers.setTimeFormat(prayers.Time12);
			prayers.setCalcMethod(prayers.Makkah);
			prayers.setAsrJuristic(prayers.Shafii);
			prayers.setAdjustHighLats(prayers.AngleBased);
			int[] offsets = { 3, -1, 0, 0, 0, 0, -3 }; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
			prayers.tune(offsets);

			Date now = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(now);

			ArrayList<String> prayerTimes = prayers.getPrayerTimes(cal,
					latitude, longitude, timezone);
			ArrayList<String> prayerNames = prayers.getTimeNames();

			String pTime = prayerTimes.get(0);
			String pTimeEncoded = tfUtil.convertUnicode2BijoyString(pTime);
			if (pTimeEncoded.contains("am")) {
				pTimeEncoded = pTimeEncoded.substring(0,
						pTimeEncoded.indexOf("am") - 1);
				txtAmPm1.setText(" am");
			} else if (pTimeEncoded.contains("pm")) {
				pTimeEncoded = pTimeEncoded.substring(0,
						pTimeEncoded.indexOf("pm") - 1);
				txtAmPm1.setText(" pm");
			}
			txtFajrTime.setText(pTimeEncoded);

			pTime = prayerTimes.get(2);
			pTimeEncoded = tfUtil.convertUnicode2BijoyString(pTime);
			if (pTimeEncoded.contains("am")) {
				pTimeEncoded = pTimeEncoded.substring(0,
						pTimeEncoded.indexOf("am") - 1);
				txtAmPm2.setText(" am");
			} else if (pTimeEncoded.contains("pm")) {
				pTimeEncoded = pTimeEncoded.substring(0,
						pTimeEncoded.indexOf("pm") - 1);
				txtAmPm2.setText(" pm");
			}
			txtDuhrTime.setText(pTimeEncoded);

			pTime = prayerTimes.get(3);
			pTimeEncoded = tfUtil.convertUnicode2BijoyString(pTime);
			if (pTimeEncoded.contains("am")) {
				pTimeEncoded = pTimeEncoded.substring(0,
						pTimeEncoded.indexOf("am") - 1);
				txtAmPm3.setText(" am");
			} else if (pTimeEncoded.contains("pm")) {
				pTimeEncoded = pTimeEncoded.substring(0,
						pTimeEncoded.indexOf("pm") - 1);
				txtAmPm3.setText(" pm");
			}
			txtAsrTime.setText(pTimeEncoded);

			pTime = prayerTimes.get(5);
			pTimeEncoded = tfUtil.convertUnicode2BijoyString(pTime);
			if (pTimeEncoded.contains("am")) {
				pTimeEncoded = pTimeEncoded.substring(0,
						pTimeEncoded.indexOf("am") - 1);
				txtAmPm4.setText(" am");
			} else if (pTimeEncoded.contains("pm")) {

				pTimeEncoded = pTimeEncoded.substring(0,
						pTimeEncoded.indexOf("pm") - 1);
				txtAmPm4.setText(" pm");
			}
			txtMaghribTime.setText(pTimeEncoded);

			pTime = prayerTimes.get(6);
			pTimeEncoded = tfUtil.convertUnicode2BijoyString(pTime);
			if (pTimeEncoded.contains("am")) {
				pTimeEncoded = pTimeEncoded.substring(0,
						pTimeEncoded.indexOf("am") - 1);
				txtAmPm5.setText(" am");
			} else if (pTimeEncoded.contains("pm")) {
				pTimeEncoded = pTimeEncoded.substring(0,
						pTimeEncoded.indexOf("pm") - 1);
				txtAmPm5.setText(" pm");
			}
			txtIshaTime.setText(pTimeEncoded);

			pTime = prayerTimes.get(1);
			pTimeEncoded = tfUtil.convertUnicode2BijoyString(pTime);
			if (pTimeEncoded.contains("am")) {
				pTimeEncoded = pTimeEncoded.substring(0,
						pTimeEncoded.indexOf("am") - 1);
				txtAmPm6.setText(" am");
			} else if (pTimeEncoded.contains("pm")) {
				pTimeEncoded = pTimeEncoded.substring(0,
						pTimeEncoded.indexOf("pm") - 1);
				txtAmPm6.setText(" pm");
			}
			txtSunriseTime.setText(pTimeEncoded);

			pTime = prayerTimes.get(4);
			pTimeEncoded = tfUtil.convertUnicode2BijoyString(pTime);
			if (pTimeEncoded.contains("am")) {
				pTimeEncoded = pTimeEncoded.substring(0,
						pTimeEncoded.indexOf("am") - 1);
				txtAmPm7.setText(" am");
			} else if (pTimeEncoded.contains("pm")) {
				pTimeEncoded = pTimeEncoded.substring(0,
						pTimeEncoded.indexOf("pm") - 1);
				txtAmPm7.setText(" pm");
			}
			txtSunsetTime.setText(pTimeEncoded);

			// map.getUiSettings().setCompassEnabled(true);
			// LatLng coord = new LatLng(currentLocation.getLatitude(),
			// currentLocation.getLongitude());
			// // for changing the center of the map
			// map.moveCamera(CameraUpdateFactory.newLatLng(coord));
			// MarkerOptions markerOptions = new MarkerOptions()
			// .position(coord)
			// .title("My Location")
			// .icon(BitmapDescriptorFactory
			// .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
			// if (currentLocationMarker != null) {
			// currentLocationMarker.remove();
			// }
			// currentLocationMarker = map.addMarker(markerOptions);
			//
			// LatLng meccaCoord = new LatLng(21.42161810, 39.82479030);
			// // for changing the center of the map
			// MarkerOptions markerOptionsQibla = new MarkerOptions()
			// .position(meccaCoord)
			// .title("Makkah")
			// .icon(BitmapDescriptorFactory
			// .defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
			// if (qiblaMarker != null) {
			// qiblaMarker.remove();
			// }
			// qiblaMarker = map.addMarker(markerOptionsQibla);
			//
			// PolylineOptions lineOps = new PolylineOptions()
			// .add(coord, meccaCoord).width(5).color(Color.RED);
			// if (line != null) {
			// line.remove();
			// }
			// line = map.addPolyline(lineOps);
		} else {

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
			// ConnectionResult.SERVICE_MISSING
			// ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED
			// ConnectionResult.SERVICE_DISABLED
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode,
					this, 1);
			dialog.show();
			// finish();
		} else {
			Toast.makeText(
					getApplicationContext(),
					"Unfortuanately Location Services are not supported in this device.",
					Toast.LENGTH_LONG).show();
			// finish();
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.action_legal) {
			// start LegalActivity
			Intent intent = new Intent(this, LegalActivity.class);
			startActivity(intent);
			return true;
		} else if (item.getItemId() == R.id.action_calendar) {
			// start CalendarActivity
			Intent intent = new Intent(this, CalendarActivity.class);
			startActivity(intent);
			return true;
		}
		// else if (item.getItemId() == R.id.action_finder) {
		// // start MosqueFinderActivity
		// Intent intent = new Intent(this, MosqueFinderActivity.class);
		// if (currentLocation != null) {
		// intent.putExtra("lat", currentLocation.getLatitude());
		// intent.putExtra("lng", currentLocation.getLongitude());
		// }
		// startActivity(intent);
		// return true;
		// }
		return super.onOptionsItemSelected(item);

	}

	private float[] calculateOrientation() {
		float[] values = new float[3];
		float[] inR = new float[9];
		float[] outR = new float[9];

		// Determine the rotation matrix
		SensorManager.getRotationMatrix(inR, null, aValues, mValues);

		// Remap the coordinates based on the natural device orientation.
		int x_axis = SensorManager.AXIS_X;
		int y_axis = SensorManager.AXIS_Y;

		switch (rotation) {
		case (Surface.ROTATION_90):
			x_axis = SensorManager.AXIS_Y;
			y_axis = SensorManager.AXIS_MINUS_X;
			break;
		case (Surface.ROTATION_180):
			y_axis = SensorManager.AXIS_MINUS_Y;
			break;
		case (Surface.ROTATION_270):
			x_axis = SensorManager.AXIS_MINUS_Y;
			y_axis = SensorManager.AXIS_X;
			break;
		default:
			break;
		}
		SensorManager.remapCoordinateSystem(inR, x_axis, y_axis, outR);

		// Obtain the current, corrected orientation.
		SensorManager.getOrientation(outR, values);

		// Convert from Radians to Degrees.
		values[0] = (float) Math.toDegrees(values[0]);
		values[1] = (float) Math.toDegrees(values[1]);
		values[2] = (float) Math.toDegrees(values[2]);

		return values;
	}

	private final SensorEventListener sensorEventListener = new SensorEventListener() {

		public void onSensorChanged(SensorEvent event) {
			if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
				aValues = event.values;
			if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
				mValues = event.values;

			updateOrientation(calculateOrientation());
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};
}
