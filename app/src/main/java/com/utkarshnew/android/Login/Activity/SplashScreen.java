package com.utkarshnew.android.Login.Activity;

import static com.utkarshnew.android.Utils.Helper.isvisible;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.content.pm.Signature;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.exoplayer2.util.Util;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.gson.Gson;
import com.utkarshnew.android.BuildConfig;
import com.utkarshnew.android.Download.Audio.AudioPlayerService;
import com.utkarshnew.android.Download.DownloadActivity;
import com.utkarshnew.android.LiveClass.Activity.LiveClassActivity;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.home.Activity.HomeActivity;
import com.utkarshnew.android.home.Constants;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.table.AudioTable;
import com.utkarshnew.android.table.UserHistroyTable;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.CustomContextWrapper;
import com.utkarshnew.android.Utils.DialogUtils;
import com.utkarshnew.android.Utils.EmulatorDetector;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.SharedPreference;
import com.utkarshnew.android.Utils.TestService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;

public class SplashScreen extends Activity implements NetworkCall.MyNetworkCallBack {

    Context context;
    private static int SPLASH_TIME_OUT = 3000;
    public static Intent intent;
    Class nextActivity;
    boolean lvl = true;
    private String fragType = "home";
    private UtkashRoom utkashRoom;
    private String videoid = "", media_id = "", tileid = "", tiletype = "";
    private String courseid, fileId, topicId, tileType, postid, revertApi, tileId, pdfid, pdftitle, pdfurl, filetype, parentid;
    NetworkCall networkCall;
    boolean isexit = false;
    Handler handler = new Handler();
    Runnable myRunnable;
    String type = "";
    AppEventsLogger logger;
    String is_otp_login="";

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        logger = AppEventsLogger.newLogger(this);
        ImageView splashImageView = findViewById(R.id.splashImageView);
        Glide.with(this)
                .load(R.drawable.new_splash_screen)
                .into(splashImageView);
        utkashRoom = UtkashRoom.getAppDatabase(context);

        if (SharedPreference.getInstance().getString("course_detail").equals("")) {
            utkashRoom.getCourseDetaildata().deletedata();
            utkashRoom.getMyCourseDao().deletedata();
            SharedPreference.getInstance().putString("course_detail", "" + BuildConfig.VERSION_CODE);
        }

