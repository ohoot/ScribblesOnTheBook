package com.example.joo.scribblesonthebook.list_scribble;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joo on 2016-03-08.
 */
public class ScribbleListAdapter extends BaseExpandableListAdapter {
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
                return null;
            case GROUP_TYPE_OTHER :
                if (isExpanded) {

                } else {

                }
                return null;
        }
        return null;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        // childview....
        return null;
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

}
