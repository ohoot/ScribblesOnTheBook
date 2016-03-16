package com.example.joo.scribblesonthebook.main.cfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.joo.scribblesonthebook.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DefaultChildFragment extends Fragment {

    public static final String IMAGE_RESOURCE_DEFAULT = "resDefault";

    public static DefaultChildFragment newInstance(int res) {
        DefaultChildFragment f = new DefaultChildFragment();
        Bundle b = new Bundle();
        b.putInt(IMAGE_RESOURCE_DEFAULT, res);
        f.setArguments(b);
        return f;
    }

    public DefaultChildFragment() {
        // Required empty public constructor
    }

    int res;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        if (arg != null) {
            res = arg.getInt(IMAGE_RESOURCE_DEFAULT);
        }
    }
    ImageView defaultImageView;

    public static final String ADDBOOK_BACKSTACK = "addbook";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_default_child, container, false);
        defaultImageView = (ImageView) view.findViewById(R.id.image_default_child);
        defaultImageView.setImageResource(res);
        defaultImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AddBookFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.add(R.id.addbook_view_container, fragment);
                ft.addToBackStack(ADDBOOK_BACKSTACK);
                ft.commit();
            }
        });
        return view;
    }

}
