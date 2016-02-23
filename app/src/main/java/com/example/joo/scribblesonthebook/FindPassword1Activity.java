package com.example.joo.scribblesonthebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.joo.scribblesonthebook.R;

import java.util.Random;

public class FindPassword1Activity extends AppCompatActivity {

    EditText editEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password1);
        this.setTitle("비밀번호 찾기");

        editEmail = (EditText) findViewById(R.id.edit_email_find_password);

        Button btn = (Button) findViewById(R.id.btn_cancel);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn = (Button) findViewById(R.id.btn_send_key);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindPassword1Activity.this, FindPassword2Activity.class);
                Random r = new Random();
                intent.putExtra(FindPassword2Activity.CONFIRM_KEY, r.nextInt(100));
                startActivity(intent);
            }
        });
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
    }
}
