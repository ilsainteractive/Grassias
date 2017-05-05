package com.ilsa.grassis.loginApi;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Zeeshan Ali Basbasah on 5/4/2017.
 */


public class Favorites
{
    @SerializedName("dispensaries")
    private Dispensaries[] dispensaries;
    @SerializedName("strands")
    private String[] strands;

    public Dispensaries[] getDispensaries ()
    {
        return dispensaries;
    }

    public void setDispensaries (Dispensaries[] dispensaries)
    {
        this.dispensaries = dispensaries;
    }

    public String[] getStrands ()
    {
        return strands;
    }

    public void setStrands (String[] strands)
    {
        this.strands = strands;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [dispensaries = "+dispensaries+", strands = "+strands+"]";
    }
}
