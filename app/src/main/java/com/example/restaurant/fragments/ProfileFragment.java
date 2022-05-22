package com.example.restaurant.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.restaurant.R;
import com.example.restaurant.activities.UpdateEmailActivity;
import com.example.restaurant.apiinterface.UserApi;
import com.example.restaurant.login.data.LoginConfig;
import com.example.restaurant.login.data.LoginRepository;
import com.example.restaurant.login.login.LoginActivity;
import com.example.restaurant.model.Client;
import com.example.restaurant.model.Purchase;
import com.example.restaurant.retrofit.ApiUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {

    private Button logOutButton;

    private LinearLayout changeEmailBtn, changeOrEditAddressBtn, changeOrEditPhoneNumberBtn;
    private TextView emailAddressTxt, phoneNumberTxt, countryTxt, addressTxt, userIdTxt, userNameTxt;
    private final LoginRepository loginRepository;
    private LoginConfig loginConfig;
    private Client client = new Client();
    private UserApi userApi;

    public ProfileFragment(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        logOutButton = view.findViewById(R.id.logOut);
        changeEmailBtn = view.findViewById(R.id.changeEmailBtn);
        changeOrEditAddressBtn = view.findViewById(R.id.changeOrEditAddressBtn);
        changeOrEditPhoneNumberBtn = view.findViewById(R.id.changeOrEditPhoneNumberBtn);
        emailAddressTxt = view.findViewById(R.id.emailAddress);
        phoneNumberTxt = view.findViewById(R.id.phoneNumberTxt);
        countryTxt = view.findViewById(R.id.countryAddressTxt);
        addressTxt = view.findViewById(R.id.homeAddressTxt);
        userIdTxt = view.findViewById(R.id.userIdTxt);
        userNameTxt = view.findViewById(R.id.userNameTxt);

        initValues();

        buttonLogic();

        retrieveUserData();

        return view;
    }

    private void retrieveUserData() {
        Call<Client> call = userApi.getClientById(Long.valueOf(loginConfig.getIdOfUser()));

        call.enqueue(new Callback<Client>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<Client> call, @NonNull Response<Client> response) {
                if (response.isSuccessful() && response.body() != null) {

                    client = response.body();

                    Log.i("LOG", "Search successfully ended and retrieved a body. " + response.body());

                    if (client.getAddress()!=null) {
                        List<String> address = new ArrayList<>();
                        Collections.addAll(address, client.getAddress().split("\\."));

                        countryTxt.setText(address.get(0));
                        addressTxt.setText(address.get(1));
                    } else {
                        addressTxt.setText("Set your home address");
                        countryTxt.setText("Set your country");
                    }
                    if (client.getPhone()!=null) {
                        phoneNumberTxt.setText(client.getPhone());
                    } else {
                        phoneNumberTxt.setText("Set your mobile phone");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Client> call, @NonNull Throwable t) {
                Log.i("LOG", t.getMessage());
            }
        });
    }

    private void buttonLogic() {
        changeEmailBtn.setOnClickListener(event -> {
            Intent intent = new Intent(getContext(), UpdateEmailActivity.class);
            intent.putExtra("client", client);
            requireContext().startActivity(intent);
        });

        changeOrEditPhoneNumberBtn.setOnClickListener(event -> {
            Intent intent = new Intent(getContext(), UpdateEmailActivity.class);
            intent.putExtra("client", client);
            requireContext().startActivity(intent);
        });

        changeOrEditAddressBtn.setOnClickListener(event -> {
            Intent intent = new Intent(getContext(), UpdateEmailActivity.class);
            intent.putExtra("client", client);
            requireContext().startActivity(intent);
        });


        logOutButton.setOnClickListener(event -> {
            loginRepository.logout(getContext());

            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        });
    }

    @SuppressLint("SetTextI18n")
    private void initValues() {
        loginConfig = new LoginConfig(getContext());
        userApi = ApiUtils.getUserApi();

        userNameTxt.setText(loginConfig.getNameOfUser());
        userIdTxt.setText("PID:" + loginConfig.getIdOfUser());
        emailAddressTxt.setText(loginConfig.getEmailOfUser());

        client.setId(loginConfig.getIdOfUser().longValue());
    }
}