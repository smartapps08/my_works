package com.smartapps.watchdog.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class WatchReBootReciver extends BroadcastReceiver {
	SharedPreferences pref;
	TelephonyManager telephonyManager;
	PhoneStateListener listener;
	String number1, number2, number3, number4, number5,
			alert = "Sim Has CHanged";
	public static String filenames = "WatchDogData";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		pref = context.getSharedPreferences(filenames, 0);

		final Context testContext = context;
		telephonyManager = (TelephonyManager) testContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		String sSimSerial = telephonyManager.getSimSerialNumber();

		String prevSim = pref.getString("sim", "");

		String number1 = pref.getString("key1", "");
		String number2 = pref.getString("key2", "");
		String number3 = pref.getString("key3", "");
		String number4 = pref.getString("key4", "");
		String number5 = pref.getString("key5", "");
		Toast.makeText(context, number1, Toast.LENGTH_LONG).show();
		Toast.makeText(context, number2, Toast.LENGTH_LONG).show();
		Toast.makeText(context, number3, Toast.LENGTH_LONG).show();
		Toast.makeText(context, number4, Toast.LENGTH_LONG).show();
		Toast.makeText(context, number5, Toast.LENGTH_LONG).show();
		
		Log.d("....Valu of Text...", number1);

		if (sSimSerial.equals(prevSim)) {
			Toast.makeText(testContext.getApplicationContext(), "Same SIM",
					Toast.LENGTH_LONG).show();
			Log.d("MESSAGE", "Same SIM");
		} else {
			Toast.makeText(testContext.getApplicationContext(), "SIM Changed",
					Toast.LENGTH_LONG).show();
			Log.d("MESSAGE", "Different SIM");

			sendSMS(number1, alert);
			sendSMS(number2, alert);
			sendSMS(number3, alert);
			sendSMS(number4, alert);
			sendSMS(number5, alert);

		}

		listener = new PhoneStateListener() {

			public void onServiceStateChanged(ServiceState serviceState) {
				// Code in here executed on Sim State change
				int simState = telephonyManager.getSimState();

				switch (simState) {

				case TelephonyManager.SIM_STATE_ABSENT:
					Toast.makeText(testContext.getApplicationContext(),
							simState, Toast.LENGTH_LONG).show();

					break;

				case (TelephonyManager.SIM_STATE_READY):
					Toast.makeText(testContext.getApplicationContext(),
							simState, Toast.LENGTH_LONG).show();

					break;

				}

			}

		};

		telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

	}

	private void sendSMS(String phoneNumber, String message) {

		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, null, null);
	}

}
