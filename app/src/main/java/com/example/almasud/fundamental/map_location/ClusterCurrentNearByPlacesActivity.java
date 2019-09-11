package com.example.almasud.fundamental.map_location;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.almasud.fundamental.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.maps.android.clustering.ClusterManager;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClusterCurrentNearByPlacesActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient mLocationProviderClient;
    private PlacesClient mPlacesClient;
    private List<AutocompletePrediction> predictionList;
    private Location mLastKnownLocation;
    private LocationCallback mLocationCallback;

    private MaterialSearchBar materialSearchBar;
    private View mapView;
    private Button findBtn;

    private final float DEFAULT_ZOOM = 17;

    private List<MarkerItem> mItems;
    private ClusterManager<MarkerItem> mClusterManager;
    private Double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cluster_current_places);

        materialSearchBar = findViewById(R.id.autocomplete_search_bar);
        findBtn = findViewById(R.id.btn_fin_places);
        mItems = new ArrayList<>();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();

        mLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        Places.initialize(getApplicationContext(), getString(R.string.google_map_api_key));
        mPlacesClient = Places.createClient(this);
        final AutocompleteSessionToken sessionToken = AutocompleteSessionToken.newInstance();

        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text.toString(), true, null, true);
            }

            @Override
            public void onButtonClicked(int buttonCode) {
                if (buttonCode == MaterialSearchBar.BUTTON_NAVIGATION) {
                    // Opening or closing a navigation drawer
                } else if (buttonCode == MaterialSearchBar.BUTTON_BACK) {
                    materialSearchBar.disableSearch();
                }
            }
        });

        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final FindAutocompletePredictionsRequest predictionsRequest = FindAutocompletePredictionsRequest
                        .builder().setCountry("bd").setTypeFilter(TypeFilter.ADDRESS)
                        .setSessionToken(sessionToken).setQuery(s.toString()).build();
                mPlacesClient.findAutocompletePredictions(predictionsRequest).addOnCompleteListener(new OnCompleteListener<FindAutocompletePredictionsResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<FindAutocompletePredictionsResponse> task) {
                        if (task.isSuccessful()) {
                            FindAutocompletePredictionsResponse predictionsResponse = task.getResult();
                            if (predictionsResponse != null) {
                                predictionList = predictionsResponse.getAutocompletePredictions();
                                List<String> suggestionsList = new ArrayList<>();
                                for (int i = 0; i < predictionList.size(); i++) {
                                    AutocompletePrediction prediction = predictionList.get(i);
                                    suggestionsList.add(prediction.getFullText(null).toString());
                                }
                                materialSearchBar.updateLastSuggestions(suggestionsList);
                                if (!materialSearchBar.isSuggestionsVisible()) {
                                    materialSearchBar.showSuggestionsList();
                                }
                            }
                        } else {
                            Log.e("My Tag", "Prediction task is unsuccessful");
                        }
                    }
                });

                materialSearchBar.setSuggestionsClickListener(new SuggestionsAdapter.OnItemViewClickListener() {
                    @Override
                    public void OnItemClickListener(int position, View v) {
                        if (position >= predictionList.size())
                            return;
                        AutocompletePrediction selectedPrediction = predictionList.get(position);
                        String suggestion = materialSearchBar.getLastSuggestions().get(position).toString();
                        materialSearchBar.setText(suggestion);
                        materialSearchBar.clearSuggestions();

                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        if (inputMethodManager != null)
                            inputMethodManager.hideSoftInputFromWindow(materialSearchBar.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
                        String placeId = selectedPrediction.getPlaceId();
                        List<Place.Field> placeFields = Arrays.asList(Place.Field.LAT_LNG);

                        FetchPlaceRequest fetchPlaceRequest = FetchPlaceRequest.builder(placeId, placeFields).build();
                        mPlacesClient.fetchPlace(fetchPlaceRequest).addOnSuccessListener(fetchPlaceResponse -> {
                            Place place = fetchPlaceResponse.getPlace();
                            Log.e("ClusterNCurrentPlaces", "Place found " + place.getName());
                            LatLng latLngOfPlace = place.getLatLng();
                            if (latLngOfPlace != null) {
                                // Code for marker
                                mMap.addMarker(new MarkerOptions().position(latLngOfPlace)
                                        .title(place.getName()).snippet(place.getAddress()));
                                mMap.setOnMapLongClickListener(latLng -> {
                                    mItems.add(new MarkerItem(latLng));
                                    mClusterManager.addItems(mItems);
                                    mClusterManager.cluster();
                                });

                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngOfPlace, DEFAULT_ZOOM));
                            }
                        }).addOnFailureListener(e -> {
                            if (e instanceof ApiException) {
                                ApiException apiException = (ApiException) e;
                                apiException.printStackTrace();
                                Log.e("My Tag", "Place not found: " + apiException.getMessage());
                                Log.e("My Tag", "Statuse code: " + apiException.getStatusCode());
                            }
                        });
                    }

                    @Override
                    public void OnItemDeleteListener(int position, View v) {

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Nearby Restaurant button
        findBtn.setOnClickListener(view -> {
            mMap.clear();
            String restaurant = "restaurant";
            String url = getUrl(latitude, longitude, restaurant);
            Object[] transferData = new Object[2];
            transferData[0] = mMap;
            transferData[1] = url;
            NearByPlaces nearByPlaces = new NearByPlaces();
            nearByPlaces.execute(transferData);
            Toast.makeText(this, "Searching for nearby restaurants...", Toast.LENGTH_SHORT).show();
        });
    }

    private String getUrl(Double latitude, Double longitude, String placeType) {
        StringBuilder googleUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googleUrl.append("location=" + latitude + "," + longitude);
        googleUrl.append("&radius=" + 3000);
        googleUrl.append("&type=" + placeType);
        googleUrl.append("&key=" + getString(R.string.google_place_api_key));

        return googleUrl.toString();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Code for marker clustering
        mClusterManager = new ClusterManager<>(ClusterCurrentNearByPlacesActivity.this, mMap);
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);

        // Define a region inside the map to reposition all map buttons
        mMap.setPadding(0, 150, 40, 160);
        // Specifically change the position of the my location button
        if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP,0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

            layoutParams.setMargins(0, 0, 40, 240);

            // Check if GPS is enabled or not and request the user to enabled it
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setInterval(10000);
            locationRequest.setFastestInterval(5000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

            SettingsClient settingsClient= LocationServices.getSettingsClient(ClusterCurrentNearByPlacesActivity.this);
            Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());
            task.addOnSuccessListener(ClusterCurrentNearByPlacesActivity.this, new OnSuccessListener<LocationSettingsResponse>() {
                @Override
                public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                    // Device location with high accuracy is already enabled
                    // Get device location
                    getDeviceLocation();
                }
            });

            task.addOnFailureListener(ClusterCurrentNearByPlacesActivity.this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (e instanceof ResolvableApiException) {
                        ResolvableApiException apiException = (ResolvableApiException) e;
                        try {
                            apiException.startResolutionForResult(ClusterCurrentNearByPlacesActivity.this, 11);
                        } catch (IntentSender.SendIntentException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });

            mMap.setOnMyLocationButtonClickListener(() -> {
                if (materialSearchBar.isSuggestionsVisible())
                    materialSearchBar.clearSuggestions();
                if (materialSearchBar.isSearchEnabled())
                    materialSearchBar.disableSearch();
                return false;
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11) {
            if (resultCode == RESULT_OK) {
                // Device location with high accuracy enabled is confirmed
                // Get device location
                getDeviceLocation();
            }
        }
    }

    private void getDeviceLocation() {
        mLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    mLastKnownLocation = task.getResult();
                    if (mLastKnownLocation != null) {
                        // Initialize latitude and longitude
                        latitude = mLastKnownLocation.getLatitude();
                        longitude = mLastKnownLocation.getLongitude();
                        // Add marker on current location
                        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));
                        // Move camera on the location
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), DEFAULT_ZOOM));
                        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                            @Override
                            public void onMapLongClick(LatLng latLng) {
//                                mItems.add(new MarkerItem(latLng));
//                                mClusterManager.addItems(mItems);
//                                mClusterManager.cluster();
                            }
                        });

                    } else {
                        LocationRequest locationRequest = LocationRequest.create();
                        locationRequest.setInterval(10000);
                        locationRequest.setFastestInterval(5000);
                        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                        mLocationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                super.onLocationResult(locationResult);
                                if (locationResult == null)
                                    return;
                                mLastKnownLocation = locationResult.getLastLocation();
                                // Add marker on current location
                                mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));
                                // Move camera on the location
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), DEFAULT_ZOOM));
                                mLocationProviderClient.removeLocationUpdates(mLocationCallback);
                                mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                                    @Override
                                    public void onMapLongClick(LatLng latLng) {
//                                mItems.add(new MarkerItem(latLng));
//                                mClusterManager.addItems(mItems);
//                                mClusterManager.cluster();
                                    }
                                });

                            }
                        };
                        mLocationProviderClient.requestLocationUpdates(locationRequest, mLocationCallback, null);
                    }
                } else {
                    Toast.makeText(ClusterCurrentNearByPlacesActivity.this, "Unable to get last location", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.currentPlaces:
                showCurrentPlaces();
                break;
        }
        return true;
    }

    private void showCurrentPlaces() {
        // Use fields to define the data types to return.
        List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME,
                Place.Field.LAT_LNG, Place.Field.ADDRESS);
        // Use the builder to create a FindCurrentPlaceRequest.
        FindCurrentPlaceRequest request =
                FindCurrentPlaceRequest.newInstance(placeFields);
        Task<FindCurrentPlaceResponse> placeResponse = mPlacesClient.findCurrentPlace(request);
        placeResponse.addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                FindCurrentPlaceResponse response = task.getResult();
                int count = response.getPlaceLikelihoods().size();

                String[] names = new String[count];
                String[] addresses = new String[count];
                LatLng[] latlangs = new LatLng[count];

                for (int i=0; i < count; i++) {
                    Place place = response.getPlaceLikelihoods().get(i).getPlace();
                    names[i] = place.getName();
                    addresses[i] = place.getAddress();
                    latlangs[i] = place.getLatLng();
                    mItems.add(new MarkerItem(latlangs[i], names[i], addresses[i]));
                }
                mClusterManager.addItems(mItems);
                mClusterManager.cluster();
                openDialogPlaces(names, addresses, latlangs);
            } else {
                Exception exception = task.getException();
                if (exception instanceof ApiException) {
                    ApiException apiException = (ApiException) exception;
                    Log.e("My Tag", "Place not found: " + apiException.getStatusCode());
                }
            }
        });
    }

    private void openDialogPlaces(String[] names, String[] addresses, LatLng[] latlangs) {
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                LatLng latLng = latlangs[i];
                String name = names[i];
                String address = addresses[i];
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng)
                        .title(name).snippet(address)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
            }
        };
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setItems(names, listener).show();
    }
}
