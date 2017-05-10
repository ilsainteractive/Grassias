package com.ilsa.grassis.rootvo;

import com.google.gson.annotations.SerializedName;
import com.ilsa.grassis.apivo.Background;
import com.ilsa.grassis.apivo.Pricing;
import com.ilsa.grassis.apivo.Products;

/**
 * Created by Zeeshan Ali Basbasah on 5/8/2017.
 */


public class ProductList {

    Products products;

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }
}