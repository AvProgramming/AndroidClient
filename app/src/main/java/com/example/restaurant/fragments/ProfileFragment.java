package com.example.restaurant.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.restaurant.R;
import com.example.restaurant.login.data.LoginRepository;
import com.example.restaurant.login.login.LoginActivity;


public class ProfileFragment extends Fragment {

    Button logOutButton;
    private final LoginRepository loginRepository;

    public ProfileFragment(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        logOutButton = view.findViewById(R.id.logOut);

        logOutButton.setOnClickListener(event -> {
            loginRepository.logout(getContext());

            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        });

        return view;
    }
}