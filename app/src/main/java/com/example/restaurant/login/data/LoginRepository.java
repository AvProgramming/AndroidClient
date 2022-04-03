package com.example.restaurant.login.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.restaurant.R;
import com.example.restaurant.model.Client;
import com.example.restaurant.model.LoggedInUser;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    private LoggedInUser user = null;

    // private constructor : singleton access
    LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public LoginRepository() {

    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout(Context context) {
        user = null;
        dataSource.logout(context);
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
    }

    public Result<LoggedInUser> login(String password, int[] code, Client client) {
        // handle login
        Result<LoggedInUser> result = dataSource.login(password, code, client);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }
}