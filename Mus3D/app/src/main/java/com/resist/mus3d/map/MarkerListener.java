package com.resist.mus3d.map;

import android.app.Activity;

import org.osmdroid.views.overlay.ItemizedIconOverlay;

public class MarkerListener implements ItemizedIconOverlay.OnItemGestureListener<Marker> {
    private Activity ctx;

    public MarkerListener(Activity ctx) {
        this.ctx = ctx;
    }

    @Override
    public boolean onItemSingleTapUp(int i, Marker o) {
		o.getDialog().show();
        return false;
    }

    @Override
    public boolean onItemLongPress(int i, Marker o) {
        return false;
    }
}
