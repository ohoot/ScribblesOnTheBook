package com.example.joo.scribblesonthebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.joo.scribblesonthebook.R;

public class FindPassword2Activity extends AppCompatActivity {
    public static final String CONFIRM_KEY = "confirm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password2);
        setTitle("비밀번호 찾기");

        Button btn = (Button) findViewById(R.id.btn_confrimkey_cancel);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn = (Button) findViewById(R.id.btn_change_password);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindPassword2Activity.this, FindPassword3Activity.class);
                startActivity(intent);
            }
        });
    }
}
