package com.softgeeks.doorstep.model;

/**
 * Created by Ghulam Qadir on 02,August,2020
 */
public class Services {
//This model class contains all services data get from backend database
    String serviceName,servicePrice,serviceDescription,serviceImgURl,serviceAvailableTime;

    public Services(String serviceName, String servicePrice, String serviceDescription, String serviceImgURl, String serviceAvailableTime) {
        this.serviceName=serviceName;
        this.servicePrice=servicePrice;
        this.serviceDescription=serviceDescription;
        this.serviceImgURl=serviceImgURl;
        this.serviceAvailableTime=serviceAvailableTime;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName=serviceName;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice=servicePrice;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription=serviceDescription;
    }

    public String getServiceImgURl() {
        return serviceImgURl;
    }

    public void setServiceImgURl(String serviceImgURl) {
        this.serviceImgURl=serviceImgURl;
    }

    public String getServiceAvailableTime() {
        return serviceAvailableTime;
    }

    public void setServiceAvailableTime(String serviceAvailableTime) {
        this.serviceAvailableTime=serviceAvailableTime;
    }
}
