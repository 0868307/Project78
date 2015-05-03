package com.resist.mus3d.map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.resist.mus3d.GpsActivity;
import com.resist.mus3d.Mus3D;
import com.resist.mus3d.R;
import com.resist.mus3d.Rajawali;
import com.resist.mus3d.Settings;
import com.resist.mus3d.database.ObjectTable;
import com.resist.mus3d.objects.Object;
import com.resist.mus3d.objects.coords.Point;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.events.DelayedMapListener;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;

import java.util.ArrayList;
import java.util.List;

public class Map extends ActionBarActivity implements GpsActivity{
    private MapView mapView;
    private MapController mapController;
    private LocationManager locationManager;
    private List<OverlayItem> overlayItemArray;
    private LocationTracker locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapView = (MapView) this.findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        mapController = (MapController) this.mapView.getController();
        mapController.setZoom(16);
		mapView.setMapListener(new DelayedMapListener(new MapScrollListener(this), 300));

        overlayItemArray = new ArrayList<OverlayItem>();

        locationListener = new LocationTracker(this);
        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(this);
        mapView.getOverlays().add(myScaleBarOverlay);
        displayMyCurrentLocationOverlay();
    }

	public void updateMarkers(IGeoPoint location) {
		List<OverlayItem> overlayItemArray = new ArrayList<>();
		ObjectTable objectTable = new ObjectTable(Mus3D.getDatabase().getDatabase());
		List<? extends com.resist.mus3d.objects.Object> list = objectTable.getObjectsAround(new Point(location), 0.005);

		for(Object o : list) {
			GeoPoint object = new GeoPoint(o.getLocation().getPosition().getLongitude(), o.getLocation().getPosition().getLatitude());

			OverlayItem objectLoc = new OverlayItem(o.getType()+"", o.getObjectid()+"", object);
            if(objectLoc.getTitle().equals("0")) {
                //Wat is deze??
            }else if(objectLoc.getTitle().equals("1")) {
                //Bolders
                Drawable icon = this.getResources().getDrawable(R.drawable.ic_bolder);
                objectLoc.setMarker(icon);
                icon.setBounds(0 - icon.getIntrinsicWidth() / 2, 0 - icon.getIntrinsicHeight(), icon.getIntrinsicWidth() / 2, 0);
            }else if(objectLoc.getTitle().equals("2")) {
                // Koningspalen denk ik?
                Drawable icon = this.getResources().getDrawable(R.drawable.ic_koningspaal);
                objectLoc.setMarker(icon);
                icon.setBounds(0 - icon.getIntrinsicWidth() / 2, 0 - icon.getIntrinsicHeight(), icon.getIntrinsicWidth() / 2, 0);
            } else {
                //Al het andere
                Drawable icon = this.getResources().getDrawable(R.drawable.ic_onbekend);
                objectLoc.setMarker(icon);
                icon.setBounds(0 - icon.getIntrinsicWidth() / 2, 0 - icon.getIntrinsicHeight(), icon.getIntrinsicWidth() / 2, 0);
            }
                overlayItemArray.add(objectLoc);
		}

		ItemizedIconOverlay<OverlayItem> itemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(this, overlayItemArray, null);


        mapView.getOverlays().clear();
		mapView.getOverlays().add(itemizedIconOverlay);
	}

    public void displayMyCurrentLocationOverlay() {
        if(locationListener != null){
            if(locationListener.getCurrentLocation() != null){

                GeoPoint currentLocation = new GeoPoint(locationListener.getCurrentLocation().getLatitude(),locationListener.getCurrentLocation().getLongitude());
                mapView.getController().setCenter(currentLocation);
                overlayItemArray.clear();

                OverlayItem currentLoc = new OverlayItem("location", "Huidige location", currentLocation);

                overlayItemArray.add(currentLoc);
                ItemizedIconOverlay<OverlayItem> itemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(this, overlayItemArray, null);

                mapView.getOverlays().add(itemizedIconOverlay);
            }
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
        //Commented omdat je anders heel de tijd naar huidige locatie gaat en je dus geen objecten kan bekijken.
        //displayMyCurrentLocationOverlay();
    }
}
