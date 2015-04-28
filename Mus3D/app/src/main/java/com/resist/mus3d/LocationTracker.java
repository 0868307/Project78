package com.resist.mus3d;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import com.resist.mus3d.Map;
import org.osmdroid.util.GeoPoint;

/**
 * Created by Armindo on 24-3-2015.
 */
public class LocationTracker implements LocationListener {
    private GeoPoint currentLocation;
    private Activity activity;

    public LocationTracker(Activity activity) {
        this.activity = activity;
    }

    public void onLocationChanged(Location location) {
        currentLocation = new GeoPoint(location);
        activity.recreate();
    }

    public void onProviderDisabled(String provider) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}