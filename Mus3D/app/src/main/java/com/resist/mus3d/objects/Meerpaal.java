package com.resist.mus3d.objects;


import com.resist.mus3d.R;
import com.resist.mus3d.map.Marker;

import java.util.Date;

public class Meerpaal extends Common {
    public static final int TYPE = 4;
    private static final int COLOR = 0x70F202FA;

	public Meerpaal(int objectid, String createdBy, Date createdAt, String editedBy, Date editedAt, String featureId) {
		super(objectid, createdBy, createdAt, editedBy, editedAt, featureId);
	}

    public Meerpaal(int objectid, String createdBy, Date createdAt, String editedBy, Date editedAt, String featureId, String facilityId, String facilitySecId, String regionId, String harbourId) {
        super(objectid, createdBy, createdAt, editedBy, editedAt, featureId, facilityId, facilitySecId, regionId, harbourId);
    }

    public int getType() {
        return TYPE;
    }

    @Override
    public int getDrawable() {
        return R.drawable.ic_meerpaal;
    }

    @Override
    public String getUsefulDescription() {
        return String.format("%s (%d)", getFeatureId(), getObjectid());
    }

    @Override
    public int getTypeName() {
        return R.string.object_meerpaal;
    }

    @Override
    public int getColor() {
        return COLOR;
    }
}
