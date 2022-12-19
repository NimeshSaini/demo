package com.utkarshnew.android.Utils;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.google.android.exoplayer2.util.Util;
import com.google.firebase.FirebaseApp;
import com.utkarshnew.android.R;

/**
 * Created by Cbc-03 on 09/13/16.
 */
public class eMedicozApp extends MultiDexApplication {

    private static Context appContext;
    protected String userAgent;

    public static Context getAppContext() {
        return appContext;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //Fabric.with(this, new Crashlytics());
        FirebaseApp.initializeApp(appContext);
        appContext = this;
        userAgent = Util.getUserAgent(this, appContext.getResources().getString(R.string.app_name));

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
