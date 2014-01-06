package com.smartapp.priyo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class SignupActivity extends SherlockActivity {
	private EditText etUser, etPass, etCPass;
	private Button btnSignup;
	private SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		etUser = (EditText) findViewById(R.id.uid);
		etPass = (EditText) findViewById(R.id.passw);
		etCPass = (EditText) findViewById(R.id.cpassw);
		btnSignup = (Button) findViewById(R.id.bsignup);
		prefs = getSharedPreferences("auto", Context.MODE_PRIVATE);
		// Show the Up button in the action bar.
		setupActionBar();

		btnSignup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String uid = etUser.getText().toString();
				String pass = etPass.getText().toString();
				String cPass = etCPass.getText().toString();

				if (!uid.equals("") && !pass.equals("") && !cPass.equals("")) {
					if (pass.equals(cPass)) {
						// save
						SharedPreferences.Editor edit = prefs.edit();
						edit.putString("uid", uid);
						edit.putString("pass", pass);
						edit.commit();
						Toast.makeText(getApplicationContext(),
								"Sign Up successful", Toast.LENGTH_LONG).show();
						finish();

					} else {
						Toast.makeText(getApplicationContext(),
								"Password fields do not match",
								Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(getApplicationContext(),
							"Please fill up all the required fields",
							Toast.LENGTH_LONG).show();
				}

			}
		});
	}

	private void setupActionBar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.signup, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
