package com.example.joo.scribblesonthebook.main.cfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.joo.scribblesonthebook.Events.SearchingIconClickEvent;
import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.main.MainActivity;
import com.squareup.otto.Bus;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddBookFragment extends Fragment {


    public AddBookFragment() {
        // Required empty public constructor
    }

    Bus bus;
    ImageView searchView, barcodeView;

    @Override
    public void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);
        bus = new Bus();
        searchView = (ImageView) view.findViewById(R.id.image_addbook_searching);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When click the searching icon.
                //getActivity().findViewById(R.id.tabhost).findViewWithTag(MainActivity.TABSPEC_SEARCHING_RECOMM).performClick();
                bus.post(new SearchingIconClickEvent());
            }
        });
        barcodeView = (ImageView) view.findViewById(R.id.image_addbook_barcode);
        barcodeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // When click the barcode icon.
            }
        });
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        bus.unregister(this);
    }
}
