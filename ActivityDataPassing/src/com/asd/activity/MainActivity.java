package com.asd.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final int CAPTURE_DATA = 1;
	private TextView txtResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		txtResult = (TextView) findViewById(R.id.txtResult);
	}

	public void goToSecond(View v) {

		Intent i = new Intent(this, SecondActivity.class);
		startActivityForResult(i, CAPTURE_DATA);
		overridePendingTransition(R.anim.enter_anim, R.anim.still_anim);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_DATA) {
			if (resultCode == Activity.RESULT_OK) {
				String str = data.getStringExtra("data");
				txtResult.setText(str);
				User user = (User) data.getSerializableExtra("user");
				Toast.makeText(getApplicationContext(),
						user.getName() + "-----" + user.getPhone(),
						Toast.LENGTH_LONG).show();
			} else {
				txtResult.setText("No Data Sent from Second");
			}
		}
	}

}
