package com.example.almasud.fundamental.shared_preference;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.almasud.fundamental.R;
import com.google.gson.Gson;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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

        final EditText name = findViewById(R.id.reg_name);
        final EditText email = findViewById(R.id.reg_email);
        final EditText password = findViewById(R.id.reg_password);
        Button registerBtn = findViewById(R.id.register_button);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = name.getText().toString();
                String newEmail = email.getText().toString();
                String newPassword = password.getText().toString();

                SharedPreferences preferences = getSharedPreferences(UserSessionManager.PREF_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(UserSessionManager.KEY_NAME, newName);
                editor.putString(UserSessionManager.KEY_EMAIL, newEmail);
                editor.putString(UserSessionManager.KEY_PASSWORD, newPassword);

                // Serialize Address object to JSON by using Google Gson library.
                Address address = new Address("Dinajpur", "Bangladesh");
                Gson gson = new Gson();
                String strGsonAddress = gson.toJson(address);
                editor.putString(UserSessionManager.ADDRESS, strGsonAddress);
                editor.commit();

                Intent loginScreen = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginScreen);
                finish();
            }
        });
    }

    class Address{
        private String city;
        private String country;

        public Address(String city, String country) {
            this.city = city;
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public String getCountry() {
            return country;
        }
    }

}
