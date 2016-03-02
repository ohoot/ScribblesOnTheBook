package com.example.joo.scribblesonthebook.signup;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.joo.scribblesonthebook.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserInfoTermsFragment extends Fragment {


    public UserInfoTermsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_info_terms, container, false);
        Button btn = (Button) view.findViewById(R.id.btn_signup_cancel);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        btn = (Button) view.findViewById(R.id.btn_signup_next);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new RegisterProfileFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.signup_container, fragment);
                ft.addToBackStack("registerProfile");
                ft.commit();
            }
        });
        return view;
    }

}
