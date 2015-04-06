package com.resist.mus3d;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONObject;

import java.util.List;

import rajawali.RajawaliActivity;

public class MainActivity extends RajawaliActivity {
    private static List<JSONObject> objectJSON = null;

    private MyRenderer myRenderer;

	public static void setObjectJSON(List<JSONObject> list) {
		objectJSON = list;
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //myRenderer = new MyRenderer(this);
        //myRenderer.setSurfaceView(mSurfaceView);
        //super.setRenderer(myRenderer);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        myRenderer = new MyRenderer(this);
        myRenderer.setSurfaceView(mSurfaceView);
        super.setRenderer(myRenderer);
    }
}
