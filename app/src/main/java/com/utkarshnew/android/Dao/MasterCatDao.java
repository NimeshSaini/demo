package com.utkarshnew.android.Dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.utkarshnew.android.table.MasterCat;

import java.util.List;


@Dao
public interface MasterCatDao {

    @Insert
    long addUser(MasterCat courseTypeMasterTable);

    @Delete
    int deleteUser(MasterCat courseTypeMasterTable);


    @Update
    int updateUser(MasterCat courseTypeMasterTable);


    @Query("SELECT * FROM MasterCat where user_id IN (:userid)")
    List<MasterCat> getmastercat(String userid);


    @Query("SELECT * FROM MasterCat where  id   IN (:master_prefence)")
    List<MasterCat> getmastercat_viaprefence(String master_prefence);



    @Query("SELECT * FROM MasterCat")
    List<MasterCat> getAllUser();


    @Query("DELETE FROM MasterCat")
    void deletedata();


}
