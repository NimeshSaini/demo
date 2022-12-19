package com.utkarshnew.android.Dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;


import com.utkarshnew.android.table.CourseDataTable;

import java.util.List;

@Dao
public interface CourseDataDao {

    @Insert
    long addCoursedata(CourseDataTable coursedetail);

    @Query("SELECT EXISTS(SELECT * FROM CourseDataTable WHERE mainid = :mainid AND  courseid = :courseid AND userid = :userid AND type_id = :type_id)")
    boolean isRecordExistsUserId(String mainid, String userid, String courseid, String type_id);

    @Query("SELECT * FROM CourseDataTable  WHERE mainid = :main_id AND userid =:userid AND type_id=:category ORDER BY autoid")
    List<CourseDataTable> getcoursedatawithfilter(String main_id, String userid, String category);

    @Query("SELECT * FROM CourseDataTable  WHERE mainid = :main_id AND userid =:userid GROUP BY courseid ORDER BY autoid")
    List<CourseDataTable> getcoursedata(String main_id, String userid);


    @Query("DELETE FROM CourseDataTable")
    void deletedata();


    @RawQuery
    List<CourseDataTable> getcoursedatafilterforpaid2(SupportSQLiteQuery query);


}
