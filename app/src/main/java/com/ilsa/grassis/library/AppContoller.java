package com.ilsa.grassis.library;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

/**
 * Created by Ilsa on 1/4/2017.
 */

public class AppContoller extends MultiDexApplication {

    public static boolean IsLoggedIn;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }
}