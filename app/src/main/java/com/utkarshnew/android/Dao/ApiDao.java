package com.utkarshnew.android.Dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.utkarshnew.android.table.APITABLE;


@Dao
public interface ApiDao {

    @Insert
    long addUser(APITABLE apitable);

    @Delete
    int deleteUser(APITABLE apitable);

    @Update
    int updateUser(APITABLE apitable);


    @Query("DELETE FROM APITABLE")
    void deletedata();


    @Query("UPDATE APITABLE  SET timestamp =:timestamp ,interval=:interval,cdtimestamp=:cdtimestamp   WHERE Apicode = :apicode  AND user_id =:userid")
    void update_api_version(String apicode, String userid, String timestamp, String interval, String cdtimestamp);


    @Query("SELECT * FROM APITABLE WHERE user_id = :userid AND Apicode =:apicode ")
    boolean is_api_code_exits(String userid, String apicode);


    @Query("SELECT * FROM APITABLE  WHERE Apicode = :apicode AND user_id =:userid")
    APITABLE getapidetail(String apicode, String userid);

    @Query("UPDATE APITABLE SET version =:version where  Apicode=:apicode AND user_id=:userid")
    int updateversion(String version, String userid, String apicode);

}

