package com.smartapps.photographr;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.google.android.gcm.GCMRegistrar;
import com.smartapps.util.Constants;

public class MainActivity extends SherlockFragmentActivity {
	private SharedPreferences preferences;
	private RegistrationDoneReceiver receiver;
	private IntentFilter filter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		preferences = getSharedPreferences(Constants.PREF_FILE_NAME,
				MODE_PRIVATE);
		boolean isReg = preferences.getBoolean(Constants.PREF_REG_FIELD, false);

		if (isReg) {
			Constants.isRegistered = true;
			// got to Home
			Intent intent = new Intent(MainActivity.this, HomeActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
		}

		receiver = new RegistrationDoneReceiver();
		filter = new IntentFilter();
		filter.addAction(Constants.REGISTRATION_SUCCESS_INTENT);
		filter.addAction(Constants.UNREGISTRATION_SUCCESS_INTENT);
		filter.addAction(Constants.REGISTRATION_FAILURE_INTENT);
		filter.addAction(Constants.UNREGISTRATION_FAILURE_INTENT);

	}

	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(receiver, filter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiver);
	}

	public void registerToGCM(View v) {
		ConnectivityManager cManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cManager.getActiveNetworkInfo();
		if (info != null) {
			if (info.isAvailable() && info.isConnected()) {
				GCMRegistrar.checkDevice(MainActivity.this);
				GCMRegistrar.checkManifest(MainActivity.this);
				final String regId = GCMRegistrar
						.getRegistrationId(MainActivity.this);
				if (regId.equals("")) {
					GCMRegistrar.register(MainActivity.this,
							Constants.GCM_SENDER_ID);
				} else {
					// Log.v("MainActivity", "Already registered");
				}
			} else {
				showErrorAlert();
			}
		} else {
			showErrorAlert();
		}
	}

	public void unregisterToGCM(View v) {
		GCMRegistrar.unregister(MainActivity.this);
	}

	private void showErrorAlert() {
		AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
		alert.setTitle("Error");
		alert.setMessage("Internet is not available. Do you want to enable Internet?");
		alert.setPositiveButton("Yes", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
			}
		});

		alert.setNegativeButton("Cancel", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		alert.show();
	}

	private class RegistrationDoneReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction()
					.equals(Constants.REGISTRATION_SUCCESS_INTENT)) {
				// write to preferences
				Toast.makeText(getApplicationContext(),
						"Congratulations! You've just made it.",
						Toast.LENGTH_LONG).show();
				SharedPreferences.Editor editor = preferences.edit();
				editor.putBoolean(Constants.PREF_REG_FIELD, true);
				editor.commit();

				Intent in = new Intent(MainActivity.this, HomeActivity.class);
				startActivity(in);
				finish();

			} else if (intent.getAction().equals(
					Constants.REGISTRATION_FAILURE_INTENT)) {
				Toast.makeText(getApplicationContext(),
						"Something went wrong! Please try again.",
						Toast.LENGTH_LONG).show();

			} else if (intent.getAction().equals(
					Constants.UNREGISTRATION_SUCCESS_INTENT)) {
				SharedPreferences.Editor editor = preferences.edit();
				editor.putBoolean(Constants.PREF_REG_FIELD, false);
				editor.commit();
				Toast.makeText(getApplicationContext(), "Unregistration done.",
						Toast.LENGTH_LONG).show();
			} else if (intent.getAction().equals(
					Constants.UNREGISTRATION_FAILURE_INTENT)) {
				Toast.makeText(getApplicationContext(),
						"Unregistration failed.", Toast.LENGTH_LONG).show();
			}

		}
	}

}
