package com.example.joo.scribblesonthebook.main.cfragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.book_detail.DetailBookInfoActivity;
import com.example.joo.scribblesonthebook.data.vo.BookData;

import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScribbleChildFragment extends Fragment {

    public static final String BOOK_DATA_TAG = "bookData";

    public static ScribbleChildFragment newInstance(BookData bookData) {
        ScribbleChildFragment f = new ScribbleChildFragment();
        Bundle b = new Bundle();
        b.putSerializable(BOOK_DATA_TAG, bookData);
        f.setArguments(b);
        return f;
    }

    public ScribbleChildFragment() {
        // Required empty public constructor
    }

    BookData bookData;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        if (arg != null) {
            bookData = (BookData) arg.getSerializable(BOOK_DATA_TAG);
        }
    }

    ImageView imageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scribble_child, container, false);
        imageView = (ImageView) view.findViewById(R.id.image_scribble_child);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailBookInfoActivity.class);
                intent.putExtra(BOOK_DATA_TAG, bookData);
                startActivity(intent);
            }
        });
        Glide.with(getContext()).load(bookData.getCoverImage()).into(imageView);

        return view;
    }

}
