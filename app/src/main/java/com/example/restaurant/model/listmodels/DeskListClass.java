package com.example.restaurant.model.listmodels;

import java.util.List;

import com.example.restaurant.model.Desk;
import com.example.restaurant.model.Purchase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeskListClass {

    @SerializedName("content")
    @Expose
    private List<Desk> desks = null;

    public List<Desk> getDesks() {
        return desks;
    }

    public void setDesks(List<Desk> desks) {
        this.desks = desks;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Purchase.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("content");
        sb.append('=');
        sb.append(((this.desks == null)?"<null>":this.desks));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}