/*
 * Copyright (C) 2007 The Android Open Source Project
 * Copyright (C) 2013 pablisco
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.camera;

import static android.provider.MediaStore.EXTRA_OUTPUT;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.CountDownLatch;

import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.media.FaceDetector;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.camera.DoneDiscardHelper.OnDoneDiscard;
import com.android.camera.gallery.IImage;
import com.android.camera.gallery.IImageList;
import com.android.gallery.R;

/**
 * The activity can crop specific region of interest from an image.
 */
public class CropImageFragment extends MonitoredFragment implements CropContext {

	private static final String TAG = "CropImage";

	/**
	 * These are various options can be specified in the intent.
	 */
	private Bitmap.CompressFormat mOutputFormat = Bitmap.CompressFormat.JPEG;
	private Uri mSaveUri = null;
	private boolean mSetWallpaper = false;
	private int mAspectX, mAspectY;
	private boolean mDoFaceDetection = true;
	private boolean mCircleCrop = false;
	private final Handler mHandler = new Handler();

	/**
	 * These options specifiy the output image size and whether we should
	 * scale the output to fit it (or just crop it).
	 */
	private int mOutputX, mOutputY;
	private boolean mScale;
	private boolean mScaleUp = true;

	/**
	 * Whether we are wait the user to pick a face.
	 */
	private boolean mWaitingToPick;
	/**
	 *  Whether the "save" button is already clicked.
	 */
	private boolean mSaving;

	private CropImageView mImageView;
	private ContentResolver mContentResolver;

	private Bitmap mBitmap;
	private HighlightView mCrop;

	private IImageList mAllImages;
	private IImage mImage;

	private final OnCropListener mDefaultCropListener = new BaseOnCropListener();
	private DoneDiscardHelper mDoneDiscardHelper;

	private DoneDiscardHelper getDiscardHelper() {
		if (mDoneDiscardHelper == null) {
			mDoneDiscardHelper = new DoneDiscardHelper(getActivity());
		}
		return mDoneDiscardHelper;
	}

	public ContentResolver getContentResolver() {
		if (mContentResolver == null) {
			mContentResolver = getActivity().getContentResolver();
		}
		return mContentResolver;
	}

	private OnCropListener onCropListener = mDefaultCropListener;

	private boolean mDisableDiscard;

	public interface OnCropListener {
		public void onCropFinished(Bundle results);

		public void onCropCancelled();
	}

	public class BaseOnCropListener implements OnCropListener {

		@Override
		public void onCropFinished(Bundle results) {
		}

		@Override
		public void onCropCancelled() {
			if (isVisible()) {
				getChildFragmentManager().popBackStack();
			}
		}

	}

	public void setOnCropListener(OnCropListener onCropListener) {
		if (onCropListener == null) {
			onCropListener = mDefaultCropListener;
		}
		this.onCropListener = onCropListener;
	}

	/**
	 * Checks if the done discard ui is needed.
	 */
	public boolean isDoneDiscardEnabled() {
		return getDiscardHelper().hasActionBar() && !mDisableDiscard;
	}

