package com.example.almasud.fundamental.map_location;

import android.annotation.SuppressLint;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.almasud.fundamental.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.maps.android.clustering.ClusterManager;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClusterNCurrentPlacesActivity extends AppCompatActivity implements OnMapReadyCallback {

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

    private GoogleMapOptions mMapOptions;
    private List<MarkerItem> mItems;
    private ClusterManager<MarkerItem> mClusterManager;
    private Marker mMarker;
    private List<Place.Field> mPlaceFields;
    private AutocompleteSupportFragment mAutocompleteSupportFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cluster_current_places);

        materialSearchBar = findViewById(R.id.autocomplete_search_bar);
        findBtn = findViewById(R.id.btn_fin_places);

        mItems = new ArrayList<>();
        mLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        Places.initialize(getApplicationContext(), getString(R.string.google_map_api_key));
        mPlacesClient = Places.createClient(this);
        final AutocompleteSessionToken sessionToken = AutocompleteSessionToken.newInstance();

//        mPlaceFields = Arrays.asList(Place.Field.ID, Place.Field.NAME,
//                Place.Field.ADDRESS, Place.Field.LAT_LNG);
//        mMapOptions = new GoogleMapOptions();
//        mMapOptions.mapType(GoogleMap.MAP_TYPE_TERRAIN);
//        mMapOptions.zoomControlsEnabled(true);
//        mMapOptions.compassEnabled(true);
//        SupportMapFragment mapFragment = SupportMapFragment.newInstance(mMapOptions);
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.map_fragment, mapFragment);
//        ft.commit();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();
//        setupPlaceAutoComplete();
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
                        mPlacesClient.fetchPlace(fetchPlaceRequest).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                            @Override
                            public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
                                Place place = fetchPlaceResponse.getPlace();
                                Log.e("My Tag", "Place found " + place.getName());
                                LatLng latLngOfPlace = place.getLatLng();
                                if (latLngOfPlace != null) {
                                    // Code for marker
                                    mMarker = mMap.addMarker(new MarkerOptions().position(latLngOfPlace)
                                            .title(place.getName()).snippet(place.getAddress()));
                                    mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                                        @Override
                                        public void onMapLongClick(LatLng latLng) {
                                            mItems.add(new MarkerItem(latLng));
                                            mClusterManager.addItems(mItems);
                                            mClusterManager.cluster();
                                        }
                                    });

                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngOfPlace, DEFAULT_ZOOM));
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (e instanceof ApiException) {
                                    ApiException apiException = (ApiException) e;
                                    apiException.printStackTrace();
                                    Log.e("My Tag", "Place not found: " + apiException.getMessage());
                                    Log.e("My Tag", "Statuse code: " + apiException.getStatusCode());
                                }
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
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        // Code for marker clustering
        mClusterManager = new ClusterManager<>(ClusterNCurrentPlacesActivity.this, mMap);
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        // Get device location
        getDeviceLocation();
        mMap.setMyLocationEnabled(true);
    }

    @SuppressLint("MissingPermission")
    private void getDeviceLocation() {
        mLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    mLastKnownLocation = task.getResult();
                    if (mLastKnownLocation != null) {
                        LatLng latLng = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                        // Code for marker
                        mMarker = mMap.addMarker(new MarkerOptions().position(latLng)
                        .title("Title").snippet("Snippet"));
                        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                            @Override
                            public void onMapLongClick(LatLng latLng) {
                                mItems.add(new MarkerItem(latLng));
                                mClusterManager.addItems(mItems);
                                mClusterManager.cluster();
                            }
                        });
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
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
                                LatLng latLng = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                                // Code for marker
                                mMarker = mMap.addMarker(new MarkerOptions().position(latLng)
                                        .title("Title").snippet("Snippet"));
                                mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                                    @Override
                                    public void onMapLongClick(LatLng latLng) {
                                        mItems.add(new MarkerItem(latLng));
                                        mClusterManager.addItems(mItems);
                                        mClusterManager.cluster();
                                    }
                                });
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                                mLocationProviderClient.removeLocationUpdates(mLocationCallback);
                            }
                        };
                        mLocationProviderClient.requestLocationUpdates(locationRequest, mLocationCallback, null);
                    }
                } else {
                    Toast.makeText(ClusterNCurrentPlacesActivity.this, "Unable to get last location", Toast.LENGTH_SHORT).show();
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
            case R.id.nearByPlaces:
                showCurrentPlaces();
                break;
        }
        return true;
    }

    private void showCurrentPlaces() {

    }
}
