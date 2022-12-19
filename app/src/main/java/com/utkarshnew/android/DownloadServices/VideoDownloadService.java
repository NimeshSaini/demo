package com.utkarshnew.android.DownloadServices;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.table.VideosDownload;
import com.utkarshnew.android.Utils.MakeMyExam;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.Key;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLException;

import static java.net.HttpURLConnection.HTTP_CLIENT_TIMEOUT;
import static java.net.HttpURLConnection.HTTP_GATEWAY_TIMEOUT;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static java.net.HttpURLConnection.HTTP_UNAVAILABLE;

public class VideoDownloadService extends CustomIntentService {
    public static String LOCAL_ENCRYPTION_KEY = "abcdefgh123456yz";
    public static final String URL = "urlpath";
    public static final String FILENAME = "filename";
    public static final String FILEPATH = "filepath";
    public static final String RESULT = "result";
    public static final String RES_ID = "resourceId";
    public static final String POS = "pos";

    public static final String FILE_SIZE = "size";
    public static final String CURRENT_PROGRESS_TEXT = "current_progress_text";
    public static final String CURRENT_PERCENT_DOWNLOADED = "current_percentage";
    public static final String VIDEO_DOWNLOAD_ACTION = "video_download_service_receiver";
    public static final String VIDEO_DOWNLOAD_PROGRESS = "video_download_progress";
    public static final String MESSAGE = "message";
    public static final String CHAPTER_ELEMENT = "chapter_element";
    public static final String CHAPTER_INDEX = "chapter_index";
    public static final String DOWNLOAD_SERVICE_ID = "download_service_id";
    public static final String CANCEL = "cancel";
    public static final String CANCEL_ALL = "cancelAll";
    public static final String PAUSE = "pause";
    public static final String FROM_TAB = "fromTab";
    public static final String FILEDOWNLOADSTATUS = "resume_status";
    public static final String DOWNLOADED_VIDEOS = "/.Videos/";
    public static final String DOWNLOADING_VIDEOS = "/.processing/";
    public static final int NOT_ENOUGH_MEMORY = -198;
    public static final int VIDEO_FILE_EXIST = -101;
    public static final int EXCEPTION_OCCURRED = -110;
    public static final int NOT_AVAILABLE_ON_SERVER = -111;
    public static final int VIDEO_DOWNLOAD_SUCCESSFUL = 1;
    public static final int VIDEO_DOWNLOAD_PAUSED = 2;
    public static final int ONTASK = 1090;

    public static final int VIDEO_DOWNLOAD_PAUSED_NO_INTERNET = 5;
    public static final int VIDEO_DOWNLOAD_CANCELLED = 3;
    public static final int VIDEO_DOWNLOAD_RESUMED = 4;
    public static final int VIDEO_DOWNLOAD_STARTED = 6;
    private long file_size;
    private String downloadServiceId = "";
    public static String action = "";
    private String originalFileLengthString = "";
    private long lengthLong;
    private Key pausedKey, secretKey;
    private Cipher ecipher, pausedEcipher;
    private Gson gson;
    private long total;
    OutputStream fileOutput;
    InputStream inputStream;
    String lengthInMb;
    int percentage, prevPercentage;
    NotificationManager mNotificationManager;
    public static boolean isServiceRunning = false;
    public static String video_id = "";
    CipherOutputStream cipherOutputStream;
    File downloadingFile;

    private Thread thread;
    String urlPath, filePath = "", notification_name = "", token = "";
    boolean onPauseCalled = false;
    UtkashRoom myDBClass;
    public static int pos = 0;

    String video_time = "";
    private String TAG = getClass().getSimpleName();

    public VideoDownloadService() {
        super("VideoDownloadService");
    }

