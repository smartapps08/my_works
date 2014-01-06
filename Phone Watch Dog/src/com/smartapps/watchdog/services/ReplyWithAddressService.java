/**
 * 
 */
package com.smartapps.watchdog.services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;

import com.smartapps.watchdog.helper.HttpRetriever;

/**
 * @author Ahsan
 * 
 */
public class ReplyWithAddressService extends Service {

	public static final String GOOGLE_GEOCODER = "http://maps.googleapis.com/maps/api/geocode/json?latlng=";
	private String msgRecipient;
	private LocationManager locmanager;
	private MyLocationListener listener;
	private static double latitude = -1;
	private static double longitude = -1;
	private String provider;
	private String smsMessageString = "";

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(this.getClass().getName(), "Service Created");
		locmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		listener = new MyLocationListener();
		if (locmanager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			provider = LocationManager.GPS_PROVIDER;
		} else if (locmanager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			provider = LocationManager.NETWORK_PROVIDER;
		}
		locmanager.requestLocationUpdates(provider, 0, 0, listener);

	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.d(this.getClass().getName(), "Service Started");
		// Extract Intent Data
		msgRecipient = intent.getStringExtra("number");
		Log.d(this.getClass().getName(), "Number: " + msgRecipient);
		ConnectivityManager mgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo netInfo = mgr.getActiveNetworkInfo();

		// get location
		if (ReplyWithAddressService.latitude == -1
				|| ReplyWithAddressService.longitude == -1) {
			Location location = locmanager.getLastKnownLocation(provider);

			if (location != null) {
				ReplyWithAddressService.latitude = location.getLatitude();
				ReplyWithAddressService.longitude = location.getLongitude();

				if (netInfo != null) {
					if (netInfo.isConnected()) {
						String address = ReplyWithAddressService
								.getAddressFromGPSData(
										ReplyWithAddressService.latitude,
										ReplyWithAddressService.longitude);
						smsMessageString += "Location: " + address + ".";
					}
				}
				smsMessageString += " Link: http://maps.google.com/maps?q="
						+ ReplyWithAddressService.latitude + "+"
						+ ReplyWithAddressService.longitude;

				Log.d("MESSAGE", smsMessageString);
				// getAddress
				// form smsString
			} else {
				smsMessageString = "Location Data Not Available";
			}
		}

		SmsManager sm = SmsManager.getDefault();
		String number = msgRecipient;
		sm.sendTextMessage(number, null, smsMessageString, null, null);
		// send message
		stopSelf(startId);

	}

	public static String getAddressFromGPSData(double lat, double longi) {
		HttpRetriever agent = new HttpRetriever();
		String request = ReplyWithAddressService.GOOGLE_GEOCODER + lat + ","
				+ longi + "&sensor=true";
		// Log.d("GeoCoder", request);
		String response = agent.retrieve(request);
		String formattedAddress = "";
		if (response != null) {
			Log.d("GeoCoder", response);
			try {
				JSONObject parentObject = new JSONObject(response);
				JSONArray arrayOfAddressResults = parentObject
						.getJSONArray("results");
				JSONObject addressItem = arrayOfAddressResults.getJSONObject(0);
				formattedAddress = addressItem.getString("formatted_address");
			} catch (JSONException e) {

				e.printStackTrace();
			}

		}

		return formattedAddress;
	}

	class MyLocationListener implements LocationListener {
		public void onLocationChanged(Location location) {
			if (location != null) {
				ReplyWithAddressService.latitude = location.getLatitude();
				ReplyWithAddressService.longitude = location.getLongitude();
				Log.d("MyLocationListener", "Location Changed: Lat="
						+ ReplyWithAddressService.latitude + " Long="
						+ ReplyWithAddressService.longitude);
			}
		}

		public void onProviderDisabled(String provider) {

		}

		public void onProviderEnabled(String provider) {

		}

		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		locmanager.removeUpdates(listener);
	}

}
