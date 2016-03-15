package com.example.joo.scribblesonthebook.find_password;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.data.ReferScribbleRecordSuccess;
import com.example.joo.scribblesonthebook.data.manager.NetworkManager;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindPasswordEmailFragment extends Fragment {

    public static final String KEY_BACKSTACK = "key";

    public FindPasswordEmailFragment() {
        // Required empty public constructor
    }

    EditText emailView;
    View view;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_find_password_email, container, false);
        emailView = (EditText) view.findViewById(R.id.edit_find_password_email);
        Button btn = (Button) view.findViewById(R.id.btn_find_password_cancel);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        btn = (Button) view.findViewById(R.id.btn_find_password_key_send);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FindPasswordKeyFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.find_password_container, fragment);
                ft.addToBackStack(KEY_BACKSTACK);
                ft.commit();
            }
        });
        return view;
    }


}
