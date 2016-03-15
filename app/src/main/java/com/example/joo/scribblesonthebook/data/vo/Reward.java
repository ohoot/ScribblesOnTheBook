package com.example.joo.scribblesonthebook.data.vo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Joo on 2016-03-04.
 */
public class Reward {
    @SerializedName("reward_id")
    int rewardId;
    @SerializedName("reward_type")
    int rewardType;
    @SerializedName("reward_quantity")
    int rewardQuantity;
    @SerializedName("reward_image_url")
    String rewardImage;
    @SerializedName("reward_list")
    List<String> rewardImageList;

}
