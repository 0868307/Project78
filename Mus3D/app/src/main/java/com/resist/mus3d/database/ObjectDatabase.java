package com.resist.mus3d.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.resist.mus3d.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ObjectDatabase {
    private static final String FILE = "objectdatabase";
    private Context ctx;

    public ObjectDatabase(Context ctx) {
        this.ctx = ctx;
    }

    public SQLiteDatabase open() {
        File file = ctx.getFileStreamPath(FILE);
        if(!file.exists()) {
            loadDatabase();
        }
        return SQLiteDatabase.openDatabase(file.toString(), null, SQLiteDatabase.OPEN_READWRITE);
    }

    private void loadDatabase() {
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
        } catch (IOException e) {
            Log.d("MUS3D", "Failed to load object database", e);
        }
    }
}