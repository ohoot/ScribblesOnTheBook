package com.example.joo.scribblesonthebook.main.cfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.data.vo.CommunityInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommCommunityFragment extends Fragment {

    public static final String RECOMM_COMMUNITY_KEY = "recommCommunity";

    public RecommCommunityFragment() {
        // Required empty public constructor
    }

    ImageView imageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recomm_community, container, false);
        imageView = (ImageView) view.findViewById(R.id.image_recomm_community);
        Glide.with(getContext()).load(communityInfo.getCommunityImage()).into(imageView);
        return view;
    }

    public static RecommCommunityFragment newInstance(CommunityInfo recommendClub) {
        RecommCommunityFragment fragment = new RecommCommunityFragment();
        Bundle b = new Bundle();
        b.putSerializable(RECOMM_COMMUNITY_KEY, recommendClub);
        fragment.setArguments(b);
        return fragment;
    }

    CommunityInfo communityInfo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        if (b != null) {
            communityInfo = (CommunityInfo) b.getSerializable(RECOMM_COMMUNITY_KEY);
        }
    }
}
