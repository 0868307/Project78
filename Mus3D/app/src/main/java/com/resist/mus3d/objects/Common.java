package com.resist.mus3d.objects;

import java.util.Date;

public class Common extends Object {
	private String facilityId;
	private String facilitySecId;
	private Harbour harbour;
	private String regionId;

	public Common(int objectid, String createdBy, Date createdAt, String editedBy, Date editedAt, String featureId, String facilityId, String facilitySecId, Harbour harbour, String regionId) {
		super(objectid, createdBy, createdAt, editedBy, editedAt, featureId);
		this.facilityId = facilityId;
		this.facilitySecId = facilitySecId;
		this.harbour = harbour;
		this.regionId = regionId;
	}
}
