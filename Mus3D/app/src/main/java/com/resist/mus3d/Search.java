package com.resist.mus3d;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.resist.mus3d.ar.Rajawali;
import com.resist.mus3d.database.ObjectTable;
import com.resist.mus3d.map.Map;
import com.resist.mus3d.objects.Object;

import java.util.ArrayList;
import java.util.List;

public class Search extends Activity {
    private static final int[] images = { R.drawable.ic_afmeerboei,
            R.drawable.ic_bolder, R.drawable.ic_koningspaal,
            R.drawable.ic_aanlegplaats, R.drawable.ic_meerpaal};

	private String[] strings;
    private int[] spinnerIds;
	private ArrayList<Object> objectList = new ArrayList<>();
	private MyArrayAdapter adapter;
    private SearchResultAdapter resultAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		strings = getResources().getStringArray(R.array.objectnamenArray);
        spinnerIds = getResources().getIntArray(R.array.objectwaardenArray);

		Spinner searchSpinner = (Spinner)findViewById(R.id.sp_search_objecttype);
		searchSpinner.setAdapter(new ObjectSelectionAdapter(this, R.layout.row, strings));
		addList();
		makeRandomObjects();
		//selected(list);

        resultAdapter = new SearchResultAdapter(this);

        ((ListView)findViewById(R.id.search_results)).setAdapter(resultAdapter);
	}

	public void makeRandomObjects() {/*
		objectList.add(new Object(1, "wouter", null, "wouter", null, "hoi"));
		objectList.add(new Object(2, "f", null, "t", null, "a"));
		objectList.add(new Object(3,"r",null,"g",null,"e"));
		objectList.add(new Object(4, "g", null, "h", null, "r"));*/
		System.out.println(objectList.size());
		adapter.notifyDataSetChanged();
	}

	public void addList() {
		ScrollView scrollView = (ScrollView)findViewById(R.id.selectedlist);
		ListView listView = new ListView(this);
		adapter = new MyArrayAdapter(this, R.layout.row, objectList);
		listView.setAdapter(adapter);
		scrollView.addView(listView);
	}

	public void searchQuery(View v) {
		ObjectTable objectTable = new ObjectTable(Mus3D.getDatabase().getDatabase());
        int index = ((Spinner)findViewById(R.id.sp_search_objecttype)).getSelectedItemPosition();
		List<com.resist.mus3d.objects.Object> objects = objectTable.findObjects(((EditText) findViewById(R.id.search_text)).getText().toString(), spinnerIds[index]);
        Log.w(Mus3D.LOG_TAG, "objects found: "+objects.size());
		resultAdapter.clear();
        resultAdapter.addAll(objects);
	}

	public void skip(View v) {
		goToNext();
	}

	public void selected(List<Object> list) {
		objectList.addAll(list);
		adapter.notifyDataSetChanged();
	}

	public void clearSearchBox(View v) {
		EditText searchText = (EditText)findViewById(R.id.search_text);
		searchText.setText("");
	}

	public void go(View v){
		goToNext();
	}

	public void goToNext() {
		Intent intent;
		ToggleButton toggle = (ToggleButton)findViewById(R.id.search_toggle);
		if(toggle.isChecked()) {
			intent = new Intent(this, Map.class);
		} else {
			intent = new Intent(this, Rajawali.class);
		}
		intent.putParcelableArrayListExtra("objectList",objectList);
		startActivity(intent);
	}

	public class ObjectSelectionAdapter extends ArrayAdapter<String> {

        public ObjectSelectionAdapter(Context context, int textViewResourceId, String[] objects) {
			super(context, textViewResourceId, objects);
		}

		@Override
		public View getDropDownView(int position, View convertView, ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}

		private View getCustomView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			View row = inflater.inflate(R.layout.row, parent, false);
			TextView label = (TextView)row.findViewById(R.id.tv_row_objecttitle);
			label.setText(strings[position]);

			ImageView icon = (ImageView)row.findViewById(R.id.iv_row_icon);
			icon.setImageResource(images[position]);

			return row;
		}
	}
}
