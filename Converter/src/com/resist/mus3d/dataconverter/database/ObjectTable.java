package com.resist.mus3d.dataconverter.database;

import org.json.JSONObject;

import com.resist.mus3d.dataconverter.DataConverter;

public class ObjectTable extends Table {
	private static final String name = "objecten";
	private static final Column[] columns = {
		new Column("objecttype", Column.TYPE_INT, true),
		new Column("objectid", Column.TYPE_INT, "OBJECTID", true),
		new Column("createdBy", Column.TYPE_TEXT, "CREATEDBY"),
		new Column("createdAt", Column.TYPE_DATE, "CREATEDDAT"),
		new Column("editedBy", Column.TYPE_TEXT, "EDITEDBY"),
		new Column("editedAt", Column.TYPE_DATE, "EDITEDDATE"),
		new Column("featureId", Column.TYPE_TEXT, "FEATUREID")
	};

	public ObjectTable() {
		super(name, columns);
	}

	@Override
	public String getInsert(int type, JSONObject json) {
		track = true;
		return "INSERT INTO "+name+" ("+getInsertKeys()+") VALUES "+getParsedValues(type, json)+";\n"+
				DataConverter.COORDS.getInsert();
	}
}
