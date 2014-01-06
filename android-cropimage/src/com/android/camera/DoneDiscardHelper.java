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

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.gallery.R;

/**
 * 
 * Helper to setup the Done/Discard pattern on
 * 
 * @author pablisco
 * 
 * @since 1.0.1
 * 
 */
@SuppressLint("NewApi")
public class DoneDiscardHelper {

	private int mActionBarDiaplayOptions = -1;
	private ActionBar mActionBar;

	@SuppressLint("NewApi")
	private static final int DISPLAY_OPTIONS_MASK = ActionBar.DISPLAY_SHOW_CUSTOM
			| ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE;

	public static interface OnDoneDiscard {
		public void onDone();

		public void onDiscard();
	}

	public DoneDiscardHelper(Activity activity) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			mActionBar = activity.getActionBar();
		}
	}

	public boolean hasActionBar() {
		return mActionBar != null;
	}

	/**
	 * 
	 * Set up the Done/Discard UI pattern in the provided activity. Need to
	 * check if the activity provided has a valid {@link ActionBar} using
	 * {@link #hasActionBar(Activity)} before using this;
	 * 
	 * @param activity
	 * @param onDoneDiscard
	 */
	public void setupDoneDiscard(final OnDoneDiscard onDoneDiscard) {
		ActionBar actionBar = mActionBar;
		LayoutInflater inflater = (LayoutInflater) actionBar.getThemedContext()
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		final View customActionBarView = inflater.inflate(
				R.layout.actionbar_custom_view_done_discard, null);

		View.OnClickListener actionListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int id = v.getId();
				switch (StaticId.from(id)) {
				case ACTIONBAR_DONE:
					onDoneDiscard.onDone();
					break;
				case ACTIONBAR_DISCARD:
					onDoneDiscard.onDiscard();
					break;
				default:
					break;
				}
			}
		};

		customActionBarView.findViewById(R.id.actionbar_discard)
				.setOnClickListener(actionListener);
		customActionBarView.findViewById(R.id.actionbar_done)
				.setOnClickListener(actionListener);

		mActionBarDiaplayOptions = actionBar.getDisplayOptions();
		// Show the custom action bar view and hide the normal Home icon and
		// title.
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,
				DISPLAY_OPTIONS_MASK);
		ActionBar.LayoutParams params = new ActionBar.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		actionBar.setCustomView(customActionBarView, params);
	}

	public void tearDownDoneDiscard() {
		final ActionBar actionBar = mActionBar;
		actionBar.getDisplayOptions();
		actionBar.setDisplayOptions(mActionBarDiaplayOptions,
				DISPLAY_OPTIONS_MASK);
		actionBar.setCustomView(null);
	}

}
