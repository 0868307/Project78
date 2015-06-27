package com.resist.mus3d.map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;

import com.resist.mus3d.R;
import com.resist.mus3d.objects.Object;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

public class Marker extends OverlayItem {
    private Activity activity;
    private Object object;

    /**
     * Instantiates a new Marker.
     *
     * @param activity the activity
     * @param object   the object
     */
    public Marker(Activity activity, com.resist.mus3d.objects.Object object) {
        this(activity, object, true);
    }

    public Marker(Activity activity, com.resist.mus3d.objects.Object object, boolean highlighted) {
        super(null, null, new GeoPoint(object.getLocation().getPosition().getLatitude(), object.getLocation().getPosition().getLongitude()));
        Drawable marker = activity.getResources().getDrawable(object.getDrawable());
        if (!highlighted) {
            marker.setAlpha(127);
        }
        setMarker(marker);
        this.activity = activity;
        this.object = object;
    }

    private String getDialogTitle() {
        int res = object.getTypeName();
        return activity.getResources().getString(res) + " " + object.getObjectid();
    }

    private String getDialogMessage() {
        DialogContents dialogContents = new DialogContents();
        object.setDialogText(dialogContents);
        return dialogContents.toString();
    }

    /**
     * Gets dialog.
     *
     * @return the dialog
     */
    public AlertDialog getDialog() {
        return new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle)
                .setMessage(getDialogMessage())
                .setTitle(getDialogTitle())
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .create();
    }

    public class DialogContents {
        private StringBuilder sb;

        public DialogContents() {
            sb = new StringBuilder();
        }

        public void append(String key, String value) {
            if (value != null) {
                if (key != null) {
                    sb.append(key);
                }
                sb.append(value).append('\n');
            }
        }

        @Override
        public String toString() {
            return sb.toString();
        }
    }
}
