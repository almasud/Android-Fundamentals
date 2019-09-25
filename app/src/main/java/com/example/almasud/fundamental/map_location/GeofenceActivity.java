package com.example.almasud.fundamental.map_location;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.almasud.fundamental.R;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class GeofenceActivity extends AppCompatActivity {
    private GeofencingClient mClient;
    private PendingIntent mPendingIntent;
    private ArrayList<Geofence> mGeofences;
    private Button geofenceToggleBtn;
    private boolean isMonitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geofence);
        geofenceToggleBtn = findViewById(R.id.buttonGeofenceMonitoring);

        mClient = LocationServices.getGeofencingClient(GeofenceActivity.this);
        mGeofences = new ArrayList<>();
        Geofence geofence = new Geofence.Builder()
                .setRequestId("Square_Hospital")
                .setCircularRegion(23.753711, 90.381158, 100)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .build();
        mGeofences.add(geofence);
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.addGeofences(mGeofences);
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        return builder.build();
    }

    private PendingIntent getPendingIntent() {
        if (mPendingIntent != null) {
            return mPendingIntent;
        } else {
            Intent intent = new Intent(this, GeofencingIntentService.class);
            mPendingIntent = PendingIntent.getService(this, 101, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            return mPendingIntent;
        }
    }

    private void startGeoFencing() {
        mClient.addGeofences(getGeofencingRequest(), getPendingIntent()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(GeofenceActivity.this, "Successfully Geofencing added.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(GeofenceActivity.this, "Failed to add Geofencing.", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("GeofencingActivity", e.getMessage());
            }
        });
    }

    private void stopGeoFencing() {
        mClient.removeGeofences(getPendingIntent()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(GeofenceActivity.this, "Successfully Geofencing removed.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(GeofenceActivity.this, "Failed to remove Geofencing.", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("GeofencingActivity", e.getMessage());
            }
        });
    }

    // Need to location access and GPS turn ON.
    public void geofencingMonitor(View view) {
        // Check if GPS is enabled or not and request the user to enabled it
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder locationRequestBuilder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        SettingsClient settingsClient= LocationServices.getSettingsClient(GeofenceActivity.this);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(locationRequestBuilder.build());
        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // Device location with high accuracy is already enabled
                if (geofenceToggleBtn.getText().toString().equals("Start Monitoring")) {
                    isMonitor = true;
                    startGeoFencing();
                    geofenceToggleBtn.setText("Stop Monitoring");
                } else {
                    isMonitor = false;
                    stopGeoFencing();
                    geofenceToggleBtn.setText("Start Monitoring");
                }
            }
        });
        task.addOnFailureListener(GeofenceActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException apiException = (ResolvableApiException) e;
                    try {
                        apiException.startResolutionForResult(GeofenceActivity.this, 11);
                    } catch (IntentSender.SendIntentException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isMonitor)
            stopGeoFencing();
    }
}
