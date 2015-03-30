package com.resist.mus3d.dataconverter.database;

import org.json.JSONArray;
import org.json.JSONObject;

public class CoordinateTable extends Table {
	private static final String name = "coordinaten";
	private static final Column[] columns = {
		new Column("id", Column.TYPE_INT, "OBJECTID", true),
		new Column("polygon", Column.TYPE_INT, false),
		new Column("multipoint", Column.TYPE_INT, false),
		new Column("x", Column.TYPE_INT, false),
		new Column("y", Column.TYPE_INT, false)
	};
	private StringBuilder insert;
	private boolean first = true;

	public CoordinateTable() {
		super(name, columns);
		insert = new StringBuilder();
	}

	public void clear() {
		first = true;
		insert.setLength(0);
	}

	public void add(JSONObject feature) {
		int id = feature.getJSONObject("properties").getInt(columns[0].getJSON());
		JSONObject geometry = feature.getJSONObject("geometry");
		String type = geometry.getString("type");
		JSONArray coords = geometry.getJSONArray("coordinates");
		if(type.equals("Point")) {
			addPoint(id, 0, 0, coords);
		} else if(type.equals("MultiPoint")) {
			addMultiPoint(id, 0, coords);
		} else if(type.equals("Polygon")) {
			addPolygon(id, coords);
		}
	}

	private void addPoint(int id, int polygon, int multipoint, JSONArray coords) {
		if(first) {
			first = false;
		} else {
			insert.append(",\n");
		}
		insert.append('(').append(id).append(", ")
		.append(polygon).append(", ").append(multipoint).append(", ")
		.append(coords.get(0)).append(", ").append(coords.get(1)).append(')');
	}

	private void addMultiPoint(int id, int polygon, JSONArray coords) {
		for(int n=0; n < coords.length(); n++) {
			addPoint(id, polygon, n, coords.getJSONArray(n));
		}
	}

	private void addPolygon(int id, JSONArray coords) {
		for(int n=0; n < coords.length(); n++) {
			addMultiPoint(id, n, coords.getJSONArray(n));
		}
	}

	private String getStoredValues() {
		return insert.toString();
	}

	public String getInsert() {
		return "INSERT INTO "+name+" ("+getInsertKeys()+") VALUES "+getStoredValues()+';';
	}
}
