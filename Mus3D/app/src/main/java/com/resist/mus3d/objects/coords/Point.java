package com.resist.mus3d.objects.coords;

import android.location.Location;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.Position;

public class Point extends Coordinate {
    private double x;
    private double y;
    private Position cachedPosition;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(IGeoPoint geoPoint) {
        this.y = geoPoint.getLatitude();
        this.x = geoPoint.getLongitude();
    }

    public Point(Location location) {
        this.y = location.getLatitude();
        this.x = location.getLongitude();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Position getPosition() {
        if (cachedPosition == null) {
            cachedPosition = new Position(y, x);
        }
        return cachedPosition;
    }
}
