package com.ilsa.grassis.apivo;

/**
 * Created by Zeeshan Ali Basbasah on 5/10/2017.
 */

public class DealsMainobject {

    private Deal deal;

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    @Override
    public String toString() {
        return "ClassPojo [deal = " + deal + "]";
    }
}
