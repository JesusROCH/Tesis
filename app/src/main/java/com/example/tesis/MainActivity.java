package com.example.tesis;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity {

//    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
//    private GoogleMap mMap;
//    private TextView textLatLong;
//    private ProgressBar progressBar;
//    LatLng sydney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                //getCurrentLocation();
//            } else {
//                Toast.makeText(this, "Permiso Denegado", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    private void getCurrentLocation() {
//        progressBar.setVisibility(View.VISIBLE);
//        final LocationRequest locationRequest = new LocationRequest();
//        locationRequest.setInterval(10000);
//        locationRequest.setFastestInterval(3000);
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        LocationServices.getFusedLocationProviderClient(MainActivity.this).requestLocationUpdates(locationRequest, new LocationCallback() {
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                super.onLocationResult(locationResult);
//                LocationServices.getFusedLocationProviderClient(MainActivity.this).removeLocationUpdates(this);
//                if (locationResult != null && locationResult.getLocations().size() > 0) {
//                    int latestLocationIndex = locationResult.getLocations().size() - 1;
//                    double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
//                    double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
//                    textLatLong.setText(
//                            String.format("Latitud: %s\nLongitud: %s",
//                                    latitude,
//                                    longitude)
//
//                    );
//                    sydney = new LatLng(-latitude, longitude);
//                }
//                progressBar.setVisibility(View.GONE);
//            }
//        }, Looper.getMainLooper());
//    }
}