package com.example.joo.scribblesonthebook.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Joo on 2016-03-22.
 */
public class LoginSuccess {
    public String message;
    @SerializedName("user_id")
    public int userId;
}
