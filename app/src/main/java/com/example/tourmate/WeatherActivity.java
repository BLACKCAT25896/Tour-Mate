package com.example.tourmate;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import com.example.tourmate.adapter.ViewPagweAdapter;
import com.example.tourmate.common.Common;
import com.example.tourmate.databinding.ActivityHomeBinding;
import com.example.tourmate.databinding.ActivityWeatherBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class WeatherActivity extends AppCompatActivity {
    private ActivityWeatherBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_weather);
//        setTitle("Weather");
//
//        setSupportActionBar(binding.toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.INTERNET).withListener(new MultiplePermissionsListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()) {
                    buildLocationRequest();
                    buildLocationCallback();

                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(WeatherActivity.this);
                    fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

                }

            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                Snackbar.make(binding.rootView,"Permission Denied",Snackbar.LENGTH_LONG).show();

            }
        }).check();


        init();



    }

    private void buildLocationCallback() {

        locationCallback = new LocationCallback(){

            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Common.current_location = locationResult.getLastLocation();
                setUpViewPager(binding.viewPager);

                binding.tabs.setupWithViewPager(binding.viewPager);
                Log.d("Location", locationResult.getLastLocation().getAltitude()+" / "+ locationResult.getLastLocation().getLongitude());

            }
        };



    }

    private void setUpViewPager(ViewPager viewPager) {


        ViewPagweAdapter adapter = new ViewPagweAdapter(getSupportFragmentManager());
        adapter.addFragment(TodayWeatherFragment.getInstance(),"Today");
        adapter.addFragment(ForecastFragment.getInstanse(),"5 Days");
        adapter.addFragment(CityFragment.getInstance(),"Cities");

        viewPager.setAdapter(adapter);

    }

    private void buildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10.0f);


    }

    private void init() {







    }

    public void back(View view) {
        startActivity(new Intent(WeatherActivity.this,HomeActivity.class));
    }
}
