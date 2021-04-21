
package com.softgeeks.doorstep.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Banner {
    //This model class is used for showing banner on top .All getter and setter methods will be used for getting& storing all image drawables

    @SerializedName("banner_img")
    private int mBannerImg;

    public Banner(int mBannerImg) {
        this.mBannerImg=mBannerImg;
    }

    public int getBannerImg() {
        return mBannerImg;
    }

    public void setBannerImg(int bannerImg) {
        mBannerImg = bannerImg;
    }

}
