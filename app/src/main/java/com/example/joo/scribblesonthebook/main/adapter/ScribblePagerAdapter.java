package com.example.joo.scribblesonthebook.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.data.vo.BookData;
import com.example.joo.scribblesonthebook.main.cfragment.ScribbleChildFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joo on 2016-02-25.
 */
public class ScribblePagerAdapter extends FragmentStatePagerAdapter {
    List<BookData> items = new ArrayList<BookData>();

    public ScribblePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ScribbleChildFragment.newInstance(items.get(position).getCoverImage());
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public float getPageWidth(int position) {
        return 0.93f;
    }

    public void addAll(ArrayList<BookData> tenseList) {
        items.addAll(tenseList);
        notifyDataSetChanged();
    }

    public void clearAll() {
        items.clear();
        notifyDataSetChanged();
    }
}
