package com.resist.mus3d;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;


public class RollSelector extends Activity {
    private SharedPreferences prefs;
    private String storedRoll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_selector);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        storedRoll = prefs.getString("rollSelector", "");
    }

    public void submit(View view) {
        startActivity();
    }

    public void startActivity() {
        SharedPreferences.Editor editor = prefs.edit();
        RadioButton rbRoeier = (RadioButton)findViewById(R.id.rb_RolRoeier);
        RadioButton rbLoods = (RadioButton) findViewById(R.id.rb_RolLoods);

        if(rbRoeier.isChecked()){
            editor.putString("rollSelector", "1");
            editor.apply();

            Intent intent = new Intent(this, Map.class);
            startActivity(intent);
            finish();
        }else if(rbLoods.isChecked()){
            editor.putString("rollSelector", "2");
            editor.apply();

            Intent intent = new Intent(this, Rajawali.class);
            startActivity(intent);
            finish();
        }
    }
}
