package com.example.restaurant.fragments;

import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.restaurant.R;
import com.example.restaurant.adapters.DeskAdapter;
import com.example.restaurant.apiinterface.DeskApi;
import com.example.restaurant.model.Desk;
import com.example.restaurant.model.DeskListClass;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderFragment extends Fragment {

    Integer page = 0;
    ProgressBar progressBar;
    NestedScrollView nestedScrollView;
    DeskAdapter deskAdapter;
    RecyclerView recyclerView;
    ArrayList<Desk> deskArrayList = new ArrayList<>();
    Retrofit retrofit;
    DeskApi deskApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_order, container, false);

        progressBar = view.findViewById(R.id.progress_bar);
        nestedScrollView = view.findViewById(R.id.nested_scroll_view_desks);

        recyclerView = view.findViewById(R.id.deskRecyclerView);
        deskAdapter = new DeskAdapter(deskArrayList, getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(deskAdapter);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3106")
                .addConverterFactory(GsonConverterFactory.create()).build();

        deskApi = retrofit.create(DeskApi.class);

        ViewCompat.setNestedScrollingEnabled(recyclerView, false);

        enqueueMethod();

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
            public void onResponse(Call<DeskListClass> call, Response<DeskListClass> response) {
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
            public void onFailure(Call<DeskListClass> call, Throwable t) {
                Toast.makeText(getContext(), "An error has occurred" + t.getMessage(),
                        Toast.LENGTH_LONG).show();
                System.out.println(t.getMessage());
            }
        });
    }

    private void parseResult(JSONArray jsonArray) {
    }
}