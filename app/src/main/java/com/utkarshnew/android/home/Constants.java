package com.utkarshnew.android.home;

import android.widget.ImageView;

import com.utkarshnew.android.Model.COURSEDETAIL.CourseDetail;
import com.utkarshnew.android.Model.Courses.ExamPrepItem;

public class Constants {
    public static final String APP_FOLDER = "DrishtiIAS";
    public static String REFRESHFEED = "";
    public static String PROFILE_PIC = "profile_picture";
    public static String NOTE_DIALOG = "note_dialog";
    public static String IMAGEPATH = "";
    public static int FEED_RV_POS = 0;
    public static int QUIZ_POS = 0;
    public static ExamPrepItem examPrepItemNew;
    public static CourseDetail courseDetail;
    public static String CC = "";
    public static String MOBILE_NO = "";
    public static String EMAIL = "";
    public static String REFRESHPEOPLE = "";
    public static long mLastClickTime = 0;

    public static boolean revison_set = false;


    public static boolean forLiveClass = true;
    public static boolean forLiveClassNotifiIcon = false;
    public static boolean forMainApp = true;
    public static boolean forRootProxy = true;
    public static boolean forWithSecurity = true;

    public static final String QUIZ_ID = "";
    public static final String HOME_THOME_TOUR_FIRST_TIMEOUR_FIRST_TIME = "HOME_TOUR_FIRST_TIME";
    public static final String ARTICLE_TOUR_FIRST_TIME = "ARTICLE_TOUR_FIRST_TIME";
    public static String DOSE_ID = "";
    public static String IS_PURCHASED = "";
    public static String REFRESHPAGE = "";
    public static String pos = "";
    public static String IS_UPLOADED = "";
    public static String STATE = "";
    //public static String NOTE_DIALOG="note_dialog";
    public static String NOTE_STRING_DIALOG = "";
    //public static boolean NOTE_FIRST_TIME_DIALOG=true;
    public static String SubjectiveUpdate = "";
    public static boolean POP_API_HIT = false;
    public static boolean frontImageClicked = false;
    public static boolean backImageClicked = false;
    public static ImageView frontIV;
    public static ImageView backIV;
    public static boolean frontImageAdded = false;
    public static boolean backImageAdded = false;
    public static String frontImage = "";
    public static String backImage = "";
    public static boolean checkMobile = false;
    //payubiz
    //public static final String MERCHANT_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCrL2HUtQuRj4xuWytYWExdpKNErDRTFnbJieWi7HN7cQ6GdSptKyi4Dy0eYlodvsVvJMDCRfSeaEAwUq//IJXagtuxNCUI2C4WshxLK3/FRB01r0QcW556OytisV/VCr4Dn2mO24wPhjo69uJiBKKDCTxfI4qcq8pwxanKNZviJo55pKoKcdJwG4i6oAARCifMFR52h6yvWoDtef0XFNH5MYFoXmAe5sIz8iRlyL3q1Pe+wUj3Vtv+OS31LhZAGK4CoRXDe1GbdzUYFgyIalfJiNspZcCPnXFI50oFlZUNRtxcor8744WuPZOQ8tToFoGuv9wRWKtV5+7PL1vqi2CTAgMBAAECggEBAJ+JD/6hH3/vrSun0bSKzdizt+knJ7iLhfb7icSV4mYXjjbm+Lw27cLjeeQIOv3frbaQJQipE3mRpMikNxtord+v9ril5SHh/snuAgpYJsKcYTCH/80B4ab+f8eBkNNGKKHNrh2SJGzPZnNPZNl+gknmkJoF0IeEXq1MuZkW7wkJ6am6YL4KW00wyF9OhzV5uN4TpInY/xLclqWuv9ty3uZwZTk2vw/xULxnGV5TkpCW6LRt6uaRLLnLU+YApiK/Cto5PcJo4oP8V0R/uFbOdzaqQtQpyw6vbHQgPevkM7A0kpEu1chte523Hb9xuyrrrBYnZKVlKSdhZpbkpGHk1aECgYEA0h2bZWsm8iqrjY8kDkhBGa5005keI0f4guZyj6Kw9XaI/PMvTypbDexb9RbEell44fRkZRzIcZ2ksLBxMI/5kDUl6u0xOp0PpttsQPgBrYcgOWR9S/NBIaGq9+OchyFITDxmadya14MdoPPgBU2+aiWMqq+oGiTwYEdYf5oq7OMCgYEA0JFicnh1XgqMJ1BKHmgBanZQjmhhjnwEw61rM8nCEhi10oqivo6mhKUsccDtarpIaMiYfJMp0eoDO37FJ1yhtvXqW5Bcb59CeAGcOVgFuNp53F4nCoCx9D10HfDBruNJpjQFkG/OL4PiNuGY4B9vbgben4zP3L1SuHKotjhSPJECgYEAl1bD6tS3BI4/aoLDUZYxbbH+a7pJvtfD42Tg9xwToXlzSd5PIRI861ZnFa0Eb6uqKfXBqiKFLXCCgtFTdcr90iS13vRhFPqzn1dELbd0XeyKBUygAuh+7R5o6ouC3sHWNTL8ARvoW3oRCTEfFjLdB89r9jT3O32HtZEt6Rq9+EkCgYEAkfM65gnfb9iHrTclNtPnMb3rr0viANhOaTQg0ZO9R/acveGf8yNV6XrAeDXerhH/e7vr4TkGScutSWPzyRCsZaD+x+ZkmQPkep/9167+ChZwskH8pf/bB6Q9bno8vwp0TmcXAbULnvp2QZtq5GN7EY6jGRlrl/ewPDyYTTNqcNECgYAV6MetHbyFkGtW+2IY3ecfdohA6q1w2L5rDm2oN40S7/ykp00MrNROW04bCak9pJLYXndAauHYOz2D33FTAABeZ86Br4KFXJx4xsAwMGrjryngvIQ2KPv54dZNj81xX25FxKXYK4K4zIFbhS2IzSjrbqYHsySlH8ZzIB3+e/0YVw==";
    //public static final String SALT = "KA7UiZyO";

