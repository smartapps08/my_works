package com.example.animationtutorial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements AnimationListener {

	private LinearLayout layout;
	private Button rotateBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		layout = (LinearLayout) findViewById(R.id.layout);
		rotateBtn = (Button) findViewById(R.id.button1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void rotate(View v) {
		Animation rotateAnim = AnimationUtils
				.loadAnimation(this, R.anim.rotate);
		rotateAnim.setAnimationListener(this);
		rotateBtn.setAnimation(rotateAnim);

	}

	public void translate(View v) {
		TranslateAnimation translateAnim = new TranslateAnimation(0, 50, 0, 50);
		translateAnim.setDuration(1000);
		translateAnim.setAnimationListener(this);
		layout.setAnimation(translateAnim);
	}

	public void next(View v) {
		Intent i = new Intent(this, NextActivity.class);
		startActivity(i);
		overridePendingTransition(R.anim.in, R.anim.out);
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		Toast.makeText(getApplicationContext(), "End", Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationStart(Animation animation) {
		Toast.makeText(getApplicationContext(), "Started", Toast.LENGTH_SHORT)
				.show();
	}

}
