package com.example.almasud.fundamental.firebase_service.build_in_auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.almasud.fundamental.R;
import com.example.almasud.fundamental.firebase_service.FirebaseActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

public class BuildInAuthActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    private ImageView userIV;
    private TextView userNameTV, userEmailTV, eVerityNoticeTV;
    private Button emailVerifyBtn, addEventActivityBtn, logoutBtn;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_in_auth);
        userIV = findViewById(R.id.imageFbaseAuthUser);
        userNameTV = findViewById(R.id.textViewFbaseAuthUname);
        userEmailTV = findViewById(R.id.textViewFbaseAuthUemail);
        eVerityNoticeTV = findViewById(R.id.textViewEmailverifyNotice);
        emailVerifyBtn = findViewById(R.id.buttonFabaseEmailVerify);
        addEventActivityBtn = findViewById(R.id.buttonAddEventActivity);
        logoutBtn = findViewById(R.id.buttonFbaseAuthLogout);

        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        user = mAuth.getCurrentUser();
        if (user == null) {
            // Choose authentication providers
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.PhoneBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build());
            // Create and launch sign-in intent
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN);
        } else {
            // Show profile if already sign in.
            profileUI(user);
            Toast.makeText(this, "Logged in as: " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN && resultCode == RESULT_OK) {
            // Start activity again after authentication
            startActivity(new Intent(this, BuildInAuthActivity.class));
            finish();
        }
    }

    private void profileUI(FirebaseUser user) {
        // Visible the profile UIs
        userIV.setVisibility(View.VISIBLE);
        userNameTV.setVisibility(View.VISIBLE);
        userEmailTV.setVisibility(View.VISIBLE);
        logoutBtn.setVisibility(View.VISIBLE);

        // Set Name, email address, and profile photo Url into profile UIs
        userNameTV.setText("Name: " + user.getDisplayName());
        userEmailTV.setText("Email: " + user.getEmail());
        boolean isEmailVerified = user.isEmailVerified();
        if (user.getPhotoUrl() != null)
            Picasso.get().load(user.getPhotoUrl()).into(userIV);

        // Check email is verified or not
        if (isEmailVerified) {
            emailVerifyBtn.setVisibility(View.INVISIBLE);
            eVerityNoticeTV.setVisibility(View.INVISIBLE);
            addEventActivityBtn.setVisibility(View.VISIBLE);
        } else {
            emailVerifyBtn.setVisibility(View.VISIBLE);
            eVerityNoticeTV.setVisibility(View.VISIBLE);
            emailVerifyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(BuildInAuthActivity.this, "A verification email is sent to: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    public void fbaseAuthLogout(View view) {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                public void onComplete(@NonNull Task<Void> task) {
                    startActivity(new Intent(BuildInAuthActivity.this, FirebaseActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                }
            });
    }

    public void eventAddActivity(View view) {
        startActivity(new Intent(this, EventActivity.class));
    }
}
