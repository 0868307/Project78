package com.resist.mus3d.dataconverter.database;

import java.util.HashSet;
import java.util.Set;

public class HavenTable extends Table {
	private static final String name = "havens";
	private static final Column[] columns = {
		new Column("havenAfkorting", Column.TYPE_TEXT, "ZZHVAFKNAA", true),
		new Column("havenNaam", Column.TYPE_TEXT, "ZZHVNAAM")
	};
	private Set<Object> inserted = new HashSet<Object>();

	public HavenTable() {
		super(name, columns);
	}

	@Override
	protected boolean canAppend(Column c, Object value) {
		if(c.isPrimary()) {
			if(inserted.contains(value)) {
				return false;
			} else {
				inserted.add(value);
			}
		}
		return true;
	}
}
