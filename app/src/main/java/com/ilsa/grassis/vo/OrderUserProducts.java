package com.ilsa.grassis.vo;

import java.util.ArrayList;

/**
 * Created by Zeeshan Ali Basbasah on 05-Jun-17.
 */

public class OrderUserProducts {
    private Order order;
    private ArrayList<UserProducs> userProducs;

    public OrderUserProducts() {

        order = new Order();
        userProducs = new ArrayList<>();
    }

    public Order getOrder() {

        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ArrayList<UserProducs> getUserProducs() {
        return userProducs;
    }

    public void setUserProducs(ArrayList<UserProducs> userProducs) {
        this.userProducs = userProducs;
    }
}
