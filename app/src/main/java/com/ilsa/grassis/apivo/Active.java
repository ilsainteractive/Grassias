package com.ilsa.grassis.apivo;

/**
 * Created by Zeeshan Ali Basbasah on 5/8/2017.
 */

public class Active {
    private String small;

    private String large;

    private String medium;

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    @Override
    public String toString() {
        return "ClassPojo [small = " + small + ", large = " + large + ", medium = " + medium + "]";
    }
}