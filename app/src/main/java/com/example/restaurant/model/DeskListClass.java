package com.example.restaurant.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeskListClass {

    @SerializedName("content")
    @Expose
    private List<Desk> desks = null;

    public List<Desk> getDesks() {
        return desks;
    }

    public void setContent(List<Desk> desks) {
        this.desks = desks;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Desk.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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