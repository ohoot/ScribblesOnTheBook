package com.example.joo.scribblesonthebook.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Joo on 2016-03-22.
 */
public class SelectedCategory {
    @SerializedName("category_select")
    public List<Integer> selectedItems;
}
