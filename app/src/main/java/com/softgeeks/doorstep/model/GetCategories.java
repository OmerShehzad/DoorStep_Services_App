package com.softgeeks.doorstep.model;

/**
 * Created by Ghulam Qadir on 29,June,2020
 */
public class GetCategories {
    //This model class is used for sending get categories request  to backend
    String type;

    public GetCategories(String type) {
        this.type=type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type=type;
    }
}
