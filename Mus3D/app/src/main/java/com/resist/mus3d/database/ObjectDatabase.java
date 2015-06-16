package com.resist.mus3d.database;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.resist.mus3d.R;
import com.resist.mus3d.Splash;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ObjectDatabase {
    private static final String FILE = "objectdatabase";
    private Context ctx;
    private SQLiteDatabase db;
	private ProgressBar progressBarI;
	private ProgressBar progressBarD;

	/**
	 * Instantiates a new Object database.
	 *
	 * @param ctx the context
	 */
	public ObjectDatabase(Context ctx) {
		this.ctx = ctx;
	}

	/**
	 * Open void.
	 *
	 * @throws IOException the iO exception
	 */
	public void open() throws IOException {
		File file = ctx.getFileStreamPath(FILE);
        if(file.exists() || (!file.exists() && loadDatabase())) {
			db = SQLiteDatabase.openDatabase(file.toString(), null, SQLiteDatabase.OPEN_READWRITE);
        } else {
            throw new IOException("Failed to open database");
		}
	}

	/**
	 * Gets database.
	 *
	 * @return the database
	 */
	public SQLiteDatabase getDatabase() {
		return db;
	}

	private void progressUpdate(float percentage) {
		if(progressBarD != null) {
			progressBarD.setProgress(Math.round(percentage * 10));
		}
	}

	private void loaded() {
		if(progressBarD != null) {
			((Activity)ctx).runOnUiThread(new Runnable() {
				@Override
				public void run() {
					progressBarD.setVisibility(View.GONE);
					progressBarI.setVisibility(View.VISIBLE);
				}
			});
		}
	}

	private void showProgressBar() {
		if(ctx instanceof Splash) {
			Splash splash = (Splash)ctx;
			progressBarD = (ProgressBar)splash.findViewById(R.id.splash_progress_determinate);
			progressBarI = (ProgressBar)splash.findViewById(R.id.splash_progress_indeterminate);
			progressBarD.setVisibility(View.VISIBLE);
			progressBarI.setVisibility(View.GONE);
		}
	}

    private boolean loadDatabase() {
		showProgressBar();
        try {
            OutputStream out = ctx.openFileOutput(FILE, Context.MODE_APPEND);
            InputStream in = ctx.getResources().openRawResource(R.raw.objects);
            int b;
			int written = 0;
            while((b = in.read()) != -1) {
                out.write(b);
				written++;
				progressUpdate(written * 100f / in.available());
            }
            in.close();
            out.flush();
            out.close();
			loaded();
			return true;
        } catch (IOException e) {
            Log.d("MUS3D", "Failed to load object database", e);
        }
		return false;
    }
}
