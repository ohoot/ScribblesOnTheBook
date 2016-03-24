package com.example.joo.scribblesonthebook.find_password;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.joo.scribblesonthebook.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindPasswordKeyFragment extends Fragment {

    public static final String CHANGE_PASSWORD_BACKSTACK = "changePwd";

    public FindPasswordKeyFragment() {
        // Required empty public constructor
    }

    Button btnNext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find_password_key, container, false);
        btnNext = (Button) view.findViewById(R.id.btn_change_password);
        //btnNext.setEnabled(false);
        Button btn = (Button) view.findViewById(R.id.btn_confirm_key_cancel);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "구현 예정...", Toast.LENGTH_SHORT).show();
                /*Fragment fragment = new ChangePasswordFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.find_password_container, fragment);
                ft.addToBackStack(CHANGE_PASSWORD_BACKSTACK);
                ft.commit();*/
            }
        });
        return view;
    }

}
