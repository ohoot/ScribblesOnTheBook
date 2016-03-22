package com.example.joo.scribblesonthebook.book_detail;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.data.vo.BookData;
import com.example.joo.scribblesonthebook.main.cfragment.ScribbleChildFragment;

public class DetailBookInfoActivity extends AppCompatActivity {

    ImageView coverView;
    BookData bookData;
    boolean isClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_book_info);
        Intent intent = getIntent();
        bookData = (BookData) intent.getSerializableExtra(ScribbleChildFragment.BOOK_DATA_TAG);
        coverView = (ImageView) findViewById(R.id.image_book_detail_cover);
        coverView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClicked) {
                    DetailInfoFragment fragment = new DetailInfoFragment();
                    Bundle b = new Bundle();
                    b.putSerializable(DetailInfoFragment.CLICKED_BOOK_DATA, bookData);
                    fragment.setArguments(b);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.add(R.id.image_container_detail, fragment);
                    ft.commit();
                    isClicked = true;
                } else {
                    DetailBlankFragment fragment = new DetailBlankFragment();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.image_container_detail, fragment);
                    ft.commit();
                    isClicked = false;
                }
            }
        });
        Glide.with(DetailBookInfoActivity.this).load(bookData.getCoverImage()).into(coverView);
    }
}
