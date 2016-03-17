package com.example.joo.scribblesonthebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joo.scribblesonthebook.data.manager.NetworkManager;
import com.example.joo.scribblesonthebook.data.vo.Success;
import com.example.joo.scribblesonthebook.find_password.FindPasswordActivity;
import com.example.joo.scribblesonthebook.list_scribble.ScribbleListActivity;
import com.example.joo.scribblesonthebook.main.MainActivity;
import com.example.joo.scribblesonthebook.signup.SignupActivity;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editId, editPassword;
    TextView textFindPassword, textSignup;

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
    }

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
                    NetworkManager.getInstance().userLogin(LoginActivity.this, editId.getText().toString(), editPassword.getText().toString(), new NetworkManager.OnResultListener<Success>() {
                        @Override
                        public void onSuccess(Request request, Success result) {
                            Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }

                        @Override
                        public void onFailure(Request request, int code, Throwable cause) {
                            Toast.makeText(LoginActivity.this, cause.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_login_facebook :
                break;
        }
    }
}
