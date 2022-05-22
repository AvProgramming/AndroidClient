package com.example.restaurant.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurant.R;
import com.example.restaurant.adapters.OrderAdapter;
import com.example.restaurant.apiinterface.PurchaseApi;
import com.example.restaurant.login.data.LoginConfig;
import com.example.restaurant.model.Purchase;
import com.example.restaurant.model.listmodels.PurchaseListClass;
import com.example.restaurant.retrofit.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderFragment extends Fragment {

    private Integer page = 0;
    private LoginConfig loginConfig;
    private ProgressBar progressBar;
    private NestedScrollView nestedScrollView;
    private OrderAdapter orderAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Purchase> purchaseArrayList = new ArrayList<>();
    private TextView textView;
    private PurchaseApi purchaseApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        progressBar = view.findViewById(R.id.OrderProgressBar);
        nestedScrollView = view.findViewById(R.id.NestedScrollViewOrders);
        recyclerView = view.findViewById(R.id.orderRecyclerView);
        textView = view.findViewById(R.id.orderText);
        loginConfig = new LoginConfig(getContext());

        orderAdapter = new OrderAdapter(getContext(), purchaseArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(orderAdapter);
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);

        purchaseApi = ApiUtils.getPurchaseApi();

        enqueueMethod();

        //Paging the recycler view on load resources
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                page++;
                progressBar.setVisibility(View.VISIBLE);

                enqueueMethod();
            }
        });
        return view;
    }

    private void enqueueMethod() {
        Call<PurchaseListClass> getPurchases = purchaseApi.getPurchasesById(page, loginConfig.getIdOfUser().longValue());

        getPurchases.enqueue(new Callback<PurchaseListClass>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<PurchaseListClass> call, @NonNull Response<PurchaseListClass> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.i("LOG", "get submitted from API." + response.body());
                    progressBar.setVisibility(View.GONE);

                    PurchaseListClass purchaseResponse = response.body();

                    List<Purchase> purchaseList = purchaseResponse.getPurchases();
                    if (purchaseList.isEmpty()) {
                        textView.setText("You don't have any orders yet");
                        progressBar.setVisibility(View.GONE);
                    }

                    purchaseArrayList.addAll(purchaseList);
                    orderAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PurchaseListClass> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "An error has occurred" + t.getMessage(),
                        Toast.LENGTH_LONG).show();
                Log.i("LOG", t.getMessage());
            }
        });
    }
}