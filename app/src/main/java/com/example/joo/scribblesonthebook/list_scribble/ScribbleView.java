package com.example.joo.scribblesonthebook.list_scribble;

import android.content.Context;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.data.manager.NetworkManager;
import com.example.joo.scribblesonthebook.data.vo.Scribble;

import java.util.List;

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
        heartView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHeartClickListener != null) {
                    mHeartClickListener.onHeartClick(mScribble);
                }
            }
        });
        optionView = (ImageView) findViewById(R.id.image_triangle_icon);
        optionView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOptionListener != null) {
                    mOptionListener.onOptionTriangleClick(mScribble);
                }
            }
        });
        userNickView = (TextView) findViewById(R.id.text_scribble_writer);
        dateView = (TextView) findViewById(R.id.text_scribble_date);
        pageView = (TextView) findViewById(R.id.text_scribble_bookmark);
        likeView = (TextView) findViewById(R.id.text_like);
        contentView = (TextView) findViewById(R.id.text_scribble_content);

    }

    Scribble mScribble;
    public void setScribbleView(ScribbleGroup scribbleGroup) {
        mScribble = scribbleGroup.myScribble;
        userNickView.setText(scribbleGroup.myScribble.getNickName().toString());
        dateView.setText(scribbleGroup.myScribble.getScribbleDate().toString());
        pageView.setText(scribbleGroup.myScribble.getPage() + "");
        likeView.setText(scribbleGroup.myScribble.getLike() + "");
        contentView.setText(scribbleGroup.myScribble.getContent().toString());
        Glide.with(getContext()).load(scribbleGroup.myScribble.getUserPhoto()).into(pictureView);

    }

    public void setScribbleViewChild(Scribble scribble) {
        mScribble = scribble;
        pictureView.setVisibility(GONE);
        optionView.setVisibility(GONE);
        userNickView.setVisibility(GONE);

        dateView.setText(scribble.getScribbleDate().toString());
        pageView.setText(scribble.getPage() + "");
        likeView.setText(scribble.getLike() + "");
        contentView.setText(scribble.getContent().toString());
    }

    public void setDetailMyScribbles(Scribble scribble) {
        userNickView.setText(scribble.getNickName().toString());
        dateView.setText(scribble.getScribbleDate().toString());
        pageView.setText(scribble.getPage() + "");
        likeView.setText(scribble.getLike() + "");
        contentView.setText(scribble.getContent().toString());
        Glide.with(getContext()).load(scribble.getUserPhoto()).into(pictureView);
    }

    public interface OnHeartClickListener {
        public void onHeartClick(Scribble scribble);
    }

    OnHeartClickListener mHeartClickListener;

    public void setOnHeartClickListener(OnHeartClickListener listener) {
        mHeartClickListener = listener;
    }

    public interface OnOptionTriangleClickListener {
        public void onOptionTriangleClick(Scribble scribble);
    }

    private OnOptionTriangleClickListener mOptionListener;

    public void setOnOptionTriangleClickListener(OnOptionTriangleClickListener listener) {
        mOptionListener = listener;
    }
}
