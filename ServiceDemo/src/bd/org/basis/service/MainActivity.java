package bd.org.basis.service;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ServiceTaskCompleteReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void startService(View v) {
		Intent intent = new Intent(this, MyService.class);
		startService(intent);
	}

	public void stopService(View v) {
		Intent intent = new Intent(this, MyService.class);
		stopService(intent);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (receiver == null) {
			receiver = new ServiceTaskCompleteReceiver();
		}
		IntentFilter filter = new IntentFilter(
				"bd.org.basis.service.task_completed");
		registerReceiver(receiver, filter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (receiver != null) {
			unregisterReceiver(receiver);
		}
	}

	class ServiceTaskCompleteReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Toast.makeText(getApplicationContext(), "Task Completed", Toast.LENGTH_LONG).show();
		}
	}
}
