package com.example.joo.scribblesonthebook.data;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Joo on 2016-02-22.
 */
public class Scribble {
    @SerializedName("doodle_id")
    int scribbleId;
    @SerializedName("writer_user_id")
    String writerId;
    String isbn;
    @SerializedName("doodle_page")
    int bookPage;
    @SerializedName("text_doodle")
    String content;
    @SerializedName("emotion_doodle_id")
    int scribbleEmotion;
    @SerializedName("heart")
    int like;
    @SerializedName("picture_doodle_url")
    String scribbleImage;
    @SerializedName("doodle_time")
    Date scribbleDate;
}
