
package com.utkarshnew.android.Model.PoJoModel;

import java.io.Serializable;

public class MasterTenPOJO implements Serializable {

    private String id;
    private String sub_id;
    private String unit_id;
    private String chapter_id;
    private String topic_id;
    private String sub_topic_id;
    private String layer;
    private String type;
    private String tile_id;
    private String revert_api;

    public MasterTenPOJO(String id, String sub_id, String unit_id, String chapter_id, String topic_id, String sub_topic_id, String layer, String type, String tile_id, String revert_api) {
        this.id = id;
        this.sub_id = sub_id;
        this.unit_id = unit_id;
        this.chapter_id = chapter_id;
        this.topic_id = topic_id;
        this.sub_topic_id = sub_topic_id;
        this.layer = layer;
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

    public String getSub_id() {
        return sub_id;
    }

    public void setSub_id(String sub_id) {
        this.sub_id = sub_id;
    }

    public String getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(String unit_id) {
        this.unit_id = unit_id;
    }

    public String getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public String getSub_topic_id() {
        return sub_topic_id;
    }

    public void setSub_topic_id(String sub_topic_id) {
        this.sub_topic_id = sub_topic_id;
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
}
