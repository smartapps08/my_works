package com.agme.geofenceonmap;

import java.util.ArrayList;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationClient.OnAddGeofencesResultListener;
import com.google.android.gms.location.LocationStatusCodes;

public class GeofenceRequester implements OnAddGeofencesResultListener,
		ConnectionCallbacks, OnConnectionFailedListener {

	// reference to calling Activity
	private Activity activity;

	// PendingIntent used to send geofence transitions back to the application
	private PendingIntent geofencePendingIntent;

	// stores current LocationClient instance
	private LocationClient locationClient;

	// a list will be used as LocationClient takes a list to add to to geofences
	private ArrayList<Geofence> currentGeofences;

	private boolean inProgress;

	public GeofenceRequester(Activity activityContext) {
		this.activity = activityContext;
		this.geofencePendingIntent = null;
		this.locationClient = null;
		this.currentGeofences = null;
		this.inProgress = false;
	}

	public void addGeofences(ArrayList<Geofence> geofences) {
		currentGeofences = geofences;
		if (!inProgress) {
			inProgress = true;
			if (locationClient == null) {
				locationClient = new LocationClient(activity, this, this);
			}

			// request a connection: On successful connection onCoonected()
			// callback method will be called
			locationClient.connect();
		}

	}

	@Override
	public void onConnected(Bundle connectionHint) {
		Log.d("Connection", "Connected");
		// get a PendingIntent object that location services issues when a
		// geofence transition occurs
		if (geofencePendingIntent == null) {
			Intent intent = new Intent(activity,
					ReceiveEventsIntentService.class);
			geofencePendingIntent = PendingIntent.getService(activity, 0,
					intent, PendingIntent.FLAG_UPDATE_CURRENT);
		}
		// request to add the current list of geofences withlast parameter as
		// the callback for listening to the result of add request
		locationClient.addGeofences(currentGeofences, geofencePendingIntent,
				this);
	}

	@Override
	public void onDisconnected() {
		Log.d("Connection", "Disconnected");
		inProgress = false;
		locationClient = null;
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		inProgress = false;
		Log.e("Connection", "Connection Failure");
	}

	/**
	 * Handle the result of adding the geofence
	 */

	@Override
	public void onAddGeofencesResult(int statusCode, String[] geofenceRequestIds) {
		if (LocationStatusCodes.SUCCESS == statusCode) {
			Log.d("Success", "Geofence list added successfully");
		} else {
			Log.e("Failure", "Geofence list could not be added");
		}
		inProgress = false;
		locationClient.disconnect();
	}

}
