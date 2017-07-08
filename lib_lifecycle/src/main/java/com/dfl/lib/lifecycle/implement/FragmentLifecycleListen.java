package com.dfl.lib.lifecycle.implement;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.dfl.lib.lifecycle.Filters;
import com.dfl.lib.lifecycle.interface_.FRAGMENTS;
import com.dfl.lib.lifecycle.interface_.Filter;
import com.dfl.lib.lifecycle.interface_.LifecycleListen;

/**
 * Created by felix.dai on 2017/4/24.
 */

public class FragmentLifecycleListen implements LifecycleListen {

    private FRAGMENTS.OnPreAttach onPreAttach = FRAGMENTS.DEFAULT_ALL;
    private FRAGMENTS.OnAttach onAttach = FRAGMENTS.DEFAULT_ALL;
    private FRAGMENTS.OnCreate onCreate = FRAGMENTS.DEFAULT_ALL;
    private FRAGMENTS.OnActivityCreate onActivityCreate = FRAGMENTS.DEFAULT_ALL;
    private FRAGMENTS.OnViewCreate onViewCreate = FRAGMENTS.DEFAULT_ALL;
    private FRAGMENTS.OnStart onStart = FRAGMENTS.DEFAULT_ALL;
    private FRAGMENTS.OnResume onResume = FRAGMENTS.DEFAULT_ALL;
    private FRAGMENTS.OnPause onPause = FRAGMENTS.DEFAULT_ALL;
    private FRAGMENTS.OnStop onStop = FRAGMENTS.DEFAULT_ALL;
    private FRAGMENTS.OnSaveInstanceState onSaveInstanceState = FRAGMENTS.DEFAULT_ALL;
    private FRAGMENTS.OnViewDestroy onViewDestroy = FRAGMENTS.DEFAULT_ALL;
    private FRAGMENTS.OnDestroy onDestroy = FRAGMENTS.DEFAULT_ALL;
    private FRAGMENTS.OnDetach onDetach = FRAGMENTS.DEFAULT_ALL;

    private Filter<? super Fragment> filter = Filters.none();

    private FragmentManager fm;
    private boolean recursive;

    private FRAGMENTS.All impl;

    public FragmentLifecycleListen onAll() {
        this.filter = Filter.ALL;
        return this;
    }

    public FragmentLifecycleListen on(@NonNull Fragment fragment) {
        this.filter = Filters.instance(fragment);
        return this;
    }

    public FragmentLifecycleListen on(@NonNull Class<? extends Fragment> clazz) {
        this.filter = Filters.type(clazz);
        return this;
    }

    public FragmentLifecycleListen on(@NonNull Filter<? super Fragment> filter) {
        this.filter = filter;
        return this;
    }

    public FragmentLifecycleListen on(@NonNull FragmentManager fm, boolean recursive) {
        this.fm = fm;
        this.recursive = recursive;
        return this;
    }

    public FragmentLifecycleListen with(FRAGMENTS.OnPreAttach onPreAttach) {
        this.onPreAttach = onPreAttach;
        return this;
    }

    public FragmentLifecycleListen with(FRAGMENTS.OnAttach onAttach) {
        this.onAttach = onAttach;
        return this;
    }

    public FragmentLifecycleListen with(FRAGMENTS.OnCreate onCreate) {
        this.onCreate = onCreate;
        return this;
    }

    public FragmentLifecycleListen with(FRAGMENTS.OnActivityCreate onActivityCreate) {
        this.onActivityCreate = onActivityCreate;
        return this;
    }

    public FragmentLifecycleListen with(FRAGMENTS.OnViewCreate onViewCreate) {
        this.onViewCreate = onViewCreate;
        return this;
    }

    public FragmentLifecycleListen with(FRAGMENTS.OnStart onStart) {
        this.onStart = onStart;
        return this;
    }

    public FragmentLifecycleListen with(FRAGMENTS.OnResume onResume) {
        this.onResume = onResume;
        return this;
    }

