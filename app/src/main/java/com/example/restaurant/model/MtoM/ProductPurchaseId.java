package com.example.restaurant.model.MtoM;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductPurchaseId implements Serializable {

    @SerializedName("product_id")
    @Expose
    private Long productId;
    @SerializedName("purchase_id")
    @Expose
    private Long purchaseId;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ProductPurchaseId.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("productId");
        sb.append('=');
        sb.append(((this.productId == null) ? "<null>" : this.productId));
        sb.append(',');
        sb.append("purchaseId");
        sb.append('=');
        sb.append(((this.purchaseId == null) ? "<null>" : this.purchaseId));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }
}
