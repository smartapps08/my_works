package com.smartapps.newsappdemo;

import java.util.Locale;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;

public class PagerAdapter extends FragmentPagerAdapter {
	private static final String[] CONTENT = new String[] { "Headliens", "NBA", "Football", "Top Stories",  "Recent" };
	private Context context;

	public PagerAdapter(FragmentManager fm, Context context) {
		super(fm);
		this.context = context;
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0:
			return new Tab1Fragment();
		case 1:
			return new Tab1Fragment();
		case 2:
			return new Tab1Fragment();
		case 3:
			return new Tab1Fragment();
		case 4:
			return new Tab1Fragment();
		}
		return new Fragment();
	}
	
	@Override
    public CharSequence getPageTitle(int position) {
        return CONTENT[position % CONTENT.length];
    }
	

	@Override
	public int getCount() {
		return 5;
	}

}
