package com.smartapps.holidaycalendar;

import java.util.ArrayList;
import java.util.Calendar;

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.caldroid.BanglaDateHelper;
import com.smartapps.helpers.BijoyFontUtil;
import com.smartapps.vacation.Holiday;

@SuppressLint({ "ValidFragment", "NewApi" })
public class DetailsDialog extends DialogFragment {

	private ArrayList<Holiday> holidays;
	private TextView txtDetails, txtNotice;
	private Button btnAddToCalendar;
	private LinearLayout layout;
	private Typeface tf;
	BijoyFontUtil tfUtil;
	String caption;
	String note;
	boolean lunarNotice = false;
	private DateTime dt;

	public DetailsDialog(ArrayList<Holiday> holidays) {
		this.holidays = holidays;
	}

	String str;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dialog_layout, container);
		txtDetails = (TextView) view.findViewById(R.id.txtOcassion);
		txtNotice = (TextView) view.findViewById(R.id.txtNotice);
		layout = (LinearLayout) view.findViewById(R.id.layout);
		btnAddToCalendar = (Button) view.findViewById(R.id.btnAddToCalendar);
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		tf = Typeface.createFromAsset(getActivity().getAssets(),
				"font/suttony.ttf");
		tfUtil = new BijoyFontUtil();
		btnAddToCalendar.setTypeface(tf);
		String strCmd = "ক্যালেন্ডারে যোগ করুন";
		String strCmdEnc = tfUtil.convertUnicode2BijoyString(strCmd);
		btnAddToCalendar.setText(strCmdEnc);
		if (holidays != null && holidays.size() > 0) {
			dt = holidays.get(0).getDateTime();
			// getDialog().setTitle(
			//
			// dt.get(DateTimeFieldType.dayOfMonth())
			// + dt.get(DateTimeFieldType.monthOfYear())
			// + dt.get(DateTimeFieldType.year()));

			//
			txtDetails.setTypeface(tf);
			str = "";

			for (Holiday holiday : holidays) {
				String lunar = "";
				if (holiday.isLunar()) {
					lunar = "*";
					lunarNotice = true;
				}
				String typeStr = "";
				switch (holiday.getType()) {
				case 0:
					layout.setBackgroundResource(R.drawable.red_border);
					txtDetails.setTextColor(Color.RED);
					typeStr = BanglaDateHelper.bangHolidayType[0];
					break;
				case 1:
					layout.setBackgroundResource(R.drawable.orange_border);
					txtDetails.setTextColor(Color.BLACK);
					typeStr = BanglaDateHelper.bangHolidayType[1];
					break;
				case 2:
					layout.setBackgroundResource(R.drawable.green_border);
					txtDetails.setTextColor(Color.BLACK);
					typeStr = BanglaDateHelper.bangHolidayType[2];
					break;
				case 3:
					layout.setBackgroundResource(R.drawable.blue_border);
					txtDetails.setTextColor(Color.BLACK);
					typeStr = BanglaDateHelper.bangHolidayType[3];
					break;
				case 4:
					layout.setBackgroundResource(R.drawable.blue_border);
					txtDetails.setTextColor(Color.BLACK);
					typeStr = BanglaDateHelper.bangHolidayType[4];
					break;
				case 5:
					layout.setBackgroundResource(R.drawable.blue_border);
					txtDetails.setTextColor(Color.BLACK);
					typeStr = BanglaDateHelper.bangHolidayType[5];
					break;
				case 6:
					layout.setBackgroundResource(R.drawable.blue_border);
					txtDetails.setTextColor(Color.BLACK);
					typeStr = BanglaDateHelper.bangHolidayType[6];
					break;
				default:
					break;
				}

				str = str.concat(holiday.getCaption() + lunar + " (" + typeStr
						+ ")" + "\n");
			}
			String strEncoded = tfUtil.convertUnicode2BijoyString(str);
			txtDetails.setText(strEncoded);
			if (lunarNotice) {
				txtNotice.setTypeface(tf);
				txtNotice.setTextColor(Color.RED);
				String s = "*চাঁদ দেখার উপর নির্ভরশীল";
				String encS = tfUtil.convertUnicode2BijoyString(s);
				txtNotice.setText(encS);
				txtNotice.setVisibility(View.VISIBLE);
			} else {
				txtNotice.setVisibility(View.GONE);
			}
			caption = str;
			note = "*চাঁদ দেখার উপর নির্ভরশীল";
		}

		btnAddToCalendar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// add event to calendar
				// if (Build.VERSION.SDK_INT >=
				// Build.VERSION_CODES.ICE_CREAM_SANDWICH) {

				Intent intent = new Intent(Intent.ACTION_INSERT,
						CalendarContract.Events.CONTENT_URI);
				intent.putExtra(CalendarContract.Events.TITLE, str);
				intent.putExtra(CalendarContract.Events.DESCRIPTION, caption);
				intent.putExtra(CalendarContract.Events.EVENT_LOCATION,
						"(এডিট করুন)");
				Calendar startTime = Calendar.getInstance();
				startTime.set(dt.get(DateTimeFieldType.year()),
						dt.get(DateTimeFieldType.monthOfYear()) - 1,
						dt.get(DateTimeFieldType.dayOfMonth()));
				// startTime.set(2013, 7, 16, 1, 11);
				try {
					intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
							startTime.getTimeInMillis());
					intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
					startActivity(intent);
				} catch (Exception e) {
					Toast.makeText(getActivity(),
							"Supported only in Android 4.0 and above.",
							Toast.LENGTH_LONG).show();
				}
				// } else {
				// Toast.makeText(
				// getActivity(),
				// "Calendar Content rovider is not supported in your device.",
				// Toast.LENGTH_LONG).show();
				// }
			}
		});
		return view;
	}

}
