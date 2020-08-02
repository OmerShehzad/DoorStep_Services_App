package com.softgeeks.doorstep.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.softgeeks.doorstep.R;
import com.softgeeks.doorstep.network.SessionStoreManager;

public class MainActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    String TAG="--> Main Activity Session: ";
    public SessionStoreManager mSessionStoreManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        progressDialog=new ProgressDialog (this);
        mSessionStoreManager=new SessionStoreManager (getApplicationContext ());
        NavController navController=Navigation.findNavController (this, R.id.mainNavHostFragment);
        NavGraph navGraph=navController.getNavInflater ().inflate (R.navigation.main_navigation);
        if(!mSessionStoreManager.getBooleanData (getString (R.string.isLogin))){
            navGraph.setStartDestination (R.id.main_navigation);
        }
        else {
            navGraph.setStartDestination (R.id.home_navigation);
        }

        navController.setGraph (navGraph);
    }

    public void showProgressDialog(String title, String msg, Context context) {
        progressDialog.setTitle (title);
        progressDialog.setMessage (msg);
        progressDialog.setCancelable (false);
        progressDialog.show ();
    }

    public void hideProgressDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing ()) {
                progressDialog.dismiss ();
            }
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed ();
    }
}
