package com.example.restaurant.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurant.R;
import com.example.restaurant.adapters.CategoryAdapter;
import com.example.restaurant.adapters.PopularAdapter;
import com.example.restaurant.apiinterface.ProductApi;
import com.example.restaurant.login.data.LoginConfig;
import com.example.restaurant.model.Category;
import com.example.restaurant.model.Product;
import com.example.restaurant.retrofit.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private RecyclerView categoryRecyclerView, popRecyclerView;
    private CategoryAdapter categoryAdapter;
    private PopularAdapter popularAdapter;
    private TextView greetingText;
    private ProductApi productApi;
    private ArrayList<Product> productArrayList;
    private List<Product> popularProducts = new ArrayList<>();

    static ArrayList<Category> categories = new ArrayList<>();

    static {
        categories.add(new Category("Entry", R.drawable.pizza));
        categories.add(new Category("Burger",  R.drawable.burger));
        categories.add(new Category("Ramen",  R.drawable.ramen));
        categories.add(new Category("Sushi",  R.drawable.sushi));
        categories.add(new Category("Drink",  R.drawable.drink));
        categories.add(new Category("All",  R.drawable.all_categories));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Initializing view for fragment, permit to see all what you create on your device
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        categoryRecyclerView = view.findViewById(R.id.categoriesRecyclerView);
        popRecyclerView = view.findViewById(R.id.popularRecyvlerView);
        greetingText = view.findViewById(R.id.greetingsText);

        LoginConfig loginConfig = new LoginConfig(getContext());

        greetingText.setText("Hi, " + loginConfig.getNameOfUser());

        recyclerViewInit();

        productInit();

        return view;
    }

    private void recyclerViewInit() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(linearLayoutManager);

        categoryAdapter = new CategoryAdapter(getContext(), categories);
        categoryRecyclerView.setAdapter(categoryAdapter);
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

                    assert productResponse != null;
                    productArrayList = (ArrayList<Product>) productResponse;

                    popularProducts.add(productArrayList.get(2));
                    popularProducts.add(productArrayList.get(5));
                    popularProducts.add(productArrayList.get(6));
                    popularProducts.add(productArrayList.get(8));

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    popRecyclerView.setLayoutManager(linearLayoutManager);

                    popularAdapter = new PopularAdapter(getContext(), popularProducts);
                    popRecyclerView.setAdapter(popularAdapter);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "An error has occurred: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
                System.out.println(t.getMessage());
            }
        });
    }
}