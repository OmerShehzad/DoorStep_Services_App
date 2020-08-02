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


    @POST("login")
    Call<Object> getLoginInstance(@Body Login login);
    @POST("sign-up")
    Call<Object> getRegisterInstance(@Body User user);


    @POST("get-categories")
    Call<Object> getCategoriesInstance(@Body GetCategories getCategories);

    @POST("getServices")
    Call<Object> getServicesInstance(@Body GetServices getServices);
}

