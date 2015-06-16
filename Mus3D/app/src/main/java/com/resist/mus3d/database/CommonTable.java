package com.resist.mus3d.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.resist.mus3d.objects.Common;

public class CommonTable extends ObjectTable {
	/**
	 * Instantiates a new Common table.
	 *
	 * @param db the database
	 */
	public CommonTable(SQLiteDatabase db) {
		super(db);
	}

	/**
	 * Load object.
	 *
	 * @param object the object
	 */
	public void loadObject(Common object) {
		Cursor c = db.rawQuery("SELECT algemeen.* FROM algemeen WHERE algemeen.objecttype = ? AND algemeen.id = ?", new String[] {String.valueOf(object.getType()), String.valueOf(object.getObjectid())});
		if(c.moveToFirst()) {
			object.setFacilityId(c.getString(c.getColumnIndex("facilityId")));
			object.setFacilitySecId(c.getString(c.getColumnIndex("facilitySecId")));
			object.setRegionId(c.getString(c.getColumnIndex("regionId")));
			object.setHarbourId(c.getString(c.getColumnIndex("harbourId")));
		}
		c.close();
	}
}
