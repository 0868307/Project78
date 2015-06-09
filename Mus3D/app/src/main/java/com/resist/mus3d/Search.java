package com.resist.mus3d;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.resist.mus3d.map.Map;

public class Search extends Activity {
	private static final int[] arr_images = { R.drawable.ic_afmeerboei,
			R.drawable.ic_bolder, R.drawable.ic_koningspaal,
			R.drawable.ic_aanlegplaats, R.drawable.ic_meerpaal};
	private String[] strings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		strings = getResources().getStringArray(R.array.objectnamenArray);

		Spinner searchSpinner = (Spinner)findViewById(R.id.sp_search_objecttype);
		searchSpinner.setAdapter(new MyAdapter(this, R.layout.row, strings));
	}

	public void skip(View v) {
		ToggleButton toggle = (ToggleButton)findViewById(R.id.search_toggle);
		if(toggle.isChecked()) {
			startActivity(new Intent(this, Map.class));
		} else {
			startActivity(new Intent(this, Rajawali.class));
		}
	}

	public class MyAdapter extends ArrayAdapter<String>{

		public MyAdapter(Context context, int textViewResourceId,   String[] objects) {
			super(context, textViewResourceId, objects);
		}

		@Override
		public View getDropDownView(int position, View convertView,ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}

		public View getCustomView(int position, View convertView, ViewGroup parent) {

			LayoutInflater inflater=getLayoutInflater();
			View row=inflater.inflate(R.layout.row, parent, false);
			TextView label=(TextView)row.findViewById(R.id.tv_row_objecttitle);
			label.setText(strings[position]);

			ImageView icon=(ImageView)row.findViewById(R.id.iv_row_icon);
			icon.setImageResource(arr_images[position]);

			return row;
		}
	}

}
