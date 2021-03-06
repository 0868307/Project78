package com.resist.mus3d.map;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.resist.mus3d.GpsActivity;

public class LocationTracker implements LocationListener {
    private static Location currentLocation;
    private LocationManager mlocManager;
    private GpsActivity activity;

    /**
     * Instantiates a new Location tracker.
     *
     * @param activity the activity
     */
    public LocationTracker(GpsActivity activity) {
        this.activity = activity;
        mlocManager = (LocationManager) ((Activity) activity).getSystemService(Context.LOCATION_SERVICE);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        currentLocation = mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        activity.update();
    }

    /**
     * Gets current location.
     *
     * @return the current location
     */
    public static Location getCurrentLocation() {
        return currentLocation;
    }

    public void onLocationChanged(Location location) {
        currentLocation = location;
        activity.update();
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    /**
     * On stop.
     */
    public void onStop() {
        mlocManager.removeUpdates(this);
    }

    /**
     * On resume.
     */
    public void onResume() {
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }
}