package com.example.joo.scribblesonthebook.signup;

import android.content.Context;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joo.scribblesonthebook.R;

/**
 * Created by Joo on 2016-03-20.
 */
public class FilterContentView extends FrameLayout implements Checkable {
    public FilterContentView(Context context) {
        super(context);
        init();
    }

    ImageView imageCheck;
    TextView textFilter;

    private void init() {
        inflate(getContext(), R.layout.view_filter_content, this);
        imageCheck = (ImageView) findViewById(R.id.image_filter_check);
        textFilter = (TextView) findViewById(R.id.text_filter_content);

    }

    boolean isChecked = false;

    public void drawCheckBox() {
        if (isChecked) {
            imageCheck.setImageResource(R.drawable.btn_filter_pre);
        } else {
            imageCheck.setImageResource(R.drawable.btn_filter_nor);
        }
    }

    @Override
    public void setChecked(boolean checked) {
        if (isChecked != checked) {
            isChecked = checked;
            drawCheckBox();
        }
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        setChecked(!isChecked);
    }

    public void setText(String texts) {
        textFilter.setText(texts);
    }
}
