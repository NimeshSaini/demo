package com.utkarshnew.android.table;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.utkarshnew.android.TypeConverter.FilterConverter;

import java.util.List;

@Entity(tableName = "MasteAllCatTable")
public class MasteAllCatTable {

    public int getAuto_id() {
        return auto_id;
    }

    public void setAuto_id(int auto_id) {
        this.auto_id = auto_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    @PrimaryKey(autoGenerate = true)
    private int auto_id;

    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "parent_id")
    private String parent_id;

    public String getMaster_type() {
        return master_type;
    }

    public void setMaster_type(String master_type) {
        this.master_type = master_type;
    }

    @ColumnInfo(name = "master_type")
    private String master_type;

    @ColumnInfo(name = "user_id")
    private String user_id;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


    public List<Subjectfilter> getFilters() {
        return filters;
    }

    public void setFilters(List<Subjectfilter> filters) {
        this.filters = filters;
    }

    @TypeConverters(FilterConverter.class)
    @ColumnInfo(name = "filters")
    private List<Subjectfilter> filters;


}
