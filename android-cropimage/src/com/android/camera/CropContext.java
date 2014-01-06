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

public interface CropContext extends MonitoredContext {

	public static final String EXTRA_ASPECT_X = "aspectX";
	public static final String EXTRA_ASPECT_Y = "aspectY";
	public static final String EXTRA_OUTPUT_X = "outputX";
	public static final String EXTRA_OUTPUT_Y = "outputY";
	public static final String EXTRA_BITMAP_DATA = "data";
	public static final String EXTRA_SCALE_UP_IF_NEEDED = "scaleUpIfNeeded";
	public static final String EXTRA_SCALE = "scale";
	public static final String EXTRA_NO_FACE_DETECTION = "noFaceDetection";
	public static final String EXTRA_SET_WALLPAPER = "setWallpaper";
	public static final String EXTRA_OUTPUT_FORMAT = "outputFormat";
	public static final String EXTRA_SOURCE = "source";
	public static final String EXTRA_DISABLE_DISCARD = "disableDiscard";

	public abstract boolean isWaitingToPick();

	public abstract void setWaitingToPick(boolean waiting);

	public abstract boolean isSaving();

	public abstract void setCrop(HighlightView crop);

}