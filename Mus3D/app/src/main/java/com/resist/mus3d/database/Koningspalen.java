package com.resist.mus3d.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.resist.mus3d.objects.Koningspaal;

public class Koningspalen extends CommonTable {
	public Koningspalen(SQLiteDatabase db) {
		super(db);
	}

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
}
