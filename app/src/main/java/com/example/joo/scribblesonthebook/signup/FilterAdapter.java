package com.example.joo.scribblesonthebook.signup;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joo on 2016-03-20.
 */
public class FilterAdapter extends BaseAdapter {
    List<String> items = new ArrayList<String>();

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FilterContentView view;
        if (convertView == null) {
            view = new FilterContentView(parent.getContext());
        } else {
            view = (FilterContentView) convertView;
        }
        view.setText(items.get(position));
        return view;
    }

    public void addFilterText(String[] filterContents) {
        for (String str : filterContents) {
            items.add(str);
        }
        notifyDataSetChanged();
    }
}
