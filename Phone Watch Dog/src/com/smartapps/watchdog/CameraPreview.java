package com.smartapps.watchdog;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
	private static final String TAG = "Preview";

	SurfaceHolder mHolder;
	public Camera camera;

	CameraPreview(Context context) {
		super(context);
		mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	// private Camera openFrontFacingCamera() {
	// int cameraCount = 0;
	// Camera cam = null;
	// Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
	// cameraCount = Camera.getNumberOfCameras();
	// for ( int camIdx = 0; camIdx < cameraCount; camIdx++ ) {
	// Camera.getCameraInfo( camIdx, cameraInfo );
	// if ( cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT ) {
	// try {
	// cam = Camera.open( camIdx );
	// } catch (RuntimeException e) {
	// Log.e(TAG, "Camera failed to open: " + e.getLocalizedMessage());
	// }
	// }
	// }
	//
	// return cam;
	// }

	public void surfaceCreated(SurfaceHolder holder) {
		camera = Camera.open();

		try {

			// Camera.Parameters p = camera.getParameters();
			// p.set("camera-id", 2);
			//
			// camera.setParameters(p);

			// openFrontFacingCamera();

			camera.setPreviewDisplay(holder);

			camera.setPreviewCallback(new PreviewCallback() {

				public void onPreviewFrame(byte[] data, Camera arg1) {
					FileOutputStream outStream = null;
					try {
						outStream = new FileOutputStream(String.format(
								"/sdcard/%d.jpg", System.currentTimeMillis()));
						outStream.write(data);
						outStream.close();
						Log.d(TAG, "onPreviewFrame - wrote bytes: "
								+ data.length);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
					}
					CameraPreview.this.invalidate();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// camera.stopPreview();
		// camera.release();
		// camera = null;
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		Camera.Parameters parameters = camera.getParameters();
		parameters.setPreviewSize(w, h);
		camera.setParameters(parameters);
		camera.startPreview();
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		Paint p = new Paint(Color.RED);
		Log.d(TAG, "draw");
		canvas.drawText("PREVIEW", canvas.getWidth() / 2,
				canvas.getHeight() / 2, p);
	}
}
