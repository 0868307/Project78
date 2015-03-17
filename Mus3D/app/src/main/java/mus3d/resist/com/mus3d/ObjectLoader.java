package mus3d.resist.com.mus3d;

import android.os.Environment;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Wouter on 3/17/2015.
 */
public class ObjectLoader {
    public static final String OBJECTPATH = "data/kaartobjecten/";
    public static final String MEERPALEN = OBJECTPATH + "Meerpalen.json";
    public static final String LIGPLAATSEN = OBJECTPATH + "Ligplaatsen.json";
    public static final String KONINGSPALENBEDRIJF = OBJECTPATH + "Koningspalen_met_Bedrijfsnamen.json";
    public static final String KONINGSPALEN = OBJECTPATH + "Koningspalen.json";
    public static final String BOLDER_BEDRIJF = OBJECTPATH + "Bolder_Bedrijfsnaam.json";
    public static final String AFMEERBOEIEN = OBJECTPATH + "Afmeerboeien.json";


    public static ArrayList<JSONObject> load(InputStream ... streams ) {
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
            } catch (JSONException e) {
                Log.d("PARSE ERROR", e.getMessage());
            } catch (IOException e) {
                Log.d("PARSE ERROR2",e.getMessage());
            }
        }
        return list;
    }
}
