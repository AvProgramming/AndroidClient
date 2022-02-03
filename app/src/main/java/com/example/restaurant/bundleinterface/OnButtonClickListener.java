package com.example.restaurant.bundleinterface;

import com.example.restaurant.model.Product;

public interface OnButtonClickListener {
    void onAddButtonClick(Product product);

    void onRemoveButtonClick(Product product);
}
