package com.agme.splashscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends Activity {

	private static final int DURATION = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new SplashThread().start();
	}

	class SplashThread extends Thread {
		public void run() {
			try {
				Thread.sleep(DURATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			handler.sendEmptyMessage(0);
		}
	}
	
//	class GenerateEquationTask extends AsyncTask<Integer, Integer, Void>
//	{
//		@Override
//		protected void onPreExecute() {
//			// TODO Auto-generated method stub
//			super.onPreExecute();
//		}
//		@Override
//		protected Void doInBackground(Integer... params) {
//			
//			return null;
//		}
//		
//		@Override
//		protected void onProgressUpdate(Integer... values) {
//			// TODO Auto-generated method stub
//			super.onProgressUpdate(values);
//		}
//		@Override
//		protected void onPostExecute(Void result) {
//			// TODO Auto-generated method stub
//			super.onPostExecute(result);
//		}
//		
//	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Intent i = new Intent(MainActivity.this, HomeActivity.class);
			startActivity(i);
			finish();
		}
	};

}
