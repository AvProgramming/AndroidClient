package com.example.restaurant.login.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.restaurant.R;

import java.util.regex.Pattern;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<RegisterFormState> registerFormState = new MutableLiveData<>();
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public RegisterViewModel() {

    }

    LiveData<RegisterFormState> getRegisterFormState() {
        return registerFormState;
    }


    public void registerEmailChanged(String email, String password) {
        if (!isEmailValid(email)) {
            registerFormState.setValue(new RegisterFormState(R.string.invalid_username, null));
        } else if (isEmailValid(email) && isPasswordValid(password)) {
            registerFormState.setValue(new RegisterFormState(true));
        }
    }

    public void registerPassChanged(String password, String email) {
        if (!isPasswordValid(password)) {
            registerFormState.setValue(new RegisterFormState(null, R.string.invalid_password));
        } else if (isEmailValid(email) && isPasswordValid(password)) {
            registerFormState.setValue(new RegisterFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        if (!email.isEmpty()) {
            return VALID_EMAIL_ADDRESS_REGEX.matcher(email).matches();
        } else {
            return !email.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 8;
    }
}
