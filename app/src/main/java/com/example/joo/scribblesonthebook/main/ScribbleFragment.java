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
import com.example.joo.scribblesonthebook.main.adapter.ScribblePagerAdapter;

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
        viewPager.setAdapter(new ScribblePagerAdapter(getChildFragmentManager()));

        initSpinner();
        return view;
    }

    private void initSpinner() {
        mApdater.add(getResources().getString(R.string.book_reading));
        mApdater.add(getResources().getString(R.string.book_done));
        mApdater.add(getResources().getString(R.string.book_will));
    }

}
