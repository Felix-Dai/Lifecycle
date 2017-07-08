package com.dfl.lib.lifecycle.implement;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;

import com.dfl.lib.lifecycle.Lifecycle;
import com.dfl.lib.lifecycle.interface_.ACTIVITIES;
import com.dfl.lib.lifecycle.interface_.Filter;
import com.dfl.lib.lifecycle.interface_.LifecycleListen;
import com.dfl.lib.lifecycle.util.HookUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by felix.dai on 2017/4/19.
 */

public class ActivityLifecycleProxy implements LifecycleListen, ACTIVITIES.All {

    private static final String CLASS_ACTIVITY_THREAD = "android.app.ActivityThread";
    private static final String METHOD_CURRENT_ACTIVITY_THREAD = "currentActivityThread";
    private static final String FIELD_INSTRUMENTATION = "mInstrumentation";

    private static ActivityLifecycleProxy instance = new ActivityLifecycleProxy();

    public static ActivityLifecycleProxy getInstance() {
        return instance;
    }

    private HashMap<ACTIVITIES.All, Filter<? super Activity>> map = new HashMap<>();

    private CallbacksDelegate callbacksDelegate;
    private InstrumentationDelegate instrumentationDelegate;

    void addListen(ACTIVITIES.All lifecycle, Filter<? super Activity> filter) {
        map.put(lifecycle, filter);
    }

    void removeListen(ACTIVITIES.All lifecycle) {
        map.remove(lifecycle);
    }

    private List<ACTIVITIES.All> getHitList(Activity activity) {
        List<ACTIVITIES.All> list = new ArrayList<>();
        for (Map.Entry<ACTIVITIES.All, Filter<? super Activity>> entry : map.entrySet()) {
            if (entry.getValue().hit(activity)) {
                list.add(entry.getKey());
            }
        }
        return list;
    }

    private void clearDeadListen(Activity activity) {
        List<ACTIVITIES.All> list = new ArrayList<>();
        for (Map.Entry<ACTIVITIES.All, Filter<? super Activity>> entry : map.entrySet()) {
            if (entry.getValue().deadWith(activity)) {
                list.add(entry.getKey());
            }
        }
        for (ACTIVITIES.All key : list) {
            map.remove(key);
        }
    }

    @Override
    public void onCreate(Activity activity, Bundle savedInstanceState) {
        for (ACTIVITIES.All listen : getHitList(activity)) {
            listen.onCreate(activity, savedInstanceState);
        }
    }

    @Override
    public void onStart(Activity activity) {
        for (ACTIVITIES.All listen : getHitList(activity)) {
            listen.onStart(activity);
        }
    }

    @Override
    public void onResume(Activity activity) {
        for (ACTIVITIES.All listen : getHitList(activity)) {
            listen.onResume(activity);
        }
    }

    @Override
    public void onPause(Activity activity) {
        for (ACTIVITIES.All listen : getHitList(activity)) {
            listen.onPause(activity);
        }
    }

    @Override
    public void onStop(Activity activity) {
        for (ACTIVITIES.All listen : getHitList(activity)) {
            listen.onStop(activity);
        }
    }

    @Override
    public void onSaveInstanceState(Activity activity, Bundle outState) {
        for (ACTIVITIES.All listen : getHitList(activity)) {
            listen.onSaveInstanceState(activity, outState);
        }
    }

    @Override
    public void onDestroy(Activity activity) {
        for (ACTIVITIES.All listen : getHitList(activity)) {
            listen.onDestroy(activity);
        }
        clearDeadListen(activity);
    }

