package com.dfl.demo.lifecycle;

import android.app.Application;

import com.dfl.lib.lifecycle.Lifecycle;
import com.dfl.lib.lifecycle.util.Logger;

/**
 * Created by felix.dai on 2017/4/20.
 */

public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Lifecycle.init(this);
        Lifecycle.activity().onAll().with(Logger.LOG_ACTIVITY).listen();
        Lifecycle.fragment().onAll().with(Logger.LOG_FRAGMENT).listen();
    }
}
