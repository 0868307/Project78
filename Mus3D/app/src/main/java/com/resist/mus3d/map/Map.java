package com.resist.mus3d.map;

import android.app.Activity;
import android.location.LocationManager;
import android.os.Bundle;

import com.resist.mus3d.Mus3D;
import com.resist.mus3d.R;
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

        getActionBar().hide();
    }

    public void updateMarkers(IGeoPoint location) {
        List<Marker> overlayItemArray = new ArrayList<>();
        ObjectTable objectTable = new ObjectTable(Mus3D.getDatabase().getDatabase());
        List<? extends com.resist.mus3d.objects.Object> list = objectTable.getObjectsAround(new Point(location), 0.003);

        for (Object o : list) {
            overlayItemArray.add(new Marker(this, o));
        }

        ItemizedIconOverlay<Marker> itemizedIconOverlay = new ItemizedIconOverlay<>(this, overlayItemArray, new MarkerListener());

        mapView.getOverlays().clear();
        mapView.getOverlays().add(itemizedIconOverlay);
        addCurrentPosition();
    }

    private void addCurrentPosition() {
        if (locationListener != null && locationListener.getCurrentLocation() != null) {
            GeoPoint currentLocation = new GeoPoint(locationListener.getCurrentLocation().getLatitude(), locationListener.getCurrentLocation().getLongitude());
            OverlayItem currentLoc = new OverlayItem("location", "Huidige location", currentLocation);
            ItemizedIconOverlay<OverlayItem> itemizedIconOverlay = new ItemizedIconOverlay<>(this, new ArrayList<OverlayItem>(), null);
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
