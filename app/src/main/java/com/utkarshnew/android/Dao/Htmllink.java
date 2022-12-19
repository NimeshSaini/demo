package com.utkarshnew.android.Dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.utkarshnew.android.table.HtmlTbale;

@Dao
public interface Htmllink {

    @Insert
    long addUser(HtmlTbale htmlTbale);

    @Delete
    int deleteUser(HtmlTbale htmlTbale);

    @Update
    int updateUser(HtmlTbale htmlTbale);

    @Query("SELECT EXISTS(SELECT * FROM HtmlTbale WHERE user_id = :user_id AND concept_id =:concept_id)")
    boolean is_concept_exit(String user_id, String concept_id);


    @Query("UPDATE htmltbale SET    highight=:highight  where  user_id =:userid  AND concept_id =:concept_id")
    void update_highlight(String highight, String userid, String concept_id);


    @Query("SELECT * FROM HtmlTbale  WHERE user_id =:userid AND concept_id=:concept_id")
    HtmlTbale getconcept(String userid, String concept_id);


    @Query("DELETE FROM HtmlTbale")
    void deletedata();

    @Query("Delete from HtmlTbale where concept_id = :videoid AND user_id=:userid")
    void delete_viaconceptid(String videoid, String userid);



}
