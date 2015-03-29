package com.resist.mus3d.dataconverter.database;

public class HavenTable extends Table {
	private static final String name = "havens";
	private static final Column[] columns = {
		new Column("havenAfkorting", Column.TYPE_TEXT, "ZZHVAFKNAA", true),
		new Column("havenNaam", Column.TYPE_TEXT, "ZZHVNAAM")
	};

	public HavenTable() {
		super(name, columns);
	}
}
