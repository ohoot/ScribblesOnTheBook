package com.example.joo.scribblesonthebook.signup;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.data.manager.NetworkManager;
import com.example.joo.scribblesonthebook.data.vo.Success;

import java.io.File;
import java.io.UnsupportedEncodingException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterProfileFragment extends Fragment {

    public static final String EMAIL_KEY_VALUE = "userEmail";
    public static final String PASSWORD1_KEY_VALUE = "password1";
    public static final String PASSWORD2_KEY_VALUE = "password2";
    public static final String FILTER_BACKSTACK = "filter";

    ImageView photoView;
    EditText nickView;
    Uri mFileUri;

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
        photoView = (ImageView) view.findViewById(R.id.image_user_profile);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callGallery();
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
                File file = null;
                if (mFileUri != null) {
                    file = new File(mFileUri.getPath());
                }
                try {
                    NetworkManager.getInstance().signupUser(getContext(), email, password1, password2, nickView.getText().toString(), file , new NetworkManager.OnResultListener<Success>() {
                        @Override
                        public void onSuccess(Request request, Success result) {
                            Fragment fragment = new FilterFragment();
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.signup_container, fragment);
                            ft.addToBackStack(FILTER_BACKSTACK);
                            ft.commit();
                        }

                        @Override
                        public void onFailure(Request request, int code, Throwable cause) {
                            Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


            }
        });
        return view;
    }

    public static final int RC_GALLERY = 1;
    public static final String DIR_PATH = "myfile";
    public static final String INTENT_TYPE = "image/*";

    private void callGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType(INTENT_TYPE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, getFileUri());
        startActivityForResult(intent, RC_GALLERY);
    }

    private Uri getFileUri() {
        File dir = getActivity().getExternalFilesDir(DIR_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, "my_image_" + System.currentTimeMillis() + ".jpeg");
        mFileUri = Uri.fromFile(file);
        return mFileUri;
    }

    public static final String SELECTED_FILE = "selected_file";

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SELECTED_FILE, mFileUri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                Cursor c = getActivity().getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                if (c.moveToNext()) {
                    String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
                    mFileUri = Uri.fromFile(new File(path));
                    Glide.with(getContext()).load(mFileUri).into(photoView);
                }
            }
        }
    }


}
