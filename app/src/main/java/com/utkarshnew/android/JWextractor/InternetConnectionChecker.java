package com.utkarshnew.android.JWextractor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Wonder 5 on 6/1/2016.
 */
public class InternetConnectionChecker extends BroadcastReceiver {

    public static final String INTERNET_STATE_RECEIVER = "INTERNET_STATE_RECEIVER";

    public boolean isOnline() {

        Runtime runtime = Runtime.getRuntime();
        try {

            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);

        } catch (IOException e) {
            Log.e("WSSpeed Check", "Checking internet speed", e);
        } catch (InterruptedException e) {
            Log.e("WSSpeed Check", "Checking internet speed", e);
        }
        return false;
    }

    public boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getExtras() != null) {
            Intent ntwrkIntent = new Intent(ConnectivityManager.CONNECTIVITY_ACTION);
            final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
            if (ni != null && ni.isConnectedOrConnecting()) {
                Log.i("Network State Change", "Network " + ni.getTypeName() + " connected");
                ntwrkIntent.putExtra("state", true);
            } else {
                Log.d("Network State Change", "There's no network connectivity");
                ntwrkIntent.putExtra("state", false);
            }
//            context.sendBroadcast(ntwrkIntent);
        }
    }

// --Commented out by Inspection START (19-07-2017 12:14):
//    public  boolean isWifiConnected(Context context) {
//        ConnectivityManager cm =
//                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        boolean isWiFi = false;
//
//        isWiFi = activeNetwork != null &&
//                activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
//        return isWiFi;
//    }
// --Commented out by Inspection STOP (19-07-2017 12:14)

}
