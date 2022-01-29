package com.example.restaurant.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.restaurant.R;
import com.example.restaurant.databinding.ActivityMainBinding;
import com.example.restaurant.fragments.CartFragment;
import com.example.restaurant.fragments.HomeFragment;
import com.example.restaurant.fragments.OrderFragment;
import com.example.restaurant.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.home_nav:
                    replaceFragment(new HomeFragment());
                    return true;
                case R.id.profile_nav:
                    replaceFragment(new ProfileFragment());
                    return true;
                case R.id.cart_nav:
                    replaceFragment(new CartFragment());
                    return true;
                case R.id.orders_nav:
                    replaceFragment(new OrderFragment());
                    return true;
            }

            return false;
        });
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame_layout, fragment);
        fragmentTransaction.commit();
    }
}