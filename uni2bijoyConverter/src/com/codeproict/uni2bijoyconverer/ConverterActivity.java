package com.codeproict.uni2bijoyconverer;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ConverterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_converter);

		// initializing tf instance with Suttony font
		Typeface tf = Typeface.createFromAsset(getAssets(), "font/suttony.ttf");

		// Initializing TextView and setting the typeface created
		TextView banglaTextView = (TextView) findViewById(R.id.textView1);
		banglaTextView.setTypeface(tf);

		// Bangla Unicode text goes here.
		String banglaUnicodeString = "ফেব্রুয়ারি";

		// creating intance of BijoyFotnUtil Class
		BijoyFontUtil tfUtil = new BijoyFontUtil();
		
		// Convert Bangla font encoding from unicode to bijoy using the utility.
		String banglaBijoyStringEncoded = tfUtil
				.convertUnicode2BijoyString(banglaUnicodeString);
		Log.e("STR", banglaUnicodeString);
		Log.e("STR", banglaBijoyStringEncoded);
		// Setting the text in the TextView
		banglaTextView.setText(banglaBijoyStringEncoded);
	}

}
