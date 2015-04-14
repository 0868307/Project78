package com.resist.mus3d.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.resist.mus3d.Mus3D;
import com.resist.mus3d.objects.*;
import com.resist.mus3d.objects.Object;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Afmeerboeien extends ObjectTable {
	private static final DateFormat DATE = new SimpleDateFormat("yyyy/MM/dd");

	public Afmeerboeien(SQLiteDatabase db) {
		super(db);
	}

	@Override
	public List<Afmeerboei> getAll() {
		List<Afmeerboei> out = new ArrayList<>();
		Cursor c = db.rawQuery("SELECT objecten.*, algemeen.*, havens.havenNaam FROM objecten LEFT JOIN algemeen ON (objecten.objecttype = algemeen.objecttype AND objecten.objectid = algemeen.id) LEFT JOIN havens ON(algemeen.harbourId = havens.havenAfkorting) WHERE objecten.objecttype = ?", new String[] {String.valueOf(Afmeerboei.TYPE)});
		c.moveToFirst();
		while(!c.isAfterLast()) {
			try {
				out.add(new Afmeerboei(
						c.getInt(c.getColumnIndex("objectid")),
						c.getString(c.getColumnIndex("createdBy")),
						DATE.parse(c.getString(c.getColumnIndex("createdAt"))),
						c.getString(c.getColumnIndex("editedBy")),
						DATE.parse(c.getString(c.getColumnIndex("editedAt"))),
						c.getString(c.getColumnIndex("featureId")),
						c.getString(c.getColumnIndex("facilityId")),
						c.getString(c.getColumnIndex("facilitySecId")),
						Harbour.cacheHarbour(c.getString(c.getColumnIndex("harbourId")), c.getString(c.getColumnIndex("havenNaam"))),
						c.getString(c.getColumnIndex("regionId"))
				));
			} catch (ParseException e) {
				Log.e(Mus3D.LOG_TAG, "Failed to parse date", e);
			}
			c.moveToNext();
		}
		c.close();
		return out;
	}
}
