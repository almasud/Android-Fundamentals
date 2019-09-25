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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class GeofenceActivity extends AppCompatActivity {
    private Button geofenceToggleBtn;
    private boolean isMonitor;

    private GeofencingClient geofencingClient;
    private List<Geofence> geofenceList;
    private PendingIntent geofencePendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geofence);
        geofenceToggleBtn = findViewById(R.id.buttonGeofenceMonitoring);

        //  Instantiate of the Geofencing client to access location APIs
        geofencingClient = LocationServices.getGeofencingClient(this);
        // Create and add geofences
        geofenceList = new ArrayList<>();
        geofenceList.add(new Geofence.Builder()
            // Set the request ID of the geofence. This is a string to identify this geofence.
            .setRequestId("DIU")
            .setCircularRegion(23.7529262,90.3815516, 100)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                    Geofence.GEOFENCE_TRANSITION_EXIT)
            .build());
    }


    // Specify geofences and initial triggers
    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(geofenceList);
        return builder.build();
    }

    // Define a broadcast receiver for geofence transitions
    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (geofencePendingIntent != null) {
            return geofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceBroadcastReceiver.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().
        geofencePendingIntent = PendingIntent.getBroadcast(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        return geofencePendingIntent;
    }

    // Start geofence monitoring
    private void startGeoFencing() {
        // Add geofences
        geofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent())
            .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // Geofences added
                    Toast.makeText(GeofenceActivity.this, "Successfully Geofencing added.", Toast.LENGTH_SHORT).show();
                }
            })
            .addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Failed to add geofences
                    Toast.makeText(GeofenceActivity.this, "Failed to add Geofencing.", Toast.LENGTH_SHORT).show();
                    Log.e("GeofencingActivity", e.getMessage());
                }
            });
    }

    // Stop geofence monitoring
    private void stopGeoFencing() {
        geofencingClient.removeGeofences(getGeofencePendingIntent())
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Geofences removed
                        Toast.makeText(GeofenceActivity.this, "Successfully Geofencing removed.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to remove geofences
                        Toast.makeText(GeofenceActivity.this, "Failed to remove Geofencing.", Toast.LENGTH_SHORT).show();
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
}
