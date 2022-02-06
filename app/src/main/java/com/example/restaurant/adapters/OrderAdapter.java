package com.example.restaurant.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurant.R;
import com.example.restaurant.activities.UpdatePurchaseActivity;
import com.example.restaurant.apiinterface.ProductApi;
import com.example.restaurant.model.Product;
import com.example.restaurant.model.Purchase;
import com.example.restaurant.retrofit.ApiUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    LayoutInflater inflater;
    private ArrayList<Purchase> purchases;
//    private ArrayList<String> productNames = new ArrayList<>();
//    ProductApi productApi;

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
//            productNames.clear();
            Purchase purchase = purchases.get(position);
            holder.orderId.setText("ID: " + purchase.getId());

//            getProductNamesById(position);

//            for (int i = 0; i < productNames.size(); i++) {
//                System.out.println(productNames);
//            }

            holder.orderDetails.setText("Order was made at: " + purchase.getTime() + "\n" +
                    "Status is: " + purchase.getType() + "\n" +
                    "Total price is: " + purchase.getTotalPrice() + "\n");

//            for (int i = 0; i < productNames.size(); i++) {
//                holder.orderDetails.append("Product number " + (i + 1) + " is: " + productNames.get(i) + " x" + purchase.getProductPurchase().get(i).getQuantity() + "\n");
//            }

            holder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent(holder.itemView.getContext(), UpdatePurchaseActivity.class);
                intent.putExtra("purchase", purchase);
                holder.itemView.getContext().startActivity(intent);
            });

        } catch (Exception throwable) {
            System.out.println(throwable.getMessage());
        }
    }

//    private void getProductNamesById(int position) {
//        productApi = ApiUtils.getProductApi();
//
//        for (int i = 0; i < purchases.get(position).getProductPurchase().size(); i++) {
//            Call<Product> getProducts = productApi.getProductById(purchases.get(position).getProductPurchase().get(i).getProductPurchaseId().getProductId());
//
//            //Creating callback with necessary logic
//            getProducts.enqueue(new Callback<Product>() {
//                @Override
//                public void onResponse(@NonNull Call<Product> call, @NonNull Response<Product> response) {
//                    //Returning list of products with response body
//                    Product productResponse = response.body();
//
//                    assert productResponse != null;
//                    productNames.add(productResponse.getProductName());
//                }
//
//                @Override
//                public void onFailure(@NonNull Call<Product> call, @NonNull Throwable t) {
//                    Toast.makeText(inflater.getContext(), "An error has occurred: " + t.getMessage(),
//                            Toast.LENGTH_LONG).show();
//                    System.out.println(t.getMessage());
//                }
//            });
//        }
//    }

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
