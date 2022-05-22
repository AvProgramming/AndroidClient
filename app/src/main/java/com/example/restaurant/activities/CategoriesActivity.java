package com.example.restaurant.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurant.R;
import com.example.restaurant.adapters.GroupAdapter;
import com.example.restaurant.apiinterface.ProductApi;
import com.example.restaurant.model.Product;
import com.example.restaurant.retrofit.ApiUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesActivity extends AppCompatActivity {

    private String category = "";
    private RecyclerView recyclerViewGroup;
    private List<Product> productArrayList;
    private List<String> categoriesList;
    private GroupAdapter groupAdapter;
    private ProductApi productApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        recyclerViewGroup = findViewById(R.id.groupRecyclerView);

        category = getIntent().getStringExtra("category");

        productInit();
    }

    private void productInit() {
        productApi = ApiUtils.getProductApi();
        Call<List<Product>> getProducts = productApi.getProducts();

        //Creating callback with necessary logic
        getProducts.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.i("LOG", "get submitted from API." + response.body());
                    //Returning list of products with response body
                    List<Product> productResponse = response.body();

                    if (category.equalsIgnoreCase("all")) {
                        assert productResponse != null;
                        productArrayList = productResponse;
                    } else {
                        productArrayList = productResponse;
                        productArrayList = productArrayList.stream()
                                .filter(product -> product.getType().equalsIgnoreCase(category))
                                .collect(Collectors.toList());
                    }
                    categoriesList = new ArrayList<>();
                    getCategories();

                    //Setting group recycler view adapter which contains categories of products
                    groupAdapter = new GroupAdapter(categoriesList, productArrayList, getApplicationContext());

                    recyclerViewGroup.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerViewGroup.setAdapter(groupAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "An error has occurred: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
                System.out.println(t.getMessage());
            }
        });
    }

    //Filtering products to get all unique categories from list
    private void getCategories() {
        for (int i = 0; i < productArrayList.size(); i++) {
            categoriesList.add(productArrayList.get(i).getType());
        }
        categoriesList = categoriesList.stream()
                .distinct().collect(Collectors.toList());

    }
}