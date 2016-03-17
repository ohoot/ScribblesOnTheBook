package com.example.joo.scribblesonthebook.data.vo;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Joo on 2016-02-22.
 */
public class Scribble {
    @SerializedName("doodle_id")
    int scribbleId;
    @SerializedName("write_user_id")
    int writerId;
    String isbn;
    @SerializedName("doodle_page")
    int page;
    @SerializedName("text_doodle")
    String content;
    @SerializedName("emotion_doodle_id")
    int scribbleEmotion;
    @SerializedName("heart")
    int like;
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

    public int getScribbleId() {
        return scribbleId;
    }

    public void setScribbleId(int scribbleId) {
        this.scribbleId = scribbleId;
    }

    public int getWriterId() {
        return writerId;
    }

    public void setWriterId(int writerId) {
        this.writerId = writerId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getScribbleEmotion() {
        return scribbleEmotion;
    }

    public void setScribbleEmotion(int scribbleEmotion) {
        this.scribbleEmotion = scribbleEmotion;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getScribbleImage() {
        return scribbleImage;
    }

    public void setScribbleImage(String scribbleImage) {
        this.scribbleImage = scribbleImage;
    }

    public String getScribbleDate() {
        return scribbleDate;
    }

    public void setScribbleDate(String scribbleDate) {
        this.scribbleDate = scribbleDate;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
}
