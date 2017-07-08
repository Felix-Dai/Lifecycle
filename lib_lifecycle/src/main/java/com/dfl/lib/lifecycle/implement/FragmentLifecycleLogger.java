package com.dfl.lib.lifecycle.implement;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.dfl.lib.lifecycle.interface_.FRAGMENTS;
import com.dfl.lib.lifecycle.util.Logger;

/**
 * Created by felix.dai on 2017/4/24.
 */

public class FragmentLifecycleLogger implements FRAGMENTS.All {

    private FRAGMENTS.All lifecycle;

    public FragmentLifecycleLogger(FRAGMENTS.All lifecycle) {
        this.lifecycle = lifecycle;
    }

    @Override
    public void onPreAttach(FragmentManager fm, Fragment f, Context context) {
        lifecycle.onPreAttach(fm, f, context);
        Logger.i(fm + ":" + f + " ## " + "on pre-attach ## "  + context);
    }

    @Override
    public void onAttach(FragmentManager fm, Fragment f, Context context) {
        lifecycle.onAttach(fm, f, context);
        Logger.i(fm + ":" + f + " ## " + "on attach ## "  + context);
    }

    @Override
    public void onCreate(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        lifecycle.onCreate(fm, f, savedInstanceState);
        Logger.i(fm + ":" + f + " ## " + "on create ## "  + savedInstanceState);
    }

    @Override
    public void onActivityCreate(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        lifecycle.onActivityCreate(fm, f, savedInstanceState);
        Logger.i(fm + ":" + f + " ## " + "on activity create ## "  + savedInstanceState);
    }

    @Override
    public void onViewCreate(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
        lifecycle.onViewCreate(fm, f, v, savedInstanceState);
        Logger.i(fm + ":" + f + " ## " + "on view create ## "  + savedInstanceState);
    }

    @Override
    public void onStart(FragmentManager fm, Fragment f) {
        lifecycle.onStart(fm, f);
        Logger.i(fm + ":" + f + " ## " + "on start ## ");
    }

    @Override
    public void onResume(FragmentManager fm, Fragment f) {
        lifecycle.onResume(fm, f);
        Logger.i(fm + ":" + f + " ## " + "on resume ## ");
    }

    @Override
    public void onPause(FragmentManager fm, Fragment f) {
        lifecycle.onPause(fm, f);
        Logger.i(fm + ":" + f + " ## " + "on pause ## ");
    }

    @Override
    public void onStop(FragmentManager fm, Fragment f) {
        lifecycle.onStop(fm, f);
        Logger.i(fm + ":" + f + " ## " + "on stop ## ");
    }

    @Override
    public void onSaveInstanceState(FragmentManager fm, Fragment f, Bundle outState) {
        lifecycle.onSaveInstanceState(fm, f, outState);
        Logger.i(fm + ":" + f + " ## " + "on save instance state ## ");
    }

    @Override
    public void onViewDestroy(FragmentManager fm, Fragment f) {
        lifecycle.onViewDestroy(fm, f);
        Logger.i(fm + ":" + f + " ## " + "on view destroy ## ");
    }

    @Override
    public void onDestroy(FragmentManager fm, Fragment f) {
        lifecycle.onDestroy(fm, f);
        Logger.i(fm + ":" + f + " ## " + "on destroy ## ");
    }

    @Override
    public void onDetach(FragmentManager fm, Fragment f) {
        lifecycle.onDetach(fm, f);
        Logger.i(fm + ":" + f + " ## " + "on detach ## ");
    }
}
