package com.utkarshnew.android.table;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.utkarshnew.android.courses.modal.NotesType;
import com.utkarshnew.android.TypeConverter.NotesConverter;

import java.util.List;

@Entity(tableName = "CourseDetailTable")
public class CourseDetailTable {

    @PrimaryKey(autoGenerate = true)
    private int autoid;

    @ColumnInfo(name = "coursetitle")
    private String course_title;

    @ColumnInfo(name = "couseid")
    private String course_id;

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    @ColumnInfo(name = "tax")
    private String tax;


    public String getIs_purchased() {
        return is_purchased;
    }

    public void setIs_purchased(String is_purchased) {
        this.is_purchased = is_purchased;
    }

    @ColumnInfo(name = "is_purchased")
    private String is_purchased;

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    @ColumnInfo(name = "validity")
    private String validity;

    public String getCourse_sp() {
        return course_sp;
    }

    public void setCourse_sp(String course_sp) {
        this.course_sp = course_sp;
    }

    @ColumnInfo(name = "course_sp")
    private String course_sp;

    public String getAuthor_title() {
        return author_title;
    }

    public void setAuthor_title(String author_title) {
        this.author_title = author_title;
    }

    @ColumnInfo(name = "author_title")
    private String author_title;

    public String getView_type() {
        return view_type;
    }

    public void setView_type(String view_type) {
        this.view_type = view_type;
    }

    @ColumnInfo(name = "view_type")
    private String view_type;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @ColumnInfo(name = "type")
    private String type;

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    @ColumnInfo(name = "mrp")
    private String mrp;

    public String getDesc_header_image() {
        return desc_header_image;
    }

    public void setDesc_header_image(String desc_header_image) {
        this.desc_header_image = desc_header_image;
    }

    @ColumnInfo(name = "desc_header_image")
    private String desc_header_image;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @ColumnInfo(name = "userid")
    private String user_id;

    @ColumnInfo(name = "tileid")
    private String tile_id;

    @ColumnInfo(name = "tiletitile")
    private String tile_title;

    @ColumnInfo(name = "tilerevert")
    private String tile_revert;

    public int getAutoid() {
        return autoid;
    }

    public void setAutoid(int autoid) {
        this.autoid = autoid;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getCourse_title() {
        return course_title;
    }

    public void setCourse_title(String course_title) {
        this.course_title = course_title;
    }

    public String getTile_id() {
        return tile_id;
    }

    public void setTile_id(String tile_id) {
        this.tile_id = tile_id;
    }

    public String getTile_title() {
        return tile_title;
    }

    public void setTile_title(String tile_title) {
        this.tile_title = tile_title;
    }

    public String getTile_revert() {
        return tile_revert;
    }

    public void setTile_revert(String tile_revert) {
        this.tile_revert = tile_revert;
    }

    public String getTile_meta() {
        return tile_meta;
    }

    public void setTile_meta(String tile_meta) {
        this.tile_meta = tile_meta;
    }

    @ColumnInfo(name = "tilemeta")
    private String tile_meta;


    public List<NotesType> getNotes_type() {
        return notes_type;
    }

    public void setNotes_type(List<NotesType> notes_type) {
        this.notes_type = notes_type;
    }

    @TypeConverters(NotesConverter.class)
    @ColumnInfo(name = "notes_type")
    private List<NotesType> notes_type;




}