package bd.org.basis.customgesturedemo;

import java.util.ArrayList;

import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity implements
		OnGesturePerformedListener {
	GestureOverlayView gestureView;
	GestureLibrary library;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gestureView = (GestureOverlayView) findViewById(R.id.gestureOverlayView1);
		gestureView.addOnGesturePerformedListener(this);
		// create library

		library = GestureLibraries.fromRawResource(this, R.raw.gestures);
		if (!library.load()) {
			Toast.makeText(getApplicationContext(),
					"Library could not be loeaded", Toast.LENGTH_LONG).show();
		}

	}

	@Override
	public void onGesturePerformed(GestureOverlayView view, Gesture gesture) {
		// match gesture with library
		ArrayList<Prediction> predictions = library.recognize(gesture);
		for (Prediction prediction : predictions) {
			Log.e("Prediction", prediction.name + "---------"
					+ prediction.score);
		}
		Toast.makeText(getApplicationContext(),
				"Detected Gesture: " + predictions.get(0).name,
				Toast.LENGTH_LONG).show();

	}

}
