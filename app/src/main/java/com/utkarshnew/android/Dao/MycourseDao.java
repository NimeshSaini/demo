package com.utkarshnew.android.Dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.utkarshnew.android.Model.Courselist;
import com.utkarshnew.android.table.MycourseTable;

import java.util.List;


@Dao
public interface MycourseDao {

    @Insert
    long addUser(MycourseTable getProfileTable);

    @Delete
    int deleteUser(MycourseTable userTable);


    @Update
    int updateUser(MycourseTable userTable);


    @Query("SELECT * FROM mycoursetable")
    List<MycourseTable> getAllUser();


    @Query("SELECT  DISTINCT id  FROM mycoursetable  where  userid=:userid")
    List<String> getAllcourseid(String userid);


    @Query("SELECT * FROM mycoursetable where  mrp = :mrp AND batchtype=:batch_type")
    List<Courselist> getFreecourse(String mrp, String batch_type);


    @Query("SELECT * FROM mycoursetable where  batchtype=:batch_type")
    List<Courselist> getpaidcourse(String batch_type);


    @Query("SELECT EXISTS(SELECT * FROM MycourseTable WHERE userid = :userid AND batchtype = :batch_type  AND id = :id)")
    boolean isRecordExistsUserId(String userid, String batch_type, String id);

    @Query("SELECT EXISTS(SELECT * FROM MycourseTable WHERE userid = :userid)")
    boolean isRecordExists(String userid);

    @Query("SELECT * FROM mycoursetable where  userid = :userid AND batchtype = :batch_type  AND id = :id")
    Courselist getuser(String userid, String id, String batch_type);


    @Query("DELETE FROM mycoursetable")
    void deletedata();

    @Query("Delete from mycoursetable where id = :course_id AND txn_id = :txn_id")
    void delete(String course_id, String txn_id);


    @Query("UPDATE mycoursetable SET lastread=:lastread_timestamp WHERE id = :c_id AND  userid = :userid AND batchtype =:batch_type")
    int update_course_lastread(String lastread_timestamp, String c_id, String userid, String batch_type);


    @Query("UPDATE mycoursetable SET lastread=:lastread_timestamp WHERE id = :c_id AND  userid = :userid ")
    int update_course_lastread(String lastread_timestamp, String c_id, String userid);


}
