package com.example.almasud.fundamental.firebase_service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.almasud.fundamental.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

public class CustomAuthActivity extends AppCompatActivity {
    private EditText eTEmail, eTPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_auth);
        eTEmail = findViewById(R.id.editText_auth_Email);
        eTPassword = findViewById(R.id.editText_auth_Password);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    public void signUp(View view) {
        String email = eTEmail.getText().toString();
        String password = eTPassword.getText().toString();

        if (email.isEmpty()) {
            eTEmail.setError("Email is required!");
        } else if (password.isEmpty()) {
            eTPassword.setError("Password is required!");
        } else {
            mAuth.createUserWithEmailAndPassword(eTEmail.getText().toString(), eTPassword.getText().toString())
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        eTEmail.setText("");
                        eTPassword.setText("");
                        Toast.makeText(CustomAuthActivity.this, "Sign up success", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CustomAuthActivity.this, "Sign up failed.\n" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }
}
