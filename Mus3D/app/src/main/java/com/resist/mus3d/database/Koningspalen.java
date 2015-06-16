package com.resist.mus3d.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.resist.mus3d.objects.Koningspaal;
import com.resist.mus3d.objects.coords.Coordinate;

import java.util.List;

public class Koningspalen extends CommonTable {
	/**
	 * Instantiates a new Koningspalen.
	 *
	 * @param db the database
	 */
	public Koningspalen(SQLiteDatabase db) {
		super(db);
	}

	/**
	 * Load object.
	 *
	 * @param object the object
	 */
	public void loadObject(Koningspaal object) {
		Cursor c = db.rawQuery("SELECT * FROM koningspalen WHERE id = ?", new String[] {String.valueOf(object.getObjectid())});
		if(c.moveToFirst()) {
			object.setConfirmation(c.getString(c.getColumnIndex("bevestiging")));
			object.setDescription(c.getString(c.getColumnIndex("description")));
			object.setMaterial(c.getString(c.getColumnIndex("materiaal")));
			object.setWearMaterial(c.getString(c.getColumnIndex("slijtMateriaal")));
		}
		c.close();
	}

    @Override
    public List<Koningspaal> getObjectsAround(Coordinate location, double distance) {
        return (List<Koningspaal>)getObjectsAround(location, distance, new int[] {Koningspaal.TYPE});
    }
}
