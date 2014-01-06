package com.smartapps.newsappdemo;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class NewsDetailsPagerAdapter extends FragmentPagerAdapter {
	private Context context;
	private ArrayList<NewsItem> newsItems;
	private int position;

	public NewsDetailsPagerAdapter(FragmentManager fm, Context context,
			ArrayList<NewsItem> newsItems, int position) {
		super(fm);
		this.context = context;
		this.newsItems = newsItems;
		this.position = position;
		Log.e("POSITION", "POS: " + position);
	}

	@Override
	public Fragment getItem(int position) {
//		Log.e("POSITION", "POS: " + position);
//		return NewsDetailsFragment.newInstance();
		Fragment fragment = new NewsDetailsFragment();
		Bundle args = new Bundle();
		args.putInt("position", position);
		args.putSerializable("items", newsItems);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public int getCount() {
		// return 10;
		return newsItems.size();
	}

}
