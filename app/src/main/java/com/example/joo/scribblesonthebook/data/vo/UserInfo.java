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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getRetainedBooks() {
        return retainedBooks;
    }

    public void setRetainedBooks(int retainedBooks) {
        this.retainedBooks = retainedBooks;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getLocalEmail() {
        return localEmail;
    }

    public void setLocalEmail(String localEmail) {
        this.localEmail = localEmail;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public int getUserEmotion() {
        return userEmotion;
    }

    public void setUserEmotion(int userEmotion) {
        this.userEmotion = userEmotion;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public ArrayList<Integer> getFilter() {
        return filter;
    }

    public void setFilter(ArrayList<Integer> filter) {
        this.filter = filter;
    }
}
