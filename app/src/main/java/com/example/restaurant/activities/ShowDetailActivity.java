package com.example.restaurant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurant.R;
import com.example.restaurant.helper.ManagementCart;
import com.example.restaurant.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ShowDetailActivity extends AppCompatActivity {

    private TextView titleTxt, priceTxt, descriptionTxt, numberOrderTxt;
    private Button addToCartBtn, addCountBtn, restCountBtn;
    private ImageView productImg;
    private Product product;
    private List<Product> products = new ArrayList<>();
    private Double totalPrice = 0.0;
    private ManagementCart managementCart = new ManagementCart();

    private int counter = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        titleTxt = findViewById(R.id.descriptionProdTitle);
        priceTxt = findViewById(R.id.descriptionPriceTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        numberOrderTxt = findViewById(R.id.prodCountTextOnDetail);
        addToCartBtn = findViewById(R.id.addToCartInDetail);
        addCountBtn = findViewById(R.id.addInDetailsButton);
        restCountBtn = findViewById(R.id.removeInDetailsButton);
        productImg = findViewById(R.id.popProdImgInDetail);

        restCountBtn.setEnabled(false);

        getBundle();

        products.add(product);

        addCountBtn.setOnClickListener(view -> {
            counter++;
            restCountBtn.setEnabled(counter > 1);
            numberOrderTxt.setText(String.valueOf(counter));
            totalPrice += product.getPrice();
            priceTxt.setText("€ " + totalPrice);
            products.add(product);
        });
        restCountBtn.setOnClickListener(view -> {
            counter--;
            restCountBtn.setEnabled(counter > 1);
            numberOrderTxt.setText(String.valueOf(counter));
            totalPrice -= product.getPrice();
            priceTxt.setText("€ " + totalPrice);
            products.remove(product);
        });
        addToCartBtn.setOnClickListener(view -> {
            managementCart.addToCartMethod(products);
            endActivity();
        });
    }

    private void getBundle() {
        product = (Product) getIntent().getSerializableExtra("product");
        totalPrice = product.getPrice();

        String urlImage = product.getImgUrl();
        System.out.println(urlImage);
        Picasso.get().load(urlImage).fit().into(productImg);

        titleTxt.setText(product.getProductName());
        priceTxt.setText("€ " + product.getPrice());
        descriptionTxt.setText("Description test");
    }

    private void endActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}