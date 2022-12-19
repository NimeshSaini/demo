package com.utkarshnew.android.table;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "TestTable")
public class TestTable {

    public int getAutoid() {
        return autoid;
    }

    public void setAutoid(int autoid) {
        this.autoid = autoid;
    }


    @PrimaryKey(autoGenerate = true)
    private int autoid;
    @ColumnInfo(name = "test_id")
    private String test_id;

    @ColumnInfo(name = "user_id")
    private String user_id;

    @ColumnInfo(name = "course_id")
    private String course_id;

    @ColumnInfo(name = "status")
    private String status;


    public String getTest_id() {
        return test_id;
    }

    public void setTest_id(String test_id) {
        this.test_id = test_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
