package com.resist.mus3d.objects;

import java.util.Date;

public class Common extends Object {
	private int id;
	private String facilityId;
	private String facilitySecId;
	private Harbour harbour;
	private String regionId;

	public Common(int objectid, String createdBy, Date createdAt, String editedBy, Date editedAt, String featureId, int id, String facilityId, String facilitySecId, Harbour harbour, String regionId) {
		super(objectid, createdBy, createdAt, editedBy, editedAt, featureId);
		this.id = id;
		this.facilityId = facilityId;
		this.facilitySecId = facilitySecId;
		this.harbour = harbour;
		this.regionId = regionId;
	}
}
