package com.utkarshnew.android.Dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.utkarshnew.android.table.PostDataTable;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class FeedsDao_Impl implements FeedsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfPostDataTable;

  private final SharedSQLiteStatement __preparedStmtOfDeletedata;

  private final SharedSQLiteStatement __preparedStmtOfUpdatePinnedPost;

  private final SharedSQLiteStatement __preparedStmtOfUpdate_result;

  private final SharedSQLiteStatement __preparedStmtOfUpdateMyLike;

  private final SharedSQLiteStatement __preparedStmtOfUpdateMyjson;

  private final SharedSQLiteStatement __preparedStmtOfDeletePosts_via_id;

  private final SharedSQLiteStatement __preparedStmtOfDeletePost;

  private final SharedSQLiteStatement __preparedStmtOfDeletePosts_via_post_type;

  private final SharedSQLiteStatement __preparedStmtOfDeletepost_without_liveclass;

  private final SharedSQLiteStatement __preparedStmtOfDeleteSubCatFeed;

  public FeedsDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPostDataTable = new EntityInsertionAdapter<PostDataTable>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `PostDataTable`(`created`,`page`,`masterCat`,`postId`,`id`,`json`,`meta_url`,`thumbnail`,`url`,`modified`,`my_like`,`name`,`post_type`,`profile_picture`,`status`,`sub_cat_id`,`text`,`total_comments`,`total_likes`,`user_id`,`newCourseData`,`livetest`,`liveclass`,`testResult`,`bannerlist`,`liveClassStatus`,`liveTestStatus`,`iscommentenable`,`sectionposiiton`,`limit`,`my_pinned`,`parentId`,`description`,`image_type`) VALUES (?,?,?,nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PostDataTable value) {
        if (value.getCreated() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getCreated());
        }
        if (value.getPage() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getPage());
        }
        if (value.getMasterCat() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getMasterCat());
        }
        stmt.bindLong(4, value.getPostId());
        if (value.getId() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getId());
        }
        if (value.getJson() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getJson());
        }
        if (value.getMeta_url() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getMeta_url());
        }
        if (value.getThumbnail() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getThumbnail());
        }
        if (value.getUrl() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getUrl());
        }
        if (value.getModified() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getModified());
        }
        if (value.getMy_like() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getMy_like());
        }
        if (value.getName() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getName());
        }
        if (value.getPost_type() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getPost_type());
        }
        if (value.getProfile_picture() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getProfile_picture());
        }
        if (value.getStatus() == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.getStatus());
        }
        if (value.getSub_cat_id() == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindString(16, value.getSub_cat_id());
        }
        if (value.getText() == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindString(17, value.getText());
        }
        if (value.getTotal_comments() == null) {
          stmt.bindNull(18);
        } else {
          stmt.bindString(18, value.getTotal_comments());
        }
        if (value.getTotal_likes() == null) {
          stmt.bindNull(19);
        } else {
          stmt.bindString(19, value.getTotal_likes());
        }
        if (value.getUser_id() == null) {
          stmt.bindNull(20);
        } else {
          stmt.bindString(20, value.getUser_id());
        }
        if (value.getNewCourseData() == null) {
          stmt.bindNull(21);
        } else {
          stmt.bindString(21, value.getNewCourseData());
        }
        if (value.getLivetest() == null) {
          stmt.bindNull(22);
        } else {
          stmt.bindString(22, value.getLivetest());
        }
        if (value.getLiveclass() == null) {
          stmt.bindNull(23);
        } else {
          stmt.bindString(23, value.getLiveclass());
        }
        if (value.getTestResult() == null) {
          stmt.bindNull(24);
        } else {
          stmt.bindString(24, value.getTestResult());
        }
        if (value.getBannerlist() == null) {
          stmt.bindNull(25);
        } else {
          stmt.bindString(25, value.getBannerlist());
        }
        if (value.getLiveClassStatus() == null) {
          stmt.bindNull(26);
        } else {
          stmt.bindString(26, value.getLiveClassStatus());
        }
        if (value.getLiveTestStatus() == null) {
          stmt.bindNull(27);
        } else {
          stmt.bindString(27, value.getLiveTestStatus());
        }
        if (value.getIscommentenable() == null) {
          stmt.bindNull(28);
        } else {
          stmt.bindString(28, value.getIscommentenable());
        }
        if (value.getSectionposiiton() == null) {
          stmt.bindNull(29);
        } else {
          stmt.bindString(29, value.getSectionposiiton());
        }
        if (value.getLimit() == null) {
          stmt.bindNull(30);
        } else {
          stmt.bindString(30, value.getLimit());
        }
        if (value.getMy_pinned() == null) {
          stmt.bindNull(31);
        } else {
          stmt.bindString(31, value.getMy_pinned());
        }
        if (value.getParentId() == null) {
          stmt.bindNull(32);
        } else {
          stmt.bindString(32, value.getParentId());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(33);
        } else {
          stmt.bindString(33, value.getDescription());
        }
        if (value.getImage_type() == null) {
          stmt.bindNull(34);
        } else {
          stmt.bindString(34, value.getImage_type());
        }
      }
    };
    this.__preparedStmtOfDeletedata = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM PostDataTable";
        return _query;
      }
    };
    this.__preparedStmtOfUpdatePinnedPost = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE PostDataTable  SET  my_pinned =?  WHERE id = ? ";
        return _query;
      }
    };
    this.__preparedStmtOfUpdate_result = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE PostDataTable  SET  json =?  WHERE id = ? AND masterCat =? ";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateMyLike = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE PostDataTable  SET  my_like =?,total_likes =?   WHERE id = ? ";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateMyjson = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE PostDataTable  SET  json =?   WHERE id = ? ";
        return _query;
      }
    };
    this.__preparedStmtOfDeletePosts_via_id = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM PostDataTable where masterCat =?";
        return _query;
      }
    };
    this.__preparedStmtOfDeletePost = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM PostDataTable where masterCat =? AND sub_cat_id =?";
        return _query;
      }
    };
    this.__preparedStmtOfDeletePosts_via_post_type = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM PostDataTable where masterCat =? AND post_type IN (?,?) ";
        return _query;
      }
    };
    this.__preparedStmtOfDeletepost_without_liveclass = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM PostDataTable where masterCat =? AND post_type NOT IN (?,?) ";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteSubCatFeed = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM PostDataTable where masterCat =? And sub_cat_id =? AND post_type NOT IN (?,?) ";
        return _query;
      }
    };
  }

  @Override
  public void insertPost(PostDataTable post) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfPostDataTable.insert(post);
      __db.setTransactionSuccessful();
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
  public void updatePinnedPost(String pinnedPost, String post_id) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdatePinnedPost.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (pinnedPost == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, pinnedPost);
      }
      _argIndex = 2;
      if (post_id == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, post_id);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdatePinnedPost.release(_stmt);
    }
  }

  @Override
  public void update_result(String json, String post_id, String mainid) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdate_result.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (json == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, json);
      }
      _argIndex = 2;
      if (post_id == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, post_id);
      }
      _argIndex = 3;
      if (mainid == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, mainid);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdate_result.release(_stmt);
    }
  }

  @Override
  public void updateMyLike(String post_id, String myLike, String totalike) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateMyLike.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (myLike == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, myLike);
      }
      _argIndex = 2;
      if (totalike == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, totalike);
      }
      _argIndex = 3;
      if (post_id == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, post_id);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateMyLike.release(_stmt);
    }
  }

  @Override
  public void updateMyjson(String post_id, String json) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateMyjson.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (json == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, json);
      }
      _argIndex = 2;
      if (post_id == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, post_id);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateMyjson.release(_stmt);
    }
  }

  @Override
  public void deletePosts() {
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
  public void deletePosts_via_id(String mainCat) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeletePosts_via_id.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (mainCat == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, mainCat);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeletePosts_via_id.release(_stmt);
    }
  }

  @Override
  public void deletePost(String mainCat, String subcat) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeletePost.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (mainCat == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, mainCat);
      }
      _argIndex = 2;
      if (subcat == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, subcat);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeletePost.release(_stmt);
    }
  }

  @Override
  public void deletePosts_via_post_type(String mainCat, String liveclassposttype,
      String livetestposttype) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeletePosts_via_post_type.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (mainCat == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, mainCat);
      }
      _argIndex = 2;
      if (liveclassposttype == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, liveclassposttype);
      }
      _argIndex = 3;
      if (livetestposttype == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, livetestposttype);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeletePosts_via_post_type.release(_stmt);
    }
  }

  @Override
  public void deletepost_without_liveclass(String mainCat, String liveclassposttype,
      String livetestposttype) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeletepost_without_liveclass.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (mainCat == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, mainCat);
      }
      _argIndex = 2;
      if (liveclassposttype == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, liveclassposttype);
      }
      _argIndex = 3;
      if (livetestposttype == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, livetestposttype);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeletepost_without_liveclass.release(_stmt);
    }
  }

  @Override
  public void deleteSubCatFeed(String mainCat, String subcat, String liveclassposttype,
      String livetestposttype) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteSubCatFeed.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (mainCat == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, mainCat);
      }
      _argIndex = 2;
      if (subcat == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, subcat);
      }
      _argIndex = 3;
      if (liveclassposttype == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, liveclassposttype);
      }
      _argIndex = 4;
      if (livetestposttype == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, livetestposttype);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteSubCatFeed.release(_stmt);
    }
  }

  @Override
  public List<PostDataTable> retrievePostData(String mainCat) {
    final String _sql = "SELECT * FROM PostDataTable WHERE masterCat = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (mainCat == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, mainCat);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfCreated = _cursor.getColumnIndexOrThrow("created");
      final int _cursorIndexOfPage = _cursor.getColumnIndexOrThrow("page");
      final int _cursorIndexOfMasterCat = _cursor.getColumnIndexOrThrow("masterCat");
      final int _cursorIndexOfPostId = _cursor.getColumnIndexOrThrow("postId");
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfJson = _cursor.getColumnIndexOrThrow("json");
      final int _cursorIndexOfMetaUrl = _cursor.getColumnIndexOrThrow("meta_url");
      final int _cursorIndexOfThumbnail = _cursor.getColumnIndexOrThrow("thumbnail");
      final int _cursorIndexOfUrl = _cursor.getColumnIndexOrThrow("url");
      final int _cursorIndexOfModified = _cursor.getColumnIndexOrThrow("modified");
      final int _cursorIndexOfMyLike = _cursor.getColumnIndexOrThrow("my_like");
      final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
      final int _cursorIndexOfPostType = _cursor.getColumnIndexOrThrow("post_type");
      final int _cursorIndexOfProfilePicture = _cursor.getColumnIndexOrThrow("profile_picture");
      final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("status");
      final int _cursorIndexOfSubCatId = _cursor.getColumnIndexOrThrow("sub_cat_id");
      final int _cursorIndexOfText = _cursor.getColumnIndexOrThrow("text");
      final int _cursorIndexOfTotalComments = _cursor.getColumnIndexOrThrow("total_comments");
      final int _cursorIndexOfTotalLikes = _cursor.getColumnIndexOrThrow("total_likes");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final int _cursorIndexOfNewCourseData = _cursor.getColumnIndexOrThrow("newCourseData");
      final int _cursorIndexOfLivetest = _cursor.getColumnIndexOrThrow("livetest");
      final int _cursorIndexOfLiveclass = _cursor.getColumnIndexOrThrow("liveclass");
      final int _cursorIndexOfTestResult = _cursor.getColumnIndexOrThrow("testResult");
      final int _cursorIndexOfBannerlist = _cursor.getColumnIndexOrThrow("bannerlist");
      final int _cursorIndexOfLiveClassStatus = _cursor.getColumnIndexOrThrow("liveClassStatus");
      final int _cursorIndexOfLiveTestStatus = _cursor.getColumnIndexOrThrow("liveTestStatus");
      final int _cursorIndexOfIscommentenable = _cursor.getColumnIndexOrThrow("iscommentenable");
      final int _cursorIndexOfSectionposiiton = _cursor.getColumnIndexOrThrow("sectionposiiton");
      final int _cursorIndexOfLimit = _cursor.getColumnIndexOrThrow("limit");
      final int _cursorIndexOfMyPinned = _cursor.getColumnIndexOrThrow("my_pinned");
      final int _cursorIndexOfParentId = _cursor.getColumnIndexOrThrow("parentId");
      final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
      final int _cursorIndexOfImageType = _cursor.getColumnIndexOrThrow("image_type");
      final List<PostDataTable> _result = new ArrayList<PostDataTable>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final PostDataTable _item;
        _item = new PostDataTable();
        final String _tmpCreated;
        _tmpCreated = _cursor.getString(_cursorIndexOfCreated);
        _item.setCreated(_tmpCreated);
        final String _tmpPage;
        _tmpPage = _cursor.getString(_cursorIndexOfPage);
        _item.setPage(_tmpPage);
        final String _tmpMasterCat;
        _tmpMasterCat = _cursor.getString(_cursorIndexOfMasterCat);
        _item.setMasterCat(_tmpMasterCat);
        final int _tmpPostId;
        _tmpPostId = _cursor.getInt(_cursorIndexOfPostId);
        _item.setPostId(_tmpPostId);
        final String _tmpId;
        _tmpId = _cursor.getString(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpJson;
        _tmpJson = _cursor.getString(_cursorIndexOfJson);
        _item.setJson(_tmpJson);
        final String _tmpMeta_url;
        _tmpMeta_url = _cursor.getString(_cursorIndexOfMetaUrl);
        _item.setMeta_url(_tmpMeta_url);
        final String _tmpThumbnail;
        _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
        _item.setThumbnail(_tmpThumbnail);
        final String _tmpUrl;
        _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
        _item.setUrl(_tmpUrl);
        final String _tmpModified;
        _tmpModified = _cursor.getString(_cursorIndexOfModified);
        _item.setModified(_tmpModified);
        final String _tmpMy_like;
        _tmpMy_like = _cursor.getString(_cursorIndexOfMyLike);
        _item.setMy_like(_tmpMy_like);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        _item.setName(_tmpName);
        final String _tmpPost_type;
        _tmpPost_type = _cursor.getString(_cursorIndexOfPostType);
        _item.setPost_type(_tmpPost_type);
        final String _tmpProfile_picture;
        _tmpProfile_picture = _cursor.getString(_cursorIndexOfProfilePicture);
        _item.setProfile_picture(_tmpProfile_picture);
        final String _tmpStatus;
        _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        _item.setStatus(_tmpStatus);
        final String _tmpSub_cat_id;
        _tmpSub_cat_id = _cursor.getString(_cursorIndexOfSubCatId);
        _item.setSub_cat_id(_tmpSub_cat_id);
        final String _tmpText;
        _tmpText = _cursor.getString(_cursorIndexOfText);
        _item.setText(_tmpText);
        final String _tmpTotal_comments;
        _tmpTotal_comments = _cursor.getString(_cursorIndexOfTotalComments);
        _item.setTotal_comments(_tmpTotal_comments);
        final String _tmpTotal_likes;
        _tmpTotal_likes = _cursor.getString(_cursorIndexOfTotalLikes);
        _item.setTotal_likes(_tmpTotal_likes);
        final String _tmpUser_id;
        _tmpUser_id = _cursor.getString(_cursorIndexOfUserId);
        _item.setUser_id(_tmpUser_id);
        final String _tmpNewCourseData;
        _tmpNewCourseData = _cursor.getString(_cursorIndexOfNewCourseData);
        _item.setNewCourseData(_tmpNewCourseData);
        final String _tmpLivetest;
        _tmpLivetest = _cursor.getString(_cursorIndexOfLivetest);
        _item.setLivetest(_tmpLivetest);
        final String _tmpLiveclass;
        _tmpLiveclass = _cursor.getString(_cursorIndexOfLiveclass);
        _item.setLiveclass(_tmpLiveclass);
        final String _tmpTestResult;
        _tmpTestResult = _cursor.getString(_cursorIndexOfTestResult);
        _item.setTestResult(_tmpTestResult);
        final String _tmpBannerlist;
        _tmpBannerlist = _cursor.getString(_cursorIndexOfBannerlist);
        _item.setBannerlist(_tmpBannerlist);
        final String _tmpLiveClassStatus;
        _tmpLiveClassStatus = _cursor.getString(_cursorIndexOfLiveClassStatus);
        _item.setLiveClassStatus(_tmpLiveClassStatus);
        final String _tmpLiveTestStatus;
        _tmpLiveTestStatus = _cursor.getString(_cursorIndexOfLiveTestStatus);
        _item.setLiveTestStatus(_tmpLiveTestStatus);
        final String _tmpIscommentenable;
        _tmpIscommentenable = _cursor.getString(_cursorIndexOfIscommentenable);
        _item.setIscommentenable(_tmpIscommentenable);
        final String _tmpSectionposiiton;
        _tmpSectionposiiton = _cursor.getString(_cursorIndexOfSectionposiiton);
        _item.setSectionposiiton(_tmpSectionposiiton);
        final String _tmpLimit;
        _tmpLimit = _cursor.getString(_cursorIndexOfLimit);
        _item.setLimit(_tmpLimit);
        final String _tmpMy_pinned;
        _tmpMy_pinned = _cursor.getString(_cursorIndexOfMyPinned);
        _item.setMy_pinned(_tmpMy_pinned);
        final String _tmpParentId;
        _tmpParentId = _cursor.getString(_cursorIndexOfParentId);
        _item.setParentId(_tmpParentId);
        final String _tmpDescription;
        _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        _item.setDescription(_tmpDescription);
        final String _tmpImage_type;
        _tmpImage_type = _cursor.getString(_cursorIndexOfImageType);
        _item.setImage_type(_tmpImage_type);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<PostDataTable> retrievePostData(String parentId, String main, String subcat) {
    final String _sql = "SELECT * FROM PostDataTable WHERE parentId = ? AND masterCat =? AND sub_cat_id =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    if (parentId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, parentId);
    }
    _argIndex = 2;
    if (main == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, main);
    }
    _argIndex = 3;
    if (subcat == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, subcat);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfCreated = _cursor.getColumnIndexOrThrow("created");
      final int _cursorIndexOfPage = _cursor.getColumnIndexOrThrow("page");
      final int _cursorIndexOfMasterCat = _cursor.getColumnIndexOrThrow("masterCat");
      final int _cursorIndexOfPostId = _cursor.getColumnIndexOrThrow("postId");
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfJson = _cursor.getColumnIndexOrThrow("json");
      final int _cursorIndexOfMetaUrl = _cursor.getColumnIndexOrThrow("meta_url");
      final int _cursorIndexOfThumbnail = _cursor.getColumnIndexOrThrow("thumbnail");
      final int _cursorIndexOfUrl = _cursor.getColumnIndexOrThrow("url");
      final int _cursorIndexOfModified = _cursor.getColumnIndexOrThrow("modified");
      final int _cursorIndexOfMyLike = _cursor.getColumnIndexOrThrow("my_like");
      final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
      final int _cursorIndexOfPostType = _cursor.getColumnIndexOrThrow("post_type");
      final int _cursorIndexOfProfilePicture = _cursor.getColumnIndexOrThrow("profile_picture");
      final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("status");
      final int _cursorIndexOfSubCatId = _cursor.getColumnIndexOrThrow("sub_cat_id");
      final int _cursorIndexOfText = _cursor.getColumnIndexOrThrow("text");
      final int _cursorIndexOfTotalComments = _cursor.getColumnIndexOrThrow("total_comments");
      final int _cursorIndexOfTotalLikes = _cursor.getColumnIndexOrThrow("total_likes");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final int _cursorIndexOfNewCourseData = _cursor.getColumnIndexOrThrow("newCourseData");
      final int _cursorIndexOfLivetest = _cursor.getColumnIndexOrThrow("livetest");
      final int _cursorIndexOfLiveclass = _cursor.getColumnIndexOrThrow("liveclass");
      final int _cursorIndexOfTestResult = _cursor.getColumnIndexOrThrow("testResult");
      final int _cursorIndexOfBannerlist = _cursor.getColumnIndexOrThrow("bannerlist");
      final int _cursorIndexOfLiveClassStatus = _cursor.getColumnIndexOrThrow("liveClassStatus");
      final int _cursorIndexOfLiveTestStatus = _cursor.getColumnIndexOrThrow("liveTestStatus");
      final int _cursorIndexOfIscommentenable = _cursor.getColumnIndexOrThrow("iscommentenable");
      final int _cursorIndexOfSectionposiiton = _cursor.getColumnIndexOrThrow("sectionposiiton");
      final int _cursorIndexOfLimit = _cursor.getColumnIndexOrThrow("limit");
      final int _cursorIndexOfMyPinned = _cursor.getColumnIndexOrThrow("my_pinned");
      final int _cursorIndexOfParentId = _cursor.getColumnIndexOrThrow("parentId");
      final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
      final int _cursorIndexOfImageType = _cursor.getColumnIndexOrThrow("image_type");
      final List<PostDataTable> _result = new ArrayList<PostDataTable>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final PostDataTable _item;
        _item = new PostDataTable();
        final String _tmpCreated;
        _tmpCreated = _cursor.getString(_cursorIndexOfCreated);
        _item.setCreated(_tmpCreated);
        final String _tmpPage;
        _tmpPage = _cursor.getString(_cursorIndexOfPage);
        _item.setPage(_tmpPage);
        final String _tmpMasterCat;
        _tmpMasterCat = _cursor.getString(_cursorIndexOfMasterCat);
        _item.setMasterCat(_tmpMasterCat);
        final int _tmpPostId;
        _tmpPostId = _cursor.getInt(_cursorIndexOfPostId);
        _item.setPostId(_tmpPostId);
        final String _tmpId;
        _tmpId = _cursor.getString(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpJson;
        _tmpJson = _cursor.getString(_cursorIndexOfJson);
        _item.setJson(_tmpJson);
        final String _tmpMeta_url;
        _tmpMeta_url = _cursor.getString(_cursorIndexOfMetaUrl);
        _item.setMeta_url(_tmpMeta_url);
        final String _tmpThumbnail;
        _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
        _item.setThumbnail(_tmpThumbnail);
        final String _tmpUrl;
        _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
        _item.setUrl(_tmpUrl);
        final String _tmpModified;
        _tmpModified = _cursor.getString(_cursorIndexOfModified);
        _item.setModified(_tmpModified);
        final String _tmpMy_like;
        _tmpMy_like = _cursor.getString(_cursorIndexOfMyLike);
        _item.setMy_like(_tmpMy_like);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        _item.setName(_tmpName);
        final String _tmpPost_type;
        _tmpPost_type = _cursor.getString(_cursorIndexOfPostType);
        _item.setPost_type(_tmpPost_type);
        final String _tmpProfile_picture;
        _tmpProfile_picture = _cursor.getString(_cursorIndexOfProfilePicture);
        _item.setProfile_picture(_tmpProfile_picture);
        final String _tmpStatus;
        _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        _item.setStatus(_tmpStatus);
        final String _tmpSub_cat_id;
        _tmpSub_cat_id = _cursor.getString(_cursorIndexOfSubCatId);
        _item.setSub_cat_id(_tmpSub_cat_id);
        final String _tmpText;
        _tmpText = _cursor.getString(_cursorIndexOfText);
        _item.setText(_tmpText);
        final String _tmpTotal_comments;
        _tmpTotal_comments = _cursor.getString(_cursorIndexOfTotalComments);
        _item.setTotal_comments(_tmpTotal_comments);
        final String _tmpTotal_likes;
        _tmpTotal_likes = _cursor.getString(_cursorIndexOfTotalLikes);
        _item.setTotal_likes(_tmpTotal_likes);
        final String _tmpUser_id;
        _tmpUser_id = _cursor.getString(_cursorIndexOfUserId);
        _item.setUser_id(_tmpUser_id);
        final String _tmpNewCourseData;
        _tmpNewCourseData = _cursor.getString(_cursorIndexOfNewCourseData);
        _item.setNewCourseData(_tmpNewCourseData);
        final String _tmpLivetest;
        _tmpLivetest = _cursor.getString(_cursorIndexOfLivetest);
        _item.setLivetest(_tmpLivetest);
        final String _tmpLiveclass;
        _tmpLiveclass = _cursor.getString(_cursorIndexOfLiveclass);
        _item.setLiveclass(_tmpLiveclass);
        final String _tmpTestResult;
        _tmpTestResult = _cursor.getString(_cursorIndexOfTestResult);
        _item.setTestResult(_tmpTestResult);
        final String _tmpBannerlist;
        _tmpBannerlist = _cursor.getString(_cursorIndexOfBannerlist);
        _item.setBannerlist(_tmpBannerlist);
        final String _tmpLiveClassStatus;
        _tmpLiveClassStatus = _cursor.getString(_cursorIndexOfLiveClassStatus);
        _item.setLiveClassStatus(_tmpLiveClassStatus);
        final String _tmpLiveTestStatus;
        _tmpLiveTestStatus = _cursor.getString(_cursorIndexOfLiveTestStatus);
        _item.setLiveTestStatus(_tmpLiveTestStatus);
        final String _tmpIscommentenable;
        _tmpIscommentenable = _cursor.getString(_cursorIndexOfIscommentenable);
        _item.setIscommentenable(_tmpIscommentenable);
        final String _tmpSectionposiiton;
        _tmpSectionposiiton = _cursor.getString(_cursorIndexOfSectionposiiton);
        _item.setSectionposiiton(_tmpSectionposiiton);
        final String _tmpLimit;
        _tmpLimit = _cursor.getString(_cursorIndexOfLimit);
        _item.setLimit(_tmpLimit);
        final String _tmpMy_pinned;
        _tmpMy_pinned = _cursor.getString(_cursorIndexOfMyPinned);
        _item.setMy_pinned(_tmpMy_pinned);
        final String _tmpParentId;
        _tmpParentId = _cursor.getString(_cursorIndexOfParentId);
        _item.setParentId(_tmpParentId);
        final String _tmpDescription;
        _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        _item.setDescription(_tmpDescription);
        final String _tmpImage_type;
        _tmpImage_type = _cursor.getString(_cursorIndexOfImageType);
        _item.setImage_type(_tmpImage_type);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<PostDataTable> retrievePostData_viaposttype(String parentId, String main,
      String subcat, String posttype) {
    final String _sql = "SELECT * FROM PostDataTable WHERE parentId = ? AND masterCat =? AND sub_cat_id =? And post_type =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 4);
    int _argIndex = 1;
    if (parentId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, parentId);
    }
    _argIndex = 2;
    if (main == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, main);
    }
    _argIndex = 3;
    if (subcat == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, subcat);
    }
    _argIndex = 4;
    if (posttype == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, posttype);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfCreated = _cursor.getColumnIndexOrThrow("created");
      final int _cursorIndexOfPage = _cursor.getColumnIndexOrThrow("page");
      final int _cursorIndexOfMasterCat = _cursor.getColumnIndexOrThrow("masterCat");
      final int _cursorIndexOfPostId = _cursor.getColumnIndexOrThrow("postId");
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfJson = _cursor.getColumnIndexOrThrow("json");
      final int _cursorIndexOfMetaUrl = _cursor.getColumnIndexOrThrow("meta_url");
      final int _cursorIndexOfThumbnail = _cursor.getColumnIndexOrThrow("thumbnail");
      final int _cursorIndexOfUrl = _cursor.getColumnIndexOrThrow("url");
      final int _cursorIndexOfModified = _cursor.getColumnIndexOrThrow("modified");
      final int _cursorIndexOfMyLike = _cursor.getColumnIndexOrThrow("my_like");
      final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
      final int _cursorIndexOfPostType = _cursor.getColumnIndexOrThrow("post_type");
      final int _cursorIndexOfProfilePicture = _cursor.getColumnIndexOrThrow("profile_picture");
      final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("status");
      final int _cursorIndexOfSubCatId = _cursor.getColumnIndexOrThrow("sub_cat_id");
      final int _cursorIndexOfText = _cursor.getColumnIndexOrThrow("text");
      final int _cursorIndexOfTotalComments = _cursor.getColumnIndexOrThrow("total_comments");
      final int _cursorIndexOfTotalLikes = _cursor.getColumnIndexOrThrow("total_likes");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final int _cursorIndexOfNewCourseData = _cursor.getColumnIndexOrThrow("newCourseData");
      final int _cursorIndexOfLivetest = _cursor.getColumnIndexOrThrow("livetest");
      final int _cursorIndexOfLiveclass = _cursor.getColumnIndexOrThrow("liveclass");
      final int _cursorIndexOfTestResult = _cursor.getColumnIndexOrThrow("testResult");
      final int _cursorIndexOfBannerlist = _cursor.getColumnIndexOrThrow("bannerlist");
      final int _cursorIndexOfLiveClassStatus = _cursor.getColumnIndexOrThrow("liveClassStatus");
      final int _cursorIndexOfLiveTestStatus = _cursor.getColumnIndexOrThrow("liveTestStatus");
      final int _cursorIndexOfIscommentenable = _cursor.getColumnIndexOrThrow("iscommentenable");
      final int _cursorIndexOfSectionposiiton = _cursor.getColumnIndexOrThrow("sectionposiiton");
      final int _cursorIndexOfLimit = _cursor.getColumnIndexOrThrow("limit");
      final int _cursorIndexOfMyPinned = _cursor.getColumnIndexOrThrow("my_pinned");
      final int _cursorIndexOfParentId = _cursor.getColumnIndexOrThrow("parentId");
      final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
      final int _cursorIndexOfImageType = _cursor.getColumnIndexOrThrow("image_type");
      final List<PostDataTable> _result = new ArrayList<PostDataTable>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final PostDataTable _item;
        _item = new PostDataTable();
        final String _tmpCreated;
        _tmpCreated = _cursor.getString(_cursorIndexOfCreated);
        _item.setCreated(_tmpCreated);
        final String _tmpPage;
        _tmpPage = _cursor.getString(_cursorIndexOfPage);
        _item.setPage(_tmpPage);
        final String _tmpMasterCat;
        _tmpMasterCat = _cursor.getString(_cursorIndexOfMasterCat);
        _item.setMasterCat(_tmpMasterCat);
        final int _tmpPostId;
        _tmpPostId = _cursor.getInt(_cursorIndexOfPostId);
        _item.setPostId(_tmpPostId);
        final String _tmpId;
        _tmpId = _cursor.getString(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpJson;
        _tmpJson = _cursor.getString(_cursorIndexOfJson);
        _item.setJson(_tmpJson);
        final String _tmpMeta_url;
        _tmpMeta_url = _cursor.getString(_cursorIndexOfMetaUrl);
        _item.setMeta_url(_tmpMeta_url);
        final String _tmpThumbnail;
        _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
        _item.setThumbnail(_tmpThumbnail);
        final String _tmpUrl;
        _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
        _item.setUrl(_tmpUrl);
        final String _tmpModified;
        _tmpModified = _cursor.getString(_cursorIndexOfModified);
        _item.setModified(_tmpModified);
        final String _tmpMy_like;
        _tmpMy_like = _cursor.getString(_cursorIndexOfMyLike);
        _item.setMy_like(_tmpMy_like);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        _item.setName(_tmpName);
        final String _tmpPost_type;
        _tmpPost_type = _cursor.getString(_cursorIndexOfPostType);
        _item.setPost_type(_tmpPost_type);
        final String _tmpProfile_picture;
        _tmpProfile_picture = _cursor.getString(_cursorIndexOfProfilePicture);
        _item.setProfile_picture(_tmpProfile_picture);
        final String _tmpStatus;
        _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        _item.setStatus(_tmpStatus);
        final String _tmpSub_cat_id;
        _tmpSub_cat_id = _cursor.getString(_cursorIndexOfSubCatId);
        _item.setSub_cat_id(_tmpSub_cat_id);
        final String _tmpText;
        _tmpText = _cursor.getString(_cursorIndexOfText);
        _item.setText(_tmpText);
        final String _tmpTotal_comments;
        _tmpTotal_comments = _cursor.getString(_cursorIndexOfTotalComments);
        _item.setTotal_comments(_tmpTotal_comments);
        final String _tmpTotal_likes;
        _tmpTotal_likes = _cursor.getString(_cursorIndexOfTotalLikes);
        _item.setTotal_likes(_tmpTotal_likes);
        final String _tmpUser_id;
        _tmpUser_id = _cursor.getString(_cursorIndexOfUserId);
        _item.setUser_id(_tmpUser_id);
        final String _tmpNewCourseData;
        _tmpNewCourseData = _cursor.getString(_cursorIndexOfNewCourseData);
        _item.setNewCourseData(_tmpNewCourseData);
        final String _tmpLivetest;
        _tmpLivetest = _cursor.getString(_cursorIndexOfLivetest);
        _item.setLivetest(_tmpLivetest);
        final String _tmpLiveclass;
        _tmpLiveclass = _cursor.getString(_cursorIndexOfLiveclass);
        _item.setLiveclass(_tmpLiveclass);
        final String _tmpTestResult;
        _tmpTestResult = _cursor.getString(_cursorIndexOfTestResult);
        _item.setTestResult(_tmpTestResult);
        final String _tmpBannerlist;
        _tmpBannerlist = _cursor.getString(_cursorIndexOfBannerlist);
        _item.setBannerlist(_tmpBannerlist);
        final String _tmpLiveClassStatus;
        _tmpLiveClassStatus = _cursor.getString(_cursorIndexOfLiveClassStatus);
        _item.setLiveClassStatus(_tmpLiveClassStatus);
        final String _tmpLiveTestStatus;
        _tmpLiveTestStatus = _cursor.getString(_cursorIndexOfLiveTestStatus);
        _item.setLiveTestStatus(_tmpLiveTestStatus);
        final String _tmpIscommentenable;
        _tmpIscommentenable = _cursor.getString(_cursorIndexOfIscommentenable);
        _item.setIscommentenable(_tmpIscommentenable);
        final String _tmpSectionposiiton;
        _tmpSectionposiiton = _cursor.getString(_cursorIndexOfSectionposiiton);
        _item.setSectionposiiton(_tmpSectionposiiton);
        final String _tmpLimit;
        _tmpLimit = _cursor.getString(_cursorIndexOfLimit);
        _item.setLimit(_tmpLimit);
        final String _tmpMy_pinned;
        _tmpMy_pinned = _cursor.getString(_cursorIndexOfMyPinned);
        _item.setMy_pinned(_tmpMy_pinned);
        final String _tmpParentId;
        _tmpParentId = _cursor.getString(_cursorIndexOfParentId);
        _item.setParentId(_tmpParentId);
        final String _tmpDescription;
        _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        _item.setDescription(_tmpDescription);
        final String _tmpImage_type;
        _tmpImage_type = _cursor.getString(_cursorIndexOfImageType);
        _item.setImage_type(_tmpImage_type);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<PostDataTable> retrievePostData_viaposttype_withoutsubcat(String parentId,
      String main, String posttype) {
    final String _sql = "SELECT * FROM PostDataTable WHERE parentId = ? AND masterCat =?  And post_type =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    if (parentId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, parentId);
    }
    _argIndex = 2;
    if (main == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, main);
    }
    _argIndex = 3;
    if (posttype == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, posttype);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfCreated = _cursor.getColumnIndexOrThrow("created");
      final int _cursorIndexOfPage = _cursor.getColumnIndexOrThrow("page");
      final int _cursorIndexOfMasterCat = _cursor.getColumnIndexOrThrow("masterCat");
      final int _cursorIndexOfPostId = _cursor.getColumnIndexOrThrow("postId");
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfJson = _cursor.getColumnIndexOrThrow("json");
      final int _cursorIndexOfMetaUrl = _cursor.getColumnIndexOrThrow("meta_url");
      final int _cursorIndexOfThumbnail = _cursor.getColumnIndexOrThrow("thumbnail");
      final int _cursorIndexOfUrl = _cursor.getColumnIndexOrThrow("url");
      final int _cursorIndexOfModified = _cursor.getColumnIndexOrThrow("modified");
      final int _cursorIndexOfMyLike = _cursor.getColumnIndexOrThrow("my_like");
      final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
      final int _cursorIndexOfPostType = _cursor.getColumnIndexOrThrow("post_type");
      final int _cursorIndexOfProfilePicture = _cursor.getColumnIndexOrThrow("profile_picture");
      final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("status");
      final int _cursorIndexOfSubCatId = _cursor.getColumnIndexOrThrow("sub_cat_id");
      final int _cursorIndexOfText = _cursor.getColumnIndexOrThrow("text");
      final int _cursorIndexOfTotalComments = _cursor.getColumnIndexOrThrow("total_comments");
      final int _cursorIndexOfTotalLikes = _cursor.getColumnIndexOrThrow("total_likes");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final int _cursorIndexOfNewCourseData = _cursor.getColumnIndexOrThrow("newCourseData");
      final int _cursorIndexOfLivetest = _cursor.getColumnIndexOrThrow("livetest");
      final int _cursorIndexOfLiveclass = _cursor.getColumnIndexOrThrow("liveclass");
      final int _cursorIndexOfTestResult = _cursor.getColumnIndexOrThrow("testResult");
      final int _cursorIndexOfBannerlist = _cursor.getColumnIndexOrThrow("bannerlist");
      final int _cursorIndexOfLiveClassStatus = _cursor.getColumnIndexOrThrow("liveClassStatus");
      final int _cursorIndexOfLiveTestStatus = _cursor.getColumnIndexOrThrow("liveTestStatus");
      final int _cursorIndexOfIscommentenable = _cursor.getColumnIndexOrThrow("iscommentenable");
      final int _cursorIndexOfSectionposiiton = _cursor.getColumnIndexOrThrow("sectionposiiton");
      final int _cursorIndexOfLimit = _cursor.getColumnIndexOrThrow("limit");
      final int _cursorIndexOfMyPinned = _cursor.getColumnIndexOrThrow("my_pinned");
      final int _cursorIndexOfParentId = _cursor.getColumnIndexOrThrow("parentId");
      final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
      final int _cursorIndexOfImageType = _cursor.getColumnIndexOrThrow("image_type");
      final List<PostDataTable> _result = new ArrayList<PostDataTable>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final PostDataTable _item;
        _item = new PostDataTable();
        final String _tmpCreated;
        _tmpCreated = _cursor.getString(_cursorIndexOfCreated);
        _item.setCreated(_tmpCreated);
        final String _tmpPage;
        _tmpPage = _cursor.getString(_cursorIndexOfPage);
        _item.setPage(_tmpPage);
        final String _tmpMasterCat;
        _tmpMasterCat = _cursor.getString(_cursorIndexOfMasterCat);
        _item.setMasterCat(_tmpMasterCat);
        final int _tmpPostId;
        _tmpPostId = _cursor.getInt(_cursorIndexOfPostId);
        _item.setPostId(_tmpPostId);
        final String _tmpId;
        _tmpId = _cursor.getString(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpJson;
        _tmpJson = _cursor.getString(_cursorIndexOfJson);
        _item.setJson(_tmpJson);
        final String _tmpMeta_url;
        _tmpMeta_url = _cursor.getString(_cursorIndexOfMetaUrl);
        _item.setMeta_url(_tmpMeta_url);
        final String _tmpThumbnail;
        _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
        _item.setThumbnail(_tmpThumbnail);
        final String _tmpUrl;
        _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
        _item.setUrl(_tmpUrl);
        final String _tmpModified;
        _tmpModified = _cursor.getString(_cursorIndexOfModified);
        _item.setModified(_tmpModified);
        final String _tmpMy_like;
        _tmpMy_like = _cursor.getString(_cursorIndexOfMyLike);
        _item.setMy_like(_tmpMy_like);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        _item.setName(_tmpName);
        final String _tmpPost_type;
        _tmpPost_type = _cursor.getString(_cursorIndexOfPostType);
        _item.setPost_type(_tmpPost_type);
        final String _tmpProfile_picture;
        _tmpProfile_picture = _cursor.getString(_cursorIndexOfProfilePicture);
        _item.setProfile_picture(_tmpProfile_picture);
        final String _tmpStatus;
        _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        _item.setStatus(_tmpStatus);
        final String _tmpSub_cat_id;
        _tmpSub_cat_id = _cursor.getString(_cursorIndexOfSubCatId);
        _item.setSub_cat_id(_tmpSub_cat_id);
        final String _tmpText;
        _tmpText = _cursor.getString(_cursorIndexOfText);
        _item.setText(_tmpText);
        final String _tmpTotal_comments;
        _tmpTotal_comments = _cursor.getString(_cursorIndexOfTotalComments);
        _item.setTotal_comments(_tmpTotal_comments);
        final String _tmpTotal_likes;
        _tmpTotal_likes = _cursor.getString(_cursorIndexOfTotalLikes);
        _item.setTotal_likes(_tmpTotal_likes);
        final String _tmpUser_id;
        _tmpUser_id = _cursor.getString(_cursorIndexOfUserId);
        _item.setUser_id(_tmpUser_id);
        final String _tmpNewCourseData;
        _tmpNewCourseData = _cursor.getString(_cursorIndexOfNewCourseData);
        _item.setNewCourseData(_tmpNewCourseData);
        final String _tmpLivetest;
        _tmpLivetest = _cursor.getString(_cursorIndexOfLivetest);
        _item.setLivetest(_tmpLivetest);
        final String _tmpLiveclass;
        _tmpLiveclass = _cursor.getString(_cursorIndexOfLiveclass);
        _item.setLiveclass(_tmpLiveclass);
        final String _tmpTestResult;
        _tmpTestResult = _cursor.getString(_cursorIndexOfTestResult);
        _item.setTestResult(_tmpTestResult);
        final String _tmpBannerlist;
        _tmpBannerlist = _cursor.getString(_cursorIndexOfBannerlist);
        _item.setBannerlist(_tmpBannerlist);
        final String _tmpLiveClassStatus;
        _tmpLiveClassStatus = _cursor.getString(_cursorIndexOfLiveClassStatus);
        _item.setLiveClassStatus(_tmpLiveClassStatus);
        final String _tmpLiveTestStatus;
        _tmpLiveTestStatus = _cursor.getString(_cursorIndexOfLiveTestStatus);
        _item.setLiveTestStatus(_tmpLiveTestStatus);
        final String _tmpIscommentenable;
        _tmpIscommentenable = _cursor.getString(_cursorIndexOfIscommentenable);
        _item.setIscommentenable(_tmpIscommentenable);
        final String _tmpSectionposiiton;
        _tmpSectionposiiton = _cursor.getString(_cursorIndexOfSectionposiiton);
        _item.setSectionposiiton(_tmpSectionposiiton);
        final String _tmpLimit;
        _tmpLimit = _cursor.getString(_cursorIndexOfLimit);
        _item.setLimit(_tmpLimit);
        final String _tmpMy_pinned;
        _tmpMy_pinned = _cursor.getString(_cursorIndexOfMyPinned);
        _item.setMy_pinned(_tmpMy_pinned);
        final String _tmpParentId;
        _tmpParentId = _cursor.getString(_cursorIndexOfParentId);
        _item.setParentId(_tmpParentId);
        final String _tmpDescription;
        _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        _item.setDescription(_tmpDescription);
        final String _tmpImage_type;
        _tmpImage_type = _cursor.getString(_cursorIndexOfImageType);
        _item.setImage_type(_tmpImage_type);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<PostDataTable> retrievePostData_withoutsubcat(String parentId, String main) {
    final String _sql = "SELECT * FROM PostDataTable WHERE parentId = ? AND masterCat =? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (parentId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, parentId);
    }
    _argIndex = 2;
    if (main == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, main);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfCreated = _cursor.getColumnIndexOrThrow("created");
      final int _cursorIndexOfPage = _cursor.getColumnIndexOrThrow("page");
      final int _cursorIndexOfMasterCat = _cursor.getColumnIndexOrThrow("masterCat");
      final int _cursorIndexOfPostId = _cursor.getColumnIndexOrThrow("postId");
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfJson = _cursor.getColumnIndexOrThrow("json");
      final int _cursorIndexOfMetaUrl = _cursor.getColumnIndexOrThrow("meta_url");
      final int _cursorIndexOfThumbnail = _cursor.getColumnIndexOrThrow("thumbnail");
      final int _cursorIndexOfUrl = _cursor.getColumnIndexOrThrow("url");
      final int _cursorIndexOfModified = _cursor.getColumnIndexOrThrow("modified");
      final int _cursorIndexOfMyLike = _cursor.getColumnIndexOrThrow("my_like");
      final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
      final int _cursorIndexOfPostType = _cursor.getColumnIndexOrThrow("post_type");
      final int _cursorIndexOfProfilePicture = _cursor.getColumnIndexOrThrow("profile_picture");
      final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("status");
      final int _cursorIndexOfSubCatId = _cursor.getColumnIndexOrThrow("sub_cat_id");
      final int _cursorIndexOfText = _cursor.getColumnIndexOrThrow("text");
      final int _cursorIndexOfTotalComments = _cursor.getColumnIndexOrThrow("total_comments");
      final int _cursorIndexOfTotalLikes = _cursor.getColumnIndexOrThrow("total_likes");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final int _cursorIndexOfNewCourseData = _cursor.getColumnIndexOrThrow("newCourseData");
      final int _cursorIndexOfLivetest = _cursor.getColumnIndexOrThrow("livetest");
      final int _cursorIndexOfLiveclass = _cursor.getColumnIndexOrThrow("liveclass");
      final int _cursorIndexOfTestResult = _cursor.getColumnIndexOrThrow("testResult");
      final int _cursorIndexOfBannerlist = _cursor.getColumnIndexOrThrow("bannerlist");
      final int _cursorIndexOfLiveClassStatus = _cursor.getColumnIndexOrThrow("liveClassStatus");
      final int _cursorIndexOfLiveTestStatus = _cursor.getColumnIndexOrThrow("liveTestStatus");
      final int _cursorIndexOfIscommentenable = _cursor.getColumnIndexOrThrow("iscommentenable");
      final int _cursorIndexOfSectionposiiton = _cursor.getColumnIndexOrThrow("sectionposiiton");
      final int _cursorIndexOfLimit = _cursor.getColumnIndexOrThrow("limit");
      final int _cursorIndexOfMyPinned = _cursor.getColumnIndexOrThrow("my_pinned");
      final int _cursorIndexOfParentId = _cursor.getColumnIndexOrThrow("parentId");
      final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
      final int _cursorIndexOfImageType = _cursor.getColumnIndexOrThrow("image_type");
      final List<PostDataTable> _result = new ArrayList<PostDataTable>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final PostDataTable _item;
        _item = new PostDataTable();
        final String _tmpCreated;
        _tmpCreated = _cursor.getString(_cursorIndexOfCreated);
        _item.setCreated(_tmpCreated);
        final String _tmpPage;
        _tmpPage = _cursor.getString(_cursorIndexOfPage);
        _item.setPage(_tmpPage);
        final String _tmpMasterCat;
        _tmpMasterCat = _cursor.getString(_cursorIndexOfMasterCat);
        _item.setMasterCat(_tmpMasterCat);
        final int _tmpPostId;
        _tmpPostId = _cursor.getInt(_cursorIndexOfPostId);
        _item.setPostId(_tmpPostId);
        final String _tmpId;
        _tmpId = _cursor.getString(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpJson;
        _tmpJson = _cursor.getString(_cursorIndexOfJson);
        _item.setJson(_tmpJson);
        final String _tmpMeta_url;
        _tmpMeta_url = _cursor.getString(_cursorIndexOfMetaUrl);
        _item.setMeta_url(_tmpMeta_url);
        final String _tmpThumbnail;
        _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
        _item.setThumbnail(_tmpThumbnail);
        final String _tmpUrl;
        _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
        _item.setUrl(_tmpUrl);
        final String _tmpModified;
        _tmpModified = _cursor.getString(_cursorIndexOfModified);
        _item.setModified(_tmpModified);
        final String _tmpMy_like;
        _tmpMy_like = _cursor.getString(_cursorIndexOfMyLike);
        _item.setMy_like(_tmpMy_like);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        _item.setName(_tmpName);
        final String _tmpPost_type;
        _tmpPost_type = _cursor.getString(_cursorIndexOfPostType);
        _item.setPost_type(_tmpPost_type);
        final String _tmpProfile_picture;
        _tmpProfile_picture = _cursor.getString(_cursorIndexOfProfilePicture);
        _item.setProfile_picture(_tmpProfile_picture);
        final String _tmpStatus;
        _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        _item.setStatus(_tmpStatus);
        final String _tmpSub_cat_id;
        _tmpSub_cat_id = _cursor.getString(_cursorIndexOfSubCatId);
        _item.setSub_cat_id(_tmpSub_cat_id);
        final String _tmpText;
        _tmpText = _cursor.getString(_cursorIndexOfText);
        _item.setText(_tmpText);
        final String _tmpTotal_comments;
        _tmpTotal_comments = _cursor.getString(_cursorIndexOfTotalComments);
        _item.setTotal_comments(_tmpTotal_comments);
        final String _tmpTotal_likes;
        _tmpTotal_likes = _cursor.getString(_cursorIndexOfTotalLikes);
        _item.setTotal_likes(_tmpTotal_likes);
        final String _tmpUser_id;
        _tmpUser_id = _cursor.getString(_cursorIndexOfUserId);
        _item.setUser_id(_tmpUser_id);
        final String _tmpNewCourseData;
        _tmpNewCourseData = _cursor.getString(_cursorIndexOfNewCourseData);
        _item.setNewCourseData(_tmpNewCourseData);
        final String _tmpLivetest;
        _tmpLivetest = _cursor.getString(_cursorIndexOfLivetest);
        _item.setLivetest(_tmpLivetest);
        final String _tmpLiveclass;
        _tmpLiveclass = _cursor.getString(_cursorIndexOfLiveclass);
        _item.setLiveclass(_tmpLiveclass);
        final String _tmpTestResult;
        _tmpTestResult = _cursor.getString(_cursorIndexOfTestResult);
        _item.setTestResult(_tmpTestResult);
        final String _tmpBannerlist;
        _tmpBannerlist = _cursor.getString(_cursorIndexOfBannerlist);
        _item.setBannerlist(_tmpBannerlist);
        final String _tmpLiveClassStatus;
        _tmpLiveClassStatus = _cursor.getString(_cursorIndexOfLiveClassStatus);
        _item.setLiveClassStatus(_tmpLiveClassStatus);
        final String _tmpLiveTestStatus;
        _tmpLiveTestStatus = _cursor.getString(_cursorIndexOfLiveTestStatus);
        _item.setLiveTestStatus(_tmpLiveTestStatus);
        final String _tmpIscommentenable;
        _tmpIscommentenable = _cursor.getString(_cursorIndexOfIscommentenable);
        _item.setIscommentenable(_tmpIscommentenable);
        final String _tmpSectionposiiton;
        _tmpSectionposiiton = _cursor.getString(_cursorIndexOfSectionposiiton);
        _item.setSectionposiiton(_tmpSectionposiiton);
        final String _tmpLimit;
        _tmpLimit = _cursor.getString(_cursorIndexOfLimit);
        _item.setLimit(_tmpLimit);
        final String _tmpMy_pinned;
        _tmpMy_pinned = _cursor.getString(_cursorIndexOfMyPinned);
        _item.setMy_pinned(_tmpMy_pinned);
        final String _tmpParentId;
        _tmpParentId = _cursor.getString(_cursorIndexOfParentId);
        _item.setParentId(_tmpParentId);
        final String _tmpDescription;
        _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        _item.setDescription(_tmpDescription);
        final String _tmpImage_type;
        _tmpImage_type = _cursor.getString(_cursorIndexOfImageType);
        _item.setImage_type(_tmpImage_type);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public PostDataTable retriveObject(String mainCat, String postid) {
    final String _sql = "SELECT * FROM PostDataTable WHERE id = ? AND masterCat =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (postid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, postid);
    }
    _argIndex = 2;
    if (mainCat == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, mainCat);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfCreated = _cursor.getColumnIndexOrThrow("created");
      final int _cursorIndexOfPage = _cursor.getColumnIndexOrThrow("page");
      final int _cursorIndexOfMasterCat = _cursor.getColumnIndexOrThrow("masterCat");
      final int _cursorIndexOfPostId = _cursor.getColumnIndexOrThrow("postId");
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfJson = _cursor.getColumnIndexOrThrow("json");
      final int _cursorIndexOfMetaUrl = _cursor.getColumnIndexOrThrow("meta_url");
      final int _cursorIndexOfThumbnail = _cursor.getColumnIndexOrThrow("thumbnail");
      final int _cursorIndexOfUrl = _cursor.getColumnIndexOrThrow("url");
      final int _cursorIndexOfModified = _cursor.getColumnIndexOrThrow("modified");
      final int _cursorIndexOfMyLike = _cursor.getColumnIndexOrThrow("my_like");
      final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
      final int _cursorIndexOfPostType = _cursor.getColumnIndexOrThrow("post_type");
      final int _cursorIndexOfProfilePicture = _cursor.getColumnIndexOrThrow("profile_picture");
      final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("status");
      final int _cursorIndexOfSubCatId = _cursor.getColumnIndexOrThrow("sub_cat_id");
      final int _cursorIndexOfText = _cursor.getColumnIndexOrThrow("text");
      final int _cursorIndexOfTotalComments = _cursor.getColumnIndexOrThrow("total_comments");
      final int _cursorIndexOfTotalLikes = _cursor.getColumnIndexOrThrow("total_likes");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final int _cursorIndexOfNewCourseData = _cursor.getColumnIndexOrThrow("newCourseData");
      final int _cursorIndexOfLivetest = _cursor.getColumnIndexOrThrow("livetest");
      final int _cursorIndexOfLiveclass = _cursor.getColumnIndexOrThrow("liveclass");
      final int _cursorIndexOfTestResult = _cursor.getColumnIndexOrThrow("testResult");
      final int _cursorIndexOfBannerlist = _cursor.getColumnIndexOrThrow("bannerlist");
      final int _cursorIndexOfLiveClassStatus = _cursor.getColumnIndexOrThrow("liveClassStatus");
      final int _cursorIndexOfLiveTestStatus = _cursor.getColumnIndexOrThrow("liveTestStatus");
      final int _cursorIndexOfIscommentenable = _cursor.getColumnIndexOrThrow("iscommentenable");
      final int _cursorIndexOfSectionposiiton = _cursor.getColumnIndexOrThrow("sectionposiiton");
      final int _cursorIndexOfLimit = _cursor.getColumnIndexOrThrow("limit");
      final int _cursorIndexOfMyPinned = _cursor.getColumnIndexOrThrow("my_pinned");
      final int _cursorIndexOfParentId = _cursor.getColumnIndexOrThrow("parentId");
      final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
      final int _cursorIndexOfImageType = _cursor.getColumnIndexOrThrow("image_type");
      final PostDataTable _result;
      if(_cursor.moveToFirst()) {
        _result = new PostDataTable();
        final String _tmpCreated;
        _tmpCreated = _cursor.getString(_cursorIndexOfCreated);
        _result.setCreated(_tmpCreated);
        final String _tmpPage;
        _tmpPage = _cursor.getString(_cursorIndexOfPage);
        _result.setPage(_tmpPage);
        final String _tmpMasterCat;
        _tmpMasterCat = _cursor.getString(_cursorIndexOfMasterCat);
        _result.setMasterCat(_tmpMasterCat);
        final int _tmpPostId;
        _tmpPostId = _cursor.getInt(_cursorIndexOfPostId);
        _result.setPostId(_tmpPostId);
        final String _tmpId;
        _tmpId = _cursor.getString(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpJson;
        _tmpJson = _cursor.getString(_cursorIndexOfJson);
        _result.setJson(_tmpJson);
        final String _tmpMeta_url;
        _tmpMeta_url = _cursor.getString(_cursorIndexOfMetaUrl);
        _result.setMeta_url(_tmpMeta_url);
        final String _tmpThumbnail;
        _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
        _result.setThumbnail(_tmpThumbnail);
        final String _tmpUrl;
        _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
        _result.setUrl(_tmpUrl);
        final String _tmpModified;
        _tmpModified = _cursor.getString(_cursorIndexOfModified);
        _result.setModified(_tmpModified);
        final String _tmpMy_like;
        _tmpMy_like = _cursor.getString(_cursorIndexOfMyLike);
        _result.setMy_like(_tmpMy_like);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        _result.setName(_tmpName);
        final String _tmpPost_type;
        _tmpPost_type = _cursor.getString(_cursorIndexOfPostType);
        _result.setPost_type(_tmpPost_type);
        final String _tmpProfile_picture;
        _tmpProfile_picture = _cursor.getString(_cursorIndexOfProfilePicture);
        _result.setProfile_picture(_tmpProfile_picture);
        final String _tmpStatus;
        _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        _result.setStatus(_tmpStatus);
        final String _tmpSub_cat_id;
        _tmpSub_cat_id = _cursor.getString(_cursorIndexOfSubCatId);
        _result.setSub_cat_id(_tmpSub_cat_id);
        final String _tmpText;
        _tmpText = _cursor.getString(_cursorIndexOfText);
        _result.setText(_tmpText);
        final String _tmpTotal_comments;
        _tmpTotal_comments = _cursor.getString(_cursorIndexOfTotalComments);
        _result.setTotal_comments(_tmpTotal_comments);
        final String _tmpTotal_likes;
        _tmpTotal_likes = _cursor.getString(_cursorIndexOfTotalLikes);
        _result.setTotal_likes(_tmpTotal_likes);
        final String _tmpUser_id;
        _tmpUser_id = _cursor.getString(_cursorIndexOfUserId);
        _result.setUser_id(_tmpUser_id);
        final String _tmpNewCourseData;
        _tmpNewCourseData = _cursor.getString(_cursorIndexOfNewCourseData);
        _result.setNewCourseData(_tmpNewCourseData);
        final String _tmpLivetest;
        _tmpLivetest = _cursor.getString(_cursorIndexOfLivetest);
        _result.setLivetest(_tmpLivetest);
        final String _tmpLiveclass;
        _tmpLiveclass = _cursor.getString(_cursorIndexOfLiveclass);
        _result.setLiveclass(_tmpLiveclass);
        final String _tmpTestResult;
        _tmpTestResult = _cursor.getString(_cursorIndexOfTestResult);
        _result.setTestResult(_tmpTestResult);
        final String _tmpBannerlist;
        _tmpBannerlist = _cursor.getString(_cursorIndexOfBannerlist);
        _result.setBannerlist(_tmpBannerlist);
        final String _tmpLiveClassStatus;
        _tmpLiveClassStatus = _cursor.getString(_cursorIndexOfLiveClassStatus);
        _result.setLiveClassStatus(_tmpLiveClassStatus);
        final String _tmpLiveTestStatus;
        _tmpLiveTestStatus = _cursor.getString(_cursorIndexOfLiveTestStatus);
        _result.setLiveTestStatus(_tmpLiveTestStatus);
        final String _tmpIscommentenable;
        _tmpIscommentenable = _cursor.getString(_cursorIndexOfIscommentenable);
        _result.setIscommentenable(_tmpIscommentenable);
        final String _tmpSectionposiiton;
        _tmpSectionposiiton = _cursor.getString(_cursorIndexOfSectionposiiton);
        _result.setSectionposiiton(_tmpSectionposiiton);
        final String _tmpLimit;
        _tmpLimit = _cursor.getString(_cursorIndexOfLimit);
        _result.setLimit(_tmpLimit);
        final String _tmpMy_pinned;
        _tmpMy_pinned = _cursor.getString(_cursorIndexOfMyPinned);
        _result.setMy_pinned(_tmpMy_pinned);
        final String _tmpParentId;
        _tmpParentId = _cursor.getString(_cursorIndexOfParentId);
        _result.setParentId(_tmpParentId);
        final String _tmpDescription;
        _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        _result.setDescription(_tmpDescription);
        final String _tmpImage_type;
        _tmpImage_type = _cursor.getString(_cursorIndexOfImageType);
        _result.setImage_type(_tmpImage_type);
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
  public PostDataTable postData(String postid) {
    final String _sql = "SELECT * FROM PostDataTable WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (postid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, postid);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfCreated = _cursor.getColumnIndexOrThrow("created");
      final int _cursorIndexOfPage = _cursor.getColumnIndexOrThrow("page");
      final int _cursorIndexOfMasterCat = _cursor.getColumnIndexOrThrow("masterCat");
      final int _cursorIndexOfPostId = _cursor.getColumnIndexOrThrow("postId");
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfJson = _cursor.getColumnIndexOrThrow("json");
      final int _cursorIndexOfMetaUrl = _cursor.getColumnIndexOrThrow("meta_url");
      final int _cursorIndexOfThumbnail = _cursor.getColumnIndexOrThrow("thumbnail");
      final int _cursorIndexOfUrl = _cursor.getColumnIndexOrThrow("url");
      final int _cursorIndexOfModified = _cursor.getColumnIndexOrThrow("modified");
      final int _cursorIndexOfMyLike = _cursor.getColumnIndexOrThrow("my_like");
      final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
      final int _cursorIndexOfPostType = _cursor.getColumnIndexOrThrow("post_type");
      final int _cursorIndexOfProfilePicture = _cursor.getColumnIndexOrThrow("profile_picture");
      final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("status");
      final int _cursorIndexOfSubCatId = _cursor.getColumnIndexOrThrow("sub_cat_id");
      final int _cursorIndexOfText = _cursor.getColumnIndexOrThrow("text");
      final int _cursorIndexOfTotalComments = _cursor.getColumnIndexOrThrow("total_comments");
      final int _cursorIndexOfTotalLikes = _cursor.getColumnIndexOrThrow("total_likes");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final int _cursorIndexOfNewCourseData = _cursor.getColumnIndexOrThrow("newCourseData");
      final int _cursorIndexOfLivetest = _cursor.getColumnIndexOrThrow("livetest");
      final int _cursorIndexOfLiveclass = _cursor.getColumnIndexOrThrow("liveclass");
      final int _cursorIndexOfTestResult = _cursor.getColumnIndexOrThrow("testResult");
      final int _cursorIndexOfBannerlist = _cursor.getColumnIndexOrThrow("bannerlist");
      final int _cursorIndexOfLiveClassStatus = _cursor.getColumnIndexOrThrow("liveClassStatus");
      final int _cursorIndexOfLiveTestStatus = _cursor.getColumnIndexOrThrow("liveTestStatus");
      final int _cursorIndexOfIscommentenable = _cursor.getColumnIndexOrThrow("iscommentenable");
      final int _cursorIndexOfSectionposiiton = _cursor.getColumnIndexOrThrow("sectionposiiton");
      final int _cursorIndexOfLimit = _cursor.getColumnIndexOrThrow("limit");
      final int _cursorIndexOfMyPinned = _cursor.getColumnIndexOrThrow("my_pinned");
      final int _cursorIndexOfParentId = _cursor.getColumnIndexOrThrow("parentId");
      final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
      final int _cursorIndexOfImageType = _cursor.getColumnIndexOrThrow("image_type");
      final PostDataTable _result;
      if(_cursor.moveToFirst()) {
        _result = new PostDataTable();
        final String _tmpCreated;
        _tmpCreated = _cursor.getString(_cursorIndexOfCreated);
        _result.setCreated(_tmpCreated);
        final String _tmpPage;
        _tmpPage = _cursor.getString(_cursorIndexOfPage);
        _result.setPage(_tmpPage);
        final String _tmpMasterCat;
        _tmpMasterCat = _cursor.getString(_cursorIndexOfMasterCat);
        _result.setMasterCat(_tmpMasterCat);
        final int _tmpPostId;
        _tmpPostId = _cursor.getInt(_cursorIndexOfPostId);
        _result.setPostId(_tmpPostId);
        final String _tmpId;
        _tmpId = _cursor.getString(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpJson;
        _tmpJson = _cursor.getString(_cursorIndexOfJson);
        _result.setJson(_tmpJson);
        final String _tmpMeta_url;
        _tmpMeta_url = _cursor.getString(_cursorIndexOfMetaUrl);
        _result.setMeta_url(_tmpMeta_url);
        final String _tmpThumbnail;
        _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
        _result.setThumbnail(_tmpThumbnail);
        final String _tmpUrl;
        _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
        _result.setUrl(_tmpUrl);
        final String _tmpModified;
        _tmpModified = _cursor.getString(_cursorIndexOfModified);
        _result.setModified(_tmpModified);
        final String _tmpMy_like;
        _tmpMy_like = _cursor.getString(_cursorIndexOfMyLike);
        _result.setMy_like(_tmpMy_like);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        _result.setName(_tmpName);
        final String _tmpPost_type;
        _tmpPost_type = _cursor.getString(_cursorIndexOfPostType);
        _result.setPost_type(_tmpPost_type);
        final String _tmpProfile_picture;
        _tmpProfile_picture = _cursor.getString(_cursorIndexOfProfilePicture);
        _result.setProfile_picture(_tmpProfile_picture);
        final String _tmpStatus;
        _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        _result.setStatus(_tmpStatus);
        final String _tmpSub_cat_id;
        _tmpSub_cat_id = _cursor.getString(_cursorIndexOfSubCatId);
        _result.setSub_cat_id(_tmpSub_cat_id);
        final String _tmpText;
        _tmpText = _cursor.getString(_cursorIndexOfText);
        _result.setText(_tmpText);
        final String _tmpTotal_comments;
        _tmpTotal_comments = _cursor.getString(_cursorIndexOfTotalComments);
        _result.setTotal_comments(_tmpTotal_comments);
        final String _tmpTotal_likes;
        _tmpTotal_likes = _cursor.getString(_cursorIndexOfTotalLikes);
        _result.setTotal_likes(_tmpTotal_likes);
        final String _tmpUser_id;
        _tmpUser_id = _cursor.getString(_cursorIndexOfUserId);
        _result.setUser_id(_tmpUser_id);
        final String _tmpNewCourseData;
        _tmpNewCourseData = _cursor.getString(_cursorIndexOfNewCourseData);
        _result.setNewCourseData(_tmpNewCourseData);
        final String _tmpLivetest;
        _tmpLivetest = _cursor.getString(_cursorIndexOfLivetest);
        _result.setLivetest(_tmpLivetest);
        final String _tmpLiveclass;
        _tmpLiveclass = _cursor.getString(_cursorIndexOfLiveclass);
        _result.setLiveclass(_tmpLiveclass);
        final String _tmpTestResult;
        _tmpTestResult = _cursor.getString(_cursorIndexOfTestResult);
        _result.setTestResult(_tmpTestResult);
        final String _tmpBannerlist;
        _tmpBannerlist = _cursor.getString(_cursorIndexOfBannerlist);
        _result.setBannerlist(_tmpBannerlist);
        final String _tmpLiveClassStatus;
        _tmpLiveClassStatus = _cursor.getString(_cursorIndexOfLiveClassStatus);
        _result.setLiveClassStatus(_tmpLiveClassStatus);
        final String _tmpLiveTestStatus;
        _tmpLiveTestStatus = _cursor.getString(_cursorIndexOfLiveTestStatus);
        _result.setLiveTestStatus(_tmpLiveTestStatus);
        final String _tmpIscommentenable;
        _tmpIscommentenable = _cursor.getString(_cursorIndexOfIscommentenable);
        _result.setIscommentenable(_tmpIscommentenable);
        final String _tmpSectionposiiton;
        _tmpSectionposiiton = _cursor.getString(_cursorIndexOfSectionposiiton);
        _result.setSectionposiiton(_tmpSectionposiiton);
        final String _tmpLimit;
        _tmpLimit = _cursor.getString(_cursorIndexOfLimit);
        _result.setLimit(_tmpLimit);
        final String _tmpMy_pinned;
        _tmpMy_pinned = _cursor.getString(_cursorIndexOfMyPinned);
        _result.setMy_pinned(_tmpMy_pinned);
        final String _tmpParentId;
        _tmpParentId = _cursor.getString(_cursorIndexOfParentId);
        _result.setParentId(_tmpParentId);
        final String _tmpDescription;
        _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        _result.setDescription(_tmpDescription);
        final String _tmpImage_type;
        _tmpImage_type = _cursor.getString(_cursorIndexOfImageType);
        _result.setImage_type(_tmpImage_type);
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
  public boolean isFeedExist(String postid) {
    final String _sql = "SELECT EXISTS(SELECT * FROM PostDataTable WHERE id = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (postid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, postid);
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
  public boolean isFeedExistviamaincat(String posttype, String subcat) {
    final String _sql = "SELECT EXISTS(SELECT * FROM PostDataTable WHERE post_type = ? AND sub_cat_id =?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (posttype == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, posttype);
    }
    _argIndex = 2;
    if (subcat == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, subcat);
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
  public boolean isliveclassExist(String posttype, String maincat) {
    final String _sql = "SELECT EXISTS(SELECT * FROM PostDataTable WHERE post_type = ?  AND masterCat =? )";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (posttype == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, posttype);
    }
    _argIndex = 2;
    if (maincat == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, maincat);
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
