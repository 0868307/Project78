package com.resist.mus3d.dataconverter.database;

public class Column {
	public static final String TYPE_INT = "INTEGER";
	public static final String TYPE_TEXT = "TEXT";
	public static final String TYPE_FLOAT = "REAL";
	public static final String TYPE_DATE = "NUMERIC";
	public static final String AUTO_INCREMENT = "AUTOINCREMENT";
	public static final String PRIMARY_KEY = "PRIMARY KEY";
	public static final String NULL = "NULL";

	private String name;
	private String type;
	private String json;
	private boolean ai;
	private boolean primary;

	public Column(String name, String type, String json) {
		this.name = name;
		this.type = type;
		this.json = json;
	}

	public Column(String name, String type, String json, boolean primary) {
		this.name = name;
		this.type = type;
		this.json = json;
		this.primary = primary;
	}

	public Column(String name, String type, boolean primary) {
		this.name = name;
		this.type = type;
		this.primary = primary;
	}

	public Column(String name) {
		this.name = name;
		this.ai = true;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getJSON() {
		return json;
	}

	public boolean isAi() {
		return ai;
	}

	public boolean isPrimary() {
		return primary || ai;
	}
}
