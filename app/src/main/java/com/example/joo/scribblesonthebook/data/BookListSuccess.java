package com.example.joo.scribblesonthebook.data;

import com.example.joo.scribblesonthebook.data.vo.BookData;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Joo on 2016-03-04.
 */
public class BookListSuccess {
    public String message;
    public int pageNum;
    @SerializedName("user_id")
    public String userId;
    public ArrayList<BookData> tenseList = new ArrayList<BookData>();
}
