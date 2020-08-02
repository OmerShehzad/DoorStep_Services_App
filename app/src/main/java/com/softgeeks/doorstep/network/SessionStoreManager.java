package com.softgeeks.doorstep.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ghulam Qadir on 22,January,2020
 */
public class SessionStoreManager {

    private String TAG="--> Session Store Manager", mShared="mSharedData";
    private Context mContext;
    private SharedPreferences mSharedPref;

    public SessionStoreManager(Context context) {
        mContext=context;
        mSharedPref=context.getSharedPreferences (mShared, Context.MODE_PRIVATE);
    }

    //Save String data to Shared Prefs
    public boolean saveStringData(String key, String value) {
        SharedPreferences.Editor editor=mSharedPref.edit ();
        editor.putString (key, value);
        return editor.commit ();
    }

    public String getStringData(String key) {
        String value=mSharedPref.getString (key, "");
        return value;
    }

    //Save Boolean data to Shared Prefs
    public boolean saveBooleanData(String key, Boolean hasValidValue) {
        SharedPreferences.Editor editor=mSharedPref.edit ();
        editor.putBoolean (key, hasValidValue);
        return editor.commit ();
    }

    public Boolean getBooleanData(String key) {
        Boolean hasValidValue=mSharedPref.getBoolean (key, false);
        return hasValidValue;
    }

    //Save Integer data to Shared Prefs
     public boolean saveIntegerData(String key,int value) {
        SharedPreferences.Editor editor=mSharedPref.edit ();
        editor.putInt (key, value);
        return editor.commit ();
    }

    public int getIntegerData(String key) {
        int value=mSharedPref.getInt (key, 0);
        return value;
    }


    //Save float data to Shared Prefs

    public boolean setFloatData(String key,float value) {
        SharedPreferences.Editor editor=mSharedPref.edit ();
        editor.putFloat (key, value);
        return editor.commit ();
    }

    public float getFloatData(String key) {
        float top=mSharedPref.getFloat (key, 0.0f);
        return top;
    }




    //Save Bitmap data to Shared Prefs

    public void saveBitmapFile(Bitmap bitmapFile,String fileName) {
        File destination=new File (getDataPath () + "/" + File.separator + fileName + ".jpg");
        try {
            FileOutputStream fos=new FileOutputStream (destination);
            bitmapFile.compress (Bitmap.CompressFormat.PNG, 90, fos);
            fos.close ();
        } catch (FileNotFoundException e) {
            Log.d (TAG, "File not found: " + e.getMessage ());
        } catch (IOException e) {
            Log.d (TAG, "Error accessing file: " + e.getMessage ());
        }
    }

    public Bitmap getBitmapFile(String fileName) {
        String destination=getDataPath () + "/" + File.separator + fileName + ".jpg";
        File imgFile=new File (destination);
        if (imgFile.exists ()) {
            return BitmapFactory.decodeFile (imgFile.getAbsolutePath ());
        }
        return null;
    }



    private String getDataPath() {
        File dataDir=mContext.getApplicationContext ().getDir ("wallet", MODE_PRIVATE);
        String targetPath=dataDir.getAbsolutePath () + File.separator + "data";
        File dir=new File (targetPath);
        if (!dir.exists ())
            dir.mkdir ();

        return targetPath;
    }


}
