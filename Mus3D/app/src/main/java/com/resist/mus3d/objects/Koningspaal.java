package com.resist.mus3d.objects;

import java.util.Date;

public class Koningspaal extends Common {
    public static final int TYPE = 2;
	private String confirmation;
	private String description;
	private String material;
	private String wearMaterial;

	public Koningspaal(int objectid, String createdBy, Date createdAt, String editedBy, Date editedAt, String featureId, String facilityId, String facilitySecId, Harbour harbour, String regionId, String confirmation, String description, String material, String wearMaterial) {
		super(objectid, createdBy, createdAt, editedBy, editedAt, featureId, facilityId, facilitySecId, harbour, regionId);
		this.confirmation = confirmation;
		this.description = description;
		this.material = material;
		this.wearMaterial = wearMaterial;
	}

	public int getType() {
		return TYPE;
	}
}
