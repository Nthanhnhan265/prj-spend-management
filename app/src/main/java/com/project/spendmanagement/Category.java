package com.project.spendmanagement;

public class Category {
    //khai bao
    private String ten_category;
    private String icon;

    public Category() {

    }

    public Category(String ten_category, String icon) {
        this.ten_category = ten_category;
        this.icon = icon;
    }

    public String getTen_category() {
        return ten_category;
    }

    public String getIcon() {
        return icon;
    }

    public void setTen_category(String ten_category) {
        this.ten_category = ten_category;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


}
