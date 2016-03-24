package com.example.joo.scribblesonthebook.signup;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

    boolean isCompletedEmail = false;
    boolean isCorresponded = false;
    boolean isAllChecked = false;
    String password1, password2;
    Button btnNext;
    CheckBox serviceTerm, personalTerm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_info_terms, container, false);

        serviceTerm = (CheckBox) view.findViewById(R.id.checkBox_service_terms);
        personalTerm = (CheckBox) view.findViewById(R.id.checkBox_personal_terms);

        serviceTerm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && personalTerm.isChecked()) {
                    isAllChecked = true;
                } else {
                    isAllChecked = false;
                }

                if (isCompletedEmail && isCorresponded && isAllChecked) {
                    btnNext.setTextColor(ContextCompat.getColor(getContext(), R.color.activatedButton));
                    btnNext.setEnabled(true);
                } else {
                    btnNext.setTextColor(ContextCompat.getColor(getContext(), R.color.disableddButton));
                    btnNext.setEnabled(false);
                }
            }
        });

        personalTerm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && serviceTerm.isChecked()) {
                    isAllChecked = true;
                } else {
                    isAllChecked = false;
                }

                if (isCompletedEmail && isCorresponded && isAllChecked) {
                    btnNext.setTextColor(ContextCompat.getColor(getContext(), R.color.activatedButton));
                    btnNext.setEnabled(true);
                } else {
                    btnNext.setTextColor(ContextCompat.getColor(getContext(), R.color.disableddButton));
                    btnNext.setEnabled(false);
                }
            }
        });

        btnNext = (Button) view.findViewById(R.id.btn_signup_next);
        btnNext.setEnabled(false);
        btnNext.setOnClickListener(new View.OnClickListener() {
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

        userEmail = (EditText) view.findViewById(R.id.edit_terms_email);
        userEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 7) {
                    isCompletedEmail = true;
                } else {
                    isCompletedEmail = false;
                }

                if (isCompletedEmail && isCorresponded && isAllChecked) {
                    btnNext.setTextColor(ContextCompat.getColor(getContext(), R.color.activatedButton));
                    btnNext.setEnabled(true);
                } else {
                    btnNext.setTextColor(ContextCompat.getColor(getContext(), R.color.disableddButton));
                    btnNext.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        userPassword1 = (EditText) view.findViewById(R.id.edit_terms_password1);
        userPassword1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 8 && s.length() <= 16) {
                    password1 = s.toString();
                } else {
                    password1 = "";
                }

                if (password1 != null && password2 != null) {
                    if (password1.length() >= 8 && password1.length() <= 16) {
                        if (password1.equals(password2)) {
                            isCorresponded = true;
                        } else {
                            isCorresponded = false;
                        }
                    } else {
                        isCorresponded = false;
                    }
                }

                if (isCompletedEmail && isCorresponded && isAllChecked) {
                    btnNext.setTextColor(ContextCompat.getColor(getContext(), R.color.activatedButton));
                    btnNext.setEnabled(true);
                } else {
                    btnNext.setTextColor(ContextCompat.getColor(getContext(), R.color.disableddButton));
                    btnNext.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        userPassword2 = (EditText) view.findViewById(R.id.edit_terms_password2);
        userPassword2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 8 && s.length() <= 16) {
                    password2 = s.toString();
                } else {
                    password2 = "";
                }

                if (password1 != null && password2 != null) {
                    if (password1.length() >= 8 && password1.length() <= 16) {
                        if (password1.equals(password2)) {
                            isCorresponded = true;
                        } else {
                            isCorresponded = false;
                        }
                    } else {
                        isCorresponded = false;
                    }
                }

                if (isCompletedEmail && isCorresponded && isAllChecked) {
                    btnNext.setTextColor(ContextCompat.getColor(getContext(), R.color.activatedButton));
                    btnNext.setEnabled(true);
                } else {
                    btnNext.setTextColor(ContextCompat.getColor(getContext(), R.color.disableddButton));
                    btnNext.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button btnCancel = (Button) view.findViewById(R.id.btn_signup_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return view;
    }
}
