package com.resist.mus3d.map;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.resist.mus3d.GpsActivity;


/**
 * Created by Armindo on 24-3-2015.
 */
public class LocationTracker implements LocationListener, SensorEventListener {
    private Location currentLocation;
    private LocationManager mlocManager;
    private SensorManager sensorManager;
    private final Sensor mAccelerometer;
    private GpsActivity activity;
    private float lastDirection;

    public LocationTracker(GpsActivity activity) {
        this.activity = activity;
        sensorManager = (SensorManager)((Activity)activity).getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        mlocManager = (LocationManager) ((Activity)activity).getSystemService(Context.LOCATION_SERVICE);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        Location location = mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        onLocationChanged(location);
    }

    public void onLocationChanged(Location location) {
        currentLocation = location;
        activity.update();
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
    public float getLastDirection(){
        return lastDirection;
    }
    public void onStop(){
        mlocManager.removeUpdates(this);
        sensorManager.unregisterListener(this);
    }
    public void onResume(){
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        sensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        System.out.println(
                "directions :"+"\n"+
                        "\n"+event.values[0]+
                        "\n"+event.values[1]+
                        "\n"+event.values[2]

        );
        lastDirection = event.values[0];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}