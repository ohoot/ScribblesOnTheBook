package com.example.joo.scribblesonthebook.data.vo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getBookEmotion() {
        return bookEmotion;
    }

    public void setBookEmotion(int bookEmotion) {
        this.bookEmotion = bookEmotion;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public int getBookCondition() {
        return bookCondition;
    }

    public void setBookCondition(int bookCondition) {
        this.bookCondition = bookCondition;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getFinishPage() {
        return finishPage;
    }

    public void setFinishPage(int finishPage) {
        this.finishPage = finishPage;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public List<BookMark> getBookMarks() {
        return bookMarks;
    }

    public void setBookMarks(List<BookMark> bookMarks) {
        this.bookMarks = bookMarks;
    }

    public List<Scribble> getScribbles() {
        return scribbles;
    }

    public void setScribbles(List<Scribble> scribbles) {
        this.scribbles = scribbles;
    }
}
