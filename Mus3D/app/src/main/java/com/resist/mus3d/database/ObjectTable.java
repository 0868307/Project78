package com.resist.mus3d.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.SparseArray;

import com.resist.mus3d.Mus3D;
import com.resist.mus3d.objects.*;
import com.resist.mus3d.objects.coords.Coordinate;
import com.resist.mus3d.objects.coords.MultiPoint;
import com.resist.mus3d.objects.coords.Point;
import com.resist.mus3d.objects.coords.Polygon;

import org.osmdroid.util.Position;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ObjectTable {
	protected static final DateFormat DATE = new SimpleDateFormat("yyyy/MM/dd");
	protected SQLiteDatabase db;

	public ObjectTable(SQLiteDatabase db) {
		this.db = db;
	}

	public List<? extends com.resist.mus3d.objects.Object> getAll() {
		return new ArrayList<>();
	}

	private void putCoordinates(Cursor c, SparseArray<SparseArray<Point>> coords) {
		int polygon = c.getInt(c.getColumnIndex("polygon"));
		int multipoint = c.getInt(c.getColumnIndex("multipoint"));
		Point p = new Point(
				c.getDouble(c.getColumnIndex("x")),
				c.getDouble(c.getColumnIndex("y"))
		);

		if(coords.get(polygon) == null) {
			coords.put(polygon, new SparseArray<Point>());
		}
		coords.get(polygon).put(multipoint, p);
	}

	private Coordinate buildCoordinates(SparseArray<SparseArray<Point>> coords) {
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

	public Coordinate getCoordinates(com.resist.mus3d.objects.Object object) {
		SparseArray<SparseArray<Point>> coords = new SparseArray<>();
		Cursor c = db.rawQuery("SELECT * FROM coordinaten WHERE objecttype = ? AND id = ?", new String[] {String.valueOf(object.getType()), String.valueOf(object.getObjectid())});
		c.moveToFirst();
		while(!c.isAfterLast()) {
			putCoordinates(c, coords);
			c.moveToNext();
		}
		c.close();
		return buildCoordinates(coords);
	}

	private MultiPoint getMultipoint(SparseArray<Point> multipoint, int size) {
		Point[] points = new Point[size];
		for(int n=0; n < size; n++) {
			points[n] = multipoint.valueAt(n);
		}
		return new MultiPoint(points);
	}

	public List<? extends com.resist.mus3d.objects.Object> getObjectsAround(Coordinate location, double distance) {
		List<com.resist.mus3d.objects.Object> out = new ArrayList<>();
		Position pos = location.getPosition();
		String x = String.valueOf(pos.getLatitude());
		String y = String.valueOf(pos.getLongitude());
		Map<com.resist.mus3d.objects.Object, SparseArray<SparseArray<Point>>> coords = new HashMap<>();

		Cursor c = db.rawQuery(
				"SELECT objecten.*, coordinaten.polygon, coordinaten.multipoint, coordinaten.x, coordinaten.y " +
				"FROM objecten " +
				"JOIN coordinaten " +
				"ON(objecten.objecttype = coordinaten.objecttype AND objecten.objectid = coordinaten.id) " +
				"WHERE (x-?)*(x-?)+(y-?)*(y-?) <= ? " +
				"ORDER BY objecttype, objectid",
				new String[] {
						x, x, y, y, String.valueOf(distance*distance)
				}
		);
		com.resist.mus3d.objects.Object current = null;
		c.moveToFirst();
		while(!c.isAfterLast()) {
			if(current == null || c.getInt(c.getColumnIndex("objecttype")) != current.getType() || c.getInt(c.getColumnIndex("objectid")) != current.getObjectid()) {
				current = createObjectFromCursor(c);
				out.add(current);
				coords.put(current, new SparseArray<SparseArray<Point>>());
			}
			putCoordinates(c, coords.get(current));
			c.moveToNext();
		}
		c.close();

		for(com.resist.mus3d.objects.Object o : out) {
			o.setLocation(buildCoordinates(coords.get(o)));
		}

		return out;
	}

    protected Date parseDate(String date) throws ParseException {
        if(date == null) {
            return null;
        }
        return DATE.parse(date);
    }

	private com.resist.mus3d.objects.Object createObjectFromCursor(Cursor c) {
		int type = c.getInt(c.getColumnIndex("objecttype"));
		try {
			if(type == Afmeerboei.TYPE) {
				return new Afmeerboei(
						c.getInt(c.getColumnIndex("objectid")),
						c.getString(c.getColumnIndex("createdBy")),
                        parseDate(c.getString(c.getColumnIndex("createdAt"))),
						c.getString(c.getColumnIndex("editedBy")),
                        parseDate(c.getString(c.getColumnIndex("editedAt"))),
						c.getString(c.getColumnIndex("featureId"))
				);
			} else if(type == Anchorage.TYPE) {
				return new Anchorage(
                        c.getInt(c.getColumnIndex("objectid")),
                        c.getString(c.getColumnIndex("createdBy")),
                        parseDate(c.getString(c.getColumnIndex("createdAt"))),
                        c.getString(c.getColumnIndex("editedBy")),
                        parseDate(c.getString(c.getColumnIndex("editedAt"))),
                        c.getString(c.getColumnIndex("featureId"))
				);
			} else if(type == Bolder.TYPE) {
				return new Bolder(
                        c.getInt(c.getColumnIndex("objectid")),
                        c.getString(c.getColumnIndex("createdBy")),
                        parseDate(c.getString(c.getColumnIndex("createdAt"))),
                        c.getString(c.getColumnIndex("editedBy")),
                        parseDate(c.getString(c.getColumnIndex("editedAt"))),
                        c.getString(c.getColumnIndex("featureId"))
				);
			} else if(type == Koningspaal.TYPE) {
				return new Koningspaal(
                        c.getInt(c.getColumnIndex("objectid")),
                        c.getString(c.getColumnIndex("createdBy")),
                        parseDate(c.getString(c.getColumnIndex("createdAt"))),
                        c.getString(c.getColumnIndex("editedBy")),
                        parseDate(c.getString(c.getColumnIndex("editedAt"))),
                        c.getString(c.getColumnIndex("featureId"))
				);
			} else if(type == Meerpaal.TYPE) {
				return new Meerpaal(
                        c.getInt(c.getColumnIndex("objectid")),
                        c.getString(c.getColumnIndex("createdBy")),
                        parseDate(c.getString(c.getColumnIndex("createdAt"))),
                        c.getString(c.getColumnIndex("editedBy")),
                        parseDate(c.getString(c.getColumnIndex("editedAt"))),
                        c.getString(c.getColumnIndex("featureId"))
				);
			}
		} catch (ParseException e) {
			Log.e(Mus3D.LOG_TAG, "Failed to parse date", e);
		}
		return null;
	}
}
