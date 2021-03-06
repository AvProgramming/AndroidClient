package com.example.restaurant.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurant.R;
import com.example.restaurant.model.Product;

import java.util.ArrayList;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    LayoutInflater inflater;
    private List<String> categories;
    private List<Product> filteredProducts;
    private List<Product> productArrayList;

    public GroupAdapter(List<String> categories, List<Product> productArrayList, Context context) {
        this.categories = categories;
        this.productArrayList = productArrayList;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = inflater.inflate(R.layout.list_raw_group, parent,
                false);
        return new GroupAdapter.GroupViewHolder(item);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        filteredProducts = new ArrayList<>();

        holder.categoryName.setText(categories.get(position));

        productListFiltering(position);

        //Creating and initializing nested recyclerview
        ProductAdapter productAdapter = new ProductAdapter(filteredProducts, inflater.getContext());

        holder.recyclerViewMember.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        holder.recyclerViewMember.setAdapter(productAdapter);

        productAdapter.notifyDataSetChanged();
    }

    private void productListFiltering(int position) {
        for (Product product : productArrayList) {
            if (product.getType().equals(categories.get(position))) {
                filteredProducts.add(product);
            }
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class GroupViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        RecyclerView recyclerViewMember;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_name);
            recyclerViewMember = itemView.findViewById(R.id.RecyclerViewMember);
        }
    }
}
