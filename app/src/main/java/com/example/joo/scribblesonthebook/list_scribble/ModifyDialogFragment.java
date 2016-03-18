package com.example.joo.scribblesonthebook.list_scribble;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.joo.scribblesonthebook.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ModifyDialogFragment extends Fragment {

    TextView textModify, textDelete;

    public ModifyDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_modify_dialog, container, false);
        textDelete = (TextView) view.findViewById(R.id.text_scribble_modify);
        textModify = (TextView) view.findViewById(R.id.text_scribble_delete);

        return view;
    }

}
