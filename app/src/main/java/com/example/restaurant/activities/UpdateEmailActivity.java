package com.example.restaurant.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.restaurant.R;
import com.example.restaurant.apiinterface.UserApi;
import com.example.restaurant.model.Client;
import com.example.restaurant.model.Purchase;
import com.example.restaurant.retrofit.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateEmailActivity extends AppCompatActivity {

    private UserApi userApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email_activity);

        userApi = ApiUtils.getUserApi();

//        Call<Client> call = userApi.updateUser(client, client.getId());
//
//        call.enqueue(new Callback<Purchase>() {
//            @Override
//            public void onResponse(@NonNull Call<Purchase> call, @NonNull Response<Purchase> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    System.out.println((response.body().toString()));
//                    Log.i("LOG", "update submitted to API." + response.body().toString());
//                    endActivity();
//                }
//            }
//            @Override
//            public void onFailure(@NonNull Call<Purchase> call, @NonNull Throwable t) {
//                Log.e("Error: ", "Something went wrong: " + t.getMessage());
//            }
//        });
    }
}