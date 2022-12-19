package com.utkarshnew.android.Dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.utkarshnew.android.table.CourseTypeMasterTable;

import java.util.List;


@Dao
public interface CourseTypeMasterDao {

    @Insert
    long addUser(CourseTypeMasterTable courseTypeMasterTable);

    @Delete
    int deleteUser(CourseTypeMasterTable courseTypeMasterTable);


    @Update
    int updateUser(CourseTypeMasterTable courseTypeMasterTable);


    @Query("SELECT * FROM CourseTypeMasterTable where user_id IN (:userid)")
    List<CourseTypeMasterTable> getcourse_typemaster(String userid);


    @Query("SELECT * FROM CourseTypeMasterTable")
    List<CourseTypeMasterTable> getAllUser();

    @Query("DELETE FROM CourseTypeMasterTable")
    void deletedata();
}
