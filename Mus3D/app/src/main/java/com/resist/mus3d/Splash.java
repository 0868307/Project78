package com.resist.mus3d;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.resist.mus3d.map.Map;

public class Splash extends Activity {
	public static boolean startTutorial = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
		loadInBackground();
	}

	private void loadInBackground() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String storedRole = prefs.getString("roleSelector", "0");
		Intent i;
		if(storedRole.equals("0") && startTutorial) {
			i = new Intent(Splash.this, ScreenSlidePager.class);
		} /*else if(!startTutorial) {
			i = new Intent(Splash.this, RoleSelector.class);
		} else if(storedRole.equals(RoleSelector.ROLE_ROEIER)) {
			i = new Intent(Splash.this, Map.class);
		} else {
			i = new Intent(Splash.this, Rajawali.class);
		}*/ else {
			i = new Intent(this, Search.class);
		}
		new SplashLoader(this, i, 500).execute();
	}
}