package com.example.telephonyapidemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private TelephonyManager manager;
	private TextView txtOutput;

	private PhoneStateListener listener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		txtOutput = (TextView) findViewById(R.id.txtOutput);
		
		

		listener = new PhoneStateListener() {
			@Override
			public void onCallStateChanged(int state, String incomingNumber) {
				switch (state) {
				case TelephonyManager.CALL_STATE_IDLE:
					Toast.makeText(getApplicationContext(), "IDLE: ",
							Toast.LENGTH_LONG).show();
					txtOutput.append("IDLE: \n");
					break;
				case TelephonyManager.CALL_STATE_RINGING:
					Toast.makeText(getApplicationContext(),
							"RINGING: " + incomingNumber, Toast.LENGTH_LONG)
							.show();
					txtOutput.append("RINGING: " + incomingNumber + "\n");
					break;
				case TelephonyManager.CALL_STATE_OFFHOOK:
					Toast.makeText(getApplicationContext(), "OFFHOOK: ",
							Toast.LENGTH_LONG).show();
					txtOutput.append("OFFHOOK: \n");
					break;
				default:
					break;
				}
			}
		};
		
		manager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
