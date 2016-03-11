package com.example.joo.scribblesonthebook.main.cfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.joo.scribblesonthebook.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScribbleChildFragment extends Fragment {

    public static final String COVER_URL = "coverUrl";

    public static ScribbleChildFragment newInstance(String coverUrl) {
        ScribbleChildFragment f = new ScribbleChildFragment();
        Bundle b = new Bundle();
        b.putString(COVER_URL, coverUrl);
        f.setArguments(b);
        return f;
    }

    public ScribbleChildFragment() {
        // Required empty public constructor
    }

    String coverUrl;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        if (arg != null) {
            coverUrl = arg.getString(COVER_URL);
        }
    }

    ImageView imageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scribble_child, container, false);
        imageView = (ImageView) view.findViewById(R.id.image_scribble_child);
        Glide.with(getContext()).load(coverUrl).into(imageView);

        return view;
    }

}
