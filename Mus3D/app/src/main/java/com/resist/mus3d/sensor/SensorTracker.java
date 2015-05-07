package com.resist.mus3d.sensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;

/**
 * Created by Wouter on 5/7/2015.
 */
public class SensorTracker implements SensorEventListener {
    SensorActivity activity;
    private SensorManager sensorManager;
    private final Sensor mAccelerometer;
    private float directionX;

    public SensorTracker(SensorActivity activity) {
        this.activity = activity;
        sensorManager = (SensorManager)((Activity)activity).getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        directionX = (360+directionX+event.values[0])%360;
        activity.updateSensor();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public void onStop(){
        sensorManager.unregisterListener(this);
    }
    public void onResume(){
        sensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    public float getDirectionX(){
        return directionX;
    }
}
