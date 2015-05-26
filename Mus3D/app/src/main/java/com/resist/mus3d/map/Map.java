package com.resist.mus3d.map;

import android.app.Activity;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.resist.mus3d.Mus3D;
import com.resist.mus3d.R;
import com.resist.mus3d.Rajawali;
import com.resist.mus3d.Settings;
import com.resist.mus3d.database.Bolders;
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

public class Map extends Activity implements GpsActivity {
    private MapView mapView;
    private MapController mapController;
    private LocationManager locationManager;
    private List<OverlayItem> overlayItemArray;
    private LocationTracker locationListener;
    private DrawerLayout menuDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        overlayItemArray = new ArrayList<OverlayItem>();
        locationListener = new LocationTracker(this);

        mapView = (MapView) this.findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        mapController = (MapController) this.mapView.getController();
        mapController.setZoom(16);
        mapView.setMapListener(new DelayedMapListener(new MapScrollListener(this), 300));

        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(this);
        mapView.getOverlays().add(myScaleBarOverlay);
        goToCurrentLocation();

        menuDrawer = (DrawerLayout)findViewById(R.id.menu_drawer);
        //TODO checkboxes aanpassen aan opgeslagen gecheckte waarden
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, menuDrawer, null, R.string.menu_open, R.string.menu_close);
        menuDrawer.setDrawerListener(drawerToggle);
    }

    public void onCheckBoxClick(View v) {
        //TODO waardes opslaan en kaart redrawen
    }

    public void updateMarkers(IGeoPoint location) {
        List<Marker> overlayItemArray = new ArrayList<>();
        ObjectTable objectTable = new ObjectTable(Mus3D.getDatabase().getDatabase());
        List<? extends com.resist.mus3d.objects.Object> list = objectTable.getObjectsAround(new Point(location), 0.003);

        for (Object o : list) {
            overlayItemArray.add(new Marker(this, o));
        }

        ItemizedIconOverlay<Marker> itemizedIconOverlay = new ItemizedIconOverlay<>(this, overlayItemArray, new MarkerListener(this));

        mapView.getOverlays().clear();
        mapView.getOverlays().add(itemizedIconOverlay);
        addCurrentPosition();
    }

    private void addCurrentPosition() {
        if (locationListener != null && locationListener.getCurrentLocation() != null) {
            GeoPoint currentLocation = new GeoPoint(locationListener.getCurrentLocation().getLatitude(), locationListener.getCurrentLocation().getLongitude());
            OverlayItem currentLoc = new OverlayItem("location", "Huidige location", currentLocation);
            ItemizedIconOverlay<OverlayItem> itemizedIconOverlay = new ItemizedIconOverlay<>(this, null, null);
            itemizedIconOverlay.addItem(currentLoc);
            mapView.getOverlays().add(itemizedIconOverlay);
        }
    }

    public void goToCurrentLocation() {
        if (locationListener != null && locationListener.getCurrentLocation() != null) {
            GeoPoint currentLocation = new GeoPoint(locationListener.getCurrentLocation().getLatitude(), locationListener.getCurrentLocation().getLongitude());
            mapView.getController().setCenter(currentLocation);
            addCurrentPosition();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings2d, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.settings_Settings:
                Intent i = new Intent(this, Settings.class);
                startActivity(i);
                break;
            case R.id.action_3D:
                Intent j = new Intent(this, Rajawali.class);
                startActivity(j);
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
    }
}
