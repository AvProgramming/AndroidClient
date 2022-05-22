package com.example.restaurant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class UpdateEmailActivity extends AppCompatActivity {

    private UserApi userApi;
    private LoginConfig loginConfig;
    private Client client = new Client();
    private List<String> emails = new ArrayList<>();
    private EditText editEmailText;
    private Button accept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email_activity);
        editEmailText = (EditText) findViewById(R.id.editEmailText);
        accept = findViewById(R.id.confirmEmailChange);

        getAllEmails();
        userApi = ApiUtils.getUserApi();
        loginConfig = new LoginConfig(getApplicationContext());
        client = (Client) getIntent().getSerializableExtra("client");

        accept.setOnClickListener(view -> {
            if (editEmailText.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "You can't have empty fields",
                        Toast.LENGTH_LONG).show();
            } else {
                if (emails.contains(editEmailText.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "There is already an existing email address",
                            Toast.LENGTH_LONG).show();
                } else {
                    client.setEmail(editEmailText.getText().toString());
                    enqueueMethod();
                }
            }
        });
    }

    private void enqueueMethod() {
        Call<Client> call = userApi.updateUserEmail(client, client.getId());

        loginConfig.saveEmailAddress(editEmailText.getText().toString());

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

    private void getAllEmails() {
        userApi = ApiUtils.getUserApi();
        Call<List<String>> getEmails = userApi.getEmails();

        //Creating callback with necessary logic
        getEmails.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.i("LOG", "get submitted from API." + response.body());
                    //Returning list of products with response body
                    List<String> productResponse = response.body();

                    assert productResponse != null;
                    emails = (ArrayList<String>) productResponse;
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "An error has occurred: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
                System.out.println(t.getMessage());
            }
        });
    }

    private void endActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}