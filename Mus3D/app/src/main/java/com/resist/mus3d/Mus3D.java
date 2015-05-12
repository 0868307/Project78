package com.resist.mus3d;


import android.content.Context;

import com.resist.mus3d.database.ObjectDatabase;

import java.io.IOException;

public class Mus3D {
	public static final double RADIUS_EARTH = 6378.137;
    public static final String LOG_TAG = "Mus3D";
    private static ObjectDatabase database;

    public static void openDatabase(Context ctx) throws IOException {
        if(database == null) {
            database = new ObjectDatabase(ctx);
            database.open();
        }
    }

    public static ObjectDatabase getDatabase() {
        return database;
    }
}
