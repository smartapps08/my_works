package com.example.navigationexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_play:
			Toast.makeText(getApplicationContext(), "Play Started", Toast.LENGTH_LONG).show();
			return true;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void goToNext(View v) {
		startActivity(new Intent(this, NextActivity.class));
	}

	public void goToAnother(View v) {
		startActivity(new Intent(this, AnotherActivity.class));
	}

}
