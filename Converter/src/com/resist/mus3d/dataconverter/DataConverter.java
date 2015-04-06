package com.resist.mus3d.dataconverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONObject;

import com.resist.mus3d.dataconverter.database.AlgemeenTable;
import com.resist.mus3d.dataconverter.database.BolderTable;
import com.resist.mus3d.dataconverter.database.CoordinateTable;
import com.resist.mus3d.dataconverter.database.HavenTable;
import com.resist.mus3d.dataconverter.database.KoningspaalTable;
import com.resist.mus3d.dataconverter.database.LigplaatsTable;
import com.resist.mus3d.dataconverter.database.ObjectTable;
import com.resist.mus3d.dataconverter.database.Table;


public class DataConverter {
	public static final Table OBJECT = new ObjectTable();
	public static final Table ALGEMEEN = new AlgemeenTable();
	public static final Table BOLDER = new BolderTable();
	public static final Table HAVEN = new HavenTable();
	public static final Table KONINGSPAAL = new KoningspaalTable();
	public static final Table LIGPLAATS = new LigplaatsTable();
	public static final CoordinateTable COORDS = new CoordinateTable();
	private static final Table[] tables = {
		OBJECT, ALGEMEEN, BOLDER, HAVEN, KONINGSPAAL, LIGPLAATS, COORDS
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
		loadAlgemeen(0, loadFile(path+"/Afmeerboeien.json"));
		loadBolders(1);
		loadKoningspalen(2);
		loadLigplaatsen(3);
		loadAlgemeen(4, loadFile(path+"/Meerpalen.json"));
	}

	private void loadAlgemeen(int index, JSONObject data) {
		System.out.println(OBJECT.getInsert(index, data));
		System.out.println(ALGEMEEN.getInsert(index, data));
	}

	private void loadBolders(int index) {
		JSONObject data = loadFile(path+"/Bolder_Bedrijfsnaam.json");
		loadAlgemeen(index, data);
		System.out.println(BOLDER.getInsert(index, data));
	}

	private void loadKoningspalen(int index) {
		JSONObject data = loadFile(path+"/Koningspalen_met_Bedrijfsnamen.json");
		loadAlgemeen(index, data);
		System.out.println(KONINGSPAAL.getInsert(index, data));
	}

	private void loadLigplaatsen(int index) {
		JSONObject data = loadFile(path+"/Ligplaatsen.json");
		System.out.println(HAVEN.getInsert(index, data));
		System.out.println(LIGPLAATS.getInsert(index, data));
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
