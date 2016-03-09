package com.example.joo.scribblesonthebook.main;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.data.vo.BookData;

/**
 * Created by Joo on 2016-03-09.
 */
public class SearchingResultView extends FrameLayout {
    public SearchingResultView(Context context) {
        super(context);
        init();
    }

    ImageView coverView, triangleView;
    TextView titleView, authorView;

    private void init() {
        inflate(getContext(), R.layout.view_searching_result, this);
        coverView = (ImageView) findViewById(R.id.image_searching_result);
        triangleView = (ImageView) findViewById(R.id.image_searching_triangle);
        titleView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Test...", Toast.LENGTH_SHORT).show();
            }
        });
        titleView = (TextView) findViewById(R.id.text_searching_title);
        authorView = (TextView) findViewById(R.id.text_searching_author);
    }


    public void setSearchingResultView(BookData bookData) {
        titleView.setText(bookData.getTitle());
        authorView.setText(bookData.getAuthor());
    }
}
