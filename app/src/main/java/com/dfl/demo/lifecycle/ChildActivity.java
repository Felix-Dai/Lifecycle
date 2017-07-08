package com.dfl.demo.lifecycle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.dfl.lib.lifecycle.Lifecycle;
import com.dfl.lib.lifecycle.util.Logger;


/**
 * Created by felix.dai on 2017/5/2.
 */

public class ChildActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Lifecycle.fragment().onAll().on(getSupportFragmentManager(), true).with(Logger.LOG_FRAGMENT).listen();
        setContentView(R.layout.activity_child);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new Adapter(getSupportFragmentManager()));
    }

    private class Adapter extends FragmentPagerAdapter {

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ChildFragment fragment = new ChildFragment();
            fragment.setIndex(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }
    }
}
