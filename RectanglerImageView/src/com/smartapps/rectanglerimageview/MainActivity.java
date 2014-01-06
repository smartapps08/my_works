package com.smartapps.rectanglerimageview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;

public class MainActivity extends Activity {
	Bitmap bm;
	ImageView imageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		imageView = (ImageView) findViewById(R.id.imageView);
		Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.test1)).getBitmap();
		ImageHelper ih = new ImageHelper();
		bm = ih.getRoundedCornerBitmap(bitmap, 30);
		imageView.setImageBitmap(bm);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
