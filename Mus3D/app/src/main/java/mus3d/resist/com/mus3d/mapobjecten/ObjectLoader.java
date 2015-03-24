package mus3d.resist.com.mus3d.mapobjecten;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import mus3d.resist.com.mus3d.MainActivity;

/**
 * Created by Wouter on 3/17/2015.
 */
public class ObjectLoader extends AsyncTask<InputStream, Void, List<JSONObject>> {

	@Override
	protected List<JSONObject> doInBackground(InputStream... streams) {
		ArrayList<JSONObject> list = new ArrayList<>();
		for(InputStream stream : streams){
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(stream));
				StringBuilder json = new StringBuilder();
				String inputStr;
				while ((inputStr = reader.readLine()) != null)
					json.append(inputStr);
				list.add(new JSONObject(json.toString()));
                reader.close();
			} catch (JSONException e) {
				Log.d("PARSE ERROR", e.getMessage());
			} catch (IOException e) {
				Log.d("PARSE ERROR2",e.getMessage());
			}
        }
		return list;
	}

	@Override
	protected void onPostExecute(List<JSONObject> result) {
		MainActivity.setObjectJSON(result);
	}
}
