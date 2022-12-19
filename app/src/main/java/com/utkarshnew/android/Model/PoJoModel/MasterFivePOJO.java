
package com.utkarshnew.android.Model.PoJoModel;

import java.io.Serializable;

public class MasterFivePOJO implements Serializable {

    private String id;
    private String layer;
    private String subject_id;
    private String topic_id;
    private String type;
    private String tile_id;
    private String revert_api;
    private String test_filter;
    private int page;

    public MasterFivePOJO(String id, String layer, String subject_id, String topic_id, String type, String tile_id, String revert_api, String test_filter, int page) {
        this.id = id;
        this.layer = layer;
        this.subject_id = subject_id;
        this.topic_id = topic_id;
        this.type = type;
        this.tile_id = tile_id;
        this.revert_api = revert_api;
        this.test_filter = test_filter;
        this.page = page;
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

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
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

    public String getTest_filter() {
        return test_filter;
    }

    public void setTest_filter(String test_filter) {
        this.test_filter = test_filter;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
