package com.example.restaurant.login.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.restaurant.R;
import com.example.restaurant.apiinterface.UserApi;
import com.example.restaurant.login.data.LoginRepository;
import com.example.restaurant.login.data.Result;
import com.example.restaurant.model.Client;
import com.example.restaurant.model.LoggedInUser;
import com.example.restaurant.retrofit.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

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

    public void login(String email, String password) {
        int[] code = new int[1];

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
                        Log.i("LOG", "get submitted from API." + response.body().toString());

                        client = response.body();

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
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 8;
    }
}