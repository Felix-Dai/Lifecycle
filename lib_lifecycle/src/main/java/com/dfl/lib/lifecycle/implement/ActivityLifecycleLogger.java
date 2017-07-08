package com.dfl.lib.lifecycle.implement;

import android.app.Activity;
import android.os.Bundle;

import com.dfl.lib.lifecycle.interface_.ACTIVITIES;
import com.dfl.lib.lifecycle.util.Logger;

/**
 * Created by felix.dai on 2017/4/21.
 */

public class ActivityLifecycleLogger implements ACTIVITIES.All {

    private ACTIVITIES.All activityLifecycle;

    public ActivityLifecycleLogger(ACTIVITIES.All activityLifecycle) {
        this.activityLifecycle = activityLifecycle;
    }

    @Override
    public void onCreate(Activity activity, Bundle savedInstanceState) {
        activityLifecycle.onCreate(activity, savedInstanceState);
        Logger.i(activity + " ## " + "on create ## "  + savedInstanceState);
    }

    @Override
    public void onStart(Activity activity) {
        activityLifecycle.onStart(activity);
        Logger.i(activity + " ## on start ## ");
    }

    @Override
    public void onResume(Activity activity) {
        activityLifecycle.onResume(activity);
        Logger.i(activity + " ## on resume ## ");
    }

    @Override
    public void onPause(Activity activity) {
        activityLifecycle.onPause(activity);
        Logger.i(activity + " ## on pause ## ");
    }

    @Override
    public void onStop(Activity activity) {
        activityLifecycle.onStop(activity);
        Logger.i(activity + " ## on stop ## ");
    }

    @Override
    public void onSaveInstanceState(Activity activity, Bundle outState) {
        activityLifecycle.onSaveInstanceState(activity, outState);
        Logger.i(activity + " ## on save instance state ## " + outState);
    }

    @Override
    public void onDestroy(Activity activity) {
        activityLifecycle.onDestroy(activity);
        Logger.i(activity + " ## on destroy ## ");
    }
}
