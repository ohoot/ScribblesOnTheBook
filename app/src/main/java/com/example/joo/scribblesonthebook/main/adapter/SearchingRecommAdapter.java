package com.example.joo.scribblesonthebook.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.main.cfragment.SearchingChildFragment;

/**
 * Created by Joo on 2016-02-29.
 */
public class SearchingRecommAdapter extends FragmentStatePagerAdapter {
    public SearchingRecommAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return SearchingChildFragment.newInstance(R.drawable.dummy_bookcover03);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public float getPageWidth(int position) {
        return 0.85f;
    }
}
