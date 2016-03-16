package com.example.joo.scribblesonthebook.data;

import com.example.joo.scribblesonthebook.data.vo.BookData;
import com.example.joo.scribblesonthebook.data.vo.CommunityInfo;
import com.example.joo.scribblesonthebook.data.vo.EventInfo;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Joo on 2016-03-04.
 */
public class RecommendationSuccess {
    public String message;
    public int page;
    public int rows;
    @SerializedName("recommend_id")
    public int recommendId;
    public BookData recommendBook;
    public EventInfo recommendProgram;
    public CommunityInfo recommendClub;
}
