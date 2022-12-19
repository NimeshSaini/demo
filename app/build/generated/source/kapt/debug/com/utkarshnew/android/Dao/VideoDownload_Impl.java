package com.utkarshnew.android.Dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.utkarshnew.android.table.VideosDownload;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class VideoDownload_Impl implements VideoDownload {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfVideosDownload;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfVideosDownload;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfVideosDownload;

  private final SharedSQLiteStatement __preparedStmtOfUpdate_videofilelenght;

  private final SharedSQLiteStatement __preparedStmtOfDelete_viavideoid;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  private final SharedSQLiteStatement __preparedStmtOfDelete_course_id;

  private final SharedSQLiteStatement __preparedStmtOfUpdate_videostatus;

  private final SharedSQLiteStatement __preparedStmtOfUpdate_videostatus_1;

  private final SharedSQLiteStatement __preparedStmtOfUpdatevideo_name;

  private final SharedSQLiteStatement __preparedStmtOfUpdate_title;

  private final SharedSQLiteStatement __preparedStmtOfUpdate_progress;

  private final SharedSQLiteStatement __preparedStmtOfUpdate_videocurrenttime;

  private final SharedSQLiteStatement __preparedStmtOfDeletedata;

  private final SharedSQLiteStatement __preparedStmtOfUpdate_pos;

  private final SharedSQLiteStatement __preparedStmtOfDelete_user_id;

  public VideoDownload_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfVideosDownload = new EntityInsertionAdapter<VideosDownload>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `VideoDownload`(`autoid`,`video_id`,`video_name`,`originalFileLengthString`,`videotime`,`total`,`lengthInMb`,`percentage`,`user_id`,`course_id`,`valid_to`,`position`,`mp4_download_url`,`video_status`,`video_enc`,`video_token`,`thumbnail_url`,`is_complete`,`videoCurrentPosition`,`jw_url`,`is_selected`,`video_history`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, VideosDownload value) {
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
        if (value.getOriginalFileLengthString() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getOriginalFileLengthString());
        }
        if (value.getVideotime() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getVideotime());
        }
        if (value.getToal_downloadlocale() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindLong(6, value.getToal_downloadlocale());
        }
        if (value.getLengthInMb() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getLengthInMb());
        }
        stmt.bindLong(8, value.getPercentage());
        if (value.getUser_id() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getUser_id());
        }
        if (value.getCourse_id() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getCourse_id());
        }
        stmt.bindLong(11, value.getValid_to());
        stmt.bindLong(12, value.getPosition());
        if (value.getMp4_download_url() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getMp4_download_url());
        }
        if (value.getVideo_status() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getVideo_status());
        }
        if (value.getVideo_enc() == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.getVideo_enc());
        }
        if (value.getVideo_token() == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindString(16, value.getVideo_token());
        }
        if (value.getThumbnail_url() == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindString(17, value.getThumbnail_url());
        }
        if (value.getIs_complete() == null) {
          stmt.bindNull(18);
        } else {
          stmt.bindString(18, value.getIs_complete());
        }
        if (value.getVideoCurrentPosition() == null) {
          stmt.bindNull(19);
        } else {
          stmt.bindLong(19, value.getVideoCurrentPosition());
        }
        if (value.getJw_url() == null) {
          stmt.bindNull(20);
        } else {
          stmt.bindString(20, value.getJw_url());
        }
        if (value.getIs_selected() == null) {
          stmt.bindNull(21);
        } else {
          stmt.bindString(21, value.getIs_selected());
        }
        if (value.getVideo_history() == null) {
          stmt.bindNull(22);
        } else {
          stmt.bindString(22, value.getVideo_history());
        }
      }
    };
    this.__deletionAdapterOfVideosDownload = new EntityDeletionOrUpdateAdapter<VideosDownload>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `VideoDownload` WHERE `autoid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, VideosDownload value) {
        stmt.bindLong(1, value.getAutoid());
      }
    };
    this.__updateAdapterOfVideosDownload = new EntityDeletionOrUpdateAdapter<VideosDownload>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `VideoDownload` SET `autoid` = ?,`video_id` = ?,`video_name` = ?,`originalFileLengthString` = ?,`videotime` = ?,`total` = ?,`lengthInMb` = ?,`percentage` = ?,`user_id` = ?,`course_id` = ?,`valid_to` = ?,`position` = ?,`mp4_download_url` = ?,`video_status` = ?,`video_enc` = ?,`video_token` = ?,`thumbnail_url` = ?,`is_complete` = ?,`videoCurrentPosition` = ?,`jw_url` = ?,`is_selected` = ?,`video_history` = ? WHERE `autoid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, VideosDownload value) {
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
        if (value.getOriginalFileLengthString() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getOriginalFileLengthString());
        }
        if (value.getVideotime() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getVideotime());
        }
        if (value.getToal_downloadlocale() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindLong(6, value.getToal_downloadlocale());
        }
        if (value.getLengthInMb() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getLengthInMb());
        }
        stmt.bindLong(8, value.getPercentage());
        if (value.getUser_id() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getUser_id());
        }
        if (value.getCourse_id() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getCourse_id());
        }
        stmt.bindLong(11, value.getValid_to());
        stmt.bindLong(12, value.getPosition());
        if (value.getMp4_download_url() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getMp4_download_url());
        }
        if (value.getVideo_status() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getVideo_status());
        }
        if (value.getVideo_enc() == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.getVideo_enc());
        }
        if (value.getVideo_token() == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindString(16, value.getVideo_token());
        }
        if (value.getThumbnail_url() == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindString(17, value.getThumbnail_url());
        }
        if (value.getIs_complete() == null) {
          stmt.bindNull(18);
        } else {
          stmt.bindString(18, value.getIs_complete());
        }
        if (value.getVideoCurrentPosition() == null) {
          stmt.bindNull(19);
        } else {
          stmt.bindLong(19, value.getVideoCurrentPosition());
        }
        if (value.getJw_url() == null) {
          stmt.bindNull(20);
        } else {
          stmt.bindString(20, value.getJw_url());
        }
        if (value.getIs_selected() == null) {
          stmt.bindNull(21);
        } else {
          stmt.bindString(21, value.getIs_selected());
        }
        if (value.getVideo_history() == null) {
          stmt.bindNull(22);
        } else {
          stmt.bindString(22, value.getVideo_history());
        }
        stmt.bindLong(23, value.getAutoid());
      }
    };
    this.__preparedStmtOfUpdate_videofilelenght = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE VideoDownload SET   mp4_download_url=? , videotime=? , originalFileLengthString=?  , video_status=?  , total =? , lengthInMb =? , percentage =?  , is_complete =? WHERE video_id = ? AND is_complete = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDelete_viavideoid = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "Delete from VideoDownload where video_id = ? AND user_id=?";
        return _query;
      }
    };
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "Delete from VideoDownload where video_id = ? AND course_id=? AND user_id=?";
        return _query;
      }
    };
    this.__preparedStmtOfDelete_course_id = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "Delete from VideoDownload where video_id = ? AND course_id =?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdate_videostatus = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE VideoDownload SET  video_status =? , percentage =?  , is_complete =?  WHERE video_id = ? ";
        return _query;
      }
    };
    this.__preparedStmtOfUpdate_videostatus_1 = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE VideoDownload SET  video_status =? , user_id =?    WHERE video_id = ? ";
        return _query;
      }
    };
    this.__preparedStmtOfUpdatevideo_name = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE VideoDownload SET   thumbnail_url =?,jw_url =? , video_history=? , video_name =? WHERE video_id = ? AND  user_id =? ";
        return _query;
      }
    };
    this.__preparedStmtOfUpdate_title = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE VideoDownload SET    video_name =? WHERE video_id = ? AND  user_id =? ";
        return _query;
      }
    };
    this.__preparedStmtOfUpdate_progress = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE VideoDownload SET  video_status =? , is_complete =?  WHERE video_id = ?  AND user_id =?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdate_videocurrenttime = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE VideoDownload SET   videoCurrentPosition =? ,  videotime = ?  WHERE video_id = ?    AND user_id =?";
        return _query;
      }
    };
    this.__preparedStmtOfDeletedata = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM VideoDownload";
        return _query;
      }
    };
    this.__preparedStmtOfUpdate_pos = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE VideoDownload SET   position =?   WHERE video_id = ?  AND user_id =?";
        return _query;
      }
    };
    this.__preparedStmtOfDelete_user_id = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "Delete from VideoDownload where user_id = ?";
        return _query;
      }
    };
  }

  @Override
  public long addUser(VideosDownload videosDownload) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfVideosDownload.insertAndReturnId(videosDownload);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteUser(VideosDownload videosDownload) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfVideosDownload.handle(videosDownload);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int updateUser(VideosDownload videosDownload) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfVideosDownload.handle(videosDownload);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int update_videofilelenght(String testid, String originalFileLengthString, Long total,
      String lengthInMb, int percentage, String iscomplete, String status, String videotime,
      String mp4ulr) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdate_videofilelenght.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (mp4ulr == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, mp4ulr);
      }
      _argIndex = 2;
      if (videotime == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, videotime);
      }
      _argIndex = 3;
      if (originalFileLengthString == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, originalFileLengthString);
      }
      _argIndex = 4;
      if (status == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, status);
      }
      _argIndex = 5;
      if (total == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindLong(_argIndex, total);
      }
      _argIndex = 6;
      if (lengthInMb == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, lengthInMb);
      }
      _argIndex = 7;
      _stmt.bindLong(_argIndex, percentage);
      _argIndex = 8;
      if (iscomplete == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, iscomplete);
      }
      _argIndex = 9;
      if (testid == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, testid);
      }
      _argIndex = 10;
      if (iscomplete == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, iscomplete);
      }
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdate_videofilelenght.release(_stmt);
    }
  }

  @Override
  public void delete_viavideoid(String videoid, String userid) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDelete_viavideoid.acquire();
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
      __preparedStmtOfDelete_viavideoid.release(_stmt);
    }
  }

  @Override
  public void delete(String videoid, String course_id, String userid) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDelete.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (videoid == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, videoid);
      }
      _argIndex = 2;
      if (course_id == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, course_id);
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
      __preparedStmtOfDelete.release(_stmt);
    }
  }

  @Override
  public void delete_course_id(String testid, String course_id) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDelete_course_id.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (testid == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, testid);
      }
      _argIndex = 2;
      if (course_id == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, course_id);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDelete_course_id.release(_stmt);
    }
  }

  @Override
  public void update_videostatus(String testid, String isconpelte, String status, int percentage) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdate_videostatus.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (status == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, status);
      }
      _argIndex = 2;
      _stmt.bindLong(_argIndex, percentage);
      _argIndex = 3;
      if (isconpelte == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, isconpelte);
      }
      _argIndex = 4;
      if (testid == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, testid);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdate_videostatus.release(_stmt);
    }
  }

  @Override
  public void update_videostatus(String videoid, String status, String user_id) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdate_videostatus_1.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (status == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, status);
      }
      _argIndex = 2;
      if (user_id == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, user_id);
      }
      _argIndex = 3;
      if (videoid == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, videoid);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdate_videostatus_1.release(_stmt);
    }
  }

  @Override
  public void updatevideo_name(String video_name, String video_id, String user_id, String jwurl,
      String thumb, String video_history) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdatevideo_name.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (thumb == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, thumb);
      }
      _argIndex = 2;
      if (jwurl == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, jwurl);
      }
      _argIndex = 3;
      if (video_history == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, video_history);
      }
      _argIndex = 4;
      if (video_name == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, video_name);
      }
      _argIndex = 5;
      if (video_id == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, video_id);
      }
      _argIndex = 6;
      if (user_id == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, user_id);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdatevideo_name.release(_stmt);
    }
  }

  @Override
  public void update_title(String video_name, String video_id, String user_id) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdate_title.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (video_name == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, video_name);
      }
      _argIndex = 2;
      if (video_id == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, video_id);
      }
      _argIndex = 3;
      if (user_id == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, user_id);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdate_title.release(_stmt);
    }
  }

  @Override
  public void update_progress(String videoid, String isconpelte, String status, String userid) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdate_progress.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (status == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, status);
      }
      _argIndex = 2;
      if (isconpelte == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, isconpelte);
      }
      _argIndex = 3;
      if (videoid == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, videoid);
      }
      _argIndex = 4;
      if (userid == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, userid);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdate_progress.release(_stmt);
    }
  }

  @Override
  public void update_videocurrenttime(String videoid, String userid, Long getCurrentPosition,
      String video_time) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdate_videocurrenttime.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (getCurrentPosition == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindLong(_argIndex, getCurrentPosition);
      }
      _argIndex = 2;
      if (video_time == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, video_time);
      }
      _argIndex = 3;
      if (videoid == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, videoid);
      }
      _argIndex = 4;
      if (userid == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, userid);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdate_videocurrenttime.release(_stmt);
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
  public void update_pos(String userid, String videoid, int getCurrentPosition) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdate_pos.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      _stmt.bindLong(_argIndex, getCurrentPosition);
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
      __preparedStmtOfUpdate_pos.release(_stmt);
    }
  }

  @Override
  public void delete_user_id(String user_id) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDelete_user_id.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (user_id == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, user_id);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDelete_user_id.release(_stmt);
    }
  }

  @Override
  public VideosDownload getuser(String videoid, String complete) {
    final String _sql = "SELECT * FROM VideoDownload  WHERE video_id = ? AND is_complete =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (videoid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, videoid);
    }
    _argIndex = 2;
    if (complete == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, complete);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfAutoid = _cursor.getColumnIndexOrThrow("autoid");
      final int _cursorIndexOfVideoId = _cursor.getColumnIndexOrThrow("video_id");
      final int _cursorIndexOfVideoName = _cursor.getColumnIndexOrThrow("video_name");
      final int _cursorIndexOfOriginalFileLengthString = _cursor.getColumnIndexOrThrow("originalFileLengthString");
      final int _cursorIndexOfVideotime = _cursor.getColumnIndexOrThrow("videotime");
      final int _cursorIndexOfToalDownloadlocale = _cursor.getColumnIndexOrThrow("total");
      final int _cursorIndexOfLengthInMb = _cursor.getColumnIndexOrThrow("lengthInMb");
      final int _cursorIndexOfPercentage = _cursor.getColumnIndexOrThrow("percentage");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final int _cursorIndexOfCourseId = _cursor.getColumnIndexOrThrow("course_id");
      final int _cursorIndexOfValidTo = _cursor.getColumnIndexOrThrow("valid_to");
      final int _cursorIndexOfPosition = _cursor.getColumnIndexOrThrow("position");
      final int _cursorIndexOfMp4DownloadUrl = _cursor.getColumnIndexOrThrow("mp4_download_url");
      final int _cursorIndexOfVideoStatus = _cursor.getColumnIndexOrThrow("video_status");
      final int _cursorIndexOfVideoEnc = _cursor.getColumnIndexOrThrow("video_enc");
      final int _cursorIndexOfVideoToken = _cursor.getColumnIndexOrThrow("video_token");
      final int _cursorIndexOfThumbnailUrl = _cursor.getColumnIndexOrThrow("thumbnail_url");
      final int _cursorIndexOfIsComplete = _cursor.getColumnIndexOrThrow("is_complete");
      final int _cursorIndexOfVideoCurrentPosition = _cursor.getColumnIndexOrThrow("videoCurrentPosition");
      final int _cursorIndexOfJwUrl = _cursor.getColumnIndexOrThrow("jw_url");
      final int _cursorIndexOfIsSelected = _cursor.getColumnIndexOrThrow("is_selected");
      final int _cursorIndexOfVideoHistory = _cursor.getColumnIndexOrThrow("video_history");
      final VideosDownload _result;
      if(_cursor.moveToFirst()) {
        _result = new VideosDownload();
        final int _tmpAutoid;
        _tmpAutoid = _cursor.getInt(_cursorIndexOfAutoid);
        _result.setAutoid(_tmpAutoid);
        final String _tmpVideo_id;
        _tmpVideo_id = _cursor.getString(_cursorIndexOfVideoId);
        _result.setVideo_id(_tmpVideo_id);
        final String _tmpVideo_name;
        _tmpVideo_name = _cursor.getString(_cursorIndexOfVideoName);
        _result.setVideo_name(_tmpVideo_name);
        final String _tmpOriginalFileLengthString;
        _tmpOriginalFileLengthString = _cursor.getString(_cursorIndexOfOriginalFileLengthString);
        _result.setOriginalFileLengthString(_tmpOriginalFileLengthString);
        final String _tmpVideotime;
        _tmpVideotime = _cursor.getString(_cursorIndexOfVideotime);
        _result.setVideotime(_tmpVideotime);
        final Long _tmpToal_downloadlocale;
        if (_cursor.isNull(_cursorIndexOfToalDownloadlocale)) {
          _tmpToal_downloadlocale = null;
        } else {
          _tmpToal_downloadlocale = _cursor.getLong(_cursorIndexOfToalDownloadlocale);
        }
        _result.setToal_downloadlocale(_tmpToal_downloadlocale);
        final String _tmpLengthInMb;
        _tmpLengthInMb = _cursor.getString(_cursorIndexOfLengthInMb);
        _result.setLengthInMb(_tmpLengthInMb);
        final int _tmpPercentage;
        _tmpPercentage = _cursor.getInt(_cursorIndexOfPercentage);
        _result.setPercentage(_tmpPercentage);
        final String _tmpUser_id;
        _tmpUser_id = _cursor.getString(_cursorIndexOfUserId);
        _result.setUser_id(_tmpUser_id);
        final String _tmpCourse_id;
        _tmpCourse_id = _cursor.getString(_cursorIndexOfCourseId);
        _result.setCourse_id(_tmpCourse_id);
        final int _tmpValid_to;
        _tmpValid_to = _cursor.getInt(_cursorIndexOfValidTo);
        _result.setValid_to(_tmpValid_to);
        final int _tmpPosition;
        _tmpPosition = _cursor.getInt(_cursorIndexOfPosition);
        _result.setPosition(_tmpPosition);
        final String _tmpMp4_download_url;
        _tmpMp4_download_url = _cursor.getString(_cursorIndexOfMp4DownloadUrl);
        _result.setMp4_download_url(_tmpMp4_download_url);
        final String _tmpVideo_status;
        _tmpVideo_status = _cursor.getString(_cursorIndexOfVideoStatus);
        _result.setVideo_status(_tmpVideo_status);
        final String _tmpVideo_enc;
        _tmpVideo_enc = _cursor.getString(_cursorIndexOfVideoEnc);
        _result.setVideo_enc(_tmpVideo_enc);
        final String _tmpVideo_token;
        _tmpVideo_token = _cursor.getString(_cursorIndexOfVideoToken);
        _result.setVideo_token(_tmpVideo_token);
        final String _tmpThumbnail_url;
        _tmpThumbnail_url = _cursor.getString(_cursorIndexOfThumbnailUrl);
        _result.setThumbnail_url(_tmpThumbnail_url);
        final String _tmpIs_complete;
        _tmpIs_complete = _cursor.getString(_cursorIndexOfIsComplete);
        _result.setIs_complete(_tmpIs_complete);
        final Long _tmpVideoCurrentPosition;
        if (_cursor.isNull(_cursorIndexOfVideoCurrentPosition)) {
          _tmpVideoCurrentPosition = null;
        } else {
          _tmpVideoCurrentPosition = _cursor.getLong(_cursorIndexOfVideoCurrentPosition);
        }
        _result.setVideoCurrentPosition(_tmpVideoCurrentPosition);
        final String _tmpJw_url;
        _tmpJw_url = _cursor.getString(_cursorIndexOfJwUrl);
        _result.setJw_url(_tmpJw_url);
        final String _tmpIs_selected;
        _tmpIs_selected = _cursor.getString(_cursorIndexOfIsSelected);
        _result.setIs_selected(_tmpIs_selected);
        final String _tmpVideo_history;
        _tmpVideo_history = _cursor.getString(_cursorIndexOfVideoHistory);
        _result.setVideo_history(_tmpVideo_history);
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
  public VideosDownload getvideo_byuserid(String videoid, String complete, String user_id) {
    final String _sql = "SELECT * FROM VideoDownload  WHERE video_id = ? AND is_complete =?  AND user_id =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    if (videoid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, videoid);
    }
    _argIndex = 2;
    if (complete == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, complete);
    }
    _argIndex = 3;
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
      final int _cursorIndexOfOriginalFileLengthString = _cursor.getColumnIndexOrThrow("originalFileLengthString");
      final int _cursorIndexOfVideotime = _cursor.getColumnIndexOrThrow("videotime");
      final int _cursorIndexOfToalDownloadlocale = _cursor.getColumnIndexOrThrow("total");
      final int _cursorIndexOfLengthInMb = _cursor.getColumnIndexOrThrow("lengthInMb");
      final int _cursorIndexOfPercentage = _cursor.getColumnIndexOrThrow("percentage");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final int _cursorIndexOfCourseId = _cursor.getColumnIndexOrThrow("course_id");
      final int _cursorIndexOfValidTo = _cursor.getColumnIndexOrThrow("valid_to");
      final int _cursorIndexOfPosition = _cursor.getColumnIndexOrThrow("position");
      final int _cursorIndexOfMp4DownloadUrl = _cursor.getColumnIndexOrThrow("mp4_download_url");
      final int _cursorIndexOfVideoStatus = _cursor.getColumnIndexOrThrow("video_status");
      final int _cursorIndexOfVideoEnc = _cursor.getColumnIndexOrThrow("video_enc");
      final int _cursorIndexOfVideoToken = _cursor.getColumnIndexOrThrow("video_token");
      final int _cursorIndexOfThumbnailUrl = _cursor.getColumnIndexOrThrow("thumbnail_url");
      final int _cursorIndexOfIsComplete = _cursor.getColumnIndexOrThrow("is_complete");
      final int _cursorIndexOfVideoCurrentPosition = _cursor.getColumnIndexOrThrow("videoCurrentPosition");
      final int _cursorIndexOfJwUrl = _cursor.getColumnIndexOrThrow("jw_url");
      final int _cursorIndexOfIsSelected = _cursor.getColumnIndexOrThrow("is_selected");
      final int _cursorIndexOfVideoHistory = _cursor.getColumnIndexOrThrow("video_history");
      final VideosDownload _result;
      if(_cursor.moveToFirst()) {
        _result = new VideosDownload();
        final int _tmpAutoid;
        _tmpAutoid = _cursor.getInt(_cursorIndexOfAutoid);
        _result.setAutoid(_tmpAutoid);
        final String _tmpVideo_id;
        _tmpVideo_id = _cursor.getString(_cursorIndexOfVideoId);
        _result.setVideo_id(_tmpVideo_id);
        final String _tmpVideo_name;
        _tmpVideo_name = _cursor.getString(_cursorIndexOfVideoName);
        _result.setVideo_name(_tmpVideo_name);
        final String _tmpOriginalFileLengthString;
        _tmpOriginalFileLengthString = _cursor.getString(_cursorIndexOfOriginalFileLengthString);
        _result.setOriginalFileLengthString(_tmpOriginalFileLengthString);
        final String _tmpVideotime;
        _tmpVideotime = _cursor.getString(_cursorIndexOfVideotime);
        _result.setVideotime(_tmpVideotime);
        final Long _tmpToal_downloadlocale;
        if (_cursor.isNull(_cursorIndexOfToalDownloadlocale)) {
          _tmpToal_downloadlocale = null;
        } else {
          _tmpToal_downloadlocale = _cursor.getLong(_cursorIndexOfToalDownloadlocale);
        }
        _result.setToal_downloadlocale(_tmpToal_downloadlocale);
        final String _tmpLengthInMb;
        _tmpLengthInMb = _cursor.getString(_cursorIndexOfLengthInMb);
        _result.setLengthInMb(_tmpLengthInMb);
        final int _tmpPercentage;
        _tmpPercentage = _cursor.getInt(_cursorIndexOfPercentage);
        _result.setPercentage(_tmpPercentage);
        final String _tmpUser_id;
        _tmpUser_id = _cursor.getString(_cursorIndexOfUserId);
        _result.setUser_id(_tmpUser_id);
        final String _tmpCourse_id;
        _tmpCourse_id = _cursor.getString(_cursorIndexOfCourseId);
        _result.setCourse_id(_tmpCourse_id);
        final int _tmpValid_to;
        _tmpValid_to = _cursor.getInt(_cursorIndexOfValidTo);
        _result.setValid_to(_tmpValid_to);
        final int _tmpPosition;
        _tmpPosition = _cursor.getInt(_cursorIndexOfPosition);
        _result.setPosition(_tmpPosition);
        final String _tmpMp4_download_url;
        _tmpMp4_download_url = _cursor.getString(_cursorIndexOfMp4DownloadUrl);
        _result.setMp4_download_url(_tmpMp4_download_url);
        final String _tmpVideo_status;
        _tmpVideo_status = _cursor.getString(_cursorIndexOfVideoStatus);
        _result.setVideo_status(_tmpVideo_status);
        final String _tmpVideo_enc;
        _tmpVideo_enc = _cursor.getString(_cursorIndexOfVideoEnc);
        _result.setVideo_enc(_tmpVideo_enc);
        final String _tmpVideo_token;
        _tmpVideo_token = _cursor.getString(_cursorIndexOfVideoToken);
        _result.setVideo_token(_tmpVideo_token);
        final String _tmpThumbnail_url;
        _tmpThumbnail_url = _cursor.getString(_cursorIndexOfThumbnailUrl);
        _result.setThumbnail_url(_tmpThumbnail_url);
        final String _tmpIs_complete;
        _tmpIs_complete = _cursor.getString(_cursorIndexOfIsComplete);
        _result.setIs_complete(_tmpIs_complete);
        final Long _tmpVideoCurrentPosition;
        if (_cursor.isNull(_cursorIndexOfVideoCurrentPosition)) {
          _tmpVideoCurrentPosition = null;
        } else {
          _tmpVideoCurrentPosition = _cursor.getLong(_cursorIndexOfVideoCurrentPosition);
        }
        _result.setVideoCurrentPosition(_tmpVideoCurrentPosition);
        final String _tmpJw_url;
        _tmpJw_url = _cursor.getString(_cursorIndexOfJwUrl);
        _result.setJw_url(_tmpJw_url);
        final String _tmpIs_selected;
        _tmpIs_selected = _cursor.getString(_cursorIndexOfIsSelected);
        _result.setIs_selected(_tmpIs_selected);
        final String _tmpVideo_history;
        _tmpVideo_history = _cursor.getString(_cursorIndexOfVideoHistory);
        _result.setVideo_history(_tmpVideo_history);
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
  public VideosDownload getvideo_byuserid(String videoid, String user_id) {
    final String _sql = "SELECT * FROM VideoDownload  WHERE video_id = ?   AND user_id =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (videoid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, videoid);
    }
    _argIndex = 2;
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
      final int _cursorIndexOfOriginalFileLengthString = _cursor.getColumnIndexOrThrow("originalFileLengthString");
      final int _cursorIndexOfVideotime = _cursor.getColumnIndexOrThrow("videotime");
      final int _cursorIndexOfToalDownloadlocale = _cursor.getColumnIndexOrThrow("total");
      final int _cursorIndexOfLengthInMb = _cursor.getColumnIndexOrThrow("lengthInMb");
      final int _cursorIndexOfPercentage = _cursor.getColumnIndexOrThrow("percentage");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final int _cursorIndexOfCourseId = _cursor.getColumnIndexOrThrow("course_id");
      final int _cursorIndexOfValidTo = _cursor.getColumnIndexOrThrow("valid_to");
      final int _cursorIndexOfPosition = _cursor.getColumnIndexOrThrow("position");
      final int _cursorIndexOfMp4DownloadUrl = _cursor.getColumnIndexOrThrow("mp4_download_url");
      final int _cursorIndexOfVideoStatus = _cursor.getColumnIndexOrThrow("video_status");
      final int _cursorIndexOfVideoEnc = _cursor.getColumnIndexOrThrow("video_enc");
      final int _cursorIndexOfVideoToken = _cursor.getColumnIndexOrThrow("video_token");
      final int _cursorIndexOfThumbnailUrl = _cursor.getColumnIndexOrThrow("thumbnail_url");
      final int _cursorIndexOfIsComplete = _cursor.getColumnIndexOrThrow("is_complete");
      final int _cursorIndexOfVideoCurrentPosition = _cursor.getColumnIndexOrThrow("videoCurrentPosition");
      final int _cursorIndexOfJwUrl = _cursor.getColumnIndexOrThrow("jw_url");
      final int _cursorIndexOfIsSelected = _cursor.getColumnIndexOrThrow("is_selected");
      final int _cursorIndexOfVideoHistory = _cursor.getColumnIndexOrThrow("video_history");
      final VideosDownload _result;
      if(_cursor.moveToFirst()) {
        _result = new VideosDownload();
        final int _tmpAutoid;
        _tmpAutoid = _cursor.getInt(_cursorIndexOfAutoid);
        _result.setAutoid(_tmpAutoid);
        final String _tmpVideo_id;
        _tmpVideo_id = _cursor.getString(_cursorIndexOfVideoId);
        _result.setVideo_id(_tmpVideo_id);
        final String _tmpVideo_name;
        _tmpVideo_name = _cursor.getString(_cursorIndexOfVideoName);
        _result.setVideo_name(_tmpVideo_name);
        final String _tmpOriginalFileLengthString;
        _tmpOriginalFileLengthString = _cursor.getString(_cursorIndexOfOriginalFileLengthString);
        _result.setOriginalFileLengthString(_tmpOriginalFileLengthString);
        final String _tmpVideotime;
        _tmpVideotime = _cursor.getString(_cursorIndexOfVideotime);
        _result.setVideotime(_tmpVideotime);
        final Long _tmpToal_downloadlocale;
        if (_cursor.isNull(_cursorIndexOfToalDownloadlocale)) {
          _tmpToal_downloadlocale = null;
        } else {
          _tmpToal_downloadlocale = _cursor.getLong(_cursorIndexOfToalDownloadlocale);
        }
        _result.setToal_downloadlocale(_tmpToal_downloadlocale);
        final String _tmpLengthInMb;
        _tmpLengthInMb = _cursor.getString(_cursorIndexOfLengthInMb);
        _result.setLengthInMb(_tmpLengthInMb);
        final int _tmpPercentage;
        _tmpPercentage = _cursor.getInt(_cursorIndexOfPercentage);
        _result.setPercentage(_tmpPercentage);
        final String _tmpUser_id;
        _tmpUser_id = _cursor.getString(_cursorIndexOfUserId);
        _result.setUser_id(_tmpUser_id);
        final String _tmpCourse_id;
        _tmpCourse_id = _cursor.getString(_cursorIndexOfCourseId);
        _result.setCourse_id(_tmpCourse_id);
        final int _tmpValid_to;
        _tmpValid_to = _cursor.getInt(_cursorIndexOfValidTo);
        _result.setValid_to(_tmpValid_to);
        final int _tmpPosition;
        _tmpPosition = _cursor.getInt(_cursorIndexOfPosition);
        _result.setPosition(_tmpPosition);
        final String _tmpMp4_download_url;
        _tmpMp4_download_url = _cursor.getString(_cursorIndexOfMp4DownloadUrl);
        _result.setMp4_download_url(_tmpMp4_download_url);
        final String _tmpVideo_status;
        _tmpVideo_status = _cursor.getString(_cursorIndexOfVideoStatus);
        _result.setVideo_status(_tmpVideo_status);
        final String _tmpVideo_enc;
        _tmpVideo_enc = _cursor.getString(_cursorIndexOfVideoEnc);
        _result.setVideo_enc(_tmpVideo_enc);
        final String _tmpVideo_token;
        _tmpVideo_token = _cursor.getString(_cursorIndexOfVideoToken);
        _result.setVideo_token(_tmpVideo_token);
        final String _tmpThumbnail_url;
        _tmpThumbnail_url = _cursor.getString(_cursorIndexOfThumbnailUrl);
        _result.setThumbnail_url(_tmpThumbnail_url);
        final String _tmpIs_complete;
        _tmpIs_complete = _cursor.getString(_cursorIndexOfIsComplete);
        _result.setIs_complete(_tmpIs_complete);
        final Long _tmpVideoCurrentPosition;
        if (_cursor.isNull(_cursorIndexOfVideoCurrentPosition)) {
          _tmpVideoCurrentPosition = null;
        } else {
          _tmpVideoCurrentPosition = _cursor.getLong(_cursorIndexOfVideoCurrentPosition);
        }
        _result.setVideoCurrentPosition(_tmpVideoCurrentPosition);
        final String _tmpJw_url;
        _tmpJw_url = _cursor.getString(_cursorIndexOfJwUrl);
        _result.setJw_url(_tmpJw_url);
        final String _tmpIs_selected;
        _tmpIs_selected = _cursor.getString(_cursorIndexOfIsSelected);
        _result.setIs_selected(_tmpIs_selected);
        final String _tmpVideo_history;
        _tmpVideo_history = _cursor.getString(_cursorIndexOfVideoHistory);
        _result.setVideo_history(_tmpVideo_history);
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
  public List<VideosDownload> getalldownload_videos(String user_id) {
    final String _sql = "SELECT * FROM VideoDownload  WHERE user_id = ?";
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
      final int _cursorIndexOfOriginalFileLengthString = _cursor.getColumnIndexOrThrow("originalFileLengthString");
      final int _cursorIndexOfVideotime = _cursor.getColumnIndexOrThrow("videotime");
      final int _cursorIndexOfToalDownloadlocale = _cursor.getColumnIndexOrThrow("total");
      final int _cursorIndexOfLengthInMb = _cursor.getColumnIndexOrThrow("lengthInMb");
      final int _cursorIndexOfPercentage = _cursor.getColumnIndexOrThrow("percentage");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final int _cursorIndexOfCourseId = _cursor.getColumnIndexOrThrow("course_id");
      final int _cursorIndexOfValidTo = _cursor.getColumnIndexOrThrow("valid_to");
      final int _cursorIndexOfPosition = _cursor.getColumnIndexOrThrow("position");
      final int _cursorIndexOfMp4DownloadUrl = _cursor.getColumnIndexOrThrow("mp4_download_url");
      final int _cursorIndexOfVideoStatus = _cursor.getColumnIndexOrThrow("video_status");
      final int _cursorIndexOfVideoEnc = _cursor.getColumnIndexOrThrow("video_enc");
      final int _cursorIndexOfVideoToken = _cursor.getColumnIndexOrThrow("video_token");
      final int _cursorIndexOfThumbnailUrl = _cursor.getColumnIndexOrThrow("thumbnail_url");
      final int _cursorIndexOfIsComplete = _cursor.getColumnIndexOrThrow("is_complete");
      final int _cursorIndexOfVideoCurrentPosition = _cursor.getColumnIndexOrThrow("videoCurrentPosition");
      final int _cursorIndexOfJwUrl = _cursor.getColumnIndexOrThrow("jw_url");
      final int _cursorIndexOfIsSelected = _cursor.getColumnIndexOrThrow("is_selected");
      final int _cursorIndexOfVideoHistory = _cursor.getColumnIndexOrThrow("video_history");
      final List<VideosDownload> _result = new ArrayList<VideosDownload>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final VideosDownload _item;
        _item = new VideosDownload();
        final int _tmpAutoid;
        _tmpAutoid = _cursor.getInt(_cursorIndexOfAutoid);
        _item.setAutoid(_tmpAutoid);
        final String _tmpVideo_id;
        _tmpVideo_id = _cursor.getString(_cursorIndexOfVideoId);
        _item.setVideo_id(_tmpVideo_id);
        final String _tmpVideo_name;
        _tmpVideo_name = _cursor.getString(_cursorIndexOfVideoName);
        _item.setVideo_name(_tmpVideo_name);
        final String _tmpOriginalFileLengthString;
        _tmpOriginalFileLengthString = _cursor.getString(_cursorIndexOfOriginalFileLengthString);
        _item.setOriginalFileLengthString(_tmpOriginalFileLengthString);
        final String _tmpVideotime;
        _tmpVideotime = _cursor.getString(_cursorIndexOfVideotime);
        _item.setVideotime(_tmpVideotime);
        final Long _tmpToal_downloadlocale;
        if (_cursor.isNull(_cursorIndexOfToalDownloadlocale)) {
          _tmpToal_downloadlocale = null;
        } else {
          _tmpToal_downloadlocale = _cursor.getLong(_cursorIndexOfToalDownloadlocale);
        }
        _item.setToal_downloadlocale(_tmpToal_downloadlocale);
        final String _tmpLengthInMb;
        _tmpLengthInMb = _cursor.getString(_cursorIndexOfLengthInMb);
        _item.setLengthInMb(_tmpLengthInMb);
        final int _tmpPercentage;
        _tmpPercentage = _cursor.getInt(_cursorIndexOfPercentage);
        _item.setPercentage(_tmpPercentage);
        final String _tmpUser_id;
        _tmpUser_id = _cursor.getString(_cursorIndexOfUserId);
        _item.setUser_id(_tmpUser_id);
        final String _tmpCourse_id;
        _tmpCourse_id = _cursor.getString(_cursorIndexOfCourseId);
        _item.setCourse_id(_tmpCourse_id);
        final int _tmpValid_to;
        _tmpValid_to = _cursor.getInt(_cursorIndexOfValidTo);
        _item.setValid_to(_tmpValid_to);
        final int _tmpPosition;
        _tmpPosition = _cursor.getInt(_cursorIndexOfPosition);
        _item.setPosition(_tmpPosition);
        final String _tmpMp4_download_url;
        _tmpMp4_download_url = _cursor.getString(_cursorIndexOfMp4DownloadUrl);
        _item.setMp4_download_url(_tmpMp4_download_url);
        final String _tmpVideo_status;
        _tmpVideo_status = _cursor.getString(_cursorIndexOfVideoStatus);
        _item.setVideo_status(_tmpVideo_status);
        final String _tmpVideo_enc;
        _tmpVideo_enc = _cursor.getString(_cursorIndexOfVideoEnc);
        _item.setVideo_enc(_tmpVideo_enc);
        final String _tmpVideo_token;
        _tmpVideo_token = _cursor.getString(_cursorIndexOfVideoToken);
        _item.setVideo_token(_tmpVideo_token);
        final String _tmpThumbnail_url;
        _tmpThumbnail_url = _cursor.getString(_cursorIndexOfThumbnailUrl);
        _item.setThumbnail_url(_tmpThumbnail_url);
        final String _tmpIs_complete;
        _tmpIs_complete = _cursor.getString(_cursorIndexOfIsComplete);
        _item.setIs_complete(_tmpIs_complete);
        final Long _tmpVideoCurrentPosition;
        if (_cursor.isNull(_cursorIndexOfVideoCurrentPosition)) {
          _tmpVideoCurrentPosition = null;
        } else {
          _tmpVideoCurrentPosition = _cursor.getLong(_cursorIndexOfVideoCurrentPosition);
        }
        _item.setVideoCurrentPosition(_tmpVideoCurrentPosition);
        final String _tmpJw_url;
        _tmpJw_url = _cursor.getString(_cursorIndexOfJwUrl);
        _item.setJw_url(_tmpJw_url);
        final String _tmpIs_selected;
        _tmpIs_selected = _cursor.getString(_cursorIndexOfIsSelected);
        _item.setIs_selected(_tmpIs_selected);
        final String _tmpVideo_history;
        _tmpVideo_history = _cursor.getString(_cursorIndexOfVideoHistory);
        _item.setVideo_history(_tmpVideo_history);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<VideosDownload> getcourse_expire(String courseid, String userid) {
    final String _sql = "SELECT * FROM VideoDownload  WHERE course_id LIKE '%' || ?   AND user_id =?";
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
      final int _cursorIndexOfVideoId = _cursor.getColumnIndexOrThrow("video_id");
      final int _cursorIndexOfVideoName = _cursor.getColumnIndexOrThrow("video_name");
      final int _cursorIndexOfOriginalFileLengthString = _cursor.getColumnIndexOrThrow("originalFileLengthString");
      final int _cursorIndexOfVideotime = _cursor.getColumnIndexOrThrow("videotime");
      final int _cursorIndexOfToalDownloadlocale = _cursor.getColumnIndexOrThrow("total");
      final int _cursorIndexOfLengthInMb = _cursor.getColumnIndexOrThrow("lengthInMb");
      final int _cursorIndexOfPercentage = _cursor.getColumnIndexOrThrow("percentage");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final int _cursorIndexOfCourseId = _cursor.getColumnIndexOrThrow("course_id");
      final int _cursorIndexOfValidTo = _cursor.getColumnIndexOrThrow("valid_to");
      final int _cursorIndexOfPosition = _cursor.getColumnIndexOrThrow("position");
      final int _cursorIndexOfMp4DownloadUrl = _cursor.getColumnIndexOrThrow("mp4_download_url");
      final int _cursorIndexOfVideoStatus = _cursor.getColumnIndexOrThrow("video_status");
      final int _cursorIndexOfVideoEnc = _cursor.getColumnIndexOrThrow("video_enc");
      final int _cursorIndexOfVideoToken = _cursor.getColumnIndexOrThrow("video_token");
      final int _cursorIndexOfThumbnailUrl = _cursor.getColumnIndexOrThrow("thumbnail_url");
      final int _cursorIndexOfIsComplete = _cursor.getColumnIndexOrThrow("is_complete");
      final int _cursorIndexOfVideoCurrentPosition = _cursor.getColumnIndexOrThrow("videoCurrentPosition");
      final int _cursorIndexOfJwUrl = _cursor.getColumnIndexOrThrow("jw_url");
      final int _cursorIndexOfIsSelected = _cursor.getColumnIndexOrThrow("is_selected");
      final int _cursorIndexOfVideoHistory = _cursor.getColumnIndexOrThrow("video_history");
      final List<VideosDownload> _result = new ArrayList<VideosDownload>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final VideosDownload _item;
        _item = new VideosDownload();
        final int _tmpAutoid;
        _tmpAutoid = _cursor.getInt(_cursorIndexOfAutoid);
        _item.setAutoid(_tmpAutoid);
        final String _tmpVideo_id;
        _tmpVideo_id = _cursor.getString(_cursorIndexOfVideoId);
        _item.setVideo_id(_tmpVideo_id);
        final String _tmpVideo_name;
        _tmpVideo_name = _cursor.getString(_cursorIndexOfVideoName);
        _item.setVideo_name(_tmpVideo_name);
        final String _tmpOriginalFileLengthString;
        _tmpOriginalFileLengthString = _cursor.getString(_cursorIndexOfOriginalFileLengthString);
        _item.setOriginalFileLengthString(_tmpOriginalFileLengthString);
        final String _tmpVideotime;
        _tmpVideotime = _cursor.getString(_cursorIndexOfVideotime);
        _item.setVideotime(_tmpVideotime);
        final Long _tmpToal_downloadlocale;
        if (_cursor.isNull(_cursorIndexOfToalDownloadlocale)) {
          _tmpToal_downloadlocale = null;
        } else {
          _tmpToal_downloadlocale = _cursor.getLong(_cursorIndexOfToalDownloadlocale);
        }
        _item.setToal_downloadlocale(_tmpToal_downloadlocale);
        final String _tmpLengthInMb;
        _tmpLengthInMb = _cursor.getString(_cursorIndexOfLengthInMb);
        _item.setLengthInMb(_tmpLengthInMb);
        final int _tmpPercentage;
        _tmpPercentage = _cursor.getInt(_cursorIndexOfPercentage);
        _item.setPercentage(_tmpPercentage);
        final String _tmpUser_id;
        _tmpUser_id = _cursor.getString(_cursorIndexOfUserId);
        _item.setUser_id(_tmpUser_id);
        final String _tmpCourse_id;
        _tmpCourse_id = _cursor.getString(_cursorIndexOfCourseId);
        _item.setCourse_id(_tmpCourse_id);
        final int _tmpValid_to;
        _tmpValid_to = _cursor.getInt(_cursorIndexOfValidTo);
        _item.setValid_to(_tmpValid_to);
        final int _tmpPosition;
        _tmpPosition = _cursor.getInt(_cursorIndexOfPosition);
        _item.setPosition(_tmpPosition);
        final String _tmpMp4_download_url;
        _tmpMp4_download_url = _cursor.getString(_cursorIndexOfMp4DownloadUrl);
        _item.setMp4_download_url(_tmpMp4_download_url);
        final String _tmpVideo_status;
        _tmpVideo_status = _cursor.getString(_cursorIndexOfVideoStatus);
        _item.setVideo_status(_tmpVideo_status);
        final String _tmpVideo_enc;
        _tmpVideo_enc = _cursor.getString(_cursorIndexOfVideoEnc);
        _item.setVideo_enc(_tmpVideo_enc);
        final String _tmpVideo_token;
        _tmpVideo_token = _cursor.getString(_cursorIndexOfVideoToken);
        _item.setVideo_token(_tmpVideo_token);
        final String _tmpThumbnail_url;
        _tmpThumbnail_url = _cursor.getString(_cursorIndexOfThumbnailUrl);
        _item.setThumbnail_url(_tmpThumbnail_url);
        final String _tmpIs_complete;
        _tmpIs_complete = _cursor.getString(_cursorIndexOfIsComplete);
        _item.setIs_complete(_tmpIs_complete);
        final Long _tmpVideoCurrentPosition;
        if (_cursor.isNull(_cursorIndexOfVideoCurrentPosition)) {
          _tmpVideoCurrentPosition = null;
        } else {
          _tmpVideoCurrentPosition = _cursor.getLong(_cursorIndexOfVideoCurrentPosition);
        }
        _item.setVideoCurrentPosition(_tmpVideoCurrentPosition);
        final String _tmpJw_url;
        _tmpJw_url = _cursor.getString(_cursorIndexOfJwUrl);
        _item.setJw_url(_tmpJw_url);
        final String _tmpIs_selected;
        _tmpIs_selected = _cursor.getString(_cursorIndexOfIsSelected);
        _item.setIs_selected(_tmpIs_selected);
        final String _tmpVideo_history;
        _tmpVideo_history = _cursor.getString(_cursorIndexOfVideoHistory);
        _item.setVideo_history(_tmpVideo_history);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<VideosDownload> getcourse_expire_left(String courseid, String userid) {
    final String _sql = "SELECT * FROM VideoDownload  WHERE course_id LIKE ? || '%'   AND user_id =?";
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
      final int _cursorIndexOfVideoId = _cursor.getColumnIndexOrThrow("video_id");
      final int _cursorIndexOfVideoName = _cursor.getColumnIndexOrThrow("video_name");
      final int _cursorIndexOfOriginalFileLengthString = _cursor.getColumnIndexOrThrow("originalFileLengthString");
      final int _cursorIndexOfVideotime = _cursor.getColumnIndexOrThrow("videotime");
      final int _cursorIndexOfToalDownloadlocale = _cursor.getColumnIndexOrThrow("total");
      final int _cursorIndexOfLengthInMb = _cursor.getColumnIndexOrThrow("lengthInMb");
      final int _cursorIndexOfPercentage = _cursor.getColumnIndexOrThrow("percentage");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final int _cursorIndexOfCourseId = _cursor.getColumnIndexOrThrow("course_id");
      final int _cursorIndexOfValidTo = _cursor.getColumnIndexOrThrow("valid_to");
      final int _cursorIndexOfPosition = _cursor.getColumnIndexOrThrow("position");
      final int _cursorIndexOfMp4DownloadUrl = _cursor.getColumnIndexOrThrow("mp4_download_url");
      final int _cursorIndexOfVideoStatus = _cursor.getColumnIndexOrThrow("video_status");
      final int _cursorIndexOfVideoEnc = _cursor.getColumnIndexOrThrow("video_enc");
      final int _cursorIndexOfVideoToken = _cursor.getColumnIndexOrThrow("video_token");
      final int _cursorIndexOfThumbnailUrl = _cursor.getColumnIndexOrThrow("thumbnail_url");
      final int _cursorIndexOfIsComplete = _cursor.getColumnIndexOrThrow("is_complete");
      final int _cursorIndexOfVideoCurrentPosition = _cursor.getColumnIndexOrThrow("videoCurrentPosition");
      final int _cursorIndexOfJwUrl = _cursor.getColumnIndexOrThrow("jw_url");
      final int _cursorIndexOfIsSelected = _cursor.getColumnIndexOrThrow("is_selected");
      final int _cursorIndexOfVideoHistory = _cursor.getColumnIndexOrThrow("video_history");
      final List<VideosDownload> _result = new ArrayList<VideosDownload>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final VideosDownload _item;
        _item = new VideosDownload();
        final int _tmpAutoid;
        _tmpAutoid = _cursor.getInt(_cursorIndexOfAutoid);
        _item.setAutoid(_tmpAutoid);
        final String _tmpVideo_id;
        _tmpVideo_id = _cursor.getString(_cursorIndexOfVideoId);
        _item.setVideo_id(_tmpVideo_id);
        final String _tmpVideo_name;
        _tmpVideo_name = _cursor.getString(_cursorIndexOfVideoName);
        _item.setVideo_name(_tmpVideo_name);
        final String _tmpOriginalFileLengthString;
        _tmpOriginalFileLengthString = _cursor.getString(_cursorIndexOfOriginalFileLengthString);
        _item.setOriginalFileLengthString(_tmpOriginalFileLengthString);
        final String _tmpVideotime;
        _tmpVideotime = _cursor.getString(_cursorIndexOfVideotime);
        _item.setVideotime(_tmpVideotime);
        final Long _tmpToal_downloadlocale;
        if (_cursor.isNull(_cursorIndexOfToalDownloadlocale)) {
          _tmpToal_downloadlocale = null;
        } else {
          _tmpToal_downloadlocale = _cursor.getLong(_cursorIndexOfToalDownloadlocale);
        }
        _item.setToal_downloadlocale(_tmpToal_downloadlocale);
        final String _tmpLengthInMb;
        _tmpLengthInMb = _cursor.getString(_cursorIndexOfLengthInMb);
        _item.setLengthInMb(_tmpLengthInMb);
        final int _tmpPercentage;
        _tmpPercentage = _cursor.getInt(_cursorIndexOfPercentage);
        _item.setPercentage(_tmpPercentage);
        final String _tmpUser_id;
        _tmpUser_id = _cursor.getString(_cursorIndexOfUserId);
        _item.setUser_id(_tmpUser_id);
        final String _tmpCourse_id;
        _tmpCourse_id = _cursor.getString(_cursorIndexOfCourseId);
        _item.setCourse_id(_tmpCourse_id);
        final int _tmpValid_to;
        _tmpValid_to = _cursor.getInt(_cursorIndexOfValidTo);
        _item.setValid_to(_tmpValid_to);
        final int _tmpPosition;
        _tmpPosition = _cursor.getInt(_cursorIndexOfPosition);
        _item.setPosition(_tmpPosition);
        final String _tmpMp4_download_url;
        _tmpMp4_download_url = _cursor.getString(_cursorIndexOfMp4DownloadUrl);
        _item.setMp4_download_url(_tmpMp4_download_url);
        final String _tmpVideo_status;
        _tmpVideo_status = _cursor.getString(_cursorIndexOfVideoStatus);
        _item.setVideo_status(_tmpVideo_status);
        final String _tmpVideo_enc;
        _tmpVideo_enc = _cursor.getString(_cursorIndexOfVideoEnc);
        _item.setVideo_enc(_tmpVideo_enc);
        final String _tmpVideo_token;
        _tmpVideo_token = _cursor.getString(_cursorIndexOfVideoToken);
        _item.setVideo_token(_tmpVideo_token);
        final String _tmpThumbnail_url;
        _tmpThumbnail_url = _cursor.getString(_cursorIndexOfThumbnailUrl);
        _item.setThumbnail_url(_tmpThumbnail_url);
        final String _tmpIs_complete;
        _tmpIs_complete = _cursor.getString(_cursorIndexOfIsComplete);
        _item.setIs_complete(_tmpIs_complete);
        final Long _tmpVideoCurrentPosition;
        if (_cursor.isNull(_cursorIndexOfVideoCurrentPosition)) {
          _tmpVideoCurrentPosition = null;
        } else {
          _tmpVideoCurrentPosition = _cursor.getLong(_cursorIndexOfVideoCurrentPosition);
        }
        _item.setVideoCurrentPosition(_tmpVideoCurrentPosition);
        final String _tmpJw_url;
        _tmpJw_url = _cursor.getString(_cursorIndexOfJwUrl);
        _item.setJw_url(_tmpJw_url);
        final String _tmpIs_selected;
        _tmpIs_selected = _cursor.getString(_cursorIndexOfIsSelected);
        _item.setIs_selected(_tmpIs_selected);
        final String _tmpVideo_history;
        _tmpVideo_history = _cursor.getString(_cursorIndexOfVideoHistory);
        _item.setVideo_history(_tmpVideo_history);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<VideosDownload> getallcourse_id(String courseid, String userid) {
    final String _sql = "SELECT * FROM VideoDownload  WHERE course_id LIKE '%' || ? || '%'  AND user_id =?";
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
      final int _cursorIndexOfVideoId = _cursor.getColumnIndexOrThrow("video_id");
      final int _cursorIndexOfVideoName = _cursor.getColumnIndexOrThrow("video_name");
      final int _cursorIndexOfOriginalFileLengthString = _cursor.getColumnIndexOrThrow("originalFileLengthString");
      final int _cursorIndexOfVideotime = _cursor.getColumnIndexOrThrow("videotime");
      final int _cursorIndexOfToalDownloadlocale = _cursor.getColumnIndexOrThrow("total");
      final int _cursorIndexOfLengthInMb = _cursor.getColumnIndexOrThrow("lengthInMb");
      final int _cursorIndexOfPercentage = _cursor.getColumnIndexOrThrow("percentage");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final int _cursorIndexOfCourseId = _cursor.getColumnIndexOrThrow("course_id");
      final int _cursorIndexOfValidTo = _cursor.getColumnIndexOrThrow("valid_to");
      final int _cursorIndexOfPosition = _cursor.getColumnIndexOrThrow("position");
      final int _cursorIndexOfMp4DownloadUrl = _cursor.getColumnIndexOrThrow("mp4_download_url");
      final int _cursorIndexOfVideoStatus = _cursor.getColumnIndexOrThrow("video_status");
      final int _cursorIndexOfVideoEnc = _cursor.getColumnIndexOrThrow("video_enc");
      final int _cursorIndexOfVideoToken = _cursor.getColumnIndexOrThrow("video_token");
      final int _cursorIndexOfThumbnailUrl = _cursor.getColumnIndexOrThrow("thumbnail_url");
      final int _cursorIndexOfIsComplete = _cursor.getColumnIndexOrThrow("is_complete");
      final int _cursorIndexOfVideoCurrentPosition = _cursor.getColumnIndexOrThrow("videoCurrentPosition");
      final int _cursorIndexOfJwUrl = _cursor.getColumnIndexOrThrow("jw_url");
      final int _cursorIndexOfIsSelected = _cursor.getColumnIndexOrThrow("is_selected");
      final int _cursorIndexOfVideoHistory = _cursor.getColumnIndexOrThrow("video_history");
      final List<VideosDownload> _result = new ArrayList<VideosDownload>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final VideosDownload _item;
        _item = new VideosDownload();
        final int _tmpAutoid;
        _tmpAutoid = _cursor.getInt(_cursorIndexOfAutoid);
        _item.setAutoid(_tmpAutoid);
        final String _tmpVideo_id;
        _tmpVideo_id = _cursor.getString(_cursorIndexOfVideoId);
        _item.setVideo_id(_tmpVideo_id);
        final String _tmpVideo_name;
        _tmpVideo_name = _cursor.getString(_cursorIndexOfVideoName);
        _item.setVideo_name(_tmpVideo_name);
        final String _tmpOriginalFileLengthString;
        _tmpOriginalFileLengthString = _cursor.getString(_cursorIndexOfOriginalFileLengthString);
        _item.setOriginalFileLengthString(_tmpOriginalFileLengthString);
        final String _tmpVideotime;
        _tmpVideotime = _cursor.getString(_cursorIndexOfVideotime);
        _item.setVideotime(_tmpVideotime);
        final Long _tmpToal_downloadlocale;
        if (_cursor.isNull(_cursorIndexOfToalDownloadlocale)) {
          _tmpToal_downloadlocale = null;
        } else {
          _tmpToal_downloadlocale = _cursor.getLong(_cursorIndexOfToalDownloadlocale);
        }
        _item.setToal_downloadlocale(_tmpToal_downloadlocale);
        final String _tmpLengthInMb;
        _tmpLengthInMb = _cursor.getString(_cursorIndexOfLengthInMb);
        _item.setLengthInMb(_tmpLengthInMb);
        final int _tmpPercentage;
        _tmpPercentage = _cursor.getInt(_cursorIndexOfPercentage);
        _item.setPercentage(_tmpPercentage);
        final String _tmpUser_id;
        _tmpUser_id = _cursor.getString(_cursorIndexOfUserId);
        _item.setUser_id(_tmpUser_id);
        final String _tmpCourse_id;
        _tmpCourse_id = _cursor.getString(_cursorIndexOfCourseId);
        _item.setCourse_id(_tmpCourse_id);
        final int _tmpValid_to;
        _tmpValid_to = _cursor.getInt(_cursorIndexOfValidTo);
        _item.setValid_to(_tmpValid_to);
        final int _tmpPosition;
        _tmpPosition = _cursor.getInt(_cursorIndexOfPosition);
        _item.setPosition(_tmpPosition);
        final String _tmpMp4_download_url;
        _tmpMp4_download_url = _cursor.getString(_cursorIndexOfMp4DownloadUrl);
        _item.setMp4_download_url(_tmpMp4_download_url);
        final String _tmpVideo_status;
        _tmpVideo_status = _cursor.getString(_cursorIndexOfVideoStatus);
        _item.setVideo_status(_tmpVideo_status);
        final String _tmpVideo_enc;
        _tmpVideo_enc = _cursor.getString(_cursorIndexOfVideoEnc);
        _item.setVideo_enc(_tmpVideo_enc);
        final String _tmpVideo_token;
        _tmpVideo_token = _cursor.getString(_cursorIndexOfVideoToken);
        _item.setVideo_token(_tmpVideo_token);
        final String _tmpThumbnail_url;
        _tmpThumbnail_url = _cursor.getString(_cursorIndexOfThumbnailUrl);
        _item.setThumbnail_url(_tmpThumbnail_url);
        final String _tmpIs_complete;
        _tmpIs_complete = _cursor.getString(_cursorIndexOfIsComplete);
        _item.setIs_complete(_tmpIs_complete);
        final Long _tmpVideoCurrentPosition;
        if (_cursor.isNull(_cursorIndexOfVideoCurrentPosition)) {
          _tmpVideoCurrentPosition = null;
        } else {
          _tmpVideoCurrentPosition = _cursor.getLong(_cursorIndexOfVideoCurrentPosition);
        }
        _item.setVideoCurrentPosition(_tmpVideoCurrentPosition);
        final String _tmpJw_url;
        _tmpJw_url = _cursor.getString(_cursorIndexOfJwUrl);
        _item.setJw_url(_tmpJw_url);
        final String _tmpIs_selected;
        _tmpIs_selected = _cursor.getString(_cursorIndexOfIsSelected);
        _item.setIs_selected(_tmpIs_selected);
        final String _tmpVideo_history;
        _tmpVideo_history = _cursor.getString(_cursorIndexOfVideoHistory);
        _item.setVideo_history(_tmpVideo_history);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public boolean isRecordExistsUserId(String video_id, String is_complete, String user_id) {
    final String _sql = "SELECT EXISTS(SELECT * FROM VideoDownload WHERE video_id = ? AND is_complete = ?  AND user_id = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    if (video_id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, video_id);
    }
    _argIndex = 2;
    if (is_complete == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, is_complete);
    }
    _argIndex = 3;
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

  @Override
  public boolean isRecordexist_1(String video_id, String is_complete, String user_id) {
    final String _sql = "SELECT EXISTS(SELECT * FROM VideoDownload WHERE video_id = ? AND is_complete = ?  AND user_id = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    if (video_id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, video_id);
    }
    _argIndex = 2;
    if (is_complete == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, is_complete);
    }
    _argIndex = 3;
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

  @Override
  public List<String> courseids(String userid) {
    final String _sql = "SELECT  DISTINCT course_id  FROM VideoDownload  where  user_id=?";
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

  @Override
  public List<String> getlikehistiry(String user_id, String courseid) {
    final String _sql = "SELECT  DISTINCT course_id FROM VideoDownload where course_id LIKE '%' || ? || '%'  AND user_id =?";
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
  public boolean isvideo_exit(String video_id, String user_id) {
    final String _sql = "SELECT EXISTS(SELECT * FROM VideoDownload WHERE video_id = ?   AND user_id = ?)";
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
