package com.resist.mus3d;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;


public class SplashLoader extends AsyncTask<Void, Void, Boolean> {
    private Activity ctx;
    private Intent intent;
    private long startTime;
    private long delay;


    /**
     * Instantiates a new Splash loader.
     *
     * @param ctx the context
     * @param intent the intent
     * @param delay the delay
     */
    public SplashLoader(Activity ctx, Intent intent, long delay) {
        this.ctx = ctx;
        this.intent = intent;
        this.delay = delay;
    }

    protected Boolean doInBackground(Void... input) {
        startTime = System.currentTimeMillis();
        return loadDatabase();
    }

    private boolean loadDatabase() {
        try {
            Mus3D.openDatabase(ctx);
        } catch (IOException e) {
            Log.e(Mus3D.LOG_TAG, "Failed to load database.", e);
            return false;
        }
        return true;
    }

    protected void onPostExecute(Boolean result) {
        if(!result) {
            Toast.makeText(ctx, R.string.failed_to_load_db, Toast.LENGTH_LONG).show();
        }
        long time = delay - (System.currentTimeMillis() - startTime);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ctx.startActivity(intent);
                ctx.finish();
            }
        }, time);
    }
}
