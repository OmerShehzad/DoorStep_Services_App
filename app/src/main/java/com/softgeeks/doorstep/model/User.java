package com.softgeeks.doorstep.model;

/**
 * Created by Ghulam Qadir on 29,June,2020
 */
public class User {
    //This model class contains all user data in which we can get all user data which is login or sign up anywhere in the app
    String user_name,email,password,mobile,city,area,country,address,fcm_token,type;

    public User(String user_name, String email, String password, String mobile, String city, String area, String country, String address, String fcm_token, String type) {
        this.user_name=user_name;
        this.email=email;
        this.password=password;
        this.mobile=mobile;
        this.city=city;
        this.area=area;
        this.country=country;
        this.address=address;
        this.fcm_token=fcm_token;
        this.type=type;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name=user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password=password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile=mobile;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city=city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area=area;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country=country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address=address;
    }

    public String getFcm_token() {
        return fcm_token;
    }

    public void setFcm_token(String fcm_token) {
        this.fcm_token=fcm_token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type=type;
    }
}
