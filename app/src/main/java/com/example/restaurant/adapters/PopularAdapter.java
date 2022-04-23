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
import com.example.restaurant.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {

    LayoutInflater inflater;
    private List<Product> products;

    public PopularAdapter(Context context, List<Product> products) {
        this.inflater = LayoutInflater.from(context);
        this.products = products;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = inflater.inflate(R.layout.popular_prod_list_view, parent,
                false);
        return new PopularAdapter.PopularViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
        Product product = products.get(position);
        holder.title.setText(product.getProductName());
        holder.price.setText(String.valueOf(product.getPrice()));

        String urlImage = product.getImgUrl();
        Picasso.get().load(urlImage).fit().into(holder.prodImage);

        holder.addBtn.setOnClickListener(view -> {

        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class PopularViewHolder extends RecyclerView.ViewHolder {
        private final TextView title, price;
        private final ImageView prodImage;
        private final Button addBtn;

        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.popularProductTitle);
            price = itemView.findViewById(R.id.popProdPrice);
            prodImage = itemView.findViewById(R.id.popProdImg);
            addBtn = itemView.findViewById(R.id.addPopProduct);
        }
    }
}
