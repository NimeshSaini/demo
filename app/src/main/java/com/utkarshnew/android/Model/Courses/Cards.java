package com.utkarshnew.android.Model.Courses;

import java.io.Serializable;

public class Cards implements Serializable {
    private String id;

    private String tile_name;

    private String c_code;

    private String type;

    private String revert_api;

    private String count;

    public Cards(String type) {
        this.type = type;
    }

    public Cards(String id, String tile_name, String c_code, String type, String revert_api, String count) {
        this.id = id;
        this.tile_name = tile_name;
        this.c_code = c_code;
        this.type = type;
        this.revert_api = revert_api;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTile_name() {
        return tile_name;
    }

    public void setTile_name(String tile_name) {
        this.tile_name = tile_name;
    }

    public String getC_code() {
        return c_code;
    }

    public void setC_code(String c_code) {
        this.c_code = c_code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRevert_api() {
        return revert_api;
    }

    public void setRevert_api(String revert_api) {
        this.revert_api = revert_api;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