    protected void onHandleIntent(Intent intent) {

        isServiceRunning = true;


        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (mNotificationManager != null) {
            mNotificationManager.cancelAll();
        }
        downloadServiceId = intent.getStringExtra(DOWNLOAD_SERVICE_ID);
        filePath = intent.getStringExtra("name");
        token = intent.getStringExtra("token");

        pos = intent.getIntExtra("pos", 0);

        /////////video_id_muser_id


        /////////videoname/////////
        notification_name = intent.getStringExtra(FILEPATH);
        NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(getApplicationContext(), getPackageName())
                            .setContentTitle(notification_name)
                            .setOngoing(true)
                            .setAutoCancel(true)
                            .setSmallIcon(android.R.drawable.stat_sys_download)
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(""))
                            .setPriority(Notification.PRIORITY_MAX)
                            .setContentText("Preparing...")
                            .setTicker("Preparing...");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String channelId = "Utkarsh_download";
                NotificationChannel channel = new NotificationChannel(channelId,
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_DEFAULT);
                channel.setSound(null, null);
                channel.enableLights(false);
                channel.enableVibration(false);
                if (mNotificationManager != null) {
                    mNotificationManager.createNotificationChannel(channel);
                }
                mBuilder.setChannelId(channelId);
            }
            myDBClass = UtkashRoom.getAppDatabase(MakeMyExam.getAppContext());

            mBuilder.setProgress(100, 0, true);
            startForeground(11111, mBuilder.build());


            video_id = downloadServiceId;
            if (myDBClass.getvideoDownloadao().isvideo_exit(video_id, MakeMyExam.userId)) {
                VideosDownload videosDownload = myDBClass.getvideoDownloadao().getvideo_byuserid(video_id, MakeMyExam.userId);
                if (!videosDownload.getVideo_status().equalsIgnoreCase("Downloading Pause")) {
                    if (intent.getStringExtra("status") != null) {
                        myDBClass.getvideoDownloadao().update_progress(downloadServiceId, "0", "Downloading Running", MakeMyExam.userId);
                        publishResults("", VIDEO_DOWNLOAD_STARTED, "");
                    }
                }

            }


        action = "";
        String resId = intent.getStringExtra(DOWNLOAD_SERVICE_ID);

        VideosDownload videosDownload = myDBClass.getvideoDownloadao().getuser(resId, "0");

        urlPath = intent.getStringExtra(URL);


        boolean isResumed = intent.getBooleanExtra(FILEDOWNLOADSTATUS, false);
        final File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + DOWNLOADED_VIDEOS + filePath + ".mp4");
        downloadingFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + DOWNLOADING_VIDEOS + filePath + ".mp4");

        if (myDBClass.getvideoDownloadao().isvideo_exit(resId, MakeMyExam.userId) && !videosDownload.getVideo_status().equalsIgnoreCase("Downloading Pause")) {
            HttpsURLConnection conn = null;
            try {
                java.net.URL urlObj = new URL(urlPath);
                if (!file.exists()) {

                    conn = (HttpsURLConnection) urlObj.openConnection();
                    conn.setRequestProperty("Connection", "keep-alive");
                    conn.setReadTimeout(Integer.MAX_VALUE);
                    gson = new Gson();
                    total = 0;
                    if (isResumed) {
                        if (myDBClass.getvideoDownloadao().isRecordExistsUserId(downloadServiceId, "0", MakeMyExam.userId)) {
                            total = downloadingFile.length();
                        } else {
                            total = 0;
                        }
                        conn.setRequestProperty("Range", "bytes=" + (total) + "-");
                    } else {
                        total = 0;
                    }

                    conn.connect();


                    int responseCode = conn.getResponseCode();
                    final String fileLengthStr = conn.getHeaderField("content-length");

                    if ((responseCode == HttpsURLConnection.HTTP_OK || responseCode == HttpsURLConnection.HTTP_PARTIAL) &&
                            fileLengthStr != null && !fileLengthStr.equalsIgnoreCase("null") && !"0".equalsIgnoreCase(fileLengthStr)) {
                        if (isResumed) {
                            originalFileLengthString = myDBClass.getvideoDownloadao().getuser(downloadServiceId, "0").getOriginalFileLengthString();
                            lengthLong = Long.parseLong(originalFileLengthString);
                        } else {
                            originalFileLengthString = fileLengthStr;
                            lengthLong = Long.parseLong(fileLengthStr);
                        }
                        lengthInMb = fileSize(lengthLong);
                        if (isMemoryAvailable(lengthLong)) {

                            file.getParentFile().mkdirs();

                            if (!downloadingFile.getParentFile().exists())
                                downloadingFile.getParentFile().mkdirs();
                            if (!downloadingFile.exists())
                                downloadingFile.createNewFile();

                            if (isResumed) {
                                publishResults(downloadingFile.getAbsolutePath(), VIDEO_DOWNLOAD_RESUMED, "");
                            } else {
                                publishResults("", VIDEO_DOWNLOAD_STARTED, "");
                                myDBClass.getvideoDownloadao().update_videofilelenght(downloadServiceId, originalFileLengthString, total, lengthInMb, percentage, "0", "Downloading Running", video_time, urlPath);
                            }


                            fileOutput = new FileOutputStream(downloadingFile, isResumed);
                            inputStream = conn.getInputStream();

                            byte[] buffer = new byte[4096];
                            int bufferLength;
                            percentage = 0;
                            prevPercentage = 0;
                            try {
                                if (token.equals("")) {
                                    Cipher ecipher = Cipher.getInstance("AES/CTR/NoPadding");
                                    int blockSize = ecipher.getBlockSize();
                                    IvParameterSpec iv = new IvParameterSpec(new byte[blockSize]);
                                    SecretKey secretKey = new SecretKeySpec(LOCAL_ENCRYPTION_KEY.getBytes(), "AES/CTR/NoPadding");

                                    long offset = downloadingFile.length();
                                    if (isResumed) {
                                        final int skip = (int) (offset % blockSize);
                                        final IvParameterSpec calculatedIVForOffset = calculateIVForOffset(iv,
                                                offset - skip);

                                        ecipher.init(Cipher.ENCRYPT_MODE, secretKey, calculatedIVForOffset);
                                        final byte[] skipBuffer = new byte[skip];
                                        ecipher.update(skipBuffer, 0, skip, skipBuffer);
                                        Arrays.fill(skipBuffer, (byte) 0);
                                    } else
                                        ecipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

                                    cipherOutputStream = new CipherOutputStream(fileOutput, ecipher);
                                }
                                while ((bufferLength = inputStream.read(buffer)) > 0) {
                                    switch (action) {
                                        case "":
                                            total += bufferLength;
                                            if (token.equals("")) {
                                                cipherOutputStream.write(buffer, 0, bufferLength);
                                            } else {
                                                fileOutput.write(buffer, 0, bufferLength);
                                            }

                                            percentage = (int) ((total * 100) / lengthLong);
                                            if (percentage - prevPercentage >= 1) {
                                                String progressText = fileSize(total) + "/" + lengthInMb;
                                                mBuilder.setProgress(100, percentage, false);
                                                mBuilder.setContentText(progressText);
                                                myDBClass.getvideoDownloadao().update_videofilelenght(downloadServiceId, originalFileLengthString, total, lengthInMb, percentage, "0", "Downloading Running", video_time, urlPath);
                                                mNotificationManager.notify(11111, mBuilder.build());
                                                publishDownloadProgress(resId, progressText, percentage);
                                            }
                                            prevPercentage = percentage;
                                            break;
                                        case PAUSE:
                                            if (!onPauseCalled) {
                                                pausedKey = secretKey;
                                                myDBClass.getvideoDownloadao().update_videofilelenght(downloadServiceId, originalFileLengthString, total, lengthInMb, percentage, "0", "Downloading Pause", video_time, urlPath);

                                                fileOutput.close();
                                                inputStream.close();

                                                if (token.equals(""))
                                                    cipherOutputStream.close();

                                                if (mNotificationManager != null) {
                                                    mNotificationManager.cancel(11111);
                                                }
                                                notifyDownloadState(notification_name, "Download has been paused by user");
                                            }
                                            publishResults(downloadingFile.getAbsolutePath(), VIDEO_DOWNLOAD_PAUSED, "");
                                            onPauseCalled = false;
                                            return;
                                        case CANCEL:
                                            fileOutput.flush();
                                            fileOutput.close();
                                            inputStream.close();
                                            if (token.equals(""))
                                                cipherOutputStream.close();
                                            if (downloadingFile.exists())
                                                downloadingFile.delete();

                                            if (mNotificationManager != null) {
                                                mNotificationManager.cancel(11111);
                                            }
                                            notifyDownloadState(notification_name, "Download is cancelled by user");
                                            publishResults(downloadingFile.getAbsolutePath(), VIDEO_DOWNLOAD_CANCELLED, "");
                                            return;
                                        case CANCEL_ALL:
                                            fileOutput.flush();
                                            fileOutput.close();
                                            inputStream.close();
                                            if (token.equals(""))
                                                cipherOutputStream.close();
                                            if (downloadingFile.exists()) {
                                                downloadingFile.delete();
                                            }

                                            if (mNotificationManager != null) {
                                                mNotificationManager.cancel(11111);
                                            }
                                            notifyDownloadState(notification_name, "Download is cancelled because user logged out");

                                            publishResults(downloadingFile.getAbsolutePath(), VIDEO_DOWNLOAD_CANCELLED, "");
                                            return;
                                    }
                                }
                                fileOutput.flush();
                                fileOutput.close();
                                inputStream.close();
                                if (token.equals(""))
                                    cipherOutputStream.close();
                                downloadingFile.renameTo(file);
                                if (mNotificationManager != null) {
                                    mNotificationManager.cancel(11111);
                                }
                                publishResults(file.getAbsolutePath(), VIDEO_DOWNLOAD_SUCCESSFUL, "success");
                            } catch (Exception e) {
                                if (e instanceof SSLException) {
                                    //Pause if no internet
                                    try {
                                        fileOutput.flush();
                                        fileOutput.close();
                                        inputStream.close();
                                        if (mNotificationManager != null) {
                                            mNotificationManager.cancel(11111);
                                        }
                                        notifyDownloadState(notification_name, "Internet Issue. Download is not completed Please retry!!!");
                                        publishResults(downloadingFile.getAbsolutePath(), VIDEO_DOWNLOAD_PAUSED, "");

                                    } catch (IOException ex) {
                                        Log.e(TAG, "Exception no net: " + ex);
                                        notifyDownloadState(notification_name, "Connection lost!!!. Please make sure network availability.");
                                    }
                                } else {
                                    if (e instanceof SocketException) {
                                /*    if(fileOutput != null)
                                    {
                                        fileOutput.flush();
                                        fileOutput.close();
                                    }
                                    if(inputStream != null)
                                        inputStream.close();
                                    if(downloadingFile.exists())
                                        downloadingFile.delete();
                                    if (mNotificationManager != null){
                                        mNotificationManager.cancel(11111);
                                    }
*/
                                        if (fileOutput != null) {
                                            fileOutput.flush();
                                            fileOutput.close();
                                        }

                                        if (inputStream != null)
                                            inputStream.close();
                                        if (mNotificationManager != null) {
                                            mNotificationManager.cancel(11111);
                                        }

                                        notifyDownloadState(notification_name, "Internet Issue. Download is not completed Please retry!!!");
                                        publishResults(downloadingFile.getAbsolutePath(), VIDEO_DOWNLOAD_PAUSED, "");

                                        //   publishResults(downloadingFile.getAbsolutePath(), resId, EXCEPTION_OCCURRED, e.getMessage());
                                        Log.e(TAG, e.getMessage());
                                    } else {
                                        fileOutput.flush();
                                        fileOutput.close();
                                        inputStream.close();
                                        if (downloadingFile.exists())
                                            downloadingFile.delete();
                                        if (mNotificationManager != null) {
                                            mNotificationManager.cancel(11111);
                                        }
                                        notifyDownloadState(notification_name, "Something went wrong. Download is not completed Please retry!!!");
                                        publishResults(downloadingFile.getAbsolutePath(), resId, EXCEPTION_OCCURRED, e.getMessage());
                                        Log.e(TAG, e.getMessage());
                                    }

                                }
                            }
                        } else {
                            if (downloadingFile.exists())
                                downloadingFile.delete();
                            notifyDownloadState(notification_name, "Not Enough Memory Space");
                            publishResults(downloadingFile.getAbsolutePath(), resId, NOT_ENOUGH_MEMORY, "Not Enough Memory Space");
                        }

                    } else {
                        if (downloadingFile.exists())
                            downloadingFile.delete();

                        if (responseCode == HttpsURLConnection.HTTP_NOT_FOUND) {
                            notifyDownloadState(resId, "Video not found!!!");
                        } else if (responseCode != HttpsURLConnection.HTTP_OK)
                            notifyDownloadState(resId, getErrorMessage(responseCode));
                        publishResults(downloadingFile.getAbsolutePath(), resId, NOT_AVAILABLE_ON_SERVER, getErrorMessage(responseCode));
                    }
                } else {
                    file.delete();
                    notifyDownloadState(notification_name, "Video is already Downloaded.");
                    publishResults(file.getAbsolutePath(), resId, VIDEO_FILE_EXIST, "Video is already Downloaded.");
                }


            } catch (Exception e) {
                if (e instanceof ConnectException) {
                    //Pause if no internet
                    try {

                        if (fileOutput != null) {
                            fileOutput.flush();
                            fileOutput.close();
                        }

                        if (inputStream != null)
                            inputStream.close();
                        if (mNotificationManager != null) {
                            mNotificationManager.cancel(11111);
                        }
                        notifyDownloadState(notification_name, "Download has been paused by user");
                        publishResults(downloadingFile.getAbsolutePath(), VIDEO_DOWNLOAD_PAUSED, "");
                    } catch (IOException ex) {
                        Log.e(TAG, "Exception no net: " + ex);
                        notifyDownloadState(notification_name, "Connection lost!!!. Please make sure network availability.");
                    }
                }
                if (e instanceof SocketException) {
                    try {

                        if (fileOutput != null) {
                            fileOutput.flush();
                            fileOutput.close();
                        }
                        if (inputStream != null)
                            inputStream.close();
                        if (mNotificationManager != null) {
                            mNotificationManager.cancel(11111);
                        }
                        if (conn != null) {
                            conn.disconnect();
                        }
                    } catch (IOException ex) {
                        Log.e(TAG, "Exception no net: " + ex);
                        notifyDownloadState(notification_name, "Connection lost!!!. Please make sure network availability.");
                    }
                } else if (e instanceof IOException) {
                    if (e instanceof UnknownHostException) {
                        try {
                            if (fileOutput != null) {
                                fileOutput.flush();
                                fileOutput.close();
                            }

                            if (inputStream != null)
                                inputStream.close();
                            if (mNotificationManager != null) {
                                mNotificationManager.cancel(11111);
                            }
                            notifyDownloadState(notification_name, "Internet Issue. Download is Pause!!!");
                            VideosDownload percetage_uipdated = myDBClass.getvideoDownloadao().getvideo_byuserid(video_id, MakeMyExam.userId);
                            if (percetage_uipdated.getToal_downloadlocale() == 0) {
                                if (downloadingFile.exists())
                                    downloadingFile.delete();
                            }
                            myDBClass.getvideoDownloadao().update_videofilelenght(downloadServiceId, percetage_uipdated.getOriginalFileLengthString(), percetage_uipdated.getToal_downloadlocale(), percetage_uipdated.getLengthInMb(), percetage_uipdated.getPercentage(), "0", "Downloading Pause", percetage_uipdated.getVideotime(), urlPath);
                            publishResults(downloadingFile.getAbsolutePath(), 2021, "");

                        } catch (Exception ee) {
                            ee.printStackTrace();
                        }


                    } else {
                        notifyDownloadState(notification_name, "Internet Issue. Download is not completed Please retry!!!");
                        publishResults(downloadingFile.getAbsolutePath(), VIDEO_DOWNLOAD_PAUSED, "");
                        if (downloadingFile.exists())
                            downloadingFile.delete();
                    }

                    // notifyDownloadState(notification_name, "Couldn't save video. Please make sure network availability.");


                } else {
                    notifyDownloadState(notification_name, "Couldn't save video. Not able to create file.");
                    publishResults(downloadingFile.getAbsolutePath(), resId, EXCEPTION_OCCURRED, e.getMessage());
                    if (downloadingFile.exists())
                        downloadingFile.delete();
                }
                Log.e(TAG, "Download error", e);
             /*   if(downloadingFile.exists())
                    downloadingFile.delete();*/

            } finally {
                Log.d(TAG, "Downloading process finished");
                if (conn != null) {
                    try {
                        if (fileOutput != null) {
                            fileOutput.flush();
                            fileOutput.close();
                        }
                        if (inputStream != null)
                            inputStream.close();
                        conn.disconnect();
                        // conn.getErrorStream().close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        } else if (isResumed) {
            if (downloadingFile.exists())
                downloadingFile.delete();

            if (mNotificationManager != null) {
                mNotificationManager.cancel(11111);
            }
            notifyDownloadState(notification_name, "Download is cancelled by user");
            publishResults(downloadingFile.getAbsolutePath(), VIDEO_DOWNLOAD_CANCELLED, "");
        } else {
            if (downloadingFile.exists())
                downloadingFile.delete();

            if (mNotificationManager != null) {
                mNotificationManager.cancel(11111);
            }
            notifyDownloadState(notification_name, "Download is cancelled by user");
            publishResults(downloadingFile.getAbsolutePath(), VIDEO_DOWNLOAD_CANCELLED, "");
        }


    }

    @Override
    protected boolean cancelDownload(String downloadServiceId) {
        //Log.e(TAG, "stopDownload: " + downloadServiceId);
        if (this.downloadServiceId.equals(downloadServiceId)) {
            action = CANCEL;
            return true;
        }
        return false;
    }

    protected void cancelAllDownload() {
        action = CANCEL_ALL;
    }

    protected boolean pauseDownload(String downloadServiceId) {
        //Log.e(TAG, "pauseDownload: " + downloadServiceId);
        if (this.downloadServiceId.equals(downloadServiceId)) {
            action = PAUSE;
            return true;
        }
        return false;
    }

/*
    private void notifyDownloadSuccess(String title, String deepLink, ChapterElementsModel chapterElementsModel) {
        Intent intent = new Intent(Wonderslate.getInstance().getContext(), GifSplashScreen.class);
        intent.setData(Uri.parse(deepLink));
        int notificationId = WSFirebaseMessagingService.NotificationID.getID();
        intent.putExtra("notificationId", notificationId);
        PendingIntent pendingIntent = PendingIntent.getActivity(Wonderslate.getInstance().getContext(), 258, intent, PendingIntent.FLAG_ONE_SHOT);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(Wonderslate.getInstance().getContext(), getPackageName())
                .setSmallIcon(R.drawable.ic_notif_icon)
                .setColor(Wonderslate.getInstance().getContext().getResources().getColor(R.color.colorAccent))
                .setColorized(true)
                .setContentTitle(title)
                .setContentText("Downloaded Successfully.")
                .setPriority(Notification.PRIORITY_MAX)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "Utkarsh_download";
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Utkarsh Video Download",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
            notificationBuilder.setChannelId(channelId);
        }

        if (notificationManager != null) {
            notificationManager.notify(notificationId, notificationBuilder.build());
        }
    }
*/

    private void notifyDownloadState(String title, String message) {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int icon = message != null && !message.isEmpty() && message.contains("Successfully") ? R.drawable.ic_launcher_background : android.R.drawable.ic_dialog_alert;
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, getPackageName())
                .setSmallIcon(icon)
                .setColor(getResources().getColor(R.color.colorAccent))
                .setColorized(true)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message))
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(Notification.PRIORITY_MAX)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "Utkarsh_download";
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Utkarsh Video Download",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
            notificationBuilder.setChannelId(channelId);
        }

        if (notificationManager != null) {
            notificationManager.notify(NotificationID.getID(), notificationBuilder.build());
        }
    }

    static class NotificationID {
        private final static AtomicInteger c = new AtomicInteger(0);

        public static int getID() {
            return c.incrementAndGet();
        }
    }


    private boolean isMemoryAvailable(final long fileSize) {
        file_size = fileSize;
        long availableSize = getAvailableExternalMemorySize();
        return availableSize > fileSize;
    }

    private String fileSize(double length) {
        DecimalFormat format = new DecimalFormat("#.##");
        String convertedSizeStr;
        if (length > 1024 * 1024) {
            convertedSizeStr = format.format(length / (1024 * 1024)) + " MB";
        } else if (length > 1024) {
            convertedSizeStr = format.format(length / 1024) + " KB";
        } else {
            convertedSizeStr = format.format(length) + " B";
        }
        return convertedSizeStr;

    }

    private long getAvailableExternalMemorySize() {
        File path = getExternalFilesDir(null);
        StatFs stat = new StatFs(path.getPath());
        long blockSize, availableBlocks;
        blockSize = stat.getBlockSizeLong();
        availableBlocks = stat.getAvailableBlocksLong();
        return availableBlocks * blockSize;
    }

    private void publishDownloadProgress(String resId, String progressText, int currentProgress) {
        Intent intent = new Intent("video_download_progress");
        intent.putExtra(RES_ID, resId);
        //  Log.d("prince",""+pos);
        intent.putExtra("pos", pos);
        intent.putExtra(CURRENT_PROGRESS_TEXT, progressText);
        intent.putExtra(FILE_SIZE, file_size + "");
        intent.putExtra(CURRENT_PERCENT_DOWNLOADED, currentProgress);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.sendBroadcast(intent);
    }

    private void publishResults(String outputPath, String resId, int result, String message) {
        if (result != VIDEO_DOWNLOAD_RESUMED && result != VIDEO_DOWNLOAD_STARTED)
            stopForeground(true);
        if (VIDEO_DOWNLOAD_SUCCESSFUL == result) {
            myDBClass.getvideoDownloadao().update_videostatus(downloadServiceId, "1", "Downloaded", percentage);

        } else if (result == EXCEPTION_OCCURRED || result == VIDEO_DOWNLOAD_CANCELLED || result == NOT_AVAILABLE_ON_SERVER || result == NOT_ENOUGH_MEMORY) {
            myDBClass.getvideoDownloadao().delete_viavideoid(downloadServiceId, MakeMyExam.userId);
        } else if (VIDEO_FILE_EXIST == result) {
        } else if (result == VIDEO_DOWNLOAD_PAUSED) {
            myDBClass.getvideoDownloadao().update_videofilelenght(downloadServiceId, originalFileLengthString, total, lengthInMb, percentage, "0", "Downloading Pause", video_time, urlPath);

        } else if (result == ONTASK) {
            UtkashRoom.destroyInstance();

        }
        Intent intent = new Intent(VIDEO_DOWNLOAD_ACTION);
        intent.putExtra(FILEPATH, outputPath);
        intent.putExtra(RESULT, result);
        intent.putExtra(MESSAGE, message);
        intent.putExtra(FILE_SIZE, file_size + "");
        intent.putExtra(RES_ID, downloadServiceId);
        intent.putExtra("percentage", percentage);
        intent.putExtra("video_time", video_time);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.sendBroadcast(intent);
        Log.e("myLog END", "" + "service END");
    }

    private void publishResults(String outputPath, int result, String message) {
        publishResults(outputPath, "", result, message);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        //Called when app is removed form recent panel

        //stopSelf();
        onPauseCalled = true;
        action = PAUSE;
        try {
            if (isServiceRunning) {
                pausedKey = secretKey;
                if (myDBClass != null) {
                    myDBClass.getvideoDownloadao().update_videofilelenght(downloadServiceId, originalFileLengthString, total, lengthInMb, percentage, "0", "Downloading Pause", video_time, urlPath);
                }
            }
            for (VideosDownload download : myDBClass.getvideoDownloadao().getalldownload_videos(MakeMyExam.userId)) {
                if (download.getVideo_status().equalsIgnoreCase("Downloading Running")) {
                    //   Log.d("prince",""+download.getVideo_id());
                    myDBClass.getvideoDownloadao().update_videostatus(download.getVideo_id(), "Downloading Pause", MakeMyExam.userId);
                }

            }

            if (fileOutput != null) {
                fileOutput.flush();
                fileOutput.close();
            }
            if (inputStream != null)
                inputStream.close();
            if (cipherOutputStream != null)
                cipherOutputStream.close();

            if (mNotificationManager != null) {
                mNotificationManager.cancel(11111);
            }
            notifyDownloadState(notification_name, "Download has been paused by user");
            publishResults(downloadingFile.getAbsolutePath(), ONTASK, "");


            isServiceRunning = false;
            video_id = "";
            onPauseCalled = false;

        } catch (Exception e) {
            Log.e(TAG, "onTaskRemoved: ", e);
        }//   FirebaseCrashlytics.getInstance().recordException(e);


    }

    @Override
    public void onDestroy() {
        isServiceRunning = false;
        video_id = "";
        super.onDestroy();
    }

    private static final int AES_BLOCK_SIZE = 16;


    private static IvParameterSpec calculateIVForOffset(final IvParameterSpec iv,
                                                        final long blockOffset) {
        final BigInteger ivBI = new BigInteger(1, iv.getIV());
        final BigInteger ivForOffsetBI = ivBI.add(BigInteger.valueOf(blockOffset
                / AES_BLOCK_SIZE));

        final byte[] ivForOffsetBA = ivForOffsetBI.toByteArray();
        final IvParameterSpec ivForOffset;
        if (ivForOffsetBA.length >= AES_BLOCK_SIZE) {
            ivForOffset = new IvParameterSpec(ivForOffsetBA, ivForOffsetBA.length - AES_BLOCK_SIZE,
                    AES_BLOCK_SIZE);
        } else {
            final byte[] ivForOffsetBASized = new byte[AES_BLOCK_SIZE];
            System.arraycopy(ivForOffsetBA, 0, ivForOffsetBASized, AES_BLOCK_SIZE
                    - ivForOffsetBA.length, ivForOffsetBA.length);
            ivForOffset = new IvParameterSpec(ivForOffsetBASized);
        }

        return ivForOffset;
    }

    public String getErrorMessage(int responseCode) {
        String message = "";
        switch (responseCode) {

            case HTTP_CLIENT_TIMEOUT:
            case -1:
                message = "Problem while connecting! please check your Internet connection.";
                break;

            case HTTP_UNAVAILABLE:
                message = "Server under maintenance! please try after some time.";
                break;

            case HTTP_GATEWAY_TIMEOUT:
            case HTTP_INTERNAL_ERROR:
                message = "Server error! please try after some time.";
                break;

            case HTTP_UNAUTHORIZED:
            case HTTP_NOT_FOUND:
                message = "Session timed out. Kindly login again.";
                break;
            case -2:
                message = "Something went wrong! Please try again.";
                break;
            case -3:
                message = "Sorry, we are facing some problems regarding payment.\nPlease try again later.";
                break;
            default:
                message = "Something went wrong, please try again after some time.";
                break;
        }
        return message;
    }

    public void stopselef() {
        stopSelf();
    }
}