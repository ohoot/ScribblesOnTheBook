package com.example.joo.scribblesonthebook.data;

import com.example.joo.scribblesonthebook.data.vo.BookData;

import java.util.ArrayList;

/**
 * Created by Joo on 2016-03-04.
 */
public class SearchingBookSuccess {
    public String message;
    public int page;
    public int rows;
    public ArrayList<BookData> searchList = new ArrayList<BookData>();
}
