package com.ilsa.grassis.apivo;

/**
 * Created by SohailZahid on 5/4/2017.
 */

public class Coords {
    private double longitude;

    private double latitude;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "ClassPojo [longitude = " + longitude + ", latitude = " + latitude + "]";
    }
}
