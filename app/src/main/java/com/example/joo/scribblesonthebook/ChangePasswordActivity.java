package com.example.joo.scribblesonthebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.joo.scribblesonthebook.data.manager.NetworkManager;
import com.example.joo.scribblesonthebook.data.vo.SimpleRequest;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText passwordView1, passwordView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        passwordView1 = (EditText) findViewById(R.id.edit_new_password1);
        passwordView2 = (EditText) findViewById(R.id.edit_new_password2);

        Button btn = (Button) findViewById(R.id.btn_change_pwd_ok);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    NetworkManager.getInstance().changePassword(ChangePasswordActivity.this, passwordView1.getText().toString(), passwordView2.getText().toString(), new NetworkManager.OnResultListener<SimpleRequest>() {
                        @Override
                        public void onSuccess(Request request, SimpleRequest result) {
                            if (result.success != null) {
                                Toast.makeText(ChangePasswordActivity.this, result.success.message, Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(ChangePasswordActivity.this, LoginActivity.class));
                                finish();
                            } else {
                                Toast.makeText(ChangePasswordActivity.this, result.error.message, Toast.LENGTH_SHORT).show();
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
        });

        btn = (Button) findViewById(R.id.btn_change_pwd_cancel);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
