package com.example.joo.scribblesonthebook.book_detail;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.joo.scribblesonthebook.data.vo.Scribble;
import com.example.joo.scribblesonthebook.list_scribble.ScribbleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joo on 2016-03-24.
 */
public class MyScribbleListAdapter extends BaseAdapter{
    List<Scribble> items = new ArrayList<Scribble>();

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
        ScribbleView view;
        if (convertView == null) {
            view = new ScribbleView(parent.getContext());
            view.setOnHeartClickListener(new ScribbleView.OnHeartClickListener() {
                @Override
                public void onHeartClick(Scribble scribble) {

                }
            });
            view.setOnOptionTriangleClickListener(new ScribbleView.OnOptionTriangleClickListener() {
                @Override
                public void onOptionTriangleClick(Scribble scribble) {

                }
            });
        } else {
            view = (ScribbleView) convertView;
        }
        view.setDetailMyScribbles(items.get(position));
        return view;
    }

    public void addScribbles(List<Scribble> scribbles) {
        items.addAll(scribbles);
        notifyDataSetChanged();
    }
}
