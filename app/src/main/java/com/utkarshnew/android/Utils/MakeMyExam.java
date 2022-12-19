package com.utkarshnew.android.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.google.android.exoplayer2.util.Util;
import com.utkarshnew.android.BuildConfig;
import com.utkarshnew.android.Model.Video;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.retrofit.WebInterface;
import com.google.firebase.FirebaseApp;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


import dagger.hilt.android.HiltAndroidApp;
import fr.maxcom.libmedia.Licensing;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Cbc-03 on 09/13/16.
 */
@HiltAndroidApp
public class MakeMyExam extends MultiDexApplication {

    public static String is_track_selector_auto = "0";

    public static Bitmap bitmap;
    private static Context appContext;
    public static boolean lvl = false;

    protected String userAgent;
    public static String FeedData = "";

    public static String getMain_cat() {
        return main_cat;
    }

    public static void setMain_cat(String main_cat) {
        MakeMyExam.main_cat = main_cat;
    }

    public static String main_cat = "";
    public static String page = "1";

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        MakeMyExam.userId = userId;
    }

    public static String userId = "0";

    public static String getFeeds() {
        return feeds;
    }

    public static void setFeeds(String feeds) {
        MakeMyExam.feeds = feeds;
    }

    public static String feeds = "0";

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        MakeMyExam.username = username;
    }

    public static String username = "";
    public static String questionbanklist = "";
    public static String object = "";
    public static long time_server = 0;

    public static long getTime_server() {
        return time_server;
    }

    public static void setTime_server(long time_server) {
        MakeMyExam.time_server = time_server;
    }

    public static ArrayList<Video> videoArrayList = new ArrayList<>();
    public static ArrayList<Video> seleced_tile_list = new ArrayList<>();
    private static Retrofit retrofit;

    public static Context getAppContext() {
        return appContext;
    }

    //nightMode
    public static final String NIGHT_MODE = "NIGHT_MODE";
    private boolean isNightModeEnabled = false;

    private static MakeMyExam singleton = null;

    public static MakeMyExam getInstance() {

        if (singleton == null) {
            singleton = new MakeMyExam();
        }
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Thread.setDefaultUncaughtExceptionHandler(new LocalFileUncaughtExceptionHandler(this,
                Thread.getDefaultUncaughtExceptionHandler()));

        appContext = this;
        // lvl =doCheck(this);
        lvl = true;
        // Log.d("prince_lvl",""+lvl);


        Licensing.allow(this);
        Licensing.setDeveloperMode(true);

        FirebaseApp.initializeApp(appContext);
        userAgent = Util.getUserAgent(this, appContext.getResources().getString(R.string.app_name));
        Helper.setCanTakeScreenShot(true);
        FacebookSdk.setIsDebugEnabled(true);
        FacebookSdk.addLoggingBehavior(LoggingBehavior.APP_EVENTS);


        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        singleton = this;
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        this.isNightModeEnabled = mPrefs.getBoolean(NIGHT_MODE, false);

        try {
            getRetrofitInstance(WebInterface.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    public static Retrofit getRetrofitInstance() {

        if (SharedPreference.getInstance().getLoggedInUser() != null) {
            if (SharedPreference.getInstance().getLoggedInUser().getId() != null) {
                MakeMyExam.setUserId(SharedPreference.getInstance().getLoggedInUser().getId());
            } else {
                userId = "0";
            }
        } else {
            userId = "0";
        }

        if (retrofit != null)
            return retrofit;
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder requestBuilder = original.newBuilder()
                                .header(Const.USERID, MakeMyExam.getUserId())
                                .header(Const.DEVICE_TYPE, Const.DEVICE_TYPE_ANDROID)
                                .header(Const.Jwt, !TextUtils.isEmpty(SharedPreference.getInstance().getString(Const.JWT)) ?
                                        SharedPreference.getInstance().getString(Const.JWT) : "")
                                .header(Const.LANG, String.valueOf(SharedPreference.getInstance().getInt(Const.LANGUAGE)))
                                .header(Const.VERSION, !TextUtils.isEmpty(SharedPreference.getInstance().getString(Const.VERSION)) ?
                                        SharedPreference.getInstance().getString(Const.VERSION) : "");
                        Request request = requestBuilder
                                .addHeader(Const.AUTHORIZATION, API.Bearer)
                                .build();
                        return chain.proceed(request);
                    }
                })
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .build();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        httpClient.addInterceptor(logging);

        retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(API.BASE_URL)
                .client(httpClient.build()).build();


        return retrofit;
    }


    static String id;

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            /*.proxy(proxy)
            .certificatePinner(certPinner)*/
            .connectTimeout(60, TimeUnit.MINUTES)
            .readTimeout(60, TimeUnit.MINUTES);

    public static <S> S getRetrofitInstance(Class<S> serviceClass) {

        if (SharedPreference.getInstance().getLoggedInUser() != null) {
            if (SharedPreference.getInstance().getLoggedInUser().getId() != null) {
                id = SharedPreference.getInstance().getLoggedInUser().getId();
            } else {
                id = "";
            }
        } else {
            id = "";
        }

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = httpClient.addInterceptor(interceptor).connectTimeout(60, TimeUnit.MINUTES).readTimeout(60, TimeUnit.MINUTES).writeTimeout(1, TimeUnit.MINUTES).build();

        final Gson gson0 = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = null;
        retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson0))
                .client(client)
                .build();

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .header(Const.AUTHORIZATION, API.Bearer)
                        .header(Const.USERID, id)
                        /*.header(Const.DEVICETOKEN, SharedPreference.getInstance().getString(Const.FIREBASE_TOKEN_ID))*/
                        .header(Const.Jwt, !TextUtils.isEmpty(SharedPreference.getInstance().getString(Const.JWT)) ?
                                SharedPreference.getInstance().getString(Const.JWT) : "")
                        .header(Const.LANG, String.valueOf(SharedPreference.getInstance().getInt(Const.LANGUAGE)))
                        .header(Const.VERSION, !TextUtils.isEmpty(SharedPreference.getInstance().getString(Const.VERSION)) ?
                                SharedPreference.getInstance().getString(Const.VERSION) : "");
//                    .addQuery("?lang=", String.valueOf(SharedPreference.getInstance().getInt(Const.LANGUAGE)))
                Request request = requestBuilder
                        //.addHeader(Const.AUTHORIZATION, API.Bearer)
                        .build();
                return chain.proceed(request);
            }
        });

        Log.d("response", "Devicetokken:" + SharedPreference.getInstance().getString(Const.FIREBASE_TOKEN_ID) + ":Jwt:" + (!TextUtils.isEmpty(SharedPreference.getInstance().getString(Const.JWT)) ?
                SharedPreference.getInstance().getString(Const.JWT) : ""));

        return retrofit.create(serviceClass);
    }


    @Override
    protected void attachBaseContext(Context base) {
        MultiDex.install(this);

        super.attachBaseContext(base);
    }

}
