package com.resist.mus3d.map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.resist.mus3d.R;
import com.resist.mus3d.objects.*;
import com.resist.mus3d.objects.Object;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

public class Marker extends OverlayItem {
	private Activity activity;
	private Object object;

	public Marker(Activity activity, com.resist.mus3d.objects.Object object) {
		super(null, null, new GeoPoint(object.getLocation().getPosition().getLatitude(), object.getLocation().getPosition().getLongitude()));
		setMarker(activity.getResources().getDrawable(getDrawable(object)));
		this.activity = activity;
		this.object = object;
	}

	private int getDrawable(Object object) {
		if (object instanceof Afmeerboei) {
			return R.drawable.ic_afmeerboei;
		} else if (object instanceof Bolder) {
			return R.drawable.ic_bolder;
		} else if (object instanceof Koningspaal) {
			return R.drawable.ic_koningspaal;
		} else if (object instanceof Anchorage) {
			return R.drawable.ic_aanlegplaats;
		} else if (object instanceof Meerpaal) {
			return R.drawable.ic_meerpaal;
		}
		return R.drawable.ic_onbekend;
	}

	private String getDialogTitle() {
		if (object instanceof Afmeerboei) {
			return "Afmeerboei";
		} else if (object instanceof Bolder) {
			return "Bolder";
		} else if (object instanceof Koningspaal) {
			return "Koningspaal";
		} else if (object instanceof Anchorage) {
			return "Ligplaats";
		} else if (object instanceof Meerpaal) {
			return "Meerpaal";
		}
		return "Onbekend";
	}

	private String getDialogMessage() {
		return "";
	}

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
}
