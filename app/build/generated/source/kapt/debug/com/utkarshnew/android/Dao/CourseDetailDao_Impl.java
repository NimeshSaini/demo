package com.utkarshnew.android.Dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.utkarshnew.android.TypeConverter.NotesConverter;
import com.utkarshnew.android.courses.modal.NotesType;
import com.utkarshnew.android.table.CourseDetailTable;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class CourseDetailDao_Impl implements CourseDetailDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfCourseDetailTable;

  private final SharedSQLiteStatement __preparedStmtOfDeletecoursedetail;

  private final SharedSQLiteStatement __preparedStmtOfDeletedata;

  public CourseDetailDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCourseDetailTable = new EntityInsertionAdapter<CourseDetailTable>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `CourseDetailTable`(`autoid`,`coursetitle`,`couseid`,`tax`,`is_purchased`,`validity`,`course_sp`,`author_title`,`view_type`,`type`,`mrp`,`desc_header_image`,`userid`,`tileid`,`tiletitile`,`tilerevert`,`tilemeta`,`notes_type`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CourseDetailTable value) {
        stmt.bindLong(1, value.getAutoid());
        if (value.getCourse_title() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getCourse_title());
        }
        if (value.getCourse_id() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCourse_id());
        }
        if (value.getTax() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getTax());
        }
        if (value.getIs_purchased() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getIs_purchased());
        }
        if (value.getValidity() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getValidity());
        }
        if (value.getCourse_sp() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getCourse_sp());
        }
        if (value.getAuthor_title() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getAuthor_title());
        }
        if (value.getView_type() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getView_type());
        }
        if (value.getType() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getType());
        }
        if (value.getMrp() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getMrp());
        }
        if (value.getDesc_header_image() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getDesc_header_image());
        }
        if (value.getUser_id() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getUser_id());
        }
        if (value.getTile_id() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getTile_id());
        }
        if (value.getTile_title() == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.getTile_title());
        }
        if (value.getTile_revert() == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindString(16, value.getTile_revert());
        }
        if (value.getTile_meta() == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindString(17, value.getTile_meta());
        }
        final String _tmp;
        _tmp = NotesConverter.fromArrayList(value.getNotes_type());
        if (_tmp == null) {
          stmt.bindNull(18);
        } else {
          stmt.bindString(18, _tmp);
        }
      }
    };
    this.__preparedStmtOfDeletecoursedetail = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "Delete  FROM CourseDetailTable  WHERE couseid LIKE '%' || ? AND userid =?";
        return _query;
      }
    };
    this.__preparedStmtOfDeletedata = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM CourseDetailTable";
        return _query;
      }
    };
  }

  @Override
  public long addCoursedetail(CourseDetailTable coursedetail) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfCourseDetailTable.insertAndReturnId(coursedetail);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deletecoursedetail(String courseid, String userid) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeletecoursedetail.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (courseid == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, courseid);
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
      __preparedStmtOfDeletecoursedetail.release(_stmt);
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
  public boolean isRecordExistsUserId(String userid, String course_id) {
    final String _sql = "SELECT EXISTS(SELECT * FROM CourseDetailTable WHERE couseid LIKE '%' || ? AND userid = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (course_id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, course_id);
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
  public List<CourseDetailTable> getcoursedetail(String courseid, String userid) {
    final String _sql = "SELECT * FROM CourseDetailTable  WHERE couseid = ? AND userid =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (courseid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, courseid);
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
      final int _cursorIndexOfCourseTitle = _cursor.getColumnIndexOrThrow("coursetitle");
      final int _cursorIndexOfCourseId = _cursor.getColumnIndexOrThrow("couseid");
      final int _cursorIndexOfTax = _cursor.getColumnIndexOrThrow("tax");
      final int _cursorIndexOfIsPurchased = _cursor.getColumnIndexOrThrow("is_purchased");
      final int _cursorIndexOfValidity = _cursor.getColumnIndexOrThrow("validity");
      final int _cursorIndexOfCourseSp = _cursor.getColumnIndexOrThrow("course_sp");
      final int _cursorIndexOfAuthorTitle = _cursor.getColumnIndexOrThrow("author_title");
      final int _cursorIndexOfViewType = _cursor.getColumnIndexOrThrow("view_type");
      final int _cursorIndexOfType = _cursor.getColumnIndexOrThrow("type");
      final int _cursorIndexOfMrp = _cursor.getColumnIndexOrThrow("mrp");
      final int _cursorIndexOfDescHeaderImage = _cursor.getColumnIndexOrThrow("desc_header_image");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("userid");
      final int _cursorIndexOfTileId = _cursor.getColumnIndexOrThrow("tileid");
      final int _cursorIndexOfTileTitle = _cursor.getColumnIndexOrThrow("tiletitile");
      final int _cursorIndexOfTileRevert = _cursor.getColumnIndexOrThrow("tilerevert");
      final int _cursorIndexOfTileMeta = _cursor.getColumnIndexOrThrow("tilemeta");
      final int _cursorIndexOfNotesType = _cursor.getColumnIndexOrThrow("notes_type");
      final List<CourseDetailTable> _result = new ArrayList<CourseDetailTable>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final CourseDetailTable _item;
        _item = new CourseDetailTable();
        final int _tmpAutoid;
        _tmpAutoid = _cursor.getInt(_cursorIndexOfAutoid);
        _item.setAutoid(_tmpAutoid);
        final String _tmpCourse_title;
        _tmpCourse_title = _cursor.getString(_cursorIndexOfCourseTitle);
        _item.setCourse_title(_tmpCourse_title);
        final String _tmpCourse_id;
        _tmpCourse_id = _cursor.getString(_cursorIndexOfCourseId);
        _item.setCourse_id(_tmpCourse_id);
        final String _tmpTax;
        _tmpTax = _cursor.getString(_cursorIndexOfTax);
        _item.setTax(_tmpTax);
        final String _tmpIs_purchased;
        _tmpIs_purchased = _cursor.getString(_cursorIndexOfIsPurchased);
        _item.setIs_purchased(_tmpIs_purchased);
        final String _tmpValidity;
        _tmpValidity = _cursor.getString(_cursorIndexOfValidity);
        _item.setValidity(_tmpValidity);
        final String _tmpCourse_sp;
        _tmpCourse_sp = _cursor.getString(_cursorIndexOfCourseSp);
        _item.setCourse_sp(_tmpCourse_sp);
        final String _tmpAuthor_title;
        _tmpAuthor_title = _cursor.getString(_cursorIndexOfAuthorTitle);
        _item.setAuthor_title(_tmpAuthor_title);
        final String _tmpView_type;
        _tmpView_type = _cursor.getString(_cursorIndexOfViewType);
        _item.setView_type(_tmpView_type);
        final String _tmpType;
        _tmpType = _cursor.getString(_cursorIndexOfType);
        _item.setType(_tmpType);
        final String _tmpMrp;
        _tmpMrp = _cursor.getString(_cursorIndexOfMrp);
        _item.setMrp(_tmpMrp);
        final String _tmpDesc_header_image;
        _tmpDesc_header_image = _cursor.getString(_cursorIndexOfDescHeaderImage);
        _item.setDesc_header_image(_tmpDesc_header_image);
        final String _tmpUser_id;
        _tmpUser_id = _cursor.getString(_cursorIndexOfUserId);
        _item.setUser_id(_tmpUser_id);
        final String _tmpTile_id;
        _tmpTile_id = _cursor.getString(_cursorIndexOfTileId);
        _item.setTile_id(_tmpTile_id);
        final String _tmpTile_title;
        _tmpTile_title = _cursor.getString(_cursorIndexOfTileTitle);
        _item.setTile_title(_tmpTile_title);
        final String _tmpTile_revert;
        _tmpTile_revert = _cursor.getString(_cursorIndexOfTileRevert);
        _item.setTile_revert(_tmpTile_revert);
        final String _tmpTile_meta;
        _tmpTile_meta = _cursor.getString(_cursorIndexOfTileMeta);
        _item.setTile_meta(_tmpTile_meta);
        final List<NotesType> _tmpNotes_type;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfNotesType);
        _tmpNotes_type = NotesConverter.fromString(_tmp);
        _item.setNotes_type(_tmpNotes_type);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
