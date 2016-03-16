package com.example.joo.scribblesonthebook.main;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.joo.scribblesonthebook.ChangePasswordActivity;
import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.data.ReferPersonalSuccess;
import com.example.joo.scribblesonthebook.data.manager.NetworkManager;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;


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
            mCallback = (OnMenuItemSeletedListener) context;
        }
    }

    private void selectMenu(int menuId) {
        if (mCallback != null) {
            mCallback.onMenuItemSelected(menuId);
        }
    }

    ImageView photoView;
    TextView changePasswordView, logoutView, filterSettingView, nickView, emailView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        nickView = (TextView) view.findViewById(R.id.text_menu_userid);
        photoView = (ImageView) view.findViewById(R.id.image_menu_photo);
        emailView = (TextView) view.findViewById(R.id.text_menu_email);

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

        try {
            NetworkManager.getInstance().getMenuInfo(getContext(), new NetworkManager.OnResultListener<ReferPersonalSuccess>() {
                @Override
                public void onSuccess(Request request, ReferPersonalSuccess result) {
                    setMenuInfo(result);
                }

                @Override
                public void onFailure(Request request, int code, Throwable cause) {

                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return view;
    }

    private void setMenuInfo(ReferPersonalSuccess result) {
        Glide.with(getContext()).load(result.me.getUserPicture()).into(photoView);
        nickView.setText(result.me.getUserNick());
        emailView.setText(result.me.getLocalEmail());
    }
}
