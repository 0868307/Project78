package com.resist.mus3d.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.resist.mus3d.Mus3D;
import com.resist.mus3d.objects.Anchorage;
import com.resist.mus3d.objects.Harbour;
import com.resist.mus3d.objects.coords.Coordinate;

import java.text.ParseException;
import java.util.List;

public class Aanlegplaatsen extends ObjectTable {
	/**
	 * Instantiates a new Aanlegplaatsen.
	 *
	 * @param db the database
	 */
	public Aanlegplaatsen(SQLiteDatabase db) {
		super(db);
	}

	/**
	 * Load object.
	 *
	 * @param object the object
	 */
	public void loadObject(Anchorage object) {
		Cursor c = db.rawQuery("SELECT ligplaatsen.*, havens.havenNaam FROM ligplaatsen LEFT JOIN havens ON(ligplaatsen.havenAfkorting = havens.havenAfkorting) WHERE ligplaatsen.id = ?", new String[] {String.valueOf(object.getObjectid())});
		if(c.moveToFirst()) {
			object.setAfmeerVz(c.getString(c.getColumnIndex("afmeerVz")));
			object.setAnchorage(c.getString(c.getColumnIndex("ligplaats")));
			object.setComplexId(c.getString(c.getColumnIndex("complexId")));
			object.setGlobalId(c.getString(c.getColumnIndex("globalId")));
			object.setKenmerkZe(c.getString(c.getColumnIndex("kenmerkZe")));
			object.setLxmeText(c.getString(c.getColumnIndex("lxmeText")));
			object.setNauticalState(c.getString(c.getColumnIndex("nautStatus")));
			object.setOccRecNo(c.getString(c.getColumnIndex("occrecnnr")));
			try {
				object.setOccupiedFrom(parseDate(c.getString(c.getColumnIndex("occFrom"))));
				object.setOccupiedTill(parseDate(c.getString(c.getColumnIndex("occTo"))));
			} catch(ParseException e) {
				Log.w(Mus3D.LOG_TAG, "Failed to parse date", e);
			}
			object.setOwner(c.getString(c.getColumnIndex("eigenaar")));
			object.setPrimaryFunction(c.getString(c.getColumnIndex("primaireFunctie")));
			object.setShoreNo(c.getString(c.getColumnIndex("oevernummer")));
			object.setVacReason(c.getString(c.getColumnIndex("vacReason")));
			object.setXmeText(c.getString(c.getColumnIndex("xmeText")));
			object.setHarbour(Harbour.cacheHarbour(c.getString(c.getColumnIndex("havenAfkorting")), c.getString(c.getColumnIndex("havenNaam"))));
		}
		c.close();
	}

    @Override
    public List<Anchorage> getObjectsAround(Coordinate location, double distance) {
        return (List<Anchorage>)getObjectsAround(location, distance, new int[] {Anchorage.TYPE});
    }
}
