package com.resist.mus3d.objects;

import java.util.Date;

public class Anchorage extends Object {
    public static final int TYPE = 3;
	private String complexId;
	private String globalId;
	private String kenmerkZe;
	private String lxmeText;
	private Date occupiedFrom;
	private String occRecNo;
	private Date occupiedTill;
	private String vacReason;
	private String xmeText;
	private String afmeerVz;
	private String owner;
	private String use;
	private Harbour harbour;
	private String anchorage;
	private String nauticalState;
	private String shoreNo;
	private String primaryFunction;
	private String type;

	public Anchorage(int objectid, String createdBy, Date createdAt, String editedBy, Date editedAt, String featureId, String complexId, String globalId, String kenmerkZe, String lxmeText, Date occupiedFrom, String occRecNo, Date occupiedTill, String vacReason, String xmeText, String afmeerVz, String owner, String use, Harbour harbour, String anchorage, String nauticalState, String shoreNo, String primaryFunction, String type) {
		super(objectid, createdBy, createdAt, editedBy, editedAt, featureId);
		this.complexId = complexId;
		this.globalId = globalId;
		this.kenmerkZe = kenmerkZe;
		this.lxmeText = lxmeText;
		this.occupiedFrom = occupiedFrom;
		this.occRecNo = occRecNo;
		this.occupiedTill = occupiedTill;
		this.vacReason = vacReason;
		this.xmeText = xmeText;
		this.afmeerVz = afmeerVz;
		this.owner = owner;
		this.use = use;
		this.harbour = harbour;
		this.anchorage = anchorage;
		this.nauticalState = nauticalState;
		this.shoreNo = shoreNo;
		this.primaryFunction = primaryFunction;
		this.type = type;
	}
}