    @Override
    public LifecycleListen listen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (callbacksDelegate == null) {
                callbacksDelegate = new CallbacksDelegate(this);
                Lifecycle.getApplication().registerActivityLifecycleCallbacks(callbacksDelegate);
            }
        } else {
            if (instrumentationDelegate == null) {
                try {
                    Object activityThread = HookUtil.invoke(CLASS_ACTIVITY_THREAD, METHOD_CURRENT_ACTIVITY_THREAD);
                    Instrumentation instrumentation = (Instrumentation) HookUtil.get(activityThread, FIELD_INSTRUMENTATION);
                    instrumentationDelegate = new InstrumentationDelegate(instrumentation, this);
                    HookUtil.set(activityThread, FIELD_INSTRUMENTATION, instrumentationDelegate);
                } catch (Exception ignored) {}
            }
        }
        return this;
    }

    @Override
    public void quit() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (callbacksDelegate != null) {
                Lifecycle.getApplication().unregisterActivityLifecycleCallbacks(callbacksDelegate);
                callbacksDelegate = null;
            }
        } else {
            if (this.instrumentationDelegate != null) {
                try {
                    Object activityThread = HookUtil.invoke(CLASS_ACTIVITY_THREAD, METHOD_CURRENT_ACTIVITY_THREAD);
                    HookUtil.set(activityThread, FIELD_INSTRUMENTATION, instrumentationDelegate.getInstrumentation());
                    instrumentationDelegate = null;
                } catch (Exception ignored) {}
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private static class CallbacksDelegate implements Application.ActivityLifecycleCallbacks {
        private ACTIVITIES.All lifecycle;

        public CallbacksDelegate(ACTIVITIES.All lifecycle) {
            this.lifecycle = lifecycle;
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            lifecycle.onCreate(activity, savedInstanceState);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            lifecycle.onStart(activity);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            lifecycle.onResume(activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {
            lifecycle.onPause(activity);
        }

        @Override
        public void onActivityStopped(Activity activity) {
            lifecycle.onStop(activity);
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            lifecycle.onSaveInstanceState(activity, outState);
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            lifecycle.onDestroy(activity);
        }
    }


    /**
     * Hook Activity的生命周期方法
     * 并且在
     * http://blog.csdn.net/u011068702/article/details/53208825
     * http://lib.csdn.net/article/android/58648
     *
     * @author billy.qi
     * @since 17/3/13 15:29
     */
    private static class InstrumentationDelegate extends Instrumentation {

        private Instrumentation instrumentation;
        private ACTIVITIES.All lifecycle;

        public InstrumentationDelegate(Instrumentation instrumentation, ACTIVITIES.All lifecycle) {
            this.instrumentation = instrumentation;
            this.lifecycle = lifecycle;
        }

        public Instrumentation getInstrumentation() {
            return instrumentation;
        }

        @Override
        public void callActivityOnCreate(Activity activity, Bundle icicle) {
            instrumentation.callActivityOnCreate(activity, icicle);
            lifecycle.onCreate(activity, icicle);
        }


        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void callActivityOnCreate(Activity activity, Bundle icicle, PersistableBundle persistentState) {
            instrumentation.callActivityOnCreate(activity, icicle, persistentState);
            lifecycle.onCreate(activity, icicle);
        }

        @Override
        public void callActivityOnRestart(Activity activity) {
            instrumentation.callActivityOnRestart(activity);
        }

        @Override
        public void callActivityOnStart(Activity activity) {
            instrumentation.callActivityOnStart(activity);
            lifecycle.onStart(activity);
        }

        @Override
        public void callActivityOnResume(Activity activity) {
            instrumentation.callActivityOnResume(activity);
            lifecycle.onResume(activity);
        }

        @Override
        public void callActivityOnPause(Activity activity) {
            instrumentation.callActivityOnPause(activity);
            lifecycle.onPause(activity);
        }

        @Override
        public void callActivityOnStop(Activity activity) {
            instrumentation.callActivityOnStop(activity);
            lifecycle.onStop(activity);
        }

        @Override
        public void callActivityOnDestroy(Activity activity) {
            instrumentation.callActivityOnDestroy(activity);
            lifecycle.onDestroy(activity);
        }

        @Override
        public void callActivityOnNewIntent(Activity activity, Intent intent) {
            instrumentation.callActivityOnNewIntent(activity, intent);
        }

        @Override
        public void callActivityOnPostCreate(Activity activity, Bundle icicle) {
            instrumentation.callActivityOnPostCreate(activity, icicle);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void callActivityOnRestoreInstanceState(Activity activity, Bundle savedInstanceState, PersistableBundle persistentState) {
            instrumentation.callActivityOnRestoreInstanceState(activity, savedInstanceState, persistentState);
        }

        @Override
        public void callActivityOnRestoreInstanceState(Activity activity, Bundle savedInstanceState) {
            instrumentation.callActivityOnRestoreInstanceState(activity, savedInstanceState);
            lifecycle.onSaveInstanceState(activity, savedInstanceState);
        }
    }
}
