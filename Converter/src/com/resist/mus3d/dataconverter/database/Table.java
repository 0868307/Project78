package com.resist.mus3d.dataconverter.database;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.resist.mus3d.dataconverter.DataConverter;

public abstract class Table {
	private String name;
	private Column[] columns;
	protected boolean track = false;

	public Table(String name, Column[] columns) {
		this.name = name;
		this.columns = columns;
	}

	protected String getCreateColumns() {
		StringBuilder sb = new StringBuilder();
		List<String> primaryKeys = new ArrayList<String>();
		boolean first = true;
		for(Column c : columns) {
			if(first) {
				first = false;
			} else {
				sb.append(", ");
			}
			sb.append(c.getName()).append(' ');
			if(!c.isAi()) {
				sb.append(c.getType());
			} else {
				sb.append(Column.TYPE_INT).append(' ').append(Column.AUTO_INCREMENT);
			}
			if(c.isPrimary()) {
				primaryKeys.add(c.getName());
			}
		}
		if(!primaryKeys.isEmpty()) {
			first = true;
			sb.append(", PRIMARY KEY(");
			for(String key : primaryKeys) {
				if(first) {
					first = false;
				} else {
					sb.append(", ");
				}
				sb.append(key);
			}
			sb.append(')');
		}
		return sb.toString();
	}

	public Column[] getColumns() {
		return columns;
	}

	public String getCreate() {
		return "CREATE TABLE "+name+'('+getCreateColumns()+");";
	}

	protected String getInsertKeys() {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for(Column c : columns) {
			if(first) {
				first = false;
			} else {
				sb.append(", ");
			}
			sb.append(c.getName());
		}
		return sb.toString();
	}

	protected String getParsedValues(int type, JSONObject json) {
		if(track) {
			DataConverter.COORDS.clear();
		}
		StringBuilder sb = new StringBuilder();
		JSONArray features = json.getJSONArray("features");
		for(int n=0; n<features.length(); n++) {
			JSONObject feature = features.getJSONObject(n);
			JSONObject properties = feature.getJSONObject("properties");
			StringBuilder row = new StringBuilder();
			boolean append = true;
			if(n != 0) {
				row.append(",\n");
			}
			row.append('(');
			boolean first = true;
			for(Column c : columns) {
				if(first) {
					first = false;
				} else {
					row.append(", ");
				}
				Object value;
				if(c.getJSON() == null) {
					value = type;
				} else {
					value = properties.get(c.getJSON());
				}
				append = append && canAppend(c, value);
				if(value != JSONObject.NULL) {
					if(c.getType() == Column.TYPE_INT || c.getType() == Column.TYPE_FLOAT) {
						row.append(value);
					} else {
						row.append('"').append(value).append('"');
					}
				} else {
					row.append(Column.NULL);
				}
			}
			row.append(')');
			if(append) {
				if(track) {
					DataConverter.COORDS.add(type, feature);
				}
				sb.append(row);
			}
		}
		track = false;
		return sb.toString();
	}

	protected boolean canAppend(Column c, Object value) {
		return true;
	}

	public String getInsert(int type, JSONObject json) {
		return "INSERT INTO "+name+" ("+getInsertKeys()+") VALUES "+getParsedValues(type, json)+';';
	}
}
