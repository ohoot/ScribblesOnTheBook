package com.example.joo.scribblesonthebook.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.data.RecommendationSuccess;
import com.example.joo.scribblesonthebook.data.vo.RecommData;
import com.example.joo.scribblesonthebook.main.cfragment.RecommBookFragment;
import com.example.joo.scribblesonthebook.main.cfragment.RecommCommunityFragment;
import com.example.joo.scribblesonthebook.main.cfragment.RecommEventFragment;
import com.example.joo.scribblesonthebook.main.cfragment.SearchingChildFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joo on 2016-02-29.
 */
public class SearchingRecommAdapter extends FragmentStatePagerAdapter {
//    List<RecommData> items = new ArrayList<RecommData>();

    RecommendationSuccess result;

    public SearchingRecommAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        if (position == 0) {
            fragment = RecommBookFragment.newInstance(result.recommendBook);
        } else if (position == 1) {
            fragment = RecommEventFragment.newInstance(result.recommendProgram);
        } else if (position == 2) {
            fragment = RecommCommunityFragment.newInstance(result.recommendClub);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        if (result == null) return 0;
        return 3;
    }

    @Override
    public float getPageWidth(int position) {
        return 0.85f;
    }

    public void addAll(RecommendationSuccess result) {
        this.result = result;
        notifyDataSetChanged();
    }
}
