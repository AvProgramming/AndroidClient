package com.example.restaurant.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
import com.example.restaurant.model.listmodels.PurchaseListClass;
import com.example.restaurant.retrofit.ApiUtils;

import org.w3c.dom.ls.LSOutput;

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
    PurchaseApi purchaseApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        try {
            progressBar = view.findViewById(R.id.OrderProgressBar);
            nestedScrollView = view.findViewById(R.id.NestedScrollViewOrders);
            recyclerView = view.findViewById(R.id.orderRecyclerView);
            // Inflate the layout for this fragment

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
        } catch (Exception throwable) {
            throwable.getMessage();
        }
        return view;
    }

    private void enqueueMethod() {
        Call<PurchaseListClass> getPurchases = purchaseApi.getPurchases(page);

        getPurchases.enqueue(new Callback<PurchaseListClass>() {
            @Override
            public void onResponse(@NonNull Call<PurchaseListClass> call, @NonNull Response<PurchaseListClass> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressBar.setVisibility(View.GONE);

                    PurchaseListClass purchaseResponse = response.body();

                    int code = response.code();
                    assert purchaseResponse != null;
                    List<Purchase> purchaseList = purchaseResponse.getPurchases();

                    purchaseArrayList.addAll(purchaseList);
                    orderAdapter.notifyDataSetChanged();
                    System.out.println(purchaseList.toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PurchaseListClass> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "An error has occurred" + t.getMessage(),
                        Toast.LENGTH_LONG).show();
                System.out.println(t.getMessage());
            }
        });
    }
}