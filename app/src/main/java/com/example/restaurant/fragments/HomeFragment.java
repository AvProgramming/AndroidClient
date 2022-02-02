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

import com.example.restaurant.adapters.GroupAdapter;
import com.example.restaurant.R;
import com.example.restaurant.apiinterface.ProductApi;
import com.example.restaurant.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    RecyclerView recyclerViewGroup;
    ArrayList<Product> productArrayList;
    List<String> categoriesList;
    GroupAdapter groupAdapter;
    Retrofit retrofit;
    ProductApi productApi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewGroup = view.findViewById(R.id.groupRecyclerView);

        productInit();

        return view;
    }

    private void productInit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3106")
                .addConverterFactory(GsonConverterFactory.create()).build();

        productApi = retrofit.create(ProductApi.class);
        Call<List<Product>> getProducts = productApi.getProducts();

        getProducts.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> productResponse = response.body();

                assert productResponse != null;
                productArrayList = (ArrayList<Product>) productResponse;
                categoriesList = new ArrayList<>();

                getCategories();

                groupAdapter = new GroupAdapter(categoriesList, productArrayList, getContext());

                recyclerViewGroup.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerViewGroup.setAdapter(groupAdapter);

                System.out.println(categoriesList.get(0));
                System.out.println(productArrayList.get(0));
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getContext(), "An error has occurred" + t.getMessage(),
                        Toast.LENGTH_LONG).show();
                System.out.println(t.getMessage());
            }
        });
    }

    private void getCategories() {
        for (int i = 0; i < productArrayList.size(); i++) {
            categoriesList.add(productArrayList.get(i).getType());
        }
        categoriesList = categoriesList.stream()
                .distinct().collect(Collectors.toList());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}