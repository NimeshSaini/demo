package com.utkarshnew.android.Dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.utkarshnew.android.table.TestTable;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("unchecked")
public final class TestDao_Impl implements TestDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfTestTable;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfTestTable;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfTestTable;

  private final SharedSQLiteStatement __preparedStmtOfDelete_test_data;

  private final SharedSQLiteStatement __preparedStmtOfDeletedata;

  public TestDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTestTable = new EntityInsertionAdapter<TestTable>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `TestTable`(`autoid`,`test_id`,`user_id`,`course_id`,`status`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TestTable value) {
        stmt.bindLong(1, value.getAutoid());
        if (value.getTest_id() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTest_id());
        }
        if (value.getUser_id() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getUser_id());
        }
        if (value.getCourse_id() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getCourse_id());
        }
        if (value.getStatus() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getStatus());
        }
      }
    };
    this.__deletionAdapterOfTestTable = new EntityDeletionOrUpdateAdapter<TestTable>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `TestTable` WHERE `autoid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TestTable value) {
        stmt.bindLong(1, value.getAutoid());
      }
    };
    this.__updateAdapterOfTestTable = new EntityDeletionOrUpdateAdapter<TestTable>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `TestTable` SET `autoid` = ?,`test_id` = ?,`user_id` = ?,`course_id` = ?,`status` = ? WHERE `autoid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TestTable value) {
        stmt.bindLong(1, value.getAutoid());
        if (value.getTest_id() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTest_id());
        }
        if (value.getUser_id() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getUser_id());
        }
        if (value.getCourse_id() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getCourse_id());
        }
        if (value.getStatus() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getStatus());
        }
        stmt.bindLong(6, value.getAutoid());
      }
    };
    this.__preparedStmtOfDelete_test_data = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE  FROM TestTable WHERE user_id = ?   AND test_id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeletedata = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM MasteAllCatTable";
        return _query;
      }
    };
  }

  @Override
  public long addUser(TestTable audioTable) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfTestTable.insertAndReturnId(audioTable);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteUser(TestTable audioTable) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfTestTable.handle(audioTable);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int updateUser(TestTable audioTable) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfTestTable.handle(audioTable);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int delete_test_data(String userid, String test_id) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDelete_test_data.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (userid == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, userid);
      }
      _argIndex = 2;
      if (test_id == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, test_id);
      }
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfDelete_test_data.release(_stmt);
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
  public TestTable test_data(String test_id, String userid) {
    final String _sql = "SELECT * FROM TestTable WHERE  test_id = ? AND user_id=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (test_id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, test_id);
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
      final int _cursorIndexOfTestId = _cursor.getColumnIndexOrThrow("test_id");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final int _cursorIndexOfCourseId = _cursor.getColumnIndexOrThrow("course_id");
      final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("status");
      final TestTable _result;
      if(_cursor.moveToFirst()) {
        _result = new TestTable();
        final int _tmpAutoid;
        _tmpAutoid = _cursor.getInt(_cursorIndexOfAutoid);
        _result.setAutoid(_tmpAutoid);
        final String _tmpTest_id;
        _tmpTest_id = _cursor.getString(_cursorIndexOfTestId);
        _result.setTest_id(_tmpTest_id);
        final String _tmpUser_id;
        _tmpUser_id = _cursor.getString(_cursorIndexOfUserId);
        _result.setUser_id(_tmpUser_id);
        final String _tmpCourse_id;
        _tmpCourse_id = _cursor.getString(_cursorIndexOfCourseId);
        _result.setCourse_id(_tmpCourse_id);
        final String _tmpStatus;
        _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        _result.setStatus(_tmpStatus);
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
  public boolean is_test_exit(String test_id, String userid) {
    final String _sql = "SELECT EXISTS(SELECT * FROM TestTable WHERE test_id = ? AND user_id=?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (test_id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, test_id);
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
}
