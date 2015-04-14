package com.resist.mus3d.objects.coords;

import org.osmdroid.util.Position;

public class Point implements Coordinate {
	private int x;
	private int y;
	private Position cachedPosition;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Position getPosition() {
		if(cachedPosition == null) {
			cachedPosition = new Position(x, y);
		}
		return cachedPosition;
	}
}
