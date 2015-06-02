package com.resist.mus3d;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.resist.mus3d.map.Map;

public class Search extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
	}

	public void skip(View v) {
		Switch toggle = (Switch)findViewById(R.id.search_toggle);
		if(toggle.isChecked()) {
			startActivity(new Intent(this, Map.class));
		} else {
			startActivity(new Intent(this, Rajawali.class));
		}
	}
}
