package com.utkarshnew.android.Download.Audio;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.utkarshnew.android.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomAudioActionReceiver implements PlayerNotificationManager.CustomActionReceiver {
    static String[] audioPlayBackSpeedList;
    static Context mContext;
    String currentAudioSpeed_new = "NORMAL";

    @Override
    public Map<String, NotificationCompat.Action> createCustomActions(Context context, int instanceId) {
        mContext = context;
        Intent intent = new Intent("audio_slow").setPackage(context.getPackageName()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        int pendingFlags;

        PendingIntent pendingIntent;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingFlags = PendingIntent.FLAG_MUTABLE;
        } else {
           // pendingFlags = PendingIntent.FLAG_UPDATE_CURRENT;
            pendingFlags = 0;
        }

        pendingIntent = PendingIntent.getBroadcast(context, instanceId, intent, pendingFlags);
        NotificationCompat.Action action1 = new NotificationCompat.Action(
                R.drawable.ic_audio_slow,
                "audio_slow",
                pendingIntent
        );
        Intent intent2 = new Intent("audio_fast").setPackage(context.getPackageName()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent2;


        pendingIntent2 = PendingIntent.getBroadcast(context, instanceId, intent2, pendingFlags);


        NotificationCompat.Action action2 = new NotificationCompat.Action(
                R.drawable.fast_icon,
                "audio_fast",
                pendingIntent2
        );
        Map<String, NotificationCompat.Action> actionMap = new HashMap<>();
        actionMap.put("audio_slow", action1);
        actionMap.put("audio_fast", action2);
        return actionMap;
    }

    @Override
    public List<String> getCustomActions(Player player) {
        List<String> customActions = new ArrayList<>();
        customActions.add("audio_slow");
        customActions.add("audio_fast");
        Log.e("getCustomActions", "action: " + player.getCurrentWindowIndex());
        return customActions;
    }

    @Override
    public void onCustomAction(Player player, String action, Intent intent) {
        audioPlayBackSpeedList = mContext.getResources().getStringArray(R.array.video_playback_speed);
        Log.e("onCustomAction", "action: " + intent.getAction() + action);

        if (action.equalsIgnoreCase("audio_slow")) {

            int currentAudioSpeedIndex = Arrays.asList(audioPlayBackSpeedList).indexOf(currentAudioSpeed_new);
            int normalSpeedIndex = 3; //Index Of NORMAL Speed IS 3
            if (!currentAudioSpeed_new.isEmpty()) {
                currentAudioSpeedIndex = currentAudioSpeedIndex - 1;
                if (!(currentAudioSpeedIndex < 0)) {
                    if (audioPlayBackSpeedList[currentAudioSpeedIndex].equalsIgnoreCase("NORMAL")) {
                        PlaybackParameters playbackParameters = new PlaybackParameters(1f);
                        player.setPlaybackParameters(playbackParameters);
                    } else {
                        PlaybackParameters playbackParameters = new PlaybackParameters(Float.parseFloat(audioPlayBackSpeedList[currentAudioSpeedIndex]));
                        player.setPlaybackParameters(playbackParameters);
                    }
                }
            } else {
                PlaybackParameters playbackParameters = new PlaybackParameters(1f);
                player.setPlaybackParameters(playbackParameters);
            }
            if (!(currentAudioSpeedIndex < 0)) {
                currentAudioSpeed_new = audioPlayBackSpeedList[currentAudioSpeedIndex];
            }
        } else if (action.equalsIgnoreCase("audio_fast")) {
            String currentAudioSpeed = currentAudioSpeed_new;
            int currentAudioSpeedIndex = Arrays.asList(audioPlayBackSpeedList).indexOf(currentAudioSpeed);
            int normalSpeedIndex = 3; //Index Of NORMAL Speed IS 3
            if (!currentAudioSpeed.isEmpty()) {
                currentAudioSpeedIndex = currentAudioSpeedIndex + 1;
                if (!(currentAudioSpeedIndex == audioPlayBackSpeedList.length)) {
                    if (audioPlayBackSpeedList[currentAudioSpeedIndex].equalsIgnoreCase("NORMAL")) {
                        PlaybackParameters playbackParameters = new PlaybackParameters(1f);
                        player.setPlaybackParameters(playbackParameters);
                    } else {
                        PlaybackParameters playbackParameters = new PlaybackParameters(Float.parseFloat(audioPlayBackSpeedList[currentAudioSpeedIndex]));
                        player.setPlaybackParameters(playbackParameters);
                    }
                }
            } else {
                PlaybackParameters playbackParameters = new PlaybackParameters(1f);
                player.setPlaybackParameters(playbackParameters);
            }
            if (!(currentAudioSpeedIndex == audioPlayBackSpeedList.length)) {
                currentAudioSpeed_new = audioPlayBackSpeedList[currentAudioSpeedIndex];
            }
        }
    }
}
