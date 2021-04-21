package com.softgeeks.doorstep.network;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;


import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;

import com.google.gson.internal.LinkedTreeMap;
import com.softgeeks.doorstep.BuildConfig;
import com.softgeeks.doorstep.R;
import com.softgeeks.doorstep.views.MainActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Ghulam Qadir on 12,July,2019
 */

//This class basically contains all request basic content what is required to make a request .like header and creates a base url by adding header into it
//and is used during API calls in Call Utils class.Header data will be "key is X-Authorization and it has specific value which verifies from server that user is valid and the request is not a spam"
public class RetrofitClientInstance {

    private static Retrofit retrofitInstance, retrofitLoginInstance;

    public static OkHttpClient.Builder provideOkHttpClientDefault(final Context mContext) {
        OkHttpClient.Builder httpClient=new OkHttpClient.Builder ();
        httpClient.addInterceptor (new Interceptor () {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Request original=chain.request ();
                Request request=original.newBuilder ()
                        .header ("X-Authorization", "rAnkwPPpVZk4MFuuu2q2tha0GB76RkEIcnJxLahpNwQ83DSydpIj9bnl36G5Bhl5")
                        .method (original.method (), original.body ())
                        .build ();

                return chain.proceed (request);
            }
        });
        return httpClient;
    }

    public static OkHttpClient.Builder provideOkHttpClientLogin(final Context mContext) {
        OkHttpClient.Builder httpClient=new OkHttpClient.Builder ();
        final SessionStoreManager mSessionStoreManager=new SessionStoreManager (mContext);
        httpClient.addInterceptor (new Interceptor () {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Request original=chain.request ();
                Request request=original.newBuilder ()
                        .header ("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9sb2NhbGhvc3Q6ODFcL2Z5cFwvcHVibGljXC9hcGlcL2xvZ2luIiwiaWF0IjoxNTkzMjAxNDgyLCJuYmYiOjE1OTMyMDE0ODIsImp0aSI6Ik1odjVOTFhFU0p6d1k3RDciLCJzdWIiOjEsInBydiI6ImVkMTU1ODRmYzY2YTY2NWIwYmM5MzFlNDE5MDNlMDdmYWY3YjJiMTAiLCJ1c2VyX2lkIjoxfQ.SjISgLZFOsLc6GfwUz46c_gXV24NuKVpvRAUA4xinHc")
                        .method (original.method (), original.body ())
                        .build ();

                return chain.proceed (request);
            }
        });
        return httpClient;
    }

    public static Retrofit getRetrofitInstance(Context mContext) {

// This retrofit client Header , URL method class is used before login , Hre Base URL is defined in build.gradle app file
        OkHttpClient.Builder httpClient=provideOkHttpClientDefault (mContext);
        OkHttpClient client=httpClient.build ();
        retrofitInstance=new Retrofit.Builder ()
                .baseUrl (BuildConfig.BASEURL)
                .addConverterFactory (GsonConverterFactory.create ())
                .client (client)
                .build ();


        return retrofitInstance;
    }

    public static Retrofit getRetrofitLoginInstance(Context mContext) {
// This retrofit client Header , URL method class is used after login , Hre Base URL is defined in build.gradle app file for getting categories and services etc

        OkHttpClient.Builder httpClient=provideOkHttpClientLogin (mContext);
        OkHttpClient client=httpClient.build ();
        retrofitLoginInstance=new Retrofit.Builder ()
                .baseUrl (BuildConfig.BASEURL)
                .addConverterFactory (GsonConverterFactory.create ())
                .client (client)
                .build ();


        return retrofitLoginInstance;
    }


}

