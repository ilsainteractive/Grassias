package com.ilsa.grassis.apivo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Zeeshan Ali Basbasah on 5/8/2017.
 */

public class Reward {
    private String id;

    private Icon icon;

    private String title;

    private String expiration;

    private String description;

    @SerializedName("background")
    private Background2 background;

    private Stamps stamps;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Background2 getBackground() {
        return background;
    }

    public void setBackground(Background2 background) {
        this.background = background;
    }

    public Stamps getStamps() {
        return stamps;
    }

    public void setStamps(Stamps stamps) {
        this.stamps = stamps;
    }

    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", icon = " + icon + ", title = " + title + ", expiration = " + expiration + ", description = " + description + ", background = " + background + ", stamps = " + stamps + "]";
    }
}
