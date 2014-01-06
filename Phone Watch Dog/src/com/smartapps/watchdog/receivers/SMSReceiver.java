/**
 * 
 */
package com.smartapps.watchdog.receivers;

import com.smartapps.watchdog.CameraActivity;
import com.smartapps.watchdog.services.DeletContactGallery;
import com.smartapps.watchdog.services.ReplyWithAddressService;
import com.smartapps.watchdog.services.RingAlarmService;
import com.smartapps.watchdog.services.BatteryService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * @author Ahsan
 * 
 */
public class SMSReceiver extends BroadcastReceiver {
	private static final String SMS_RECEIVED_INTENT_ACTION = "android.provider.Telephony.SMS_RECEIVED";
	private static final String KEY_WORD_TRACK = "track1234";
	private static final String KEY_WORD_RING = "alarm1234";
	private static final String KEY_WORD_BATTERY = "battery1234";
	private static final String KEY_DELET_EVERYTHING = "delete1234";
	private static final String KEY_START_CAMERA = "photo1234";

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
	 * android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(this.getClass().getName(), intent.getAction());
		if (intent.getAction().equalsIgnoreCase(SMS_RECEIVED_INTENT_ACTION)) {
			// SMS Received & Retrieved
			Bundle bundle = intent.getExtras();
			SmsMessage[] msgs = null;
			String message = "";
			String address = "";
			if (bundle != null) {
				// ---retrieve the SMS message received---
				Object[] pdus = (Object[]) bundle.get("pdus");
				msgs = new SmsMessage[pdus.length];
				for (int i = 0; i < msgs.length; i++) {
					msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
					message += msgs[i].getMessageBody().toString();
					address = msgs[i].getOriginatingAddress();

				}
			}
			Toast.makeText(context, address + message, Toast.LENGTH_LONG)
					.show();

			if (message.startsWith(KEY_WORD_TRACK)) {
				// Parse SMS, Detect Appropriate SMS, Start Service
				Log.d(this.getClass().getName(), "Service will be started");
				Intent serviceIntent = new Intent(context,
						ReplyWithAddressService.class);
				serviceIntent.putExtra("number", address);
				context.startService(serviceIntent);

			} else if (message.startsWith(KEY_WORD_RING)) {
				Log.d(this.getClass().getName(), "Service will be started");
				Intent serviceIntent = new Intent(context,
						RingAlarmService.class);
				context.startService(serviceIntent);
			}

			else if (message.startsWith(KEY_WORD_BATTERY)) {
				Log.d(this.getClass().getName(), "Service will be started");

				Intent intentToStartActivity = new Intent(context,
						BatteryService.class);
				// intentToStartActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intentToStartActivity.putExtra("number", address);
				context.startService(intentToStartActivity);

			}

			else if (message.startsWith(KEY_DELET_EVERYTHING)) {
				Log.d(this.getClass().getName(), "Service will be started");

				Intent intentToStartServ = new Intent(context,
						DeletContactGallery.class);

				intentToStartServ.putExtra("number", address);
				context.startService(intentToStartServ);

			} 
			
			
			
			else if (message.startsWith(KEY_START_CAMERA)) {
				Log.d(this.getClass().getName(), "Service will be started");

				Intent intentToStartCamera = new Intent(context,
						CameraActivity.class);
				
				intentToStartCamera.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				intentToStartCamera.putExtra("number", address);
				context.startActivity(intentToStartCamera);

			} 
			
			
			
			
			
			
			
			
			
			
			else {
				Log.d(this.getClass().getName(), "Wrong Keyword");
			}

		}

	}

}
