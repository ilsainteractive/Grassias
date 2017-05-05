package com.ilsa.grassis.apivo;

import com.google.gson.annotations.SerializedName;
import com.ilsa.grassis.apivo.Avatar;
import com.ilsa.grassis.apivo.Favorites;

/**
 * Created by Zeeshan Ali Basbasah on 5/4/2017.
 */

public class User {

    @SerializedName("id")
    private String id;
    @SerializedName("email")
    private String email;
    @SerializedName("dispensary_id")
    private String dispensary_id;
    @SerializedName("username")
    private String username;
    @SerializedName("first_name")
    private String first_name;
    @SerializedName("last_name")
    private String last_name;
    @SerializedName("card_id")
    private String card_id;
    @SerializedName("doctor")
    private String doctor;
    @SerializedName("issued_at")
    private String issued_at;
    @SerializedName("expires_at")
    private String expires_at;
    @SerializedName("born_at")
    private String born_at;
    @SerializedName("verified")
    private String verified;
    @SerializedName("phone_number")
    private String phone_number;
    @SerializedName("favorites")
    private Favorites favorites;
    @SerializedName("background")
    private Background background;
    @SerializedName("avatar")
    private Avatar avatar;
    @SerializedName("points_balance")
    private String points_balance;
    @SerializedName("token")
    private String token;
    @SerializedName("access_token")
    private String access_token;
    @SerializedName("state_change")
    private String state_change;


    public String getIssued_at() {
        return issued_at;
    }

    public void setIssued_at(String issued_at) {
        this.issued_at = issued_at;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getBorn_at() {
        return born_at;
    }

    public void setBorn_at(String born_at) {
        this.born_at = born_at;
    }

    public String getDispensary_id() {
        return dispensary_id;
    }

    public void setDispensary_id(String dispensary_id) {
        this.dispensary_id = dispensary_id;
    }

    public Favorites getFavorites() {
        return favorites;
    }

    public void setFavorites(Favorites favorites) {
        this.favorites = favorites;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(String expires_at) {
        this.expires_at = expires_at;
    }

    public String getState_change() {
        return state_change;
    }

    public void setState_change(String state_change) {
        this.state_change = state_change;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPoints_balance() {
        return points_balance;
    }

    public void setPoints_balance(String points_balance) {
        this.points_balance = points_balance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public Background getBackground() {
        return background;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    @Override
    public String toString() {
        return "ClassPojo [issued_at = " + issued_at + ", phone_number = " + phone_number + ", born_at = " + born_at + ", dispensary_id = " + dispensary_id + ", favorites = " + favorites + ", avatar = " + avatar + ", card_id = " + card_id + ", doctor = " + doctor + ", id = " + id + ", first_name = " + first_name + ", username = " + username + ", expires_at = " + expires_at + ", state_change = " + state_change + ", token = " + token + ", points_balance = " + points_balance + ", email = " + email + ", verified = " + verified + ", background = " + background + ", last_name = " + last_name + ", access_token = " + access_token + "]";
    }
}
