package com.resist.mus3d.map;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.resist.mus3d.GpsActivity;


/**
 * Created by Armindo on 24-3-2015.
 */
public class LocationTracker implements LocationListener {
    private Location currentLocation;
    private LocationManager mlocManager;
    private GpsActivity activity;

    public LocationTracker(GpsActivity activity) {
        this.activity = activity;

        mlocManager = (LocationManager) ((Activity)activity).getSystemService(Context.LOCATION_SERVICE);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        Location location = mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        onLocationChanged(location);
    }

    public void onLocationChanged(Location location) {
       currentLocation = location;
    }

    public void onProviderDisabled(String provider) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
    public Location getCurrentLocation() {
        return currentLocation;
    }
    public void onStop(){
        mlocManager.removeUpdates(this);
    }
    public void onResume(){
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }
}