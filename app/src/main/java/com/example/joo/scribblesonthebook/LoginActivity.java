package com.example.joo.scribblesonthebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joo.scribblesonthebook.data.LoginRequest;
import com.example.joo.scribblesonthebook.data.manager.NetworkManager;
import com.example.joo.scribblesonthebook.data.manager.UserPropertyManager;
import com.example.joo.scribblesonthebook.data.vo.SimpleRequest;
import com.example.joo.scribblesonthebook.find_password.FindPasswordActivity;
import com.example.joo.scribblesonthebook.main.MainActivity;
import com.example.joo.scribblesonthebook.signup.SignupActivity;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editId, editPassword;
    TextView textFindPassword, textSignup;
    LoginManager loginManager;
    CallbackManager callbackManager;
    AccessTokenTracker tracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editId = (EditText) findViewById(R.id.edit_id);
        editPassword = (EditText) findViewById(R.id.edit_password);

        textFindPassword = (TextView) findViewById(R.id.text_find_password);
        textFindPassword.setOnClickListener(this);

        textSignup = (TextView) findViewById(R.id.text_signup);
        textSignup.setOnClickListener(this);

        Button btn = (Button) findViewById(R.id.btn_login);
        btn.setOnClickListener(this);

        btn = (Button) findViewById(R.id.btn_login_facebook);
        btn.setOnClickListener(this);

        loginManager = LoginManager.getInstance();
        callbackManager = CallbackManager.Factory.create();
    }

    private static final String NETWORK_ERROR_MESSAGE = "Network Error";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_find_password :
                Intent intent = new Intent(LoginActivity.this, FindPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.text_signup :
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                break;
            case R.id.btn_login :
                try {
                    NetworkManager.getInstance().userLogin(LoginActivity.this, editId.getText().toString(), editPassword.getText().toString(), new NetworkManager.OnResultListener<LoginRequest>() {
                        @Override
                        public void onSuccess(Request request, LoginRequest result) {
                            if (result.error == null) {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                UserPropertyManager.getInstance().userId = result.success.userId;
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, result.error.message, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Request request, int code, Throwable cause) {
                            Toast.makeText(LoginActivity.this, NETWORK_ERROR_MESSAGE, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_login_facebook :
                facebookLogin();
                break;
        }
    }

    private void facebookLogin() {
        AccessToken token = AccessToken.getCurrentAccessToken();
        if (token == null) {
            loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    AccessToken token = AccessToken.getCurrentAccessToken();
                    try {
                        NetworkManager.getInstance().facebookLogin(LoginActivity.this, token.getToken(), new NetworkManager.OnResultListener<LoginRequest>() {
                            @Override
                            public void onSuccess(Request request, LoginRequest result) {
                                if (result.error == null) {
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    UserPropertyManager.getInstance().userId = result.success.userId;
                                    Toast.makeText(LoginActivity.this, UserPropertyManager.getInstance().userId + "", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this ,result.error.message, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Request request, int code, Throwable cause) {

                            }
                        });
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(FacebookException error) {

                }
            });
            loginManager.logInWithReadPermissions(this, null);
        } else {
            loginManager.logOut();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
