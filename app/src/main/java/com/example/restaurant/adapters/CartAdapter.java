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
import com.example.restaurant.bundleinterface.ToCartFragmentListener;
import com.example.restaurant.fragments.CartFragment;
import com.example.restaurant.model.CartProduct;
import com.example.restaurant.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private LayoutInflater inflater;
    private ToCartFragmentListener listener;
    private List<CartProduct> products;
    private List<Product> productArrayList = new ArrayList<>();

    public CartAdapter(List<CartProduct> products, Context context, ToCartFragmentListener listener) {
        this.products = products;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = inflater.inflate(R.layout.cart_list_view, parent,
                false);
        return new CartViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartProduct product = products.get(position);
        Product productToList = new Product()
                .withProductName(product.getProductName())
                .withId(product.getId())
                .withImgUrl(product.getImgUrl())
                .withPrice(product.getPrice())
                .withType(product.getType())
                .withVegan(product.getVegan());

        for (int j = 0; j < products.get(position).getCount(); j++) {
            productArrayList.add(productToList);
        }

        System.out.println("Cart Adapter List SOUT");
        productArrayList.forEach(System.out::println);

        holder.title.setText(product.getProductName());
        holder.totalEachItem.setText(String.valueOf(product.getCount()));


        String urlImage = product.getImgUrl();
        Picasso.get().load(urlImage).fit().into(holder.prodImg);

        holder.addBtn.setOnClickListener(view -> {
            product.setCount(product.getCount()+1);
            holder.totalEachItem.setText(String.valueOf(product.getCount()));
            if (product.getCount()>0) {
                holder.rmvBtn.setEnabled(true);
            }
            productArrayList.add(productToList);
            listener.cartFragmentMethodAdd(productArrayList);
        });

        holder.rmvBtn.setOnClickListener(view -> {
            product.setCount(product.getCount()-1);
            holder.totalEachItem.setText(String.valueOf(product.getCount()));
            if (product.getCount()<1) {
                holder.rmvBtn.setEnabled(false);
                products.remove(product);
                listener.cartFragmentMethodRemovalFromCart(products, position);
            }
            productArrayList.remove(productToList);
            listener.cartFragmentMethodRemoval(productArrayList);
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView prodImg;
        Button addBtn, rmvBtn;
        TextView totalEachItem, title;


        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.cartProductTitle);
            prodImg = itemView.findViewById(R.id.prodCartImg);
            addBtn = itemView.findViewById(R.id.addInCartButton);
            rmvBtn = itemView.findViewById(R.id.removeInCartButton);
            totalEachItem = itemView.findViewById(R.id.prodCountTextInCart);
        }
    }
}
