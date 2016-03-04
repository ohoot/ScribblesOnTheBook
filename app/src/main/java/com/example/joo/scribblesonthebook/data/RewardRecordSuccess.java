package com.example.joo.scribblesonthebook.data;

import com.example.joo.scribblesonthebook.data.vo.Reward;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Joo on 2016-03-04.
 */
public class RewardRecordSuccess {
    public String message;
    public int page;
    public int rows;
    @SerializedName("user_id")
    public String userId;
    public ArrayList<Reward> rewardList = new ArrayList<Reward>();

}
