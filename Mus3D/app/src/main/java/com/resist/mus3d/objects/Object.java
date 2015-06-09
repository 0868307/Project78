package com.resist.mus3d.objects;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.resist.mus3d.Mus3D;
import com.resist.mus3d.database.ObjectTable;
import com.resist.mus3d.objects.coords.Coordinate;
import com.resist.mus3d.objects.coords.Point;

import java.util.Date;

public class Object implements Parcelable {
    public static final int TYPE = -1;
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

	}

	@Override
	public String toString() {
		return "Object{" +
				"featureId='" + featureId + '\'' +
				'}';
	}
}
