package com.smartapps.holidaycalendar;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.caldroid.BanglaDateHelper;
import com.smartapps.helpers.BijoyFontUtil;
import com.smartapps.vacation.Holiday;
import com.smartapps.vacation.HolidayHelper;

public class ListHolidayActivity extends SherlockActivity {
	public int monthFromIntent;
	public ArrayList<Holiday> holidays;
	public HolidayAdapter adapter;
	private ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_holiday);

		lv = (ListView) findViewById(R.id.list);
		// Show the Up button in the action bar.

		setupActionBar();
		monthFromIntent = getIntent().getIntExtra("month", -1);
		if (monthFromIntent > -1) {
			holidays = HolidayHelper.holidayMap.get(monthFromIntent + "");
			adapter = new HolidayAdapter(this, R.layout.list_row, holidays);
			lv.setAdapter(adapter);
		}

	}

	private void setupActionBar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
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

	public class HolidayAdapter extends ArrayAdapter<Holiday> {
		ArrayList<Holiday> days;
		Activity context;

		Typeface tf;
		BijoyFontUtil tfUtil;

		public HolidayAdapter(Context context, int resource,
				List<Holiday> objects) {
			super(context, resource, objects);
			this.days = (ArrayList<Holiday>) objects;
			this.context = (Activity) context;

		}

		@Override
		public int getCount() {
			return days.size();
		}

		@Override
		public Holiday getItem(int position) {
			return days.get(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			if (convertView == null) {
				LayoutInflater inflator = context.getLayoutInflater();
				view = inflator.inflate(R.layout.list_row, null);
				TextView txtCaption = (TextView) view
						.findViewById(R.id.txtCaption);
				TextView txtType = (TextView) view.findViewById(R.id.txtType);
				TextView txtDate = (TextView) view.findViewById(R.id.txtDate);
				RelativeLayout layout = (RelativeLayout) view
						.findViewById(R.id.layout);
				tf = Typeface.createFromAsset(getAssets(), "font/suttony.ttf");
				txtCaption.setTypeface(tf);
				txtType.setTypeface(tf);
				txtDate.setTypeface(tf);
				tfUtil = new BijoyFontUtil();
				Holiday h = days.get(position);

				if (h.getType() == 0) {
					txtCaption.setTextColor(Color.BLACK);
					// layout.setBackgroundResource(resid)
				} else if (h.getType() == 1) {
					layout.setBackgroundResource(R.color.LightOrange);
				} else if (h.getType() == 2) {
					layout.setBackgroundResource(R.color.LightGreen);
				} else if (h.getType() > 2) {
					layout.setBackgroundResource(R.color.LightBlue);
				}

				txtDate.setText(h.getDateTime().getDayOfMonth() + "-"
						+ h.getDateTime().getMonthOfYear() + "-"
						+ h.getDateTime().getYear());
				String str = h.getCaption();
				String sEnc = tfUtil.convertUnicode2BijoyString(str);
				txtCaption.setText(sEnc);

				str = BanglaDateHelper.bangHolidayType[h.getType()];
				sEnc = tfUtil.convertUnicode2BijoyString(str);
				txtType.setText(sEnc);

			} else {
				view = convertView;
			}

			return view;
		}
	}

}
