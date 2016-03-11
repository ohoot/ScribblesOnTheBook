package com.example.joo.scribblesonthebook.main;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.data.BookListSuccess;
import com.example.joo.scribblesonthebook.data.manager.NetworkManager;
import com.example.joo.scribblesonthebook.data.vo.BookData;
import com.example.joo.scribblesonthebook.list_scribble.ScribbleListActivity;
import com.example.joo.scribblesonthebook.main.adapter.ScribblePagerAdapter;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScribbleFragment extends Fragment {

    public ScribbleFragment() {
        // Required empty public constructor
    }
    Spinner spinner;
    ViewPager viewPager;
    ArrayAdapter<String> mApdater;
    ImageView arrowView;
    ScribblePagerAdapter pAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scribble, container, false);
        spinner = (Spinner) view.findViewById(R.id.spinner_scribble);
        mApdater = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item);
        mApdater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(mApdater);
        viewPager = (ViewPager) view.findViewById(R.id.scribble_pager);
        pAdapter = new ScribblePagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pAdapter);
        arrowView = (ImageView) view.findViewById(R.id.image_swaping_arrow);
        arrowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ScribbleListActivity.class));
            }
        });
        initSpinner();

        try {
            NetworkManager.getInstance().getBookList(getContext(), "" + 0, "" + 4, new NetworkManager.OnResultListener<BookListSuccess>() {
                @Override
                public void onSuccess(Request request, BookListSuccess result) {
                    pAdapter.addAll(result.tenseList);
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

    private void initSpinner() {
        mApdater.add(getResources().getString(R.string.book_reading));
        mApdater.add(getResources().getString(R.string.book_done));
        mApdater.add(getResources().getString(R.string.book_will));
    }

}
