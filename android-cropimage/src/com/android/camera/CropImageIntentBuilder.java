/*
 * Copyright (C) 2012 Lorenzo Villani.
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

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * By default the following features are enabled, unless you override them by
 * calling setters in the builder:
 * 
 * <ul>
 * <li>Scale;</li>
 * <li>Scale up (if needed);</li>
 * <li>Face detection;</li>
 * </ul>
 * 
 * @since 1.0.1
 */
public class CropImageIntentBuilder extends
		CropImageExtrasBuilder<CropImageIntentBuilder> {

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
	public CropImageIntentBuilder(final int outputX, final int outputY,
			final Uri saveUri) {
		super(outputX, outputY, saveUri);
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
	public CropImageIntentBuilder(final int aspectX, final int aspectY,
			final int outputX, final int outputY, final Uri saveUri) {
		super(aspectX, aspectY, outputX, outputY, saveUri);
	}

	/**
	 * Builds the Intent.
	 * 
	 * @param context
	 *            The application context.
	 * @return The newly created intent.
	 * @since 1.0.1
	 */
	public Intent getIntent(final Context context) {
		final Intent intent = new Intent(context, CropImage.class);
		intent.putExtras(buildExtras());
		return intent;
	}

}
