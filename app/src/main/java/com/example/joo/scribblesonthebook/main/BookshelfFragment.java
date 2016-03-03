package com.example.joo.scribblesonthebook.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.main.adapter.BookshelfPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookshelfFragment extends Fragment {

    TextView monthlyPage;
    ViewPager viewPager;
    ImageView monthltPageText;

    public BookshelfFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // commit test
        View view = inflater.inflate(R.layout.fragment_bookshelf, container, false);
        monthlyPage = (TextView) view.findViewById(R.id.text_monthly_page);
        viewPager = (ViewPager) view.findViewById(R.id.bookshelf_pager);
        viewPager.setAdapter(new BookshelfPagerAdapter(getChildFragmentManager()));
        monthltPageText = (ImageView) view.findViewById(R.id.image_monthly_page);

        return view;
    }
}
