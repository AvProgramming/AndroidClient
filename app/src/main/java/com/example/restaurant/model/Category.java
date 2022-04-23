package com.example.restaurant.model;

public class Category {
    private String title;
    private Integer picture;

    public Category(String title, Integer picture) {
        this.title = title;
        this.picture = picture;
    }

    public Category() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPicture() {
        return picture;
    }

    public void setPicture(Integer picture) {
        this.picture = picture;
    }
}
