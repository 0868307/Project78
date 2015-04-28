package com.resist.mus3d;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.resist.mus3d.map.Map;

import rajawali.RajawaliActivity;

public class Rajawali extends RajawaliActivity {
    private MyRenderer myRenderer;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myRenderer = new MyRenderer(this);
        myRenderer.setSurfaceView(mSurfaceView);

        super.setRenderer(myRenderer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case R.id.settings_Settings:
                Intent i = new Intent(this, Settings.class);
                startActivity(i);
                break;
            case R.id.settings_Map:
                Intent j = new Intent(this, Map.class);
                startActivity(j);
                break;
            case R.id.settings_Rajawali:
                Intent k = new Intent(this, Rajawali.class);
                startActivity(k);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
