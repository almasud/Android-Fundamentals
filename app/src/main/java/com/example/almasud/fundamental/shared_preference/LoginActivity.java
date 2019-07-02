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
import android.widget.Toast;

import com.example.almasud.fundamental.R;

public class LoginActivity extends AppCompatActivity {
    EditText loginEmail, loginPassword;
    Button loginBtn, registerBtn;
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

        // Get email and password input fields.
        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);

        // User login and registration button.
        loginBtn = findViewById(R.id.btn_login);
        registerBtn = findViewById(R.id.btn_login_reg);

        // Login button action.
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get username and password from input fields.
                String logEmail = loginEmail.getText().toString();
                String logPassword = loginPassword.getText().toString();

                // If username and password are filled.
                if(logEmail.trim().length() > 0
                        && logPassword.trim().length() > 0) {

                    SharedPreferences preferences = getSharedPreferences(UserSessionManager.PREF_NAME, MODE_PRIVATE);
                    String username = preferences.getString(UserSessionManager.KEY_EMAIL, null);
                    String password = preferences.getString(UserSessionManager.KEY_PASSWORD, null);

                    if(logEmail.equals(username) && logPassword.equals(password)) {
                        // Create an instance of UserSessionManager object.
                        session = new UserSessionManager(LoginActivity.this);
                        // Now create Login Session
                        session.createLoginSession();

                        Intent intent = new Intent(LoginActivity.this, SharedPreferenceActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Username or password doesn't match!",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Please fill out all fields.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        // Register button action of login form
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerScreen = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerScreen);
            }
        });

    }
}
