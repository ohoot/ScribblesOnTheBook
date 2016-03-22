package com.example.joo.scribblesonthebook.writing_scribble;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.data.manager.NetworkManager;
import com.example.joo.scribblesonthebook.data.vo.BookData;
import com.example.joo.scribblesonthebook.data.vo.Scribble;
import com.example.joo.scribblesonthebook.data.vo.SimpleRequest;
import com.example.joo.scribblesonthebook.list_scribble.ScribbleListActivity;

import java.io.File;
import java.io.UnsupportedEncodingException;

import okhttp3.Request;

public class WritingScribbleActivity extends AppCompatActivity {

    public static final String CURRENT_BOOK_DATA = "currentBook";
    public static final String MODIFY_SCRIBBLE_DATA = "modifiedScribble";
    public static final String OUTPUT_TYPE = "outPutType";
    public static final int OUTPUT_TYPE_WRITING = 1;
    public static final int OUTPUT_TYPE_MODIFIYNG = 2;

    EditText pageView, contentView;
    ImageView imagePage, imageScribble, imageGallary;
    BookData bookData;
    Scribble scribble;
    RadioGroup radioGroup;
    int type = 0;
    int emotionId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_scribble);
        pageView = (EditText) findViewById(R.id.edit_scribble_page);
        contentView = (EditText) findViewById(R.id.edit_scribble_content);
        imagePage = (ImageView) findViewById(R.id.image_scribble_page);
        imageScribble = (ImageView) findViewById(R.id.image_scribble_content);
        imageGallary = (ImageView) findViewById(R.id.image_scribble_gallary);
        imageGallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callGallery();
            }
        });

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup_writing_sc_emotion);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_enjoy : emotionId = 1; break;
                    case R.id.radio_sad : emotionId = 2; break;
                    case R.id.radio_emb : emotionId = 3; break;
                    case R.id.radio_awaken : emotionId = 4; break;
                    case R.id.radio_lovely : emotionId = 5; break;
                    case R.id.radio_surprise : emotionId = 6; break;
                }
            }
        });

        Intent intent = getIntent();
        type = intent.getIntExtra(OUTPUT_TYPE, 0);
        if (type == OUTPUT_TYPE_WRITING) {
            bookData = (BookData) intent.getSerializableExtra(CURRENT_BOOK_DATA);
            type = OUTPUT_TYPE_WRITING;
        } else if (type == OUTPUT_TYPE_MODIFIYNG) {
            scribble = (Scribble) intent.getSerializableExtra(ScribbleListActivity.MODIFY_SCRIBBLE_DATA);
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
                File file = null;
                if (mFileUri != null) {
                    file = new File(mFileUri.getPath());
                }

                if (type == OUTPUT_TYPE_WRITING) {
                    try {
                        NetworkManager.getInstance().writeScribble(WritingScribbleActivity.this,
                                bookData.getIsbn() + "", pageView.getText().toString(), contentView.getText().toString(), file, "" + emotionId, new NetworkManager.OnResultListener<SimpleRequest>() {
                                    @Override
                                    public void onSuccess(Request request, SimpleRequest result) {
                                        if (result.success != null) {
                                            Toast.makeText(WritingScribbleActivity.this, result.success.message, Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(WritingScribbleActivity.this, ScribbleListActivity.class);
                                            intent.putExtra(CURRENT_BOOK_DATA, bookData);
                                            startActivity(intent);
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
                        NetworkManager.getInstance().modifyScribble(WritingScribbleActivity.this, scribble.getIsbn(), pageView.getText().toString(), contentView.getText().toString(), file, "" + emotionId, "" + scribble.getScribbleId(), new NetworkManager.OnResultListener<SimpleRequest>() {
                            @Override
                            public void onSuccess(Request request, SimpleRequest result) {
                                if (result.success.message != null) {
                                    Intent intent = new Intent(WritingScribbleActivity.this, ScribbleListActivity.class);
                                    intent.putExtra(ScribbleListActivity.MODIFY_SCRIBBLE_DATA, scribble);
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

    public static final int RC_GALLERY = 1;
    public static final String DIR_PATH = "myfile";
    public static final String INTENT_TYPE = "image/*";
    Uri mFileUri;

    private void callGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType(INTENT_TYPE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, getFileUri());
        startActivityForResult(intent, RC_GALLERY);
    }

    private Uri getFileUri() {
        File dir = getExternalFilesDir(DIR_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, "my_image_" + System.currentTimeMillis() + ".jpeg");
        mFileUri = Uri.fromFile(file);
        return mFileUri;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                Cursor c = getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                if (c.moveToNext()) {
                    String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
                    mFileUri = Uri.fromFile(new File(path));
                    Glide.with(WritingScribbleActivity.this).load(mFileUri).into(imageScribble);
                }
            }
        }
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
