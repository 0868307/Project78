package com.resist.mus3d.objects;

import java.util.Date;

public class Common extends Object {
	private String facilityId;
	private String facilitySecId;
	private Harbour harbour;
	private String regionId;

	public Common(int objectid, String createdBy, Date createdAt, String editedBy, Date editedAt, String featureId) {
		super(objectid, createdBy, createdAt, editedBy, editedAt, featureId);
	}

	public Common(int objectid, String createdBy, Date createdAt, String editedBy, Date editedAt, String featureId, String facilityId, String facilitySecId, Harbour harbour, String regionId) {
		super(objectid, createdBy, createdAt, editedBy, editedAt, featureId);
		this.facilityId = facilityId;
		this.facilitySecId = facilitySecId;
		this.harbour = harbour;
		this.regionId = regionId;
		super.load();
	}

	public String getFacilityId() {
		if(!isComplete()) {
			load();
		}
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		if(!isComplete()) {
			load();
		}
		this.facilityId = facilityId;
	}

	public String getFacilitySecId() {
		if(!isComplete()) {
			load();
		}
		return facilitySecId;
	}

	public void setFacilitySecId(String facilitySecId) {
		if(!isComplete()) {
			load();
		}
		this.facilitySecId = facilitySecId;
	}

	public Harbour getHarbour() {
		if(!isComplete()) {
			load();
		}
		return harbour;
	}

	public void setHarbour(Harbour harbour) {
		if(!isComplete()) {
			load();
		}
		this.harbour = harbour;
	}

	public String getRegionId() {
		if(!isComplete()) {
			load();
		}
		return regionId;
	}

	public void setRegionId(String regionId) {
		if(!isComplete()) {
			load();
		}
		this.regionId = regionId;
	}
}
