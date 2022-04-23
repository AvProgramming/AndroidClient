package com.example.restaurant.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurant.R;
import com.example.restaurant.adapters.GroupAdapter;
import com.example.restaurant.apiinterface.ProductApi;
import com.example.restaurant.fragments.HomeFragment;
import com.example.restaurant.model.Product;
import com.example.restaurant.retrofit.ApiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CodeHolder {

//    ConstraintLayout cl;
//    RecyclerView recyclerViewGroup;
//    Button createOrder;
//    Double totalPrice = 0.0;
//    private ArrayList<Product> productArrayList;
//    private ArrayList<Product> cart = new ArrayList<>();
//    private List<String> categoriesList;
//    GroupAdapter groupAdapter;
//    ProductApi productApi;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        //Initializing view for fragment, permit to see all what you create on your device
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
//        recyclerViewGroup = view.findViewById(R.id.groupRecyclerView);
//        createOrder = view.findViewById(R.id.logOut);
//        cl = view.findViewById(R.id.cl);
//
//        createOrder.setVisibility(View.GONE);
//
//        productInit();
//
//        createOrder.setOnClickListener(event -> {
//            Intent intent = new Intent(getContext(), CreateOrderActivity.class);
//            intent.putExtra("cart", cart);
//            requireContext().startActivity(intent);
//        });
//
//
//        return view;
//    }
//
//    private void productInit() {
//        productApi = ApiUtils.getProductApi();
//        Call<List<Product>> getProducts = productApi.getProducts();
//
//        //Creating callback with necessary logic
//        getProducts.enqueue(new Callback<List<Product>>() {
//            @Override
//            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    Log.i("LOG", "get submitted from API." + response.body());
//                    //Returning list of products with response body
//                    List<Product> productResponse = response.body();
//
//                    assert productResponse != null;
//                    productArrayList = (ArrayList<Product>) productResponse;
//                    categoriesList = new ArrayList<>();
//
//                    getCategories();
//
//                    //Setting group recycler view adapter which contains categories of products
//                    groupAdapter = new GroupAdapter(categoriesList, productArrayList, getContext(), HomeFragment.this);
//
//                    recyclerViewGroup.setLayoutManager(new LinearLayoutManager(getActivity()));
//                    recyclerViewGroup.setAdapter(groupAdapter);
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
//                Toast.makeText(getContext(), "An error has occurred: " + t.getMessage(),
//                        Toast.LENGTH_LONG).show();
//                System.out.println(t.getMessage());
//            }
//        });
//    }
//
//    //Filtering products to get all unique categories from list
//    private void getCategories() {
//        for (int i = 0; i < productArrayList.size(); i++) {
//            categoriesList.add(productArrayList.get(i).getType());
//        }
//        categoriesList = categoriesList.stream()
//                .distinct().collect(Collectors.toList());
//    }
//
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//    }
//
//    //Setting price on order confirmation button if add button is pressed
//    @Override
//    public void onAddingInterfaceChanged(Product product) {
//        cart.add(product);
//
//        createOrder.setVisibility(View.VISIBLE);
//
//        totalPrice += product.getPrice();
//        createOrder.setText(String.format("Total price is: %.3f", totalPrice));
//    }
//
//    //Setting price if remove button is pressed
//    @Override
//    public void onRemoveInterfaceChanged(Product product) {
//        cart.remove(product);
//
//        totalPrice -= product.getPrice();
//        createOrder.setText(String.format("Total price is: %.3f", totalPrice));
//        if (totalPrice == 0) {
//            createOrder.setVisibility(View.GONE);
//        }
//    }

//    <androidx.constraintlayout.widget.ConstraintLayout
//    android:id="@+id/cl"
//    android:layout_width="match_parent"
//    android:layout_height="match_parent">
//
//        <androidx.recyclerview.widget.RecyclerView
//    android:layout_width="match_parent"
//    android:layout_height="wrap_content"
//    android:id="@+id/groupRecyclerView"
//    tools:listitem="@layout/list_raw_group"
//    app:layout_constraintEnd_toEndOf="parent"
//    app:layout_constraintStart_toStartOf="parent"
//    app:layout_constraintTop_toTopOf="parent"
//    app:layout_constraintHorizontal_bias="0.0" />
//
//       <com.google.android.material.button.MaterialButton
//    app:cornerRadius="20dp"
//    android:layout_width="200dp"
//    android:layout_height="wrap_content"
//    app:layout_constraintEnd_toEndOf="parent"
//    app:layout_constraintStart_toStartOf="parent"
//    app:layout_constraintBottom_toBottomOf="parent"
//    android:layout_marginBottom="24dp"
//    app:backgroundTint="#33EAA9"
//    android:id="@+id/logOut"
//    android:textSize="12dp"
//    android:textColor="#000000" />
//    </androidx.constraintlayout.widget.ConstraintLayout>
}
