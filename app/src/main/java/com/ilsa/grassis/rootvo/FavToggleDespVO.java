package com.ilsa.grassis.rootvo;

import com.google.gson.annotations.SerializedName;
import com.ilsa.grassis.apivo.Dispensary;


public class FavToggleDespVO {
    @SerializedName("dispensary")
    private Dispensary dispensary;

    public Dispensary getDispensary() {
        return dispensary;
    }

    public void setDispensary(Dispensary dispensaries) {
        this.dispensary = dispensaries;
    }
}
