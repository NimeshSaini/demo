package com.utkarshnew.android.Dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.utkarshnew.android.table.APITABLE;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("unchecked")
public final class ApiDao_Impl implements ApiDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfAPITABLE;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfAPITABLE;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfAPITABLE;

  private final SharedSQLiteStatement __preparedStmtOfDeletedata;

  private final SharedSQLiteStatement __preparedStmtOfUpdate_api_version;

  private final SharedSQLiteStatement __preparedStmtOfUpdateversion;

  public ApiDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAPITABLE = new EntityInsertionAdapter<APITABLE>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `APITABLE`(`auto_id`,`user_id`,`timestamp`,`cdtimestamp`,`version`,`Apiname`,`Apicode`,`interval`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, APITABLE value) {
        stmt.bindLong(1, value.getAuto_id());
        if (value.getUser_id() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getUser_id());
        }
        if (value.getTimestamp() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getTimestamp());
        }
        if (value.getCdtimestamp() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getCdtimestamp());
        }
        if (value.getVersion() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getVersion());
        }
        if (value.getApiname() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getApiname());
        }
        if (value.getApicode() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getApicode());
        }
        if (value.getInterval() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getInterval());
        }
      }
    };
    this.__deletionAdapterOfAPITABLE = new EntityDeletionOrUpdateAdapter<APITABLE>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `APITABLE` WHERE `auto_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, APITABLE value) {
        stmt.bindLong(1, value.getAuto_id());
      }
    };
    this.__updateAdapterOfAPITABLE = new EntityDeletionOrUpdateAdapter<APITABLE>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `APITABLE` SET `auto_id` = ?,`user_id` = ?,`timestamp` = ?,`cdtimestamp` = ?,`version` = ?,`Apiname` = ?,`Apicode` = ?,`interval` = ? WHERE `auto_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, APITABLE value) {
        stmt.bindLong(1, value.getAuto_id());
        if (value.getUser_id() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getUser_id());
        }
        if (value.getTimestamp() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getTimestamp());
        }
        if (value.getCdtimestamp() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getCdtimestamp());
        }
        if (value.getVersion() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getVersion());
        }
        if (value.getApiname() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getApiname());
        }
        if (value.getApicode() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getApicode());
        }
        if (value.getInterval() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getInterval());
        }
        stmt.bindLong(9, value.getAuto_id());
      }
    };
    this.__preparedStmtOfDeletedata = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM APITABLE";
        return _query;
      }
    };
    this.__preparedStmtOfUpdate_api_version = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE APITABLE  SET timestamp =? ,interval=?,cdtimestamp=?   WHERE Apicode = ?  AND user_id =?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateversion = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE APITABLE SET version =? where  Apicode=? AND user_id=?";
        return _query;
      }
    };
  }

  @Override
  public long addUser(APITABLE apitable) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfAPITABLE.insertAndReturnId(apitable);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteUser(APITABLE apitable) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfAPITABLE.handle(apitable);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int updateUser(APITABLE apitable) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfAPITABLE.handle(apitable);
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
  public void update_api_version(String apicode, String userid, String timestamp, String interval,
      String cdtimestamp) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdate_api_version.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (timestamp == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, timestamp);
      }
      _argIndex = 2;
      if (interval == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, interval);
      }
      _argIndex = 3;
      if (cdtimestamp == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, cdtimestamp);
      }
      _argIndex = 4;
      if (apicode == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, apicode);
      }
      _argIndex = 5;
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
    final String _sql = "SELECT * FROM APITABLE WHERE user_id = ? AND Apicode =? ";
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
  public APITABLE getapidetail(String apicode, String userid) {
    final String _sql = "SELECT * FROM APITABLE  WHERE Apicode = ? AND user_id =?";
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
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final int _cursorIndexOfTimestamp = _cursor.getColumnIndexOrThrow("timestamp");
      final int _cursorIndexOfCdtimestamp = _cursor.getColumnIndexOrThrow("cdtimestamp");
      final int _cursorIndexOfVersion = _cursor.getColumnIndexOrThrow("version");
      final int _cursorIndexOfApiname = _cursor.getColumnIndexOrThrow("Apiname");
      final int _cursorIndexOfApicode = _cursor.getColumnIndexOrThrow("Apicode");
      final int _cursorIndexOfInterval = _cursor.getColumnIndexOrThrow("interval");
      final APITABLE _result;
      if(_cursor.moveToFirst()) {
        _result = new APITABLE();
        final int _tmpAuto_id;
        _tmpAuto_id = _cursor.getInt(_cursorIndexOfAutoId);
        _result.setAuto_id(_tmpAuto_id);
        final String _tmpUser_id;
        _tmpUser_id = _cursor.getString(_cursorIndexOfUserId);
        _result.setUser_id(_tmpUser_id);
        final String _tmpTimestamp;
        _tmpTimestamp = _cursor.getString(_cursorIndexOfTimestamp);
        _result.setTimestamp(_tmpTimestamp);
        final String _tmpCdtimestamp;
        _tmpCdtimestamp = _cursor.getString(_cursorIndexOfCdtimestamp);
        _result.setCdtimestamp(_tmpCdtimestamp);
        final String _tmpVersion;
        _tmpVersion = _cursor.getString(_cursorIndexOfVersion);
        _result.setVersion(_tmpVersion);
        final String _tmpApiname;
        _tmpApiname = _cursor.getString(_cursorIndexOfApiname);
        _result.setApiname(_tmpApiname);
        final String _tmpApicode;
        _tmpApicode = _cursor.getString(_cursorIndexOfApicode);
        _result.setApicode(_tmpApicode);
        final String _tmpInterval;
        _tmpInterval = _cursor.getString(_cursorIndexOfInterval);
        _result.setInterval(_tmpInterval);
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
