package com.utkarshnew.android.Dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.utkarshnew.android.table.PigibagTable;


@Dao
public interface PigiBag {
    @Insert
    long addApiedata(PigibagTable pigibag);

    @Query("UPDATE PigibagTable SET cdtimestamp =:cdtimestamp  where  user_id=:userid")
    int updaterecord(String userid, String cdtimestamp);

    @Query("SELECT EXISTS(SELECT * FROM PigibagTable WHERE user_id = :userid)")
    boolean isRecordExistsUserId(String userid);

    @Query("SELECT * FROM PigibagTable  WHERE  user_id =:userid")
    PigibagTable getpigidetail(String userid);

    @Query("SELECT * FROM PigibagTable")
    PigibagTable getuserid();

    @Query("DELETE FROM PigibagTable")
    void deletedata();

    @Query("Delete  FROM PigibagTable  WHERE user_id =:userid")
    int deletepigibagdata(String userid);
}
