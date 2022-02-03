
package com.example.restaurant.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("name")
    @Expose
    private String productName;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("img_url")
    @Expose
    private String imgUrl;
    @SerializedName("vegan")
    @Expose
    private Boolean vegan;
    @SerializedName("type")
    @Expose
    private String type;

    public Product(String productName, Double price, String imgUrl, Boolean vegan, String type) {
        this.productName = productName;
        this.price = price;
        this.imgUrl = imgUrl;
        this.vegan = vegan;
        this.type = type;
    }

    public Product(Long id, String productName, Double price, String imgUrl, Boolean vegan, String type) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.imgUrl = imgUrl;
        this.vegan = vegan;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product withId(Long id) {
        this.id = id;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Product withProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Product withPrice(Double price) {
        this.price = price;
        return this;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Product withImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public Boolean getVegan() {
        return vegan;
    }

    public void setVegan(Boolean vegan) {
        this.vegan = vegan;
    }

    public Product withVegan(Boolean vegan) {
        this.vegan = vegan;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Product withType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", imgUrl='" + imgUrl + '\'' +
                ", vegan=" + vegan +
                ", type='" + type + '\'' +
                '}';
    }

}
