package com.example.restaurant.apiinterface;

import com.example.restaurant.model.Client;
import com.example.restaurant.model.Purchase;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserApi {

    @POST("/clients/")
    Call<Client> performUserSignIn(@Body Client client);

    @GET("/login")
    Call<Client> performUserLogin(String userName, String password);
}
