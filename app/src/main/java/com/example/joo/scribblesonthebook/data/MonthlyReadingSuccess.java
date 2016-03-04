package com.example.joo.scribblesonthebook.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Joo on 2016-03-04.
 */
public class MonthlyReadingSuccess {
    public String message;
    @SerializedName("month_page")
    public int monthlyPage;
}
