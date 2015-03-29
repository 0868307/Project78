package com.resist.mus3d.dataconverter.database;

public class KoningspaalTable extends Table {
	private static final String name = "koningspalen";
	private static final Column[] columns = {
		new Column("id", Column.TYPE_INT, "OBJECTID", true),
		new Column("bevestiging", Column.TYPE_TEXT, "O_BEV_PALE"),
		new Column("description", Column.TYPE_TEXT, "O_DESCR"),
		new Column("materiaal", Column.TYPE_TEXT, "O_MAT_ALG_"),
		new Column("slijtMateriaal", Column.TYPE_TEXT, "O_SLIJT_MA")
	};

	public KoningspaalTable() {
		super(name, columns);
	}
}
