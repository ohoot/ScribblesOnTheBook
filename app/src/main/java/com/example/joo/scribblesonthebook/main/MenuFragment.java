package com.example.joo.scribblesonthebook.main;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.joo.scribblesonthebook.ChangePasswordActivity;
import com.example.joo.scribblesonthebook.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {


    public MenuFragment() {
        // Required empty public constructor
    }

    public static final int MENU_ID_CHANGE_PASSWORD = 1;
    public static final int MENU_ID_LOGOUT = 2;
    public static final int MENU_ID_FILTER_SETTING = 3;

    public interface OnMenuItemSeletedListener {
        public void onMenuItemSelected(int menuId);
    }

    OnMenuItemSeletedListener mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMenuItemSeletedListener) {
            mCallback = (OnMenuItemSeletedListener)context;
        }
    }

    private void selectMenu(int menuId) {
        if (mCallback != null) {
            mCallback.onMenuItemSelected(menuId);
        }
    }

    TextView changePasswordView, logoutView, filterSettingView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        changePasswordView = (TextView) view.findViewById(R.id.text_menu_change_password);
        changePasswordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMenu(MENU_ID_CHANGE_PASSWORD);
            }
        });
        logoutView = (TextView) view.findViewById(R.id.text_menu_logout);
        logoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMenu(MENU_ID_LOGOUT);
            }
        });
        filterSettingView = (TextView) view.findViewById(R.id.text_menu_filter_setting);
        filterSettingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMenu((MENU_ID_FILTER_SETTING));
            }
        });
        return view;
    }
}
