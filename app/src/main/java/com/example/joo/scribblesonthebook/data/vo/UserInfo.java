package com.example.joo.scribblesonthebook.data.vo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Joo on 2016-02-22.
 */
public class UserInfo {
    @SerializedName("user_id")
    String userId;
    String userPassword;
    int retainedBooks;
    @SerializedName("nickname")
    String userNick;
    @SerializedName("local_email")
    String localEmail;
    @SerializedName("facebook_id")
    String facebookId;
    @SerializedName("pop_emotion_id")
    int userEmotion;
    @SerializedName("user_photo_url")
    String userPicture;
    @SerializedName("login_time")
    Date loginTime;
    ArrayList<Integer> filter = new ArrayList<Integer>();
}
