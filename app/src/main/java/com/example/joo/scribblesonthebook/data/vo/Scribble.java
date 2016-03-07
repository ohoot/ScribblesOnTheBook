package com.example.joo.scribblesonthebook.data.vo;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Joo on 2016-02-22.
 */
public class Scribble {
    @SerializedName("doodle_id")
    String scribbleId;
    @SerializedName("writer_user_id")
    String writerId;
    String isbn;
    String page;
    @SerializedName("text_doodle")
    String content;
    @SerializedName("emotion_doodle_id")
    String scribbleEmotion;
    @SerializedName("heart")
    String like;
    @SerializedName("picture_doodle_url")
    String scribbleImage;
    @SerializedName("doodle_time")
    String scribbleDate;
    @SerializedName("user_photo_url")
    String userPhoto;
    @SerializedName("nickname")
    String nickName;
    @SerializedName("cover_url")
    String coverUrl;

}
