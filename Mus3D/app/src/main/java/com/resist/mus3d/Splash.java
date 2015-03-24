package com.resist.mus3d;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

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
		new ObjectLoader(this, i).execute(
			R.raw.afmeerboeien,
			R.raw.meerpalen,
			R.raw.koningspalen,
			R.raw.koningspalen_met_bedrijfsnamen,
			R.raw.bolder_bedrijfsnaam,
			R.raw.ligplaatsen
		);
	}
}