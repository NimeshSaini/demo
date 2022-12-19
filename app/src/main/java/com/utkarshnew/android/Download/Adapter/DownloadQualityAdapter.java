package com.utkarshnew.android.Download.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.snackbar.Snackbar;
import com.utkarshnew.android.Download.Audio.AudioPlayerService;
import com.utkarshnew.android.Download.DownloadActivity;
import com.utkarshnew.android.Download.DownloadVideoPlayer;
import com.utkarshnew.android.Download.Interface.Getvideourl;
import com.utkarshnew.android.Download.Interface.onItemClick;
import com.utkarshnew.android.DownloadServices.VideoDownloadService;
import com.utkarshnew.android.Model.UrlObject;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.table.VideosDownload;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by appsquadz on 25/9/17.
 */

public class DownloadQualityAdapter extends RecyclerView.Adapter<DownloadQualityAdapter.StreamHolder> {
    Context context;

    private ArrayList<UrlObject> download_videos;
    private Getvideourl getvideourl;
    private int itempos = 0;
    private VideosDownload videosDownload;

    public DownloadQualityAdapter(Context context, ArrayList<UrlObject> download_videos, Getvideourl getvideourl, int itempos, VideosDownload videosDownload) {
        this.context = context;
        this.download_videos = download_videos;
        this.getvideourl = getvideourl;
        this.itempos = itempos;
        this.videosDownload = videosDownload;
    }

    @Override
    public StreamHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quality_adapter, parent, false);
        return new StreamHolder(view);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(StreamHolder holder, final int position) {
        if (!TextUtils.isEmpty(download_videos.get(position).getSize())) {
            if (TextUtils.isDigitsOnly(download_videos.get(position).getSize())) {
                holder.title.setText(download_videos.get(position).getTitle() + " (" + getFileSize(Long.parseLong(download_videos.get(position).getSize())) + ")");
            } else {
                holder.title.setText(download_videos.get(position).getTitle() + "(" + download_videos.get(position).getSize() + ")");
            }
        } else {
            holder.title.setText(download_videos.get(position).getTitle());

        }

        //   holder.title.setText(download_videos.get(position).getTitle()+ " ("+download_videos.get(position).getSize()+")");


        holder.title.setOnClickListener(new OnSingleClickListener(() -> {
            getvideourl.getVideourl(position, download_videos.get(position), itempos, videosDownload);
            return null;
        }));

    }

    public static String getFileSize(long size) {
        if (size <= 0)
            return "0";

        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));

        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    @Override
    public int getItemCount() {
        if (download_videos != null && download_videos.size() > 0)
            return download_videos.size();
        else return 0;
    }


    public class StreamHolder extends RecyclerView.ViewHolder {
        Button title;


        public StreamHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);

        }
    }

}
