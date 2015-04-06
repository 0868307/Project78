package com.resist.mus3d.mapobjecten;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.resist.mus3d.FileReader;
import com.resist.mus3d.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wouter on 3/17/2015.
 */
public class ObjectLoader extends AsyncTask<Integer, Void, List<JSONObject>> {
    public static final String OBJECTPATH = "data/kaartobjecten/";
    public static final String MEERPALEN = OBJECTPATH + "Meerpaal.json";
    public static final String LIGPLAATSEN = OBJECTPATH + "Ligplaatsen.json";
    public static final String KONINGSPALENBEDRIJF = OBJECTPATH + "Koningspalen_met_Bedrijfsnamen.json";
    public static final String KONINGSPALEN = OBJECTPATH + "Koningspalen.json";
    public static final String BOLDER_BEDRIJF = OBJECTPATH + "Bolder_Bedrijfsnaam.json";
    public static final String AFMEERBOEIEN = OBJECTPATH + "Afmeerboei.json";
	private Activity parent;
	private Intent child;

	public ObjectLoader(Activity a, Intent i) {
		this.parent = a;
		this.child = i;
	}

	@Override
	protected List<JSONObject> doInBackground(Integer... streams) {
		ArrayList<JSONObject> list = new ArrayList<>();
		for(int stream : streams){
			BufferedReader reader = null;
			try {
				list.add(new JSONObject(new FileReader(parent, stream).getData()));
			} catch (JSONException e) {
				Log.d("PARSE ERROR", e.getMessage());
			}
        }
		return list;
	}

	@Override
	protected void onPostExecute(List<JSONObject> result) {
		MainActivity.setObjectJSON(result);
		//parent.startActivity(child);
		//parent.finish();
	}
}
