package com.resist.mus3d.objects.coords;

public class MultiPoint implements Coordinate {
	private Point[] points;

	public MultiPoint(Point[] points) {
		this.points = points;
	}

	public Point[] getPoints() {
		return points;
	}
}
