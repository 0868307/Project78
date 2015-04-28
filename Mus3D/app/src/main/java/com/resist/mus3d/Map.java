package com.resist.mus3d;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import android.widget.Toast;
import com.resist.mus3d.database.Afmeerboeien;
import com.resist.mus3d.database.ObjectTable;
import com.resist.mus3d.objects.*;
import com.resist.mus3d.objects.Object;
import com.resist.mus3d.objects.coords.Point;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.SimpleLocationOverlay;

import java.util.ArrayList;
import java.util.List;

public class Map extends ActionBarActivity {
    private MapView mapView;
    private MapController mapController;
    private LocationManager locationManager;
    private List<OverlayItem> overlayItemArray;
    private LocationListener locationListener;
    private GeoPoint currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapView = (MapView) this.findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        mapController = (MapController) this.mapView.getController();
        mapController.setZoom(16);

        overlayItemArray = new ArrayList<OverlayItem>();

        locationListener = new LocationTracker(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if( location != null ) {
                currentLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
            }

        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(this);
        mapView.getOverlays().add(myScaleBarOverlay);
        displayMyCurrentLocationOverlay();
    }

    public void displayMyCurrentLocationOverlay() {
        if(currentLocation != null) {
            mapView.getController().setCenter(currentLocation);
            overlayItemArray.clear();

            OverlayItem currentLoc = new OverlayItem("location", "Huidige location", currentLocation);
            ObjectTable objectTable = new ObjectTable(Mus3D.getDatabase().getDatabase());
            List<? extends com.resist.mus3d.objects.Object> list = objectTable.getObjectsAround(new Point(currentLocation), 0.00000000000001);
            Log.d(Mus3D.LOG_TAG, list.size()+"");

            for(Object o : list) {
                GeoPoint object = new GeoPoint(o.getLocation().getPosition().getLongitude(), o.getLocation().getPosition().getLatitude());

                OverlayItem objectLoc = new OverlayItem(o.getObjectid() +" "+ o.getType(), o.getLocation().toString(), object);
                overlayItemArray.add(objectLoc);
            }

            overlayItemArray.add(currentLoc);
            ItemizedIconOverlay<OverlayItem> itemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(this, overlayItemArray, null);
            mapView.getOverlays().add(itemizedIconOverlay);
        }
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

}
