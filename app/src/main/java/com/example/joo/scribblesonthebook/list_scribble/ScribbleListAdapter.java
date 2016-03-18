package com.example.joo.scribblesonthebook.list_scribble;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.example.joo.scribblesonthebook.data.vo.Scribble;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joo on 2016-03-08.
 */
public class ScribbleListAdapter extends BaseExpandableListAdapter implements ScribbleView.OnHeartClickListener {
    List<ScribbleGroup> items = new ArrayList<ScribbleGroup>();
    @Override
    public int getGroupCount() {
        return items.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return items.get(groupPosition);
    }

    @Override
    public int getGroupTypeCount() {
        return 2;
    }

    private static final int GROUP_TYPE_MY = 0;
    private static final int GROUP_TYPE_OTHER = 1;
    @Override
    public int getGroupType(int groupPosition) {
        if (items.get(groupPosition).type == ScribbleGroup.GROUP_TYPE_MY) {
            return GROUP_TYPE_MY;
        } else {
            return GROUP_TYPE_OTHER;
        }
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        switch (getGroupType(groupPosition)) {
            case GROUP_TYPE_MY :
                ScribbleView sView;
                if (convertView == null || !(convertView instanceof ScribbleView)) {
                    sView = new ScribbleView(parent.getContext());
                    sView.setOnHeartClickListener(this);
                } else {
                    sView = (ScribbleView) convertView;
                }
                sView.setScribbleView(items.get(groupPosition));
                return sView;
            case GROUP_TYPE_OTHER :
                ScribbleCountView cView;
                if (convertView == null || !(convertView instanceof ScribbleCountView)) {
                    cView = new ScribbleCountView(parent.getContext());
                } else {
                    cView = (ScribbleCountView) convertView;
                }

                if (isExpanded) {
                    ScribbleEmptyView eView = new ScribbleEmptyView(parent.getContext());
                    return eView;
                } else {
                    cView.setCountView(items.get(groupPosition).otherList.size());
                    return cView;
                }
            default: return null;
        }
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        // childview....
        ScribbleView view;
        if (convertView == null) {
            view = new ScribbleView(parent.getContext());
            view.setOnHeartClickListener(this);
        } else {
            view = (ScribbleView) convertView;
        }
        view.setScribbleViewChild(items.get(groupPosition).otherList.get(childPosition));
        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return items.get(groupPosition).otherList.size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return items.get(groupPosition).otherList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void addAll(List<ScribbleGroup> result) {
        this.items.addAll(result);
        notifyDataSetChanged();
    }

    @Override
    public void onHeartClick(Scribble scribble) {
        if (mAdapterHeartClickListener != null) {
            mAdapterHeartClickListener.receiveScribble(scribble);
        }
    }

    public interface OnAdapterHeartClickListener {
        public void receiveScribble(Scribble scribble);
    }

    private OnAdapterHeartClickListener mAdapterHeartClickListener;

    public void setOnAdapterHeartClickListener(OnAdapterHeartClickListener listener) {
        mAdapterHeartClickListener = listener;
    }

}
