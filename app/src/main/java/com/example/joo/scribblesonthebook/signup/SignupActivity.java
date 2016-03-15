package com.example.joo.scribblesonthebook.signup;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.joo.scribblesonthebook.R;

public class SignupActivity extends AppCompatActivity {
    public static final String REGISTER_USER_BACKSTACK = "registerUserInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Fragment fragment = new SignupUserFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.signup_container, fragment);
        ft.addToBackStack(REGISTER_USER_BACKSTACK);
        ft.commit();
    }
}
