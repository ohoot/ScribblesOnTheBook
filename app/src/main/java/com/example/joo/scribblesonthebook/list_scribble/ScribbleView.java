package com.example.joo.scribblesonthebook.list_scribble;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joo.scribblesonthebook.R;

/**
 * Created by Joo on 2016-03-08.
 */
public class ScribbleView extends FrameLayout {
    public ScribbleView(Context context) {
        super(context);
        init();
    }

    ImageView pictureView, heartView, optionView;
    TextView userNickView, dateView, pageView, likeView, contentView;

    private void init() {
        inflate(getContext(), R.layout.view_scribble, this);
        pictureView = (ImageView) findViewById(R.id.image_scribble_writer);
        heartView = (ImageView) findViewById(R.id.image_heart);
        optionView = (ImageView) findViewById(R.id.image_triangle_icon);
        userNickView = (TextView) findViewById(R.id.text_scribble_writer);
        dateView = (TextView) findViewById(R.id.text_scribble_date);
        pageView = (TextView) findViewById(R.id.text_scribble_bookmark);
        likeView = (TextView) findViewById(R.id.text_like);
        contentView = (TextView) findViewById(R.id.text_scribble_content);
    }

    public void setScribbleView() {

    }
}
