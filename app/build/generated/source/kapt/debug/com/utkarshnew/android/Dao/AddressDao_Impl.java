package com.utkarshnew.android.Dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.utkarshnew.android.table.AddressTable;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class AddressDao_Impl implements AddressDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfAddressTable;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfAddressTable;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfAddressTable;

  private final SharedSQLiteStatement __preparedStmtOfDelete_address;

  private final SharedSQLiteStatement __preparedStmtOfDeletedata;

  private final SharedSQLiteStatement __preparedStmtOfUpdate_address;

  public AddressDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAddressTable = new EntityInsertionAdapter<AddressTable>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `AddressTable`(`uid`,`address_id`,`address_one`,`address_two`,`city`,`country`,`state`,`state_id`,`city_id`,`pincode`,`phone`,`delivery_price`,`name`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, AddressTable value) {
        stmt.bindLong(1, value.getUid());
        if (value.getAddress_id() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getAddress_id());
        }
        if (value.getAddress_one() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getAddress_one());
        }
        if (value.getAddress_two() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getAddress_two());
        }
        if (value.getCity() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getCity());
        }
        if (value.getCountry() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getCountry());
        }
        if (value.getState() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getState());
        }
        if (value.getState_id() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getState_id());
        }
        if (value.getCity_id() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getCity_id());
        }
        if (value.getPincode() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getPincode());
        }
        if (value.getPhone() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getPhone());
        }
        if (value.getDelivery_price() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getDelivery_price());
        }
        if (value.getName() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getName());
        }
      }
    };
    this.__deletionAdapterOfAddressTable = new EntityDeletionOrUpdateAdapter<AddressTable>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `AddressTable` WHERE `uid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, AddressTable value) {
        stmt.bindLong(1, value.getUid());
      }
    };
    this.__updateAdapterOfAddressTable = new EntityDeletionOrUpdateAdapter<AddressTable>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `AddressTable` SET `uid` = ?,`address_id` = ?,`address_one` = ?,`address_two` = ?,`city` = ?,`country` = ?,`state` = ?,`state_id` = ?,`city_id` = ?,`pincode` = ?,`phone` = ?,`delivery_price` = ?,`name` = ? WHERE `uid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, AddressTable value) {
        stmt.bindLong(1, value.getUid());
        if (value.getAddress_id() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getAddress_id());
        }
        if (value.getAddress_one() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getAddress_one());
        }
        if (value.getAddress_two() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getAddress_two());
        }
        if (value.getCity() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getCity());
        }
        if (value.getCountry() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getCountry());
        }
        if (value.getState() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getState());
        }
        if (value.getState_id() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getState_id());
        }
        if (value.getCity_id() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getCity_id());
        }
        if (value.getPincode() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getPincode());
        }
        if (value.getPhone() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getPhone());
        }
        if (value.getDelivery_price() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getDelivery_price());
        }
        if (value.getName() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getName());
        }
        stmt.bindLong(14, value.getUid());
      }
    };
    this.__preparedStmtOfDelete_address = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "Delete from AddressTable where address_id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeletedata = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM AddressTable";
        return _query;
      }
    };
    this.__preparedStmtOfUpdate_address = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE AddressTable  SET  address_one =? , address_two =?,state =?,city =?,name =?,pincode =?,phone =?,delivery_price =? ,state_id =? ,city_id =? WHERE address_id = ?";
        return _query;
      }
    };
  }

  @Override
  public long addAddress(AddressTable addressTable) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfAddressTable.insertAndReturnId(addressTable);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteUser(AddressTable addressTable) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfAddressTable.handle(addressTable);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int updateUser(AddressTable addressTable) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfAddressTable.handle(addressTable);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete_address(String addres_id) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDelete_address.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (addres_id == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, addres_id);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDelete_address.release(_stmt);
    }
  }

  @Override
  public void deletedata() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeletedata.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeletedata.release(_stmt);
    }
  }

  @Override
  public void update_address(String address_id, String address_one, String address_two,
      String state, String city, String name, String pincode, String phone, String deleivery_price,
      String state_id, String city_id) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdate_address.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (address_one == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, address_one);
      }
      _argIndex = 2;
      if (address_two == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, address_two);
      }
      _argIndex = 3;
      if (state == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, state);
      }
      _argIndex = 4;
      if (city == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, city);
      }
      _argIndex = 5;
      if (name == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, name);
      }
      _argIndex = 6;
      if (pincode == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, pincode);
      }
      _argIndex = 7;
      if (phone == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, phone);
      }
      _argIndex = 8;
      if (deleivery_price == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, deleivery_price);
      }
      _argIndex = 9;
      if (state_id == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, state_id);
      }
      _argIndex = 10;
      if (city_id == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, city_id);
      }
      _argIndex = 11;
      if (address_id == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, address_id);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdate_address.release(_stmt);
    }
  }

  @Override
  public boolean is_address_exit(String address_id) {
    final String _sql = "SELECT EXISTS(SELECT * FROM AddressTable WHERE address_id = ? )";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (address_id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, address_id);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final boolean _result;
      if(_cursor.moveToFirst()) {
        final int _tmp;
        _tmp = _cursor.getInt(0);
        _result = _tmp != 0;
      } else {
        _result = false;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public AddressTable getAddress(String address_id) {
    final String _sql = "SELECT * FROM AddressTable  WHERE address_id = ? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (address_id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, address_id);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfUid = _cursor.getColumnIndexOrThrow("uid");
      final int _cursorIndexOfAddressId = _cursor.getColumnIndexOrThrow("address_id");
      final int _cursorIndexOfAddressOne = _cursor.getColumnIndexOrThrow("address_one");
      final int _cursorIndexOfAddressTwo = _cursor.getColumnIndexOrThrow("address_two");
      final int _cursorIndexOfCity = _cursor.getColumnIndexOrThrow("city");
      final int _cursorIndexOfCountry = _cursor.getColumnIndexOrThrow("country");
      final int _cursorIndexOfState = _cursor.getColumnIndexOrThrow("state");
      final int _cursorIndexOfStateId = _cursor.getColumnIndexOrThrow("state_id");
      final int _cursorIndexOfCityId = _cursor.getColumnIndexOrThrow("city_id");
      final int _cursorIndexOfPincode = _cursor.getColumnIndexOrThrow("pincode");
      final int _cursorIndexOfPhone = _cursor.getColumnIndexOrThrow("phone");
      final int _cursorIndexOfDeliveryPrice = _cursor.getColumnIndexOrThrow("delivery_price");
      final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
      final AddressTable _result;
      if(_cursor.moveToFirst()) {
        _result = new AddressTable();
        final int _tmpUid;
        _tmpUid = _cursor.getInt(_cursorIndexOfUid);
        _result.setUid(_tmpUid);
        final String _tmpAddress_id;
        _tmpAddress_id = _cursor.getString(_cursorIndexOfAddressId);
        _result.setAddress_id(_tmpAddress_id);
        final String _tmpAddress_one;
        _tmpAddress_one = _cursor.getString(_cursorIndexOfAddressOne);
        _result.setAddress_one(_tmpAddress_one);
        final String _tmpAddress_two;
        _tmpAddress_two = _cursor.getString(_cursorIndexOfAddressTwo);
        _result.setAddress_two(_tmpAddress_two);
        final String _tmpCity;
        _tmpCity = _cursor.getString(_cursorIndexOfCity);
        _result.setCity(_tmpCity);
        final String _tmpCountry;
        _tmpCountry = _cursor.getString(_cursorIndexOfCountry);
        _result.setCountry(_tmpCountry);
        final String _tmpState;
        _tmpState = _cursor.getString(_cursorIndexOfState);
        _result.setState(_tmpState);
        final String _tmpState_id;
        _tmpState_id = _cursor.getString(_cursorIndexOfStateId);
        _result.setState_id(_tmpState_id);
        final String _tmpCity_id;
        _tmpCity_id = _cursor.getString(_cursorIndexOfCityId);
        _result.setCity_id(_tmpCity_id);
        final String _tmpPincode;
        _tmpPincode = _cursor.getString(_cursorIndexOfPincode);
        _result.setPincode(_tmpPincode);
        final String _tmpPhone;
        _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
        _result.setPhone(_tmpPhone);
        final String _tmpDelivery_price;
        _tmpDelivery_price = _cursor.getString(_cursorIndexOfDeliveryPrice);
        _result.setDelivery_price(_tmpDelivery_price);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        _result.setName(_tmpName);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<AddressTable> getAllAddress() {
    final String _sql = "SELECT * FROM AddressTable";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfUid = _cursor.getColumnIndexOrThrow("uid");
      final int _cursorIndexOfAddressId = _cursor.getColumnIndexOrThrow("address_id");
      final int _cursorIndexOfAddressOne = _cursor.getColumnIndexOrThrow("address_one");
      final int _cursorIndexOfAddressTwo = _cursor.getColumnIndexOrThrow("address_two");
      final int _cursorIndexOfCity = _cursor.getColumnIndexOrThrow("city");
      final int _cursorIndexOfCountry = _cursor.getColumnIndexOrThrow("country");
      final int _cursorIndexOfState = _cursor.getColumnIndexOrThrow("state");
      final int _cursorIndexOfStateId = _cursor.getColumnIndexOrThrow("state_id");
      final int _cursorIndexOfCityId = _cursor.getColumnIndexOrThrow("city_id");
      final int _cursorIndexOfPincode = _cursor.getColumnIndexOrThrow("pincode");
      final int _cursorIndexOfPhone = _cursor.getColumnIndexOrThrow("phone");
      final int _cursorIndexOfDeliveryPrice = _cursor.getColumnIndexOrThrow("delivery_price");
      final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
      final List<AddressTable> _result = new ArrayList<AddressTable>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final AddressTable _item;
        _item = new AddressTable();
        final int _tmpUid;
        _tmpUid = _cursor.getInt(_cursorIndexOfUid);
        _item.setUid(_tmpUid);
        final String _tmpAddress_id;
        _tmpAddress_id = _cursor.getString(_cursorIndexOfAddressId);
        _item.setAddress_id(_tmpAddress_id);
        final String _tmpAddress_one;
        _tmpAddress_one = _cursor.getString(_cursorIndexOfAddressOne);
        _item.setAddress_one(_tmpAddress_one);
        final String _tmpAddress_two;
        _tmpAddress_two = _cursor.getString(_cursorIndexOfAddressTwo);
        _item.setAddress_two(_tmpAddress_two);
        final String _tmpCity;
        _tmpCity = _cursor.getString(_cursorIndexOfCity);
        _item.setCity(_tmpCity);
        final String _tmpCountry;
        _tmpCountry = _cursor.getString(_cursorIndexOfCountry);
        _item.setCountry(_tmpCountry);
        final String _tmpState;
        _tmpState = _cursor.getString(_cursorIndexOfState);
        _item.setState(_tmpState);
        final String _tmpState_id;
        _tmpState_id = _cursor.getString(_cursorIndexOfStateId);
        _item.setState_id(_tmpState_id);
        final String _tmpCity_id;
        _tmpCity_id = _cursor.getString(_cursorIndexOfCityId);
        _item.setCity_id(_tmpCity_id);
        final String _tmpPincode;
        _tmpPincode = _cursor.getString(_cursorIndexOfPincode);
        _item.setPincode(_tmpPincode);
        final String _tmpPhone;
        _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
        _item.setPhone(_tmpPhone);
        final String _tmpDelivery_price;
        _tmpDelivery_price = _cursor.getString(_cursorIndexOfDeliveryPrice);
        _item.setDelivery_price(_tmpDelivery_price);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        _item.setName(_tmpName);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
