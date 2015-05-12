package com.resist.mus3d.map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.resist.mus3d.R;
import com.resist.mus3d.objects.Afmeerboei;
import com.resist.mus3d.objects.Anchorage;
import com.resist.mus3d.objects.Bolder;
import com.resist.mus3d.objects.Koningspaal;
import com.resist.mus3d.objects.Meerpaal;

import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

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
