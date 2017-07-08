package com.dfl.lib.lifecycle.implement;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.dfl.lib.lifecycle.interface_.ACTIVITIES;
import com.dfl.lib.lifecycle.interface_.FRAGMENTS;
import com.dfl.lib.lifecycle.interface_.Filter;
import com.dfl.lib.lifecycle.interface_.LifecycleListen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by felix.dai on 2017/4/24.
 */

public class FragmentLifecycleProxy implements LifecycleListen, FRAGMENTS.All {

    private static FragmentLifecycleProxy instance = new FragmentLifecycleProxy();

    public static FragmentLifecycleProxy getInstance() {
        return instance;
    }

    private HashMap<FRAGMENTS.All, Filter<? super Fragment>> map = new HashMap<>();
    private WeakHashMap<Fragment, List<FRAGMENTS.All>> deadMap = new WeakHashMap<>();

    private LifecycleListen activityListen;
    private CallbacksDelegate callbacksDelegate;

    void addListen(FRAGMENTS.All lifecycle, Filter<? super Fragment> filter) {
        map.put(lifecycle, filter);
    }

    void removeListen(FRAGMENTS.All lifecycle) {
        map.remove(lifecycle);
    }
    
    @Override
    public LifecycleListen listen() {
        if (activityListen == null) {
            activityListen = new ActivityLifecycleListen().onAll().with(new ACTIVITIES.OnCreate() {
                @Override
                public void onCreate(Activity activity, Bundle savedInstanceState) {
                    if (!(activity instanceof FragmentActivity)) {
                        return;
                    }
                    if (callbacksDelegate == null) {
                        callbacksDelegate = new CallbacksDelegate(FragmentLifecycleProxy.this);
                    }
                    ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(callbacksDelegate, true);
                }
            }).listen();
        }
        return this;
    }

    @Override
    public void quit() {
        if (activityListen != null) {
            activityListen.quit();
            activityListen = null;
        }
    }

    private List<FRAGMENTS.All> getHitList(FragmentManager fm, Fragment fragment) {
        List<FRAGMENTS.All> list = new ArrayList<>();
        for (Map.Entry<FRAGMENTS.All, Filter<? super Fragment>> entry : map.entrySet()) {
            if (entry.getValue().hit(fragment)) {
                list.add(entry.getKey());
            }
        }
        return list;
    }

    private List<FRAGMENTS.All> getDeadList(Fragment fragment) {
        List<FRAGMENTS.All> list = new ArrayList<>();
        for (Map.Entry<FRAGMENTS.All, Filter<? super Fragment>> entry : map.entrySet()) {
            if (entry.getValue().deadWith(fragment)) {
                list.add(entry.getKey());
            }
        }
        return list;
    }

    @Override
    public void onPreAttach(FragmentManager fm, Fragment f, Context context) {
        for (FRAGMENTS.All listen : getHitList(fm, f)) {
            listen.onPreAttach(fm, f, context);
        }
    }

    @Override
    public void onAttach(FragmentManager fm, Fragment f, Context context) {
        for (FRAGMENTS.All listen : getHitList(fm, f)) {
            listen.onAttach(fm, f, context);
        }
    }

    @Override
    public void onCreate(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        for (FRAGMENTS.All listen : getHitList(fm, f)) {
            listen.onCreate(fm, f, savedInstanceState);
        }
    }

    @Override
    public void onActivityCreate(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        for (FRAGMENTS.All listen : getHitList(fm, f)) {
            listen.onActivityCreate(fm, f, savedInstanceState);
        }
    }

    @Override
    public void onViewCreate(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
        for (FRAGMENTS.All listen : getHitList(fm, f)) {
            listen.onViewCreate(fm, f, v, savedInstanceState);
        }
    }

    @Override
    public void onStart(FragmentManager fm, Fragment f) {
        for (FRAGMENTS.All listen : getHitList(fm, f)) {
            listen.onStart(fm, f);
        }
    }

    @Override
    public void onResume(FragmentManager fm, Fragment f) {
        for (FRAGMENTS.All listen : getHitList(fm, f)) {
            listen.onResume(fm, f);
        }
    }

    @Override
    public void onPause(FragmentManager fm, Fragment f) {
        for (FRAGMENTS.All listen : getHitList(fm, f)) {
            listen.onPause(fm, f);
        }
    }

    @Override
    public void onStop(FragmentManager fm, Fragment f) {
        for (FRAGMENTS.All listen : getHitList(fm, f)) {
            listen.onStop(fm, f);
        }
    }

