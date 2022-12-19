
package com.utkarshnew.android.Model.PoJoModel;

import java.io.Serializable;

public class RequestTwoPOJO implements Serializable {

    private String name;
    private String course_id;
    private String tile_id;

    public RequestTwoPOJO(String name, String course_id, String tile_id) {
        this.name = name;
        this.course_id = course_id;
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

    public String getTile_id() {
        return tile_id;
    }

    public void setTile_id(String tile_id) {
        this.tile_id = tile_id;
    }
}
