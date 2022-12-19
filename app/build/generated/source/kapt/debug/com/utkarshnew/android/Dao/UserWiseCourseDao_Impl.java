package com.utkarshnew.android.Dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.utkarshnew.android.table.UserWiseCourseTable;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("unchecked")
public final class UserWiseCourseDao_Impl implements UserWiseCourseDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfUserWiseCourseTable;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfUserWiseCourseTable;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfUserWiseCourseTable;

  private final SharedSQLiteStatement __preparedStmtOfDeletedata;

  private final SharedSQLiteStatement __preparedStmtOfUpdate_api_version;

  private final SharedSQLiteStatement __preparedStmtOfUpdateversion;

  public UserWiseCourseDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserWiseCourseTable = new EntityInsertionAdapter<UserWiseCourseTable>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `UserWiseCourseTable`(`auto_id`,`meta_id`,`code`,`version`,`exp`,`userid`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UserWiseCourseTable value) {
        stmt.bindLong(1, value.getAuto_id());
        if (value.getMeta_id() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getMeta_id());
        }
        if (value.getCode() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCode());
        }
        if (value.getVersion() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getVersion());
        }
        if (value.getExp() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getExp());
        }
        if (value.getUserid() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getUserid());
        }
      }
    };
    this.__deletionAdapterOfUserWiseCourseTable = new EntityDeletionOrUpdateAdapter<UserWiseCourseTable>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `UserWiseCourseTable` WHERE `auto_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UserWiseCourseTable value) {
        stmt.bindLong(1, value.getAuto_id());
      }
    };
    this.__updateAdapterOfUserWiseCourseTable = new EntityDeletionOrUpdateAdapter<UserWiseCourseTable>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `UserWiseCourseTable` SET `auto_id` = ?,`meta_id` = ?,`code` = ?,`version` = ?,`exp` = ?,`userid` = ? WHERE `auto_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UserWiseCourseTable value) {
        stmt.bindLong(1, value.getAuto_id());
        if (value.getMeta_id() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getMeta_id());
        }
        if (value.getCode() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCode());
        }
        if (value.getVersion() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getVersion());
        }
        if (value.getExp() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getExp());
        }
        if (value.getUserid() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getUserid());
        }
        stmt.bindLong(7, value.getAuto_id());
      }
    };
    this.__preparedStmtOfDeletedata = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM UserWiseCourseTable";
        return _query;
      }
    };
    this.__preparedStmtOfUpdate_api_version = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE UserWiseCourseTable  SET  version =?  WHERE meta_id = ?  AND userid =?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateversion = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE UserWiseCourseTable SET version =? where  meta_id=? AND userid=?";
        return _query;
      }
    };
  }

  @Override
  public long addUser(UserWiseCourseTable apitable) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfUserWiseCourseTable.insertAndReturnId(apitable);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteUser(UserWiseCourseTable apitable) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfUserWiseCourseTable.handle(apitable);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int updateUser(UserWiseCourseTable apitable) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfUserWiseCourseTable.handle(apitable);
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
  public void update_api_version(String apicode, String userid, String version) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdate_api_version.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (version == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, version);
      }
      _argIndex = 2;
      if (apicode == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, apicode);
      }
      _argIndex = 3;
      if (userid == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, userid);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdate_api_version.release(_stmt);
    }
  }

  @Override
  public int updateversion(String version, String userid, String apicode) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateversion.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (version == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, version);
      }
      _argIndex = 2;
      if (apicode == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, apicode);
      }
      _argIndex = 3;
      if (userid == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, userid);
      }
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateversion.release(_stmt);
    }
  }

  @Override
  public boolean is_api_code_exits(String userid, String apicode) {
    final String _sql = "SELECT * FROM UserWiseCourseTable WHERE userid = ? AND meta_id =? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (userid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userid);
    }
    _argIndex = 2;
    if (apicode == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, apicode);
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
  public UserWiseCourseTable getapidetail(String apicode, String userid) {
    final String _sql = "SELECT * FROM UserWiseCourseTable  WHERE meta_id = ? AND userid =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (apicode == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, apicode);
    }
    _argIndex = 2;
    if (userid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userid);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfAutoId = _cursor.getColumnIndexOrThrow("auto_id");
      final int _cursorIndexOfMetaId = _cursor.getColumnIndexOrThrow("meta_id");
      final int _cursorIndexOfCode = _cursor.getColumnIndexOrThrow("code");
      final int _cursorIndexOfVersion = _cursor.getColumnIndexOrThrow("version");
      final int _cursorIndexOfExp = _cursor.getColumnIndexOrThrow("exp");
      final int _cursorIndexOfUserid = _cursor.getColumnIndexOrThrow("userid");
      final UserWiseCourseTable _result;
      if(_cursor.moveToFirst()) {
        _result = new UserWiseCourseTable();
        final int _tmpAuto_id;
        _tmpAuto_id = _cursor.getInt(_cursorIndexOfAutoId);
        _result.setAuto_id(_tmpAuto_id);
        final String _tmpMeta_id;
        _tmpMeta_id = _cursor.getString(_cursorIndexOfMetaId);
        _result.setMeta_id(_tmpMeta_id);
        final String _tmpCode;
        _tmpCode = _cursor.getString(_cursorIndexOfCode);
        _result.setCode(_tmpCode);
        final String _tmpVersion;
        _tmpVersion = _cursor.getString(_cursorIndexOfVersion);
        _result.setVersion(_tmpVersion);
        final String _tmpExp;
        _tmpExp = _cursor.getString(_cursorIndexOfExp);
        _result.setExp(_tmpExp);
        final String _tmpUserid;
        _tmpUserid = _cursor.getString(_cursorIndexOfUserid);
        _result.setUserid(_tmpUserid);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
