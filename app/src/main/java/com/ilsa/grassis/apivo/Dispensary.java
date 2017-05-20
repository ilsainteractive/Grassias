package com.ilsa.grassis.apivo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by SohailZahid on 5/4/2017.
 */

public class Dispensary {
    private Widgets widgets;

    private String instagram;
    private String state_change;
    private String phone;


    private Location location;

    private String[] prefixes;

    private String id;

    private String twitter;

    private String description;

    private Layout layout;

    private String facebook;

    private String name;

    private String youtube;

    private ArrayList<Features> features;

    private String apple_app_store_url;

    private Feature_settings feature_settings;

    private String template;

    private Logo logo;

    private Shades shades;

    private String store_url;

    private String status;

    private Colors colors;

    private ArrayList<String> channels;

    private String android_app_store_url;

    private String url;

    private ArrayList<Photos> photos;

    private Schedule schedule;

    private String email;

    @SerializedName("background")
    private Background2 background;

    public Widgets getWidgets() {
        return widgets;
    }

    public void setWidgets(Widgets widgets) {
        this.widgets = widgets;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String[] getPrefixes() {
        return prefixes;
    }

    public void setPrefixes(String[] prefixes) {
        this.prefixes = prefixes;
    }

    public String getId() {
        return id;
    }

    public String getState_change() {
        return state_change;
    }

    public void setState_change(String state_change) {
        this.state_change = state_change;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public ArrayList<Features> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<Features> features) {
        this.features = features;
    }

    public String getApple_app_store_url() {
        return apple_app_store_url;
    }

    public void setApple_app_store_url(String apple_app_store_url) {
        this.apple_app_store_url = apple_app_store_url;
    }

    public Feature_settings getFeature_settings() {
        return feature_settings;
    }

    public void setFeature_settings(Feature_settings feature_settings) {
        this.feature_settings = feature_settings;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Logo getLogo() {
        return logo;
    }

    public void setLogo(Logo logo) {
        this.logo = logo;
    }

    public Shades getShades() {
        return shades;
    }

    public void setShades(Shades shades) {
        this.shades = shades;
    }

    public String getStore_url() {
        return store_url;
    }

    public void setStore_url(String store_url) {
        this.store_url = store_url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Colors getColors() {
        return colors;
    }

    public void setColors(Colors colors) {
        this.colors = colors;
    }

    public ArrayList<String> getChannels() {
        return channels;
    }

    public void setChannels(ArrayList<String> channels) {
        this.channels = channels;
    }

    public String getAndroid_app_store_url() {
        return android_app_store_url;
    }

    public void setAndroid_app_store_url(String android_app_store_url) {
        this.android_app_store_url = android_app_store_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<Photos> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Photos> photos) {
        this.photos = photos;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Background2 getBackground() {
        return background;
    }

    public void setBackground(Background2 background) {
        this.background = background;
    }

    @Override
    public String toString() {
        return "ClassPojo [widgets = " + widgets + ", instagram = " + instagram + ", phone = " + phone + ", location = " + location + ", prefixes = " + prefixes + ", id = " + id + ", twitter = " + twitter + ", description = " + description + ", layout = " + layout + ", facebook = " + facebook + ", name = " + name + ", youtube = " + youtube + ", features = " + features + ", apple_app_store_url = " + apple_app_store_url + ", feature_settings = " + feature_settings + ", template = " + template + ", logo = " + logo + ", shades = " + shades + ", store_url = " + store_url + ", status = " + status + ", colors = " + colors + ", channels = " + channels + ", android_app_store_url = " + android_app_store_url + ", url = " + url + ", photos = " + photos + ", schedule = " + schedule + ", email = " + email + ", background = " + background + "]";
    }
}
