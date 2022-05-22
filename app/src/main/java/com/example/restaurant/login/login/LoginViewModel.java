package com.example.restaurant.login.login;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.restaurant.R;
import com.example.restaurant.apiinterface.UserApi;
import com.example.restaurant.login.data.LoginConfig;
import com.example.restaurant.login.data.LoginRepository;
import com.example.restaurant.login.data.Result;
import com.example.restaurant.model.Client;
import com.example.restaurant.model.LoggedInUser;
import com.example.restaurant.retrofit.ApiUtils;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private final LoginRepository loginRepository;
    private LoginConfig loginConfig;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    UserApi userApi;
    LoggedInUser user = new LoggedInUser();
    Client client;

    public void login(String email, String password, boolean isRememberUserLogin, Context applicationContext) {
        int[] code = new int[1];
        loginConfig = new LoginConfig(applicationContext);

        userApi = ApiUtils.getUserApi();

        Call<Client> clientCall = userApi.performUserLogin(email);

        clientCall.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                code[0] = response.code();
                System.out.println(code[0]);
                if (code[0] == 500) {
                    Log.i("LOG", "There is no such user with this email");
                } else {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.i("LOG", "get submitted from API." + response.body());

                        client = response.body();
                        if (isRememberUserLogin) {
                            loginConfig.updateUserLoginStatus(true);
                        }
                        loginConfig.saveNameOfUser(client.getName());
                        loginConfig.saveIdOfUser(client.getId());
                        loginConfig.saveEmailAddress(client.getEmail());

                        user.setUserId(client.getId());
                        user.setDisplayName(client.getName());

                        Result<LoggedInUser> result = loginRepository.login(password, code, client);

                        if (result instanceof Result.Success) {
                            LoggedInUser data = user;
                            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
                        } else {
                            loginResult.setValue(new LoginResult(R.string.login_failed));
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                Log.e("Error: ", "Something went wrong: " + t.getMessage());
            }
        });
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (isUserNameValid(username) && isPasswordValid(password)){
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    public void passDataChanged(String password, String username) {
        if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else if (isUserNameValid(username) && isPasswordValid(password)){
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return VALID_EMAIL_ADDRESS_REGEX.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 8;
    }
}