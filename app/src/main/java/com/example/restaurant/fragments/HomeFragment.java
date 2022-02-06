package com.example.restaurant.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurant.R;
import com.example.restaurant.activities.CreateOrderActivity;
import com.example.restaurant.adapters.GroupAdapter;
import com.example.restaurant.apiinterface.ProductApi;
import com.example.restaurant.bundleinterface.OnInterfaceListener;
import com.example.restaurant.model.Product;
import com.example.restaurant.retrofit.ApiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements OnInterfaceListener {

    ConstraintLayout cl;
    RecyclerView recyclerViewGroup;
    Button createOrder;
    Double totalPrice = 0.0;
    private ArrayList<Product> productArrayList;
    private ArrayList<Product> cart = new ArrayList<>();
    private List<String> categoriesList;
    GroupAdapter groupAdapter;
    ProductApi productApi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Initializing view for fragment, permit to see all what you create on your device
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewGroup = view.findViewById(R.id.groupRecyclerView);
        createOrder = view.findViewById(R.id.createOrder);
        cl = view.findViewById(R.id.cl);

        createOrder.setVisibility(View.GONE);

        productInit();

        createOrder.setOnClickListener(event -> {
            Intent intent = new Intent(getContext(), CreateOrderActivity.class);
            intent.putExtra("cart", cart);
            requireContext().startActivity(intent);
        });


        return view;
    }

    private void productInit() {
        productApi = ApiUtils.getProductApi();
        Call<List<Product>> getProducts = productApi.getProducts();

        //Creating callback with necessary logic
        getProducts.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                //Returning list of products with response body
                List<Product> productResponse = response.body();

                assert productResponse != null;
                productArrayList = (ArrayList<Product>) productResponse;
                categoriesList = new ArrayList<>();

                getCategories();

                //Setting group recycler view adapter which contains categories of products
                groupAdapter = new GroupAdapter(categoriesList, productArrayList, getContext(), HomeFragment.this);

                recyclerViewGroup.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerViewGroup.setAdapter(groupAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "An error has occurred: " + t.getMessage(),
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    //Setting price on order confirmation button if add button is pressed
    @Override
    public void onAddingInterfaceChanged(Product product) {
        cart.add(product);

        createOrder.setVisibility(View.VISIBLE);

        totalPrice += product.getPrice();
        createOrder.setText(String.format("Total price is: %.3f", totalPrice));
    }
    //Setting price if remove button is pressed
    @Override
    public void onRemoveInterfaceChanged(Product product) {
        cart.remove(product);

        totalPrice -= product.getPrice();
        createOrder.setText(String.format("Total price is: %.3f", totalPrice));
        if (totalPrice==0) {
            createOrder.setVisibility(View.GONE);
        }
    }
}