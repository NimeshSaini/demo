package com.utkarshnew.android.Dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.utkarshnew.android.table.HomeApiStatusTable;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("unchecked")
public final class HomeApiStatusDao_Impl implements HomeApiStatusDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfHomeApiStatusTable;

  private final SharedSQLiteStatement __preparedStmtOfUpdaterecord;

  private final SharedSQLiteStatement __preparedStmtOfDeletedata;

  public HomeApiStatusDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfHomeApiStatusTable = new EntityInsertionAdapter<HomeApiStatusTable>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `HomeApiStatusTable`(`autoid`,`userid`,`mainid`,`status`,`page`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, HomeApiStatusTable value) {
        stmt.bindLong(1, value.getAutoid());
        if (value.getUser_id() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getUser_id());
        }
        if (value.getMain_id() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getMain_id());
        }
        if (value.getStatus() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getStatus());
        }
        if (value.getPage() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getPage());
        }
      }
    };
    this.__preparedStmtOfUpdaterecord = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE HomeApiStatusTable SET status=? , page=? WHERE mainid = ? AND  userid = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeletedata = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM HomeApiStatusTable";
        return _query;
      }
    };
  }

  @Override
  public long addCoursedata(HomeApiStatusTable homeApiStatusTable) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfHomeApiStatusTable.insertAndReturnId(homeApiStatusTable);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int updaterecord(String page, String status, String mainid, String userid) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdaterecord.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (status == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, status);
      }
      _argIndex = 2;
      if (page == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, page);
      }
      _argIndex = 3;
      if (mainid == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, mainid);
      }
      _argIndex = 4;
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
      __preparedStmtOfUpdaterecord.release(_stmt);
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
  public boolean isRecordExistsUserId(String userid, String mainid) {
    final String _sql = "SELECT EXISTS(SELECT * FROM HomeApiStatusTable WHERE mainid = ? AND userid = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (mainid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, mainid);
    }
    _argIndex = 2;
    if (userid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userid);
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
  public HomeApiStatusTable getcoursedetail(String mainid, String userid) {
    final String _sql = "SELECT * FROM HomeApiStatusTable  WHERE mainid = ? AND userid =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (mainid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, mainid);
    }
    _argIndex = 2;
    if (userid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userid);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfAutoid = _cursor.getColumnIndexOrThrow("autoid");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("userid");
      final int _cursorIndexOfMainId = _cursor.getColumnIndexOrThrow("mainid");
      final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("status");
      final int _cursorIndexOfPage = _cursor.getColumnIndexOrThrow("page");
      final HomeApiStatusTable _result;
      if(_cursor.moveToFirst()) {
        _result = new HomeApiStatusTable();
        final int _tmpAutoid;
        _tmpAutoid = _cursor.getInt(_cursorIndexOfAutoid);
        _result.setAutoid(_tmpAutoid);
        final String _tmpUser_id;
        _tmpUser_id = _cursor.getString(_cursorIndexOfUserId);
        _result.setUser_id(_tmpUser_id);
        final String _tmpMain_id;
        _tmpMain_id = _cursor.getString(_cursorIndexOfMainId);
        _result.setMain_id(_tmpMain_id);
        final String _tmpStatus;
        _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        _result.setStatus(_tmpStatus);
        final String _tmpPage;
        _tmpPage = _cursor.getString(_cursorIndexOfPage);
        _result.setPage(_tmpPage);
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
