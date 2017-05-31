package com.academiamoviles.seminario.services;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class GeolocationService extends Service {

    GoogleApiClient mGoogleApiClient;

    LocationRequest mLocationRequest;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("GeolocationService", "onCreate");
    }

    private void setupLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10 * 1000); // milisegundos
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); // consumo de bateria
        mLocationRequest.setSmallestDisplacement(5); // metros
    }

    private void setupGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(GeolocationService.this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        if (ActivityCompat.checkSelfPermission(GeolocationService.this, Manifest.permission.ACCESS_FINE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED &&
                                ActivityCompat.checkSelfPermission(GeolocationService.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                                        != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, new LocationListener() {
                            @Override
                            public void onLocationChanged(Location location) {
                                if (location != null) {
                                    Log.i("GeolocationService", "latitud: " + location.getLatitude());
                                    Log.i("GeolocationService", "longitud: " + location.getLongitude());
                                }
                            }
                        });
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .build();

        mGoogleApiClient.connect();

    }


    // START_STICKY : el servicio continua vivo
    // START_NOT_STICKY : el servicio muere junto con la app

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("GeolocationService", "onStartCommand");

        setupLocationRequest();
        setupGoogleApiClient();

        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("GeolocationService", "onDestroy");
    }


    public GeolocationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
