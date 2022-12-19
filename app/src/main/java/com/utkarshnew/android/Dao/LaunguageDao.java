package com.utkarshnew.android.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.utkarshnew.android.table.LanguagesTable;

import java.util.List;


@Dao
public interface LaunguageDao {

    @Insert
    long addLaunguage(LanguagesTable languagesTable);

    @Query("SELECT * FROM LanguagesTable")
    List<LanguagesTable> getLaunguagedetail();

    @Query("DELETE FROM LanguagesTable")
    void deletedata();

}
