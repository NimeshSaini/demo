package com.utkarshnew.android.Dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.utkarshnew.android.table.VideosDownload;

import java.util.List;

@Dao
public interface VideoDownload {

    @Insert
    long addUser(VideosDownload videosDownload);

    @Delete
    int deleteUser(VideosDownload videosDownload);

    @Update
    int updateUser(VideosDownload videosDownload);


    @Query("SELECT * FROM VideoDownload  WHERE video_id = :videoid AND is_complete =:complete")
    VideosDownload getuser(String videoid, String complete);

    @Query("SELECT * FROM VideoDownload  WHERE video_id = :videoid AND is_complete =:complete  AND user_id =:user_id")
    VideosDownload getvideo_byuserid(String videoid, String complete, String user_id);


    @Query("SELECT * FROM VideoDownload  WHERE video_id = :videoid   AND user_id =:user_id")
    VideosDownload getvideo_byuserid(String videoid, String user_id);


    @Query("SELECT * FROM VideoDownload  WHERE user_id = :user_id")
    List<VideosDownload> getalldownload_videos(String user_id);


    @Query("SELECT * FROM VideoDownload  WHERE course_id LIKE '%' || :courseid   AND user_id =:userid")
    List<VideosDownload> getcourse_expire(String courseid, String userid);


    @Query("SELECT * FROM VideoDownload  WHERE course_id LIKE :courseid || '%'   AND user_id =:userid")
    List<VideosDownload> getcourse_expire_left(String courseid, String userid);


    @Query("SELECT * FROM VideoDownload  WHERE course_id LIKE '%' || :courseid || '%'  AND user_id =:userid")
    List<VideosDownload> getallcourse_id(String courseid, String userid);

    @Query("UPDATE VideoDownload SET   mp4_download_url=:mp4ulr , videotime=:videotime , originalFileLengthString=:originalFileLengthString  , video_status=:status  , total =:total , lengthInMb =:lengthInMb , percentage =:percentage  , is_complete =:iscomplete WHERE video_id = :testid AND is_complete = :iscomplete")
    int update_videofilelenght(String testid, String originalFileLengthString, Long total, String lengthInMb, int percentage, String iscomplete, String status, String videotime, String mp4ulr);


    ////////////////////user userid_course_id_videoid////////////
    /*@Query("Delete from VideoDownload where video_id = :testid")
    void delete(String testid);
*/

    @Query("Delete from VideoDownload where video_id = :videoid AND user_id=:userid")
    void delete_viavideoid(String videoid, String userid);


    /*@Query("Delete from VideoDownload where video_id = :testid AND course_id=:course_id")
    void delete(String testid,String course_id);

*/
    @Query("Delete from VideoDownload where video_id = :videoid AND course_id=:course_id AND user_id=:userid")
    void delete(String videoid, String course_id, String userid);


    @Query("Delete from VideoDownload where video_id = :testid AND course_id =:course_id")
    void delete_course_id(String testid, String course_id);


    @Query("SELECT EXISTS(SELECT * FROM VideoDownload WHERE video_id = :video_id AND is_complete = :is_complete  AND user_id = :user_id)")
    boolean isRecordExistsUserId(String video_id, String is_complete, String user_id);


    @Query("SELECT EXISTS(SELECT * FROM VideoDownload WHERE video_id = :video_id AND is_complete = :is_complete  AND user_id = :user_id)")
    boolean isRecordexist_1(String video_id, String is_complete, String user_id);


    @Query("SELECT  DISTINCT course_id  FROM VideoDownload  where  user_id=:userid")
    List<String> courseids(String userid);


    @Query("SELECT  DISTINCT course_id FROM VideoDownload where course_id LIKE '%' || :courseid || '%'  AND user_id =:user_id")
    List<String> getlikehistiry(String user_id, String courseid);


    @Query("SELECT EXISTS(SELECT * FROM VideoDownload WHERE video_id = :video_id   AND user_id = :user_id)")
    boolean isvideo_exit(String video_id, String user_id);


    @Query("UPDATE VideoDownload SET  video_status =:status , percentage =:percentage  , is_complete =:isconpelte  WHERE video_id = :testid ")
    void update_videostatus(String testid, String isconpelte, String status, int percentage);


    @Query("UPDATE VideoDownload SET  video_status =:status , user_id =:user_id    WHERE video_id = :videoid ")
    void update_videostatus(String videoid, String status, String user_id);


    @Query("UPDATE VideoDownload SET   thumbnail_url =:thumb,jw_url =:jwurl , video_history=:video_history , video_name =:video_name WHERE video_id = :video_id AND  user_id =:user_id ")
    void updatevideo_name(String video_name, String video_id, String user_id, String jwurl, String thumb, String video_history);


    @Query("UPDATE VideoDownload SET    video_name =:video_name WHERE video_id = :video_id AND  user_id =:user_id ")
    void update_title(String video_name, String video_id, String user_id);


    @Query("UPDATE VideoDownload SET  video_status =:status , is_complete =:isconpelte  WHERE video_id = :videoid  AND user_id =:userid")
    void update_progress(String videoid, String isconpelte, String status, String userid);


    @Query("UPDATE VideoDownload SET   videoCurrentPosition =:getCurrentPosition ,  videotime = :video_time  WHERE video_id = :videoid    AND user_id =:userid")
    void update_videocurrenttime(String videoid, String userid, Long getCurrentPosition, String video_time);


    @Query("DELETE FROM VideoDownload")
    void deletedata();


    @Query("UPDATE VideoDownload SET   position =:getCurrentPosition   WHERE video_id = :videoid  AND user_id =:userid")
    void update_pos(String userid, String videoid, int getCurrentPosition);


    @Query("Delete from VideoDownload where user_id = :user_id")
    void delete_user_id(String user_id);


}
