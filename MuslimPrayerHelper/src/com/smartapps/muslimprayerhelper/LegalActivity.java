package com.smartapps.muslimprayerhelper;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class LegalActivity extends SherlockActivity {

	private TextView txtLegal;
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_legal);
		txtLegal = (TextView) findViewById(R.id.txtLegal);
		txtLegal.setText(GooglePlayServicesUtil
				.getOpenSourceSoftwareLicenseInfo(this));

		// Show the Up button in the action bar.
		actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// for Up navigarion
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
