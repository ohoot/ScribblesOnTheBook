package com.example.joo.scribblesonthebook.main.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.joo.scribblesonthebook.data.vo.BookData;
import com.example.joo.scribblesonthebook.main.SearchingResultView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joo on 2016-03-09.
 */
public class SearchingResultAdapter extends BaseAdapter {
    List<BookData> items = new ArrayList<BookData>();
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
        SearchingResultView view;
        if (convertView == null || !(convertView instanceof SearchingResultView)) {
            view = new SearchingResultView(parent.getContext());
        } else {
            view = (SearchingResultView) convertView;
        }
        view.setSearchingResultView(items.get(position));
        return view;
    }

    public void addAll(ArrayList<BookData> searchList) {
        items.addAll(searchList);
        notifyDataSetChanged();
    }
}
