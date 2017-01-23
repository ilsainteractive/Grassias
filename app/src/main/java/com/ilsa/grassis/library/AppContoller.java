package com.ilsa.grassis.library;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

/**
 * App contoller class to mange application lifecycle
 */
public class AppContoller extends MultiDexApplication {

    /**
     * The constant IsLoggedIn.
     */
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