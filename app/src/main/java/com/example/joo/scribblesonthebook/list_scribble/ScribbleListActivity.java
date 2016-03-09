package com.example.joo.scribblesonthebook.list_scribble;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.joo.scribblesonthebook.R;
import com.example.joo.scribblesonthebook.data.vo.Scribble;

import java.util.ArrayList;
import java.util.List;

public class ScribbleListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scribble_list);
    }

    private List<ScribbleGroup> convertScribbleGroup(List<Scribble> scribbleList) {
        List<ScribbleGroup> list = new ArrayList<>();
        String writerid = null;
        ScribbleGroup otherGroup = null;
        for (Scribble scribble : scribbleList) {
            if (scribble.getWriterId().equals(writerid)) {
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
