package mus3d.resist.com.mus3d;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import mus3d.resist.com.mus3d.mapobjecten.ObjectLoader;

public class Splash extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
		loadObjects();
		final Intent i = new Intent(Splash.this, MainActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(i);
                finish();
            }
        }, 500);
    }

	private void loadObjects() {
		new ObjectLoader().execute(
			getResources().openRawResource(R.raw.afmeerboeien),
			getResources().openRawResource(R.raw.meerpalen),
			getResources().openRawResource(R.raw.koningspalen),
			getResources().openRawResource(R.raw.koningspalen_met_bedrijfsnamen),
			getResources().openRawResource(R.raw.bolder_bedrijfsnaam),
			getResources().openRawResource(R.raw.ligplaatsen)
		);
	}
}