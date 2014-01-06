package com.smartapps.watchdog.services;

import com.smartapps.watchdog.BatteryInfo;

import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;

public class BatteryService extends Service {

	String number;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		number = intent.getStringExtra("number");

		batteryLevel();

		return START_STICKY;

	}

	private void sendSMS(String phoneNumber, String message) {

		SmsManager sms = SmsManager.getDefault();

		Log.d("phoneNumber", phoneNumber);

		sms.sendTextMessage(phoneNumber, null, message, null, null);

	}

	public void batteryLevel() {
		BroadcastReceiver batReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				context.unregisterReceiver(this);
				BatteryInfo.health = intent.getIntExtra(
						BatteryManager.EXTRA_HEALTH, 0);
				BatteryInfo.icon_small = intent.getIntExtra(
						BatteryManager.EXTRA_ICON_SMALL, 0);
				BatteryInfo.level = intent.getIntExtra(
						BatteryManager.EXTRA_LEVEL, 0);
				BatteryInfo.plugged = intent.getIntExtra(
						BatteryManager.EXTRA_PLUGGED, 0);
				BatteryInfo.present = intent.getExtras().getBoolean(
						BatteryManager.EXTRA_PRESENT);
				BatteryInfo.scale = intent.getIntExtra(
						BatteryManager.EXTRA_SCALE, 0);
				BatteryInfo.status = intent.getIntExtra(
						BatteryManager.EXTRA_STATUS, 0);
				BatteryInfo.technology = intent.getExtras().getString(
						BatteryManager.EXTRA_TECHNOLOGY);
				BatteryInfo.temperature = intent.getIntExtra(
						BatteryManager.EXTRA_TEMPERATURE, 0);
				BatteryInfo.voltage = intent.getIntExtra(
						BatteryManager.EXTRA_VOLTAGE, 0);
				sendSMS(number, BatteryInfo.getMessage());
				stopSelf();
			}
		};

		IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		registerReceiver(batReceiver, filter);
	}

}