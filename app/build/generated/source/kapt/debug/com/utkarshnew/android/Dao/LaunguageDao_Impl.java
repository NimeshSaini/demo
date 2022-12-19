package com.utkarshnew.android.Dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.utkarshnew.android.table.LanguagesTable;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class LaunguageDao_Impl implements LaunguageDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfLanguagesTable;

  private final SharedSQLiteStatement __preparedStmtOfDeletedata;

  public LaunguageDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLanguagesTable = new EntityInsertionAdapter<LanguagesTable>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `LanguagesTable`(`autoid`,`id`,`language`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, LanguagesTable value) {
        stmt.bindLong(1, value.getAutoid());
        if (value.getId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getId());
        }
        if (value.getLanguage() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getLanguage());
        }
      }
    };
    this.__preparedStmtOfDeletedata = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM LanguagesTable";
        return _query;
      }
    };
  }

  @Override
  public long addLaunguage(LanguagesTable languagesTable) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfLanguagesTable.insertAndReturnId(languagesTable);
      __db.setTransactionSuccessful();
      return _result;
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
  public List<LanguagesTable> getLaunguagedetail() {
    final String _sql = "SELECT * FROM LanguagesTable";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfAutoid = _cursor.getColumnIndexOrThrow("autoid");
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfLanguage = _cursor.getColumnIndexOrThrow("language");
      final List<LanguagesTable> _result = new ArrayList<LanguagesTable>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final LanguagesTable _item;
        _item = new LanguagesTable();
        final int _tmpAutoid;
        _tmpAutoid = _cursor.getInt(_cursorIndexOfAutoid);
        _item.setAutoid(_tmpAutoid);
        final String _tmpId;
        _tmpId = _cursor.getString(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpLanguage;
        _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
        _item.setLanguage(_tmpLanguage);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
