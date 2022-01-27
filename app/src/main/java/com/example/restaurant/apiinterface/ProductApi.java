package com.example.restaurant.apiinterface;

import com.example.restaurant.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductApi {

    @GET("/products")
    Call<List<Product>> getProducts();
}
