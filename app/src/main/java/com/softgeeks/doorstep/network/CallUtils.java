package com.softgeeks.doorstep.network;


import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;

import com.google.gson.internal.LinkedTreeMap;
import com.softgeeks.doorstep.R;
import com.softgeeks.doorstep.model.Login;
import com.softgeeks.doorstep.model.User;
import com.softgeeks.doorstep.views.MainActivity;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ghulam Qadir on 24,July,2019
 */


public class CallUtils {
String TAG="--> Call Utils";

    public void showingErrorMessage(Context context, ResponseBody errorBody, String message) {
        try {
            String error=errorBody.string ();
            String msg=new JSONObject (error).getString ("error");
            Toast.makeText (context, msg, Toast.LENGTH_LONG).show ();
        } catch (IOException e) {
            Toast.makeText (context, message, Toast.LENGTH_SHORT).show ();
            e.printStackTrace ();
        } catch (JSONException e) {
            e.printStackTrace ();
            Toast.makeText (context, message, Toast.LENGTH_SHORT).show ();
        }
    }
    public void uidLogin(final NavController actionHomeNavController, final  Context context, String email, String pin) {
        ((MainActivity) context).showProgressDialog ("", "Verifying credentials..", context);
        GetDataService service=RetrofitClientInstance.getRetrofitInstance (context).create (GetDataService.class);
        Call<Object> call=service.getLoginInstance (new Login (email, pin, "dasdr5wetrer45646"));
        call.enqueue (new Callback<Object> () {
            @RequiresApi(api=Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                ((MainActivity) context).hideProgressDialog ();
                if (response.isSuccessful ()) {
                    if (response.body () != null) {
                        try {
                            if(new JSONObject ((LinkedTreeMap) response.body ()).get ("Code").toString ().equalsIgnoreCase ("401.0")){
                                Toast.makeText (context, new JSONObject ((LinkedTreeMap) response.body ()).get ("Message").toString (), Toast.LENGTH_SHORT).show ();
                            }
                            else {

                                ((MainActivity)context).mSessionStoreManager.saveStringData (context.getString (R.string.txtOwnerName),new JSONObject ((LinkedTreeMap) response.body ()).getString ("name"));
                                ((MainActivity)context).mSessionStoreManager.saveStringData (context.getString (R.string.txtOwnerEmail),new JSONObject ((LinkedTreeMap) response.body ()).getString ("email"));
                                ((MainActivity)context).mSessionStoreManager.saveStringData (context.getString (R.string.txtOwnerMobile),new JSONObject ((LinkedTreeMap) response.body ()).getString ("mobile"));
                                ((MainActivity)context).mSessionStoreManager.saveStringData (context.getString (R.string.txtOwnerCity),new JSONObject ((LinkedTreeMap) response.body ()).getString ("city"));
                                ((MainActivity)context).mSessionStoreManager.saveStringData (context.getString (R.string.txtOwnerArea),new JSONObject ((LinkedTreeMap) response.body ()).getString ("area"));
                                ((MainActivity)context).mSessionStoreManager.saveStringData (context.getString (R.string.txtOwnerAddress),new JSONObject ((LinkedTreeMap) response.body ()).getString ("address"));
                                ((MainActivity)context).mSessionStoreManager.saveStringData (context.getString (R.string.txtOwnerCountry),new JSONObject ((LinkedTreeMap) response.body ()).getString ("country"));
                                ((MainActivity)context).mSessionStoreManager.saveStringData (context.getString (R.string.txtOwnerType),new JSONObject ((LinkedTreeMap) response.body ()).getString ("type"));
                                ((MainActivity)context).mSessionStoreManager.saveStringData (context.getString (R.string.txtOwnerAuthKey),new JSONObject ((LinkedTreeMap) response.body ()).getString ("auth_key"));

                                ((MainActivity)context).mSessionStoreManager.saveBooleanData (context.getString (R.string.isLogin),true);
                                ((MainActivity)context).mSessionStoreManager.saveStringData (context.getString (R.string.loginSession),"user");
                                actionHomeNavController.navigate (R.id.action_login_to_home);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace ();
                        }



                    } else {
                        Toast.makeText (context, context.getString (R.string.uidErrorVerifyingCredentials), Toast.LENGTH_SHORT).show ();
                    }

                } else {

                    new CallUtils ().showingErrorMessage (context, response.errorBody (), context.getString (R.string.uidErrorVerifyingCredentials));
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                ((MainActivity) context).hideProgressDialog ();
                Toast.makeText (context, context.getString (R.string.uidErrorVerifyingCredentials), Toast.LENGTH_SHORT).show ();
                Log.d (TAG,  t.toString ());
            }
        });
    }

    public void uidSignUp(String mobileNo,String city,String area,String country,String userName,String address,final NavController actionHomeNavController, final  Context context, String email, String pin) {
        ((MainActivity) context).showProgressDialog ("", "Creating users..", context);
        GetDataService service=RetrofitClientInstance.getRetrofitInstance (context).create (GetDataService.class);
        Call<Object> call=service.getRegisterInstance (new User (userName,email, pin,mobileNo ,city,area,country,address,"dasdr5wetrer45646","rider"));
        call.enqueue (new Callback<Object> () {
            @RequiresApi(api=Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                ((MainActivity) context).hideProgressDialog ();
                if (response.isSuccessful ()) {
                    if (response.body () != null) {
                        try {
                            if(new JSONObject ((LinkedTreeMap) response.body ()).get ("Code").toString ().equalsIgnoreCase ("200.0")){
                                if(new JSONObject ((LinkedTreeMap) response.body ()).get ("Message").toString ().equalsIgnoreCase ("success")){
                                    actionHomeNavController.navigate (R.id.action_profile_to_login);
                                }
                                else {
                                    Toast.makeText (context, context.getString (R.string.uidErrorVerifyingCredentials), Toast.LENGTH_SHORT).show ();
                                }
                            }
                            else {
                                Toast.makeText (context, context.getString (R.string.uidErrorVerifyingCredentials), Toast.LENGTH_SHORT).show ();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace ();
                        }


                    } else {
                        Toast.makeText (context, context.getString (R.string.uidErrorVerifyingCredentials), Toast.LENGTH_SHORT).show ();
                    }

                } else {

                    new CallUtils ().showingErrorMessage (context, response.errorBody (), context.getString (R.string.uidErrorVerifyingCredentials));
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                ((MainActivity) context).hideProgressDialog ();
                Toast.makeText (context, context.getString (R.string.uidErrorVerifyingCredentials), Toast.LENGTH_SHORT).show ();
                Log.d (TAG,  t.toString ());
            }
        });
    }

}