    public FragmentLifecycleListen with(FRAGMENTS.OnPause onPause) {
        this.onPause = onPause;
        return this;
    }

    public FragmentLifecycleListen with(FRAGMENTS.OnStop onStop) {
        this.onStop = onStop;
        return this;
    }

    public FragmentLifecycleListen with(FRAGMENTS.OnSaveInstanceState onSaveInstanceState) {
        this.onSaveInstanceState = onSaveInstanceState;
        return this;
    }

    public FragmentLifecycleListen with(FRAGMENTS.OnViewDestroy onViewDestroy) {
        this.onViewDestroy = onViewDestroy;
        return this;
    }

    public FragmentLifecycleListen with(FRAGMENTS.OnDestroy onDestroy) {
        this.onDestroy = onDestroy;
        return this;
    }

    public FragmentLifecycleListen with(FRAGMENTS.OnDetach onDetach) {
        this.onDetach = onDetach;
        return this;
    }

    public FragmentLifecycleListen with(FRAGMENTS.All all) {
        this.onPreAttach = all;
        this.onAttach = all;
        this.onCreate = all;
        this.onActivityCreate = all;
        this.onViewCreate = all;
        this.onStart = all;
        this.onResume = all;
        this.onPause = all;
        this.onStop = all;
        this.onSaveInstanceState = all;
        this.onViewDestroy = all;
        this.onDestroy = all;
        this.onDetach = all;
        return this;
    }

    @Override
    public LifecycleListen listen() {
        if (impl == null) {
            impl = new FragmentLifecycleImpl(this);
        }
        if (fm == null) {
            FragmentLifecycleProxy.getInstance().addListen(impl, filter);
        } else {
            new FragmentLifecycleProxy.Bound(fm, recursive).listen().addListen(impl, filter);
        }
        return this;
    }

    @Override
    public void quit() {
        if (impl == null) {
            return;
        }
        FragmentLifecycleProxy.getInstance().removeListen(impl);
    }
    
    private static class FragmentLifecycleImpl implements FRAGMENTS.All {

        FragmentLifecycleListen listen;

        public FragmentLifecycleImpl(FragmentLifecycleListen listen) {
            this.listen = listen;
        }

        @Override
        public void onPreAttach(FragmentManager fm, Fragment f, Context context) {
            listen.onPreAttach.onPreAttach(fm, f, context);
        }

        @Override
        public void onAttach(FragmentManager fm, Fragment f, Context context) {
            listen.onAttach.onAttach(fm, f, context);
        }

        @Override
        public void onCreate(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
            listen.onCreate.onCreate(fm, f, savedInstanceState);
        }

        @Override
        public void onActivityCreate(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
            listen.onActivityCreate.onActivityCreate(fm, f, savedInstanceState);
        }

        @Override
        public void onViewCreate(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
            listen.onViewCreate.onViewCreate(fm, f, v, savedInstanceState);
        }

        @Override
        public void onStart(FragmentManager fm, Fragment f) {
            listen.onStart.onStart(fm, f);
        }

        @Override
        public void onResume(FragmentManager fm, Fragment f) {
            listen.onResume.onResume(fm, f);
        }

        @Override
        public void onPause(FragmentManager fm, Fragment f) {
            listen.onPause.onPause(fm, f);
        }

        @Override
        public void onStop(FragmentManager fm, Fragment f) {
            listen.onStop.onStop(fm, f);
        }

        @Override
        public void onSaveInstanceState(FragmentManager fm, Fragment f, Bundle outState) {
            listen.onSaveInstanceState.onSaveInstanceState(fm, f, outState);
        }

        @Override
        public void onViewDestroy(FragmentManager fm, Fragment f) {
            listen.onViewDestroy.onViewDestroy(fm, f);
        }

        @Override
        public void onDestroy(FragmentManager fm, Fragment f) {
            listen.onDestroy.onDestroy(fm, f);
        }

        @Override
        public void onDetach(FragmentManager fm, Fragment f) {
            listen.onDetach.onDetach(fm, f);
        }
    }
}
