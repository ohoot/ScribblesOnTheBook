package com.example.joo.scribblesonthebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.joo.scribblesonthebook.R;

public class FindPassword2Activity extends AppCompatActivity {
    public static final String CONFIRM_KEY = "confirm";
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password2);

        textView = (TextView) findViewById(R.id.testText);
        Intent intent = getIntent();
        int i = intent.getIntExtra(CONFIRM_KEY, 0);

        textView.setText(i + "");
    }
}
