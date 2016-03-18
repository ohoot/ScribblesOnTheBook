package com.example.joo.scribblesonthebook.list_scribble;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joo.scribblesonthebook.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ModifyDialogFragment extends DialogFragment {

    TextView textModify, textDelete;

    public ModifyDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_modify_dialog, container, false);
        textModify = (TextView) view.findViewById(R.id.text_scribble_modify);
        textModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Modify", Toast.LENGTH_SHORT).show();
            }
        });
        textDelete = (TextView) view.findViewById(R.id.text_scribble_delete);
        textDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Delete", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int width = getResources().getDimensionPixelSize(R.dimen.scribble_list_dialog_width);
        int height = getResources().getDimensionPixelSize(R.dimen.scribble_list_dialog_height);
        getDialog().getWindow().setLayout(width, height);
    }
}
