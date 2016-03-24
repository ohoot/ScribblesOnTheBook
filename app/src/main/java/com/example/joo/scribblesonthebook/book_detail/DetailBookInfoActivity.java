package com.example.joo.scribblesonthebook.book_detail;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.data.ReferScribbleRecordSuccess;
import com.example.joo.scribblesonthebook.data.manager.NetworkManager;
import com.example.joo.scribblesonthebook.data.manager.UserPropertyManager;
import com.example.joo.scribblesonthebook.data.vo.BookData;
import com.example.joo.scribblesonthebook.data.vo.Scribble;
import com.example.joo.scribblesonthebook.list_scribble.ScribbleListAdapter;
import com.example.joo.scribblesonthebook.main.cfragment.ScribbleChildFragment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class DetailBookInfoActivity extends AppCompatActivity {

    ImageView coverView;
    BookData bookData;
    boolean isClicked = false;
    ListView scribbleList;
    MyScribbleListAdapter mAdapter;
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

        mAdapter = new MyScribbleListAdapter();
        scribbleList = (ListView) findViewById(R.id.listView_detail_book_scribbles);
        try {
            NetworkManager.getInstance().getScribbleList(DetailBookInfoActivity.this, bookData.getIsbn(), "" + 1, new NetworkManager.OnResultListener<ReferScribbleRecordSuccess>() {
                @Override
                public void onSuccess(Request request, ReferScribbleRecordSuccess result) {
                    mAdapter.addScribbles(convertScribbleList(result.doodleList));
                }

                @Override
                public void onFailure(Request request, int code, Throwable cause) {

                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        scribbleList.setAdapter(mAdapter);
    }

    private List<Scribble> convertScribbleList(ArrayList<Scribble> doodleList) {
        List<Scribble> items = new ArrayList<Scribble>();
        for (Scribble s : doodleList) {
            if (UserPropertyManager.getInstance().userId == s.getWriterId()) {
                items.add(s);
            }
        }
        return items;
    }
}
