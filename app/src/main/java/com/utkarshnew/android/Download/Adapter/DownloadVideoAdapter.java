package com.utkarshnew.android.Download.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.utkarshnew.android.Download.Interface.onItemClick;
import com.utkarshnew.android.DownloadServices.VideoDownloadService;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.table.VideosDownload;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;

import java.io.File;
import java.util.List;

/**
 * Created by appsquadz on 25/9/17.
 */

public class DownloadVideoAdapter extends RecyclerView.Adapter<DownloadVideoAdapter.StreamHolder> {
    Context context;

    private boolean delete_check = false;
    private List<VideosDownload> download_videos;
    private onItemClick onclick;

    public DownloadVideoAdapter(Context context, List<VideosDownload> download_videos, onItemClick onclick) {
        this.context = context;
        this.download_videos = download_videos;
        this.onclick = onclick;
    }

    @Override
    public StreamHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.download_adpter, parent, false);
        return new StreamHolder(view);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(StreamHolder holder, final int position) {

        try {

            if (delete_check) {
                holder.file_mb.setText(download_videos.get(holder.getAdapterPosition()).getLengthInMb());
                //holder.selected_video.setChecked(false);
                if (download_videos.get(holder.getAdapterPosition()).getIs_complete().equalsIgnoreCase("1")) {
                    if (download_videos.get(holder.getAdapterPosition()).getIs_selected() != null && download_videos.get(holder.getAdapterPosition()).getIs_selected().equalsIgnoreCase("1")) {
                        holder.selected_video.setChecked(true);
                    } else {
                        holder.selected_video.setChecked(false);

                    }

                    //  holder.selected_video.setChecked(false);
                    holder.delete_layout.setVisibility(View.VISIBLE);
                } else {
                    holder.delete_layout.setVisibility(View.GONE);
                }
            } else {
                holder.delete_layout.setVisibility(View.GONE);
            }

            if (download_videos.get(holder.getAdapterPosition()).getThumbnail_url() != null && !download_videos.get(holder.getAdapterPosition()).getThumbnail_url().equalsIgnoreCase("") && download_videos.get(holder.getAdapterPosition()).getPercentage() == 100) {

                Glide.with(context)
                        .load(download_videos.get(holder.getAdapterPosition()).getThumbnail_url().replaceAll(" ", "%20"))
                        .diskCacheStrategy(DiskCacheStrategy.DATA).dontAnimate()
                        .into(holder.courseImage);

            } else {
                holder.courseImage.setImageResource(R.drawable.square_thumbnail);
                //  Helper.setThumbnailImage(context, download_videos.get(holder.getAdapterPosition()).getThumbnail_url(), context.getResources().getDrawable(R.mipmap.square_placeholder), holder.courseImage);
            }

            holder.selected_video.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        download_videos.get(holder.getAdapterPosition()).setIs_selected("1");
                    } else {
                        download_videos.get(holder.getAdapterPosition()).setIs_selected("0");
                    }
                    ((DownloadActivity) context).visible_seletebutton(download_videos);
                }
            });

            holder.video_name.setText(download_videos.get(holder.getAdapterPosition()).getVideo_name());

            if (download_videos.get(holder.getAdapterPosition()).getVideotime() != null && !download_videos.get(holder.getAdapterPosition()).getVideotime().equalsIgnoreCase("")) {
                holder.video_time.setVisibility(View.VISIBLE);
                holder.video_time.setText("Video :- " + download_videos.get(holder.getAdapterPosition()).getVideotime());
            } else {
                holder.video_time.setVisibility(View.GONE);
            }
            if (download_videos.get(holder.getAdapterPosition()).getMp4_download_url() == null || (int) download_videos.get(holder.getAdapterPosition()).getPercentage() == 100) {
                if ((int) download_videos.get(holder.getAdapterPosition()).getPercentage() == 100) {
                    holder.pauseLayout.setVisibility(View.GONE);
                    holder.cancel_progress_layout.setVisibility(View.GONE);
                    holder.download_icon.setVisibility(View.VISIBLE);
                    holder.download_icon.setImageResource(R.drawable.play_icon);
                } else {
                    holder.loadingProgress.setProgress(0);
                    holder.loadingProgress.setProgressDrawable(context.getResources().getDrawable(R.drawable.circular_progess));
                    holder.percentageTxt.setText(download_videos.get(holder.getAdapterPosition()).getPercentage() + "%");
                    holder.cancel_progress_layout.setVisibility(View.VISIBLE);
                    holder.pauseLayout.setVisibility(View.GONE);
                    holder.download_icon.setVisibility(View.GONE);
                }

              /*  if ((int) download_videos.get(holder.getAdapterPosition()).getPercentage() == 0 ){
                    holder.loadingProgress.setProgress(0);
                    holder.loadingProgress.setProgressDrawable(context.getResources().getDrawable(R.drawable.circular_progess));
                    holder.percentageTxt.setText(download_videos.get(holder.getAdapterPosition()).getPercentage()+"%");
                }
                holder.cancel_progress_layout.setVisibility(View.VISIBLE);
                holder.pauseLayout.setVisibility(View.GONE);
                holder.download_icon.setVisibility(View.GONE);
*/

            } else {
                if (download_videos.get(holder.getAdapterPosition()).getVideo_status().equalsIgnoreCase("Downloading Running")) {
                    holder.pauseLayout.setVisibility(View.VISIBLE);

                    holder.download_icon.setVisibility(View.GONE);
                    holder.cancel_progress_layout.setVisibility(View.VISIBLE);

                    holder.optionPauseImgView.setImageResource(R.drawable.ic_video_download_pause);
                    holder.pauseTextView.setText("Downloading Running");


                    if ((int) download_videos.get(holder.getAdapterPosition()).getPercentage() > 0 && (int) download_videos.get(holder.getAdapterPosition()).getPercentage() < 100) {

                        holder.loadingProgress.setProgress(download_videos.get(holder.getAdapterPosition()).getPercentage());
                        holder.loadingProgress.setProgressDrawable(context.getResources().getDrawable(R.drawable.circular_progess));
                        holder.percentageTxt.setText(download_videos.get(holder.getAdapterPosition()).getPercentage() + "%");
                    } else if ((int) download_videos.get(holder.getAdapterPosition()).getPercentage() == 0) {

                        holder.loadingProgress.setProgress(0);
                        holder.loadingProgress.setProgressDrawable(context.getResources().getDrawable(R.drawable.circular_progess));
                        holder.percentageTxt.setText(download_videos.get(holder.getAdapterPosition()).getPercentage() + "%");
                    }
                } else if (download_videos.get(holder.getAdapterPosition()).getVideo_status().equalsIgnoreCase("Downloading Pause")) {
                    holder.cancel_progress_layout.setVisibility(View.VISIBLE);

                    holder.download_icon.setVisibility(View.GONE);
                    holder.pauseLayout.setVisibility(View.VISIBLE);

                    holder.optionPauseImgView.setImageResource(R.drawable.download_icon);
                    holder.pauseTextView.setText("Downloading Pause");


                    if ((int) download_videos.get(holder.getAdapterPosition()).getPercentage() >= 0 && (int) download_videos.get(holder.getAdapterPosition()).getPercentage() < 100) {
                        holder.loadingProgress.setProgress(download_videos.get(holder.getAdapterPosition()).getPercentage());
                        holder.loadingProgress.setProgressDrawable(context.getResources().getDrawable(R.drawable.circular_progess));
                        holder.percentageTxt.setText(download_videos.get(holder.getAdapterPosition()).getPercentage() + "%");
                    }

                }/*else if (download_videos.get(holder.getAdapterPosition()).getVideo_status().equalsIgnoreCase("Download")){
                holder.pauseLayout.setVisibility(View.GONE);
                holder.cancel_progress_layout.setVisibility(View.GONE);
                holder.download_icon.setImageResource(R.drawable.download_icon);
                holder.download_icon.setVisibility(View.VISIBLE);
            }*/ else if (download_videos.get(holder.getAdapterPosition()).getPercentage() == 100) {
                    holder.pauseLayout.setVisibility(View.GONE);
                    holder.cancel_progress_layout.setVisibility(View.GONE);
                    holder.download_icon.setVisibility(View.VISIBLE);
                    holder.download_icon.setImageResource(R.drawable.play_icon);
                }
            }


            holder.optionPauseImgView.setOnClickListener(new OnSingleClickListener(() -> {
                if (download_videos.get(holder.getAdapterPosition()).getPercentage() == 100) {
                    //  Toast.makeText(context, "Video is alredy downloaded..", Toast.LENGTH_SHORT).show();
                } else {
                    if (!Helper.isConnected(context)) {
                        Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        return null;
                    }
                    if (onclick != null) {
                        if (download_videos.get(holder.getAdapterPosition()).getVideo_status().equalsIgnoreCase("Downloading Running")) {
                            if (VideoDownloadService.isServiceRunning && VideoDownloadService.video_id.equalsIgnoreCase(download_videos.get(holder.getAdapterPosition()).getVideo_id())) {
                                onclick.OnVideoClick(holder.getAdapterPosition(), download_videos.get(holder.getAdapterPosition()), "pause");
                            } else {
                                Snackbar.make(holder.optionPauseImgView, "Only Downloading video is pause", Snackbar.LENGTH_SHORT).show();
                            }
                        } else if (download_videos.get(holder.getAdapterPosition()).getVideo_status().equalsIgnoreCase("Downloading Pause")) {
                            onclick.OnVideoClick(holder.getAdapterPosition(), download_videos.get(holder.getAdapterPosition()), "play");
                        }

                    }
                }
                return null;
            }));

            holder.pauseLayout.setOnClickListener(new OnSingleClickListener(() -> {
                if (download_videos.get(holder.getAdapterPosition()).getPercentage() == 100) {
                    // Toast.makeText(context, "Video is alredy downloaded..", Toast.LENGTH_SHORT).show();
                } else {
                    if (!Helper.isConnected(context)) {
                        Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        return null;
                    }
                    if (onclick != null) {
                        if (download_videos.get(holder.getAdapterPosition()).getVideo_status().equalsIgnoreCase("Downloading Running")) {
                            onclick.OnVideoClick(holder.getAdapterPosition(), download_videos.get(holder.getAdapterPosition()), "pause");
                        }
                        if (download_videos.get(holder.getAdapterPosition()).getVideo_status().equalsIgnoreCase("Downloading Pause")) {
                            onclick.OnVideoClick(holder.getAdapterPosition(), download_videos.get(holder.getAdapterPosition()), "play");
                        }

                    }
                }

                return null;
            }));

           /* holder.download_icon.setOnClickListener(new OnSingleClickListener(()->{

                if (!VideoDownloadService.isServiceRunning)
                {
                    if (download_videos.get(holder.getAdapterPosition()).getPercentage()==100)
                    {

                        Intent i = new Intent(context, DownloadVideoPlayer.class);
                        i.putExtra("video_id",download_videos.get(holder.getAdapterPosition()).getVideo_id());
                        i.putExtra("video_name",download_videos.get(holder.getAdapterPosition()).getVideo_name());
                        i.putExtra("video_play",download_videos.get(holder.getAdapterPosition()).getVideo_history());
                        i.putExtra("current_pos",download_videos.get(holder.getAdapterPosition()).getVideoCurrentPosition());
                        i.putExtra("video", download_videos.get(holder.getAdapterPosition()).getVideo_history());
                        i.putExtra("video_time", download_videos.get(holder.getAdapterPosition()).getVideotime());

                        context.startActivity(i);
                    }else
                    if (onclick!=null)
                    {
                        if (!Helper.isConnected(context))
                    {
                        Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        return null;
                    }
                        onclick.getvideo_url(holder.getAdapterPosition(),download_videos.get(holder.getAdapterPosition()),"Download");
                    }

                }else {
                    if (download_videos.get(holder.getAdapterPosition()).getPercentage()==100)
                    {
                        Intent i = new Intent(context, DownloadVideoPlayer.class);
                        i.putExtra("video_name",download_videos.get(holder.getAdapterPosition()).getVideo_name());
                        i.putExtra("video_id",download_videos.get(holder.getAdapterPosition()).getVideo_id());
                        i.putExtra("current_pos",download_videos.get(holder.getAdapterPosition()).getVideoCurrentPosition());
                        i.putExtra("video", download_videos.get(holder.getAdapterPosition()).getVideo_history());
                        i.putExtra("video_time", download_videos.get(holder.getAdapterPosition()).getVideotime());

                        context.startActivity(i);
                        //  Toast.makeText(context, "Video is alredy downloaded..", Toast.LENGTH_SHORT).show();
                    }else{

                    }
                       // Toast.makeText(context, "you can download one video at a time ", Toast.LENGTH_SHORT).show();
                }
                return null;
            }));
*/

            holder.cancel_progress_layout.setOnClickListener(new OnSingleClickListener(() -> {
                if (onclick != null) {
                    if (!Helper.isConnected(context)) {
                        Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        return null;
                    }
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Download Video?");
                    alertDialogBuilder.setMessage("Do you want to delete this video");
                    alertDialogBuilder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
                    alertDialogBuilder.setPositiveButton("Yes", (dialog, which) -> {
                        try {
                            // Toast.makeText(context, "A_"+download_videos.size()+"-"+position, Toast.LENGTH_SHORT).show();
                            if (download_videos.get(position) != null && download_videos.get(position).getPercentage() != 100) {
                                onclick.OnVideoClick(position, download_videos.get(position), "cancel");
                            } else {
                                Toast.makeText(context, "Video is downloaded", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            try {
                                //   Toast.makeText(context, "B_"+download_videos.size()+"-"+position, Toast.LENGTH_SHORT).show();
                                if (download_videos.get(position) != null && download_videos.get(position).getPercentage() != 100) {
                                    onclick.OnVideoClick(position, download_videos.get(position), "cancel");
                                } else {
                                    Toast.makeText(context, "Video is downloaded", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e1) {
                                //Toast.makeText(context, "C_"+download_videos.size()+"-"+position, Toast.LENGTH_SHORT).show();
                                e1.printStackTrace();
                            }
                            e.printStackTrace();
                        }

                        dialog.dismiss();
                        dialog.cancel();
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                return null;
            }));


            holder.study_single_itemLL.setOnClickListener(new OnSingleClickListener(() ->
            {
                if (download_videos.get(holder.getAdapterPosition()).getPercentage() == 100) {
                    if (AudioPlayerService.player != null) {
                        if (AudioPlayerService.type.equalsIgnoreCase("youtube")) {
                            ((DownloadActivity) context).utkashRoom.getyoutubedata().updateTime(AudioPlayerService.player.getCurrentPosition(), AudioPlayerService.videoid, MakeMyExam.userId, "1");
                        } else {
                            ((DownloadActivity) context).utkashRoom.getaudiodao().update_audio_currentpos(AudioPlayerService.videoid, MakeMyExam.userId, AudioPlayerService.player.getCurrentPosition());
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
                    File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.Videos/" + download_videos.get(holder.getAdapterPosition()).getVideo_history() + ".mp4");
                    if (file.exists()) {
                        Intent i = new Intent(context, DownloadVideoPlayer.class);
                        i.putExtra("video_name", download_videos.get(holder.getAdapterPosition()).getVideo_name());
                        i.putExtra("video_id", download_videos.get(holder.getAdapterPosition()).getVideo_id());
                        i.putExtra("current_pos", download_videos.get(holder.getAdapterPosition()).getVideoCurrentPosition());
                        i.putExtra("video", download_videos.get(holder.getAdapterPosition()).getVideo_history());
                        i.putExtra("video_time", download_videos.get(holder.getAdapterPosition()).getVideotime());
                        i.putExtra("course_id", download_videos.get(holder.getAdapterPosition()).getCourse_id());
                        i.putExtra("token", download_videos.get(holder.getAdapterPosition()).getVideo_token());
                        context.startActivity(i);
                    } else {
                        File file_processing = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.processing/" + download_videos.get(holder.getAdapterPosition()).getVideo_history() + ".mp4");
                        if (file_processing.exists()) {
                            file_processing.delete();
                            ((DownloadActivity) context).utkashRoom.getvideoDownloadao().delete_viavideoid(download_videos.get(holder.getAdapterPosition()).getVideo_id(), MakeMyExam.userId);
                            download_videos.remove(holder.getAdapterPosition());
                        }

                    }

                }

                return null;
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void alert_dailog(StreamHolder holder) {

    }


    @Override
    public int getItemCount() {
        if (download_videos != null && download_videos.size() > 0)
            return download_videos.size();
        else return 0;
    }


    public void visible_layout(boolean delete_check, List<VideosDownload> download_videos) {
        this.delete_check = delete_check;
        if (!delete_check) {
            this.download_videos = download_videos;

        }
        notifyDataSetChanged();
    }

    public void notifydata(List<VideosDownload> download_videos) {
        this.download_videos = download_videos;
        delete_check = false;
        notifyDataSetChanged();
    }


    public class StreamHolder extends RecyclerView.ViewHolder {
        TextView video_name, video_time, percentageTxt, pauseTextView;
        RelativeLayout pauseLayout, cancel_progress_layout, delete_layout;
        ImageView optionPauseImgView, download_icon, courseImage;
        LinearLayout study_single_itemLL;
        TextView file_mb;
        CheckBox selected_video;
        ProgressBar loadingProgress;

        public StreamHolder(View itemView) {
            super(itemView);

            video_time = itemView.findViewById(R.id.video_time);
            file_mb = itemView.findViewById(R.id.file_mb);
            selected_video = itemView.findViewById(R.id.check_box);
            delete_layout = itemView.findViewById(R.id.delete_layout);
            loadingProgress = itemView.findViewById(R.id.loadingProgress);
            percentageTxt = itemView.findViewById(R.id.percentageTxt);
            download_icon = itemView.findViewById(R.id.download_icon);
            cancel_progress_layout = itemView.findViewById(R.id.cancel_progress_layout);
            video_name = itemView.findViewById(R.id.video_name);
            courseImage = itemView.findViewById(R.id.courseImage);
            pauseLayout = itemView.findViewById(R.id.pauseLayout);
            pauseTextView = itemView.findViewById(R.id.pauseTextView);
            optionPauseImgView = itemView.findViewById(R.id.optionPauseImgView);
            study_single_itemLL = itemView.findViewById(R.id.study_single_itemLL);
        }
    }

}
