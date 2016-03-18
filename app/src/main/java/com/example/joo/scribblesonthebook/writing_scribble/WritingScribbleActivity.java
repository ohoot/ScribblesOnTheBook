package com.example.joo.scribblesonthebook.writing_scribble;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.data.manager.NetworkManager;
import com.example.joo.scribblesonthebook.data.vo.BookData;
import com.example.joo.scribblesonthebook.data.vo.Scribble;
import com.example.joo.scribblesonthebook.data.vo.SimpleRequest;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

public class WritingScribbleActivity extends AppCompatActivity {

    public static final String CURRENT_BOOK_DATA = "currentBook";
    public static final String MODIFY_SCRIBBLE_DATA = "modifyScribble";
    public static final String OUTPUT_TYPE = "outPutType";
    public static final int OUTPUT_TYPE_WRITING = 1;
    public static final int OUTPUT_TYPE_MODIFIYNG = 2;


    EditText pageView, contentView;
    ImageView imagePage, imageScribble;
    BookData bookData;
    Scribble scribble;
    int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_scribble);
        pageView = (EditText) findViewById(R.id.edit_scribble_page);
        contentView = (EditText) findViewById(R.id.edit_scribble_content);
        imagePage = (ImageView) findViewById(R.id.image_scribble_page);
        imageScribble = (ImageView) findViewById(R.id.image_scribble_content);

        Intent intent = getIntent();
        type = intent.getIntExtra(OUTPUT_TYPE, 0);
        if (type == OUTPUT_TYPE_WRITING) {
            bookData = (BookData) intent.getSerializableExtra(CURRENT_BOOK_DATA);
            type = OUTPUT_TYPE_WRITING;
        } else if (type == OUTPUT_TYPE_MODIFIYNG) {
            scribble = (Scribble) intent.getSerializableExtra(MODIFY_SCRIBBLE_DATA);
            type = OUTPUT_TYPE_MODIFIYNG;
            setScribbleNote(scribble);
        }

        imagePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button btn = (Button) findViewById(R.id.btn_upload_tmp);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == OUTPUT_TYPE_WRITING) {
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
                } else if (type == OUTPUT_TYPE_MODIFIYNG) {
                    // 수정하기
                    try {
                        NetworkManager.getInstance().modifyScribble(WritingScribbleActivity.this, scribble.getIsbn(), pageView.getText().toString(), contentView.getText().toString(), null, null, "" + scribble.getScribbleId(), new NetworkManager.OnResultListener<SimpleRequest>() {
                            @Override
                            public void onSuccess(Request request, SimpleRequest result) {
                                if (result.success.message != null) {
                                    Toast.makeText(WritingScribbleActivity.this, result.success.message, Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(WritingScribbleActivity.this, result.error.message, Toast.LENGTH_SHORT).show();
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
            }
        });
    }

    private void setScribbleNote(Scribble scribble) {
        pageView.setText(scribble.getPage() + "");
        if (scribble.getScribbleImage() != null) {
            Glide.with(WritingScribbleActivity.this).load(scribble.getScribbleImage()).into(imageScribble);
        }
        if (scribble.getContent() != null) {
            contentView.setText(scribble.getContent());
        }
    }
}
