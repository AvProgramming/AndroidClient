package com.example.restaurant.login.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.restaurant.R;

public class LoginConfig {

    private Context context;
    private SharedPreferences sharedPreferences;

    public LoginConfig(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_file_key), Context.MODE_PRIVATE);
    }

    public boolean isUserLogin() {
        return sharedPreferences.getBoolean(context.getString(R.string.pref_is_user_login), false);
    }

    public void updateUserLoginStatus(boolean status) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_is_user_login), status);
        editor.apply();
    }

    public void saveNameOfUser(String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_name_of_user), name);
        editor.apply();
    }

    public void saveIdOfUser(Long id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(context.getString(R.string.pref_id_of_user), Math.toIntExact(id));
        editor.apply();
    }

    public void saveEmailAddress(String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_email_of_user), email);
        editor.apply();
    }

    public String getNameOfUser() {
        return sharedPreferences.getString(context.getString(R.string.pref_name_of_user), "Unknown");
    }

    public Integer getIdOfUser() {
        return sharedPreferences.getInt(context.getString(R.string.pref_id_of_user), 99999999);
    }

    public String getEmailOfUser() {
        return sharedPreferences.getString(context.getString(R.string.pref_email_of_user), "email");
    }


}
