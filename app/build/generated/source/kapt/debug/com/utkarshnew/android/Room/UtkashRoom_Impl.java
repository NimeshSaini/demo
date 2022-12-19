package com.utkarshnew.android.Room;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.utkarshnew.android.Dao.AddressDao;
import com.utkarshnew.android.Dao.AddressDao_Impl;
import com.utkarshnew.android.Dao.ApiDao;
import com.utkarshnew.android.Dao.ApiDao_Impl;
import com.utkarshnew.android.Dao.AudioDao;
import com.utkarshnew.android.Dao.AudioDao_Impl;
import com.utkarshnew.android.Dao.CourseDataDao;
import com.utkarshnew.android.Dao.CourseDataDao_Impl;
import com.utkarshnew.android.Dao.CourseDetailDao;
import com.utkarshnew.android.Dao.CourseDetailDao_Impl;
import com.utkarshnew.android.Dao.CourseTypeMasterDao;
import com.utkarshnew.android.Dao.CourseTypeMasterDao_Impl;
import com.utkarshnew.android.Dao.FeedsDao;
import com.utkarshnew.android.Dao.FeedsDao_Impl;
import com.utkarshnew.android.Dao.GetMasterAllCatDao;
import com.utkarshnew.android.Dao.GetMasterAllCatDao_Impl;
import com.utkarshnew.android.Dao.HomeApiStatusDao;
import com.utkarshnew.android.Dao.HomeApiStatusDao_Impl;
import com.utkarshnew.android.Dao.Htmllink;
import com.utkarshnew.android.Dao.Htmllink_Impl;
import com.utkarshnew.android.Dao.LaunguageDao;
import com.utkarshnew.android.Dao.LaunguageDao_Impl;
import com.utkarshnew.android.Dao.MasterCatDao;
import com.utkarshnew.android.Dao.MasterCatDao_Impl;
import com.utkarshnew.android.Dao.MycourseDao;
import com.utkarshnew.android.Dao.MycourseDao_Impl;
import com.utkarshnew.android.Dao.PigiBag;
import com.utkarshnew.android.Dao.PigiBag_Impl;
import com.utkarshnew.android.Dao.TestDao;
import com.utkarshnew.android.Dao.TestDao_Impl;
import com.utkarshnew.android.Dao.UserHistroyDao;
import com.utkarshnew.android.Dao.UserHistroyDao_Impl;
import com.utkarshnew.android.Dao.UserWiseCourseDao;
import com.utkarshnew.android.Dao.UserWiseCourseDao_Impl;
import com.utkarshnew.android.Dao.VideoDao;
import com.utkarshnew.android.Dao.VideoDao_Impl;
import com.utkarshnew.android.Dao.VideoDownload;
import com.utkarshnew.android.Dao.VideoDownload_Impl;
import com.utkarshnew.android.Dao.YoutubePlayerDao;
import com.utkarshnew.android.Dao.YoutubePlayerDao_Impl;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public final class UtkashRoom_Impl extends UtkashRoom {
  private volatile VideoDownload _videoDownload;

  private volatile AudioDao _audioDao;

  private volatile VideoDao _videoDao;

  private volatile Htmllink _htmllink;

  private volatile AddressDao _addressDao;

  private volatile ApiDao _apiDao;

  private volatile FeedsDao _feedsDao;

  private volatile GetMasterAllCatDao _getMasterAllCatDao;

  private volatile CourseTypeMasterDao _courseTypeMasterDao;

  private volatile MycourseDao _mycourseDao;

  private volatile MasterCatDao _masterCatDao;

  private volatile UserHistroyDao _userHistroyDao;

  private volatile YoutubePlayerDao _youtubePlayerDao;

  private volatile CourseDetailDao _courseDetailDao;

  private volatile CourseDataDao _courseDataDao;

  private volatile HomeApiStatusDao _homeApiStatusDao;

  private volatile LaunguageDao _launguageDao;

  private volatile PigiBag _pigiBag;

  private volatile TestDao _testDao;

  private volatile UserWiseCourseDao _userWiseCourseDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(3) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `UserWiseCourseTable` (`auto_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `meta_id` TEXT, `code` TEXT, `version` TEXT, `exp` TEXT, `userid` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `PostDataTable` (`created` TEXT NOT NULL, `page` TEXT NOT NULL, `masterCat` TEXT NOT NULL, `postId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `id` TEXT NOT NULL, `json` TEXT NOT NULL, `meta_url` TEXT NOT NULL, `thumbnail` TEXT NOT NULL, `url` TEXT NOT NULL, `modified` TEXT NOT NULL, `my_like` TEXT NOT NULL, `name` TEXT NOT NULL, `post_type` TEXT NOT NULL, `profile_picture` TEXT NOT NULL, `status` TEXT NOT NULL, `sub_cat_id` TEXT NOT NULL, `text` TEXT NOT NULL, `total_comments` TEXT NOT NULL, `total_likes` TEXT NOT NULL, `user_id` TEXT NOT NULL, `newCourseData` TEXT NOT NULL, `livetest` TEXT NOT NULL, `liveclass` TEXT NOT NULL, `testResult` TEXT NOT NULL, `bannerlist` TEXT NOT NULL, `liveClassStatus` TEXT NOT NULL, `liveTestStatus` TEXT NOT NULL, `iscommentenable` TEXT NOT NULL, `sectionposiiton` TEXT NOT NULL, `limit` TEXT NOT NULL, `my_pinned` TEXT NOT NULL, `parentId` TEXT NOT NULL, `description` TEXT NOT NULL, `image_type` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `AddressTable` (`uid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `address_id` TEXT, `address_one` TEXT, `address_two` TEXT, `city` TEXT, `country` TEXT, `state` TEXT, `state_id` TEXT, `city_id` TEXT, `pincode` TEXT, `phone` TEXT, `delivery_price` TEXT, `name` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `TestTable` (`autoid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `test_id` TEXT, `user_id` TEXT, `course_id` TEXT, `status` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `APITABLE` (`auto_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `user_id` TEXT, `timestamp` TEXT, `cdtimestamp` TEXT, `version` TEXT, `Apiname` TEXT, `Apicode` TEXT, `interval` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `PigibagTable` (`auto_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `cdtimestamp` TEXT, `user_id` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `LanguagesTable` (`autoid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `id` TEXT, `language` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `HomeApiStatusTable` (`autoid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userid` TEXT, `mainid` TEXT, `status` TEXT, `page` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `CourseDataTable` (`autoid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userid` TEXT, `mainid` TEXT, `category` TEXT, `courseid` TEXT, `title` TEXT, `cover_image` TEXT, `mrp` TEXT, `course_sp` TEXT, `subject_id` TEXT, `lang_id` TEXT, `validity` TEXT, `type_id` TEXT, `valid_to` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `mycoursetable` (`autoid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `id` TEXT, `title` TEXT, `isSelect` TEXT, `isExpand` TEXT, `batch_id` TEXT, `cover_image` TEXT, `expiry_date` TEXT, `purchase_date` TEXT, `mrp` TEXT, `txn_id` TEXT, `lastread` TEXT, `userid` TEXT, `batchtype` TEXT, `delete` INTEGER NOT NULL, `prices` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `CourseDetailTable` (`autoid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `coursetitle` TEXT, `couseid` TEXT, `tax` TEXT, `is_purchased` TEXT, `validity` TEXT, `course_sp` TEXT, `author_title` TEXT, `view_type` TEXT, `type` TEXT, `mrp` TEXT, `desc_header_image` TEXT, `userid` TEXT, `tileid` TEXT, `tiletitile` TEXT, `tilerevert` TEXT, `tilemeta` TEXT, `notes_type` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `HtmlTbale` (`autoid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `concept_id` TEXT, `tile_id` TEXT, `user_id` TEXT, `course_id` TEXT, `highight` TEXT, `html` TEXT, `valid_to` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `YoutubePlayerTable` (`autoid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `youtubeid` TEXT, `youtubetime` INTEGER NOT NULL, `userid` TEXT, `videoid` TEXT, `isaudio` TEXT, `videoname` TEXT, `valid_to` TEXT, `course_id` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `VideoTable` (`autoid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `video_id` TEXT, `video_name` TEXT, `user_id` TEXT, `video_currentpos` INTEGER, `jw_url` TEXT, `course_id` TEXT, `valid_to` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `UserHistroyTable` (`autoid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `video_id` TEXT, `video_name` TEXT, `user_id` TEXT, `type` TEXT, `pdf_name` TEXT, `youtube_url` TEXT, `pdf_url` TEXT, `current_time` TEXT, `course_id` TEXT, `valid_to` TEXT, `coursename` TEXT, `tileid` TEXT, `testid` TEXT, `testname` TEXT, `topicname` TEXT, `url` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `AudioTable` (`autoid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `video_id` TEXT, `video_name` TEXT, `user_id` TEXT, `audio_url` TEXT, `audio_currentpos` INTEGER, `jw_url` TEXT, `valid_to` TEXT, `course_id` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `VideoDownload` (`autoid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `video_id` TEXT, `video_name` TEXT, `originalFileLengthString` TEXT, `videotime` TEXT, `total` INTEGER, `lengthInMb` TEXT, `percentage` INTEGER NOT NULL, `user_id` TEXT, `course_id` TEXT, `valid_to` INTEGER NOT NULL, `position` INTEGER NOT NULL, `mp4_download_url` TEXT, `video_status` TEXT, `video_enc` TEXT, `video_token` TEXT, `thumbnail_url` TEXT, `is_complete` TEXT, `videoCurrentPosition` INTEGER, `jw_url` TEXT, `is_selected` TEXT, `video_history` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `MasteAllCatTable` (`auto_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `id` TEXT, `name` TEXT, `parent_id` TEXT, `master_type` TEXT, `user_id` TEXT, `filters` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `CourseTypeMasterTable` (`auto_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `id` TEXT, `name` TEXT, `user_id` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `MasterCat` (`auto_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `id` TEXT, `cat` TEXT, `user_id` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"7f702e903f0661fc0f6abc145d9b4155\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `UserWiseCourseTable`");
        _db.execSQL("DROP TABLE IF EXISTS `PostDataTable`");
        _db.execSQL("DROP TABLE IF EXISTS `AddressTable`");
        _db.execSQL("DROP TABLE IF EXISTS `TestTable`");
        _db.execSQL("DROP TABLE IF EXISTS `APITABLE`");
        _db.execSQL("DROP TABLE IF EXISTS `PigibagTable`");
        _db.execSQL("DROP TABLE IF EXISTS `LanguagesTable`");
        _db.execSQL("DROP TABLE IF EXISTS `HomeApiStatusTable`");
        _db.execSQL("DROP TABLE IF EXISTS `CourseDataTable`");
        _db.execSQL("DROP TABLE IF EXISTS `mycoursetable`");
        _db.execSQL("DROP TABLE IF EXISTS `CourseDetailTable`");
        _db.execSQL("DROP TABLE IF EXISTS `HtmlTbale`");
        _db.execSQL("DROP TABLE IF EXISTS `YoutubePlayerTable`");
        _db.execSQL("DROP TABLE IF EXISTS `VideoTable`");
        _db.execSQL("DROP TABLE IF EXISTS `UserHistroyTable`");
        _db.execSQL("DROP TABLE IF EXISTS `AudioTable`");
        _db.execSQL("DROP TABLE IF EXISTS `VideoDownload`");
        _db.execSQL("DROP TABLE IF EXISTS `MasteAllCatTable`");
        _db.execSQL("DROP TABLE IF EXISTS `CourseTypeMasterTable`");
        _db.execSQL("DROP TABLE IF EXISTS `MasterCat`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsUserWiseCourseTable = new HashMap<String, TableInfo.Column>(6);
        _columnsUserWiseCourseTable.put("auto_id", new TableInfo.Column("auto_id", "INTEGER", true, 1));
        _columnsUserWiseCourseTable.put("meta_id", new TableInfo.Column("meta_id", "TEXT", false, 0));
        _columnsUserWiseCourseTable.put("code", new TableInfo.Column("code", "TEXT", false, 0));
        _columnsUserWiseCourseTable.put("version", new TableInfo.Column("version", "TEXT", false, 0));
        _columnsUserWiseCourseTable.put("exp", new TableInfo.Column("exp", "TEXT", false, 0));
        _columnsUserWiseCourseTable.put("userid", new TableInfo.Column("userid", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUserWiseCourseTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUserWiseCourseTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUserWiseCourseTable = new TableInfo("UserWiseCourseTable", _columnsUserWiseCourseTable, _foreignKeysUserWiseCourseTable, _indicesUserWiseCourseTable);
        final TableInfo _existingUserWiseCourseTable = TableInfo.read(_db, "UserWiseCourseTable");
        if (! _infoUserWiseCourseTable.equals(_existingUserWiseCourseTable)) {
          throw new IllegalStateException("Migration didn't properly handle UserWiseCourseTable(com.utkarshnew.android.table.UserWiseCourseTable).\n"
                  + " Expected:\n" + _infoUserWiseCourseTable + "\n"
                  + " Found:\n" + _existingUserWiseCourseTable);
        }
        final HashMap<String, TableInfo.Column> _columnsPostDataTable = new HashMap<String, TableInfo.Column>(34);
        _columnsPostDataTable.put("created", new TableInfo.Column("created", "TEXT", true, 0));
        _columnsPostDataTable.put("page", new TableInfo.Column("page", "TEXT", true, 0));
        _columnsPostDataTable.put("masterCat", new TableInfo.Column("masterCat", "TEXT", true, 0));
        _columnsPostDataTable.put("postId", new TableInfo.Column("postId", "INTEGER", true, 1));
        _columnsPostDataTable.put("id", new TableInfo.Column("id", "TEXT", true, 0));
        _columnsPostDataTable.put("json", new TableInfo.Column("json", "TEXT", true, 0));
        _columnsPostDataTable.put("meta_url", new TableInfo.Column("meta_url", "TEXT", true, 0));
        _columnsPostDataTable.put("thumbnail", new TableInfo.Column("thumbnail", "TEXT", true, 0));
        _columnsPostDataTable.put("url", new TableInfo.Column("url", "TEXT", true, 0));
        _columnsPostDataTable.put("modified", new TableInfo.Column("modified", "TEXT", true, 0));
        _columnsPostDataTable.put("my_like", new TableInfo.Column("my_like", "TEXT", true, 0));
        _columnsPostDataTable.put("name", new TableInfo.Column("name", "TEXT", true, 0));
        _columnsPostDataTable.put("post_type", new TableInfo.Column("post_type", "TEXT", true, 0));
        _columnsPostDataTable.put("profile_picture", new TableInfo.Column("profile_picture", "TEXT", true, 0));
        _columnsPostDataTable.put("status", new TableInfo.Column("status", "TEXT", true, 0));
        _columnsPostDataTable.put("sub_cat_id", new TableInfo.Column("sub_cat_id", "TEXT", true, 0));
        _columnsPostDataTable.put("text", new TableInfo.Column("text", "TEXT", true, 0));
        _columnsPostDataTable.put("total_comments", new TableInfo.Column("total_comments", "TEXT", true, 0));
        _columnsPostDataTable.put("total_likes", new TableInfo.Column("total_likes", "TEXT", true, 0));
        _columnsPostDataTable.put("user_id", new TableInfo.Column("user_id", "TEXT", true, 0));
        _columnsPostDataTable.put("newCourseData", new TableInfo.Column("newCourseData", "TEXT", true, 0));
        _columnsPostDataTable.put("livetest", new TableInfo.Column("livetest", "TEXT", true, 0));
        _columnsPostDataTable.put("liveclass", new TableInfo.Column("liveclass", "TEXT", true, 0));
        _columnsPostDataTable.put("testResult", new TableInfo.Column("testResult", "TEXT", true, 0));
        _columnsPostDataTable.put("bannerlist", new TableInfo.Column("bannerlist", "TEXT", true, 0));
        _columnsPostDataTable.put("liveClassStatus", new TableInfo.Column("liveClassStatus", "TEXT", true, 0));
        _columnsPostDataTable.put("liveTestStatus", new TableInfo.Column("liveTestStatus", "TEXT", true, 0));
        _columnsPostDataTable.put("iscommentenable", new TableInfo.Column("iscommentenable", "TEXT", true, 0));
        _columnsPostDataTable.put("sectionposiiton", new TableInfo.Column("sectionposiiton", "TEXT", true, 0));
        _columnsPostDataTable.put("limit", new TableInfo.Column("limit", "TEXT", true, 0));
        _columnsPostDataTable.put("my_pinned", new TableInfo.Column("my_pinned", "TEXT", true, 0));
        _columnsPostDataTable.put("parentId", new TableInfo.Column("parentId", "TEXT", true, 0));
        _columnsPostDataTable.put("description", new TableInfo.Column("description", "TEXT", true, 0));
        _columnsPostDataTable.put("image_type", new TableInfo.Column("image_type", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPostDataTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPostDataTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPostDataTable = new TableInfo("PostDataTable", _columnsPostDataTable, _foreignKeysPostDataTable, _indicesPostDataTable);
        final TableInfo _existingPostDataTable = TableInfo.read(_db, "PostDataTable");
        if (! _infoPostDataTable.equals(_existingPostDataTable)) {
          throw new IllegalStateException("Migration didn't properly handle PostDataTable(com.utkarshnew.android.table.PostDataTable).\n"
                  + " Expected:\n" + _infoPostDataTable + "\n"
                  + " Found:\n" + _existingPostDataTable);
        }
        final HashMap<String, TableInfo.Column> _columnsAddressTable = new HashMap<String, TableInfo.Column>(13);
        _columnsAddressTable.put("uid", new TableInfo.Column("uid", "INTEGER", true, 1));
        _columnsAddressTable.put("address_id", new TableInfo.Column("address_id", "TEXT", false, 0));
        _columnsAddressTable.put("address_one", new TableInfo.Column("address_one", "TEXT", false, 0));
        _columnsAddressTable.put("address_two", new TableInfo.Column("address_two", "TEXT", false, 0));
        _columnsAddressTable.put("city", new TableInfo.Column("city", "TEXT", false, 0));
        _columnsAddressTable.put("country", new TableInfo.Column("country", "TEXT", false, 0));
        _columnsAddressTable.put("state", new TableInfo.Column("state", "TEXT", false, 0));
        _columnsAddressTable.put("state_id", new TableInfo.Column("state_id", "TEXT", false, 0));
        _columnsAddressTable.put("city_id", new TableInfo.Column("city_id", "TEXT", false, 0));
        _columnsAddressTable.put("pincode", new TableInfo.Column("pincode", "TEXT", false, 0));
        _columnsAddressTable.put("phone", new TableInfo.Column("phone", "TEXT", false, 0));
        _columnsAddressTable.put("delivery_price", new TableInfo.Column("delivery_price", "TEXT", false, 0));
        _columnsAddressTable.put("name", new TableInfo.Column("name", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAddressTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAddressTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAddressTable = new TableInfo("AddressTable", _columnsAddressTable, _foreignKeysAddressTable, _indicesAddressTable);
        final TableInfo _existingAddressTable = TableInfo.read(_db, "AddressTable");
        if (! _infoAddressTable.equals(_existingAddressTable)) {
          throw new IllegalStateException("Migration didn't properly handle AddressTable(com.utkarshnew.android.table.AddressTable).\n"
                  + " Expected:\n" + _infoAddressTable + "\n"
                  + " Found:\n" + _existingAddressTable);
        }
        final HashMap<String, TableInfo.Column> _columnsTestTable = new HashMap<String, TableInfo.Column>(5);
        _columnsTestTable.put("autoid", new TableInfo.Column("autoid", "INTEGER", true, 1));
        _columnsTestTable.put("test_id", new TableInfo.Column("test_id", "TEXT", false, 0));
        _columnsTestTable.put("user_id", new TableInfo.Column("user_id", "TEXT", false, 0));
        _columnsTestTable.put("course_id", new TableInfo.Column("course_id", "TEXT", false, 0));
        _columnsTestTable.put("status", new TableInfo.Column("status", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTestTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTestTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTestTable = new TableInfo("TestTable", _columnsTestTable, _foreignKeysTestTable, _indicesTestTable);
        final TableInfo _existingTestTable = TableInfo.read(_db, "TestTable");
        if (! _infoTestTable.equals(_existingTestTable)) {
          throw new IllegalStateException("Migration didn't properly handle TestTable(com.utkarshnew.android.table.TestTable).\n"
                  + " Expected:\n" + _infoTestTable + "\n"
                  + " Found:\n" + _existingTestTable);
        }
        final HashMap<String, TableInfo.Column> _columnsAPITABLE = new HashMap<String, TableInfo.Column>(8);
        _columnsAPITABLE.put("auto_id", new TableInfo.Column("auto_id", "INTEGER", true, 1));
        _columnsAPITABLE.put("user_id", new TableInfo.Column("user_id", "TEXT", false, 0));
        _columnsAPITABLE.put("timestamp", new TableInfo.Column("timestamp", "TEXT", false, 0));
        _columnsAPITABLE.put("cdtimestamp", new TableInfo.Column("cdtimestamp", "TEXT", false, 0));
        _columnsAPITABLE.put("version", new TableInfo.Column("version", "TEXT", false, 0));
        _columnsAPITABLE.put("Apiname", new TableInfo.Column("Apiname", "TEXT", false, 0));
        _columnsAPITABLE.put("Apicode", new TableInfo.Column("Apicode", "TEXT", false, 0));
        _columnsAPITABLE.put("interval", new TableInfo.Column("interval", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAPITABLE = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAPITABLE = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAPITABLE = new TableInfo("APITABLE", _columnsAPITABLE, _foreignKeysAPITABLE, _indicesAPITABLE);
        final TableInfo _existingAPITABLE = TableInfo.read(_db, "APITABLE");
        if (! _infoAPITABLE.equals(_existingAPITABLE)) {
          throw new IllegalStateException("Migration didn't properly handle APITABLE(com.utkarshnew.android.table.APITABLE).\n"
                  + " Expected:\n" + _infoAPITABLE + "\n"
                  + " Found:\n" + _existingAPITABLE);
        }
        final HashMap<String, TableInfo.Column> _columnsPigibagTable = new HashMap<String, TableInfo.Column>(3);
        _columnsPigibagTable.put("auto_id", new TableInfo.Column("auto_id", "INTEGER", true, 1));
        _columnsPigibagTable.put("cdtimestamp", new TableInfo.Column("cdtimestamp", "TEXT", false, 0));
        _columnsPigibagTable.put("user_id", new TableInfo.Column("user_id", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPigibagTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPigibagTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPigibagTable = new TableInfo("PigibagTable", _columnsPigibagTable, _foreignKeysPigibagTable, _indicesPigibagTable);
        final TableInfo _existingPigibagTable = TableInfo.read(_db, "PigibagTable");
        if (! _infoPigibagTable.equals(_existingPigibagTable)) {
          throw new IllegalStateException("Migration didn't properly handle PigibagTable(com.utkarshnew.android.table.PigibagTable).\n"
                  + " Expected:\n" + _infoPigibagTable + "\n"
                  + " Found:\n" + _existingPigibagTable);
        }
        final HashMap<String, TableInfo.Column> _columnsLanguagesTable = new HashMap<String, TableInfo.Column>(3);
        _columnsLanguagesTable.put("autoid", new TableInfo.Column("autoid", "INTEGER", true, 1));
        _columnsLanguagesTable.put("id", new TableInfo.Column("id", "TEXT", false, 0));
        _columnsLanguagesTable.put("language", new TableInfo.Column("language", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLanguagesTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesLanguagesTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLanguagesTable = new TableInfo("LanguagesTable", _columnsLanguagesTable, _foreignKeysLanguagesTable, _indicesLanguagesTable);
        final TableInfo _existingLanguagesTable = TableInfo.read(_db, "LanguagesTable");
        if (! _infoLanguagesTable.equals(_existingLanguagesTable)) {
          throw new IllegalStateException("Migration didn't properly handle LanguagesTable(com.utkarshnew.android.table.LanguagesTable).\n"
                  + " Expected:\n" + _infoLanguagesTable + "\n"
                  + " Found:\n" + _existingLanguagesTable);
        }
        final HashMap<String, TableInfo.Column> _columnsHomeApiStatusTable = new HashMap<String, TableInfo.Column>(5);
        _columnsHomeApiStatusTable.put("autoid", new TableInfo.Column("autoid", "INTEGER", true, 1));
        _columnsHomeApiStatusTable.put("userid", new TableInfo.Column("userid", "TEXT", false, 0));
        _columnsHomeApiStatusTable.put("mainid", new TableInfo.Column("mainid", "TEXT", false, 0));
        _columnsHomeApiStatusTable.put("status", new TableInfo.Column("status", "TEXT", false, 0));
        _columnsHomeApiStatusTable.put("page", new TableInfo.Column("page", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysHomeApiStatusTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesHomeApiStatusTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoHomeApiStatusTable = new TableInfo("HomeApiStatusTable", _columnsHomeApiStatusTable, _foreignKeysHomeApiStatusTable, _indicesHomeApiStatusTable);
        final TableInfo _existingHomeApiStatusTable = TableInfo.read(_db, "HomeApiStatusTable");
        if (! _infoHomeApiStatusTable.equals(_existingHomeApiStatusTable)) {
          throw new IllegalStateException("Migration didn't properly handle HomeApiStatusTable(com.utkarshnew.android.table.HomeApiStatusTable).\n"
                  + " Expected:\n" + _infoHomeApiStatusTable + "\n"
                  + " Found:\n" + _existingHomeApiStatusTable);
        }
        final HashMap<String, TableInfo.Column> _columnsCourseDataTable = new HashMap<String, TableInfo.Column>(14);
        _columnsCourseDataTable.put("autoid", new TableInfo.Column("autoid", "INTEGER", true, 1));
        _columnsCourseDataTable.put("userid", new TableInfo.Column("userid", "TEXT", false, 0));
        _columnsCourseDataTable.put("mainid", new TableInfo.Column("mainid", "TEXT", false, 0));
        _columnsCourseDataTable.put("category", new TableInfo.Column("category", "TEXT", false, 0));
        _columnsCourseDataTable.put("courseid", new TableInfo.Column("courseid", "TEXT", false, 0));
        _columnsCourseDataTable.put("title", new TableInfo.Column("title", "TEXT", false, 0));
        _columnsCourseDataTable.put("cover_image", new TableInfo.Column("cover_image", "TEXT", false, 0));
        _columnsCourseDataTable.put("mrp", new TableInfo.Column("mrp", "TEXT", false, 0));
        _columnsCourseDataTable.put("course_sp", new TableInfo.Column("course_sp", "TEXT", false, 0));
        _columnsCourseDataTable.put("subject_id", new TableInfo.Column("subject_id", "TEXT", false, 0));
        _columnsCourseDataTable.put("lang_id", new TableInfo.Column("lang_id", "TEXT", false, 0));
        _columnsCourseDataTable.put("validity", new TableInfo.Column("validity", "TEXT", false, 0));
        _columnsCourseDataTable.put("type_id", new TableInfo.Column("type_id", "TEXT", false, 0));
        _columnsCourseDataTable.put("valid_to", new TableInfo.Column("valid_to", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCourseDataTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCourseDataTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCourseDataTable = new TableInfo("CourseDataTable", _columnsCourseDataTable, _foreignKeysCourseDataTable, _indicesCourseDataTable);
        final TableInfo _existingCourseDataTable = TableInfo.read(_db, "CourseDataTable");
        if (! _infoCourseDataTable.equals(_existingCourseDataTable)) {
          throw new IllegalStateException("Migration didn't properly handle CourseDataTable(com.utkarshnew.android.table.CourseDataTable).\n"
                  + " Expected:\n" + _infoCourseDataTable + "\n"
                  + " Found:\n" + _existingCourseDataTable);
        }
        final HashMap<String, TableInfo.Column> _columnsMycoursetable = new HashMap<String, TableInfo.Column>(16);
        _columnsMycoursetable.put("autoid", new TableInfo.Column("autoid", "INTEGER", true, 1));
        _columnsMycoursetable.put("id", new TableInfo.Column("id", "TEXT", false, 0));
        _columnsMycoursetable.put("title", new TableInfo.Column("title", "TEXT", false, 0));
        _columnsMycoursetable.put("isSelect", new TableInfo.Column("isSelect", "TEXT", false, 0));
        _columnsMycoursetable.put("isExpand", new TableInfo.Column("isExpand", "TEXT", false, 0));
        _columnsMycoursetable.put("batch_id", new TableInfo.Column("batch_id", "TEXT", false, 0));
        _columnsMycoursetable.put("cover_image", new TableInfo.Column("cover_image", "TEXT", false, 0));
        _columnsMycoursetable.put("expiry_date", new TableInfo.Column("expiry_date", "TEXT", false, 0));
        _columnsMycoursetable.put("purchase_date", new TableInfo.Column("purchase_date", "TEXT", false, 0));
        _columnsMycoursetable.put("mrp", new TableInfo.Column("mrp", "TEXT", false, 0));
        _columnsMycoursetable.put("txn_id", new TableInfo.Column("txn_id", "TEXT", false, 0));
        _columnsMycoursetable.put("lastread", new TableInfo.Column("lastread", "TEXT", false, 0));
        _columnsMycoursetable.put("userid", new TableInfo.Column("userid", "TEXT", false, 0));
        _columnsMycoursetable.put("batchtype", new TableInfo.Column("batchtype", "TEXT", false, 0));
        _columnsMycoursetable.put("delete", new TableInfo.Column("delete", "INTEGER", true, 0));
        _columnsMycoursetable.put("prices", new TableInfo.Column("prices", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMycoursetable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMycoursetable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMycoursetable = new TableInfo("mycoursetable", _columnsMycoursetable, _foreignKeysMycoursetable, _indicesMycoursetable);
        final TableInfo _existingMycoursetable = TableInfo.read(_db, "mycoursetable");
        if (! _infoMycoursetable.equals(_existingMycoursetable)) {
          throw new IllegalStateException("Migration didn't properly handle mycoursetable(com.utkarshnew.android.table.MycourseTable).\n"
                  + " Expected:\n" + _infoMycoursetable + "\n"
                  + " Found:\n" + _existingMycoursetable);
        }
        final HashMap<String, TableInfo.Column> _columnsCourseDetailTable = new HashMap<String, TableInfo.Column>(18);
        _columnsCourseDetailTable.put("autoid", new TableInfo.Column("autoid", "INTEGER", true, 1));
        _columnsCourseDetailTable.put("coursetitle", new TableInfo.Column("coursetitle", "TEXT", false, 0));
        _columnsCourseDetailTable.put("couseid", new TableInfo.Column("couseid", "TEXT", false, 0));
        _columnsCourseDetailTable.put("tax", new TableInfo.Column("tax", "TEXT", false, 0));
        _columnsCourseDetailTable.put("is_purchased", new TableInfo.Column("is_purchased", "TEXT", false, 0));
        _columnsCourseDetailTable.put("validity", new TableInfo.Column("validity", "TEXT", false, 0));
        _columnsCourseDetailTable.put("course_sp", new TableInfo.Column("course_sp", "TEXT", false, 0));
        _columnsCourseDetailTable.put("author_title", new TableInfo.Column("author_title", "TEXT", false, 0));
        _columnsCourseDetailTable.put("view_type", new TableInfo.Column("view_type", "TEXT", false, 0));
        _columnsCourseDetailTable.put("type", new TableInfo.Column("type", "TEXT", false, 0));
        _columnsCourseDetailTable.put("mrp", new TableInfo.Column("mrp", "TEXT", false, 0));
        _columnsCourseDetailTable.put("desc_header_image", new TableInfo.Column("desc_header_image", "TEXT", false, 0));
        _columnsCourseDetailTable.put("userid", new TableInfo.Column("userid", "TEXT", false, 0));
        _columnsCourseDetailTable.put("tileid", new TableInfo.Column("tileid", "TEXT", false, 0));
        _columnsCourseDetailTable.put("tiletitile", new TableInfo.Column("tiletitile", "TEXT", false, 0));
        _columnsCourseDetailTable.put("tilerevert", new TableInfo.Column("tilerevert", "TEXT", false, 0));
        _columnsCourseDetailTable.put("tilemeta", new TableInfo.Column("tilemeta", "TEXT", false, 0));
        _columnsCourseDetailTable.put("notes_type", new TableInfo.Column("notes_type", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCourseDetailTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCourseDetailTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCourseDetailTable = new TableInfo("CourseDetailTable", _columnsCourseDetailTable, _foreignKeysCourseDetailTable, _indicesCourseDetailTable);
        final TableInfo _existingCourseDetailTable = TableInfo.read(_db, "CourseDetailTable");
        if (! _infoCourseDetailTable.equals(_existingCourseDetailTable)) {
          throw new IllegalStateException("Migration didn't properly handle CourseDetailTable(com.utkarshnew.android.table.CourseDetailTable).\n"
                  + " Expected:\n" + _infoCourseDetailTable + "\n"
                  + " Found:\n" + _existingCourseDetailTable);
        }
        final HashMap<String, TableInfo.Column> _columnsHtmlTbale = new HashMap<String, TableInfo.Column>(8);
        _columnsHtmlTbale.put("autoid", new TableInfo.Column("autoid", "INTEGER", true, 1));
        _columnsHtmlTbale.put("concept_id", new TableInfo.Column("concept_id", "TEXT", false, 0));
        _columnsHtmlTbale.put("tile_id", new TableInfo.Column("tile_id", "TEXT", false, 0));
        _columnsHtmlTbale.put("user_id", new TableInfo.Column("user_id", "TEXT", false, 0));
        _columnsHtmlTbale.put("course_id", new TableInfo.Column("course_id", "TEXT", false, 0));
        _columnsHtmlTbale.put("highight", new TableInfo.Column("highight", "TEXT", false, 0));
        _columnsHtmlTbale.put("html", new TableInfo.Column("html", "TEXT", false, 0));
        _columnsHtmlTbale.put("valid_to", new TableInfo.Column("valid_to", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysHtmlTbale = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesHtmlTbale = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoHtmlTbale = new TableInfo("HtmlTbale", _columnsHtmlTbale, _foreignKeysHtmlTbale, _indicesHtmlTbale);
        final TableInfo _existingHtmlTbale = TableInfo.read(_db, "HtmlTbale");
        if (! _infoHtmlTbale.equals(_existingHtmlTbale)) {
          throw new IllegalStateException("Migration didn't properly handle HtmlTbale(com.utkarshnew.android.table.HtmlTbale).\n"
                  + " Expected:\n" + _infoHtmlTbale + "\n"
                  + " Found:\n" + _existingHtmlTbale);
        }
        final HashMap<String, TableInfo.Column> _columnsYoutubePlayerTable = new HashMap<String, TableInfo.Column>(9);
        _columnsYoutubePlayerTable.put("autoid", new TableInfo.Column("autoid", "INTEGER", true, 1));
        _columnsYoutubePlayerTable.put("youtubeid", new TableInfo.Column("youtubeid", "TEXT", false, 0));
        _columnsYoutubePlayerTable.put("youtubetime", new TableInfo.Column("youtubetime", "INTEGER", true, 0));
        _columnsYoutubePlayerTable.put("userid", new TableInfo.Column("userid", "TEXT", false, 0));
        _columnsYoutubePlayerTable.put("videoid", new TableInfo.Column("videoid", "TEXT", false, 0));
        _columnsYoutubePlayerTable.put("isaudio", new TableInfo.Column("isaudio", "TEXT", false, 0));
        _columnsYoutubePlayerTable.put("videoname", new TableInfo.Column("videoname", "TEXT", false, 0));
        _columnsYoutubePlayerTable.put("valid_to", new TableInfo.Column("valid_to", "TEXT", false, 0));
        _columnsYoutubePlayerTable.put("course_id", new TableInfo.Column("course_id", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysYoutubePlayerTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesYoutubePlayerTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoYoutubePlayerTable = new TableInfo("YoutubePlayerTable", _columnsYoutubePlayerTable, _foreignKeysYoutubePlayerTable, _indicesYoutubePlayerTable);
        final TableInfo _existingYoutubePlayerTable = TableInfo.read(_db, "YoutubePlayerTable");
        if (! _infoYoutubePlayerTable.equals(_existingYoutubePlayerTable)) {
          throw new IllegalStateException("Migration didn't properly handle YoutubePlayerTable(com.utkarshnew.android.table.YoutubePlayerTable).\n"
                  + " Expected:\n" + _infoYoutubePlayerTable + "\n"
                  + " Found:\n" + _existingYoutubePlayerTable);
        }
        final HashMap<String, TableInfo.Column> _columnsVideoTable = new HashMap<String, TableInfo.Column>(8);
        _columnsVideoTable.put("autoid", new TableInfo.Column("autoid", "INTEGER", true, 1));
        _columnsVideoTable.put("video_id", new TableInfo.Column("video_id", "TEXT", false, 0));
        _columnsVideoTable.put("video_name", new TableInfo.Column("video_name", "TEXT", false, 0));
        _columnsVideoTable.put("user_id", new TableInfo.Column("user_id", "TEXT", false, 0));
        _columnsVideoTable.put("video_currentpos", new TableInfo.Column("video_currentpos", "INTEGER", false, 0));
        _columnsVideoTable.put("jw_url", new TableInfo.Column("jw_url", "TEXT", false, 0));
        _columnsVideoTable.put("course_id", new TableInfo.Column("course_id", "TEXT", false, 0));
        _columnsVideoTable.put("valid_to", new TableInfo.Column("valid_to", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVideoTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesVideoTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoVideoTable = new TableInfo("VideoTable", _columnsVideoTable, _foreignKeysVideoTable, _indicesVideoTable);
        final TableInfo _existingVideoTable = TableInfo.read(_db, "VideoTable");
        if (! _infoVideoTable.equals(_existingVideoTable)) {
          throw new IllegalStateException("Migration didn't properly handle VideoTable(com.utkarshnew.android.table.VideoTable).\n"
                  + " Expected:\n" + _infoVideoTable + "\n"
                  + " Found:\n" + _existingVideoTable);
        }
        final HashMap<String, TableInfo.Column> _columnsUserHistroyTable = new HashMap<String, TableInfo.Column>(17);
        _columnsUserHistroyTable.put("autoid", new TableInfo.Column("autoid", "INTEGER", true, 1));
        _columnsUserHistroyTable.put("video_id", new TableInfo.Column("video_id", "TEXT", false, 0));
        _columnsUserHistroyTable.put("video_name", new TableInfo.Column("video_name", "TEXT", false, 0));
        _columnsUserHistroyTable.put("user_id", new TableInfo.Column("user_id", "TEXT", false, 0));
        _columnsUserHistroyTable.put("type", new TableInfo.Column("type", "TEXT", false, 0));
        _columnsUserHistroyTable.put("pdf_name", new TableInfo.Column("pdf_name", "TEXT", false, 0));
        _columnsUserHistroyTable.put("youtube_url", new TableInfo.Column("youtube_url", "TEXT", false, 0));
        _columnsUserHistroyTable.put("pdf_url", new TableInfo.Column("pdf_url", "TEXT", false, 0));
        _columnsUserHistroyTable.put("current_time", new TableInfo.Column("current_time", "TEXT", false, 0));
        _columnsUserHistroyTable.put("course_id", new TableInfo.Column("course_id", "TEXT", false, 0));
        _columnsUserHistroyTable.put("valid_to", new TableInfo.Column("valid_to", "TEXT", false, 0));
        _columnsUserHistroyTable.put("coursename", new TableInfo.Column("coursename", "TEXT", false, 0));
        _columnsUserHistroyTable.put("tileid", new TableInfo.Column("tileid", "TEXT", false, 0));
        _columnsUserHistroyTable.put("testid", new TableInfo.Column("testid", "TEXT", false, 0));
        _columnsUserHistroyTable.put("testname", new TableInfo.Column("testname", "TEXT", false, 0));
        _columnsUserHistroyTable.put("topicname", new TableInfo.Column("topicname", "TEXT", false, 0));
        _columnsUserHistroyTable.put("url", new TableInfo.Column("url", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUserHistroyTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUserHistroyTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUserHistroyTable = new TableInfo("UserHistroyTable", _columnsUserHistroyTable, _foreignKeysUserHistroyTable, _indicesUserHistroyTable);
        final TableInfo _existingUserHistroyTable = TableInfo.read(_db, "UserHistroyTable");
        if (! _infoUserHistroyTable.equals(_existingUserHistroyTable)) {
          throw new IllegalStateException("Migration didn't properly handle UserHistroyTable(com.utkarshnew.android.table.UserHistroyTable).\n"
                  + " Expected:\n" + _infoUserHistroyTable + "\n"
                  + " Found:\n" + _existingUserHistroyTable);
        }
        final HashMap<String, TableInfo.Column> _columnsAudioTable = new HashMap<String, TableInfo.Column>(9);
        _columnsAudioTable.put("autoid", new TableInfo.Column("autoid", "INTEGER", true, 1));
        _columnsAudioTable.put("video_id", new TableInfo.Column("video_id", "TEXT", false, 0));
        _columnsAudioTable.put("video_name", new TableInfo.Column("video_name", "TEXT", false, 0));
        _columnsAudioTable.put("user_id", new TableInfo.Column("user_id", "TEXT", false, 0));
        _columnsAudioTable.put("audio_url", new TableInfo.Column("audio_url", "TEXT", false, 0));
        _columnsAudioTable.put("audio_currentpos", new TableInfo.Column("audio_currentpos", "INTEGER", false, 0));
        _columnsAudioTable.put("jw_url", new TableInfo.Column("jw_url", "TEXT", false, 0));
        _columnsAudioTable.put("valid_to", new TableInfo.Column("valid_to", "TEXT", false, 0));
        _columnsAudioTable.put("course_id", new TableInfo.Column("course_id", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAudioTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAudioTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAudioTable = new TableInfo("AudioTable", _columnsAudioTable, _foreignKeysAudioTable, _indicesAudioTable);
        final TableInfo _existingAudioTable = TableInfo.read(_db, "AudioTable");
        if (! _infoAudioTable.equals(_existingAudioTable)) {
          throw new IllegalStateException("Migration didn't properly handle AudioTable(com.utkarshnew.android.table.AudioTable).\n"
                  + " Expected:\n" + _infoAudioTable + "\n"
                  + " Found:\n" + _existingAudioTable);
        }
        final HashMap<String, TableInfo.Column> _columnsVideoDownload = new HashMap<String, TableInfo.Column>(22);
        _columnsVideoDownload.put("autoid", new TableInfo.Column("autoid", "INTEGER", true, 1));
        _columnsVideoDownload.put("video_id", new TableInfo.Column("video_id", "TEXT", false, 0));
        _columnsVideoDownload.put("video_name", new TableInfo.Column("video_name", "TEXT", false, 0));
        _columnsVideoDownload.put("originalFileLengthString", new TableInfo.Column("originalFileLengthString", "TEXT", false, 0));
        _columnsVideoDownload.put("videotime", new TableInfo.Column("videotime", "TEXT", false, 0));
        _columnsVideoDownload.put("total", new TableInfo.Column("total", "INTEGER", false, 0));
        _columnsVideoDownload.put("lengthInMb", new TableInfo.Column("lengthInMb", "TEXT", false, 0));
        _columnsVideoDownload.put("percentage", new TableInfo.Column("percentage", "INTEGER", true, 0));
        _columnsVideoDownload.put("user_id", new TableInfo.Column("user_id", "TEXT", false, 0));
        _columnsVideoDownload.put("course_id", new TableInfo.Column("course_id", "TEXT", false, 0));
        _columnsVideoDownload.put("valid_to", new TableInfo.Column("valid_to", "INTEGER", true, 0));
        _columnsVideoDownload.put("position", new TableInfo.Column("position", "INTEGER", true, 0));
        _columnsVideoDownload.put("mp4_download_url", new TableInfo.Column("mp4_download_url", "TEXT", false, 0));
        _columnsVideoDownload.put("video_status", new TableInfo.Column("video_status", "TEXT", false, 0));
        _columnsVideoDownload.put("video_enc", new TableInfo.Column("video_enc", "TEXT", false, 0));
        _columnsVideoDownload.put("video_token", new TableInfo.Column("video_token", "TEXT", false, 0));
        _columnsVideoDownload.put("thumbnail_url", new TableInfo.Column("thumbnail_url", "TEXT", false, 0));
        _columnsVideoDownload.put("is_complete", new TableInfo.Column("is_complete", "TEXT", false, 0));
        _columnsVideoDownload.put("videoCurrentPosition", new TableInfo.Column("videoCurrentPosition", "INTEGER", false, 0));
        _columnsVideoDownload.put("jw_url", new TableInfo.Column("jw_url", "TEXT", false, 0));
        _columnsVideoDownload.put("is_selected", new TableInfo.Column("is_selected", "TEXT", false, 0));
        _columnsVideoDownload.put("video_history", new TableInfo.Column("video_history", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVideoDownload = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesVideoDownload = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoVideoDownload = new TableInfo("VideoDownload", _columnsVideoDownload, _foreignKeysVideoDownload, _indicesVideoDownload);
        final TableInfo _existingVideoDownload = TableInfo.read(_db, "VideoDownload");
        if (! _infoVideoDownload.equals(_existingVideoDownload)) {
          throw new IllegalStateException("Migration didn't properly handle VideoDownload(com.utkarshnew.android.table.VideosDownload).\n"
                  + " Expected:\n" + _infoVideoDownload + "\n"
                  + " Found:\n" + _existingVideoDownload);
        }
        final HashMap<String, TableInfo.Column> _columnsMasteAllCatTable = new HashMap<String, TableInfo.Column>(7);
        _columnsMasteAllCatTable.put("auto_id", new TableInfo.Column("auto_id", "INTEGER", true, 1));
        _columnsMasteAllCatTable.put("id", new TableInfo.Column("id", "TEXT", false, 0));
        _columnsMasteAllCatTable.put("name", new TableInfo.Column("name", "TEXT", false, 0));
        _columnsMasteAllCatTable.put("parent_id", new TableInfo.Column("parent_id", "TEXT", false, 0));
        _columnsMasteAllCatTable.put("master_type", new TableInfo.Column("master_type", "TEXT", false, 0));
        _columnsMasteAllCatTable.put("user_id", new TableInfo.Column("user_id", "TEXT", false, 0));
        _columnsMasteAllCatTable.put("filters", new TableInfo.Column("filters", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMasteAllCatTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMasteAllCatTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMasteAllCatTable = new TableInfo("MasteAllCatTable", _columnsMasteAllCatTable, _foreignKeysMasteAllCatTable, _indicesMasteAllCatTable);
        final TableInfo _existingMasteAllCatTable = TableInfo.read(_db, "MasteAllCatTable");
        if (! _infoMasteAllCatTable.equals(_existingMasteAllCatTable)) {
          throw new IllegalStateException("Migration didn't properly handle MasteAllCatTable(com.utkarshnew.android.table.MasteAllCatTable).\n"
                  + " Expected:\n" + _infoMasteAllCatTable + "\n"
                  + " Found:\n" + _existingMasteAllCatTable);
        }
        final HashMap<String, TableInfo.Column> _columnsCourseTypeMasterTable = new HashMap<String, TableInfo.Column>(4);
        _columnsCourseTypeMasterTable.put("auto_id", new TableInfo.Column("auto_id", "INTEGER", true, 1));
        _columnsCourseTypeMasterTable.put("id", new TableInfo.Column("id", "TEXT", false, 0));
        _columnsCourseTypeMasterTable.put("name", new TableInfo.Column("name", "TEXT", false, 0));
        _columnsCourseTypeMasterTable.put("user_id", new TableInfo.Column("user_id", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCourseTypeMasterTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCourseTypeMasterTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCourseTypeMasterTable = new TableInfo("CourseTypeMasterTable", _columnsCourseTypeMasterTable, _foreignKeysCourseTypeMasterTable, _indicesCourseTypeMasterTable);
        final TableInfo _existingCourseTypeMasterTable = TableInfo.read(_db, "CourseTypeMasterTable");
        if (! _infoCourseTypeMasterTable.equals(_existingCourseTypeMasterTable)) {
          throw new IllegalStateException("Migration didn't properly handle CourseTypeMasterTable(com.utkarshnew.android.table.CourseTypeMasterTable).\n"
                  + " Expected:\n" + _infoCourseTypeMasterTable + "\n"
                  + " Found:\n" + _existingCourseTypeMasterTable);
        }
        final HashMap<String, TableInfo.Column> _columnsMasterCat = new HashMap<String, TableInfo.Column>(4);
        _columnsMasterCat.put("auto_id", new TableInfo.Column("auto_id", "INTEGER", true, 1));
        _columnsMasterCat.put("id", new TableInfo.Column("id", "TEXT", false, 0));
        _columnsMasterCat.put("cat", new TableInfo.Column("cat", "TEXT", false, 0));
        _columnsMasterCat.put("user_id", new TableInfo.Column("user_id", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMasterCat = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMasterCat = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMasterCat = new TableInfo("MasterCat", _columnsMasterCat, _foreignKeysMasterCat, _indicesMasterCat);
        final TableInfo _existingMasterCat = TableInfo.read(_db, "MasterCat");
        if (! _infoMasterCat.equals(_existingMasterCat)) {
          throw new IllegalStateException("Migration didn't properly handle MasterCat(com.utkarshnew.android.table.MasterCat).\n"
                  + " Expected:\n" + _infoMasterCat + "\n"
                  + " Found:\n" + _existingMasterCat);
        }
      }
    }, "7f702e903f0661fc0f6abc145d9b4155", "2ea3120fdd149619432f0ec8ca56a61f");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "UserWiseCourseTable","PostDataTable","AddressTable","TestTable","APITABLE","PigibagTable","LanguagesTable","HomeApiStatusTable","CourseDataTable","mycoursetable","CourseDetailTable","HtmlTbale","YoutubePlayerTable","VideoTable","UserHistroyTable","AudioTable","VideoDownload","MasteAllCatTable","CourseTypeMasterTable","MasterCat");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `UserWiseCourseTable`");
      _db.execSQL("DELETE FROM `PostDataTable`");
      _db.execSQL("DELETE FROM `AddressTable`");
      _db.execSQL("DELETE FROM `TestTable`");
      _db.execSQL("DELETE FROM `APITABLE`");
      _db.execSQL("DELETE FROM `PigibagTable`");
      _db.execSQL("DELETE FROM `LanguagesTable`");
      _db.execSQL("DELETE FROM `HomeApiStatusTable`");
      _db.execSQL("DELETE FROM `CourseDataTable`");
      _db.execSQL("DELETE FROM `mycoursetable`");
      _db.execSQL("DELETE FROM `CourseDetailTable`");
      _db.execSQL("DELETE FROM `HtmlTbale`");
      _db.execSQL("DELETE FROM `YoutubePlayerTable`");
      _db.execSQL("DELETE FROM `VideoTable`");
      _db.execSQL("DELETE FROM `UserHistroyTable`");
      _db.execSQL("DELETE FROM `AudioTable`");
      _db.execSQL("DELETE FROM `VideoDownload`");
      _db.execSQL("DELETE FROM `MasteAllCatTable`");
      _db.execSQL("DELETE FROM `CourseTypeMasterTable`");
      _db.execSQL("DELETE FROM `MasterCat`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public VideoDownload getvideoDownloadao() {
    if (_videoDownload != null) {
      return _videoDownload;
    } else {
      synchronized(this) {
        if(_videoDownload == null) {
          _videoDownload = new VideoDownload_Impl(this);
        }
        return _videoDownload;
      }
    }
  }

  @Override
  public AudioDao getaudiodao() {
    if (_audioDao != null) {
      return _audioDao;
    } else {
      synchronized(this) {
        if(_audioDao == null) {
          _audioDao = new AudioDao_Impl(this);
        }
        return _audioDao;
      }
    }
  }

  @Override
  public VideoDao getvideoDao() {
    if (_videoDao != null) {
      return _videoDao;
    } else {
      synchronized(this) {
        if(_videoDao == null) {
          _videoDao = new VideoDao_Impl(this);
        }
        return _videoDao;
      }
    }
  }

  @Override
  public Htmllink gethtmllink() {
    if (_htmllink != null) {
      return _htmllink;
    } else {
      synchronized(this) {
        if(_htmllink == null) {
          _htmllink = new Htmllink_Impl(this);
        }
        return _htmllink;
      }
    }
  }

  @Override
  public AddressDao getAddress() {
    if (_addressDao != null) {
      return _addressDao;
    } else {
      synchronized(this) {
        if(_addressDao == null) {
          _addressDao = new AddressDao_Impl(this);
        }
        return _addressDao;
      }
    }
  }

  @Override
  public ApiDao getapidao() {
    if (_apiDao != null) {
      return _apiDao;
    } else {
      synchronized(this) {
        if(_apiDao == null) {
          _apiDao = new ApiDao_Impl(this);
        }
        return _apiDao;
      }
    }
  }

  @Override
  public FeedsDao getFeedDao() {
    if (_feedsDao != null) {
      return _feedsDao;
    } else {
      synchronized(this) {
        if(_feedsDao == null) {
          _feedsDao = new FeedsDao_Impl(this);
        }
        return _feedsDao;
      }
    }
  }

  @Override
  public GetMasterAllCatDao getMasterAllCatDao() {
    if (_getMasterAllCatDao != null) {
      return _getMasterAllCatDao;
    } else {
      synchronized(this) {
        if(_getMasterAllCatDao == null) {
          _getMasterAllCatDao = new GetMasterAllCatDao_Impl(this);
        }
        return _getMasterAllCatDao;
      }
    }
  }

  @Override
  public CourseTypeMasterDao getcoursetypemaster() {
    if (_courseTypeMasterDao != null) {
      return _courseTypeMasterDao;
    } else {
      synchronized(this) {
        if(_courseTypeMasterDao == null) {
          _courseTypeMasterDao = new CourseTypeMasterDao_Impl(this);
        }
        return _courseTypeMasterDao;
      }
    }
  }

  @Override
  public MycourseDao getMyCourseDao() {
    if (_mycourseDao != null) {
      return _mycourseDao;
    } else {
      synchronized(this) {
        if(_mycourseDao == null) {
          _mycourseDao = new MycourseDao_Impl(this);
        }
        return _mycourseDao;
      }
    }
  }

  @Override
  public MasterCatDao getMastercatDao() {
    if (_masterCatDao != null) {
      return _masterCatDao;
    } else {
      synchronized(this) {
        if(_masterCatDao == null) {
          _masterCatDao = new MasterCatDao_Impl(this);
        }
        return _masterCatDao;
      }
    }
  }

  @Override
  public UserHistroyDao getuserhistorydao() {
    if (_userHistroyDao != null) {
      return _userHistroyDao;
    } else {
      synchronized(this) {
        if(_userHistroyDao == null) {
          _userHistroyDao = new UserHistroyDao_Impl(this);
        }
        return _userHistroyDao;
      }
    }
  }

  @Override
  public YoutubePlayerDao getyoutubedata() {
    if (_youtubePlayerDao != null) {
      return _youtubePlayerDao;
    } else {
      synchronized(this) {
        if(_youtubePlayerDao == null) {
          _youtubePlayerDao = new YoutubePlayerDao_Impl(this);
        }
        return _youtubePlayerDao;
      }
    }
  }

  @Override
  public CourseDetailDao getCourseDetaildata() {
    if (_courseDetailDao != null) {
      return _courseDetailDao;
    } else {
      synchronized(this) {
        if(_courseDetailDao == null) {
          _courseDetailDao = new CourseDetailDao_Impl(this);
        }
        return _courseDetailDao;
      }
    }
  }

  @Override
  public CourseDataDao getCoursedata() {
    if (_courseDataDao != null) {
      return _courseDataDao;
    } else {
      synchronized(this) {
        if(_courseDataDao == null) {
          _courseDataDao = new CourseDataDao_Impl(this);
        }
        return _courseDataDao;
      }
    }
  }

  @Override
  public HomeApiStatusDao getHomeApiStatusdata() {
    if (_homeApiStatusDao != null) {
      return _homeApiStatusDao;
    } else {
      synchronized(this) {
        if(_homeApiStatusDao == null) {
          _homeApiStatusDao = new HomeApiStatusDao_Impl(this);
        }
        return _homeApiStatusDao;
      }
    }
  }

  @Override
  public LaunguageDao getLaunguages() {
    if (_launguageDao != null) {
      return _launguageDao;
    } else {
      synchronized(this) {
        if(_launguageDao == null) {
          _launguageDao = new LaunguageDao_Impl(this);
        }
        return _launguageDao;
      }
    }
  }

  @Override
  public PigiBag getpigibag() {
    if (_pigiBag != null) {
      return _pigiBag;
    } else {
      synchronized(this) {
        if(_pigiBag == null) {
          _pigiBag = new PigiBag_Impl(this);
        }
        return _pigiBag;
      }
    }
  }

  @Override
  public TestDao getTestDao() {
    if (_testDao != null) {
      return _testDao;
    } else {
      synchronized(this) {
        if(_testDao == null) {
          _testDao = new TestDao_Impl(this);
        }
        return _testDao;
      }
    }
  }

  @Override
  public UserWiseCourseDao getuserwisecourse() {
    if (_userWiseCourseDao != null) {
      return _userWiseCourseDao;
    } else {
      synchronized(this) {
        if(_userWiseCourseDao == null) {
          _userWiseCourseDao = new UserWiseCourseDao_Impl(this);
        }
        return _userWiseCourseDao;
      }
    }
  }
}
