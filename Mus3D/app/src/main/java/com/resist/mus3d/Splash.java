package com.resist.mus3d;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
		loadInBackground();
    }

    private void loadInBackground() {
        Intent i = new Intent(Splash.this, MainActivity.class);
        new SplashLoader(this, i, 500).execute();
    }
}