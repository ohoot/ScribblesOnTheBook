package com.example.joo.scribblesonthebook.main.cfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.data.vo.BookData;
import com.example.joo.scribblesonthebook.data.vo.CommunityInfo;
import com.example.joo.scribblesonthebook.data.vo.EventInfo;
import com.example.joo.scribblesonthebook.data.vo.RecommData;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchingChildFragment extends Fragment {

    public static final String RECOMM_DATA = "recommData";

    public static SearchingChildFragment newInstance(RecommData recommData) {
        SearchingChildFragment f = new SearchingChildFragment();
        Bundle b = new Bundle();
        if(recommData instanceof BookData) {
            b.putSerializable(RECOMM_DATA, (BookData) recommData);
        } else if (recommData instanceof EventInfo) {
            b.putSerializable(RECOMM_DATA, (EventInfo) recommData);
        } else if (recommData instanceof CommunityInfo) {
            b.putSerializable(RECOMM_DATA, (CommunityInfo) recommData);
        }
        f.setArguments(b);
        return f;
    }

    public SearchingChildFragment() {
        // Required empty public constructor
    }

    RecommData recommData;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        if (arg != null) {
            recommData = (RecommData) arg.getSerializable(RECOMM_DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_searching_child, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_searching_child);

        return view;
    }

}
