package com.example.restaurant.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurant.R;
import com.example.restaurant.bundleinterface.OnButtonClickListener;
import com.example.restaurant.bundleinterface.OnInterfaceListener;
import com.example.restaurant.model.Product;

import java.util.ArrayList;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> implements OnButtonClickListener {

    OnInterfaceListener listener;
    LayoutInflater inflater;
    private List<String> categories;
    private ArrayList<Product> filteredProducts;
    private ArrayList<Product> productArrayList;

    public GroupAdapter(List<String> categories, ArrayList<Product> productArrayList, Context context, OnInterfaceListener listener) {
        this.categories = categories;
        this.productArrayList = productArrayList;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = inflater.inflate(R.layout.list_raw_group, parent,
                false);
        return new GroupAdapter.GroupViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        filteredProducts = new ArrayList<>();

        holder.categoryName.setText(categories.get(position));

        productListFiltering(position);

        //Creating and initializing nested recyclerview
        ProductAdapter productAdapter = new ProductAdapter(filteredProducts, inflater.getContext(), this);

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

    //Interface bus implementation
    @Override
    public void onAddButtonClick(Product product) {
        listener.onAddingInterfaceChanged(product);
    }

    @Override
    public void onRemoveButtonClick(Product product) {
        listener.onRemoveInterfaceChanged(product);
    }

    public static class GroupViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        RecyclerView recyclerViewMember;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_name);
            recyclerViewMember = itemView.findViewById(R.id.rv_member);
        }
    }
}
