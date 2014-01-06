/*
 * Copyright (C) 2013 pablisco
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.camera;

import static android.provider.MediaStore.EXTRA_OUTPUT;
import static com.android.camera.CropContext.EXTRA_ASPECT_X;
import static com.android.camera.CropContext.EXTRA_ASPECT_Y;
import static com.android.camera.CropContext.EXTRA_BITMAP_DATA;
import static com.android.camera.CropContext.EXTRA_DISABLE_DISCARD;
import static com.android.camera.CropContext.EXTRA_NO_FACE_DETECTION;
import static com.android.camera.CropContext.EXTRA_OUTPUT_X;
import static com.android.camera.CropContext.EXTRA_OUTPUT_Y;
import static com.android.camera.CropContext.EXTRA_SCALE;
import static com.android.camera.CropContext.EXTRA_SCALE_UP_IF_NEEDED;
import static com.android.camera.CropContext.EXTRA_SOURCE;
import android.app.ActionBar;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

/**
 * 
 * Extracted the builder pattern from {@link CropImageIntentBuilder}.
 * 
 * @author pablisco
 * 
 * @param <SELF>
 */
// TODO: Circle Crop
// TODO: Set Wallpaper
public class CropImageExtrasBuilder<SELF extends CropImageExtrasBuilder<SELF>> {

	private static final int DEFAULT_SCALE = 1;

	private boolean scale = true;
	private boolean scaleUpIfNeeded = true;
	private boolean doFaceDetection = true;
	private boolean disableDiscard = false;
	private Uri sourceImage;
	private Bitmap bitmap;

	private final int aspectX;
	private final int aspectY;
	private final int outputX;
	private final int outputY;
	private final Uri saveUri;

	/**
	 * Constructor.
	 * 
	 * @param outputX
	 *            Output vertical size in pixels.
	 * @param outputY
	 *            Output horizontal size in pixels.
	 * @param saveUri
	 *            Output file URI.
	 * @since 1.0.1
	 */
	public CropImageExtrasBuilder(final int outputX, final int outputY,
			final Uri saveUri) {
		this(DEFAULT_SCALE, DEFAULT_SCALE, outputX, outputY, saveUri);
	}

	/**
	 * Constructor.
	 * 
	 * @param aspectX
	 *            Horizontal aspect ratio.
	 * @param aspectY
	 *            Vertical aspect ratio.
	 * @param outputX
	 *            Output vertical size in pixels.
	 * @param outputY
	 *            Output horizontal size in pixels.
	 * @param saveUri
	 *            Output file URI.
	 * @since 1.0.1
	 */
	public CropImageExtrasBuilder(final int aspectX, final int aspectY,
			final int outputX, final int outputY, final Uri saveUri) {
		this.aspectX = aspectX;
		this.aspectY = aspectY;
		this.outputX = outputX;
		this.outputY = outputY;
		this.saveUri = saveUri;
	}

	public Bundle buildExtras() {

		Bundle args = new Bundle();

		//
		// Required arguments
		//

		args.putInt(EXTRA_ASPECT_X, this.aspectX);
		args.putInt(EXTRA_ASPECT_Y, this.aspectY);
		args.putInt(EXTRA_OUTPUT_X, this.outputX);
		args.putInt(EXTRA_OUTPUT_Y, this.outputY);
		args.putParcelable(EXTRA_OUTPUT, this.saveUri);

		//
		// Optional arguments
		//
		args.putBoolean(EXTRA_DISABLE_DISCARD, disableDiscard);
		args.putBoolean(EXTRA_SCALE, this.scale);
		args.putBoolean(EXTRA_SCALE_UP_IF_NEEDED, this.scaleUpIfNeeded);
		args.putBoolean(EXTRA_NO_FACE_DETECTION, !this.doFaceDetection);

		if (this.bitmap != null) {
			args.putParcelable(EXTRA_BITMAP_DATA, this.bitmap);
		}

		if (this.sourceImage != null) {
			args.putParcelable(EXTRA_SOURCE, this.sourceImage);
		}

		return args;

	}

	/**
	 * Scales down the picture.
	 * 
	 * @param scale
	 *            Whether to scale down the image.
	 * @return This Builder object to allow for chaining of calls to set
	 *         methods.
	 * @since 1.0.1
	 */
	public SELF setScale(final boolean scale) {
		this.scale = scale;

		return self();
	}

	/**
	 * Whether to scale up the image if the cropped region is smaller than the
	 * output size.
	 * 
	 * @param scaleUpIfNeeded
	 *            Whether to scale up the image.
	 * @return This Builder object to allow for chaining of calls to set
	 *         methods.
	 * @since 1.0.1
	 */
	public SELF setScaleUpIfNeeded(final boolean scaleUpIfNeeded) {
		this.scaleUpIfNeeded = scaleUpIfNeeded;

		return self();
	}

	/**
	 * Performs face detection before allowing users to crop the image.
	 * 
	 * @param doFaceDetection
	 *            Whether to perform face detection.
	 * @return This Builder object to allow for chaining of calls to set
	 *         methods.
	 * @since 1.0.1
	 */
	public SELF setDoFaceDetection(final boolean doFaceDetection) {
		this.doFaceDetection = doFaceDetection;
		return self();
	}

	/**
	 * 
	 * Disable the Done/Discard buttons on the {@link ActionBar}
	 * 
	 * @param disableDiscard
	 *            default false
	 * @return
	 * 
	 * @since TODO
	 */
	public SELF setDisableDiscard(final boolean disableDiscard) {
		this.disableDiscard = disableDiscard;
		return self();
	}

	/**
	 * Sets bitmap data to crop. Please note that this method overrides any
	 * source image set by {@link #setSourceImage(Uri)}.
	 * 
	 * @param bitmap
	 *            The {@link Bitmap} to crop.
	 * @return This Builder object to allow for chaining of calls to set
	 *         methods.
	 * @since 1.0.1
	 */
	public SELF setBitmap(final Bitmap bitmap) {
		this.bitmap = bitmap;
		return self();
	}

	/**
	 * Sets the Uri of the image to crop. It must be accessible to the calling
	 * application/activity.
	 * 
	 * @param sourceImage
	 *            Uri of the image to crop.
	 * @return This Builder object to allow for chaining of calls to set
	 *         methods.
	 * @since 1.0.1
	 */
	public SELF setSourceImage(final Uri sourceImage) {
		this.sourceImage = sourceImage;
		return self();
	}

	/**
	 * 
	 * @return a copy of itself properly casted.
	 */
	@SuppressWarnings("unchecked")
	protected SELF self() {
		return (SELF) this;
	}

}
