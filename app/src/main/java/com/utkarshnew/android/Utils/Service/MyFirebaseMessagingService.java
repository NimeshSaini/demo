package com.utkarshnew.android.Utils.Service;

import static com.utkarshnew.android.home.Activity.HomeActivity.homeactivity;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.exoplayer2.util.Util;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.utkarshnew.android.home.Activity.HomeActivity;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.Notification.NotificationUtils;
import com.utkarshnew.android.Utils.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.Random;

import me.leolin.shortcutbadger.ShortcutBadger;


@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String CHANNEL_ID = "Utkarsh";
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            try {
                JSONObject json = new JSONObject(Objects.requireNonNull(remoteMessage.getNotification().getBody()));
                Log.d(TAG, "onMessageReceived: "+json);
                handleDataMessage(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                if (remoteMessage.getData().containsKey(Const.MESSAGE) && remoteMessage.getData().containsKey(Const.TYPE)) {
                    JSONObject json = new JSONObject(remoteMessage.getData().get(Const.MESSAGE).toString());
                    if (SharedPreference.getInstance().getBoolean(Const.IS_USER_LOGGED_IN) && !SharedPreference.getInstance().getBoolean(Const.IS_NOTIFICATION_BLOCKED)) {
                        handleDataMessage(json);
                    }
                } else if (remoteMessage.getData().containsKey(Const.MESSAGE) && remoteMessage.getData().size() == 1) {
                    JSONObject json = new JSONObject(remoteMessage.getData().get(Const.MESSAGE).toString());
                    handleDataMessage(json);
                }
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void handleNotification(String message) {
        Intent pushNotification = new Intent();
        pushNotification.setAction("android.intent.action.MAIN");
        pushNotification.putExtra(Const.MESSAGE, message);
        sendBroadcast(pushNotification);

        NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
        notificationUtils.playNotificationSound();
    }

    private void handleDataMessage(JSONObject json) {
        createNotificationChannel();
        try {
            Intent intent = null;
            String message = json.optString(Const.MESSAGE);
            String title = json.optString(Const.TITLE);
            JSONObject data = null;
            String post_id = null, message_target = null, comment_id = null, url = null,url_2=null, id = null, course_id = null;
            int notification_code = json.optInt(Const.NOTIFICATION_CODE);
            if (json.optJSONObject(Const.DATA) != null) {
                data = json.getJSONObject(Const.DATA);
                message_target = data.optString(Const.MESSAGE_TARGET);
                url = data.optString(Const.URL);
                url_2 = data.optString("url_2");
                course_id = data.optString(Const.COURSE_ID);
            }
            if (!(notification_code == 110110)) {
                handleNotification(message);
            }

            if (notification_code == 90002) { // (notification_code == 90002   for Live Video notification
                intent = new Intent(this, HomeActivity.class);
                JSONObject jsonObject = json.optJSONObject("data").getJSONObject("additional_json");
                String courseid, fileId, topicId, tileType, revertApi, tileId;
                courseid = jsonObject.optString("course_id");
                fileId = jsonObject.optString("file_id");
                topicId = jsonObject.optString("topic_id");
                tileType = jsonObject.optString("tile_type");
                revertApi = jsonObject.optString("revert_api");
                tileId = jsonObject.optString("tile_id");
                // for live notification will go to feeds fragment.
                intent.putExtra(Const.NOTIFICATION_CODE, notification_code);
                intent.putExtra(Const.COURSE_ID, courseid);
                intent.putExtra(Const.FileID, fileId);
                intent.putExtra(Const.TOPIC_ID, topicId);
                intent.putExtra(Const.TILE_TYPE, tileType);
                intent.putExtra(Const.TILE_ID, tileId);
                intent.putExtra(Const.REVERT_API, revertApi);
                intent.putExtra(Const.TYPE, Const.ONLINE_COURSES);
                SharedPreference.getInstance().putInt(Const.NOTIFICATION_COUNT, SharedPreference.getInstance().getInt(Const.NOTIFICATION_COUNT) + 1);
                if (homeactivity instanceof HomeActivity) {
                    ((HomeActivity) homeactivity).homeAutoRefresh();
                }
            } else if (notification_code == 90003) {
                intent = new Intent(this, HomeActivity.class);

            } else if (notification_code == 90001) {
                if (Objects.requireNonNull(message_target).equalsIgnoreCase("6")) {

                    intent = new Intent(this, HomeActivity.class);
                    intent.putExtra("title", title);
                    intent.putExtra(Const.NOTIFICATION_CODE, 90001);
                    intent.putExtra("url", url);
                    intent.putExtra("target", message_target);
                    SharedPreference.getInstance().putInt(Const.NOTIFICATION_COUNT, SharedPreference.getInstance().getInt(Const.NOTIFICATION_COUNT) + 1);
                    if (homeactivity instanceof HomeActivity) {
                        ((HomeActivity) homeactivity).homeAutoRefresh();
                    }
                } else if (message_target.equalsIgnoreCase("4")) { // to open a video Chanel Specific
//                    intent = new Intent(this, Video_Detail_IBT.class);
                    intent.putExtra(Const.URL, url);
                    intent.putExtra(Const.TYPE, Const.NOTIFICATION);
                    intent.putExtra(Const.NOTIFICATION_CODE, 90001);
                    intent.putExtra("target", message_target);
                    SharedPreference.getInstance().putInt(Const.NOTIFICATION_COUNT, SharedPreference.getInstance().getInt(Const.NOTIFICATION_COUNT) + 1);
                    if (homeactivity instanceof HomeActivity) {
                        ((HomeActivity) homeactivity).homeAutoRefresh();
                    }
                } else if (message_target.equalsIgnoreCase("2")) { //
                    intent = new Intent(this, HomeActivity.class);
                    intent.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                    intent.putExtra(Const.COURSE_ID, course_id);
                    intent.putExtra(Const.NOTIFICATION_CODE, 90001);
                    intent.putExtra("target", message_target);
                    intent.putExtra(Const.COURSE_PARENT_ID, "");
                    intent.putExtra(Const.IS_COMBO, false);
                    SharedPreference.getInstance().putString(Const.ID, course_id);
                    SharedPreference.getInstance().putInt(Const.NOTIFICATION_COUNT, SharedPreference.getInstance().getInt(Const.NOTIFICATION_COUNT) + 1);
                    if (homeactivity instanceof HomeActivity) {
                        ((HomeActivity) homeactivity).homeAutoRefresh();
                    }
                } else if (message_target.equalsIgnoreCase("5")) {
                    intent = new Intent(this, HomeActivity.class);
                    intent.putExtra("urlType", "IMAGE");
                    intent.putExtra("title", title);
                    intent.putExtra("target", message_target);
                    intent.putExtra("url", url);
                    intent.putExtra(Const.NOTIFICATION_CODE, 90001);
                    intent.putExtra("description", message);
                    SharedPreference.getInstance().putInt(Const.NOTIFICATION_COUNT, SharedPreference.getInstance().getInt(Const.NOTIFICATION_COUNT) + 1);
                    if (homeactivity instanceof HomeActivity) {
                        ((HomeActivity) homeactivity).homeAutoRefresh();
                    }
                } else if (message_target.equalsIgnoreCase("1")) {
                    //intent = new Intent(this, HomeActivity.class);// notification fragment
                    intent = new Intent(this, HomeActivity.class);
                    intent.putExtra("urlType", "GENERAL");
                    intent.putExtra("title", title);
                    intent.putExtra("target", message_target);
                    intent.putExtra(Const.NOTIFICATION_CODE, 90001);
                    intent.putExtra("url", url);
                    intent.putExtra("description", message);
                    SharedPreference.getInstance().putInt(Const.NOTIFICATION_COUNT, SharedPreference.getInstance().getInt(Const.NOTIFICATION_COUNT) + 1);
                    if (homeactivity instanceof HomeActivity) {
                        ((HomeActivity) homeactivity).homeAutoRefresh();
                    }
                } else if (message_target.equalsIgnoreCase("8")) {
                    JSONObject jsonObject = Objects.requireNonNull(json.optJSONObject("data")).getJSONObject("additional_json");
                    String coupon_for = jsonObject.optString("coupon_for");
                    String ts = jsonObject.optString("start");

                    intent = new Intent(this, HomeActivity.class);
                    intent.putExtra("urlType", "GENERAL");
                    intent.putExtra("title", title);
                    intent.putExtra("coupon_for", coupon_for);
                    intent.putExtra("ts", Long.parseLong(ts));
                    intent.putExtra("target", message_target);
                    intent.putExtra(Const.NOTIFICATION_CODE, 90001);
                    intent.putExtra("url", url);
                    intent.putExtra("description", message);
                    SharedPreference.getInstance().putInt(Const.NOTIFICATION_COUNT, SharedPreference.getInstance().getInt(Const.NOTIFICATION_COUNT) + 1);
                    if (homeactivity instanceof HomeActivity) {
                        ((HomeActivity) homeactivity).homeAutoRefresh();
                    }
                } else if (message_target.equalsIgnoreCase("7"))
                {
                    JSONObject jsonObject =new JSONObject(json.getJSONObject("data").optString("additional_json"));

                    // JSONObject jsonObject = Objects.requireNonNull(json.optJSONObject("data")).getJSONObject("additional_json");
                    String master_cat = jsonObject.optString("master_cat");
                    String main_cat = jsonObject.optString("main_cat");
                    String sub_cat = jsonObject.optString("sub_cat");

                    intent = new Intent(this, HomeActivity.class);
                    intent.putExtra("urlType", "GENERAL");
                    intent.putExtra("title", title);
                    intent.putExtra("target", message_target);
                    intent.putExtra("master_cat", master_cat);
                    intent.putExtra("main_cat", main_cat);
                    intent.putExtra("sub_cat", sub_cat);
                    intent.putExtra(Const.NOTIFICATION_CODE, 90001);
                    if (homeactivity instanceof HomeActivity) {
                        ((HomeActivity) homeactivity).homeAutoRefresh();
                    }
                }
                else {
                    intent = new Intent(this, HomeActivity.class);
                    intent.putExtra("urlType", "GENERAL");
                    intent.putExtra("title", title);
                    intent.putExtra("target", message_target);
                    intent.putExtra(Const.NOTIFICATION_CODE, 90001);
                    intent.putExtra("url", url);
                    intent.putExtra("description", message);
                    SharedPreference.getInstance().putInt(Const.NOTIFICATION_COUNT, SharedPreference.getInstance().getInt(Const.NOTIFICATION_COUNT) + 1);
                    if (homeactivity instanceof HomeActivity) {
                        ((HomeActivity) homeactivity).homeAutoRefresh();
                    }
                }

            }
            if (notification_code == 110110) {
                getLogoutDialog(getApplicationContext(), title, message);
            } else {
                showNotification(message, title, intent, url, message_target);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */

    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }


    public void getLogoutDialog(final Context ctx, final String title, final String message) {
        Helper.SignOutUsernotifi(ctx);
    }

    public void showNotification(String pushMessage, String pushTitle, Intent intent, String url, String message_target) {
        Spanned title = Html.fromHtml(pushTitle);
        Spanned mes = Html.fromHtml(pushMessage);
        Random random = new Random();
        int notificationId = random.nextInt(10000);
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.both);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);


        int pendingFlags = 0;
        if (Util.SDK_INT >= 23) {
            pendingFlags = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE;
        } else {
            pendingFlags = PendingIntent.FLAG_UPDATE_CURRENT;
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, pendingFlags);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notif_icon)
                .setLargeIcon(Bitmap.createScaledBitmap(icon, 70, 70, false))
                // .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_notif_icon))
                .setContentTitle(title)
                .setContentText(mes)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(mes))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setColor(getApplicationContext().getResources().getColor(R.color.colorPrimaryDark))
                .setVibrate(new long[]{500, 500, 500, 500, 500})
                .setContentIntent(pendingIntent);
        if (message_target.equalsIgnoreCase("5")) {
            if (!TextUtils.isEmpty(url)) {
                Bitmap myBitmap;
                try {
                    URL urlImg = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) urlImg.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    myBitmap = BitmapFactory.decodeStream(input);
                } catch (IOException e) {
                    myBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                }
                NotificationCompat.BigPictureStyle bps = new NotificationCompat.BigPictureStyle().bigPicture(myBitmap);
                notificationBuilder.setStyle(bps);
            }

        }


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        ShortcutBadger.applyCount(getApplicationContext(), SharedPreference.getInstance().getInt(Const.NOTIFICATION_COUNT));
        notificationManager.notify(notificationId, notificationBuilder.build());
    }
}