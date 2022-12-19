package com.utkarshnew.android.Download;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.gson.Gson;
import com.utkarshnew.android.Download.Adapter.DownloadVideoAdapter;
import com.utkarshnew.android.Download.Interface.onItemClick;
import com.utkarshnew.android.DownloadServices.VideoDownloadService;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.home.model.MyCourse;
import com.utkarshnew.android.Model.Courselist;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.table.APITABLE;
import com.utkarshnew.android.table.MycourseTable;
import com.utkarshnew.android.table.VideosDownload;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;

import static com.utkarshnew.android.DownloadServices.VideoDownloadService.EXCEPTION_OCCURRED;
import static com.utkarshnew.android.DownloadServices.VideoDownloadService.NOT_AVAILABLE_ON_SERVER;
import static com.utkarshnew.android.DownloadServices.VideoDownloadService.RES_ID;
import static com.utkarshnew.android.DownloadServices.VideoDownloadService.VIDEO_DOWNLOAD_CANCELLED;
import static com.utkarshnew.android.DownloadServices.VideoDownloadService.VIDEO_DOWNLOAD_PAUSED;
import static com.utkarshnew.android.DownloadServices.VideoDownloadService.VIDEO_DOWNLOAD_STARTED;
import static com.utkarshnew.android.DownloadServices.VideoDownloadService.VIDEO_DOWNLOAD_SUCCESSFUL;
import static com.utkarshnew.android.DownloadServices.VideoDownloadService.VIDEO_FILE_EXIST;
import static com.utkarshnew.android.Utils.Helper.isvisible;

public class DownloadActivity extends AppCompatActivity implements onItemClick, NetworkCall.MyNetworkCallBack {

    RelativeLayout no_data_found_RL;
    Button backBtn;
    RecyclerView download_recycler;
    NestedScrollView nestedScrollView;
    private int downloading_pos;
    public UtkashRoom utkashRoom;
    private ImageView image_back;
    private CheckBox select_all_delete;
    private List<VideosDownload> download_videos = new ArrayList<>();

