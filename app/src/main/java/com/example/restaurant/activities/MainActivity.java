package com.example.restaurant.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

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

        binding.bottomNavigationView.setOnItemReselectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.home_nav:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.profile_nav:
                    replaceFragment(new ProfileFragment());
                    break;
                case R.id.cart_nav:
                    replaceFragment(new CartFragment());
                    break;
                case R.id.orders_nav:
                    replaceFragment(new OrderFragment());
                    break;
            }

        });
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame_layout, fragment);
        fragmentTransaction.commit();
    }
}