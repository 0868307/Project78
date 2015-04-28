package com.resist.mus3d;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.resist.mus3d.map.Map;

public class RoleSelector extends Activity {
	public static final String ROLE_ROEIER = "1";
	public static final String ROLE_LOODS = "2";
    private SharedPreferences prefs;
    private String storedRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selector);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        storedRole = prefs.getString("roleSelector", "0");
    }

    public void submit(View view) {
        startActivity();
    }

    public void startActivity() {
        SharedPreferences.Editor editor = prefs.edit();
        RadioButton rbRoeier = (RadioButton)findViewById(R.id.rb_RolRoeier);
        RadioButton rbLoods = (RadioButton) findViewById(R.id.rb_RolLoods);

        if(rbRoeier.isChecked()) {
            editor.putString("roleSelector", ROLE_ROEIER);
            editor.apply();

            Intent intent = new Intent(this, Map.class);
            startActivity(intent);
            finish();
        } else if(rbLoods.isChecked()) {
            editor.putString("roleSelector", ROLE_LOODS);
            editor.apply();

            Intent intent = new Intent(this, Rajawali.class);
            startActivity(intent);
            finish();
        }
    }
}