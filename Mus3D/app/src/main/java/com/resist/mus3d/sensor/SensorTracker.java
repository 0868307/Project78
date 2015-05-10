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
    private float directionX;
    private float[] mGravity;
    private float[] mMagnetic;

    public SensorTracker(SensorActivity activity) {
        this.activity = activity;
        sensorManager = (SensorManager)((Activity)activity).getSystemService(Context.SENSOR_SERVICE);
    }

    private float getDirection()
    {

        float[] temp = new float[9];
        float[] R = new float[9];
        //Load rotation matrix into R
        SensorManager.getRotationMatrix(temp, null,
                mGravity, mMagnetic);

        //Remap to camera's point-of-view
        SensorManager.remapCoordinateSystem(temp,
                SensorManager.AXIS_X,
                SensorManager.AXIS_Z, R);

        //Return the orientation values
        float[] values = new float[3];
        SensorManager.getOrientation(R, values);

        //Convert to degrees
        for (int i=0; i < values.length; i++) {
            Double degrees = (values[i] * 180) / Math.PI;
            values[i] = degrees.floatValue();
        }

        return values[0];

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch(event.sensor.getType()) {

            case Sensor.TYPE_ACCELEROMETER:
                mGravity = event.values.clone();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                mMagnetic = event.values.clone();
                break;
            default:
                return;
        }
        if(mGravity != null && mMagnetic != null) {
            float x = getDirection();
            float y = -10;
            float z = 0;
            activity.updateSensor(x,y,z);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public void onStop(){
        sensorManager.unregisterListener(this);
    }
    public void onResume(){
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), sensorManager.SENSOR_DELAY_GAME);
    }
    public float getDirectionX(){
        return directionX;
    }
}
