package com.ilsa.grassis.apivo;

/**
 * Created by SohailZahid on 5/4/2017.
 */

public class Colors {
    private String text;

    private String widget_background;

    private String link;

    private String background;

    private String block;

    private String widget_text;

    private String navbar;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getWidget_background() {
        return widget_background;
    }

    public void setWidget_background(String widget_background) {
        this.widget_background = widget_background;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getWidget_text() {
        return widget_text;
    }

    public void setWidget_text(String widget_text) {
        this.widget_text = widget_text;
    }

    public String getNavbar() {
        return navbar;
    }

    public void setNavbar(String navbar) {
        this.navbar = navbar;
    }

    @Override
    public String toString() {
        return "ClassPojo [text = " + text + ", widget_background = " + widget_background + ", link = " + link + ", background = " + background + ", block = " + block + ", widget_text = " + widget_text + ", navbar = " + navbar + "]";
    }
}
