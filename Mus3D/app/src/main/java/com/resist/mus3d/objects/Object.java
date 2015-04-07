package com.resist.mus3d.objects;

import com.resist.mus3d.objects.coords.Coordinate;

import java.util.Date;

public class Object {
	private int objectid;
	private String createdBy;
	private Date createdAt;
	private String editedBy;
	private Date editedAt;
	private String featureId;
	private Coordinate location;

	public Object(int objectid, String createdBy, Date createdAt, String editedBy, Date editedAt, String featureId) {
		this.objectid = objectid;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.editedBy = editedBy;
		this.editedAt = editedAt;
		this.featureId = featureId;
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

	public Coordinate getLocation() {
		return location;
	}
}
