package com.resist.mus3d.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.resist.mus3d.R;

public class SearchTypeSelectAdapter extends ArrayAdapter<String> {
    private static final int[] images = {R.drawable.ic_afmeerboei,
            R.drawable.ic_bolder, R.drawable.ic_koningspaal,
            R.drawable.ic_aanlegplaats, R.drawable.ic_meerpaal};
    private Activity activity;

    /**
     * Instantiates a new Search type select adapter.
     *
     * @param activity the activity
     */
    public SearchTypeSelectAdapter(Activity activity) {
        super(activity, R.layout.row);
        this.activity = activity;
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
        LayoutInflater inflater = activity.getLayoutInflater();
        View row = inflater.inflate(R.layout.row, parent, false);
        TextView label = (TextView) row.findViewById(R.id.tv_row_objecttitle);
        label.setText(getItem(position));

        ImageView icon = (ImageView) row.findViewById(R.id.iv_row_icon);
        icon.setImageResource(images[position]);

        return row;
    }
}
