package com.example.restaurant.helper;

import com.example.restaurant.bundleinterface.AddToCartInterface;
import com.example.restaurant.model.CartProduct;
import com.example.restaurant.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ManagementCart implements AddToCartInterface {

    public static ArrayList<CartProduct> cart = new ArrayList<>();
    public static ArrayList<Product> productArrayList = new ArrayList<>();

    @Override
    public void addToCartMethod(List<Product> products) {
        productArrayList.addAll(products);
        Product first = products.get(0);
        CartProduct product = new CartProduct()
                .withProductName(first.getProductName())
                .withId(first.getId())
                .withImgUrl(first.getImgUrl())
                .withPrice(first.getPrice())
                .withType(first.getType())
                .withVegan(first.getVegan())
                .withCount(products.size());

        if (containsName(cart, product.getProductName())) {
            for (CartProduct productIterable : cart) {
                if (productIterable != null && product.getProductName().equals(productIterable.getProductName())) {
                    productIterable.setCount(productIterable.getCount() + product.getCount());
                    break;
                }
            }
        } else {
            cart.add(product);
        }
    }

    @Override
    public void clearCart() {
        cart.clear();
        productArrayList.clear();
    }

    public boolean containsName(final List<CartProduct> list, final String name) {
        return list.stream().anyMatch(o -> o.getProductName().equals(name));
    }
}
