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
import com.example.restaurant.adapters.DeskAdapter;
import com.example.restaurant.apiinterface.DeskApi;
import com.example.restaurant.model.Desk;
import com.example.restaurant.model.listmodels.DeskListClass;
import com.example.restaurant.retrofit.ApiUtils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeskFragment extends Fragment {

    private Integer page = 0;
    ProgressBar progressBar;
    NestedScrollView nestedScrollView;
    DeskAdapter deskAdapter;
    RecyclerView recyclerView;
    private ArrayList<Desk> deskArrayList = new ArrayList<>();
    DeskApi deskApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_desk, container, false);

        progressBar = view.findViewById(R.id.ProgressBarDesks);
        nestedScrollView = view.findViewById(R.id.NestedScrollViewDesks);
        recyclerView = view.findViewById(R.id.deskRecyclerView);

        //Setting the recycler view
        deskAdapter = new DeskAdapter(deskArrayList, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(deskAdapter);
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);

        deskApi = ApiUtils.getDeskApi();

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
        Call<DeskListClass> getDesks = deskApi.getDesks(page);

        getDesks.enqueue(new Callback<DeskListClass>() {
            @Override
            public void onResponse(@NonNull Call<DeskListClass> call, @NonNull Response<DeskListClass> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressBar.setVisibility(View.GONE);

                    DeskListClass deskResponse = response.body();

                    int code = response.code();
                    assert deskResponse != null;
                    List<Desk> desksList = deskResponse.getDesks();

                    deskArrayList.addAll(desksList);
                    deskAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DeskListClass> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "An error has occurred" + t.getMessage(),
                        Toast.LENGTH_LONG).show();
                System.out.println(t.getMessage());
            }
        });
    }
}