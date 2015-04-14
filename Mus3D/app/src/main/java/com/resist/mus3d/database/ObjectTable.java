package com.resist.mus3d.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.SparseArray;

import com.resist.mus3d.objects.coords.Coordinate;
import com.resist.mus3d.objects.coords.MultiPoint;
import com.resist.mus3d.objects.coords.Point;
import com.resist.mus3d.objects.coords.Polygon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectTable {
	protected SQLiteDatabase db;

	public ObjectTable(SQLiteDatabase db) {
		this.db = db;
	}

	public List<? extends com.resist.mus3d.objects.Object> getAll() {
		return new ArrayList<>();
	}

	public Coordinate getCoordinates(com.resist.mus3d.objects.Object object) {
		SparseArray<SparseArray<Point>> coords = new SparseArray<>();
		Cursor c = db.rawQuery("SELECT * FROM coordinaten WHERE objecttype = ? AND id = ?", new String[] {String.valueOf(object.getType()), String.valueOf(object.getObjectid())});
		c.moveToFirst();
		while(!c.isAfterLast()) {
			int polygon = c.getInt(c.getColumnIndex("polygon"));
			int multipoint = c.getInt(c.getColumnIndex("multipoint"));
			Point p = new Point(
					c.getInt(c.getColumnIndex("x")),
					c.getInt(c.getColumnIndex("y"))
			);
			if(coords.get(polygon) == null) {
				coords.put(polygon, new SparseArray<Point>());
			}
			coords.get(polygon).put(multipoint, p);
			c.moveToNext();
		}
		c.close();
		int size = coords.size();
		if(size == 1) {
			SparseArray<Point> multipoint = coords.get(0);
			size = multipoint.size();
			if(size == 1) {
				return multipoint.get(0);
			} else if(size > 1) {
				return getMultipoint(multipoint, size);
			}
		} else if(size > 1) {
			MultiPoint[] multiPoints = new MultiPoint[size];
			for(int n=0; n < size; n++) {
				SparseArray<Point> multipoint = coords.valueAt(n);
				multiPoints[n] = getMultipoint(multipoint, multipoint.size());
			}
			return new Polygon(multiPoints);
		}
		return null;
	}

	private MultiPoint getMultipoint(SparseArray<Point> multipoint, int size) {
		Point[] points = new Point[size];
		for(int n=0; n < size; n++) {
			points[n] = multipoint.valueAt(n);
		}
		return new MultiPoint(points);
	}
}
