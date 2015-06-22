package com.resist.mus3d.objects;

import com.resist.mus3d.Mus3D;
import com.resist.mus3d.database.CommonTable;
import com.resist.mus3d.map.Marker;

import java.util.Date;

public abstract class Common extends Object {
	private static final int COLOR = 0x70FF0000;
	private String facilityId;
	private String facilitySecId;
	private String regionId;
	private String harbourId;

	public Common(int objectid, String createdBy, Date createdAt, String editedBy, Date editedAt, String featureId) {
		super(objectid, createdBy, createdAt, editedBy, editedAt, featureId);
	}

	public Common(int objectid, String createdBy, Date createdAt, String editedBy, Date editedAt, String featureId, String facilityId, String facilitySecId, String regionId, String harbourId) {
		super(objectid, createdBy, createdAt, editedBy, editedAt, featureId);
		this.facilityId = facilityId;
		this.facilitySecId = facilitySecId;
		this.regionId = regionId;
		this.harbourId = harbourId;
		super.load();
	}

	public String getHarbourId() {
		return harbourId;
	}

	public void setHarbourId(String harbourId) {
		this.harbourId = harbourId;
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

	@Override
	protected void load() {
		super.load();
		new CommonTable(Mus3D.getDatabase().getDatabase()).loadObject(this);
	}

    @Override
    public void setDialogText(Marker.DialogContents dialog) {
        super.setDialogText(dialog);
        dialog.append(null, getFacilitySecId());
        dialog.append("Haven: ", getHarbourId());
    }
	@Override
	public int getColor() {
		return COLOR;
	}
}
