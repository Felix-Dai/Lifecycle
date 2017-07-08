package com.dfl.demo.lifecycle;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.dfl.lib.lifecycle.Lifecycle;
import com.dfl.lib.lifecycle.util.Logger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Lifecycle.fragment(ChildFragment.class).with(Logger.LOG_FRAGMENT).listen();
        Lifecycle.fragment().onAll().with(Logger.LOG_FRAGMENT).on(getSupportFragmentManager(), true).listen();
        setContentView(R.layout.activity_main);
        MainFragment fragment = (MainFragment) getSupportFragmentManager().findFragmentByTag("Main");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fragment == null) {
            fragment = new MainFragment();
            transaction.add(R.id.container, fragment, "Main");
        }
        transaction.show(fragment).commit();
    }
}
