package com.example.joo.scribblesonthebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joo.scribblesonthebook.find_password.FindPasswordActivity;
import com.example.joo.scribblesonthebook.main.MainActivity;
import com.example.joo.scribblesonthebook.signup.SignupActivity;

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
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                break;
            case R.id.btn_login_facebook :
                Toast.makeText(LoginActivity.this, "Facebook Login Test...", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
