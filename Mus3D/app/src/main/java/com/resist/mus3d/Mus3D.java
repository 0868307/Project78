package com.resist.mus3d;


import android.content.Context;

import com.resist.mus3d.database.ObjectDatabase;

import java.io.IOException;

public class Mus3D {
    /**
     * The constant RADIUS_EARTH.
     */
    public static final double RADIUS_EARTH = 6378.137;
    /**
     * The constant LOG_TAG.
     */
    public static final String LOG_TAG = "Mus3D";
    private static ObjectDatabase database;

    /**
     * Open database.
     *
     * @param ctx the context
     * @throws IOException the iO exception
     */
    public static void openDatabase(Context ctx) throws IOException {
        if (database == null) {
            database = new ObjectDatabase(ctx);
            database.open();
        }
    }

    /**
     * Gets database.
     *
     * @return the database
     */
    public static ObjectDatabase getDatabase() {
        return database;
    }
}