        if (SharedPreference.getInstance().getString("appversion").equals("")) {
            utkashRoom.getuserhistorydao().deletedata();
            utkashRoom.getFeedDao().deletedata();

            SharedPreference.getInstance().putString("appversion", "" + BuildConfig.VERSION_CODE);
        }
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("hashKey", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            //Log.e(TAG, "printHashKey()", e);
        } catch (Exception e) {
            // Log.e(TAG, "printHashKey()", e);
        }
        try {
            Intent intentStartervice = new Intent(SplashScreen.this, TestService.class);
            stopService(intentStartervice);


        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Helper.isNetworkConnected(this)) {


            SharedPreference.getInstance().putString(Const.VERSION, String.valueOf(Helper.getVersionCode(SplashScreen.this)));
            Helper.enableScreenShot(this);
            context = SplashScreen.this;
            networkCall = new NetworkCall(SplashScreen.this, SplashScreen.this);
            if (getIntent() != null) {
                isexit = getIntent().getBooleanExtra("EXIT", false);
            }
            if (isexit) {
                finish();
            }


            try {
                if (AudioPlayerService.isAudioPlaying) {
                    if (getIntent().getExtras() != null) {
                        String url = getIntent().getExtras().getString("url");
                        videoid = getIntent().getExtras().getString("videoid");
                        type = getIntent().getExtras().getString("type");
                        media_id = getIntent().getExtras().getString("media_id");
                        tileid = getIntent().getExtras().getString("tileid");
                        tiletype = getIntent().getExtras().getString("tiletype");
                        if (AudioPlayerService.type.equalsIgnoreCase("youtube") || AudioPlayerService.type.equalsIgnoreCase("aws")) {
                            if (media_id != null && !media_id.equalsIgnoreCase("")) {
                                utkashRoom.getyoutubedata().updateTime(AudioPlayerService.player.getCurrentPosition(), videoid, MakeMyExam.userId, "1");
                            }
                        } else {
                            if (videoid != null && !videoid.equalsIgnoreCase("")) {
                                utkashRoom.getaudiodao().update_videotime(videoid, MakeMyExam.userId, AudioPlayerService.player.getCurrentPosition(), url);
                            }
                        }
                    }
                    Intent audioPlayerIntent = new Intent(SplashScreen.this, AudioPlayerService.class);
                    audioPlayerIntent.setAction("Stop_Service");
                    Util.startForegroundService(SplashScreen.this, audioPlayerIntent);
                    AudioPlayerService.video_currentpos = 0L;
                }
                if (AudioPlayerService.player != null) {
                    videoid = getIntent().getExtras().getString("videoid");
                    type = getIntent().getExtras().getString("type");
                    media_id = getIntent().getExtras().getString("media_id");
                    if (AudioPlayerService.type.equalsIgnoreCase("youtube") || AudioPlayerService.type.equalsIgnoreCase("aws")) {
                        utkashRoom.getyoutubedata().updateTime(AudioPlayerService.player.getCurrentPosition(), videoid, MakeMyExam.userId, "1");
                    } else if (videoid != null && !videoid.equalsIgnoreCase("") && !MakeMyExam.userId.equalsIgnoreCase("0")) {
                        utkashRoom.getaudiodao().update_videotime(videoid, MakeMyExam.userId, AudioPlayerService.player.getCurrentPosition());
                    }
                    AudioPlayerService.player.release();
                    AudioPlayerService.player = null;
                }
            } catch (Exception e) {
                if (AudioPlayerService.isAudioPlaying) {
                    Intent audioPlayerIntent = new Intent(SplashScreen.this, AudioPlayerService.class);
                    audioPlayerIntent.setAction("Stop_Service");
                    Util.startForegroundService(SplashScreen.this, audioPlayerIntent);
                    AudioPlayerService.video_currentpos = 0L;
                }
                if (AudioPlayerService.player != null) {
                    AudioPlayerService.player.release();
                    AudioPlayerService.player = null;
                }
                e.printStackTrace();
            }
            try {
                CheckDeepLinking();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (Helper.isConnected(this)) {
                Log.d("cnajcbnskj", "kxmkx");
                networkCall.NetworkAPICall(API.API_GET_APP_VERSION, "", false, false);
            } else {
                init_splash();
            }
           /* if (doCheck(this))
            {

            }*/
        } else init_splash();

        Helper.createShortcuts(getApplicationContext());
    }

    private void init_splash() {
        if (SharedPreference.getInstance().getBoolean(Const.IS_USER_LOGGED_IN) && SharedPreference.getInstance().getBoolean(Const.IS_USER_REGISTRATION_DONE)) {
            nextActivity = HomeActivity.class;
            // for launguage
            int check = SharedPreference.getInstance().getInt(Const.LANGUAGE);
            if (check == Const.ENGLISH_CODE) {
                Helper.changeLang(SharedPreference.getInstance().getString(Const.APP_LANGUAGE, Const.ENGLISH), this);
            } else if (check == Const.HINDI_CODE) {
                Helper.changeLang(SharedPreference.getInstance().getString(Const.APP_LANGUAGE, Const.HINDI), this);
            }
        } else {
            //nextActivity = LoginCatActivity.class;
            Bundle params = new Bundle();
//            nextActivity = SignInActivity.class;

            if(is_otp_login !=null && is_otp_login.equals("0"))
            {
                nextActivity = SignInActivity.class;
            }
            else
            {
                nextActivity = LoginWithOtp.class;
            }

            params.putString("status", "login");
            logger.logEvent("LoginScreenMove");
            Helper.changeLang(SharedPreference.getInstance().getString(Const.APP_LANGUAGE, Const.ENGLISH), this);
        }


        myRunnable = new Runnable() {
            public void run() {
                next();
            }
        };

        handler.postDelayed(myRunnable, SPLASH_TIME_OUT);

    }

