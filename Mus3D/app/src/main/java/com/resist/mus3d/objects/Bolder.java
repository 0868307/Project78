package com.resist.mus3d.objects;

import java.util.Date;

public class Bolder extends Common {
	private int id;
	private String description;
	private String material;
	private String anchor;
	private String type;
	private String partner;
	private String company;

	public Bolder(int objectid, String createdBy, Date createdAt, String editedBy, Date editedAt, String featureId, int id, String facilityId, String facilitySecId, Harbour harbour, String regionId, int id1, String description, String material, String anchor, String type, String partner, String company) {
		super(objectid, createdBy, createdAt, editedBy, editedAt, featureId, id, facilityId, facilitySecId, harbour, regionId);
		id = id1;
		this.description = description;
		this.material = material;
		this.anchor = anchor;
		this.type = type;
		this.partner = partner;
		this.company = company;
	}
}
