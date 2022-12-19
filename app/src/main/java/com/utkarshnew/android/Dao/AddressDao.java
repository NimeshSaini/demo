package com.utkarshnew.android.Dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.utkarshnew.android.table.AddressTable;

import java.util.List;

@Dao
public interface AddressDao {

    @Insert
    long addAddress(AddressTable addressTable);

    @Delete
    int deleteUser(AddressTable addressTable);

    @Update
    int updateUser(AddressTable addressTable);


    @Query("SELECT EXISTS(SELECT * FROM AddressTable WHERE address_id = :address_id )")
    boolean is_address_exit(String address_id);


    @Query("Delete from AddressTable where address_id = :addres_id")
    void delete_address(String addres_id);



    @Query("SELECT * FROM AddressTable  WHERE address_id = :address_id ")
    AddressTable getAddress(String address_id);


    @Query("DELETE FROM AddressTable")
    void deletedata();

    @Query("SELECT * FROM AddressTable")
    List<AddressTable> getAllAddress();


    @Query("UPDATE AddressTable  SET  address_one =:address_one , address_two =:address_two,state =:state,city =:city,name =:name,pincode =:pincode,phone =:phone,delivery_price =:deleivery_price ,state_id =:state_id ,city_id =:city_id WHERE address_id = :address_id")
    void update_address(String address_id, String address_one, String address_two,String state,String city,String name,String pincode,String phone,String deleivery_price,String state_id,String city_id);



}
