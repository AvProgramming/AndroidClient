package com.example.restaurant.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.restaurant.R;
import com.example.restaurant.databinding.ActivityMainBinding;
import com.example.restaurant.fragments.OrderFragment;
import com.example.restaurant.fragments.HomeFragment;
import com.example.restaurant.fragments.DeskFragment;
import com.example.restaurant.fragments.ProfileFragment;
import com.example.restaurant.login.data.LoginDataSource;
import com.example.restaurant.login.data.LoginRepository;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @SuppressLint("NonConstantResourceId")
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
                    replaceFragment(new ProfileFragment(LoginRepository.getInstance(new LoginDataSource())));
                    return true;
                case R.id.order_nav:
                    replaceFragment(new OrderFragment());
                    return true;
                case R.id.desk_nav:
                    replaceFragment(new DeskFragment());
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