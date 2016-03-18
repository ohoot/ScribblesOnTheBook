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
    ImageView imagePage;
    BookData bookData;
    Scribble scribble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_scribble);
        pageView = (EditText) findViewById(R.id.edit_scribble_page);
        contentView = (EditText) findViewById(R.id.edit_scribble_content);
        imagePage = (ImageView) findViewById(R.id.image_scribble_page);

        Intent intent = getIntent();
        int type = intent.getIntExtra(OUTPUT_TYPE, 0);
        if (type == 1) {
            bookData = (BookData) intent.getSerializableExtra(CURRENT_BOOK_DATA);
        } else if (type == 2) {
            scribble = (Scribble) intent.getSerializableExtra(MODIFY_SCRIBBLE_DATA);
        }

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

    private void setScribbleNote(BookData bookData) {

    }
}
