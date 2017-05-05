package com.ilsa.grassis.apivo;

/**
 * Created by SohailZahid on 5/4/2017.
 */

public class Shades {
    private String tabbar;
    public String getTabbar ()
    {
        return tabbar;
    }

    public void setTabbar (String tabbar)
    {
        this.tabbar = tabbar;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [tabbar = "+tabbar+"]";
    }
}
