package com.smartapps.holidaycalendar;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.smartapps.helpers.BijoyFontUtil;

public class NoticeActivity extends SherlockActivity {

	private TextView txtCaption, txtRule1, txtRule2;
	private Typeface tf;
	private BijoyFontUtil tfUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notice);

		txtCaption = (TextView) findViewById(R.id.txtCaption);
		txtRule1 = (TextView) findViewById(R.id.txtRule1);
		txtRule2 = (TextView) findViewById(R.id.txtRule2);

		tf = Typeface.createFromAsset(getAssets(), "font/suttony.ttf");
		txtCaption.setTypeface(tf);
		txtRule1.setTypeface(tf);
		txtRule2.setTypeface(tf);
		tfUtil = new BijoyFontUtil();
		String strLabel = "টীকা";

		String strLabelEncoded = tfUtil.convertUnicode2BijoyString(strLabel);
		txtCaption.setText(strLabelEncoded);

		strLabel = "১: যে কোন সম্প্রদায়ের একজন কর্মচারিকে তাঁহার নিজের ধর্ম অনুযায়ী বছরে মোট ৩ (তিন) দিনের মাত্রা পর্যন্ত ঐচ্ছিক ছুটি ভোগ করিবার অনুমতি প্রদান করা যাইতে পারে এবং এ ব্যাপারে প্রত্যেক কর্মচারিকে বৎসরের প্রারম্ভে নিজ ধর্ম অনুযায়ী নির্ধারিত ৩ (তিন) দিনের ঐচ্ছিক ছুটি  ভোগ করিবার ইচ্ছা উপযুক্ত কর্তৃপক্ষ কর্তৃক অনুমোদন করাইয়া লইতে হইবে। সাধারণ ছুটি, নির্বাহী আদেশে সরকারি ছুটি ও সাপ্তাহিক ছুটির সহিত যুক্ত করিয়া ঐচ্ছিক ছুটি ভোগ করিবার অনুমতি দেওয়া যাইতে পারে।";
		strLabelEncoded = tfUtil.convertUnicode2BijoyString(strLabel);
		txtRule1.setText(strLabelEncoded);

		strLabel = "২: এ বিজ্ঞপ্তিতে ঘোষিত ছুটি সকল অফিস ও প্রতিষ্ঠানের বেলায় প্রযোজ্য। কিন্তু যে সকল অফিস ও প্রতিষ্ঠান যথা- ব্যাংক, ইন্স্যুরেন্স, ডাক, তার টেলিফোন, রেলওয়ে, হাসপাতাল ও রাষ্ট্রায়ত্ত শিল্প প্রতিষ্ঠান ও কলকারখানা ইত্যাদিতে অফিসের সময়সূচি ও ছুটি তাদের নিজস্ব আইন-কানুন দ্বারা নিয়ন্ত্রিত হইয়া থাকে অথবা যে সকল অফিস ও প্রতিষ্ঠানের চাকুরী সরকার কর্তৃক অত্যাবশ্যক চাকুরী (এসেন্সিয়াল সার্ভিস) হিসাবে ঘোষণা করা হইয়াছে উহাদের বেলায় এইরূপ ছুটি আপনা-আপনি প্রযোজ্য হইবে না। ঐ সকল অফিস ও প্রতিষ্ঠান তাহাদের নিজস্ব আইন-কানুন অনুযায়ী জনস্বার্থ বিবেচনা করিয়া তাহাদের ছুটি ঘোষণা করিবে।";
		strLabelEncoded = tfUtil.convertUnicode2BijoyString(strLabel);
		txtRule2.setText(strLabelEncoded);

		// Show the Up button in the action bar.
		setupActionBar();
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

}
