package com.example.buttonanimation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;

public class MainActivity extends Activity implements AnimationListener {
	Button btnSecond;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnSecond = (Button) findViewById(R.id.button2);
	}

	public void showSecondButton(View v) {
		Animation anim = AnimationUtils.loadAnimation(this,
				R.anim.translate_anim);
		anim.setAnimationListener(this);
		btnSecond.setAnimation(anim);
		// ScaleAnimation scaleAnim = new ScaleAnimation(0.0f, 1.0f, 0.0f,
		// 1.0f);
		// scaleAnim.setDuration(3000);
		// scaleAnim.setAnimationListener(this);
		// btnSecond.setAnimation(scaleAnim);

	}

	@Override
	public void onAnimationEnd(Animation arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationRepeat(Animation arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationStart(Animation arg0) {
		btnSecond.setVisibility(View.VISIBLE);
	}
}
