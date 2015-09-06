package com.smartdevelopers.kandie.nicedrawer.app;

/**
 * Created by 4331 on 01/08/2015.
 */
import android.app.Application;
import android.content.Context;

public class VolleyImplement extends Application {
    private static VolleyImplement mInstance;
    private static Context mAppContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        this.setAppContext(getApplicationContext());
    }
    public static VolleyImplement getInstance(){
        return mInstance;
    }
    public static Context getAppContext() {
        return mAppContext;
    }
    public void setAppContext(Context mAppContext) {
        this.mAppContext = mAppContext;
    }


}