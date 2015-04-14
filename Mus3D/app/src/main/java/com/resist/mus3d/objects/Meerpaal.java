package com.resist.mus3d.objects;


import java.util.Date;

public class Meerpaal extends Common {
    public static final int TYPE = 4;

    public Meerpaal(int objectid, String createdBy, Date createdAt, String editedBy, Date editedAt, String featureId, String facilityId, String facilitySecId, Harbour harbour, String regionId) {
        super(objectid, createdBy, createdAt, editedBy, editedAt, featureId, facilityId, facilitySecId, harbour, regionId);
    }

    public int getType() {
        return TYPE;
    }
}
