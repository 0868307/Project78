package com.resist.mus3d.objects;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.resist.mus3d.Mus3D;
import com.resist.mus3d.R;
import com.resist.mus3d.database.ObjectTable;
import com.resist.mus3d.map.LocationTracker;
import com.resist.mus3d.map.Marker;
import com.resist.mus3d.objects.coords.Coordinate;
import com.resist.mus3d.objects.coords.Point;

import java.util.Date;

public abstract class Object implements Parcelable {
    public static final int TYPE = -1;
    public static final Parcelable.Creator<Object> CREATOR = new Parcelable.Creator<Object>() {
        @Override
        public Object createFromParcel(Parcel in) {
            int[] ids = new int[2];
            String[] vals = new String[3];
            in.readIntArray(ids);
            in.readStringArray(vals);
            int objectid = ids[0];
            int type = ids[1];
            switch (type) {
                case Ligplaats.TYPE:
                    return new Ligplaats(objectid, vals[0], null, vals[1], null, vals[2]);
                case Afmeerboei.TYPE:
                    return new Afmeerboei(objectid, vals[0], null, vals[1], null, vals[2]);
                case Bolder.TYPE:
                    return new Bolder(objectid, vals[0], null, vals[1], null, vals[2]);
                case Koningspaal.TYPE:
                    return new Koningspaal(objectid, vals[0], null, vals[1], null, vals[2]);
                case Meerpaal.TYPE:
                    return new Meerpaal(objectid, vals[0], null, vals[1], null, vals[2]);
            }
            return null;
        }

        @Override
        public Object[] newArray(int size) {
            return new Object[size];
        }
    };

	private int objectid;
	private String createdBy;
	private Date createdAt;
	private String editedBy;
	private Date editedAt;
	private String featureId;
	private Coordinate location;
	private boolean complete = false;

	public Object(int objectid, String createdBy, Date createdAt, String editedBy, Date editedAt, String featureId) {
		this.objectid = objectid;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.editedBy = editedBy;
		this.editedAt = editedAt;
		this.featureId = featureId;
	}

    public abstract int getDrawable();

	public int getType() {
		return TYPE;
	}

	public int getObjectid() {
		return objectid;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public String getEditedBy() {
		return editedBy;
	}

	public Date getEditedAt() {
		return editedAt;
	}

	public String getFeatureId() {
		return featureId;
	}

	public void setLocation(Coordinate coordinate) {
		location = coordinate;
	}

	public Coordinate getLocation() {
		if(location == null) {
			location = new ObjectTable(Mus3D.getDatabase().getDatabase()).getCoordinates(this);
		}
		return location;
	}

	protected boolean isComplete() {
		return complete;
	}

	protected void load() {
		complete = true;
	}

	public double getDistanceTo(Object object) {
		return getLocation().getDistanceTo(object.getLocation());
	}

	public double getDistanceTo(Location location) {
		return getLocation().getDistanceTo(new Point(location));
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
        parcel.writeIntArray(new int[] {objectid, getType()});
        parcel.writeStringArray(new String[] {createdBy, editedBy, featureId});
	}

    public String getUsefulDescription() {
        return getFeatureId();
    }

	@Override
	public boolean equals(java.lang.Object other) {
		if(other instanceof com.resist.mus3d.objects.Object) {
			com.resist.mus3d.objects.Object that = (com.resist.mus3d.objects.Object)other;
			return that.getType() == this.getType() && that.getObjectid() == this.getObjectid();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return ("com.resist.mus3d.objects.Object["+getType()+","+getObjectid()+"]").hashCode();
	}

    public int getTypeName() {
        return R.string.object_onbekend;
    }

    public void setDialogText(Marker.DialogContents dialog) {
        if(LocationTracker.getCurrentLocation() != null) {
            dialog.append("Afstand: ", String.format("%.2f meter", getDistanceTo(LocationTracker.getCurrentLocation())));
        }
        dialog.append("FeatureId: ", getFeatureId());
        dialog.append("Gemaakt door: ", getCreatedBy());
    }
}
