package com.dfl.lib.lifecycle.interface_;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

/**
 * Created by felix.dai on 2017/4/24.
 */

public class FRAGMENTS {
    
    public interface OnPreAttach {
        void onPreAttach(FragmentManager fm, Fragment f, Context context);
    }
    
    public interface OnAttach {
        void onAttach(FragmentManager fm, Fragment f, Context context);
    }
    
    public interface OnCreate {
        void onCreate(FragmentManager fm, Fragment f, Bundle savedInstanceState);
    }
    
    public interface OnActivityCreate {
        void onActivityCreate(FragmentManager fm, Fragment f, Bundle savedInstanceState);
    }
    
    public interface OnViewCreate {
        void onViewCreate(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState);
    }
    
    public interface OnStart {
        void onStart(FragmentManager fm, Fragment f);
    }
    
    public interface OnResume {
        void onResume(FragmentManager fm, Fragment f);
    }
    
    public interface OnPause {
        void onPause(FragmentManager fm, Fragment f);
    }
    
    public interface OnStop {
        void onStop(FragmentManager fm, Fragment f);
    }
    
    public interface OnSaveInstanceState {
        void onSaveInstanceState(FragmentManager fm, Fragment f, Bundle outState);
    }
    
    public interface OnViewDestroy {
        void onViewDestroy(FragmentManager fm, Fragment f);
    }

    public interface OnDestroy {
        void onDestroy(FragmentManager fm, Fragment f);
    }

    public interface OnDetach {
        void onDetach(FragmentManager fm, Fragment f);
    }

    public interface All extends OnPreAttach, OnAttach, OnCreate, OnActivityCreate, OnViewCreate,
            OnStart, OnResume, OnPause, OnStop, OnSaveInstanceState, OnViewDestroy, OnDestroy,
            OnDetach {}

    public static final All DEFAULT_ALL = new All() {
        @Override
        public void onActivityCreate(FragmentManager fm, Fragment f, Bundle savedInstanceState) {}

        @Override
        public void onAttach(FragmentManager fm, Fragment f, Context context) {}

        @Override
        public void onCreate(FragmentManager fm, Fragment f, Bundle savedInstanceState) {}

        @Override
        public void onDestroy(FragmentManager fm, Fragment f) {}

        @Override
        public void onDetach(FragmentManager fm, Fragment f) {}

        @Override
        public void onPause(FragmentManager fm, Fragment f) {}

        @Override
        public void onPreAttach(FragmentManager fm, Fragment f, Context context) {}

        @Override
        public void onResume(FragmentManager fm, Fragment f) {}

        @Override
        public void onSaveInstanceState(FragmentManager fm, Fragment f, Bundle outState) {}

        @Override
        public void onStart(FragmentManager fm, Fragment f) {}

        @Override
        public void onStop(FragmentManager fm, Fragment f) {}

        @Override
        public void onViewCreate(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {}

        @Override
        public void onViewDestroy(FragmentManager fm, Fragment f) {}
    };
}
