package com.example.joo.scribblesonthebook.data.vo;

import com.google.gson.annotations.SerializedName;

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
    String category;
    @SerializedName("pop_emotion_id")
    int bookEmotion;
    @SerializedName("cover_url")
    String coverImage;
    @SerializedName("tense")
    int bookCondition;
    @SerializedName("pageNum")
    int currentPage;
    @SerializedName("finish_page")
    int finishPage;
    @SerializedName("start_page")
    int startPage;
    List<BookMark> bookMarks;
    @SerializedName("doodleList")
    List<Scribble> scribbles;
}
