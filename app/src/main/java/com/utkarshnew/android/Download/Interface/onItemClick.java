package com.utkarshnew.android.Download.Interface;

import com.utkarshnew.android.table.VideosDownload;

public interface onItemClick {
    void OnVideoClick(int pos, VideosDownload videoDownload, String type);

    void getvideo_url(int pos, VideosDownload videoDownload, String type);

}
