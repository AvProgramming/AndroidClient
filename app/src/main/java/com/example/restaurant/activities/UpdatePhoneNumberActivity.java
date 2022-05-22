package com.example.restaurant.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.restaurant.R;
import com.example.restaurant.apiinterface.UserApi;
import com.example.restaurant.retrofit.ApiUtils;

public class UpdatePhoneNumberActivity extends AppCompatActivity {

    private UserApi userApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_phone_number);

        userApi = ApiUtils.getUserApi();
    }
}