    ArrayList<String> video_ids = new ArrayList<>();
    String api_send_file_ids = "";
    private DownloadVideoAdapter downloadVideoAdapter;
    private TextView delete;
    RelativeLayout layout;
    private String issue_type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.activity_download);
        try {
            if (Build.VERSION.SDK_INT >= 21) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
                utkashRoom = UtkashRoom.getAppDatabase(DownloadActivity.this);
                utkashRoom.getOpenHelper().getWritableDatabase().enableWriteAheadLogging();
                if (SharedPreference.getInstance().getLoggedInUser() != null && SharedPreference.getInstance().getLoggedInUser().getId() != null && !SharedPreference.getInstance().getLoggedInUser().getId().equalsIgnoreCase("0")) {
                    MakeMyExam.setUserId(SharedPreference.getInstance().getLoggedInUser().getId());
                    MakeMyExam.userId = SharedPreference.getInstance().getLoggedInUser().getId();

                }

                download_recycler = findViewById(R.id.download_recycler);
                //nestedScrollView = findViewById(R.id.nestedScrollView);

                no_data_found_RL = findViewById(R.id.no_data_found_RL);
                backBtn = findViewById(R.id.backBtn);

                select_all_delete = findViewById(R.id.select_all_delete);
                layout = findViewById(R.id.layout);
                delete = findViewById(R.id.delete);
                image_back = findViewById(R.id.image_back);

                FirebaseCrashlytics crashlytics = FirebaseCrashlytics.getInstance();
                crashlytics.setUserId(MakeMyExam.userId);

                image_back.setOnClickListener(new OnSingleClickListener(() -> {
                    onBackPressed();
                    return null;
                }));

                backBtn.setOnClickListener(new OnSingleClickListener(() -> {
                    onBackPressed();
                    return null;
                }));
                select_all_delete.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        if (downloadVideoAdapter != null && download_videos.size() > 0) {
                            downloadVideoAdapter.visible_layout(select_all_delete.isChecked(), download_videos);
                        }
                    } else {
                        if (downloadVideoAdapter != null && download_videos.size() > 0) {
                            downloadVideoAdapter.visible_layout(false, download_videos);
                        }
                    }
                });

                delete.setOnClickListener(v -> alert_dialog());


                if (Helper.isConnected(this)) {
                    if (utkashRoom.getMyCourseDao().isRecordExists(MakeMyExam.userId)) {
                        Helper.showProgressDialog(this);
                        logout_user_videos();
                    } else {
                        NetworkCall networkCall = new NetworkCall(this, this);
                        networkCall.NetworkAPICall(API.get_my_courses, "", true, false);
                    }
                } else {
                    Snackbar.make(no_data_found_RL, "No Internet Connection", Snackbar.LENGTH_SHORT).show();
                    Helper.showProgressDialog(this);
                    getAllVideo();
                }
            }
        } catch (Exception e) {
            Helper.dismissProgressDialog();
            e.printStackTrace();
        }

    }


    private void unstallvideos() {
        File files = new File(getExternalFilesDir(null) + "/.Videos");
        if (!files.exists() || files.listFiles() == null || Objects.requireNonNull(files.listFiles()).length == 0) {
            getbelow10videos();
            return;
        }
        File[] fileslisting = files.listFiles();
        if (Objects.requireNonNull(fileslisting).length > 0) {
            if (!Helper.isNetworkConnected(this)) {
                Snackbar.make(no_data_found_RL, "No Internet Connection", Snackbar.LENGTH_SHORT).show();
                return;
            }
        }
        List<File> tempList = new ArrayList<File>();
        getbelow10videos();

      /*  for (int i = 0; i < Objects.requireNonNull(fileslisting).length; i++) {
            if (new File(fileslisting[i].getAbsolutePath()).getName().contains("_")) {
                if (fileslisting[i].getName().split("_").length == 4) {
                    if (fileslisting[i].getName().split("_")[3].equalsIgnoreCase(SharedPreference.getInstance().getLoggedInUser().getMobile() + ".mp4")) {
                        tempList.add(fileslisting[i]);
                        video_ids.add(new File(fileslisting[i].getAbsolutePath()).getName().split("_")[2]);
                    }
                }

            }
        }
        if (video_ids.size() > 0) {
            api_send_file_ids = TextUtils.join(", ", video_ids);
            AsyncTask.execute(() -> {
                for (int i = 0; i < Objects.requireNonNull(tempList).size(); i++) {
                    if (new File(getExternalFilesDir(null).getAbsolutePath() + "/.Videos/" + tempList.get(i).getName()).exists()) {
                        moveFile(new File(tempList.get(i).getAbsolutePath()), files.getAbsolutePath() + File.separator, tempList.get(i).getName(), getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.Videos/", i, tempList.size());

                    }
                }
            });

        } else {
            getAllVideo();
        }*/

    }

    private void getbelow10videos() {
        File files = new File(Environment.getExternalStorageDirectory() + "/.Videos/");
        if (!files.exists() || files.listFiles() == null || Objects.requireNonNull(files.listFiles()).length == 0) {
            getAllVideo();
            return;
        }
        getAllVideo();
       /* File[] fileslisting = files.listFiles();
        if (Objects.requireNonNull(fileslisting).length > 0) {
            if (!Helper.isNetworkConnected(this)) {
                Snackbar.make(no_data_found_RL, "No Internet Connection", Snackbar.LENGTH_SHORT).show();
                return;
            }
        }
        for (int i = 0; i < Objects.requireNonNull(fileslisting).length; i++) {
            if (new File(fileslisting[i].getAbsolutePath()).getName().contains("_")) {
                if (fileslisting[i].getName().split("_")[3].equalsIgnoreCase(SharedPreference.getInstance().getLoggedInUser().getMobile() + ".mp4")) {
                    video_ids.add(new File(fileslisting[i].getAbsolutePath()).getName().split("_")[2]);

                }
            }
        }
        if (video_ids.size() > 0) {
            api_send_file_ids = TextUtils.join(", ", video_ids);
            AsyncTask.execute(() -> {
                for (int i = 0; i < Objects.requireNonNull(fileslisting).length; i++) {
                    if (new File(Environment.getExternalStorageDirectory() + "/.Videos/" + fileslisting[i].getName()).exists()) {
                        moveFile(new File(fileslisting[i].getAbsolutePath()), files.getAbsolutePath() + File.separator, fileslisting[i].getName(), getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.Videos/", i, fileslisting.length);


                    }
                }
            });

        } else {
            getAllVideo();
        }*/
    }

    private void getAllVideo() {
        AsyncTask.execute(() -> {
            try {
                {
                    download_videos = utkashRoom.getvideoDownloadao().getalldownload_videos(MakeMyExam.userId);
                    runOnUiThread(() -> {
                        Helper.dismissProgressDialog();
                        if (download_videos.size() > 0) {
                            //  Toast.makeText(this,""+download_videos.size(),Toast.LENGTH_SHORT).show();
                            select_all_delete.setChecked(false);
                            delete.setVisibility(View.GONE);
                            select_all_delete.setVisibility(View.VISIBLE);
                            download_recycler.setVisibility(View.VISIBLE);
                            no_data_found_RL.setVisibility(View.GONE);
                            if (downloadVideoAdapter == null) {
                                downloadVideoAdapter = new DownloadVideoAdapter(DownloadActivity.this, download_videos, this);
                                LinearLayoutManager llm = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
                                download_recycler.setLayoutManager(llm);
                                download_recycler.smoothScrollToPosition(download_videos.size() - 1);
                                // nestedScrollView.smo(download_videos.size()-1);
                                download_recycler.setAdapter(downloadVideoAdapter);
                                ((SimpleItemAnimator) Objects.requireNonNull(download_recycler.getItemAnimator())).setSupportsChangeAnimations(false);

                            } else {
                                Objects.requireNonNull(downloadVideoAdapter).notifydata(download_videos);
                            }
                        } else {
                            delete.setVisibility(View.GONE);
                            select_all_delete.setVisibility(View.GONE);
                            download_recycler.setVisibility(View.GONE);
                            no_data_found_RL.setVisibility(View.VISIBLE);

                        }
                    });

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }


    private void logout_user_videos() {

        if (utkashRoom.getvideoDownloadao().getalldownload_videos(MakeMyExam.userId).size() == 0) {

            File files = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.Videos/");
            if (!files.exists() || files.listFiles() == null || Objects.requireNonNull(files.listFiles()).length == 0) {
                unstallvideos();
                return;
            }
            File[] fileslisting = files.listFiles();
            if (Objects.requireNonNull(fileslisting).length > 0) {
                if (!Helper.isNetworkConnected(this)) {
                    Snackbar.make(no_data_found_RL, "No Internet. Please connect device to internet", Snackbar.LENGTH_LONG).show();
                    return;
                }
            }
            unstallvideos();
          /*  List<File> tempList = new ArrayList<File>();
            for (int i = 0; i < Objects.requireNonNull(fileslisting).length; i++) {
                if (new File(fileslisting[i].getAbsolutePath()).getName().contains("_")) {
                    if (fileslisting[i].getName().split("_")[1].equalsIgnoreCase(MakeMyExam.userId)) {
                        tempList.add(fileslisting[i]);
                        video_ids.add(new File(fileslisting[i].getAbsolutePath()).getName().split("_")[0]);
                    }
                }
            }
            if (video_ids.size() > 0) {
                api_send_file_ids = TextUtils.join(", ", video_ids);

                AsyncTask.execute(() -> {
                    for (int i = 0; i < Objects.requireNonNull(tempList).size(); i++) {
                        inser_db(new File(tempList.get(i).getAbsolutePath()), i, tempList.size());
                    }
                });

            } else {
                unstallvideos();
            }*/
        } else {
            unstallvideos();
        }
    }


    private void inser_db(File file, int i, int length) {
        if (file.getName().contains("_")) {
            if (!utkashRoom.getvideoDownloadao().isvideo_exit(file.getName().split("_")[0], MakeMyExam.userId) && file.getName().split("_")[1].equalsIgnoreCase(MakeMyExam.userId)) {
                String histroy = file.getName().substring(0, file.getName().lastIndexOf("."));


                VideosDownload videosDownload = new VideosDownload();
                videosDownload.setVideo_id(file.getName().split("_")[0]);
                videosDownload.setVideo_name(file.getName().split("_")[0]);
                videosDownload.setToal_downloadlocale(file.length());
                videosDownload.setPercentage(100);
                videosDownload.setOriginalFileLengthString("" + file.length());
                long sizeInBytes = file.length();
                videosDownload.setLengthInMb("" + sizeInBytes / (1024 * 1024) + "MB");
                videosDownload.setIs_complete("1");
                videosDownload.setVideo_status("Downloaded");

                String jwurl = "https://cdn.jwplayer.com/v2/media/" + histroy.split("_")[2];
                String thumb = "https://cdn.jwplayer.com/v2/media/" + histroy.split("_")[2] + "/poster.jpg?width=720";
                if (histroy.split("_").length == 4) {
                    videosDownload.setCourse_id(histroy.split("_")[3]);
                }
                videosDownload.setJw_url(jwurl);
                videosDownload.setVideo_history(histroy);
                videosDownload.setThumbnail_url(thumb);
                videosDownload.setUser_id(MakeMyExam.userId);
                videosDownload.setVideoCurrentPosition(0L);
                utkashRoom.getvideoDownloadao().addUser(videosDownload);

                if (i == length - 1) {
                    NetworkCall networkCall = new NetworkCall(this, this);
                    networkCall.NetworkAPICall(API.get_file_names, "", false, false);
                }

            }
        }

    }

    private void alert_dialog() {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Delete Video");
        alertDialogBuilder.setMessage("Are you sure,You want to delete the selected videos ?");
        alertDialogBuilder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        alertDialogBuilder.setPositiveButton("Yes", (dialog, which) -> {
            int i = 0;
            for (i = 0; i < download_videos.size(); i++) {
                if (download_videos.get(i).getIs_selected() != null && download_videos.get(i).getIs_selected().equalsIgnoreCase("1")) {
                    /*EncryptionData encryptionData =new EncryptionData();

                    encryptionData.setCourse_id(download_videos.get(i).getCourse_id());
                    encryptionData.setVideo_id(download_videos.get(i).getVideo_id());
                    encryptionData.setUser_id(MakeMyExam.userId);
                    encryptionData.setServercourseid("");
                    encryptionData.setType("Mannual");

                    firebaseDatabase.push().setValue(new Gson().toJson(encryptionData));*/

                    File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.Videos/" + download_videos.get(i).getVideo_history() + ".mp4");
                    if (file.exists()) {
                        file.delete();
                    }
                    if (download_videos.get(i).getCourse_id() != null && !download_videos.get(i).getCourse_id().equalsIgnoreCase("")) {
                        utkashRoom.getuserhistorydao().delete_viavideo_id(download_videos.get(i).getVideo_id(), download_videos.get(i).getCourse_id(), MakeMyExam.userId);
                    } else {
                        utkashRoom.getuserhistorydao().deletevideo_id(download_videos.get(i).getVideo_id(), MakeMyExam.userId);
                    }

                    utkashRoom.getvideoDownloadao().delete_viavideoid(download_videos.get(i).getVideo_id(), MakeMyExam.userId);
                }
            }

            AsyncTask.execute(() -> {
                try {
                    download_videos = utkashRoom.getvideoDownloadao().getalldownload_videos(MakeMyExam.userId);
                    runOnUiThread(() -> {
                        if (download_videos == null || download_videos.size() == 0) {
                            download_videos = new ArrayList<>();
                            select_all_delete.setVisibility(View.GONE);
                            downloadVideoAdapter.notifydata(download_videos);
                            delete.setVisibility(View.GONE);
                        } else {
                            select_all_delete.setVisibility(View.VISIBLE);
                            select_all_delete.setChecked(false);
                            delete.setVisibility(View.GONE);
                        }

                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            dialog.dismiss();
            dialog.cancel();
        });
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }


    private void moveFile(File filname, String inputPath, String inputFile, String outputPath, int i, int length) {

        InputStream in = null;
        OutputStream out = null;
        try {

            File dir = new File(outputPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            in = new FileInputStream(inputPath + inputFile);
            out = new FileOutputStream(outputPath + inputFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            out.flush();
            out.close();

            String histroy = filname.getName().substring(0, filname.getName().lastIndexOf("."));

            if (new File(dir + "/" + inputFile).exists()) {
                new File(dir + "/" + inputFile).renameTo(new File(dir + "/" + histroy.split("_")[2] + "_" + histroy.split("_")[3] + "_" + "jwid" + ".mp4"));
            }


            VideosDownload videosDownload = new VideosDownload();
            videosDownload.setThumbnail_url("");
            videosDownload.setVideo_id(filname.getName().split("_")[2]);

            videosDownload.setVideo_name(filname.getName().split("_")[2]);


            videosDownload.setVideo_history(histroy.split("_")[2] + "_" + histroy.split("_")[3] + "_" + "jwid");


            videosDownload.setToal_downloadlocale(filname.length());

            videosDownload.setPercentage(100);

            videosDownload.setCourse_id(filname.getName().split("_")[0] + "#" + filname.getName().split("_")[0]);


            videosDownload.setOriginalFileLengthString("" + filname.length());

            long sizeInBytes = filname.length();

            videosDownload.setLengthInMb("" + sizeInBytes / (1024 * 1024) + "MB");
            videosDownload.setIs_complete("1");
            videosDownload.setVideo_status("Downloaded");
            videosDownload.setUser_id(MakeMyExam.userId);
            videosDownload.setVideoCurrentPosition(0L);
            videosDownload.setJw_url("cross");
            utkashRoom.getvideoDownloadao().addUser(videosDownload);

            new File(inputPath + inputFile).delete();

            if (i == length - 1) {
                NetworkCall networkCall = new NetworkCall(this, this);
                networkCall.NetworkAPICall(API.get_file_names, "", false, false);
            }

        } catch (Exception fnfe1) {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.flush();
                    out.close();
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    public void openwatchlist_dailog_resource(Context context, HashMap<Integer, String> mediaResponseMap, VideosDownload videosDownload, int pos) {
        try {
            BottomSheetDialog watchlist;

            watchlist = new BottomSheetDialog(context, R.style.videosheetDialogTheme);
            watchlist.setContentView(R.layout.quality_download_view);
            Objects.requireNonNull(watchlist.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            watchlist.setCancelable(false);
            watchlist.setCanceledOnTouchOutside(true);
            LinearLayout lnPreparing = watchlist.findViewById(R.id.lnPreparing);
            TextView btnLow = watchlist.findViewById(R.id.btnLow);
            TextView tvResName = watchlist.findViewById(R.id.tvResName);
            TextView btnMedium = watchlist.findViewById(R.id.btnMedium);
            TextView btnHigh = watchlist.findViewById(R.id.btnHigh);
            tvResName.setText(videosDownload.getVideo_name());
            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                if (mediaResponseMap.get(180) != null) {
                    btnLow.setVisibility(View.VISIBLE);
                }
                if (mediaResponseMap.get(270) != null) {
                    Objects.requireNonNull(btnMedium).setVisibility(View.VISIBLE);
                }
                if (mediaResponseMap.get(360) != null) {
                    Objects.requireNonNull(btnHigh).setVisibility(View.VISIBLE);
                }
                Objects.requireNonNull(lnPreparing).setVisibility(View.GONE);

            }, 500);


            Objects.requireNonNull(btnLow).setOnClickListener(new OnSingleClickListener(() -> {
                if (!utkashRoom.getOpenHelper().getWritableDatabase().isDbLockedByCurrentThread()) {
                    Download_dialog(mediaResponseMap.get(180), videosDownload, pos);
                    dismissCalculatorDialog(watchlist);
                }

                return null;
            }));
            Objects.requireNonNull(btnMedium).setOnClickListener(new OnSingleClickListener(() -> {
                if (!utkashRoom.getOpenHelper().getWritableDatabase().isDbLockedByCurrentThread()) {
                    Download_dialog(mediaResponseMap.get(270), videosDownload, pos);
                    dismissCalculatorDialog(watchlist);
                }

                return null;
            }));
            Objects.requireNonNull(btnHigh).setOnClickListener(new OnSingleClickListener(() -> {
                if (!utkashRoom.getOpenHelper().getWritableDatabase().isDbLockedByCurrentThread()) {
                    Download_dialog(mediaResponseMap.get(360), videosDownload, pos);
                    dismissCalculatorDialog(watchlist);
                }

                return null;
            }));

            if (!watchlist.isShowing()) {
                watchlist.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dismissCalculatorDialog(BottomSheetDialog watchlist) {
        if (watchlist != null && watchlist.isShowing()) {
            watchlist.dismiss();
            watchlist.cancel();
            watchlist = null;
        }
    }


    private void Download_dialog(String url, VideosDownload videosDownload, int position) {

        Intent videoDownloadIntent = null;
        VideosDownload adapter_data = utkashRoom.getvideoDownloadao().getuser(videosDownload.getVideo_id(), videosDownload.getIs_complete());
        Log.d("prince_new", "" + adapter_data.getPercentage());
        if (utkashRoom.getvideoDownloadao().isRecordexist_1(videosDownload.getVideo_id(), "1", videosDownload.getUser_id())) {
            final File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.Videos/" + videosDownload.getVideo_name() + ".mp4");
            if (file.exists()) {
            } else {
                utkashRoom.getvideoDownloadao().delete_viavideoid(videosDownload.getVideo_id(), MakeMyExam.userId);
            }
        } else if (utkashRoom.getvideoDownloadao().getuser(videosDownload.getVideo_id(), videosDownload.getIs_complete()).getToal_downloadlocale() == 0) {


            utkashRoom.getvideoDownloadao().update_videostatus(videosDownload.getVideo_id(), "0", "Downloading Running", 0);

            if (videosDownload.getVideo_id() != null) {
                int index = 0;
                if (download_videos.size() > 0) {
                    for (VideosDownload datadownload : download_videos) {
                        index++;
                        if (datadownload.getVideo_id().equalsIgnoreCase(videosDownload.getVideo_id())) {
                            videosDownload.setVideo_status("Downloading Running");
                            videosDownload.setPercentage(0);
                            download_videos.set(index - 1, videosDownload);
                            break;
                        }
                    }
                    downloadVideoAdapter.notifyItemChanged(index - 1);
                }
            }

            videoDownloadIntent = new Intent(DownloadActivity.this, VideoDownloadService.class);
            videoDownloadIntent.putExtra(VideoDownloadService.FILEDOWNLOADSTATUS, false);
            videoDownloadIntent.putExtra(VideoDownloadService.URL, url);
            videoDownloadIntent.putExtra(VideoDownloadService.DOWNLOAD_SERVICE_ID, videosDownload.getVideo_id());
            videoDownloadIntent.putExtra(VideoDownloadService.FILEPATH, videosDownload.getVideo_name());
            videoDownloadIntent.putExtra("pos", position);
            videoDownloadIntent.putExtra("status", "Downloading Running");
            videoDownloadIntent.putExtra("name", videosDownload.getVideo_history());
            videoDownloadIntent.putExtra("token", videosDownload.getVideo_token());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(videoDownloadIntent);
            } else {
                startService(videoDownloadIntent);
            }
        } else if (utkashRoom.getvideoDownloadao().getuser(videosDownload.getVideo_id(), videosDownload.getIs_complete()).getToal_downloadlocale() >= 0) {

            utkashRoom.getvideoDownloadao().update_videostatus(videosDownload.getVideo_id(), "0", "Downloading Running", adapter_data.getPercentage());

            if (videosDownload.getVideo_id() != null) {
                int index = 0;
                if (download_videos.size() > 0) {
                    for (VideosDownload datadownload : download_videos) {
                        index++;
                        if (datadownload.getVideo_id().equalsIgnoreCase(videosDownload.getVideo_id())) {
                            videosDownload.setVideo_status("Downloading Running");
                            videosDownload.setPercentage(adapter_data.getPercentage());
                            download_videos.set(index - 1, videosDownload);
                            break;
                        }
                    }
                    downloadVideoAdapter.notifyItemChanged(index - 1);
                }
            }


            videoDownloadIntent = new Intent(DownloadActivity.this, VideoDownloadService.class);
            videoDownloadIntent.putExtra(VideoDownloadService.URL, url);
            videoDownloadIntent.putExtra(VideoDownloadService.DOWNLOAD_SERVICE_ID, videosDownload.getVideo_id());
            videoDownloadIntent.putExtra("pos", position);
            videoDownloadIntent.putExtra(VideoDownloadService.FILEPATH, videosDownload.getVideo_name());
            videoDownloadIntent.putExtra("name", videosDownload.getVideo_history());
            videoDownloadIntent.putExtra("token", videosDownload.getVideo_token());

            videoDownloadIntent.putExtra(VideoDownloadService.FILEDOWNLOADSTATUS, true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(videoDownloadIntent);
            } else {
                startService(videoDownloadIntent);
            }
        } else {
            // Toast.makeText(this, "alredy downladed", Toast.LENGTH_SHORT).show();
        }

    }

    public static String convertMillieToHMmSs(long millie) {
        long seconds = (millie / 1000);
        long second = seconds % 60;
        long minute = (seconds / 60) % 60;
        long hour = (seconds / (60 * 60)) % 24;

        String result = "";
        if (hour > 0) {
            return String.format("%02d:%02d:%02d", hour, minute, second);
        } else {
            return String.format("%02d:%02d", minute, second);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (utkashRoom == null) {
            utkashRoom = UtkashRoom.getAppDatabase(DownloadActivity.this);
            utkashRoom.getOpenHelper().getWritableDatabase().enableWriteAheadLogging();
        }


        LocalBroadcastManager.getInstance(this)
                .registerReceiver(videoDownloadReceiver, new IntentFilter(VideoDownloadService.VIDEO_DOWNLOAD_ACTION));


        LocalBroadcastManager.getInstance(this)
                .registerReceiver(percentage_receiver, new IntentFilter(VideoDownloadService.VIDEO_DOWNLOAD_PROGRESS));

    }


    private final BroadcastReceiver videoDownloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int serviceStatus = intent.getIntExtra("result", -1);
            String res_id = intent.getStringExtra(RES_ID);

            if (serviceStatus == VIDEO_DOWNLOAD_SUCCESSFUL) {

                VideosDownload videosDownload = utkashRoom.getvideoDownloadao().getuser(res_id, "1");
                if (videosDownload != null && videosDownload.getVideo_id() != null) {
                    int index = 0;
                    if (download_videos.size() > 0) {
                        for (VideosDownload datadownload : download_videos) {
                            index++;
                            if (datadownload.getVideo_id().equalsIgnoreCase(videosDownload.getVideo_id())) {
                                download_videos.set(index - 1, videosDownload);
                                break;
                            }
                        }
                        if (download_recycler != null) {
                            if (downloadVideoAdapter == null) {
                                downloadVideoAdapter = new DownloadVideoAdapter(DownloadActivity.this, download_videos, DownloadActivity.this);
                                download_recycler.setAdapter(downloadVideoAdapter);
                            } else
                                downloadVideoAdapter.notifyItemChanged(index - 1);


                        } else {
                            download_recycler = findViewById(R.id.download_recycler);

                            if (downloadVideoAdapter == null) {
                                downloadVideoAdapter = new DownloadVideoAdapter(DownloadActivity.this, download_videos, DownloadActivity.this);
                                download_recycler.setAdapter(downloadVideoAdapter);
                            } else
                                downloadVideoAdapter.notifyItemChanged(index - 1);


                        }

                    }
                }


            } else if (serviceStatus == VIDEO_FILE_EXIST) {
                Toast.makeText(context, "Please retry download video", Toast.LENGTH_SHORT).show();

            } else if (serviceStatus == EXCEPTION_OCCURRED) {
                Toast.makeText(context, "Internet Issue. Download is not completed Please retry!!!", Toast.LENGTH_SHORT).show();
                download_videos = utkashRoom.getvideoDownloadao().getalldownload_videos(MakeMyExam.userId);

                downloadVideoAdapter.notifyDataSetChanged();

            } else if (serviceStatus == NOT_AVAILABLE_ON_SERVER) {
                Toast.makeText(context, "Server issue please retry again", Toast.LENGTH_SHORT).show();
                download_videos.clear();
                download_videos = utkashRoom.getvideoDownloadao().getalldownload_videos(MakeMyExam.userId);
                if (download_videos.size() == 0) {
                    select_all_delete.setVisibility(View.GONE);
                }
                downloadVideoAdapter.notifydata(download_videos);
            } else if (serviceStatus == VIDEO_DOWNLOAD_CANCELLED) {
                download_videos.clear();
                download_videos = utkashRoom.getvideoDownloadao().getalldownload_videos(MakeMyExam.userId);

                downloadVideoAdapter.notifydata(download_videos);


                  /*  download_videos.remove(pos);
                    downloadVideoAdapter.notifyItemRemoved(pos);
                    if (download_videos.size()==0)
                    {
                        downloadVideoAdapter.notifyDataSetChanged();
                        select_all_delete.setVisibility(View.GONE);
                    }else {
                        downloadVideoAdapter.notifyItemRangeChanged(pos, download_videos.size());
                    }*/
            } else if (serviceStatus == 2021) {
                download_videos.clear();
                download_videos = utkashRoom.getvideoDownloadao().getalldownload_videos(MakeMyExam.userId);

                downloadVideoAdapter.notifydata(download_videos);

            } else if (serviceStatus == VIDEO_DOWNLOAD_PAUSED) {
                VideosDownload videosDownload = utkashRoom.getvideoDownloadao().getuser(res_id, "0");

                if (videosDownload != null && videosDownload.getVideo_id() != null) {
                    int index = 0;
                    if (download_videos.size() > 0) {
                        for (VideosDownload datadownload : download_videos) {
                            index++;
                            if (datadownload.getVideo_id().equalsIgnoreCase(videosDownload.getVideo_id())) {
                                download_videos.set(index - 1, videosDownload);
                                break;
                            }
                        }
                        downloadVideoAdapter.notifyItemChanged(index - 1);

                    }
                }


            } else if (serviceStatus == VIDEO_DOWNLOAD_STARTED) {
                if (download_videos.size() == 0) {
                    VideosDownload videosDownload = utkashRoom.getvideoDownloadao().getuser(res_id, "0");

                    if (videosDownload != null && videosDownload.getVideo_id() != null) {
                        int index = 0;

                        for (VideosDownload datadownload : download_videos) {
                            index++;
                            if (datadownload.getVideo_id().equalsIgnoreCase(videosDownload.getVideo_id())) {
                                download_videos.set(index - 1, videosDownload);
                                break;
                            }
                        }
                        if (download_recycler != null) {
                            if (downloadVideoAdapter == null) {
                                downloadVideoAdapter = new DownloadVideoAdapter(DownloadActivity.this, download_videos, DownloadActivity.this);
                                download_recycler.setAdapter(downloadVideoAdapter);
                            } else
                                downloadVideoAdapter.notifyItemChanged(index - 1);
                        } else {
                            download_recycler = findViewById(R.id.download_recycler);

                            if (downloadVideoAdapter == null) {
                                downloadVideoAdapter = new DownloadVideoAdapter(DownloadActivity.this, download_videos, DownloadActivity.this);
                                download_recycler.setAdapter(downloadVideoAdapter);
                            } else
                                downloadVideoAdapter.notifyItemChanged(index - 1);
                        }


                        //  downloadVideoAdapter.notifyItemChanged(index-1);/////issue recycerview null cheeck
                    }

                    // download_videos.set(pos,videosDownload);
                    //downloadVideoAdapter.notifyItemChanged(pos);
                } else {
                        /*if (pos <= download_videos.size()-1) {
                            VideosDownload videosDownload = utkashRoom.getvideoDownloadao().getuser(res_id,"0");
                            download_videos.set(pos,videosDownload);
                            downloadVideoAdapter.notifyItemChanged(pos);
                        }*/

                }
            }

        }
    };
    private BroadcastReceiver percentage_receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //downloading_pos =intent.getIntExtra("pos", 0);
            // Log.d("prince",""+downloading_pos);
            String res_id = intent.getStringExtra(RES_ID);
            VideosDownload videosDownload = utkashRoom.getvideoDownloadao().getuser(res_id, "0");
            if (videosDownload != null && videosDownload.getVideo_id() != null) {
                int index = 0;
                if (download_videos.size() > 0) {
                    for (VideosDownload datadownload : download_videos) {
                        index++;
                        if (datadownload.getVideo_id().equalsIgnoreCase(videosDownload.getVideo_id())) {
                            download_videos.set(index - 1, videosDownload);
                            break;
                        }
                    }
                    if (download_recycler != null) {
                        if (downloadVideoAdapter == null) {
                            downloadVideoAdapter = new DownloadVideoAdapter(DownloadActivity.this, download_videos, DownloadActivity.this);
                            download_recycler.setAdapter(downloadVideoAdapter);
                        } else
                            downloadVideoAdapter.notifyItemChanged(index - 1);
                    } else {
                        download_recycler = findViewById(R.id.download_recycler);
                        if (downloadVideoAdapter == null) {
                            downloadVideoAdapter = new DownloadVideoAdapter(DownloadActivity.this, download_videos, DownloadActivity.this);
                            download_recycler.setAdapter(downloadVideoAdapter);
                        } else
                            downloadVideoAdapter.notifyItemChanged(index - 1);
                    }

                }


            }


        }
    };


    @Override
    public void OnVideoClick(int pos, VideosDownload videoDownload, String type) {
        if (type.equalsIgnoreCase("play")) {
            Download_dialog(videoDownload.getMp4_download_url(), videoDownload, pos);
        } else if (type.equalsIgnoreCase("Pause")) {
            videoDownload = utkashRoom.getvideoDownloadao().getvideo_byuserid(videoDownload.getVideo_id(), videoDownload.getIs_complete(), MakeMyExam.userId);
            if (videoDownload != null && videoDownload.getPercentage() > 0) {
                if (VideoDownloadService.video_id.equalsIgnoreCase(videoDownload.getVideo_id())) {
                    VideoDownloadService.action = VideoDownloadService.PAUSE;
                }
            }

        } else if (type.equalsIgnoreCase("Cancel")) {
            issue_type = "";
            if (videoDownload.getPercentage() == 100) {
                File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.Videos/" + videoDownload.getVideo_history() + ".mp4");
                if (file.exists()) {
                    file.delete();
                }
                utkashRoom.getvideoDownloadao().delete_viavideoid(videoDownload.getVideo_id(), MakeMyExam.userId);
                download_videos.remove(pos);
                downloadVideoAdapter.notifyItemRemoved(pos);

                if (download_videos.size() == 0) {
                    downloadVideoAdapter.notifyDataSetChanged();

                    select_all_delete.setVisibility(View.GONE);
                } else {
                    downloadVideoAdapter.notifyItemRangeChanged(pos, download_videos.size());
                }
                Toast.makeText(this, "Cancel video. Please wait...", Toast.LENGTH_SHORT).show();
            } else if (videoDownload.getPercentage() >= 0 && videoDownload.getPercentage() < 100) {
                if (videoDownload.getVideo_status().equalsIgnoreCase("Downloading Pause")) {
                    File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.Videos/" + videoDownload.getVideo_history() + ".mp4");
                    if (file.exists()) {
                        file.delete();
                    }
                    utkashRoom.getvideoDownloadao().delete_viavideoid(videoDownload.getVideo_id(), MakeMyExam.userId);
                    download_videos.remove(pos);
                    downloadVideoAdapter.notifyItemRemoved(pos);
                    if (download_videos.size() == 0) {
                        downloadVideoAdapter.notifyDataSetChanged();

                        select_all_delete.setVisibility(View.GONE);
                    } else {
                        downloadVideoAdapter.notifyItemRangeChanged(pos, download_videos.size());
                    }

                } else {
                    Toast.makeText(this, "Video is deleted successfully..", Toast.LENGTH_SHORT).show();

                    if (VideoDownloadService.video_id.equalsIgnoreCase(videoDownload.getVideo_id())) {
                        try {
                            VideoDownloadService.action = VideoDownloadService.CANCEL;

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.Videos/" + videoDownload.getVideo_history() + ".mp4");
                        if (file.exists()) {
                            file.delete();
                        }
                        download_videos = utkashRoom.getvideoDownloadao().getalldownload_videos(MakeMyExam.userId);
                        utkashRoom.getvideoDownloadao().delete_viavideoid(videoDownload.getVideo_id(), MakeMyExam.userId);
                        download_videos.remove(pos);

                        if (download_videos.size() == 0) {
                            downloadVideoAdapter.notifyDataSetChanged();
                            select_all_delete.setVisibility(View.GONE);
                        } else {
                            downloadVideoAdapter.notifydata(download_videos);
                            // downloadVideoAdapter.notifyItemRangeChanged(pos, download_videos.size());
                        }


                    }
                }
            }
        }
    }

    @Override
    public void getvideo_url(int pos, VideosDownload videoDownload, String type) {
        try {
            //  extractJWPlayerUrl(videoDownload.getJw_url(),videoDownload,pos);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoDownloadReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(videoDownloadReceiver);
        }
        if (percentage_receiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(percentage_receiver);
        }
    }


    @Override
    public void onBackPressed() {
        if (getIntent().hasExtra(Const.NOTIFICATION_CODE)) {
            if (Helper.isConnected(this)) {
                NetworkCall networkCall = new NetworkCall(this, this);
                networkCall.NetworkAPICall(API.API_GET_APP_VERSION, "", false, false);
                //super.onBackPressed();
            } else finishAffinity();
        } else super.onBackPressed();
    }

    public void visible_seletebutton(List<VideosDownload> videos) {
        boolean checkvisiblestatus = false;
        // seleted_videodelete.clear();
        for (VideosDownload videosDownload : videos) {
            if (videosDownload.getIs_selected() != null && videosDownload.getIs_selected().equalsIgnoreCase("1")) {
                checkvisiblestatus = true;
                delete.setVisibility(View.VISIBLE);
                select_all_delete.setVisibility(View.GONE);
            }
        }
        if (!checkvisiblestatus) {
            select_all_delete.setVisibility(View.VISIBLE);
            delete.setVisibility(View.GONE);
        }
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.API_GET_APP_VERSION:
                return service.getAppVersion();

            case API.get_file_names:
                Log.d("prince", "" + api_send_file_ids);
                EncryptionData masterdataencryptionData2 = new EncryptionData();
                masterdataencryptionData2.setFile_ids(api_send_file_ids);
                String masterdataencryptionDatadoseStr3 = new Gson().toJson(masterdataencryptionData2);
                String masterdatadoseStrScr3 = AES.encrypt(masterdataencryptionDatadoseStr3);
                return service.get_file_names(masterdatadoseStrScr3);

            case API.get_my_courses:
                EncryptionData getcoursedataencryptionData = new EncryptionData();
                getcoursedataencryptionData.setUser_id(MakeMyExam.userId);
                String getcoursedataencryptionDatadoseStr = new Gson().toJson(getcoursedataencryptionData);
                String getcoursedatadoseStrScr = AES.encrypt(getcoursedataencryptionDatadoseStr);
                return service.get_my_courses(getcoursedatadoseStrScr);


        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonstring, String apitype, String typeApi, boolean showprogress) throws JSONException {
        switch (apitype) {
            case API.API_GET_APP_VERSION:
                if (jsonstring.optString(Const.STATUS).equals(Const.TRUE)) {
                    JSONObject data = jsonstring.getJSONObject(Const.DATA);
                    if (data.has("feeds")) {
                        String feeds = data.optString("feeds");
                        MakeMyExam.setFeeds(feeds);
                    }
                }
                finish();
                break;
            case API.get_my_courses:
                if (jsonstring.optString(Const.STATUS).equals(Const.TRUE)) {
                    MyCourse myCourse = new Gson().fromJson(jsonstring.toString(), MyCourse.class);
                    if (myCourse.getData().size() > 0) {
                        if (utkashRoom.getapidao().is_api_code_exits(MakeMyExam.userId, "ut_012")) {
                            utkashRoom.getapidao().update_api_version("ut_012", MakeMyExam.userId, String.valueOf(jsonstring.optLong("time")), String.valueOf(jsonstring.optLong("interval")), String.valueOf(jsonstring.optLong("cd_time")));

                        } else {
                            APITABLE apiMangerTable = new APITABLE();
                            apiMangerTable.setApicode("ut_012");
                            apiMangerTable.setApiname("get_my_courses");
                            apiMangerTable.setInterval(String.valueOf(jsonstring.optLong("interval")));
                            apiMangerTable.setUser_id(MakeMyExam.getUserId());
                            apiMangerTable.setTimestamp(String.valueOf(jsonstring.optLong("time")));
                            apiMangerTable.setCdtimestamp(String.valueOf(jsonstring.optLong("cd_time")));
                            apiMangerTable.setVersion("0.000");
                            utkashRoom.getapidao().addUser(apiMangerTable);
                        }


                        if (myCourse.getData().size() > 0) {


                            for (Courselist courselist : myCourse.getData()) {
                                if (!TextUtils.isEmpty(courselist.getBatch_id()) && !courselist.getBatch_id().equalsIgnoreCase("0")) {

                                    if (!utkashRoom.getMyCourseDao().isRecordExistsUserId(MakeMyExam.userId, "2", courselist.getId())) {
                                        MycourseTable mycourseTable = new MycourseTable();
                                        mycourseTable.setBatch_id(courselist.getBatch_id());
                                        mycourseTable.setBatchtype("2");
                                        mycourseTable.setCover_image(courselist.getCover_image());
                                        mycourseTable.setId(courselist.getId());

                                        mycourseTable.setExpiry_date("0");

                                        mycourseTable.setPurchase_date(courselist.getPurchase_date());
                                        mycourseTable.setLastread("" + MakeMyExam.getTime_server());
                                        mycourseTable.setTitle(courselist.getTitle());
                                        mycourseTable.setTxn_id(courselist.getTxn_id());
                                        mycourseTable.setMrp(courselist.getMrp());
                                        mycourseTable.setUserid(MakeMyExam.userId);

                                        mycourseTable.setIsExpand(courselist.getCombo_course_ids());

                                        courselist.setPrices(null);
                                        courselist.setExpiry_date("0");

                                        utkashRoom.getMyCourseDao().addUser(mycourseTable);
                                    }

                                } else if (courselist.getMrp().equalsIgnoreCase("0")) {
                                    if (!utkashRoom.getMyCourseDao().isRecordExistsUserId(MakeMyExam.userId, "0", courselist.getId())) {
                                        MycourseTable mycourseTable = new MycourseTable();
                                        mycourseTable.setBatch_id("");
                                        mycourseTable.setBatchtype("0");
                                        mycourseTable.setCover_image(courselist.getCover_image());
                                        mycourseTable.setId(courselist.getId());
                                        mycourseTable.setExpiry_date(courselist.getExpiry_date());
                                        mycourseTable.setPurchase_date(courselist.getPurchase_date());
                                        mycourseTable.setLastread("" + MakeMyExam.getTime_server());
                                        mycourseTable.setTitle(courselist.getTitle());
                                        mycourseTable.setTxn_id(courselist.getTxn_id());
                                        mycourseTable.setMrp(courselist.getMrp());
                                        mycourseTable.setUserid(MakeMyExam.userId);

                                        mycourseTable.setIsExpand(courselist.getCombo_course_ids());

                                        mycourseTable.setDelete(1);
                                        if (courselist.getPrices() != null) {
                                            if (courselist.getPrices().size() > 0) {
                                                mycourseTable.setPrices(courselist.getPrices());
                                            }
                                        }
                                        courselist.setDelete(1);

                                        utkashRoom.getMyCourseDao().addUser(mycourseTable);
                                    }
                                } else if (Integer.parseInt(courselist.getMrp()) > 0) {

                                    if (!utkashRoom.getMyCourseDao().isRecordExistsUserId(MakeMyExam.userId, "1", courselist.getId())) {
                                        MycourseTable mycourseTable = new MycourseTable();
                                        mycourseTable.setBatch_id("");
                                        mycourseTable.setBatchtype("1");
                                        mycourseTable.setCover_image(courselist.getCover_image());
                                        mycourseTable.setId(courselist.getId());
                                        mycourseTable.setExpiry_date(courselist.getExpiry_date());
                                        mycourseTable.setPurchase_date(courselist.getPurchase_date());
                                        mycourseTable.setLastread("" + MakeMyExam.getTime_server());
                                        mycourseTable.setTitle(courselist.getTitle());
                                        mycourseTable.setTxn_id(courselist.getTxn_id());

                                        mycourseTable.setIsExpand(courselist.getCombo_course_ids());

                                        mycourseTable.setMrp(courselist.getMrp());
                                        mycourseTable.setUserid(MakeMyExam.userId);
                                        if (courselist.getPrices() != null) {
                                            if (courselist.getPrices().size() > 0) {
                                                mycourseTable.setPrices(courselist.getPrices());
                                            }
                                        }
                                        utkashRoom.getMyCourseDao().addUser(mycourseTable);
                                    }
                                }
                            }


                            List<String> courseidlist = utkashRoom.getvideoDownloadao().courseids(MakeMyExam.userId);
                            List<String> courseidlist_userhistory = utkashRoom.getuserhistorydao().courseids(MakeMyExam.userId);


                            //////////In db//////////////
                            HashSet<String> courseidlist_userhistory_hashset = new HashSet<>();
                            for (String item : courseidlist_userhistory) {
                                if (item != null) {
                                    String[] itemstring = item.split("#");
                                    if (itemstring.length == 2) {
                                        courseidlist_userhistory_hashset.add(itemstring[1]);
                                    } else if (itemstring.length > 0) {
                                        courseidlist_userhistory_hashset.add(itemstring[0]);
                                    }
                                }

                            }

                            //////////////// in db/////////
                            HashSet<String> coursehassetid = new HashSet<>();
                            for (String item : courseidlist) {
                                if (item != null) {
                                    String[] itemstring = item.split("#");
                                    if (itemstring.length == 2) {
                                        coursehassetid.add(itemstring[1]);
                                    } else if (itemstring.length > 0) {
                                        coursehassetid.add(itemstring[0]);
                                    }
                                }

                            }

                            ///////ina= api////
                            HashSet<String> courseids = new HashSet<>();
                            for (int i = 0; i < myCourse.getData().size(); i++) {
                                courseids.add(myCourse.getData().get(i).getId());
                                if (myCourse.getData().get(i).getCombo_course_ids() != null && !myCourse.getData().get(i).getCombo_course_ids().equalsIgnoreCase("")) {
                                    List<String> listParts = Arrays.asList(myCourse.getData().get(i).getCombo_course_ids().split(","));
                                    if (listParts.size() > 0)
                                        courseids.addAll(listParts);
                                }
                            }

                            Log.d("prince", "" + new Gson().toJson(coursehassetid));
                            Log.d("prince", "" + new Gson().toJson(courseids));

                            List<String> base;
                            base = new ArrayList<String>(coursehassetid);
                            base.removeAll(courseids);


                            List<String> base_userhistory;
                            base_userhistory = new ArrayList<String>(courseidlist_userhistory_hashset);
                            base_userhistory.removeAll(courseids);

                            if (base_userhistory != null && base_userhistory.size() > 0) {
                                for (String courseid : base_userhistory) {
                                    utkashRoom.getuserhistorydao().delete(courseid + "#", MakeMyExam.userId);
                                    utkashRoom.getuserhistorydao().delete_right("#" + courseid, MakeMyExam.userId);

                                }

                            }


                            Log.d("prince", "" + new Gson().toJson(base));
                            if (base != null && base.size() > 0) {
                                for (String downloadCourse_id : base) {
                                    List<VideosDownload> videosDownloads_combo = utkashRoom.getvideoDownloadao().getcourse_expire("#" + downloadCourse_id, MakeMyExam.userId);

                                    Log.d("prince", "" + videosDownloads_combo.size());
                                    if (videosDownloads_combo != null && videosDownloads_combo.size() > 0) {
                                        for (VideosDownload videosDownload : videosDownloads_combo) {
                                            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.Videos/" + videosDownload.getVideo_history() + ".mp4");
                                            File file1 = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.processing/" + videosDownload.getVideo_history() + ".mp4");
                                            if (file.exists()) {
                                                file.delete();
                                            }
                                            if (file1.exists()) {
                                                file1.delete();
                                            }
                                            utkashRoom.getvideoDownloadao().delete(videosDownload.getVideo_id(), videosDownload.getCourse_id(), MakeMyExam.userId);
                                        }
                                    }

                                    List<VideosDownload> videosDownloads_combo_new = utkashRoom.getvideoDownloadao().getcourse_expire_left(downloadCourse_id + "#", MakeMyExam.userId);
                                    Log.d("prince", "" + videosDownloads_combo_new.size());
                                    if (videosDownloads_combo_new != null && videosDownloads_combo_new.size() > 0) {
                                        for (VideosDownload videosDownload : videosDownloads_combo_new) {
                                            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.Videos/" + videosDownload.getVideo_history() + ".mp4");
                                            File file1 = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.processing/" + videosDownload.getVideo_history() + ".mp4");
                                            if (file.exists()) {
                                                file.delete();
                                            }
                                            if (file1.exists()) {
                                                file1.delete();
                                            }
                                            utkashRoom.getvideoDownloadao().delete(videosDownload.getVideo_id(), videosDownload.getCourse_id(), MakeMyExam.userId);
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
                Helper.showProgressDialog(this);
                logout_user_videos();


                break;

            case API.get_file_names:
                if (jsonstring.optString(Const.STATUS).equals(Const.TRUE)) {
                    JSONArray list_videos = jsonstring.optJSONArray(Const.DATA);
                    if (Objects.requireNonNull(list_videos).length() > 0) {
                        String path = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.Videos/";
                        for (int i = 0; i < list_videos.length(); i++) {
                            JSONObject jsonObject = list_videos.getJSONObject(i);
                            String video_id = jsonObject.optString("id");
                            String video_name = jsonObject.optString("title");

                            if (jsonObject.has("file_url")) {
                                String file_url = jsonObject.optString("file_url");
                                File file = new File(path + video_id + "_" + SharedPreference.getInstance().getLoggedInUser().getMobile() + "_" + "jwid.mp4");
                                if (file.exists()) {
                                    if (!file_url.equals("") && file_url.contains("jwplatform")) {
                                        String mediaId = file_url.substring(file_url.lastIndexOf("/") + 1, file_url.indexOf("-"));
                                        VideosDownload videoDownload = utkashRoom.getvideoDownloadao().getvideo_byuserid(video_id, MakeMyExam.userId);
                                        String video_history = video_id + "_" + MakeMyExam.userId + "_" + mediaId + "_" + videoDownload.getCourse_id();
                                        String jwurl = "https://cdn.jwplayer.com/v2/media/" + mediaId;
                                        String thumb = "https://cdn.jwplayer.com/v2/media/" + mediaId + "/poster.jpg?width=720";
                                        file.renameTo(new File(path + video_history + ".mp4"));
                                        utkashRoom.getvideoDownloadao().updatevideo_name(video_name, video_id, MakeMyExam.userId, jwurl, thumb, video_history);
                                    }

                                } else {
                                    utkashRoom.getvideoDownloadao().update_title(video_name, video_id, MakeMyExam.userId);

                                }
                            } else {
                                Log.d("prince", "" + video_name + "_" + video_id);
                                utkashRoom.getvideoDownloadao().update_title(video_name, video_id, MakeMyExam.userId);

                            }

                        }
                        unstallvideos();
                    }


                } else {
                    unstallvideos();
                }
                break;
        }

    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {

    }
}