package com.ilsa.grassis.apivo;

/**
 * Created by SohailZahid on 5/4/2017.
 */

public class Products {
    private Pricing[] pricing;

    private String id;

    private String cbd;

    private String updated_at;

    private String thc;

    private String description;

    private Background background;

    private String name;

    private String cbn;

    private String dispensary_id;

    private String created_at;

    private String product_category_id;

    public Pricing[] getPricing() {
        return pricing;
    }

    public void setPricing(Pricing[] pricing) {
        this.pricing = pricing;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String

    getCbd() {
        return cbd;
    }

    public void setCbd(String cbd) {
        this.cbd = cbd;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String

    getThc() {
        return thc;
    }

    public void setThc(String thc) {
        this.thc = thc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Background getBackground() {
        return background;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String

    getCbn() {
        return cbn;
    }

    public void setCbn(String cbn) {
        this.cbn = cbn;
    }

    public String getDispensary_id() {
        return dispensary_id;
    }

    public void setDispensary_id(String dispensary_id) {
        this.dispensary_id = dispensary_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getProduct_category_id() {
        return product_category_id;
    }

    public void setProduct_category_id(String product_category_id) {
        this.product_category_id = product_category_id;
    }

    @Override
    public String toString() {
        return "ClassPojo [pricing = " + pricing + ", id = " + id + ", cbd = " + cbd + ", updated_at = " + updated_at + ", thc = " + thc + ", description = " + description + ", background = " + background + ", name = " + name + ", cbn = " + cbn + ", dispensary_id = " + dispensary_id + ", created_at = " + created_at + ", product_category_id = " + product_category_id + "]";
    }
}
