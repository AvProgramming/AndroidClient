package com.example.restaurant.apiinterface;

import com.example.restaurant.model.Client;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {

    @POST("/clients/")
    Call<Client> performUserSignIn(@Body Client client);

    @GET("/clients/login/{email}")
    Call<Client> performUserLogin(@Path("email") String email);

    @PATCH("/clients/address/{id}")
    Call<Client> updateUserAddress(@Body Client client, @Path("id") Long id);

    @PATCH("/clients/phone/{id}")
    Call<Client> updateUserPhone(@Body Client client, @Path("id") Long id);

    @PATCH("/clients/email/{id}")
    Call<Client> updateUserEmail(@Body Client client, @Path("id") Long id);

    @GET("/clients/email")
    Call<List<String>> getEmails();

    @GET("/clients/{id}")
    Call<Client> getClientById(@Path("id") Long id);
}
