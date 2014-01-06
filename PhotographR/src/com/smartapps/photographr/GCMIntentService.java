package com.smartapps.photographr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.smartapps.util.Constants;
import com.smartapps.util.Installation;

public class GCMIntentService extends GCMBaseIntentService {

	public GCMIntentService() {
		super(Constants.GCM_SENDER_ID);
	}

	@Override
	protected void onError(Context context, String error) {
		Log.e(getClass().getSimpleName(), "Error Occurred: Error: " + error);
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		Log.e("GCM MESSAGE",
				"-----------------Message Received: Updates are available--------------------");
		String title = intent.getExtras().getString("title");
		String message = intent.getExtras().getString("message");
		generateNotification(context, title, message);
	}

	private static void generateNotification(Context context, String title,
			String message) {
		int icon = R.drawable.ic_launcher;
		long when = System.currentTimeMillis();
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(icon, message, when);

		Intent notificationIntent = new Intent(context, HomeActivity.class);
		// set intent so it does not start a new activity
		notificationIntent.putExtra("from", "newdata");
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent intent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);
		notification.setLatestEventInfo(context, title, message, intent);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		// Play default notification sound
		notification.defaults |= Notification.DEFAULT_SOUND;

		// Vibrate if vibrate is enabled
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notificationManager.notify(1808, notification);

	}

	@Override
	protected void onRegistered(Context context, String regId) {
		Log.e(getClass().getSimpleName(), "Registered: RegId: " + regId);
		handleRegistration(context, regId);
	}

	@Override
	protected void onUnregistered(Context context, String regId) {
		Log.e(getClass().getSimpleName(), "Unregistered: RegId: " + regId);
		handleUnregistration(context, regId);
	}

	private void handleRegistration(Context context, String regId) {
		new RegistrationThread(Installation.id(context), regId).start();
	}

	public class RegistrationThread extends Thread {
		private String deviceId, regId;

		public RegistrationThread(String deviceId, String regId) {
			this.deviceId = deviceId;
			this.regId = regId;
		}

		public void run() {
			Log.e(getClass().getSimpleName(), "Registered: RegId: " + regId);
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost postReq = new HttpPost(Constants.REGISTRATION_REQUEST);
			try {
				// Add your data
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						2);
				nameValuePairs.add(new BasicNameValuePair("deviceId",
						this.deviceId));
				nameValuePairs.add(new BasicNameValuePair("regId", this.regId));
				postReq.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				HttpResponse response = client.execute(postReq);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity entity = response.getEntity();
					String respStr = EntityUtils.toString(entity);
					Log.e(getClass().getSimpleName(), respStr);
					JSONObject respJson = new JSONObject(respStr);
					String successValue = respJson.getString("success");
					if (successValue.equalsIgnoreCase("1")) {
						registrationHandler.sendEmptyMessage(Constants.SUCCESS);
					} else {
						registrationHandler.sendEmptyMessage(Constants.FAILURE);
					}
					Log.e(getClass().getSimpleName(), respStr);
				} else {
					Log.e(getClass().getSimpleName(), "FAILURE");
					registrationHandler.sendEmptyMessage(Constants.FAILURE);
				}

			} catch (ClientProtocolException e) {
				Log.e(getClass().getSimpleName(), e.getMessage());
				registrationHandler.sendEmptyMessage(Constants.FAILURE);
			} catch (IOException e) {
				Log.e(getClass().getSimpleName(), e.getMessage());
				registrationHandler.sendEmptyMessage(Constants.FAILURE);
			} catch (JSONException e) {
				Log.e(getClass().getSimpleName(), e.getMessage());
				registrationHandler.sendEmptyMessage(Constants.FAILURE);
			}

		}
	}

	public Handler registrationHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Constants.SUCCESS:
				// broadcast to activity
				Intent iSuccess = new Intent(
						Constants.REGISTRATION_SUCCESS_INTENT);
				sendBroadcast(iSuccess);
				LocalBroadcastManager.getInstance(GCMIntentService.this)
						.sendBroadcast(iSuccess);
				break;
			case Constants.FAILURE:
				// broadcast to activity
				Intent iFailed = new Intent(
						Constants.REGISTRATION_FAILURE_INTENT);
				LocalBroadcastManager.getInstance(GCMIntentService.this)
						.sendBroadcast(iFailed);
				break;
			default:
				break;
			}
		}
	};

	private void handleUnregistration(Context context, String regId) {
		new UnregistrationThread(Installation.id(context), regId).start();
	}

	public class UnregistrationThread extends Thread {
		private String deviceId, regId;

		public UnregistrationThread(String deviceId, String regId) {
			this.deviceId = deviceId;
			this.regId = regId;
		}

		public void run() {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost postReq = new HttpPost(Constants.UNREGISTRATION_REQUEST);
			try {
				// Add your data
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						2);
				nameValuePairs.add(new BasicNameValuePair("deviceId",
						this.deviceId));
				nameValuePairs.add(new BasicNameValuePair("regId", this.regId));
				postReq.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				HttpResponse response = client.execute(postReq);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity entity = response.getEntity();
					String respStr = EntityUtils.toString(entity);
					JSONObject respJson = new JSONObject(respStr);
					String successValue = respJson.getString("success");
					if (successValue.equalsIgnoreCase("1")) {
						unregistrationHandler
								.sendEmptyMessage(Constants.SUCCESS);
					} else {
						unregistrationHandler
								.sendEmptyMessage(Constants.FAILURE);
					}
				} else {
					unregistrationHandler.sendEmptyMessage(Constants.FAILURE);
				}
			} catch (ClientProtocolException e) {
				unregistrationHandler.sendEmptyMessage(Constants.FAILURE);
			} catch (IOException e) {
				unregistrationHandler.sendEmptyMessage(Constants.FAILURE);
			} catch (JSONException e) {
				unregistrationHandler.sendEmptyMessage(Constants.FAILURE);
			}

		}
	}

	public Handler unregistrationHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Constants.SUCCESS:
				// broadcast to activity
				Intent iSuccess = new Intent(
						Constants.UNREGISTRATION_SUCCESS_INTENT);
				LocalBroadcastManager.getInstance(GCMIntentService.this)
						.sendBroadcast(iSuccess);
				break;
			case Constants.FAILURE:
				// broadcast to activity
				Intent iFailed = new Intent(
						Constants.UNREGISTRATION_FAILURE_INTENT);
				LocalBroadcastManager.getInstance(GCMIntentService.this)
						.sendBroadcast(iFailed);
				break;
			default:
				break;
			}
		}
	};
}
