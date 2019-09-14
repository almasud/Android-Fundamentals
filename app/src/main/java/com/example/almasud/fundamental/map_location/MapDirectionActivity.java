package com.example.almasud.fundamental.map_location;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.almasud.fundamental.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The api URL is only for test and alternative of google direction api
 * because the lack of google billing account.
 */

public class MapDirectionActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap map;
    private DirectionService service;
    private static final String BASE_URL = "https://api.tomtom.com/routing/";
    private String origin = "52.50931,13.42936";
    private String destination = "52.50274,13.43872";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_direction);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_direction);
        mapFragment.getMapAsync(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(DirectionService.class);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);

        getDirectionsData();
    }

    private void getDirectionsData() {
        String apiKey = getString(R.string.tomtom_direction_api_key);
        String urlString = String.format("1/calculateRoute/%s:%s/json?key=%s", origin, destination, apiKey);
        Call<DirectionResponse> directionResponseCall = service.getDirection(urlString);
        directionResponseCall.enqueue(new Callback<DirectionResponse>() {
            @Override
            public void onResponse(Call<DirectionResponse> call, Response<DirectionResponse> response) {
                if (response.code() == 200) {
                    DirectionResponse directionResponse = response.body();
                    List<DirectionResponse.Point> points = directionResponse.getRoutes().get(0)
                            .getLegs().get(0).getPoints();
                    // Initial camera LatLng
                    LatLng focusAreaLatLng = new LatLng(
                            points.get(0).getLatitude(), points.get(0).getLongitude());
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(focusAreaLatLng, 15));

                    for (int i = 1; i < points.size(); i++) {
                        double startLat = points.get(i-1).getLatitude();
                        double startLng = points.get(i-1).getLongitude();
                        double endLat = points.get(i).getLatitude();
                        double endLng = points.get(i).getLongitude();

                        LatLng start = new LatLng(startLat, startLng);
                        LatLng end = new LatLng(endLat, endLng);

                        Polyline polyline = map.addPolyline(new PolylineOptions()
                                .add(start).add(end).clickable(true));
                    }
                }
            }

            @Override
            public void onFailure(Call<DirectionResponse> call, Throwable t) {

            }
        });
    }
}
