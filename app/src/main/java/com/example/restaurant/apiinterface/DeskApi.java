package com.example.restaurant.apiinterface;

import com.example.restaurant.model.listmodels.DeskListClass;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DeskApi {

    @GET("/desks/pagination/{page}/10")
    Call<DeskListClass> getDesks(@Path("page") Integer page);
}
