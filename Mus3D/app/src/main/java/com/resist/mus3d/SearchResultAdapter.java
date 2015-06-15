package com.resist.mus3d;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchResultAdapter extends ArrayAdapter<com.resist.mus3d.objects.Object> {
    private Activity activity;
    public SearchResultAdapter(Activity activity) {
        super(activity, R.layout.row);
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        com.resist.mus3d.objects.Object object = getItem(position);
        LayoutInflater inflater = activity.getLayoutInflater();
        View row = inflater.inflate(R.layout.row, parent, false);
        TextView label = (TextView)row.findViewById(R.id.tv_row_objecttitle);
        label.setText(object.toString());

        ImageView icon = (ImageView)row.findViewById(R.id.iv_row_icon);
        icon.setImageResource(object.getDrawable());

        return row;
    }
}
