package com.utkarshnew.android.Utils;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static com.google.android.gms.common.internal.Preconditions.checkArgument;
import static com.utkarshnew.android.Utils.MakeMyExam.is_track_selector_auto;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StatFs;
import android.provider.Settings;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.TypefaceSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import com.amulyakhare.textdrawable.TextDrawable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.vending.licensing.AESObfuscator;
import com.google.android.vending.licensing.LicenseChecker;
import com.google.android.vending.licensing.LicenseCheckerCallback;
import com.google.android.vending.licensing.ServerManagedPolicy;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.utkarshnew.android.Login.Activity.LoginWithOtp;
import com.utkarshnew.android.courses.Activity.CourseActivity;
import com.utkarshnew.android.courses.Activity.PdfDetailScreen;
import com.utkarshnew.android.courses.Activity.WebFragActivity;
import com.utkarshnew.android.courses.modal.CartItems;
import com.utkarshnew.android.Download.Audio.AudioPlayerService;
import com.utkarshnew.android.Download.DownloadActivity;
import com.utkarshnew.android.Download.DownloadVideoPlayer;
import com.utkarshnew.android.DownloadServices.VideoDownloadService;
import com.utkarshnew.android.home.Activity.HomeActivity;
import com.utkarshnew.android.home.Constants;
import com.utkarshnew.android.home.model.Menu;
import com.utkarshnew.android.JWextractor.JWVideoPlayer;
import com.utkarshnew.android.LiveClass.Activity.LiveClassActivity;
import com.utkarshnew.android.Login.Activity.LoginCatActivity;
import com.utkarshnew.android.Login.Activity.SignInActivity;
import com.utkarshnew.android.Login.Activity.SplashScreen;
import com.utkarshnew.android.Model.COURSEDETAIL.CourseDetailData;
import com.utkarshnew.android.Model.Courses.SinglestudyModel;
import com.utkarshnew.android.Model.PostFile;
import com.utkarshnew.android.Model.UrlObject;
import com.utkarshnew.android.Model.Video;
import com.utkarshnew.android.Player.CustomMediaPlayer;
import com.utkarshnew.android.Player.DrmVideoPlayerActivity;
import com.utkarshnew.android.Player.Liveawsactivity;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.retrofit.NimbusRetrofitApi;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitClient;
import com.utkarshnew.android.Webview.PdfHtmlAcivity;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class Helper {
    private static Dialog dialog;

    public static LicenseCheckerCallback mLicenseCheckerCallback;
    public static LicenseChecker mChecker;
    public static Handler mHandler;
    private static final byte[] SALT = new byte[]{
            -46, 65, 30, -128, -103, -57, 74, -64, 51, 88, -95, -45, 77, -117, -36, -113, -11, 32, -64,
            89
    };

    public static boolean isValidMobile(String phone) {
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() >= 10 && phone.length() <= 10;
        }
        return false;
    }

    public static void GoToLiveAwsVideoActivity(String videotype, String chatnode, Activity activity, String Url, String islive, String vid, String title, String is_audio, String thubnail, String course_id, String tileid, String tiletype, String islocked, String pos, String parentid, String json) {
        Intent intent = new Intent(activity, Liveawsactivity.class);

        intent.putExtra(Const.VIDEO_LINK, Url);
        intent.putExtra("Chat_node", chatnode);
        intent.putExtra("video_type", videotype);
        intent.putExtra(Const.VIDEO_LIVE, islive);
        intent.putExtra("video_id", vid);
        intent.putExtra("video_name", title);
        intent.putExtra("isaudio", is_audio);
        intent.putExtra("thumbnail", thubnail);
        intent.putExtra("tileid", tileid);
        intent.putExtra("tiletype", tiletype);
        intent.putExtra("courseid", course_id);
        intent.putExtra("islocked", islocked);
        intent.putExtra("parentid", parentid);
        intent.putExtra("pos", pos);
        intent.putExtra("token", "");
        intent.putExtra("json", json);
        gotoActivity(intent, activity);
    }

    public static void GoToLiveDrmVideoActivity(String videotype, String chatnode, Activity activity, String Url, String islive, String vid, String title, String is_audio, String thubnail, String course_id, String tileid, String tiletype, String islocked, String pos, String parentid, String json) {
        Intent intent = new Intent(activity, DrmVideoPlayerActivity.class);
        intent.putExtra(Const.VIDEO_LINK, Url);
        intent.putExtra("Chat_node", chatnode);
        intent.putExtra("video_type", videotype);
        intent.putExtra(Const.VIDEO_LIVE, islive);
        intent.putExtra("video_id", vid);
        intent.putExtra("video_name", title);
        intent.putExtra("isaudio", is_audio);
        intent.putExtra("thumbnail", thubnail);
        intent.putExtra("tileid", tileid);
        intent.putExtra("tiletype", tiletype);
        intent.putExtra("courseid", course_id);
        intent.putExtra("islocked", islocked);
        intent.putExtra("parentid", parentid);
        intent.putExtra("pos", pos);
        intent.putExtra("token", "");
        intent.putExtra("json", json);
        gotoActivity(intent, activity);
    }

    public static String isvisible = "0";

    public static void shareTestg(final Activity activity, String maincourseid, String fieldid, String topicid, String tile_type, String tileid, String revertapi, String type, String image, String name, String parentid) {
        final Progress progress = new Progress(activity);
        progress.show();

        String actualurl;

        if (parentid.equalsIgnoreCase(maincourseid)) {
            parentid = null;
        }

        if (parentid == null || parentid.isEmpty()) {
            actualurl = "testcourseid=" + maincourseid
                    + "&fieldid=" + fieldid
                    + "&topicid=" + topicid
                    + "&tile_type=" + tile_type
                    + "&tileid=" + tileid
                    + "&revertapi=" + revertapi
                    + "&type=" + type;
        } else {
            actualurl = "testcourseid=" + maincourseid
                    + "&fieldid=" + fieldid
                    + "&topicid=" + topicid
                    + "&tile_type=" + tile_type
                    + "&parentid=" + parentid
                    + "&tileid=" + tileid
                    + "&revertapi=" + revertapi
                    + "&type=" + type;

        }


        FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse(API.BASE_URL_WEB + "?" + "data=" +
                        Base64.encodeToString(actualurl.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT)))

                .setSocialMetaTagParameters(new DynamicLink.SocialMetaTagParameters.Builder()
                        .setImageUrl(Uri.parse(image)).setTitle(name).build())

                .setDynamicLinkDomain("utkarshnew.page.link")
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                .setIosParameters(
                        new DynamicLink.IosParameters.Builder("com.eUtkarsh.ios")
                                .setAppStoreId("1446577039")
                                .build())
                .buildShortDynamicLink()
                .addOnCompleteListener(activity, task -> {
                    progress.dismiss();
                    if (task.isSuccessful()) {
                        Uri shortLink = task.getResult().getShortLink();
                        String msgHtml = String.format(shortLink.toString());
                        sendLink(activity, "Course link", shortLink.toString(),
                                msgHtml);
                    } else {
                        progress.dismiss();
                        Toast.makeText(activity, "Link could not be generated please try again!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static String getDate(long milliSeconds, String dateFormat) {
        Date date = new Date(milliSeconds);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(date);
    }

    public static void shareCourse(final Activity activity, String maincourseid, String iscombo, String parentcourseid, String name, String image, String title) {
        final Progress progress = new Progress(activity);
        progress.show();
        String actualurl;
        if (parentcourseid == null || parentcourseid.isEmpty()) {
            actualurl = "maincouseid=" + maincourseid
                    + "&iscombo=" + iscombo;
        } else {
            actualurl = "maincouseid=" + maincourseid
                    + "&iscombo=" + iscombo
                    + "&parentcourseid=" + parentcourseid;
        }


        FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse(API.BASE_URL_WEB + "?" + "data=" +
                        Base64.encodeToString(actualurl.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT)))

                .setSocialMetaTagParameters(new DynamicLink.SocialMetaTagParameters.Builder()
                        .setImageUrl(Uri.parse(image)).setTitle(title).build())
                .setDynamicLinkDomain("utkarshnew.page.link")
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                .setIosParameters(
                        new DynamicLink.IosParameters.Builder("com.eUtkarsh.ios")
                                .setAppStoreId("1446577039")
                                .build())
                .buildShortDynamicLink()
                .addOnCompleteListener(activity, task -> {
                    progress.dismiss();
                    if (task.isSuccessful()) {
                        Uri shortLink = task.getResult().getShortLink();
                        String msgHtml = String.format(shortLink.toString());
                        sendLink(activity, "Course link", shortLink.toString(),
                                msgHtml);
                    } else {
                        progress.dismiss();
                        Toast.makeText(activity, "Link could not be generated please try again!", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public static void sharePost(final Activity activity, String maincourseid, String image, String title) {
        final Progress progress = new Progress(activity);
        progress.show();
        String actualurl;
        actualurl = "postid=" + maincourseid;
        Log.d("hyyy", "" + Uri.parse(API.BASE_URL_WEB + "?" + "data=" +
                Base64.encodeToString(actualurl.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT)));

        FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse(API.BASE_URL_WEB + "?" + "data=" +
                        Base64.encodeToString(actualurl.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT)))

                .setSocialMetaTagParameters(new DynamicLink.SocialMetaTagParameters.Builder()
                        .setImageUrl(Uri.parse(image)).setTitle(title).build())
                .setDynamicLinkDomain("utkarshnew.page.link")
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                .setIosParameters(new DynamicLink.IosParameters.Builder("com.eUtkarsh.ios").setAppStoreId("1446577039").build())
                .buildShortDynamicLink()
                .addOnCompleteListener(activity, task -> {
                    progress.dismiss();
                    if (task.isSuccessful()) {
                        Uri shortLink = task.getResult().getShortLink();
                        String msgHtml = String.format(shortLink.toString());
                        sendLink(activity, "Course link", shortLink.toString(),
                                msgHtml);
                    } else {
                        progress.dismiss();
                        Toast.makeText(activity, "Link could not be generated please try again!", Toast.LENGTH_SHORT).show();
                    }
                });

    }


    public static TextDrawable GetDrawableWithCustomColor(String text, Context context, int color) {
        if (!TextUtils.isEmpty(text)) {
            String firstLetter = text.substring(0, 1);
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(text, color);
            return drawable;
        } else
            return null;
    }

    public static void dismissProgressDialog() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void showProgressDialog(Context context) {
        try {
            dismissProgressDialog();

            dialog = new Dialog(context, R.style.TransparentDialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            View view = LayoutInflater.from(context).inflate(R.layout.progress_layout_new, null);
            dialog.setContentView(view);
            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean doCheck(Context activity) {
        final boolean[] status = {false};
        @SuppressLint("HardwareIds") String deviceId = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
        String BASE64_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkdWEIng+GPNtD237Vjn+1v9vO1j0Kho0q/HGtbzugiJlRFjFSGrQCHh4pJYtTlraCWI5vEw+uzphm42ovM3BVPO9PUNVT607U22AggvxxcryHFc154VPJvLuJyRdfo43RFgPTzK7TrYlwFOOld5T+B7ad02Vf+WggNeW3VeGkzmyOiIQiy2pMD6L+xUV9ScLurUVpNDZyrByYpb0s8Ebd6gz8RBYlYvfrygdIUWEAuSSeElcRLE9os4FVJekkSLaQTP41NEfmYw9VOtQvC+p3gS+5W5sipWgNplsfTceyqtkD85x4zOtLlYd8i1dgUxSDKuOIo8krvea7U/O7fytzwIDAQAB";
        mChecker = new LicenseChecker(activity, new ServerManagedPolicy(activity, new AESObfuscator(SALT, activity.getPackageName(), deviceId)), BASE64_PUBLIC_KEY.trim());
        mChecker.checkAccess(new LicenseCheckerCallback() {

            @Override
            public void allow(int reason) {
               /* if (activity.isFinishing()) {
                    return;
                }*/
                status[0] = true;
            }

            @Override
            public void dontAllow(int reason) {
               /* if (activity.isFinishing()) {
                    return;
                }*/
                status[0] = false;

            }

            @Override
            public void applicationError(int errorCode) {
               /* if (activity.isFinishing()) {
                    return;
                }*/
                status[0] = false;

            }
        });
        return status[0];
    }


/*
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService
-keep public class com.google.android.vending.licensing.ILicensingService
-dontnote com.android.vending.licensing.ILicensingService
-dontnote com.google.vending.licensing.ILicensingService
-dontnote com.google.android.vending.licensing.ILicensingService*/

    public static String convertSeconds(int seconds) {
        int h = seconds / 3600;
        int m = (seconds % 3600) / 60;
        int s = seconds % 60;
        String sh = (h > 0 ? String.valueOf(h) + " " + "h" : "");
        String sm = (m < 10 && m > 0 && h > 0 ? "0" : "") + (m > 0 ? (h > 0 && s == 0 ? String.valueOf(m) : String.valueOf(m) + " " + "min") : "");
        String ss = (s == 0 && (h > 0 || m > 0) ? "" : (s < 10 && (h > 0 || m > 0) ? "0" : "") + String.valueOf(s) + " " + "sec");
        return sh + (h > 0 ? " " : "") + sm + (m > 0 ? " " : "") + ss;
    }

    private static boolean canTakeScreenShot;

    public static boolean setCanTakeScreenShot() {
        return canTakeScreenShot;
    }

    public static void setCanTakeScreenShot(boolean canTakeScreenShot) {
        Helper.canTakeScreenShot = canTakeScreenShot;
    }

    public static void showToastSecurity(Activity activity) {
        Toast.makeText(activity, "Unable to load due to security!", Toast.LENGTH_SHORT).show();
    }

    public static boolean DataNotValid(EditText view) {
        view.setError("This field is required");
        view.requestFocus();
        return false;
    }

    public static boolean DataNotValid(AutoCompleteTextView view) {
        view.setError("This field is required");
        view.requestFocus();
        return false;
    }

    public static boolean DataNotValid(TextInputLayout view) {
        view.setError("This field is required");
        view.requestFocus();
        return false;
    }

    public static boolean isNetworkConnected(Context ctx) {
        try {

            ConnectivityManager connectivityManager = (ConnectivityManager) MakeMyExam.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
            if (ni != null && ni.isAvailable() && ni.isConnected()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static void getAvailableInternalSpace(String stringurl) {
        URL url = null;
        try {
            url = new URL(stringurl);
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
            int file_size = urlConnection.getContentLength();
            Log.e("File Size", String.valueOf(file_size));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long availableBlocks = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            availableBlocks = stat.getBlockSizeLong();
        } else {
            availableBlocks = stat.getBlockSize();
        }
        return availableBlocks;
    }

    public static void showSnackBar(View view, CharSequence text) {
        try {
            if (view != null) {
                final Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_INDEFINITE);
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
                textView.setTextColor(Color.parseColor(Const.SNACKBAR_TEXT_COLOR));
                textView.setMaxLines(5);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        snackbar.dismiss();
                    }
                }, 4000);
                snackbar.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void restartsApp(Context context, boolean restartProcess) {
        Intent i = new Intent(context, SplashScreen.class);
        context.startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

        if (restartProcess) {
            System.exit(0);
        } else {
            //Toast.makeText(this, "Activity restarted", Toast.LENGTH_SHORT).show();
        }
    }


    public static void enableScreenShot(Activity activity) {
        if (!Helper.isNetworkConnected(activity)) {
            if (activity instanceof SplashScreen) {

            } else {
                Helper.showInternetToast(activity);
            }
        }
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        if (Constants.forWithSecurity) {
            if (canTakeScreenShot) {
                activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                        WindowManager.LayoutParams.FLAG_SECURE);
            }
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    public static void checkXposed(Activity activity) {
        if (Constants.forWithSecurity) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    securityForRoot(activity);
                }
            }, 100);
        }

        /*AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                securityForRoot(activity);
            }
        });*/
    }

    public static void securityForRoot(Activity activity) {
        boolean secutiy = false;
        boolean secutiyIsFirst = true;
        PackageManager pm = activity.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo packageInfo : packages) {
            try {
                String app_name = (String) pm.getApplicationLabel(pm.getApplicationInfo(packageInfo.packageName, PackageManager.GET_META_DATA));
                if (packageInfo.packageName.contains("de.robv.android.xposed.installer") ||
                        packageInfo.packageName.contains("de.robv.android.xposed.installer.WelcomeActivity") ||
                        packageInfo.packageName.contains("com.liof.screenrecfree") ||
                        packageInfo.packageName.contains("com.hecorat.screenrecorder.free") ||
                        packageInfo.packageName.contains("me.weishu.exp") ||
                        packageInfo.packageName.contains("io.virtualapp") ||
                        packageInfo.packageName.contains("vidma.screenrecorder.videorecorder.videoeditor.pro") ||
                        packageInfo.packageName.contains("de.robv.android.xposed:api:82") ||
                        packageInfo.packageName.contains("de.robv.android.xposed:api:82:sources") ||
                        app_name.equalsIgnoreCase("VirtualXposed") ||
                        packageInfo.packageName.contains("com.topjohnwu.magisk")) {
                    secutiy = true;
                    break;
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (secutiy) {
            if (secutiyIsFirst) {
                secutiyIsFirst = false;
                DialogUtils.makeSingleButtonDialog(activity, "", activity.getResources().getString(R.string.screen_capturing_msg),
                        activity.getResources().getString(R.string.close), false, new DialogUtils.onDialogUtilsOkClick() {
                            @Override
                            public void onOKClick() {
                                activity.finish();
                            }
                        });
            }
        } else {
            if (bluestack() != null && bluestack().contains("bluestack")) {
                DialogUtils.makeSingleButtonDialog(activity, "", activity.getResources().getString(R.string.physical_device_msg),
                        activity.getResources().getString(R.string.close), false, new DialogUtils.onDialogUtilsOkClick() {
                            @Override
                            public void onOKClick() {
                                activity.finish();
                            }
                        });
            } else {
                if (RootUtil.isDeviceRooted() || RootChecker.isRooted(activity) || RootUtil.detectFullRoot(activity) || RootUtil.check_proxy(activity) || Build.HOST.startsWith("Build")
                        || "google_sdk" == Build.PRODUCT
                        || EmulatorDetector.isEmulator(activity)
                        || Build.MODEL.contains("Emulator")
                        || Build.HARDWARE.contains("BlueStack")//bluestacks
                        || Build.MANUFACTURER.contains("Genymotion")) {
                    DialogUtils.makeSingleButtonDialog(activity, "", activity.getResources().getString(R.string.physical_device_msg),
                            activity.getResources().getString(R.string.close), false, new DialogUtils.onDialogUtilsOkClick() {
                                @Override
                                public void onOKClick() {
                                    activity.finish();
                                }
                            });

                } else {
                    if (Helper.isNetworkConnected(activity)) {
                        if (TextUtils.isEmpty(Build.getRadioVersion())) {
                            DialogUtils.makeSingleButtonDialog(activity, "", activity.getResources().getString(R.string.physical_device_msg),
                                    activity.getResources().getString(R.string.close), false, new DialogUtils.onDialogUtilsOkClick() {
                                        @Override
                                        public void onOKClick() {
                                            activity.finish();
                                        }
                                    });
                        }
                    } else {
                        DialogUtils.makeSingleButtonDialog(activity, "", activity.getResources().getString(R.string.no_internet_connection),
                                activity.getResources().getString(R.string.close), false, new DialogUtils.onDialogUtilsOkClick() {
                                    @Override
                                    public void onOKClick() {
                                        activity.finish();
                                    }
                                });
                    }
                }
            }
        }
    }

    public static String bluestack() {
        String name = "Build.PRODUCT: " + Build.PRODUCT + "\n" +
                "Build.MANUFACTURER: " + Build.MANUFACTURER + "\n" +
                "Build.BRAND: " + Build.BRAND + "\n" +
                "Build.DEVICE: " + Build.DEVICE + "\n" +
                "Build.MODEL: " + Build.MODEL + "\n" +
                "Build.HARDWARE: " + Build.HARDWARE + "\n" +
                "Build.FINGERPRINT: " + Build.FINGERPRINT;
        return name;
    }

    /*public static  boolean isDevMode(Activity activity) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent = activity.registerReceiver(null, new IntentFilter("android.hardware.usb.action.USB_STATE"));
            return intent!=null?intent.getExtras().getBoolean("connected"):false;
        }else {
            Intent intent = activity.registerReceiver(null, new IntentFilter("Intent.ACTION_POWER_CONNECTED"));
            return intent!=null?intent.getExtras().getBoolean("connected"):false;
        }
    }*/

    public static boolean isDevMode(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent = activity.registerReceiver(null, new IntentFilter("android.hardware.usb.action.USB_STATE"));
            return intent.getExtras().getBoolean("connected");
        } else {
            Intent intent = activity.registerReceiver(null, new IntentFilter("android.hardware.usb.action.USB_STATE"));
            return intent.getExtras().getBoolean("connected");
        }
    }

    public static String youtubevalidation(String des) {

        des = des.trim();
        String[] parts = des.split("\\s+");

        Log.d("Youtube Validation", "Enter");
        Log.d("String", parts[0]);
        final String regex1 = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|watch\\?v%3D|\u200C\u200B%2Fvideos%2F|embed%2\u200C\u200BF|youtu.be%2F|%2Fv%2\u200C\u200BF)[^#\\&\\?\\n]*";
        final Pattern pattern1 = Pattern.compile(regex1, Pattern.MULTILINE);
        for (int i = 0; i < parts.length; i++) {
            final Matcher matcher1 = pattern1.matcher(parts[i]);
            Log.d("Youtube Validation", "Matching");
            if (matcher1.find()) {
                Log.d("Youtube Validation", "Matched");
                return matcher1.group();
            }
        }
        return null;
    }

    public static void logUser(Activity activity) {
    }

    public static void rateapp(Activity activity) {
        Uri uri = Uri.parse("market://details?id=" + activity.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            activity.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + activity.getPackageName())));
        }
    }

    public static String getVersionName(Activity activity) {
        String version = "";
        try {
            PackageInfo pInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static int getVersionCode(Activity activity) {
        int version = 0;
        try {
            PackageInfo pInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            version = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    // List of the Navigation menu
    public static ArrayList<String> gettitleList(Activity activity) {
        ArrayList<String> expandableListTitle = new ArrayList<>();
        expandableListTitle.add(activity.getString(R.string.specialities));
        expandableListTitle.add(activity.getString(R.string.course));
        expandableListTitle.add(activity.getString(R.string.video));
        expandableListTitle.add(activity.getString(R.string.savedNotes));
        expandableListTitle.add(activity.getString(R.string.rewardpoints));
        expandableListTitle.add(activity.getString(R.string.chatbot));
        expandableListTitle.add(activity.getString(R.string.feedback));
//        expandableListTitle.add(activity.getString(R.string.termscond));
//        expandableListTitle.add(activity.getString(R.string.privacy));
        expandableListTitle.add(activity.getString(R.string.appSettings));
        expandableListTitle.add(activity.getString(R.string.logout));

        return expandableListTitle;
    }


    // List of the Navigation Course Section menu
    public static ArrayList<String> getcourseSubList(Context activity) {
        ArrayList<String> coursesublist = new ArrayList<>();
        coursesublist.add(activity.getString(R.string.allcourses));
        coursesublist.add(activity.getString(R.string.mycourse));
        coursesublist.add(activity.getString(R.string.leaderboard));
        return coursesublist;
    }

    public static File createImageFile(Context ctx) {
        File extStorageAppBasePath = null;
        File extStorageAppCachePath;
        String state = Environment.getExternalStorageState();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File mFileTemp = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            // Retrieve the base path for the application in the external storage
            File externalStorageDir = Environment.getExternalStorageDirectory();

            if (externalStorageDir != null) {
                // {SD_PATH}/Android/data/com.app.urend
                extStorageAppBasePath = new File(externalStorageDir.getAbsolutePath() +
                        File.separator + "Android" + File.separator + "data" +
                        File.separator + ctx.getPackageName() + File.separator + "EmedicozImages");
            }

            if (extStorageAppBasePath != null) {
                // {SD_PATH}/Android/data/com.app.urend/cache
                extStorageAppCachePath = new File(extStorageAppBasePath.getAbsolutePath() +
                        File.separator + "cache");

                boolean isCachePathAvailable = true;

                if (!extStorageAppCachePath.exists()) {
                    // Create the cache path on the external storage
                    isCachePathAvailable = extStorageAppCachePath.mkdirs();
                }

                if (!isCachePathAvailable) {
                    // Unable to create the cache path
                    extStorageAppCachePath = null;
                }
            }
        }
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            try {
                if (extStorageAppBasePath != null) {
                    mFileTemp = File.createTempFile(imageFileName, ".jpg", extStorageAppBasePath);
                } else {
                    mFileTemp = File.createTempFile(imageFileName, ".jpg", Environment.getExternalStorageDirectory());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (extStorageAppBasePath != null) {
                    mFileTemp = File.createTempFile(imageFileName, ".jpg", extStorageAppBasePath);
                } else {
                    mFileTemp = File.createTempFile(imageFileName, ".jpg", ctx.getFilesDir());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mFileTemp;
    }

    //methods to compress image starts//
    public static Bitmap decodeSampledBitmap(String url, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // BitmapFactory.decodeResource(res, resId, options);
        BitmapFactory.decodeFile(url, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(url, options);
    }

    //methods to compress image starts//
    public static byte[] FileToByteArray(String file) {
        File fil = new File(file);

        byte[] b = new byte[(int) fil.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(fil);
            fileInputStream.read(b);
            for (int i = 0; i < b.length; i++) {
                System.out.print((char) b[i]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found.");
            e.printStackTrace();
        } catch (IOException e1) {
            System.out.println("Error Reading The File.");
            e1.printStackTrace();
        }
        return b;
    }

    public static void downloadFile(String _url, String _name, Context mContext) {


//        try {
//            URL u = new URL(_url);
//            mContext.getFilesDir();
//            DataInputStream stream = new DataInputStream(u.openStream());
//            byte[] buffer = IOUtils.toByteArray(stream);
//            FileOutputStream fos = mContext.openFileOutput(_name, Context.MODE_PRIVATE);
//            fos.write(buffer);
//            fos.flush();
//            fos.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return;
//        }
    }

    public static void downloadFileExternal(String _url, String _name, Context mContext) {

        File path = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/" + mContext.getPackageName() + "/" + ".Downloaded");
        if (!path.exists()) {
            Log.e("Download", "path created " + path.toString());
            path.mkdirs();
        }

        String FileName = _name;
        File filepath = new File(path + "/" + FileName);
        if (!filepath.exists()) {

            DownloadManager manager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri downloadUri = Uri.parse(_url);
            DownloadManager.Request request = new DownloadManager.Request(downloadUri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE |
                            DownloadManager.Request.NETWORK_WIFI).setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION).
                    setTitle(mContext.getResources().getString(R.string.app_name)).setAllowedOverRoaming(true);
            File internalPath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + "UtkarshFiles");
            if (!internalPath.exists()) {
                Log.e("Download", "internalPath created " + internalPath.toString());
                internalPath.mkdirs();
            }

            //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, FileName);
            request.setDestinationInExternalFilesDir(mContext, Environment.DIRECTORY_DOWNLOADS + "/" + "UtkarshFiles", FileName);
            request.allowScanningByMediaScanner();
            manager.enqueue(request);
//            File NewFileCreated = new File(internalPath + "/" + FileName);
//            if (postFile.getFile_type().equals(Const.PDF))
//                showPdf(NewFileCreated, activity, 1);
        }
    }

    public static boolean DeleteFileFromStorage(String _name, Context mContext) {
        return mContext.deleteFile(_name);
    }

    public static boolean checkfileExists(final Activity activity, final PostFile postFile) {
        boolean isExist = false;
        File path = new File(Environment.getExternalStorageDirectory().getPath() + "/" + activity.getPackageName() + "/" + "Downloaded");
        if (!path.exists()) {
            path.mkdirs();
        }
        final String FileName = postFile.getFile_info();
        final File filepath = new File(path + "/" + FileName);
        if (!filepath.exists()) {
            isExist = false;
        } else {
            isExist = true;
        }
        return isExist;
    }

    public static ProgressDialog progressBar;

    public static void DownloadfilefromURL(final Activity activity, final PostFile postFile) {
        //Toast.makeText(activity, "Downloading in progress", Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            progressBar = new ProgressDialog(new ContextThemeWrapper(activity, android.R.style.Theme_Holo_Light_Dialog));
        } else {
            progressBar = new ProgressDialog(activity);
        }
        try {
            Log.e("Download", "This is state " + Environment.getExternalStorageState());
            Log.e("Download", "This is getAbsolutePath " + Environment.getExternalStorageDirectory().getAbsolutePath());
            boolean flag = false;
            File path = new File(Environment.getExternalStorageDirectory().getPath() + "/" + activity.getPackageName() + "/" + "Downloaded");
            if (!path.exists()) {
                Log.e("Download", "path created " + path.toString());
                path.mkdirs();
            }
            final String FileName = postFile.getFile_info();
            final File filepath = new File(path + "/" + FileName);
            if (!filepath.exists()) {
                DownloadManager manager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
                Uri downloadUri = Uri.parse(postFile.getLink());
                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE |
                                DownloadManager.Request.NETWORK_WIFI)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setTitle(activity.getResources().getString(R.string.app_name)).setAllowedOverRoaming(true);
                final File internalPath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + "UtkarshFiles");
                if (!internalPath.exists()) {
                    Log.e("Download", "internalPath created " + internalPath.toString());
                    internalPath.mkdirs();
                } else {
                    Log.e("Download", "internalPath created " + internalPath.toString());
                }
                //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, FileName);
                request.setDestinationInExternalFilesDir(activity, Environment.DIRECTORY_DOWNLOADS + "/" + "UtkarshFiles", FileName);
                request.allowScanningByMediaScanner();
                final Long id = manager.enqueue(request);
                flag = true;
                final boolean finalFlag = flag;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    progressBar = new ProgressDialog(new ContextThemeWrapper(activity, android.R.style.Theme_Holo_Light_Dialog));
                } else {
                    progressBar = new ProgressDialog(activity);
                }
                progressBar.setCancelable(false);
                progressBar.setMessage("Downloading...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressBar.setProgress(0);
                progressBar.setMax(100);
                progressBar.setProgressDrawable(activity.getResources().getDrawable(R.drawable.progress_bar_download));
                progressBar.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (manager != null && id != null) {
                            manager.remove(id);
                        }
                        try {
                            dialog.dismiss();
                        } catch (Exception e) {
                            Log.d("progressBar", "dismiss: " + e.getMessage());
                        }
                    }
                });
                try {
                    progressBar.show();
                } catch (Exception e) {
                    Log.d("progressBar", "show: " + e.getMessage());
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            try {
                                boolean downloading = true;
                                DownloadManager manager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
                                while (downloading) {
                                    DownloadManager.Query q = new DownloadManager.Query();
                                    q.setFilterById(id); //filter by id which you have receieved when reqesting download from download manager
                                    Cursor cursor = manager.query(q);
                                    cursor.moveToFirst();

                                    int bytes_downloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                                    int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                                    if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                                        downloading = false;
                                        Log.e("status", String.valueOf(downloading));
                                    }

                                    final int dl_progress = (int) ((bytes_downloaded * 100l) / bytes_total);
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressBar.setProgress((int) dl_progress);
                                        }
                                    });
                                    cursor.close();
                                }
                            } catch (Throwable t) {
                                // just end the background thread
                                Log.i("Animation", "Thread  exception " + t);
                                try {
                                    if (progressBar != null && progressBar.isShowing()) {
                                        progressBar.dismiss();
                                    }
                                } catch (Exception e) {
                                    Log.d("progressBar", "dismiss: " + e.getMessage());
                                }
                            }

                            activity.runOnUiThread(new Runnable() {
                                public void run() {
                                    try {
                                        if (progressBar != null && progressBar.isShowing()) {
                                            progressBar.dismiss();
                                        }
                                    } catch (Exception e) {
                                        Log.d("progressBar", "dismiss: " + e.getMessage());
                                    }

                                    /*try {
                                        showFiles(filepath, activity, postFile.getFile_type().equals(Const.PDF) ? postFile.getFile_type() : Const.DOC, finalFlag);
                                    } catch (Exception e) {
                                        Log.d("showFiles", "showFiles: " + e.getMessage());
                                    }*/
                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                            try {
                                if (progressBar != null && progressBar.isShowing()) {
                                    progressBar.dismiss();
                                }
                            } catch (Exception ex) {
                                Log.d("progressBar", "dismiss: " + ex.getMessage());
                            }
                        }

                    }

                }).start();

            } else {
                try {
                    showFiles(filepath, activity, postFile.getFile_type().equals(Const.PDF) ? postFile.getFile_type() : Const.DOC, flag);
                } catch (Exception e) {
                    Log.d("showFiles", "showFiles: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void DownloadfilefromURL(final Activity activity, final ImageView doc_imageIV, final PostFile postFile) {
        Log.e("Download", "This is state " + Environment.getExternalStorageState());
        Log.e("Download", "This is getAbsolutePath " + Environment.getExternalStorageDirectory().getAbsolutePath());

        boolean flag = false;
        File path = new File(Environment.getExternalStorageDirectory().getPath() + "/" + activity.getPackageName() + "/" + "Downloaded");
        if (!path.exists()) {
            Log.e("Download", "path created " + path.toString());
            path.mkdirs();
        }
        final String FileName = postFile.getFile_info();
        final File filepath = new File(path + "/" + FileName);
        if (!filepath.exists()) {
            DownloadManager manager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri downloadUri = Uri.parse(postFile.getLink());
            DownloadManager.Request request = new DownloadManager.Request(downloadUri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE |
                            DownloadManager.Request.NETWORK_WIFI)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setTitle(activity.getResources().getString(R.string.app_name)).setAllowedOverRoaming(true);
            final File internalPath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + "UtkarshFiles");
            if (!internalPath.exists()) {
                Log.e("Download", "internalPath created " + internalPath.toString());
                internalPath.mkdirs();
            } else {
                Log.e("Download", "internalPath created " + internalPath.toString());
            }
            //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, FileName);
            request.setDestinationInExternalFilesDir(activity, Environment.DIRECTORY_DOWNLOADS + "/" + "UtkarshFiles", FileName);
            request.allowScanningByMediaScanner();
            final Long id = manager.enqueue(request);
            flag = true;
            final boolean finalFlag = flag;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        boolean downloading = true;
                        DownloadManager manager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
                        while (downloading) {
                            DownloadManager.Query q = new DownloadManager.Query();
                            q.setFilterById(id); //filter by id which you have receieved when reqesting download from download manager
                            Cursor cursor = manager.query(q);
                            cursor.moveToFirst();

                            if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                                downloading = false;
                                Log.e("status", String.valueOf(downloading));
                            }/*else if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_RUNNING) {
                                downloading = true;
                                Toast.makeText(activity, "File already downloading...", Toast.LENGTH_SHORT).show();
                            }*/
                        }
                    } catch (Throwable t) {
                        // just end the background thread
                        Log.i("Animation", "Thread  exception " + t);
                    }

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (postFile.getFile_type().equals(Const.PDF)) {
                                doc_imageIV.setImageResource(R.mipmap.pdf);
                            } else if (postFile.getFile_type().equals(Const.DOC)) {
                                doc_imageIV.setImageResource(R.mipmap.doc);
                            } else if (postFile.getFile_type().equals(Const.XLS)) {
                                doc_imageIV.setImageResource(R.mipmap.doc);
                            }
                            //showFiles(filepath, activity, postFile.getFile_type().equals(Const.PDF) ? postFile.getFile_type() : Const.DOC, finalFlag);
                        }
                    });

                }

            }).start();

        } else {
            showFiles(filepath, activity, postFile.getFile_type().equals(Const.PDF) ? postFile.getFile_type() : Const.DOC, flag);
        }
    }

    public static boolean isDownloading(Context context, long downloadId) {
        return getStatus(context, downloadId) == DownloadManager.STATUS_RUNNING;
    }

    public static int getStatus(Context context, long downloadId) {
        DownloadManager downloadManager =
                (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);// filter your download bu download Id
        Cursor c = downloadManager.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            c.close();
            Log.i("DOWNLOAD_STATUS", String.valueOf(status));
            return status;
        }
        Log.i("AUTOMATION_DOWNLOAD", "DEFAULT");
        return -1;
    }

    public static void showPdf(File file, Activity activity, int type) {
        PackageManager packageManager = activity.getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        if (type == 1)
            testIntent.setType("application/pdf");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri uri = UtkarshFileProvider.getUriForFile(activity, "com.utkarshnew.android.Utils.UtkarshFileProvider", file);
        if (type == 1)
            intent.setDataAndType(uri, "application/pdf");
        try {
            Helper.gotoActivity(intent, activity);
        } catch (ActivityNotFoundException e) {
            // Instruct the user to install a PDF reader here, or something
            Toast.makeText(activity, "Unable to open the file. No application available", Toast.LENGTH_SHORT).show();
        }

    }

    private static Uri getUriForFile(Context context, File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                String packageId = context.getPackageName();
                return UtkarshFileProvider.getUriForFile(context, "com.utkarshnew.android.Utils.UtkarshFileProvider", file);
            } catch (IllegalArgumentException e) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    throw new SecurityException();
                } else {
                    return Uri.fromFile(file);
                }
            }
        } else {
            return Uri.fromFile(file);
        }
    }

    public static void getimageFile(Context ctx) {

        String FILENAME = "sample-ppt";
        String string = "hello world!";
        Uri pptUri = Uri.parse("file://Internal storage/Download/sample-ppt.ppt");

        File NewFileCreated = new File("//Internal storage/Download/sample-ppt.ppt");

        FileOutputStream fos = null;
        try {
            fos = ctx.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(NewFileCreated.getAbsolutePath().getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.e("Tag Dowload ", ctx.getFilesDir().toString());
        getimage(ctx);
    }

    public static void getimage(Context ctx) {

        String FILENAME = "sample-ppt";
//        Uri pptUri = Uri.parse("file://Internal storage/Download/sample-ppt.ppt");
//
//        File NewFileCreated = new File("//Internal storage/Download/sample-ppt.ppt");
//        File NewFileCreatednew = new File("//Internal storage/Download/sample-ppt1.ppt");

        FileInputStream fos = null;
        try {
            int len = 0;
            fos = ctx.openFileInput(FILENAME);
            len = fos.read();
            Log.e("Tag len ", String.valueOf(len));
//            while (len  != -1) {
//                os.write(b, 0, length);
//            }
//
//            is.close();
//            os.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("Tag Dowload ", ctx.getFilesDir().toString());
    }

    public static void sendLink(Activity activity, String subject, String msg, String msgHtml) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, msg);
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        sharingIntent.putExtra(Intent.EXTRA_HTML_TEXT, msgHtml);
        if (sharingIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(sharingIntent);
        }
    }

    public static void makeLinks(TextView textView, String link, ClickableSpan clickableSpan) {
        SpannableString spannableString = new SpannableString(textView.getText());

        int startIndexOfLink = textView.getText().toString().indexOf(link);
        spannableString.setSpan(clickableSpan, startIndexOfLink, startIndexOfLink + link.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(spannableString, TextView.BufferType.SPANNABLE);
    }

//    public static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
//                                                                           final int maxLine, final String spanableText, final boolean viewMore) {
//        String str = strSpanned.toString();
//        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);
//
//        if (str.contains(spanableText)) {
//            ssb.setSpan(new MySpannable(false) {
//                @Override
//                public void onClick(View widget) {
//                    if (viewMore) {
//                        tv.setLayoutParams(tv.getLayoutParams());
//                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
//                        tv.invalidate();
//                        makeTextViewResizable(tv, -1, " Read Less", false);
//                    } else {
//                        tv.setLayoutParams(tv.getLayoutParams());
//                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
//                        tv.invalidate();
//                        makeTextViewResizable(tv, 5, "...Read More", true);
//                    }
//                }
//            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);
//        }
//        return ssb;
//    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static boolean DataNotValid(Context context, EditText view) {
        view.setError(context.getResources().getString(R.string.thisfieldrequired));
        view.requestFocus();
        return false;
    }

    public static boolean DataNotValid(EditText view, int type) {
        if (type == 1) view.setError("This Email Id is invalid");
        else if (type == 2) view.setError("This Number is invalid");
        else if (type == 3) view.setError("Password must contain at least 8 characters");
        else if (type == 4) view.setError("Confirm password did not match");
        else if (type == 5) view.setError("Pincode must contain 6 characters");
        view.requestFocus();
        return false;
    }


    public static String GetText(EditText text) {
        return text.getText().toString().trim();
    }


    public static boolean isConnected(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
        if (ni != null && ni.isAvailable() && ni.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public static ArrayList<UrlObject> speedlist() {
        ArrayList<UrlObject> sppedlist = new ArrayList<>();
        UrlObject urlObject = new UrlObject();
        urlObject.setTitle("0.25x");
        sppedlist.add(urlObject);
        UrlObject urlObject2 = new UrlObject();
        urlObject2.setTitle("0.5x");
        sppedlist.add(urlObject2);
        UrlObject urlObject3 = new UrlObject();
        urlObject3.setTitle("0.75x");
        sppedlist.add(urlObject3);
        UrlObject urlObject4 = new UrlObject();
        urlObject4.setTitle("Normal");
        sppedlist.add(urlObject4);
        UrlObject urlObject5 = new UrlObject();
        urlObject5.setTitle("1.25x");
        sppedlist.add(urlObject5);
        UrlObject urlObject6 = new UrlObject();
        urlObject6.setTitle("1.5x");
        sppedlist.add(urlObject6);
        UrlObject urlObject7 = new UrlObject();
        urlObject7.setTitle("1.75x");
        sppedlist.add(urlObject7);
        UrlObject urlObject8 = new UrlObject();
        urlObject8.setTitle("2x");
        sppedlist.add(urlObject8);
        UrlObject urlObject9 = new UrlObject();
        urlObject9.setTitle("2.25x");
        sppedlist.add(urlObject9);
        UrlObject urlObject10 = new UrlObject();
        urlObject10.setTitle("2.5x");
        sppedlist.add(urlObject10);
        UrlObject urlObject11 = new UrlObject();
        urlObject11.setTitle("2.75x");
        sppedlist.add(urlObject11);
        UrlObject urlObject12 = new UrlObject();
        urlObject12.setTitle("3x");
        sppedlist.add(urlObject12);

        return sppedlist;
    }

    public static void getCourseMaintanaceDialog(final Activity ctx, String title, String msg) {
        DialogUtils.makeSingleButtonDialog(ctx, title, msg,
                ctx.getResources().getString(R.string.ok), false, new DialogUtils.onDialogUtilsOkClick() {
                    @Override
                    public void onOKClick() {

                    }
                });
    }

    public static void getMaintanaceDialog(final Activity ctx, String breakFrom, String breakTo) {
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
            }
        }
    }

    public static boolean isEMIPayImmediate(final Activity ctx, String validTo) {
        boolean isAfter = false;
        if (!TextUtils.isEmpty(validTo)) {
            // milliseconds
            long toDateValue = Long.parseLong(validTo) * 1000;
            // Creating date format
            java.text.DateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS Z");
            Date toDate = new Date(toDateValue);
            String toDt = sdf.format(toDate);
            Date currentDate = Calendar.getInstance().getTime();
            String currentDt = sdf.format(currentDate);

            if (toDate.before(currentDate)) {
                isAfter = true;
                //Toast.makeText(ctx, "Date specified [" + toDt + "] is before today [" + currentDt + "]", Toast.LENGTH_SHORT).show();
            } else {
                isAfter = false;
                //Toast.makeText(ctx, "Date specified [" + toDt + "] is NOT before today [" + currentDt + "]", Toast.LENGTH_SHORT).show();
            }
        } else {
            isAfter = false;
        }
        return isAfter;
    }

    public static void getVersionUpdateDialog(final Activity ctx, String androidType) {
        boolean isHard;
        if (androidType.equalsIgnoreCase("0")) {
            isvisible = "1";
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
                }
                , new DialogUtils.onDialogUtilsCancelClick() {
                    @Override
                    public void onCancelClick() {
                        if (isHard) {
                            Intent i = new Intent(ctx, SplashScreen.class);
                            i.putExtra("EXIT", true);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            ctx.startActivity(i);
                            ctx.finish();
                        }
                    }
                });
    }

    public static boolean isLinkedInInstalled(Activity activity) {
        PackageManager pm = activity.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo("com.linkedin.android", PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public static void GoToOtpVerificationActivity(Activity activity, String otp, int type, String FragType, boolean resetPass, boolean isChangePass, boolean isphone) {
        Intent intent = new Intent(activity, LoginCatActivity.class);
        intent.putExtra(Const.OTP, otp);
        // 1 is for registration
        // 2 is for change password after mobile verification
        // 3 if to change the mobile number after verfication
        intent.putExtra(Const.TYPE, type);
        intent.putExtra(Const.RESET_PASS, resetPass);
        intent.putExtra(Const.IS_CHANGE_PASS, isChangePass);
        intent.putExtra("isphone", isphone);
        intent.putExtra(Const.FRAG_TYPE, FragType);
        Helper.gotoActivity(intent, activity);
    }

    public static void GoToRegistrationActivity(Activity activity, String FragType, String otp, boolean isChangePass) {
        Intent intent = new Intent(activity, LoginCatActivity.class);
        intent.putExtra(Const.OTP, otp);
        intent.putExtra(Const.FRAG_TYPE, FragType);
        intent.putExtra(Const.IS_CHANGE_PASS, isChangePass);
        Helper.gotoActivity(intent, activity);
    }

    public static void GoToOtpVerificationActivity1(Activity activity, String otp, int type, String FragType, boolean resetPass) {
        Intent intent = new Intent(activity, LoginCatActivity.class);
        intent.putExtra(Const.OTP, otp);
        // 1 is for registration
        // 2 is for change password after mobile verification
        // 3 if to change the mobile number after verfication
        intent.putExtra(Const.TYPE, type);
        intent.putExtra(Const.RESET_PASS, resetPass);
        intent.putExtra(Const.FRAG_TYPE, FragType);
        Helper.gotoActivity(intent, activity);
    }

    public static void ShowKeyboard(Context ctx) {
        InputMethodManager inputMethodManager = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void HideKeyboard(Activity ctx) {
        {
            InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Activity.INPUT_METHOD_SERVICE);
            //Find the currently focused view, so we can grab the correct window token from it.
            View view = ctx.getCurrentFocus();
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = new View(ctx);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {

        }
    }

    public static void GoToChangePasswordActivity(Activity activity, String otp, String FragType, boolean resetPass, boolean isChangePass, boolean isphone) {
        Intent intent = new Intent(activity, LoginCatActivity.class);
        intent.putExtra(Const.OTP, otp);
        intent.putExtra(Const.RESET_PASS, resetPass);
        intent.putExtra(Const.FRAG_TYPE, FragType);
        intent.putExtra(Const.IS_CHANGE_PASS, isChangePass);
        intent.putExtra("isphone", isphone);
        Helper.gotoActivity(intent, activity);
    }



    public static void GuestSignOutUser(Context context) {
        ClearUserData(context);
        if(SharedPreference.getInstance().getString("is_otp_login")!=null && SharedPreference.getInstance().getString("is_otp_login").equals("0"))
        {

            Intent intent = new Intent(context, SignInActivity.class);
            intent.putExtra(Const.TYPE, Const.SIGNIN);
            intent.putExtra(Const.OPEN_WITH, Const.GUEST_OPEN);
            intent.putExtra(Const.SOCIAL_TYPE, Const.SOCIAL_TYPE_GMAIL);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            ((Activity) context).finish();

        }
        else
        {
            Intent intent = new Intent(context, LoginWithOtp.class);
            intent.putExtra(Const.TYPE, Const.SIGNIN);
            intent.putExtra(Const.OPEN_WITH, Const.GUEST_OPEN);
            intent.putExtra(Const.SOCIAL_TYPE, Const.SOCIAL_TYPE_GMAIL);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            ((Activity) context).finish();
        }

    }

    public static void SignOutUser(Context context) {
        /*ClearUserData(context);
        Intent intent = new Intent(context, SplashScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        ((Activity) context).finish();*/

        isvisible = "0";
        ClearUserData(context);
        Helper.createShortcuts(context);

        if(SharedPreference.getInstance().getString("is_otp_login")!=null && SharedPreference.getInstance().getString("is_otp_login").equals("0"))
        {
            Intent intent = new Intent(context, SignInActivity.class);
            intent.putExtra(Const.TYPE, Const.SIGNIN);
            intent.putExtra(Const.OPEN_WITH, Const.GUEST_OPEN);
            intent.putExtra(Const.SOCIAL_TYPE, Const.SOCIAL_TYPE_GMAIL);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            ((Activity) context).finish();
        }
        else
        {
            Intent intent = new Intent(context, LoginWithOtp.class);
            intent.putExtra(Const.TYPE, Const.SIGNIN);
            intent.putExtra(Const.OPEN_WITH, Const.GUEST_OPEN);
            intent.putExtra(Const.SOCIAL_TYPE, Const.SOCIAL_TYPE_GMAIL);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            ((Activity) context).finish();
        }




       /* if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {
                Intent intent = new Intent(Intent.ACTION_VIEW, null, HomeActivity.this, LiveClassActivity.class);
                intent.putExtra("liveclass",true);
                ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
                ShortcutInfo shortcut = null;
                shortcut = new ShortcutInfo.Builder(this, "Classes")
                        .setShortLabel("Live Class")
                        .setIcon(Icon.createWithResource(this, R.drawable.live_classes))
                        .setIntent(intent)
                        .build();
                shortcutManager.setDynamicShortcuts(Arrays.asList(shortcut));
            }
            ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
            shortcutManager.removeAllDynamicShortcuts();
        }*/

    }

    public static void ClearUserData(Context context) {
        SharedPreference.getInstance().putInt(Const.LANGUAGE, Const.ENGLISH_CODE);
        SharedPreference.getInstance().putString(Const.APP_LANGUAGE, Const.ENGLISH);
        CustomContextWrapper.wrap(context, Const.ENGLISH);
        Helper.changeLang(SharedPreference.getInstance().getString(Const.APP_LANGUAGE), context);
        MakeMyExam.FeedData = "";
        MakeMyExam.main_cat = "";
        MakeMyExam.page = "1";
        SharedPreference sharedPreference = SharedPreference.getInstance();
        sharedPreference.ClearLoggedInUser();
        sharedPreference.remove(Const.MASTER_FEED_HIT_RESPONSE);
        sharedPreference.remove(Const.MASTER_REGISTRATION_HIT_RESPONSE);
        sharedPreference.remove(Const.FEED_PREFERENCE);
        sharedPreference.remove(Const.JWT);
        sharedPreference.remove(Const.IS_COUPON_AVAILABLE);
        sharedPreference.remove(Const.SERVER_TIME);
        sharedPreference.remove("key");
        sharedPreference.putString("key", "");
        sharedPreference.putString("appversion", "");
        sharedPreference.putLong("time", 0);
        sharedPreference.putInt(Const.FEED_COUNT, 0);

        sharedPreference.putString("course_detail", "");
        sharedPreference.putBoolean("is_showcase", false);
        sharedPreference.putString("prefence", "");
        sharedPreference.remove(Const.MODERATOR_SELECTED_STREAM);
        sharedPreference.putBoolean(Const.IS_USER_LOGGED_IN, false);
        sharedPreference.putBoolean(Const.IS_USER_REGISTRATION_DONE, false);
        sharedPreference.putBoolean(Const.IS_NOTIFICATION_BLOCKED, false);
        DbAdapter.getInstance(context).deleteAll(DbAdapter.TABLE_NAME_COLORCODE);
        UtkashRoom myDBClass = UtkashRoom.getAppDatabase(context);
        myDBClass.getTestDao().deletedata();
        myDBClass.gethtmllink().deletedata();
        myDBClass.getAddress().deletedata();
        myDBClass.getFeedDao().deletedata();
        is_track_selector_auto = "0";

        if (AudioPlayerService.player != null) {
            if (AudioPlayerService.type.equalsIgnoreCase("youtube")) {
                myDBClass.getyoutubedata().updateTime(AudioPlayerService.player.getCurrentPosition(), AudioPlayerService.videoid, MakeMyExam.userId, "1");
            } else {
                myDBClass.getaudiodao().update_audio_currentpos(AudioPlayerService.videoid, MakeMyExam.userId, AudioPlayerService.player.getCurrentPosition());
            }

            if (AudioPlayerService.player != null) {
                AudioPlayerService.player.release();
                AudioPlayerService.player = null;
            }
        }

        if (AudioPlayerService.isAudioPlaying) {
            Intent audioPlayerIntent = new Intent(context, AudioPlayerService.class);
            audioPlayerIntent.setAction("Stop_Service");
            Util.startForegroundService(context, audioPlayerIntent);
            AudioPlayerService.video_currentpos = 0L;
        }


        if (TestService.is_uploading) {
            TestService.shouldStop = true;
        }


        if (VideoDownloadService.isServiceRunning) {
            try {
                VideoDownloadService.action = VideoDownloadService.CANCEL;
                Log.d("prince", "" + VideoDownloadService.video_id);
                myDBClass.getvideoDownloadao().delete_viavideoid(VideoDownloadService.video_id, MakeMyExam.userId);
                MakeMyExam.userId = "0";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static boolean isValidUrl(String linkToCheck) {
        Pattern urlPattern = Pattern
                .compile("^((https?:\\/\\/(www)?)|(www)).+(\\..{2,4})(\\/)?.+$");
        Matcher urlMatcher = urlPattern.matcher(linkToCheck);
        return urlMatcher.matches();
    }

    public static void clearappdata(Context context) {
        UtkashRoom myDBClass = UtkashRoom.getAppDatabase(context);
        myDBClass.getMasterAllCatDao().deletedata();
        myDBClass.getMastercatDao().deletedata();
        myDBClass.getcoursetypemaster().deletedata();
        myDBClass.getaudiodao().deletedata();
        myDBClass.getMyCourseDao().deletedata();
        myDBClass.getuserhistorydao().deletedata();
        myDBClass.getvideoDao().deletedata();
        myDBClass.getyoutubedata().deletedata();
        myDBClass.getLaunguages().deletedata();
        myDBClass.getCoursedata().deletedata();
        myDBClass.getCourseDetaildata().deletedata();
        myDBClass.getHomeApiStatusdata().deletedata();
        myDBClass.getpigibag().deletedata();
        myDBClass.getapidao().deletedata();
        myDBClass.getuserwisecourse().deletedata();
        myDBClass.getAddress().deletedata();
        myDBClass.getFeedDao().deletedata();
        //  myDBClass.getvideoDownloadao().deletedata();
        //UtkashRoom.destroyInstance();
    }

    public static void clearAppData(Context context) {
        try {
            // clearing app data
            if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
                ((ActivityManager) context.getSystemService(ACTIVITY_SERVICE)).clearApplicationUserData(); // note: it has a return value!
            } else {
                String packageName = context.getApplicationContext().getPackageName();
                Runtime runtime = Runtime.getRuntime();
                runtime.exec("pm clear " + packageName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteFile(File file) {
        boolean deletedAll = true;
        if (file != null) {
            if (file.isDirectory()) {
                String[] children = file.list();
                for (int i = 0; i < children.length; i++) {
                    deletedAll = deleteFile(new File(file, children[i])) && deletedAll;
                }
            } else {
                deletedAll = file.delete();
            }
        }

        return deletedAll;
    }

    public static String getCurrencySymbol() {
        Locale defaultLocale = Locale.getDefault();

        Currency currency = Currency.
                getInstance(new Locale(SharedPreference.getInstance().getString(Const.LANGUAGE)/*, SharedPreference.getInstance().getLoggedInUser().getC_code()*/));
        System.out.println("Currency Code: " + currency.getCurrencyCode());
        System.out.println("Symbol: " + currency.getSymbol());
        System.out.println("Default Fraction Digits: " + currency.getDefaultFractionDigits());
        return currency.getSymbol();
    }

    public static void closeKeyboard(Activity cnx) {

        InputMethodManager imm = (InputMethodManager) cnx.getSystemService(Context.INPUT_METHOD_SERVICE);
        try {
            if (imm.isAcceptingText() || imm.isActive())
                imm.hideSoftInputFromWindow(cnx.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void CaptializeFirstLetter(final EditText ET) {

        ET.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable et) {
                String s = et.toString();
                if ((!s.equals(s.toUpperCase()) && s.length() == 1)) {
                    s = s.toUpperCase();
                    ET.setText(s);
                    ET.setSelection(s.length());
                }
            }
        });


    }

    public static void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    public static void gotoActivityWithBundle(Activity context, Class<?> className, Bundle bundle) {
        Intent intent = new Intent(context, className);
        intent.putExtras(bundle);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }


    public static void gotoActivity(Activity context, Class<?> className, String type) {
        Intent intent = new Intent(context, className);
        intent.putExtra(Const.FRAG_TYPE, Const.CREATE_TEST_FRAG_ONE);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    public static void gotoActivity(Intent intent, Activity context) {
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    public static void gotoActivity_finish(Intent intent, Activity context) {
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        context.finish();
    }

    public static void gotoActivity(Activity context, Class<?> className) {
        Intent intent = new Intent(context, className);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    public static void gotoActivity(Activity context, Class<?> className, int notification_code) {
        Intent intent = new Intent(context, className);
        intent.putExtra(Const.NOTIFICATION_CODE, notification_code);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    public static void gotoActivity_withour_intent(Activity context, Class<?> className) {
        Intent intent = new Intent(context, className);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    public static String getHtmlUpdatedDatas(String value) {
        String base64version;
        if (!TextUtils.isEmpty(value)) {
            base64version = Base64.encodeToString(value.trim().getBytes(), Base64.DEFAULT);
        } else {
            base64version = "";
        }
        return base64version;
    }

    public static void showWebDatas(Activity activity, String data, WebView webView) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Code for WebView goes here
                webView.loadData(data, "text/html; charset=UTF-8", "base64");
            }
        });
    }

    public static String getHtmlUpdatedData(String value) {
        String base64version = value;
        return base64version;
    }

    public static void showWebData(Activity activity, String data, WebView webView) {
        String dataFinal = String.valueOf(Html.fromHtml(Html.fromHtml(data).toString()));
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webView.loadData(dataFinal, "text/html", "UTF-8");
            }
        });
    }

    public static void showToast(Context activity, String msg, int visibility) {
        if (visibility == 1) {
            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
        }
    }

    public static void showInternetToast(Context activity) {
        if (!(activity instanceof DownloadVideoPlayer) && !(activity instanceof DownloadActivity)) {
            Toast.makeText(activity, activity.getResources().getString(R.string.internet_error_message), Toast.LENGTH_SHORT).show();
        }
    }

    public static void buildAlertMessageNoGps(final Activity activity) {
        DialogUtils.makeDialog(activity, activity.getResources().getString(R.string.app_name), activity.getResources().getString(R.string.your_gps_seems_disabled),
                activity.getResources().getString(android.R.string.yes), activity.getResources().getString(android.R.string.no), false, new DialogUtils.onDialogUtilsOkClick() {
                    @Override
                    public void onOKClick() {
                        activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        SharedPreference.getInstance().putBoolean(Const.LOCATION, true);
                    }
                }, new DialogUtils.onDialogUtilsCancelClick() {
                    @Override
                    public void onCancelClick() {

                    }
                });

        /*final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(activity);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        activity.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        SharedPreference.getInstance().putBoolean(Const.LOCATION, true);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final android.support.v7.app.AlertDialog alert = builder.create();
        alert.show();
        TextView tv_message = (TextView) alert.findViewById(android.R.id.message);*/

    }

    public static SpannableString typeface(Typeface typeface, CharSequence string) {
        SpannableString s = new SpannableString(string);
        s.setSpan(new TypefaceSpan(String.valueOf(typeface)), 0, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return s;
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                SharedPreference.getInstance().putBoolean(Const.LOCATION, false);
                return false;
            }
            SharedPreference.getInstance().putBoolean(Const.LOCATION, true);
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            SharedPreference.getInstance().putBoolean(Const.LOCATION, true);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    public static String getLocalIpAddress() {
        /*try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return "";*/
        return TextUtils.isEmpty(SharedPreference.getInstance().getString(Const.IP_ADDRESS)) ? "N/A" : SharedPreference.getInstance().getString(Const.IP_ADDRESS);
    }


    public static void GoToLiveVideoActivity(String chatnode, Activity activity, String Url, String islive, String vid, String title, String is_audio, String thubnail, String islocked, String courseid, String pos, String parentid, String tileid, String tiletype) {
        Intent intent = new Intent(activity, CustomMediaPlayer.class);
        intent.putExtra(Const.VIDEO_LINK, Url);
        intent.putExtra("Chat_node", chatnode);
        intent.putExtra(Const.VIDEO_LIVE, islive);
        intent.putExtra("video_id", vid);
        intent.putExtra("video_name", title);
        intent.putExtra("isaudio", is_audio);
        intent.putExtra("thumbnail", thubnail);
        intent.putExtra("islocked", islocked);
        intent.putExtra("courseid", courseid);
        intent.putExtra("pos", pos);
        intent.putExtra("parentid", parentid);
        intent.putExtra("tileid", tileid);
        intent.putExtra("tiletype", tiletype);
        Helper.gotoActivity(intent, activity);
    }


    public static void GoToJWVideoActivity(Activity activity, String Url) {
        Intent intent = new Intent(activity, JWVideoPlayer.class);
        intent.putExtra(Const.VIDEO_LINK, Url);
        Helper.gotoActivity(intent, activity);
    }


    public static void GoToYoutubeExoActivity(Activity activity, String Url, Video video) {
        /*Intent intent = new Intent(activity, YoutubeExoPlayerActivity.class);// this is for exoplayer
        intent.putExtra(Const.VIDEO_LINK, Url);
        intent.putExtra(Const.VIDEO, video);
        Helper.gotoActivity(intent,activity);*/
    }


    public static String getCurrentAddress(Location location, Activity activity) {
        // Getting address from latitude & longitude //
        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
        String add = "";
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            // System.out.println(addresses);
            if (addresses.isEmpty()) {
                add = "";
                return add;
            } else {
                if (addresses.size() > 0) {
                    String address = addresses.get(0).getAddressLine(1);
                    String feature = addresses.get(0).getFeatureName();
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String zip = addresses.get(0).getPostalCode();
                    add = feature + " " + address + " ,  " + city + " ,  " + state + " ,  " + country + " ,  " + zip;
                    return add;
                }
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            add = "";
            return add;
        }
        return add;
    }


    public static boolean isAppRunning(final Context context) {
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
        if (procInfos != null) {
            for (final ActivityManager.RunningAppProcessInfo processInfo : procInfos) {
                if (processInfo.processName.equals(context.getPackageName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getScreenResolution(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        return "{" + width + "," + height + "}";
    }

    public static void changeLang(String lang, Context ctx) {
        if (lang.equalsIgnoreCase(""))
            return;

        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);

        //android.content.res.Configuration config = new android.content.res.Configuration();
        Configuration config = new Configuration(ctx.getResources().getConfiguration());
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            config.setLocale(myLocale);
            ctx.createConfigurationContext(config);
        } else {
            config.locale = myLocale;
            ctx.getResources().updateConfiguration(config, ctx.getResources().getDisplayMetrics());
        }

        SharedPreference.getInstance().putString(Const.APP_LANGUAGE, lang);
        if (lang.equals(Const.ENGLISH))
            SharedPreference.getInstance().putInt(Const.LANGUAGE, Const.ENGLISH_CODE);
        else
            SharedPreference.getInstance().putInt(Const.LANGUAGE, Const.HINDI_CODE);
    }

    public static String calculatePriceBasedOnCurrency(String price) {
        String priceValue = "";
        priceValue = !SharedPreference.getInstance().getBoolean(Const.CURRENCY_INDIAN) ? String.format("%.2f", Integer.parseInt(price) * 0.015239) : price;
        return priceValue;
    }

    public static int CheckMyNumber(int arg) {


        boolean even = false;
        boolean prime = true;
        int myNumber = arg;

        if (myNumber % 2 == 0) {
            even = true;
            prime = false;
        } else {
            for (int i = 3; i * i <= myNumber; i += 2) {
                if (myNumber % i == 0) {
                    prime = false;
                }
            }
        }

        if (even) {
            return 1;
        } else {
            if (prime) {
                return 3;
            } else {
                return 2;
            }
        }
    }

    public static String getAppUrl(Context ctx) {
        final String appPackageName = ctx.getPackageName(); // getPackageName() from Context or Activity object
        final String appUrl = "https://play.google.com/store/apps/details?id=" + appPackageName;
        //return "https://play.google.com/store/apps/details?id=com.utkarshnew.android";
        return appUrl;
    }

    public static void openGooglePlayStore(Context ctx) {
        Intent playStoreIntent = new Intent(Intent.ACTION_VIEW);
        playStoreIntent.setData(Uri.parse(Helper.getAppUrl(ctx)));
        ctx.startActivity(playStoreIntent);
    }

    public static void aDialogOnPermissionDenied(final Activity mContext) {
        DialogUtils.makeSingleButtonDialog(mContext, mContext.getResources().getString(R.string.alert), mContext.getResources().getString(R.string.reGrantPermissionMsg),
                mContext.getResources().getString(android.R.string.yes), false, new DialogUtils.onDialogUtilsOkClick() {
                    @Override
                    public void onOKClick() {
                        Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
                        Intent settingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri);
                        settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        mContext.startActivity(settingsIntent);
                    }
                });

        /*AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle(mContext.getResources().getString(R.string.alert));
        alertDialogBuilder.setMessage(mContext.getResources().getString(R.string.reGrantPermissionMsg));
        alertDialogBuilder.setPositiveButton(mContext.getResources().getString(R.string.action_settings),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
                        Intent settingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri);
                        settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        mContext.startActivity(settingsIntent);
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        // show it
        alertDialog.show();*/
    }


    public static void showFiles(File filepath, Activity activity, String type, boolean finalFlag) {
        if (activity != null) {
            switch (type) {
                case Const.PDF:
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri uri = UtkarshFileProvider.getUriForFile(activity, "com.utkarshnew.android.Utils.UtkarshFileProvider", filepath);
                    intent.setDataAndType(uri, "application/pdf");
                    try {
                        Helper.gotoActivity(intent, activity);
                    } catch (ActivityNotFoundException e) {
                        // Instruct the user to install a PDF reader here, or something
                        Toast.makeText(activity, "Unable to open the file. No application available", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case Const.DOC:
                    MimeTypeMap myMime = MimeTypeMap.getSingleton();
                    Intent newIntent = new Intent(Intent.ACTION_VIEW);
                    String mimeType = myMime.getMimeTypeFromExtension(filepath.getName().substring(filepath.getName().lastIndexOf(".") + 1));
                    newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    newIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri uris = UtkarshFileProvider.getUriForFile(activity, "com.utkarshnew.android.Utils.UtkarshFileProvider", filepath);
                    newIntent.setDataAndType(uris, mimeType);
                    try {
                        activity.startActivity(newIntent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(activity, "Unable to open the file. No application available", Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
    }

    public static String gaetFormatedDate(long time) {
        String date;
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        date = DateFormat.format("dd-MMM-yyyy hh:mm a", cal).toString();
        return date;
    }

    public static String formatSeconds(int timeInSeconds) {
        int hours = timeInSeconds / 3600;
        int secondsLeft = timeInSeconds - hours * 3600;
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;

        String formattedTime = "";
        if (hours < 10)
            formattedTime += "0";
        formattedTime += hours + ":";
        if (minutes < 10)
            formattedTime += "0";
        formattedTime += minutes + ":";

        if (seconds < 10)
            formattedTime += "0";
        formattedTime += seconds;

        return formattedTime;
    }

    public static int getValueInDP(Context context, int value) {
        int dpValue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
        return dpValue;
    }

    public static void GoToJWVideo_Params(Activity activity, String Url, String video_id, String title, Long current_pos, String course_id) {
        Intent intent = new Intent(activity, JWVideoPlayer.class);
        intent.putExtra(Const.VIDEO_LINK, Url);
        intent.putExtra("video_id", video_id);
        intent.putExtra("current_pos", current_pos);
        intent.putExtra("title", title);
        intent.putExtra("course_id", course_id);
        Helper.gotoActivity(intent, activity);
    }


    public static void GoToJWVideo_Params_new(Activity activity, String Url, String video_id, String title, Long current_pos, String course_id, String tileid, String tile_type) {
        Intent intent = new Intent(activity, JWVideoPlayer.class);
        intent.putExtra(Const.VIDEO_LINK, Url);
        intent.putExtra("video_id", video_id);
        intent.putExtra("current_pos", current_pos);
        intent.putExtra("title", title);
        intent.putExtra("course_id", course_id);
        intent.putExtra("tile_id", tileid);
        intent.putExtra("tile_type", tile_type);
        Helper.gotoActivity(intent, activity);
    }

    public static void GoToJWVideo_Params_newarray(Activity activity, String Url, String video_id, String title, Long current_pos, String course_id, String tileid, String tile_type, ArrayList<UrlObject> bitrate_urls, long expire_time) {
        Intent intent = new Intent(activity, JWVideoPlayer.class);
        if (bitrate_urls != null && bitrate_urls.size() > 0) {
            String url = new Gson().toJson(bitrate_urls);
            intent.putExtra("url_object", url);
        } else {
            intent.putExtra("url_object", "");

        }

        intent.putExtra(Const.VIDEO_LINK, Url);
        intent.putExtra("video_id", video_id);
        intent.putExtra("current_pos", current_pos);
        intent.putExtra("title", title);
        intent.putExtra("course_id", course_id);
        intent.putExtra("tile_id", tileid);
        intent.putExtra("tile_type", tile_type);
        intent.putExtra("expire_time", expire_time);

        Helper.gotoActivity(intent, activity);
    }


    public static void createShortcuts(Context context) {
        ShortcutManager shortcutManager = null;
        ShortcutInfo shortcutLiveClassActivity = null, shortcutLiveTestActivity = null, shortcutDownloadActivity = null, shortcutCourseActivity = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {
            shortcutManager = context.getSystemService(ShortcutManager.class);
        }

        Intent liveclassintent, livetestintent, mycourseintent, downloadintent;

        if (!(SharedPreference.getInstance().getLoggedInUser() != null && SharedPreference.getInstance().getLoggedInUser().getId() != null)) {
            if(SharedPreference.getInstance().getString("is_otp_login")!=null && SharedPreference.getInstance().getString("is_otp_login").equals("0"))
            {
                liveclassintent = new Intent(Intent.ACTION_MAIN, Uri.EMPTY, context, SignInActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                liveclassintent.putExtra(Const.TYPE, Const.SIGNIN);
                liveclassintent.putExtra(Const.OPEN_WITH, Const.USER_OPEN);
                liveclassintent.putExtra(Const.SOCIAL_TYPE, Const.SOCIAL_TYPE_GMAIL);
            }
            else
            {
                liveclassintent = new Intent(Intent.ACTION_MAIN, Uri.EMPTY, context, LoginWithOtp.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                liveclassintent.putExtra(Const.TYPE, Const.SIGNIN);
                liveclassintent.putExtra(Const.OPEN_WITH, Const.USER_OPEN);
                liveclassintent.putExtra(Const.SOCIAL_TYPE, Const.SOCIAL_TYPE_GMAIL);
            }

        } else {
            liveclassintent = new Intent(Intent.ACTION_MAIN, Uri.EMPTY, context, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        liveclassintent.putExtra(Const.NOTIFICATION_CODE, 1001000);


        if (!(SharedPreference.getInstance().getLoggedInUser() != null && SharedPreference.getInstance().getLoggedInUser().getId() != null)) {
            if(SharedPreference.getInstance().getString("is_otp_login")!=null && SharedPreference.getInstance().getString("is_otp_login").equals("0"))
            {
                livetestintent = new Intent(Intent.ACTION_MAIN, Uri.EMPTY, context, SignInActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                livetestintent.putExtra(Const.TYPE, Const.SIGNIN);
                livetestintent.putExtra(Const.OPEN_WITH, Const.USER_OPEN);
                livetestintent.putExtra(Const.SOCIAL_TYPE, Const.SOCIAL_TYPE_GMAIL);
            }
            else
            {
                livetestintent = new Intent(Intent.ACTION_MAIN, Uri.EMPTY, context, LoginWithOtp.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                livetestintent.putExtra(Const.TYPE, Const.SIGNIN);
                livetestintent.putExtra(Const.OPEN_WITH, Const.USER_OPEN);
                livetestintent.putExtra(Const.SOCIAL_TYPE, Const.SOCIAL_TYPE_GMAIL);
            }

        } else {
            livetestintent = new Intent(Intent.ACTION_MAIN, Uri.EMPTY, context, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        livetestintent.putExtra(Const.NOTIFICATION_CODE, 1001001);


        if (!(SharedPreference.getInstance().getLoggedInUser() != null && SharedPreference.getInstance().getLoggedInUser().getId() != null)) {
            if(SharedPreference.getInstance().getString("is_otp_login")!=null && SharedPreference.getInstance().getString("is_otp_login").equals("0"))
            {
                mycourseintent = new Intent(Intent.ACTION_MAIN, Uri.EMPTY, context, SignInActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                mycourseintent.putExtra(Const.TYPE, Const.SIGNIN);
                mycourseintent.putExtra(Const.OPEN_WITH, Const.USER_OPEN);
                mycourseintent.putExtra(Const.SOCIAL_TYPE, Const.SOCIAL_TYPE_GMAIL);
            }
            else
            {
                mycourseintent = new Intent(Intent.ACTION_MAIN, Uri.EMPTY, context, LoginWithOtp.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                mycourseintent.putExtra(Const.TYPE, Const.SIGNIN);
                mycourseintent.putExtra(Const.OPEN_WITH, Const.USER_OPEN);
                mycourseintent.putExtra(Const.SOCIAL_TYPE, Const.SOCIAL_TYPE_GMAIL);
            }

        } else {
            mycourseintent = new Intent(Intent.ACTION_MAIN, Uri.EMPTY, context, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        mycourseintent.putExtra(Const.NOTIFICATION_CODE, 1001010);


        if (!(SharedPreference.getInstance().getLoggedInUser() != null && SharedPreference.getInstance().getLoggedInUser().getId() != null)) {
            if(SharedPreference.getInstance().getString("is_otp_login")!=null && SharedPreference.getInstance().getString("is_otp_login").equals("0"))
            {
                downloadintent = new Intent(Intent.ACTION_MAIN, Uri.EMPTY, context, SignInActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                downloadintent.putExtra(Const.TYPE, Const.SIGNIN);
                downloadintent.putExtra(Const.OPEN_WITH, Const.USER_OPEN);
                downloadintent.putExtra(Const.SOCIAL_TYPE, Const.SOCIAL_TYPE_GMAIL);
            }
            else
            {
                downloadintent = new Intent(Intent.ACTION_MAIN, Uri.EMPTY, context, LoginWithOtp.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                downloadintent.putExtra(Const.TYPE, Const.SIGNIN);
                downloadintent.putExtra(Const.OPEN_WITH, Const.USER_OPEN);
                downloadintent.putExtra(Const.SOCIAL_TYPE, Const.SOCIAL_TYPE_GMAIL);
            }

        } else {
            downloadintent = new Intent(Intent.ACTION_MAIN, Uri.EMPTY, context, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        downloadintent.putExtra(Const.NOTIFICATION_CODE, 1001011);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            shortcutLiveClassActivity = new ShortcutInfo.Builder(context, "liveclassid")
                    .setShortLabel("Live Classes")
                    .setLongLabel("Live Classes")
                    .setIcon(Icon.createWithResource(context, R.drawable.live_classes))
                    .setIntent(liveclassintent)
                    .build();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            shortcutLiveTestActivity = new ShortcutInfo.Builder(context, "livetestid")
                    .setShortLabel("Live Test")
                    .setLongLabel("Live Test")
                    .setIcon(Icon.createWithResource(context, R.drawable.live_tests))
                    .setIntent(livetestintent)
                    .build();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            shortcutCourseActivity = new ShortcutInfo.Builder(context, "courseid")
                    .setShortLabel("My Library")
                    .setLongLabel("My Library")
                    .setIcon(Icon.createWithResource(context, R.mipmap.my_library))
                    .setIntent(mycourseintent)
                    .build();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            shortcutDownloadActivity = new ShortcutInfo.Builder(context, "downloadclassid")
                    .setShortLabel("My Downloads")
                    .setLongLabel("My Downloads")
                    .setIcon(Icon.createWithResource(context, R.drawable.download_icon))
                    .setIntent(downloadintent)
                    .build();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            shortcutManager.setDynamicShortcuts(Arrays.asList(shortcutLiveClassActivity, shortcutLiveTestActivity, shortcutCourseActivity, shortcutDownloadActivity));
        }
    }

    public static void GoToJWVideo_Params_newarrayobject(Activity activity, String Url, String video_id, String title, Long current_pos, String course_id, String tileid, String tile_type, ArrayList<UrlObject> bitrate_urls, long expire_time, String objectcookie) {
        Intent intent = new Intent(activity, JWVideoPlayer.class);
        if (bitrate_urls != null && bitrate_urls.size() > 0) {
            String url = new Gson().toJson(bitrate_urls);
            intent.putExtra("url_object", url);
        } else {
            intent.putExtra("url_object", "");

        }

        intent.putExtra(Const.VIDEO_LINK, Url);
        intent.putExtra("video_id", video_id);
        intent.putExtra("current_pos", current_pos);
        intent.putExtra("title", title);
        intent.putExtra("course_id", course_id);
        intent.putExtra("tile_id", tileid);
        intent.putExtra("tile_type", tile_type);
        intent.putExtra("expire_time", expire_time);
        intent.putExtra("objectcookie", objectcookie);

        Helper.gotoActivity(intent, activity);
    }


    public static void GoToJWVideo_Params_newarray(Activity activity, String Url, String video_id, String title, Long current_pos, String course_id, String tileid, String tile_type, ArrayList<UrlObject> bitrate_urls) {
        Intent intent = new Intent(activity, JWVideoPlayer.class);
        if (bitrate_urls != null && bitrate_urls.size() > 0) {
            String url = new Gson().toJson(bitrate_urls);
            intent.putExtra("url_object", url);
        } else {
            intent.putExtra("url_object", "");

        }

        intent.putExtra(Const.VIDEO_LINK, Url);
        intent.putExtra("video_id", video_id);
        intent.putExtra("current_pos", current_pos);
        intent.putExtra("title", title);
        intent.putExtra("course_id", course_id);
        intent.putExtra("tile_id", tileid);
        intent.putExtra("tile_type", tile_type);

        Helper.gotoActivity(intent, activity);
    }


    public static void audio_service_close(Activity activity) {
        try {
            if (activity instanceof LiveClassActivity) {
                if (AudioPlayerService.player != null) {
                    if (AudioPlayerService.type.equalsIgnoreCase("youtube")) {
                        ((LiveClassActivity) activity).myDBClass.getyoutubedata().updateTime(AudioPlayerService.player.getCurrentPosition(), AudioPlayerService.videoid, MakeMyExam.userId, "1");
                    } else {
                        ((LiveClassActivity) activity).myDBClass.getaudiodao().update_audio_currentpos(AudioPlayerService.videoid, MakeMyExam.userId, AudioPlayerService.player.getCurrentPosition());
                    }

                    if (AudioPlayerService.player != null) {
                        AudioPlayerService.player.release();
                        AudioPlayerService.player = null;
                    }
                }
            } else {
                if (AudioPlayerService.player != null) {
                    if (AudioPlayerService.type.equalsIgnoreCase("youtube")) {
                        UtkashRoom.getAppDatabase(activity).getyoutubedata().updateTime(AudioPlayerService.player.getCurrentPosition(), AudioPlayerService.videoid, MakeMyExam.userId, "1");
                    } else {
                        UtkashRoom.getAppDatabase(activity).getaudiodao().update_audio_currentpos(AudioPlayerService.videoid, MakeMyExam.userId, AudioPlayerService.player.getCurrentPosition());
                    }

                    if (AudioPlayerService.player != null) {
                        AudioPlayerService.player.release();
                        AudioPlayerService.player = null;
                    }
                }
            }
            if (AudioPlayerService.isAudioPlaying) {
                Intent audioPlayerIntent = new Intent(activity, AudioPlayerService.class);
                audioPlayerIntent.setAction("Stop_Service");
                Util.startForegroundService(activity, audioPlayerIntent);
                AudioPlayerService.video_currentpos = 0L;
            }
        } catch (Exception e) {

            if (AudioPlayerService.player != null) {
                AudioPlayerService.player.release();
                AudioPlayerService.player = null;
            }
            if (AudioPlayerService.isAudioPlaying) {
                Intent audioPlayerIntent = new Intent(activity, AudioPlayerService.class);
                audioPlayerIntent.setAction("Stop_Service");
                Util.startForegroundService(activity, audioPlayerIntent);
                AudioPlayerService.video_currentpos = 0L;
            }
            e.printStackTrace();
        }
    }


    public static String removeHTML(String htmlString) {
        // Remove HTML tag from java String
        String noHTMLString = htmlString.replaceAll("\\<.*?\\>", "");

// Remove Carriage return from java String
        noHTMLString = noHTMLString.replaceAll("\r", "<br/>");
        noHTMLString = noHTMLString.replaceAll("<([bip])>.*?</\1>", "");
// Remove New line from java string and replace html break
        noHTMLString = noHTMLString.replaceAll("\n", " ");
        noHTMLString = noHTMLString.replaceAll("\"", "&quot;");
        noHTMLString = noHTMLString.replaceAll("<(.*?)\\>", " ");//Removes all items in brackets
        noHTMLString = noHTMLString.replaceAll("<(.*?)\\\n", " ");//Must be undeneath
        noHTMLString = noHTMLString.replaceFirst("(.*?)\\>", " ");
        noHTMLString = noHTMLString.replaceAll("&nbsp;", " ");
        noHTMLString = noHTMLString.replaceAll("&amp;", " ");
        return noHTMLString;
    }

    public static void shareLiveClass(final Activity activity, String maincourseid, String fieldid, String topicid, String tile_type, String tileid, String revertapi, String type, String image, String name, String parentid) {
        final Progress progress = new Progress(activity);
        progress.show();
        String actualurl;
        if (parentid == null || parentid.isEmpty()) {
            actualurl = "maincouseidlive=" + maincourseid
                    + "&fieldid=" + fieldid
                    + "&topicid=" + topicid
                    + "&tile_type=" + tile_type
                    + "&tileid=" + tileid
                    + "&revertapi=" + revertapi
                    + "&type=" + type;
        } else {
            actualurl = "maincouseidlive=" + maincourseid
                    + "&fieldid=" + fieldid
                    + "&parentid=" + parentid
                    + "&topicid=" + topicid
                    + "&tile_type=" + tile_type
                    + "&tileid=" + tileid
                    + "&revertapi=" + revertapi
                    + "&type=" + type;
        }


        FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse(API.BASE_URL_WEB + "?" + "data=" +
                        Base64.encodeToString(actualurl.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT)))

                .setSocialMetaTagParameters(new DynamicLink.SocialMetaTagParameters.Builder()
                        .setImageUrl(Uri.parse(image)).setTitle(name).build())

                .setDynamicLinkDomain("utkarshnew.page.link")
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                .setIosParameters(
                        new DynamicLink.IosParameters.Builder("com.eUtkarsh.ios")
                                .setAppStoreId("1446577039")
                                .build())
                .buildShortDynamicLink()
                .addOnCompleteListener(activity, task -> {
                    progress.dismiss();
                    if (task.isSuccessful()) {
                        Uri shortLink = task.getResult().getShortLink();
                        String msgHtml = String.format(shortLink.toString());
                        sendLink(activity, "Video link", shortLink.toString(),
                                msgHtml);
                    } else {
                        progress.dismiss();
                        Toast.makeText(activity, "Link could not be generated please try again!", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public static void sharejwvideo(final Activity activity, String maincourseid, String fieldid, String topicid, String tile_type, String tileid, String revertapi, String type, String image, String name, String parentid) {
        final Progress progress = new Progress(activity);
        progress.show();
        String actualurl;
        if (parentid == null || parentid.isEmpty()) {
            actualurl = "maincouseidjw=" + maincourseid
                    + "&fieldid=" + fieldid
                    + "&topicid=" + topicid
                    + "&tile_type=" + tile_type
                    + "&tileid=" + tileid
                    + "&revertapi=" + revertapi
                    + "&type=" + type;
        } else {
            actualurl = "maincouseidjw=" + maincourseid
                    + "&fieldid=" + fieldid
                    + "&parentid=" + parentid
                    + "&topicid=" + topicid
                    + "&tile_type=" + tile_type
                    + "&tileid=" + tileid
                    + "&revertapi=" + revertapi
                    + "&type=" + type;
        }


        FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse(API.BASE_URL_WEB + "?" + "data=" +
                        Base64.encodeToString(actualurl.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT)))

                .setSocialMetaTagParameters(new DynamicLink.SocialMetaTagParameters.Builder()
                        .setImageUrl(Uri.parse(image)).setTitle(name).build())

                .setDynamicLinkDomain("utkarshnew.page.link")
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                .setIosParameters(
                        new DynamicLink.IosParameters.Builder("com.eUtkarsh.ios")
                                .setAppStoreId("1446577039")
                                .build())
                .buildShortDynamicLink()
                .addOnCompleteListener(activity, task -> {
                    progress.dismiss();
                    if (task.isSuccessful()) {
                        Uri shortLink = task.getResult().getShortLink();
                        String msgHtml = String.format(shortLink.toString());
                        sendLink(activity, "Video link", shortLink.toString(),
                                msgHtml);
                    } else {
                        progress.dismiss();
                        Toast.makeText(activity, "Link could not be generated please try again!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /*  public static void sharePdf(final Activity activity,String id,String url,String title,boolean isdownload,String filetype) {
          final Progress progress = new Progress(activity);
          progress.show();
          FirebaseDynamicLinks.getInstance().createDynamicLink()
                  .setLink(Uri.parse(API.BASE_URL+"?mainpdfid=" + id
                          +"&url="+ url
                          +"&title="+ title
                          +"&isdownload="+ isdownload
                          +"&filetype="+ filetype
                  ))
                  .setDynamicLinkDomain("utkarshnew.page.link")
                  .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                  .buildShortDynamicLink()
                  .addOnCompleteListener(activity, task -> {
                      progress.dismiss();
                      if (task.isSuccessful()) {
                          Uri shortLink = task.getResult().getShortLink();
                          String msgHtml = String.format("<p>Hey There!!! Please find the Video"
                                  + "<a href=\"%s\">referrer link</a>!</p>", shortLink.toString());
                          sendLink(activity, "Course link", "Hey There!!! Please find the live class with my referrer link: " + shortLink.toString(),
                                  msgHtml);
                      } else {
                          progress.dismiss();
                          Toast.makeText(activity, "Link could not be generated please try again!", Toast.LENGTH_SHORT).show();
                      }
                  });
      }*/
    public static void sharePdf(final Activity activity, String maincourseid, String fieldid, String topicid, String tile_type, String tileid, String revertapi, String type, String image, String name, String parentid) {
        final Progress progress = new Progress(activity);
        progress.show();
        String actualurl;
        if (parentid == null || parentid.isEmpty()) {
            actualurl = "mainpdfid=" + maincourseid
                    + "&fieldid=" + fieldid
                    + "&topicid=" + topicid
                    + "&tile_type=" + tile_type
                    + "&tileid=" + tileid
                    + "&revertapi=" + revertapi
                    + "&type=" + type;
        } else {
            actualurl = "mainpdfid=" + maincourseid
                    + "&fieldid=" + fieldid
                    + "&topicid=" + topicid
                    + "&parentid=" + parentid
                    + "&tile_type=" + tile_type
                    + "&tileid=" + tileid
                    + "&revertapi=" + revertapi
                    + "&type=" + type;
        }


        FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse(API.BASE_URL_WEB + "?" + "data=" +
                        Base64.encodeToString(actualurl.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT)))
                .setSocialMetaTagParameters(new DynamicLink.SocialMetaTagParameters.Builder()
                        .setImageUrl(Uri.parse(image)).setTitle(name).build())
                .setDynamicLinkDomain("utkarshnew.page.link")
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                .setIosParameters(
                        new DynamicLink.IosParameters.Builder("com.eUtkarsh.ios")
                                .setAppStoreId("1446577039")
                                .build())
                .buildShortDynamicLink()
                .addOnCompleteListener(activity, task -> {
                    progress.dismiss();
                    if (task.isSuccessful()) {
                        Uri shortLink = task.getResult().getShortLink();
                        if (type.equalsIgnoreCase("7")) {
                            String msgHtml = String.format(shortLink.toString());
                            sendLink(activity, "Pdf link", shortLink.toString(),
                                    msgHtml);
                        } else {
                            String msgHtml = String.format(shortLink.toString());
                            sendLink(activity, "Concept link", shortLink.toString(),
                                    msgHtml);
                        }

                    } else {
                        progress.dismiss();
                        Toast.makeText(activity, "Link could not be generated please try again!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static String changeAMPM(String date) {
        return date.replace("am", "AM").replace("pm", "PM");
    }


    public static void backButtonClick(Activity activity) {
        hideSoftKeyboard(activity);
        activity.finish();
    }

    public static void SignOutUsernotifi(Context context) {
        ClearUserData(context);
        Intent intent = new Intent(context, SplashScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        ((Activity) context).finish();
        Toast.makeText(((Activity) context), "Hello", Toast.LENGTH_SHORT).show();
    }

    private static class WebViewClickListener implements View.OnLongClickListener {
        WebView web;

        WebViewClickListener(WebView option1_webview) {
            web = option1_webview;
            web.setLongClickable(false);
            web.setHapticFeedbackEnabled(false);
        }

        @Override
        public boolean onLongClick(View v) {
            v.setLongClickable(false);
            v.setHapticFeedbackEnabled(false);
            return true;
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    public static void TestWebHTMLLoad(WebView clickableWebView, String description) {
        try {
            clickableWebView.getSettings().setJavaScriptEnabled(true);
            String data = "";
            clickableWebView.setLongClickable(false);
            clickableWebView.setOnLongClickListener(new WebViewClickListener(clickableWebView));

            if (description.contains("math-tex")) {
                data = getOfflineKatexConfig(description);
            } else if (description.contains("&lt") || description.contains("&gt")) {
                data = String.valueOf(Html.fromHtml(description));
            } else {
                data = description;
            }
            clickableWebView.loadDataWithBaseURL("null", data, "text/html",
                    "UTF-8", "about:blank");
        } catch (Exception e) {
            Log.d("TAG", "TestWebHTMLLoad: " + e.getMessage());
        }
    }

    public static void load(WebView clickableWebView, String description) {
        try {
            clickableWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

            if (description.contains("&lt") || description.contains("&gt")) {
                description = String.valueOf(Html.fromHtml(description));
            }

            String data = getOfflineKatexConfig(description);

            clickableWebView.loadDataWithBaseURL("null", data, "text/html",
                    "UTF-8", "about:blank");

        } catch (Exception e) {
            Log.d("TAG", "TestWebHTMLLoad: " + e.getMessage());
        }
    }

    private static String getOfflineKatexConfig(String formulaString) {
        String offline_config = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <title>Auto-render test</title>\n" +
                "        <link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/katex/katex.min.css\">\n" +
                "        <script type=\"text/javascript\" src=\"file:///android_asset/katex/katex.min.js\"></script>\n" +
                "        <script type=\"text/javascript\" src=\"file:///android_asset/katex/contrib/auto-render.min.js\"></script>\n" +
                " <style type='text/css'>" +
                "body {" +
                "font-size:0px" +
                "color:#000000;" +
                "padding:0px" +
                " }" +
                " </style>" +
                "    </head>\n" +
                "    <body>\n" +
                "        {formula}\n" +
                "        <script>\n" +
                "          renderMathInElement(\n" +
                "              document.body\n" +
                "          );\n" +
                "        </script>\n" +
                "    </body>\n" +
                "</html>";


        return offline_config.replace("{formula}", formulaString);
    }

//    public static void TestWebHTMLLoad(WebView clickableWebView, String description) {
//      try {
//          clickableWebView.getSettings().setJavaScriptEnabled(true);
//          clickableWebView.setLongClickable(false);
//          clickableWebView.setOnLongClickListener(new WebViewClickListener(clickableWebView));
//          clickableWebView.loadDataWithBaseURL("http://bar",
//                  "<script type=\"text/x-mathjax-config\">" +
//                          "  MathJax.Hub.Config({" +
//                          "extensions: [\"tex2jax.js\"],messageStyle:\"none\"," +
//                          "jax: [\"input/TeX\",\"output/HTML-CSS\"]," +
//                          "tex2jax: {inlineMath: [['$','$'],['\\\\(','\\\\)']]}" +
//                          "});" +
//                          "</script>" +
//                          "<script type=\"text/javascript\" async src=\"file:///android_asset/MathJax/MathJax.js?config=TeX-AMS-MML_HTMLorMML\"></script>" +
//                          "" +
//                          "</head>" +
//                          "" +
//                          "<body>" +
//                          description +
//                          "</body>" +
//                          "</html>", "text/html", "utf-8", "");
//      }catch (Exception e){
//          Log.d("TAG", "TestWebHTMLLoad: "+e.getMessage());
//      }
//    }

    public static class MySpannable extends ClickableSpan {

        private boolean isUnderline = false;

        /***
         * Constructor
         */
        public MySpannable(boolean isUnderline) {
            this.isUnderline = isUnderline;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(isUnderline);
            ds.setColor(Color.parseColor("#33A2D9"));
        }

        @Override
        public void onClick(View widget) {

        }
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        try {

            Log.e("TAG", "getRoundedCornerBitmap: " + bitmap);
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);
            final float roundPx = 10;

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

            return output;
        } catch (Exception e) {
            Log.e("TAG", "getRoundedCornerBitmap: " + e);

        }
        return null;
    }

    @Nullable
    public static AlertDialog startCast(Activity context) {
        if (context == null || context.isFinishing() || context.isDestroyed())
            return null;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Screen Mirroring feature supports below mentioned conditions");
        builder.setMessage(R.string.screen_mirror_dialog_msg);
        builder.setPositiveButton("OK", (dialog, which) -> {
            try {
                context.startActivity(new Intent("android.settings.WIFI_DISPLAY_SETTINGS"));
                return;
            } catch (ActivityNotFoundException activitynotfoundexception) {
            }
            try {
                context.startActivity(new Intent("android.settings.CAST_SETTINGS"));
            } catch (Exception e) {
                Toast.makeText(context, "TV not supported", Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        ((TextView) dialog.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
        return dialog;
    }

    public static void activityAnimation(Activity activity) {
        activity.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }


    public static NimbusRetrofitApi getApi() {
        return RetrofitClient.getClient(API.SERVER_URL).create(NimbusRetrofitApi.class);
    }

    public static NimbusRetrofitApi getApiV2() {
        return RetrofitClient.getClientV2().create(NimbusRetrofitApi.class);
    }

    public static NimbusRetrofitApi getRetroApi() {
        return RetrofitClient.getClient(API.MAIN_SERVER_URL).create(NimbusRetrofitApi.class);
    }

    public static String getVideoId(String watchLink) {
        if (watchLink.contains("?")) {
            String[] separated = watchLink.replace("?", "~").split("~");
            String firstUrl = separated[0]; // this will contain "Fruit"
            watchLink = firstUrl;
        }
        watchLink = watchLink.trim();
        String[] parts = watchLink.split("\\s+");
        Log.d("String", parts[0]);
        return watchLink.substring(parts[0].length() - 11);
    }

    public static void showDialog(final Activity activity, String Message) {
        DialogUtils.makeSingleButtonDialog(activity, "", Message,
                activity.getResources().getString(R.string.close), false, new DialogUtils.onDialogUtilsOkClick() {
                    @Override
                    public void onOKClick() {

                    }
                });
    }

    public static Cipher getCipher() throws GeneralSecurityException {

        String iv = AES.encryptPassword(AES.strArrayvector);

        byte[] AesKeyData = AES.encryptPassword(AES.strArrayKey).getBytes();
        byte[] InitializationVectorData = iv.getBytes();
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        SecretKeySpec keySpec = new SecretKeySpec(AesKeyData, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(InitializationVectorData));
        return cipher;
    }

    public static boolean isEmulator() {
        return ((Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.HARDWARE.contains("BlueStack")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.PRODUCT.contains("sdk_google")
                || Build.PRODUCT.contains("google_sdk")
                || Build.PRODUCT.contains("sdk")
                || Build.PRODUCT.contains("sdk_x86")
                || Build.PRODUCT.contains("vbox86p")
                || Build.PRODUCT.contains("emulator")
                || Build.PRODUCT.contains("simulator")
                || Build.BOARD == "QC_Reference_Phone"
                || Build.HOST.startsWith("Build")
                || "google_sdk".equals(Build.PRODUCT));
    }

    public static boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    public static boolean checkPermissionForExternalStorage(Activity activity) {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean requestStoragePermission(Activity activity, int READ_STORAGE_PERMISSION) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        READ_STORAGE_PERMISSION);
            }
        } else {
        }
        return false;
    }

    public static boolean isAppInstalled(Activity activity, String packageName) {
        PackageManager pm = activity.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public static float convertPixelsToDp(int px, Context context) {
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static float convertDpToPixel(int dp, Context context) {
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static String getFirebaseToken() {
        return TextUtils.isEmpty(FirebaseInstanceId.getInstance().getToken()) ? "utkarsh_device" : FirebaseInstanceId.getInstance().getToken();
    }

    public static void setThumbnailImage(Context context, String url, Drawable placeHolder, ImageView imageView) {
        if (url != null)
            Glide.with(context)
                    .load(url.replaceAll(" ", "%20"))
                    .apply(new RequestOptions().placeholder(placeHolder).error(placeHolder).diskCacheStrategy(DiskCacheStrategy.DATA))
                    .into(imageView);


    }


    public static boolean isStringValid(String string) {
        if (string != null && !TextUtils.isEmpty(string) && !string.equalsIgnoreCase(""))
            return true;
        else return false;
    }

    public static ArrayList<CartItems> getMyCourseCart(Activity activity) {
        Gson gson = new Gson();
        ArrayList<CartItems> myCartList = new ArrayList<>();
        ArrayList<CartItems> myCartListNew = new ArrayList<>();
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Const.SHARED_PREFS.NAME, MODE_PRIVATE);
        String json = sharedPreferences.getString(Const.SHARED_PREFS.CART_LIST, "");
        myCartList = gson.fromJson(json, new TypeToken<List<CartItems>>() {
        }.getType());
        if (myCartList != null && myCartList.size() > 0) {
            for (CartItems item : myCartList) {
                if (item.getUser_id().equalsIgnoreCase(SharedPreference.getInstance().getLoggedInUser().getId())) {
                    myCartListNew.add(item);
                }
            }
        }
        //return myCartList==null?new ArrayList<CartItems>():myCartList;
        return myCartListNew == null ? new ArrayList<CartItems>() : myCartListNew;
    }

    public static void checkMyCartCourses(Activity activity, Button addToCart, CartItems cartItems) {

        ArrayList<CartItems> myCartList = new ArrayList<>();
        myCartList = getMyCourseCart(activity);

        if (myCartList.size() >= 1) {
            boolean dealAddAlready = false;
            for (CartItems item : myCartList) {
                if (item.getUser_id().equalsIgnoreCase(cartItems.getUser_id()) && item.getCourse_id().equalsIgnoreCase(cartItems.getCourse_id())) {
                    dealAddAlready = true;
                }
            }
            if (dealAddAlready) {
                addToCart.setText(activity.getResources().getString(R.string.remove_from_cart));
            } else {
                addToCart.setText(activity.getResources().getString(R.string.add_to_cart));
            }
        } else {
            addToCart.setText(activity.getResources().getString(R.string.add_to_cart));
        }
    }

    public static void buyNowCourses(Activity activity, SinglestudyModel singleStudy) {
        ArrayList<SinglestudyModel> singlestudyModels = new ArrayList<>();
        singlestudyModels.add(singleStudy);
        Intent courseInvoice = new Intent(activity, CourseActivity.class);
        courseInvoice.putExtra(Const.FRAG_TYPE, Const.COURSE_INVOICE);
        courseInvoice.putExtra(Const.EMI_TYPE, Const.EMI_INSTALLMENT);
        courseInvoice.putExtra(Const.SINGLE_STUDY, singlestudyModels);
        activity.startActivity(courseInvoice);
    }

    public static void buyNowCourses(Activity activity, CourseDetailData courseDetailData) {
        Intent courseInvoice = new Intent(activity, CourseActivity.class);
        courseInvoice.putExtra(Const.FRAG_TYPE, Const.COURSE_INVOICE);
        courseInvoice.putExtra("CourseDetailData", courseDetailData);
        activity.startActivity(courseInvoice);
    }

    public static void addToMyCartCourses(Activity activity, CartItems item, boolean isAdd, boolean isBuyNow, Button addToCart) {
        ArrayList<CartItems> myCartList = new ArrayList<>();
        myCartList = getMyCourseCart(activity);
        boolean removes = false;
        if (isAdd) {
            for (CartItems service : myCartList) {
                if (item.getCourse_id().equals(service.getCourse_id())) {
                    //Toast.makeText(activity, "Item already exist", Toast.LENGTH_SHORT).show();
                    if (isBuyNow) {
                        Intent myCart = new Intent(activity, CourseActivity.class); // AllCourse List
                        myCart.putExtra(Const.FRAG_TYPE, Const.MYCART);
                        activity.startActivity(myCart);
                    }
                    return;
                }
            }
            myCartList.add(item);
            if (addToCart != null) {
                addToCart.setText(activity.getResources().getString(R.string.remove_from_cart));
            }
            if (!isBuyNow) {
                //Toast.makeText(activity, "Course added in your cart.", Toast.LENGTH_SHORT).show();
            }
        } else {
            for (int i = 0; i < myCartList.size(); i++) {
                if (myCartList.get(i).getCourse_id().equals(item.getCourse_id())) {
                    if (!removes) {
                        myCartList.remove(i);
                        removes = true;
                    }
                }
            }
            removes = false;
            if (addToCart != null) {
                addToCart.setText(activity.getResources().getString(R.string.add_to_cart));
            }
            //Toast.makeText(activity, "Course removed from your cart.", Toast.LENGTH_SHORT).show();
        }

        SharedPreferences sharedPreferences = activity.getSharedPreferences(Const.SHARED_PREFS.NAME, MODE_PRIVATE);
        SharedPreferences.Editor editorUpdate = sharedPreferences.edit();
        Gson gsonUpdate = new Gson();
        String jsonUpdate = gsonUpdate.toJson(myCartList);
        editorUpdate.putString(Const.SHARED_PREFS.CART_LIST, jsonUpdate);
        editorUpdate.commit();

        if (isBuyNow) {
            Intent myCart = new Intent(activity, CourseActivity.class); // AllCourse List
            myCart.putExtra(Const.FRAG_TYPE, Const.MYCART);
            activity.startActivity(myCart);
        }

        //Toast.makeText(activity, "Course Size: "+myCartList.size(), Toast.LENGTH_SHORT).show();
    }

    public static void clearMyCartCourses(Activity activity) {
        ArrayList<CartItems> myCartList = new ArrayList<>();
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Const.SHARED_PREFS.NAME, MODE_PRIVATE);
        SharedPreferences.Editor editorUpdate = sharedPreferences.edit();
        Gson gsonUpdate = new Gson();
        String jsonUpdate = gsonUpdate.toJson(myCartList);
        editorUpdate.putString(Const.SHARED_PREFS.CART_LIST, jsonUpdate);
        editorUpdate.commit();

        //Toast.makeText(activity, "Course Size: "+myCartList.size(), Toast.LENGTH_SHORT).show();
    }

    public static void buyNowLogin(final Activity activity) {
        DialogUtils.makeDialog(activity, activity.getResources().getString(R.string.alert_guest), activity.getResources().getString(R.string.alert_guest_msg),
                activity.getResources().getString(R.string.login_sml), activity.getResources().getString(R.string.skip_for_now), true, new DialogUtils.onDialogUtilsOkClick() {
                    @Override
                    public void onOKClick() {
                        GuestSignOutUser(activity);
                    }
                }, new DialogUtils.onDialogUtilsCancelClick() {
                    @Override
                    public void onCancelClick() {

                    }
                });
    }

    public static void GoWebViewPDFActivity(Context activity, String title, String url) {
        Activity activity1 = (Activity) activity;
        Intent courseList = new Intent(activity, WebFragActivity.class);
        courseList.putExtra("title", title);
        courseList.putExtra("url", url);
        gotoActivity(courseList, activity1);

    }

    private static boolean checkStoragePerm(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public static void GoToWebViewPDFActivity(Context activity, String v_id, String url, boolean isDownload, String pdf_name, String course_id) {
        if (checkStoragePerm(activity))
        {
            if (url.contains(".pdf") || url.contains(".zip")) {
                if (url.contains(".zip")) {
                    url = url.replace(".zip", ".pdf");
                }
                Intent newintent = new Intent(activity, PdfDetailScreen.class);
                newintent.putExtra(Const.TITLE, v_id);
                newintent.putExtra(Const.URL, url);
                newintent.putExtra("pdf_name", pdf_name);
                newintent.putExtra("is_download", isDownload);
                if (course_id.contains("#")) {
                    newintent.putExtra("course_id", course_id);
                } else {
                    newintent.putExtra("course_id", course_id + "#");
                }
                activity.startActivity(newintent);
            } else if (url.contains(".ws")) {
                GoToWS(activity, v_id, url, isDownload, pdf_name, v_id, course_id);
            }
        }

    }


    public static void GoToWS(Context activity, String title, String url, boolean isDownload, String pdf_name, String video_id, String course_id) {
        Intent newintent = new Intent(activity, PdfHtmlAcivity.class);
        newintent.putExtra(Const.URL, url);
        newintent.putExtra("type", pdf_name);
        newintent.putExtra("file_type", "ws");
        newintent.putExtra("video_id", title);
        newintent.putExtra("course_id", course_id);
        activity.startActivity(newintent);
    }


    public static String getDaySuffix(final int n) {
        checkArgument(n >= 1 && n <= 31, "illegal day of month: " + n);
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

    public static TextDrawable GetDrawable(String text, Context context, String userid) {
        //if (!TextUtils.isEmpty(text)) {
        String firstLetter = text == null ? "D" : text.substring(0, 1);
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, Const.color[DbAdapter.getInstance(context).getColor(userid)]);
        return drawable;
        /*} else {
            return null;
        }*/
    }

    public static ArrayList<Menu> getLeftMenu() {
        ArrayList<Menu> menuArrayList = new ArrayList<>();
        //menuArrayList.add(new Menu("17","0","Home","Home","17","0",R.drawable.ic_log_out,"17",new ArrayList<>()));
        menuArrayList.add(new Menu("1", "0", "Downloads", "Downloads", "1", "0", R.drawable.ic_downloads, "1", new ArrayList<>()));
        //   menuArrayList.add(new Menu("2","0"," Change Preferences"," Change Preferences","2","0",R.drawable.ic_storage_usage,"2",new ArrayList<>()));
        menuArrayList.add(new Menu("18", "0", "Course Transfer", "Course Transfer", "18", "0", R.drawable.ic_storage_usage, "18", new ArrayList<>()));
        if (SharedPreference.getInstance().getString(Const.IS_COUPON_AVAILABLE) != null
                && SharedPreference.getInstance().getString(Const.IS_COUPON_AVAILABLE).length() > 0
                && !SharedPreference.getInstance().getString(Const.IS_COUPON_AVAILABLE).equalsIgnoreCase("0")) {
            menuArrayList.add(new Menu("20", "0", "Coupon", "Coupon", "20", "0", R.mipmap.coupon_default, "20", new ArrayList<>()));
        }
        menuArrayList.add(new Menu("3", "0", "Usage History", "Usage History", "3", "0", R.drawable.ic_usage_history, "3", new ArrayList<>()));
        menuArrayList.add(new Menu("15", "0", "Purchase History", "Purchase History", "15", "0", R.drawable.ic_purchase_history, "15", new ArrayList<>()));
        menuArrayList.add(new Menu("6", "0", "Invite Friends", "Invite Friends", "6", "0", R.drawable.ic_invite_friends, "6", new ArrayList<>()));
        menuArrayList.add(new Menu("5", "0", "Contact Us", "Contact Us", "5", "0", R.drawable.ic_contact_us, "5", new ArrayList<>()));
        menuArrayList.add(new Menu("16", "0", "App Tutorial", "App Tutorial", "16", "0", R.drawable.ic_app_tutorial, "16", new ArrayList<>()));
        menuArrayList.add(new Menu("14", "0", "Offline Batch", "Offline Batch", "14", "0", R.drawable.ic_offline_batch, "14", new ArrayList<>()));
        // menuArrayList.add(new Menu("13","0","Help","Help","13","0",R.mipmap.help,"13",new ArrayList<>()));
        //    menuArrayList.add(new Menu("19", "0", "Chat with us", "Chat with us", "9", "0", R.mipmap.chatbot1, "19", new ArrayList<>()));
        menuArrayList.add(new Menu("4", "0", "FAQ", "FAQ", "4", "0", R.drawable.ic_faq, "4", new ArrayList<>()));
        menuArrayList.add(new Menu("8", "0", "Settings", "Settings", "8", "0", R.drawable.ic_setting_icon, "8", new ArrayList<>()));

        menuArrayList.add(new Menu("9", "0", "Logout", "Logout", "9", "0", R.drawable.ic_log_out, "9", new ArrayList<>()));
        return menuArrayList;
    }
}



