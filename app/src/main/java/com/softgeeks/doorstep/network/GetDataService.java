package com.softgeeks.doorstep.network;




import com.softgeeks.doorstep.model.GetCategories;
import com.softgeeks.doorstep.model.GetServices;
import com.softgeeks.doorstep.model.Login;
import com.softgeeks.doorstep.model.Services;
import com.softgeeks.doorstep.model.User;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Ghulam Qadir on 12,July,2019
 */


//all endpoints are mentioned in this class
public interface GetDataService {

     //"login" endpoint represent the login use case, all user data is passed in model class of login in json format, In this use case a created user verified through db and allow to enter into app.
    @POST("login")
    Call<Object> getLoginInstance(@Body Login login);

    //"sign-up" endpoint represent the sign up use case, all user data is passed in model class of User in json format, In this use case a new user created.
    @POST("sign-up")
    Call<Object> getRegisterInstance(@Body User user);

    // This endpoint get the main categories from backend data base and display on home screen.
    @POST("get-categories")
    Call<Object> getCategoriesInstance(@Body GetCategories getCategories);

    // This endpoint get the services according to each categories from backend data base and display on services screen.
    @POST("get-services")
    Call<Object> getServicesInstance(@Body GetServices getServices);
}

