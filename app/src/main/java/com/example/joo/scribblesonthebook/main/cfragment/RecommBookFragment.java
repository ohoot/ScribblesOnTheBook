package com.example.joo.scribblesonthebook.main.cfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.data.vo.BookData;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommBookFragment extends Fragment {

    public static final String RECOMM_BOOK_KEY = "recommBook";

    public static RecommBookFragment newInstance(BookData recommendBook) {
        RecommBookFragment fragment = new RecommBookFragment();
        Bundle b = new Bundle();
        b.putSerializable(RECOMM_BOOK_KEY, recommendBook);
        fragment.setArguments(b);
        return fragment;
    }


    public RecommBookFragment() {
        // Required empty public constructor
    }

    BookData bookData;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        if (b != null) {
            bookData = (BookData) b.getSerializable(RECOMM_BOOK_KEY);
        }
    }

    ImageView imageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recomm_book, container, false);
        imageView = (ImageView) view.findViewById(R.id.image_recomm_book);
        Glide.with(getContext()).load(bookData.getCoverImage()).into(imageView);
        return view;
    }

}
