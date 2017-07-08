package com.dfl.lib.lifecycle.implement;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.dfl.lib.lifecycle.Filters;
import com.dfl.lib.lifecycle.interface_.ACTIVITIES;
import com.dfl.lib.lifecycle.interface_.Filter;
import com.dfl.lib.lifecycle.interface_.LifecycleListen;

/**
 * Created by felix.dai on 2017/4/20.
 */

public class ActivityLifecycleListen implements LifecycleListen {

    private ACTIVITIES.OnCreate onCreate = ACTIVITIES.DEFAULT_ALL;
    private ACTIVITIES.OnStart onStart = ACTIVITIES.DEFAULT_ALL;
    private ACTIVITIES.OnResume onResume = ACTIVITIES.DEFAULT_ALL;
    private ACTIVITIES.OnPause onPause = ACTIVITIES.DEFAULT_ALL;
    private ACTIVITIES.OnStop onStop = ACTIVITIES.DEFAULT_ALL;
    private ACTIVITIES.OnSaveInstanceState onSaveInstanceState = ACTIVITIES.DEFAULT_ALL;
    private ACTIVITIES.OnDestroy onDestroy = ACTIVITIES.DEFAULT_ALL;

    private Filter<? super Activity> filter = Filters.none();

    private ACTIVITIES.All impl;

    public ActivityLifecycleListen onAll() {
        this.filter = Filter.ALL;
        return this;
    }

    public ActivityLifecycleListen on(@NonNull Activity activity) {
        this.filter = Filters.instance(activity);
        return this;
    }

    public ActivityLifecycleListen on(@NonNull Class<? extends Activity> clazz) {
        this.filter = Filters.type(clazz);
        return this;
    }

    public ActivityLifecycleListen on(@NonNull Filter<? super Activity> filter) {
        this.filter = filter;
        return this;
    }

    public ActivityLifecycleListen with(ACTIVITIES.OnCreate onCreate) {
        this.onCreate = onCreate;
        return this;
    }

    public ActivityLifecycleListen with(ACTIVITIES.OnStart onStart) {
        this.onStart = onStart;
        return this;
    }

    public ActivityLifecycleListen with(ACTIVITIES.OnResume onResume) {
        this.onResume = onResume;
        return this;
    }

    public ActivityLifecycleListen with(ACTIVITIES.OnPause onPause) {
        this.onPause = onPause;
        return this;
    }

    public ActivityLifecycleListen with(ACTIVITIES.OnStop onStop) {
        this.onStop = onStop;
        return this;
    }

    public ActivityLifecycleListen with(ACTIVITIES.OnSaveInstanceState onSaveInstanceState) {
        this.onSaveInstanceState = onSaveInstanceState;
        return this;
    }

    public ActivityLifecycleListen with(ACTIVITIES.OnDestroy onDestroy) {
        this.onDestroy = onDestroy;
        return this;
    }

    public ActivityLifecycleListen with(ACTIVITIES.All all) {
        this.onCreate = all;
        this.onStart = all;
        this.onResume = all;
        this.onPause = all;
        this.onStop = all;
        this.onSaveInstanceState = all;
        this.onDestroy = all;
        return this;
    }

    @Override
    public LifecycleListen listen() {
        if (impl == null) {
            impl = new ActivityLifecycleImpl(this);
        }
        ActivityLifecycleProxy.getInstance().addListen(impl, filter);
        return this;
    }

    @Override
    public void quit() {
        if (impl == null) {
            return;
        }
        ActivityLifecycleProxy.getInstance().removeListen(impl);
    }

    private static class ActivityLifecycleImpl implements ACTIVITIES.All {

        private ActivityLifecycleListen listen;

        private ActivityLifecycleImpl(ActivityLifecycleListen listen) {
            this.listen = listen;
        }

        @Override
        public void onCreate(Activity activity, Bundle savedInstanceState) {
            listen.onCreate.onCreate(activity, savedInstanceState);
        }

        @Override
        public void onStart(Activity activity) {
            listen.onStart.onStart(activity);
        }

        @Override
        public void onResume(Activity activity) {
            listen.onResume.onResume(activity);
        }

        @Override
        public void onPause(Activity activity) {
            listen.onPause.onPause(activity);
        }

        @Override
        public void onStop(Activity activity) {
            listen.onStop.onStop(activity);
        }

        @Override
        public void onSaveInstanceState(Activity activity, Bundle outState) {
            listen.onSaveInstanceState.onSaveInstanceState(activity, outState);
        }

        @Override
        public void onDestroy(Activity activity) {
            listen.onDestroy.onDestroy(activity);
        }
    }
}
