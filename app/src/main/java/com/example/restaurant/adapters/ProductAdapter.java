package com.example.restaurant.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurant.R;
import com.example.restaurant.bundleinterface.OnButtonClickListener;
import com.example.restaurant.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    LayoutInflater inflater;
    OnButtonClickListener listener;
    private ArrayList<Product> products;
    private int counter = 0;

    public ProductAdapter(ArrayList<Product> products, Context context, OnButtonClickListener onButtonClickListener) {
        this.products = products;
        this.inflater = LayoutInflater.from(context);
        this.listener = onButtonClickListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = inflater.inflate(R.layout.menu_list_view, parent,
                false);
        return new ProductViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.productName.setText(product.getProductName());
        holder.productPrice.setText(String.valueOf(product.getPrice()));

        holder.restButton.setEnabled(false);

        //Button events for passing data from recyclerview to parent fragment
        holder.addButton.setOnClickListener(view -> {
            holder.restButton.setEnabled(counter != 1);
            counter++;
            listener.onAddButtonClick(products.get(position));
        });
        holder.restButton.setOnClickListener(view -> {
            holder.restButton.setEnabled(counter != 1);
            counter--;
            listener.onRemoveButtonClick(products.get(position));
        });

        String urlImage = product.getImgUrl();
        Picasso.get().load(urlImage).fit().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView productName;
        private final TextView productPrice;
        private final Button addButton, restButton;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgItem);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            restButton = itemView.findViewById(R.id.removeButton);
            addButton = itemView.findViewById(R.id.addButton);
        }
    }
}
