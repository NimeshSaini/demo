package com.naveed.ytextractor.utils;

import android.util.Log;
import com.naveed.ytextractor.BuildConfig;

public class LogUtils
{
private static String livevideourl="";
	public static void log(String x){
		livevideourl=x;
		if(BuildConfig.DEBUG)
		Log.d("Naveed",x);
	}

	public static String getLivevideourl() {
		return livevideourl;
	}

	public static void log(int x){
		if(BuildConfig.DEBUG)
			Log.d("Naveed",String.valueOf(x));
	}
	
}
