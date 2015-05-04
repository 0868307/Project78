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

public class MarkerListener implements ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {
    private Activity ctx;

    public MarkerListener(Activity ctx) {
        this.ctx = ctx;
    }

    @Override
    public boolean onItemSingleTapUp(int i, OverlayItem o) {
        if (o.getTitle().equals("0")) {
            AlertDialog dialog = new AlertDialog.Builder(ctx, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                    .setMessage(o.getSnippet())
                    .setTitle("Afmeerboei")
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .create();
            dialog.show();
        } else if (o.getTitle().equals("1")) {
            AlertDialog dialog = new AlertDialog.Builder(ctx, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                    .setMessage(o.getSnippet())
                    .setTitle("Bolder")
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .create();
            dialog.show();
        } else if (o.getTitle().equals("2")) {
            AlertDialog dialog = new AlertDialog.Builder(ctx, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                    .setMessage(o.getSnippet())
                    .setTitle("Koningspaal")
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .create();
            dialog.show();
        } else if (o.getTitle().equals("3")) {
            AlertDialog dialog = new AlertDialog.Builder(ctx, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                    .setMessage(o.getSnippet())
                    .setTitle("Aanlegplaats")
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .create();
            dialog.show();
        } else if (o.getTitle().equals("4")) {
            AlertDialog dialog = new AlertDialog.Builder(ctx, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                    .setMessage(o.getSnippet())
                    .setTitle("Meerpaal")
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .create();
            dialog.show();
        } else {
            AlertDialog dialog = new AlertDialog.Builder(ctx, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                    .setMessage(o.getSnippet())
                    .setTitle(o.getTitle())
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .create();
            dialog.show();
        }
        return false;
    }

    @Override
    public boolean onItemLongPress(int i, OverlayItem o) {
        return false;
    }
}
