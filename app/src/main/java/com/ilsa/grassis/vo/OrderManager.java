package com.ilsa.grassis.vo;

import com.ilsa.grassis.apivo.Dispensary;
import com.ilsa.grassis.apivo.Products;

import java.util.ArrayList;

/**
 * Created by Zeeshan Ali Basbasah on 01-Jun-17.
 */

public class OrderManager {
    Dispensary dispensary;
    ArrayList<Products> productses;

    public OrderManager() {
        productses = new ArrayList<>();
        dispensary = new Dispensary();
    }

    public Dispensary getDispensary() {
        return dispensary;
    }

    public void setDispensary(Dispensary dispensary) {
        this.dispensary = dispensary;
    }

    public ArrayList<Products> getProductses() {
        return productses;
    }

    public void setProductses(ArrayList<Products> productses) {
        this.productses = productses;
    }
}
