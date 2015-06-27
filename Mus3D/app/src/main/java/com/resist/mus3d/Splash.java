package com.resist.mus3d;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class Splash extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
        loadInBackground();
    }

    private void loadInBackground() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Intent i;
        if (!prefs.contains("skipTutorial")) {
            i = new Intent(this, Tutorial.class);
        } else {
            i = new Intent(this, Search.class);
        }
        new SplashLoader(this, i, 2000).execute();
    }
}