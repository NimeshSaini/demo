package com.utkarshnew.android.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.utkarshnew.android.table.UserWiseCourseTable;

@Dao
public interface UserWiseCourseDao {

    @Insert
    long addUser(UserWiseCourseTable apitable);

    @Delete
    int deleteUser(UserWiseCourseTable apitable);

    @Update
    int updateUser(UserWiseCourseTable apitable);

    @Query("DELETE FROM UserWiseCourseTable")
    void deletedata();


    @Query("UPDATE UserWiseCourseTable  SET  version =:version  WHERE meta_id = :apicode  AND userid =:userid")
    void update_api_version(String apicode, String userid, String version);


    @Query("SELECT * FROM UserWiseCourseTable WHERE userid = :userid AND meta_id =:apicode ")
    boolean is_api_code_exits(String userid, String apicode);


    @Query("SELECT * FROM UserWiseCourseTable  WHERE meta_id = :apicode AND userid =:userid")
    UserWiseCourseTable getapidetail(String apicode, String userid);

    @Query("UPDATE UserWiseCourseTable SET version =:version where  meta_id=:apicode AND userid=:userid")
    int updateversion(String version, String userid, String apicode);
}
