package com.example.joo.scribblesonthebook.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.main.adapter.SearchingRecommAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchRecommFragment extends Fragment {


    public SearchRecommFragment() {
        // Required empty public constructor
    }

    ViewPager viewPager;
    SearchingRecommAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_recomm, container, false);
        mAdapter = new SearchingRecommAdapter(getChildFragmentManager());
        viewPager = (ViewPager) view.findViewById(R.id.searchingRecomm_pager);
        viewPager.setAdapter(mAdapter);
        return view;
    }

}