    @Override
    public void onSaveInstanceState(FragmentManager fm, Fragment f, Bundle outState) {
        for (FRAGMENTS.All listen : getHitList(fm, f)) {
            listen.onSaveInstanceState(fm, f, outState);
        }
    }

    @Override
    public void onViewDestroy(FragmentManager fm, Fragment f) {
        for (FRAGMENTS.All listen : getHitList(fm, f)) {
            listen.onViewDestroy(fm, f);
        }
    }

    @Override
    public void onDestroy(FragmentManager fm, Fragment f) {
        for (FRAGMENTS.All listen : getHitList(fm, f)) {
            listen.onDestroy(fm, f);
        }
        List<FRAGMENTS.All> deadList = deadMap.get(f);
        if (deadList == null) {
            deadList = new ArrayList<>();
        }
        deadList.addAll(getDeadList(f));
        deadMap.put(f, deadList);
    }

    @Override
    public void onDetach(FragmentManager fm, Fragment f) {
        for (FRAGMENTS.All listen : getHitList(fm, f)) {
            listen.onDetach(fm, f);
        }
        List<FRAGMENTS.All> deadList = deadMap.get(f);
        if (deadList == null) {
            return;
        }
        for (FRAGMENTS.All listen : deadList) {
            map.remove(listen);
        }
    }

    private static class CallbacksDelegate extends FragmentManager.FragmentLifecycleCallbacks {
        private FRAGMENTS.All lifecycle;

        public CallbacksDelegate(FRAGMENTS.All lifecycle) {
            super();
            this.lifecycle = lifecycle;
        }

        @Override
        public void onFragmentPreAttached(FragmentManager fm, Fragment f, Context context) {
            super.onFragmentPreAttached(fm, f, context);
            lifecycle.onPreAttach(fm, f, context);
        }

        @Override
        public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
            super.onFragmentAttached(fm, f, context);
            lifecycle.onAttach(fm, f, context);
        }

        @Override
        public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
            super.onFragmentCreated(fm, f, savedInstanceState);
            lifecycle.onCreate(fm, f, savedInstanceState);
        }

        @Override
        public void onFragmentActivityCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
            super.onFragmentActivityCreated(fm, f, savedInstanceState);
            lifecycle.onActivityCreate(fm, f, savedInstanceState);
        }

        @Override
        public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState);
            lifecycle.onViewCreate(fm, f, v, savedInstanceState);
        }

        @Override
        public void onFragmentStarted(FragmentManager fm, Fragment f) {
            super.onFragmentStarted(fm, f);
            lifecycle.onStart(fm, f);
        }

        @Override
        public void onFragmentResumed(FragmentManager fm, Fragment f) {
            super.onFragmentResumed(fm, f);
            lifecycle.onResume(fm, f);
        }

        @Override
        public void onFragmentPaused(FragmentManager fm, Fragment f) {
            super.onFragmentPaused(fm, f);
            lifecycle.onPause(fm, f);
        }

        @Override
        public void onFragmentStopped(FragmentManager fm, Fragment f) {
            super.onFragmentStopped(fm, f);
            lifecycle.onStop(fm, f);
        }

        @Override
        public void onFragmentSaveInstanceState(FragmentManager fm, Fragment f, Bundle outState) {
            super.onFragmentSaveInstanceState(fm, f, outState);
            lifecycle.onSaveInstanceState(fm, f, outState);
        }

        @Override
        public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
            super.onFragmentViewDestroyed(fm, f);
            lifecycle.onViewDestroy(fm, f);
        }

        @Override
        public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
            super.onFragmentDestroyed(fm, f);
            lifecycle.onDestroy(fm, f);
        }

        @Override
        public void onFragmentDetached(FragmentManager fm, Fragment f) {
            super.onFragmentDetached(fm, f);
            lifecycle.onDetach(fm, f);
        }
    }

    static class Bound extends FragmentLifecycleProxy {

        private FragmentManager fm;
        private boolean recursive;
        private CallbacksDelegate callbacksDelegate;

        public Bound(FragmentManager fm, boolean recursive) {
            this.fm = fm;
            this.recursive = recursive;
        }

        @Override
        public Bound listen() {
            if (callbacksDelegate == null) {
                callbacksDelegate = new CallbacksDelegate(this);
            }
            fm.registerFragmentLifecycleCallbacks(callbacksDelegate, recursive);
            return this;
        }

        @Override
        public void quit() {
            fm.unregisterFragmentLifecycleCallbacks(callbacksDelegate);
        }
    }
}
