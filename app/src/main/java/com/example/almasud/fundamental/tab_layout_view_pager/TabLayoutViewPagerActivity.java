package com.example.almasud.fundamental.tab_layout_view_pager;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import com.google.android.material.tabs.TabLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.almasud.fundamental.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.Serializable;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TabLayoutViewPagerActivity extends AppCompatActivity {
    public static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    public static final String LOCATIONS = "locations";
    public static final String WEATHER_SERVICE = "weatherService";
    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private WeatherService mWeatherService;
    private static final int REQUEST_CODE = 1;
    private List<Location> locations;
    private Boolean mLocationGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialization of views
        setContentView(R.layout.activity_tab_layout_view_pager);
        tabLayout = findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.viewPager);

        // Creating new tab layouts
        tabLayout.addTab(tabLayout.newTab().setText("Current Weather").setIcon(R.mipmap.ic_launcher_round));
        tabLayout.addTab(tabLayout.newTab().setText("Forecast Weather").setIcon(R.mipmap.ic_launcher_round));

        // Instantiating retrofit to get weather information
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Initialize mWeatherService
        mWeatherService = retrofit.create(WeatherService.class);

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

                String message = "Device location is needed to show current weather.";
                showAlertDialog("Permission required", message, new ClickListener() {
                    @Override
                    public void onClick() {
                        // Request the permission again
                        ActivityCompat.requestPermissions(TabLayoutViewPagerActivity.this,
                                new String[]{
                                        Manifest.permission.ACCESS_COARSE_LOCATION,
                                        Manifest.permission.ACCESS_FINE_LOCATION},
                                REQUEST_CODE);
                    }
                }, true);

            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE);
            }
        } else {
            // Permission has already been granted
            mLocationGranted = true;
            // Get mLatitude and mLongitude
            FusedLocationProviderClient locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationCallback locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);

                    locations = locationResult.getLocations();
                    WeatherPagerAdapter  pagerAdapter = new WeatherPagerAdapter(getSupportFragmentManager(),
                                tabLayout.getTabCount(), locations, mWeatherService);
                    mViewPager.setAdapter(pagerAdapter);
                    // Change Tab Layout with View Pager
                    mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                    tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                        @Override
                        public void onTabSelected(TabLayout.Tab tab) {
                            mViewPager.setCurrentItem(tab.getPosition());
                        }

                        @Override
                        public void onTabUnselected(TabLayout.Tab tab) {

                        }

                        @Override
                        public void onTabReselected(TabLayout.Tab tab) {

                        }
                    });
                }
            };

            locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }

    // Permission callback method
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED &&
                        !ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                    // User is denied and check don't ask again button
                    String message = "Device location is needed to show current weather.";
                    showAlertDialog("Permission required", message, new ClickListener() {
                        @Override
                        public void onClick() {
                            // Go to application details settings for permission
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    }, true);
                } else if (grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission is successfully granted.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public class WeatherPagerAdapter extends FragmentPagerAdapter {
        private int tabCount;
        private Fragment fragment;
        private List<Location> locations;
        private WeatherService weatherService;

        public WeatherPagerAdapter(FragmentManager fm, int tabCount, List<Location> locations, WeatherService weatherService) {
            super(fm);
            this.tabCount = tabCount;
            this.locations = locations;
            this.weatherService = weatherService;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    fragment = new CurrentFragment();
                    if (locations != null) {
                        Bundle b = new Bundle();
                        b.putSerializable(LOCATIONS, (Serializable) locations);
                        b.putSerializable(WEATHER_SERVICE, (Serializable) weatherService);
                        fragment.setArguments(b);
                    } else {
                        Log.e("FragmentPagerAdapter", " locations is null");
                    }

                    break;
                case 1:
                    fragment = new ForecastFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return tabCount;
        }
    }

    public void showAlertDialog(String title, String message, final ClickListener clickListener, Boolean cancelOption) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher_round)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       if (clickListener != null) {
                           clickListener.onClick();
                       }
                    }
                });

        if (cancelOption) {
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }
        builder.show();
    }

    // Custom click listener
    public interface ClickListener {
        void onClick();
    }

}
