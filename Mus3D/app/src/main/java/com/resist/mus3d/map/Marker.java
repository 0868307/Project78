package com.resist.mus3d.map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.resist.mus3d.R;
import com.resist.mus3d.objects.Afmeerboei;
import com.resist.mus3d.objects.Anchorage;
import com.resist.mus3d.objects.Bolder;
import com.resist.mus3d.objects.Common;
import com.resist.mus3d.objects.Koningspaal;
import com.resist.mus3d.objects.Meerpaal;
import com.resist.mus3d.objects.Object;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

public class Marker extends OverlayItem {
	private Activity activity;
	private Object object;

	public Marker(Activity activity, com.resist.mus3d.objects.Object object) {
		super(null, null, new GeoPoint(object.getLocation().getPosition().getLatitude(), object.getLocation().getPosition().getLongitude()));
		setMarker(activity.getResources().getDrawable(object.getDrawable()));
		this.activity = activity;
		this.object = object;
	}

	private String getDialogTitle() {
        int res = R.string.object_onbekend;
		if (object instanceof Afmeerboei) {
			res = R.string.object_afmeerboei;
		} else if (object instanceof Bolder) {
            res = R.string.object_bolder;
		} else if (object instanceof Koningspaal) {
            res = R.string.object_koningspaal;
		} else if (object instanceof Anchorage) {
            res = R.string.object_ligplaats;
		} else if (object instanceof Meerpaal) {
            res = R.string.object_meerpaal;
		}
        return activity.getResources().getString(res)+" "+object.getObjectid();
	}

	private String getDialogMessage() {
		String descriptionLabel = "Description: ";
		String materialLabel = "Materiaal: ";
		if(LocationTracker.getCurrentLocation() != null){
		}
		StringBuilder sb = new StringBuilder();
		append(sb, "distance: ",String.format("%.2f", object.getDistanceTo(LocationTracker.getCurrentLocation()))+"meter");
		append(sb, "FeatureId: ", object.getFeatureId());
		if(object instanceof Common) {
			Common c = (Common)object;
			append(sb, null, c.getFacilitySecId());
			append(sb, "Haven: ", c.getHarbourId());
		}
		if(object instanceof Anchorage) {
			Anchorage a = (Anchorage)object;
			append(sb, "XME Text: ", a.getXmeText());
			append(sb, "KenmerkZe: ", a.getKenmerkZe());
			append(sb, "Vac Reden: ", a.getVacReason());
			append(sb, "Afmeer Vz: ", a.getAfmeerVz());
		} else if(object instanceof Bolder) {
			Bolder b = (Bolder)object;
			append(sb, descriptionLabel, b.getDescription());
			append(sb, materialLabel, b.getMaterial());
			append(sb, "Bedrijf: ", b.getCompany());
		} else if(object instanceof Koningspaal) {
			Koningspaal k = (Koningspaal)object;
			append(sb, descriptionLabel, k.getDescription());
			append(sb, materialLabel, k.getMaterial());
			append(sb, "Slijtmateriaal: ", k.getWearMaterial());
		}

		return sb.toString();

	}

	private void append(StringBuilder sb, String key, String value) {
		if(value != null) {
			if(key != null) {
				sb.append(key);
			}
			sb.append(value).append('\n');
		}
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
