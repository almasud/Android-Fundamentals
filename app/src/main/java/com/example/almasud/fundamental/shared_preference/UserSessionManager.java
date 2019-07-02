package com.example.almasud.fundamental.shared_preference;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.HashMap;

class UserSessionManager {
    // Reference of Shared Preference .
    private SharedPreferences pref;
    // Editor reference for shared preference.
    private SharedPreferences.Editor editor;
    // Activity context
    private Context context;
    // Shared Preference file name
    static final String PREF_NAME = "MYPREFS";
    // User is log in or not status.
    private static final String IS_USER_LOGIN = "IsLoggedIn";
    // Name
    static final String KEY_NAME = "name";
    // Email
    static final String KEY_EMAIL = "email";
    // Password
    static final String KEY_PASSWORD = "password";
    // Address
    static final String ADDRESS = "address";

    /**
     * A constructor that initialize the field values and
     * return the reference of this class.
     * @param context Context of the current class.
     */
    UserSessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    /**
     * Create a session for an user login.
     */
    void createLoginSession() {
        // Storing login value as true.
        editor.putBoolean(IS_USER_LOGIN, true);
        // Commit changes
        editor.commit();
    }

    /*
        Check the user is login or not. If user is not logged in,
        This will redirect the user to LoginActivity.
     */
    void loginScreen() {
        // Check login status.
        if(!isUserLoggedIn()) {
            // Redirect to login activity if user is not login.
            Intent loginScreen = new Intent(context, LoginActivity.class);
            context.startActivity(loginScreen);
        }
    }

    // Clear login session
    void logoutUser() {
        // Cleaning all data in Shared Preference.
        editor.clear();
        editor.commit();

        // After logout redirect user to login activity.
        Intent loginScreen = new Intent(context, LoginActivity.class);
        // Closing all the activity
        loginScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new flag to start new Activity
        loginScreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Starting login activity
        context.startActivity(loginScreen);
    }

    // Check for user login
    boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }

    // Get stored session data
    HashMap<String, String> getUserDetails() {
        // Use HashMap to store user credentials.
        HashMap<String, String> user = new HashMap<>();
        // Put username in user HashMap object.
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        // Put email in user HashMap object.
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        // Put password in user HashMap object.
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));

        return user;
    }

    // Deserialize Address object from JSON by using Google Gson library.
    public RegisterActivity.Address getAddressObject() {
        String strGsonAddress = pref.getString(ADDRESS, null);
        Gson gson = new Gson();

        return gson.fromJson(strGsonAddress, RegisterActivity.Address.class);
    }

}
