package com.example.almasud.fundamental.map_location;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.almasud.fundamental.MainActivity;
import com.example.almasud.fundamental.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;

public class CurrentLocationActivity extends AppCompatActivity {
    private TextView latView, longView, addressView;
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);
        latView = findViewById(R.id.latitudeView);
        longView = findViewById(R.id.longitudeView);
        addressView = findViewById(R.id.addressView);

        // Check runtime permission
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation why need permission.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) &&
                    ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)) {

                String message = "Location permissions is needed to show current location.";
                MainActivity.messageDialog(this,"Alert", message, "We hope you understand.");

            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this, new String[]{
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE);

                // requestCode is app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
            LocationRequest request = new LocationRequest()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(3000)
                    .setFastestInterval(1000);
            LocationCallback callback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);

                    for (Location location : locationResult.getLocations()) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        Geocoder geocoder = new Geocoder(CurrentLocationActivity.this);
                        try {
                            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                            String city = addresses.get(0).getLocality();
                            addressView.setText(city);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        latView.setText(String.valueOf(latitude));
                        longView.setText(String.valueOf(longitude));
                    }
                }
            };
            client.requestLocationUpdates(request, callback, null);
        }
    }


    // Permission callback method
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                MainActivity.messageDialog(this,"Success", "Your permission is successfully granted.\nNow you can show your current location.", null);
            }
        }
    }
}
