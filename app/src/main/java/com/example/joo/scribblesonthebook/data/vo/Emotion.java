package com.example.joo.scribblesonthebook.data.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Joo on 2016-03-03.
 */
public class Emotion {
    @SerializedName("emotion_id")
    int emotionId;
    @SerializedName("emotion_name")
    String emotionName;
}
