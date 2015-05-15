package com.resist.mus3d;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.resist.mus3d.map.GpsActivity;
import com.resist.mus3d.map.LocationTracker;
import com.resist.mus3d.map.Map;
import com.resist.mus3d.sensor.SensorActivity;
import com.resist.mus3d.sensor.SensorTracker;

import rajawali.RajawaliActivity;

public class Rajawali extends RajawaliActivity implements GpsActivity, SensorActivity, View.OnTouchListener {
    private MyRenderer myRenderer;
    private LocationTracker locationListener;
    private SensorTracker sensorTracker;
    private int counter = 1000;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myRenderer = new MyRenderer(this);
        myRenderer.setSurfaceView(mSurfaceView);
        mSurfaceView.setOnTouchListener(this);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.CENTER);

        TextView label = new TextView(this);
        label.setText("Hola");
        label.setTextSize(20);
        label.setGravity(Gravity.CENTER);

        label.setHeight(100);
        ll.addView(label);

        mLayout.addView(ll);


        super.setRenderer(myRenderer);
        locationListener = new LocationTracker(this);
        sensorTracker = new SensorTracker(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings3d, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case R.id.settings_Settings:
                Intent i = new Intent(this, Settings.class);
                startActivity(i);
                break;
            case R.id.action_2D:
                Intent j = new Intent(this, Map.class);
                startActivity(j);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStop() {
        locationListener.onStop();
        sensorTracker.onStop();
        super.onStop();
    }

    @Override
    protected void onResume() {
        locationListener.onResume();
        sensorTracker.onResume();
        super.onResume();
    }
    @Override
    public void update() {
        if(locationListener != null) {
            /*System.out.println("");
            System.out.println(
                    "latitude : " + (float) locationListener.getCurrentLocation().getLatitude() +
                            "\n longtitude : " + (float) locationListener.getCurrentLocation().getLongitude() +
                            "\n altitude : " + (float) locationListener.getCurrentLocation().getAltitude()+
                    "\n angle : " +  locationListener.getLastDirection()
            );
            */
            myRenderer.setCamera(
                    (float) locationListener.getCurrentLocation().getLongitude()*MyRenderer.MULTIPLIER,
                    0,
                    (float) locationListener.getCurrentLocation().getLatitude()*MyRenderer.MULTIPLIER
            );
            if(counter > 1000){
                myRenderer.makeObjects();
                counter = 0;
            }
        }
        counter++;
    }
    public Location getLocation(){
        if(locationListener != null && locationListener.getCurrentLocation() != null)
            return locationListener.getCurrentLocation();
        Location location = new Location((String)null);
        location.setLatitude(0);
        location.setLongitude(0);
        return location;
    }

    @Override
    public void updateSensor(float x,float y,float z) {
        if(sensorTracker != null){
            myRenderer.setCameraRotation(x,-y,z);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            myRenderer.getObjectAt(event.getX(), event.getY());

        }

        return super.onTouchEvent(event);
    }
}
