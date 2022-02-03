package com.example.restaurant.apiinterface;

import com.example.restaurant.model.Purchase;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PurchaseApi {

    @POST("/purchases/")
    Call<Purchase> savePost(@Body Purchase purchase);
}
