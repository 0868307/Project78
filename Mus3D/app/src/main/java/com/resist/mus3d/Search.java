package com.resist.mus3d;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.resist.mus3d.adapters.SearchResultAdapter;
import com.resist.mus3d.adapters.SearchTypeSelectAdapter;
import com.resist.mus3d.ar.Rajawali;
import com.resist.mus3d.database.ObjectTable;
import com.resist.mus3d.map.Map;
import com.resist.mus3d.objects.Object;

import java.util.List;

public class Search extends Activity {
    private int[] spinnerIds;
    private SearchResultAdapter resultAdapter;
    private SearchResultAdapter selectedAdapter;
    private EditText searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        spinnerIds = getResources().getIntArray(R.array.objectwaardenArray);

        Spinner searchSpinner = (Spinner) findViewById(R.id.sp_search_objecttype);
        searchText = (EditText) findViewById(R.id.search_text);

        SearchTypeSelectAdapter selectAdapter = new SearchTypeSelectAdapter(this);
        selectAdapter.addAll(getResources().getStringArray(R.array.objectnamenArray));
        searchSpinner.setAdapter(selectAdapter);

        resultAdapter = new SearchResultAdapter(this);
        selectedAdapter = new SearchResultAdapter(this);
        ListView results = (ListView) findViewById(R.id.search_results);
        ListView selected = (ListView) findViewById(R.id.search_selected);

        results.setEmptyView(findViewById(R.id.search_results_empty));
        results.setAdapter(resultAdapter);
        selected.setEmptyView(findViewById(R.id.search_selected_empty));
        selected.setAdapter(selectedAdapter);

        results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object object = resultAdapter.getItem(position);
                selectedAdapter.add(object);
                resultAdapter.remove(object);
            }
        });

        selected.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object object = selectedAdapter.getItem(position);
                resultAdapter.add(object);
                selectedAdapter.remove(object);
            }
        });

        searchEnterPressHandler();
    }


    /**
     * Search query handler.
     *
     * @param v the view
     */
    public void searchQueryHandler(View v) {
        ObjectTable objectTable = new ObjectTable(Mus3D.getDatabase().getDatabase());
        int index = ((Spinner) findViewById(R.id.sp_search_objecttype)).getSelectedItemPosition();
        List<com.resist.mus3d.objects.Object> objects = objectTable.findObjects(((EditText) findViewById(R.id.search_text)).getText().toString(), spinnerIds[index]);
        Log.d(Mus3D.LOG_TAG, "objects found: " + objects.size());
        resultAdapter.clear();
        resultAdapter.addAll(objects);
    }

    private void searchEnterPressHandler() {

        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    in.hideSoftInputFromWindow(searchText.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    searchQueryHandler(null);
                    return true;
                }
                return false;
            }
        });

    }

    /**
     * Clear search box.
     *
     * @param v the view
     */
    public void clearSearchBox(View v) {
        searchText.setText("");
    }

    /**
     * Search button
     *
     * @param v the view
     */
    public void search(View v) {
        if (selectedAdapter.isEmpty()) {
            Toast.makeText(this, R.string.select_items_first, Toast.LENGTH_SHORT).show();
        } else {
            goToNext(true);
        }
    }

    /**
     * Skip button.
     *
     * @param v the view
     */
    public void skip(View v) {
        goToNext(false);
    }


    private void goToNext(boolean search) {
        Intent intent;
        ToggleButton toggle = (ToggleButton) findViewById(R.id.search_toggle);
        if (toggle.isChecked()) {
            intent = new Intent(this, Map.class);
        } else {
            intent = new Intent(this, Rajawali.class);
        }
        if (search) {
            Log.d(Mus3D.LOG_TAG, "num items: " + selectedAdapter.getItems().size());
            intent.putParcelableArrayListExtra("objectList", selectedAdapter.getItems());
        }
        startActivity(intent);
    }
}
