package com.example.restaurant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurant.R;
import com.example.restaurant.apiinterface.PurchaseApi;
import com.example.restaurant.model.Purchase;
import com.example.restaurant.model.enumeral.PurchaseStatus;
import com.example.restaurant.retrofit.ApiUtils;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePurchaseActivity extends AppCompatActivity {

    AutoCompleteTextView orderType;
    Purchase purchase;
    Button deleteOrder, updateOrder;
    PurchaseApi purchaseApi;
    List<PurchaseStatus> statuses = Arrays.asList(PurchaseStatus.values());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_purchase);

        orderType = findViewById(R.id.orderAutoStatus);
        deleteOrder = findViewById(R.id.deleteOrderBtn);
        updateOrder = findViewById(R.id.updateOrderBtn);
        getBundle();

        adapterInit();

        purchaseApi = ApiUtils.getPurchaseApi();

        System.out.println(purchase);

        deleteOrder.setOnClickListener(view -> {
            Call<Purchase> call = purchaseApi.deletePurchase(purchase.getId());

            call.enqueue(new Callback<Purchase>() {
                @Override
                public void onResponse(@NonNull Call<Purchase> call, @NonNull Response<Purchase> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        System.out.println((response.body().toString()));
                        Log.i("LOG", "delete submitted to API." + response.body().toString());
                        endActivity();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<Purchase> call, @NonNull Throwable t) {
                    System.out.println(t.getMessage());
                }
            });
        });

        updateOrder.setOnClickListener(view -> {
            Call<Purchase> call = purchaseApi.updatePurchase(purchase, purchase.getId());

            call.enqueue(new Callback<Purchase>() {
                @Override
                public void onResponse(@NonNull Call<Purchase> call, @NonNull Response<Purchase> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        System.out.println((response.body().toString()));
                        Log.i("LOG", "update submitted to API." + response.body().toString());
                        endActivity();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<Purchase> call, @NonNull Throwable t) {
                    System.out.println(t.getMessage());
                }
            });
        });
    }

    private void endActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void getBundle() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            purchase = (Purchase) getIntent().getSerializableExtra("purchase");
        }
    }

    private void adapterInit() {
        ArrayAdapter<PurchaseStatus> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, statuses);

        orderType.setAdapter(adapter);
        orderType.setOnItemClickListener((adapterView, view, i, l) -> {
            switch (statuses.get(i)) {
                case OPEN:
                    purchase.setType(PurchaseStatus.OPEN);
                    break;
                case DONE:
                    purchase.setType(PurchaseStatus.DONE);
                    break;
                case PROCESSING:
                    purchase.setType(PurchaseStatus.PROCESSING);
                    break;
                default:
                    break;
            }
        });
    }
}