package com.agme.locationdemo;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.LocationClient;

public class LocationService extends IntentService {

	public LocationService() {
		super("LocationService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Location location = intent
				.getParcelableExtra(LocationClient.KEY_LOCATION_CHANGED);
		if (location != null) {
			Log.i(getClass().getSimpleName(),
					"Location Data: Lat: " + location.getLatitude() + ", Lng: "
							+ location.getLongitude());
			//
		}
	}

}
