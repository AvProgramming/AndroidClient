package com.example.restaurant.apiinterface;

import com.example.restaurant.model.Purchase;
import com.example.restaurant.model.enumeral.PurchaseStatus;
import com.example.restaurant.model.listmodels.PurchaseListClass;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PurchaseApi {

    @POST("/purchases/")
    Call<Purchase> savePurchase(@Body Purchase purchase);

    @GET("/purchases/pagination/{page}/5")
    Call<PurchaseListClass> getPurchases(@Path("page") Integer page);

    @GET("/purchases/{id}")
    Call<Purchase> getPurchaseById(@Path("id") Long id);

    @DELETE("/purchases/{id}")
    Call<Purchase> deletePurchase(@Path("id") Long id);

    @PATCH("/purchases/{id}")
    Call<Purchase> updatePurchase(@Body Purchase purchase, @Path("id") Long id);

    @GET("/purchases/pagination/{page}/5")
    Call<PurchaseListClass> getByFilter(@Path("page") Integer id, @Query("field") PurchaseStatus status);
}
