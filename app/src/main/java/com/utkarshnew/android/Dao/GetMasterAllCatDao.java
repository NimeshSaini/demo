package com.utkarshnew.android.Dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.utkarshnew.android.table.MasteAllCatTable;

import java.util.List;


@Dao
public interface GetMasterAllCatDao {

    @Query("DELETE FROM MasteAllCatTable")
    void deletedata();

    @Insert
    long addUser(MasteAllCatTable getMasterData_allcat);

    @Delete
    int deleteUser(MasteAllCatTable getMasterData_allcat);

    @Update
    int updateUser(MasteAllCatTable getMasterData_allcat);

    @Query("SELECT * FROM MasteAllCatTable")
    List<MasteAllCatTable> getAllUser();

    @Query("SELECT * FROM MasteAllCatTable where user_id IN (:userid)")
    List<MasteAllCatTable> getmaster_allcat(String userid);



    @Query("SELECT * FROM MasteAllCatTable where  parent_id=:parentid")
    List<MasteAllCatTable> getmaster_allcat_parentid(String parentid);


    @Query("SELECT * FROM MasteAllCatTable where master_type IN (:userid)")
    List<MasteAllCatTable> getmaster_allcat_viaprefence(String userid);



    @Query("SELECT EXISTS(SELECT * FROM MasteAllCatTable WHERE user_id = :userid)")
    boolean isRecordExistsUserId(String userid);

    @Query("SELECT filters FROM MasteAllCatTable where id IN (:id)")
    String getfilteddata(String id);

}
