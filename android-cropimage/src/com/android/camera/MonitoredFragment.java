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

import android.os.Bundle;
import android.support.v4.app.Fragment;

public abstract class MonitoredFragment extends Fragment implements MonitoredContext {

    private final ArrayList<LifeCycleListener> mListeners =
            new ArrayList<LifeCycleListener>();

    public void addLifeCycleListener(LifeCycleListener listener) {
        if (mListeners.contains(listener)) return;
        mListeners.add(listener);
    }

    public void removeLifeCycleListener(LifeCycleListener listener) {
        mListeners.remove(listener);
    }
    
    protected void dispatchOnCreate() {
    	for (LifeCycleListener listener : mListeners) {
    		listener.onActivityCreated(this);
    	}
    }
    
    protected void dispatchOnDestroy() {
    	for (LifeCycleListener listener : mListeners) {
    		listener.onActivityDestroyed(this);
    	}
    }
    
    protected void dispatchOnStart() {
        for (LifeCycleListener listener : mListeners) {
            listener.onActivityStarted(this);
        }
    }
    
    protected void dispatchOnStop() {
        for (LifeCycleListener listener : mListeners) {
            listener.onActivityStopped(this);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dispatchOnCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dispatchOnDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        dispatchOnStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        dispatchOnStop();
    }
    
}
