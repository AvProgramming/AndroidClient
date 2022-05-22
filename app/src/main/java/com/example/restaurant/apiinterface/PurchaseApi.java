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

    @GET("/purchases/pagination/{page}/8")
    Call<PurchaseListClass> getPurchasesById(@Path("page") Integer page, @Query("id") Long id);
}
