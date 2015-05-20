package com.resist.mus3d;

import android.app.ActionBar;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.resist.mus3d.map.GpsActivity;
import com.resist.mus3d.map.LocationTracker;
import com.resist.mus3d.map.Map;
import com.resist.mus3d.sensor.SensorActivity;
import com.resist.mus3d.sensor.SensorTracker;

import java.util.logging.Handler;

import rajawali.RajawaliActivity;

public class Rajawali extends RajawaliActivity implements GpsActivity, SensorActivity, View.OnTouchListener {
    private MyRenderer myRenderer;
    private LocationTracker locationListener;
    private SensorTracker sensorTracker;
    private int counter = 1000;
    private ProgressBar mProgBarD;
    private ProgressBar mProgBarI;
    private TextView mLabel;
    private Spinner mSpinner;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myRenderer = new MyRenderer(this);
        myRenderer.setSurfaceView(mSurfaceView);
        mSurfaceView.setOnTouchListener(this);
        createUI();

        super.setRenderer(myRenderer);
        locationListener = new LocationTracker(this);
        sensorTracker = new SensorTracker(this);
    }

    private void createUI(){
        // The label
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.CENTER_HORIZONTAL + Gravity.CENTER_VERTICAL);

        mLabel = new TextView(this);
        mLabel.setGravity(Gravity.CENTER_HORIZONTAL);
        mLabel.setTextSize(40);
        mLabel.setPadding(0,0,0,30);
        ll.addView(mLabel);

        // The inderterminate progressbar
        mProgBarI = new ProgressBar(this,null, R.id.progress_circular);
        mProgBarI.setIndeterminate(true);
        mProgBarI.setVisibility(View.GONE);
        ll.addView(mProgBarI,50,50);

        // the determinate progressbar
        mProgBarD = new ProgressBar(this, null, R.id.progress_horizontal);
        mProgBarD.setIndeterminate(false);
        mProgBarD.setVisibility(View.GONE);
        mProgBarD.setPadding(50,0,50,0);
        mProgBarD.setProgress(0);
        ll.addView(mProgBarD);

        mLayout.addView(ll);

        // Horizontal layout
        LinearLayout bottom = new LinearLayout(this);
        bottom.setOrientation(LinearLayout.HORIZONTAL);
        bottom.setGravity(Gravity.BOTTOM);
        bottom.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

        // Vertical layout
        LinearLayout right = new LinearLayout(this);
        right.setOrientation(LinearLayout.VERTICAL);
        right.setGravity(Gravity.RIGHT);
        right.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 100));

        bottom.addView(right);


        mLayout.addView(bottom);
    }

    public void clearLabel(){
        mLabel.setText("");
        mProgBarI.setVisibility(View.GONE);
        mProgBarD.setVisibility(View.GONE);
        mLabel.setVisibility(View.GONE);
        mSpinner.setVisibility(View.VISIBLE);
    }

    public void setLabel(String text){
        mLabel.setVisibility(View.VISIBLE);
        mLabel.setText(text);
        mSpinner.setVisibility(View.GONE);
    }

    public void showProgressBar(boolean determinate){
        int det = determinate ? View.VISIBLE : View.GONE,
                ind = determinate ? View.GONE : View.VISIBLE;

        mProgBarI.setVisibility(ind);
        mProgBarD.setVisibility(det);
    }

    public void setProgressBarPosition(int i)
    {
        mProgBarD.setProgress(i);
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
