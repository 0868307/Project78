package com.resist.mus3d;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.resist.mus3d.database.ObjectDatabase;
import com.resist.mus3d.mapobjecten.ObjectLoader;

public class Splash extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
		final Intent i = new Intent(Splash.this, MainActivity.class);
		loadObjects(i);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(i);
                finish();
            }
        }, 500);
    }

	private void loadObjects(Intent i) {
        SQLiteDatabase db = new ObjectDatabase(this).open();
        Cursor c = db.query("coordinaten", null, null, null, null, null, null, "1");
        for(String col : c.getColumnNames()) {
            Log.d("MooiLog", col);
        }
        c.close();
        db.close();
        /*new ObjectLoader(this, i).execute(
			R.raw.afmeerboeien,
			R.raw.meerpalen,
			R.raw.koningspalen,
			R.raw.koningspalen_met_bedrijfsnamen,
			R.raw.bolder_bedrijfsnaam,
			R.raw.ligplaatsen
		);*/
	}
}