package com.example.restaurant.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.restaurant.R;
import com.example.restaurant.apiinterface.UserApi;
import com.example.restaurant.login.data.LoginConfig;
import com.example.restaurant.model.Client;
import com.example.restaurant.retrofit.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePhoneNumberActivity extends AppCompatActivity {

    private UserApi userApi;
    private LoginConfig loginConfig;
    private Client client = new Client();
    private EditText editPhoneText;
    private Button accept;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_phone_number);
        editPhoneText = (EditText) findViewById(R.id.editPhoneNumberText);
        accept = findViewById(R.id.confirmPhoneNumberChange);

        userApi = ApiUtils.getUserApi();
        loginConfig = new LoginConfig(getApplicationContext());
        client = (Client) getIntent().getSerializableExtra("client");

        userApi = ApiUtils.getUserApi();

        accept.setOnClickListener(view -> {
            if (editPhoneText.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "You can't have empty fields",
                        Toast.LENGTH_LONG).show();
            } else {
                if (!editPhoneText.getText().toString().matches("^34(?:6[0-9]|7[1-9])[0-9]{7}$")) {
                    Toast.makeText(getApplicationContext(), "Not a valid number",
                            Toast.LENGTH_LONG).show();
                } else {
                    client.setPhone(editPhoneText.getText().toString());
                    enqueueMethod();
                }
            }
        });
    }

    private void enqueueMethod() {
        Call<Client> call = userApi.updateUserPhone(client, client.getId());

        loginConfig.savePhone(editPhoneText.getText().toString());

        call.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(@NonNull Call<Client> call, @NonNull Response<Client> response) {
                if (response.isSuccessful() && response.body() != null) {
                    System.out.println((response.body()));
                    Log.i("LOG", "update submitted to API." + response.body().toString());
                    endActivity();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Client> call, @NonNull Throwable t) {
                Log.e("Error: ", "Something went wrong: " + t.getMessage());
            }
        });
    }

    private void endActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}