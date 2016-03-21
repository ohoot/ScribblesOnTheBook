package com.example.joo.scribblesonthebook.list_scribble;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.data.ReferScribbleRecordSuccess;
import com.example.joo.scribblesonthebook.data.manager.NetworkManager;
import com.example.joo.scribblesonthebook.data.vo.BookData;
import com.example.joo.scribblesonthebook.data.vo.Scribble;
import com.example.joo.scribblesonthebook.data.vo.SimpleRequest;
import com.example.joo.scribblesonthebook.writing_scribble.WritingScribbleActivity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class ScribbleListActivity extends AppCompatActivity {

    public static final String CURRENT_BOOK_DATA = "currentBook";
    public static final String MODIFY_SCRIBBLE_DATA = "modiScribble";
    public static final String TRIANGLE_CLICKED_SCRIBBLE = "triangleClickedScribble";
    public static final String MODIFY_DIALOG_TAG = "modifyDialog";

    ExpandableListView listView;
    ScribbleListAdapter sAdapter;
    BookData bookData;
    Scribble scribbleData;
    String isbn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scribble_list);
        Intent intent = getIntent();
        bookData = (BookData) intent.getSerializableExtra(WritingScribbleActivity.CURRENT_BOOK_DATA);
        scribbleData = (Scribble) intent.getSerializableExtra(WritingScribbleActivity.MODIFY_SCRIBBLE_DATA);
        if (bookData != null) {
            isbn = bookData.getIsbn();
        } else if (scribbleData != null) {
            isbn = scribbleData.getIsbn();
        }

        listView = (ExpandableListView) findViewById(R.id.expandableScribbleListView);
        sAdapter = new ScribbleListAdapter();
        sAdapter.setOnAdapterHeartClickListener(new ScribbleListAdapter.OnAdapterHeartClickListener() {
            @Override
            public void receiveHeartClickedScribble(Scribble scribble) {
                try {
                    NetworkManager.getInstance().setScribbleLike(ScribbleListActivity.this, scribble.getIsbn(), "" + scribble.getScribbleId(), new NetworkManager.OnResultListener<SimpleRequest>() {
                        @Override
                        public void onSuccess(Request request, SimpleRequest result) {
                            if (result.success != null) {
                                Toast.makeText(ScribbleListActivity.this, result.success.message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ScribbleListActivity.this, result.error.message, Toast.LENGTH_SHORT).show();
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

        sAdapter.setOnAdapterOptionTriangleClickListener(new ScribbleListAdapter.OnAdapterOptionTriangleClickListener() {
            @Override
            public void receiveTriangleClickedScribble(Scribble scribble) {
                ModifyDialogFragment fragment = new ModifyDialogFragment();
                Bundle b = new Bundle();
                b.putSerializable(TRIANGLE_CLICKED_SCRIBBLE, scribble);
                fragment.setArguments(b);
                fragment.show(getSupportFragmentManager(), MODIFY_DIALOG_TAG);
            }
        });

        listView.setAdapter(sAdapter);

        try {
            NetworkManager.getInstance().getScribbleList(ScribbleListActivity.this, isbn, "" + 1, new NetworkManager.OnResultListener<ReferScribbleRecordSuccess>() {
                @Override
                public void onSuccess(Request request, ReferScribbleRecordSuccess result) {
                    sAdapter.addAll(convertScribbleGroup(result.doodleList));
                }

                @Override
                public void onFailure(Request request, int code, Throwable cause) {

                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private List<ScribbleGroup> convertScribbleGroup(List<Scribble> scribbleList) {
        List<ScribbleGroup> list = new ArrayList<>();
        int writerId = -1;
        ScribbleGroup otherGroup = null;
        for (Scribble scribble : scribbleList) {
            writerId = scribble.getWriterId();
            if (scribble.getWriterId() == writerId) {
                ScribbleGroup g = new ScribbleGroup();
                g.type = ScribbleGroup.GROUP_TYPE_MY;
                g.myScribble = scribble;
                list.add(g);
                otherGroup = null;
            } else {
                if (otherGroup == null) {
                    otherGroup = new ScribbleGroup();
                    otherGroup.type = ScribbleGroup.GROUP_TYPE_OTHER;
                    list.add(otherGroup);
                }
                otherGroup.otherList.add(scribble);
            }
        }
        return list;
    }
}
