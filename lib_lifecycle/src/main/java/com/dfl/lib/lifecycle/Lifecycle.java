package com.dfl.lib.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.dfl.lib.lifecycle.implement.ActivityLifecycleListen;
import com.dfl.lib.lifecycle.implement.ActivityLifecycleProxy;
import com.dfl.lib.lifecycle.implement.FragmentLifecycleListen;
import com.dfl.lib.lifecycle.implement.FragmentLifecycleProxy;

import java.lang.ref.WeakReference;

/**
 * Created by felix.dai on 2017/4/19.
 */

public class Lifecycle {

    private static WeakReference<Application> appRef;

    public static void init(Application app) {
        appRef = new WeakReference<>(app);
        ActivityLifecycleProxy.getInstance().listen();
        FragmentLifecycleProxy.getInstance().listen();
    }

    public static void quit() {
        FragmentLifecycleProxy.getInstance().quit();
        ActivityLifecycleProxy.getInstance().quit();
        appRef = null;
    }

    @NonNull
    public static Application getApplication() {
        Application app = appRef == null ? null : appRef.get();
        if (app == null) {
            throw new IllegalStateException("Lifecycle is not inited.");
        }
        return app;
    }

    public static ActivityLifecycleListen activity() {
        return new ActivityLifecycleListen();
    }

    public static ActivityLifecycleListen activity(Class<? extends Activity> clazz) {
        return new ActivityLifecycleListen().on(clazz);
    }

    public static ActivityLifecycleListen activity(Activity activity) {
        return new ActivityLifecycleListen().on(activity);
    }

    public static FragmentLifecycleListen fragment() {
        return new FragmentLifecycleListen();
    }

    public static FragmentLifecycleListen fragment(Class<? extends Fragment> clazz) {
        return new FragmentLifecycleListen().on(clazz);
    }

    public static FragmentLifecycleListen fragment(Fragment fragment) {
        return new FragmentLifecycleListen().on(fragment);
    }
}
