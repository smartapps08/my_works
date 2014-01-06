package bd.org.basis.service;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

	private Timer timer;
	private TimerTask task;
	int counter = 0;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.e(getClass().getSimpleName(), "----------onCreate-------");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e(getClass().getSimpleName(), "----------onStartCommand-------");
		new TimeConsumingThread().start();
		// timer=new Timer();
		// task=new TimerTask() {
		//
		// @Override
		// public void run() {
		// counter++;
		// Log.e(getClass().getSimpleName(), "Value of Counter="+counter);
		//
		// }
		// };
		// timer.schedule(task, 1000, 5000);
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e(getClass().getSimpleName(), "----------onDestroy-------");

	}

	class TimeConsumingThread extends Thread {
		public void run() {
			// time consuming task
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			handler.sendEmptyMessage(0);

		}
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Log.e(getClass().getSimpleName(), "----------Task Done-------");
			// stopSelf();
			sendBroadcast(new Intent("bd.org.basis.service.task_completed"));
			stopSelf();
		}
	};

}
