package com.example.almasud.fundamental.map_location;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.almasud.fundamental.R;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

    }

    public void activityCurrentLocation(View view) {
        startActivity(new Intent(this, CurrentLocationActivity.class));
    }

    public void activityMap(View view) {
        startActivity(new Intent(this, MapMarkerActivity.class));
    }

    public void activityClusterPlaces(View view) {
        startActivity(new Intent(this, LocationPermissionActivity.class));
    }
}
