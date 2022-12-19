package com.utkarshnew.android.Dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.utkarshnew.android.table.HtmlTbale;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("unchecked")
public final class Htmllink_Impl implements Htmllink {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfHtmlTbale;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfHtmlTbale;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfHtmlTbale;

  private final SharedSQLiteStatement __preparedStmtOfUpdate_highlight;

  private final SharedSQLiteStatement __preparedStmtOfDeletedata;

  private final SharedSQLiteStatement __preparedStmtOfDelete_viaconceptid;

  public Htmllink_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfHtmlTbale = new EntityInsertionAdapter<HtmlTbale>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `HtmlTbale`(`autoid`,`concept_id`,`tile_id`,`user_id`,`course_id`,`highight`,`html`,`valid_to`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, HtmlTbale value) {
        stmt.bindLong(1, value.getAutoid());
        if (value.getConcept_id() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getConcept_id());
        }
        if (value.getTile_id() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getTile_id());
        }
        if (value.getUser_id() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getUser_id());
        }
        if (value.getCourse_id() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getCourse_id());
        }
        if (value.getHighight() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getHighight());
        }
        if (value.getHtml() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getHtml());
        }
        if (value.getValid_to() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getValid_to());
        }
      }
    };
    this.__deletionAdapterOfHtmlTbale = new EntityDeletionOrUpdateAdapter<HtmlTbale>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `HtmlTbale` WHERE `autoid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, HtmlTbale value) {
        stmt.bindLong(1, value.getAutoid());
      }
    };
    this.__updateAdapterOfHtmlTbale = new EntityDeletionOrUpdateAdapter<HtmlTbale>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `HtmlTbale` SET `autoid` = ?,`concept_id` = ?,`tile_id` = ?,`user_id` = ?,`course_id` = ?,`highight` = ?,`html` = ?,`valid_to` = ? WHERE `autoid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, HtmlTbale value) {
        stmt.bindLong(1, value.getAutoid());
        if (value.getConcept_id() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getConcept_id());
        }
        if (value.getTile_id() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getTile_id());
        }
        if (value.getUser_id() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getUser_id());
        }
        if (value.getCourse_id() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getCourse_id());
        }
        if (value.getHighight() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getHighight());
        }
        if (value.getHtml() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getHtml());
        }
        if (value.getValid_to() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getValid_to());
        }
        stmt.bindLong(9, value.getAutoid());
      }
    };
    this.__preparedStmtOfUpdate_highlight = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE htmltbale SET    highight=?  where  user_id =?  AND concept_id =?";
        return _query;
      }
    };
    this.__preparedStmtOfDeletedata = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM HtmlTbale";
        return _query;
      }
    };
    this.__preparedStmtOfDelete_viaconceptid = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "Delete from HtmlTbale where concept_id = ? AND user_id=?";
        return _query;
      }
    };
  }

  @Override
  public long addUser(HtmlTbale htmlTbale) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfHtmlTbale.insertAndReturnId(htmlTbale);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteUser(HtmlTbale htmlTbale) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfHtmlTbale.handle(htmlTbale);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int updateUser(HtmlTbale htmlTbale) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfHtmlTbale.handle(htmlTbale);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update_highlight(String highight, String userid, String concept_id) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdate_highlight.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (highight == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, highight);
      }
      _argIndex = 2;
      if (userid == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, userid);
      }
      _argIndex = 3;
      if (concept_id == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, concept_id);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdate_highlight.release(_stmt);
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
  public void delete_viaconceptid(String videoid, String userid) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDelete_viaconceptid.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (videoid == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, videoid);
      }
      _argIndex = 2;
      if (userid == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, userid);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDelete_viaconceptid.release(_stmt);
    }
  }

  @Override
  public boolean is_concept_exit(String user_id, String concept_id) {
    final String _sql = "SELECT EXISTS(SELECT * FROM HtmlTbale WHERE user_id = ? AND concept_id =?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (user_id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, user_id);
    }
    _argIndex = 2;
    if (concept_id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, concept_id);
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
  public HtmlTbale getconcept(String userid, String concept_id) {
    final String _sql = "SELECT * FROM HtmlTbale  WHERE user_id =? AND concept_id=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (userid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userid);
    }
    _argIndex = 2;
    if (concept_id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, concept_id);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfAutoid = _cursor.getColumnIndexOrThrow("autoid");
      final int _cursorIndexOfConceptId = _cursor.getColumnIndexOrThrow("concept_id");
      final int _cursorIndexOfTileId = _cursor.getColumnIndexOrThrow("tile_id");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final int _cursorIndexOfCourseId = _cursor.getColumnIndexOrThrow("course_id");
      final int _cursorIndexOfHighight = _cursor.getColumnIndexOrThrow("highight");
      final int _cursorIndexOfHtml = _cursor.getColumnIndexOrThrow("html");
      final int _cursorIndexOfValidTo = _cursor.getColumnIndexOrThrow("valid_to");
      final HtmlTbale _result;
      if(_cursor.moveToFirst()) {
        _result = new HtmlTbale();
        final int _tmpAutoid;
        _tmpAutoid = _cursor.getInt(_cursorIndexOfAutoid);
        _result.setAutoid(_tmpAutoid);
        final String _tmpConcept_id;
        _tmpConcept_id = _cursor.getString(_cursorIndexOfConceptId);
        _result.setConcept_id(_tmpConcept_id);
        final String _tmpTile_id;
        _tmpTile_id = _cursor.getString(_cursorIndexOfTileId);
        _result.setTile_id(_tmpTile_id);
        final String _tmpUser_id;
        _tmpUser_id = _cursor.getString(_cursorIndexOfUserId);
        _result.setUser_id(_tmpUser_id);
        final String _tmpCourse_id;
        _tmpCourse_id = _cursor.getString(_cursorIndexOfCourseId);
        _result.setCourse_id(_tmpCourse_id);
        final String _tmpHighight;
        _tmpHighight = _cursor.getString(_cursorIndexOfHighight);
        _result.setHighight(_tmpHighight);
        final String _tmpHtml;
        _tmpHtml = _cursor.getString(_cursorIndexOfHtml);
        _result.setHtml(_tmpHtml);
        final String _tmpValid_to;
        _tmpValid_to = _cursor.getString(_cursorIndexOfValidTo);
        _result.setValid_to(_tmpValid_to);
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
