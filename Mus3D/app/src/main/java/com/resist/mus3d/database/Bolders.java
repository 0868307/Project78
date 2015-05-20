package com.resist.mus3d.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.resist.mus3d.objects.Bolder;
import com.resist.mus3d.objects.coords.Coordinate;

import java.util.List;

public class Bolders extends CommonTable {
	public Bolders(SQLiteDatabase db) {
		super(db);
	}

	public void loadObject(Bolder object) {
		Cursor c = db.rawQuery("SELECT * FROM bolders WHERE id = ?", new String[] {String.valueOf(object.getObjectid())});
		if(c.moveToFirst()) {
			object.setMaterial(c.getString(c.getColumnIndex("materiaal")));
			object.setDescription(c.getString(c.getColumnIndex("description")));
			object.setAnchor(c.getString(c.getColumnIndex("verankering")));
			object.setPartner(c.getString(c.getColumnIndex("partner")));
			object.setCompany(c.getString(c.getColumnIndex("bedrijf")));
		}
		c.close();
	}

    @Override
    public List<Bolder> getObjectsAround(Coordinate location, double distance) {
        return (List<Bolder>)getObjectsAround(location, distance, new int[] {Bolder.TYPE});
    }
}
