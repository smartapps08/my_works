package bd.org.basis.gcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {

	public GCMIntentService() {
		super(GCMUtil.SENDER_ID);
	}

	@Override
	protected void onError(Context context, String errorMsg) {
		Log.e(getClass().getSimpleName(), errorMsg);
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		Log.e(getClass().getSimpleName(),
				"-----------GCM Message Received-----------");
		String type = intent.getStringExtra("type");
		String title = intent.getStringExtra("title");
		String msg = intent.getStringExtra("msg");
		String url = intent.getStringExtra("url");

		NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		Notification notification = new Notification(R.drawable.ic_launcher,
				title, System.currentTimeMillis());

		Intent i = new Intent(context, NotificationResultActivity.class);
		PendingIntent pi = PendingIntent.getActivity(context, 0, intent,
				Notification.FLAG_AUTO_CANCEL);

		notification.setLatestEventInfo(context, title, msg + "--" + url, pi);
		manager.notify(0, notification);
		PowerManager pm=(PowerManager)getSystemService(Context.POWER_SERVICE);
		WakeLock wl=pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "tag");
		wl.acquire();
	}

	@Override
	protected void onRegistered(Context context, String regId) {
		Log.e(getClass().getSimpleName(), "-------------Registered: RegId: "
				+ regId);
	}

	@Override
	protected void onUnregistered(Context context, String regId) {
		Log.e(getClass().getSimpleName(), "--------------Unregistered: RegId: "
				+ regId);
	}

}
