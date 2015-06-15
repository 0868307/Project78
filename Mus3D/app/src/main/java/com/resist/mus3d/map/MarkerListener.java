package com.resist.mus3d.map;

import org.osmdroid.views.overlay.ItemizedIconOverlay;

public class MarkerListener implements ItemizedIconOverlay.OnItemGestureListener<Marker> {
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