	private class OnDoneDiscardWrapper implements OnDoneDiscard,
			View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (StaticId.from(v.getId())) {
			case SAVE:
				onDone();
				break;
			case DISCARD:
				onDiscard();
				break;
			default:
				break;
			}
		}

		@Override
		public void onDone() {
			onSaveClicked();
		}

		@Override
		public void onDiscard() {
			onCropListener.onCropCancelled();
		}

	}

	private OnDoneDiscardWrapper actionListener = new OnDoneDiscardWrapper();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View result;
		if (isDoneDiscardEnabled()) {
			result = inflater.inflate(R.layout.cropimage, null);
		} else {
			result = inflater.inflate(R.layout.cropimage_buttoned, null);
			result.findViewById(R.id.discard)
					.setOnClickListener(actionListener);
			result.findViewById(R.id.save).setOnClickListener(actionListener);
		}
		mImageView = (CropImageView) result.findViewById(R.id.image);
		mImageView.setCropContext(this);

		return result;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isDoneDiscardEnabled()) {
			setHasOptionsMenu(true);
		}
	}
	
	@Override
	public void setArguments(Bundle extras) {
		super.setArguments(extras);
		if (extras != null) {
			if (extras.getString("circleCrop") != null) {
				mCircleCrop = true;
				mAspectX = 1;
				mAspectY = 1;
			}
			mSaveUri = (Uri) extras.getParcelable(MediaStore.EXTRA_OUTPUT);
			if (mSaveUri != null) {
				String outputFormatString = extras
						.getString(CropContext.EXTRA_OUTPUT_FORMAT);
				if (outputFormatString != null) {
					mOutputFormat = Bitmap.CompressFormat
							.valueOf(outputFormatString);
				}
			} else {
				mSetWallpaper = extras
						.getBoolean(CropContext.EXTRA_SET_WALLPAPER);
			}
			mBitmap = (Bitmap) extras.getParcelable(EXTRA_BITMAP_DATA);
			mAspectX = extras.getInt(EXTRA_ASPECT_X);
			mAspectY = extras.getInt(EXTRA_ASPECT_Y);
			mOutputX = extras.getInt(EXTRA_OUTPUT_X);
			mOutputY = extras.getInt(EXTRA_OUTPUT_Y);
			mScale = extras.getBoolean(EXTRA_SCALE, true);
			mScaleUp = extras.getBoolean(EXTRA_SCALE_UP_IF_NEEDED, true);
			mDoFaceDetection = extras.getBoolean(EXTRA_NO_FACE_DETECTION, true);
			mTarget = extras.getParcelable(EXTRA_SOURCE);
			mDisableDiscard = extras.getBoolean(EXTRA_DISABLE_DISCARD, false);
		}
	}

	/**
	 * onResume() makes the fragment interacting with the user (based on its
	 * containing activity being resumed).
	 * 
	 * @see <code><a
	 *      href="http://developer.android.com/reference/android/app/Fragment.html#Lifecycle">Fragment lifecycle</a></code>
	 */
	@Override
	public void onResume() {
		if (mBitmap == null) {
			mAllImages = ImageManager.makeImageList(getContentResolver(),
					mTarget, ImageManager.SORT_ASCENDING);
			mImage = mAllImages.getImageForUri(mTarget);
			if (mImage != null) {
				// Don't read in really large bitmaps. Use the (big) thumbnail
				// instead.
				// TODO when saving the resulting bitmap use the
				// decode/crop/encode api so we don't lose any resolution.
				mBitmap = mImage.thumbBitmap(IImage.ROTATE_AS_NEEDED);
			}
		}

		if (mBitmap == null) {
			dispatchCropCancelled();
		} else {
			startFaceDetection();
		}

		// enable done discard if needed
		if (isDoneDiscardEnabled()) {
			getDiscardHelper().setupDoneDiscard(actionListener);
		}
		super.onResume();
	}
	
	private void startFaceDetection() {
		if (getActivity().isFinishing()) {
			return;
		}

		mImageView.setImageBitmapResetBase(mBitmap, true);

		Util.startBackgroundJob(this, null,
				getResources().getString(R.string.runningFaceDetection),
				new Runnable() {
					public void run() {
						final CountDownLatch latch = new CountDownLatch(1);
						final Bitmap b = (mImage != null) ? mImage
								.fullSizeBitmap(IImage.UNCONSTRAINED,
										1024 * 1024) : mBitmap;
						mHandler.post(new Runnable() {
							public void run() {
								if (b != mBitmap && b != null) {
									mImageView.setImageBitmapResetBase(b, true);
									mBitmap.recycle();
									mBitmap = b;
								}
								if (mImageView.getScale() == 1F) {
									mImageView.center(true, true);
								}
								latch.countDown();
							}
						});
						try {
							latch.await();
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}
						mRunFaceDetection.run();
					}
				}, mHandler);
	}

	private void onSaveClicked() {
		// TODO this code needs to change to use the decode/crop/encode single
		// step api so that we don't require that the whole (possibly large)
		// bitmap doesn't have to be read into memory
		if (mCrop == null) {
			return;
		}

		if (mSaving)
			return;
		mSaving = true;

		Bitmap croppedImage;

		// If the output is required to a specific size, create an new image
		// with the cropped image in the center and the extra space filled.
		if (mOutputX != 0 && mOutputY != 0 && !mScale) {
			// Don't scale the image but instead fill it so it's the
			// required dimension
			croppedImage = Bitmap.createBitmap(mOutputX, mOutputY,
					Bitmap.Config.RGB_565);
			Canvas canvas = new Canvas(croppedImage);

			Rect srcRect = mCrop.getCropRect();
			Rect dstRect = new Rect(0, 0, mOutputX, mOutputY);

			int dx = (srcRect.width() - dstRect.width()) / 2;
			int dy = (srcRect.height() - dstRect.height()) / 2;

			// If the srcRect is too big, use the center part of it.
			srcRect.inset(Math.max(0, dx), Math.max(0, dy));

			// If the dstRect is too big, use the center part of it.
			dstRect.inset(Math.max(0, -dx), Math.max(0, -dy));

			// Draw the cropped bitmap in the center
			canvas.drawBitmap(mBitmap, srcRect, dstRect, null);

			// Release bitmap memory as soon as possible
			mImageView.clear();
			mBitmap.recycle();
		} else {
			Rect r = mCrop.getCropRect();

			int width = r.width();
			int height = r.height();

			// If we are circle cropping, we want alpha channel, which is the
			// third param here.
			croppedImage = Bitmap.createBitmap(width, height,
					mCircleCrop ? Bitmap.Config.ARGB_8888
							: Bitmap.Config.RGB_565);

			Canvas canvas = new Canvas(croppedImage);
			Rect dstRect = new Rect(0, 0, width, height);
			canvas.drawBitmap(mBitmap, r, dstRect, null);

			// Release bitmap memory as soon as possible
			mImageView.clear();
			mBitmap.recycle();

			if (mCircleCrop) {
				// OK, so what's all this about?
				// Bitmaps are inherently rectangular but we want to return
				// something that's basically a circle. So we fill in the
				// area around the circle with alpha. Note the all important
				// PortDuff.Mode.CLEAR.
				Canvas c = new Canvas(croppedImage);
				Path p = new Path();
				p.addCircle(width / 2F, height / 2F, width / 2F,
						Path.Direction.CW);
				c.clipPath(p, Region.Op.DIFFERENCE);
				c.drawColor(0x00000000, PorterDuff.Mode.CLEAR);
			}

			// If the required dimension is specified, scale the image.
			if (mOutputX != 0 && mOutputY != 0 && mScale) {
				croppedImage = Util.transform(new Matrix(), croppedImage,
						mOutputX, mOutputY, mScaleUp, Util.RECYCLE_INPUT);
			}
		}

		mImageView.setImageBitmapResetBase(croppedImage, true);
		mImageView.center(true, true);
		mImageView.mHighlightViews.clear();

		// Return the cropped image directly or save it to the specified URI.
		Bundle myExtras = getIntent().getExtras();
		if (myExtras != null
				&& (myExtras.getParcelable("data") != null || myExtras
						.getBoolean("return-data"))) {
			Bundle extras = new Bundle();
			extras.putParcelable(EXTRA_BITMAP_DATA, croppedImage);
			dispatchCropFinished(extras);
		} else {
			final Bitmap b = croppedImage;
			final int msdId = mSetWallpaper ? R.string.wallpaper
					: R.string.savingImage;
			Util.startBackgroundJob(this, null,
					getResources().getString(msdId), new Runnable() {
						public void run() {
							saveOutput(b);
						}
					}, mHandler);
		}
	}

	private Intent getIntent() {
		return getActivity().getIntent();
	}

	private void dispatchCropFinished(final Bundle extra) {
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				onCropListener.onCropFinished(extra);
			}
		});
	}

	private void dispatchCropCancelled() {
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				onCropListener.onCropCancelled();
			}
		});
	}

	private void saveOutput(Bitmap croppedImage) {
		if (mSaveUri != null) {
			OutputStream outputStream = null;
			try {
				outputStream = getContentResolver().openOutputStream(mSaveUri);
				if (outputStream != null) {
					croppedImage.compress(mOutputFormat, 75, outputStream);
				}
			} catch (IOException ex) {
				// TODO: report error to caller
				Log.e(TAG, "Cannot open file: " + mSaveUri, ex);
			} finally {
				Util.closeSilently(outputStream);
			}
			Bundle extras = new Bundle();
			dispatchCropFinished(extras);
		} else if (mSetWallpaper) {
			try {
				WallpaperManager.getInstance(getActivity()).setBitmap(
						croppedImage);
				dispatchCropFinished(new Bundle());
			} catch (IOException e) {
				Log.e(TAG, "Failed to set wallpaper.", e);
				dispatchCropCancelled();
			}
		} else {
			Bundle extras = new Bundle();
			extras.putString("rect", mCrop.getCropRect().toString());

			File oldPath = new File(mImage.getDataPath());
			File directory = new File(oldPath.getParent());

			int x = 0;
			String fileName = oldPath.getName();
			fileName = fileName.substring(0, fileName.lastIndexOf("."));

			// Try file-1.jpg, file-2.jpg, ... until we find a filename which
			// does not exist yet.
			while (true) {
				x += 1;
				String candidate = directory.toString() + "/" + fileName + "-"
						+ x + ".jpg";
				boolean exists = (new File(candidate)).exists();
				if (!exists) {
					break;
				}
			}

			try {
				int[] degree = new int[1];
				Uri newUri = ImageManager.addImage(getContentResolver(),
						mImage.getTitle(),
						mImage.getDateTaken(),
						null, // TODO this null is going to cause us to lose
								// the location (gps).
						directory.toString(), fileName + "-" + x + ".jpg",
						croppedImage, null, degree);
				extras.putParcelable(EXTRA_OUTPUT, newUri);
				dispatchCropFinished(extras);
			} catch (Exception ex) {
				// basically ignore this or put up
				// some ui saying we failed
				Log.e(TAG, "store image fail, continue anyway", ex);
			}
		}

		final Bitmap b = croppedImage;
		mHandler.post(new Runnable() {
			public void run() {
				mImageView.clear();
				b.recycle();
			}
		});
	}

	/**
	 * fragment is no longer interacting with the user either because its
	 * activity is being paused or a fragment operation is modifying it in the
	 * activity.
	 * 
	 * @see <code><a
	 *      href="http://developer.android.com/reference/android/app/Fragment.html#Lifecycle">Fragment lifecycle</a></code>
	 */
	@Override
	public void onPause() {
		// tear down the done/discard
		if (isDoneDiscardEnabled()) {
			getDiscardHelper().tearDownDoneDiscard();
		}
		super.onPause();
	}

	@Override
	public void onDestroy() {
		if (mAllImages != null) {
			mAllImages.close();
		}
		super.onDestroy();
	}

	Runnable mRunFaceDetection = new Runnable() {
		float mScale = 1F;
		Matrix mImageMatrix;
		FaceDetector.Face[] mFaces = new FaceDetector.Face[3];
		int mNumFaces;

		// For each face, we create a HightlightView for it.
		private void handleFace(FaceDetector.Face f) {
			PointF midPoint = new PointF();

			int r = ((int) (f.eyesDistance() * mScale)) * 2;
			f.getMidPoint(midPoint);
			midPoint.x *= mScale;
			midPoint.y *= mScale;

			int midX = (int) midPoint.x;
			int midY = (int) midPoint.y;

			HighlightView hv = new HighlightView(mImageView);

			int width = mBitmap.getWidth();
			int height = mBitmap.getHeight();

			Rect imageRect = new Rect(0, 0, width, height);

			RectF faceRect = new RectF(midX, midY, midX, midY);
			faceRect.inset(-r, -r);
			if (faceRect.left < 0) {
				faceRect.inset(-faceRect.left, -faceRect.left);
			}

			if (faceRect.top < 0) {
				faceRect.inset(-faceRect.top, -faceRect.top);
			}

			if (faceRect.right > imageRect.right) {
				faceRect.inset(faceRect.right - imageRect.right, faceRect.right
						- imageRect.right);
			}

			if (faceRect.bottom > imageRect.bottom) {
				faceRect.inset(faceRect.bottom - imageRect.bottom,
						faceRect.bottom - imageRect.bottom);
			}

			hv.setup(mImageMatrix, imageRect, faceRect, mCircleCrop,
					mAspectX != 0 && mAspectY != 0);

			mImageView.add(hv);
		}

		// Create a default HightlightView if we found no face in the picture.
		private void makeDefault() {
			HighlightView hv = new HighlightView(mImageView);

			int width = mBitmap.getWidth();
			int height = mBitmap.getHeight();

			Rect imageRect = new Rect(0, 0, width, height);

			// make the default size about 4/5 of the width or height
			int cropWidth = Math.min(width, height) * 4 / 5;
			int cropHeight = cropWidth;

			if (mAspectX != 0 && mAspectY != 0) {
				if (mAspectX > mAspectY) {
					cropHeight = cropWidth * mAspectY / mAspectX;
				} else {
					cropWidth = cropHeight * mAspectX / mAspectY;
				}
			}

			int x = (width - cropWidth) / 2;
			int y = (height - cropHeight) / 2;

			RectF cropRect = new RectF(x, y, x + cropWidth, y + cropHeight);
			hv.setup(mImageMatrix, imageRect, cropRect, mCircleCrop,
					mAspectX != 0 && mAspectY != 0);
			mImageView.add(hv);
		}

		// Scale the image down for faster face detection.
		private Bitmap prepareBitmap() {
			if (mBitmap == null) {
				return null;
			}

			// 256 pixels wide is enough.
			if (mBitmap.getWidth() > 256) {
				mScale = 256.0F / mBitmap.getWidth();
			}
			Matrix matrix = new Matrix();
			matrix.setScale(mScale, mScale);
			Bitmap faceBitmap = Bitmap.createBitmap(mBitmap, 0, 0,
					mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
			return faceBitmap;
		}

		public void run() {
			mImageMatrix = mImageView.getImageMatrix();
			Bitmap faceBitmap = prepareBitmap();

			mScale = 1.0F / mScale;
			if (faceBitmap != null && mDoFaceDetection) {
				FaceDetector detector = new FaceDetector(faceBitmap.getWidth(),
						faceBitmap.getHeight(), mFaces.length);
				mNumFaces = detector.findFaces(faceBitmap, mFaces);
			}

			if (faceBitmap != null && faceBitmap != mBitmap) {
				faceBitmap.recycle();
			}

			mHandler.post(new Runnable() {
				public void run() {
					mWaitingToPick = mNumFaces > 1;
					if (mNumFaces > 0) {
						for (int i = 0; i < mNumFaces; i++) {
							handleFace(mFaces[i]);
						}
					} else {
						makeDefault();
					}
					mImageView.invalidate();
					if (mImageView.mHighlightViews.size() == 1) {
						mCrop = mImageView.mHighlightViews.get(0);
						mCrop.setFocus(true);
					}

					if (mNumFaces > 1) {
						Toast t = Toast.makeText(getActivity(),
								R.string.multiface_crop_help,
								Toast.LENGTH_SHORT);
						t.show();
					}
				}
			});
		}
	};

	private Uri mTarget;

	@Override
	public Context getContext() {
		return getActivity();
	}

	@Override
	public boolean isWaitingToPick() {
		return mWaitingToPick;
	}

	@Override
	public void setWaitingToPick(boolean waiting) {
		mWaitingToPick = waiting;
	}

	@Override
	public boolean isSaving() {
		return mSaving;
	}

	@Override
	public void setCrop(HighlightView crop) {
		mCrop = crop;
	}
}