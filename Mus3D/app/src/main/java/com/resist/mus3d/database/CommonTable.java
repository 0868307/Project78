package com.resist.mus3d.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.resist.mus3d.objects.Common;
import com.resist.mus3d.objects.Harbour;

public class CommonTable extends ObjectTable {
	public CommonTable(SQLiteDatabase db) {
		super(db);
	}

	public void loadObject(Common object) {
		Cursor c = db.rawQuery("SELECT algemeen.*, havens.havenNaam FROM algemeen LEFT JOIN havens ON(algemeen.harbourId = havens.havenAfkorting) WHERE algemeen.objecttype = ? AND algemeen.id = ?", new String[] {String.valueOf(object.getType()), String.valueOf(object.getObjectid())});
		if(c.moveToFirst()) {
			object.setFacilityId(c.getString(c.getColumnIndex("facilityId")));
			object.setFacilitySecId(c.getString(c.getColumnIndex("facilitySecId")));
			object.setRegionId(c.getString(c.getColumnIndex("regionId")));
			object.setHarbour(Harbour.cacheHarbour(c.getString(c.getColumnIndex("harbourId")), c.getString(c.getColumnIndex("havenNaam"))));
		}
		c.close();
	}
}