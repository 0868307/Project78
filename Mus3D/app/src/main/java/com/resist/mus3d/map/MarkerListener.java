package com.resist.mus3d.map;

import android.app.Activity;
import android.app.AlertDialog;

import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

public class MarkerListener implements ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {
	private Activity ctx;

	public MarkerListener(Activity ctx) {
		this.ctx = ctx;
	}

	@Override
	public boolean onItemSingleTapUp(int i, OverlayItem o) {
		AlertDialog dialog = new AlertDialog.Builder(ctx)
				.setMessage(o.getSnippet())
				.setTitle(o.getTitle())
				.create();
		dialog.show();
		return false;
	}

	@Override
	public boolean onItemLongPress(int i, OverlayItem o) {
		return false;
	}
}
