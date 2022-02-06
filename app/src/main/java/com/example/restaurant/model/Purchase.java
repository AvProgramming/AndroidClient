package com.example.restaurant.model;

import com.example.restaurant.model.enumeral.PurchaseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Purchase implements Serializable {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("total_price")
    @Expose
    private Long totalPrice;
    @SerializedName("type")
    @Expose
    private PurchaseStatus type;
    @SerializedName("client")
    @Expose
    private Client client;
    @SerializedName("restaurant")
    @Expose
    private Restaurant restaurant;
    @Expose
    private List<ProductPurchase> productPurchase = null;

    public Purchase(String time, String content, Long totalPrice, PurchaseStatus type, Client client, Restaurant restaurant) {
        this.time = time;
        this.content = content;
        this.totalPrice = totalPrice;
        this.type = type;
        this.client = client;
        this.restaurant = restaurant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public PurchaseStatus getType() {
        return type;
    }

    public void setType(PurchaseStatus type) {
        this.type = type;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<ProductPurchase> getProductPurchase() {
        return productPurchase;
    }

    public void setProductPurchase(List<ProductPurchase> productPurchase) {
        this.productPurchase = productPurchase;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Purchase.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("time");
        sb.append('=');
        sb.append(((this.time == null) ? "<null>" : this.time));
        sb.append(',');
        sb.append("content");
        sb.append('=');
        sb.append(((this.content == null) ? "<null>" : this.content));
        sb.append(',');
        sb.append("totalPrice");
        sb.append('=');
        sb.append(((this.totalPrice == null) ? "<null>" : this.totalPrice));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null) ? "<null>" : this.type));
        sb.append(',');
        sb.append("client");
        sb.append('=');
        sb.append(((this.client == null) ? "<null>" : this.client));
        sb.append(',');
        sb.append("restaurant");
        sb.append('=');
        sb.append(((this.restaurant == null) ? "<null>" : this.restaurant));
        sb.append(',');
        sb.append("productPurchase");
        sb.append('=');
        sb.append(((this.productPurchase == null)?"<null>":this.productPurchase));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}