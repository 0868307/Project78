package com.resist.mus3d.dataconverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONObject;

import com.resist.mus3d.dataconverter.database.AlgemeenTable;
import com.resist.mus3d.dataconverter.database.BolderTable;
import com.resist.mus3d.dataconverter.database.HavenTable;
import com.resist.mus3d.dataconverter.database.KoningspaalTable;
import com.resist.mus3d.dataconverter.database.LigplaatsTable;
import com.resist.mus3d.dataconverter.database.ObjectTable;
import com.resist.mus3d.dataconverter.database.Table;


public class DataConverter {
	private static final Table OBJECT = new ObjectTable();
	private static final Table ALGEMEEN = new AlgemeenTable();
	private static final Table BOLDER = new BolderTable();
	private static final Table HAVEN = new HavenTable();
	private static final Table KONINGSPAAL = new KoningspaalTable();
	private static final Table LIGPLAATS = new LigplaatsTable();
	private static final Table[] tables = {
		OBJECT, ALGEMEEN, BOLDER, HAVEN, KONINGSPAAL, LIGPLAATS
	};
	private String path;

	public static void main(String[] args) {
		new DataConverter(args[0]).createTables().createInsert();
	}

	public DataConverter(String path) {
		this.path = path;
	}

	public DataConverter createTables() {
		for(Table t : tables) {
			System.out.println(t.getCreate());
		}
		return this;
	}

	public void createInsert() {
		loadAlgemeen(loadFile(path+"/Afmeerboeien.json"));
		loadBolders();
		loadKoningspalen();
		loadLigplaatsen();
		loadAlgemeen(loadFile(path+"/Meerpalen.json"));
	}

	private void loadAlgemeen(JSONObject data) {
		System.out.println(OBJECT.getInsert(data));
		System.out.println(ALGEMEEN.getInsert(data));
	}

	private void loadBolders() {
		JSONObject data = loadFile(path+"/Bolder_Bedrijfsnaam.json");
		loadAlgemeen(data);
		System.out.println(BOLDER.getInsert(data));
	}

	private void loadKoningspalen() {
		JSONObject data = loadFile(path+"/Koningspalen_met_Bedrijfsnamen.json");
		loadAlgemeen(data);
		System.out.println(KONINGSPAAL.getInsert(data));
	}

	private void loadLigplaatsen() {
		JSONObject data = loadFile(path+"/Ligplaatsen.json");
		System.out.println(HAVEN.getInsert(data));
		System.out.println(LIGPLAATS.getInsert(data));
	}

	private JSONObject loadFile(String path) {
		StringBuilder sb = new StringBuilder();
		try {
			FileReader in = new FileReader(new File(path));
			int b;
			while((b = in.read()) != -1) {
				sb.appendCodePoint(b);
			}
			in.close();
			return new JSONObject(sb.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
