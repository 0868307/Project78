package com.resist.mus3d.objects;

import java.util.Date;

public class Koningspaal extends Common {
	private int id;
	private String confirmation;
	private String description;
	private String material;
	private String wearMaterial;

	public Koningspaal(int objectid, String createdBy, Date createdAt, String editedBy, Date editedAt, String featureId, int id, String facilityId, String facilitySecId, Harbour harbour, String regionId, int id1, String confirmation, String description, String material, String wearMaterial) {
		super(objectid, createdBy, createdAt, editedBy, editedAt, featureId, id, facilityId, facilitySecId, harbour, regionId);
		id = id1;
		this.confirmation = confirmation;
		this.description = description;
		this.material = material;
		this.wearMaterial = wearMaterial;
	}
}
