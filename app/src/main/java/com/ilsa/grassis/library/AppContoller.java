package com.ilsa.grassis.library;

import android.app.Activity;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.ilsa.grassis.rootvo.NearByVo;
import com.ilsa.grassis.rootvo.UserDataVO;

import java.util.ArrayList;

/**
 * App contoller class to mange application lifecycle
 */
public class AppContoller extends MultiDexApplication {

    /**
     * The constant IsLoggedIn.
     */
    public static boolean IsLoggedIn;
    public static UserDataVO userData;
    public static NearByVo nearByVo;

    public static ArrayList<Activity> DeadActivities;

    @Override
    public void onCreate() {
        super.onCreate();
        DeadActivities = new ArrayList<>();
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }
}