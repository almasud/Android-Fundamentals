package com.example.almasud.fundamental.navigation;


import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.almasud.fundamental.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentFragment extends Fragment {

    private List<Location> mLocations;
    private WeatherService mWeatherService;
    private CurrentWeatherResponse mWeatherResponse;
    private double mLatitude;
    private double mLongitude;
    private List<Address> mAddresses;
    private String mCity;
    private String mCountry;

    public CurrentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_current, container, false);

        // Receive data from activity via FragmentPagerAdapter
        if (getArguments() != null
                && getArguments().containsKey(TabLayoutViewPagerActivity.LOCATIONS)
                && getArguments().containsKey(TabLayoutViewPagerActivity.WEATHER_SERVICE)) {

            mLocations = (List<Location>) getArguments().getSerializable(TabLayoutViewPagerActivity.LOCATIONS);
            mWeatherService = (WeatherService) getArguments().getSerializable(TabLayoutViewPagerActivity.WEATHER_SERVICE);

            if (mLocations != null && mWeatherService != null) {

                for (Location location : mLocations) {
                    mLatitude = location.getLatitude();
                    mLongitude = location.getLongitude();

                    Geocoder geocoder = new Geocoder(getContext());
                    try {
                        mAddresses = geocoder.getFromLocation(mLatitude, mLongitude, 1);
                        mCity = mAddresses.get(0).getLocality();
                        mCountry = mAddresses.get(0).getCountryName();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                final Handler handler = new Handler();
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                // Get weather information form mLatitude and mLongitude
                                String unit = "metric";  // For celsius scale
                                String urlString = String.format("weather?lat=%f&lon=%f&units=%s&appid=%s", mLatitude, mLongitude, unit, getString(R.string.weather_api));
                                Call<CurrentWeatherResponse> currentWeatherResponseCall = mWeatherService.getCurrentWeather(urlString);
                                currentWeatherResponseCall.enqueue(new Callback<CurrentWeatherResponse>() {
                                    @Override
                                    public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                                        if (response.code() == 200) {
                                            mWeatherResponse = response.body();
                                            // Free API not provide legal location or info so we use country and city form GeoCoder
//                                            String city = mWeatherResponse.getName();
//                                            String country = mWeatherResponse.getSys().getCountry();
                                            double temperature = mWeatherResponse.getMain().getTemp();
                                            double windSpeed = mWeatherResponse.getWind().getSpeed();
                                            double humidityPercent = mWeatherResponse.getMain().getHumidity();
                                            Date date = new Date();
                                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                            String today = dateFormat.format(date);

                                            List<CurrentWeatherResponse.Weather> weathers = mWeatherResponse.getWeather();
                                            String description = weathers.get(0).getDescription();
                                            String icon = weathers.get(0).getIcon();

                                            // Set in view
                                            ((TextView) view.findViewById(R.id.cWeatherDate)).setText(today);
                                            ((TextView) view.findViewById(R.id.cWeatherLocation)).setText(String.format("%s, %s", mCity, mCountry));
                                            ((TextView) view.findViewById(R.id.cWeatherTemp)).setText(String.format("%s °", temperature));
                                            ((TextView) view.findViewById(R.id.cWeatherDescription)).setText(description);
                                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into((ImageView) view.findViewById(R.id.cWeatherIcon));
                                            ((TextView) view.findViewById(R.id.cWeatherWindSpeed)).setText(String.format("%s km/h", windSpeed));
                                            ((TextView) view.findViewById(R.id.cWeatherHumidityPercent)).setText(String.format("%s %%", humidityPercent));
                                        } else {
                                            Log.e("onCreate", "Server not response");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {
                                        Log.e("OnFailure", t.getMessage());
                                    }
                                });
                            }
                        });
                    }
                }).start();

            }
        }

        return view;
    }

}
