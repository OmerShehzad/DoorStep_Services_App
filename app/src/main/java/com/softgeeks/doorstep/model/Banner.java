
package com.softgeeks.doorstep.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Banner {

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
