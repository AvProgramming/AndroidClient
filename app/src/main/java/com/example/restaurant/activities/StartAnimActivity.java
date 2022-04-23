package com.example.restaurant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.restaurant.R;
import com.example.restaurant.login.login.LoginActivity;

public class StartAnimActivity extends AppCompatActivity {

    ImageView splashImg, logo;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        splashImg = findViewById(R.id.splashImg);
        lottieAnimationView = findViewById(R.id.animationStart);
        logo = findViewById(R.id.logoImg);

        splashImg.animate().translationY(-2500).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(2000).setDuration(1200).setStartDelay(4000);
        lottieAnimationView.animate().translationY(2000).setDuration(1200).setStartDelay(4000);

        int SPLASH_SCREEN = 6000; // converted automatically
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(StartAnimActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_SCREEN);
    }
}