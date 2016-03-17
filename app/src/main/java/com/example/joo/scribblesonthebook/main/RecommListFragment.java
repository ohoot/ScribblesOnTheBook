package com.example.joo.scribblesonthebook.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.data.RecommendationSuccess;
import com.example.joo.scribblesonthebook.data.manager.NetworkManager;
import com.example.joo.scribblesonthebook.main.adapter.SearchingRecommAdapter;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommListFragment extends Fragment {


    public RecommListFragment() {
        // Required empty public constructor
    }

    ViewPager viewPager;
    SearchingRecommAdapter mAdapter;
    TextView descView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recomm_list, container, false);
        descView = (TextView) view.findViewById(R.id.text_recomm);
        mAdapter = new SearchingRecommAdapter(getChildFragmentManager());
        viewPager = (ViewPager) view.findViewById(R.id.searchingRecomm_pager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    descView.setVisibility(View.VISIBLE);
                } else {
                    descView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(mAdapter);

        try {
            NetworkManager.getInstance().getRecommendations(getContext(), new NetworkManager.OnResultListener<RecommendationSuccess>() {
                @Override
                public void onSuccess(Request request, RecommendationSuccess result) {
                    mAdapter.addAll(result);
                    descView.setText(result.recommendClub.getCommunityExplan());
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
