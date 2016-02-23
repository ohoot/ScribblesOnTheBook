package com.example.joo.scribblesonthebook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.joo.scribblesonthebook.R;

public class FindPassword3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password3);
        setTitle("비밀번호 찾기");

        Button btn = (Button) findViewById(R.id.btn_new_password_cancel);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn = (Button) findViewById(R.id.btn_new_password_ok);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FindPassword3Activity.this, "비밀번호 변경 Test", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
