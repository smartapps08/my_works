package com.agme.geofenceonmap;

import java.util.List;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;

public class ReceiveEventsIntentService extends IntentService {

	public ReceiveEventsIntentService() {
		super("ReceiveEventsIntentService");

	}

	@Override
	protected void onHandleIntent(Intent intent) {
		if (!LocationClient.hasError(intent)) {
			// get type of transition (entry/exit)
			int transition = LocationClient.getGeofenceTransition(intent);
			if (transition == Geofence.GEOFENCE_TRANSITION_ENTER
					|| transition == Geofence.GEOFENCE_TRANSITION_EXIT) {
				List<Geofence> geofences = LocationClient
						.getTriggeringGeofences(intent);
				String[] geofenceIds = new String[geofences.size()];
				for (int i = 0; i < geofences.size(); i++) {
					geofenceIds[i] = geofences.get(i).getRequestId();
				}
				String ids = TextUtils.join("", geofenceIds);
				String transitionType = "";
				switch (transition) {
				case Geofence.GEOFENCE_TRANSITION_ENTER:
					transitionType = "Entered the Geofence";
					break;
				case Geofence.GEOFENCE_TRANSITION_EXIT:
					transitionType = "Exited from the Geofence";
					break;
				default:
					break;
				}
				sendNotification(transitionType, ids);
			}
		} else {
			// Handle all errors
		}
	}

	// Post a notification to the status bar
	private void sendNotification(String transitionType, String ids) {
		// Create an explicit content Intent that starts the main Activity
		Intent notificationIntent = new Intent(getApplicationContext(),
				MainActivity.class);
		// Construct a task stack
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the main Activity to the task stack as the parent
		stackBuilder.addParentStack(MainActivity.class);
		// Push the content Intent onto the stack
		stackBuilder.addNextIntent(notificationIntent);
		// Get a PendingIntent containing the entire back stack
		PendingIntent notificationPendingIntent = stackBuilder
				.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				this);

		// Set the notification contents
		builder.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle(transitionType + ids)
				.setContentText("Click Notification to return to the app")
				.setContentIntent(notificationPendingIntent);

		// Get an instance of the Notification manager
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		// Issue the notification
		mNotificationManager.notify(0, builder.build());

	}

}
