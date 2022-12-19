package com.utkarshnew.android.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.utkarshnew.android.table.HomeApiStatusTable;


@Dao
public interface HomeApiStatusDao {

    @Insert
    long addCoursedata(HomeApiStatusTable homeApiStatusTable);

    @Query("UPDATE HomeApiStatusTable SET status=:status , page=:page WHERE mainid = :mainid AND  userid = :userid")
    int updaterecord(String page, String status, String mainid, String userid);

    @Query("SELECT EXISTS(SELECT * FROM HomeApiStatusTable WHERE mainid = :mainid AND userid = :userid)")
    boolean isRecordExistsUserId(String userid, String mainid);

    @Query("SELECT * FROM HomeApiStatusTable  WHERE mainid = :mainid AND userid =:userid")
    HomeApiStatusTable getcoursedetail(String mainid, String userid);

    @Query("DELETE FROM HomeApiStatusTable")
    void deletedata();
}
