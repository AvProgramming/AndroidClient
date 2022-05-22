package com.example.restaurant.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurant.R;
import com.example.restaurant.model.Purchase;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    LayoutInflater inflater;
    private ArrayList<Purchase> purchases;

    public OrderAdapter(Context context, ArrayList<Purchase> purchases) {
        this.inflater = LayoutInflater.from(context);
        this.purchases = purchases;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = inflater.inflate(R.layout.order_list_view, parent,
                false);
        return new OrderViewHolder(item);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        try {
            Purchase purchase = purchases.get(position);
            holder.orderId.setText("ID: " + purchase.getId());

            holder.orderDetails.setText("Order was made at: " + purchase.getTime() + "\n" +
                    "Status is: " + purchase.getType() + "\n" +
                    "Total price is: " + purchase.getTotalPrice() + "€\n");

        } catch (Exception throwable) {
            System.out.println(throwable.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return purchases.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        private final TextView orderId;
        private final TextView orderDetails;


        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderDetails = itemView.findViewById(R.id.orderDetails);
            orderId = itemView.findViewById(R.id.orderIdText);
        }

    }
}
