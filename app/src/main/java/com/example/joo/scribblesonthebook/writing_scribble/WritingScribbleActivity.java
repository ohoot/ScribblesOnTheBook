package com.example.joo.scribblesonthebook.writing_scribble;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.data.manager.NetworkManager;
import com.example.joo.scribblesonthebook.data.vo.BookData;
import com.example.joo.scribblesonthebook.data.vo.SimpleRequest;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

public class WritingScribbleActivity extends AppCompatActivity {

    public static final String CURRENT_BOOK_DATA = "currentBook";

    EditText pageView, contentView;
    ImageView imagePage;
    BookData bookData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_scribble);
        pageView = (EditText) findViewById(R.id.edit_scribble_page);
        contentView = (EditText) findViewById(R.id.edit_scribble_content);
        imagePage = (ImageView) findViewById(R.id.image_scribble_page);

        Intent intent = getIntent();
        bookData = (BookData) intent.getSerializableExtra(CURRENT_BOOK_DATA);

        imagePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    NetworkManager.getInstance().writeScribble(WritingScribbleActivity.this,
                            bookData.getIsbn() + "", pageView.getText().toString(), contentView.getText().toString(), null, null, new NetworkManager.OnResultListener<SimpleRequest>() {
                                @Override
                                public void onSuccess(Request request, SimpleRequest result) {
                                    if (result.success != null) {
                                        Toast.makeText(WritingScribbleActivity.this, result.success.message, Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(WritingScribbleActivity.this, result.error.message, Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Request request, int code, Throwable cause) {
                                    Toast.makeText(WritingScribbleActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                                }
                            });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });





    }
}
