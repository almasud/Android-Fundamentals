package com.example.almasud.fundamental.map_location;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class MarkerItem implements ClusterItem {
    private LatLng mLatLng;
    private String mTitle;
    private String mSnippet;

    public MarkerItem(LatLng mLatLng) {
        this.mLatLng = mLatLng;
    }

    public MarkerItem(LatLng mLatLng, String mTitle, String mSnippet) {
        this.mLatLng = mLatLng;
        this.mTitle = mTitle;
        this.mSnippet = mSnippet;
    }

    @Override
    public LatLng getPosition() {
        return mLatLng;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getSnippet() {
        return mSnippet;
    }
}
