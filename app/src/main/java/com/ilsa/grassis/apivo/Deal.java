package com.ilsa.grassis.apivo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SohailZahid on 5/4/2017.
 */

public class Deal {

    private String id;

    private String title;

    private String expiration;

    private String description;

    @SerializedName("background")
    private Background2 background;

    private String dispensary_id;

    private String deal_type;

    private String foreground_color;
    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getExpiration ()
    {
        return expiration;
    }

    public void setExpiration (String expiration)
    {
        this.expiration = expiration;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public Background2 getBackground ()
    {
        return background;
    }

    public void setBackground (Background2 background)
    {
        this.background = background;
    }

    public String getDispensary_id ()
    {
        return dispensary_id;
    }

    public void setDispensary_id (String dispensary_id)
    {
        this.dispensary_id = dispensary_id;
    }

    public String getDeal_type ()
    {
        return deal_type;
    }

    public void setDeal_type (String deal_type)
    {
        this.deal_type = deal_type;
    }

    public String getForeground_color ()
    {
        return foreground_color;
    }

    public void setForeground_color (String foreground_color)
    {
        this.foreground_color = foreground_color;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", title = "+title+", expiration = "+expiration+", description = "+description+", background = "+background+", dispensary_id = "+dispensary_id+", deal_type = "+deal_type+", foreground_color = "+foreground_color+"]";
    }
}
