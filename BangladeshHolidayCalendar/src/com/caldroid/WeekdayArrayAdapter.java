package com.caldroid;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.smartapps.helpers.BijoyFontUtil;
import com.smartapps.holidaycalendar.R;

/**
 * Customize the weekday gridview
 */
public class WeekdayArrayAdapter extends ArrayAdapter<String> {
	public static int textColor = Color.DKGRAY;
	private Typeface banglaTypeface;
	private BijoyFontUtil tfUtil;
	private Context context;
	private List<String> weekdays;

	public WeekdayArrayAdapter(Context context, int textViewResourceId,
			List<String> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.weekdays = objects;
	}

	// To prevent cell highlighted when clicked
	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}

	// Set color to gray and text size to 12sp
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// To customize text size and color
		TextView textView = (TextView) super.getView(position, convertView,
				parent);

		String item = getItem(position);
		if (position == 5 || position == 6) {
			textView.setTextColor(Color.RED);
		} else {
			textView.setTextColor(textColor);
		}
		textView.setGravity(Gravity.CENTER);
		tfUtil = new BijoyFontUtil();
		banglaTypeface = Typeface.createFromAsset(context.getAssets(),
				"font/suttony.ttf");
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
		textView.setTypeface(banglaTypeface);
		textView.setText(tfUtil
				.convertUnicode2BijoyString(BanglaDateHelper.days[position]));
		return textView;
	}

}
