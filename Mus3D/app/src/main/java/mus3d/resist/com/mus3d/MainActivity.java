package mus3d.resist.com.mus3d;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends ActionBarActivity {

    ArrayList<JSONObject> objectjsonarray = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        objectjsonarray = ObjectLoader.load(
                getResources().openRawResource(R.raw.afmeerboeien),
                getResources().openRawResource(R.raw.meerpalen),
                getResources().openRawResource(R.raw.koningspalen),
                getResources().openRawResource(R.raw.koningspalen_met_bedrijfsnamen),
                getResources().openRawResource(R.raw.bolder_bedrijfsnaam),
                getResources().openRawResource(R.raw.ligplaatsen)
                );
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
}
