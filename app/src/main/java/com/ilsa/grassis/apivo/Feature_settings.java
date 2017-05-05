package com.ilsa.grassis.apivo;

/**
 * Created by SohailZahid on 5/4/2017.
 */

public class Feature_settings {

    private String gallery;

    private String events;

    private String newsletter;

    private String find_us;

    private String menu;

    private String rewards;

    private String points;

    private String specials;

    public String getGallery() {
        return gallery;
    }

    public void setGallery(String gallery) {
        this.gallery = gallery;
    }

    public String getEvents() {
        return events;
    }

    public void setEvents(String events) {
        this.events = events;
    }

    public String getNewsletter() {
        return newsletter;
    }

    public void setNewsletter(String newsletter) {
        this.newsletter = newsletter;
    }

    public String getFind_us() {
        return find_us;
    }

    public void setFind_us(String find_us) {
        this.find_us = find_us;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getRewards() {
        return rewards;
    }

    public void setRewards(String rewards) {
        this.rewards = rewards;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getSpecials() {
        return specials;
    }

    public void setSpecials(String specials) {
        this.specials = specials;
    }

    @Override
    public String toString() {
        return "ClassPojo [gallery = " + gallery + ", events = " + events + ", newsletter = " + newsletter + ", find_us = " + find_us + ", menu = " + menu + ", rewards = " + rewards + ", points = " + points + ", specials = " + specials + "]";
    }
}
