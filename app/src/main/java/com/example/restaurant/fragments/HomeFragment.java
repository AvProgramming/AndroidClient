package com.example.restaurant.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurant.adapters.ProductAdapter;
import com.example.restaurant.R;
import com.example.restaurant.apiinterface.ProductApi;
import com.example.restaurant.model.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    ProductAdapter productAdapter;
    RecyclerView recyclerView;
    ArrayList<Product> productArrayList = new ArrayList<>();
    Retrofit retrofit;
    ProductApi productApi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.productRecyclerView);
        productAdapter = new ProductAdapter(productArrayList, getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(productAdapter);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3106")
                .addConverterFactory(GsonConverterFactory.create()).build();

        productApi = retrofit.create(ProductApi.class);
        Call<List<Product>> getProducts = productApi.getProducts();

        getProducts.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> productResponse = response.body();

                int code = response.code();

                productArrayList.clear();
                assert productResponse != null;
                productArrayList.addAll(productResponse);

                productAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getContext(), "An error has occurred" + t.getMessage(),
                        Toast.LENGTH_LONG).show();
                System.out.println(t.getMessage());
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}