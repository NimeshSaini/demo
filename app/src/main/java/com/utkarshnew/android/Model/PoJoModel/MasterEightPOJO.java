
package com.utkarshnew.android.Model.PoJoModel;

import java.io.Serializable;

public class MasterEightPOJO implements Serializable {

    private String id;
    private String layer;
    private String main_id;
    private String sub_id;
    private String test_type;
    private String type;
    private String tile_id;
    private String revert_api;
    private String test_filter;
    private int page;

    public MasterEightPOJO(String id, String layer, String main_id, String sub_id, String test_type, String type, String tile_id, String revert_api, String test_filter, int page) {
        this.id = id;
        this.layer = layer;
        this.main_id = main_id;
        this.sub_id = sub_id;
        this.test_type = test_type;
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

    public String getMain_id() {
        return main_id;
    }

    public void setMain_id(String main_id) {
        this.main_id = main_id;
    }

    public String getSub_id() {
        return sub_id;
    }

    public void setSub_id(String sub_id) {
        this.sub_id = sub_id;
    }

    public String getTest_type() {
        return test_type;
    }

    public void setTest_type(String test_type) {
        this.test_type = test_type;
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
