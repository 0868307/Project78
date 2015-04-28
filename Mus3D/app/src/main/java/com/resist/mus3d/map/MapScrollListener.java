package com.resist.mus3d.map;

import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;

public class MapScrollListener implements MapListener {
	private Map map;

	public MapScrollListener(Map map) {
		this.map = map;
	}

	@Override
	public boolean onScroll(ScrollEvent scrollEvent) {
		map.updateMarkers(scrollEvent.getSource().getMapCenter());
		return true;
	}

	@Override
	public boolean onZoom(ZoomEvent zoomEvent) {
		return true;
	}
}
