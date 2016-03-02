package com.example.joo.scribblesonthebook.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.main.adapter.DefaultPagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class DefaultFragment extends Fragment {


    public DefaultFragment() {
        // Required empty public constructor
    }

    Spinner spinner;
    ViewPager viewPager;
    ArrayAdapter<String> mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_default, container, false);
        spinner = (Spinner) view.findViewById(R.id.spinner_default);
        mAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(mAdapter);
        viewPager = (ViewPager) view.findViewById(R.id.default_pager);
        viewPager.setAdapter(new DefaultPagerAdapter(getChildFragmentManager()));
        initSpinner();
        return view;
    }

    private void initSpinner() {
        mAdapter.add(getResources().getString(R.string.book_reading));
        mAdapter.add(getResources().getString(R.string.book_done));
        mAdapter.add(getResources().getString(R.string.book_will));
    }

}
