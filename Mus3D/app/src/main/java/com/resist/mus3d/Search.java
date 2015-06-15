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

import com.resist.mus3d.adapters.MyArrayAdapter;
import com.resist.mus3d.adapters.SearchResultAdapter;
import com.resist.mus3d.adapters.SearchTypeSelectAdapter;
import com.resist.mus3d.ar.Rajawali;
import com.resist.mus3d.database.ObjectTable;
import com.resist.mus3d.map.Map;
import com.resist.mus3d.objects.Object;

import java.util.ArrayList;
import java.util.List;

public class Search extends Activity {

    private int[] spinnerIds;
	private ArrayList<Object> objectList = new ArrayList<>();
	private MyArrayAdapter adapter;
    private SearchResultAdapter resultAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

        spinnerIds = getResources().getIntArray(R.array.objectwaardenArray);

		Spinner searchSpinner = (Spinner)findViewById(R.id.sp_search_objecttype);
        SearchTypeSelectAdapter selectAdapter = new SearchTypeSelectAdapter(this);
        selectAdapter.addAll(getResources().getStringArray(R.array.objectnamenArray));
		searchSpinner.setAdapter(selectAdapter);
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
		adapter = new MyArrayAdapter(this, R.layout.resultrow, objectList);
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
}
