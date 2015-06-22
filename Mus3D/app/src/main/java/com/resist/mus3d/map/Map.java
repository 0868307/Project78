package com.resist.mus3d.map;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.resist.mus3d.GpsActivity;
import com.resist.mus3d.Mus3D;
import com.resist.mus3d.R;
import com.resist.mus3d.database.ObjectTable;
import com.resist.mus3d.objects.coords.Point;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.events.DelayedMapListener;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.Position;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Map extends Activity implements GpsActivity {
    private MapView mapView;
    private MapController mapController;
    private LocationTracker locationListener;
	private List<com.resist.mus3d.objects.Object> highlighted = new ArrayList<>();
    private boolean addedHighlighted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

		if(getIntent() != null) {
            highlighted = getIntent().getParcelableArrayListExtra("objectList");
		}

        locationListener = new LocationTracker(this);

        mapView = (MapView) this.findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        mapController = (MapController) this.mapView.getController();
        mapController.setZoom(16);
        mapView.setMapListener(new DelayedMapListener(new MapScrollListener(this), 300));

        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(this);
        mapView.getOverlays().add(myScaleBarOverlay);
		if(!highlighted.isEmpty()) {
			goToObject(highlighted.get(0));
		} else {
			goToCurrentLocation();
		}
    }

    /**
     * Update markers.
     *
     * @param location the location
     */
    public void updateMarkers(IGeoPoint location) {
        if(!highlighted.isEmpty()) {
            if(!addedHighlighted) {
                addedHighlighted = true;
                addAllItems(highlighted);
            }
        } else {
            ObjectTable objectTable = new ObjectTable(Mus3D.getDatabase().getDatabase());
            List<? extends com.resist.mus3d.objects.Object> list = objectTable.getObjectsAround(new Point(location), Math.min(0.05 / mapView.getZoomLevel(), 0.02));
            addAllItems(list);
        }
    }

    private void addAllItems(List<? extends com.resist.mus3d.objects.Object> list) {
        List<Marker> overlayItemArray = new ArrayList<>();
        for (com.resist.mus3d.objects.Object o : list) {
            overlayItemArray.add(new Marker(this, o));
        }
        ItemizedIconOverlay<Marker> itemizedIconOverlay = new ItemizedIconOverlay<>(this, overlayItemArray, new MarkerListener());
        mapView.getOverlays().clear();
        mapView.getOverlays().add(itemizedIconOverlay);
        addCurrentPosition();
    }

    private void addCurrentPosition() {
        if (locationListener != null && LocationTracker.getCurrentLocation() != null) {
            GeoPoint currentLocation = new GeoPoint(LocationTracker.getCurrentLocation().getLatitude(), LocationTracker.getCurrentLocation().getLongitude());
            OverlayItem currentLoc = new OverlayItem("location", "Huidige location", currentLocation);
            ItemizedIconOverlay<OverlayItem> itemizedIconOverlay = new ItemizedIconOverlay<>(this, new ArrayList<OverlayItem>(), null);
            itemizedIconOverlay.addItem(currentLoc);
            mapView.getOverlays().add(itemizedIconOverlay);
        }
    }

    /**
     * Go to current location.
     */
	public void goToCurrentLocation() {
		if (locationListener != null && LocationTracker.getCurrentLocation() != null) {
			GeoPoint currentLocation = new GeoPoint(LocationTracker.getCurrentLocation());
			mapView.getController().setCenter(currentLocation);
			addCurrentPosition();
		}
	}

	private void goToObject(final com.resist.mus3d.objects.Object object) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Position pos = object.getLocation().getPosition();
                GeoPoint location = new GeoPoint(pos.getLatitude(), pos.getLongitude());
                mapView.getController().setCenter(location);
            }
        }, 1000);
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
