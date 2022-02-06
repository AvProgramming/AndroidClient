package com.example.restaurant.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.restaurant.R;
import com.example.restaurant.apiinterface.PurchaseApi;
import com.example.restaurant.model.Client;
import com.example.restaurant.model.Product;
import com.example.restaurant.model.Purchase;
import com.example.restaurant.model.Restaurant;
import com.example.restaurant.model.enumeral.PurchaseStatus;
import com.example.restaurant.retrofit.ApiUtils;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateOrderActivity extends AppCompatActivity {

    ConstraintLayout constraintLayout;
    ListView listView;
    TextInputLayout personId, restaurantIdInput;
    TextView totalPriceText;
    Button submit;
    ImageView imageView;
    ArrayList<String> productNameList;
    private double totalPrice = 0.0;
    static ArrayList<Product> cartList = new ArrayList<>();
    static ArrayAdapter<String> productNamesAdapterList;
    PurchaseApi purchaseApi;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        initValues();

        getBundle();
        totalPriceCounter();

        //Getting names of products for simpleListView
        productNameList = (ArrayList<String>) cartList.stream().map(Product::getProductName).collect(Collectors.toList());
        productNamesAdapterList = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productNameList);
        listView.setAdapter(productNamesAdapterList);

        totalPriceText.setText(String.format("Total price is: %.3f", totalPrice));

        submit.setOnClickListener(view -> {
            //On error messages
            if (Objects.requireNonNull(personId.getEditText()).getText().toString().isEmpty() || Objects.requireNonNull(restaurantIdInput.getEditText()).getText().toString().isEmpty()) {
                Snackbar.make(constraintLayout, "You can't have null person or null restaurant", BaseTransientBottomBar.LENGTH_SHORT).
                        setAction("Action", event -> System.out.println("Name is null")).show();
                Log.e("Error: ", "You can't have null person or null restaurant");
            } else {

                List<String> contentList = getStrings();
                Purchase purchase = getPurchase(contentList);


                //Calling interface with endpoint
                purchaseApi = ApiUtils.getPurchaseApi();
                sendPost(purchase);

                endActivity();
            }
        });
    }

    private void initValues() {
        listView = findViewById(R.id.productListView);
        personId = findViewById(R.id.editTextPersonName);
        totalPriceText = findViewById(R.id.totalPriceCreated);
        submit = findViewById(R.id.submit);
        constraintLayout = findViewById(R.id.clMain);
        restaurantIdInput = findViewById(R.id.restaurantIdEdit);
        imageView = findViewById(R.id.logoRestaurant);
    }

    //Filtering contents to take list of ids of products
    @NonNull
    private List<String> getStrings() {
        List<String> contents = new ArrayList<>();

        for (Product p: cartList) {
            contents.add(String.valueOf(p.getId()));
        }
        return contents;
    }

    //Creating new instance of product
    @NonNull
    private Purchase getPurchase(List<String> contenList) {
        String content = contenList.toString();

        System.out.println("CONT " + content);

        Long clientId = Long.valueOf(personId.getEditText().getText().toString());
        Long restaurantId = Long.valueOf(restaurantIdInput.getEditText().getText().toString());

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt = new Date();

        String currentTime = sdf.format(dt);

        return new Purchase(currentTime, content, (long) totalPrice, PurchaseStatus.OPEN, (new Client(clientId)), (new Restaurant(restaurantId)));
    }

    //Finalizing activity after successful sending of object to server
    private void endActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    //Sending post to server
    private void sendPost(Purchase purchase) {
        Call<Purchase> call = purchaseApi.savePurchase(purchase);

        call.enqueue(new Callback<Purchase>() {
            @Override
            public void onResponse(@NonNull Call<Purchase> call, @NonNull Response<Purchase> response) {
                if (response.isSuccessful() && response.body() != null) {
                    System.out.println((response.body().toString()));
                    Log.i("LOG", "post submitted to API." + response.body().toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Purchase> call, @NonNull Throwable t) {
                Log.e("Error: ", "Something went wrong: " + t.getMessage());
            }
        });
    }

    private void getBundle() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cartList = (ArrayList<Product>) getIntent().getSerializableExtra("cart");
        }
    }

    private void totalPriceCounter() {
        for (Product p : cartList) {
            totalPrice += p.getPrice();
        }
    }
}