package com.dfl.lib.lifecycle.util;

import android.util.Log;

import com.dfl.lib.lifecycle.implement.ActivityLifecycleLogger;
import com.dfl.lib.lifecycle.implement.FragmentLifecycleLogger;
import com.dfl.lib.lifecycle.interface_.ACTIVITIES;
import com.dfl.lib.lifecycle.interface_.FRAGMENTS;

/**
 * Created by felix.dai on 2017/4/21.
 */

public class Logger {

    public static ACTIVITIES.All LOG_ACTIVITY = new ActivityLifecycleLogger(ACTIVITIES.DEFAULT_ALL);
    public static FRAGMENTS.All LOG_FRAGMENT = new FragmentLifecycleLogger(FRAGMENTS.DEFAULT_ALL);

    private static final String TAG = "LC_LOG";

    public static void i(String msg) {
        Log.i(TAG, msg);
    }
}
