package com.resist.mus3d;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONObject;

import java.util.List;

import rajawali.RajawaliActivity;

public class MainActivity extends ActionBarActivity {
    private static List<JSONObject> objectJSON = null;

	public static void setObjectJSON(List<JSONObject> list) {
		objectJSON = list;
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void btn_map(View view) {
        Intent intent = new Intent(this, Map.class);
        startActivity(intent);
    }

    public void teken_2D(View view){
        Intent intent = new Intent(this, Rajawali.class);
        startActivity(intent);
    }
}
