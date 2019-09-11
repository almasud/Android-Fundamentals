package com.example.almasud.fundamental.map_location;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class NearByPlaces extends AsyncTask<Object, String, String> {
    private String mGooglePlaceData, mUrl;
    private GoogleMap mMap;

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap) objects[0];
        mUrl = (String) objects[1];

        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            mGooglePlaceData = downloadUrl.readPlaceUrl(mUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mGooglePlaceData;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        DataParser dataParser = new DataParser();
        List<HashMap<String, String>> nearByPlacesList = dataParser.parse(s);
        displayNearByPlaces(nearByPlacesList);
    }

    private void displayNearByPlaces(List<HashMap<String, String>> nearByPlacesList) {
        for (int i = 0; i <nearByPlacesList.size() ; i++) {
            HashMap<String, String> googleNearByPlace = nearByPlacesList.get(i);
            String name = googleNearByPlace.get("name");
            String vicinity = googleNearByPlace.get("vicinity");
            Double latitude = Double.parseDouble(googleNearByPlace.get("lat"));
            Double longitude = Double.parseDouble(googleNearByPlace.get("lng"));
            // Add marker to the map
            LatLng latLng = new LatLng(latitude, longitude);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(name + " : " + vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
        }
    }
}
