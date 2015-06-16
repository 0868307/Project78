package com.resist.mus3d.map;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;

public class MapScrollListener implements MapListener {
	private Map map;
	private double lastLatitude;
	private double lastLongitude;

	/**
	 * Instantiates a new Map scroll listener.
	 *
	 * @param map the map
	 */
	public MapScrollListener(Map map) {
		this.map = map;
	}

	@Override
	public boolean onScroll(ScrollEvent scrollEvent) {
		IGeoPoint loc = scrollEvent.getSource().getMapCenter();
		if(lastLatitude != loc.getLatitude() || lastLongitude != loc.getLongitude()) {
			lastLatitude = loc.getLatitude();
			lastLongitude = loc.getLongitude();
			map.updateMarkers(loc);
		}
		return true;
	}

	@Override
	public boolean onZoom(ZoomEvent zoomEvent) {
		return true;
	}
}
