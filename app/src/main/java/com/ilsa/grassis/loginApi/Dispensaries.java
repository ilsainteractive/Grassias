package com.ilsa.grassis.loginApi;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Zeeshan Ali Basbasah on 5/4/2017.
 */

public class Dispensaries {

    @SerializedName("id")
    private String id;
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("dispensary_id")
    private String dispensary_id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDispensary_id() {
        return dispensary_id;
    }

    public void setDispensary_id(String dispensary_id) {
        this.dispensary_id = dispensary_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", dispensary_id = " + dispensary_id + ", user_id = " + user_id + "]";
    }
}
