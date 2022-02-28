package com.example.restaurant.retrofit;

import com.example.restaurant.apiinterface.DeskApi;
import com.example.restaurant.apiinterface.ProductApi;
import com.example.restaurant.apiinterface.PurchaseApi;
import com.example.restaurant.apiinterface.UserApi;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "http://10.0.2.2:3106";

    public static DeskApi getDeskApi() {
        return RetrofitClient.getClient(BASE_URL).create(DeskApi.class);
    }

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
