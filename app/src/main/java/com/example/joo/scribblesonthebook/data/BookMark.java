package com.example.joo.scribblesonthebook.data;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Joo on 2016-02-22.
 */
public class BookMark {
    int page;
    int pageEmotion;
    @SerializedName("bookmark_time")
    Date date;
}
