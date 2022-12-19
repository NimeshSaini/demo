package com.utkarshnew.android.Dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.utkarshnew.android.TypeConverter.FilterConverter;
import com.utkarshnew.android.table.MasteAllCatTable;
import com.utkarshnew.android.table.Subjectfilter;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class GetMasterAllCatDao_Impl implements GetMasterAllCatDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfMasteAllCatTable;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfMasteAllCatTable;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfMasteAllCatTable;

  private final SharedSQLiteStatement __preparedStmtOfDeletedata;

  public GetMasterAllCatDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMasteAllCatTable = new EntityInsertionAdapter<MasteAllCatTable>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `MasteAllCatTable`(`auto_id`,`id`,`name`,`parent_id`,`master_type`,`user_id`,`filters`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MasteAllCatTable value) {
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
        if (value.getParent_id() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getParent_id());
        }
        if (value.getMaster_type() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getMaster_type());
        }
        if (value.getUser_id() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getUser_id());
        }
        final String _tmp;
        _tmp = FilterConverter.fromArrayList(value.getFilters());
        if (_tmp == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, _tmp);
        }
      }
    };
    this.__deletionAdapterOfMasteAllCatTable = new EntityDeletionOrUpdateAdapter<MasteAllCatTable>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `MasteAllCatTable` WHERE `auto_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MasteAllCatTable value) {
        stmt.bindLong(1, value.getAuto_id());
      }
    };
    this.__updateAdapterOfMasteAllCatTable = new EntityDeletionOrUpdateAdapter<MasteAllCatTable>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `MasteAllCatTable` SET `auto_id` = ?,`id` = ?,`name` = ?,`parent_id` = ?,`master_type` = ?,`user_id` = ?,`filters` = ? WHERE `auto_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MasteAllCatTable value) {
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
        if (value.getParent_id() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getParent_id());
        }
        if (value.getMaster_type() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getMaster_type());
        }
        if (value.getUser_id() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getUser_id());
        }
        final String _tmp;
        _tmp = FilterConverter.fromArrayList(value.getFilters());
        if (_tmp == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, _tmp);
        }
        stmt.bindLong(8, value.getAuto_id());
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
  public long addUser(MasteAllCatTable getMasterData_allcat) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfMasteAllCatTable.insertAndReturnId(getMasterData_allcat);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteUser(MasteAllCatTable getMasterData_allcat) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfMasteAllCatTable.handle(getMasterData_allcat);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int updateUser(MasteAllCatTable getMasterData_allcat) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfMasteAllCatTable.handle(getMasterData_allcat);
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
  public List<MasteAllCatTable> getAllUser() {
    final String _sql = "SELECT * FROM MasteAllCatTable";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfAutoId = _cursor.getColumnIndexOrThrow("auto_id");
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
      final int _cursorIndexOfParentId = _cursor.getColumnIndexOrThrow("parent_id");
      final int _cursorIndexOfMasterType = _cursor.getColumnIndexOrThrow("master_type");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final int _cursorIndexOfFilters = _cursor.getColumnIndexOrThrow("filters");
      final List<MasteAllCatTable> _result = new ArrayList<MasteAllCatTable>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final MasteAllCatTable _item;
        _item = new MasteAllCatTable();
        final int _tmpAuto_id;
        _tmpAuto_id = _cursor.getInt(_cursorIndexOfAutoId);
        _item.setAuto_id(_tmpAuto_id);
        final String _tmpId;
        _tmpId = _cursor.getString(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        _item.setName(_tmpName);
        final String _tmpParent_id;
        _tmpParent_id = _cursor.getString(_cursorIndexOfParentId);
        _item.setParent_id(_tmpParent_id);
        final String _tmpMaster_type;
        _tmpMaster_type = _cursor.getString(_cursorIndexOfMasterType);
        _item.setMaster_type(_tmpMaster_type);
        final String _tmpUser_id;
        _tmpUser_id = _cursor.getString(_cursorIndexOfUserId);
        _item.setUser_id(_tmpUser_id);
        final List<Subjectfilter> _tmpFilters;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfFilters);
        _tmpFilters = FilterConverter.fromString(_tmp);
        _item.setFilters(_tmpFilters);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<MasteAllCatTable> getmaster_allcat(String userid) {
    final String _sql = "SELECT * FROM MasteAllCatTable where user_id IN (?)";
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
      final int _cursorIndexOfParentId = _cursor.getColumnIndexOrThrow("parent_id");
      final int _cursorIndexOfMasterType = _cursor.getColumnIndexOrThrow("master_type");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final int _cursorIndexOfFilters = _cursor.getColumnIndexOrThrow("filters");
      final List<MasteAllCatTable> _result = new ArrayList<MasteAllCatTable>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final MasteAllCatTable _item;
        _item = new MasteAllCatTable();
        final int _tmpAuto_id;
        _tmpAuto_id = _cursor.getInt(_cursorIndexOfAutoId);
        _item.setAuto_id(_tmpAuto_id);
        final String _tmpId;
        _tmpId = _cursor.getString(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        _item.setName(_tmpName);
        final String _tmpParent_id;
        _tmpParent_id = _cursor.getString(_cursorIndexOfParentId);
        _item.setParent_id(_tmpParent_id);
        final String _tmpMaster_type;
        _tmpMaster_type = _cursor.getString(_cursorIndexOfMasterType);
        _item.setMaster_type(_tmpMaster_type);
        final String _tmpUser_id;
        _tmpUser_id = _cursor.getString(_cursorIndexOfUserId);
        _item.setUser_id(_tmpUser_id);
        final List<Subjectfilter> _tmpFilters;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfFilters);
        _tmpFilters = FilterConverter.fromString(_tmp);
        _item.setFilters(_tmpFilters);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<MasteAllCatTable> getmaster_allcat_parentid(String parentid) {
    final String _sql = "SELECT * FROM MasteAllCatTable where  parent_id=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (parentid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, parentid);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfAutoId = _cursor.getColumnIndexOrThrow("auto_id");
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
      final int _cursorIndexOfParentId = _cursor.getColumnIndexOrThrow("parent_id");
      final int _cursorIndexOfMasterType = _cursor.getColumnIndexOrThrow("master_type");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final int _cursorIndexOfFilters = _cursor.getColumnIndexOrThrow("filters");
      final List<MasteAllCatTable> _result = new ArrayList<MasteAllCatTable>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final MasteAllCatTable _item;
        _item = new MasteAllCatTable();
        final int _tmpAuto_id;
        _tmpAuto_id = _cursor.getInt(_cursorIndexOfAutoId);
        _item.setAuto_id(_tmpAuto_id);
        final String _tmpId;
        _tmpId = _cursor.getString(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        _item.setName(_tmpName);
        final String _tmpParent_id;
        _tmpParent_id = _cursor.getString(_cursorIndexOfParentId);
        _item.setParent_id(_tmpParent_id);
        final String _tmpMaster_type;
        _tmpMaster_type = _cursor.getString(_cursorIndexOfMasterType);
        _item.setMaster_type(_tmpMaster_type);
        final String _tmpUser_id;
        _tmpUser_id = _cursor.getString(_cursorIndexOfUserId);
        _item.setUser_id(_tmpUser_id);
        final List<Subjectfilter> _tmpFilters;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfFilters);
        _tmpFilters = FilterConverter.fromString(_tmp);
        _item.setFilters(_tmpFilters);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<MasteAllCatTable> getmaster_allcat_viaprefence(String userid) {
    final String _sql = "SELECT * FROM MasteAllCatTable where master_type IN (?)";
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
      final int _cursorIndexOfParentId = _cursor.getColumnIndexOrThrow("parent_id");
      final int _cursorIndexOfMasterType = _cursor.getColumnIndexOrThrow("master_type");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final int _cursorIndexOfFilters = _cursor.getColumnIndexOrThrow("filters");
      final List<MasteAllCatTable> _result = new ArrayList<MasteAllCatTable>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final MasteAllCatTable _item;
        _item = new MasteAllCatTable();
        final int _tmpAuto_id;
        _tmpAuto_id = _cursor.getInt(_cursorIndexOfAutoId);
        _item.setAuto_id(_tmpAuto_id);
        final String _tmpId;
        _tmpId = _cursor.getString(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        _item.setName(_tmpName);
        final String _tmpParent_id;
        _tmpParent_id = _cursor.getString(_cursorIndexOfParentId);
        _item.setParent_id(_tmpParent_id);
        final String _tmpMaster_type;
        _tmpMaster_type = _cursor.getString(_cursorIndexOfMasterType);
        _item.setMaster_type(_tmpMaster_type);
        final String _tmpUser_id;
        _tmpUser_id = _cursor.getString(_cursorIndexOfUserId);
        _item.setUser_id(_tmpUser_id);
        final List<Subjectfilter> _tmpFilters;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfFilters);
        _tmpFilters = FilterConverter.fromString(_tmp);
        _item.setFilters(_tmpFilters);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public boolean isRecordExistsUserId(String userid) {
    final String _sql = "SELECT EXISTS(SELECT * FROM MasteAllCatTable WHERE user_id = ?)";
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
  public String getfilteddata(String id) {
    final String _sql = "SELECT filters FROM MasteAllCatTable where id IN (?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final String _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getString(0);
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