    /*//public static final String MERCHANT_KEY = "AgrqpF";
    public static final String MERCHANT_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCrL2HUtQuRj4xuWytYWExdpKNErDRTFnbJieWi7HN7cQ6GdSptKyi4Dy0eYlodvsVvJMDCRfSeaEAwUq//IJXagtuxNCUI2C4WshxLK3/FRB01r0QcW556OytisV/VCr4Dn2mO24wPhjo69uJiBKKDCTxfI4qcq8pwxanKNZviJo55pKoKcdJwG4i6oAARCifMFR52h6yvWoDtef0XFNH5MYFoXmAe5sIz8iRlyL3q1Pe+wUj3Vtv+OS31LhZAGK4CoRXDe1GbdzUYFgyIalfJiNspZcCPnXFI50oFlZUNRtxcor8744WuPZOQ8tToFoGuv9wRWKtV5+7PL1vqi2CTAgMBAAECggEBAJ+JD/6hH3/vrSun0bSKzdizt+knJ7iLhfb7icSV4mYXjjbm+Lw27cLjeeQIOv3frbaQJQipE3mRpMikNxtord+v9ril5SHh/snuAgpYJsKcYTCH/80B4ab+f8eBkNNGKKHNrh2SJGzPZnNPZNl+gknmkJoF0IeEXq1MuZkW7wkJ6am6YL4KW00wyF9OhzV5uN4TpInY/xLclqWuv9ty3uZwZTk2vw/xULxnGV5TkpCW6LRt6uaRLLnLU+YApiK/Cto5PcJo4oP8V0R/uFbOdzaqQtQpyw6vbHQgPevkM7A0kpEu1chte523Hb9xuyrrrBYnZKVlKSdhZpbkpGHk1aECgYEA0h2bZWsm8iqrjY8kDkhBGa5005keI0f4guZyj6Kw9XaI/PMvTypbDexb9RbEell44fRkZRzIcZ2ksLBxMI/5kDUl6u0xOp0PpttsQPgBrYcgOWR9S/NBIaGq9+OchyFITDxmadya14MdoPPgBU2+aiWMqq+oGiTwYEdYf5oq7OMCgYEA0JFicnh1XgqMJ1BKHmgBanZQjmhhjnwEw61rM8nCEhi10oqivo6mhKUsccDtarpIaMiYfJMp0eoDO37FJ1yhtvXqW5Bcb59CeAGcOVgFuNp53F4nCoCx9D10HfDBruNJpjQFkG/OL4PiNuGY4B9vbgben4zP3L1SuHKotjhSPJECgYEAl1bD6tS3BI4/aoLDUZYxbbH+a7pJvtfD42Tg9xwToXlzSd5PIRI861ZnFa0Eb6uqKfXBqiKFLXCCgtFTdcr90iS13vRhFPqzn1dELbd0XeyKBUygAuh+7R5o6ouC3sHWNTL8ARvoW3oRCTEfFjLdB89r9jT3O32HtZEt6Rq9+EkCgYEAkfM65gnfb9iHrTclNtPnMb3rr0viANhOaTQg0ZO9R/acveGf8yNV6XrAeDXerhH/e7vr4TkGScutSWPzyRCsZaD+x+ZkmQPkep/9167+ChZwskH8pf/bB6Q9bno8vwp0TmcXAbULnvp2QZtq5GN7EY6jGRlrl/ewPDyYTTNqcNECgYAV6MetHbyFkGtW+2IY3ecfdohA6q1w2L5rDm2oN40S7/ykp00MrNROW04bCak9pJLYXndAauHYOz2D33FTAABeZ86Br4KFXJx4xsAwMGrjryngvIQ2KPv54dZNj81xX25FxKXYK4K4zIFbhS2IzSjrbqYHsySlH8ZzIB3+e/0YVw==";
    public static final String SALT = "KA7UiZyO";
    //public static final int ENV = PayuConstants.PRODUCTION_ENV;
    public static final int ENV = PayuConstants.MOBILE_STAGING_ENV;
    public static final String SURL = "https://dl.dropboxusercontent.com/s/rsyajmdygg50ug8/success.html";
    public static final String FURL = "https://dl.dropboxusercontent.com/s/haywukd51v4bqlg/failure.html";*/

