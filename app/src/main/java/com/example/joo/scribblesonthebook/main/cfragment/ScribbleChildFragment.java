package com.example.joo.scribblesonthebook.main.cfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.main.ScribbleFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScribbleChildFragment extends Fragment {

    public static final String IMAGE_RESOURCE = "res";

    public static ScribbleChildFragment newInstance(int res) {
        ScribbleChildFragment f = new ScribbleChildFragment();
        Bundle b = new Bundle();
        b.putInt(IMAGE_RESOURCE, res);
        f.setArguments(b);
        return f;
    }

    public ScribbleChildFragment() {
        // Required empty public constructor
    }

    int res;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        if (arg != null) {
            res = arg.getInt(IMAGE_RESOURCE);
        }
    }

    ImageView imageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scribble_child, container, false);
        imageView = (ImageView) view.findViewById(R.id.image_scribble_child);
        imageView.setImageResource(res);
        return view;
    }

}
