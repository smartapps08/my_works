package com.asd.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SecondActivity extends Activity {
	private EditText etData, etNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		etData = (EditText) findViewById(R.id.etData);
		etNumber = (EditText) findViewById(R.id.etNumber);
	}

	public void captureData(View v) {
		String data = etData.getText().toString();
		String number = etNumber.getText().toString();
		User user = new User(data, number);

		Intent i = getIntent();
		/**
		 * Intent: action, category, extra
		 */
		i.putExtra("data", data);
		i.putExtra("user", user);
		if (data.equals("")) {
			setResult(Activity.RESULT_CANCELED, i);
		} else {
			setResult(Activity.RESULT_OK, i);
		}
		finish();
		overridePendingTransition(R.anim.still_anim, R.anim.exit_anim);
	}

}
