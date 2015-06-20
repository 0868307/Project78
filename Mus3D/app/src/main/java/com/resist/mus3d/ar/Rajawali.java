package com.resist.mus3d.ar;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.resist.mus3d.GpsActivity;
import com.resist.mus3d.Mus3D;
import com.resist.mus3d.R;
import com.resist.mus3d.map.LocationTracker;
import com.resist.mus3d.objects.Object;
import com.resist.mus3d.sensor.SensorActivity;
import com.resist.mus3d.sensor.SensorTracker;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import rajawali.RajawaliActivity;

public class Rajawali extends RajawaliActivity implements GpsActivity, SensorActivity, View.OnTouchListener {
	private ObjectRenderer objectRenderer;
	private LocationTracker locationListener;
	private SensorTracker sensorTracker;
	private int counter = 1000;
	private View progressBarGPS;
	private View progressBarObjects;
	private final int ZOOMLEVEL = 16;
	private MapView minimap;

	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		objectRenderer = new ObjectRenderer(this);
		objectRenderer.setSurfaceView(mSurfaceView);
		mSurfaceView.setOnTouchListener(this);
		super.setRenderer(objectRenderer);
		createUI();
		locationListener = new LocationTracker(this);
		sensorTracker = new SensorTracker(this);

	}

	private void createUI(){
		RelativeLayout rl = new RelativeLayout(this);
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		ll.setGravity(Gravity.CENTER);
		rl.addView(ll);
		LayoutInflater inflater = LayoutInflater.from(this);
		addMinimap(rl, inflater);
		progressBarGPS = inflater.inflate(R.layout.progress_gps, null);
		progressBarObjects = inflater.inflate(R.layout.progress_objects, null);
		progressBarObjects.setVisibility(View.INVISIBLE);
		rl.addView(progressBarGPS);
		rl.addView(progressBarObjects);
		mLayout.addView(rl);


	}

	/**
	 * Get intent from search.
	 */
	public HashSet<Object> getIntentFromSearch() {
		Bundle searchIntentArray = getIntent().getExtras();
		if (searchIntentArray != null) {
			List<Object> objectlist = searchIntentArray.getParcelableArrayList("objectList");
			return new HashSet<>(objectlist);
		} else {
			return null;
		}
	}


	private void addMinimap(RelativeLayout layout, LayoutInflater inflater){
		LinearLayout mapViewParent = (LinearLayout)inflater.inflate(R.layout.minimap,null);
		minimap = (MapView)mapViewParent.getChildAt(0);
		minimap.setMultiTouchControls(false);
		minimap.setBuiltInZoomControls(false);
		MapController mapController = (MapController) minimap.getController();
		mapController.setZoom(ZOOMLEVEL);
		addCurrentPosition();
		layout.addView(mapViewParent);
	}
	private void addCurrentPosition() {
		if (locationListener != null && LocationTracker.getCurrentLocation() != null) {
			GeoPoint currentLocation = new GeoPoint(LocationTracker.getCurrentLocation().getLatitude(), LocationTracker.getCurrentLocation().getLongitude());
			OverlayItem currentLoc = new OverlayItem("location", "Huidige location", currentLocation);
			ItemizedIconOverlay<OverlayItem> itemizedIconOverlay = new ItemizedIconOverlay<>(this, new ArrayList<OverlayItem>(), null);
			itemizedIconOverlay.addItem(currentLoc);
			minimap.getOverlays().add(itemizedIconOverlay);
		}
	}

	/**
	 * On object loading progress.
	 *
	 * @param progress the progress
	 */
	public void onObjectLoadingProgress(double progress) {
		ProgressBar bar = (ProgressBar)((ViewGroup) progressBarObjects).getChildAt(0);
		bar.setProgress((int)(progress*10));
	}

	/**
	 * On objects loaded.
	 *
	 * @param success the success
	 */
	public void onObjectsLoaded(boolean success) {
		if(success) {
			progressBarObjects.setVisibility(View.INVISIBLE);
		}
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
			if(progressBarGPS.getVisibility() == View.VISIBLE) {
				Log.d(Mus3D.LOG_TAG, "Loaded GPS");
				progressBarGPS.setVisibility(View.GONE);
				progressBarObjects.setVisibility(View.VISIBLE);
			}
			objectRenderer.setCamera(
					(float) LocationTracker.getCurrentLocation().getLongitude() * ObjectRenderer.MULTIPLIER,
					0,
					(float) LocationTracker.getCurrentLocation().getLatitude() * ObjectRenderer.MULTIPLIER
			);
			GeoPoint currentLocation = new GeoPoint(LocationTracker.getCurrentLocation().getLatitude(), LocationTracker.getCurrentLocation().getLongitude());
			minimap.getController().setCenter(currentLocation);
			if(counter > 1000){
				objectRenderer.makeObjects();
				counter = 0;
			}
		}
		counter++;
	}

	/**
	 * Get location.
	 *
	 * @return the location
	 */
	public Location getLocation() {
		if (locationListener != null && LocationTracker.getCurrentLocation() != null)
			return LocationTracker.getCurrentLocation();
		Location location = new Location((String)null);
		location.setLatitude(0);
		location.setLongitude(0);
		return location;
	}

	@Override
	public void updateSensor(float x,float y,float z) {
		if(sensorTracker != null){
			y = -y;
			objectRenderer.setCameraRotation(x,(y+180)%360,z);
			minimap.setRotation(y);
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			objectRenderer.getObjectAt(event.getX(), event.getY());

		}

		return super.onTouchEvent(event);
	}
}
