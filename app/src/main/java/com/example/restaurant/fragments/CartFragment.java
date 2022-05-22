package com.example.restaurant.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurant.R;
import com.example.restaurant.activities.MainActivity;
import com.example.restaurant.adapters.CartAdapter;
import com.example.restaurant.apiinterface.PurchaseApi;
import com.example.restaurant.bundleinterface.ToCartFragmentListener;
import com.example.restaurant.helper.ManagementCart;
import com.example.restaurant.login.data.LoginConfig;
import com.example.restaurant.model.CartProduct;
import com.example.restaurant.model.Client;
import com.example.restaurant.model.Product;
import com.example.restaurant.model.Purchase;
import com.example.restaurant.model.Restaurant;
import com.example.restaurant.model.enumeral.PurchaseStatus;
import com.example.restaurant.retrofit.ApiUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment implements ToCartFragmentListener {

    private RecyclerView recyclerView;
    private LinearLayout layout;
    private static List<CartProduct> productsCart = ManagementCart.cart;
    private static List<Product> productArrayList = new ArrayList<>();
    private TextView deliveryPriceTxt, totalOrderPriceTxt, totalProductsPriceTxt, emptyCartTxt;
    private CartAdapter cartAdapter;
    private double totalPrice = 0.0;
    private double totalProductsPrice = 0.0;
    private double delivery = 1;
    private Button submit;
    private PurchaseApi purchaseApi;
    private LoginConfig loginConfig;
    private ManagementCart managementCart = new ManagementCart();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = view.findViewById(R.id.cartRecyclerView);
        deliveryPriceTxt = view.findViewById(R.id.deliveryPrice);
        totalOrderPriceTxt = view.findViewById(R.id.totalOrderPriceCart);
        totalProductsPriceTxt = view.findViewById(R.id.totalProductPriceCart);
        submit = view.findViewById(R.id.submitBtn);
        layout = view.findViewById(R.id.linearLayoutCart);
        emptyCartTxt = view.findViewById(R.id.emptyCartAlertTxt);

        initList();

        initValues();

        if (productsCart.isEmpty()) {
            layout.setVisibility(View.GONE);
            emptyCartTxt.setVisibility(View.VISIBLE);
        }

        submit.setOnClickListener(event -> {
            List<String> contentList = getStrings();
            Purchase purchase = getPurchase(contentList);

            //Calling interface with endpoint
            purchaseApi = ApiUtils.getPurchaseApi();
            sendPost(purchase);
            managementCart.clearCart();

            endActivity();
        });

        return view;
    }

    private void initValues() {
        for (int i = 0; i < productsCart.size(); i++) {
            totalProductsPrice += productsCart.get(i).getPrice() * productsCart.get(i).getCount();
        }
        totalPrice += totalProductsPrice + delivery;

        deliveryPriceTxt.setText(delivery + " €");
        totalOrderPriceTxt.setText(totalPrice + " €");
        totalProductsPriceTxt.setText(totalProductsPrice + " €");
    }

    private void priceCalc() {
        totalPrice = 1;
        totalProductsPrice = 0;
        if (productArrayList.isEmpty()) {
            totalPrice = 0;
            totalProductsPrice = 0;
            delivery = 0;
        } else {
            for (int i = 0; i < productArrayList.size(); i++) {
                totalProductsPrice += productArrayList.get(i).getPrice();
            }
            totalPrice += totalProductsPrice;
            delivery = 1;
        }
    }

    private void initList() {
        for (int i = 0; i < productsCart.size(); i++) {
            CartProduct product = productsCart.get(i);
            Product productToList = new Product()
                    .withProductName(product.getProductName())
                    .withId(product.getId())
                    .withImgUrl(product.getImgUrl())
                    .withPrice(product.getPrice())
                    .withType(product.getType())
                    .withVegan(product.getVegan());
            for (int j = 0; j < productsCart.get(i).getCount(); j++) {
                productArrayList.add(productToList);
            }
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        cartAdapter = new CartAdapter(productsCart, getContext(), CartFragment.this);
        recyclerView.setAdapter(cartAdapter);
    }

    @Override
    public void cartFragmentMethodRemoval(List<Product> products) {
        dataUpdate(products);
    }

    @Override
    public void cartFragmentMethodRemovalFromCart(List<CartProduct> cartProducts, Integer position) {
        productsCart = cartProducts;

        cartAdapter.notifyItemRemoved(position);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            cartAdapter = new CartAdapter(productsCart, getContext(), CartFragment.this);
            recyclerView.setAdapter(cartAdapter);
        }, 1000);

    }

    @Override
    public void cartFragmentMethodAdd(List<Product> products) {
        dataUpdate(products);
    }

    private void dataUpdate(List<Product> products) {
        productArrayList = products;
        priceCalc();

        deliveryPriceTxt.setText(delivery + " €");
        totalOrderPriceTxt.setText(totalPrice + " €");
        totalProductsPriceTxt.setText(totalProductsPrice + " €");
    }

    private void endActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @NonNull
    private List<String> getStrings() {
        List<String> contents = new ArrayList<>();

        for (Product p : productArrayList) {
            contents.add(String.valueOf(p.getId()));
        }
        return contents;
    }

    @NonNull
    private Purchase getPurchase(List<String> contentList) {
        String content = contentList.toString();

        System.out.println("CONT " + content);

        loginConfig = new LoginConfig(getContext());

        Long clientId = loginConfig.getIdOfUser().longValue();
        Long restaurantId = 1L;

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt = new Date();

        String currentTime = sdf.format(dt);

        return new Purchase(currentTime, content, (long) totalPrice, PurchaseStatus.OPEN, (new Client(clientId)), (new Restaurant(restaurantId)));
    }

    private void sendPost(Purchase purchase) {
        Call<Purchase> call = purchaseApi.savePurchase(purchase);

        call.enqueue(new Callback<Purchase>() {
            @Override
            public void onResponse(@NonNull Call<Purchase> call, @NonNull Response<Purchase> response) {
                if (response.isSuccessful() && response.body() != null) {
                    System.out.println((response.body()));
                    Log.i("LOG", "post submitted to API." + response.body().toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Purchase> call, @NonNull Throwable t) {
                Log.e("Error: ", "Something went wrong: " + t.getMessage());
            }
        });
    }
}