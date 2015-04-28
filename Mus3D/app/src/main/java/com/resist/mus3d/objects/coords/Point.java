package com.resist.mus3d.objects.coords;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.Position;

public class Point implements Coordinate {
	private double x;
	private double y;
	private Position cachedPosition;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

    public Point(GeoPoint geoPoint) {
        this.y = geoPoint.getLatitude();
        this.x = geoPoint.getLongitude();
    }

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public Position getPosition() {
		if(cachedPosition == null) {
			cachedPosition = new Position(y, x);
		}
		return cachedPosition;
	}
}
