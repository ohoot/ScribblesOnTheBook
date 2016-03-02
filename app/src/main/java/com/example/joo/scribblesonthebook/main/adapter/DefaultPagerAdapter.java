package com.example.joo.scribblesonthebook.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.main.cfragment.DefaultChildFragment;

/**
 * Created by Joo on 2016-02-24.
 */
public class DefaultPagerAdapter extends FragmentStatePagerAdapter {
    public DefaultPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return DefaultChildFragment.newInstance(R.drawable.btn_bookplus);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public float getPageWidth(int position) {
        return 0.93f;
    }
}
