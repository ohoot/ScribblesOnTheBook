package com.example.joo.scribblesonthebook.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Joo on 2016-02-22.
 */
public class BookData {
    String title;
    String author;
    String publisher;
    @SerializedName("total_page")
    int totalPage;
    String isbn;
    @SerializedName("category_id")
    int categoryId;
    @SerializedName("pop_emotion_id")
    int bookEmotion;
    @SerializedName("cover_url")
    String coverImage;
    @SerializedName("tense")
    int bookCondition;
    int currentPage;
    @SerializedName("finish_page")
    int finishPage;
    @SerializedName("start_page")
    int startPage;
    List<BookMark> bookMarks;
    List<Scribble> scribbles;
}
