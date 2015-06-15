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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    protected String[] appendTypes(StringBuilder sb, int[] types, int start) {
        String[] args = new String[start];
        if(types != null) {
            args = new String[start + types.length];
            sb.append("objecten.objecttype IN (");
            for(int n=0; n < types.length; n++) {
                args[n] = String.valueOf(types[n]);
                if(n > 0) {
                    sb.append(", ");
                }
                sb.append('?');
            }
            sb.append(')');
        }
        return args;
    }

    public List<? extends com.resist.mus3d.objects.Object> getObjectsAround(Coordinate location, double distance, int[] types) {
        List<com.resist.mus3d.objects.Object> out = new ArrayList<>();
        Position pos = location.getPosition();
        String x = String.valueOf(pos.getLongitude());
        String y = String.valueOf(pos.getLatitude());
        String d = String.valueOf(distance);
        Map<com.resist.mus3d.objects.Object, SparseArray<SparseArray<Point>>> coords = new HashMap<>();

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT objecten.*, coordinaten.polygon, coordinaten.multipoint, coordinaten.x, coordinaten.y ")
                .append("FROM objecten ")
                .append("JOIN coordinaten ")
                .append("ON(objecten.objecttype = coordinaten.objecttype AND objecten.objectid = coordinaten.id) ")
                .append("WHERE ");
        String[] args = appendTypes(sb, types, 6);
        int typeLength = args.length - 6;
        if(typeLength != 0) {
            sb.append(" AND ");
        }
        sb.append(" (coordinaten.x-?)*(coordinaten.x-?)+(coordinaten.y-?)*(coordinaten.y-?) <= ?*? ")
                .append("ORDER BY objecten.objecttype, objectid");
        args[typeLength] = x;
        args[typeLength + 1] = x;
        args[typeLength + 2] = y;
        args[typeLength + 3] = y;
        args[typeLength + 4] = d;
        args[typeLength + 5] = d;
        Cursor c = db.rawQuery(
                sb.toString(),
                args
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

	public List<? extends com.resist.mus3d.objects.Object> getObjectsAround(Coordinate location, double distance) {
		return getObjectsAround(location, distance, null);
	}

    protected Date parseDate(String date) throws ParseException {
        if(date == null) {
            return null;
        }
        return DATE.parse(date);
    }

    public List<com.resist.mus3d.objects.Object> findObjects(String searchQuery, int searchType) {
        String[] terms = searchQuery.trim().replace("  ", " ").split(" ");
        List<String> args = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT objecten.* ")
                .append("FROM objecten ");
        if(searchType == Anchorage.TYPE) {
            sb.append("JOIN ligplaatsen ON(ligplaatsen.id = objecten.objectid AND objecten.objecttype = ?) ");
            args.add(""+searchType);
        } else if(searchType == Bolder.TYPE) {
            sb.append("JOIN bolders ON(bolders.id = objecten.objectid AND objecten.objecttype = ?) ");
            args.add(""+searchType);
        } else if(searchType == Koningspaal.TYPE) {
            sb.append("JOIN koningspalen ON(koningspalen.id = objecten.objectid AND objecten.objecttype = ?) ");
            args.add(""+searchType);
        }
        sb.append("WHERE (");
        for(int n=0; n < terms.length;n++) {
            if(n != 0) {
                sb.append("OR ");
            }
            if(searchType == Anchorage.TYPE) {
                sb.append("ligplaatsen.xmeText LIKE ? ");
            } else if(searchType == Bolder.TYPE || searchType == Koningspaal.TYPE) {
                sb.append("description LIKE ? ");
            } else {
                sb.append("objecten.featureId LIKE ? ");
            }
            terms[n] = '%'+terms[n]+'%';
        }
        sb.append(") AND objecten.objecttype = ?");

        if(searchType == Anchorage.TYPE) {
            sb.append(" AND ligplaatsen.kenmerkZe != ?");
            args.add("Binnenvaart");
        }

        args.addAll(Arrays.asList(terms));
        args.add(""+searchType);
        sb.append(" ORDER BY objecten.objecttype, objecten.objectid");
        Cursor c = db.rawQuery(sb.toString(), args.toArray(new String[0]));
        List<com.resist.mus3d.objects.Object> out = new ArrayList<>();
        while(c.moveToNext()) {
            out.add(createObjectFromCursor(c));
        }
        c.close();
        return out;
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
