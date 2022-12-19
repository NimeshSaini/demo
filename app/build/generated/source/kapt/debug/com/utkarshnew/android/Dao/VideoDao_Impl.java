package com.utkarshnew.android.Dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.utkarshnew.android.table.VideoTable;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("unchecked")
public final class VideoDao_Impl implements VideoDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfVideoTable;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfVideoTable;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfVideoTable;

  private final SharedSQLiteStatement __preparedStmtOfDeletedata;

  private final SharedSQLiteStatement __preparedStmtOfUpdate_video_currentpos;

  public VideoDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfVideoTable = new EntityInsertionAdapter<VideoTable>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `VideoTable`(`autoid`,`video_id`,`video_name`,`user_id`,`video_currentpos`,`jw_url`,`course_id`,`valid_to`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, VideoTable value) {
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
        if (value.getVideo_currentpos() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, value.getVideo_currentpos());
        }
        if (value.getJw_url() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getJw_url());
        }
        if (value.getCourse_id() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getCourse_id());
        }
        if (value.getValid_to() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getValid_to());
        }
      }
    };
    this.__deletionAdapterOfVideoTable = new EntityDeletionOrUpdateAdapter<VideoTable>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `VideoTable` WHERE `autoid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, VideoTable value) {
        stmt.bindLong(1, value.getAutoid());
      }
    };
    this.__updateAdapterOfVideoTable = new EntityDeletionOrUpdateAdapter<VideoTable>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `VideoTable` SET `autoid` = ?,`video_id` = ?,`video_name` = ?,`user_id` = ?,`video_currentpos` = ?,`jw_url` = ?,`course_id` = ?,`valid_to` = ? WHERE `autoid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, VideoTable value) {
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
        if (value.getVideo_currentpos() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, value.getVideo_currentpos());
        }
        if (value.getJw_url() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getJw_url());
        }
        if (value.getCourse_id() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getCourse_id());
        }
        if (value.getValid_to() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getValid_to());
        }
        stmt.bindLong(9, value.getAutoid());
      }
    };
    this.__preparedStmtOfDeletedata = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM VideoTable";
        return _query;
      }
    };
    this.__preparedStmtOfUpdate_video_currentpos = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE videotable SET   video_currentpos =?   WHERE video_id = ?  AND user_id =?";
        return _query;
      }
    };
  }

  @Override
  public long addUser(VideoTable videoTable) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfVideoTable.insertAndReturnId(videoTable);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteUser(VideoTable videoTable) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfVideoTable.handle(videoTable);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int updateUser(VideoTable audioTable) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfVideoTable.handle(audioTable);
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
  public void update_video_currentpos(String videoid, String userid, Long getCurrentPosition) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdate_video_currentpos.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (getCurrentPosition == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindLong(_argIndex, getCurrentPosition);
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
      __preparedStmtOfUpdate_video_currentpos.release(_stmt);
    }
  }

  @Override
  public void update_videotime(String videoid, String userid, Long getCurrentPosition) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdate_video_currentpos.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (getCurrentPosition == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindLong(_argIndex, getCurrentPosition);
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
      __preparedStmtOfUpdate_video_currentpos.release(_stmt);
    }
  }

  @Override
  public VideoTable getuser(String videoid, String userid) {
    final String _sql = "SELECT * FROM VideoTable  WHERE video_id = ? AND user_id =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (videoid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, videoid);
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
      final int _cursorIndexOfVideoId = _cursor.getColumnIndexOrThrow("video_id");
      final int _cursorIndexOfVideoName = _cursor.getColumnIndexOrThrow("video_name");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final int _cursorIndexOfVideoCurrentpos = _cursor.getColumnIndexOrThrow("video_currentpos");
      final int _cursorIndexOfJwUrl = _cursor.getColumnIndexOrThrow("jw_url");
      final int _cursorIndexOfCourseId = _cursor.getColumnIndexOrThrow("course_id");
      final int _cursorIndexOfValidTo = _cursor.getColumnIndexOrThrow("valid_to");
      final VideoTable _result;
      if(_cursor.moveToFirst()) {
        _result = new VideoTable();
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
        final Long _tmpVideo_currentpos;
        if (_cursor.isNull(_cursorIndexOfVideoCurrentpos)) {
          _tmpVideo_currentpos = null;
        } else {
          _tmpVideo_currentpos = _cursor.getLong(_cursorIndexOfVideoCurrentpos);
        }
        _result.setVideo_currentpos(_tmpVideo_currentpos);
        final String _tmpJw_url;
        _tmpJw_url = _cursor.getString(_cursorIndexOfJwUrl);
        _result.setJw_url(_tmpJw_url);
        final String _tmpCourse_id;
        _tmpCourse_id = _cursor.getString(_cursorIndexOfCourseId);
        _result.setCourse_id(_tmpCourse_id);
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

  @Override
  public boolean isvideo_exit(String video_id, String user_id) {
    final String _sql = "SELECT EXISTS(SELECT * FROM VideoTable WHERE video_id = ?   AND user_id = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (video_id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, video_id);
    }
    _argIndex = 2;
    if (user_id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, user_id);
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
