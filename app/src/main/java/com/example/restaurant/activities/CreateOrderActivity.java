package com.example.restaurant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.restaurant.R;
import com.example.restaurant.apiinterface.PurchaseApi;
import com.example.restaurant.model.Client;
import com.example.restaurant.model.Product;
import com.example.restaurant.model.Purchase;
import com.example.restaurant.model.Restaurant;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateOrderActivity extends AppCompatActivity {

    ConstraintLayout constraintLayout;
    ListView listView;
    TextInputLayout personId, restaurantId;
    TextView totalPriceText;
    Button submit;
    ArrayList<String> productNameList;
    private double totalPrice = 0.0;
    static ArrayList<Product> cartList = new ArrayList<>();
    static ArrayAdapter<String> productNamesAdapterList;
    Retrofit retrofit;
    PurchaseApi purchaseApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        listView = findViewById(R.id.productListView);
        personId = findViewById(R.id.editTextPersonName);
        totalPriceText = findViewById(R.id.totalPriceCreated);
        submit = findViewById(R.id.submit);
        constraintLayout = findViewById(R.id.clMain);
        restaurantId = findViewById(R.id.restaurantIdEdit);

        getBundle();
        totalPriceCounter();

        productNameList = (ArrayList<String>) cartList.stream().map(Product::getProductName).collect(Collectors.toList());
        productNamesAdapterList = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productNameList);
        listView.setAdapter(productNamesAdapterList);

        totalPriceText.setText(String.format("Total price is: %.3f", totalPrice));

        submit.setOnClickListener(view -> {
            if (personId.getEditText().getText().toString().isEmpty() || restaurantId.getEditText().getText().toString().isEmpty()) {
                Snackbar.make(constraintLayout, "You can't have null person or null restaurant", BaseTransientBottomBar.LENGTH_SHORT).
                        setAction("Action", event -> System.out.println("Name is null")).show();
                Log.e("Error: ", "Something went wrong");
            } else {

                String content = cartList.toString();

                System.out.println("CONT " + content);

                Long clientId = Long.valueOf(personId.getEditText().getText().toString());
                Long restaurantId = Long.valueOf(personId.getEditText().getText().toString());

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date dt = new Date();

                String currentTime = sdf.format(dt);

                Purchase purchase = new Purchase(currentTime, content, (long) totalPrice, "OPEN", (new Client(clientId)), (new Restaurant(restaurantId)));

                retrofit = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:3106")
                        .addConverterFactory(GsonConverterFactory.create()).build();

                //Calling interface with endpoint
                purchaseApi = retrofit.create(PurchaseApi.class);

                Call<Purchase> call = purchaseApi.savePost(purchase);

                call.enqueue(new Callback<Purchase>() {
                    @Override
                    public void onResponse(Call<Purchase> call, Response<Purchase> response) {
                        if (response.isSuccessful()) {
                            System.out.println((response.body().toString()));
                            Log.i("LOG", "post submitted to API." + response.body().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Purchase> call, Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });


                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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