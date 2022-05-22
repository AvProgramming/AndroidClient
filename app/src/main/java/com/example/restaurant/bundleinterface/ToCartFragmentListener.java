package com.example.restaurant.bundleinterface;

import com.example.restaurant.model.CartProduct;
import com.example.restaurant.model.Product;

import java.util.List;

public interface ToCartFragmentListener {
    void cartFragmentMethodRemoval(List<Product> products);

    void cartFragmentMethodRemovalFromCart(List<CartProduct> cartProducts, Integer position);

    void cartFragmentMethodAdd(List<Product> products);
}
