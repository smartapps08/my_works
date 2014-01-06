/**
 * 
 */
package com.smartapps.watchdog.services;

import java.io.IOException;

import com.smartapps.watchdog.R;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.IBinder;

/**
 * @author Ahsan
 * 
 */
public class RingAlarmService extends Service {
	MediaPlayer player;

	@Override
	public IBinder onBind(Intent intent) {
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
		super.onStart(intent, startId);
		player = MediaPlayer.create(RingAlarmService.this, R.raw.alarm);

		player.setOnCompletionListener(new OnCompletionListener() {

			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				stopSelf();
			}
		});
		player.start();

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		player.release();
	}
}
