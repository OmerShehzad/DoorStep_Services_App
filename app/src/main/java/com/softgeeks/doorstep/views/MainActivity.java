package com.softgeeks.doorstep.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.softgeeks.doorstep.R;
import com.softgeeks.doorstep.network.SessionStoreManager;

// This is base Activity named Main Activity , A launcher screen on which all navigation of different fragments are though his screen.
//All base methods which are mostly used in whole app are defined here and we can call these methods at every where in app
public class MainActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    public SessionStoreManager mSessionStoreManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        //Initialize progress dialogue object
        progressDialog=new ProgressDialog (this);
        //Initialize session store manager object
        mSessionStoreManager=new SessionStoreManager (getApplicationContext ());

        // Initialize navigation controller objects and decide according to session store manager key value if isLogin key value is true than new screen appears will be home navigation
        //other wise welcome screen
        NavController navController=Navigation.findNavController (this, R.id.mainNavHostFragment);
        NavGraph navGraph=navController.getNavInflater ().inflate (R.navigation.main_navigation);
        if (!mSessionStoreManager.getBooleanData (getString (R.string.isLogin))) {
            navGraph.setStartDestination (R.id.main_navigation);
        } else {
            navGraph.setStartDestination (R.id.home_navigation);
        }

        navController.setGraph (navGraph);
    }
//show progress dialogues shall be called anywhere in the app for showing dialogue where required
    public void showProgressDialog(String title, String msg, Context context) {
        progressDialog.setTitle (title);
        progressDialog.setMessage (msg);
        progressDialog.setCancelable (false);
        progressDialog.show ();
    }
// on showing dialogue if required to hide you can call this method any where in the app
    public void hideProgressDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing ()) {
                progressDialog.dismiss ();
            }
        }
    }

// This method return info abut whether internet is connected or not on the basis of network info
    public Boolean getInternetConnection() {
        if (isNetworkStatusAvialable (getApplicationContext ())) {
            return true;

        } else {
            Toast.makeText (getApplicationContext (), "No Internet Connected, Please check your Internet Connection", Toast.LENGTH_SHORT).show ();
            return false;
        }
    }

    public static boolean isNetworkStatusAvialable(Context context) {
        ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService (Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfos=connectivityManager.getActiveNetworkInfo ();
            if (netInfos != null) {
                return netInfos.isConnected ();
            }
        }
        return false;
    }
}
