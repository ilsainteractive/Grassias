package com.ilsa.grassis.apivo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SohailZahid on 5/4/2017.
 */

public class Dispensaries {

    @SerializedName("dispensary")
    private Dispensary dispensary;

    public Dispensary getDispensaries() {
        return dispensary;
    }

    public void setDispensaries(Dispensary dispensaries) {
        this.dispensary = dispensaries;
    }
}
