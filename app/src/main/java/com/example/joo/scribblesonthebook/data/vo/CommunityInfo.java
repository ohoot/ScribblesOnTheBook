package com.example.joo.scribblesonthebook.data.vo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Joo on 2016-02-22.
 */
public class CommunityInfo implements RecommData, Serializable{
    @SerializedName("clubId")
    int communityId;
    @SerializedName("clubName")
    String communityName;
    @SerializedName("clubImage")
    String communityImage;
    @SerializedName("clubContents")
    String communityExplan;
    String link;

    public int getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCommunityImage() {
        return communityImage;
    }

    public void setCommunityImage(String communityImage) {
        this.communityImage = communityImage;
    }

    public String getCommunityExplan() {
        return communityExplan;
    }

    public void setCommunityExplan(String communityExplan) {
        this.communityExplan = communityExplan;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
