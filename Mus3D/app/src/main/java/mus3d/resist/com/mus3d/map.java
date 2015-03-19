package mus3d.resist.com.mus3d;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;

import java.util.ArrayList;


public class Map extends ActionBarActivity {
    private MapView mapView;
    private String ikbenArmindo = "Ik kan geen bestand een normale naam geven";
    private MapController mapController;
    private LocationManager locationManager;
    private ArrayList<OverlayItem> overlayItemArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapView = (MapView) this.findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        mapController = (MapController) this.mapView.getController();
        mapController.setZoom(12);

        overlayItemArray = new ArrayList<OverlayItem>();

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(lastLocation != null){
            updateLoc(lastLocation);
        }

        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(this);
        mapView.getOverlays().add(myScaleBarOverlay);
    }

    private void updateLoc(Location loc){
        GeoPoint locGeoPoint = new GeoPoint(loc.getLatitude(), loc.getLongitude());
        mapController.setCenter(locGeoPoint);

        setOverlayLoc(loc);

        mapView.invalidate();
    }

    private void setOverlayLoc(Location overlayloc){
        GeoPoint overlocGeoPoint = new GeoPoint(overlayloc);
        overlayItemArray.clear();
        OverlayItem newMyLocationItem = new OverlayItem("My Location", "My Location", overlocGeoPoint);
        overlayItemArray.add(newMyLocationItem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
