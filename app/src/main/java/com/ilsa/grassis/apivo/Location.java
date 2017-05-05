package com.ilsa.grassis.apivo;

/**
 * Created by SohailZahid on 5/4/2017.
 */

public class Location {
    private Coords coords;

    private String address;

    private String zipcode;

    private String state;

    private String nearby_radius;

    private String city;

    public Coords getCoords() {
        return coords;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNearby_radius() {
        return nearby_radius;
    }

    public void setNearby_radius(String nearby_radius) {
        this.nearby_radius = nearby_radius;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "ClassPojo [coords = " + coords + ", address = " + address + ", zipcode = " + zipcode + ", state = " + state + ", nearby_radius = " + nearby_radius + ", city = " + city + "]";
    }

}
