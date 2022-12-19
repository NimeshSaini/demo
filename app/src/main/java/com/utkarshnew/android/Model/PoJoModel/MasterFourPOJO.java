
package com.utkarshnew.android.Model.PoJoModel;

import java.io.Serializable;

public class MasterFourPOJO implements Serializable {

    private String id;
    private String layer;
    private String subject_id;
    private String type;
    private String tile_id;
    private String revert_api;

    public MasterFourPOJO(String id, String layer, String subject_id, String type, String tile_id, String revert_api) {
        this.id = id;
        this.layer = layer;
        this.subject_id = subject_id;
        this.type = type;
        this.tile_id = tile_id;
        this.revert_api = revert_api;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTile_id() {
        return tile_id;
    }

    public void setTile_id(String tile_id) {
        this.tile_id = tile_id;
    }

    public String getRevert_api() {
        return revert_api;
    }

    public void setRevert_api(String revert_api) {
        this.revert_api = revert_api;
    }
}
