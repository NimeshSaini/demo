package com.utkarshnew.android.Dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.utkarshnew.android.table.MasterCat;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class MasterCatDao_Impl implements MasterCatDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfMasterCat;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfMasterCat;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfMasterCat;

  private final SharedSQLiteStatement __preparedStmtOfDeletedata;

  public MasterCatDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMasterCat = new EntityInsertionAdapter<MasterCat>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `MasterCat`(`auto_id`,`id`,`cat`,`user_id`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MasterCat value) {
        stmt.bindLong(1, value.getAuto_id());
        if (value.getId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getId());
        }
        if (value.getCat() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCat());
        }
        if (value.getUser_id() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getUser_id());
        }
      }
    };
    this.__deletionAdapterOfMasterCat = new EntityDeletionOrUpdateAdapter<MasterCat>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `MasterCat` WHERE `auto_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MasterCat value) {
        stmt.bindLong(1, value.getAuto_id());
      }
    };
    this.__updateAdapterOfMasterCat = new EntityDeletionOrUpdateAdapter<MasterCat>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `MasterCat` SET `auto_id` = ?,`id` = ?,`cat` = ?,`user_id` = ? WHERE `auto_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MasterCat value) {
        stmt.bindLong(1, value.getAuto_id());
        if (value.getId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getId());
        }
        if (value.getCat() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCat());
        }
        if (value.getUser_id() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getUser_id());
        }
        stmt.bindLong(5, value.getAuto_id());
      }
    };
    this.__preparedStmtOfDeletedata = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM MasterCat";
        return _query;
      }
    };
  }

  @Override
  public long addUser(MasterCat courseTypeMasterTable) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfMasterCat.insertAndReturnId(courseTypeMasterTable);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteUser(MasterCat courseTypeMasterTable) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfMasterCat.handle(courseTypeMasterTable);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int updateUser(MasterCat courseTypeMasterTable) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfMasterCat.handle(courseTypeMasterTable);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
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
  public List<MasterCat> getmastercat(String userid) {
    final String _sql = "SELECT * FROM MasterCat where user_id IN (?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (userid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userid);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfAutoId = _cursor.getColumnIndexOrThrow("auto_id");
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfCat = _cursor.getColumnIndexOrThrow("cat");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final List<MasterCat> _result = new ArrayList<MasterCat>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final MasterCat _item;
        _item = new MasterCat();
        final int _tmpAuto_id;
        _tmpAuto_id = _cursor.getInt(_cursorIndexOfAutoId);
        _item.setAuto_id(_tmpAuto_id);
        final String _tmpId;
        _tmpId = _cursor.getString(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpCat;
        _tmpCat = _cursor.getString(_cursorIndexOfCat);
        _item.setCat(_tmpCat);
        final String _tmpUser_id;
        _tmpUser_id = _cursor.getString(_cursorIndexOfUserId);
        _item.setUser_id(_tmpUser_id);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<MasterCat> getmastercat_viaprefence(String master_prefence) {
    final String _sql = "SELECT * FROM MasterCat where  id   IN (?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (master_prefence == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, master_prefence);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfAutoId = _cursor.getColumnIndexOrThrow("auto_id");
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfCat = _cursor.getColumnIndexOrThrow("cat");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final List<MasterCat> _result = new ArrayList<MasterCat>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final MasterCat _item;
        _item = new MasterCat();
        final int _tmpAuto_id;
        _tmpAuto_id = _cursor.getInt(_cursorIndexOfAutoId);
        _item.setAuto_id(_tmpAuto_id);
        final String _tmpId;
        _tmpId = _cursor.getString(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpCat;
        _tmpCat = _cursor.getString(_cursorIndexOfCat);
        _item.setCat(_tmpCat);
        final String _tmpUser_id;
        _tmpUser_id = _cursor.getString(_cursorIndexOfUserId);
        _item.setUser_id(_tmpUser_id);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<MasterCat> getAllUser() {
    final String _sql = "SELECT * FROM MasterCat";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfAutoId = _cursor.getColumnIndexOrThrow("auto_id");
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfCat = _cursor.getColumnIndexOrThrow("cat");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final List<MasterCat> _result = new ArrayList<MasterCat>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final MasterCat _item;
        _item = new MasterCat();
        final int _tmpAuto_id;
        _tmpAuto_id = _cursor.getInt(_cursorIndexOfAutoId);
        _item.setAuto_id(_tmpAuto_id);
        final String _tmpId;
        _tmpId = _cursor.getString(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpCat;
        _tmpCat = _cursor.getString(_cursorIndexOfCat);
        _item.setCat(_tmpCat);
        final String _tmpUser_id;
        _tmpUser_id = _cursor.getString(_cursorIndexOfUserId);
        _item.setUser_id(_tmpUser_id);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
