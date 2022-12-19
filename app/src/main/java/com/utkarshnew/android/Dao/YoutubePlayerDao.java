package com.utkarshnew.android.Dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.utkarshnew.android.table.YoutubePlayerTable;

@Dao
public interface YoutubePlayerDao {

    @Insert
    long addVideo(YoutubePlayerTable youtubePlayerTable);
/*

    @Query("SELECT youtubetime FROM YoutubePlayerTable where youtubeid = :youtubeid AND userid = :userid")
    Long getyoutubedata(String userid,String youtubeid);

    @Query("SELECT * FROM YoutubePlayerTable WHERE youtubeid = :youtubeid ")
    boolean isUserExist(String youtubeid);

    @Query("UPDATE YoutubePlayerTable SET youtubetime = :youtubetime  WHERE youtubeid = :youtubeid")
    int updateTime(Long youtubetime,String youtubeid);
*/


    @Query("SELECT *  FROM YoutubePlayerTable where youtubeid = :youtubeid AND userid = :userid")
    YoutubePlayerTable getdata(String userid, String youtubeid);


    @Query("DELETE FROM YoutubePlayerTable")
    void deletedata();


    @Query("SELECT youtubetime FROM YoutubePlayerTable where videoid = :videoid AND userid = :userid AND isaudio =:isaudio")
    long getyoutubedata(String userid, String videoid, String isaudio);


    @Query("SELECT EXISTS(SELECT * FROM YoutubePlayerTable WHERE  videoid = :videoid AND userid =:userid AND isaudio =:isaudio)")
    boolean isUserExist(String videoid, String userid, String isaudio);


    @Query("UPDATE YoutubePlayerTable SET youtubetime = :youtubetime  WHERE videoid = :videoid And userid =:userid AND isaudio =:isaudio")
    int updateTime(Long youtubetime, String videoid, String userid, String isaudio);


}
