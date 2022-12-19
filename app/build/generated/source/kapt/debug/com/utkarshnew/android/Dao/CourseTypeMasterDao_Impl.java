package com.utkarshnew.android.Dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.utkarshnew.android.table.CourseTypeMasterTable;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class CourseTypeMasterDao_Impl implements CourseTypeMasterDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfCourseTypeMasterTable;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfCourseTypeMasterTable;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfCourseTypeMasterTable;

  private final SharedSQLiteStatement __preparedStmtOfDeletedata;

  public CourseTypeMasterDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCourseTypeMasterTable = new EntityInsertionAdapter<CourseTypeMasterTable>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `CourseTypeMasterTable`(`auto_id`,`id`,`name`,`user_id`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CourseTypeMasterTable value) {
        stmt.bindLong(1, value.getAuto_id());
        if (value.getId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getId());
        }
        if (value.getName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getName());
        }
        if (value.getUser_id() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getUser_id());
        }
      }
    };
    this.__deletionAdapterOfCourseTypeMasterTable = new EntityDeletionOrUpdateAdapter<CourseTypeMasterTable>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `CourseTypeMasterTable` WHERE `auto_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CourseTypeMasterTable value) {
        stmt.bindLong(1, value.getAuto_id());
      }
    };
    this.__updateAdapterOfCourseTypeMasterTable = new EntityDeletionOrUpdateAdapter<CourseTypeMasterTable>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `CourseTypeMasterTable` SET `auto_id` = ?,`id` = ?,`name` = ?,`user_id` = ? WHERE `auto_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CourseTypeMasterTable value) {
        stmt.bindLong(1, value.getAuto_id());
        if (value.getId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getId());
        }
        if (value.getName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getName());
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
        final String _query = "DELETE FROM CourseTypeMasterTable";
        return _query;
      }
    };
  }

  @Override
  public long addUser(CourseTypeMasterTable courseTypeMasterTable) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfCourseTypeMasterTable.insertAndReturnId(courseTypeMasterTable);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteUser(CourseTypeMasterTable courseTypeMasterTable) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfCourseTypeMasterTable.handle(courseTypeMasterTable);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int updateUser(CourseTypeMasterTable courseTypeMasterTable) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfCourseTypeMasterTable.handle(courseTypeMasterTable);
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
  public List<CourseTypeMasterTable> getcourse_typemaster(String userid) {
    final String _sql = "SELECT * FROM CourseTypeMasterTable where user_id IN (?)";
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
      final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final List<CourseTypeMasterTable> _result = new ArrayList<CourseTypeMasterTable>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final CourseTypeMasterTable _item;
        _item = new CourseTypeMasterTable();
        final int _tmpAuto_id;
        _tmpAuto_id = _cursor.getInt(_cursorIndexOfAutoId);
        _item.setAuto_id(_tmpAuto_id);
        final String _tmpId;
        _tmpId = _cursor.getString(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        _item.setName(_tmpName);
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
  public List<CourseTypeMasterTable> getAllUser() {
    final String _sql = "SELECT * FROM CourseTypeMasterTable";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfAutoId = _cursor.getColumnIndexOrThrow("auto_id");
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final List<CourseTypeMasterTable> _result = new ArrayList<CourseTypeMasterTable>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final CourseTypeMasterTable _item;
        _item = new CourseTypeMasterTable();
        final int _tmpAuto_id;
        _tmpAuto_id = _cursor.getInt(_cursorIndexOfAutoId);
        _item.setAuto_id(_tmpAuto_id);
        final String _tmpId;
        _tmpId = _cursor.getString(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        _item.setName(_tmpName);
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
