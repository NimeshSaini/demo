package com.utkarshnew.android.Dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.utkarshnew.android.table.AudioTable;

@Dao
public interface AudioDao {

    @Insert
    long addUser(AudioTable audioTable);

    @Delete
    int deleteUser(AudioTable audioTable);

    @Update
    int updateUser(AudioTable audioTable);


    @Query("SELECT * FROM AudioTable  WHERE video_id = :videoid AND user_id =:userid")
    AudioTable getuser(String videoid, String userid);


    @Query("DELETE FROM AudioTable")
    void deletedata();


    @Query("SELECT EXISTS(SELECT * FROM AudioTable WHERE video_id = :video_id   AND user_id = :user_id)")
    boolean isvideo_exit(String video_id, String user_id);


    @Query("UPDATE audiotable SET   audio_currentpos =:getCurrentPosition   WHERE video_id = :videoid  AND user_id =:userid")
    void update_audio_currentpos(String videoid, String userid, Long getCurrentPosition);


    @Query("UPDATE audiotable SET audio_url =:audio_url ,  audio_currentpos =:getCurrentPosition   WHERE video_id = :videoid  AND user_id =:userid")
    void update_videotime(String videoid, String userid, Long getCurrentPosition, String audio_url);


    @Query("UPDATE audiotable SET   audio_currentpos =:getCurrentPosition   WHERE video_id = :videoid  AND user_id =:userid")
    void update_videotime(String videoid, String userid, Long getCurrentPosition);


}
