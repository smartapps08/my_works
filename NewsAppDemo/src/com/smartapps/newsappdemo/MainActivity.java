package com.smartapps.newsappdemo;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;


public class MainActivity extends SherlockFragmentActivity {
	Button btnUpdate;

	ActionBar actionBar;
	PagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	public SlidingMenu menu;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		setContentView(R.layout.simple_tabs);

		
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
				Toast.makeText(getApplicationContext(), "I am Working....", Toast.LENGTH_SHORT).show();
				menu.toggle();
			}
		});
        
        actionBar = getSupportActionBar();
//		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		
		mSectionsPagerAdapter = new PagerAdapter(getSupportFragmentManager(), this);
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
//		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
//				.getDisplayMetrics());
//		mViewPager.setPageMargin(pageMargin);

		TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);
		 
        indicator.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
//				actionBar.setSelectedNavigationItem(position);
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
		
//	    String actiontitle = "News App Demo";
//		actionBar.setTitle(actiontitle);

//		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
//			if (i==0) {
//				actionBar.addTab(actionBar.newTab().setTabListener(this));
//			    actionBar.getTabAt(i).setText("Headlines");
//			}else if (i==1) {
//				actionBar.addTab(actionBar.newTab().setText("Recent news").setTabListener(this));
//			}else if (i==2) {
//				actionBar.addTab(actionBar.newTab().setText("NBL News").setTabListener(this));
//			}
//			else if (i==3) {
//				actionBar.addTab(actionBar.newTab().setText("Sports").setTabListener(this));
//			}
//			else if (i==4) {
//				actionBar.addTab(actionBar.newTab().setText("Top Stories").setTabListener(this));
//			}
//		}
		
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
