
package com.utkarshnew.android.Model.PoJoModel;

import java.io.Serializable;

public class RequestThreePOJO implements Serializable {

    private String name;
    private String course_id;
    private String type;
    private String tile_id;

    public RequestThreePOJO(String name, String course_id, String type, String tile_id) {
        this.name = name;
        this.course_id = course_id;
        this.type = type;
        this.tile_id = tile_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
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
}
