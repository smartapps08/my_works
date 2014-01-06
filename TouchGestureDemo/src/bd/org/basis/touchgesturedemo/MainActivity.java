package bd.org.basis.touchgesturedemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

public class MainActivity extends Activity implements OnGestureListener,
		OnDoubleTapListener {

	GestureDetector detector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		detector = new GestureDetector(this, this);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// Log.e(getClass().getSimpleName(), "----------onTouchEvent--------");
		detector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		Log.e(getClass().getSimpleName(),
				"----------onDown--------" + e.getAction() + " x: " + e.getX()
						+ " y: " + e.getY());
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float vx, float vy) {
		Log.e(getClass().getSimpleName(),
				"----------onFling--------" + e1.getAction() + " x1: "
						+ e1.getX() + " y1: " + e1.getY() + " x2: " + e2.getX()
						+ " y2: " + e2.getY() + vx + "---" + vy);

		if (e1.getX() - e2.getX() > 0) {
			// right to left
		} else {
			// left to right
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		Log.e(getClass().getSimpleName(),
				"----------onLongPress--------" + e.getAction() + " x: "
						+ e.getX() + " y: " + e.getY());
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx, float dy) {
		Log.e(getClass().getSimpleName(),
				"----------onScroll--------" + e1.getAction() + " x1: "
						+ e1.getX() + " y1: " + e1.getY() + " x2: " + e2.getX()
						+ " y2: " + e2.getY() + dx + "---" + dy);
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		Log.e(getClass().getSimpleName(),
				"----------onShowPress--------" + e.getAction() + " x: "
						+ e.getX() + " y: " + e.getY());
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		Log.e(getClass().getSimpleName(),
				"----------onSingleTapUp--------" + e.getAction() + " x: "
						+ e.getX() + " y: " + e.getY());
		return false;
	}

	@Override
	public boolean onDoubleTap(MotionEvent e) {
		Log.e(getClass().getSimpleName(),
				"----------onDoubleTap--------" + e.getAction() + " x: "
						+ e.getX() + " y: " + e.getY());
		return false;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		Log.e(getClass().getSimpleName(), "----------onDoubleTapEvent--------"
				+ e.getAction() + " x: " + e.getX() + " y: " + e.getY());
		return false;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		Log.e(getClass().getSimpleName(),
				"----------onSingleTapConfirmed--------" + e.getAction()
						+ " x: " + e.getX() + " y: " + e.getY());
		return false;
	}

}
