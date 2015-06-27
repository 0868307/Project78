package com.resist.mus3d.objects;

import com.resist.mus3d.Mus3D;
import com.resist.mus3d.R;
import com.resist.mus3d.database.Aanlegplaatsen;
import com.resist.mus3d.map.Marker;

import java.util.Date;

public class Ligplaats extends Object {
    public static final int TYPE = 3;
    private static final int COLOR = 0x7000FF00;
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
    private Haven haven;
    private String anchorage;
    private String nauticalState;
    private String shoreNo;
    private String primaryFunction;
    private String type;

    public Ligplaats(int objectid, String createdBy, Date createdAt, String editedBy, Date editedAt, String featureId) {
        super(objectid, createdBy, createdAt, editedBy, editedAt, featureId);
    }

    public Ligplaats(int objectid, String createdBy, Date createdAt, String editedBy, Date editedAt, String featureId, String complexId, String globalId, String kenmerkZe, String lxmeText, Date occupiedFrom, String occRecNo, Date occupiedTill, String vacReason, String xmeText, String afmeerVz, String owner, String use, Haven haven, String anchorage, String nauticalState, String shoreNo, String primaryFunction, String type) {
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
        this.haven = haven;
        this.anchorage = anchorage;
        this.nauticalState = nauticalState;
        this.shoreNo = shoreNo;
        this.primaryFunction = primaryFunction;
        this.type = type;
        super.load();
    }

    public String getComplexId() {
        if (!isComplete()) {
            load();
        }
        return complexId;
    }

    public void setComplexId(String complexId) {
        if (!isComplete()) {
            load();
        }
        this.complexId = complexId;
    }

    public String getGlobalId() {
        if (!isComplete()) {
            load();
        }
        return globalId;
    }

    public void setGlobalId(String globalId) {
        if (!isComplete()) {
            load();
        }
        this.globalId = globalId;
    }

    public String getKenmerkZe() {
        if (!isComplete()) {
            load();
        }
        return kenmerkZe;
    }

    public void setKenmerkZe(String kenmerkZe) {
        if (!isComplete()) {
            load();
        }
        this.kenmerkZe = kenmerkZe;
    }

    public String getLxmeText() {
        if (!isComplete()) {
            load();
        }
        return lxmeText;
    }

    public void setLxmeText(String lxmeText) {
        if (!isComplete()) {
            load();
        }
        this.lxmeText = lxmeText;
    }

    public Date getOccupiedFrom() {
        if (!isComplete()) {
            load();
        }
        return occupiedFrom;
    }

    public void setOccupiedFrom(Date occupiedFrom) {
        if (!isComplete()) {
            load();
        }
        this.occupiedFrom = occupiedFrom;
    }

    public String getOccRecNo() {
        if (!isComplete()) {
            load();
        }
        return occRecNo;
    }

    public void setOccRecNo(String occRecNo) {
        if (!isComplete()) {
            load();
        }
        this.occRecNo = occRecNo;
    }

    public Date getOccupiedTill() {
        if (!isComplete()) {
            load();
        }
        return occupiedTill;
    }

    public void setOccupiedTill(Date occupiedTill) {
        if (!isComplete()) {
            load();
        }
        this.occupiedTill = occupiedTill;
    }

    public String getVacReason() {
        if (!isComplete()) {
            load();
        }
        return vacReason;
    }

    public void setVacReason(String vacReason) {
        if (!isComplete()) {
            load();
        }
        this.vacReason = vacReason;
    }

    public String getXmeText() {
        if (!isComplete()) {
            load();
        }
        return xmeText;
    }

    public void setXmeText(String xmeText) {
        if (!isComplete()) {
            load();
        }
        this.xmeText = xmeText;
    }

    public String getAfmeerVz() {
        if (!isComplete()) {
            load();
        }
        return afmeerVz;
    }

    public void setAfmeerVz(String afmeerVz) {
        if (!isComplete()) {
            load();
        }
        this.afmeerVz = afmeerVz;
    }

    public String getOwner() {
        if (!isComplete()) {
            load();
        }
        return owner;
    }

    public void setOwner(String owner) {
        if (!isComplete()) {
            load();
        }
        this.owner = owner;
    }

    public String getUse() {
        if (!isComplete()) {
            load();
        }
        return use;
    }

    public void setUse(String use) {
        if (!isComplete()) {
            load();
        }
        this.use = use;
    }

    public Haven getHaven() {
        if (!isComplete()) {
            load();
        }
        return haven;
    }

    public void setHaven(Haven haven) {
        if (!isComplete()) {
            load();
        }
        this.haven = haven;
    }

    public String getAnchorage() {
        if (!isComplete()) {
            load();
        }
        return anchorage;
    }

    public void setAnchorage(String anchorage) {
        if (!isComplete()) {
            load();
        }
        this.anchorage = anchorage;
    }

    public String getNauticalState() {
        if (!isComplete()) {
            load();
        }
        return nauticalState;
    }

    public void setNauticalState(String nauticalState) {
        if (!isComplete()) {
            load();
        }
        this.nauticalState = nauticalState;
    }

    public String getShoreNo() {
        if (!isComplete()) {
            load();
        }
        return shoreNo;
    }

    public void setShoreNo(String shoreNo) {
        if (!isComplete()) {
            load();
        }
        this.shoreNo = shoreNo;
    }

    public String getPrimaryFunction() {
        if (!isComplete()) {
            load();
        }
        return primaryFunction;
    }

    public void setPrimaryFunction(String primaryFunction) {
        if (!isComplete()) {
            load();
        }
        this.primaryFunction = primaryFunction;
    }

    public int getType() {
        return TYPE;
    }

    @Override
    protected void load() {
        super.load();
        new Aanlegplaatsen(Mus3D.getDatabase().getDatabase()).loadObject(this);
    }

    @Override
    public int getDrawable() {
        return R.drawable.ic_aanlegplaats;
    }

    @Override
    public String getUsefulDescription() {
        return getXmeText();
    }

    @Override
    public int getTypeName() {
        return R.string.object_ligplaats;
    }

    @Override
    public void setDialogText(Marker.DialogContents dialog) {
        super.setDialogText(dialog);
        dialog.append("XME Text: ", getXmeText());
        dialog.append("KenmerkZe: ", getKenmerkZe());
        //dialog.append("Vac Reden: ", getVacReason());
        dialog.append("Afmeer Vz: ", getAfmeerVz());
    }

    @Override
    public int getColor() {
        return COLOR;
    }
}
