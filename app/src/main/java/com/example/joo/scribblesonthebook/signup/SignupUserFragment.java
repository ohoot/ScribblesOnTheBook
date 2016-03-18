package com.example.joo.scribblesonthebook.signup;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.data.manager.NetworkManager;
import com.example.joo.scribblesonthebook.data.vo.Success;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupUserFragment extends Fragment {

    public static final String REGISTER_PROFILE_BACKSTACK = "registerProfile";

    EditText userEmail, userPassword1, userPassword2;

    public SignupUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_info_terms, container, false);

        userEmail = (EditText) view.findViewById(R.id.edit_terms_email);
        userPassword1 = (EditText) view.findViewById(R.id.edit_terms_password1);
        userPassword2 = (EditText) view.findViewById(R.id.edit_terms_password2);

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
                RegisterProfileFragment fragment = new RegisterProfileFragment();
                Bundle b = new Bundle();
                b.putString(RegisterProfileFragment.EMAIL_KEY_VALUE, userEmail.getText().toString());
                b.putString(RegisterProfileFragment.PASSWORD1_KEY_VALUE, userPassword1.getText().toString());
                b.putString(RegisterProfileFragment.PASSWORD2_KEY_VALUE, userPassword2.getText().toString());
                fragment.setArguments(b);

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.signup_container, fragment);
                ft.addToBackStack(REGISTER_PROFILE_BACKSTACK);
                ft.commit();
            }
        });

        return view;
    }
}
