package com.resist.mus3d;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.resist.mus3d.objects.Object;

import java.util.ArrayList;

/**
 * Created by Wouter on 6/9/2015.
 */
public class MyArrayAdapter extends ArrayAdapter<Object> {

    Context mContext;
    int layoutResourceId;
    ArrayList<Object> data = null;

    public MyArrayAdapter(Context mContext, int layoutResourceId,
                            ArrayList<Object> data) {
        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        Object objectItem = data.get(position);
        TextView textViewItem = (TextView) new TextView(convertView.getContext());
        textViewItem.setText("result " + position);
        textViewItem.setTag("" + position);

        return convertView;

    }
}
