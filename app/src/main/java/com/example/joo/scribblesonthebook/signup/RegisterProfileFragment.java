package com.example.joo.scribblesonthebook.signup;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.joo.scribblesonthebook.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterProfileFragment extends Fragment {

    public static final String EMAIL_KEY_VALUE = "userEmail";
    public static final String PASSWORD1_KEY_VALUE = "password1";
    public static final String PASSWORD2_KEY_VALUE = "password2";
    public static final String FILTER_BACKSTACK = "filter";

    ImageView profileView;
    EditText nickView;

    public RegisterProfileFragment() {
        // Required empty public constructor
    }

    String email;
    String password1;
    String password2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        if (b != null) {
            email = b.getString(EMAIL_KEY_VALUE);
            password1 = b.getString(PASSWORD1_KEY_VALUE);
            password2 = b.getString(PASSWORD2_KEY_VALUE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_profile, container, false);
        profileView = (ImageView) view.findViewById(R.id.image_user_profile);
        profileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        nickView = (EditText) view.findViewById(R.id.edit_nick);

        Button btn = (Button) view.findViewById(R.id.btn_profile_cancel);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        btn = (Button) view.findViewById(R.id.btn_profile_next);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FilterFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.signup_container, fragment);
                ft.addToBackStack(FILTER_BACKSTACK);
                ft.commit();
            }
        });
        return view;
    }

}
