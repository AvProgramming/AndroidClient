package com.example.restaurant.bundleinterface;

import com.example.restaurant.login.data.Result;
import com.example.restaurant.model.LoggedInUser;

public interface OnLoginClickListener {
    Result<LoggedInUser> onLoginClick(Result<LoggedInUser> result);
}
