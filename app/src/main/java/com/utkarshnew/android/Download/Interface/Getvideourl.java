package com.utkarshnew.android.Download.Interface;

import com.utkarshnew.android.Model.UrlObject;
import com.utkarshnew.android.table.VideosDownload;

public interface Getvideourl {
    void getVideourl(int pos, UrlObject urlObject, int itempos, VideosDownload videosDownload);
}
