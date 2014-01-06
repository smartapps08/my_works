package com.smartapps.newsappdemo;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

public class NewsDetails extends SherlockFragmentActivity {
	Button btnUpdate;
	ActionBar actionBar;
	NewsDetailsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	public SlidingMenu menu;
	public ArrayList<NewsItem> newsItems;
	private Context context;
	public int position;

	public static void newInstance()// ArrayList<NewsItem> newsItemsArray, int
									// pos) {
	{
		// TODO Auto-generated constructor stub
		// newsItems = newsItemsArray;
		// position = pos;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_circles);
		// setContentView(R.layout.simple_tabs);
		newsItems = (ArrayList<NewsItem>) getIntent().getSerializableExtra(
				"items");
		position = getIntent().getIntExtra("position", -1);

		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		menu.setMenu(R.layout.sliding_menu);

		btnUpdate = (Button) findViewById(R.id.btnUpdate);

		btnUpdate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "I am Working....",
						Toast.LENGTH_SHORT).show();
				menu.toggle();
			}
		});

		actionBar = getSupportActionBar();
		// actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		// actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);

		// mSectionsPagerAdapter = new
		// NewsDetailsPagerAdapter(getSupportFragmentManager(), this);
		mSectionsPagerAdapter = new NewsDetailsPagerAdapter(
				getSupportFragmentManager(), this, newsItems, position);
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		PageIndicator mIndicator;
		CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
		mIndicator = indicator;
		// indicator.setViewPager(mViewPager);
		indicator.setViewPager(mViewPager, position);

		final float density = getResources().getDisplayMetrics().density;
		indicator.setBackgroundColor(0xFF333333);
		indicator.setRadius(4 * density);
		indicator.setPageColor(0xFF333333);
		indicator.setFillColor(0xFFFFFFFF);
		indicator.setStrokeColor(0xFFcccccc);
		indicator.setStrokeWidth(1 * density);

		indicator
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						// actionBar.setSelectedNavigationItem(position);
						switch (position) {
						case 0:
							menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
							break;
						default:
							menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
							break;
						}
					}
				});

		// String actiontitle = "News App Demo";
		// actionBar.setTitle(actiontitle);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			menu.toggle();
			break;

		default:
			return super.onOptionsItemSelected(item);
		}
		return false;
	}

}
