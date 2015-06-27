package com.resist.mus3d.objects;

import com.resist.mus3d.Mus3D;
import com.resist.mus3d.R;
import com.resist.mus3d.database.Bolders;
import com.resist.mus3d.map.Marker;

import java.util.Date;

public class Bolder extends Common {
    public static final int TYPE = 1;
    private static final int COLOR = 0x700000FF;
    private String description;
    private String material;
    private String anchor;
    private String type;
    private String partner;
    private String company;

    public Bolder(int objectid, String createdBy, Date createdAt, String editedBy, Date editedAt, String featureId) {
        super(objectid, createdBy, createdAt, editedBy, editedAt, featureId);
    }

    public Bolder(int objectid, String createdBy, Date createdAt, String editedBy, Date editedAt, String featureId, String facilityId, String facilitySecId, String regionId, String description, String material, String anchor, String type, String partner, String company, String harbourId) {
        super(objectid, createdBy, createdAt, editedBy, editedAt, featureId, facilityId, facilitySecId, regionId, harbourId);
        this.description = description;
        this.material = material;
        this.anchor = anchor;
        this.type = type;
        this.partner = partner;
        this.company = company;
        super.load();
    }

    public String getDescription() {
        if (!isComplete()) {
            load();
        }
        return description;
    }

    public void setDescription(String description) {
        if (!isComplete()) {
            load();
        }
        this.description = description;
    }

    public String getMaterial() {
        if (!isComplete()) {
            load();
        }
        return material;
    }

    public void setMaterial(String material) {
        if (!isComplete()) {
            load();
        }
        this.material = material;
    }

    public String getAnchor() {
        if (!isComplete()) {
            load();
        }
        return anchor;
    }

    public void setAnchor(String anchor) {
        if (!isComplete()) {
            load();
        }
        this.anchor = anchor;
    }

    public String getPartner() {
        if (!isComplete()) {
            load();
        }
        return partner;
    }

    public void setPartner(String partner) {
        if (!isComplete()) {
            load();
        }
        this.partner = partner;
    }

    public String getCompany() {
        if (!isComplete()) {
            load();
        }
        return company;
    }

    public void setCompany(String company) {
        if (!isComplete()) {
            load();
        }
        this.company = company;
    }

    public int getType() {
        return TYPE;
    }

    @Override
    protected void load() {
        super.load();
        new Bolders(Mus3D.getDatabase().getDatabase()).loadObject(this);
    }

    @Override
    public int getDrawable() {
        return R.drawable.ic_bolder;
    }

    @Override
    public String getUsefulDescription() {
        return getDescription();
    }

    @Override
    public int getTypeName() {
        return R.string.object_bolder;
    }

    @Override
    public void setDialogText(Marker.DialogContents dialog) {
        super.setDialogText(dialog);
        dialog.append("Beschrijving: ", getDescription());
        dialog.append("Materiaal: ", getMaterial());
        dialog.append("Bedrijf: ", getCompany());
    }

    @Override
    public int getColor() {
        return COLOR;
    }
}
