package com.ilsa.grassis.apivo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SohailZahid on 5/4/2017.
 */

public class NearByVo {

    @SerializedName("dispensaries")
    private Dispensaries[] dispensaries;
    @SerializedName("strands")
    private Strands[] strands;
    @SerializedName("products")
    private Products[] products;

    public NearByVo() {
    }

    public Dispensaries[] getDispensaries() {
        return dispensaries;
    }

    public void setDispensaries(Dispensaries[] dispensaries) {
        this.dispensaries = dispensaries;
    }

    public Strands[] getStrands() {
        return strands;
    }

    public void setStrands(Strands[] strands) {
        this.strands = strands;
    }

    public Products[] getProductses() {
        return products;
    }

    public void setProductses(Products[] productses) {
        this.products = productses;
    }
}
