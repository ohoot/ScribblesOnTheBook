package com.example.joo.scribblesonthebook.list_scribble;

import com.example.joo.scribblesonthebook.data.vo.Scribble;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joo on 2016-03-09.
 */
public class ScribbleGroup {
    public static final int GROUP_TYPE_MY = 1;
    public static final int GROUP_TYPE_OTHER = 2;
    int type;
    public Scribble myScribble;
    public List<Scribble> otherList = new ArrayList<Scribble>();
}
