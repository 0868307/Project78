package com.resist.mus3d;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class RoleSelector extends Activity {
	private static final int ROEIER = 1;
	private static final int LOODS = 2;
    private SharedPreferences prefs;
    private int storedRoll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selector);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        storedRoll = prefs.getInt("roleSelector", 0);
    }

    public void submit(View view) {
        startActivity();
    }

    public void startActivity() {
        SharedPreferences.Editor editor = prefs.edit();
        RadioButton rbRoeier = (RadioButton)findViewById(R.id.rb_RolRoeier);
        RadioButton rbLoods = (RadioButton) findViewById(R.id.rb_RolLoods);

        if(rbRoeier.isChecked()) {
            editor.putInt("roleSelector", ROEIER);
            editor.apply();

            Intent intent = new Intent(this, Map.class);
            startActivity(intent);
            finish();
        } else if(rbLoods.isChecked()) {
            editor.putInt("roleSelector", LOODS);
            editor.apply();

            Intent intent = new Intent(this, Rajawali.class);
            startActivity(intent);
            finish();
        }
    }
}
