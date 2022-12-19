package com.utkarshnew.android.Utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;

import com.utkarshnew.android.Utils.Network.API;
import com.scottyab.rootbeer.RootBeer;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.util.List;
import java.util.Objects;

public class RootUtil {

    static boolean proxy;

    public static boolean isDeviceRooted() {
        return detectTestKeys() || checkForSuBinary() || checkSuExists() || checkForBusyBoxBinary();
    }


    public static boolean check_proxy(Activity activity) {
        URI uri = URI.create(API.BASE_URL);
        ProxySelector defaultProxySelector = ProxySelector.getDefault();
        List<Proxy> proxyList = defaultProxySelector.select(uri);
        if (proxyList.size() > 0) {
            if (proxyList.get(0).toString().contains(":")) {
                proxy = true;
            } else {
                checkVpn(activity);
            }
        }
        return proxy;
    }

    public static void checkVpn(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Helper.isNetworkConnected(activity)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if ((cm.getNetworkCapabilities(cm.getActiveNetwork())).hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                    proxy = true;
                }
            } else {
                if (Objects.requireNonNull(cm.getNetworkInfo(ConnectivityManager.TYPE_VPN)).isConnectedOrConnecting()) {
                    proxy = true;
                }
            }
        }
    }

    public static boolean detectFullRoot(Context context) {
        RootBeer rootBeer = new RootBeer(context);
        if (rootBeer.isRooted()) {
            //we found indication of root
            return true;
        } else {
            //we didn't find indication of root
            return false;
        }
    }

    public static boolean detectTestKeys() {
        String buildTags = Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }

    public static boolean checkForSuBinary() {
        return checkForBinary("su"); // function is available below
    }

    public static boolean checkForBusyBoxBinary() {
        return checkForBinary("busybox");//function is available below
    }

    /**
     * @param filename - check for this existence of this
     *                 file("su","busybox")
     * @return true if exists
     */
    public static boolean checkForBinary(String filename) {
        for (String path : binaryPaths) {
            File f = new File(path, filename);
            boolean fileExists = f.exists();
            if (fileExists) {
                return true;
            }
        }
        return false;
    }

    /**
     * A variation on the checking for SU, this attempts a 'which su'
     * different file system check for the su binary
     *
     * @return true if su exists
     */
    public static boolean checkSuExists() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[]
                    {"/system /xbin/which", "su"});
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line = in.readLine();
            process.destroy();
            return line != null;
        } catch (Exception e) {
            if (process != null) {
                process.destroy();
            }
            return false;
        }
    }

    public static String[] binaryPaths = {
            "/data/local/",
            "/data/local/bin/",
            "/data/local/xbin/",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/data/local/su",
            "/su/bin/su",
            "/sbin/",
            "/su/bin/",
            "/system/bin/",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/system/bin/.ext/",
            "/system/bin/failsafe/",
            "/system/sd/xbin/",
            "/system/sd/xbin/su",
            "/system/usr/we-need-root/",
            "/system/xbin/",
            "/system/app/Superuser.apk",
            "/system/bin/failsafe/su",
            "/cache",
            "/data",
            "/dev"
    };
}
