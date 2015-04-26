package com.resist.mus3d.objects;

import java.util.Date;

public class Bolder extends Common {
    public static final int TYPE = 1;
	private String description;
	private String material;
	private String anchor;
	private String type;
	private String partner;
	private String company;

	public Bolder(int objectid, String createdBy, Date createdAt, String editedBy, Date editedAt, String featureId) {
		super(objectid, createdBy, createdAt, editedBy, editedAt, featureId);
	}

	public Bolder(int objectid, String createdBy, Date createdAt, String editedBy, Date editedAt, String featureId, String facilityId, String facilitySecId, Harbour harbour, String regionId, String description, String material, String anchor, String type, String partner, String company) {
		super(objectid, createdBy, createdAt, editedBy, editedAt, featureId, facilityId, facilitySecId, harbour, regionId);
		this.description = description;
		this.material = material;
		this.anchor = anchor;
		this.type = type;
		this.partner = partner;
		this.company = company;
	}

	public int getType() {
		return TYPE;
	}
}
