package com.example.restaurant.login.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.restaurant.R;
import com.example.restaurant.activities.MainActivity;
import com.example.restaurant.databinding.ActivityLoginBinding;
import com.example.restaurant.login.data.LoginConfig;
import com.example.restaurant.login.register.RegisterActivity;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    private boolean isRememberUserLogin = false;
    private LoginConfig loginConfig;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        loginConfig = new LoginConfig(this);

        final TextInputEditText usernameEditText = findViewById(R.id.loginUsername);
        final TextInputEditText passwordEditText = findViewById(R.id.loginPassword);
        final CheckBox rememberMe = findViewById(R.id.rememberMe);
        final Button loginButton = binding.loginBtn;
        final TextView registerButton = binding.toRegisterActivityBtn;

        loginButton.setBackgroundColor(getColor(R.color.inactive_button));

        loginViewModel.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            loginButton.setEnabled(loginFormState.isDataValid());
            if (loginButton.isEnabled()) {
                loginButton.setBackgroundColor(getColor(R.color.button));
            }
            if (loginFormState.getUsernameError() != null) {
                usernameEditText.setError(getString(loginFormState.getUsernameError()));
            }
            if (loginFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(loginFormState.getPasswordError()));
            }
        });

        loginViewModel.getLoginResult().observe(this, loginResult -> {
            if (loginResult == null) {
                return;
            }

            if (loginResult.getError() != null) {
                showLoginFailed();

            }
            if (loginResult.getSuccess() != null) {
                updateUiWithUser(loginResult.getSuccess());
                setResult(Activity.RESULT_OK);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
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
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(), passwordEditText.getText().toString());
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
                loginViewModel.passDataChanged(passwordEditText.getText().toString(), usernameEditText.getText().toString());
            }
        };
        passwordEditText.addTextChangedListener(afterPassChangedListener);
        usernameEditText.addTextChangedListener(afterEmailChangedListener);
        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(), isRememberUserLogin, getApplicationContext());
            }
            return false;
        });

        registerButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), RegisterActivity.class)));

        loginButton.setOnClickListener(v -> loginViewModel.login(usernameEditText.getText().toString(),
                passwordEditText.getText().toString(), isRememberUserLogin, getApplicationContext()));

        rememberMe.setOnClickListener(view -> isRememberUserLogin = rememberMe.isChecked());

        if (loginConfig.isUserLogin()) {
            String name = loginConfig.getNameOfUser();
            String welcome = getString(R.string.welcome) + name;
            Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
            setResult(Activity.RESULT_OK);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed() {
        Toast.makeText(getApplicationContext(),"Email or password is invalid", Toast.LENGTH_SHORT).show();
    }
}