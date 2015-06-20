package com.resist.mus3d.sensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class SensorTracker implements SensorEventListener {
    private static final float SKIP_DISTANCE = 1000;
    private static final int NOT_UPDATED_MAX = 30;
    private static final int INTERVAL = 200;
    private static final int COUNTER_MAX = 1000;
    private static final float DEGREE_DIFFERENCE = 10;
    private float directionY;
    private float[] mGravity;
    private float[] mMagnetic;
    private float total = 0;
    private int counter = 0;
    private boolean first = true;
    private int notUpdated;
    private SensorManager sensorManager;
    private SensorActivity activity;

    /**
     * Instantiates a new Sensor tracker.
     *
     * @param activity the activity
     */
    public SensorTracker(SensorActivity activity) {
        sensorManager = (SensorManager)((Activity)activity).getSystemService(Context.SENSOR_SERVICE);
        this.activity = activity;
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
        //return values[0]%DEGREE_DIFFERENCE >=DEGREE_DIFFERENCE/2 ? values[0] + DEGREE_DIFFERENCE-(values[0]%DEGREE_DIFFERENCE) : values[0]-(values[0]%DEGREE_DIFFERENCE);
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
        if(mGravity != null && mMagnetic != null && System.currentTimeMillis() % INTERVAL <= 2) {

            float x = 0;
            float y = directionY;
            float z = 0;
            if(((getDirection() > directionY -SKIP_DISTANCE && getDirection() < directionY +SKIP_DISTANCE) && (getDirection() > directionY + DEGREE_DIFFERENCE || getDirection() < directionY - DEGREE_DIFFERENCE)) || first ) {
                first = false;
                directionY = getDirection();
                notUpdated = 0;
            }else if(getDirection() < directionY -SKIP_DISTANCE || getDirection() > directionY +SKIP_DISTANCE){
                notUpdated++;
                if(notUpdated > NOT_UPDATED_MAX){
                    notUpdated = 0;
                    directionY = getDirection();
                }
            }
            activity.updateSensor(x, y, z);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * On stop.
     */
    public void onStop(){
        sensorManager.unregisterListener(this);
    }

    /**
     * On resume.
     */
    public void onResume(){
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_FASTEST);
    }

}