    public interface LEFT_NAV_KEY {
        String download = "1";
        String storage = "2";
        String usage_history = "3";
        String faq = "4";
        String contact_us = "5";
        String invite_friends = "6";
        String chromecast = "7";
        String settings = "8";
        String logout = "9";
        String tearms_conditions = "10";
        String leave_review = "11";
        String clear_cache = "12";
        String Offline_Batch = "14";
        String app_tutorial = "16";
        String purchase_histroy = "15";
        String help = "13";
        String home = "17";
        String coupon = "20";
        String course_transfer = "18";
        String chatbot = "19";
    }

    public interface LINK_PAGE_KEY {
        String landing = "0";
        String landing_Start = "1";
        String DetailLevelOne = "2";
        String DetailLevelTwo = "3";
    }

    public interface BANNER_NAV_KEY {
        String Web = "1";
        String Course = "2";
        String Study = "3";
        String Feeds = "4";
    }

    public interface LEADSQUARED_KEY {
        String BANNER = "1";
        String COURSES = "2";
        String PAYMENT_CONTINUE = "3";
        String PAYMENT_ADDRESS = "4";
        String PAYMENT_COMPLETE = "5";
        String PAYMENT_FAIL = "6";
        String PAYMENT_DECLINE = "7";
        String SIGNUP_GMAIL = "8";
        String SIGNUP_PASS = "9";
    }

    public interface TOPPER_DESK {
        String Articles = "1";
        String Videos = "2";
        String Both = "3";
        String All = "";
    }

    public interface SHARED_PREF {
        String NAME = "app_tour_shared_pref";
    }
}
