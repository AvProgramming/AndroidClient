package com.example.restaurant.retrofit;

import com.example.restaurant.apiinterface.ProductApi;
import com.example.restaurant.apiinterface.PurchaseApi;
import com.example.restaurant.apiinterface.UserApi;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "http://192.168.0.20:3106";

    public static ProductApi getProductApi() {
        return RetrofitClient.getClient(BASE_URL).create(ProductApi.class);

    }

    public static PurchaseApi getPurchaseApi() {
        return RetrofitClient.getClient(BASE_URL).create(PurchaseApi.class);
    }

    public static UserApi getUserApi() {
        return RetrofitClient.getClient(BASE_URL).create(UserApi.class);
    }
}
