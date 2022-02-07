package com.example.restaurant.fragments;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
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
import com.example.restaurant.model.Purchase;
import com.example.restaurant.model.enumeral.PurchaseStatus;
import com.example.restaurant.model.listmodels.PurchaseListClass;
import com.example.restaurant.retrofit.ApiUtils;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderFragment extends Fragment {

    private Integer page = 0;
    ProgressBar progressBar;
    NestedScrollView nestedScrollView;
    OrderAdapter orderAdapter;
    RecyclerView recyclerView;
    private ArrayList<Purchase> purchaseArrayList = new ArrayList<>();
    SearchView searchView;
    ChipGroup chipGroup;
    PurchaseApi purchaseApi;

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
        searchView = view.findViewById(R.id.searchOrders);
        chipGroup = view.findViewById(R.id.statusChipGroup);
        // Inflate the layout for this fragment

        orderAdapter = new OrderAdapter(getContext(), purchaseArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(orderAdapter);
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);

        purchaseApi = ApiUtils.getPurchaseApi();

        enqueueMethod();
        searchViewInit();

        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            page = 0;
            purchaseArrayList.clear();
            chipGroupOptionMethod(checkedId);
        });

        //Paging the recycler view on load resources
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                page++;
                progressBar.setVisibility(View.VISIBLE);
                int checked;
                checked = chipGroup.getCheckedChipId();
                System.out.println(checked);


                if (checked == -1) {
                    enqueueMethod();
                } else {
                    chipGroupOptionMethod(checked);
                }
            }
        });
        return view;
    }


    private void chipGroupOptionMethod(int checkedId) {
        switch (checkedId) {
            case R.id.chipOpen:
                filterMethod(PurchaseStatus.OPEN);
                break;
            case R.id.chipDone:
                filterMethod(PurchaseStatus.DONE);
                break;
            case R.id.chipProcessing:
                filterMethod(PurchaseStatus.PROCESSING);
                break;
        }
    }


    private void filterMethod(PurchaseStatus status) {
        Call<PurchaseListClass> call = purchaseApi.getByFilter(page, status);

        call.enqueue(new Callback<PurchaseListClass>() {
            @Override
            public void onResponse(@NonNull Call<PurchaseListClass> call, @NonNull Response<PurchaseListClass> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressBar.setVisibility(View.GONE);

                    PurchaseListClass purchase = response.body();

                    Log.i("LOG", "Search successfully ended and retrieved a body. " + response.body().toString());


                    purchaseArrayList.addAll(purchase.getPurchases());
                    orderAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PurchaseListClass> call, @NonNull Throwable t) {
                Log.i("LOG", t.getMessage());
            }
        });
    }

    private void searchViewInit() {
        searchView.clearFocus();
        searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Call<Purchase> call = purchaseApi.getPurchaseById(Long.valueOf(s));

                call.enqueue(new Callback<Purchase>() {
                    @Override
                    public void onResponse(@NonNull Call<Purchase> call, @NonNull Response<Purchase> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            Purchase purchase = response.body();

                            Log.i("LOG", "Search successfully ended and retrieved a body. " + response.body().toString());
                            purchaseArrayList.clear();

                            purchaseArrayList.add(purchase);
                            orderAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Purchase> call, @NonNull Throwable t) {
                        Log.i("LOG", t.getMessage());
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                purchaseArrayList.clear();
                enqueueMethod();
                return false;
            }
        });
    }

    private void enqueueMethod() {
        Call<PurchaseListClass> getPurchases = purchaseApi.getPurchases(page);

        getPurchases.enqueue(new Callback<PurchaseListClass>() {
            @Override
            public void onResponse(@NonNull Call<PurchaseListClass> call, @NonNull Response<PurchaseListClass> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.i("LOG", "get submitted from API." + response.body().toString());
                    progressBar.setVisibility(View.GONE);

                    PurchaseListClass purchaseResponse = response.body();

                    int code = response.code();
                    List<Purchase> purchaseList = purchaseResponse.getPurchases();

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