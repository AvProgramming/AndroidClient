package com.example.restaurant.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurant.R;
import com.example.restaurant.apiinterface.UserApi;
import com.example.restaurant.login.data.LoginConfig;
import com.example.restaurant.model.Client;
import com.example.restaurant.model.Product;
import com.example.restaurant.retrofit.ApiUtils;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateAddressActivity extends AppCompatActivity {

    private UserApi userApi;
    private LoginConfig loginConfig;
    private Client client = new Client();
    private EditText editHomeAddress, editCountry, editPostalCode, editCityText;
    private Button accept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address);
        editCountry = (EditText) findViewById(R.id.editCountryText);
        editHomeAddress = (EditText) findViewById(R.id.editHomeAddressText);
        editPostalCode = (EditText) findViewById(R.id.editPostalCodeText);
        editCityText = (EditText) findViewById(R.id.edtiCityText);
        accept = findViewById(R.id.confirmAddressChange);

        userApi = ApiUtils.getUserApi();
        loginConfig = new LoginConfig(getApplicationContext());
        client = (Client) getIntent().getSerializableExtra("client");

        accept.setOnClickListener(view -> {
            if (editCountry.getText().toString().isEmpty() || editHomeAddress.getText().toString().isEmpty() || editPostalCode.getText().toString().isEmpty()
                    || editCityText.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "You can't have empty fields",
                        Toast.LENGTH_LONG).show();
            } else {
                client.setAddress(editCountry.getText().toString() + "." +
                        editHomeAddress.getText().toString() + ".\n" +
                        editCityText.getText().toString() + ", " + editPostalCode.getText().toString() + ".");
                enqueueMethod();
            }
        });
    }

    private void enqueueMethod() {
        Call<Client> call = userApi.updateUserAddress(client, client.getId());

        loginConfig.saveHomeAddress(editCountry.getText().toString() + "." +
                editHomeAddress.getText().toString() + ".\n" +
                editCityText.getText().toString() + ", " + editPostalCode.getText().toString() + ".");

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