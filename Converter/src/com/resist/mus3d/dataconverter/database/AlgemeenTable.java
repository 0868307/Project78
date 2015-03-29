package com.resist.mus3d.dataconverter.database;

public class AlgemeenTable extends Table {
	private static final String name = "algemeen";
	private static final Column[] columns = {
		new Column("id", Column.TYPE_INT, "OBJECTID", true),
		new Column("facilityId", Column.TYPE_TEXT, "FACILITYID"),
		new Column("facilitySecId", Column.TYPE_TEXT, "FACSECID"),
		new Column("harbourId", Column.TYPE_TEXT, "HARBOURID"),
		new Column("regionId", Column.TYPE_TEXT, "REGIONID")
	};

	public AlgemeenTable() {
		super(name, columns);
	}
}
