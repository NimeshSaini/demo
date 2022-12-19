package com.utkarshnew.android.Dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.utkarshnew.android.table.VideoTable;

@Dao
public interface VideoDao {

    @Insert
    long addUser(VideoTable videoTable);

    @Delete
    int deleteUser(VideoTable videoTable);

    @Update
    int updateUser(VideoTable audioTable);


    @Query("SELECT * FROM VideoTable  WHERE video_id = :videoid AND user_id =:userid")
    VideoTable getuser(String videoid, String userid);


    @Query("DELETE FROM VideoTable")
    void deletedata();


    @Query("SELECT EXISTS(SELECT * FROM VideoTable WHERE video_id = :video_id   AND user_id = :user_id)")
    boolean isvideo_exit(String video_id, String user_id);


    @Query("UPDATE videotable SET   video_currentpos =:getCurrentPosition   WHERE video_id = :videoid  AND user_id =:userid")
    void update_video_currentpos(String videoid, String userid, Long getCurrentPosition);


    @Query("UPDATE videotable SET   video_currentpos =:getCurrentPosition   WHERE video_id = :videoid  AND user_id =:userid")
    void update_videotime(String videoid, String userid, Long getCurrentPosition);


}
