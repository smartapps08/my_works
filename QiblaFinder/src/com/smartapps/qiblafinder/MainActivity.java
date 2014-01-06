package com.smartapps.qiblafinder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {// implements SensorEventListener {

	// private SensorManager sensorManager;
	// private CompassView compassView;

	private TextView txtPrayerTimes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		txtPrayerTimes = (TextView) findViewById(R.id.txtPrayerTimes);

	}

	public void getTime(View v) {
		// Retrive lat, lng using location API
		double latitude = 23.7;
		double longitude = 90.3833;
		double timezone = (Calendar.getInstance().getTimeZone()
				.getOffset(Calendar.getInstance().getTimeInMillis()))
				/ (1000 * 60 * 60);
		PrayTime prayers = new PrayTime();

		prayers.setTimeFormat(prayers.Time12);
		prayers.setCalcMethod(prayers.Makkah);
		prayers.setAsrJuristic(prayers.Shafii);
		prayers.setAdjustHighLats(prayers.AngleBased);
		int[] offsets = { 0, 0, 0, 0, 0, 0, 0 }; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
		prayers.tune(offsets);

		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);

		ArrayList<String> prayerTimes = prayers.getPrayerTimes(cal, latitude,
				longitude, timezone);
		ArrayList<String> prayerNames = prayers.getTimeNames();

		for (int i = 0; i < prayerTimes.size(); i++) {
			txtPrayerTimes.append("\n" + prayerNames.get(i) + " - "
					+ prayerTimes.get(i));
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		// Sensor sensor = sensorManager.getSensorList(Sensor.TYPE_ORIENTATION)
		// .get(0);
		// sensorManager.registerListener(this, sensor,
		// SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// sensorManager.unregisterListener(this);
	}

	// @Override
	// public void onAccuracyChanged(Sensor sensor, int accuracy) {
	//
	// }
	//
	// @Override
	// public void onSensorChanged(SensorEvent event) {
	//
//	 int orientation = (int) event.values[0];
//	 compassView.setDirection(orientation);
	// }

}
