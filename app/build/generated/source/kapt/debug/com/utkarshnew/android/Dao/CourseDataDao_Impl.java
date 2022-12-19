package com.utkarshnew.android.Dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.utkarshnew.android.table.CourseDataTable;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class CourseDataDao_Impl implements CourseDataDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfCourseDataTable;

  private final SharedSQLiteStatement __preparedStmtOfDeletedata;

  public CourseDataDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCourseDataTable = new EntityInsertionAdapter<CourseDataTable>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `CourseDataTable`(`autoid`,`userid`,`mainid`,`category`,`courseid`,`title`,`cover_image`,`mrp`,`course_sp`,`subject_id`,`lang_id`,`validity`,`type_id`,`valid_to`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CourseDataTable value) {
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
        if (value.getCategory() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getCategory());
        }
        if (value.getCourse_id() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getCourse_id());
        }
        if (value.getTitle() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getTitle());
        }
        if (value.getCover_image() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getCover_image());
        }
        if (value.getMrp() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getMrp());
        }
        if (value.getCourse_sp() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getCourse_sp());
        }
        if (value.getSubject_id() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getSubject_id());
        }
        if (value.getLang_id() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getLang_id());
        }
        if (value.getValidity() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getValidity());
        }
        if (value.getType_id() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getType_id());
        }
        if (value.getValid_to() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getValid_to());
        }
      }
    };
    this.__preparedStmtOfDeletedata = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM CourseDataTable";
        return _query;
      }
    };
  }

  @Override
  public long addCoursedata(CourseDataTable coursedetail) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfCourseDataTable.insertAndReturnId(coursedetail);
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
  public boolean isRecordExistsUserId(String mainid, String userid, String courseid,
      String type_id) {
    final String _sql = "SELECT EXISTS(SELECT * FROM CourseDataTable WHERE mainid = ? AND  courseid = ? AND userid = ? AND type_id = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 4);
    int _argIndex = 1;
    if (mainid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, mainid);
    }
    _argIndex = 2;
    if (courseid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, courseid);
    }
    _argIndex = 3;
    if (userid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userid);
    }
    _argIndex = 4;
    if (type_id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, type_id);
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
  public List<CourseDataTable> getcoursedatawithfilter(String main_id, String userid,
      String category) {
    final String _sql = "SELECT * FROM CourseDataTable  WHERE mainid = ? AND userid =? AND type_id=? ORDER BY autoid";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    if (main_id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, main_id);
    }
    _argIndex = 2;
    if (userid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userid);
    }
    _argIndex = 3;
    if (category == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, category);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfAutoid = _cursor.getColumnIndexOrThrow("autoid");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("userid");
      final int _cursorIndexOfMainId = _cursor.getColumnIndexOrThrow("mainid");
      final int _cursorIndexOfCategory = _cursor.getColumnIndexOrThrow("category");
      final int _cursorIndexOfCourseId = _cursor.getColumnIndexOrThrow("courseid");
      final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
      final int _cursorIndexOfCoverImage = _cursor.getColumnIndexOrThrow("cover_image");
      final int _cursorIndexOfMrp = _cursor.getColumnIndexOrThrow("mrp");
      final int _cursorIndexOfCourseSp = _cursor.getColumnIndexOrThrow("course_sp");
      final int _cursorIndexOfSubjectId = _cursor.getColumnIndexOrThrow("subject_id");
      final int _cursorIndexOfLangId = _cursor.getColumnIndexOrThrow("lang_id");
      final int _cursorIndexOfValidity = _cursor.getColumnIndexOrThrow("validity");
      final int _cursorIndexOfTypeId = _cursor.getColumnIndexOrThrow("type_id");
      final int _cursorIndexOfValidTo = _cursor.getColumnIndexOrThrow("valid_to");
      final List<CourseDataTable> _result = new ArrayList<CourseDataTable>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final CourseDataTable _item;
        _item = new CourseDataTable();
        final int _tmpAutoid;
        _tmpAutoid = _cursor.getInt(_cursorIndexOfAutoid);
        _item.setAutoid(_tmpAutoid);
        final String _tmpUser_id;
        _tmpUser_id = _cursor.getString(_cursorIndexOfUserId);
        _item.setUser_id(_tmpUser_id);
        final String _tmpMain_id;
        _tmpMain_id = _cursor.getString(_cursorIndexOfMainId);
        _item.setMain_id(_tmpMain_id);
        final String _tmpCategory;
        _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        _item.setCategory(_tmpCategory);
        final String _tmpCourse_id;
        _tmpCourse_id = _cursor.getString(_cursorIndexOfCourseId);
        _item.setCourse_id(_tmpCourse_id);
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        _item.setTitle(_tmpTitle);
        final String _tmpCover_image;
        _tmpCover_image = _cursor.getString(_cursorIndexOfCoverImage);
        _item.setCover_image(_tmpCover_image);
        final String _tmpMrp;
        _tmpMrp = _cursor.getString(_cursorIndexOfMrp);
        _item.setMrp(_tmpMrp);
        final String _tmpCourse_sp;
        _tmpCourse_sp = _cursor.getString(_cursorIndexOfCourseSp);
        _item.setCourse_sp(_tmpCourse_sp);
        final String _tmpSubject_id;
        _tmpSubject_id = _cursor.getString(_cursorIndexOfSubjectId);
        _item.setSubject_id(_tmpSubject_id);
        final String _tmpLang_id;
        _tmpLang_id = _cursor.getString(_cursorIndexOfLangId);
        _item.setLang_id(_tmpLang_id);
        final String _tmpValidity;
        _tmpValidity = _cursor.getString(_cursorIndexOfValidity);
        _item.setValidity(_tmpValidity);
        final String _tmpType_id;
        _tmpType_id = _cursor.getString(_cursorIndexOfTypeId);
        _item.setType_id(_tmpType_id);
        final String _tmpValid_to;
        _tmpValid_to = _cursor.getString(_cursorIndexOfValidTo);
        _item.setValid_to(_tmpValid_to);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<CourseDataTable> getcoursedata(String main_id, String userid) {
    final String _sql = "SELECT * FROM CourseDataTable  WHERE mainid = ? AND userid =? GROUP BY courseid ORDER BY autoid";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (main_id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, main_id);
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
      final int _cursorIndexOfCategory = _cursor.getColumnIndexOrThrow("category");
      final int _cursorIndexOfCourseId = _cursor.getColumnIndexOrThrow("courseid");
      final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
      final int _cursorIndexOfCoverImage = _cursor.getColumnIndexOrThrow("cover_image");
      final int _cursorIndexOfMrp = _cursor.getColumnIndexOrThrow("mrp");
      final int _cursorIndexOfCourseSp = _cursor.getColumnIndexOrThrow("course_sp");
      final int _cursorIndexOfSubjectId = _cursor.getColumnIndexOrThrow("subject_id");
      final int _cursorIndexOfLangId = _cursor.getColumnIndexOrThrow("lang_id");
      final int _cursorIndexOfValidity = _cursor.getColumnIndexOrThrow("validity");
      final int _cursorIndexOfTypeId = _cursor.getColumnIndexOrThrow("type_id");
      final int _cursorIndexOfValidTo = _cursor.getColumnIndexOrThrow("valid_to");
      final List<CourseDataTable> _result = new ArrayList<CourseDataTable>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final CourseDataTable _item;
        _item = new CourseDataTable();
        final int _tmpAutoid;
        _tmpAutoid = _cursor.getInt(_cursorIndexOfAutoid);
        _item.setAutoid(_tmpAutoid);
        final String _tmpUser_id;
        _tmpUser_id = _cursor.getString(_cursorIndexOfUserId);
        _item.setUser_id(_tmpUser_id);
        final String _tmpMain_id;
        _tmpMain_id = _cursor.getString(_cursorIndexOfMainId);
        _item.setMain_id(_tmpMain_id);
        final String _tmpCategory;
        _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        _item.setCategory(_tmpCategory);
        final String _tmpCourse_id;
        _tmpCourse_id = _cursor.getString(_cursorIndexOfCourseId);
        _item.setCourse_id(_tmpCourse_id);
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        _item.setTitle(_tmpTitle);
        final String _tmpCover_image;
        _tmpCover_image = _cursor.getString(_cursorIndexOfCoverImage);
        _item.setCover_image(_tmpCover_image);
        final String _tmpMrp;
        _tmpMrp = _cursor.getString(_cursorIndexOfMrp);
        _item.setMrp(_tmpMrp);
        final String _tmpCourse_sp;
        _tmpCourse_sp = _cursor.getString(_cursorIndexOfCourseSp);
        _item.setCourse_sp(_tmpCourse_sp);
        final String _tmpSubject_id;
        _tmpSubject_id = _cursor.getString(_cursorIndexOfSubjectId);
        _item.setSubject_id(_tmpSubject_id);
        final String _tmpLang_id;
        _tmpLang_id = _cursor.getString(_cursorIndexOfLangId);
        _item.setLang_id(_tmpLang_id);
        final String _tmpValidity;
        _tmpValidity = _cursor.getString(_cursorIndexOfValidity);
        _item.setValidity(_tmpValidity);
        final String _tmpType_id;
        _tmpType_id = _cursor.getString(_cursorIndexOfTypeId);
        _item.setType_id(_tmpType_id);
        final String _tmpValid_to;
        _tmpValid_to = _cursor.getString(_cursorIndexOfValidTo);
        _item.setValid_to(_tmpValid_to);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<CourseDataTable> getcoursedatafilterforpaid2(SupportSQLiteQuery query) {
    final SupportSQLiteQuery _internalQuery = query;
    final Cursor _cursor = __db.query(_internalQuery);
    try {
      final List<CourseDataTable> _result = new ArrayList<CourseDataTable>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final CourseDataTable _item;
        _item = __entityCursorConverter_comUtkarshnewAndroidTableCourseDataTable(_cursor);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
    }
  }

  private CourseDataTable __entityCursorConverter_comUtkarshnewAndroidTableCourseDataTable(
      Cursor cursor) {
    final CourseDataTable _entity;
    final int _cursorIndexOfAutoid = cursor.getColumnIndex("autoid");
    final int _cursorIndexOfUserId = cursor.getColumnIndex("userid");
    final int _cursorIndexOfMainId = cursor.getColumnIndex("mainid");
    final int _cursorIndexOfCategory = cursor.getColumnIndex("category");
    final int _cursorIndexOfCourseId = cursor.getColumnIndex("courseid");
    final int _cursorIndexOfTitle = cursor.getColumnIndex("title");
    final int _cursorIndexOfCoverImage = cursor.getColumnIndex("cover_image");
    final int _cursorIndexOfMrp = cursor.getColumnIndex("mrp");
    final int _cursorIndexOfCourseSp = cursor.getColumnIndex("course_sp");
    final int _cursorIndexOfSubjectId = cursor.getColumnIndex("subject_id");
    final int _cursorIndexOfLangId = cursor.getColumnIndex("lang_id");
    final int _cursorIndexOfValidity = cursor.getColumnIndex("validity");
    final int _cursorIndexOfTypeId = cursor.getColumnIndex("type_id");
    final int _cursorIndexOfValidTo = cursor.getColumnIndex("valid_to");
    _entity = new CourseDataTable();
    if (_cursorIndexOfAutoid != -1) {
      final int _tmpAutoid;
      _tmpAutoid = cursor.getInt(_cursorIndexOfAutoid);
      _entity.setAutoid(_tmpAutoid);
    }
    if (_cursorIndexOfUserId != -1) {
      final String _tmpUser_id;
      _tmpUser_id = cursor.getString(_cursorIndexOfUserId);
      _entity.setUser_id(_tmpUser_id);
    }
    if (_cursorIndexOfMainId != -1) {
      final String _tmpMain_id;
      _tmpMain_id = cursor.getString(_cursorIndexOfMainId);
      _entity.setMain_id(_tmpMain_id);
    }
    if (_cursorIndexOfCategory != -1) {
      final String _tmpCategory;
      _tmpCategory = cursor.getString(_cursorIndexOfCategory);
      _entity.setCategory(_tmpCategory);
    }
    if (_cursorIndexOfCourseId != -1) {
      final String _tmpCourse_id;
      _tmpCourse_id = cursor.getString(_cursorIndexOfCourseId);
      _entity.setCourse_id(_tmpCourse_id);
    }
    if (_cursorIndexOfTitle != -1) {
      final String _tmpTitle;
      _tmpTitle = cursor.getString(_cursorIndexOfTitle);
      _entity.setTitle(_tmpTitle);
    }
    if (_cursorIndexOfCoverImage != -1) {
      final String _tmpCover_image;
      _tmpCover_image = cursor.getString(_cursorIndexOfCoverImage);
      _entity.setCover_image(_tmpCover_image);
    }
    if (_cursorIndexOfMrp != -1) {
      final String _tmpMrp;
      _tmpMrp = cursor.getString(_cursorIndexOfMrp);
      _entity.setMrp(_tmpMrp);
    }
    if (_cursorIndexOfCourseSp != -1) {
      final String _tmpCourse_sp;
      _tmpCourse_sp = cursor.getString(_cursorIndexOfCourseSp);
      _entity.setCourse_sp(_tmpCourse_sp);
    }
    if (_cursorIndexOfSubjectId != -1) {
      final String _tmpSubject_id;
      _tmpSubject_id = cursor.getString(_cursorIndexOfSubjectId);
      _entity.setSubject_id(_tmpSubject_id);
    }
    if (_cursorIndexOfLangId != -1) {
      final String _tmpLang_id;
      _tmpLang_id = cursor.getString(_cursorIndexOfLangId);
      _entity.setLang_id(_tmpLang_id);
    }
    if (_cursorIndexOfValidity != -1) {
      final String _tmpValidity;
      _tmpValidity = cursor.getString(_cursorIndexOfValidity);
      _entity.setValidity(_tmpValidity);
    }
    if (_cursorIndexOfTypeId != -1) {
      final String _tmpType_id;
      _tmpType_id = cursor.getString(_cursorIndexOfTypeId);
      _entity.setType_id(_tmpType_id);
    }
    if (_cursorIndexOfValidTo != -1) {
      final String _tmpValid_to;
      _tmpValid_to = cursor.getString(_cursorIndexOfValidTo);
      _entity.setValid_to(_tmpValid_to);
    }
    return _entity;
  }
}
