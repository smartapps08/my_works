package com.smartapps.holidaycalendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.caldroid.CaldroidFragment;
import com.caldroid.CaldroidListener;
import com.smartapps.helpers.BijoyFontUtil;
import com.smartapps.vacation.Holiday;
import com.smartapps.vacation.HolidayHelper;

public class MainActivity extends SherlockFragmentActivity {

	private SimpleDateFormat formatter;
	private CaldroidFragment caldroidFragment;

	private Button btnShow;
	private Typeface tf;
	BijoyFontUtil tfUtil;
	private ArrayList<Holiday> holidays;
	private int selectedMonth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnShow = (Button) findViewById(R.id.btnList);
		setUpUI();
		// Show the Up button in the action bar.
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		formatter = new SimpleDateFormat("dd MMM yyyy");
		// Setup caldroid fragment
		// **** If you want normal CaldroidFragment, use below line ****
		caldroidFragment = new CaldroidFragment();

		// If Activity is created after rotation
		if (savedInstanceState != null) {
			caldroidFragment.restoreStatesFromKey(savedInstanceState,
					"CALDROID_SAVED_STATE");
		}
		// If activity is created from fresh
		else {
			Bundle args = new Bundle();
			Calendar cal = Calendar.getInstance();
			args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
			args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
			args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
			args.putBoolean(CaldroidFragment.FIT_ALL_MONTHS, false);

			// Uncomment this to customize startDayOfWeek
			// args.putInt("startDayOfWeek", 6); // Saturday
			caldroidFragment.setArguments(args);
		}

		// Attach to the activity
		FragmentTransaction t = getSupportFragmentManager().beginTransaction();
		t.replace(R.id.calendar1, caldroidFragment);
		t.commit();

		// Setup listener
		final CaldroidListener listener = new CaldroidListener() {

			@Override
			public void onSelectDate(Date date, View view) {

				if (holidays != null && holidays.size() > 0) {
					showEditDialog(date);
				}
			}

			@Override
			public void onChangeMonth(int month, int year) {
				// String text = "month: " + month + " year: " + year;
				// Toast.makeText(getApplicationContext(), text,
				// Toast.LENGTH_SHORT).show();

				holidays = HolidayHelper.holidayMap.get(month + "");
				selectedMonth = month;
			}
		};

		caldroidFragment.setCaldroidListener(listener);
	}

	private void showEditDialog(Date date) {
		ArrayList<Holiday> selected = new ArrayList<Holiday>();
		DateTime jd = new DateTime(date.getTime());
		for (Holiday hd : holidays) {
			if (jd.getDayOfMonth() == hd.getDateTime().getDayOfMonth()
					&& jd.getMonthOfYear() == hd.getDateTime().getMonthOfYear()) {
				selected.add(hd);
			}
		}
		if (selected != null && selected.size() > 0) {
			FragmentManager fm = getSupportFragmentManager();
			DetailsDialog detailsDialog = new DetailsDialog(selected);
			detailsDialog.show(fm, "fragment_details");
		}
	}

	private void setUpUI() {
		tf = Typeface.createFromAsset(getAssets(), "font/suttony.ttf");
		tfUtil = new BijoyFontUtil();

		btnShow.setTypeface(tf);
		String strLabel = "এই মাসের ছুটির তালিকা দেখতে...";
		String strLabelEncoded = tfUtil.convertUnicode2BijoyString(strLabel);
		btnShow.setText(strLabelEncoded);

	}

	public void showList(View v) {
		if (holidays.size() > 0) {
			Intent intent = new Intent(this, ListHolidayActivity.class);
			intent.putExtra("month", selectedMonth);
			startActivity(intent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			startActivity(new Intent(this, NoticeActivity.class));
			return true;

		case R.id.action_help:
			showHelpDialog();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void showHelpDialog() {
		FragmentManager fm = getSupportFragmentManager();
		HelpDialog helpDialog = new HelpDialog();
		helpDialog.show(fm, "fragment_help");
	}

}
