package com.ilsa.grassis.vo;

/**
 * Dispensary vo.
 */
public class MenuCategoriesVO {

    public MenuCategoriesVO() {
    }

    public MenuCategoriesVO(int id, String Name) {
        this.id = id;
        this.CategoryName = Name;
    }

    String CategoryName;

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;
}
