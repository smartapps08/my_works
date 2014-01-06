package bd.org.basis.gcm;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void registerToGCM(View v) {
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		String regId = GCMRegistrar.getRegistrationId(this);
		if (regId == null || regId.equals("")) {
			// we have to register
			GCMRegistrar.register(this, GCMUtil.SENDER_ID);
		} else {
			Toast.makeText(
					getApplicationContext(),
					"You've been already Registered. Unregister to Register again!",
					Toast.LENGTH_LONG).show();
		}
	}

	public void unregisterToGCM(View v) {
		GCMRegistrar.unregister(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}
}
