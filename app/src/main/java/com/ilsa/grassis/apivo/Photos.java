package com.ilsa.grassis.apivo;

/**
 * Created by SohailZahid on 5/4/2017.
 */

public class Photos {
    private Photo photo;

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "ClassPojo [photo = " + photo + "]";
    }
}
