package com.example.joo.scribblesonthebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.joo.scribblesonthebook.data.manager.NetworkManager;
import com.example.joo.scribblesonthebook.data.vo.BookData;
import com.example.joo.scribblesonthebook.data.vo.SimpleRequest;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

public class AddBookActivity extends AppCompatActivity {

    public static final String RECOMMENDED_BOOK_TAG = "recommendedBook";
    public static final String SEARCHED_BOOK_TAG = "searchedBook";
    BookData bookData;

    ImageView coverView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        Intent intent = getIntent();
        bookData = (BookData) intent.getSerializableExtra(RECOMMENDED_BOOK_TAG);
        if (bookData == null) {
            bookData = (BookData) intent.getSerializableExtra(SEARCHED_BOOK_TAG);
        }

        coverView = (ImageView) findViewById(R.id.image_add_book_cover);
        Glide.with(AddBookActivity.this).load(bookData.getCoverImage()).into(coverView);

        Button btn = (Button) findViewById(R.id.btn_add_book);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    NetworkManager.getInstance().addBook(AddBookActivity.this, bookData.getIsbn(), "" + bookData.getBookCondition(), new NetworkManager.OnResultListener<SimpleRequest>() {
                        @Override
                        public void onSuccess(Request request, SimpleRequest result) {
                            if (result.error == null) {
                                Toast.makeText(AddBookActivity.this, result.success.message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AddBookActivity.this, result.error.message, Toast.LENGTH_SHORT).show();
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
    }
}
