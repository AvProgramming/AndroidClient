package com.example.restaurant.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Desk {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("number")
    @Expose
    private Long number;
    @SerializedName("max_capacity")
    @Expose
    private Long maxCapacity;
    @SerializedName("restDesks")
    @Expose
    private List<Object> restDesks = null;
    @SerializedName("reservations")
    @Expose
    private List<Object> reservations = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Long getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Long maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public List<Object> getRestDesks() {
        return restDesks;
    }

    public void setRestDesks(List<Object> restDesks) {
        this.restDesks = restDesks;
    }

    public List<Object> getReservations() {
        return reservations;
    }

    public void setReservations(List<Object> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Purchase.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("number");
        sb.append('=');
        sb.append(((this.number == null)?"<null>":this.number));
        sb.append(',');
        sb.append("maxCapacity");
        sb.append('=');
        sb.append(((this.maxCapacity == null)?"<null>":this.maxCapacity));
        sb.append(',');
        sb.append("restDesks");
        sb.append('=');
        sb.append(((this.restDesks == null)?"<null>":this.restDesks));
        sb.append(',');
        sb.append("reservations");
        sb.append('=');
        sb.append(((this.reservations == null)?"<null>":this.reservations));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}