package com.utkarshnew.android.Room;

import android.content.Context;
import android.os.Environment;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.utkarshnew.android.Dao.AddressDao;
import com.utkarshnew.android.Dao.ApiDao;
import com.utkarshnew.android.Dao.AudioDao;
import com.utkarshnew.android.Dao.CourseDataDao;
import com.utkarshnew.android.Dao.CourseDetailDao;
import com.utkarshnew.android.Dao.CourseTypeMasterDao;

import com.utkarshnew.android.Dao.FeedsDao;
import com.utkarshnew.android.Dao.GetMasterAllCatDao;
import com.utkarshnew.android.Dao.HomeApiStatusDao;
import com.utkarshnew.android.Dao.Htmllink;
import com.utkarshnew.android.Dao.LaunguageDao;
import com.utkarshnew.android.Dao.MasterCatDao;
import com.utkarshnew.android.Dao.MycourseDao;
import com.utkarshnew.android.Dao.PigiBag;
import com.utkarshnew.android.Dao.TestDao;
import com.utkarshnew.android.Dao.UserHistroyDao;
import com.utkarshnew.android.Dao.UserWiseCourseDao;
import com.utkarshnew.android.Dao.VideoDao;
import com.utkarshnew.android.Dao.VideoDownload;
import com.utkarshnew.android.Dao.YoutubePlayerDao;
import com.utkarshnew.android.table.APITABLE;
import com.utkarshnew.android.table.AddressTable;
import com.utkarshnew.android.table.AudioTable;
import com.utkarshnew.android.table.CourseDataTable;
import com.utkarshnew.android.table.CourseDetailTable;
import com.utkarshnew.android.table.CourseTypeMasterTable;
import com.utkarshnew.android.table.HomeApiStatusTable;
import com.utkarshnew.android.table.HtmlTbale;
import com.utkarshnew.android.table.LanguagesTable;
import com.utkarshnew.android.table.MasteAllCatTable;
import com.utkarshnew.android.table.MasterCat;
import com.utkarshnew.android.table.MycourseTable;
import com.utkarshnew.android.table.PigibagTable;
import com.utkarshnew.android.table.TestTable;
import com.utkarshnew.android.table.UserHistroyTable;
import com.utkarshnew.android.table.UserWiseCourseTable;
import com.utkarshnew.android.table.VideoTable;
import com.utkarshnew.android.table.VideosDownload;
import com.utkarshnew.android.table.YoutubePlayerTable;
import com.utkarshnew.android.TypeConverter.Converters;
import com.utkarshnew.android.TypeConverter.FilterConverter;
import com.utkarshnew.android.TypeConverter.NotesConverter;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.table.PostDataTable;

@Database(entities = {UserWiseCourseTable.class, PostDataTable.class, AddressTable.class, TestTable.class, APITABLE.class, PigibagTable.class, LanguagesTable.class, HomeApiStatusTable.class, CourseDataTable.class, MycourseTable.class, CourseDetailTable.class, HtmlTbale.class, YoutubePlayerTable.class, VideoTable.class, UserHistroyTable.class, AudioTable.class, VideosDownload.class, MasteAllCatTable.class, CourseTypeMasterTable.class, MasterCat.class,}, version = 3, exportSchema = false)
@TypeConverters({Converters.class, FilterConverter.class, NotesConverter.class})
public abstract class UtkashRoom extends RoomDatabase {
    private static UtkashRoom INSTANCE;
    private static final String DATABASE_NAME = "UtkarshDB.db";

    public abstract VideoDownload getvideoDownloadao();

    public abstract AudioDao getaudiodao();

    public abstract VideoDao getvideoDao();

    public abstract Htmllink gethtmllink();

    public abstract AddressDao getAddress();

    public abstract ApiDao getapidao();

    public abstract FeedsDao getFeedDao();

    public abstract GetMasterAllCatDao getMasterAllCatDao();

    public abstract CourseTypeMasterDao getcoursetypemaster();

    public abstract MycourseDao getMyCourseDao();

    public abstract MasterCatDao getMastercatDao();

    public abstract UserHistroyDao getuserhistorydao();

    public abstract YoutubePlayerDao getyoutubedata();

    public abstract CourseDetailDao getCourseDetaildata();

    public abstract CourseDataDao getCoursedata();

    public abstract HomeApiStatusDao getHomeApiStatusdata();

    public abstract LaunguageDao getLaunguages();

    public abstract PigiBag getpigibag();

    public abstract TestDao getTestDao();

    public abstract UserWiseCourseDao getuserwisecourse();

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE CourseDetailTable ADD COLUMN notes_type TEXT");
            database.execSQL("CREATE TABLE IF NOT EXISTS  `AddressTable` ( `uid` INTEGER NOT NULL, `address_id` TEXT , `address_one` TEXT , `address_two` TEXT , `city` TEXT , `country` TEXT  , `phone` TEXT ,   `pincode` TEXT ,   `state_id` TEXT ,  `city_id` TEXT ,  `state` TEXT  , `name` TEXT , `delivery_price` TEXT, PRIMARY KEY(`uid`))");

