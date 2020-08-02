package com.softgeeks.doorstep.model;

/**
 * Created by Ghulam Qadir on 02,August,2020
 */
public class GetServices {
    String categoryID;

    public GetServices(String categoryID) {
        this.categoryID=categoryID;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID=categoryID;
    }
}
