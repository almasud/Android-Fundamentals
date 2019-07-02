package com.example.almasud.fundamental.shared_preference;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.almasud.fundamental.R;

import java.util.HashMap;

public class SharedPreferenceActivity extends AppCompatActivity {
    Button logoutBtn;
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // UserSessionManager class instance.
        session = new UserSessionManager(SharedPreferenceActivity.this);
//        // Create Session
//        session.createLoginSession();
        /*
            Check the user is login or not. If user is not logged in,
            This will redirect the user to LoginActivity and finish current
            activity from activity stack.
         */
        if(!session.isUserLoggedIn()) {
            session.loginScreen();
            finish();
        } else{
            // Get views
            logoutBtn = findViewById(R.id.btn_logout);
            TextView nameView = findViewById(R.id.user_name);
            TextView emailView = findViewById(R.id.user_email);
            TextView cityView = findViewById(R.id.user_city);
            TextView countryView = findViewById(R.id.user_country);

            // Get user data from session
            HashMap<String, String> user = session.getUserDetails();
            RegisterActivity.Address address = session.getAddressObject();

            // Set Name, Email, City and Country in Views.
            nameView.setText(user.get(UserSessionManager.KEY_NAME));
            emailView.setText(user.get(UserSessionManager.KEY_EMAIL));
            cityView.setText(address.getCity());
            countryView.setText(address.getCountry());

            //Logout button action
            logoutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Clear user session data and redirect to login activity.
                    session.logoutUser();
                }
            });
        }
    }
}
