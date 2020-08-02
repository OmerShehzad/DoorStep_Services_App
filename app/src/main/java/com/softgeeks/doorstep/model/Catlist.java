
package com.softgeeks.doorstep.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Catlist {

    @SerializedName("id")
    private String mId;
    @SerializedName("img")
    private String mImg;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("count")
    private String mCount;

    public String getmCount() {
        return mCount;
    }

    public void setmCount(String mCount) {
        this.mCount = mCount;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getImg() {
        return mImg;
    }

    public void setImg(String img) {
        mImg = img;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

}
