package com.smartapp.priyo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class SimulateActivity extends SherlockActivity {
	double speed1, time1, speed2, time2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// setContentView(R.layout.activity_simulate);
		// Show the Up button in the action bar.
		setupActionBar();
		speed1 = getIntent().getDoubleExtra("speed1", -1);
		speed2 = getIntent().getDoubleExtra("speed2", -1);
		time1 = getIntent().getDoubleExtra("time1", -1);
		time2 = getIntent().getDoubleExtra("time2", -1);
		setContentView(new Panel(this));
	}

	private void setupActionBar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.simulate, menu);
		return true;
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

	class Panel extends SurfaceView implements SurfaceHolder.Callback {

		private Bitmap mBitmap;
		private Bitmap mBitmap1;
		private ViewThread mThread;

		private int mX;
		private int mY;
		private int mX1;
		private int mY1;

		public boolean finished = false;

		public Panel(Context context) {
			super(context);
			mBitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.car1);
			mBitmap1 = BitmapFactory.decodeResource(getResources(),
					R.drawable.car2);
			getHolder().addCallback(this);
			mThread = new ViewThread(this);
			mY = 200;
			mY1 = mY + 50;
			// SurfaceHolder h = getHolder();
			// Canvas canvas = h.lockCanvas();
			// doDraw(canvas);
			// h.unlockCanvasAndPost(canvas);
		}

		public void doDraw(Canvas canvas) {
			canvas.drawColor(Color.WHITE);
			Paint paint = new Paint();

			canvas.drawBitmap(mBitmap, mX, mY, null);
			canvas.drawBitmap(mBitmap1, mX1, mY1, null);
			if (finished == true) {
				if (time1 < time2) {
					paint.setColor(Color.RED);
					paint.setTextSize(40.0f);
					canvas.drawText("Vehicle 1 is better", 30, 50, paint);
				} else {
					paint.setColor(Color.RED);
					paint.setTextSize(40.0f);
					canvas.drawText("Vehicle 2 is better", 30, 50, paint);
				}
			}
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			// TODO Auto-generated method stub
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			if (!mThread.isAlive()) {
				mThread = new ViewThread(this);
				mThread.setRunning(true);
				mThread.start();
			}
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			if (mThread.isAlive()) {
				mThread.setRunning(false);
			}
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			if (!finished) {
				mX = 5;
				mY = 200;
				mX1 = 5;
				mY1 = mY + 50;
			} else {
				finish();
			}

			return super.onTouchEvent(event);
		}

		class ViewThread extends Thread {
			private Panel mPanel;
			private SurfaceHolder mHolder;
			private boolean mRun = false;

			public ViewThread(Panel panel) {
				mPanel = panel;
				mHolder = mPanel.getHolder();
			}

			public void setRunning(boolean run) {
				mRun = run;
			}

			@Override
			public void run() {
				Canvas canvas = null;
				while (mRun) {

					canvas = mHolder.lockCanvas();
					if (canvas != null) {
						if (mX < getWidth() || mX1 < getWidth()) {
							mX += getWidth() / (10 * time1);
							Log.e("mX", "mX Value " + mX + " Time: " + time1
									+ " " + getWidth());
							mY += 0;
							mX1 += getWidth() / (10 * time2);
							Log.e("mX1", "mX1 Value " + mX1 + " Time: " + time2
									+ " " + getWidth());
							mY1 += 0;
						} else {
							finished = true;
						}
						mPanel.doDraw(canvas);
						mHolder.unlockCanvasAndPost(canvas);
					}
				}
			}
		}
	}

	// ///////

}
