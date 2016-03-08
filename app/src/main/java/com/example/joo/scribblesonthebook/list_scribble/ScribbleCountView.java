package com.example.joo.scribblesonthebook.list_scribble;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.joo.scribblesonthebook.R;

/**
 * Created by Joo on 2016-03-08.
 */
public class ScribbleCountView extends FrameLayout{
    public ScribbleCountView(Context context) {
        super(context);
        init();
    }

    TextView countView;
    private void init() {
        inflate(getContext(), R.layout.view_other_scribble_count, this);
        countView = (TextView) findViewById(R.id.text_scribble_count);
    }

    public void setCountView() {

    }
}
