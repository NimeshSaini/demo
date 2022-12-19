package com.utkarshnew.android.JWextractor;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.utkarshnew.android.Download.AudioPlayerActivty;
import com.utkarshnew.android.Download.DownloadActivity;
import com.utkarshnew.android.Download.DownloadVideoPlayer;
import com.utkarshnew.android.Model.UrlObject;
import com.utkarshnew.android.Player.CustommediaPlayerDialog;
import com.utkarshnew.android.Player.Liveawsactivity;
import com.utkarshnew.android.R;
import com.utkarshnew.android.feeds.activity.FeedVideoPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class SpeedoAdapter extends RecyclerView.Adapter<SpeedoAdapter.ViewHolder> {
    Activity activity;
    private ArrayList<UrlObject> sppedolist;
    SelectSpeedo selectSpeedo;
    int oldpos;

    private static int lastCheckedPos = 0;
    private static RadioButton lastChecked = null;


    public SpeedoAdapter(Activity activity, ArrayList<UrlObject> sppedolist, SelectSpeedo selectSpeedo) {
        this.activity = activity;
        this.sppedolist = sppedolist;
        this.selectSpeedo = selectSpeedo;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RadioButton radio_speed;
        TextView spped_value;

        public ViewHolder(View v) {
            super(v);
            radio_speed = v.findViewById(R.id.radio_speed);
            spped_value = v.findViewById(R.id.spped_value);
        }
    }

    @NonNull
    @Override
    public SpeedoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.speed_control_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SpeedoAdapter.ViewHolder holder, final int position) {
        holder.spped_value.setText(Objects.requireNonNull(sppedolist.get(position)).getTitle());
        holder.radio_speed.setTag(position);
        holder.radio_speed.setChecked(sppedolist.get(position).isSelected());

        if (position == 3 && sppedolist.get(3).isSelected() && holder.radio_speed.isChecked()) {
            lastChecked = holder.radio_speed;
            lastCheckedPos = 3;
        }
        holder.radio_speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity instanceof Liveawsactivity) {
                    if (((Liveawsactivity) activity).player != null && ((Liveawsactivity) activity).trackSelector.getCurrentMappedTrackInfo() != null) {
                        qualitypos(v, position);
                    } else {
                        holder.radio_speed.setChecked(false);
                        Toast.makeText(activity, "Player not ready, Please wait", Toast.LENGTH_SHORT).show();

                    }
                }
                if (activity instanceof AudioPlayerActivty) {
                    if (((AudioPlayerActivty) activity).player != null) {
                        qualitypos(v, position);
                    } else {
                        holder.radio_speed.setChecked(false);
                        Toast.makeText(activity, "Player not ready, Please wait", Toast.LENGTH_SHORT).show();

                    }
                } else if (activity instanceof JWVideoPlayer) {
                    if (((JWVideoPlayer) activity).player != null && null != ((JWVideoPlayer) activity).trackSelector.getCurrentMappedTrackInfo()) {
                        qualitypos(v, position);
                    } else {
                        holder.radio_speed.setChecked(false);
                        Toast.makeText(activity, "Player not ready, Please wait", Toast.LENGTH_SHORT).show();

                    }
                }else if (activity instanceof CustommediaPlayerDialog) {
                    if (((CustommediaPlayerDialog) activity).player != null && null != ((CustommediaPlayerDialog) activity).trackSelector.getCurrentMappedTrackInfo()) {
                        qualitypos(v, position);
                    } else {
                        holder.radio_speed.setChecked(false);
                        Toast.makeText(activity, "Player not ready, Please wait", Toast.LENGTH_SHORT).show();

                    }
                }
                else if (activity instanceof FeedVideoPlayer) {
                    if (((FeedVideoPlayer) activity).getPlayer() != null && Objects.requireNonNull(((FeedVideoPlayer) activity).getTrackSelector()).getCurrentMappedTrackInfo() != null) {
                        qualitypos(v, position);
                    } else {
                        holder.radio_speed.setChecked(false);
                        Toast.makeText(activity, "Player not ready, Please wait", Toast.LENGTH_SHORT).show();

                    }
                } else if (activity instanceof DownloadVideoPlayer) {
                    if (((DownloadVideoPlayer) activity).getPlayer() != null && Objects.requireNonNull(((DownloadVideoPlayer) activity).getTrackSelector()).getCurrentMappedTrackInfo() != null) {
                        qualitypos(v, position);
                    } else {
                        holder.radio_speed.setChecked(false);
                        Toast.makeText(activity, "Player not ready, Please wait", Toast.LENGTH_SHORT).show();

                    }
                }


            }
        });



    }

    @Override
    public int getItemCount() {
        return sppedolist.size();
    }

    public void qualitypos(View v, int position) {
        if (lastCheckedPos != position) {
            RadioButton cb = (RadioButton) v;
            int clickedPos = (Integer) cb.getTag();
            if (cb.isChecked()) {
                if (lastChecked != null) {
                    lastChecked.setChecked(false);
                    sppedolist.get(lastCheckedPos).setSelected(false);
                    notifyItemChanged(lastCheckedPos);
                }
                lastChecked = cb;
                lastCheckedPos = clickedPos;
            } else
                lastChecked = null;

            selectSpeedo.selectSpeedPos(position);
            sppedolist.get(clickedPos).setSelected(cb.isChecked());
        }
    }
}
