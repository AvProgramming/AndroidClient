package com.example.restaurant.login.register;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.restaurant.R;
import com.example.restaurant.apiinterface.UserApi;
import com.example.restaurant.databinding.ActivityRegisterBinding;
import com.example.restaurant.encryptor.Encryptor;
import com.example.restaurant.model.Client;
import com.example.restaurant.retrofit.ApiUtils;
import com.google.android.material.textfield.TextInputEditText;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding registerBinding;
    private RegisterViewModel registerViewModel;
    private TextInputEditText usernameEditText;
    private TextInputEditText passwordEditText;
    private TextInputEditText emailEdittext;
    private ProgressBar progressBar;
    UserApi userApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(registerBinding.getRoot());

        registerViewModel = new ViewModelProvider(this, new RegisterViewModelFactory())
                .get(RegisterViewModel.class);

        usernameEditText = findViewById(R.id.registerUsername);
        passwordEditText = findViewById(R.id.registerPassword);
        emailEdittext = findViewById(R.id.registerEmail);
        final Button registrationBtn = registerBinding.confirmRegistrationBtn;
        progressBar = registerBinding.registerProgressBar;

        registrationBtn.setBackgroundColor(getColor(R.color.inactive_button));

        progressBar.setVisibility(View.GONE);

        registerViewModel.getRegisterFormState().observe(this, loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            registrationBtn.setEnabled(loginFormState.isDataValid());
            if (registrationBtn.isEnabled()) {
                registrationBtn.setBackgroundColor(getColor(R.color.button));
            }
            if (loginFormState.getUsernameError() != null) {
                emailEdittext.setError(getString(loginFormState.getUsernameError()));
            }
            if (loginFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(loginFormState.getPasswordError()));
            }
        });

        TextWatcher afterEmailChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                registerViewModel.registerEmailChanged(emailEdittext.getText().toString(), passwordEditText.getText().toString());
            }
        };

        TextWatcher afterPassChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                registerViewModel.registerPassChanged(passwordEditText.getText().toString(), emailEdittext.getText().toString());
            }
        };
        emailEdittext.addTextChangedListener(afterEmailChangedListener);
        passwordEditText.addTextChangedListener(afterPassChangedListener);

        registrationBtn.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            performSignUp();
        });
    }

    private void performSignUp() {
        if (usernameEditText.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "You can't have null username",
                    Toast.LENGTH_LONG).show();
        } else {
            userApi = ApiUtils.getUserApi();
            Client client = new Client();

            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String email = emailEdittext.getText().toString();

            String generatedSecuredPasswordHash = "";
            try {
                generatedSecuredPasswordHash = Encryptor.generateStrongPasswordHash(password);
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.printStackTrace();
            }

            client.setName(username);
            client.setPassword(generatedSecuredPasswordHash);
            client.setEmail(email);

            Call<Client> clientCall = userApi.performUserSignIn(client);

            clientCall.enqueue(new Callback<Client>() {
                @Override
                public void onResponse(@NonNull Call<Client> call, @NonNull Response<Client> response) {
                    int code = response.code();
                    System.out.println(code);
                    if (code == 500) {
                        Log.i("LOG", "This person is already exist");
                        Toast.makeText(getApplicationContext(), "This person with this email is already exist",
                                Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    } else {
                        if (response.isSuccessful() && response.body() != null) {
                            Log.i("LOG", "get submitted from API." + response.body().toString());
                            Toast.makeText(getApplicationContext(), "Registration successful, now you can login",
                                    Toast.LENGTH_LONG).show();

                            progressBar.setVisibility(View.GONE);
                            onBackPressed();
                            finish();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Client> call, @NonNull Throwable t) {
                    Toast.makeText(getApplicationContext(), "An error has occurred" + t.getMessage(),
                            Toast.LENGTH_LONG).show();
                    Log.e("Error: ", "Something went wrong: " + t.getMessage());
                }
            });
        }
    }
}