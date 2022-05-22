package com.example.restaurant.bundleinterface;

import com.example.restaurant.model.Product;

import java.util.List;

public interface AddToCartInterface {
    void addToCartMethod(List<Product> products);

    void clearCart();
}
