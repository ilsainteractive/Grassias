package com.ilsa.grassis.rootvo;

import com.google.gson.annotations.SerializedName;
import com.ilsa.grassis.apivo.Dispensaries;
import com.ilsa.grassis.apivo.Products;
import com.ilsa.grassis.apivo.Strands;

import java.util.ArrayList;

/**
 * Created by SohailZahid on 5/4/2017.
 */

public class NearByVo {


    @SerializedName("dispensaries")
    private ArrayList<Dispensaries> dispensaries;

    @SerializedName("strands")
    private ArrayList<Strands> strands;

    @SerializedName("products")
    private ArrayList<Products> products;

    public NearByVo() {
    }

    public ArrayList<Dispensaries> getDispensaries() {
        return dispensaries;
    }

    public void setDispensaries(ArrayList<Dispensaries> dispensariess) {
        this.dispensaries = dispensariess;
    }

    public ArrayList<Strands> getStrands() {
        return strands;
    }

    public void setStrands(ArrayList<Strands> strands) {
        this.strands = strands;
    }

    public ArrayList<Products> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Products> productses) {
        this.products = productses;
    }
}
