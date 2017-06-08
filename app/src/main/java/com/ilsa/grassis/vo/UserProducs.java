package com.ilsa.grassis.vo;

import com.ilsa.grassis.apivo.Product;

/**
 * Created by Zeeshan Ali Basbasah on 05-Jun-17.
 */

public class UserProducs {

    private String product_id;
    private String dispensary_id;
    private String quantity;
    private String price;

    public UserProducs() {
    }

    public String getProduct_id() {

        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getDispensary_id() {
        return dispensary_id;
    }

    public void setDispensary_id(String dispensary_id) {
        this.dispensary_id = dispensary_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
