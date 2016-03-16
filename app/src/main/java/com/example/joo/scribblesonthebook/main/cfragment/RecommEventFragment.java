package com.example.joo.scribblesonthebook.main.cfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.data.vo.EventInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommEventFragment extends Fragment {

    public static final String EVENT_INFO_KEY = "eventInfo";

    public static RecommEventFragment newInstance (EventInfo eventInfo) {
        RecommEventFragment fragment = new RecommEventFragment();
        Bundle b = new Bundle();
        b.putSerializable(EVENT_INFO_KEY, eventInfo);
        fragment.setArguments(b);
        return fragment;
    }

    public RecommEventFragment() {
        // Required empty public constructor
    }

    EventInfo eventInfo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        if (b != null) {
            eventInfo = (EventInfo) b.getSerializable(EVENT_INFO_KEY);
        }
    }

    ImageView imageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recomm_event, container, false);
        imageView = (ImageView) view.findViewById(R.id.image_recomm_event);
        Glide.with(getContext()).load(eventInfo.getProgramImage()).into(imageView);
        return view;
    }

}
