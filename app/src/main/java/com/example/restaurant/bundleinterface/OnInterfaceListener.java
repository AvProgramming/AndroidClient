package com.example.restaurant.bundleinterface;

import com.example.restaurant.model.Product;

public interface OnInterfaceListener {
    void onAddingInterfaceChanged(Product product);

    void onRemoveInterfaceChanged(Product product);
}
