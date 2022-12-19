package com.utkarshnew.android.table;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "HtmlTbale")
public class HtmlTbale {

    public int getAutoid() {
        return autoid;
    }

    public void setAutoid(int autoid) {
        this.autoid = autoid;
    }


    @PrimaryKey(autoGenerate = true)
    private int autoid;

    public String getConcept_id() {
        return concept_id;
    }

    public void setConcept_id(String concept_id) {
        this.concept_id = concept_id;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


    public String getHighight() {
        return highight;
    }

    public void setHighight(String highight) {
        this.highight = highight;
    }


    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getTile_id() {
        return tile_id;
    }

    public void setTile_id(String tile_id) {
        this.tile_id = tile_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    @ColumnInfo(name = "concept_id")
    private String concept_id;
    @ColumnInfo(name = "tile_id")
    private String tile_id;
    @ColumnInfo(name = "user_id")
    private String user_id;

    @ColumnInfo(name = "course_id")
    private String course_id;

    @ColumnInfo(name = "highight")
    private String highight;


    @ColumnInfo(name = "html")
    private String html;


    public String getValid_to() {
        return valid_to;
    }

    public void setValid_to(String valid_to) {
        this.valid_to = valid_to;
    }

    @ColumnInfo(name = "valid_to")
    private String valid_to;


}
