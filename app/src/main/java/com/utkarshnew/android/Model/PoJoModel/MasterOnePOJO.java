
package com.utkarshnew.android.Model.PoJoModel;

import java.io.Serializable;

public class MasterOnePOJO implements Serializable {

    private String id;
    private String layer;
    private String type;
    private String tile_id;
    private String revert_api;
    private String course_ids;

    public MasterOnePOJO(String id, String layer, String type, String tile_id, String revert_api, String course_ids) {
        this.id = id;
        this.layer = layer;
        this.type = type;
        this.tile_id = tile_id;
        this.revert_api = revert_api;
        this.course_ids = course_ids;
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

    public String getCourse_ids() {
        return course_ids;
    }

    public void setCourse_ids(String course_ids) {
        this.course_ids = course_ids;
    }
}
