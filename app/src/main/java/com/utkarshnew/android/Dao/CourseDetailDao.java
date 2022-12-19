package com.utkarshnew.android.Dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.utkarshnew.android.table.CourseDetailTable;

import java.util.List;

@Dao
public interface CourseDetailDao {

    @Insert
    long addCoursedetail(CourseDetailTable coursedetail);

    @Query("SELECT EXISTS(SELECT * FROM CourseDetailTable WHERE couseid LIKE '%' || :course_id AND userid = :userid)")
    boolean isRecordExistsUserId(String userid, String course_id);

    @Query("SELECT * FROM CourseDetailTable  WHERE couseid = :courseid AND userid =:userid")
    List<CourseDetailTable> getcoursedetail(String courseid, String userid);

    @Query("Delete  FROM CourseDetailTable  WHERE couseid LIKE '%' || :courseid AND userid =:userid")
    int deletecoursedetail(String courseid, String userid);

    @Query("DELETE FROM CourseDetailTable")
    void deletedata();
}
