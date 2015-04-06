package com.resist.mus3d.dataconverter.database;

public class BolderTable extends Table {
	private static final String name = "bolders";
	private static final Column[] columns = {
		new Column("id", Column.TYPE_INT, "OBJECTID", true),
		new Column("description", Column.TYPE_TEXT, "O_DESCR"),
		new Column("materiaal", Column.TYPE_TEXT, "O_MAT_ALG_"),
		new Column("verankering", Column.TYPE_TEXT, "O_METH_VER"),
		new Column("type", Column.TYPE_TEXT, "O_TYP_BOLD"),
		new Column("partner", Column.TYPE_TEXT, "XPARTNER_B"),
		new Column("bedrijf", Column.TYPE_TEXT, "XNAME_TR06")
	};

	public BolderTable() {
		super(name, columns);
	}
}
