package com.utkarshnew.android.Utils;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ProgressEvent;
import com.amazonaws.services.s3.model.ProgressListener;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.utkarshnew.android.Model.MediaFile;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.Utils.Network.API;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;


public class TestService extends IntentService {
    public static final String action = "test_json";
    public static boolean is_uploading = false;
    public String chatnode = "";
    public String test_file_name = "";
    public boolean response = false;
    private CognitoCachingCredentialsProvider credentialsProvider = null;
    String MY_OBJECT_KEY;
    Long contentLength;
    NotificationManager mNotificationManager;
    String amazonFileUploadLocationOriginal;
    public static volatile boolean shouldStop = false;
    ArrayList<MediaFile> mediaFiles = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            startForeground(1, new Notification());

        amazonFileUploadLocationOriginal = API.API_AMAZON_S3_BUCKET_NAME_JSON_IMAGE;
        credentialsProvider = new CognitoCachingCredentialsProvider(
                this,
                Const.CONGNITO_POOL_ID, // Identity pool ID
                Regions.AP_SOUTH_1 // Region
        );

    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return START_NOT_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startMyOwnForeground() {
        String NOTIFICATION_CHANNEL_ID = "com.utkarshnew.android";
        String channelName = "Utkarsh";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setSound(null, null);
        chan.enableLights(false);
        chan.enableVibration(false);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("App is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);
    }

    public TestService() {
        super(TestService.class.getSimpleName());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            is_uploading = true;
            chatnode = intent.getStringExtra("test_id");
            test_file_name = intent.getStringExtra("test_file_name");
            mediaFiles = (ArrayList<MediaFile>) intent.getSerializableExtra("mediaFileArrayList");

            byte[] content = null;
            PutObjectRequest putObjectRequest = null;
            AmazonS3Client amazonClient = new AmazonS3Client(credentialsProvider);
            amazonClient.setEndpoint(Const.AMAZON_S3_END_POINT);

            if (mediaFiles.size() > 0) {
                MediaFile file = mediaFiles.get(0);

                if (file.getFile_type().equals(Const.json))
                    MY_OBJECT_KEY = test_file_name;

                Log.e("Etag:", " MY_OBJECT_KEY: " + MY_OBJECT_KEY);

                if (file.getFile_type().equals(Const.json)) {
                    content = Helper.FileToByteArray(file.getFile());
                }
                ByteArrayInputStream bs = new ByteArrayInputStream(content);
                ObjectMetadata objectMetadata = new ObjectMetadata();
                contentLength = Long.valueOf(content.length);
                objectMetadata.setContentLength(contentLength);

                if (amazonFileUploadLocationOriginal.equalsIgnoreCase(Const.AMAZON_S3_BUCKET_TEST)) {
                    amazonFileUploadLocationOriginal = amazonFileUploadLocationOriginal + chatnode;
                }
                putObjectRequest = new PutObjectRequest(amazonFileUploadLocationOriginal, MY_OBJECT_KEY, bs, objectMetadata);

                putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
                putObjectRequest.setProgressListener(new ProgressListener() {
                    int total = 0;

                    @Override
                    public void progressChanged(ProgressEvent pv) {
                        Integer[] valuesProgress = new Integer[2];
                        valuesProgress[0] = Integer.parseInt(String.valueOf(contentLength)) * 100;
                        total += pv.getBytesTransfered();
                        if (total > 0) {
                            Log.d("prince", "" + valuesProgress[0] / total);
                            valuesProgress[1] = total;
                        }
                    }
                });

                amazonClient.putObject(putObjectRequest);
                if (amazonFileUploadLocationOriginal.equalsIgnoreCase((API.API_AMAZON_S3_BUCKET_NAME_JSON_IMAGE) + chatnode)) {
                    response = true;
                    is_uploading = false;
                    if (new File(file.getFile()).exists()) {
                        new File(file.getFile()).delete();
                    }
                    shouldStop = true;
                }

                Intent intent1 = new Intent(action);
                intent1.putExtra("response", response);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent1);
            }
        } catch (Exception e) {
            shouldStop = true;
            response = false;
            e.printStackTrace();
        } finally {
            is_uploading = false;
            shouldStop = true;
            if (shouldStop) {
                stopSelf();
            }
        }

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        shouldStop = true;
        if (shouldStop) {
            stopSelf();
        }
        UtkashRoom.destroyInstance();

    }
}