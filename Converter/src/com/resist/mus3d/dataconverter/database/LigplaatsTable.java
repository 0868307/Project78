package com.resist.mus3d.dataconverter.database;

public class LigplaatsTable extends Table {
	private static final String name = "ligplaatsen";
	private static final Column[] columns = {
		new Column("id", Column.TYPE_INT, "OBJECTID", true),
		new Column("complexId", Column.TYPE_TEXT, "COMPLEXID"),
		new Column("globalId", Column.TYPE_TEXT, "GLOBALID"),
		new Column("kenmerkZe", Column.TYPE_TEXT, "KENMERK_ZE"),
		new Column("lxmeText", Column.TYPE_TEXT, "LXMETXT"),
		new Column("occFrom", Column.TYPE_DATE, "OCCFROM"),
		new Column("occrecnnr", Column.TYPE_TEXT, "OCCRECNNR"),
		new Column("occTo", Column.TYPE_DATE, "OCCTO"),
		new Column("vacReason", Column.TYPE_TEXT, "VACREASON"),
		new Column("xmeText", Column.TYPE_TEXT, "XMETXT"),
		new Column("afmeerVz", Column.TYPE_TEXT, "ZZAFMVZ"),
		new Column("eigenaar", Column.TYPE_TEXT, "ZZEIGE"),
		new Column("gebruik", Column.TYPE_TEXT, "ZZGEBR"),
		new Column("havenAfkorting", Column.TYPE_TEXT, "ZZHVAFKNAA"),
		new Column("ligplaats", Column.TYPE_TEXT, "ZZLIGPLG"),
		new Column("nautStatus", Column.TYPE_TEXT, "ZZNAUTST"),
		new Column("oevernummer", Column.TYPE_TEXT, "ZZOEVFRN"),
		new Column("primaireFunctie", Column.TYPE_TEXT, "ZZPRIMF"),
		new Column("type", Column.TYPE_TEXT, "ZZUITG")
	};

	public LigplaatsTable() {
		super(name, columns);
	}
}
