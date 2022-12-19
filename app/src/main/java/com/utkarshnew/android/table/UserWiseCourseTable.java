package com.utkarshnew.android.table;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "UserWiseCourseTable")
public class UserWiseCourseTable {
    @PrimaryKey(autoGenerate = true)
    private int auto_id;

    @ColumnInfo(name = "meta_id")
    private String meta_id;

    @ColumnInfo(name = "code")
    private String code;

    @ColumnInfo(name = "version")
    private String version;

    public int getAuto_id() {
        return auto_id;
    }

    public void setAuto_id(int auto_id) {
        this.auto_id = auto_id;
    }

    public String getMeta_id() {
        return meta_id;
    }

    public void setMeta_id(String meta_id) {
        this.meta_id = meta_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    @ColumnInfo(name = "exp")
    private String exp;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @ColumnInfo(name = "userid")
    private String userid;

}
