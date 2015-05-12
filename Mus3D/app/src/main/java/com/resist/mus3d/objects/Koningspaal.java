package com.resist.mus3d.objects;

import java.util.Date;

public class Koningspaal extends Common {
    public static final int TYPE = 2;
	private String confirmation;
	private String description;
	private String material;
	private String wearMaterial;

	public Koningspaal(int objectid, String createdBy, Date createdAt, String editedBy, Date editedAt, String featureId) {
		super(objectid, createdBy, createdAt, editedBy, editedAt, featureId);
	}

	public Koningspaal(int objectid, String createdBy, Date createdAt, String editedBy, Date editedAt, String featureId, String facilityId, String facilitySecId, Harbour harbour, String regionId, String confirmation, String description, String material, String wearMaterial) {
		super(objectid, createdBy, createdAt, editedBy, editedAt, featureId, facilityId, facilitySecId, harbour, regionId);
		this.confirmation = confirmation;
		this.description = description;
		this.material = material;
		this.wearMaterial = wearMaterial;
		super.load();
	}

	public String getConfirmation() {
		if(!isComplete()) {
			load();
		}
		return confirmation;
	}

	public void setConfirmation(String confirmation) {
		if(!isComplete()) {
			load();
		}
		this.confirmation = confirmation;
	}

	public String getDescription() {
		if(!isComplete()) {
			load();
		}
		return description;
	}

	public void setDescription(String description) {
		if(!isComplete()) {
			load();
		}
		this.description = description;
	}

	public String getMaterial() {
		if(!isComplete()) {
			load();
		}
		return material;
	}

	public void setMaterial(String material) {
		if(!isComplete()) {
			load();
		}
		this.material = material;
	}

	public String getWearMaterial() {
		if(!isComplete()) {
			load();
		}
		return wearMaterial;
	}

	public void setWearMaterial(String wearMaterial) {
		if(!isComplete()) {
			load();
		}
		this.wearMaterial = wearMaterial;
	}

	public int getType() {
		return TYPE;
	}
}
