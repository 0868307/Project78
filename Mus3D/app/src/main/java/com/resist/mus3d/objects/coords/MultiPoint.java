package com.resist.mus3d.objects.coords;

import org.osmdroid.util.Position;

public class MultiPoint extends Coordinate {
    private Point[] points;
    private Position cachedPosition;

    public MultiPoint(Point[] points) {
        this.points = points;
    }

    public Point[] getPoints() {
        return points;
    }

    public Position getPosition() {
        if (cachedPosition == null) {
            double x = 0, y = 0;
            for (Point p : points) {
                Position pos = p.getPosition();
                x += pos.getLatitude();
                y += pos.getLongitude();
            }
            cachedPosition = new Position(x / points.length, y / points.length);
        }
        return cachedPosition;
    }
}
