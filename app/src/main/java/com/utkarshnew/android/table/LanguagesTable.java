package com.utkarshnew.android.table;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "LanguagesTable")

public class LanguagesTable {


    public int getAutoid() {
        return autoid;
    }

    public void setAutoid(int autoid) {
        this.autoid = autoid;
    }

    @PrimaryKey(autoGenerate = true)
    private int autoid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ColumnInfo(name = "id")
    private String id;

    public String getLanguage() {

        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @ColumnInfo(name = "language")
    private String language;

}
