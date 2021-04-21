package com.softgeeks.doorstep.model;

/**
 * Created by Ghulam Qadir on 02,August,2020
 */
public class GetServices {
    //This model class is used in API for getting services . In this model class we send category id param .
    String catID;

    public GetServices(String catID) {
        this.catID=catID;
    }

    public String getcatID() {
        return catID;
    }

    public void setcatID(String catID) {
        this.catID=catID;
    }
}
