package com.example.joo.scribblesonthebook.data.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Joo on 2016-02-22.
 */
public class CommunityInfo {
    @SerializedName("clubId")
    int communityId;
    @SerializedName("clubName")
    String communityName;
    @SerializedName("clubImage")
    String communityImage;
    @SerializedName("clubContents")
    String communityExplan;
    String link;
}
