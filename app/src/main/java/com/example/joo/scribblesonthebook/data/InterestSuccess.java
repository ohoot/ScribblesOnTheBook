package com.example.joo.scribblesonthebook.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Joo on 2016-03-04.
 */
public class InterestSuccess {
    public String message;
    @SerializedName("filter")
    public ArrayList<Filter> filters = new ArrayList<Filter>();
}
