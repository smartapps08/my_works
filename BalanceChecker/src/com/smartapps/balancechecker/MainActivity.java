package com.smartapps.balancechecker;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void checkBalance(View v) {
		startActivity(new Intent("android.intent.action.CALL",
				Uri.parse("tel:*" + "152" + Uri.encode("#"))));
	}
}
