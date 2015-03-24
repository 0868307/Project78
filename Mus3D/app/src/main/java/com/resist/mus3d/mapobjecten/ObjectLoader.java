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
<<<<<<< HEAD:Mus3D/app/src/main/java/mus3d/resist/com/mus3d/mapobjecten/ObjectLoader.java
public class ObjectLoader extends AsyncTask<InputStream, Void, List<JSONObject>> {
=======
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
>>>>>>> origin/master:Mus3D/app/src/main/java/com/resist/mus3d/mapobjecten/ObjectLoader.java

	@Override
	protected List<JSONObject> doInBackground(Integer... streams) {
		ArrayList<JSONObject> list = new ArrayList<>();
		for(int stream : streams){
			BufferedReader reader = null;
			try {
<<<<<<< HEAD:Mus3D/app/src/main/java/mus3d/resist/com/mus3d/mapobjecten/ObjectLoader.java
				reader = new BufferedReader(new InputStreamReader(stream));
				StringBuilder json = new StringBuilder();
				String inputStr;
				while ((inputStr = reader.readLine()) != null)
					json.append(inputStr);
				list.add(new JSONObject(json.toString()));
                reader.close();
=======
				list.add(new JSONObject(new FileReader(parent, stream).getData()));
>>>>>>> origin/master:Mus3D/app/src/main/java/com/resist/mus3d/mapobjecten/ObjectLoader.java
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
