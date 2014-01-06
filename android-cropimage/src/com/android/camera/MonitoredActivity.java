/*
 * Copyright (C) 2009 The Android Open Source Project
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

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;

public class MonitoredActivity extends NoSearchActivity implements MonitoredContext {

    private final ArrayList<MonitoredContext.LifeCycleListener> mListeners =
            new ArrayList<MonitoredContext.LifeCycleListener>();

    /* (non-Javadoc)
	 * @see com.android.camera.MonitoredContext#addLifeCycleListener(com.android.camera.MonitoredActivity.LifeCycleListener)
	 */
    @Override
	public void addLifeCycleListener(MonitoredContext.LifeCycleListener listener) {
        if (mListeners.contains(listener)) return;
        mListeners.add(listener);
    }

    /* (non-Javadoc)
	 * @see com.android.camera.MonitoredContext#removeLifeCycleListener(com.android.camera.MonitoredActivity.LifeCycleListener)
	 */
    @Override
	public void removeLifeCycleListener(MonitoredContext.LifeCycleListener listener) {
        mListeners.remove(listener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (MonitoredContext.LifeCycleListener listener : mListeners) {
            listener.onActivityCreated(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (MonitoredContext.LifeCycleListener listener : mListeners) {
            listener.onActivityDestroyed(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        for (MonitoredContext.LifeCycleListener listener : mListeners) {
            listener.onActivityStarted(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        for (MonitoredContext.LifeCycleListener listener : mListeners) {
            listener.onActivityStopped(this);
        }
    }

	@Override
	public Context getContext() {
		return this;
	}

}