            database.execSQL("CREATE TABLE IF NOT EXISTS  `PostDataTable` ( `created` TEXT NOT NULL, `masterCat` TEXT NOT NULL, `my_pinned` TEXT NOT NULL,`parentId` TEXT NOT NULL, `postId` INTEGER NOT NULL, `id` TEXT NOT NULL ,  `page` TEXT NOT NULL , `json` TEXT NOT NULL, `meta_url` TEXT NOT NULL,`sectionposiiton` TEXT NOT NULL,  `limit` TEXT NOT NULL, `thumbnail` TEXT NOT NULL ,  `description` TEXT NOT NULL ,  `iscommentenable` TEXT NOT NULL , `url` TEXT NOT NULL ,   `modified` TEXT NOT NULL,   `my_like` TEXT NOT NULL,  `name` TEXT NOT NULL,  `post_type` TEXT NOT NULL  , `profile_picture` TEXT NOT NULL , `status` TEXT NOT NULL ,`sub_cat_id` TEXT NOT NULL, `text` TEXT NOT NULL, `total_comments` TEXT NOT NULL, `total_likes` TEXT NOT NULL, `user_id` TEXT NOT NULL, `newCourseData` TEXT NOT NULL, `livetest` TEXT NOT NULL, `liveclass` TEXT NOT NULL, `testResult` TEXT NOT NULL, `bannerlist` TEXT NOT NULL, `liveClassStatus` TEXT NOT NULL, `liveTestStatus` TEXT NOT NULL , PRIMARY KEY(`postId`))");

//
//            database.execSQL("ALTER TABLE VideoDownload ADD COLUMN  course_id TEXT ");
//
//            database.execSQL("ALTER TABLE   UserHistroyTable ADD COLUMN  course_id TEXT ");
//            database.execSQL("ALTER TABLE   VideoTable ADD COLUMN  course_id TEXT ");
            //database.execSQL("ALTER TABLE user ADD COLUMN  is_active INTEGER  NOT NULL DEFAULT(0)");
        /*    database.execSQL("ALTER TABLE LiveClass ADD COLUMN  live_class_timstamp TEXT ");

            database.execSQL("UPDATE LiveClass set live_class_timstamp = timstamp");*/
            ////column chnage ///
            // database.execSQL("ALTER TABLE LiveClass  RENAME COLUMN liveclass_id_image TO liveclass_image");
        }

    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE PostDataTable ADD COLUMN image_type TEXT");
            database.execSQL("ALTER TABLE VideoDownload ADD COLUMN video_enc TEXT");
            database.execSQL("ALTER TABLE VideoDownload ADD COLUMN video_token TEXT");

        }

    };


   /* static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

            database.execSQL("ALTER TABLE VideoDownload ADD COLUMN  valid_to INTEGER  NOT NULL DEFAULT(0) ");
            //  database.execSQL("CREATE TABLE IF NOT EXISTS  `Test` ( `uid` INTEGER NOT NULL, `test_id` TEXT , `test_name` TEXT , `test_paid` TEXT , `test_image` TEXT , `test_cut_price` TEXT , `timstamp` TEXT , `test_normal_price` TEXT, PRIMARY KEY(`uid`))");
            //database.execSQL("ALTER TABLE user ADD COLUMN  is_active INTEGER  NOT NULL DEFAULT(0)");
        *//*    database.execSQL("ALTER TABLE LiveClass ADD COLUMN  live_class_timstamp TEXT ");

            database.execSQL("UPDATE LiveClass set live_class_timstamp = timstamp");*//*
            ////column chnage ///
            // database.execSQL("ALTER TABLE LiveClass  RENAME COLUMN liveclass_id_image TO liveclass_image");
        }

    };*/

    public static synchronized UtkashRoom getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(MakeMyExam.getAppContext(),
                                    UtkashRoom.class, MakeMyExam.getAppContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                                            + "/UTKARSH/" + DATABASE_NAME)
                            .allowMainThreadQueries()
                            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                            .build();

        /*    INSTANCE =
                    Room.databaseBuilder(MakeMyExam.getAppContext(),
                            UtkashRoom.class, MakeMyExam.getAppContext().getFilesDir().getAbsolutePath()
                                    + "/UTKARSH/" + DATABASE_NAME)
                            .allowMainThreadQueries()
                            .addMigrations(MIGRATION_1_2)
                            .build();*/

        }
        return INSTANCE;
    }


    public static void destroyInstance() {
        if (INSTANCE != null) {
            if (INSTANCE.isOpen()) {
                INSTANCE.close();
            }
        }
        INSTANCE = null;
    }
}
