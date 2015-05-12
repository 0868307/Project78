package com.resist.mus3d.objects.coords;

import org.osmdroid.util.Position;

public class Polygon extends Coordinate {
	private MultiPoint[] multiPoints;
	private Position cachedPosition;

	public Polygon(MultiPoint[] multiPoints) {

		this.multiPoints = multiPoints;
	}

	public MultiPoint[] getMultiPoints() {
		return multiPoints;
	}

	public Position getPosition() {
		if(cachedPosition == null) {
			double x = 0, y = 0;
			for(MultiPoint m : multiPoints) {
				Position p = m.getPosition();
				x += p.getLatitude();
				y += p.getLongitude();
			}
			cachedPosition = new Position(x / multiPoints.length, y / multiPoints.length);
		}
		return cachedPosition;
	}
}
