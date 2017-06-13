package com.ilsa.grassis.library;

import android.app.Activity;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import com.crashlytics.android.Crashlytics;
import com.ilsa.grassis.apivo.Dispensary;
import com.ilsa.grassis.rootvo.NearByVo;
import com.ilsa.grassis.rootvo.UserDataVO;
import com.ilsa.grassis.unknow.Dispensaries;
import com.ilsa.grassis.vo.MenuCategoriesVO;
import com.ilsa.grassis.vo.OrderManager;
import com.ilsa.grassis.vo.OrderUserProducts;

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
    public static OrderManager orderManager;
    public static OrderUserProducts orderUserProducts;

    public static ArrayList<Activity> DeadActivities;
    public static ArrayList<Dispensaries> FavDispensariesIds;
    public static ArrayList<Dispensary> FavDispensaries;

    public static ArrayList<MenuCategoriesVO> MenuCategories;
    public String[] CategoryName = {"", "Indica", "Sativa", "Hybrid", "Extract", "Edible", "Topicals", "Grow", "Gear"};


    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        DeadActivities = new ArrayList<>();
        FavDispensariesIds = new ArrayList<>();
        FavDispensaries = new ArrayList<>();
        MenuCategories = new ArrayList<>();
        PopulateMenuCategories(MenuCategories);
        orderManager = new OrderManager();
        orderUserProducts = new OrderUserProducts();

    }


    private void PopulateMenuCategories(ArrayList<MenuCategoriesVO> menuCategories) {

        for (int i = 1; i < CategoryName.length; i++) {
            menuCategories.add(new MenuCategoriesVO(i, CategoryName[i]));
        }
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }
}