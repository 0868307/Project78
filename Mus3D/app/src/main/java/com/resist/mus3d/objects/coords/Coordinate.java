package com.resist.mus3d.objects.coords;

import com.resist.mus3d.Mus3D;

import org.osmdroid.util.Position;

public abstract class Coordinate {
    public abstract Position getPosition();

    public double getDistanceTo(Coordinate coordinate) {
        double convert = Math.PI / 180.0;
        double deltaLatitude = (coordinate.getPosition().getLatitude() - getPosition().getLatitude()) * convert;
        double deltaLongitude = (coordinate.getPosition().getLongitude() - getPosition().getLongitude()) * convert;
        double sinLatitude = Math.sin(deltaLatitude / 2);
        double sinLongitude = Math.sin(deltaLongitude / 2);
        double a = sinLatitude * sinLatitude + Math.cos(getPosition().getLatitude() * convert) * Math.cos(coordinate.getPosition().getLatitude() * convert) * sinLongitude * sinLongitude;
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return Mus3D.RADIUS_EARTH * c * 1000;
    }
}
