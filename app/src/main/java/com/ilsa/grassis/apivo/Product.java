package com.ilsa.grassis.apivo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Zeeshan Ali Basbasah on 5/8/2017.
 */


public class Product
{
    private String id;

    @SerializedName("icon")
    private Icon2 icon2;

    private String title;

    private String points;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public Icon2 getIcon ()
    {
        return icon2;
    }

    public void setIcon (Icon2 icon)
    {
        this.icon2 = icon;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getPoints ()
    {
        return points;
    }

    public void setPoints (String points)
    {
        this.points = points;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", icon = "+icon2+", title = "+title+", points = "+points+"]";
    }
}
