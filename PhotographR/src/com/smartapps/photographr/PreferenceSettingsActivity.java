package com.smartapps.photographr;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.smartapps.util.Constants;

public class PreferenceSettingsActivity extends SherlockActivity implements
		OnCheckedChangeListener {
	// private CheckBox chkStartup, chkService, chkAlarm;
	private SharedPreferences prefs;
	private Spinner spnAccuracy;
	private String[] strAccuracy = { "Street Level", "City Level",
			"Region Level", "Country Level" };
	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefs = getSharedPreferences(Constants.PREF_FILE_NAME,
				Context.MODE_PRIVATE);
		setContentView(R.layout.activity_preference_settings);
		// chkStartup = (CheckBox) findViewById(R.id.chkStartup);
		// chkService = (CheckBox) findViewById(R.id.chkService);
		// chkAlarm = (CheckBox) findViewById(R.id.chkAlarm);
		spnAccuracy = (Spinner) findViewById(R.id.spnAccuracy);

		// chkStartup.setOnCheckedChangeListener(this);
		// chkService.setOnCheckedChangeListener(this);
		// chkAlarm.setOnCheckedChangeListener(this);

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, strAccuracy);
		spnAccuracy.setAdapter(adapter);

		// boolean isStartup = prefs.getBoolean(
		// Constants.PREF_SETTINGS_STARTUP_FIELD, false);
		int acc = prefs.getInt(Constants.PREF_SETTINGS_ACCURACY_FIELD, 0);
		// if (isStartup) {
		// chkStartup.setChecked(true);
		// }
		spnAccuracy.setSelection(acc);
		spnAccuracy.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int pos,
					long id) {
				SharedPreferences.Editor editor = prefs.edit();
				editor.putInt(Constants.PREF_SETTINGS_ACCURACY_FIELD, pos);
				editor.commit();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		// Show the Up button in the action bar.
		setupActionBar();
	}

	private void setupActionBar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.preference_settings, menu);
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

	@Override
	public void onCheckedChanged(CompoundButton v, boolean isChecked) {
		switch (v.getId()) {
		// case R.id.chkStartup:
		// SharedPreferences.Editor editor = prefs.edit();
		// editor.putBoolean(Constants.PREF_SETTINGS_STARTUP_FIELD, isChecked);
		// editor.commit();
		// break;
		// case R.id.chkService:
		// Toast.makeText(getApplicationContext(),
		// "Not implemented yet. You'll get it in next release.",
		// Toast.LENGTH_LONG).show();
		// break;
		// case R.id.chkAlarm:
		// Toast.makeText(getApplicationContext(),
		// "Not implemented yet. You'll get it in next release.",
		// Toast.LENGTH_LONG).show();
		// break;
		default:
			break;
		}
	}

}
