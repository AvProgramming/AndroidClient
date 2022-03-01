package com.example.restaurant.login.data;

import android.util.Log;

import com.example.restaurant.apiinterface.UserApi;
import com.example.restaurant.bundleinterface.OnLoginClickListener;
import com.example.restaurant.encryptor.Encryptor;
import com.example.restaurant.model.Client;
import com.example.restaurant.model.LoggedInUser;
import com.example.restaurant.retrofit.ApiUtils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    LoggedInUser user = new LoggedInUser();
    Result<LoggedInUser> result;

    public Result<LoggedInUser> login(String password, int[] code, Client client) {

        try {
            resultSetter(password, code, client);
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }

    private void resultSetter(String password, int[] code, Client client) {
        if (code[0] == 500) {
            result = new Result.Error("There is no such user with this email");
        } else {
            String generatedSecuredPasswordHash = "";
            try {
                generatedSecuredPasswordHash = Encryptor.generateStrongPasswordHash(password);
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.printStackTrace();
            }
            if (generatedSecuredPasswordHash.equals(client.getPassword())) {
                result = new Result.Success<>(user);
            } else {
                result = new Result.Error("Unexpected error");
            }
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}