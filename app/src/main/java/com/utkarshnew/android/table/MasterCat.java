package com.utkarshnew.android.table;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MasterCat")
public class MasterCat {

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


    @PrimaryKey(autoGenerate = true)
    private int auto_id;

    @ColumnInfo(name = "id")
    private String id;

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    @ColumnInfo(name = "cat")
    private String cat;


    @ColumnInfo(name = "user_id")
    private String user_id;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


}