    public void CheckDeepLinking() {
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, pendingDynamicLinkData -> {
                    Uri deepLink = null;
                    if (pendingDynamicLinkData != null) {
                        deepLink = pendingDynamicLinkData.getLink();
                        if (deepLink.getBooleanQueryParameter("data", false)) {
                            String data1 = deepLink.getQueryParameter("data");
                            byte[] data = Base64.decode(data1, Base64.DEFAULT);
                            try {
                                String text = new String(data, "UTF-8");
                                deepLink = Uri.parse(API.BASE_URL_WEB + "?" + text);

                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (deepLink != null) {
                        if (deepLink.getBooleanQueryParameter("postid", false)) {
                            postid = deepLink.getQueryParameter("postid");
                            fragType = "post";
                        } else if (deepLink.getBooleanQueryParameter("maincouseid", false)) {
                            fragType = "course";
                            String maincouseid = deepLink.getQueryParameter("maincouseid");
                            String iscombo = deepLink.getQueryParameter("iscombo");
                            String parentcourseid = deepLink.getQueryParameter("parentcourseid");
                            SharedPreference.getInstance().putString("maincourseid", maincouseid);
                            SharedPreference.getInstance().putString("iscombo", iscombo);
                            if (parentcourseid == null) {
                                parentid = "";
                                SharedPreference.getInstance().putString("parentcourseid", "");
                            } else {
                                parentid = parentcourseid;
                                SharedPreference.getInstance().putString("parentcourseid", parentcourseid);
                            }
                        } else if (deepLink.getBooleanQueryParameter("maincouseidlive", false)) {
                            fragType = "video";
                            courseid = deepLink.getQueryParameter("maincouseidlive");
                            fileId = deepLink.getQueryParameter("fieldid");
                            topicId = deepLink.getQueryParameter("topicid");
                            tileType = deepLink.getQueryParameter("tile_type");

                            revertApi = revertapi(deepLink.toString());
                            tileId = deepLink.getQueryParameter("tileid");
                            parentid = deepLink.getQueryParameter("parentid");
                        } else if (deepLink.getBooleanQueryParameter("maincouseidjw", false)) {
                            fragType = "jwvideo";
                            courseid = deepLink.getQueryParameter("maincouseidjw");
                            fileId = deepLink.getQueryParameter("fieldid");
                            topicId = deepLink.getQueryParameter("topicid");
                            tileType = deepLink.getQueryParameter("tile_type");
                            revertApi = revertapi(deepLink.toString());
                            tileId = deepLink.getQueryParameter("tileid");
                            parentid = deepLink.getQueryParameter("parentid");
                        } else if (deepLink.getBooleanQueryParameter("mainpdfid", false)) {
                            fragType = "pdf";
                            courseid = deepLink.getQueryParameter("mainpdfid");
                            fileId = deepLink.getQueryParameter("fieldid");
                            topicId = deepLink.getQueryParameter("topicid");
                            tileType = deepLink.getQueryParameter("tile_type");
                            revertApi = revertapi(deepLink.toString());
                            tileId = deepLink.getQueryParameter("tileid");
                            parentid = deepLink.getQueryParameter("parentid");
                        } else if (deepLink.getBooleanQueryParameter("testcourseid", false)) {
                            fragType = "test";
                            courseid = deepLink.getQueryParameter("testcourseid");
                            fileId = deepLink.getQueryParameter("fieldid");
                            topicId = deepLink.getQueryParameter("topicid");
                            tileType = deepLink.getQueryParameter("tile_type");
                            revertApi = revertapi(deepLink.toString());
                            tileId = deepLink.getQueryParameter("tileid");
                            parentid = deepLink.getQueryParameter("parentid");

                        }
                        if (parentid == null) {
                            parentid = "";
                        }
                    }
                });
    }

    public String revertapi(String s) {
        String revert = "";
        if (s.contains("revertapi=")) {
            String revertapi[] = s.split("revertapi=");
            if (revertapi.length == 2) {
                if (revertapi[1].contains("&")) {
                    String reverta[] = revertapi[1].split("&");
                    revert = reverta[0];
                }
            }
        }
        return revert;
    }

    public void next() {


        if (Constants.forWithSecurity) {
            if (bluestack() != null && bluestack().contains("bluestack")) {
                DialogUtils.makeSingleButtonDialog(SplashScreen.this, "", "You cannot run this app on a bluesatck device",
                        getResources().getString(R.string.close), false, () -> finish());
            } else {

                if (Build.MODEL.equalsIgnoreCase("rmx1941") || Build.MODEL.equalsIgnoreCase("v2027")) {
                    openHomePage();
                } else {
                    {
                        if (bluestack() != null && bluestack().contains("bluestack")) {
                            DialogUtils.makeSingleButtonDialog(SplashScreen.this, "", "You cannot run this app on a bluesatck device",
                                    getResources().getString(R.string.close), false, () -> finish());
                        } else {

                            if (Build.MODEL.equalsIgnoreCase("rmx1941") || Build.MODEL.equalsIgnoreCase("v2027")) {
                                openHomePage();
                            } else {
                                if (EmulatorDetector.isEmulator(this) || Build.MODEL.contains("Emulator") || Build.HARDWARE.contains("BlueStack") || Build.MANUFACTURER.contains("Genymotion")) {
                                    DialogUtils.makeSingleButtonDialog(SplashScreen.this, "", "You cannot run this app on a Emulator",
                                            getResources().getString(R.string.close), false, () -> finish());
                                } else {
                                    openHomePage();
                                }
                            }


                        }
                    }
                    if (EmulatorDetector.isEmulator(this) || Build.MODEL.contains("Emulator") || Build.HARDWARE.contains("BlueStack") || Build.MANUFACTURER.contains("Genymotion")) {
                        DialogUtils.makeSingleButtonDialog(SplashScreen.this, "", "You cannot run this app on a Emulator",
                                getResources().getString(R.string.close), false, () -> finish());
                    } else {
                        openHomePage();
                    }
                }


            }
        } else {
            openHomePage();
        }

    }

    public void movetoLoginorHome() {
        Intent intent = new Intent(SplashScreen.this, nextActivity);
        if (nextActivity.equals(HomeActivity.class)) {

            Bundle params = new Bundle();
            params.putString("userid", SharedPreference.getInstance().getLoggedInUser().getId());
            logger.logEvent("alreadylogin", params);

            switch (fragType) {
                case "pdf":
                    intent.putExtra(Const.NOTIFICATION_CODE, 124);
                    intent.putExtra(Const.COURSE_ID, courseid);
                    intent.putExtra(Const.FileID, fileId);
                    intent.putExtra(Const.TOPIC_ID, topicId);
                    intent.putExtra(Const.TILE_TYPE, tileType);
                    intent.putExtra(Const.TILE_ID, tileId);
                    intent.putExtra(Const.REVERT_API, revertApi);
                    intent.putExtra(Const.SHARE_TYPE, Const.ONLINE_COURSES);
                    intent.putExtra(Const.shareparentid, parentid);
                    break;

                case "jwvideo":
                    intent.putExtra(Const.NOTIFICATION_CODE, 123);
                    intent.putExtra(Const.COURSE_ID, courseid);
                    intent.putExtra(Const.FileID, fileId);
                    intent.putExtra(Const.TOPIC_ID, topicId);
                    intent.putExtra(Const.TILE_TYPE, tileType);
                    intent.putExtra(Const.TILE_ID, tileId);
                    intent.putExtra(Const.REVERT_API, revertApi);
                    intent.putExtra(Const.SHARE_TYPE, Const.ONLINE_COURSES);
                    intent.putExtra(Const.shareparentid, parentid);
                    break;
                case "test":
                    intent.putExtra(Const.NOTIFICATION_CODE, 125);
                    intent.putExtra(Const.COURSE_ID, courseid);
                    intent.putExtra(Const.FileID, fileId);
                    intent.putExtra(Const.TOPIC_ID, topicId);
                    intent.putExtra(Const.TILE_TYPE, tileType);
                    intent.putExtra(Const.TILE_ID, tileId);
                    intent.putExtra(Const.REVERT_API, revertApi);
                    intent.putExtra(Const.SHARE_TYPE, Const.ONLINE_COURSES);
                    intent.putExtra(Const.shareparentid, parentid);
                    break;

                case "course":
                    intent.putExtra(Const.NOTIFICATION_CODE, 101);
                    intent.putExtra(Const.shareparentid, parentid);
                    break;
                case "post":
                    intent.putExtra(Const.NOTIFICATION_CODE, 2022);
                    intent.putExtra("postid", postid);
                    break;

                case "video":
                    intent.putExtra(Const.NOTIFICATION_CODE, 90002);
                    intent.putExtra(Const.COURSE_ID, courseid);
                    intent.putExtra(Const.FileID, fileId);
                    intent.putExtra(Const.TOPIC_ID, topicId);
                    intent.putExtra(Const.TILE_TYPE, tileType);
                    intent.putExtra(Const.TILE_ID, tileId);
                    intent.putExtra(Const.REVERT_API, revertApi);
                    intent.putExtra(Const.SHARE_TYPE, Const.ONLINE_COURSES);
                    intent.putExtra(Const.shareparentid, parentid);

                    break;
                default:
                    if (videoid != null && !videoid.equalsIgnoreCase("")) {
                        if (type.equalsIgnoreCase("jw")) {
                            AudioTable audioTable = utkashRoom.getaudiodao().getuser(videoid, MakeMyExam.userId);
                            UserHistroyTable userHistroyTable = utkashRoom.getuserhistorydao().user_hisorydao(MakeMyExam.userId, videoid);
                            intent.putExtra("audiotable", audioTable);
                            if (userHistroyTable != null && userHistroyTable.getVideo_id() != null)
                                intent.putExtra("userHistroyTable", new Gson().toJson(userHistroyTable));

                            intent.putExtra("type", type);
                        } else if (type.equalsIgnoreCase("youtube") || type.equalsIgnoreCase("aws")) {
                            intent.putExtra("type", type + "_" + videoid);
                            intent.putExtra(Const.TILE_ID, tileid);
                            intent.putExtra("tiletype", tiletype);
                        }
                    }
                    intent.putExtra(Const.SHARE_TYPE, Const.FEEDS);
                    break;
            }
        }
        if (nextActivity.equals(LoginCatActivity.class)) {
            intent.putExtra(Const.FRAG_TYPE, Const.CHANGELANGUAGE);
        }
        if (nextActivity.equals(SignInActivity.class)) {
            intent.putExtra(Const.TYPE, Const.SIGNIN);
            intent.putExtra(Const.OPEN_WITH, Const.USER_OPEN);
            intent.putExtra(Const.SOCIAL_TYPE, Const.SOCIAL_TYPE_GMAIL);
            switch (fragType) {
                case "course":
                    intent.putExtra(Const.NOTIFICATION_CODE, 101);
                    intent.putExtra(Const.shareparentid, parentid);
                    break;

                case "pdf":
                    intent.putExtra(Const.NOTIFICATION_CODE, 124);
                    intent.putExtra(Const.COURSE_ID, courseid);
                    intent.putExtra(Const.FileID, fileId);
                    intent.putExtra(Const.TOPIC_ID, topicId);
                    intent.putExtra(Const.TILE_TYPE, tileType);
                    intent.putExtra(Const.TILE_ID, tileId);
                    intent.putExtra(Const.REVERT_API, revertApi);
                    intent.putExtra(Const.SHARE_TYPE, Const.ONLINE_COURSES);
                    intent.putExtra(Const.shareparentid, parentid);
                    break;
                case "post":
                    intent.putExtra(Const.NOTIFICATION_CODE, 2022);
                    intent.putExtra("postid", postid);
                    break;
                case "jwvideo":
                    intent.putExtra(Const.NOTIFICATION_CODE, 123);
                    intent.putExtra(Const.COURSE_ID, courseid);
                    intent.putExtra(Const.FileID, fileId);
                    intent.putExtra(Const.TOPIC_ID, topicId);
                    intent.putExtra(Const.TILE_TYPE, tileType);
                    intent.putExtra(Const.TILE_ID, tileId);
                    intent.putExtra(Const.REVERT_API, revertApi);
                    intent.putExtra(Const.SHARE_TYPE, Const.ONLINE_COURSES);
                    intent.putExtra(Const.shareparentid, parentid);
                    break;
                case "test":
                    intent.putExtra(Const.NOTIFICATION_CODE, 125);
                    intent.putExtra(Const.COURSE_ID, courseid);
                    intent.putExtra(Const.FileID, fileId);
                    intent.putExtra(Const.TOPIC_ID, topicId);
                    intent.putExtra(Const.TILE_TYPE, tileType);
                    intent.putExtra(Const.TILE_ID, tileId);
                    intent.putExtra(Const.REVERT_API, revertApi);
                    intent.putExtra(Const.shareparentid, parentid);
                    intent.putExtra(Const.SHARE_TYPE, Const.ONLINE_COURSES);
                    break;

                case "video":
                    intent.putExtra(Const.NOTIFICATION_CODE, 90002);
                    intent.putExtra(Const.COURSE_ID, courseid);
                    intent.putExtra(Const.FileID, fileId);
                    intent.putExtra(Const.TOPIC_ID, topicId);
                    intent.putExtra(Const.TILE_TYPE, tileType);
                    intent.putExtra(Const.TILE_ID, tileId);
                    intent.putExtra(Const.REVERT_API, revertApi);
                    intent.putExtra(Const.SHARE_TYPE, Const.ONLINE_COURSES);
                    intent.putExtra(Const.shareparentid, parentid);
                    break;
                default:
                    intent.putExtra(Const.SHARE_TYPE, Const.FEEDS);
                    break;
            }
        }

        if (nextActivity.equals(LoginWithOtp.class)) {
            intent.putExtra(Const.TYPE, Const.SIGNIN);
            intent.putExtra(Const.OPEN_WITH, Const.USER_OPEN);
            intent.putExtra(Const.SOCIAL_TYPE, Const.SOCIAL_TYPE_GMAIL);
            switch (fragType) {
                case "course":
                    intent.putExtra(Const.NOTIFICATION_CODE, 101);
                    intent.putExtra(Const.shareparentid, parentid);
                    break;

                case "pdf":
                    intent.putExtra(Const.NOTIFICATION_CODE, 124);
                    intent.putExtra(Const.COURSE_ID, courseid);
                    intent.putExtra(Const.FileID, fileId);
                    intent.putExtra(Const.TOPIC_ID, topicId);
                    intent.putExtra(Const.TILE_TYPE, tileType);
                    intent.putExtra(Const.TILE_ID, tileId);
                    intent.putExtra(Const.REVERT_API, revertApi);
                    intent.putExtra(Const.SHARE_TYPE, Const.ONLINE_COURSES);
                    intent.putExtra(Const.shareparentid, parentid);
                    break;
                case "post":
                    intent.putExtra(Const.NOTIFICATION_CODE, 2022);
                    intent.putExtra("postid", postid);
                    break;
                case "jwvideo":
                    intent.putExtra(Const.NOTIFICATION_CODE, 123);
                    intent.putExtra(Const.COURSE_ID, courseid);
                    intent.putExtra(Const.FileID, fileId);
                    intent.putExtra(Const.TOPIC_ID, topicId);
                    intent.putExtra(Const.TILE_TYPE, tileType);
                    intent.putExtra(Const.TILE_ID, tileId);
                    intent.putExtra(Const.REVERT_API, revertApi);
                    intent.putExtra(Const.SHARE_TYPE, Const.ONLINE_COURSES);
                    intent.putExtra(Const.shareparentid, parentid);
                    break;
                case "test":
                    intent.putExtra(Const.NOTIFICATION_CODE, 125);
                    intent.putExtra(Const.COURSE_ID, courseid);
                    intent.putExtra(Const.FileID, fileId);
                    intent.putExtra(Const.TOPIC_ID, topicId);
                    intent.putExtra(Const.TILE_TYPE, tileType);
                    intent.putExtra(Const.TILE_ID, tileId);
                    intent.putExtra(Const.REVERT_API, revertApi);
                    intent.putExtra(Const.shareparentid, parentid);
                    intent.putExtra(Const.SHARE_TYPE, Const.ONLINE_COURSES);
                    break;

                case "video":
                    intent.putExtra(Const.NOTIFICATION_CODE, 90002);
                    intent.putExtra(Const.COURSE_ID, courseid);
                    intent.putExtra(Const.FileID, fileId);
                    intent.putExtra(Const.TOPIC_ID, topicId);
                    intent.putExtra(Const.TILE_TYPE, tileType);
                    intent.putExtra(Const.TILE_ID, tileId);
                    intent.putExtra(Const.REVERT_API, revertApi);
                    intent.putExtra(Const.SHARE_TYPE, Const.ONLINE_COURSES);
                    intent.putExtra(Const.shareparentid, parentid);
                    break;
                default:
                    intent.putExtra(Const.SHARE_TYPE, Const.FEEDS);
                    break;
            }
        }


        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void openHomePage() {
        if (Helper.isNetworkConnected(this)) {
            if (MakeMyExam.lvl) {
                movetoLoginorHome();
            } else if (lvl) {
                movetoLoginorHome();
            } else {
                Toast.makeText(context, "Please uninstall replica of Utkarsh APP", Toast.LENGTH_SHORT).show();

            }


        } else {
            if (nextActivity != null && nextActivity.equals(HomeActivity.class)) {
                if (SharedPreference.getInstance().getLoggedInUser() != null && SharedPreference.getInstance().getLoggedInUser().getId() != null) {
                    if (SharedPreference.getInstance().getLoggedInUser() != null && !SharedPreference.getInstance().getLoggedInUser().getId().equalsIgnoreCase("0")) {
                        MakeMyExam.setUserId(SharedPreference.getInstance().getLoggedInUser().getId());
                        MakeMyExam.userId = SharedPreference.getInstance().getLoggedInUser().getId();
                        Intent i = new Intent(SplashScreen.this, DownloadActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }
                }

            } else {
                DialogUtils.makeSingleButtonDialog(SplashScreen.this, "", getResources().getString(R.string.no_internet_connection),
                        getResources().getString(R.string.retry), false, () -> next());
            }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        String lang = SharedPreference.getInstance().getString(Const.APP_LANGUAGE);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            super.attachBaseContext(CustomContextWrapper.wrap(newBase, lang));
        } else {
            super.attachBaseContext(newBase);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("response", "onDestroy");
    }

    public String bluestack() {
        String name = "Build.PRODUCT: " + Build.PRODUCT + "\n" +
                "Build.MANUFACTURER: " + Build.MANUFACTURER + "\n" +
                "Build.BRAND: " + Build.BRAND + "\n" +
                "Build.DEVICE: " + Build.DEVICE + "\n" +
                "Build.MODEL: " + Build.MODEL + "\n" +
                "Build.HARDWARE: " + Build.HARDWARE + "\n" +
                "Build.FINGERPRINT: " + Build.FINGERPRINT;
        //Toast.makeText(this,name,Toast.LENGTH_SHORT).show();
        return name;
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.API_GET_APP_VERSION:
                return service.getAppVersion();

        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonObject, String apitype, String typeApi, boolean showprogress) throws JSONException {
        switch (apitype) {
            case API.API_GET_APP_VERSION:
                if (jsonObject.optString(Const.STATUS).equals(Const.TRUE)) {
                    JSONObject data = jsonObject.getJSONObject(Const.DATA);
                    String androidv = data.optString("version");
                    String androidmv = data.optString("min_version");
                    String androidType = data.optString("force_update");
                    String breakFrom = data.optString("break_from");
                    String breakTo = data.optString("break_to");
                    is_otp_login = data.optString("is_otp_login");
                    SharedPreference.getInstance().putString("is_otp_login",is_otp_login);
                    if (data.has("feeds")) {
                        String feeds = data.optString("feeds");
                        MakeMyExam.setFeeds(feeds);
                    }
                    int aCode = Integer.parseInt(androidv);
                    int minVersion = Integer.parseInt(androidmv);
                    if (Long.parseLong(breakFrom) < System.currentTimeMillis() && Long.parseLong(breakTo) > System.currentTimeMillis()) {
                        getMaintanaceDialog(((Activity) context), breakFrom, breakTo);
                    } else {
                        if (androidType.equalsIgnoreCase("0")) {
                            if (isvisible.equalsIgnoreCase("0")) {
                                if (aCode > Helper.getVersionCode(((Activity) this)) || Helper.getVersionCode(((Activity) this)) < minVersion) {
                                    getVersionUpdateDialog(((Activity) this), (Helper.getVersionCode(((Activity) this)) < minVersion ? "1" : androidType));
                                } else {
                                    init_splash();
                                }
                            }
                        } else {
                            isvisible = "0";
                            if (aCode > Helper.getVersionCode(((Activity) this)) || Helper.getVersionCode(((Activity) this)) < minVersion) {
                                getVersionUpdateDialog(((Activity) this), (Helper.getVersionCode(((Activity) this)) < minVersion ? "1" : androidType));
                            } else {
                                init_splash();
                            }
                        }
//                        Helper.getVersionUpdateDialog(SplashScreen.this, androidType);
                    }
                }
                break;
        }
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {
        //Toast.makeText(SplashScreen.this, "" + jsonstring, Toast.LENGTH_SHORT).show();
    }


    public void getMaintanaceDialog(final Activity ctx, String breakFrom, String breakTo) {
        if (!TextUtils.isEmpty(breakFrom) && !TextUtils.isEmpty(breakTo)) {
            // milliseconds
            long startDataValue = Long.parseLong(breakFrom);
            long endDateValue = Long.parseLong(breakTo);
            // Creating date format
            java.text.DateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS Z");
            Date startDate = new Date(startDataValue);
            Date endDate = new Date(endDateValue);
            Date d = Calendar.getInstance().getTime();
            String currDt = sdf.format(d);
            if ((d.after(startDate) && (d.before(endDate))) || (currDt.equals(sdf.format(startDate)) || currDt.equals(sdf.format(endDate)))) {
                DialogUtils.makeSingleButtonDialog(ctx, ctx.getString(R.string.maintain_app_dialog_title), ctx.getString(R.string.maintain_app_dialog_message),
                        ctx.getResources().getString(R.string.close), false, new DialogUtils.onDialogUtilsOkClick() {
                            @Override
                            public void onOKClick() {
                                ctx.finish();
                            }
                        });
            } else {
                if (SharedPreference.getInstance().getBoolean(Const.IS_USER_LOGGED_IN) && SharedPreference.getInstance().getBoolean(Const.IS_USER_REGISTRATION_DONE)) {
                    nextActivity = HomeActivity.class;
                    // for launguage
                    int check = SharedPreference.getInstance().getInt(Const.LANGUAGE);
                    if (check == Const.ENGLISH_CODE) {
                        Helper.changeLang(SharedPreference.getInstance().getString(Const.APP_LANGUAGE, Const.ENGLISH), this);
                    } else if (check == Const.HINDI_CODE) {
                        Helper.changeLang(SharedPreference.getInstance().getString(Const.APP_LANGUAGE, Const.HINDI), this);
                    }
                } else {
                    //nextActivity = LoginCatActivity.class;
//                    nextActivity = SignInActivity.class;

                    if(is_otp_login !=null && is_otp_login.equals("0"))
                    {
                        nextActivity = SignInActivity.class;
                    }
                    else
                    {
                        nextActivity = LoginWithOtp.class;
                    }

                    Helper.changeLang(SharedPreference.getInstance().getString(Const.APP_LANGUAGE, Const.ENGLISH), this);
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        next();
                    }
                }, SPLASH_TIME_OUT);
            }
        }
    }


    public void getVersionUpdateDialog(final Activity ctx, String androidType) {
        boolean isHard;
        if (androidType.equalsIgnoreCase("0")) {
            isHard = false;
        } else {
            isHard = true;
        }
        DialogUtils.makeDialog(ctx, ctx.getString(R.string.update_app_dialog_title), ctx.getString(R.string.update_app_dialog_message),
                ctx.getResources().getString(R.string.update), ctx.getResources().getString(R.string.cancel), isHard, new DialogUtils.onDialogUtilsOkClick() {
                    @Override
                    public void onOKClick() {
                        Helper.rateapp(ctx);
                        ctx.finish();
                    }
                }, new DialogUtils.onDialogUtilsCancelClick() {
                    @Override
                    public void onCancelClick() {
                        if (isHard) {
                            ctx.finish();
                        } else {
                            init_splash();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (handler != null && myRunnable != null) {
            handler.removeCallbacks(myRunnable);
        }
    }
}