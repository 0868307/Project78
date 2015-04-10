package com.resist.mus3d.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.resist.mus3d.Mus3D;
import com.resist.mus3d.R;
import com.resist.mus3d.objects.Afmeerboei;
import com.resist.mus3d.objects.Harbour;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ObjectDatabase {
    private static final String FILE = "objectdatabase";
    private static final DateFormat DATE = new SimpleDateFormat("yyyy/MM/dd");
    private Context ctx;
    private SQLiteDatabase db;

    public ObjectDatabase(Context ctx) {
        this.ctx = ctx;
    }

    public void open() throws IOException {
        File file = ctx.getFileStreamPath(FILE);
        if(file.exists() || (!file.exists() && loadDatabase())) {
			db = SQLiteDatabase.openDatabase(file.toString(), null, SQLiteDatabase.OPEN_READWRITE);
        } else {
            throw new IOException("Failed to open database");
        }
    }

    private boolean loadDatabase() {
        try {
            OutputStream out = ctx.openFileOutput(FILE, Context.MODE_APPEND);
            InputStream in = ctx.getResources().openRawResource(R.raw.objects);
            int b;
            while((b = in.read()) != -1) {
                out.write(b);
            }
            in.close();
            out.flush();
            out.close();
			return true;
        } catch (IOException e) {
            Log.d("MUS3D", "Failed to load object database", e);
        }
		return false;
    }

    public List<Afmeerboei> getAfmeerboeien() {
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
                        Harbour.cacheHarbour(
                                c.getString(c.getColumnIndex("harbourId")),
                                c.getString(c.getColumnIndex("havenNaam"))
                        ),
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
