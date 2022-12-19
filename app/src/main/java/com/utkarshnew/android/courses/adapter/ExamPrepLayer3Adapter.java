package com.utkarshnew.android.courses.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.utkarshnew.android.Download.Adapter.DownloadQualityAdapter;
import com.utkarshnew.android.Download.Interface.Getvideourl;
import com.utkarshnew.android.courses.Activity.Concept_newActivity;
import com.utkarshnew.android.courses.Activity.CourseActivity;
import com.utkarshnew.android.courses.Activity.QuizActivity;
import com.utkarshnew.android.courses.Fragment.SingleStudy;
import com.utkarshnew.android.Download.Audio.AudioPlayerService;
import com.utkarshnew.android.Download.AudioPlayerActivty;
import com.utkarshnew.android.Download.DownloadActivity;
import com.utkarshnew.android.Download.DownloadVideoPlayer;
import com.utkarshnew.android.DownloadServices.VideoDownloadService;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.Model.COURSEDETAIL.CourseDetail;
import com.utkarshnew.android.Model.UrlObject;
import com.utkarshnew.android.Model.Video;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.Payment.PurchaseActivity;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.table.AudioTable;
import com.utkarshnew.android.table.TestTable;
import com.utkarshnew.android.table.VideoTable;
import com.utkarshnew.android.table.VideosDownload;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;
import com.utkarshnew.android.Utils.Progress;
import com.utkarshnew.android.Utils.SharedPreference;
import com.utkarshnew.android.Webview.RevisionActivity;
import com.utkarshnew.android.Webview.WebViewActivty;
import com.utkarshnew.android.pojo.Userinfo.Data;
import com.utkarshnew.android.testmodule.activity.TestBaseActivity;
import com.utkarshnew.android.testmodule.activity.ViewSolutionActivity;
import com.utkarshnew.android.testmodule.model.InstructionData;
import com.utkarshnew.android.testmodule.model.ResultTestSeries_Report;
import com.utkarshnew.android.testmodule.model.TestBasicInst;
import com.utkarshnew.android.testmodule.model.TestSectionInst;
import com.utkarshnew.android.testmodule.model.TestseriesBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExamPrepLayer3Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements NetworkCall.MyNetworkCallBack, Getvideourl {
    int lang;  // 0 for Hindi , 1 for English
    Activity activity;
    ArrayList<Video> videoArrayList;
    private String quiz_id;
    private boolean is_download = false;
    Data signUpResponse;
    private Video videodata;
    String TAG = "ExamPrepLayer3Adapter";
    CourseDetail singleStudy;
    long time = MakeMyExam.getTime_server();
    int position_delete;
    private String totalQuestion, first_attempt;
    private String quiz_name;
    String tileTypeAPI;
    String tileIdAPI;
    String revertAPI;
    private Progress progress;
    BottomSheetDialog watchlist;


    public UtkashRoom utkashRoom = UtkashRoom.getAppDatabase(MakeMyExam.getAppContext());

    private Video videopositonwise;
    private boolean isaudio = false;
    private String tile_id = "", is_purchase = "", result_date = "", test_submission = "";

    public ExamPrepLayer3Adapter(Activity activity, CourseDetail singleStudy, ArrayList<Video> videoArrayList, String tileIdAPI, String tileTypeAPI, String revertAPI, String tile_id, String is_purchase) {
        this.activity = activity;
        this.singleStudy = singleStudy;
        this.videoArrayList = videoArrayList;
        signUpResponse = SharedPreference.getInstance().getLoggedInUser();
        time = time == 0 ? System.currentTimeMillis() : MakeMyExam.getTime_server();
        this.tileTypeAPI = tileTypeAPI;
        this.tileIdAPI = tileIdAPI;
        this.revertAPI = revertAPI;
        this.tile_id = tile_id;
        //   this.utkashRoom = ((CourseActivity) activity).myDBClass;
        this.is_purchase = is_purchase;
        progress = new Progress(activity);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view;
        view = LayoutInflater.from(activity).inflate(R.layout.detail_single_item, null);
        viewHolder = new SubjectVideosHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((SubjectVideosHolder) holder).setData(videoArrayList.get(position), ((CourseActivity) activity).contentType, position, holder);
    }

    @Override
    public int getItemCount() {
        return videoArrayList == null ? 0 : videoArrayList.size();
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.get_video_link:
                EncryptionData encryptionData = new EncryptionData();
                encryptionData.setName(videodata.getId() + "_" + "0" + "_" + "0");
                if (is_download) {
                    encryptionData.setDownload_click("1");
                } else {
                    encryptionData.setDownload_click("0");
                }
                encryptionData.setCourse_id(SingleStudy.parentCourseId.equalsIgnoreCase("") ? singleStudy.getData().getCourseDetail().getId() : SingleStudy.parentCourseId);
                encryptionData.setTile_id(videodata.getPayloadData().getTile_id());
                encryptionData.setType(videodata.getPayloadData().getTile_type());
                String device_id = (Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID));
                String device_name = android.os.Build.MANUFACTURER + android.os.Build.MODEL;
                device_id = device_id == null && device_id.equalsIgnoreCase("") ? "1234567890" : device_id;
                encryptionData.setDevice_id(device_id);
                encryptionData.setDevice_name(device_name);
                String data1 = new Gson().toJson(encryptionData);
                String encryptjson = AES.encrypt(data1);
                return service.getVideoLink(encryptjson);


            case API.delete_revision:
                EncryptionData delete_revision = new EncryptionData();
                delete_revision.setRevision_id(videodata.getId());
                delete_revision.setCourse_id(singleStudy.getData().getCourseDetail().getId());
                String data = new Gson().toJson(delete_revision);
                String delete = AES.encrypt(data);
                return service.delete_revision(delete);


            case API.API_GET_TEST_INSTRUCTION_DATA:
                EncryptionData masterdatadetailencryptionData = new EncryptionData();
                masterdatadetailencryptionData.setTest_id(quiz_id);
                masterdatadetailencryptionData.setCourse_id(singleStudy.getData().getCourseDetail().getId());

                String getmasterdataencryptionDatadoseStr = new Gson().toJson(masterdatadetailencryptionData);
                String masterdatadoseStrScr = AES.encrypt(getmasterdataencryptionDatadoseStr);
                return service.API_GET_TEST_INSTRUCTION_DATA(masterdatadoseStrScr);

            case API.API_GET_INFO_TEST_SERIES:
                EncryptionData masterdatadetailencryptionData1 = new EncryptionData();
                masterdatadetailencryptionData1.setTest_id(quiz_id);
                masterdatadetailencryptionData1.setCourse_id(singleStudy.getData().getCourseDetail().getId());

                String getmasterdataencryptionDatadoseStr1 = new Gson().toJson(masterdatadetailencryptionData1);
                String masterdatadoseStrScr1 = AES.encrypt(getmasterdataencryptionDatadoseStr1);
                return service.API_GET_INFO_TEST_SERIES(masterdatadoseStrScr1);
        }
        return null;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void SuccessCallBack(JSONObject jsonobject, String apitype, String typeApi, boolean showprogress) throws JSONException {
        Gson gson = new Gson();
        switch (apitype) {

            case API.get_video_link:
                try {
                    if (jsonobject.getString("status").equalsIgnoreCase("true")) {
                        JSONObject jsonObject = new JSONObject(jsonobject.toString());

                        MakeMyExam.setTime_server((Long.parseLong(jsonobject.optString("time"))) * 1000);
                        long expire_time = 0;

                        if (jsonObject.has("data")) {
                            JSONObject dataJsonObject = jsonObject.getJSONObject(Const.DATA);
                            String link = dataJsonObject.optString("link");
                            String token = dataJsonObject.optString("token");

                            String c_type = dataJsonObject.optString("content_type");
                            if (videodata.getVideo_type().equalsIgnoreCase("5") && videodata.getLive_status().equalsIgnoreCase("1")) {
                                if (c_type.equalsIgnoreCase("2")) {
                                    if (isaudio) {
                                        isaudio = false;
                                        Helper.GoToLiveDrmVideoActivity(videodata.getVideo_type(), videodata.getChat_node(), activity, videodata.getId(), videodata.getVideo_type(), videodata.getId(), videodata.getTitle(), "1", videodata.getThumbnail_url(), videodata.getPayloadData().getCourse_id(), videodata.getPayloadData().getTile_id(), videodata.getPayloadData().getTile_type(), videodata.getIs_chat_lock(), String.valueOf(0), SingleStudy.parentCourseId, jsonobject.toString());

                                    } else {
                                        if (dataJsonObject.has("bitrate_urls")) {
                                            JSONArray arrJson = dataJsonObject.optJSONArray("bitrate_urls");
                                            if (arrJson != null && arrJson.length() > 0) {
                                                ArrayList<UrlObject> urlObjects = new ArrayList<>();
                                                for (int i = 0; i < Objects.requireNonNull(arrJson).length(); i++) {
                                                    if (token.equals("")) {
                                                        JSONObject dataObj = arrJson.optJSONObject(i);
                                                        UrlObject urlObject = new UrlObject();
                                                        urlObject.setTitle(dataObj.optString("title"));
                                                        urlObject.setUrl(dataObj.optString("url"));
                                                        urlObject.setSize(dataObj.optString("size"));

                                                        urlObjects.add(urlObject);
                                                    } else {
                                                        JSONObject dataObj = arrJson.optJSONObject(i);
                                                        UrlObject urlObject = new UrlObject();
                                                        urlObject.setTitle(dataObj.optString("title"));
                                                        urlObject.setUrl(dataObj.optString("url"));
                                                        urlObject.setSize(dataObj.optString("size"));

                                                        urlObject.setToken(token);
                                                        urlObjects.add(urlObject);
                                                    }
                                                }
                                                videodata.setBitrate_urls(urlObjects);
                                            }
                                        }
                                        if (is_download) {
                                            is_download = false;
                                            if (videodata.getBitrate_urls() != null && videodata.getBitrate_urls().size() > 0) {
                                                download_service(videodata, position_delete, link);
                                            } else {
                                                Toast.makeText(activity, "no bitrate url found", Toast.LENGTH_SHORT).show();
                                            }
                                        } else
                                            Helper.GoToLiveDrmVideoActivity(videodata.getVideo_type(), videodata.getChat_node(), activity, videodata.getId(), videodata.getVideo_type(), videodata.getId(), videodata.getTitle(), "0", videodata.getThumbnail_url(), videodata.getPayloadData().getCourse_id(), videodata.getPayloadData().getTile_id(), videodata.getPayloadData().getTile_type(), videodata.getIs_chat_lock(), String.valueOf(0), SingleStudy.parentCourseId, jsonobject.toString());
                                    }
                                } else {
                                    if (isaudio) {
                                        isaudio = false;
                                        Helper.GoToLiveAwsVideoActivity(videodata.getVideo_type(), videodata.getChat_node(), activity, videodata.getId(), videodata.getVideo_type(), videodata.getId(), videodata.getTitle(), "1", videodata.getThumbnail_url(), videodata.getPayloadData().getCourse_id(), videodata.getPayloadData().getTile_id(), videodata.getPayloadData().getTile_type(), videodata.getIs_chat_lock(), String.valueOf(0), SingleStudy.parentCourseId, jsonobject.toString());
                                    } else {
                                        if (dataJsonObject.has("bitrate_urls")) {
                                            JSONArray arrJson = dataJsonObject.optJSONArray("bitrate_urls");
                                            if (arrJson != null && arrJson.length() > 0) {
                                                ArrayList<UrlObject> urlObjects = new ArrayList<>();
                                                for (int i = 0; i < Objects.requireNonNull(arrJson).length(); i++) {
                                                    if (token.equals("")) {
                                                        JSONObject dataObj = arrJson.optJSONObject(i);
                                                        UrlObject urlObject = new UrlObject();
                                                        urlObject.setTitle(dataObj.optString("title"));
                                                        urlObject.setUrl(dataObj.optString("url"));
                                                        urlObject.setSize(dataObj.optString("size"));

                                                        urlObjects.add(urlObject);
                                                    } else {
                                                        JSONObject dataObj = arrJson.optJSONObject(i);
                                                        UrlObject urlObject = new UrlObject();
                                                        urlObject.setTitle(dataObj.optString("title"));
                                                        urlObject.setUrl(dataObj.optString("url"));
                                                        urlObject.setSize(dataObj.optString("size"));

                                                        urlObject.setToken(token);
                                                        urlObjects.add(urlObject);
                                                    }
                                                }
                                                videodata.setBitrate_urls(urlObjects);
                                            }
                                        }
                                        if (is_download) {
                                            is_download = false;
                                            if (videodata.getBitrate_urls() != null && videodata.getBitrate_urls().size() > 0) {
                                                download_service(videodata, position_delete, link);
                                            } else {
                                                Toast.makeText(activity, "no bitrate url found", Toast.LENGTH_SHORT).show();
                                            }
                                        } else
                                            Helper.GoToLiveAwsVideoActivity(videodata.getVideo_type(), videodata.getChat_node(), activity, videodata.getId(), videodata.getVideo_type(), videodata.getId(), videodata.getTitle(), "0", videodata.getThumbnail_url(), videodata.getPayloadData().getCourse_id(), videodata.getPayloadData().getTile_id(), videodata.getPayloadData().getTile_type(), videodata.getIs_chat_lock(), String.valueOf(0), SingleStudy.parentCourseId, jsonobject.toString());

                                    }
                                }
                            } else if (videodata.getVideo_type().equalsIgnoreCase("5") && videodata.getLive_status().equalsIgnoreCase("0")) {
                                if (c_type.equalsIgnoreCase("2")) {
                                    if (isaudio) {
                                        isaudio = false;
                                        Helper.GoToLiveDrmVideoActivity(videodata.getVideo_type(), videodata.getChat_node(), activity, videodata.getId(), videodata.getVideo_type(), videodata.getId(), videodata.getTitle(), "1", videodata.getThumbnail_url(), videodata.getPayloadData().getCourse_id(), videodata.getPayloadData().getTile_id(), videodata.getPayloadData().getTile_type(), videodata.getIs_chat_lock(), String.valueOf(0), SingleStudy.parentCourseId, jsonobject.toString());

                                    } else {
                                        if (dataJsonObject.has("bitrate_urls")) {
                                            JSONArray arrJson = dataJsonObject.optJSONArray("bitrate_urls");
                                            if (arrJson != null && arrJson.length() > 0) {
                                                ArrayList<UrlObject> urlObjects = new ArrayList<>();
                                                for (int i = 0; i < Objects.requireNonNull(arrJson).length(); i++) {
                                                    if (token.equals("")) {
                                                        JSONObject dataObj = arrJson.optJSONObject(i);
                                                        UrlObject urlObject = new UrlObject();
                                                        urlObject.setTitle(dataObj.optString("title"));
                                                        urlObject.setUrl(dataObj.optString("url"));
                                                        urlObject.setSize(dataObj.optString("size"));
                                                        urlObjects.add(urlObject);
                                                    } else {
                                                        JSONObject dataObj = arrJson.optJSONObject(i);
                                                        UrlObject urlObject = new UrlObject();
                                                        urlObject.setTitle(dataObj.optString("title"));
                                                        urlObject.setUrl(dataObj.optString("url"));
                                                        urlObject.setSize(dataObj.optString("size"));
                                                        urlObject.setToken(token);
                                                        urlObjects.add(urlObject);
                                                    }
                                                }
                                                videodata.setBitrate_urls(urlObjects);
                                            }
                                        }
                                        if (is_download) {
                                            is_download = false;
                                            if (videodata.getBitrate_urls() != null && videodata.getBitrate_urls().size() > 0) {
                                                download_service(videodata, position_delete, link);
                                            } else {
                                                Toast.makeText(activity, "no bitrate url found", Toast.LENGTH_SHORT).show();
                                            }
                                        } else
                                            Helper.GoToLiveDrmVideoActivity(videodata.getVideo_type(), videodata.getChat_node(), activity, videodata.getId(), videodata.getVideo_type(), videodata.getId(), videodata.getTitle(), "0", videodata.getThumbnail_url(), videodata.getPayloadData().getCourse_id(), videodata.getPayloadData().getTile_id(), videodata.getPayloadData().getTile_type(), videodata.getIs_chat_lock(), String.valueOf(0), SingleStudy.parentCourseId, jsonobject.toString());
                                    }
                                } else {
                                    if (isaudio) {
                                        isaudio = false;
                                        Helper.GoToLiveAwsVideoActivity(videodata.getVideo_type(), videodata.getChat_node(), activity, videodata.getId(), videodata.getVideo_type(), videodata.getId(), videodata.getTitle(), "1", videodata.getThumbnail_url(), videodata.getPayloadData().getCourse_id(), videodata.getPayloadData().getTile_id(), videodata.getPayloadData().getTile_type(), videodata.getIs_chat_lock(), String.valueOf(0), SingleStudy.parentCourseId, jsonobject.toString());
                                    } else {
                                        if (dataJsonObject.has("bitrate_urls")) {
                                            JSONArray arrJson = dataJsonObject.optJSONArray("bitrate_urls");
                                            if (arrJson != null && arrJson.length() > 0) {
                                                ArrayList<UrlObject> urlObjects = new ArrayList<>();
                                                for (int i = 0; i < Objects.requireNonNull(arrJson).length(); i++) {
                                                    if (token.equals("")) {
                                                        JSONObject dataObj = arrJson.optJSONObject(i);
                                                        UrlObject urlObject = new UrlObject();
                                                        urlObject.setTitle(dataObj.optString("title"));
                                                        urlObject.setUrl(dataObj.optString("url"));
                                                        urlObject.setSize(dataObj.optString("size"));

                                                        urlObjects.add(urlObject);
                                                    } else {
                                                        JSONObject dataObj = arrJson.optJSONObject(i);
                                                        UrlObject urlObject = new UrlObject();
                                                        urlObject.setTitle(dataObj.optString("title"));
                                                        urlObject.setUrl(dataObj.optString("url"));
                                                        urlObject.setSize(dataObj.optString("size"));

                                                        urlObject.setToken(token);
                                                        urlObjects.add(urlObject);
                                                    }
                                                }
                                                videodata.setBitrate_urls(urlObjects);
                                            }
                                        }
                                        if (is_download) {
                                            is_download = false;
                                            if (videodata.getBitrate_urls() != null && videodata.getBitrate_urls().size() > 0) {
                                                download_service(videodata, position_delete, link);
                                            } else {
                                                Toast.makeText(activity, "no bitrate url found", Toast.LENGTH_SHORT).show();
                                            }
                                        } else
                                            Helper.GoToLiveAwsVideoActivity(videodata.getVideo_type(), videodata.getChat_node(), activity, videodata.getId(), videodata.getVideo_type(), videodata.getId(), videodata.getTitle(), "0", videodata.getThumbnail_url(), videodata.getPayloadData().getCourse_id(), videodata.getPayloadData().getTile_id(), videodata.getPayloadData().getTile_type(), videodata.getIs_chat_lock(), String.valueOf(0), SingleStudy.parentCourseId, jsonobject.toString());

                                    }
                                }
                            } else if (videodata.getVideo_type().equalsIgnoreCase("0")) {
                                if (c_type.equalsIgnoreCase("2")) {
                                    if (isaudio) {
                                        isaudio = false;
                                        Helper.GoToLiveDrmVideoActivity(videodata.getVideo_type(), videodata.getChat_node(), activity, videodata.getId(), videodata.getVideo_type(), videodata.getId(), videodata.getTitle(), "1", videodata.getThumbnail_url(), videodata.getPayloadData().getCourse_id(), videodata.getPayloadData().getTile_id(), videodata.getPayloadData().getTile_type(), videodata.getIs_chat_lock(), String.valueOf(0), SingleStudy.parentCourseId, jsonobject.toString());
                                    } else {
                                        if (dataJsonObject.has("bitrate_urls")) {
                                            JSONArray arrJson = dataJsonObject.optJSONArray("bitrate_urls");
                                            if (arrJson != null && arrJson.length() > 0) {
                                                ArrayList<UrlObject> urlObjects = new ArrayList<>();
                                                for (int i = 0; i < Objects.requireNonNull(arrJson).length(); i++) {
                                                    if (token.equals("")) {
                                                        JSONObject dataObj = arrJson.optJSONObject(i);
                                                        UrlObject urlObject = new UrlObject();
                                                        urlObject.setTitle(dataObj.optString("title"));
                                                        urlObject.setUrl(dataObj.optString("url"));
                                                        urlObject.setSize(dataObj.optString("size"));

                                                        urlObjects.add(urlObject);
                                                    } else {
                                                        JSONObject dataObj = arrJson.optJSONObject(i);
                                                        UrlObject urlObject = new UrlObject();
                                                        urlObject.setTitle(dataObj.optString("title"));
                                                        urlObject.setUrl(dataObj.optString("url"));
                                                        urlObject.setSize(dataObj.optString("size"));

                                                        urlObject.setToken(token);
                                                        urlObjects.add(urlObject);
                                                    }
                                                }
                                                videodata.setBitrate_urls(urlObjects);
                                            }
                                        }
                                        if (is_download) {
                                            is_download = false;
                                            if (videodata.getBitrate_urls() != null && videodata.getBitrate_urls().size() > 0) {
                                                download_service(videodata, position_delete, link);
                                            } else {
                                                Toast.makeText(activity, "no bitrate url found", Toast.LENGTH_SHORT).show();
                                            }
                                        } else
                                            Helper.GoToLiveDrmVideoActivity(videodata.getVideo_type(), videodata.getChat_node(), activity, videodata.getId(), videodata.getVideo_type(), videodata.getId(), videodata.getTitle(), "0", videodata.getThumbnail_url(), videodata.getPayloadData().getCourse_id(), videodata.getPayloadData().getTile_id(), videodata.getPayloadData().getTile_type(), videodata.getIs_chat_lock(), String.valueOf(0), SingleStudy.parentCourseId, jsonobject.toString());
                                    }
                                } else if (c_type.equalsIgnoreCase("")) {
                                    String audio_url = dataJsonObject.optString("audio_url");
                                    JSONObject objectcookie = null;
                                    if (dataJsonObject.has("cookie") && dataJsonObject.optJSONObject("cookie") != null) {
                                        objectcookie = dataJsonObject.optJSONObject("cookie");
                                    } else {
                                        objectcookie = new JSONObject("{}");
                                    }
                                    if (dataJsonObject.optLong("expiry_seconds") != 0) {
                                        expire_time = (Long.parseLong(jsonobject.optString("time"))) + dataJsonObject.optLong("expiry_seconds");
                                    }
                                    if (dataJsonObject.has("bitrate_urls")) {
                                        JSONArray arrJson = dataJsonObject.optJSONArray("bitrate_urls");
                                        if (arrJson != null && arrJson.length() > 0) {
                                            ArrayList<UrlObject> urlObjects = new ArrayList<>();
                                            for (int i = 0; i < Objects.requireNonNull(arrJson).length(); i++) {
                                                if (token.equals("")) {
                                                    JSONObject dataObj = arrJson.optJSONObject(i);
                                                    UrlObject urlObject = new UrlObject();
                                                    urlObject.setTitle(dataObj.optString("title"));
                                                    urlObject.setUrl(dataObj.optString("url"));
                                                    urlObject.setSize(dataObj.optString("size"));

                                                    urlObjects.add(urlObject);
                                                } else {
                                                    JSONObject dataObj = arrJson.optJSONObject(i);
                                                    UrlObject urlObject = new UrlObject();
                                                    urlObject.setTitle(dataObj.optString("title"));
                                                    urlObject.setUrl(dataObj.optString("url"));
                                                    urlObject.setSize(dataObj.optString("size"));

                                                    urlObject.setToken(token);
                                                    urlObjects.add(urlObject);
                                                }
                                            }
                                            videodata.setBitrate_urls(urlObjects);
                                        }
                                    }
                                    if (is_download) {
                                        is_download = false;
                                        if (videodata.getBitrate_urls() != null && videodata.getBitrate_urls().size() > 0) {
                                            download_service(videodata, position_delete, link);
                                        } else {
                                            Toast.makeText(activity, "no bitrate url found", Toast.LENGTH_SHORT).show();

                                        }
                                        isaudio = false;
                                    } else {
                                        if (isaudio) {
                                            isaudio = false;
                                            is_download = false;
                                            if (!utkashRoom.getaudiodao().isvideo_exit(videodata.getId(), MakeMyExam.userId)) {
                                                AudioTable audioTable = new AudioTable();
                                                audioTable.setVideo_id(videodata.getId());
                                                audioTable.setJw_url(videodata.getThumbnail_url());
                                                audioTable.setVideo_name(videodata.getTitle());
                                                audioTable.setAudio_currentpos(0L);
                                                audioTable.setUser_id(MakeMyExam.userId);
                                                String course_id = "";
                                                if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                                                    course_id = singleStudy.getData().getCourseDetail().getId() + "#";
                                                } else {
                                                    course_id = SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId();
                                                }
                                                audioTable.setCourse_id(course_id);
                                                utkashRoom.getaudiodao().addUser(audioTable);
                                                extractJWPlayerUrl(audio_url, audioTable, videodata, objectcookie.toString());
                                            } else {
                                                AudioTable audioTable = new AudioTable();
                                                audioTable.setVideo_id(videodata.getId());
                                                audioTable.setJw_url(videodata.getThumbnail_url());
                                                audioTable.setVideo_name(videodata.getTitle());
                                                audioTable.setAudio_currentpos(utkashRoom.getaudiodao().getuser(videodata.getId(), MakeMyExam.userId).getAudio_currentpos());
                                                extractJWPlayerUrl(audio_url, audioTable, videodata, objectcookie.toString());

                                            }

                                        } else {
                                            isaudio = false;
                                            is_download = false;
                                            if (utkashRoom.getvideoDao().isvideo_exit(videodata.getId(), MakeMyExam.userId)) {
                                                if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                                                    Helper.GoToJWVideo_Params_newarrayobject(activity, link, videodata.getId(), videodata.getTitle(), utkashRoom.getvideoDao().getuser(videodata.getId(), MakeMyExam.userId).getVideo_currentpos(), singleStudy.getData().getCourseDetail().getId() + "#", videodata.getPayloadData().getTile_id(), videodata.getPayloadData().getTile_type(), videodata.getBitrate_urls(), expire_time, objectcookie.toString());
                                                } else {
                                                    Helper.GoToJWVideo_Params_newarrayobject(activity, link, videodata.getId(), videodata.getTitle(), utkashRoom.getvideoDao().getuser(videodata.getId(), MakeMyExam.userId).getVideo_currentpos(), SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId(), videodata.getPayloadData().getTile_id(), videodata.getPayloadData().getTile_type(), videodata.getBitrate_urls(), expire_time, objectcookie.toString());
                                                }
                                            } else {
                                                VideoTable videoTable = new VideoTable();
                                                videoTable.setVideo_id(videodata.getId());
                                                videoTable.setVideo_name(videodata.getTitle());
                                                videoTable.setJw_url(link);
                                                videoTable.setVideo_currentpos(0L);
                                                videoTable.setUser_id(MakeMyExam.userId);
                                                if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                                                    videoTable.setCourse_id(singleStudy.getData().getCourseDetail().getId() + "#");
                                                    Helper.GoToJWVideo_Params_newarrayobject(activity, link, videodata.getId(), videodata.getTitle(), 0L, singleStudy.getData().getCourseDetail().getId() + "#", videodata.getPayloadData().getTile_id(), videodata.getPayloadData().getTile_type(), videodata.getBitrate_urls(), expire_time, objectcookie.toString());
                                                    utkashRoom.getvideoDao().addUser(videoTable);
                                                } else {
                                                    videoTable.setCourse_id(SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());
                                                    Helper.GoToJWVideo_Params_newarrayobject(activity, link, videodata.getId(), videodata.getTitle(), 0L, SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId(), videodata.getPayloadData().getTile_id(), videodata.getPayloadData().getTile_type(), videodata.getBitrate_urls(), expire_time, objectcookie.toString());
                                                    utkashRoom.getvideoDao().addUser(videoTable);
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    if (isaudio) {
                                        isaudio = false;
                                        Helper.GoToLiveAwsVideoActivity(videodata.getVideo_type(), videodata.getChat_node(), activity, videodata.getId(), videodata.getVideo_type(), videodata.getId(), videodata.getTitle(), "1", videodata.getThumbnail_url(), videodata.getPayloadData().getCourse_id(), videodata.getPayloadData().getTile_id(), videodata.getPayloadData().getTile_type(), videodata.getIs_chat_lock(), String.valueOf(0), SingleStudy.parentCourseId, jsonobject.toString());
                                    } else {
                                        if (dataJsonObject.has("bitrate_urls")) {
                                            JSONArray arrJson = dataJsonObject.optJSONArray("bitrate_urls");
                                            if (arrJson != null && arrJson.length() > 0) {
                                                ArrayList<UrlObject> urlObjects = new ArrayList<>();
                                                for (int i = 0; i < Objects.requireNonNull(arrJson).length(); i++) {
                                                    if (token.equals("")) {
                                                        JSONObject dataObj = arrJson.optJSONObject(i);
                                                        UrlObject urlObject = new UrlObject();
                                                        urlObject.setTitle(dataObj.optString("title"));
                                                        urlObject.setUrl(dataObj.optString("url"));
                                                        urlObject.setSize(dataObj.optString("size"));

                                                        urlObjects.add(urlObject);
                                                    } else {
                                                        JSONObject dataObj = arrJson.optJSONObject(i);
                                                        UrlObject urlObject = new UrlObject();
                                                        urlObject.setTitle(dataObj.optString("title"));
                                                        urlObject.setUrl(dataObj.optString("url"));
                                                        urlObject.setSize(dataObj.optString("size"));

                                                        urlObject.setToken(token);
                                                        urlObjects.add(urlObject);
                                                    }
                                                }
                                                videodata.setBitrate_urls(urlObjects);
                                            }
                                        }
                                        if (is_download) {
                                            is_download = false;
                                            if (videodata.getBitrate_urls() != null && videodata.getBitrate_urls().size() > 0) {
                                                download_service(videodata, position_delete, link);
                                            } else {
                                                Toast.makeText(activity, "no bitrate url found", Toast.LENGTH_SHORT).show();
                                            }
                                        } else
                                            Helper.GoToLiveAwsVideoActivity(videodata.getVideo_type(), videodata.getChat_node(), activity, videodata.getId(), videodata.getVideo_type(), videodata.getId(), videodata.getTitle(), "0", videodata.getThumbnail_url(), videodata.getPayloadData().getCourse_id(), videodata.getPayloadData().getTile_id(), videodata.getPayloadData().getTile_type(), videodata.getIs_chat_lock(), String.valueOf(0), SingleStudy.parentCourseId, jsonobject.toString());
                                    }
                                }
                            } else if (videodata != null && videodata.getVideo_type().equalsIgnoreCase("4")) {
                                if ((videodata.getLive_status().equalsIgnoreCase("1") || videodata.getLive_status().equalsIgnoreCase("0")) && videodata.getOpen_in_app().equalsIgnoreCase("0"))
                                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + link)));

                            } else if (videodata != null && videodata.getVideo_type().equalsIgnoreCase("1")) {
                                if (videodata.getOpen_in_app().equalsIgnoreCase("0"))
                                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + link)));

                            } else {
                                String audio_url = dataJsonObject.optString("audio_url");
                                JSONObject objectcookie = null;
                                if (dataJsonObject.has("cookie") && dataJsonObject.optJSONObject("cookie") != null) {
                                    objectcookie = dataJsonObject.optJSONObject("cookie");
                                } else {
                                    objectcookie = new JSONObject("{}");
                                }
                                if (dataJsonObject.optLong("expiry_seconds") != 0) {
                                    expire_time = (Long.parseLong(jsonobject.optString("time"))) + dataJsonObject.optLong("expiry_seconds");
                                }
                                if (dataJsonObject.has("bitrate_urls")) {
                                    JSONArray arrJson = dataJsonObject.optJSONArray("bitrate_urls");
                                    if (arrJson != null && arrJson.length() > 0) {
                                        ArrayList<UrlObject> urlObjects = new ArrayList<>();
                                        for (int i = 0; i < Objects.requireNonNull(arrJson).length(); i++) {
                                            if (token.equals("")) {
                                                JSONObject dataObj = arrJson.optJSONObject(i);
                                                UrlObject urlObject = new UrlObject();
                                                urlObject.setTitle(dataObj.optString("title"));
                                                urlObject.setUrl(dataObj.optString("url"));
                                                urlObject.setSize(dataObj.optString("size"));

                                                urlObjects.add(urlObject);
                                            } else {
                                                JSONObject dataObj = arrJson.optJSONObject(i);
                                                UrlObject urlObject = new UrlObject();
                                                urlObject.setTitle(dataObj.optString("title"));
                                                urlObject.setUrl(dataObj.optString("url"));
                                                urlObject.setSize(dataObj.optString("size"));

                                                urlObject.setToken(token);
                                                urlObjects.add(urlObject);
                                            }
                                        }
                                        videodata.setBitrate_urls(urlObjects);
                                    }
                                }
                                if (is_download) {
                                    is_download = false;
                                    if (videodata.getBitrate_urls() != null && videodata.getBitrate_urls().size() > 0) {
                                        download_service(videodata, position_delete, link);
                                    } else {
                                        Toast.makeText(activity, "no bitrate url found", Toast.LENGTH_SHORT).show();

                                    }
                                    isaudio = false;
                                } else {
                                    if (isaudio) {
                                        isaudio = false;
                                        is_download = false;
                                        if (!utkashRoom.getaudiodao().isvideo_exit(videodata.getId(), MakeMyExam.userId)) {
                                            AudioTable audioTable = new AudioTable();
                                            audioTable.setVideo_id(videodata.getId());
                                            audioTable.setJw_url(videodata.getThumbnail_url());
                                            audioTable.setVideo_name(videodata.getTitle());
                                            audioTable.setAudio_currentpos(0L);
                                            audioTable.setUser_id(MakeMyExam.userId);
                                            String course_id = "";
                                            if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                                                course_id = singleStudy.getData().getCourseDetail().getId() + "#";
                                            } else {
                                                course_id = SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId();
                                            }
                                            audioTable.setCourse_id(course_id);
                                            utkashRoom.getaudiodao().addUser(audioTable);
                                            extractJWPlayerUrl(audio_url, audioTable, videodata, objectcookie.toString());
                                        } else {
                                            AudioTable audioTable = new AudioTable();
                                            audioTable.setVideo_id(videodata.getId());
                                            audioTable.setJw_url(videodata.getThumbnail_url());
                                            audioTable.setVideo_name(videodata.getTitle());
                                            audioTable.setAudio_currentpos(utkashRoom.getaudiodao().getuser(videodata.getId(), MakeMyExam.userId).getAudio_currentpos());
                                            extractJWPlayerUrl(audio_url, audioTable, videodata, objectcookie.toString());

                                        }
                                    }
                                    else {
                                        isaudio = false;
                                        is_download = false;
                                        if (utkashRoom.getvideoDao().isvideo_exit(videodata.getId(), MakeMyExam.userId)) {
                                            if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                                                Helper.GoToJWVideo_Params_newarrayobject(activity, link, videodata.getId(), videodata.getTitle(), utkashRoom.getvideoDao().getuser(videodata.getId(), MakeMyExam.userId).getVideo_currentpos(), singleStudy.getData().getCourseDetail().getId() + "#", videodata.getPayloadData().getTile_id(), videodata.getPayloadData().getTile_type(), videodata.getBitrate_urls(), expire_time, objectcookie.toString());
                                            } else {
                                                Helper.GoToJWVideo_Params_newarrayobject(activity, link, videodata.getId(), videodata.getTitle(), utkashRoom.getvideoDao().getuser(videodata.getId(), MakeMyExam.userId).getVideo_currentpos(), SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId(), videodata.getPayloadData().getTile_id(), videodata.getPayloadData().getTile_type(), videodata.getBitrate_urls(), expire_time, objectcookie.toString());
                                            }
                                        } else {
                                            VideoTable videoTable = new VideoTable();
                                            videoTable.setVideo_id(videodata.getId());
                                            videoTable.setVideo_name(videodata.getTitle());
                                            videoTable.setJw_url(link);
                                            videoTable.setVideo_currentpos(0L);
                                            videoTable.setUser_id(MakeMyExam.userId);
                                            if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                                                videoTable.setCourse_id(singleStudy.getData().getCourseDetail().getId() + "#");
                                                Helper.GoToJWVideo_Params_newarrayobject(activity, link, videodata.getId(), videodata.getTitle(), 0L, singleStudy.getData().getCourseDetail().getId() + "#", videodata.getPayloadData().getTile_id(), videodata.getPayloadData().getTile_type(), videodata.getBitrate_urls(), expire_time, objectcookie.toString());
                                                utkashRoom.getvideoDao().addUser(videoTable);
                                            } else {
                                                videoTable.setCourse_id(SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());
                                                Helper.GoToJWVideo_Params_newarrayobject(activity, link, videodata.getId(), videodata.getTitle(), 0L, SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId(), videodata.getPayloadData().getTile_id(), videodata.getPayloadData().getTile_type(), videodata.getBitrate_urls(), expire_time, objectcookie.toString());
                                                utkashRoom.getvideoDao().addUser(videoTable);
                                            }
                                        }
                                    }
                                }

                            }


                        } else {
                            Toast.makeText(activity, "url not found", Toast.LENGTH_SHORT).show();
                        }
                        isaudio = false;
                        is_download = false;
                    } else {
                        isaudio = false;
                        is_download = false;
                        this.ErrorCallBack(jsonobject.getString(Const.MESSAGE), apitype, typeApi);
                        RetrofitResponse.GetApiData(activity, jsonobject.getString(Const.AUTH_CODE), jsonobject.getString(Const.MESSAGE), false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case API.delete_revision:
                if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                    Toast.makeText(activity, "" + jsonobject.optString(Const.MESSAGE), Toast.LENGTH_SHORT).show();

                    videoArrayList.remove(position_delete);
                    if (videoArrayList.size() == 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyItemRemoved(position_delete);
                        notifyItemRangeChanged(position_delete, videoArrayList.size());
                    }

                } else if (jsonobject.optString("status").equals(Const.FALSE)) {
                    RetrofitResponse.GetApiData(activity, jsonobject.has(Const.AUTH_CODE) ? jsonobject.getString(Const.AUTH_CODE) : "", jsonobject.getString(Const.MESSAGE), false);
                }
                break;
            case API.API_GET_TEST_INSTRUCTION_DATA:
                if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                    JSONObject data1 = jsonobject.getJSONObject("data");
                    InstructionData instructionData = gson.fromJson(data1.toString(), InstructionData.class);

                    showPopUp(instructionData);

                } else if (jsonobject.optString("status").equals(Const.FALSE)) {
                    RetrofitResponse.GetApiData(activity, jsonobject.has(Const.AUTH_CODE) ? jsonobject.getString(Const.AUTH_CODE) : "", jsonobject.getString(Const.MESSAGE), false);
                }
                break;

            case API.API_GET_INFO_TEST_SERIES:
                if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                    Long time = jsonobject.optLong("time");

                    TestseriesBase testseriesBase = null;
                    try {
                        testseriesBase = gson.fromJson(jsonobject.toString(), TestseriesBase.class);
                        if (testseriesBase.getData().getQuestions() != null && testseriesBase.getData().getQuestions().size() > 0 && lang == 1) {

                            Intent quizView = new Intent(activity, TestBaseActivity.class);
                            quizView.putExtra(Const.STATUS, false);
                            quizView.putExtra(Const.TEST_SERIES_ID, quiz_id);
                            quizView.putExtra(Const.TEST_SERIES_Name, quiz_name);
                            SharedPreference.getInstance().putString("test_series", jsonobject.toString());
                            quizView.putExtra("course_id", SingleStudy.parentCourseId.equalsIgnoreCase("") ? singleStudy.getData().getCourseDetail().getId() : SingleStudy.parentCourseId);
                            quizView.putExtra("TOTAL_QUESTIONS", totalQuestion);

                            quizView.putExtra("first_attempt", first_attempt);
                            quizView.putExtra("result_date", result_date);
                            quizView.putExtra("test_submission", test_submission);
                            quizView.putExtra(Const.LANG, lang);
                            quizView.putExtra("time", time);
                            quizView.putExtra("enddate", videopositonwise.getEnd_date());
                            Helper.gotoActivity(quizView, activity);
                        } else if (testseriesBase.getData().getQuestionsHindi() != null && testseriesBase.getData().getQuestionsHindi().size() > 0 && lang == 2) {
                            testseriesBase.getData().setQuestions(testseriesBase.getData().getQuestionsHindi());
                            Intent quizView = new Intent(activity, TestBaseActivity.class);
                            quizView.putExtra(Const.STATUS, false);
                            quizView.putExtra(Const.TEST_SERIES_ID, quiz_id);
                            quizView.putExtra(Const.TEST_SERIES_Name, quiz_name);
                            SharedPreference.getInstance().putString("test_series", jsonobject.toString());
                            quizView.putExtra("course_id", SingleStudy.parentCourseId.equalsIgnoreCase("") ? singleStudy.getData().getCourseDetail().getId() : SingleStudy.parentCourseId);
                            quizView.putExtra("TOTAL_QUESTIONS", totalQuestion);
                            quizView.putExtra("first_attempt", first_attempt);
                            quizView.putExtra("result_date", result_date);
                            quizView.putExtra("test_submission", test_submission);
                            quizView.putExtra("time", time);
                            quizView.putExtra("enddate", videopositonwise.getEnd_date());
                            quizView.putExtra(Const.LANG, lang);
                            Helper.gotoActivity(quizView, activity);
                        } else {
                            Toast.makeText(activity, "No Question Found", Toast.LENGTH_SHORT).show();

                        }

                    } catch (Exception e) {
                        Toast.makeText(activity, "Something went wrong.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    RetrofitResponse.GetApiData(activity, jsonobject.has(Const.AUTH_CODE) ? jsonobject.getString(Const.AUTH_CODE) : "", jsonobject.getString(Const.MESSAGE), false);
                }
                break;
        }
    }

    public void download_service(Video video, int position, String link) {
        VideosDownload videosDownload = new VideosDownload();
        if (utkashRoom.getvideoDownloadao().isvideo_exit(video.getId(), MakeMyExam.userId)) {
            videosDownload = utkashRoom.getvideoDownloadao().getvideo_byuserid(video.getId(), MakeMyExam.userId);
            videosDownload.setJw_url(link);
            videosDownload.setVideo_status("Download Running");
            videosDownload.setThumbnail_url(video.getThumbnail_url());
        } else {
            videosDownload.setVideo_id(video.getId());
            if (video.getVideo_type().equalsIgnoreCase("6")) {
                videosDownload.setJw_url(link);
                videosDownload.setThumbnail_url(video.getThumbnail_url());
            }
            String course_id = "";
            if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                course_id = singleStudy.getData().getCourseDetail().getId() + "#";
            } else {
                course_id = SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId();
            }
            videosDownload.setVideo_name(video.getTitle());
            videosDownload.setVideo_history(video.getId() + "_" + MakeMyExam.userId + "_" + video.getId() + "_" + course_id);
            videosDownload.setToal_downloadlocale(0L);
            videosDownload.setPercentage(0);
            videosDownload.setOriginalFileLengthString("0");
            videosDownload.setCourse_id(course_id);
            videosDownload.setLengthInMb("");
            videosDownload.setIs_complete("0");
            videosDownload.setVideo_status("Download Running");
            videosDownload.setUser_id(MakeMyExam.userId);
            videosDownload.setVideo_token(videodata.getBitrate_urls().get(0).getToken() == null ? "" : videodata.getBitrate_urls().get(0).getToken());
            videosDownload.setVideoCurrentPosition(0L);
        }

        if (progress.isShowing())
            progress.dismiss();

        if (video.getBitrate_urls() != null && video.getBitrate_urls().size() > 0) {
            openwatchlist_dailog_resource(activity, video.getBitrate_urls(), videosDownload, position);
        }
    }

    public void openwatchlist_dailog_resource(Context context, ArrayList<UrlObject> mediaResponseMap, VideosDownload videosDownload, int pos) {
        try {
            if (progress.isShowing())
                progress.dismiss();
            watchlist = new BottomSheetDialog(context, R.style.videosheetDialogTheme);
            watchlist.setContentView(R.layout.quality_download_view);
            Objects.requireNonNull(watchlist.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            watchlist.setCancelable(false);
            watchlist.setCanceledOnTouchOutside(true);
            LinearLayout lnPreparing = watchlist.findViewById(R.id.lnPreparing);
            RecyclerView qualityrecycler = watchlist.findViewById(R.id.qualityrecycler);
            TextView btnLow = watchlist.findViewById(R.id.btnLow);
            TextView tvResName = watchlist.findViewById(R.id.tvResName);
            TextView btnMedium = watchlist.findViewById(R.id.btnMedium);
            TextView btnHigh = watchlist.findViewById(R.id.btnHigh);
            Objects.requireNonNull(tvResName).setText(videosDownload.getVideo_name());
            if (mediaResponseMap.size() > 0) {
                Objects.requireNonNull(qualityrecycler).setVisibility(View.VISIBLE);
                DownloadQualityAdapter downloadQualityAdapter = new DownloadQualityAdapter(activity, mediaResponseMap, this, pos, videosDownload);
                qualityrecycler.setAdapter(downloadQualityAdapter);
            }

        /*    if (mediaResponseMap.size() >= 3) {
                Objects.requireNonNull(btnLow).setVisibility(View.VISIBLE);
                Objects.requireNonNull(btnMedium).setVisibility(View.VISIBLE);
                Objects.requireNonNull(btnHigh).setVisibility(View.VISIBLE);

            } else if (mediaResponseMap.size() == 2) {
                Objects.requireNonNull(btnLow).setVisibility(View.VISIBLE);
                Objects.requireNonNull(btnMedium).setVisibility(View.VISIBLE);
            } else if (mediaResponseMap.size() == 1) {
                Objects.requireNonNull(btnLow).setVisibility(View.VISIBLE);
            }*/

            Objects.requireNonNull(lnPreparing).setVisibility(View.GONE);

            Objects.requireNonNull(btnLow).setOnClickListener(new OnSingleClickListener(() -> {
                videoArrayList.get(pos).setVideo_status("Download Running");
                notifyItemChanged(pos);
                dismissCalculatorDialog(watchlist);

                AsyncTask.execute(() -> {
                    if (utkashRoom.getvideoDownloadao().isvideo_exit(videosDownload.getVideo_id(), MakeMyExam.userId)) {
                        utkashRoom.getvideoDownloadao().update_videostatus(videosDownload.getVideo_id(), videosDownload.getVideo_status(), MakeMyExam.userId);
                    } else {
                        utkashRoom.getvideoDownloadao().addUser(videosDownload);
                    }
                    Download_dialog(mediaResponseMap.get(0).getUrl(), videosDownload, pos);
                });
                return null;
            }));
            Objects.requireNonNull(btnMedium).setOnClickListener(new OnSingleClickListener(() -> {
                videoArrayList.get(pos).setVideo_status("Download Running");
                notifyItemChanged(pos);
                dismissCalculatorDialog(watchlist);

                AsyncTask.execute(() -> {

                    if (utkashRoom.getvideoDownloadao().isvideo_exit(videosDownload.getVideo_id(), MakeMyExam.userId)) {
                        utkashRoom.getvideoDownloadao().update_videostatus(videosDownload.getVideo_id(), videosDownload.getVideo_status(), MakeMyExam.userId);
                    } else {
                        utkashRoom.getvideoDownloadao().addUser(videosDownload);
                    }

                    Download_dialog(mediaResponseMap.get(1).getUrl(), videosDownload, pos);


                });


                return null;
            }));
            Objects.requireNonNull(btnHigh).setOnClickListener(new OnSingleClickListener(() -> {
                videoArrayList.get(pos).setVideo_status("Download Running");
                notifyItemChanged(pos);
                dismissCalculatorDialog(watchlist);

                AsyncTask.execute(() -> {
                    if (utkashRoom.getvideoDownloadao().isvideo_exit(videosDownload.getVideo_id(), MakeMyExam.userId)) {
                        utkashRoom.getvideoDownloadao().update_videostatus(videosDownload.getVideo_id(), videosDownload.getVideo_status(), MakeMyExam.userId);
                    } else {
                        utkashRoom.getvideoDownloadao().addUser(videosDownload);
                    }
                    Download_dialog(mediaResponseMap.get(2).getUrl(), videosDownload, pos);
                });
                return null;
            }));
            watchlist.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    is_download = false;
                }
            });
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
        }
    }


    public void extractJWPlayerUrl(String mediaUrl, AudioTable videoData, Video videodata, String objectcookie) {

        if (mediaUrl != null && !mediaUrl.equalsIgnoreCase("")) {
            try {
                if (AudioPlayerService.player != null) {
                    if (AudioPlayerService.type.equalsIgnoreCase("Youtube")) {
                        utkashRoom.getyoutubedata().updateTime(AudioPlayerService.player.getCurrentPosition(), AudioPlayerService.media_id, MakeMyExam.userId, "1");

                    } else
                        utkashRoom.getaudiodao().update_audio_currentpos(AudioPlayerService.videoid, MakeMyExam.userId, AudioPlayerService.player.getCurrentPosition());

                    if (AudioPlayerService.player != null) {
                        AudioPlayerService.player.release();
                        AudioPlayerService.player = null;
                    }
                }
                if (AudioPlayerService.isAudioPlaying) {
                    Intent audioPlayerIntent = new Intent(activity, AudioPlayerService.class);
                    audioPlayerIntent.setAction("Stop_Service");
                    Util.startForegroundService(activity, audioPlayerIntent);
                    AudioPlayerService.video_currentpos = 0L;
                    AudioPlayerService.media_id = "";
                }
            }catch (Exception e)
            {
                Log.d("extractJWPlayerUrl", e.getMessage());
            }
            finally {
                Intent intent = new Intent(activity, AudioPlayerActivty.class);
                AudioPlayerService.videoid = "";
                AudioPlayerService.media_id = "";
                intent.putExtra("url", mediaUrl);
                intent.putExtra("videoid", videodata.getId());
                intent.putExtra("currentpos", videoData.getAudio_currentpos());
                intent.putExtra("list_video", videoArrayList);
                intent.putExtra("pos", "" + position_delete);
                intent.putExtra("tile_id", videodata.getPayloadData().getTile_id());
                intent.putExtra("tile_type", videodata.getPayloadData().getTile_type());
                intent.putExtra("video_name", videoData.getVideo_name());
                intent.putExtra("image_url", videodata.getThumbnail_url());
                intent.putExtra("objectcookie", objectcookie.toString());
                if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                    intent.putExtra("course_id", singleStudy.getData().getCourseDetail().getId() + "#");
                } else {
                    intent.putExtra("course_id", SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());
                }
                Helper.gotoActivity(intent, activity);
            }


        }
    }


    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {
        is_download = false;
        isaudio = false;
        Toast.makeText(activity, "" + jsonstring, Toast.LENGTH_SHORT).show();

    }

    @SuppressLint("NotifyDataSetChanged")
    public void sendlist(ArrayList<Video> videoArrayList) {
        this.videoArrayList = videoArrayList;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void changedata(int parseInt) {
        notifyDataSetChanged();
    }


    @Override
    public void getVideourl(int pos, UrlObject urlObject, int itempos, VideosDownload videosDownload) {
        videoArrayList.get(itempos).setVideo_status("Download Running");
        notifyItemChanged(itempos);
        dismissCalculatorDialog(watchlist);

        AsyncTask.execute(() -> {
            if (utkashRoom.getvideoDownloadao().isvideo_exit(videosDownload.getVideo_id(), MakeMyExam.userId)) {
                utkashRoom.getvideoDownloadao().update_videostatus(videosDownload.getVideo_id(), videosDownload.getVideo_status(), MakeMyExam.userId);
            } else {
                utkashRoom.getvideoDownloadao().addUser(videosDownload);
            }
            Download_dialog(urlObject.getUrl(), videosDownload, itempos);
        });
    }

    public void notifydata(ArrayList<Video> videoArrayList, String tileIdAPI, String tileTypeAPI, String revertAPI, String tile_id, String is_purchase) {
        this.videoArrayList = videoArrayList;
        this.tileTypeAPI = tileTypeAPI;
        this.tileIdAPI = tileIdAPI;
        this.revertAPI = revertAPI;
        this.tile_id = tile_id;
        this.is_purchase = is_purchase;
        notifyDataSetChanged();
    }

    protected class SubjectVideosHolder extends RecyclerView.ViewHolder {

        final ImageView thumb, optionMenuImgView;
        GifImageView liveIv;
        RelativeLayout lockRL;
        CardView videoTile;


        TextView title, durationTV, watch_now, listne_now, learn, practice, share, read_now;
        RelativeLayout study_single_itemLL;
        LinearLayout attemptLL;

        public SubjectVideosHolder(View itemView) {
            super(itemView);
            lockRL = itemView.findViewById(R.id.lockRL);
            title = itemView.findViewById(R.id.ibt_single_sub_vd_tv_title);
            durationTV = itemView.findViewById(R.id.durationTV);
            read_now = itemView.findViewById(R.id.read_now);
            liveIv = itemView.findViewById(R.id.liveIV);
            thumb = itemView.findViewById(R.id.ibt_single_sub_vd_iv);
            videoTile = itemView.findViewById(R.id.ibt_single_sub_vd_RL);
            //  ImageRl = itemView.findViewById(R.id.ImageRL);
            study_single_itemLL = itemView.findViewById(R.id.study_single_itemLL);
            attemptLL = itemView.findViewById(R.id.attemptLL);
            learn = itemView.findViewById(R.id.learn);
            practice = itemView.findViewById(R.id.practice);
            optionMenuImgView = itemView.findViewById(R.id.optionMenuImgView);
            watch_now = itemView.findViewById(R.id.watch_now);
            listne_now = itemView.findViewById(R.id.listne_now);
            share = itemView.findViewById(R.id.share);
        }

        @SuppressLint("SetTextI18n")
        public void setData(Video video, String fileType, int position, RecyclerView.ViewHolder holder) {

            if (singleStudy != null && singleStudy.getData().getCourseDetail() != null) {
                if (singleStudy.getData().getCourseDetail().getIsPurchased().equals("1")) {
                    video.setIs_locked("0");
                }
            }

            if (video.getIs_locked().equals("0")) {
                lockRL.setVisibility(View.GONE);
            } else {
                lockRL.setVisibility(View.VISIBLE);
            }

            if (((CourseActivity) activity).contentType.equals(Const.CONTENT + tileIdAPI)) {
                switch (video.getFile_type()) {
                    case Const.CUSTOME_REVISION:
                        attemptLL.setVisibility(View.GONE);
                        share.setVisibility(View.GONE);
                        optionMenuImgView.setVisibility(View.VISIBLE);
                        optionMenuImgView.setImageResource(R.drawable.delete);
                        durationTV.setVisibility(View.VISIBLE);
                        durationTV.setText("Revision Set");
                        title.setText(video.getTitle());
                        watch_now.setVisibility(View.GONE);
                        liveIv.setVisibility(View.GONE);
                        read_now.setVisibility(View.VISIBLE);
                        listne_now.setVisibility(View.GONE);
                        thumb.setImageResource(R.drawable.square_thumbnail);
                        setOnClick(video, position, "read", holder);
                        break;
                    case Const.CUSTOME_LINK:
                        durationTV.setVisibility(View.VISIBLE);
                        durationTV.setText("Search WebLink");
                        read_now.setVisibility(View.VISIBLE);
                        share.setVisibility(View.GONE);
                        attemptLL.setVisibility(View.GONE);
                        optionMenuImgView.setImageResource(R.drawable.delete);
                        optionMenuImgView.setVisibility(View.VISIBLE);
                        title.setText(video.getTitle());
                        watch_now.setVisibility(View.GONE);
                        listne_now.setVisibility(View.GONE);
                        liveIv.setVisibility(View.GONE);

                        if (video.getThumbnail_url().equalsIgnoreCase("")) {
                            thumb.setImageResource(R.drawable.square_thumbnail);
                        } else {
                            Helper.setThumbnailImage(activity, video.getThumbnail_url(), activity.getDrawable(R.drawable.square_thumbnail), thumb);
                        }
                        setOnClick(video, position, "read", holder);
                        break;
                    case Const.CUSTOME_VIDEO:
                        durationTV.setVisibility(View.VISIBLE);
                        durationTV.setText("Search VideoRef");
                        attemptLL.setVisibility(View.GONE);
                        share.setVisibility(View.GONE);
                        read_now.setVisibility(View.GONE);
                        optionMenuImgView.setImageResource(R.drawable.delete);
                        optionMenuImgView.setVisibility(View.VISIBLE);
                        title.setText(video.getTitle());
                        watch_now.setVisibility(View.VISIBLE);
                        listne_now.setVisibility(View.GONE);
                        liveIv.setVisibility(View.GONE);
                        if (video.getThumbnail_url().equalsIgnoreCase("")) {
                            thumb.setImageResource(R.drawable.square_thumbnail);
                        } else {
                            Helper.setThumbnailImage(activity, video.getThumbnail_url(), activity.getDrawable(R.drawable.square_thumbnail), thumb);
                        }
                        setOnClick(video, position, "watch", holder);
                        break;
                    case Const.CONTENT_PDF:
                        share.setVisibility(View.VISIBLE);
                        attemptLL.setVisibility(View.GONE);
                        optionMenuImgView.setVisibility(View.GONE);
                        title.setText(video.getTitle());
                        liveIv.setVisibility(View.GONE);
                        durationTV.setVisibility(View.GONE);
                        watch_now.setVisibility(View.GONE);
                        listne_now.setVisibility(View.GONE);
                        read_now.setVisibility(View.VISIBLE);
                        // durationTV.setText("Pdf Type");
                        if (video.getThumbnail_url().equalsIgnoreCase("")) {
                            thumb.setImageResource(R.mipmap.pdf);
                        } else {
                            Helper.setThumbnailImage(activity, video.getThumbnail_url(), activity.getDrawable(R.drawable.square_thumbnail), thumb);
                        }
                        setPdfeOnClick(video);
                        break;

                    case Const.CONTENT_PPT:
                        optionMenuImgView.setVisibility(View.GONE);
                        title.setText(video.getTitle());
                        durationTV.setVisibility(View.GONE);
                        read_now.setVisibility(View.GONE);
                        share.setVisibility(View.GONE);
                        attemptLL.setVisibility(View.GONE);
                        optionMenuImgView.setVisibility(View.GONE);
                        if (video.getThumbnail_url().equalsIgnoreCase("")) {
                            thumb.setImageResource(R.drawable.square_thumbnail);
                        } else {
                            Helper.setThumbnailImage(activity, video.getThumbnail_url(), activity.getDrawable(R.drawable.square_thumbnail), thumb);
                        }
                        break;

                    case Const.CONTENT_VIDEO:
                        attemptLL.setVisibility(View.GONE);
                        share.setVisibility(View.VISIBLE);
                        title.setText(video.getTitle());
                        read_now.setVisibility(View.GONE);
                        learn.setVisibility(View.GONE);
                        practice.setVisibility(View.GONE);
                        if (video.getLive_status() != null && video.getLive_status().equalsIgnoreCase("3")) {
                            optionMenuImgView.setVisibility(View.VISIBLE);
                            durationTV.setTextColor(activity.getResources().getColor(R.color.red));
                            durationTV.setText("Live Class Has been Cancelled");
                        } else if (video.getVideo_status() != null) {
                            if (video.getVideo_status().equalsIgnoreCase("Download Running")) {
                                optionMenuImgView.setVisibility(View.GONE);
                                durationTV.setVisibility(View.GONE);
                            } else if (video.getVideo_status().equalsIgnoreCase("Downloaded")) {
                                optionMenuImgView.setVisibility(View.VISIBLE);
                                durationTV.setVisibility(View.VISIBLE);
                                optionMenuImgView.setEnabled(false);
                                optionMenuImgView.setImageResource(R.drawable.ic_downloaded_chapter);
                                durationTV.setText("Duration : " + video.getVideotime());
                            } else if (video.getVideo_status().equalsIgnoreCase("Download")) {
                                optionMenuImgView.setEnabled(true);
                                optionMenuImgView.setImageResource(R.drawable.ic_menu_download);
                                optionMenuImgView.setVisibility(View.VISIBLE);
                                durationTV.setVisibility(View.GONE);

                            } else {
                                optionMenuImgView.setVisibility(View.GONE);
                                durationTV.setVisibility(View.GONE);
                            }
                        } else {
                            optionMenuImgView.setVisibility(View.GONE);
                            durationTV.setVisibility(View.GONE);
                        }
                        watch_now.setVisibility(View.VISIBLE);

                        if (video.getOpen_in_app() != null && video.getOpen_in_app().equalsIgnoreCase("1")) {
                            if (AudioPlayerService.isAudioPlaying && AudioPlayerService.videoid.equalsIgnoreCase(video.getId())) {
                                listne_now.setText("STOP");
                            } else {
                                listne_now.setText("LISTEN");
                            }
                            listne_now.setVisibility(View.VISIBLE);
                        } else {
                            listne_now.setVisibility(View.VISIBLE);
                        }
                        Glide.with(activity).load(R.mipmap.live).into(liveIv);
                        if (video.getIs_live() != null) {
                            if (video.getIs_live().equals("1"))
                                liveIv.setVisibility(View.VISIBLE);
                            else
                                liveIv.setVisibility(View.GONE);
                        } else {
                            liveIv.setVisibility(View.GONE);
                        }
                        if (video.getThumbnail_url().equalsIgnoreCase("")) {
                            thumb.setImageResource(R.drawable.square_thumbnail);
                        } else {
                            Helper.setThumbnailImage(activity, video.getThumbnail_url(), activity.getDrawable(R.drawable.square_thumbnail), thumb);
                        }
                        setOnClick(video, position, "watch", holder);
                        break;

                    case Const.CONTENT_EPUB:
                        read_now.setVisibility(View.GONE);

                        attemptLL.setVisibility(View.GONE);
                        break;

                    case Const.CONTENT_DOC:
                        attemptLL.setVisibility(View.GONE);
                        read_now.setVisibility(View.GONE);

                        break;

                    case Const.CONTENT_IMAGE:
                        attemptLL.setVisibility(View.GONE);
                        optionMenuImgView.setVisibility(View.GONE);
                        title.setText(video.getTitle());
                        read_now.setVisibility(View.VISIBLE);
                        read_now.setText("VIEW");
                        watch_now.setVisibility(View.GONE);
                        listne_now.setVisibility(View.GONE);
                        durationTV.setVisibility(View.VISIBLE);
                        durationTV.setText("Image Type");
                        learn.setVisibility(View.GONE);
                        practice.setVisibility(View.GONE);
                        liveIv.setVisibility(View.GONE);

                        if (video.getThumbnail_url().equalsIgnoreCase("")) {
                            thumb.setImageResource(R.drawable.square_thumbnail);
                        } else {
                            Helper.setThumbnailImage(activity, video.getThumbnail_url(), activity.getDrawable(R.drawable.square_thumbnail), thumb);
                        }
                        setImageOnClick(video);
                        break;

                    case Const.CONTENT_CONCEPT:
                        attemptLL.setVisibility(View.GONE);
                        share.setVisibility(View.VISIBLE);
                        title.setText(video.getTitle());
                        optionMenuImgView.setVisibility(View.GONE);
                        watch_now.setVisibility(View.GONE);
                        listne_now.setVisibility(View.GONE);
                        durationTV.setVisibility(View.VISIBLE);
                        //durationTV.setText("Concept Type");
                        watch_now.setVisibility(View.GONE);
                        listne_now.setVisibility(View.GONE);
                        learn.setVisibility(View.GONE);
                        practice.setVisibility(View.GONE);
                        liveIv.setVisibility(View.GONE);

                        if (video.getThumbnail_url().equalsIgnoreCase("")) {
                            thumb.setImageResource(R.drawable.square_thumbnail);
                        } else {
                            Helper.setThumbnailImage(activity, video.getThumbnail_url(), activity.getDrawable(R.drawable.square_thumbnail), thumb);
                        }
                        read_now.setVisibility(View.VISIBLE);

                        setConceptOnClick(video);
                        break;

                    case Const.CONTENT_LINK:
                        attemptLL.setVisibility(View.GONE);
                        share.setVisibility(View.VISIBLE);
                        title.setText(video.getTitle());
                        optionMenuImgView.setVisibility(View.GONE);
                        watch_now.setVisibility(View.GONE);
                        read_now.setVisibility(View.VISIBLE);

                        listne_now.setVisibility(View.GONE);
                        if (video.getThumbnail_url() == null || video.getThumbnail_url().equalsIgnoreCase("")) {
                            thumb.setImageResource(R.drawable.square_thumbnail);
                        } else {
                            Helper.setThumbnailImage(activity, video.getThumbnail_url(), activity.getDrawable(R.drawable.square_thumbnail), thumb);
                        }
                        setOnClick(video, position, "read", holder);
                        break;

                    case Const.CONTENT_TEST:
                        share.setVisibility(View.VISIBLE);
                        optionMenuImgView.setVisibility(View.GONE);
                        title.setText(video.getTitle());
                        if (video.getEnd_date().equalsIgnoreCase("") && video.getStart_date().equalsIgnoreCase("") || video.getEnd_date().equalsIgnoreCase("0") && video.getStart_date().equalsIgnoreCase("0")) {
                            durationTV.setVisibility(View.GONE);
                        } else {
                            if (!video.getEnd_date().equalsIgnoreCase("0") && !video.getEnd_date().equalsIgnoreCase("")) {
                                durationTV.setVisibility(View.VISIBLE);
                                String start = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.US).format(new Date(Long.parseLong(video.getStart_date()) * 1000));
                                String edateString = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.US).format(new Date(Long.parseLong(video.getEnd_date()) * 1000));
                                durationTV.setText("Test Start: " + Helper.changeAMPM(start) + "\n" + "Test End: " + Helper.changeAMPM(edateString));
                            } else {
                                durationTV.setVisibility(View.GONE);
                            }
                        }

                        read_now.setVisibility(View.GONE);

                        liveIv.setVisibility(View.GONE);
                        watch_now.setVisibility(View.GONE);
                        listne_now.setVisibility(View.GONE);
                        attemptLL.setVisibility(View.VISIBLE);

                        if (video.getState().equals("1")) {
                            study_single_itemLL.setEnabled(true);
                            utkashRoom.getTestDao().delete_test_data(MakeMyExam.userId, video.getId());
                            if (video.getStart_date().equalsIgnoreCase("") || video.getStart_date().equalsIgnoreCase("0") && video.getEnd_date().equalsIgnoreCase("") || video.getEnd_date().equalsIgnoreCase("0")) {

                                learn.setVisibility(View.VISIBLE);
                                practice.setVisibility(View.VISIBLE);
                                attemptLL.getChildAt(0).setVisibility(View.VISIBLE);
                                ((TextView) attemptLL.getChildAt(0)).setText(activity.getResources().getString(R.string.view_rank));
                            } else {
                                if (time >= Long.parseLong(video.getEnd_date()) * 1000) {
                                    if (video.getResult_date().equalsIgnoreCase("0") || video.getResult_date().equalsIgnoreCase("1") || video.getResult_date().equalsIgnoreCase("")) {
                                        attemptLL.setVisibility(View.GONE);
                                        learn.setVisibility(View.VISIBLE);
                                        practice.setVisibility(View.VISIBLE);
                                    } else {
                                        if (time > Long.parseLong(video.getResult_date()) * 1000) {
                                            if (Long.parseLong(video.getEnd_date()) < 1640066737) {
                                                attemptLL.setVisibility(View.GONE);
                                            } else {
                                                attemptLL.getChildAt(0).setVisibility(View.VISIBLE);
                                                ((TextView) attemptLL.getChildAt(0)).setText(activity.getResources().getString(R.string.view_rank));
                                            }

                                            learn.setVisibility(View.VISIBLE);
                                            practice.setVisibility(View.VISIBLE);
                                        } else {
                                            attemptLL.setVisibility(View.VISIBLE);
                                            ((TextView) attemptLL.getChildAt(0)).setText("ATTEMPTED");
                                            learn.setVisibility(View.VISIBLE);
                                            practice.setVisibility(View.VISIBLE);
                                        }
                                    }
                                } else {
                                    if (!video.getIs_reattempt().equalsIgnoreCase("0")) {
                                        learn.setVisibility(View.GONE);
                                        practice.setVisibility(View.GONE);
                                        attemptLL.setVisibility(View.VISIBLE);
                                        ((TextView) attemptLL.getChildAt(0)).setText("RE-ATTEMPT");
                                    } else {
                                        if (video.getResult_date() == null || video.getResult_date().equalsIgnoreCase("1") || video.getResult_date().equalsIgnoreCase("0") || video.getResult_date().equalsIgnoreCase("")) {
                                            learn.setVisibility(View.GONE);
                                            practice.setVisibility(View.GONE);
                                            attemptLL.getChildAt(0).setVisibility(View.VISIBLE);
                                            ((TextView) attemptLL.getChildAt(0)).setText(activity.getResources().getString(R.string.view_rank));
                                        } else {
                                            learn.setVisibility(View.GONE);
                                            practice.setVisibility(View.GONE);
                                            attemptLL.getChildAt(0).setVisibility(View.VISIBLE);
                                            ((TextView) attemptLL.getChildAt(0)).setText("ATTEMPTED");
                                        }

                                    }
                                }
                            }


                        } else {
                            if (video.getStart_date().equalsIgnoreCase("") || video.getStart_date().equalsIgnoreCase("0") && video.getEnd_date().equalsIgnoreCase("") || video.getEnd_date().equalsIgnoreCase("0")) {

                                TestTable test_data = utkashRoom.getTestDao().test_data(video.getId(), MakeMyExam.userId);
                                if (test_data != null && test_data.getStatus() != null && !test_data.getStatus().equalsIgnoreCase("")) {
                                    study_single_itemLL.setEnabled(true);
                                    attemptLL.getChildAt(0).setVisibility(View.VISIBLE);

                                    if (!video.getIs_reattempt().equalsIgnoreCase("0")) {
                                        ((TextView) attemptLL.getChildAt(0)).setText("RE-ATTEMPT");

                                    } else {
                                        ((TextView) attemptLL.getChildAt(0)).setText(test_data.getStatus());
                                    }
                                    learn.setVisibility(View.GONE);
                                    practice.setVisibility(View.GONE);

                                } else {
                                    study_single_itemLL.setEnabled(true);
                                    attemptLL.getChildAt(0).setVisibility(View.VISIBLE);
                                    ((TextView) attemptLL.getChildAt(0)).setText(activity.getResources().getString(R.string.attempt_now));
                                    learn.setVisibility(View.GONE);
                                    practice.setVisibility(View.GONE);
                                }

                            } else {
                                if (time >= Long.parseLong(video.getEnd_date()) * 1000) {
                                    study_single_itemLL.setEnabled(true);

                                    if (video.getResult_date().equalsIgnoreCase("0") || video.getResult_date().equalsIgnoreCase("1") || video.getResult_date().equalsIgnoreCase("")) {
                                        attemptLL.setVisibility(View.GONE);
                                        learn.setVisibility(View.VISIBLE);
                                        practice.setVisibility(View.VISIBLE);
                                    }
                                    if (Long.parseLong(video.getResult_date()) * 1000 > time) {
                                        attemptLL.setVisibility(View.GONE);
                                        learn.setVisibility(View.VISIBLE);
                                        practice.setVisibility(View.VISIBLE);
                                    } else if (time > Long.parseLong(video.getResult_date()) * 1000) {
                                        if (Long.parseLong(video.getEnd_date()) < 1640066737) {
                                            attemptLL.setVisibility(View.GONE);
                                        } else {
                                            attemptLL.setVisibility(View.VISIBLE);
                                            attemptLL.getChildAt(0).setVisibility(View.VISIBLE);
                                            ((TextView) attemptLL.getChildAt(0)).setText(activity.getResources().getString(R.string.view_rank));
                                        }

                                        learn.setVisibility(View.VISIBLE);
                                        practice.setVisibility(View.VISIBLE);
                                    } else {
                                        attemptLL.setVisibility(View.GONE);
                                        learn.setVisibility(View.VISIBLE);
                                        practice.setVisibility(View.VISIBLE);
                                    }
                                } else if (time < Long.parseLong(video.getStart_date()) * 1000) {
                                    study_single_itemLL.setEnabled(true);

                                    attemptLL.getChildAt(0).setVisibility(View.VISIBLE);
                                    ((TextView) attemptLL.getChildAt(0)).setText("Upcoming Test");
                                    learn.setVisibility(View.GONE);
                                    practice.setVisibility(View.GONE);
                                } else {
                                    TestTable test_data = utkashRoom.getTestDao().test_data(video.getId(), MakeMyExam.userId);
                                    if (test_data != null && test_data.getStatus() != null && !test_data.getStatus().equalsIgnoreCase("")) {
                                        study_single_itemLL.setEnabled(true);
                                        attemptLL.getChildAt(0).setVisibility(View.VISIBLE);

                                        if (!video.getIs_reattempt().equalsIgnoreCase("0")) {
                                            ((TextView) attemptLL.getChildAt(0)).setText("RE-ATTEMPT");

                                        } else {
                                            ((TextView) attemptLL.getChildAt(0)).setText(test_data.getStatus());
                                        }


                                        learn.setVisibility(View.GONE);
                                        practice.setVisibility(View.GONE);

                                    } else {
                                        study_single_itemLL.setEnabled(true);
                                        attemptLL.getChildAt(0).setVisibility(View.VISIBLE);
                                        ((TextView) attemptLL.getChildAt(0)).setText(activity.getResources().getString(R.string.attempt_now));
                                        learn.setVisibility(View.GONE);
                                        practice.setVisibility(View.GONE);
                                    }

                                }
                            }


                        }
                        if (video.getThumbnail_url() == null || video.getThumbnail_url().equalsIgnoreCase("")) {
                            thumb.setImageResource(R.drawable.square_thumbnail);
                        } else {
                            Helper.setThumbnailImage(activity, video.getThumbnail_url(), activity.getDrawable(R.drawable.square_thumbnail), thumb);
                        }
                        setTestOnClick(video, fileType);
                        break;
                }
            } else {
                if (fileType.equalsIgnoreCase(Const.VIDEO + tileIdAPI)) {
                    title.setText(video.getTitle());
                    attemptLL.setVisibility(View.GONE);
                    read_now.setVisibility(View.GONE);

                    if (video.getVideo_status() != null) {
                        if (video.getVideo_status().equalsIgnoreCase("Download Running")) {
                            optionMenuImgView.setVisibility(View.GONE);
                            durationTV.setVisibility(View.GONE);
                        } else if (video.getVideo_status().equalsIgnoreCase("Downloaded")) {
                            optionMenuImgView.setVisibility(View.VISIBLE);
                            durationTV.setVisibility(View.VISIBLE);
                            optionMenuImgView.setEnabled(false);
                            optionMenuImgView.setImageResource(R.drawable.ic_downloaded_chapter);
                            durationTV.setText("Duration : " + video.getVideotime());
                        } else if (video.getVideo_status().equalsIgnoreCase("Download")) {
                            optionMenuImgView.setEnabled(true);
                            optionMenuImgView.setImageResource(R.drawable.ic_menu_download);
                            optionMenuImgView.setVisibility(View.VISIBLE);
                            durationTV.setVisibility(View.GONE);

                        } else {
                            durationTV.setVisibility(View.GONE);

                            optionMenuImgView.setVisibility(View.GONE);
                        }
                    }
                    watch_now.setVisibility(View.VISIBLE);

                    if (video.getOpen_in_app() != null && video.getVideo_type() != null && video.getVideo_type().equalsIgnoreCase("6") && video.getOpen_in_app().equalsIgnoreCase("1")) {
                        if (AudioPlayerService.isAudioPlaying && AudioPlayerService.videoid.equalsIgnoreCase(video.getId())) {
                            listne_now.setText("STOP");
                        } else {
                            listne_now.setText("LISTEN");
                        }
                        listne_now.setVisibility(View.VISIBLE);
                    } else if (video.getOpen_in_app() != null && video.getVideo_type() != null && video.getVideo_type().equalsIgnoreCase("5")
                            || video.getOpen_in_app() != null && video.getVideo_type() != null && video.getVideo_type().equalsIgnoreCase("0")
                    ) {
                        if (AudioPlayerService.isAudioPlaying && AudioPlayerService.videoid.equalsIgnoreCase(video.getId())) {
                            listne_now.setText("STOP");
                        } else {
                            listne_now.setText("LISTEN");
                        }
                        listne_now.setVisibility(View.VISIBLE);
                    } else {
                        listne_now.setVisibility(View.VISIBLE);
                    }
                    Glide.with(activity).load(R.mipmap.live).into(liveIv);
                    if (video.getIs_live() != null) {
                        if (video.getIs_live().equals("1"))
                            liveIv.setVisibility(View.VISIBLE);
                        else
                            liveIv.setVisibility(View.GONE);
                    } else {
                        liveIv.setVisibility(View.GONE);
                    }

                    if (video.getThumbnail_url() == null || video.getThumbnail_url().equalsIgnoreCase("")) {
                        thumb.setImageResource(R.drawable.square_thumbnail);
                    } else {
                        Helper.setThumbnailImage(activity, video.getThumbnail_url(), activity.getDrawable(R.drawable.square_thumbnail), thumb);
                    }
                    //Helper.setThumbnailImage(activity, video.getThumbnail_url(), activity.getDrawable(R.drawable.square_thumbnail), thumb);
                    setOnClick(video, position, "watch", holder);
                } else if (fileType.equalsIgnoreCase(Const.TEST + tileIdAPI)) {
                    share.setVisibility(View.GONE);
                    optionMenuImgView.setVisibility(View.GONE);
                    title.setText(video.getTest_series_name());

                    if (video.getEnd_date().equalsIgnoreCase("") && video.getStart_date().equalsIgnoreCase("") || video.getEnd_date().equalsIgnoreCase("0") && video.getStart_date().equalsIgnoreCase("0")) {
                        durationTV.setVisibility(View.GONE);

                    } else if (!video.getEnd_date().equalsIgnoreCase("0") && !video.getEnd_date().equalsIgnoreCase("")) {
                        durationTV.setVisibility(View.VISIBLE);
                        String edateString = new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(Long.parseLong(video.getEnd_date()) * 1000));
                        durationTV.setText("Test End: " + Helper.changeAMPM(edateString));
                    } else {
                        durationTV.setVisibility(View.GONE);
                    }

                    liveIv.setVisibility(View.GONE);
                    watch_now.setVisibility(View.GONE);
                    listne_now.setVisibility(View.GONE);
                    attemptLL.setVisibility(View.VISIBLE);

                    if (video.getState().equals("1")) {
                        utkashRoom.getTestDao().delete_test_data(MakeMyExam.userId, video.getId());

                        if (video.getStart_date().equalsIgnoreCase("") || video.getStart_date().equalsIgnoreCase("0") && video.getEnd_date().equalsIgnoreCase("") || video.getEnd_date().equalsIgnoreCase("0")) {

                            learn.setVisibility(View.VISIBLE);
                            practice.setVisibility(View.VISIBLE);

                            attemptLL.getChildAt(0).setVisibility(View.VISIBLE);
                            ((TextView) attemptLL.getChildAt(0)).setText(activity.getResources().getString(R.string.view_rank));
                        } else if (time >= Long.parseLong(video.getEnd_date()) * 1000) {
                            if (video.getResult_date().equalsIgnoreCase("0") && video.getResult_date().equalsIgnoreCase("1") && video.getResult_date().equalsIgnoreCase("")) {
                                attemptLL.setVisibility(View.GONE);
                                learn.setVisibility(View.VISIBLE);
                                practice.setVisibility(View.VISIBLE);
                            } else {
                                if (time > Long.parseLong(video.getResult_date()) * 1000) {
                                    if (Long.parseLong(video.getEnd_date()) < 1640066737) {
                                        attemptLL.setVisibility(View.GONE);
                                    } else {
                                        attemptLL.getChildAt(0).setVisibility(View.VISIBLE);
                                        ((TextView) attemptLL.getChildAt(0)).setText(activity.getResources().getString(R.string.view_rank));
                                    }

                                    learn.setVisibility(View.VISIBLE);
                                    practice.setVisibility(View.VISIBLE);
                                } else {
                                    attemptLL.setVisibility(View.VISIBLE);
                                    ((TextView) attemptLL.getChildAt(0)).setText("ATTEMPTED");
                                    learn.setVisibility(View.VISIBLE);
                                    practice.setVisibility(View.VISIBLE);
                                }
                            }
                        } else {
                            if (!video.getIs_reattempt().equalsIgnoreCase("0")) {
                                learn.setVisibility(View.GONE);
                                practice.setVisibility(View.GONE);
                                attemptLL.setVisibility(View.VISIBLE);
                                ((TextView) attemptLL.getChildAt(0)).setText("RE-ATTEMPT");
                            } else {
                                if (video.getResult_date() == null || video.getResult_date().equalsIgnoreCase("1") || video.getResult_date().equalsIgnoreCase("0") || video.getResult_date().equalsIgnoreCase("")) {
                                    learn.setVisibility(View.GONE);
                                    practice.setVisibility(View.GONE);
                                    attemptLL.getChildAt(0).setVisibility(View.VISIBLE);
                                    ((TextView) attemptLL.getChildAt(0)).setText(activity.getResources().getString(R.string.view_rank));
                                } else {
                                    learn.setVisibility(View.GONE);
                                    practice.setVisibility(View.GONE);
                                    attemptLL.getChildAt(0).setVisibility(View.VISIBLE);
                                    ((TextView) attemptLL.getChildAt(0)).setText("ATTEMPTED");
                                }
                            }
                        }
                    } else {

                        if (video.getStart_date().equalsIgnoreCase("") || video.getStart_date().equalsIgnoreCase("0") && video.getEnd_date().equalsIgnoreCase("") || video.getEnd_date().equalsIgnoreCase("0")) {

                            TestTable test_data = utkashRoom.getTestDao().test_data(video.getId(), MakeMyExam.userId);
                            if (test_data != null && test_data.getStatus() != null && !test_data.getStatus().equalsIgnoreCase("")) {
                                study_single_itemLL.setEnabled(true);
                                attemptLL.setVisibility(View.VISIBLE);

                                attemptLL.getChildAt(0).setVisibility(View.VISIBLE);

                                if (!video.getIs_reattempt().equalsIgnoreCase("0")) {
                                    ((TextView) attemptLL.getChildAt(0)).setText("RE-ATTEMPT");

                                } else {
                                    ((TextView) attemptLL.getChildAt(0)).setText(test_data.getStatus());
                                }
                                learn.setVisibility(View.GONE);
                                practice.setVisibility(View.GONE);

                            } else {
                                study_single_itemLL.setEnabled(true);
                                attemptLL.getChildAt(0).setVisibility(View.VISIBLE);
                                ((TextView) attemptLL.getChildAt(0)).setText(activity.getResources().getString(R.string.attempt_now));
                                learn.setVisibility(View.GONE);
                                practice.setVisibility(View.GONE);
                            }

                        } else if (time >= Long.parseLong(video.getEnd_date()) * 1000) {
                            utkashRoom.getTestDao().delete_test_data(MakeMyExam.userId, video.getId());
                            if (video.getResult_date().equalsIgnoreCase("0") && video.getResult_date().equalsIgnoreCase("1") && video.getResult_date().equalsIgnoreCase("")) {
                                attemptLL.setVisibility(View.GONE);
                                learn.setVisibility(View.VISIBLE);
                                practice.setVisibility(View.VISIBLE);
                            }
                            if (Long.parseLong(video.getResult_date()) * 1000 > time) {
                                attemptLL.setVisibility(View.GONE);
                                learn.setVisibility(View.VISIBLE);
                                practice.setVisibility(View.VISIBLE);
                            } else if (time > Long.parseLong(video.getResult_date()) * 1000) {
                                if (Long.parseLong(video.getEnd_date()) < 1640066737) {
                                    attemptLL.setVisibility(View.GONE);
                                } else {
                                    attemptLL.getChildAt(0).setVisibility(View.VISIBLE);
                                    ((TextView) attemptLL.getChildAt(0)).setText(activity.getResources().getString(R.string.view_rank));
                                }

                                learn.setVisibility(View.VISIBLE);
                                practice.setVisibility(View.VISIBLE);
                            } else {
                                attemptLL.setVisibility(View.GONE);
                                learn.setVisibility(View.VISIBLE);
                                practice.setVisibility(View.VISIBLE);
                            }
                        } else if (time < Long.parseLong(video.getStart_date()) * 1000) {
                            attemptLL.getChildAt(0).setVisibility(View.VISIBLE);
                            ((TextView) attemptLL.getChildAt(0)).setText("Upcoming Test");
                            learn.setVisibility(View.GONE);
                            practice.setVisibility(View.GONE);
                        } else {
                            TestTable test_data = utkashRoom.getTestDao().test_data(video.getId(), MakeMyExam.userId);
                            if (test_data != null && test_data.getStatus() != null && !test_data.getStatus().equalsIgnoreCase("")) {
                                attemptLL.getChildAt(0).setVisibility(View.VISIBLE);

                                if (!video.getIs_reattempt().equalsIgnoreCase("0")) {
                                    ((TextView) attemptLL.getChildAt(0)).setText("RE-ATTEMPT");

                                } else {
                                    ((TextView) attemptLL.getChildAt(0)).setText(test_data.getStatus());
                                }
                                learn.setVisibility(View.GONE);
                                practice.setVisibility(View.GONE);

                            } else
                                attemptLL.getChildAt(0).setVisibility(View.VISIBLE);
                            ((TextView) attemptLL.getChildAt(0)).setText(activity.getResources().getString(R.string.attempt_now));
                            learn.setVisibility(View.GONE);
                            practice.setVisibility(View.GONE);
                        }

                    }
                    if (video.getThumbnail_url() == null || video.getThumbnail_url().equalsIgnoreCase("")) {
                        thumb.setImageResource(R.drawable.square_thumbnail);
                    } else {
                        Helper.setThumbnailImage(activity, video.getThumbnail_url(), activity.getDrawable(R.drawable.square_thumbnail), thumb);
                    }
                    setTestOnClick(video, fileType);
                } else if (fileType.equalsIgnoreCase(Const.PDF + tileIdAPI)) {
                    switch (video.getFile_type()) {
                        case Const.CUSTOME_REVISION:
                            attemptLL.setVisibility(View.GONE);
                            optionMenuImgView.setVisibility(View.VISIBLE);
                            optionMenuImgView.setImageResource(R.drawable.delete);
                            durationTV.setVisibility(View.VISIBLE);
                            durationTV.setText("Revision Set");
                            title.setText(video.getTitle());
                            watch_now.setVisibility(View.GONE);
                            liveIv.setVisibility(View.GONE);
                            listne_now.setVisibility(View.GONE);
                            thumb.setImageResource(R.drawable.square_thumbnail);
                            read_now.setVisibility(View.VISIBLE);

                            setOnClick(video, position, "read", holder);
                            break;
                        case Const.CUSTOME_LINK:
                            durationTV.setVisibility(View.VISIBLE);
                            durationTV.setText("Search WebLink");
                            attemptLL.setVisibility(View.GONE);
                            optionMenuImgView.setImageResource(R.drawable.delete);
                            optionMenuImgView.setVisibility(View.VISIBLE);
                            title.setText(video.getTitle());
                            watch_now.setVisibility(View.GONE);
                            listne_now.setVisibility(View.GONE);
                            liveIv.setVisibility(View.GONE);
                            read_now.setVisibility(View.VISIBLE);


                            if (video.getThumbnail_url().equalsIgnoreCase("")) {
                                thumb.setImageResource(R.drawable.square_thumbnail);
                            } else {
                                Helper.setThumbnailImage(activity, video.getThumbnail_url(), activity.getDrawable(R.drawable.square_thumbnail), thumb);
                            }
                            setOnClick(video, position, "watch", holder);
                            break;
                        case Const.CUSTOME_VIDEO:
                            durationTV.setVisibility(View.VISIBLE);
                            durationTV.setText("Search VideoRef");
                            attemptLL.setVisibility(View.GONE);
                            optionMenuImgView.setImageResource(R.drawable.delete);
                            optionMenuImgView.setVisibility(View.VISIBLE);
                            title.setText(video.getTitle());
                            watch_now.setVisibility(View.VISIBLE);
                            listne_now.setVisibility(View.GONE);
                            liveIv.setVisibility(View.GONE);
                            read_now.setVisibility(View.GONE);

                            if (video.getThumbnail_url().equalsIgnoreCase("")) {
                                thumb.setImageResource(R.drawable.square_thumbnail);
                            } else {
                                Helper.setThumbnailImage(activity, video.getThumbnail_url(), activity.getDrawable(R.drawable.square_thumbnail), thumb);
                            }
                            setOnClick(video, position, "watch", holder);
                            break;

                        default:
                            title.setText(video.getTitle());
                            durationTV.setVisibility(View.GONE);
                            liveIv.setVisibility(View.GONE);
                            attemptLL.setVisibility(View.GONE);
                            read_now.setVisibility(View.VISIBLE);

                            if (video.getThumbnail_url().equalsIgnoreCase("")) {
                                thumb.setImageResource(R.drawable.square_thumbnail);

                            } else {
                                Helper.setThumbnailImage(activity, video.getThumbnail_url(), activity.getDrawable(R.drawable.square_thumbnail), thumb);

                            }
                            setPdfeOnClick(video);
                    }

                } else if (fileType.equalsIgnoreCase(Const.CONCEPT + tileIdAPI)) {
                    attemptLL.setVisibility(View.GONE);
                    title.setText(video.getTitle());
                    optionMenuImgView.setVisibility(View.GONE);
                    watch_now.setVisibility(View.GONE);
                    listne_now.setVisibility(View.GONE);
                    read_now.setVisibility(View.VISIBLE);
                    share.setVisibility(View.VISIBLE);

                    learn.setVisibility(View.GONE);
                    practice.setVisibility(View.GONE);
                    liveIv.setVisibility(View.GONE);


                    if (video.getThumbnail_url().equalsIgnoreCase("")) {
                        thumb.setImageResource(R.drawable.square_thumbnail);

                    } else {
                        Helper.setThumbnailImage(activity, video.getThumbnail_url(), activity.getDrawable(R.drawable.square_thumbnail), thumb);

                    }
                    setConceptOnClick(video);
                } else if (fileType.equalsIgnoreCase(Const.IMAGE + tileIdAPI)) {
                    title.setText(video.getTitle());
                    durationTV.setVisibility(View.GONE);
                    liveIv.setVisibility(View.GONE);
                    attemptLL.setVisibility(View.GONE);
                    read_now.setVisibility(View.VISIBLE);
                    read_now.setText("VIEW");
                    watch_now.setVisibility(View.GONE);
                    listne_now.setVisibility(View.GONE);
                    learn.setVisibility(View.GONE);
                    practice.setVisibility(View.GONE);
                    if (video.getThumbnail_url().equalsIgnoreCase("")) {
                        thumb.setImageResource(R.drawable.square_thumbnail);

                    } else {
                        Helper.setThumbnailImage(activity, video.getThumbnail_url(), activity.getDrawable(R.drawable.square_thumbnail), thumb);

                    }
                    setImageOnClick(video);
                } else {
                    title.setText(video.getTitle());
                    durationTV.setVisibility(View.GONE);
                    liveIv.setVisibility(View.GONE);
                    attemptLL.setVisibility(View.GONE);
                    read_now.setVisibility(View.VISIBLE);

                    if (video.getThumbnail_url().equalsIgnoreCase("")) {
                        thumb.setImageResource(R.drawable.square_thumbnail);

                    } else {
                        Helper.setThumbnailImage(activity, video.getThumbnail_url(), activity.getDrawable(R.drawable.square_thumbnail), thumb);

                    }
                    setPdfeOnClick(video);
                }
            }

        }

        private void setPdfeOnClick(final Video video) {
            share.setOnClickListener(v -> {
                if (is_purchase.equalsIgnoreCase("1")) {
                    if (video.getFile_type().equalsIgnoreCase("8")
                            || video.getFile_type().equalsIgnoreCase("7")
                            || video.getFile_type().equalsIgnoreCase("1")

                    ) {
                        Helper.sharePdf(activity, video.getPayloadData().getCourse_id(), video.getId(), video.getPayloadData().getTopic_id(), video.getPayloadData().getTile_type(), video.getPayloadData().getTile_id(), video.getPayloadData().getRevert_api(), "pdf", video.getThumbnail_url(), video.getTitle(), SingleStudy.parentCourseId);

                    }
                } else {
                    if (video.getIs_locked().equalsIgnoreCase("1")) {
                        Intent intent = new Intent(activity, PurchaseActivity.class);
                        intent.putExtra("single_study", singleStudy);
                        Helper.gotoActivity(intent, activity);
                    } else {
                        if (video.getFile_type().equalsIgnoreCase("8")
                                || video.getFile_type().equalsIgnoreCase("7")
                                || video.getFile_type().equalsIgnoreCase("1")

                        ) {
                            Helper.sharePdf(activity, video.getPayloadData().getCourse_id(), video.getId(), video.getPayloadData().getTopic_id(), video.getPayloadData().getTile_type(), video.getPayloadData().getTile_id(), video.getPayloadData().getRevert_api(), "pdf", video.getThumbnail_url(), video.getTitle(), SingleStudy.parentCourseId);
                        }
                    }
                }
            });

            study_single_itemLL.setOnClickListener(View -> {
                study_single_itemLL.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.nav_ripple));
                if (is_purchase.equalsIgnoreCase("1")) {
                    if (!video.getFile_type().equalsIgnoreCase("7"))
                        if (TextUtils.isEmpty(video.getFile_url()) && TextUtils.isEmpty(video.getId())) {
                            Toast.makeText(activity, "No pdf found!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    if (video.getFile_type().equalsIgnoreCase("8")) {
                        Intent intent4 = new Intent(activity, WebViewActivty.class);
                        intent4.putExtra("type", video.getTitle());
                        intent4.putExtra("url", video.getFile_url());
                        intent4.putExtra("video_id", video.getId());
                        intent4.putExtra("link", video.getFile_type());
                        if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                            intent4.putExtra("course_id", singleStudy.getData().getCourseDetail().getId() + "#");
                            Helper.gotoActivity(intent4, activity);
                        } else {
                            intent4.putExtra("course_id", SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());
                            Helper.gotoActivity(intent4, activity);
                        }

                    }
                    /*if (video.getFile_type().equalsIgnoreCase("7")) {
                        Intent intent4 = new Intent(activity, WebViewActivty.class);
                        intent4.putExtra("type", video.getTitle());
                        intent4.putExtra("url", video.getDescription());
                         Helper.gotoActivity(intent4, activity);
                    }*/
                    else if (video.getFile_type().equalsIgnoreCase("1")) {
                        boolean isDownload = false;
                        if (!TextUtils.isEmpty(video.getIs_download_available()) && video.getIs_download_available().equalsIgnoreCase("1")) {
                            isDownload = true;
                        } else {
                            isDownload = false;
                        }
                        if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                            Helper.GoToWebViewPDFActivity(activity, video.getId(), video.getFile_url(), isDownload, video.getTitle(), singleStudy.getData().getCourseDetail().getId() + "#");

                        } else {
                            Helper.GoToWebViewPDFActivity(activity, video.getId(), video.getFile_url(), isDownload, video.getTitle(), SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());

                        }

                    }
                } else {
                    if (video.getIs_locked().equalsIgnoreCase("1")) {

                        Intent intent = new Intent(activity, PurchaseActivity.class);
                        intent.putExtra("single_study", singleStudy);
                        Helper.gotoActivity(intent, activity);
                    } else {
                        if (!video.getFile_type().equalsIgnoreCase("7"))
                            if (TextUtils.isEmpty(video.getFile_url()) && TextUtils.isEmpty(video.getId())) {
                                Toast.makeText(activity, "No pdf found!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        if (video.getFile_type().equalsIgnoreCase("8")) {
                            Intent intent4 = new Intent(activity, WebViewActivty.class);
                            intent4.putExtra("type", video.getTitle());
                            intent4.putExtra("url", video.getFile_url());
                            intent4.putExtra("video_id", video.getId());
                            intent4.putExtra("link", video.getFile_type());
                            if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                                intent4.putExtra("course_id", singleStudy.getData().getCourseDetail().getId() + "#");
                                Helper.gotoActivity(intent4, activity);
                            } else {
                                intent4.putExtra("course_id", SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());
                                Helper.gotoActivity(intent4, activity);
                            }
                        } else if (video.getFile_type().equalsIgnoreCase("1")) {
                            boolean isDownload = false;
                            if (!TextUtils.isEmpty(video.getIs_download_available()) && video.getIs_download_available().equalsIgnoreCase("1")) {
                                isDownload = true;
                            } else {
                                isDownload = false;
                            }
                            if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                                Helper.GoToWebViewPDFActivity(activity, video.getId(), video.getFile_url(), isDownload, video.getTitle(), singleStudy.getData().getCourseDetail().getId() + "#");
                            } else {
                                Helper.GoToWebViewPDFActivity(activity, video.getId(), video.getFile_url(), isDownload, video.getTitle(), SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());

                            }

                        }
                    }
                }
            });

            read_now.setOnClickListener(view -> {
                if (is_purchase.equalsIgnoreCase("1")) {
                    if (!video.getFile_type().equalsIgnoreCase("7"))
                        if (TextUtils.isEmpty(video.getFile_url()) && TextUtils.isEmpty(video.getId())) {
                            Toast.makeText(activity, "No pdf found!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    if (video.getFile_type().equalsIgnoreCase("8")) {
                        Intent intent4 = new Intent(activity, WebViewActivty.class);
                        intent4.putExtra("type", video.getTitle());
                        intent4.putExtra("url", video.getFile_url());
                        intent4.putExtra("video_id", video.getId());
                        intent4.putExtra("link", video.getFile_type());
                        if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                            intent4.putExtra("course_id", singleStudy.getData().getCourseDetail().getId() + "#");
                            Helper.gotoActivity(intent4, activity);
                        } else {
                            intent4.putExtra("course_id", SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());
                            Helper.gotoActivity(intent4, activity);
                        }
                    }
                    /*if (video.getFile_type().equalsIgnoreCase("7")) {
                        Intent intent4 = new Intent(activity, WebViewActivty.class);
                        intent4.putExtra("type", video.getTitle());
                        intent4.putExtra("url", video.getDescription());
                         Helper.gotoActivity(intent4, activity);
                    }*/
                    else if (video.getFile_type().equalsIgnoreCase("1")) {
                        boolean isDownload = false;
                        if (!TextUtils.isEmpty(video.getIs_download_available()) && video.getIs_download_available().equalsIgnoreCase("1")) {
                            isDownload = true;
                        } else {
                            isDownload = false;
                        }
                        if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                            Helper.GoToWebViewPDFActivity(activity, video.getId(), video.getFile_url(), isDownload, video.getTitle(), singleStudy.getData().getCourseDetail().getId() + "#");

                        } else {
                            Helper.GoToWebViewPDFActivity(activity, video.getId(), video.getFile_url(), isDownload, video.getTitle(), SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());

                        }

                    }
                } else {
                    if (video.getIs_locked().equalsIgnoreCase("1")) {

                        Intent intent = new Intent(activity, PurchaseActivity.class);
                        intent.putExtra("single_study", singleStudy);
                        Helper.gotoActivity(intent, activity);
                    } else {
                        if (!video.getFile_type().equalsIgnoreCase("7"))
                            if (TextUtils.isEmpty(video.getFile_url()) && TextUtils.isEmpty(video.getId())) {
                                Toast.makeText(activity, "No pdf found!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        if (video.getFile_type().equalsIgnoreCase("8")) {
                            Intent intent4 = new Intent(activity, WebViewActivty.class);
                            intent4.putExtra("type", video.getTitle());
                            intent4.putExtra("url", video.getFile_url());
                            intent4.putExtra("video_id", video.getId());
                            intent4.putExtra("link", video.getFile_type());
                            if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                                intent4.putExtra("course_id", singleStudy.getData().getCourseDetail().getId() + "#");
                                Helper.gotoActivity(intent4, activity);
                            } else {
                                intent4.putExtra("course_id", SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());
                                Helper.gotoActivity(intent4, activity);
                            }
                        } else if (video.getFile_type().equalsIgnoreCase("1")) {
                            boolean isDownload = false;
                            if (!TextUtils.isEmpty(video.getIs_download_available()) && video.getIs_download_available().equalsIgnoreCase("1")) {
                                isDownload = true;
                            } else {
                                isDownload = false;
                            }

                            if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                                Helper.GoToWebViewPDFActivity(activity, video.getId(), video.getFile_url(), isDownload, video.getTitle(), singleStudy.getData().getCourseDetail().getId() + "#");

                            } else {
                                Helper.GoToWebViewPDFActivity(activity, video.getId(), video.getFile_url(), isDownload, video.getTitle(), SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());

                            }

                        }
                    }
                }


            });

        }

        public void setOnClick(final Video video, int position, String click, RecyclerView.ViewHolder holder) {

            share.setOnClickListener(v -> {
                if (is_purchase.equalsIgnoreCase("1")) {
                    if (video.getFile_type().equalsIgnoreCase("8")) {
                        Helper.sharePdf(activity, video.getPayloadData().getCourse_id(), video.getId(), video.getPayloadData().getTopic_id(), video.getPayloadData().getTile_type(), video.getPayloadData().getTile_id(), video.getPayloadData().getRevert_api(), "pdf", video.getThumbnail_url(), video.getTitle(), SingleStudy.parentCourseId);
                    } else {
                        if (video.getVideo_type().equalsIgnoreCase("4") || video.getVideo_type().equalsIgnoreCase("1")) {
                            Helper.shareLiveClass(activity, video.getPayloadData().getCourse_id(), video.getId(), video.getPayloadData().getTopic_id(), video.getPayloadData().getTile_type(), video.getPayloadData().getTile_id(), video.getPayloadData().getRevert_api(), "video", video.getThumbnail_url(), video.getTitle(), SingleStudy.parentCourseId);
                        } else if (video.getVideo_type().equalsIgnoreCase("6")) {
                            Helper.sharejwvideo(activity, video.getPayloadData().getCourse_id(), video.getId(), video.getPayloadData().getTopic_id(), video.getPayloadData().getTile_type(), video.getPayloadData().getTile_id(), video.getPayloadData().getRevert_api(), "video", video.getThumbnail_url(), video.getTitle(), SingleStudy.parentCourseId);
                        } else if (video.getVideo_type().equalsIgnoreCase("0") || video.getVideo_type().equalsIgnoreCase("5")) {
                            Helper.shareLiveClass(activity, video.getPayloadData().getCourse_id(), video.getId(), video.getPayloadData().getTopic_id(), video.getPayloadData().getTile_type(), video.getPayloadData().getTile_id(), video.getPayloadData().getRevert_api(), "video", video.getThumbnail_url(), video.getTitle(), SingleStudy.parentCourseId);
                        }
                    }


                } else {
                    if (video.getIs_locked().equalsIgnoreCase("1")) {
                        Intent intent = new Intent(activity, PurchaseActivity.class);
                        intent.putExtra("single_study", singleStudy);
                        Helper.gotoActivity(intent, activity);
                    } else {
                        if (video.getFile_type().equalsIgnoreCase("8")) {
                            Helper.sharePdf(activity, video.getPayloadData().getCourse_id(), video.getId(), video.getPayloadData().getTopic_id(), video.getPayloadData().getTile_type(), video.getPayloadData().getTile_id(), video.getPayloadData().getRevert_api(), "pdf", video.getThumbnail_url(), video.getTitle(), SingleStudy.parentCourseId);
                        } else {
                            if (video.getVideo_type().equalsIgnoreCase("4") || video.getVideo_type().equalsIgnoreCase("1")) {
                                Helper.shareLiveClass(activity, video.getPayloadData().getCourse_id(), video.getId(), video.getPayloadData().getTopic_id(), video.getPayloadData().getTile_type(), video.getPayloadData().getTile_id(), video.getPayloadData().getRevert_api(), "video", video.getThumbnail_url(), video.getTitle(), SingleStudy.parentCourseId);
                            } else if (video.getVideo_type().equalsIgnoreCase("6")) {
                                Helper.sharejwvideo(activity, video.getPayloadData().getCourse_id(), video.getId(), video.getPayloadData().getTopic_id(), video.getPayloadData().getTile_type(), video.getPayloadData().getTile_id(), video.getPayloadData().getRevert_api(), "video", video.getThumbnail_url(), video.getTitle(), SingleStudy.parentCourseId);
                            } else if (video.getVideo_type().equalsIgnoreCase("0") || video.getVideo_type().equalsIgnoreCase("5")) {
                                Helper.shareLiveClass(activity, video.getPayloadData().getCourse_id(), video.getId(), video.getPayloadData().getTopic_id(), video.getPayloadData().getTile_type(), video.getPayloadData().getTile_id(), video.getPayloadData().getRevert_api(), "video", video.getThumbnail_url(), video.getTitle(), SingleStudy.parentCourseId);
                            }
                        }


                    }
                }


            });

            study_single_itemLL.setOnClickListener(v -> {
                study_single_itemLL.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.nav_ripple));
                isaudio = false;
                is_download = false;
                if (click.equalsIgnoreCase("watch")) {
                    if (!Helper.isConnected(activity)) {
                        VideosDownload videosDownload = utkashRoom.getvideoDownloadao().getuser(video.getId(), "1");
                        if (videosDownload != null && videosDownload.getPercentage() == 100) {
                            File file = new File(activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.Videos/" + videosDownload.getVideo_history() + ".mp4");
                            if (file.exists()) {
                                Intent i = new Intent(activity, DownloadVideoPlayer.class);
                                i.putExtra("video_name", videosDownload.getVideo_name());
                                i.putExtra("video_id", videosDownload.getVideo_id());
                                i.putExtra("current_pos", videosDownload.getVideoCurrentPosition());
                                i.putExtra("video", videosDownload.getVideo_history());
                                i.putExtra("video_time", videosDownload.getVideotime());
                                i.putExtra("course_id", videosDownload.getCourse_id());
                                i.putExtra("token", videosDownload.getVideo_token());
                                i.putExtra("thumbnailurl", videosDownload.getThumbnail_url());

                                activity.startActivity(i);
                            } else {
                                Toast.makeText(activity, "No File Found", Toast.LENGTH_SHORT).show();
                            }
                        }
                        Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        return;
                    }  else {
                        VideosDownload videosDownload = utkashRoom.getvideoDownloadao().getuser(video.getId(), "1");
                        if (videosDownload != null && videosDownload.getPercentage() == 100 && videosDownload.getVideo_status() != null && videosDownload.getVideo_status().equalsIgnoreCase("Downloaded")) {
                            File file = new File(activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.Videos/" + videosDownload.getVideo_history() + ".mp4");
                            if (file.exists()) {
                                Intent i = new Intent(activity, DownloadVideoPlayer.class);
                                i.putExtra("video_name", videosDownload.getVideo_name());
                                i.putExtra("video_id", videosDownload.getVideo_id());
                                i.putExtra("current_pos", videosDownload.getVideoCurrentPosition());
                                i.putExtra("video", videosDownload.getVideo_history());
                                i.putExtra("video_time", videosDownload.getVideotime());
                                i.putExtra("course_id", videosDownload.getCourse_id());
                                i.putExtra("token", videosDownload.getVideo_token());
                                i.putExtra("thumbnailurl", videosDownload.getThumbnail_url());
                                activity.startActivity(i);
                            } else {
                                Toast.makeText(activity, "No File Found", Toast.LENGTH_SHORT).show();
                            }
                            return;
                        }
                    }
                    if (is_purchase.equalsIgnoreCase("1")) {
                        if (TextUtils.isEmpty(video.getId())) {
                            Toast.makeText(activity, "No video found!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Helper.audio_service_close(activity);
                        API_REQUEST_VIDEO_LINK(video, position);
                    } else {
                        if (video.getIs_locked().equalsIgnoreCase("1")) {
                            Intent intent = new Intent(activity, PurchaseActivity.class);
                            intent.putExtra("single_study", singleStudy);
                            Helper.gotoActivity(intent, activity);
                        } else {
                            if (TextUtils.isEmpty(video.getId())) {
                                Toast.makeText(activity, "No video found!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Helper.audio_service_close((CourseActivity) activity);
                            API_REQUEST_VIDEO_LINK(video, position);
                        }
                    }
                }
                else if (click.equalsIgnoreCase("read")) {
                    if (!Helper.isConnected(activity)) {
                        Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (is_purchase.equalsIgnoreCase("1")) {

                        if (video.getFile_type().equalsIgnoreCase("12")) {
                            if (videoArrayList.size() > 0) {
                                Intent intent = new Intent(activity, RevisionActivity.class);
                                intent.putExtra("t_id", tile_id);
                                intent.putExtra("postion", position);
                                intent.putExtra("video_id", video.getId());
                                intent.putExtra("v_list", videoArrayList);
                                intent.putExtra("f_id", videoArrayList.get(0).getId());
                                intent.putExtra("c_id", singleStudy.getData().getCourseDetail().getId());
                                intent.putExtra("title", video.getTitle());
                                intent.putExtra("url", video.getFile_url());
                                intent.putExtra("via", "main");
                                Helper.gotoActivity(intent, activity);
                            }

                        } else if (video.getFile_type().equalsIgnoreCase("11")) {
                            Intent intent4 = new Intent(activity, WebViewActivty.class);
                            intent4.putExtra("type", video.getTitle());
                            intent4.putExtra("url", video.getFile_url());
                            Helper.gotoActivity(intent4, activity);
                        } else if (video.getFile_type().equalsIgnoreCase("8")) {
                            Intent intent4 = new Intent(activity, WebViewActivty.class);
                            intent4.putExtra("type", video.getTitle());
                            intent4.putExtra("url", video.getFile_url());
                            intent4.putExtra("video_id", video.getId());
                            intent4.putExtra("link", video.getFile_type());
                            if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                                intent4.putExtra("course_id", singleStudy.getData().getCourseDetail().getId() + "#");
                                Helper.gotoActivity(intent4, activity);
                            } else {
                                intent4.putExtra("course_id", SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());
                                Helper.gotoActivity(intent4, activity);
                            }
                        }
                    } else {
                        if (video.getIs_locked().equalsIgnoreCase("1")) {

                            Intent intent = new Intent(activity, PurchaseActivity.class);
                            intent.putExtra("single_study", singleStudy);
                            Helper.gotoActivity(intent, activity);
                        } else {

                            if (video.getFile_type().equalsIgnoreCase("12")) {
                                if (videoArrayList.size() > 0) {
                                    Intent intent = new Intent(activity, RevisionActivity.class);
                                    intent.putExtra("t_id", tile_id);
                                    intent.putExtra("postion", position);
                                    intent.putExtra("video_id", video.getId());
                                    intent.putExtra("v_list", videoArrayList);
                                    intent.putExtra("f_id", videoArrayList.get(0).getId());
                                    intent.putExtra("c_id", singleStudy.getData().getCourseDetail().getId());
                                    intent.putExtra("title", video.getTitle());
                                    intent.putExtra("url", video.getFile_url());
                                    intent.putExtra("via", "main");
                                    Helper.gotoActivity(intent, activity);
                                }

                            } else if (video.getFile_type().equalsIgnoreCase("11")) {
                                Intent intent4 = new Intent(activity, WebViewActivty.class);
                                intent4.putExtra("type", video.getTitle());
                                intent4.putExtra("url", video.getFile_url());
                                Helper.gotoActivity(intent4, activity);
                            } else if (video.getFile_type().equalsIgnoreCase("8")) {
                                Intent intent4 = new Intent(activity, WebViewActivty.class);
                                intent4.putExtra("type", video.getTitle());
                                intent4.putExtra("url", video.getFile_url());
                                intent4.putExtra("video_id", video.getId());
                                intent4.putExtra("link", video.getFile_type());
                                if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                                    intent4.putExtra("course_id", singleStudy.getData().getCourseDetail().getId() + "#");
                                    Helper.gotoActivity(intent4, activity);
                                } else {
                                    intent4.putExtra("course_id", SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());
                                    Helper.gotoActivity(intent4, activity);
                                }
                            }
                        }
                    }
                }


            });

            watch_now.setOnClickListener(v -> {
                if (!Helper.isConnected(activity)) {
                    VideosDownload videosDownload = utkashRoom.getvideoDownloadao().getuser(video.getId(), "1");
                    if (videosDownload != null && videosDownload.getPercentage() == 100) {
                        File file = new File(activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.Videos/" + videosDownload.getVideo_history() + ".mp4");
                        if (file.exists()) {
                            Intent i = new Intent(activity, DownloadVideoPlayer.class);
                            i.putExtra("video_name", videosDownload.getVideo_name());
                            i.putExtra("video_id", videosDownload.getVideo_id());
                            i.putExtra("current_pos", videosDownload.getVideoCurrentPosition());
                            i.putExtra("video", videosDownload.getVideo_history());
                            i.putExtra("video_time", videosDownload.getVideotime());
                            i.putExtra("course_id", videosDownload.getCourse_id());
                            i.putExtra("token", videosDownload.getVideo_token());
                            i.putExtra("thumbnailurl", videosDownload.getThumbnail_url());

                            activity.startActivity(i);
                        } else {
                            Toast.makeText(activity, "No File Found", Toast.LENGTH_SHORT).show();
                        }
                    }
                    Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    VideosDownload videosDownload = utkashRoom.getvideoDownloadao().getuser(video.getId(), "1");
                    if (videosDownload != null && videosDownload.getPercentage() == 100 && videosDownload.getVideo_status() != null && videosDownload.getVideo_status().equalsIgnoreCase("Downloaded")) {
                        File file = new File(activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.Videos/" + videosDownload.getVideo_history() + ".mp4");
                        if (file.exists()) {
                            Intent i = new Intent(activity, DownloadVideoPlayer.class);
                            i.putExtra("video_name", videosDownload.getVideo_name());
                            i.putExtra("video_id", videosDownload.getVideo_id());
                            i.putExtra("current_pos", videosDownload.getVideoCurrentPosition());
                            i.putExtra("video", videosDownload.getVideo_history());
                            i.putExtra("video_time", videosDownload.getVideotime());
                            i.putExtra("course_id", videosDownload.getCourse_id());
                            i.putExtra("token", videosDownload.getVideo_token());
                            i.putExtra("thumbnailurl", videosDownload.getThumbnail_url());
                            activity.startActivity(i);
                        } else {
                            Toast.makeText(activity, "No File Found", Toast.LENGTH_SHORT).show();
                        }
                        return;
                    }
                }
                isaudio = false;
                is_download = false;
                if (is_purchase.equalsIgnoreCase("1")) {
                    if (TextUtils.isEmpty(video.getId())) {
                        Toast.makeText(activity, "No video found!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Helper.audio_service_close((CourseActivity) activity);
                    API_REQUEST_VIDEO_LINK(video, position);
                } else {
                    if (video.getIs_locked().equalsIgnoreCase("1")) {
                        Intent intent = new Intent(activity, PurchaseActivity.class);
                        intent.putExtra("single_study", singleStudy);
                        Helper.gotoActivity(intent, activity);
                    } else {
                        if (TextUtils.isEmpty(video.getId())) {
                            Toast.makeText(activity, "No video found!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Helper.audio_service_close((CourseActivity) activity);
                        API_REQUEST_VIDEO_LINK(video, position);
                    }
                }
            });

            listne_now.setOnClickListener(v -> {
                is_download = false;
                if (Helper.isNetworkConnected(activity)) {
                    if (is_purchase.equalsIgnoreCase("1")) {
                        if (TextUtils.isEmpty(video.getId())) {
                            Toast.makeText(activity, "No video found!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (listne_now.getText().toString().equalsIgnoreCase("STOP")) {
                            try {
                                Helper.audio_service_close(activity);
                                final Handler handler = new Handler(Looper.getMainLooper());
                                handler.postDelayed(() -> changedata(position), 500);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return;
                        } else {
                            Helper.audio_service_close(activity);
                        }

                        if (video.getVideo_type().equalsIgnoreCase("6")) {

                            position_delete = position;
                            audio_play("", video);
                        } else if (video.getVideo_type().equalsIgnoreCase("1")) {
                            Helper.GoToLiveVideoActivity(video.getChat_node(), activity, video.getFile_url(), video.getVideo_type(), video.getId(), video.getTitle(), "1", video.getThumbnail_url(), video.getIs_chat_lock(), video.getPayloadData().getCourse_id(), String.valueOf(position), SingleStudy.parentCourseId, video.getPayloadData().getTile_id(), video.getPayloadData().getTile_type());
                        } else if (video.getVideo_type().equalsIgnoreCase("0")) {
                            isaudio = true;

                            videodata = video;
                            NetworkCall networkCall = new NetworkCall(ExamPrepLayer3Adapter.this, activity);
                            networkCall.NetworkAPICall(API.get_video_link, "", true, false);


                        } else if (video.getVideo_type().equalsIgnoreCase("5")) {

                            if (video.getLive_status().equalsIgnoreCase("1")) {
                                if (video.getFile_url() == null ||
                                        video.getFile_url().equalsIgnoreCase("")) {
                                    Toast.makeText(activity, "Url is not found", Toast.LENGTH_SHORT).show();
                                } else {
                                    isaudio = true;

                                    videodata = video;
                                    NetworkCall networkCall = new NetworkCall(ExamPrepLayer3Adapter.this, activity);
                                    networkCall.NetworkAPICall(API.get_video_link, "", true, false);


                                }
                            } else if (video.getLive_status().equalsIgnoreCase("0")) {
                                Toast.makeText(activity, "Live Class will start on " + new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(Long.parseLong(video.getStart_date()) * 1000)), Toast.LENGTH_SHORT).show();
                            } else if (video.getLive_status().equalsIgnoreCase("2")) {
                                Toast.makeText(activity, "Live class is ended", Toast.LENGTH_SHORT).show();
                            } else if (video.getLive_status().equalsIgnoreCase("3")) {
                                Toast.makeText(activity, "Live class is cancelled", Toast.LENGTH_SHORT);
                            }


                        } else if (video.getVideo_type().equalsIgnoreCase("4")) {
                            if (video.getLive_status().equalsIgnoreCase("1")) {
                                if (video.getFile_url() == null ||
                                        video.getFile_url().equalsIgnoreCase("")) {
                                    Toast.makeText(activity, "Url is not found", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (video.getOpen_in_app() != null && video.getOpen_in_app().equalsIgnoreCase("1")) {
                                        Helper.GoToLiveVideoActivity(video.getChat_node(), activity, video.getFile_url(), video.getVideo_type(), video.getId(), video.getTitle(), "1", video.getThumbnail_url(), video.getIs_chat_lock(), video.getPayloadData().getCourse_id(), String.valueOf(position), SingleStudy.parentCourseId, video.getPayloadData().getTile_id(), video.getPayloadData().getTile_type());
                                    } else {
                                        Helper.GoToLiveVideoActivity(video.getChat_node(), activity, video.getFile_url(), video.getVideo_type(), video.getId(), video.getTitle(), "1", video.getThumbnail_url(), video.getIs_chat_lock(), video.getPayloadData().getCourse_id(), String.valueOf(position), SingleStudy.parentCourseId, video.getPayloadData().getTile_id(), video.getPayloadData().getTile_type());
                                        //activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + video.getFile_url())));
                                    }
                                }
                            } else if (video.getLive_status().equalsIgnoreCase("0")) {
                                Toast.makeText(activity, "Live Class will start on " + new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(Long.parseLong(video.getStart_date()) * 1000)), Toast.LENGTH_SHORT).show();
                            } else if (video.getLive_status().equalsIgnoreCase("2")) {
                                Toast.makeText(activity, "Live class is ended", Toast.LENGTH_SHORT).show();
                            } else if (video.getLive_status().equalsIgnoreCase("3")) {
                                Toast.makeText(activity, "Live class is cancelled", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        if (video.getIs_locked().equalsIgnoreCase("1")) {

                            Intent intent = new Intent(activity, PurchaseActivity.class);
                            intent.putExtra("single_study", singleStudy);
                            Helper.gotoActivity(intent, activity);
                        } else {
                            if (TextUtils.isEmpty(video.getId())) {
                                Toast.makeText(activity, "No video found!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Helper.audio_service_close((CourseActivity) activity);
                            if (video.getVideo_type().equalsIgnoreCase("6")) {
                                position_delete = position;

                                audio_play("", video);
                            } else if (video.getVideo_type().equalsIgnoreCase("1")) {
                                Helper.GoToLiveVideoActivity(video.getChat_node(), activity, video.getVideo_type(), video.getIs_live(), video.getId(), video.getTitle(), "1", video.getThumbnail_url(), video.getIs_chat_lock(), video.getPayloadData().getCourse_id(), String.valueOf(position), SingleStudy.parentCourseId, video.getPayloadData().getTile_id(), video.getPayloadData().getTile_type());
                            } else if (video.getVideo_type().equalsIgnoreCase("5")) {
                                isaudio = true;

                                videodata = video;
                                NetworkCall networkCall = new NetworkCall(ExamPrepLayer3Adapter.this, activity);
                                networkCall.NetworkAPICall(API.get_video_link, "", true, false);


                                //  Helper.GoToLiveAwsVideoActivity(video.getVideo_type(), video.getChat_node(), activity, video.getId(), video.getVideo_type(), video.getId(), video.getTitle(), "1", video.getThumbnail_url(), video.getPayloadData().getCourse_id(), video.getPayloadData().getTile_id(), video.getPayloadData().getTile_type(), video.getIs_chat_lock(), String.valueOf(position), SingleStudy.parentCourseId);
                            } else if (video.getVideo_type().equalsIgnoreCase("4")) {
                                if (video.getLive_status().equalsIgnoreCase("1")) {
                                    if (video.getFile_url() == null ||
                                            video.getFile_url().equalsIgnoreCase("")) {
                                        Toast.makeText(activity, "Url is not found", Toast.LENGTH_SHORT).show();
                                    } else {
                                        if (video.getOpen_in_app() != null && video.getOpen_in_app().equalsIgnoreCase("1")) {
                                            Helper.GoToLiveVideoActivity(video.getChat_node(), activity, video.getFile_url(), video.getVideo_type(), video.getId(), video.getTitle(), "1", video.getThumbnail_url(), video.getIs_chat_lock(), video.getPayloadData().getCourse_id(), String.valueOf(position), SingleStudy.parentCourseId, video.getPayloadData().getTile_id(), video.getPayloadData().getTile_type());
                                        } else {
                                            videodata = video;
                                            NetworkCall networkCall = new NetworkCall(ExamPrepLayer3Adapter.this, activity);
                                            networkCall.NetworkAPICall(API.get_video_link, "", true, false);

                                            // activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + video.getFile_url())));
                                        }
                                    }
                                } else if (video.getLive_status().equalsIgnoreCase("0")) {
                                    Toast.makeText(activity, "Live Class will start on " + new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(Long.parseLong(video.getStart_date()) * 1000)), Toast.LENGTH_SHORT).show();
                                } else if (video.getLive_status().equalsIgnoreCase("2")) {
                                    Toast.makeText(activity, "Live class is ended", Toast.LENGTH_SHORT).show();
                                } else if (video.getLive_status().equalsIgnoreCase("3")) {
                                    Toast.makeText(activity, "Live class is cancelled", Toast.LENGTH_SHORT).show();

                                }
                            }
                        }
                    }

                } else {
                    Toast.makeText(activity, "No Internet Connection.", Toast.LENGTH_SHORT).show();
                }
            });

            optionMenuImgView.setOnClickListener(new OnSingleClickListener(() -> {
                if (Helper.isConnected(activity)) {
                    if (is_purchase.equalsIgnoreCase("1")) {
                        if (video.getFile_type().equalsIgnoreCase("10") || video.getFile_type().equalsIgnoreCase("12") || video.getFile_type().equalsIgnoreCase("11")) {
                            videodata = video;
                            position_delete = position;
                            delete_meg(videodata);
                        } else {
                            if (video.getFile_type().equalsIgnoreCase("3")) {
                                download_data(video, position);
                            } else {
                                Toast.makeText(activity, "Only jw video download...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        if (video.getIs_locked().equalsIgnoreCase("1")) {

                            Intent intent = new Intent(activity, PurchaseActivity.class);
                            intent.putExtra("single_study", singleStudy);
                            Helper.gotoActivity(intent, activity);
                        } else {
                            if (video.getFile_type().equalsIgnoreCase("10") || video.getFile_type().equalsIgnoreCase("12") || video.getFile_type().equalsIgnoreCase("11")) {
                                videodata = video;
                                position_delete = position;
                                delete_meg(videodata);
                            } else if (!VideoDownloadService.isServiceRunning) {
                                if (video.getFile_type().equalsIgnoreCase("3")) {
                                    download_data(video, position);
                                } else {
                                    Toast.makeText(activity, "Only jw video download...", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(activity, "Please wait downloading is in progress", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
                return null;
            }));

            read_now.setOnClickListener(v -> {

                if (!Helper.isConnected(activity)) {
                    Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (is_purchase.equalsIgnoreCase("1")) {

                    if (video.getFile_type().equalsIgnoreCase("12")) {
                        if (videoArrayList.size() > 0) {
                            Intent intent = new Intent(activity, RevisionActivity.class);
                            intent.putExtra("t_id", tile_id);
                            intent.putExtra("postion", position);
                            intent.putExtra("video_id", video.getId());
                            intent.putExtra("v_list", videoArrayList);
                            intent.putExtra("f_id", videoArrayList.get(0).getId());
                            intent.putExtra("c_id", singleStudy.getData().getCourseDetail().getId());
                            intent.putExtra("title", video.getTitle());
                            intent.putExtra("url", video.getFile_url());
                            intent.putExtra("via", "main");
                            Helper.gotoActivity(intent, activity);
                        }

                    } else if (video.getFile_type().equalsIgnoreCase("11")) {
                        Intent intent4 = new Intent(activity, WebViewActivty.class);
                        intent4.putExtra("type", video.getTitle());
                        intent4.putExtra("url", video.getFile_url());
                        Helper.gotoActivity(intent4, activity);
                    } else if (video.getFile_type().equalsIgnoreCase("8")) {
                        Intent intent4 = new Intent(activity, WebViewActivty.class);
                        intent4.putExtra("type", video.getTitle());
                        intent4.putExtra("url", video.getFile_url());
                        intent4.putExtra("video_id", video.getId());
                        intent4.putExtra("link", video.getFile_type());
                        if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                            intent4.putExtra("course_id", singleStudy.getData().getCourseDetail().getId() + "#");
                            Helper.gotoActivity(intent4, activity);
                        } else {
                            intent4.putExtra("course_id", SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());
                            Helper.gotoActivity(intent4, activity);
                        }
                    }
                } else {
                    if (video.getIs_locked().equalsIgnoreCase("1")) {

                        Intent intent = new Intent(activity, PurchaseActivity.class);
                        intent.putExtra("single_study", singleStudy);
                        Helper.gotoActivity(intent, activity);
                    } else {

                        if (video.getFile_type().equalsIgnoreCase("12")) {
                            if (videoArrayList.size() > 0) {
                                Intent intent = new Intent(activity, RevisionActivity.class);
                                intent.putExtra("t_id", tile_id);
                                intent.putExtra("postion", position);
                                intent.putExtra("video_id", video.getId());
                                intent.putExtra("v_list", videoArrayList);
                                intent.putExtra("f_id", videoArrayList.get(0).getId());
                                intent.putExtra("c_id", singleStudy.getData().getCourseDetail().getId());
                                intent.putExtra("title", video.getTitle());
                                intent.putExtra("url", video.getFile_url());
                                intent.putExtra("via", "main");
                                Helper.gotoActivity(intent, activity);
                            }

                        } else if (video.getFile_type().equalsIgnoreCase("11")) {
                            Intent intent4 = new Intent(activity, WebViewActivty.class);
                            intent4.putExtra("type", video.getTitle());
                            intent4.putExtra("url", video.getFile_url());
                            Helper.gotoActivity(intent4, activity);
                        } else if (video.getFile_type().equalsIgnoreCase("8")) {
                            Intent intent4 = new Intent(activity, WebViewActivty.class);
                            intent4.putExtra("type", video.getTitle());
                            intent4.putExtra("url", video.getFile_url());
                            intent4.putExtra("video_id", video.getId());
                            intent4.putExtra("link", video.getFile_type());
                            if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                                intent4.putExtra("course_id", singleStudy.getData().getCourseDetail().getId() + "#");
                                Helper.gotoActivity(intent4, activity);
                            } else {
                                intent4.putExtra("course_id", SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());
                                Helper.gotoActivity(intent4, activity);
                            }
                        }
                    }
                }

            });
        }

        private void download_data(Video video, int position) {
            final Dialog dialog = new Dialog(activity);
            dialog.setCancelable(false);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
            dialog.setContentView(R.layout.dialog_alert_simple);
            TextView titleDialog = dialog.findViewById(R.id.titleDialog);
            titleDialog.setText("Download Video?");
            TextView msgDialog = dialog.findViewById(R.id.msgDialog);
            msgDialog.setText(video.getTitle() + ".\nThis video will be downloaded in your local storage.\n");
            Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
            btn_cancel.setText("CANCEL");
            Button btn_submit = dialog.findViewById(R.id.btn_submit);
            btn_submit.setText("DOWNLOAD");

            btn_submit.setOnClickListener(new OnSingleClickListener(() -> {
                if (is_purchase.equalsIgnoreCase("1")) {
                    //      optionMenuImgView.setEnabled(false);
                    videodata = video;
                    position_delete = position;
                    is_download = true;
                    NetworkCall networkCall = new NetworkCall(ExamPrepLayer3Adapter.this, activity);
                    networkCall.NetworkAPICall(API.get_video_link, "", true, false);


                } else {
                    Toast.makeText(activity, "This feature is only available for courses in your library ", Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();
                dialog.cancel();
                return null;
            }));

            btn_cancel.setOnClickListener(v -> {
                dialog.dismiss();
            });
            dialog.show();
        }


        private void delete_meg(Video video) {
            final Dialog dialog = new Dialog(activity);
            dialog.setCancelable(false);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
            dialog.setContentView(R.layout.dialog_alert_simple);
            TextView titleDialog = dialog.findViewById(R.id.titleDialog);
            titleDialog.setVisibility(View.GONE);
            TextView msgDialog = dialog.findViewById(R.id.msgDialog);
            msgDialog.setText("Do you want to Delete ? \n" + video.getTitle());
            Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
            btn_cancel.setText("No");
            Button btn_submit = dialog.findViewById(R.id.btn_submit);
            btn_submit.setText("Yes");
            btn_submit.setOnClickListener(v -> {
                NetworkCall networkCall = new NetworkCall(ExamPrepLayer3Adapter.this, activity);
                networkCall.NetworkAPICall(API.delete_revision, "", true, false);
                dialog.dismiss();
                dialog.cancel();
            });
            btn_cancel.setOnClickListener(v -> {
                dialog.dismiss();
            });
            dialog.show();
        }

        private void waiting_dialog(String video_name) {
            if (!activity.isFinishing()) {
                final Dialog dialog = new Dialog(activity);
                dialog.setCancelable(false);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                dialog.setContentView(R.layout.dialog_alert_downloads);
                TextView titleDialog = dialog.findViewById(R.id.titleDialog);
                titleDialog.setText("Video");
                TextView msgDialog = dialog.findViewById(R.id.msgDialog);
                msgDialog.setText(video_name);
                Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
                Button btn_submit = dialog.findViewById(R.id.btn_submit);
                btn_submit.setOnClickListener(v -> {
                    Intent intent = new Intent(activity, DownloadActivity.class);
                    Helper.gotoActivity(intent, activity);
                    activity.finish();
                    dialog.dismiss();
                    dialog.cancel();
                });
                btn_cancel.setOnClickListener(v -> {
                    dialog.dismiss();
                    dialog.cancel();
                });
                dialog.show();
            }


        }


        private void audio_play(String mediaId, Video videoData) {
            isaudio = true;
           /* if (!utkashRoom.getaudiodao().isvideo_exit(videoData.getId(), MakeMyExam.userId)) {
                AudioTable audioTable = new AudioTable();
                audioTable.setVideo_id(videoData.getId());
                if (videoData.getFile_url().contains("jwplayer") || videoData.getFile_url().contains("jwplatform")) {
                    audioTable.setJw_url(mediaId);
                }
                audioTable.setVideo_name(videoData.getTitle());
                audioTable.setAudio_currentpos(0L);
                audioTable.setUser_id(MakeMyExam.userId);
                String course_id = "";
                if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                    course_id = singleStudy.getData().getCourseDetail().getId() + "#";

                } else {
                    course_id = SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId();

                }
                audioTable.setCourse_id(course_id);

                utkashRoom.getaudiodao().addUser(audioTable);
                extractJWPlayerUrl(mediaId, audioTable);
            } else {
                AudioTable audioTable = new AudioTable();
                audioTable.setVideo_id(videoData.getId());
                audioTable.setJw_url(mediaId);
                audioTable.setVideo_name(videoData.getTitle());
                audioTable.setAudio_currentpos(utkashRoom.getaudiodao().getuser(videoData.getId(), MakeMyExam.userId).getAudio_currentpos());
                extractJWPlayerUrl(mediaId, audioTable);

            }
*/
            videodata = videoData;
            NetworkCall networkCall = new NetworkCall(ExamPrepLayer3Adapter.this, activity);
            networkCall.NetworkAPICall(API.get_video_link, "", true, false);


        }


        public void setTestOnClick(final Video video, final String fileType) {

            study_single_itemLL.setOnClickListener(v -> {
                study_single_itemLL.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.nav_ripple));
                if (attemptLL.getVisibility() == View.VISIBLE) {
                    if (!Helper.isConnected(activity)) {
                        Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (is_purchase.equalsIgnoreCase("1")) {
                        SharedPreference.getInstance().putString(Const.ID, singleStudy.getData().getCourseDetail().getId());

                        if (((TextView) attemptLL.getChildAt(0)).getText().toString().equalsIgnoreCase("RE-ATTEMPT")) {
                            quiz_id = video.getId();
                            quiz_name = video.getTest_series_name();
                            totalQuestion = video.getTotal_questions();
                            first_attempt = "0";
                            result_date = video.getResult_date();
                            videopositonwise = video;
                            startTestAPI();
                        } else if (((TextView) attemptLL.getChildAt(0)).getText().toString().equalsIgnoreCase("ATTEMPT NOW")) {
                            quiz_id = video.getId();
                            quiz_name = video.getTest_series_name();
                            totalQuestion = video.getTotal_questions();
                            first_attempt = "1";
                            test_submission = video.getSubmission_type();
                            result_date = video.getResult_date();
                            videopositonwise = video;
                            startTestAPI();
                        } else if (((TextView) attemptLL.getChildAt(0)).getText().toString().equalsIgnoreCase("ATTEMPTED")) {
                            if (video.getResult_date() != null && !video.getResult_date().equalsIgnoreCase("0") && !video.getResult_date().equalsIgnoreCase("1") && !video.getResult_date().equalsIgnoreCase("")) {
                                Snackbar.make(v, "Your Result will be declare on " + new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(Long.parseLong(video.getResult_date()) * 1000)), Snackbar.LENGTH_SHORT).show();

                            } else if (utkashRoom.getTestDao().is_test_exit(video.getId(), MakeMyExam.userId)) {
                                Snackbar.make(v, "Your Result is Getting Ready Please refresh your page..", Snackbar.LENGTH_SHORT).show();
                            } else {
                                Snackbar.make(v, "Your Test has been alredy submiited", Snackbar.LENGTH_SHORT).show();
                            }

                        } else if (((TextView) attemptLL.getChildAt(0)).getText().toString().equalsIgnoreCase(activity.getResources().getString(R.string.view_rank))) {
                            quiz_id = video.getId();
                            quiz_name = video.getTest_series_name();
                            totalQuestion = video.getTotal_questions();

                            if (video.getState().equalsIgnoreCase("1")) {
                                if (video.getResult_date() == null || video.getResult_date().equalsIgnoreCase("") || video.getResult_date().equalsIgnoreCase("0") || video.getResult_date().equalsIgnoreCase("1")) {
                                    Intent resultScreen = new Intent(activity, QuizActivity.class);
                                    resultScreen.putExtra(Const.FRAG_TYPE, Const.RESULT_SCREEN);
                                    resultScreen.putExtra(Const.STATUS, quiz_id);
                                    resultScreen.putExtra(Const.NAME, quiz_name);
                                    //resultScreen.putExtra("show_leader", "0");
                                    resultScreen.putExtra("first_attempt", "1");
                                    Helper.gotoActivity(resultScreen, activity);
                                } else {
                                    Intent resultScreen = new Intent(activity, QuizActivity.class);
                                    resultScreen.putExtra(Const.FRAG_TYPE, Const.RESULT_SCREEN);
                                    resultScreen.putExtra(Const.STATUS, quiz_id);
                                    resultScreen.putExtra(Const.NAME, quiz_name);
                                    resultScreen.putExtra("first_attempt", "1");
                                    Helper.gotoActivity(resultScreen, activity);
                                }
                            } else {
                                Intent resultScreen = new Intent(activity, QuizActivity.class);
                                resultScreen.putExtra(Const.FRAG_TYPE, "leader_board");
                                resultScreen.putExtra(Const.STATUS, quiz_id);
                                resultScreen.putExtra(Const.NAME, quiz_name);
                                Helper.gotoActivity(resultScreen, activity);
                            }
                        }
                    } else {
                        if (video.getIs_locked().equalsIgnoreCase("1")) {

                            Intent intent = new Intent(activity, PurchaseActivity.class);
                            intent.putExtra("single_study", singleStudy);
                            Helper.gotoActivity(intent, activity);
                        } else {
                            SharedPreference.getInstance().putString(Const.ID, singleStudy.getData().getCourseDetail().getId());

                            if (((TextView) attemptLL.getChildAt(0)).getText().toString().equalsIgnoreCase("RE-ATTEMPT")) {
                                quiz_id = video.getId();
                                quiz_name = video.getTest_series_name();
                                totalQuestion = video.getTotal_questions();
                                first_attempt = "0";
                                result_date = video.getResult_date();
                                videopositonwise = video;
                                startTestAPI();
                            } else if (((TextView) attemptLL.getChildAt(0)).getText().toString().equalsIgnoreCase("ATTEMPT NOW")) {
                                quiz_id = video.getId();
                                quiz_name = video.getTest_series_name();
                                totalQuestion = video.getTotal_questions();
                                first_attempt = "1";
                                test_submission = video.getSubmission_type();

                                result_date = video.getResult_date();
                                videopositonwise = video;
                                startTestAPI();
                            } else if (((TextView) attemptLL.getChildAt(0)).getText().toString().equalsIgnoreCase(activity.getResources().getString(R.string.view_rank))) {
                                quiz_id = video.getId();
                                quiz_name = video.getTest_series_name();
                                totalQuestion = video.getTotal_questions();
                                if (video.getState().equalsIgnoreCase("1")) {
                                    Intent resultScreen = new Intent(activity, QuizActivity.class);
                                    resultScreen.putExtra(Const.FRAG_TYPE, Const.RESULT_SCREEN);
                                    resultScreen.putExtra(Const.STATUS, quiz_id);
                                    resultScreen.putExtra(Const.NAME, quiz_name);
                                    resultScreen.putExtra("first_attempt", "1");
                                    activity.startActivity(resultScreen);

                                } else {
                                    Intent resultScreen = new Intent(activity, QuizActivity.class);
                                    resultScreen.putExtra(Const.FRAG_TYPE, "leader_board");
                                    resultScreen.putExtra(Const.STATUS, quiz_id);
                                    resultScreen.putExtra(Const.NAME, quiz_name);
                                    activity.startActivity(resultScreen);
                                }

                            }
                        }
                    }
                }
            });

            practice.setOnClickListener(v -> {
                if (!Helper.isConnected(activity)) {
                    Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (is_purchase.equalsIgnoreCase("1")) {
                    SharedPreference.getInstance().putString(Const.ID, singleStudy.getData().getCourseDetail().getId());
                    quiz_id = video.getId();
                    quiz_name = video.getTest_series_name();
                    totalQuestion = video.getTotal_questions();
                    first_attempt = "0";
                    result_date = video.getResult_date();
                    videopositonwise = video;
                    startTestAPI();
                } else {
                    if (video.getIs_locked().equalsIgnoreCase("1")) {

                        Intent intent = new Intent(activity, PurchaseActivity.class);
                        intent.putExtra("single_study", singleStudy);
                        Helper.gotoActivity(intent, activity);
                    } else {
                        SharedPreference.getInstance().putString(Const.ID, singleStudy.getData().getCourseDetail().getId());
                        quiz_id = video.getId();
                        quiz_name = video.getTest_series_name();
                        totalQuestion = video.getTotal_questions();
                        first_attempt = "0";
                        result_date = video.getResult_date();
                        videopositonwise = video;
                        startTestAPI();
                    }
                }
            });

            learn.setOnClickListener(v -> {
                if (!Helper.isConnected(activity)) {
                    Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (is_purchase.equalsIgnoreCase("1")) {
                    SharedPreference.getInstance().putString(Const.ID, singleStudy.getData().getCourseDetail().getId());
                    quiz_id = video.getId();
                    quiz_name = video.getTest_series_name();
                    totalQuestion = video.getTotal_questions();
                    if (video.getState().equalsIgnoreCase("1")) {
                        quiz_id = video.getId();
                        quiz_name = video.getTest_series_name();
                        totalQuestion = video.getTotal_questions();
                        result_without_submit(quiz_id, singleStudy.getData().getCourseDetail().getId(), "1", quiz_name);
                    } else {
                        quiz_id = video.getId();
                        quiz_name = video.getTest_series_name();
                        totalQuestion = video.getTotal_questions();
                        result_without_submit(quiz_id, singleStudy.getData().getCourseDetail().getId(), "0", quiz_name);

                    }
                } else {
                    if (video.getIs_locked().equalsIgnoreCase("1")) {

                        Intent intent = new Intent(activity, PurchaseActivity.class);
                        intent.putExtra("single_study", singleStudy);
                        Helper.gotoActivity(intent, activity);
                    } else {
                        SharedPreference.getInstance().putString(Const.ID, singleStudy.getData().getCourseDetail().getId());
                        if (video.getState().equalsIgnoreCase("1")) {
                            quiz_id = video.getId();
                            quiz_name = video.getTest_series_name();
                            totalQuestion = video.getTotal_questions();
                            result_without_submit(quiz_id, singleStudy.getData().getCourseDetail().getId(), "1", quiz_name);
                        } else {
                            quiz_id = video.getId();
                            quiz_name = video.getTest_series_name();
                            totalQuestion = video.getTotal_questions();
                            result_without_submit(quiz_id, singleStudy.getData().getCourseDetail().getId(), "0", quiz_name);

                        }
                    }
                }
            });

            share.setOnClickListener(v -> {
                if (is_purchase.equalsIgnoreCase("1")) {
                    Helper.shareTestg(activity, video.getPayloadData().getCourse_id(), video.getId(), video.getPayloadData().getTopic_id(), video.getPayloadData().getTile_type(), video.getPayloadData().getTile_id(), video.getPayloadData().getRevert_api(), "test", video.getImage(), video.getTitle(), SingleStudy.parentCourseId);
                } else {
                    if (video.getIs_locked().equalsIgnoreCase("1")) {
                        Intent intent = new Intent(activity, PurchaseActivity.class);
                        intent.putExtra("single_study", singleStudy);
                        Helper.gotoActivity(intent, activity);
                    } else {
                        Helper.shareTestg(activity, video.getPayloadData().getCourse_id(), video.getId(), video.getPayloadData().getTopic_id(), video.getPayloadData().getTile_type(), video.getPayloadData().getTile_id(), video.getPayloadData().getRevert_api(), "test", video.getImage(), video.getTitle(), SingleStudy.parentCourseId);
                    }
                }
            });
            attemptLL.setOnClickListener(view -> {
                if (!Helper.isConnected(activity)) {
                    Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (is_purchase.equalsIgnoreCase("1")) {
                    SharedPreference.getInstance().putString(Const.ID, singleStudy.getData().getCourseDetail().getId());

                    if (((TextView) attemptLL.getChildAt(0)).getText().toString().equalsIgnoreCase("RE-ATTEMPT")) {
                        quiz_id = video.getId();
                        quiz_name = video.getTest_series_name();
                        totalQuestion = video.getTotal_questions();
                        first_attempt = "0";
                        result_date = video.getResult_date();
                        videopositonwise = video;
                        startTestAPI();
                    } else if (((TextView) attemptLL.getChildAt(0)).getText().toString().equalsIgnoreCase("ATTEMPT NOW")) {
                        quiz_id = video.getId();
                        quiz_name = video.getTest_series_name();
                        totalQuestion = video.getTotal_questions();
                        first_attempt = "1";
                        test_submission = video.getSubmission_type();
                        result_date = video.getResult_date();
                        videopositonwise = video;
                        startTestAPI();
                    } else if (((TextView) attemptLL.getChildAt(0)).getText().toString().equalsIgnoreCase("ATTEMPTED")) {
                        if (video.getResult_date() != null && !video.getResult_date().equalsIgnoreCase("0") && !video.getResult_date().equalsIgnoreCase("1") && !video.getResult_date().equalsIgnoreCase("")) {
                            Snackbar.make(view, "Your Result will be declare on " + new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(Long.parseLong(video.getResult_date()) * 1000)), Snackbar.LENGTH_SHORT).show();

                        } else if (utkashRoom.getTestDao().is_test_exit(video.getId(), MakeMyExam.userId)) {
                            Snackbar.make(view, "Your Result is Getting Ready Please refresh your page..", Snackbar.LENGTH_SHORT).show();
                        } else {
                            Snackbar.make(view, "Your Test has been alredy submiited", Snackbar.LENGTH_SHORT).show();
                        }

                    } else if (((TextView) attemptLL.getChildAt(0)).getText().toString().equalsIgnoreCase(activity.getResources().getString(R.string.view_rank))) {
                        quiz_id = video.getId();
                        quiz_name = video.getTest_series_name();
                        totalQuestion = video.getTotal_questions();

                        if (video.getState().equalsIgnoreCase("1")) {

                            if (video.getResult_date() == null || video.getResult_date().equalsIgnoreCase("") || video.getResult_date().equalsIgnoreCase("0") || video.getResult_date().equalsIgnoreCase("1")) {
                                Intent resultScreen = new Intent(activity, QuizActivity.class);
                                resultScreen.putExtra(Const.FRAG_TYPE, Const.RESULT_SCREEN);
                                resultScreen.putExtra(Const.STATUS, quiz_id);
                                resultScreen.putExtra(Const.NAME, quiz_name);
                                // resultScreen.putExtra("show_leader", "0");
                                resultScreen.putExtra("first_attempt", "1");
                                Helper.gotoActivity(resultScreen, activity);


                            } else {
                                Intent resultScreen = new Intent(activity, QuizActivity.class);
                                resultScreen.putExtra(Const.FRAG_TYPE, Const.RESULT_SCREEN);
                                resultScreen.putExtra(Const.STATUS, quiz_id);
                                resultScreen.putExtra(Const.NAME, quiz_name);
                                resultScreen.putExtra("first_attempt", "1");
                                Helper.gotoActivity(resultScreen, activity);
                            }


                        } else {
                            Intent resultScreen = new Intent(activity, QuizActivity.class);
                            resultScreen.putExtra(Const.FRAG_TYPE, "leader_board");
                            resultScreen.putExtra(Const.STATUS, quiz_id);
                            resultScreen.putExtra(Const.NAME, quiz_name);
                            Helper.gotoActivity(resultScreen, activity);

                        }

                    }
                } else {
                    if (video.getIs_locked().equalsIgnoreCase("1")) {

                        Intent intent = new Intent(activity, PurchaseActivity.class);
                        intent.putExtra("single_study", singleStudy);
                        Helper.gotoActivity(intent, activity);
                    } else {
                        SharedPreference.getInstance().putString(Const.ID, singleStudy.getData().getCourseDetail().getId());

                        if (((TextView) attemptLL.getChildAt(0)).getText().toString().equalsIgnoreCase("RE-ATTEMPT")) {
                            quiz_id = video.getId();
                            quiz_name = video.getTest_series_name();
                            totalQuestion = video.getTotal_questions();
                            first_attempt = "0";
                            result_date = video.getResult_date();
                            videopositonwise = video;
                            startTestAPI();
                        } else if (((TextView) attemptLL.getChildAt(0)).getText().toString().equalsIgnoreCase("ATTEMPT NOW")) {
                            quiz_id = video.getId();
                            quiz_name = video.getTest_series_name();
                            totalQuestion = video.getTotal_questions();
                            first_attempt = "1";
                            test_submission = video.getSubmission_type();

                            result_date = video.getResult_date();
                            videopositonwise = video;
                            startTestAPI();
                        } else if (((TextView) attemptLL.getChildAt(0)).getText().toString().equalsIgnoreCase(activity.getResources().getString(R.string.view_rank))) {
                            quiz_id = video.getId();
                            quiz_name = video.getTest_series_name();
                            totalQuestion = video.getTotal_questions();
                            if (video.getState().equalsIgnoreCase("1")) {
                                Intent resultScreen = new Intent(activity, QuizActivity.class);
                                resultScreen.putExtra(Const.FRAG_TYPE, Const.RESULT_SCREEN);
                                resultScreen.putExtra(Const.STATUS, quiz_id);
                                resultScreen.putExtra(Const.NAME, quiz_name);
                                resultScreen.putExtra("first_attempt", "1");
                                activity.startActivity(resultScreen);

                            } else {
                                Intent resultScreen = new Intent(activity, QuizActivity.class);
                                resultScreen.putExtra(Const.FRAG_TYPE, "leader_board");
                                resultScreen.putExtra(Const.STATUS, quiz_id);
                                resultScreen.putExtra(Const.NAME, quiz_name);
                                activity.startActivity(resultScreen);
                            }

                        }
                    }
                }
            });


/*
            study_single_itemLL.setOnClickListener(view -> {
                SharedPreference.getInstance().putString(Const.ID, singleStudy.getData().getCourseDetail().getId());
                if (is_purchase.equalsIgnoreCase("1")) {
                    if (video.getState().equals("1")) {
                        long tsLong = time;
                        if (!TextUtils.isEmpty(video.getResult_date())) {
                            if (video.getResult_date().equalsIgnoreCase("0")) {
                                quiz_id = video.getId();
                                quiz_name = video.getTest_series_name();
                                totalQuestion = video.getTotal_questions();
                                Intent resultScreen = new Intent(activity, QuizActivity.class);
                                resultScreen.putExtra(Const.FRAG_TYPE, Const.RESULT_SCREEN);
                                resultScreen.putExtra(Const.STATUS, quiz_id);
                                resultScreen.putExtra(Const.NAME, quiz_name);
                                resultScreen.putExtra("first_attempt", "1");
                                activity.startActivity(resultScreen);

                            }else  if (video.getResult_date().equalsIgnoreCase("1")){
                                AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.CustomAlertDialog);
                                builder.setTitle("Thank You");
                                builder.setCancelable(false);
                                builder.setNegativeButton(Html.fromHtml("<font color='#000000'>Ok</font>"), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        quiz_id = video.getId();
                                        quiz_name = video.getTest_series_name();
                                        totalQuestion = video.getTotal_questions();

                                        Intent resultScreen = new Intent(activity, QuizActivity.class);
                                        resultScreen.putExtra(Const.FRAG_TYPE, Const.SOLUTIONREPORT);
                                        resultScreen.putExtra(Const.STATUS, quiz_id);
                                        resultScreen.putExtra(Const.NAME, quiz_name);
                                        resultScreen.putExtra("first_attempt", "1");
                                        activity.startActivity(resultScreen);
                                    }
                                });
                                builder.show();

                            }else {
                                if (tsLong > (Long.parseLong(video.getResult_date()) * 1000)) {
                                    quiz_id = video.getId();
                                    quiz_name = video.getTest_series_name();
                                    totalQuestion = video.getTotal_questions();

                                    Intent resultScreen = new Intent(activity, QuizActivity.class);
                                    resultScreen.putExtra(Const.FRAG_TYPE, Const.RESULT_SCREEN);
                                    resultScreen.putExtra(Const.STATUS, quiz_id);
                                    resultScreen.putExtra(Const.NAME, quiz_name);
                                    resultScreen.putExtra("first_attempt", "1");
                                    activity.startActivity(resultScreen);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.CustomAlertDialog);
                                    long result_time = Long.parseLong(video.getResult_date()) * 1000;
                                    Date date = new Date(result_time);
                                    String formateDate = new SimpleDateFormat("dd-MMM-yyyy hh:mm aa").format(date);
                                    builder.setTitle("Test Result will be available on " + formateDate);
                                    builder.setCancelable(false);
                                    builder.setNegativeButton(Html.fromHtml("<font color='#000000'>Ok</font>"), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            quiz_id = video.getId();
                                            quiz_name = video.getTest_series_name();
                                            totalQuestion = video.getTotal_questions();

                                            Intent resultScreen = new Intent(activity, QuizActivity.class);
                                            resultScreen.putExtra(Const.FRAG_TYPE, Const.SOLUTIONREPORT);
                                            resultScreen.putExtra(Const.STATUS, quiz_id);
                                            resultScreen.putExtra(Const.NAME, quiz_name);
                                            resultScreen.putExtra("first_attempt", "1");
                                            activity.startActivity(resultScreen);
                                        }
                                    });
                                    builder.show();

                                }
                            }
                        }
                    } else if (video.getState().equals("0")) {
                        if (!video.getStart_date().equalsIgnoreCase("") || !video.getEnd_date().equalsIgnoreCase("")) {
                            long millis = Long.parseLong(video.getStart_date())*1000;
                            long end_millis = Long.parseLong(video.getEnd_date())*1000;
                            Date d = new Date(millis);
                            //Date d = new Date(Long.parseLong("1576837800")*1000);
                            DateFormat f = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
                            String dateString = f.format(d);
                            Date date = null;
                            try {
                                date = f.parse(f.format(d));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            String[] amPM = dateString.split("\\s+");

                            String[] fullDate = String.valueOf(date).split("\\s+");
                            String correctDateFormat = fullDate[0] + ", " + fullDate[1] + " " + fullDate[2] + ", " + fullDate[5] + " at " + amPM[1] + " " + amPM[2];

                            System.out.println("Current time in AM/PM: " + correctDateFormat);

                            System.out.println("Current time : " + time);
                            System.out.println("Current time_start : " + millis);
                            System.out.println("Current time_end : " + end_millis);

                            if (time < millis) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.CustomAlertDialog);
                                builder.setTitle("This test will be available on " + correctDateFormat);
                                //builder.setTitle("Alert !");
                                builder.setCancelable(false);
                                builder.setNegativeButton(Html.fromHtml("<font color='#000000'>Close</font>"), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            } else if (end_millis < time) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.CustomAlertDialog);
                                builder.setTitle("Test Date Expired !");
                                //builder.setTitle("Alert !");
                                builder.setCancelable(false);

                                builder.setNegativeButton(Html.fromHtml("<font color='#000000'>Close</font>"), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            } else {
                                SharedPreference.getInstance().putBoolean(Const.RE_ATTEMPT, false);
                                quiz_id = video.getId();
                                quiz_name = video.getTest_series_name();
                                totalQuestion = video.getTotal_questions();
                                startTestAPI();
                            }
                        } else {
                            SharedPreference.getInstance().putBoolean(Const.RE_ATTEMPT, false);
                            quiz_id = video.getId();
                            quiz_name = video.getTest_series_name();
                            totalQuestion = video.getTotal_questions();
                            startTestAPI();
                        }
                    } else {
                        if (!video.getStart_date().equalsIgnoreCase("") || !video.getEnd_date().equalsIgnoreCase("")) {
                            long millis = Long.parseLong(video.getStart_date())*1000;
                            long end_millis = Long.parseLong(video.getEnd_date())*1000;
                            Date d = new Date(millis);
                            //Date d = new Date(Long.parseLong("1576837800")*1000);
                            DateFormat f = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
                            String dateString = f.format(d);
                            Date date = null;
                            try {
                                date = f.parse(f.format(d));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            String[] amPM = dateString.split("\\s+");

                            String[] fullDate = String.valueOf(date).split("\\s+");
                            String correctDateFormat = fullDate[0] + ", " + fullDate[1] + " " + fullDate[2] + ", " + fullDate[5] + " at " + amPM[1] + " " + amPM[2];

                            System.out.println("Current time in AM/PM: " + correctDateFormat);
                            System.out.println("Current time : " + time);
                            System.out.println("Current time_start : " + millis);
                            System.out.println("Current time_end : " + end_millis);

                            if (time < millis) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.CustomAlertDialog);
                                builder.setTitle("This test will be available on " + correctDateFormat);
                                //builder.setTitle("Alert !");
                                builder.setCancelable(false);

                                builder.setNegativeButton(Html.fromHtml("<font color='#000000'>Close</font>"), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            } else if (end_millis < time) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.CustomAlertDialog);
                                builder.setTitle("Test Date Expired !");
                                //builder.setTitle("Alert !");
                                builder.setCancelable(false);

                                builder.setNegativeButton(Html.fromHtml("<font color='#000000'>Close</font>"), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            } else {
                                SharedPreference.getInstance().putBoolean(Const.RE_ATTEMPT, false);
                                quiz_id = video.getId();
                                quiz_name = video.getTest_series_name();
                                totalQuestion = video.getTotal_questions();
                                startTestAPI();
                            }
                        } else {
                            SharedPreference.getInstance().putBoolean(Const.RE_ATTEMPT, false);
                            quiz_id = video.getId();
                            quiz_name = video.getTest_series_name();
                            totalQuestion = video.getTotal_questions();
                            startTestAPI();
                        }

                    }
                }else{
                    if (video.getIs_locked().equalsIgnoreCase("1")) {

                        Intent intent = new Intent(activity, PurchaseActivity.class);
                        intent.putExtra("single_study",singleStudy);
                        Helper.gotoActivity(intent,activity);
                    }else {
                        if (video.getState().equals("1")) {
                            long tsLong = time;
                            if (!TextUtils.isEmpty(video.getResult_date())) {
                                if (video.getResult_date().equalsIgnoreCase("0")) {
                                    quiz_id = video.getId();
                                    quiz_name = video.getTest_series_name();
                                    totalQuestion = video.getTotal_questions();
                                    Intent resultScreen = new Intent(activity, QuizActivity.class);
                                    resultScreen.putExtra(Const.FRAG_TYPE, Const.RESULT_SCREEN);
                                    resultScreen.putExtra(Const.STATUS, quiz_id);
                                    resultScreen.putExtra(Const.NAME, quiz_name);
                                    resultScreen.putExtra("first_attempt", "1");
                                    activity.startActivity(resultScreen);

                                }else  if (video.getResult_date().equalsIgnoreCase("1")){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.CustomAlertDialog);
                                    builder.setTitle("Thank You");
                                    builder.setCancelable(false);
                                    builder.setNegativeButton(Html.fromHtml("<font color='#000000'>Ok</font>"), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            quiz_id = video.getId();
                                            quiz_name = video.getTest_series_name();
                                            totalQuestion = video.getTotal_questions();

                                            Intent resultScreen = new Intent(activity, QuizActivity.class);
                                            resultScreen.putExtra(Const.FRAG_TYPE, Const.SOLUTIONREPORT);
                                            resultScreen.putExtra(Const.STATUS, quiz_id);
                                            resultScreen.putExtra(Const.NAME, quiz_name);
                                            resultScreen.putExtra("first_attempt", "1");
                                            activity.startActivity(resultScreen);
                                        }
                                    });
                                    builder.show();

                                }else {
                                    if (tsLong > (Long.parseLong(video.getResult_date()) * 1000)) {
                                        quiz_id = video.getId();
                                        quiz_name = video.getTest_series_name();
                                        totalQuestion = video.getTotal_questions();

                                        Intent resultScreen = new Intent(activity, QuizActivity.class);
                                        resultScreen.putExtra(Const.FRAG_TYPE, Const.RESULT_SCREEN);
                                        resultScreen.putExtra(Const.STATUS, quiz_id);
                                        resultScreen.putExtra(Const.NAME, quiz_name);
                                        resultScreen.putExtra("first_attempt", "1");
                                        activity.startActivity(resultScreen);
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.CustomAlertDialog);
                                        long result_time = Long.parseLong(video.getResult_date()) * 1000;
                                        Date date = new Date(result_time);
                                        String formateDate = new SimpleDateFormat("dd-MMM-yyyy hh:mm aa").format(date);
                                        builder.setTitle("Test Result will be available on " + formateDate);
                                        builder.setCancelable(false);
                                        builder.setNegativeButton(Html.fromHtml("<font color='#000000'>Ok</font>"), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                                quiz_id = video.getId();
                                                quiz_name = video.getTest_series_name();
                                                totalQuestion = video.getTotal_questions();

                                                Intent resultScreen = new Intent(activity, QuizActivity.class);
                                                resultScreen.putExtra(Const.FRAG_TYPE, Const.SOLUTIONREPORT);
                                                resultScreen.putExtra(Const.STATUS, quiz_id);
                                                resultScreen.putExtra(Const.NAME, quiz_name);
                                                resultScreen.putExtra("first_attempt", "1");
                                                activity.startActivity(resultScreen);
                                            }
                                        });
                                        builder.show();

                                    }
                                }
                            }
                        } else if (video.getState().equals("0")) {
                            if (!video.getStart_date().equalsIgnoreCase("") || !video.getEnd_date().equalsIgnoreCase("")) {
                                long millis = Long.parseLong(video.getStart_date())*1000;
                                long end_millis = Long.parseLong(video.getEnd_date())*1000;
                                Date d = new Date(millis);
                                //Date d = new Date(Long.parseLong("1576837800")*1000);
                                DateFormat f = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
                                String dateString = f.format(d);
                                Date date = null;
                                try {
                                    date = f.parse(f.format(d));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                String[] amPM = dateString.split("\\s+");

                                String[] fullDate = String.valueOf(date).split("\\s+");
                                String correctDateFormat = fullDate[0] + ", " + fullDate[1] + " " + fullDate[2] + ", " + fullDate[5] + " at " + amPM[1] + " " + amPM[2];

                                System.out.println("Current time in AM/PM: " + correctDateFormat);

                                System.out.println("Current time : " + time);
                                System.out.println("Current time_start : " + millis);
                                System.out.println("Current time_end : " + end_millis);

                                if (time < millis) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.CustomAlertDialog);
                                    builder.setTitle("This test will be available on " + correctDateFormat);
                                    //builder.setTitle("Alert !");
                                    builder.setCancelable(false);
                                    builder.setNegativeButton(Html.fromHtml("<font color='#000000'>Close</font>"), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });

                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                } else if (end_millis < time) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.CustomAlertDialog);
                                    builder.setTitle("Test Date Expired !");
                                    //builder.setTitle("Alert !");
                                    builder.setCancelable(false);

                                    builder.setNegativeButton(Html.fromHtml("<font color='#000000'>Close</font>"), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });

                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                } else {
                                    SharedPreference.getInstance().putBoolean(Const.RE_ATTEMPT, false);
                                    quiz_id = video.getId();
                                    quiz_name = video.getTest_series_name();
                                    totalQuestion = video.getTotal_questions();
                                    startTestAPI();
                                }
                            } else {
                                SharedPreference.getInstance().putBoolean(Const.RE_ATTEMPT, false);
                                quiz_id = video.getId();
                                quiz_name = video.getTest_series_name();
                                totalQuestion = video.getTotal_questions();
                                startTestAPI();
                            }
                        } else {
                            if (!video.getStart_date().equalsIgnoreCase("") || !video.getEnd_date().equalsIgnoreCase("")) {
                                long millis = Long.parseLong(video.getStart_date())*1000;
                                long end_millis = Long.parseLong(video.getEnd_date())*1000;
                                Date d = new Date(millis);
                                //Date d = new Date(Long.parseLong("1576837800")*1000);
                                DateFormat f = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
                                String dateString = f.format(d);
                                Date date = null;
                                try {
                                    date = f.parse(f.format(d));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                String[] amPM = dateString.split("\\s+");

                                String[] fullDate = String.valueOf(date).split("\\s+");
                                String correctDateFormat = fullDate[0] + ", " + fullDate[1] + " " + fullDate[2] + ", " + fullDate[5] + " at " + amPM[1] + " " + amPM[2];

                                System.out.println("Current time in AM/PM: " + correctDateFormat);
                                System.out.println("Current time : " + time);
                                System.out.println("Current time_start : " + millis);
                                System.out.println("Current time_end : " + end_millis);

                                if (time < millis) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.CustomAlertDialog);
                                    builder.setTitle("This test will be available on " + correctDateFormat);
                                    //builder.setTitle("Alert !");
                                    builder.setCancelable(false);

                                    builder.setNegativeButton(Html.fromHtml("<font color='#000000'>Close</font>"), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });

                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                } else if (end_millis < time) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.CustomAlertDialog);
                                    builder.setTitle("Test Date Expired !");
                                    //builder.setTitle("Alert !");
                                    builder.setCancelable(false);

                                    builder.setNegativeButton(Html.fromHtml("<font color='#000000'>Close</font>"), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });

                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                } else {
                                    SharedPreference.getInstance().putBoolean(Const.RE_ATTEMPT, false);
                                    quiz_id = video.getId();
                                    quiz_name = video.getTest_series_name();
                                    totalQuestion = video.getTotal_questions();
                                    startTestAPI();
                                }
                            } else {
                                SharedPreference.getInstance().putBoolean(Const.RE_ATTEMPT, false);
                                quiz_id = video.getId();
                                quiz_name = video.getTest_series_name();
                                totalQuestion = video.getTotal_questions();
                                startTestAPI();
                            }

                        }
                    }
                }



            });
*/


        }


        public void result_without_submit(String quiz_id, String course_id, String s, String quiz_name) {
            if (Helper.isNetworkConnected(activity)) {
                Helper.showProgressDialog(activity);
                APIInterface Service = MakeMyExam.getRetrofitInstance().create(APIInterface.class);
                EncryptionData masterdatadetailencryptionData = new EncryptionData();
                masterdatadetailencryptionData.setTest_id(quiz_id);
                masterdatadetailencryptionData.setCourse_id(course_id);
                masterdatadetailencryptionData.setFirst_attempt(s);
                String doseStr = new Gson().toJson(masterdatadetailencryptionData);
                String doseStrScr = AES.encrypt(doseStr);
                Call<String> response;
                response = Service.getTestlearn(doseStrScr);
                assert response != null;
                response.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Helper.dismissProgressDialog();
                        if (response.body() != null) {
                            JSONObject jsonResponse = null;
                            String jsonString;
                            try {
                                jsonString = AES.decrypt(response.body(), AES.generatekeyAPI(), AES.generateVectorAPI());
                                jsonResponse = new JSONObject(jsonString);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (jsonResponse == null) {
                                Helper.showToastSecurity(activity);
                                return;
                            }
                            ResultTestSeries_Report resultTestSeries2 = new Gson().fromJson(jsonResponse.toString(), ResultTestSeries_Report.class);
                            if (resultTestSeries2 == null) {
                                Helper.showToastSecurity(activity);
                                return;
                            }
                            try {
                                MakeMyExam.setTime_server((Long.parseLong(jsonResponse.optString("time"))) * 1000);

                                if (resultTestSeries2.getStatus().equalsIgnoreCase("true")) {
                                    SharedPreference.getInstance().putString("testresult", new Gson().toJson(resultTestSeries2));

                                    Intent intent = new Intent(activity, ViewSolutionActivity.class);
                                    intent.putExtra(Const.TESTSEGMENT_ID, quiz_id);
                                    intent.putExtra(Const.FRAG_TYPE, Const.SOLUTIONREPORT);
//                                    intent.putExtra(Const.RESULT_SCREEN, resultTestSeries2);
                                    intent.putExtra(Const.NAME, quiz_name);
                                    intent.putExtra("type", "learn");
                                    if (resultTestSeries2.getData().getLang_id().split(",")[0].equals("1")) {
                                        lang = Integer.parseInt(resultTestSeries2.getData().getLang_id().split(",")[0]);
                                    } else if (resultTestSeries2.getData().getLang_id().split(",")[0].equals("2")) {
                                        lang = Integer.parseInt(resultTestSeries2.getData().getLang_id().split(",")[0]);
                                    }
                                    intent.putExtra(Const.LANG, lang);
                                    Helper.gotoActivity(intent, activity);
                                } else {

                                    RetrofitResponse.GetApiData(activity, jsonResponse.optString(Const.AUTH_CODE), jsonResponse.optString(Const.MESSAGE), false);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {


                    }
                });
            } else {
                Toast.makeText(activity, R.string.Retry_with_Internet_connection, Toast.LENGTH_LONG).show();
            }
        }


        public void netoworkCallForQuizResult2(String quiz_id, String course_id, String s, String quiz_name) {
            if (Helper.isNetworkConnected(activity)) {
                Helper.showProgressDialog(activity);
                APIInterface Service = MakeMyExam.getRetrofitInstance().create(APIInterface.class);
                EncryptionData masterdatadetailencryptionData = new EncryptionData();
                masterdatadetailencryptionData.setTest_id(quiz_id);
                masterdatadetailencryptionData.setCourse_id(course_id);
                masterdatadetailencryptionData.setFirst_attempt(s);
                String doseStr = new Gson().toJson(masterdatadetailencryptionData);
                String doseStrScr = AES.encrypt(doseStr);

                Call<String> response;
                response = Service.getTestResult(doseStrScr);

                assert response != null;
                response.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Helper.dismissProgressDialog();
                        if (response.body() != null) {
                            JSONObject jsonResponse = null;
                            String jsonString;
                            try {
                                jsonString = AES.decrypt(response.body(), AES.generatekeyAPI(), AES.generateVectorAPI());
                                jsonResponse = new JSONObject(jsonString);
                            } catch (Exception e) {
                            }

                            if (jsonResponse == null) {
                                Helper.showToastSecurity(activity);
                                return;
                            }

                            ResultTestSeries_Report resultTestSeries2 = new Gson().fromJson(jsonResponse.toString(), ResultTestSeries_Report.class);

                            if (resultTestSeries2 == null) {
                                Helper.showToastSecurity(activity);
                                return;
                            }

                            try {
                                MakeMyExam.setTime_server((Long.parseLong(jsonResponse.optString("time"))) * 1000);

                                if (resultTestSeries2.getData().getQuestions() != null && resultTestSeries2.getStatus().equalsIgnoreCase("true")) {
                                    if (resultTestSeries2.getData().getQuestions().size() > 0) {
                                        SharedPreference.getInstance().putString("testresult", new Gson().toJson(resultTestSeries2));

                                        Intent intent = new Intent(activity, ViewSolutionActivity.class);
                                        intent.putExtra(Const.TESTSEGMENT_ID, quiz_id);
                                        intent.putExtra(Const.FRAG_TYPE, Const.SOLUTIONREPORT);
//                                        intent.putExtra(Const.RESULT_SCREEN, resultTestSeries2);
                                        intent.putExtra(Const.NAME, quiz_name);
                                        intent.putExtra("type", "learn");
                                        if (resultTestSeries2.getData().getLang_id().split(",")[0].equals("1")) {
                                            lang = Integer.parseInt(resultTestSeries2.getData().getLang_id().split(",")[0]);
                                        } else if (resultTestSeries2.getData().getLang_id().split(",")[0].equals("2")) {
                                            lang = Integer.parseInt(resultTestSeries2.getData().getLang_id().split(",")[0]);
                                        }
                                        intent.putExtra(Const.LANG, lang);
                                        Helper.gotoActivity(intent, activity);
                                    } else {
                                        Toast.makeText(activity, "No Question Found", Toast.LENGTH_SHORT).show();
                                    }


                                } else {

                                    RetrofitResponse.GetApiData(activity, jsonResponse.optString(Const.AUTH_CODE), jsonResponse.optString(Const.MESSAGE), false);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                        Helper.dismissProgressDialog();
                    }
                });
            } else {
                Toast.makeText(activity, R.string.Retry_with_Internet_connection, Toast.LENGTH_LONG).show();
            }

        }


        public void setImageOnClick(final Video video) {

            if (!Helper.isConnected(activity)) {
                Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
                return;
            }

            study_single_itemLL.setOnClickListener(view -> {
                study_single_itemLL.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.nav_ripple));

                if (is_purchase.equalsIgnoreCase("1")) {
                    if (TextUtils.isEmpty(video.getFile_url()) && TextUtils.isEmpty(video.getId())) {
                        Toast.makeText(activity, "No image found", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Intent intent4 = new Intent(activity, WebViewActivty.class);
                    intent4.putExtra("type", video.getTitle());
                    intent4.putExtra("url", video.getFile_url());
                    intent4.putExtra("file_type", "image");
                    Helper.gotoActivity(intent4, activity);

                } else {
                    if (video.getIs_locked().equalsIgnoreCase("1")) {

                        Intent intent = new Intent(activity, PurchaseActivity.class);
                        intent.putExtra("single_study", singleStudy);
                        Helper.gotoActivity(intent, activity);
                    } else {
                        if (TextUtils.isEmpty(video.getFile_url()) && TextUtils.isEmpty(video.getId())) {
                            Toast.makeText(activity, "No image found", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Intent intent4 = new Intent(activity, WebViewActivty.class);
                        intent4.putExtra("type", video.getTitle());
                        intent4.putExtra("url", video.getFile_url());
                        intent4.putExtra("file_type", "image");
                        Helper.gotoActivity(intent4, activity);
                    }
                }
            });

            read_now.setOnClickListener(view -> {

                if (is_purchase.equalsIgnoreCase("1")) {
                    if (TextUtils.isEmpty(video.getFile_url()) && TextUtils.isEmpty(video.getId())) {
                        Toast.makeText(activity, "No image found", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Intent intent4 = new Intent(activity, WebViewActivty.class);
                    intent4.putExtra("type", video.getTitle());
                    intent4.putExtra("url", video.getFile_url());
                    intent4.putExtra("file_type", "image");
                    Helper.gotoActivity(intent4, activity);

                } else {
                    if (video.getIs_locked().equalsIgnoreCase("1")) {

                        Intent intent = new Intent(activity, PurchaseActivity.class);
                        intent.putExtra("single_study", singleStudy);
                        Helper.gotoActivity(intent, activity);
                    } else {
                        if (TextUtils.isEmpty(video.getFile_url()) && TextUtils.isEmpty(video.getId())) {
                            Toast.makeText(activity, "No image found", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Intent intent4 = new Intent(activity, WebViewActivty.class);
                        intent4.putExtra("type", video.getTitle());
                        intent4.putExtra("url", video.getFile_url());
                        intent4.putExtra("file_type", "image");
                        Helper.gotoActivity(intent4, activity);
                    }
                }

            });
        }

        public void setConceptOnClick(final Video video) {

            if (!Helper.isConnected(activity)) {
                Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
                return;
            }

            study_single_itemLL.setOnClickListener(view -> {
                study_single_itemLL.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.nav_ripple));

                if (is_purchase.equalsIgnoreCase("1")) {
                    Intent intent = new Intent(activity, Concept_newActivity.class); // AllCourse List
                    intent.putExtra("id", video.getId());
                    intent.putExtra("name", video.getTitle());
                    if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                        intent.putExtra(Const.COURSE_ID, singleStudy.getData().getCourseDetail().getId() + "#");

                    } else {
                        intent.putExtra(Const.COURSE_ID, SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());

                    }
                    intent.putExtra("modified", video.getModified());

                    intent.putExtra(Const.TILE_ID, video.getPayloadData().getTile_id());
                    Helper.gotoActivity(intent, activity);
                } else {
                    if (video.getIs_locked().equalsIgnoreCase("1")) {

                        Intent intent = new Intent(activity, PurchaseActivity.class);
                        intent.putExtra("single_study", singleStudy);
                        Helper.gotoActivity(intent, activity);
                    } else {
                        Intent intent = new Intent(activity, Concept_newActivity.class); // AllCourse List
                        intent.putExtra("id", video.getId());
                        intent.putExtra("name", video.getTitle());
                        if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                            intent.putExtra(Const.COURSE_ID, singleStudy.getData().getCourseDetail().getId() + "#");

                        } else {
                            intent.putExtra(Const.COURSE_ID, SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());

                        }
                        intent.putExtra("modified", video.getModified());

                        intent.putExtra(Const.TILE_ID, video.getPayloadData().getTile_id());
                        Helper.gotoActivity(intent, activity);
                    }
                }
            });

            read_now.setOnClickListener(view -> {
                if (is_purchase.equalsIgnoreCase("1")) {
                    Intent intent = new Intent(activity, Concept_newActivity.class); // AllCourse List
                    intent.putExtra("id", video.getId());
                    intent.putExtra("name", video.getTitle());

                    if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                        intent.putExtra(Const.COURSE_ID, singleStudy.getData().getCourseDetail().getId() + "#");

                    } else {
                        intent.putExtra(Const.COURSE_ID, SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());

                    }
                    intent.putExtra("modified", video.getModified());

                    intent.putExtra(Const.TILE_ID, video.getPayloadData().getTile_id());
                    Helper.gotoActivity(intent, activity);
                } else {
                    if (video.getIs_locked().equalsIgnoreCase("1")) {
                        Intent intent = new Intent(activity, PurchaseActivity.class);
                        intent.putExtra("single_study", singleStudy);
                        Helper.gotoActivity(intent, activity);
                    } else {
                        Intent intent = new Intent(activity, Concept_newActivity.class); // AllCourse List
                        intent.putExtra("id", video.getId());
                        intent.putExtra("name", video.getTitle());
                        if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                            intent.putExtra(Const.COURSE_ID, singleStudy.getData().getCourseDetail().getId() + "#");

                        } else {
                            intent.putExtra(Const.COURSE_ID, SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());

                        }

                        intent.putExtra("modified", video.getModified());
                        intent.putExtra(Const.TILE_ID, video.getPayloadData().getTile_id());
                        Helper.gotoActivity(intent, activity);
                    }
                }

            });

            share.setOnClickListener(v -> {
                if (is_purchase.equalsIgnoreCase("1")) {
                    if (video.getFile_type().equalsIgnoreCase("8")
                            || video.getFile_type().equalsIgnoreCase("7")
                            || video.getFile_type().equalsIgnoreCase("1")

                    ) {
                        Helper.sharePdf(activity, video.getPayloadData().getCourse_id(), video.getId(), video.getPayloadData().getTopic_id(), video.getPayloadData().getTile_type(), video.getPayloadData().getTile_id(), video.getPayloadData().getRevert_api(), "pdf", video.getThumbnail_url(), video.getTitle(), SingleStudy.parentCourseId);

                    }
                } else {
                    if (video.getIs_locked().equalsIgnoreCase("1")) {
                        Intent intent = new Intent(activity, PurchaseActivity.class);
                        intent.putExtra("single_study", singleStudy);
                        Helper.gotoActivity(intent, activity);
                    } else {
                        if (video.getFile_type().equalsIgnoreCase("8")
                                || video.getFile_type().equalsIgnoreCase("7")
                                || video.getFile_type().equalsIgnoreCase("1")

                        ) {
                            Helper.sharePdf(activity, video.getPayloadData().getCourse_id(), video.getId(), video.getPayloadData().getTopic_id(), video.getPayloadData().getTile_type(), video.getPayloadData().getTile_id(), video.getPayloadData().getRevert_api(), "pdf", video.getThumbnail_url(), video.getTitle(), SingleStudy.parentCourseId);
                        }
                    }
                }
            });

        }

        private void API_REQUEST_VIDEO_LINK(final Video videoData, int position) {

            if (videoData.getFile_type().equalsIgnoreCase("10")) {
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + videoData.getFile_url())));
            } else if (videoData.getVideo_type().equalsIgnoreCase("5")) {
                if (videoData.getLive_status().equalsIgnoreCase("1")) {
                    if (videoData.getId() == null ||
                            videoData.getId().equalsIgnoreCase("")) {
                        Toast.makeText(activity, "Url is not found", Toast.LENGTH_SHORT).show();
                    } else {
                        videodata = videoData;
                        NetworkCall networkCall = new NetworkCall(ExamPrepLayer3Adapter.this, activity);
                        networkCall.NetworkAPICall(API.get_video_link, "", true, false);

                        //  Helper.GoToLiveAwsVideoActivity(videoData.getVideo_type(), videoData.getChat_node(), activity, videoData.getId(), videoData.getVideo_type(), videoData.getId(), videoData.getTitle(), "0", videoData.getThumbnail_url(), videoData.getPayloadData().getCourse_id(), videoData.getPayloadData().getTile_id(), videoData.getPayloadData().getTile_type(), videoData.getIs_chat_lock(), String.valueOf(position), SingleStudy.parentCourseId);
                    }
                } else if (videoData.getLive_status().equalsIgnoreCase("0")) {
                    Toast.makeText(activity, "Live Class will start on " + new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(Long.parseLong(videoData.getStart_date()) * 1000)), Toast.LENGTH_SHORT).show();
                } else if (videoData.getLive_status().equalsIgnoreCase("2")) {
                    Toast.makeText(activity, "Live class is ended", Toast.LENGTH_SHORT).show();
                } else if (videoData.getLive_status().equalsIgnoreCase("3")) {
                    Toast.makeText(activity, "Live class is cancelled", Toast.LENGTH_SHORT).show();
                }
            } else if (videoData.getVideo_type().equalsIgnoreCase("0")) {
                videodata = videoData;
                NetworkCall networkCall = new NetworkCall(ExamPrepLayer3Adapter.this, activity);
                networkCall.NetworkAPICall(API.get_video_link, "", true, false);


                // Helper.GoToLiveAwsVideoActivity(videoData.getVideo_type(), videoData.getChat_node(), activity, videoData.getId(), videoData.getVideo_type(), videoData.getId(), videoData.getTitle(), "0", videoData.getThumbnail_url(), videoData.getPayloadData().getCourse_id(), videoData.getPayloadData().getTile_id(), videoData.getPayloadData().getTile_type(), videoData.getIs_chat_lock(), String.valueOf(position), SingleStudy.parentCourseId);
            } else if (videoData.getVideo_type().equalsIgnoreCase("6")) {

                if (utkashRoom.getvideoDownloadao().isvideo_exit(videoData.getId(), MakeMyExam.userId)) {
                    VideosDownload videoDownload = utkashRoom.getvideoDownloadao().getvideo_byuserid(videoData.getId(), MakeMyExam.userId);
                    if (videoDownload.getIs_complete().equalsIgnoreCase("1")) {
                        Intent i = new Intent(activity, DownloadVideoPlayer.class);
                        i.putExtra("video_name", videoDownload.getVideo_name());
                        i.putExtra("video_id", videoDownload.getVideo_id());
                        i.putExtra("current_pos", videoDownload.getVideoCurrentPosition());
                        i.putExtra("video", videoDownload.getVideo_history());
                        i.putExtra("video_time", videoDownload.getVideotime());
                        i.putExtra("course_id", videoDownload.getCourse_id());
                        activity.startActivity(i);
                    } else {
                        videodata = videoData;
                        NetworkCall networkCall = new NetworkCall(ExamPrepLayer3Adapter.this, activity);
                        networkCall.NetworkAPICall(API.get_video_link, "", true, false);

                    }
                } else {
                    videodata = videoData;
                    NetworkCall networkCall = new NetworkCall(ExamPrepLayer3Adapter.this, activity);
                    networkCall.NetworkAPICall(API.get_video_link, "", true, false);

                       /* if (utkashRoom.getvideoDao().isvideo_exit(videoData.getId(), MakeMyExam.userId)) {
                            if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                                Helper.GoToJWVideo_Params(activity, mediaId, videoData.getId(), videoData.getTitle(), utkashRoom.getvideoDao().getuser(videoData.getId(), MakeMyExam.userId).getVideo_currentpos(), singleStudy.getData().getCourseDetail().getId() + "#");

                            } else {
                                Helper.GoToJWVideo_Params(activity, mediaId, videoData.getId(), videoData.getTitle(), utkashRoom.getvideoDao().getuser(videoData.getId(), MakeMyExam.userId).getVideo_currentpos(), SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());

                            }
                        } else {
                            VideoTable videoTable = new VideoTable();
                            videoTable.setVideo_id(videoData.getId());
                            videoTable.setVideo_name(videoData.getTitle());
                            videoTable.setJw_url(mediaId);
                            videoTable.setVideo_currentpos(0L);
                            videoTable.setUser_id(MakeMyExam.userId);
                            if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                                videoTable.setCourse_id(singleStudy.getData().getCourseDetail().getId() + "#");
                                Helper.GoToJWVideo_Params(activity, mediaId, videoData.getId(), videoData.getTitle(), 0L, singleStudy.getData().getCourseDetail().getId() + "#");
                                utkashRoom.getvideoDao().addUser(videoTable);
                            } else {
                                videoTable.setCourse_id(SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());
                                Helper.GoToJWVideo_Params(activity, mediaId, videoData.getId(), videoData.getTitle(), 0L, SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());
                                utkashRoom.getvideoDao().addUser(videoTable);
                            }
                        }*/
                }


            } else if (videoData.getVideo_type().equalsIgnoreCase("1")) {
                Helper.audio_service_close((CourseActivity) activity);


                if (videoData.getOpen_in_app() != null && videoData.getOpen_in_app().equalsIgnoreCase("1")) {
                    Helper.GoToLiveVideoActivity(videoData.getChat_node(), activity, videoData.getFile_url(), videoData.getVideo_type(), videoData.getId(), videoData.getTitle(), "0", videoData.getThumbnail_url(), videoData.getIs_chat_lock(), videoData.getPayloadData().getCourse_id(), String.valueOf(position), SingleStudy.parentCourseId, videoData.getPayloadData().getTile_id(), videoData.getPayloadData().getTile_type());
                } else {
                    videodata = videoData;
                    NetworkCall networkCall = new NetworkCall(ExamPrepLayer3Adapter.this, activity);
                    networkCall.NetworkAPICall(API.get_video_link, "", true, false);


                    //  activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + videoData.getFile_url())));
                }
            } else if (videoData.getVideo_type().equalsIgnoreCase("4")) {
                Helper.audio_service_close((CourseActivity) activity);

                if (videoData.getLive_status().equalsIgnoreCase("1")) {
                    if (videoData.getFile_url() == null ||
                            videoData.getFile_url().equalsIgnoreCase("")) {
                        Toast.makeText(activity, "Url is not found", Toast.LENGTH_SHORT).show();
                    } else {

                        if (videoData.getOpen_in_app() != null && videoData.getOpen_in_app().equalsIgnoreCase("1")) {
                            Helper.GoToLiveVideoActivity(videoData.getChat_node(), activity, videoData.getFile_url(), videoData.getVideo_type(), videoData.getId(), videoData.getTitle(), "0", videoData.getThumbnail_url(), videoData.getIs_chat_lock(), videoData.getPayloadData().getCourse_id(), String.valueOf(position), SingleStudy.parentCourseId, videoData.getPayloadData().getTile_id(), videoData.getPayloadData().getTile_type());
                        } else {
                            videodata = videoData;
                            NetworkCall networkCall = new NetworkCall(ExamPrepLayer3Adapter.this, activity);
                            networkCall.NetworkAPICall(API.get_video_link, "", true, false);

                            // activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + videoData.getFile_url())));
                        }
                    }
                } else if (videoData.getLive_status().equalsIgnoreCase("0")) {
                    Toast.makeText(activity, "Live Class will start on " + new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(Long.parseLong(videoData.getStart_date()) * 1000)), Toast.LENGTH_SHORT).show();
                } else if (videoData.getLive_status().equalsIgnoreCase("2")) {
                    Toast.makeText(activity, "Live class is ended", Toast.LENGTH_SHORT).show();
                } else if (videoData.getLive_status().equalsIgnoreCase("3")) {
                    Toast.makeText(activity, "Live class is cancelled", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    private void videogetqulaity(VideosDownload videosDownload, HashMap<Integer, String> mediaResponseMap, int pos) {
        videoArrayList.get(pos).setVideo_status("Download Running");
        notifyItemChanged(pos);
        if (mediaResponseMap.get(180) != null) {
            AsyncTask.execute(() -> {
                if (utkashRoom.getvideoDownloadao().isvideo_exit(videosDownload.getVideo_id(), MakeMyExam.userId)) {
                    utkashRoom.getvideoDownloadao().update_videostatus(videosDownload.getVideo_id(), videosDownload.getVideo_status(), MakeMyExam.userId);
                } else {
                    utkashRoom.getvideoDownloadao().addUser(videosDownload);
                }
                Download_dialog(mediaResponseMap.get(180), videosDownload, pos);
            });

        } else if (mediaResponseMap.get(270) != null) {
            AsyncTask.execute(() -> {
                if (utkashRoom.getvideoDownloadao().isvideo_exit(videosDownload.getVideo_id(), MakeMyExam.userId)) {
                    utkashRoom.getvideoDownloadao().update_videostatus(videosDownload.getVideo_id(), videosDownload.getVideo_status(), MakeMyExam.userId);
                } else {
                    utkashRoom.getvideoDownloadao().addUser(videosDownload);
                }
                Download_dialog(mediaResponseMap.get(270), videosDownload, pos);
            });

        } else if (mediaResponseMap.get(360) != null) {
            AsyncTask.execute(() -> {
                if (utkashRoom.getvideoDownloadao().isvideo_exit(videosDownload.getVideo_id(), MakeMyExam.userId)) {
                    utkashRoom.getvideoDownloadao().update_videostatus(videosDownload.getVideo_id(), videosDownload.getVideo_status(), MakeMyExam.userId);
                } else {
                    utkashRoom.getvideoDownloadao().addUser(videosDownload);
                }
                Download_dialog(mediaResponseMap.get(360), videosDownload, pos);
            });

        }


    }

    private void Download_dialog(String url, VideosDownload videosDownload, int position) {
        if (utkashRoom.getvideoDownloadao().getuser(videosDownload.getVideo_id(), videosDownload.getIs_complete()).getToal_downloadlocale() == 0) {
            utkashRoom.getvideoDownloadao().update_pos(MakeMyExam.userId, videosDownload.getVideo_id(), utkashRoom.getvideoDownloadao().getalldownload_videos(MakeMyExam.userId).size() - 1);
            Intent videoDownloadIntent = null;
            videoDownloadIntent = new Intent(activity, VideoDownloadService.class);
            videoDownloadIntent.putExtra(VideoDownloadService.FILEDOWNLOADSTATUS, false);
            videoDownloadIntent.putExtra(VideoDownloadService.URL, url);
            videoDownloadIntent.putExtra(VideoDownloadService.DOWNLOAD_SERVICE_ID, videosDownload.getVideo_id());
            videoDownloadIntent.putExtra(VideoDownloadService.FILEPATH, videosDownload.getVideo_name());
            videoDownloadIntent.putExtra("name", videosDownload.getVideo_history());
            videoDownloadIntent.putExtra("pos", utkashRoom.getvideoDownloadao().getalldownload_videos(MakeMyExam.userId).size() - 1);
            videoDownloadIntent.putExtra("status", videosDownload.getVideo_status());
            videoDownloadIntent.putExtra("token", videosDownload.getVideo_token());
            VideoDownloadService.isServiceRunning = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                activity.startForegroundService(videoDownloadIntent);
            } else {
                activity.startService(videoDownloadIntent);
            }
        }
        activity.runOnUiThread(() -> {
            waiting_dialog(videosDownload.getVideo_name());

        });

    }

    private void waiting_dialog(String video_name) {
        final Dialog dialog = new Dialog(activity);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        dialog.setContentView(R.layout.dialog_alert_downloads);
        TextView titleDialog = dialog.findViewById(R.id.titleDialog);
        titleDialog.setText("Video");
        TextView msgDialog = dialog.findViewById(R.id.msgDialog);
        msgDialog.setText(video_name);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        Button btn_submit = dialog.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(v -> {
            Intent intent = new Intent(activity, DownloadActivity.class);
            Helper.gotoActivity(intent, activity);
            activity.finish();
            dialog.dismiss();
            dialog.cancel();
        });
        btn_cancel.setOnClickListener(v -> {
            dialog.dismiss();
            dialog.cancel();
        });
        dialog.show();
    }


    private void showPopUp(final InstructionData instructionData) {
        LayoutInflater li = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = li.inflate(R.layout.popup_basicinfo_quiz_career, null, false);
        //final Dialog quizBasicInfoDialog = new Dialog(activity, R.style.MyDialogTheme);
        final Dialog quizBasicInfoDialog = new Dialog(activity, R.style.CustomAlertDialog);
        quizBasicInfoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        quizBasicInfoDialog.setCanceledOnTouchOutside(true);
        quizBasicInfoDialog.setContentView(v);
        quizBasicInfoDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        quizBasicInfoDialog.show();

        RelativeLayout basicInfoLL, basicInfoQuizLL;
        final TextView quizTitle, quizQuesNumTv, quizTimeTV, quizCorrectValue, quizWrongValue, quizTotalMarks, generalInstrValueTV, reAttempt, languageSpinnerTV, quizsectionValueTV;
        ImageView quizImage;
        final CheckBox check_box;
        Button startQuiz;
        LinearLayout sectionListLL, general_layout, section_time;
        final TestBasicInst testBasicInst = instructionData.getTestBasic();

        basicInfoQuizLL = (RelativeLayout) v.findViewById(R.id.basicInfoDialogLL);
        quizTitle = (TextView) v.findViewById(R.id.quizTitleTV);
        quizImage = (ImageView) v.findViewById(R.id.quizImageIV);
        quizCorrectValue = (TextView) v.findViewById(R.id.marksCorrectValueTV);
        quizWrongValue = (TextView) v.findViewById(R.id.marksWrongValueTV);
        quizTotalMarks = (TextView) v.findViewById(R.id.marksTextValueTV);

        section_time = v.findViewById(R.id.section_time);

        quizQuesNumTv = (TextView) v.findViewById(R.id.numQuesValueTV);
        quizsectionValueTV = (TextView) v.findViewById(R.id.sectionValueTV);
        languageSpinnerTV = (TextView) v.findViewById(R.id.languageSpinnerTV);
        quizTimeTV = (TextView) v.findViewById(R.id.quizTimeValueTV);
        reAttempt = (TextView) v.findViewById(R.id.remarksTV);
        check_box = (CheckBox) v.findViewById(R.id.check_box);
        generalInstrValueTV = (TextView) v.findViewById(R.id.generalInstrValueTV);
        startQuiz = (Button) v.findViewById(R.id.startQuizBtn);
        sectionListLL = (LinearLayout) v.findViewById(R.id.sectionListLL);
        general_layout = (LinearLayout) v.findViewById(R.id.general_layout);

        if (testBasicInst.getTest_assets() != null) {
            if (testBasicInst.getTest_assets().getHide_inst_time().equalsIgnoreCase("0")) {
                section_time.setVisibility(View.VISIBLE);
            } else {
                section_time.setVisibility(View.INVISIBLE);
            }
        }

        int total_ques = 0;
        float totalmarks = 0;
        for (TestSectionInst testSectionInst : instructionData.getTestSections()) {
            if (testSectionInst.getOptional_que() == null) {
                testSectionInst.setOptional_que("0");
            }
            total_ques = total_ques + (Integer.parseInt(testSectionInst.getTotalQuestions()) - Integer.parseInt(testSectionInst.getOptional_que()));
            totalmarks = totalmarks + ((Integer.parseInt(testSectionInst.getTotalQuestions()) - Integer.parseInt(testSectionInst.getOptional_que())) * Float.parseFloat(testSectionInst.getMarksPerQuestion()));
        }
        quizQuesNumTv.setText("" + total_ques);
        quizTotalMarks.setText("" + totalmarks);

        addSectionView(sectionListLL, instructionData);

        if (SharedPreference.getInstance().getBoolean(Const.RE_ATTEMPT)) {
            reAttempt.setVisibility(View.GONE);
        } else
            reAttempt.setVisibility(View.GONE);
        Log.e("langlanth", "" + testBasicInst.getLang_id());
        if (testBasicInst.getLang_id().length() == 3) {
            languageSpinnerTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopMenuForLangauge(languageSpinnerTV, testBasicInst);
                }
            });
        }

        if (testBasicInst.getLang_id().split(",")[0].equals("1")) {
            languageSpinnerTV.setText(activity.getResources().getStringArray(R.array.dialog_choose_language_array)[0]);
            lang = Integer.parseInt(testBasicInst.getLang_id().split(",")[0]);
        } else if (testBasicInst.getLang_id().split(",")[0].equals("2")) {
            languageSpinnerTV.setText(activity.getResources().getStringArray(R.array.dialog_choose_language_array)[1]);
            lang = Integer.parseInt(testBasicInst.getLang_id().split(",")[0]);
        }

        quizTitle.setText(testBasicInst.getTestSeriesName());
        //   quizQuesNumTv.setText(testBasicInst.getTotalQuestions());
        quizTimeTV.setText(testBasicInst.getTimeInMins());
        //    quizTotalMarks.setText(testBasicInst.getTotalMarks());
        if (testBasicInst.getDescription().isEmpty()) {
            general_layout.setVisibility(View.GONE);
        } else {
            general_layout.setVisibility(View.VISIBLE);
            generalInstrValueTV.setText(Html.fromHtml(testBasicInst.getDescription()));
        }
        quizsectionValueTV.setText("" + instructionData.getTestSections().size());

        startQuiz.setTag(testBasicInst);
        startQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (testBasicInst.getTotalQuestions().equalsIgnoreCase("0")) {
                    Toast.makeText(activity, "Please add Question.", Toast.LENGTH_SHORT).show();

                } else {
                    if (check_box.isChecked()) {
                        quizBasicInfoDialog.dismiss();
                        quiz_id = testBasicInst.getId();
                        NetworkCall networkCall = new NetworkCall(ExamPrepLayer3Adapter.this, activity);
                        networkCall.NetworkAPICall(API.API_GET_INFO_TEST_SERIES, "", true, false);
                    } else {
                        Toast.makeText(activity, "Please check following instructions.", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void addSectionView(LinearLayout sectionListLL, InstructionData instructionData) {
        int count = 0;
        for (TestSectionInst testSectionInst : instructionData.getTestSections()) {
            if (testSectionInst.getOptional_que() == null) {
                testSectionInst.setOptional_que("0");
            }
            sectionListLL.addView(initSectionListView(testSectionInst, count, instructionData.getTestBasic().getTest_assets() == null ? "" : instructionData.getTestBasic().getTest_assets().getHide_inst_time()));
            count++;
        }
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    public LinearLayout initSectionListView(TestSectionInst testSectionInst, int tag, String hide_inst_time) {
        List<View> LinearLayoutList = new ArrayList<>();
        LinearLayout v = (LinearLayout) View.inflate(activity, R.layout.layout_option_section_list_view, null);
        TextView secNameTV = (TextView) v.findViewById(R.id.secNameTV);
        TextView totQuesTV = (TextView) v.findViewById(R.id.totQuesTV);
        TextView totTimeTV = (TextView) v.findViewById(R.id.totTimeTV);
        TextView maxMarksTV = (TextView) v.findViewById(R.id.maxMarksTV);
        TextView option_count = (TextView) v.findViewById(R.id.option_count);

        TextView markPerQuesTV = (TextView) v.findViewById(R.id.markPerQuesTV);
        TextView negMarkPerQuesTV = (TextView) v.findViewById(R.id.negMarkPerQuesTV);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);

        v.setLayoutParams(lp);

        if (!hide_inst_time.equalsIgnoreCase("")) {
            if (hide_inst_time.equalsIgnoreCase("0")) {
                totTimeTV.setVisibility(View.VISIBLE);
            } else {
                totTimeTV.setVisibility(View.INVISIBLE);
            }
        }

        secNameTV.setText(testSectionInst.getName() + "\n" + "(" + testSectionInst.getSectionPart() + ")");
        totQuesTV.setText(testSectionInst.getTotalQuestions());
        totTimeTV.setText(testSectionInst.getSectionTiming());
        option_count.setText("" + (Integer.parseInt(testSectionInst.getTotalQuestions()) - Integer.parseInt(testSectionInst.getOptional_que())));
        maxMarksTV.setText(String.format("%.2f", (Float.parseFloat(testSectionInst.getMarksPerQuestion()) * (Integer.parseInt(testSectionInst.getTotalQuestions()) - Integer.parseInt(testSectionInst.getOptional_que())))));
        markPerQuesTV.setText(testSectionInst.getMarksPerQuestion());
        negMarkPerQuesTV.setText("" + Float.parseFloat(testSectionInst.getNegativeMarks()));

        v.setTag(tag);
        LinearLayoutList.add(v);

        return v;
    }

    public void showPopMenuForLangauge(final View v, TestBasicInst testBasicInst) {
        PopupMenu popup = new PopupMenu(activity, v);

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ((TextView) v).setText(item.getTitle().toString());
                if (item.getTitle().toString().equals(activity.getString(R.string.hindi)))
                    lang = 2;
                else if (item.getTitle().toString().equals(activity.getString(R.string.english)))
                    lang = 1;
//                    else lang = -1;
                Log.e("Language", String.valueOf(lang));
                return false;
            }
        });

        for (int i = 0; i < testBasicInst.getLang_id().split(",").length; i++) {
//            if (quiz.getBasic_info().getLang_id().split(",").length >= 2)
//                popup.getMenu().add(activity.getResources().getStringArray(R.array.dialog_choose_language_array)[i]);
//            else {
            if (testBasicInst.getLang_id().split(",")[i].equals("1"))
                popup.getMenu().add(activity.getResources().getStringArray(R.array.dialog_choose_language_array)[0]);
            else if (testBasicInst.getLang_id().split(",")[i].equals("2"))
                popup.getMenu().add(activity.getResources().getStringArray(R.array.dialog_choose_language_array)[1]);
//            }
        }
        popup.show();
    }

    private void startTestAPI() {
        NetworkCall networkCall = new NetworkCall(ExamPrepLayer3Adapter.this, activity);
        networkCall.NetworkAPICall(API.API_GET_TEST_INSTRUCTION_DATA, "", true, false);
    }
}