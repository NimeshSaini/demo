package com.utkarshnew.android.Dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.utkarshnew.android.table.YoutubePlayerTable;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("unchecked")
public final class YoutubePlayerDao_Impl implements YoutubePlayerDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfYoutubePlayerTable;

  private final SharedSQLiteStatement __preparedStmtOfDeletedata;

  private final SharedSQLiteStatement __preparedStmtOfUpdateTime;

  public YoutubePlayerDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfYoutubePlayerTable = new EntityInsertionAdapter<YoutubePlayerTable>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `YoutubePlayerTable`(`autoid`,`youtubeid`,`youtubetime`,`userid`,`videoid`,`isaudio`,`videoname`,`valid_to`,`course_id`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, YoutubePlayerTable value) {
        stmt.bindLong(1, value.getAutoid());
        if (value.getYoutubeid() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getYoutubeid());
        }
        stmt.bindLong(3, value.getYoutubetime());
        if (value.getUserid() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getUserid());
        }
        if (value.getVideoid() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getVideoid());
        }
        if (value.getIsaudio() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getIsaudio());
        }
        if (value.getVideoname() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getVideoname());
        }
        if (value.getValid_to() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getValid_to());
        }
        if (value.getCourse_id() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getCourse_id());
        }
      }
    };
    this.__preparedStmtOfDeletedata = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM YoutubePlayerTable";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateTime = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE YoutubePlayerTable SET youtubetime = ?  WHERE videoid = ? And userid =? AND isaudio =?";
        return _query;
      }
    };
  }

  @Override
  public long addVideo(YoutubePlayerTable youtubePlayerTable) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfYoutubePlayerTable.insertAndReturnId(youtubePlayerTable);
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
  public int updateTime(Long youtubetime, String videoid, String userid, String isaudio) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateTime.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (youtubetime == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindLong(_argIndex, youtubetime);
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
      _argIndex = 4;
      if (isaudio == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, isaudio);
      }
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateTime.release(_stmt);
    }
  }

  @Override
  public YoutubePlayerTable getdata(String userid, String youtubeid) {
    final String _sql = "SELECT *  FROM YoutubePlayerTable where youtubeid = ? AND userid = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (youtubeid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, youtubeid);
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
      final int _cursorIndexOfYoutubeid = _cursor.getColumnIndexOrThrow("youtubeid");
      final int _cursorIndexOfYoutubetime = _cursor.getColumnIndexOrThrow("youtubetime");
      final int _cursorIndexOfUserid = _cursor.getColumnIndexOrThrow("userid");
      final int _cursorIndexOfVideoid = _cursor.getColumnIndexOrThrow("videoid");
      final int _cursorIndexOfIsaudio = _cursor.getColumnIndexOrThrow("isaudio");
      final int _cursorIndexOfVideoname = _cursor.getColumnIndexOrThrow("videoname");
      final int _cursorIndexOfValidTo = _cursor.getColumnIndexOrThrow("valid_to");
      final int _cursorIndexOfCourseId = _cursor.getColumnIndexOrThrow("course_id");
      final YoutubePlayerTable _result;
      if(_cursor.moveToFirst()) {
        _result = new YoutubePlayerTable();
        final int _tmpAutoid;
        _tmpAutoid = _cursor.getInt(_cursorIndexOfAutoid);
        _result.setAutoid(_tmpAutoid);
        final String _tmpYoutubeid;
        _tmpYoutubeid = _cursor.getString(_cursorIndexOfYoutubeid);
        _result.setYoutubeid(_tmpYoutubeid);
        final long _tmpYoutubetime;
        _tmpYoutubetime = _cursor.getLong(_cursorIndexOfYoutubetime);
        _result.setYoutubetime(_tmpYoutubetime);
        final String _tmpUserid;
        _tmpUserid = _cursor.getString(_cursorIndexOfUserid);
        _result.setUserid(_tmpUserid);
        final String _tmpVideoid;
        _tmpVideoid = _cursor.getString(_cursorIndexOfVideoid);
        _result.setVideoid(_tmpVideoid);
        final String _tmpIsaudio;
        _tmpIsaudio = _cursor.getString(_cursorIndexOfIsaudio);
        _result.setIsaudio(_tmpIsaudio);
        final String _tmpVideoname;
        _tmpVideoname = _cursor.getString(_cursorIndexOfVideoname);
        _result.setVideoname(_tmpVideoname);
        final String _tmpValid_to;
        _tmpValid_to = _cursor.getString(_cursorIndexOfValidTo);
        _result.setValid_to(_tmpValid_to);
        final String _tmpCourse_id;
        _tmpCourse_id = _cursor.getString(_cursorIndexOfCourseId);
        _result.setCourse_id(_tmpCourse_id);
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
  public long getyoutubedata(String userid, String videoid, String isaudio) {
    final String _sql = "SELECT youtubetime FROM YoutubePlayerTable where videoid = ? AND userid = ? AND isaudio =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
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
    _argIndex = 3;
    if (isaudio == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, isaudio);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final long _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getLong(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public boolean isUserExist(String videoid, String userid, String isaudio) {
    final String _sql = "SELECT EXISTS(SELECT * FROM YoutubePlayerTable WHERE  videoid = ? AND userid =? AND isaudio =?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
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
    _argIndex = 3;
    if (isaudio == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, isaudio);
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
