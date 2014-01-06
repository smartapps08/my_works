package com.android.camera;

import android.content.Context;

public interface MonitoredContext {

	public static interface LifeCycleListener {
	    public void onActivityCreated(MonitoredContext activity);
	    public void onActivityDestroyed(MonitoredContext activity);
	    public void onActivityStarted(MonitoredContext activity);
	    public void onActivityStopped(MonitoredContext activity);
	}

	public static class LifeCycleAdapter implements LifeCycleListener {
	    public void onActivityCreated(MonitoredContext activity) {
	    }
	
	    public void onActivityDestroyed(MonitoredContext activity) {
	    }
	
	    public void onActivityStarted(MonitoredContext activity) {
	    }
	
	    public void onActivityStopped(MonitoredContext activity) {
	    }
	}

	public void addLifeCycleListener(MonitoredContext.LifeCycleListener listener);

	public void removeLifeCycleListener(MonitoredContext.LifeCycleListener listener);

	public Context getContext();
	
	
}