package com.utkarshnew.android.Dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.utkarshnew.android.table.TestTable;

@Dao
public interface TestDao {

    @Insert
    long addUser(TestTable audioTable);

    @Delete
    int deleteUser(TestTable audioTable);

    @Update
    int updateUser(TestTable audioTable);


    @Query("SELECT * FROM TestTable WHERE  test_id = :test_id AND user_id=:userid")
    TestTable test_data(String test_id, String userid);


    @Query("DELETE  FROM TestTable WHERE user_id = :userid   AND test_id = :test_id")
    int delete_test_data(String userid, String test_id);


    @Query("DELETE FROM MasteAllCatTable")
    void deletedata();


    @Query("SELECT EXISTS(SELECT * FROM TestTable WHERE test_id = :test_id AND user_id=:userid)")
    boolean is_test_exit(String test_id, String userid);


}
