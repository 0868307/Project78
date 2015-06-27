package com.resist.mus3d.objects;


import com.resist.mus3d.R;

import java.util.Date;

public class Afmeerboei extends Common {
    public static final int TYPE = 0;
    private static final int COLOR = 0x70FF0000;

    public Afmeerboei(int objectid, String createdBy, Date createdAt, String editedBy, Date editedAt, String featureId) {
        super(objectid, createdBy, createdAt, editedBy, editedAt, featureId);
    }

    public Afmeerboei(int objectid, String createdBy, Date createdAt, String editedBy, Date editedAt, String featureId, String facilityId, String facilitySecId, String regionId, String harbourId) {
        super(objectid, createdBy, createdAt, editedBy, editedAt, featureId, facilityId, facilitySecId, regionId, harbourId);
    }

    public int getType() {
        return TYPE;
    }

    @Override
    public int getDrawable() {
        return R.drawable.ic_afmeerboei;
    }

    @Override
    public int getTypeName() {
        return R.string.object_afmeerboei;
    }

    @Override
    public int getColor() {
        return COLOR;
    }
}
