package com.utkarshnew.android.Dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.utkarshnew.android.table.PigibagTable;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("unchecked")
public final class PigiBag_Impl implements PigiBag {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfPigibagTable;

  private final SharedSQLiteStatement __preparedStmtOfUpdaterecord;

  private final SharedSQLiteStatement __preparedStmtOfDeletedata;

  private final SharedSQLiteStatement __preparedStmtOfDeletepigibagdata;

  public PigiBag_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPigibagTable = new EntityInsertionAdapter<PigibagTable>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `PigibagTable`(`auto_id`,`cdtimestamp`,`user_id`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PigibagTable value) {
        stmt.bindLong(1, value.getAuto_id());
        if (value.getCdtimestamp() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getCdtimestamp());
        }
        if (value.getUser_id() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getUser_id());
        }
      }
    };
    this.__preparedStmtOfUpdaterecord = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE PigibagTable SET cdtimestamp =?  where  user_id=?";
        return _query;
      }
    };
    this.__preparedStmtOfDeletedata = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM PigibagTable";
        return _query;
      }
    };
    this.__preparedStmtOfDeletepigibagdata = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "Delete  FROM PigibagTable  WHERE user_id =?";
        return _query;
      }
    };
  }

  @Override
  public long addApiedata(PigibagTable pigibag) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfPigibagTable.insertAndReturnId(pigibag);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int updaterecord(String userid, String cdtimestamp) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdaterecord.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (cdtimestamp == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, cdtimestamp);
      }
      _argIndex = 2;
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
  public int deletepigibagdata(String userid) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeletepigibagdata.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
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
      __preparedStmtOfDeletepigibagdata.release(_stmt);
    }
  }

  @Override
  public boolean isRecordExistsUserId(String userid) {
    final String _sql = "SELECT EXISTS(SELECT * FROM PigibagTable WHERE user_id = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
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
  public PigibagTable getpigidetail(String userid) {
    final String _sql = "SELECT * FROM PigibagTable  WHERE  user_id =?";
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
      final int _cursorIndexOfCdtimestamp = _cursor.getColumnIndexOrThrow("cdtimestamp");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final PigibagTable _result;
      if(_cursor.moveToFirst()) {
        _result = new PigibagTable();
        final int _tmpAuto_id;
        _tmpAuto_id = _cursor.getInt(_cursorIndexOfAutoId);
        _result.setAuto_id(_tmpAuto_id);
        final String _tmpCdtimestamp;
        _tmpCdtimestamp = _cursor.getString(_cursorIndexOfCdtimestamp);
        _result.setCdtimestamp(_tmpCdtimestamp);
        final String _tmpUser_id;
        _tmpUser_id = _cursor.getString(_cursorIndexOfUserId);
        _result.setUser_id(_tmpUser_id);
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
  public PigibagTable getuserid() {
    final String _sql = "SELECT * FROM PigibagTable";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfAutoId = _cursor.getColumnIndexOrThrow("auto_id");
      final int _cursorIndexOfCdtimestamp = _cursor.getColumnIndexOrThrow("cdtimestamp");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final PigibagTable _result;
      if(_cursor.moveToFirst()) {
        _result = new PigibagTable();
        final int _tmpAuto_id;
        _tmpAuto_id = _cursor.getInt(_cursorIndexOfAutoId);
        _result.setAuto_id(_tmpAuto_id);
        final String _tmpCdtimestamp;
        _tmpCdtimestamp = _cursor.getString(_cursorIndexOfCdtimestamp);
        _result.setCdtimestamp(_tmpCdtimestamp);
        final String _tmpUser_id;
        _tmpUser_id = _cursor.getString(_cursorIndexOfUserId);
        _result.setUser_id(_tmpUser_id);
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
