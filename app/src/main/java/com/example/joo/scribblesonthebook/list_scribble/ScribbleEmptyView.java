package com.example.joo.scribblesonthebook.list_scribble;

import android.content.Context;
import android.widget.FrameLayout;

import com.example.joo.scribblesonthebook.R;

/**
 * Created by Joo on 2016-03-09.
 */
public class ScribbleEmptyView extends FrameLayout{
    public ScribbleEmptyView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_empty, this);
    }
}
