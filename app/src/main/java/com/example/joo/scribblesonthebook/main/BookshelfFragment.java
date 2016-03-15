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
import com.example.joo.scribblesonthebook.data.BookListSuccess;
import com.example.joo.scribblesonthebook.data.MonthlyReadingSuccess;
import com.example.joo.scribblesonthebook.data.manager.NetworkManager;
import com.example.joo.scribblesonthebook.main.adapter.BookshelfPagerAdapter;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookshelfFragment extends Fragment {

    TextView monthlyPage, monthValueView;
    ViewPager viewPager;
    ImageView monthlyPageText;
    BookshelfPagerAdapter mAdapter;

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
        mAdapter = new BookshelfPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(mAdapter);
        monthlyPageText = (ImageView) view.findViewById(R.id.image_monthly_page);
        monthValueView = (TextView) view.findViewById(R.id.text_month_value);

        int month = Integer.parseInt(monthValueView.getText().toString());
        try {
            NetworkManager.getInstance().getMonthlyReading(getContext(), Calendar.getInstance().get(Calendar.YEAR) + "", month + "", new NetworkManager.OnResultListener<MonthlyReadingSuccess>() {
                @Override
                public void onSuccess(Request request, MonthlyReadingSuccess result) {
                    monthlyPage.setText(result.monthlyPage + "");
                }

                @Override
                public void onFailure(Request request, int code, Throwable cause) {

                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            NetworkManager.getInstance().getBookList(getContext(), 0 + "", 4 + "", new NetworkManager.OnResultListener<BookListSuccess>() {
                @Override
                public void onSuccess(Request request, BookListSuccess result) {
                    mAdapter.addAll(result.tenseList);
                }

                @Override
                public void onFailure(Request request, int code, Throwable cause) {

                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return view;
    }
}
