package com.resist.mus3d;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.resist.mus3d.map.LocationTracker;
import com.resist.mus3d.map.Map;
import rajawali.RajawaliActivity;

public class Rajawali extends RajawaliActivity implements GpsActivity{
    private MyRenderer myRenderer;
    private LocationTracker locationListener;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myRenderer = new MyRenderer(this);
        myRenderer.setSurfaceView(mSurfaceView);

        super.setRenderer(myRenderer);
        locationListener = new LocationTracker(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
            case R.id.settings_Map:
                Intent j = new Intent(this, Map.class);
                startActivity(j);
                break;
            case R.id.settings_Rajawali:
                Intent k = new Intent(this, Rajawali.class);
                startActivity(k);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStop() {
        locationListener.onStop();
        super.onStop();
    }

    @Override
    protected void onResume() {
        locationListener.onResume();
        super.onResume();
    }
    @Override
    public void update() {
        if(locationListener != null) {
            if (locationListener.getCurrentLocation() != null) {
                /*System.out.println("");
                System.out.println(
                        "latitude : " + (float) locationListener.getCurrentLocation().getLatitude() +
                                "\n longtitude : " + (float) locationListener.getCurrentLocation().getLongitude() +
                                "\n altitude : " + (float) locationListener.getCurrentLocation().getAltitude()+
                        "\n angle : " +  locationListener.getLastDirection()
                );
                */
                myRenderer.setCamera(
                        (float) locationListener.getCurrentLocation().getLatitude(),
                        (float) locationListener.getCurrentLocation().getLongitude(),
                        0
                );
                myRenderer.setCameraRotation(locationListener.getLastDirection(),0,0);
            }
        }
    }
}
