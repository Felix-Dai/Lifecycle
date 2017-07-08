package com.dfl.lib.lifecycle.interface_;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by felix.dai on 2017/4/19.
 */

public class ACTIVITIES {

    public interface OnCreate {
        void onCreate(Activity activity, Bundle savedInstanceState);
    }

    public interface OnStart {
        void onStart(Activity activity);
    }

    public interface OnResume {
        void onResume(Activity activity);
    }

    public interface OnPause {
        void onPause(Activity activity);
    }

    public interface OnStop {
        void onStop(Activity activity);
    }

    public interface OnSaveInstanceState {
        void onSaveInstanceState(Activity activity, Bundle outState);
    }

    public interface OnDestroy {
        void onDestroy(Activity activity);
    }

    public interface All extends OnCreate, OnStart, OnResume, OnPause, OnStop, OnSaveInstanceState,
            OnDestroy {}

    public static final All DEFAULT_ALL = new All() {
        @Override
        public void onCreate(Activity activity, Bundle savedInstanceState) {}

        @Override
        public void onDestroy(Activity activity) {}

        @Override
        public void onPause(Activity activity) {}

        @Override
        public void onResume(Activity activity) {}

        @Override
        public void onSaveInstanceState(Activity activity, Bundle outState) {}

        @Override
        public void onStart(Activity activity) {}

        @Override
        public void onStop(Activity activity) {}
    };
}
