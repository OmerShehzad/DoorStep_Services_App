package com.softgeeks.doorstep.model;

/**
 * Created by Ghulam Qadir on 28,June,2020
 */
public class Login {
    //This model class is used while sending /verifying user data in Login API
    String email,password,fcm_token;

    public Login(String email, String password, String fcm_token) {
        this.email=email;
        this.password=password;
        this.fcm_token=fcm_token;
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

    public String getFcm_token() {
        return fcm_token;
    }

    public void setFcm_token(String fcm_token) {
        this.fcm_token=fcm_token;
    }
}
