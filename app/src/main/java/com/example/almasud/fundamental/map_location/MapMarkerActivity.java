package com.example.almasud.fundamental.map_location;

import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.almasud.fundamental.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapMarkerActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private GoogleMapOptions mapOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_marker);
        mapOptions = new GoogleMapOptions();
        mapOptions.mapType(GoogleMap.MAP_TYPE_TERRAIN);
        mapOptions.compassEnabled(true);
        mapOptions.zoomControlsEnabled(true);
        SupportMapFragment mapFragment = SupportMapFragment.newInstance(mapOptions);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.map_container, mapFragment);
        ft.commit();

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLngDPI = new LatLng(25.6231205, 88.6450555);
        map = googleMap;
        map.addMarker(new MarkerOptions().position(latLngDPI)
                .title("Dinajpur Polytechnic Institute (DPI)")
                .snippet("Dinajpur, Rangpur, Bangladesh"));
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                map.clear();
                map.addMarker(new MarkerOptions().position(latLng));
            }
        });
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngDPI, 17));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, 1);
            return;
        }
        map.setMyLocationEnabled(true);
    }
}
