package com.example.restaurant.apiinterface;

import com.example.restaurant.model.Client;
import com.example.restaurant.model.Purchase;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {

    @POST("/clients/")
    Call<Client> performUserSignIn(@Body Client client);

    @GET("/clients/login/{email}")
    Call<Client> performUserLogin(@Path("email") String email);
}
