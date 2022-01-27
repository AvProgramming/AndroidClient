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

import com.example.restaurant.ProductAdapter;
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

//        Product pizza = new Product("Pizza", 10.5, "https://static.sscontent.com/prodimg/thumb/500/500/products/124/v1061874_prozis_4-x-ultra-thin-pizza-crusts_4.jpg", false, "ProductType.FOOD");
//        Product ramen = new Product("Ramen", 11.0, "https://www.elmundoeats.com/wp-content/uploads/2021/02/FP-Quick-30-minutes-chicken-ramen-500x500.jpg", false," ProductType.FOOD");
//        Product sushi = new Product("Sushi", 14.5, "https://blog.dia.es/wp-content/uploads/2020/06/sushi-8V3FQM6-500x500.jpg", true, "ProductType.FOOD");
//
//        productArrayList.add(pizza);
//        productArrayList.add(ramen);
//        productArrayList.add(sushi);
//
//        productAdapter.notifyDataSetChanged();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.40.53:3106")
                .addConverterFactory(GsonConverterFactory.create()).build();
        System.out.println("aa1");

        productApi = retrofit.create(ProductApi.class);
        System.out.println("aa2");
        Call<List<Product>> getProducts = productApi.getProducts();

        getProducts.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                System.out.println("aa3");
                List<Product> productResponse = response.body();
                System.out.println("aa3");

                int code = response.code();

                System.out.println("aa4");

                productArrayList.clear();
                assert productResponse != null;
                productArrayList.addAll(productResponse);

                productAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getContext(), "An error has occured" + t.getMessage(),
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