package com.example.joo.scribblesonthebook.list_scribble;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.data.ReferScribbleRecordSuccess;
import com.example.joo.scribblesonthebook.data.manager.NetworkManager;
import com.example.joo.scribblesonthebook.data.vo.BookData;
import com.example.joo.scribblesonthebook.data.vo.Scribble;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class ScribbleListActivity extends AppCompatActivity {

    public static final String CURRENT_BOOK_DATA = "currentBook";

    ExpandableListView listView;
    ScribbleListAdapter sAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scribble_list);
        Intent intent = getIntent();
        BookData bookData = (BookData) intent.getSerializableExtra(CURRENT_BOOK_DATA);

        try {
            NetworkManager.getInstance().getScribbleList(ScribbleListActivity.this, bookData.getIsbn(), "" + 1, new NetworkManager.OnResultListener<ReferScribbleRecordSuccess>() {
                @Override
                public void onSuccess(Request request, ReferScribbleRecordSuccess result) {
                    listView = (ExpandableListView) findViewById(R.id.expandableScribbleListView);
                    sAdapter = new ScribbleListAdapter();
                    listView.setAdapter(sAdapter);
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
        int writerId = 1;
        ScribbleGroup otherGroup = null;
        for (Scribble scribble : scribbleList) {
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
