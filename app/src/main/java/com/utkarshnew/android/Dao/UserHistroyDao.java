package com.utkarshnew.android.Dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.utkarshnew.android.table.UserHistroyTable;

import java.util.List;

@Dao
public interface UserHistroyDao {

    @Insert
    long addUser(UserHistroyTable userHistroyTable);

    @Delete
    int deleteUser(UserHistroyTable userHistroyTable);

    @Update
    int updateUser(UserHistroyTable userHistroyTable);


    @Query("DELETE FROM UserHistroyTable")
    void deletedata();

    @Query("SELECT * FROM UserHistroyTable  WHERE user_id = :user_id  ORDER BY `current_time` DESC   LIMIT 1000 OFFSET 0")
    List<UserHistroyTable> getallhistory(String user_id);



    @Query("SELECT * FROM UserHistroyTable  WHERE user_id = :user_id AND video_id =:videoid")
    UserHistroyTable user_hisorydao(String user_id ,String videoid);




    @Query("DELETE FROM UserHistroyTable WHERE `current_time` < (SELECT `current_time` FROM UserHistroyTable ORDER BY `current_time` DESC LIMIT 1 OFFSET 1000)")
    void delete();


    @Query("SELECT * FROM UserHistroyTable  WHERE user_id = :user_id  AND video_id =:videoid")
    UserHistroyTable user_history(String user_id, String videoid);


    @Query("Delete from UserHistroyTable where course_id LIKE :courseid || '%'  AND user_id =:Userid")
    void delete(String courseid, String Userid);


    @Query("Delete from UserHistroyTable where course_id   LIKE '%' || :courseid     AND user_id =:Userid")
    void delete_right(String courseid, String Userid);


    @Query("Delete from UserHistroyTable where user_id =:Userid")
    void delete_via_user(String Userid);


    @Query("SELECT  DISTINCT course_id FROM UserHistroyTable where course_id LIKE '%' || :courseid || '%'  AND user_id =:user_id")
    List<String> getlikehistiry(String user_id, String courseid);


    @Query("Delete from UserHistroyTable where course_id = :course_id AND video_id =:videoid AND user_id =:userid")
    void delete_viavideo_id(String videoid, String course_id, String userid);


    @Query("Delete from UserHistroyTable where user_id = :userid AND video_id =:videoid")
    void deletevideo_id(String videoid, String userid);


    @Query("SELECT EXISTS(SELECT * FROM UserHistroyTable WHERE course_id = :course_id And user_id=:userid)")
    boolean isRecordExistsUserId(String course_id, String userid);


    @Query("SELECT  DISTINCT course_id  FROM UserHistroyTable  where  user_id=:userid")
    List<String> courseids(String userid);


}
