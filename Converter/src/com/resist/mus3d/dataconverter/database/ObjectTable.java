package com.resist.mus3d.dataconverter.database;

public class ObjectTable extends Table {
	private static final String name = "objecten";
	private static final Column[] columns = {
		new Column("objectid", Column.TYPE_INT, "OBJECTID", true),
		new Column("createdBy", Column.TYPE_DATE, "CREATEDBY"),
		new Column("createdAt", Column.TYPE_DATE, "CREATEDDAT"),
		new Column("editedBy", Column.TYPE_TEXT, "EDITEDBY"),
		new Column("editedAt", Column.TYPE_DATE, "EDITEDDATE"),
		new Column("featureId", Column.TYPE_TEXT, "FEATUREID")
	};

	public ObjectTable() {
		super(name, columns);
	}
}
