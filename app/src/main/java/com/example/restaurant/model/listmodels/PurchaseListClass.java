package com.example.restaurant.model.listmodels;

import com.example.restaurant.model.Purchase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PurchaseListClass {

    @SerializedName("totalItems")
    @Expose
    private Long totalItems;
    @SerializedName("purchases")
    @Expose
    private List<Purchase> purchases = null;
    @SerializedName("totalPages")
    @Expose
    private Long totalPages;
    @SerializedName("currentPage")
    @Expose
    private Long currentPage;

    public Long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Long totalItems) {
        this.totalItems = totalItems;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public Long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Purchase.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("totalItems");
        sb.append('=');
        sb.append(((this.totalItems == null) ? "<null>" : this.totalItems));
        sb.append(',');
        sb.append("purchases");
        sb.append('=');
        sb.append(((this.purchases == null) ? "<null>" : this.purchases));
        sb.append(',');
        sb.append("totalPages");
        sb.append('=');
        sb.append(((this.totalPages == null) ? "<null>" : this.totalPages));
        sb.append(',');
        sb.append("currentPage");
        sb.append('=');
        sb.append(((this.currentPage == null) ? "<null>" : this.currentPage));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}