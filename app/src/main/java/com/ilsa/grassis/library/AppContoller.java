package com.ilsa.grassis.library;

import android.app.Activity;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.ilsa.grassis.apivo.Dispensary;
import com.ilsa.grassis.apivo.UserVo;
import com.ilsa.grassis.rootvo.NearByVo;
import com.ilsa.grassis.rootvo.UserDataVO;
import com.ilsa.grassis.unknow.Dispensaries;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;

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
    public static ArrayList<Dispensaries> FavDispensariesIds;
    public static ArrayList<Dispensary> FavDispensaries;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        DeadActivities = new ArrayList<>();
        FavDispensariesIds = new ArrayList<>();
        FavDispensaries = new ArrayList<>();
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }
}