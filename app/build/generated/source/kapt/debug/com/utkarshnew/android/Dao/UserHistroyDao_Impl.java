package com.utkarshnew.android.Dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.utkarshnew.android.table.UserHistroyTable;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class UserHistroyDao_Impl implements UserHistroyDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfUserHistroyTable;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfUserHistroyTable;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfUserHistroyTable;

  private final SharedSQLiteStatement __preparedStmtOfDeletedata;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  private final SharedSQLiteStatement __preparedStmtOfDelete_1;

  private final SharedSQLiteStatement __preparedStmtOfDelete_right;

  private final SharedSQLiteStatement __preparedStmtOfDelete_via_user;

  private final SharedSQLiteStatement __preparedStmtOfDelete_viavideo_id;

  private final SharedSQLiteStatement __preparedStmtOfDeletevideo_id;

  public UserHistroyDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserHistroyTable = new EntityInsertionAdapter<UserHistroyTable>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `UserHistroyTable`(`autoid`,`video_id`,`video_name`,`user_id`,`type`,`pdf_name`,`youtube_url`,`pdf_url`,`current_time`,`course_id`,`valid_to`,`coursename`,`tileid`,`testid`,`testname`,`topicname`,`url`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UserHistroyTable value) {
        stmt.bindLong(1, value.getAutoid());
        if (value.getVideo_id() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getVideo_id());
        }
        if (value.getVideo_name() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getVideo_name());
        }
        if (value.getUser_id() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getUser_id());
        }
        if (value.getType() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getType());
        }
        if (value.getPdf_name() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getPdf_name());
        }
        if (value.getYoutube_url() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getYoutube_url());
        }
        if (value.getPdf_url() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getPdf_url());
        }
        if (value.getCurrent_time() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getCurrent_time());
        }
        if (value.getCourse_id() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getCourse_id());
        }
        if (value.getValid_to() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getValid_to());
        }
        if (value.getCoursename() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getCoursename());
        }
        if (value.getTileid() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getTileid());
        }
        if (value.getTestid() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getTestid());
        }
        if (value.getTestname() == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.getTestname());
        }
        if (value.getTopicname() == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindString(16, value.getTopicname());
        }
        if (value.getUrl() == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindString(17, value.getUrl());
        }
      }
    };
    this.__deletionAdapterOfUserHistroyTable = new EntityDeletionOrUpdateAdapter<UserHistroyTable>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `UserHistroyTable` WHERE `autoid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UserHistroyTable value) {
        stmt.bindLong(1, value.getAutoid());
      }
    };
    this.__updateAdapterOfUserHistroyTable = new EntityDeletionOrUpdateAdapter<UserHistroyTable>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `UserHistroyTable` SET `autoid` = ?,`video_id` = ?,`video_name` = ?,`user_id` = ?,`type` = ?,`pdf_name` = ?,`youtube_url` = ?,`pdf_url` = ?,`current_time` = ?,`course_id` = ?,`valid_to` = ?,`coursename` = ?,`tileid` = ?,`testid` = ?,`testname` = ?,`topicname` = ?,`url` = ? WHERE `autoid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UserHistroyTable value) {
        stmt.bindLong(1, value.getAutoid());
        if (value.getVideo_id() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getVideo_id());
        }
        if (value.getVideo_name() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getVideo_name());
        }
        if (value.getUser_id() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getUser_id());
        }
        if (value.getType() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getType());
        }
        if (value.getPdf_name() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getPdf_name());
        }
        if (value.getYoutube_url() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getYoutube_url());
        }
        if (value.getPdf_url() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getPdf_url());
        }
        if (value.getCurrent_time() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getCurrent_time());
        }
        if (value.getCourse_id() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getCourse_id());
        }
        if (value.getValid_to() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getValid_to());
        }
        if (value.getCoursename() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getCoursename());
        }
        if (value.getTileid() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getTileid());
        }
        if (value.getTestid() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getTestid());
        }
        if (value.getTestname() == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.getTestname());
        }
        if (value.getTopicname() == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindString(16, value.getTopicname());
        }
        if (value.getUrl() == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindString(17, value.getUrl());
        }
        stmt.bindLong(18, value.getAutoid());
      }
    };
    this.__preparedStmtOfDeletedata = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM UserHistroyTable";
        return _query;
      }
    };
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM UserHistroyTable WHERE `current_time` < (SELECT `current_time` FROM UserHistroyTable ORDER BY `current_time` DESC LIMIT 1 OFFSET 1000)";
        return _query;
      }
    };
    this.__preparedStmtOfDelete_1 = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "Delete from UserHistroyTable where course_id LIKE ? || '%'  AND user_id =?";
        return _query;
      }
    };
    this.__preparedStmtOfDelete_right = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "Delete from UserHistroyTable where course_id   LIKE '%' || ?     AND user_id =?";
        return _query;
      }
    };
    this.__preparedStmtOfDelete_via_user = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "Delete from UserHistroyTable where user_id =?";
        return _query;
      }
    };
    this.__preparedStmtOfDelete_viavideo_id = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "Delete from UserHistroyTable where course_id = ? AND video_id =? AND user_id =?";
        return _query;
      }
    };
    this.__preparedStmtOfDeletevideo_id = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "Delete from UserHistroyTable where user_id = ? AND video_id =?";
        return _query;
      }
    };
  }

  @Override
  public long addUser(UserHistroyTable userHistroyTable) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfUserHistroyTable.insertAndReturnId(userHistroyTable);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteUser(UserHistroyTable userHistroyTable) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfUserHistroyTable.handle(userHistroyTable);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int updateUser(UserHistroyTable userHistroyTable) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfUserHistroyTable.handle(userHistroyTable);
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
  public void delete() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDelete.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDelete.release(_stmt);
    }
  }

  @Override
  public void delete(String courseid, String Userid) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDelete_1.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (courseid == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, courseid);
      }
      _argIndex = 2;
      if (Userid == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, Userid);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDelete_1.release(_stmt);
    }
  }

  @Override
  public void delete_right(String courseid, String Userid) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDelete_right.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (courseid == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, courseid);
      }
      _argIndex = 2;
      if (Userid == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, Userid);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDelete_right.release(_stmt);
    }
  }

  @Override
  public void delete_via_user(String Userid) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDelete_via_user.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (Userid == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, Userid);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDelete_via_user.release(_stmt);
    }
  }

  @Override
  public void delete_viavideo_id(String videoid, String course_id, String userid) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDelete_viavideo_id.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (course_id == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, course_id);
      }
      _argIndex = 2;
      if (videoid == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, videoid);
      }
      _argIndex = 3;
      if (userid == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, userid);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDelete_viavideo_id.release(_stmt);
    }
  }

  @Override
  public void deletevideo_id(String videoid, String userid) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeletevideo_id.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (userid == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, userid);
      }
      _argIndex = 2;
      if (videoid == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, videoid);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeletevideo_id.release(_stmt);
    }
  }

  @Override
  public List<UserHistroyTable> getallhistory(String user_id) {
    final String _sql = "SELECT * FROM UserHistroyTable  WHERE user_id = ?  ORDER BY `current_time` DESC   LIMIT 1000 OFFSET 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (user_id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, user_id);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfAutoid = _cursor.getColumnIndexOrThrow("autoid");
      final int _cursorIndexOfVideoId = _cursor.getColumnIndexOrThrow("video_id");
      final int _cursorIndexOfVideoName = _cursor.getColumnIndexOrThrow("video_name");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final int _cursorIndexOfType = _cursor.getColumnIndexOrThrow("type");
      final int _cursorIndexOfPdfName = _cursor.getColumnIndexOrThrow("pdf_name");
      final int _cursorIndexOfYoutubeUrl = _cursor.getColumnIndexOrThrow("youtube_url");
      final int _cursorIndexOfPdfUrl = _cursor.getColumnIndexOrThrow("pdf_url");
      final int _cursorIndexOfCurrentTime = _cursor.getColumnIndexOrThrow("current_time");
      final int _cursorIndexOfCourseId = _cursor.getColumnIndexOrThrow("course_id");
      final int _cursorIndexOfValidTo = _cursor.getColumnIndexOrThrow("valid_to");
      final int _cursorIndexOfCoursename = _cursor.getColumnIndexOrThrow("coursename");
      final int _cursorIndexOfTileid = _cursor.getColumnIndexOrThrow("tileid");
      final int _cursorIndexOfTestid = _cursor.getColumnIndexOrThrow("testid");
      final int _cursorIndexOfTestname = _cursor.getColumnIndexOrThrow("testname");
      final int _cursorIndexOfTopicname = _cursor.getColumnIndexOrThrow("topicname");
      final int _cursorIndexOfUrl = _cursor.getColumnIndexOrThrow("url");
      final List<UserHistroyTable> _result = new ArrayList<UserHistroyTable>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final UserHistroyTable _item;
        _item = new UserHistroyTable();
        final int _tmpAutoid;
        _tmpAutoid = _cursor.getInt(_cursorIndexOfAutoid);
        _item.setAutoid(_tmpAutoid);
        final String _tmpVideo_id;
        _tmpVideo_id = _cursor.getString(_cursorIndexOfVideoId);
        _item.setVideo_id(_tmpVideo_id);
        final String _tmpVideo_name;
        _tmpVideo_name = _cursor.getString(_cursorIndexOfVideoName);
        _item.setVideo_name(_tmpVideo_name);
        final String _tmpUser_id;
        _tmpUser_id = _cursor.getString(_cursorIndexOfUserId);
        _item.setUser_id(_tmpUser_id);
        final String _tmpType;
        _tmpType = _cursor.getString(_cursorIndexOfType);
        _item.setType(_tmpType);
        final String _tmpPdf_name;
        _tmpPdf_name = _cursor.getString(_cursorIndexOfPdfName);
        _item.setPdf_name(_tmpPdf_name);
        final String _tmpYoutube_url;
        _tmpYoutube_url = _cursor.getString(_cursorIndexOfYoutubeUrl);
        _item.setYoutube_url(_tmpYoutube_url);
        final String _tmpPdf_url;
        _tmpPdf_url = _cursor.getString(_cursorIndexOfPdfUrl);
        _item.setPdf_url(_tmpPdf_url);
        final String _tmpCurrent_time;
        _tmpCurrent_time = _cursor.getString(_cursorIndexOfCurrentTime);
        _item.setCurrent_time(_tmpCurrent_time);
        final String _tmpCourse_id;
        _tmpCourse_id = _cursor.getString(_cursorIndexOfCourseId);
        _item.setCourse_id(_tmpCourse_id);
        final String _tmpValid_to;
        _tmpValid_to = _cursor.getString(_cursorIndexOfValidTo);
        _item.setValid_to(_tmpValid_to);
        final String _tmpCoursename;
        _tmpCoursename = _cursor.getString(_cursorIndexOfCoursename);
        _item.setCoursename(_tmpCoursename);
        final String _tmpTileid;
        _tmpTileid = _cursor.getString(_cursorIndexOfTileid);
        _item.setTileid(_tmpTileid);
        final String _tmpTestid;
        _tmpTestid = _cursor.getString(_cursorIndexOfTestid);
        _item.setTestid(_tmpTestid);
        final String _tmpTestname;
        _tmpTestname = _cursor.getString(_cursorIndexOfTestname);
        _item.setTestname(_tmpTestname);
        final String _tmpTopicname;
        _tmpTopicname = _cursor.getString(_cursorIndexOfTopicname);
        _item.setTopicname(_tmpTopicname);
        final String _tmpUrl;
        _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
        _item.setUrl(_tmpUrl);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public UserHistroyTable user_hisorydao(String user_id, String videoid) {
    final String _sql = "SELECT * FROM UserHistroyTable  WHERE user_id = ? AND video_id =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (user_id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, user_id);
    }
    _argIndex = 2;
    if (videoid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, videoid);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfAutoid = _cursor.getColumnIndexOrThrow("autoid");
      final int _cursorIndexOfVideoId = _cursor.getColumnIndexOrThrow("video_id");
      final int _cursorIndexOfVideoName = _cursor.getColumnIndexOrThrow("video_name");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final int _cursorIndexOfType = _cursor.getColumnIndexOrThrow("type");
      final int _cursorIndexOfPdfName = _cursor.getColumnIndexOrThrow("pdf_name");
      final int _cursorIndexOfYoutubeUrl = _cursor.getColumnIndexOrThrow("youtube_url");
      final int _cursorIndexOfPdfUrl = _cursor.getColumnIndexOrThrow("pdf_url");
      final int _cursorIndexOfCurrentTime = _cursor.getColumnIndexOrThrow("current_time");
      final int _cursorIndexOfCourseId = _cursor.getColumnIndexOrThrow("course_id");
      final int _cursorIndexOfValidTo = _cursor.getColumnIndexOrThrow("valid_to");
      final int _cursorIndexOfCoursename = _cursor.getColumnIndexOrThrow("coursename");
      final int _cursorIndexOfTileid = _cursor.getColumnIndexOrThrow("tileid");
      final int _cursorIndexOfTestid = _cursor.getColumnIndexOrThrow("testid");
      final int _cursorIndexOfTestname = _cursor.getColumnIndexOrThrow("testname");
      final int _cursorIndexOfTopicname = _cursor.getColumnIndexOrThrow("topicname");
      final int _cursorIndexOfUrl = _cursor.getColumnIndexOrThrow("url");
      final UserHistroyTable _result;
      if(_cursor.moveToFirst()) {
        _result = new UserHistroyTable();
        final int _tmpAutoid;
        _tmpAutoid = _cursor.getInt(_cursorIndexOfAutoid);
        _result.setAutoid(_tmpAutoid);
        final String _tmpVideo_id;
        _tmpVideo_id = _cursor.getString(_cursorIndexOfVideoId);
        _result.setVideo_id(_tmpVideo_id);
        final String _tmpVideo_name;
        _tmpVideo_name = _cursor.getString(_cursorIndexOfVideoName);
        _result.setVideo_name(_tmpVideo_name);
        final String _tmpUser_id;
        _tmpUser_id = _cursor.getString(_cursorIndexOfUserId);
        _result.setUser_id(_tmpUser_id);
        final String _tmpType;
        _tmpType = _cursor.getString(_cursorIndexOfType);
        _result.setType(_tmpType);
        final String _tmpPdf_name;
        _tmpPdf_name = _cursor.getString(_cursorIndexOfPdfName);
        _result.setPdf_name(_tmpPdf_name);
        final String _tmpYoutube_url;
        _tmpYoutube_url = _cursor.getString(_cursorIndexOfYoutubeUrl);
        _result.setYoutube_url(_tmpYoutube_url);
        final String _tmpPdf_url;
        _tmpPdf_url = _cursor.getString(_cursorIndexOfPdfUrl);
        _result.setPdf_url(_tmpPdf_url);
        final String _tmpCurrent_time;
        _tmpCurrent_time = _cursor.getString(_cursorIndexOfCurrentTime);
        _result.setCurrent_time(_tmpCurrent_time);
        final String _tmpCourse_id;
        _tmpCourse_id = _cursor.getString(_cursorIndexOfCourseId);
        _result.setCourse_id(_tmpCourse_id);
        final String _tmpValid_to;
        _tmpValid_to = _cursor.getString(_cursorIndexOfValidTo);
        _result.setValid_to(_tmpValid_to);
        final String _tmpCoursename;
        _tmpCoursename = _cursor.getString(_cursorIndexOfCoursename);
        _result.setCoursename(_tmpCoursename);
        final String _tmpTileid;
        _tmpTileid = _cursor.getString(_cursorIndexOfTileid);
        _result.setTileid(_tmpTileid);
        final String _tmpTestid;
        _tmpTestid = _cursor.getString(_cursorIndexOfTestid);
        _result.setTestid(_tmpTestid);
        final String _tmpTestname;
        _tmpTestname = _cursor.getString(_cursorIndexOfTestname);
        _result.setTestname(_tmpTestname);
        final String _tmpTopicname;
        _tmpTopicname = _cursor.getString(_cursorIndexOfTopicname);
        _result.setTopicname(_tmpTopicname);
        final String _tmpUrl;
        _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
        _result.setUrl(_tmpUrl);
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
  public UserHistroyTable user_history(String user_id, String videoid) {
    final String _sql = "SELECT * FROM UserHistroyTable  WHERE user_id = ?  AND video_id =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (user_id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, user_id);
    }
    _argIndex = 2;
    if (videoid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, videoid);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfAutoid = _cursor.getColumnIndexOrThrow("autoid");
      final int _cursorIndexOfVideoId = _cursor.getColumnIndexOrThrow("video_id");
      final int _cursorIndexOfVideoName = _cursor.getColumnIndexOrThrow("video_name");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final int _cursorIndexOfType = _cursor.getColumnIndexOrThrow("type");
      final int _cursorIndexOfPdfName = _cursor.getColumnIndexOrThrow("pdf_name");
      final int _cursorIndexOfYoutubeUrl = _cursor.getColumnIndexOrThrow("youtube_url");
      final int _cursorIndexOfPdfUrl = _cursor.getColumnIndexOrThrow("pdf_url");
      final int _cursorIndexOfCurrentTime = _cursor.getColumnIndexOrThrow("current_time");
      final int _cursorIndexOfCourseId = _cursor.getColumnIndexOrThrow("course_id");
      final int _cursorIndexOfValidTo = _cursor.getColumnIndexOrThrow("valid_to");
      final int _cursorIndexOfCoursename = _cursor.getColumnIndexOrThrow("coursename");
      final int _cursorIndexOfTileid = _cursor.getColumnIndexOrThrow("tileid");
      final int _cursorIndexOfTestid = _cursor.getColumnIndexOrThrow("testid");
      final int _cursorIndexOfTestname = _cursor.getColumnIndexOrThrow("testname");
      final int _cursorIndexOfTopicname = _cursor.getColumnIndexOrThrow("topicname");
      final int _cursorIndexOfUrl = _cursor.getColumnIndexOrThrow("url");
      final UserHistroyTable _result;
      if(_cursor.moveToFirst()) {
        _result = new UserHistroyTable();
        final int _tmpAutoid;
        _tmpAutoid = _cursor.getInt(_cursorIndexOfAutoid);
        _result.setAutoid(_tmpAutoid);
        final String _tmpVideo_id;
        _tmpVideo_id = _cursor.getString(_cursorIndexOfVideoId);
        _result.setVideo_id(_tmpVideo_id);
        final String _tmpVideo_name;
        _tmpVideo_name = _cursor.getString(_cursorIndexOfVideoName);
        _result.setVideo_name(_tmpVideo_name);
        final String _tmpUser_id;
        _tmpUser_id = _cursor.getString(_cursorIndexOfUserId);
        _result.setUser_id(_tmpUser_id);
        final String _tmpType;
        _tmpType = _cursor.getString(_cursorIndexOfType);
        _result.setType(_tmpType);
        final String _tmpPdf_name;
        _tmpPdf_name = _cursor.getString(_cursorIndexOfPdfName);
        _result.setPdf_name(_tmpPdf_name);
        final String _tmpYoutube_url;
        _tmpYoutube_url = _cursor.getString(_cursorIndexOfYoutubeUrl);
        _result.setYoutube_url(_tmpYoutube_url);
        final String _tmpPdf_url;
        _tmpPdf_url = _cursor.getString(_cursorIndexOfPdfUrl);
        _result.setPdf_url(_tmpPdf_url);
        final String _tmpCurrent_time;
        _tmpCurrent_time = _cursor.getString(_cursorIndexOfCurrentTime);
        _result.setCurrent_time(_tmpCurrent_time);
        final String _tmpCourse_id;
        _tmpCourse_id = _cursor.getString(_cursorIndexOfCourseId);
        _result.setCourse_id(_tmpCourse_id);
        final String _tmpValid_to;
        _tmpValid_to = _cursor.getString(_cursorIndexOfValidTo);
        _result.setValid_to(_tmpValid_to);
        final String _tmpCoursename;
        _tmpCoursename = _cursor.getString(_cursorIndexOfCoursename);
        _result.setCoursename(_tmpCoursename);
        final String _tmpTileid;
        _tmpTileid = _cursor.getString(_cursorIndexOfTileid);
        _result.setTileid(_tmpTileid);
        final String _tmpTestid;
        _tmpTestid = _cursor.getString(_cursorIndexOfTestid);
        _result.setTestid(_tmpTestid);
        final String _tmpTestname;
        _tmpTestname = _cursor.getString(_cursorIndexOfTestname);
        _result.setTestname(_tmpTestname);
        final String _tmpTopicname;
        _tmpTopicname = _cursor.getString(_cursorIndexOfTopicname);
        _result.setTopicname(_tmpTopicname);
        final String _tmpUrl;
        _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
        _result.setUrl(_tmpUrl);
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
  public List<String> getlikehistiry(String user_id, String courseid) {
    final String _sql = "SELECT  DISTINCT course_id FROM UserHistroyTable where course_id LIKE '%' || ? || '%'  AND user_id =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (courseid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, courseid);
    }
    _argIndex = 2;
    if (user_id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, user_id);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final List<String> _result = new ArrayList<String>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final String _item;
        _item = _cursor.getString(0);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public boolean isRecordExistsUserId(String course_id, String userid) {
    final String _sql = "SELECT EXISTS(SELECT * FROM UserHistroyTable WHERE course_id = ? And user_id=?)";
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
  public List<String> courseids(String userid) {
    final String _sql = "SELECT  DISTINCT course_id  FROM UserHistroyTable  where  user_id=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (userid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userid);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final List<String> _result = new ArrayList<String>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final String _item;
        _item = _cursor.getString(0);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
