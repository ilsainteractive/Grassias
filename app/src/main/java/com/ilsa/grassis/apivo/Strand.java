package com.ilsa.grassis.apivo;

/**
 * Created by SohailZahid on 5/4/2017.
 */

public class Strand {
    private String template;

    private String symbol;

    private String review_count;

    private String status;

    private String[] prefixes;

    private String id;

    private String category;

    private String favorited;

    private String description;

    private Layout layout;

    private String name;

    private Background background;

    private Flavors[] flavors;

    private String rating;

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getReview_count() {
        return review_count;
    }

    public void setReview_count(String review_count) {
        this.review_count = review_count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFavorited() {
        return favorited;
    }

    public void setFavorited(String favorited) {
        this.favorited = favorited;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Background getBackground() {
        return background;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public Flavors[] getFlavors() {
        return flavors;
    }

    public void setFlavors(Flavors[] flavors) {
        this.flavors = flavors;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "ClassPojo [template = " + template + ", symbol = " + symbol + ", review_count = " + review_count + ", status = " + status + ", prefixes = " + prefixes + ", id = " + id + ", category = " + category + ", favorited = " + favorited + ", description = " + description + ", layout = " + layout + ", name = " + name + ", background = " + background + ", flavors = " + flavors + ", rating = " + rating + "]";
    }
}
