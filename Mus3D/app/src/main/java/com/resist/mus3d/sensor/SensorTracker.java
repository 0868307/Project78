package com.resist.mus3d.sensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by Wouter on 5/7/2015.
 */
public class SensorTracker implements SensorEventListener {
    SensorActivity activity;
    private SensorManager sensorManager;
    private final int COUNTER_MAX = 20;
    private float directionX;
    private float[] mGravity;
    private float[] mMagnetic;
    private int counter = 0;
    private float total =0;
    private boolean first = true;
    private int notupdated;

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
                SensorManager.AXIS_Y,
                SensorManager.AXIS_Z, R);

        //Return the orientation values
        float[] values = new float[3];
        SensorManager.getOrientation(R, values);

        //Convert to degrees
        for (int i=0; i < values.length; i++) {
            Double degrees = (values[i] * 180) / Math.PI;
            values[i] = degrees.floatValue();
        }
        return values[0]%5 >=3 ? values[0] + 5-(values[0]%5) : values[0]-(values[0]%5);
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
            int difference = 15;
            if((getDirection() > total/counter-difference && getDirection() < total/counter+difference) || first ) {
                first = false;
                total += getDirection();
                if (counter >= COUNTER_MAX) {
                    directionX = total / counter;
                    total = 0;
                    counter = 0;
                    first = true;
                }
                counter++;
            }else{
                notupdated++;
                if(notupdated > COUNTER_MAX){
                    notupdated = 0;
                    directionX = getDirection();
                }
            }
            float x = 0;
            float y = (directionX+180)%360;
            float z = 0;

            activity.updateSensor(x, y, z);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public void onStop(){
        sensorManager.unregisterListener(this);
    }
    public void onResume(){
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), sensorManager.SENSOR_DELAY_UI);
    }
    public float getDirectionX(){
        return directionX;
    }
}
