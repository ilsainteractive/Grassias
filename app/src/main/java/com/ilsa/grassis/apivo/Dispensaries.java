package com.ilsa.grassis.apivo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SohailZahid on 5/4/2017.
 */

public class Dispensaries {

    @SerializedName("dispensary")
    private Dispensary dispensary;

    public Dispensary getDispensary() {
        return dispensary;
    }

    public void setDispensary(Dispensary dispensaries) {
        this.dispensary = dispensaries;
    }
}
