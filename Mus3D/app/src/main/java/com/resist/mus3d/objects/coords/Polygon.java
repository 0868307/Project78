package com.resist.mus3d.objects.coords;

public class Polygon implements Coordinate {
	private MultiPoint[] multiPoints;

	public Polygon(MultiPoint[] multiPoints) {

		this.multiPoints = multiPoints;
	}

	public MultiPoint[] getMultiPoints() {
		return multiPoints;
	}
}
