package com.utkarshnew.android.JWextractor;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.utkarshnew.android.Model.UrlObject;
import com.utkarshnew.android.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class VideoPopUpMenuAdapter extends RecyclerView.Adapter<VideoPopUpMenuAdapter.ViewHolder> {
    private static final String TAG = "VideoPopUp Adapter";
    private Context mContext;
    private HashMap<Integer, UrlObject> popUpList;

    private Player player;
    private String[] popUpSpeedList;
    private List<Integer> popUpQualityList;
    private List<String> stringResolutionList;
    private static String stringQuality;
    public static int selectedPlayBackSpeed = 3; // 3 for normal(array index). when starting the video, speed is always selected normal
    private static int selectedPlayBackQuality = 0; // 0 for highest quality available(item index). when starting the video,
    // quality is always selected highest
    private List<ViewHolder> holderList;
    private String popUpContext;
    private JWVideoPlayer activity;
    private JWVideoPlayer playerActivity;
    private boolean isLiveStream, isJWPlayer, isRecordedLive = false, isVimeo;

    public VideoPopUpMenuAdapter(JWVideoPlayer activity, HashMap<Integer, UrlObject> popUpList, Player player,
                                 String popUpContext, String sQuality, boolean isJwplayer, boolean isLive, boolean isRecordedLiv) {
        this.popUpQualityList = new ArrayList<>();
        this.popUpList = popUpList;
        this.player = player;
        this.popUpContext = popUpContext;
        holderList = new ArrayList<>();
        this.activity = activity;
        stringQuality = sQuality;
        isJWPlayer = isJwplayer;
        this.isVimeo = isVimeo;
        isLiveStream = isLive;
        isRecordedLive = isRecordedLiv;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView popUpItemText;
        RelativeLayout popUpLinearLayout;

        public ViewHolder(View v) {
            super(v);
            popUpItemText = v.findViewById(R.id.videopopupitemtext);
            popUpLinearLayout = v.findViewById(R.id.popupitemholder);
        }
    }

    @NonNull
    @Override
    public VideoPopUpMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (activity != null) {
            v = LayoutInflater.from(activity).inflate(R.layout.video_pop_up_menu_item, parent, false);
        } else {
            v = LayoutInflater.from(playerActivity).inflate(R.layout.video_pop_up_menu_item, parent, false);
        }
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoPopUpMenuAdapter.ViewHolder holder, final int position) {
        holderList.add(holder);

        holder.popUpItemText.setText(Objects.requireNonNull(popUpList.get(position)).getTitle());


        for (int i = 0; i < popUpList.size(); i++) {
            if (stringQuality.equalsIgnoreCase(Objects.requireNonNull(popUpList.get(position)).getTitle())) {
                holder.popUpItemText.setTextColor(activity.getResources().getColor(R.color.colorPrimary));
            }
        }

        holder.popUpLinearLayout.setOnClickListener(view -> {
            for (int i = 0; i < holderList.size(); i++) {
                holderList.get(i).popUpItemText.setTextColor(activity.getResources().getColor(R.color.colorActionBarText));
            }
            stringQuality = popUpList.get(position).getTitle();
            holder.popUpItemText.setTextColor(activity.getResources().getColor(R.color.colorAccent));
            activity.changeVideoQuality(popUpList.get(position).getTitle(),position);

        });
    }

    @Override
    public int getItemCount() {
        if (popUpContext.equalsIgnoreCase("speed")) {
            return popUpSpeedList.length;
        } else {
            return popUpList.size();
        }
    }
}
