package com.agme.nearme;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class SettingsActivity extends SherlockActivity {
	private EditText etRadius;
	private SharedPreferences pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		etRadius = (EditText) findViewById(R.id.etRadius);
		// Show the Up button in the action bar.
		setupActionBar();
		pref = getSharedPreferences("nearme", Context.MODE_PRIVATE);
		int radius=pref.getInt("radius", 1000);
		etRadius.setText(radius+"");
	}

	private void setupActionBar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_save:
			String radiusString = etRadius.getText().toString();
			if (!radiusString.equals("")) {
				SharedPreferences.Editor editor = pref.edit();
				editor.putInt("radius", Integer.parseInt(radiusString));
				editor.commit();
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
