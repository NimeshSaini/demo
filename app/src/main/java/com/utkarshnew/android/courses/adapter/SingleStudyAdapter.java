package com.utkarshnew.android.courses.adapter;

import static com.utkarshnew.android.courses.Fragment.ExamPrepLayer2.actual_videolist;
import static com.utkarshnew.android.Utils.Helper.convertDpToPixel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.utkarshnew.android.Download.Adapter.DownloadQualityAdapter;
import com.utkarshnew.android.Download.Interface.Getvideourl;
import com.utkarshnew.android.courses.Activity.Concept_newActivity;
import com.utkarshnew.android.courses.Activity.CourseActivity;
import com.utkarshnew.android.courses.Activity.QuizActivity;
import com.utkarshnew.android.courses.Fragment.SingleStudy;
import com.utkarshnew.android.courses.overview.adapter.OverviewRVAdapter;
import com.utkarshnew.android.Download.Audio.AudioPlayerService;
import com.utkarshnew.android.Download.AudioPlayerActivty;
import com.utkarshnew.android.Download.DownloadVideoPlayer;
import com.utkarshnew.android.DownloadServices.VideoDownloadService;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.Model.COURSEDETAIL.CourseDetail;
import com.utkarshnew.android.Model.COURSEDETAIL.CourseDetailData;
import com.utkarshnew.android.Model.COURSEDETAIL.TilesItem;
import com.utkarshnew.android.Model.Courselist;
import com.utkarshnew.android.Model.Courses.ExamPrepItem;
import com.utkarshnew.android.Model.Courses.Lists;
import com.utkarshnew.android.Model.FAQs.FaqData;
import com.utkarshnew.android.Model.Overview.OverviewData;
import com.utkarshnew.android.Model.UrlObject;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.home.Constants;
import com.utkarshnew.android.table.AudioTable;
import com.utkarshnew.android.table.TestTable;
import com.utkarshnew.android.table.VideoTable;
import com.utkarshnew.android.table.VideosDownload;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.LinearLayoutManagerWithSmoothScroller;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;
import com.utkarshnew.android.Utils.Progress;
import com.utkarshnew.android.Utils.SharedPreference;
import com.utkarshnew.android.Webview.RevisionActivity;
import com.utkarshnew.android.Webview.WebViewActivty;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SingleStudyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements NetworkCall.MyNetworkCallBack, Getvideourl {
    public String contentType;
    Activity activity;
    CourseDetail singleStudy;
    ExamPrepItem examPrepItem;
    OverviewData overviewData;
    onButtonClicked buttonClicked;
    ArrayList<Courselist> courseDataArrayList;
    ArrayList<FaqData> faqData;
    String parentCourseId;
    boolean isCombo = false;
    Boolean isLodded = true;
    String isSkip;
    int tilePos = 0;
    int lang;  // 0 for Hindi , 1 for English
    private String quiz_id;
    String is_purchase = "";
    private String quiz_name;

    String tileTypeAPI;
    String tileIdAPI;
    String revertAPI;
    int position_delete;
    BottomSheetDialog watchlist;

    public long server_time = MakeMyExam.getTime_server();
    public UtkashRoom utkashRoom = UtkashRoom.getAppDatabase(MakeMyExam.getAppContext());
    private Lists videodata;
    private boolean is_download = false;
    private boolean is_audio = false;
    SingleStudy study;
    private String totalQuestion, first_attempt, result_date = "", submission_type = "";

    public SingleStudyAdapter(Activity activity, CourseDetail singleStudy, ExamPrepItem examPrepItem, OverviewData overviewData, ArrayList<Courselist> courseDataArrayList, ArrayList<FaqData> faqData, onButtonClicked buttonClicked, String parentCourseId, boolean isCombo, String isSkip, int tilePos, String tileIdAPI, String tileTypeAPI, String revertAPI, SingleStudy study) {
        this.singleStudy = singleStudy;
        this.activity = activity;
        this.study = study;
        this.examPrepItem = examPrepItem;
        this.overviewData = overviewData;
        this.buttonClicked = buttonClicked;
        this.courseDataArrayList = courseDataArrayList;
        this.faqData = faqData;
        this.parentCourseId = parentCourseId;
        this.isCombo = isCombo;
        server_time = server_time == 0 ? System.currentTimeMillis() : MakeMyExam.getTime_server();
        this.isSkip = isSkip;
        this.tilePos = tilePos;
        this.tileTypeAPI = tileTypeAPI;
        this.tileIdAPI = tileIdAPI;
        this.revertAPI = revertAPI;
        is_purchase = singleStudy.getData().getCourseDetail().getIsPurchased();
        //  this.utkashRoom = utkashRoom;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(activity).inflate(R.layout.exam_prep_header, null);
            return new SingleStudyHeaderHolder(view);
        } else if (viewType == 1) {
            view = LayoutInflater.from(activity).inflate(R.layout.exam_prep_single_row_item, null);
            return new SingleStudyListHolder(view);
        } else if (viewType == 7) {
            view = LayoutInflater.from(activity).inflate(R.layout.exam_prep_single_row_item_pdf, null);
            return new SingleStudyPdfListHolder(view);
        } else if (viewType == 8) {
            view = LayoutInflater.from(activity).inflate(R.layout.exam_prep_single_row_item_concept, null);
            return new SingleStudyConceptListHolder(view);
        } else if (viewType == 4) {
            view = LayoutInflater.from(activity).inflate(R.layout.exam_prep_single_row_item_test, null);
            return new SingleStudyTestListHolder(view);
        } else if (viewType == 3) {
            view = LayoutInflater.from(activity).inflate(R.layout.course_overview_layout, null);
            return new OverViewHolder(view);
        } else if (viewType == 6) {
            view = LayoutInflater.from(activity).inflate(R.layout.faq_overview_layout, null);
            return new FAQViewHolder(view);
        } else if (viewType == 5) {
            view = LayoutInflater.from(activity).inflate(R.layout.exam_prep_single_row_item_combo, null);
            return new SingleStudyComboListHolder(view);
        } else if (viewType == 9) {
            view = LayoutInflater.from(activity).inflate(R.layout.no_data_found, null);
            return new StudyNoDataViewHolder(view);
        } else {
            view = LayoutInflater.from(activity).inflate(R.layout.no_data_found, null);
            return new StudyNoDataViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == 0) {
            ((SingleStudyHeaderHolder) holder).setDataHeader(singleStudy);
        } else if (getItemViewType(position) == 3) {
            ((OverViewHolder) holder).setData(singleStudy.getData().getCourseDetail(), overviewData);
        } else if (getItemViewType(position) == 1) {
            ((SingleStudyListHolder) holder).setData(examPrepItem.getList(), position);
        } else if (getItemViewType(position) == 7) {
            ((SingleStudyPdfListHolder) holder).setData(examPrepItem.getList(), position);
        } else if (getItemViewType(position) == 8) {
            ((SingleStudyConceptListHolder) holder).setData(examPrepItem.getList(), position);
        } else if (getItemViewType(position) == 4) {
            ((SingleStudyTestListHolder) holder).setData(examPrepItem.getList(), position);
        } else if (getItemViewType(position) == 5) {
            ((SingleStudyComboListHolder) holder).setData(courseDataArrayList, position);
        } else if (getItemViewType(position) == 6) {
            ((FAQViewHolder) holder).setData(faqData);
        } else if (getItemViewType(position) == 9) {
            ((StudyNoDataViewHolder) holder).setData();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return 0;  // HEADER
        else if (contentType.equalsIgnoreCase(Const.TEST + tileIdAPI)) return 4; //Test
        else if (contentType.equalsIgnoreCase(Const.BOOK + tileIdAPI)) return 2; //WebView
        else if (contentType.equalsIgnoreCase(Const.OVERVIEW + tileIdAPI)) return 3; //Overview
        else if (contentType.equalsIgnoreCase(Const.COMBO + tileIdAPI)) return 5; //Combo
        else if (contentType.equalsIgnoreCase(Const.FAQ + tileIdAPI)) return 6; //Faq
        else if (contentType.equalsIgnoreCase(Const.PDF + tileIdAPI)) return 7; //Pdf
        else if (contentType.equalsIgnoreCase(Const.CONCEPT + tileIdAPI)) return 8; //concept
        else if (contentType.equalsIgnoreCase(Const.NO_DATA)) return 9;
        else return 1;
    }

    @Override
    public int getItemCount() {
        if (contentType.equalsIgnoreCase(Const.BOOK + tileIdAPI)) {
            return 2;
        } else if (contentType.equalsIgnoreCase(Const.OVERVIEW + tileIdAPI)) {
            return 2;
        } else if (contentType.equalsIgnoreCase(Const.COMBO + tileIdAPI)) {
            return (courseDataArrayList != null && courseDataArrayList.size() > 0) ? (courseDataArrayList.size() + 1) : 2;
        } else if (contentType.equalsIgnoreCase(Const.FAQ + tileIdAPI)) {
            return 2;
        } else {
            return (examPrepItem.getList() != null && examPrepItem.getList().size() > 0) ? (examPrepItem.getList().size() + 1) : 2;
        }
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
                encryptionData.setTile_id(videodata.getPayload().getTile_id());
                encryptionData.setType(videodata.getPayload().getTile_type());
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

    @Override
    public void SuccessCallBack(JSONObject jsonobject, String apitype, String typeApi, boolean showprogress) throws JSONException {
        Gson gson = new Gson();
        switch (apitype) {
            case API.get_video_link:
                if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                    JSONObject dataJsonObject = jsonobject.getJSONObject(Const.DATA);
                    String link = dataJsonObject.optString("link");
                    String content_type = dataJsonObject.optString("content_type");
                    String token = dataJsonObject.optString("token");

                    JSONObject objectcookie = null;
                    if (jsonobject.getJSONObject("data").has("cookie") && jsonobject.getJSONObject("data").optJSONObject("cookie") != null) {
                        objectcookie = jsonobject.getJSONObject("data").optJSONObject("cookie");
                    } else {
                        objectcookie = new JSONObject("{}");
                    }


                    if (videodata != null && videodata.getVideo_type().equalsIgnoreCase("5") && videodata.getLive_status().equalsIgnoreCase("1")) {
                        if (is_audio) {
                            if (content_type.equalsIgnoreCase("2")) {
                                Helper.GoToLiveDrmVideoActivity(videodata.getVideo_type(), videodata.getChat_node(), activity, videodata.getFile_url(), videodata.getVideo_type(), videodata.getId(), videodata.getTitle(), "1", videodata.getThumbnail_url(), videodata.getPayload().getCourse_id(), videodata.getPayload().getTile_id(), videodata.getPayload().getTile_type(), videodata.getIs_chat_lock(), String.valueOf(0), SingleStudy.parentCourseId, jsonobject.toString());
                            } else {
                                Helper.GoToLiveAwsVideoActivity(videodata.getVideo_type(), videodata.getChat_node(), activity, videodata.getFile_url(), videodata.getVideo_type(), videodata.getId(), videodata.getTitle(), "1", videodata.getThumbnail_url(), videodata.getPayload().getCourse_id(), videodata.getPayload().getTile_id(), videodata.getPayload().getTile_type(), videodata.getIs_chat_lock(), String.valueOf(0), SingleStudy.parentCourseId, jsonobject.toString());
                            }
                            is_audio = false;
                        } else {
                            if (content_type.equalsIgnoreCase("2")) {
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
                                    Helper.GoToLiveDrmVideoActivity(videodata.getVideo_type(), videodata.getChat_node(), activity, videodata.getId(), videodata.getVideo_type(), videodata.getId(), videodata.getTitle(), "0", videodata.getThumbnail_url(), videodata.getPayload().getCourse_id(), videodata.getPayload().getTile_id(), videodata.getPayload().getTile_type(), videodata.getIs_chat_lock(), String.valueOf(0), SingleStudy.parentCourseId, jsonobject.toString());
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
                                    Helper.GoToLiveAwsVideoActivity(videodata.getVideo_type(), videodata.getChat_node(), activity, videodata.getId(), videodata.getVideo_type(), videodata.getId(), videodata.getTitle(), "0", videodata.getThumbnail_url(), videodata.getPayload().getCourse_id(), videodata.getPayload().getTile_id(), videodata.getPayload().getTile_type(), videodata.getIs_chat_lock(), String.valueOf(0), SingleStudy.parentCourseId, jsonobject.toString());
                            }
                        }

                    } else if (videodata != null && videodata.getVideo_type().equalsIgnoreCase("0")) {
                        if (content_type.equalsIgnoreCase("")) {
                            String audio_url = dataJsonObject.optString("audio_url");

                            long expire_time = 0;
                            if (dataJsonObject.optLong("expiry_seconds") != 0) {
                                expire_time = (Long.parseLong(jsonobject.optString("time"))) + dataJsonObject.optLong("expiry_seconds");
                            }

                            JSONArray arrJson = dataJsonObject.optJSONArray("bitrate_urls");
                            if (arrJson != null && arrJson.length() > 0) {
                                ArrayList<UrlObject> urlObjects = new ArrayList<>();
                                for (int i = 0; i < Objects.requireNonNull(arrJson).length(); i++) {
                                    JSONObject dataObj = arrJson.optJSONObject(i);
                                    UrlObject urlObject = new UrlObject();
                                    urlObject.setTitle(dataObj.optString("title"));
                                    urlObject.setUrl(dataObj.optString("url"));
                                    urlObject.setSize(dataObj.optString("size"));
                                    urlObject.setToken(token);
                                    urlObjects.add(urlObject);
                                }
                                videodata.setBitrate_urls(urlObjects);
                            }

                            if (is_download) {
                                is_download = false;
                                if (videodata.getBitrate_urls() != null && videodata.getBitrate_urls().size() > 0) {

                                    download_service(videodata, position_delete, link);
                                } else {
                                    Toast.makeText(activity, "no bitrate url found", Toast.LENGTH_SHORT).show();
                                }
                                is_audio = false;
                            } else {
                                is_download = false;
                                if (is_audio) {
                                    is_audio = false;
                                    audio_play(audio_url, videodata, objectcookie.toString());
                                } else {
                                    if (utkashRoom.getvideoDownloadao().isvideo_exit(videodata.getId(), MakeMyExam.userId)) {
                                        VideosDownload videoDownload = utkashRoom.getvideoDownloadao().getvideo_byuserid(videodata.getId(), MakeMyExam.userId);
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
                                            if (utkashRoom.getvideoDao().isvideo_exit(videodata.getId(), MakeMyExam.userId)) {
                                                if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                                                    Helper.GoToJWVideo_Params_newarrayobject(activity, link, videodata.getId(), videodata.getTitle(), utkashRoom.getvideoDao().getuser(videodata.getId(), MakeMyExam.userId).getVideo_currentpos(), singleStudy.getData().getCourseDetail().getId() + "#", videodata.getPayload().getTile_id(), videodata.getPayload().getTile_type(), videodata.getBitrate_urls(), expire_time, objectcookie.toString());
                                                } else {
                                                    Helper.GoToJWVideo_Params_newarrayobject(activity, link, videodata.getId(), videodata.getTitle(), utkashRoom.getvideoDao().getuser(videodata.getId(), MakeMyExam.userId).getVideo_currentpos(), SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId(), videodata.getPayload().getTile_id(), videodata.getPayload().getTile_type(), videodata.getBitrate_urls(), expire_time, objectcookie.toString());
                                                }
                                            } else {
                                                VideoTable videoTable = new VideoTable();
                                                videoTable.setVideo_id(videodata.getId());
                                                videoTable.setVideo_name(videodata.getTitle());
                                                videoTable.setJw_url(link);
                                                videoTable.setVideo_currentpos(0L);
                                                videoTable.setUser_id(MakeMyExam.userId);
                                                videoTable.setCourse_id(SingleStudy.parentCourseId.equalsIgnoreCase("") ? singleStudy.getData().getCourseDetail().getId() : SingleStudy.parentCourseId);

                                                utkashRoom.getvideoDao().addUser(videoTable);
                                                Helper.GoToJWVideo_Params_newarrayobject(activity, link, videodata.getId(), videodata.getTitle(), 0L, SingleStudy.parentCourseId.equalsIgnoreCase("") ? singleStudy.getData().getCourseDetail().getId() + "#" : SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId(), videodata.getPayload().getTile_id(), videodata.getPayload().getTile_type(), videodata.getBitrate_urls(), expire_time, objectcookie.toString());
                                            }
                                        }
                                    } else {
                                        if (utkashRoom.getvideoDao().isvideo_exit(videodata.getId(), MakeMyExam.userId)) {
                                            Helper.GoToJWVideo_Params_newarrayobject(activity, link, videodata.getId(), videodata.getTitle(), utkashRoom.getvideoDao().getuser(videodata.getId(), MakeMyExam.userId).getVideo_currentpos(), SingleStudy.parentCourseId.equalsIgnoreCase("") ? singleStudy.getData().getCourseDetail().getId() + "#" : SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId(), videodata.getPayload().getTile_id(), videodata.getPayload().getTile_type(), videodata.getBitrate_urls(), expire_time, objectcookie.toString());
                                        } else {
                                            VideoTable videoTable = new VideoTable();
                                            videoTable.setVideo_id(videodata.getId());
                                            videoTable.setVideo_name(videodata.getTitle());
                                            videoTable.setJw_url(link);
                                            videoTable.setVideo_currentpos(0L);
                                            videoTable.setUser_id(MakeMyExam.userId);
                                            videoTable.setCourse_id(SingleStudy.parentCourseId.equalsIgnoreCase("") ? singleStudy.getData().getCourseDetail().getId() + "#" : SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());
                                            utkashRoom.getvideoDao().addUser(videoTable);
                                            Helper.GoToJWVideo_Params_newarrayobject(activity, link, videodata.getId(), videodata.getTitle(), 0L, SingleStudy.parentCourseId.equalsIgnoreCase("") ? singleStudy.getData().getCourseDetail().getId() + "#" : SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId(), videodata.getPayload().getTile_id(), videodata.getPayload().getTile_type(), videodata.getBitrate_urls(), expire_time, objectcookie.toString());

                                        }
                                    }


                                }
                            }
                        } else if (is_audio) {
                            if (content_type.equalsIgnoreCase("2")) {
                                Helper.GoToLiveDrmVideoActivity(videodata.getVideo_type(), videodata.getChat_node(), activity, videodata.getFile_url(), videodata.getVideo_type(), videodata.getId(), videodata.getTitle(), "1", videodata.getThumbnail_url(), videodata.getPayload().getCourse_id(), videodata.getPayload().getTile_id(), videodata.getPayload().getTile_type(), videodata.getIs_chat_lock(), String.valueOf(0), SingleStudy.parentCourseId, jsonobject.toString());
                            } else {
                                Helper.GoToLiveAwsVideoActivity(videodata.getVideo_type(), videodata.getChat_node(), activity, videodata.getFile_url(), videodata.getVideo_type(), videodata.getId(), videodata.getTitle(), "1", videodata.getThumbnail_url(), videodata.getPayload().getCourse_id(), videodata.getPayload().getTile_id(), videodata.getPayload().getTile_type(), videodata.getIs_chat_lock(), String.valueOf(0), SingleStudy.parentCourseId, jsonobject.toString());
                            }
                            is_audio = false;

                        } else {
                            if (content_type.equalsIgnoreCase("2")) {
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
                                    Helper.GoToLiveDrmVideoActivity(videodata.getVideo_type(), videodata.getChat_node(), activity, videodata.getId(), videodata.getVideo_type(), videodata.getId(), videodata.getTitle(), "0", videodata.getThumbnail_url(), videodata.getPayload().getCourse_id(), videodata.getPayload().getTile_id(), videodata.getPayload().getTile_type(), videodata.getIs_chat_lock(), String.valueOf(0), SingleStudy.parentCourseId, jsonobject.toString());
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
                                    Helper.GoToLiveAwsVideoActivity(videodata.getVideo_type(), videodata.getChat_node(), activity, videodata.getId(), videodata.getVideo_type(), videodata.getId(), videodata.getTitle(), "0", videodata.getThumbnail_url(), videodata.getPayload().getCourse_id(), videodata.getPayload().getTile_id(), videodata.getPayload().getTile_type(), videodata.getIs_chat_lock(), String.valueOf(0), SingleStudy.parentCourseId, jsonobject.toString());

                            }

                        }


                    }

                    if (videodata != null && videodata.getVideo_type().equalsIgnoreCase("4")) {
                        if ((videodata.getLive_status().equalsIgnoreCase("1") || videodata.getLive_status().equalsIgnoreCase("0")) && videodata.getOpen_in_app().equalsIgnoreCase("0"))
                            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + link)));

                    } else if (videodata != null && videodata.getVideo_type().equalsIgnoreCase("1")) {
                        if (videodata.getOpen_in_app().equalsIgnoreCase("0"))
                            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + link)));

                    } else {
                        String audio_url = dataJsonObject.optString("audio_url");

                        long expire_time = 0;
                        if (dataJsonObject.optLong("expiry_seconds") != 0) {
                            expire_time = (Long.parseLong(jsonobject.optString("time"))) + dataJsonObject.optLong("expiry_seconds");
                        }

                        JSONArray arrJson = dataJsonObject.optJSONArray("bitrate_urls");
                        if (arrJson != null && arrJson.length() > 0) {
                            ArrayList<UrlObject> urlObjects = new ArrayList<>();
                            for (int i = 0; i < Objects.requireNonNull(arrJson).length(); i++) {
                                JSONObject dataObj = arrJson.optJSONObject(i);
                                UrlObject urlObject = new UrlObject();
                                urlObject.setTitle(dataObj.optString("title"));
                                urlObject.setUrl(dataObj.optString("url"));
                                urlObject.setSize(dataObj.optString("size"));
                                urlObject.setToken(token);
                                urlObjects.add(urlObject);
                            }
                            videodata.setBitrate_urls(urlObjects);
                        }

                        if (is_download) {
                            is_download = false;
                            if (videodata.getBitrate_urls() != null && videodata.getBitrate_urls().size() > 0) {

                                download_service(videodata, position_delete, link);
                            } else {
                                Toast.makeText(activity, "no bitrate url found", Toast.LENGTH_SHORT).show();
                            }
                            is_audio = false;
                        } else {
                            is_download = false;
                            if (is_audio) {
                                is_audio = false;
                                audio_play(audio_url, videodata, objectcookie.toString());
                            } else {
                                if (utkashRoom.getvideoDownloadao().isvideo_exit(videodata.getId(), MakeMyExam.userId)) {
                                    VideosDownload videoDownload = utkashRoom.getvideoDownloadao().getvideo_byuserid(videodata.getId(), MakeMyExam.userId);
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
                                        if (utkashRoom.getvideoDao().isvideo_exit(videodata.getId(), MakeMyExam.userId)) {
                                            if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                                                Helper.GoToJWVideo_Params_newarrayobject(activity, link, videodata.getId(), videodata.getTitle(), utkashRoom.getvideoDao().getuser(videodata.getId(), MakeMyExam.userId).getVideo_currentpos(), singleStudy.getData().getCourseDetail().getId() + "#", videodata.getPayload().getTile_id(), videodata.getPayload().getTile_type(), videodata.getBitrate_urls(), expire_time, objectcookie.toString());
                                            } else {
                                                Helper.GoToJWVideo_Params_newarrayobject(activity, link, videodata.getId(), videodata.getTitle(), utkashRoom.getvideoDao().getuser(videodata.getId(), MakeMyExam.userId).getVideo_currentpos(), SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId(), videodata.getPayload().getTile_id(), videodata.getPayload().getTile_type(), videodata.getBitrate_urls(), expire_time, objectcookie.toString());
                                            }
                                        } else {
                                            VideoTable videoTable = new VideoTable();
                                            videoTable.setVideo_id(videodata.getId());
                                            videoTable.setVideo_name(videodata.getTitle());
                                            videoTable.setJw_url(link);
                                            videoTable.setVideo_currentpos(0L);
                                            videoTable.setUser_id(MakeMyExam.userId);
                                            videoTable.setCourse_id(SingleStudy.parentCourseId.equalsIgnoreCase("") ? singleStudy.getData().getCourseDetail().getId() : SingleStudy.parentCourseId);

                                            utkashRoom.getvideoDao().addUser(videoTable);
                                            Helper.GoToJWVideo_Params_newarrayobject(activity, link, videodata.getId(), videodata.getTitle(), 0L, SingleStudy.parentCourseId.equalsIgnoreCase("") ? singleStudy.getData().getCourseDetail().getId() + "#" : SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId(), videodata.getPayload().getTile_id(), videodata.getPayload().getTile_type(), videodata.getBitrate_urls(), expire_time, objectcookie.toString());
                                        }
                                    }
                                } else {
                                    if (utkashRoom.getvideoDao().isvideo_exit(videodata.getId(), MakeMyExam.userId)) {
                                        Helper.GoToJWVideo_Params_newarrayobject(activity, link, videodata.getId(), videodata.getTitle(), utkashRoom.getvideoDao().getuser(videodata.getId(), MakeMyExam.userId).getVideo_currentpos(), SingleStudy.parentCourseId.equalsIgnoreCase("") ? singleStudy.getData().getCourseDetail().getId() + "#" : SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId(), videodata.getPayload().getTile_id(), videodata.getPayload().getTile_type(), videodata.getBitrate_urls(), expire_time, objectcookie.toString());
                                    } else {
                                        VideoTable videoTable = new VideoTable();
                                        videoTable.setVideo_id(videodata.getId());
                                        videoTable.setVideo_name(videodata.getTitle());
                                        videoTable.setJw_url(link);
                                        videoTable.setVideo_currentpos(0L);
                                        videoTable.setUser_id(MakeMyExam.userId);
                                        videoTable.setCourse_id(SingleStudy.parentCourseId.equalsIgnoreCase("") ? singleStudy.getData().getCourseDetail().getId() + "#" : SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());
                                        utkashRoom.getvideoDao().addUser(videoTable);
                                        Helper.GoToJWVideo_Params_newarrayobject(activity, link, videodata.getId(), videodata.getTitle(), 0L, SingleStudy.parentCourseId.equalsIgnoreCase("") ? singleStudy.getData().getCourseDetail().getId() + "#" : SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId(), videodata.getPayload().getTile_id(), videodata.getPayload().getTile_type(), videodata.getBitrate_urls(), expire_time, objectcookie.toString());

                                    }
                                }


                            }
                        }
                    }


                    is_download = false;
                    is_audio = false;
                } else if (jsonobject.optString("status").equals(Const.FALSE)) {
                    is_download = false;
                    is_download = false;
                    is_audio = false;
                    RetrofitResponse.GetApiData(activity, jsonobject.has(Const.AUTH_CODE) ? jsonobject.getString(Const.AUTH_CODE) : "", jsonobject.getString(Const.MESSAGE), false);
                }
                break;

            case API.delete_revision:
                if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                    Toast.makeText(activity, "" + jsonobject.optString(Const.MESSAGE), Toast.LENGTH_SHORT).show();

                    examPrepItem.getList().remove(position_delete);
                    if (examPrepItem.getList().size() == 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyItemRemoved(position_delete);
                        notifyDataSetChanged();
                        //notifyItemRangeChanged(position_delete, examPrepItem.getList().size()+1);
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
                            quizView.putExtra("course_id", SingleStudy.parentCourseId.equalsIgnoreCase("") ? singleStudy.getData().getCourseDetail().getId() : SingleStudy.parentCourseId);
                            quizView.putExtra(Const.TEST_SERIES_Name, quiz_name);
                            SharedPreference.getInstance().putString("test_series", jsonobject.toString());
                            quizView.putExtra("TOTAL_QUESTIONS", totalQuestion);
                            quizView.putExtra("first_attempt", first_attempt);
                            quizView.putExtra(Const.LANG, lang);
                            quizView.putExtra("result_date", result_date);
                            quizView.putExtra("test_submission", submission_type);
                            quizView.putExtra("time", time);
                            quizView.putExtra("enddate", videodata.getEnd_date());
                            Helper.gotoActivity(quizView, activity);
                        } else if (testseriesBase.getData().getQuestionsHindi() != null && testseriesBase.getData().getQuestionsHindi().size() > 0 && lang == 2) {

                            testseriesBase.getData().setQuestions(testseriesBase.getData().getQuestionsHindi());

                            Intent quizView = new Intent(activity, TestBaseActivity.class);
                            quizView.putExtra(Const.STATUS, false);
                            quizView.putExtra(Const.TEST_SERIES_ID, quiz_id);
                            quizView.putExtra("course_id", SingleStudy.parentCourseId.equalsIgnoreCase("") ? singleStudy.getData().getCourseDetail().getId() : SingleStudy.parentCourseId);
                            quizView.putExtra(Const.TEST_SERIES_Name, quiz_name);
                            //quizView.putExtra(Const.TESTSERIES, testseriesBase);
                            SharedPreference.getInstance().putString("test_series", jsonobject.toString());

                            quizView.putExtra("TOTAL_QUESTIONS", totalQuestion);
                            quizView.putExtra("first_attempt", first_attempt);
                            quizView.putExtra(Const.LANG, lang);
                            quizView.putExtra("result_date", result_date);
                            quizView.putExtra("test_submission", submission_type);
                            quizView.putExtra("time", time);
                            quizView.putExtra("enddate", videodata.getEnd_date());


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

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {

    }

    @Override
    public void getVideourl(int itempos, UrlObject urlObject, int pos, VideosDownload videosDownload) {
        if (!utkashRoom.getOpenHelper().getWritableDatabase().isDbLockedByCurrentThread()) {
            if (Helper.isNetworkConnected(activity)) {
                examPrepItem.getList().get(pos - 1).setVideo_status("Download Running");
                if (utkashRoom.getvideoDownloadao().isvideo_exit(videosDownload.getVideo_id(), MakeMyExam.userId)) {
                    utkashRoom.getvideoDownloadao().update_videostatus(videosDownload.getVideo_id(), videosDownload.getVideo_status(), MakeMyExam.userId);
                } else {
                    utkashRoom.getvideoDownloadao().addUser(videosDownload);
                }
                notifyItemChanged(pos);
                Download_dialog(urlObject.getUrl(), videosDownload);
                dismissCalculatorDialog(watchlist);
            }

        }
    }

    public interface onButtonClicked {
        void onTitleClicked(TilesItem cards, List<TilesItem> tiles, int tilePos);
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
        quizQuesNumTv = (TextView) v.findViewById(R.id.numQuesValueTV);
        quizsectionValueTV = (TextView) v.findViewById(R.id.sectionValueTV);
        languageSpinnerTV = (TextView) v.findViewById(R.id.languageSpinnerTV);
        quizTimeTV = (TextView) v.findViewById(R.id.quizTimeValueTV);
        reAttempt = (TextView) v.findViewById(R.id.remarksTV);
        check_box = (CheckBox) v.findViewById(R.id.check_box);
        generalInstrValueTV = (TextView) v.findViewById(R.id.generalInstrValueTV);
        startQuiz = (Button) v.findViewById(R.id.startQuizBtn);
        sectionListLL = (LinearLayout) v.findViewById(R.id.sectionListLL);
        section_time = (LinearLayout) v.findViewById(R.id.section_time);
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
        // quizQuesNumTv.setText(testBasicInst.getTotalQuestions());
        quizTimeTV.setText(testBasicInst.getTimeInMins());
        // quizTotalMarks.setText(testBasicInst.getTotalMarks());
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
                        NetworkCall networkCall = new NetworkCall(SingleStudyAdapter.this, activity);

                        networkCall.NetworkAPICall(API.API_GET_INFO_TEST_SERIES, "", true, false);
                    } else {
                        Toast.makeText(activity, "Please check following instructions.", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    public class FaqListRecyclerAdapter extends RecyclerView.Adapter<FaqListRecyclerAdapter.FaqListHolder> {
        int positions;
        ArrayList<FaqData> faqData;

        public FaqListRecyclerAdapter(ArrayList<FaqData> faqData, int position) {
            this.positions = position;
            this.faqData = faqData;
        }

        @NonNull
        @Override
        public FaqListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_faq_data, parent, false);
            return new FaqListHolder(view);
        }

        @Override
        public int getItemCount() {
            return faqData.size();
        }

        @Override
        public void onBindViewHolder(@NonNull FaqListHolder holder, int position) {
            holder.setSingleFAQData(faqData.get(position).getQuestion(), position, faqData.get(position).getDescription());
        }

        public class FaqListHolder extends RecyclerView.ViewHolder {
            private TextView answertextTV, questiontextTV;
            private ImageView dropDownIV;
            private LinearLayout mainLL, parentLL;

            public FaqListHolder(View itemView) {
                super(itemView);
                questiontextTV = (TextView) itemView.findViewById(R.id.questiontextTV);
                dropDownIV = (ImageView) itemView.findViewById(R.id.dropDownIV);
                answertextTV = (TextView) itemView.findViewById(R.id.answertextTV);
                mainLL = itemView.findViewById(R.id.lowerViewItem);
                parentLL = itemView.findViewById(R.id.parentLL);
            }

            public void setSingleFAQData(String singlefaqdata, int pos, String answersData) {

                parentLL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mainLL.getVisibility() == View.GONE) {
                            mainLL.setVisibility(View.VISIBLE);
                            dropDownIV.setImageResource(R.mipmap.up_black);
                        } else {
                            mainLL.setVisibility(View.GONE);
                            dropDownIV.setImageResource(R.mipmap.down_black);
                        }
                    }
                });
                questiontextTV.setText(singlefaqdata);
                answertextTV.setText(answersData);
            }
        }
    }


    public class FAQViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        RelativeLayout no_data_found_RL;
        Button backBtn;

        FAQViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.faqRV);
            no_data_found_RL = itemView.findViewById(R.id.no_data_found_RL);
            backBtn = itemView.findViewById(R.id.backBtn);
        }

        public void setData(ArrayList<FaqData> faqData) {
            if (faqData != null && faqData.size() > 0) {
                no_data_found_RL.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(new FaqListRecyclerAdapter(faqData, 0));
                recyclerView.setNestedScrollingEnabled(false);
            } else {
                no_data_found_RL.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);

                backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.finish();
                    }
                });
            }
        }

    }

    public class OverViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        RelativeLayout no_data_found_RL;
        Button backBtn;

        OverViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.overviewRV);
            no_data_found_RL = itemView.findViewById(R.id.no_data_found_RL);
            backBtn = itemView.findViewById(R.id.backBtn);
        }

        public void setData(CourseDetailData basic, OverviewData overview) {
            if (overview != null) {
                recyclerView.setVisibility(View.VISIBLE);
                no_data_found_RL.setVisibility(View.GONE);

                if (isLodded) {
                    boolean isBoth = false;
                    boolean isHindi = false;
                    if (overview.getData().getVisibility().equalsIgnoreCase("1")) {
                        isBoth = false;
                        isHindi = false;
                    } else if (overview.getData().getVisibility().equalsIgnoreCase("2")) {
                        isBoth = false;
                        isHindi = true;
                    } else {
                        isBoth = true;
                        isHindi = false;
                    }
                    OverviewRVAdapter overviewRVAdapter = new OverviewRVAdapter(activity, basic, overview, recyclerView, isHindi, isBoth);
                    recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(overviewRVAdapter);
                    isLodded = false;
                }
            } else {
                recyclerView.setVisibility(View.GONE);
                no_data_found_RL.setVisibility(View.VISIBLE);

                backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.finish();
                    }
                });
            }
        }

    }

    public class SingleStudyHeaderHolder extends RecyclerView.ViewHolder {
        RecyclerView tileRv;
        ImageView coursebg;
        LinearLayout imageRL;
        RelativeLayout headerLL;
        TextView course_name, authorname, validityTV, type, courseid;

        public SingleStudyHeaderHolder(View itemView) {
            super(itemView);
            headerLL = itemView.findViewById(R.id.headerLL);
            tileRv = itemView.findViewById(R.id.tileRv);
            imageRL = itemView.findViewById(R.id.imageRL);
            coursebg = itemView.findViewById(R.id.courseImagebg);
            course_name = itemView.findViewById(R.id.course_name);
            authorname = itemView.findViewById(R.id.authorname);
            courseid = itemView.findViewById(R.id.courseid);
            type = itemView.findViewById(R.id.type);
            validityTV = itemView.findViewById(R.id.validityTV);
        }


        public void setDataHeader(CourseDetail singleStudy) {
            if (singleStudy.getData().getCourseDetail().getIsPurchased().equalsIgnoreCase("1")) {
                headerLL.setBackgroundColor(activity.getResources().getColor(R.color.white));
                imageRL.setVisibility(View.GONE);
            } else {
                headerLL.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimaryDark));
                imageRL.setVisibility(View.VISIBLE);

                if (!TextUtils.isEmpty(singleStudy.getData().getCourseDetail().getDescHeaderImage())) {
                    Helper.setThumbnailImage(activity, singleStudy.getData().getCourseDetail().getDescHeaderImage(), activity.getResources().getDrawable(R.drawable.square_thumbnail), coursebg);
                } else {
                    coursebg.setImageResource(R.mipmap.book_placeholder);
                }

                if (singleStudy.getData().getCourseDetail() != null) {
                    course_name.setText(singleStudy.getData().getCourseDetail().getTitle());
                    authorname.setText("By " + singleStudy.getData().getCourseDetail().getAuthor().getTitle());
                    validityTV.setText("Validity : " + singleStudy.getData().getCourseDetail().getValidity());
                    courseid.setText(singleStudy.getData().getCourseDetail().getId());
                    if (singleStudy.getData().getCourseDetail().getViewType().equalsIgnoreCase("0"))
                        type.setText("online");
                    else if (singleStudy.getData().getCourseDetail().getViewType().equalsIgnoreCase("1")) {
                        type.setText("offline");
                    } else if (singleStudy.getData().getCourseDetail().getViewType().equalsIgnoreCase("2")) {
                        type.setText("Package");
                    }
                }
            }

            ArrayList<TilesItem> cardsArrayList = new ArrayList<>();
            if (singleStudy.getData().getCourseDetail().getIsPurchased().equalsIgnoreCase("1")) {
                for (TilesItem tilesItem : singleStudy.getData().getTiles()) {
                    if (tilesItem.getType().equalsIgnoreCase(Const.OVERVIEW) || tilesItem.getType().equalsIgnoreCase(Const.FAQ) ||
                            tilesItem.getType().equalsIgnoreCase(Const.CONTENT) || tilesItem.getType().equalsIgnoreCase(Const.COMBO)) {
                        cardsArrayList.add(tilesItem);
                    }
                }
            } else {
                for (TilesItem tilesItem : singleStudy.getData().getTiles()) {
                    cardsArrayList.add(tilesItem);
                }
            }

            TileItemsAdapter tileItemsAdapter = new TileItemsAdapter(activity, cardsArrayList);
            tileRv.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManagerWithSmoothScroller.HORIZONTAL, false));
            tileRv.setAdapter(tileItemsAdapter);
            tileRv.setNestedScrollingEnabled(false);
            tileRv.scrollToPosition(tilePos);
        }

        public class TileItemsAdapter extends RecyclerView.Adapter<TileItemsAdapter.MyViewHolder> {

            private List<TilesItem> cards;
            private Context context;

            public TileItemsAdapter(Context context, ArrayList<TilesItem> cards) {
                this.cards = cards;
                this.context = context;
            }

            @Override
            public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
                final TilesItem card = cards.get(position);

                holder.tilesText.setText(card.getTileName());

                if (contentType.equals(card.getType() + card.getId())) {
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setColor(Color.parseColor("#000000"));
                    gradientDrawable.setCornerRadius(60);
                    holder.parent.setBackground(gradientDrawable);
                    holder.tilesText.setTextColor(Color.WHITE);
                } else {
                    holder.parent.setBackground(activity.getResources().getDrawable(R.drawable.bg_tile_unselected));
                    holder.tilesText.setTextColor(context.getResources().getColor(R.color.black));
                }

                holder.parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        contentType = card.getType() + card.getId();
                        tilePos = position;
                        buttonClicked.onTitleClicked(card, singleStudy.getData().getTiles(), tilePos);
                    }
                });
            }

            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.single_item_tiles, parent, false);
                return new MyViewHolder(itemView);
            }

            public class MyViewHolder extends RecyclerView.ViewHolder {
                public LinearLayout parent;
                public LinearLayout tour_ll;
                public TextView tilesText;

                public MyViewHolder(View view) {
                    super(view);
                    tilesText = (TextView) view.findViewById(R.id.tilesTextTv);
                    parent = (LinearLayout) view.findViewById(R.id.parentBottom);
                    tour_ll = (LinearLayout) view.findViewById(R.id.tour_ll);

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(6, 0, 6, 0);
                    parent.setLayoutParams(layoutParams);
                    tilesText.setPadding(Math.round(convertDpToPixel(16, context)), Math.round(convertDpToPixel(8, context)), Math.round(convertDpToPixel(16, context)), Math.round(convertDpToPixel(8, context)));
                }
            }

            @Override
            public int getItemCount() {
                return cards.size();
            }

        }
    }

    public class SingleStudyComboListHolder extends RecyclerView.ViewHolder {
        ImageView videoImage, eye;
        TextView titleTV, descriptionTV;
        RelativeLayout lockRL;
        RelativeLayout no_data_found_RL;
        Button backBtn;
        LinearLayout tileRL;
        CardView ibt_single_sub_vd_RL;

        public SingleStudyComboListHolder(View itemView) {
            super(itemView);
            lockRL = itemView.findViewById(R.id.lockRL);
            videoImage = itemView.findViewById(R.id.ibt_single_vd_iv);
            titleTV = itemView.findViewById(R.id.ibt_current_affair_title);
            eye = itemView.findViewById(R.id.open_eye);
            descriptionTV = itemView.findViewById(R.id.ibt_single_vd_tv_day);
            descriptionTV.setSelected(true);
            tileRL = itemView.findViewById(R.id.currentAffairRL);
            no_data_found_RL = itemView.findViewById(R.id.no_data_found_RL);
            backBtn = itemView.findViewById(R.id.backBtn);
            ibt_single_sub_vd_RL = itemView.findViewById(R.id.ibt_single_sub_vd_RL);
        }

        public void setData(final ArrayList<Courselist> courses, final int position) {

            if (courses != null && courses.size() > 0) {


                ibt_single_sub_vd_RL.setVisibility(View.VISIBLE);
                no_data_found_RL.setVisibility(View.GONE);

                Courselist course = courses.get(position - 1);


                titleTV.setText(course.getTitle());
                if (!TextUtils.isEmpty(course.getSegment_information())) {
                    descriptionTV.setVisibility(View.VISIBLE);
                    descriptionTV.setText(course.getSegment_information());
                } else {
                    descriptionTV.setVisibility(View.GONE);
                }
                Helper.setThumbnailImage(activity, course.getCover_image(), activity.getResources().getDrawable(R.mipmap.book_placeholder), videoImage);

                if (TextUtils.isEmpty(course.getIs_locked())) {
                    course.setIs_locked("0");
                }
                if (singleStudy != null && singleStudy.getData().getCourseDetail() != null) {
                    if (singleStudy.getData().getCourseDetail().getIsPurchased().equals("1")) {
                        course.setIs_locked("0");
                    }
                }

                if (course.getIs_locked().equals("0")) {
                    eye.setVisibility(View.GONE);
                    lockRL.setVisibility(View.GONE);
                } else {
                    eye.setVisibility(View.GONE);
                    lockRL.setVisibility(View.VISIBLE);
                }


                tileRL.setOnClickListener(v -> {
                    if (course.getIs_locked().equalsIgnoreCase("1")) {

                        if (study != null) {
                            study.buynow();
                        }
                      /*  Intent intent = new Intent(activity, PurchaseActivity.class);
                        intent.putExtra("single_study", new Gson().toJson(singleStudy));
                        if (study!=null)
                            intent.putExtra("notes_type",  new Gson().toJson(study.notesType));

                        Helper.gotoActivity(intent, activity);*/
                        return;
                    }

                    if (TextUtils.isEmpty(course.getMaintenanceText())) {
                        Intent courseList = new Intent(activity, CourseActivity.class);//FRAG_TYPE, Const.SINGLE_COURSE AllCoursesAdapter
                        courseList.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                        courseList.putExtra(Const.COURSE_ID_MAIN, course.getId());
                        courseList.putExtra(Const.COURSE_PARENT_ID, singleStudy.getData().getCourseDetail().getId());
                        courseList.putExtra(Const.IS_COMBO, true);
                        courseList.putExtra("valid_to", singleStudy.getData().getCourseDetail().getValid_to());
                        courseList.putExtra("course_name", titleTV.getText().toString());
                        Helper.gotoActivity(courseList, activity);
                    } else {
                        Helper.getCourseMaintanaceDialog(activity, "", course.getMaintenanceText());
                    }

                });
            } else {
                ibt_single_sub_vd_RL.setVisibility(View.GONE);
                no_data_found_RL.setVisibility(View.VISIBLE);

                backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.finish();
                    }
                });
            }
        }
    }

    public class SingleStudyTestListHolder extends RecyclerView.ViewHolder {
        RelativeLayout parentLL, studyitemLL, lockRL;
        ImageView imageIcon;
        TextView titleCategory;
        TextView subItemRV, share;
        LinearLayout attemptLL;
        RelativeLayout no_data_found_RL;
        Button backBtn;
        CardView ibt_single_sub_vd_RL;
        TextView learn, practice;

        public SingleStudyTestListHolder(View itemView) {
            super(itemView);
            lockRL = itemView.findViewById(R.id.lockRL);
            parentLL = itemView.findViewById(R.id.parentLL);
            studyitemLL = itemView.findViewById(R.id.study_single_itemLL);
            imageIcon = itemView.findViewById(R.id.profileImage);
            titleCategory = itemView.findViewById(R.id.examPrepTitleTV);
            subItemRV = itemView.findViewById(R.id.subItemRV);
            attemptLL = itemView.findViewById(R.id.attemptLL);
            no_data_found_RL = itemView.findViewById(R.id.no_data_found_RL);
            backBtn = itemView.findViewById(R.id.backBtn);
            ibt_single_sub_vd_RL = itemView.findViewById(R.id.ibt_single_sub_vd_RL);
            learn = itemView.findViewById(R.id.learn);
            share = itemView.findViewById(R.id.share);
            practice = itemView.findViewById(R.id.practice);
        }

        public void setData(final ArrayList<Lists> list, final int position) {
            if (list != null && list.size() > 0) {
                ibt_single_sub_vd_RL.setVisibility(View.VISIBLE);
                no_data_found_RL.setVisibility(View.GONE);

                if (TextUtils.isEmpty(list.get(position - 1).getIs_locked())) {
                    list.get(position - 1).setIs_locked("0");
                }
                if (singleStudy != null && singleStudy.getData().getCourseDetail() != null) {
                    if (singleStudy.getData().getCourseDetail().getIsPurchased().equals("1")) {
                        list.get(position - 1).setIs_locked("0");
                    }
                }
                if (list.get(position - 1).getIs_locked().equals("0")) {
                    lockRL.setVisibility(View.GONE);
                } else {
                    lockRL.setVisibility(View.VISIBLE);
                }

                if (isSkip.equalsIgnoreCase("3")) {
                    share.setVisibility(View.VISIBLE);
                    titleCategory.setText(list.get(position - 1).getTest_series_name());
                    if (list.get(position - 1).getEnd_date().equalsIgnoreCase("") && list.get(position - 1).getStart_date().equalsIgnoreCase("") || list.get(position - 1).getEnd_date().equalsIgnoreCase("0") && list.get(position - 1).getStart_date().equalsIgnoreCase("0")) {
                        subItemRV.setVisibility(View.GONE);
                    } else if (!list.get(position - 1).getEnd_date().equalsIgnoreCase("0") && !list.get(position - 1).getEnd_date().equalsIgnoreCase("")) {
                        subItemRV.setVisibility(View.VISIBLE);
                        String edateString = new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(Long.parseLong(list.get(position - 1).getEnd_date()) * 1000));
                        subItemRV.setText("Test End: " + Helper.changeAMPM(edateString));
                    } else {
                        subItemRV.setVisibility(View.GONE);
                    }

                    attemptLL.setVisibility(View.VISIBLE);


                    if (list.get(position - 1).getState().equals("1")) {

                        utkashRoom.getTestDao().delete_test_data(MakeMyExam.userId, list.get(position - 1).getId());

                        if (list.get(position - 1).getStart_date().equalsIgnoreCase("") || list.get(position - 1).getStart_date().equalsIgnoreCase("0") && list.get(position - 1).getEnd_date().equalsIgnoreCase("") || list.get(position - 1).getEnd_date().equalsIgnoreCase("0")) {
                            learn.setVisibility(View.VISIBLE);
                            practice.setVisibility(View.VISIBLE);

                            attemptLL.getChildAt(0).setVisibility(View.VISIBLE);
                            ((TextView) attemptLL.getChildAt(0)).setText(activity.getResources().getString(R.string.view_rank));
                        } else if (server_time >= Long.parseLong(list.get(position - 1).getEnd_date()) * 1000) {
                            if (list.get(position - 1).getResult_date().equalsIgnoreCase("0") || list.get(position - 1).getResult_date().equalsIgnoreCase("1") || list.get(position - 1).getResult_date().equalsIgnoreCase("")) {
                                attemptLL.setVisibility(View.GONE);
                                learn.setVisibility(View.VISIBLE);
                                practice.setVisibility(View.VISIBLE);
                            } else {
                                if (server_time > Long.parseLong(list.get(position - 1).getResult_date()) * 1000) {
                                    if (Long.parseLong(list.get(position - 1).getEnd_date()) < 1640066737) {
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
                            if (!list.get(position - 1).getIs_reattempt().equalsIgnoreCase("0")) {
                                learn.setVisibility(View.GONE);
                                practice.setVisibility(View.GONE);
                                attemptLL.setVisibility(View.VISIBLE);
                                ((TextView) attemptLL.getChildAt(0)).setText("RE-ATTEMPT");
                            } else {
                                if (list.get(position - 1).getResult_date() == null || list.get(position - 1).getResult_date().equalsIgnoreCase("1") || list.get(position - 1).getResult_date().equalsIgnoreCase("0") || list.get(position - 1).getResult_date().equalsIgnoreCase("")) {
                                    learn.setVisibility(View.GONE);
                                    practice.setVisibility(View.GONE);
                                    attemptLL.getChildAt(0).setVisibility(View.VISIBLE);
                                    ((TextView) attemptLL.getChildAt(0)).setText(activity.getResources().getString(R.string.view_rank));
                                } else {
                                    learn.setVisibility(View.GONE);
                                    practice.setVisibility(View.GONE);
                                    attemptLL.getChildAt(0).setVisibility(View.VISIBLE);
                                    ((TextView) attemptLL.getChildAt(0)).setText(activity.getResources().getString(R.string.attempted));
                                }
                            }
                        }
                    } else {
                        if (list.get(position - 1).getStart_date().equalsIgnoreCase("") || list.get(position - 1).getStart_date().equalsIgnoreCase("0") && list.get(position - 1).getEnd_date().equalsIgnoreCase("") || list.get(position - 1).getEnd_date().equalsIgnoreCase("0")) {

                            TestTable test_data = utkashRoom.getTestDao().test_data(list.get(position - 1).getId(), MakeMyExam.userId);
                            if (test_data != null && test_data.getStatus() != null && !test_data.getStatus().equalsIgnoreCase("")) {
                                attemptLL.setVisibility(View.VISIBLE);
                                attemptLL.getChildAt(0).setVisibility(View.VISIBLE);

                                if (!list.get(position - 1).getIs_reattempt().equalsIgnoreCase("0")) {
                                    ((TextView) attemptLL.getChildAt(0)).setText("RE-ATTEMPT");

                                } else {
                                    ((TextView) attemptLL.getChildAt(0)).setText(test_data.getStatus());
                                }
                                learn.setVisibility(View.GONE);
                                practice.setVisibility(View.GONE);

                            } else {
                                attemptLL.getChildAt(0).setVisibility(View.VISIBLE);
                                ((TextView) attemptLL.getChildAt(0)).setText(activity.getResources().getString(R.string.attempt_now));
                                learn.setVisibility(View.GONE);
                                practice.setVisibility(View.GONE);
                            }

                        } else if (server_time >= Long.parseLong(list.get(position - 1).getEnd_date()) * 1000) {
                            if (list.get(position - 1).getResult_date().equalsIgnoreCase("0") || list.get(position - 1).getResult_date().equalsIgnoreCase("1") || list.get(position - 1).getResult_date().equalsIgnoreCase("")) {
                                attemptLL.setVisibility(View.GONE);
                                learn.setVisibility(View.VISIBLE);
                                practice.setVisibility(View.VISIBLE);
                            }
                            if (Long.parseLong(list.get(position - 1).getResult_date()) * 1000 > server_time) {
                                attemptLL.setVisibility(View.GONE);
                                learn.setVisibility(View.VISIBLE);
                                practice.setVisibility(View.VISIBLE);
                            } else if (server_time > Long.parseLong(list.get(position - 1).getResult_date()) * 1000) {
                                if (Long.parseLong(list.get(position - 1).getEnd_date()) < 1640066737) {
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
                        } else if (server_time < Long.parseLong(list.get(position - 1).getStart_date()) * 1000) {
                            attemptLL.getChildAt(0).setVisibility(View.VISIBLE);
                            ((TextView) attemptLL.getChildAt(0)).setText("Test is Upcoming");
                            learn.setVisibility(View.GONE);
                            practice.setVisibility(View.GONE);
                        } else {
//                            attemptLL.getChildAt(0).setVisibility(View.VISIBLE);
//                            ((TextView) attemptLL.getChildAt(0)).setText(activity.getResources().getString(R.string.attempt_now));
//                            learn.setVisibility(View.GONE);
//                            practice.setVisibility(View.GONE);

                            TestTable test_data = utkashRoom.getTestDao().test_data(list.get(position - 1).getId(), MakeMyExam.userId);
                            if (test_data != null && test_data.getStatus() != null && !test_data.getStatus().equalsIgnoreCase("")) {
                                attemptLL.getChildAt(0).setVisibility(View.VISIBLE);
                                ((TextView) attemptLL.getChildAt(0)).setText(test_data.getStatus());
                                learn.setVisibility(View.GONE);
                                practice.setVisibility(View.GONE);

                            } else
                                attemptLL.getChildAt(0).setVisibility(View.VISIBLE);
                            ((TextView) attemptLL.getChildAt(0)).setText(activity.getResources().getString(R.string.attempt_now));
                            learn.setVisibility(View.GONE);
                            practice.setVisibility(View.GONE);
                        }

                    }

                    if (!TextUtils.isEmpty(list.get(position - 1).getThumbnail_url())) {
                        Helper.setThumbnailImage(activity, list.get(position - 1).getThumbnail_url(), activity.getResources().getDrawable(R.drawable.square_thumbnail), imageIcon);
                    } else {
                        imageIcon.setImageResource(R.drawable.square_thumbnail);
                    }

                } else {
                    share.setVisibility(View.GONE);

                    subItemRV.setVisibility(View.VISIBLE);
                    attemptLL.setVisibility(View.GONE);
                    learn.setVisibility(View.GONE);
                    practice.setVisibility(View.GONE);
                    if (!TextUtils.isEmpty(list.get(position - 1).getImage_icon())) {
                        Helper.setThumbnailImage(activity, list.get(position - 1).getImage_icon(), activity.getResources().getDrawable(R.drawable.square_thumbnail), imageIcon);
                    } else {
                        imageIcon.setImageResource(R.drawable.square_thumbnail);
                    }
                    subItemRV.setVisibility(View.GONE);
                    subItemRV.setText("Total : " + list.get(position - 1).getCount());
                    titleCategory.setText(list.get(position - 1).getTitle());
                }

                if (isSkip.equalsIgnoreCase("3")) {
                    practice.setOnClickListener(v -> {
                        if (!Helper.isConnected(activity)) {
                            Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (is_purchase.equalsIgnoreCase("1")) {
                            SharedPreference.getInstance().putString(Const.ID, singleStudy.getData().getCourseDetail().getId());
                            quiz_id = list.get(position - 1).getId();
                            quiz_name = list.get(position - 1).getTest_series_name();
                            totalQuestion = list.get(position - 1).getTotal_questions();
                            result_date = list.get(position - 1).getResult_date();
                            submission_type = list.get(position - 1).getSubmission_type();
                            first_attempt = "0";
                            videodata = list.get(position - 1);
                            startTestAPI();
                        } else {
                            if (list.get(position - 1).getIs_locked().equalsIgnoreCase("1")) {

                                if (study != null) {
                                    study.buynow();
                                }                              /*  Intent intent = new Intent(activity, PurchaseActivity.class);

                                intent.putExtra("single_study", new Gson().toJson(singleStudy));
                                Helper.gotoActivity(intent, activity);*/
                            } else {
                                SharedPreference.getInstance().putString(Const.ID, singleStudy.getData().getCourseDetail().getId());
                                quiz_id = list.get(position - 1).getId();
                                quiz_name = list.get(position - 1).getTest_series_name();
                                totalQuestion = list.get(position - 1).getTotal_questions();
                                first_attempt = "0";
                                result_date = list.get(position - 1).getResult_date();
                                videodata = list.get(position - 1);
                                submission_type = list.get(position - 1).getSubmission_type();
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
                            if (list.get(position - 1).getState().equalsIgnoreCase("1")) {
                                quiz_id = list.get(position - 1).getId();
                                quiz_name = list.get(position - 1).getTest_series_name();
                                totalQuestion = list.get(position - 1).getTotal_questions();
                                result_without_submit(quiz_id, singleStudy.getData().getCourseDetail().getId(), "1", quiz_name);
                            } else {
                                quiz_id = list.get(position - 1).getId();
                                quiz_name = list.get(position - 1).getTest_series_name();
                                totalQuestion = list.get(position - 1).getTotal_questions();
                                result_without_submit(quiz_id, singleStudy.getData().getCourseDetail().getId(), "0", quiz_name);

                            }
                        } else {
                            if (list.get(position - 1).getIs_locked().equalsIgnoreCase("1")) {

                                if (study != null) {
                                    study.buynow();
                                }
                                /*Intent intent = new Intent(activity, PurchaseActivity.class);
                                intent.putExtra("single_study", new Gson().toJson(singleStudy));
                                Helper.gotoActivity(intent, activity);*/
                            } else {
                                SharedPreference.getInstance().putString(Const.ID, singleStudy.getData().getCourseDetail().getId());
                                if (list.get(position - 1).getState().equalsIgnoreCase("1")) {
                                    quiz_id = list.get(position - 1).getId();
                                    quiz_name = list.get(position - 1).getTest_series_name();
                                    totalQuestion = list.get(position - 1).getTotal_questions();
                                    result_without_submit(quiz_id, singleStudy.getData().getCourseDetail().getId(), "1", quiz_name);
                                } else {
                                    quiz_id = list.get(position - 1).getId();
                                    quiz_name = list.get(position - 1).getTest_series_name();
                                    totalQuestion = list.get(position - 1).getTotal_questions();
                                    result_without_submit(quiz_id, singleStudy.getData().getCourseDetail().getId(), "0", quiz_name);

                                }
                            }
                        }
                    });
                    attemptLL.setOnClickListener(v -> {
                        if (!Helper.isConnected(activity)) {
                            Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (is_purchase.equalsIgnoreCase("1")) {
                            SharedPreference.getInstance().putString(Const.ID, singleStudy.getData().getCourseDetail().getId());

                            if (((TextView) attemptLL.getChildAt(0)).getText().toString().equalsIgnoreCase("RE-ATTEMPT")) {
                                quiz_id = list.get(position - 1).getId();
                                quiz_name = list.get(position - 1).getTest_series_name();
                                totalQuestion = list.get(position - 1).getTotal_questions();
                                result_date = list.get(position - 1).getResult_date();
                                submission_type = list.get(position - 1).getSubmission_type();
                                first_attempt = "0";
                                videodata = list.get(position - 1);
                                startTestAPI();
                            } else if (((TextView) attemptLL.getChildAt(0)).getText().toString().equalsIgnoreCase("Attempt Now")) {
                                quiz_id = list.get(position - 1).getId();
                                quiz_name = list.get(position - 1).getTest_series_name();
                                totalQuestion = list.get(position - 1).getTotal_questions();
                                first_attempt = "1";
                                result_date = list.get(position - 1).getResult_date();
                                submission_type = list.get(position - 1).getSubmission_type();
                                videodata = list.get(position - 1);
                                startTestAPI();
                            } else if (((TextView) attemptLL.getChildAt(0)).getText().toString().equalsIgnoreCase("ATTEMPTED")) {
                                if (list.get(position - 1).getResult_date() != null && !list.get(position - 1).getResult_date().equalsIgnoreCase("0") && !list.get(position - 1).getResult_date().equalsIgnoreCase("1") && !list.get(position - 1).getResult_date().equalsIgnoreCase("")) {
                                    Snackbar.make(v, "Your Result will be declare on " + new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(Long.parseLong(list.get(position - 1).getResult_date()) * 1000)), Snackbar.LENGTH_SHORT).show();

                                } else if (utkashRoom.getTestDao().is_test_exit(list.get(position - 1).getId(), MakeMyExam.userId)) {
                                    Snackbar.make(v, "Your Result is Getting Ready Please refresh your page..", Snackbar.LENGTH_SHORT).show();
                                } else {
                                    Snackbar.make(v, "Your Test has been alredy submiited", Snackbar.LENGTH_SHORT).show();
                                }

                            } else if (((TextView) attemptLL.getChildAt(0)).getText().toString().equalsIgnoreCase(activity.getResources().getString(R.string.view_rank))) {
                                quiz_id = list.get(position - 1).getId();
                                quiz_name = list.get(position - 1).getTest_series_name();
                                totalQuestion = list.get(position - 1).getTotal_questions();
                                if (list.get(position - 1).getState().equalsIgnoreCase("1")) {

                                    if (list.get(position - 1).getResult_date() == null || list.get(position - 1).getResult_date().equalsIgnoreCase("") || list.get(position - 1).getResult_date().equalsIgnoreCase("0") || list.get(position - 1).getResult_date().equalsIgnoreCase("1")) {
                                        Intent resultScreen = new Intent(activity, QuizActivity.class);
                                        resultScreen.putExtra(Const.FRAG_TYPE, Const.RESULT_SCREEN);
                                        resultScreen.putExtra(Const.STATUS, quiz_id);
                                        resultScreen.putExtra(Const.NAME, quiz_name);
                                        //   resultScreen.putExtra("show_leader", "0");
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
//                                    Intent resultScreen = new Intent(activity, QuizActivity.class);
//                                    resultScreen.putExtra(Const.FRAG_TYPE, Const.RESULT_SCREEN);
//                                    resultScreen.putExtra(Const.STATUS, quiz_id);
//                                    resultScreen.putExtra(Const.NAME, quiz_name);
//                                    resultScreen.putExtra("first_attempt", "1");
//                                    activity.startActivity(resultScreen);

                                } else {
                                    Intent resultScreen = new Intent(activity, QuizActivity.class);
                                    resultScreen.putExtra(Const.FRAG_TYPE, "leader_board");
                                    resultScreen.putExtra(Const.STATUS, quiz_id);
                                    resultScreen.putExtra(Const.NAME, quiz_name);
                                    activity.startActivity(resultScreen);
                                }

                            }
                        } else {
                            if (list.get(position - 1).getIs_locked().equalsIgnoreCase("1")) {

                                if (study != null) {
                                    study.buynow();
                                }

//                                Intent intent = new Intent(activity, PurchaseActivity.class);
//                                intent.putExtra("single_study", new Gson().toJson(singleStudy));
//                                Helper.gotoActivity(intent, activity);
                            } else {
                                SharedPreference.getInstance().putString(Const.ID, singleStudy.getData().getCourseDetail().getId());

                                if (((TextView) attemptLL.getChildAt(0)).getText().toString().equalsIgnoreCase("RE-ATTEMPT")) {
                                    quiz_id = list.get(position - 1).getId();
                                    quiz_name = list.get(position - 1).getTest_series_name();
                                    totalQuestion = list.get(position - 1).getTotal_questions();
                                    first_attempt = "0";
                                    result_date = list.get(position - 1).getResult_date();
                                    submission_type = list.get(position - 1).getSubmission_type();
                                    videodata = list.get(position - 1);
                                    startTestAPI();
                                } else if (((TextView) attemptLL.getChildAt(0)).getText().toString().equalsIgnoreCase("Attempt Now")) {
                                    quiz_id = list.get(position - 1).getId();
                                    quiz_name = list.get(position - 1).getTest_series_name();
                                    totalQuestion = list.get(position - 1).getTotal_questions();
                                    first_attempt = "1";
                                    result_date = list.get(position - 1).getResult_date();
                                    submission_type = list.get(position - 1).getSubmission_type();
                                    videodata = list.get(position - 1);
                                    startTestAPI();
                                } else if (((TextView) attemptLL.getChildAt(0)).getText().toString().equalsIgnoreCase("ATTEMPTED")) {
                                    if (list.get(position - 1).getResult_date() != null && !list.get(position - 1).getResult_date().equalsIgnoreCase("0") && !list.get(position - 1).getResult_date().equalsIgnoreCase("1") && !list.get(position - 1).getResult_date().equalsIgnoreCase("")) {
                                        Snackbar.make(v, "Your Result will be declare on " + new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(Long.parseLong(list.get(position - 1).getResult_date()) * 1000)), Snackbar.LENGTH_SHORT).show();

                                    } else if (utkashRoom.getTestDao().is_test_exit(list.get(position - 1).getId(), MakeMyExam.userId)) {
                                        Snackbar.make(v, "Your Result is Getting Ready Please refresh your page..", Snackbar.LENGTH_SHORT).show();
                                    } else {
                                        Snackbar.make(v, "Your Test has been alredy submiited", Snackbar.LENGTH_SHORT).show();
                                    }
                                } else if (((TextView) attemptLL.getChildAt(0)).getText().toString().equalsIgnoreCase(activity.getResources().getString(R.string.view_rank))) {
                                    quiz_id = list.get(position - 1).getId();
                                    quiz_name = list.get(position - 1).getTest_series_name();
                                    totalQuestion = list.get(position - 1).getTotal_questions();
                                    if (list.get(position - 1).getState().equalsIgnoreCase("1")) {

                                        if (list.get(position - 1).getResult_date() == null || list.get(position - 1).getResult_date().equalsIgnoreCase("") || list.get(position - 1).getResult_date().equalsIgnoreCase("0") || list.get(position - 1).getResult_date().equalsIgnoreCase("1")) {
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
//                                    Intent resultScreen = new Intent(activity, QuizActivity.class);
//                                    resultScreen.putExtra(Const.FRAG_TYPE, Const.RESULT_SCREEN);
//                                    resultScreen.putExtra(Const.STATUS, quiz_id);
//                                    resultScreen.putExtra(Const.NAME, quiz_name);
//                                    resultScreen.putExtra("first_attempt", "1");
//                                    activity.startActivity(resultScreen);

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
                    share.setOnClickListener(v -> {
                        if (is_purchase.equalsIgnoreCase("1")) {
                            Helper.shareTestg(activity, examPrepItem.getList().get(position - 1).getPayload().getCourse_id(), examPrepItem.getList().get(position - 1).getId(), examPrepItem.getList().get(position - 1).getPayload().getTopic_id(), examPrepItem.getList().get(position - 1).getPayload().getTile_type(), examPrepItem.getList().get(position - 1).getPayload().getTile_id(), examPrepItem.getList().get(position - 1).getPayload().getRevert_api(), "test", examPrepItem.getList().get(position - 1).getImage(), examPrepItem.getList().get(position - 1).getTitle(), SingleStudy.parentCourseId);
                        } else {
                            if (examPrepItem.getList().get(position - 1).getIs_locked().equalsIgnoreCase("1")) {
                              /*  Intent intent = new Intent(activity, PurchaseActivity.class);
                                intent.putExtra("single_study", new Gson().toJson(singleStudy));
                                Helper.gotoActivity(intent, activity);*/
                                if (study != null) {
                                    study.buynow();
                                }
                            } else {
                                Helper.shareTestg(activity, examPrepItem.getList().get(position - 1).getPayload().getCourse_id(), examPrepItem.getList().get(position - 1).getId(), examPrepItem.getList().get(position - 1).getPayload().getTopic_id(), examPrepItem.getList().get(position - 1).getPayload().getTile_type(), examPrepItem.getList().get(position - 1).getPayload().getTile_id(), examPrepItem.getList().get(position - 1).getPayload().getRevert_api(), "test", examPrepItem.getList().get(position - 1).getImage(), examPrepItem.getList().get(position - 1).getTitle(), SingleStudy.parentCourseId);
                            }
                        }
                    });

                    studyitemLL.setOnClickListener(v -> {
                        studyitemLL.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.nav_ripple));
                        if (attemptLL.getVisibility() == View.VISIBLE) {
                            if (!Helper.isConnected(activity)) {
                                Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (is_purchase.equalsIgnoreCase("1")) {
                                SharedPreference.getInstance().putString(Const.ID, singleStudy.getData().getCourseDetail().getId());

                                if (((TextView) attemptLL.getChildAt(0)).getText().toString().equalsIgnoreCase("RE-ATTEMPT")) {
                                    quiz_id = list.get(position - 1).getId();
                                    quiz_name = list.get(position - 1).getTest_series_name();
                                    totalQuestion = list.get(position - 1).getTotal_questions();
                                    result_date = list.get(position - 1).getResult_date();
                                    submission_type = list.get(position - 1).getSubmission_type();
                                    first_attempt = "0";
                                    videodata = list.get(position - 1);
                                    startTestAPI();
                                } else if (((TextView) attemptLL.getChildAt(0)).getText().toString().equalsIgnoreCase("Attempt Now")) {
                                    quiz_id = list.get(position - 1).getId();
                                    quiz_name = list.get(position - 1).getTest_series_name();
                                    totalQuestion = list.get(position - 1).getTotal_questions();
                                    first_attempt = "1";
                                    result_date = list.get(position - 1).getResult_date();
                                    submission_type = list.get(position - 1).getSubmission_type();
                                    videodata = list.get(position - 1);
                                    startTestAPI();
                                } else if (((TextView) attemptLL.getChildAt(0)).getText().toString().equalsIgnoreCase("ATTEMPTED")) {
                                    if (list.get(position - 1).getResult_date() != null && !list.get(position - 1).getResult_date().equalsIgnoreCase("0") && !list.get(position - 1).getResult_date().equalsIgnoreCase("1") && !list.get(position - 1).getResult_date().equalsIgnoreCase("")) {
                                        Snackbar.make(v, "Your Result will be declare on " + new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(Long.parseLong(list.get(position - 1).getResult_date()) * 1000)), Snackbar.LENGTH_SHORT).show();

                                    } else if (utkashRoom.getTestDao().is_test_exit(list.get(position - 1).getId(), MakeMyExam.userId)) {
                                        Snackbar.make(v, "Your Result is Getting Ready Please refresh your page..", Snackbar.LENGTH_SHORT).show();
                                    } else {
                                        Snackbar.make(v, "Your Test has been alredy submiited", Snackbar.LENGTH_SHORT).show();
                                    }

                                } else if (((TextView) attemptLL.getChildAt(0)).getText().toString().equalsIgnoreCase(activity.getResources().getString(R.string.view_rank))) {
                                    quiz_id = list.get(position - 1).getId();
                                    quiz_name = list.get(position - 1).getTest_series_name();
                                    totalQuestion = list.get(position - 1).getTotal_questions();
                                    if (list.get(position - 1).getState().equalsIgnoreCase("1")) {

                                        if (list.get(position - 1).getResult_date() == null || list.get(position - 1).getResult_date().equalsIgnoreCase("") || list.get(position - 1).getResult_date().equalsIgnoreCase("0") || list.get(position - 1).getResult_date().equalsIgnoreCase("1")) {
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
//                                    Intent resultScreen = new Intent(activity, QuizActivity.class);
//                                    resultScreen.putExtra(Const.FRAG_TYPE, Const.RESULT_SCREEN);
//                                    resultScreen.putExtra(Const.STATUS, quiz_id);
//                                    resultScreen.putExtra(Const.NAME, quiz_name);
//                                    resultScreen.putExtra("first_attempt", "1");
//                                    activity.startActivity(resultScreen);

                                    } else {
                                        Intent resultScreen = new Intent(activity, QuizActivity.class);
                                        resultScreen.putExtra(Const.FRAG_TYPE, "leader_board");
                                        resultScreen.putExtra(Const.STATUS, quiz_id);
                                        resultScreen.putExtra(Const.NAME, quiz_name);
                                        activity.startActivity(resultScreen);
                                    }

                                }
                            } else {
                                if (list.get(position - 1).getIs_locked().equalsIgnoreCase("1")) {

                                  /*  Intent intent = new Intent(activity, PurchaseActivity.class);
                                    intent.putExtra("single_study", new Gson().toJson(singleStudy));
                                    Helper.gotoActivity(intent, activity);*/

                                    if (study != null) {
                                        study.buynow();
                                    }
                                } else {
                                    SharedPreference.getInstance().putString(Const.ID, singleStudy.getData().getCourseDetail().getId());

                                    if (((TextView) attemptLL.getChildAt(0)).getText().toString().equalsIgnoreCase("RE-ATTEMPT")) {
                                        quiz_id = list.get(position - 1).getId();
                                        quiz_name = list.get(position - 1).getTest_series_name();
                                        totalQuestion = list.get(position - 1).getTotal_questions();
                                        first_attempt = "0";
                                        result_date = list.get(position - 1).getResult_date();
                                        submission_type = list.get(position - 1).getSubmission_type();
                                        videodata = list.get(position - 1);
                                        startTestAPI();
                                    } else if (((TextView) attemptLL.getChildAt(0)).getText().toString().equalsIgnoreCase("Attempt Now")) {
                                        quiz_id = list.get(position - 1).getId();
                                        quiz_name = list.get(position - 1).getTest_series_name();
                                        totalQuestion = list.get(position - 1).getTotal_questions();
                                        first_attempt = "1";
                                        result_date = list.get(position - 1).getResult_date();
                                        submission_type = list.get(position - 1).getSubmission_type();
                                        videodata = list.get(position - 1);
                                        startTestAPI();
                                    } else if (((TextView) attemptLL.getChildAt(0)).getText().toString().equalsIgnoreCase("ATTEMPTED")) {
                                        if (list.get(position - 1).getResult_date() != null && !list.get(position - 1).getResult_date().equalsIgnoreCase("0") && !list.get(position - 1).getResult_date().equalsIgnoreCase("1") && !list.get(position - 1).getResult_date().equalsIgnoreCase("")) {
                                            Snackbar.make(v, "Your Result will be declare on " + new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(Long.parseLong(list.get(position - 1).getResult_date()) * 1000)), Snackbar.LENGTH_SHORT).show();

                                        } else if (utkashRoom.getTestDao().is_test_exit(list.get(position - 1).getId(), MakeMyExam.userId)) {
                                            Snackbar.make(v, "Your Result is Getting Ready Please refresh your page..", Snackbar.LENGTH_SHORT).show();
                                        } else {
                                            Snackbar.make(v, "Your Test has been alredy submiited", Snackbar.LENGTH_SHORT).show();
                                        }
                                    } else if (((TextView) attemptLL.getChildAt(0)).getText().toString().equalsIgnoreCase(activity.getResources().getString(R.string.view_rank))) {
                                        quiz_id = list.get(position - 1).getId();
                                        quiz_name = list.get(position - 1).getTest_series_name();
                                        totalQuestion = list.get(position - 1).getTotal_questions();
                                        if (list.get(position - 1).getState().equalsIgnoreCase("1")) {

                                            if (list.get(position - 1).getResult_date() == null || list.get(position - 1).getResult_date().equalsIgnoreCase("") || list.get(position - 1).getResult_date().equalsIgnoreCase("0") || list.get(position - 1).getResult_date().equalsIgnoreCase("1")) {
                                                Intent resultScreen = new Intent(activity, QuizActivity.class);
                                                resultScreen.putExtra(Const.FRAG_TYPE, Const.RESULT_SCREEN);
                                                resultScreen.putExtra(Const.STATUS, quiz_id);
                                                resultScreen.putExtra(Const.NAME, quiz_name);
                                                //    resultScreen.putExtra("show_leader", "0");
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
//                                    Intent resultScreen = new Intent(activity, QuizActivity.class);
//                                    resultScreen.putExtra(Const.FRAG_TYPE, Const.RESULT_SCREEN);
//                                    resultScreen.putExtra(Const.STATUS, quiz_id);
//                                    resultScreen.putExtra(Const.NAME, quiz_name);
//                                    resultScreen.putExtra("first_attempt", "1");
//                                    activity.startActivity(resultScreen);

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

                } else {
                    studyitemLL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (list.get(position - 1).getIs_locked().equalsIgnoreCase("1")) {
                              /*  Intent intent = new Intent(activity, PurchaseActivity.class);
                                intent.putExtra("single_study", new Gson().toJson(singleStudy));
                                Helper.gotoActivity(intent, activity);*/

                                if (study != null) {
                                    study.buynow();
                                }
                                return;
                            }

                            if (isSkip.equalsIgnoreCase("1") || isSkip.equalsIgnoreCase("2")) {
                                Intent examPrepLayer1 = new Intent(activity, CourseActivity.class);
                           /*     examPrepLayer1.putExtra(Const.SINGLE_STUDY_DATA, new Gson().toJson(singleStudy));
                                examPrepLayer1.putExtra(Const.EXAMPREP, examPrepItem);
*/
                                Constants.examPrepItemNew =examPrepItem;
                                Constants.courseDetail =singleStudy;


                                examPrepLayer1.putExtra(Const.FRAG_TYPE, Const.EXAMPREPLAST);
                                examPrepLayer1.putExtra(Const.IS_COMBO, isCombo);
                                examPrepLayer1.putExtra(Const.TITLE, examPrepItem.getList().get(position - 1).getTitle());
                                examPrepLayer1.putExtra(Const.CONTENT_TYPE, contentType);
                                examPrepLayer1.putExtra(Const.TEST_TYPE, examPrepItem.getList().get(position - 1).getCount());
                                examPrepLayer1.putExtra(Const.LIST, examPrepItem.getList().get(position - 1));
                                examPrepLayer1.putExtra(Const.TILE_ID, tileIdAPI);
                                examPrepLayer1.putExtra(Const.TILE_TYPE, tileTypeAPI);
                                examPrepLayer1.putExtra(Const.LIST_SUBJECT, examPrepItem.getList().get(position - 1));
                                examPrepLayer1.putExtra(Const.REVERT_API, revertAPI);
                                Helper.gotoActivity(examPrepLayer1, activity);

                            } else if (isSkip.equalsIgnoreCase("3")) {

                            } else {
                                ExamPrepItem examPrepItemNew = new ExamPrepItem();
                                examPrepItemNew.setList(examPrepItem.getList().get(position - 1).getList());
                                Intent examPrepLayer1 = new Intent(activity, CourseActivity.class);
                           /*     examPrepLayer1.putExtra(Const.SINGLE_STUDY_DATA, new Gson().toJson(singleStudy));
                                examPrepLayer1.putExtra(Const.EXAMPREP, examPrepItemNew);
*/
                                Constants.examPrepItemNew =examPrepItemNew;
                                Constants.courseDetail =singleStudy;

                                examPrepLayer1.putExtra(Const.FRAG_TYPE, Const.EXAMPREP);
                                examPrepLayer1.putExtra(Const.IS_COMBO, isCombo);
                                examPrepLayer1.putExtra(Const.CONTENT_TYPE, contentType);
                                examPrepLayer1.putExtra(Const.TITLE, singleStudy.getData().getCourseDetail() != null ? singleStudy.getData().getCourseDetail().getTitle() : "Course");
                                examPrepLayer1.putExtra(Const.LIST, list.get(position - 1));
                                examPrepLayer1.putExtra(Const.TILE_ID, tileIdAPI);
                                examPrepLayer1.putExtra(Const.TILE_TYPE, tileTypeAPI);
                                examPrepLayer1.putExtra(Const.REVERT_API, revertAPI);
                                Helper.gotoActivity(examPrepLayer1, activity);

                            }
                        }
                    });
                }


            } else {
                ibt_single_sub_vd_RL.setVisibility(View.GONE);
                no_data_found_RL.setVisibility(View.VISIBLE);

                backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.finish();
                    }
                });
            }
        }

        private void startTestAPI() {
            NetworkCall networkCall = new NetworkCall(SingleStudyAdapter.this, activity);
            networkCall.NetworkAPICall(API.API_GET_TEST_INSTRUCTION_DATA, "", true, false);
        }

        public void result_without_submit(String quiz_id, String course_id, String s, String quiz_name) {
            if (Helper.isNetworkConnected(activity)) {
                Progress mprogress = new Progress(activity);
                mprogress.show();
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
                        mprogress.dismiss();
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
                                    mprogress.dismiss();
                                    RetrofitResponse.GetApiData(activity, jsonResponse.optString(Const.AUTH_CODE), jsonResponse.optString(Const.MESSAGE), false);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mprogress.dismiss();

                    }
                });
            } else {
                Toast.makeText(activity, R.string.Retry_with_Internet_connection, Toast.LENGTH_LONG).show();
            }

        }


        public void netoworkCallForQuizResult2(String quiz_id, String course_id, String s, String quiz_name) {
            if (Helper.isNetworkConnected(activity)) {
                Progress mprogress = new Progress(activity);
                mprogress.show();
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
                        mprogress.dismiss();
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
                                if (resultTestSeries2.getStatus().equalsIgnoreCase("true")) {
                                    if (resultTestSeries2.getData().getQuestions() != null && resultTestSeries2.getData().getQuestions().size() > 0) {
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
                                    mprogress.dismiss();
                                    RetrofitResponse.GetApiData(activity, jsonResponse.optString(Const.AUTH_CODE), jsonResponse.optString(Const.MESSAGE), false);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mprogress.dismiss();

                    }
                });
            } else {
                Toast.makeText(activity, R.string.Retry_with_Internet_connection, Toast.LENGTH_LONG).show();
            }

        }
    }

    public class SingleStudyListHolder extends RecyclerView.ViewHolder {

        RelativeLayout parentLL, studyitemLL, layout_fun;
        ImageView imageIcon;
        TextView titleCategory,count;
        RelativeLayout no_data_found_RL;
        Button backBtn;
        TextView subItemRV;
        GifImageView liveIV;
        TextView watch_now, listen_now, share, read_now;
        View view1;
        ImageView optionMenuImgView;
        CardView ibt_single_sub_vd_RL;
        RelativeLayout lockRL;


        public SingleStudyListHolder(View itemView) {
            super(itemView);
            lockRL = itemView.findViewById(R.id.lockRL);
            liveIV = itemView.findViewById(R.id.liveIV);
            parentLL = itemView.findViewById(R.id.parentLL);
            listen_now = itemView.findViewById(R.id.listne_now);
            studyitemLL = itemView.findViewById(R.id.study_single_itemLL);
            imageIcon = itemView.findViewById(R.id.profileImage);
            watch_now = itemView.findViewById(R.id.watch_now);
            read_now = itemView.findViewById(R.id.read_now);
            share = itemView.findViewById(R.id.share);
            titleCategory = itemView.findViewById(R.id.examPrepTitleTV);
            subItemRV = itemView.findViewById(R.id.subItemRV);
            layout_fun = itemView.findViewById(R.id.layout_fun);
            optionMenuImgView = itemView.findViewById(R.id.optionMenuImgView);
            ibt_single_sub_vd_RL = itemView.findViewById(R.id.ibt_single_sub_vd_RL);
            no_data_found_RL = itemView.findViewById(R.id.no_data_found_RL);
            backBtn = itemView.findViewById(R.id.backBtn);
            count = itemView.findViewById(R.id.count);

        }

        @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
        public void setData(final ArrayList<Lists> list, final int position) {
            if (list != null && list.size() > 0) {
                ibt_single_sub_vd_RL.setVisibility(View.VISIBLE);
                no_data_found_RL.setVisibility(View.GONE);

                if (list.get(position - 1).getIs_live() != null) {
                    if (list.get(position - 1).getIs_live().equals("1")) {
                        liveIV.setVisibility(View.VISIBLE);
                    } else {
                        liveIV.setVisibility(View.GONE);
                    }
                } else {
                    liveIV.setVisibility(View.GONE);
                }

                if (TextUtils.isEmpty(list.get(position - 1).getIs_locked())) {
                    list.get(position - 1).setIs_locked("0");
                }
                if (singleStudy != null && singleStudy.getData().getCourseDetail() != null) {
                    if (singleStudy.getData().getCourseDetail().getIsPurchased().equals("1")) {
                        list.get(position - 1).setIs_locked("0");
                    }
                }

                if (list.get(position - 1).getIs_locked().equals("0")) {
                    lockRL.setVisibility(View.GONE);
                } else {
                    lockRL.setVisibility(View.VISIBLE);
                }

                if (isSkip.equalsIgnoreCase("3")) {
                    if (!TextUtils.isEmpty(list.get(position - 1).getThumbnail_url())) {
                        Helper.setThumbnailImage(activity, list.get(position - 1).getThumbnail_url(), activity.getResources().getDrawable(R.drawable.square_thumbnail), imageIcon);
                    } else {
                        imageIcon.setImageResource(R.drawable.square_thumbnail);
                    }
                } else {
                    count.setVisibility(View.VISIBLE);
                    count.setText(""+position);
                    imageIcon.setVisibility(View.GONE);
                  /*  if (!TextUtils.isEmpty(list.get(position - 1).getImage_icon())) {
                        Helper.setThumbnailImage(activity, list.get(position - 1).getImage_icon(), activity.getResources().getDrawable(R.drawable.square_thumbnail), imageIcon);
                    } else {
                        imageIcon.setImageResource(R.drawable.square_thumbnail);
                    }*/
                }
                titleCategory.setText(list.get(position - 1).getTitle());

                if (isSkip.equalsIgnoreCase("3")) {
                    layout_fun.setVisibility(View.VISIBLE);

                    if (list.get(position - 1).getVideo_status() != null) {
                        if (list.get(position - 1).getVideo_status().equalsIgnoreCase("Download Running")) {
                            optionMenuImgView.setVisibility(View.GONE);
                            subItemRV.setVisibility(View.GONE);
                        } else if (list.get(position - 1).getVideo_status().equalsIgnoreCase("Downloaded")) {
                            optionMenuImgView.setVisibility(View.VISIBLE);
                            optionMenuImgView.setEnabled(false);
                            subItemRV.setVisibility(View.GONE);
                            optionMenuImgView.setImageResource(R.drawable.ic_downloaded_chapter);
                            subItemRV.setText("Duration : " + list.get(position - 1).getVideo_time());
                        } else if (list.get(position - 1).getVideo_status().equalsIgnoreCase("Download")) {
                            optionMenuImgView.setEnabled(true);
                            subItemRV.setVisibility(View.GONE);
                            optionMenuImgView.setImageResource(R.drawable.ic_menu_download);
                            optionMenuImgView.setVisibility(View.VISIBLE);
                        } else {
                            subItemRV.setVisibility(View.GONE);
                            optionMenuImgView.setVisibility(View.GONE);
                        }
                    }
                    if (list.get(position - 1).getFile_type().equalsIgnoreCase("3")) {
                        watch_now.setVisibility(View.VISIBLE);
                        if (list.get(position - 1).getOpen_in_app() != null && list.get(position - 1).getOpen_in_app().equalsIgnoreCase("1")) {
                            listen_now.setVisibility(View.VISIBLE);
                        } else {
                            if (list.get(position - 1).getVideo_type().equalsIgnoreCase("0") ||
                                    list.get(position - 1).getVideo_type().equalsIgnoreCase("1") ||
                                    list.get(position - 1).getVideo_type().equalsIgnoreCase("5") ||
                                    list.get(position - 1).getVideo_type().equalsIgnoreCase("6")
                            ) {
                                listen_now.setVisibility(View.VISIBLE);
                            } else {
                                listen_now.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        watch_now.setVisibility(View.GONE);
                    }
                    share.setVisibility(View.VISIBLE);

                    if (list.get(position - 1).getFile_type().equalsIgnoreCase("12") || list.get(position - 1).getFile_type().equalsIgnoreCase("11") || list.get(position - 1).getFile_type().equalsIgnoreCase("8")) {
                        studyitemLL.setEnabled(true);
                        read_now.setEnabled(true);
                        read_now.setVisibility(View.VISIBLE);
                        if (list.get(position - 1).getFile_type().equalsIgnoreCase("6")) {
                            read_now.setText("VIEW");
                        } else {
                            read_now.setText("READ");
                        }
                    } else {
                        studyitemLL.setEnabled(true);
                        read_now.setEnabled(false);
                        read_now.setVisibility(View.GONE);
                    }

                } else {
                    layout_fun.setVisibility(View.GONE);
                    watch_now.setVisibility(View.GONE);
                    listen_now.setVisibility(View.GONE);
                    if (TextUtils.isEmpty(list.get(position - 1).getCount())) {
                        subItemRV.setVisibility(View.INVISIBLE);
                    } else {
                        subItemRV.setVisibility(View.GONE);
                        subItemRV.setText("Total : " + list.get(position - 1).getCount());
                    }
                }

                share.setOnClickListener(v ->
                {
                    is_audio = false;

                    if (is_purchase.equalsIgnoreCase("1")) {
                        if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("8")
                                || examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("7")
                                || examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("1")

                        ) {
                            Helper.sharePdf(activity, examPrepItem.getList().get(position - 1).getPayload().getCourse_id(), examPrepItem.getList().get(position - 1).getId(), examPrepItem.getList().get(position - 1).getPayload().getTopic_id(), examPrepItem.getList().get(position - 1).getPayload().getTile_type(), examPrepItem.getList().get(position - 1).getPayload().getTile_id(), examPrepItem.getList().get(position - 1).getPayload().getRevert_api(), "pdf", examPrepItem.getList().get(position - 1).getThumbnail_url(), examPrepItem.getList().get(position - 1).getTitle(), SingleStudy.parentCourseId);
                        } else {
                            if (examPrepItem.getList().get(position - 1).getVideo_type().equalsIgnoreCase("4") || examPrepItem.getList().get(position - 1).getVideo_type().equalsIgnoreCase("1")) {
                            } else if (examPrepItem.getList().get(position - 1).getVideo_type().equalsIgnoreCase("6")) {
                                Helper.sharejwvideo(activity, examPrepItem.getList().get(position - 1).getPayload().getCourse_id(), examPrepItem.getList().get(position - 1).getId(), examPrepItem.getList().get(position - 1).getPayload().getTopic_id(), examPrepItem.getList().get(position - 1).getPayload().getTile_type(), examPrepItem.getList().get(position - 1).getPayload().getTile_id(), examPrepItem.getList().get(position - 1).getPayload().getRevert_api(), "video", examPrepItem.getList().get(position - 1).getThumbnail_url(), examPrepItem.getList().get(position - 1).getTitle(), SingleStudy.parentCourseId);
                            }
                        }
                    } else {
                        if (examPrepItem.getList().get(position - 1).getIs_locked().equalsIgnoreCase("1")) {
                            /*Intent intent = new Intent(activity, PurchaseActivity.class);
                            intent.putExtra("single_study", new Gson().toJson(singleStudy));
                            Helper.gotoActivity(intent, activity);*/

                            if (study != null) {
                                study.buynow();
                            }
                        } else {
                            if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("8")
                                    || examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("7")
                                    || examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("1")

                            ) {
                                Helper.sharePdf(activity, examPrepItem.getList().get(position - 1).getPayload().getCourse_id(), examPrepItem.getList().get(position - 1).getId(), examPrepItem.getList().get(position - 1).getPayload().getTopic_id(), examPrepItem.getList().get(position - 1).getPayload().getTile_type(), examPrepItem.getList().get(position - 1).getPayload().getTile_id(), examPrepItem.getList().get(position - 1).getPayload().getRevert_api(), "pdf", examPrepItem.getList().get(position - 1).getThumbnail_url(), examPrepItem.getList().get(position - 1).getTitle(), SingleStudy.parentCourseId);
                            } else {
                                if (examPrepItem.getList().get(position - 1).getVideo_type().equalsIgnoreCase("4") || examPrepItem.getList().get(position - 1).getVideo_type().equalsIgnoreCase("1")) {
                                } else if (examPrepItem.getList().get(position - 1).getVideo_type().equalsIgnoreCase("6")) {
                                    Helper.sharejwvideo(activity, examPrepItem.getList().get(position - 1).getPayload().getCourse_id(), examPrepItem.getList().get(position - 1).getId(), examPrepItem.getList().get(position - 1).getPayload().getTopic_id(), examPrepItem.getList().get(position - 1).getPayload().getTile_type(), examPrepItem.getList().get(position - 1).getPayload().getTile_id(), examPrepItem.getList().get(position - 1).getPayload().getRevert_api(), "video", examPrepItem.getList().get(position - 1).getThumbnail_url(), examPrepItem.getList().get(position - 1).getTitle(), SingleStudy.parentCourseId);
                                } else if (examPrepItem.getList().get(position - 1).getVideo_type().equalsIgnoreCase("0")) {
                                    Helper.shareLiveClass(activity, examPrepItem.getList().get(position - 1).getPayload().getCourse_id(), examPrepItem.getList().get(position - 1).getId(), examPrepItem.getList().get(position - 1).getPayload().getTopic_id(), examPrepItem.getList().get(position - 1).getPayload().getTile_type(), examPrepItem.getList().get(position - 1).getPayload().getTile_id(), examPrepItem.getList().get(position - 1).getPayload().getRevert_api(), "video", examPrepItem.getList().get(position - 1).getThumbnail_url(), examPrepItem.getList().get(position - 1).getTitle(), SingleStudy.parentCourseId);
                                }
                            }
                        }
                    }
                });
                optionMenuImgView.setOnClickListener(v -> {
                    is_audio = false;
                    is_download = false;
                    if (is_purchase.equalsIgnoreCase("1")) {
                        if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("10") || examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("12") || examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("11")) {
                            videodata = examPrepItem.getList().get(position - 1);
                            position_delete = position - 1;
                            delete_meg(videodata);
                        } else if (!VideoDownloadService.isServiceRunning) {
                            if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("3")) {

                                download_data(examPrepItem.getList().get(position - 1), position);
                            } else {
                                Toast.makeText(activity, "Only jw video download...", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(activity, "Please wait downloading is in progress", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (examPrepItem.getList().get(position - 1).getIs_locked().equalsIgnoreCase("1")) {

                            /*Intent intent = new Intent(activity, PurchaseActivity.class);
                            intent.putExtra("single_study", new Gson().toJson(singleStudy));
                            Helper.gotoActivity(intent, activity);*/

                            if (study != null) {
                                study.buynow();
                            }
                        } else {
                            if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("10") || examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("12") || examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("11")) {
                                videodata = examPrepItem.getList().get(position - 1);
                                position_delete = position - 1;
                                delete_meg(videodata);
                            } else if (!VideoDownloadService.isServiceRunning) {
                                if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("3")) {
                                    download_data(examPrepItem.getList().get(position - 1), position);
                                } else {
                                    Toast.makeText(activity, "Only jw video download...", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(activity, "Please wait downloading is in progress", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                watch_now.setOnClickListener(v -> {
                    is_audio = false;
                    if (is_purchase.equalsIgnoreCase("1")) {
                        if (TextUtils.isEmpty(examPrepItem.getList().get(position - 1).getId())) {
                            Toast.makeText(activity, "No video found!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Helper.audio_service_close((CourseActivity) activity);
                        API_REQUEST_VIDEO_LINK(examPrepItem.getList().get(position - 1), position - 1);
                    } else {
                        if (examPrepItem.getList().get(position - 1).getIs_locked().equalsIgnoreCase("1")) {

                           /* Intent intent = new Intent(activity, PurchaseActivity.class);
                            intent.putExtra("single_study", new Gson().toJson(singleStudy));
                            Helper.gotoActivity(intent, activity);
*/
                            if (study != null) {
                                study.buynow();
                            }
                        } else {
                            if (TextUtils.isEmpty(examPrepItem.getList().get(position - 1).getId())) {
                                Toast.makeText(activity, "No video found!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Helper.audio_service_close((CourseActivity) activity);
                            API_REQUEST_VIDEO_LINK(examPrepItem.getList().get(position - 1), position - 1);
                        }
                    }
                });
                listen_now.setOnClickListener(v -> {
                    is_audio = false;
                    if (Helper.isNetworkConnected(activity)) {
                        if (is_purchase.equalsIgnoreCase("1")) {
                            if (TextUtils.isEmpty(examPrepItem.getList().get(position - 1).getId())) {
                                Toast.makeText(activity, "No video found!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Helper.audio_service_close(activity);
                            if (examPrepItem.getList().get(position - 1).getVideo_type().equalsIgnoreCase("6")) {
                                videodata = examPrepItem.getList().get(position - 1);
                                is_audio = true;
                                NetworkCall networkCall = new NetworkCall(SingleStudyAdapter.this, activity);
                                networkCall.NetworkAPICall(API.get_video_link, "", true, false);


                            } else if (examPrepItem.getList().get(position - 1).getVideo_type().equalsIgnoreCase("1")) {
                                Helper.GoToLiveVideoActivity(examPrepItem.getList().get(position - 1).getChat_node(), activity, examPrepItem.getList().get(position - 1).getFile_url(), examPrepItem.getList().get(position - 1).getVideo_type(), examPrepItem.getList().get(position - 1).getId(), examPrepItem.getList().get(position - 1).getTitle(), "1", examPrepItem.getList().get(position - 1).getThumbnail_url(), examPrepItem.getList().get(position - 1).getIs_chat_lock(), examPrepItem.getList().get(position - 1).getPayload().getCourse_id(), String.valueOf(position - 1), SingleStudy.parentCourseId, examPrepItem.getList().get(position - 1).getPayload().getTile_id(), examPrepItem.getList().get(position - 1).getPayload().getTile_type());
                            } else if (examPrepItem.getList().get(position - 1).getVideo_type().equalsIgnoreCase("5")) {
                                if (examPrepItem.getList().get(position - 1).getLive_status().equalsIgnoreCase("1")) {

                                    videodata = examPrepItem.getList().get(position - 1);
                                    is_audio = true;
                                    NetworkCall networkCall = new NetworkCall(SingleStudyAdapter.this, activity);
                                    networkCall.NetworkAPICall(API.get_video_link, "", true, false);


                                } else if (examPrepItem.getList().get(position - 1).getLive_status().equalsIgnoreCase("0")) {
                                    Toast.makeText(activity, "Live Class will start on " + new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(Long.parseLong(examPrepItem.getList().get(position - 1).getStart_date()) * 1000)), Toast.LENGTH_SHORT).show();
                                } else if (examPrepItem.getList().get(position - 1).getLive_status().equalsIgnoreCase("2")) {
                                    Toast.makeText(activity, "Live class is ended", Toast.LENGTH_SHORT).show();
                                } else if (examPrepItem.getList().get(position - 1).getLive_status().equalsIgnoreCase("3")) {
                                    Toast.makeText(activity, "Live class is cancelled", Toast.LENGTH_SHORT).show();
                                }
                            } else if (examPrepItem.getList().get(position - 1).getVideo_type().equalsIgnoreCase("0")) {
                                videodata = examPrepItem.getList().get(position - 1);
                                is_audio = true;
                                NetworkCall networkCall = new NetworkCall(SingleStudyAdapter.this, activity);
                                networkCall.NetworkAPICall(API.get_video_link, "", true, false);


                            }

                        } else {
                            if (examPrepItem.getList().get(position - 1).getIs_locked().equalsIgnoreCase("1")) {

                                /*Intent intent = new Intent(activity, PurchaseActivity.class);
                                intent.putExtra("single_study", new Gson().toJson(singleStudy));
                                Helper.gotoActivity(intent, activity);*/

                                if (study != null) {
                                    study.buynow();
                                }

                            } else {
                                if (TextUtils.isEmpty(examPrepItem.getList().get(position - 1).getId())) {
                                    Toast.makeText(activity, "No video found!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Helper.audio_service_close((CourseActivity) activity);
                                if (examPrepItem.getList().get(position - 1).getVideo_type().equalsIgnoreCase("6")) {
                                    // String mediaId = "https://cdn.jwplayer.com/v2/media/" + examPrepItem.getList().get(position - 1).getFile_url().substring(examPrepItem.getList().get(position - 1).getFile_url().lastIndexOf("/") + 1, examPrepItem.getList().get(position - 1).getFile_url().indexOf("-"));
                                    //  audio_play(mediaId, examPrepItem.getList().get(position - 1));

                                    videodata = examPrepItem.getList().get(position - 1);
                                    is_audio = true;
                                    NetworkCall networkCall = new NetworkCall(SingleStudyAdapter.this, activity);
                                    networkCall.NetworkAPICall(API.get_video_link, "", true, false);


                                } else if (examPrepItem.getList().get(position - 1).getVideo_type().equalsIgnoreCase("1")) {
                                    Helper.GoToLiveVideoActivity(examPrepItem.getList().get(position - 1).getChat_node(), activity, examPrepItem.getList().get(position - 1).getFile_url(), examPrepItem.getList().get(position - 1).getVideo_type(), examPrepItem.getList().get(position - 1).getId(), examPrepItem.getList().get(position - 1).getTitle(), "1", examPrepItem.getList().get(position - 1).getThumbnail_url(), examPrepItem.getList().get(position - 1).getIs_chat_lock(), examPrepItem.getList().get(position - 1).getPayload().getCourse_id(), String.valueOf(position - 1), SingleStudy.parentCourseId, examPrepItem.getList().get(position - 1).getPayload().getTile_id(), examPrepItem.getList().get(position - 1).getPayload().getTile_type());
                                } else if (examPrepItem.getList().get(position - 1).getVideo_type().equalsIgnoreCase("5")) {
                                    videodata = examPrepItem.getList().get(position - 1);
                                    is_audio = true;
                                    NetworkCall networkCall = new NetworkCall(SingleStudyAdapter.this, activity);
                                    networkCall.NetworkAPICall(API.get_video_link, "", true, false);


                                    //  Helper.GoToLiveAwsVideoActivity(examPrepItem.getList().get(position - 1).getVideo_type(), examPrepItem.getList().get(position - 1).getChat_node(), activity, examPrepItem.getList().get(position - 1).getFile_url(), examPrepItem.getList().get(position - 1).getVideo_type(), examPrepItem.getList().get(position - 1).getId(), examPrepItem.getList().get(position - 1).getTitle(), "1", examPrepItem.getList().get(position - 1).getThumbnail_url(), examPrepItem.getList().get(position - 1).getPayload().getCourse_id(), examPrepItem.getList().get(position - 1).getPayload().getTile_id(), examPrepItem.getList().get(position - 1).getPayload().getTile_type(), examPrepItem.getList().get(position - 1).getIs_chat_lock(), String.valueOf(position - 1), SingleStudy.parentCourseId,jsonobject.toString());
                                } else if (examPrepItem.getList().get(position - 1).getVideo_type().equalsIgnoreCase("0")) {
                                    videodata = examPrepItem.getList().get(position - 1);
                                    is_audio = true;
                                    NetworkCall networkCall = new NetworkCall(SingleStudyAdapter.this, activity);
                                    networkCall.NetworkAPICall(API.get_video_link, "", true, false);


                                    //  Helper.GoToLiveAwsVideoActivity(examPrepItem.getList().get(position - 1).getVideo_type(), examPrepItem.getList().get(position - 1).getChat_node(), activity, examPrepItem.getList().get(position - 1).getFile_url(), examPrepItem.getList().get(position - 1).getVideo_type(), examPrepItem.getList().get(position - 1).getId(), examPrepItem.getList().get(position - 1).getTitle(), "1", examPrepItem.getList().get(position - 1).getThumbnail_url(), examPrepItem.getList().get(position - 1).getPayload().getCourse_id(), examPrepItem.getList().get(position - 1).getPayload().getTile_id(), examPrepItem.getList().get(position - 1).getPayload().getTile_type(), examPrepItem.getList().get(position - 1).getIs_chat_lock(), String.valueOf(position - 1), SingleStudy.parentCourseId,jsonobject.toString());
                                }

                            }
                        }

                    } else {
                        Toast.makeText(activity, "No Internet Connection.", Toast.LENGTH_SHORT).show();
                    }
                });
                read_now.setOnClickListener(v -> {
                    is_audio = false;

                    if (list.get(position - 1).getIs_locked().equalsIgnoreCase("1")) {
                     /*   Intent intent = new Intent(activity, PurchaseActivity.class);
                        intent.putExtra("single_study", new Gson().toJson(singleStudy));
                        Helper.gotoActivity(intent, activity);*/

                        if (study != null) {
                            study.buynow();
                        }
                        return;
                    }

                    if (is_purchase.equalsIgnoreCase("1")) {
                        if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("12")) {
                            if (examPrepItem.getList().size() > 0) {
                                Intent intent = new Intent(activity, RevisionActivity.class);
                                intent.putExtra("t_id", tileIdAPI);
                                intent.putExtra("postion", position);
                                intent.putExtra("video_id", examPrepItem.getList().get(position - 1).getId());
                                intent.putExtra("v_list", actual_videolist);
                                intent.putExtra("f_id", examPrepItem.getList().get(0).getId());
                                intent.putExtra("c_id", singleStudy.getData().getCourseDetail().getId());
                                intent.putExtra("title", examPrepItem.getList().get(position - 1).getTitle());
                                intent.putExtra("url", examPrepItem.getList().get(position - 1).getFile_url());
                                intent.putExtra("via", "main");
                                Helper.gotoActivity(intent, activity);
                            }
                        } else if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("11")) {
                            Intent intent4 = new Intent(activity, WebViewActivty.class);
                            intent4.putExtra("type", examPrepItem.getList().get(position - 1).getTitle());
                            intent4.putExtra("url", examPrepItem.getList().get(position - 1).getFile_url());
                            Helper.gotoActivity(intent4, activity);
                        } else if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("8")) {
                            Intent intent4 = new Intent(activity, WebViewActivty.class);
                            intent4.putExtra("type", examPrepItem.getList().get(position - 1).getTitle());
                            intent4.putExtra("url", examPrepItem.getList().get(position - 1).getFile_url());
                            if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                                intent4.putExtra("course_id", singleStudy.getData().getCourseDetail().getId() + "#");

                            } else {
                                intent4.putExtra("course_id", SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());

                            }
                            intent4.putExtra("video_id", examPrepItem.getList().get(position - 1).getId());
                            intent4.putExtra("link", examPrepItem.getList().get(position - 1).getFile_type());
                            Helper.gotoActivity(intent4, activity);
                        } else if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("6")) {
                            if (TextUtils.isEmpty(examPrepItem.getList().get(position - 1).getFile_url()) && TextUtils.isEmpty(examPrepItem.getList().get(position - 1).getId())) {
                                Toast.makeText(activity, "No image found", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Intent intent = new Intent(activity, WebViewActivty.class);
                            intent.putExtra("type", examPrepItem.getList().get(position - 1).getTitle());
                            intent.putExtra("url", examPrepItem.getList().get(position - 1).getFile_url());
                            intent.putExtra("file_type", "image");
                            Helper.gotoActivity(intent, activity);
                        }
                    } else {
                        if (examPrepItem.getList().get(position - 1).getIs_locked().equalsIgnoreCase("1")) {

                            /*Intent intent = new Intent(activity, PurchaseActivity.class);
                            intent.putExtra("single_study", new Gson().toJson(singleStudy));
                            Helper.gotoActivity(intent, activity);*/

                            if (study != null) {
                                study.buynow();
                            }
                        } else {
                            if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("12")) {
                                if (examPrepItem.getList().size() > 0) {
                                    Intent intent = new Intent(activity, RevisionActivity.class);
                                    intent.putExtra("t_id", tileIdAPI);
                                    intent.putExtra("postion", position);
                                    intent.putExtra("video_id", examPrepItem.getList().get(position - 1).getId());
                                    intent.putExtra("v_list", actual_videolist);
                                    intent.putExtra("f_id", examPrepItem.getList().get(0).getId());
                                    intent.putExtra("c_id", singleStudy.getData().getCourseDetail().getId());
                                    intent.putExtra("title", examPrepItem.getList().get(position - 1).getTitle());
                                    intent.putExtra("url", examPrepItem.getList().get(position - 1).getFile_url());
                                    intent.putExtra("via", "main");
                                    Helper.gotoActivity(intent, activity);
                                }

                            } else if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("11")) {
                                Intent intent4 = new Intent(activity, WebViewActivty.class);
                                intent4.putExtra("type", examPrepItem.getList().get(position - 1).getTitle());
                                intent4.putExtra("url", examPrepItem.getList().get(position - 1).getFile_url());
                                Helper.gotoActivity(intent4, activity);
                            } else if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("8")) {
                                Intent intent4 = new Intent(activity, WebViewActivty.class);
                                intent4.putExtra("type", examPrepItem.getList().get(position - 1).getTitle());
                                intent4.putExtra("url", examPrepItem.getList().get(position - 1).getFile_url());
                                if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                                    intent4.putExtra("course_id", singleStudy.getData().getCourseDetail().getId() + "#");

                                } else {
                                    intent4.putExtra("course_id", SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());

                                }
                                intent4.putExtra("video_id", examPrepItem.getList().get(position - 1).getId());
                                intent4.putExtra("link", examPrepItem.getList().get(position - 1).getFile_type());
                                Helper.gotoActivity(intent4, activity);
                            } else if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("6")) {
                                if (TextUtils.isEmpty(examPrepItem.getList().get(position - 1).getFile_url()) && TextUtils.isEmpty(examPrepItem.getList().get(position - 1).getId())) {
                                    Toast.makeText(activity, "No image found", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Intent intent = new Intent(activity, WebViewActivty.class);
                                intent.putExtra("type", examPrepItem.getList().get(position - 1).getTitle());
                                intent.putExtra("url", examPrepItem.getList().get(position - 1).getFile_url());
                                intent.putExtra("file_type", "image");
                                Helper.gotoActivity(intent, activity);
                            }
                        }
                    }
                });

                studyitemLL.setOnClickListener(view -> {
                    is_audio = false;
                    studyitemLL.setFocusable(true);
                    studyitemLL.setClickable(true);
                    studyitemLL.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.nav_ripple));


                    if (list.get(position - 1).getIs_locked().equalsIgnoreCase("1")) {
                       /* Intent intent = new Intent(activity, PurchaseActivity.class);
                        intent.putExtra("single_study", new Gson().toJson(singleStudy));
                        Helper.gotoActivity(intent, activity);*/

                        if (study != null) {
                            study.buynow();
                        }

                        return;
                    }

                    if (isSkip.equalsIgnoreCase("1") || isSkip.equalsIgnoreCase("2")) {
                        Intent examPrepLayer1 = new Intent(activity, CourseActivity.class);
                     /*   examPrepLayer1.putExtra(Const.SINGLE_STUDY_DATA, new Gson().toJson(singleStudy));
                        examPrepLayer1.putExtra(Const.EXAMPREP, examPrepItem);
*/
                        Constants.examPrepItemNew =examPrepItem;
                        Constants.courseDetail =singleStudy;



                        examPrepLayer1.putExtra(Const.FRAG_TYPE, Const.EXAMPREPLAST);
                        examPrepLayer1.putExtra(Const.IS_COMBO, isCombo);
                        examPrepLayer1.putExtra(Const.LIST_SUBJECT, examPrepItem.getList().get(position - 1));

                        examPrepLayer1.putExtra(Const.TITLE, examPrepItem.getList().get(position - 1).getTitle());
                        examPrepLayer1.putExtra(Const.CONTENT_TYPE, contentType);
                        examPrepLayer1.putExtra(Const.TEST_TYPE, examPrepItem.getList().get(position - 1).getCount());
                        examPrepLayer1.putExtra(Const.LIST, examPrepItem.getList().get(position - 1));
                        examPrepLayer1.putExtra(Const.TILE_ID, tileIdAPI);
                        examPrepLayer1.putExtra(Const.TILE_TYPE, tileTypeAPI);
                        examPrepLayer1.putExtra(Const.REVERT_API, revertAPI);
                        Helper.gotoActivity(examPrepLayer1, activity);
                    } else if (isSkip.equalsIgnoreCase("3")) {
                        if (is_purchase.equalsIgnoreCase("1")) {
                            if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("12")) {
                                if (examPrepItem.getList().size() > 0) {
                                    Intent intent = new Intent(activity, RevisionActivity.class);
                                    intent.putExtra("t_id", tileIdAPI);
                                    intent.putExtra("postion", position);
                                    intent.putExtra("video_id", examPrepItem.getList().get(position - 1).getId());
                                    intent.putExtra("v_list", actual_videolist);
                                    intent.putExtra("f_id", examPrepItem.getList().get(0).getId());
                                    intent.putExtra("c_id", singleStudy.getData().getCourseDetail().getId());
                                    intent.putExtra("title", examPrepItem.getList().get(position - 1).getTitle());
                                    intent.putExtra("url", examPrepItem.getList().get(position - 1).getFile_url());
                                    intent.putExtra("via", "main");
                                    Helper.gotoActivity(intent, activity);
                                }
                            } else if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("11")) {
                                Intent intent4 = new Intent(activity, WebViewActivty.class);
                                intent4.putExtra("type", examPrepItem.getList().get(position - 1).getTitle());
                                intent4.putExtra("url", examPrepItem.getList().get(position - 1).getFile_url());
                                Helper.gotoActivity(intent4, activity);
                            } else if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("8")) {
                                Intent intent4 = new Intent(activity, WebViewActivty.class);
                                intent4.putExtra("type", examPrepItem.getList().get(position - 1).getTitle());
                                intent4.putExtra("url", examPrepItem.getList().get(position - 1).getFile_url());
                                if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                                    intent4.putExtra("course_id", singleStudy.getData().getCourseDetail().getId() + "#");

                                } else {
                                    intent4.putExtra("course_id", SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());

                                }
                                intent4.putExtra("video_id", examPrepItem.getList().get(position - 1).getId());
                                intent4.putExtra("link", examPrepItem.getList().get(position - 1).getFile_type());
                                Helper.gotoActivity(intent4, activity);
                            } else if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("6")) {
                                if (TextUtils.isEmpty(examPrepItem.getList().get(position - 1).getFile_url()) && TextUtils.isEmpty(examPrepItem.getList().get(position - 1).getId())) {
                                    Toast.makeText(activity, "No image found", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Intent intent = new Intent(activity, WebViewActivty.class);
                                intent.putExtra("type", examPrepItem.getList().get(position - 1).getTitle());
                                intent.putExtra("url", examPrepItem.getList().get(position - 1).getFile_url());
                                intent.putExtra("file_type", "image");
                                Helper.gotoActivity(intent, activity);
                            }
                        } else {
                            if (examPrepItem.getList().get(position - 1).getIs_locked().equalsIgnoreCase("1")) {

                                /*Intent intent = new Intent(activity, PurchaseActivity.class);
                                intent.putExtra("single_study", new Gson().toJson(singleStudy));
                                Helper.gotoActivity(intent, activity);*/

                                if (study != null) {
                                    study.buynow();
                                }

                            } else {
                                if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("12")) {
                                    if (examPrepItem.getList().size() > 0) {
                                        Intent intent = new Intent(activity, RevisionActivity.class);
                                        intent.putExtra("t_id", tileIdAPI);
                                        intent.putExtra("postion", position);
                                        intent.putExtra("video_id", examPrepItem.getList().get(position - 1).getId());
                                        intent.putExtra("v_list", actual_videolist);
                                        intent.putExtra("f_id", examPrepItem.getList().get(0).getId());
                                        intent.putExtra("c_id", singleStudy.getData().getCourseDetail().getId());
                                        intent.putExtra("title", examPrepItem.getList().get(position - 1).getTitle());
                                        intent.putExtra("url", examPrepItem.getList().get(position - 1).getFile_url());
                                        intent.putExtra("via", "main");
                                        Helper.gotoActivity(intent, activity);
                                    }

                                } else if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("11")) {
                                    Intent intent4 = new Intent(activity, WebViewActivty.class);
                                    intent4.putExtra("type", examPrepItem.getList().get(position - 1).getTitle());
                                    intent4.putExtra("url", examPrepItem.getList().get(position - 1).getFile_url());
                                    Helper.gotoActivity(intent4, activity);
                                } else if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("8")) {
                                    Intent intent4 = new Intent(activity, WebViewActivty.class);
                                    intent4.putExtra("type", examPrepItem.getList().get(position - 1).getTitle());
                                    intent4.putExtra("url", examPrepItem.getList().get(position - 1).getFile_url());
                                    if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                                        intent4.putExtra("course_id", singleStudy.getData().getCourseDetail().getId() + "#");

                                    } else {
                                        intent4.putExtra("course_id", SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());

                                    }
                                    intent4.putExtra("video_id", examPrepItem.getList().get(position - 1).getId());
                                    intent4.putExtra("link", examPrepItem.getList().get(position - 1).getFile_type());
                                    Helper.gotoActivity(intent4, activity);
                                } else if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("6")) {
                                    if (TextUtils.isEmpty(examPrepItem.getList().get(position - 1).getFile_url()) && TextUtils.isEmpty(examPrepItem.getList().get(position - 1).getId())) {
                                        Toast.makeText(activity, "No image found", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    Intent intent = new Intent(activity, WebViewActivty.class);
                                    intent.putExtra("type", examPrepItem.getList().get(position - 1).getTitle());
                                    intent.putExtra("url", examPrepItem.getList().get(position - 1).getFile_url());
                                    intent.putExtra("file_type", "image");
                                    Helper.gotoActivity(intent, activity);
                                } else if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("3")) {
                                    if (is_purchase.equalsIgnoreCase("1")) {
                                        if (TextUtils.isEmpty(examPrepItem.getList().get(position - 1).getFile_url()) && TextUtils.isEmpty(examPrepItem.getList().get(position - 1).getId())) {
                                            Toast.makeText(activity, "No video found!", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        Helper.audio_service_close((CourseActivity) activity);
                                        API_REQUEST_VIDEO_LINK(examPrepItem.getList().get(position - 1), position - 1);

                                    } else {
                                        if (examPrepItem.getList().get(position - 1).getIs_locked().equalsIgnoreCase("1")) {

                                          /*  Intent intent = new Intent(activity, PurchaseActivity.class);
                                            intent.putExtra("single_study", new Gson().toJson(singleStudy));
                                            Helper.gotoActivity(intent, activity);*/

                                            if (study != null) {
                                                study.buynow();
                                            }
                                        } else {
                                            if (TextUtils.isEmpty(examPrepItem.getList().get(position - 1).getFile_url()) && TextUtils.isEmpty(examPrepItem.getList().get(position - 1).getId())) {
                                                Toast.makeText(activity, "No video found!", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            Helper.audio_service_close((CourseActivity) activity);
                                            API_REQUEST_VIDEO_LINK(examPrepItem.getList().get(position - 1), position - 1);
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        ExamPrepItem examPrepItemNew = new ExamPrepItem();
                        examPrepItemNew.setList(examPrepItem.getList().get(position - 1).getList());
                        Intent examPrepLayer1 = new Intent(activity, CourseActivity.class);

                        Constants.examPrepItemNew =examPrepItemNew;
                        Constants.courseDetail =singleStudy;

                        examPrepLayer1.putExtra(Const.FRAG_TYPE, Const.EXAMPREP);
                        examPrepLayer1.putExtra(Const.IS_COMBO, isCombo);
                        examPrepLayer1.putExtra(Const.CONTENT_TYPE, contentType);
                        examPrepLayer1.putExtra(Const.TITLE, singleStudy.getData().getCourseDetail() != null ? singleStudy.getData().getCourseDetail().getTitle() : "Course");
                        examPrepLayer1.putExtra(Const.LIST, list.get(position - 1));
                        examPrepLayer1.putExtra(Const.TILE_ID, tileIdAPI);
                        examPrepLayer1.putExtra(Const.TILE_TYPE, tileTypeAPI);
                        examPrepLayer1.putExtra(Const.REVERT_API, revertAPI);
                        Helper.gotoActivity(examPrepLayer1, activity);
                    }
                });
            } else {
                ibt_single_sub_vd_RL.setVisibility(View.GONE);
                no_data_found_RL.setVisibility(View.VISIBLE);

                backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.finish();
                    }
                });
            }
        }

    }

    public class StudyNoDataViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout no_data_found_RL;
        Button backBtn;

        public StudyNoDataViewHolder(View itemView) {
            super(itemView);
            no_data_found_RL = itemView.findViewById(R.id.no_data_found_RL);
            backBtn = itemView.findViewById(R.id.backBtn);
        }

        public void setData() {
            no_data_found_RL.setVisibility(View.VISIBLE);

            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.finish();
                }
            });
        }
    }

    private void delete_meg(Lists video) {
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
            NetworkCall networkCall = new NetworkCall(SingleStudyAdapter.this, activity);
            networkCall.NetworkAPICall(API.delete_revision, "", true, false);
            dialog.dismiss();
            dialog.cancel();
        });
        btn_cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }


    /*        private void download_data(Lists lists, int position) {
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(activity);
                alertDialogBuilder.setTitle("Download Video?");
                alertDialogBuilder.setMessage(lists.getTitle()+".\nThis video will be downloaded in your local storage.\n");
                alertDialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
                alertDialogBuilder.setPositiveButton("Download", (dialog, which) -> {
                    download_service(lists,position);
                    dialog.dismiss();
                    dialog.cancel();
                });
                android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }*/
    @SuppressLint("SetTextI18n")
    private void download_data(Lists lists, int position) {
        final Dialog dialog = new Dialog(activity);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        dialog.setContentView(R.layout.dialog_alert_simple);
        TextView titleDialog = dialog.findViewById(R.id.titleDialog);
        titleDialog.setText("Download Video?");
        TextView msgDialog = dialog.findViewById(R.id.msgDialog);
        msgDialog.setText(lists.getTitle() + ".\nThis video will be downloaded in your local storage.\n");
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        btn_cancel.setText("CANCEL");
        Button btn_submit = dialog.findViewById(R.id.btn_submit);
        btn_submit.setText("DOWNLOAD");
        btn_submit.setOnClickListener(v -> {
            if (is_purchase.equalsIgnoreCase("1") && !is_download) {
                videodata = examPrepItem.getList().get(position - 1);
                position_delete = position;
                is_download = true;
                NetworkCall networkCall = new NetworkCall(SingleStudyAdapter.this, activity);
                networkCall.NetworkAPICall(API.get_video_link, "", true, false);

            } else {
                is_download = false;
                Toast.makeText(activity, "This feature is only available for courses in your library ", Toast.LENGTH_SHORT).show();
            }

            dialog.dismiss();
            dialog.cancel();
        });
        btn_cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    private void download_service(Lists video, int position, String link) {
        VideosDownload videosDownload = new VideosDownload();
        if (utkashRoom.getvideoDownloadao().isvideo_exit(video.getId(), MakeMyExam.userId)) {
            videosDownload = utkashRoom.getvideoDownloadao().getvideo_byuserid(video.getId(), MakeMyExam.userId);
            videosDownload.setJw_url(link);
            videosDownload.setVideo_status("Download Running");
            videosDownload.setThumbnail_url(video.getThumbnail_url());


        } else {
            videosDownload.setVideo_id(video.getId());
            if (video.getFile_url().contains("jwplayer") || video.getFile_url().contains("jwplatform")) {
                videosDownload.setJw_url(link);
                videosDownload.setThumbnail_url(video.getThumbnail_url());
            }

            String courze_id = "";

            if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                courze_id = singleStudy.getData().getCourseDetail().getId() + "#";

            } else {
                courze_id = SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId();
            }


            videosDownload.setVideo_name(video.getTitle());
            videosDownload.setToal_downloadlocale(0L);
            videosDownload.setPercentage(0);
            videosDownload.setOriginalFileLengthString("0");
            videosDownload.setLengthInMb("");
            videosDownload.setVideo_history(video.getId() + "_" + MakeMyExam.userId + "_" + video.getId() + "_" + courze_id);
            videosDownload.setCourse_id(courze_id);
            videosDownload.setIs_complete("0");
            videosDownload.setVideo_status("Download Running");
            videosDownload.setUser_id(MakeMyExam.userId);
            videosDownload.setVideo_token(video.getBitrate_urls().get(0).getToken() == null ? "" : video.getBitrate_urls().get(0).getToken());

            videosDownload.setVideoCurrentPosition(0L);
        }

        if (video.getBitrate_urls() != null && video.getBitrate_urls().size() > 0) {
            openwatchlist_dailog_resource(activity, video.getBitrate_urls(), videosDownload, position);
        }


    }


    public void openwatchlist_dailog_resource(Context context, ArrayList<UrlObject> mediaResponseMap, VideosDownload videosDownload, int pos) {
        try {

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
            Objects.requireNonNull(tvResName).setText(videosDownload.getVideo_name());
            RecyclerView qualityrecycler = watchlist.findViewById(R.id.qualityrecycler);

            if (mediaResponseMap.size() > 0) {
                Objects.requireNonNull(qualityrecycler).setVisibility(View.VISIBLE);
                DownloadQualityAdapter downloadQualityAdapter = new DownloadQualityAdapter(activity, mediaResponseMap, this, pos, videosDownload);
                qualityrecycler.setAdapter(downloadQualityAdapter);
            }


          /*  if (mediaResponseMap.size() >= 3) {
                Objects.requireNonNull(btnLow).setVisibility(View.VISIBLE);
                Objects.requireNonNull(btnMedium).setVisibility(View.VISIBLE);
                Objects.requireNonNull(btnHigh).setVisibility(View.VISIBLE);

            } else if (mediaResponseMap.size() == 2) {
                Objects.requireNonNull(btnLow).setVisibility(View.VISIBLE);
                Objects.requireNonNull(btnMedium).setVisibility(View.VISIBLE);
            } else if (mediaResponseMap.size() == 1) {
                Objects.requireNonNull(btnLow).setVisibility(View.VISIBLE);

            }
*/
            Objects.requireNonNull(lnPreparing).setVisibility(View.GONE);


            Objects.requireNonNull(btnLow).setOnClickListener(new OnSingleClickListener(() -> {
                if (!utkashRoom.getOpenHelper().getWritableDatabase().isDbLockedByCurrentThread()) {
                    if (Helper.isNetworkConnected(activity)) {
                        examPrepItem.getList().get(pos - 1).setVideo_status("Download Running");
                        if (utkashRoom.getvideoDownloadao().isvideo_exit(videosDownload.getVideo_id(), MakeMyExam.userId)) {
                            utkashRoom.getvideoDownloadao().update_videostatus(videosDownload.getVideo_id(), videosDownload.getVideo_status(), MakeMyExam.userId);
                        } else {
                            utkashRoom.getvideoDownloadao().addUser(videosDownload);
                        }
                        notifyItemChanged(pos);
                        Download_dialog(mediaResponseMap.get(0).getUrl(), videosDownload);
                        dismissCalculatorDialog(watchlist);
                    }

                }
                return null;
            }));
            Objects.requireNonNull(btnMedium).setOnClickListener(new OnSingleClickListener(() -> {
                if (!utkashRoom.getOpenHelper().getWritableDatabase().isDbLockedByCurrentThread()) {
                    if (Helper.isNetworkConnected(activity)) {
                        examPrepItem.getList().get(pos - 1).setVideo_status("Download Running");
                        if (utkashRoom.getvideoDownloadao().isvideo_exit(videosDownload.getVideo_id(), MakeMyExam.userId)) {
                            utkashRoom.getvideoDownloadao().update_videostatus(videosDownload.getVideo_id(), videosDownload.getVideo_status(), MakeMyExam.userId);
                        } else {
                            utkashRoom.getvideoDownloadao().addUser(videosDownload);
                        }
                        notifyItemChanged(pos);
                        Download_dialog(mediaResponseMap.get(1).getUrl(), videosDownload);
                        dismissCalculatorDialog(watchlist);
                    }

                }
                return null;
            }));
            Objects.requireNonNull(btnHigh).setOnClickListener(new OnSingleClickListener(() -> {
                if (!utkashRoom.getOpenHelper().getWritableDatabase().isDbLockedByCurrentThread()) {

                    if (Helper.isNetworkConnected(activity)) {
                        examPrepItem.getList().get(pos - 1).setVideo_status("Download Running");
                        if (utkashRoom.getvideoDownloadao().isvideo_exit(videosDownload.getVideo_id(), MakeMyExam.userId)) {
                            utkashRoom.getvideoDownloadao().update_videostatus(videosDownload.getVideo_id(), videosDownload.getVideo_status(), MakeMyExam.userId);
                        } else {
                            utkashRoom.getvideoDownloadao().addUser(videosDownload);
                        }
                        notifyItemChanged(pos);
                        Download_dialog(mediaResponseMap.get(2).getUrl(), videosDownload);
                        dismissCalculatorDialog(watchlist);
                    }
                }
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

    private void Download_dialog(String url, VideosDownload videosDownload) {
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
    }


    private void dismissCalculatorDialog(BottomSheetDialog watchlist) {
        if (watchlist != null && watchlist.isShowing()) {
            watchlist.dismiss();
            watchlist.cancel();
            watchlist = null;
        }
    }

    private void audio_play(String mediaId, Lists lists, String objectcookie) {
        if (!mediaId.equalsIgnoreCase("")) {
            if (!utkashRoom.getaudiodao().isvideo_exit(lists.getId(), MakeMyExam.userId)) {
                AudioTable audioTable = new AudioTable();
                audioTable.setVideo_id(lists.getId());
                audioTable.setJw_url(mediaId);
                audioTable.setVideo_name(lists.getTitle());
                audioTable.setAudio_currentpos(0L);
                audioTable.setUser_id(MakeMyExam.userId);
                if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                    audioTable.setCourse_id(singleStudy.getData().getCourseDetail().getId() + "#");
                } else {
                    audioTable.setCourse_id(SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());
                }

                utkashRoom.getaudiodao().addUser(audioTable);
                extractJWPlayerUrl(mediaId, audioTable, lists, objectcookie);
            } else {
                AudioTable audioTable = new AudioTable();
                audioTable.setVideo_id(lists.getId());
                audioTable.setJw_url(mediaId);
                audioTable.setVideo_name(lists.getTitle());
                audioTable.setAudio_currentpos(utkashRoom.getaudiodao().getuser(lists.getId(), MakeMyExam.userId).getAudio_currentpos());
                extractJWPlayerUrl(mediaId, audioTable, lists, objectcookie);
            }
        }

    }

    public void extractJWPlayerUrl(String mediaUrl, AudioTable videoData, Lists lists, String objectcookie) {
        if (mediaUrl != null) {
            Intent intent = new Intent(activity, AudioPlayerActivty.class);
            AudioPlayerService.videoid = "";
            AudioPlayerService.media_id = "";
            intent.putExtra("url", mediaUrl);
            intent.putExtra("videoid", videoData.getVideo_id());
            intent.putExtra("currentpos", videoData.getAudio_currentpos());
            intent.putExtra("video_name", videoData.getVideo_name());
            intent.putExtra("image_url", lists.getThumbnail_url());
            intent.putExtra("objectcookie", objectcookie);
            if (SingleStudy.parentCourseId.equalsIgnoreCase("")) {
                intent.putExtra("course_id", singleStudy.getData().getCourseDetail().getId() + "#");
            } else {
                intent.putExtra("course_id", SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());
            }

            Helper.gotoActivity(intent, activity);
        }

    }
//
//    private void db_insert_video(String mediaId, Lists lists) {
//
//        if (utkashRoom.getvideoDownloadao().isvideo_exit(lists.getId(), MakeMyExam.userId)) {
//            VideosDownload videoDownload = utkashRoom.getvideoDownloadao().getvideo_byuserid(lists.getId(), MakeMyExam.userId);
//            if (videoDownload.getIs_complete().equalsIgnoreCase("1")) {
//                Intent i = new Intent(activity, DownloadVideoPlayer.class);
//                i.putExtra("video_name", videoDownload.getVideo_name());
//                i.putExtra("video_id", videoDownload.getVideo_id());
//                i.putExtra("current_pos", videoDownload.getVideoCurrentPosition());
//                i.putExtra("video", videoDownload.getVideo_history());
//                i.putExtra("video_time", videoDownload.getVideotime());
//                i.putExtra("course_id", videoDownload.getCourse_id());
//
//
//                activity.startActivity(i);
//            } else {
//                if (utkashRoom.getvideoDao().isvideo_exit(lists.getId(), MakeMyExam.userId)) {
//                    if (SingleStudy.parentCourseId.equalsIgnoreCase(""))
//                    {
//                        Helper.GoToJWVideo_Params(activity, mediaId, lists.getId(), lists.getTitle(), utkashRoom.getvideoDao().getuser(lists.getId(), MakeMyExam.userId).getVideo_currentpos(),singleStudy.getData().getCourseDetail().getId()+"#");
//
//                    }else {
//                        Helper.GoToJWVideo_Params(activity, mediaId, lists.getId(), lists.getTitle(), utkashRoom.getvideoDao().getuser(lists.getId(), MakeMyExam.userId).getVideo_currentpos(),SingleStudy.parentCourseId+"#"+singleStudy.getData().getCourseDetail().getId());
//
//                    }
//
//
//
//                } else {
//                    VideoTable videoTable = new VideoTable();
//                    videoTable.setVideo_id(lists.getId());
//                    videoTable.setVideo_name(lists.getTitle());
//                    videoTable.setJw_url(mediaId);
//                    videoTable.setVideo_currentpos(0L);
//                    videoTable.setUser_id(MakeMyExam.userId);
//                    videoTable.setCourse_id(SingleStudy.parentCourseId+"#"+singleStudy.getData().getCourseDetail().getId());
//
//                    utkashRoom.getvideoDao().addUser(videoTable);
//                    Helper.GoToJWVideo_Params(activity, mediaId, lists.getId(), lists.getTitle(), 0L,SingleStudy.parentCourseId+"#"+singleStudy.getData().getCourseDetail().getId());
//                }
//            }
//        } else if (utkashRoom.getvideoDao().isvideo_exit(lists.getId(), MakeMyExam.userId)) {
//            Helper.GoToJWVideo_Params(activity, mediaId, lists.getId(), lists.getTitle(), utkashRoom.getvideoDao().getuser(lists.getId(), MakeMyExam.userId).getVideo_currentpos(),SingleStudy.parentCourseId+"#"+singleStudy.getData().getCourseDetail().getId());
//        } else {
//
//            VideoTable videoTable = new VideoTable();
//            videoTable.setVideo_id(lists.getId());
//            videoTable.setVideo_name(lists.getTitle());
//            videoTable.setJw_url(mediaId);
//            videoTable.setVideo_currentpos(0L);
//            videoTable.setUser_id(MakeMyExam.userId);
//            videoTable.setCourse_id(SingleStudy.parentCourseId+"#"+singleStudy.getData().getCourseDetail().getId());
//
//            utkashRoom.getvideoDao().addUser(videoTable);
//            Helper.GoToJWVideo_Params(activity, mediaId, lists.getId(), lists.getTitle(), 0L,SingleStudy.parentCourseId+"#"+singleStudy.getData().getCourseDetail().getId());
//        }
//    }

    private void API_REQUEST_VIDEO_LINK(final Lists videoData, int position) {
        is_audio = false;
        if (videoData.getFile_type().equalsIgnoreCase("10")) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + videoData.getFile_url())));
        } else if (videoData.getVideo_type().equalsIgnoreCase("5")) {
            if (videoData.getLive_status().equalsIgnoreCase("1")) {
                if (videoData.getId() == null ||
                        videoData.getId().equalsIgnoreCase("")) {
                    Toast.makeText(activity, "Url is not found", Toast.LENGTH_SHORT).show();
                } else {
                    videodata = videoData;
                    NetworkCall networkCall = new NetworkCall(SingleStudyAdapter.this, activity);
                    networkCall.NetworkAPICall(API.get_video_link, "", true, false);


                    // Helper.GoToLiveAwsVideoActivity(videoData.getVideo_type(), videoData.getChat_node(), activity, videoData.getId(), videoData.getVideo_type(), videoData.getId(), videoData.getTitle(), "0", videoData.getThumbnail_url(), videoData.getPayload().getCourse_id(), videoData.getPayload().getTile_id(), videoData.getPayload().getTile_type(), videoData.getIs_chat_lock(), String.valueOf(position), SingleStudy.parentCourseId);
                    //  Helper.GoToLiveAwsVideoActivity(videoData.getVideo_type(), videoData.getChat_node(), activity, videoData.getId(), videoData.getVideo_type(), videoData.getId(), videoData.getTitle(), "0", videoData.getThumbnail_url(), videoData.getPayload().getCourse_id(), videoData.getPayload().getTile_id(), videoData.getPayload().getTile_type(), videoData.getIs_chat_lock(), String.valueOf(position), SingleStudy.parentCourseId);

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
            NetworkCall networkCall = new NetworkCall(SingleStudyAdapter.this, activity);
            networkCall.NetworkAPICall(API.get_video_link, "", true, false);


            //  Helper.GoToLiveAwsVideoActivity(videoData.getVideo_type(), videoData.getChat_node(), activity, videoData.getId(), videoData.getVideo_type(), videoData.getId(), videoData.getTitle(), "0", videoData.getThumbnail_url(), videoData.getPayload().getCourse_id(), videoData.getPayload().getTile_id(), videoData.getPayload().getTile_type(), videoData.getIs_chat_lock(), String.valueOf(position), SingleStudy.parentCourseId);
        } else if (videoData.getVideo_type().equalsIgnoreCase("6")) {
            try {

                videodata = videoData;
                NetworkCall networkCall = new NetworkCall(SingleStudyAdapter.this, activity);
                networkCall.NetworkAPICall(API.get_video_link, "", true, false);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (videoData.getVideo_type().equalsIgnoreCase("1")) {
            Helper.audio_service_close((CourseActivity) activity);
            if (videoData.getOpen_in_app() != null && videoData.getOpen_in_app().equalsIgnoreCase("1")) {
                Helper.GoToLiveVideoActivity(videoData.getChat_node(), activity, videoData.getFile_url(), videoData.getVideo_type(), videoData.getId(), videoData.getTitle(), "0", videoData.getThumbnail_url(), videoData.getIs_chat_lock(), videoData.getPayload().getCourse_id(), String.valueOf(position), SingleStudy.parentCourseId, videoData.getPayload().getTile_id(), videoData.getPayload().getTile_type());
            } else {
                videodata = videoData;
                NetworkCall networkCall = new NetworkCall(SingleStudyAdapter.this, activity);
                networkCall.NetworkAPICall(API.get_video_link, "", true, false);


                //activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + videoData.getFile_url())));
            }

        } else if (videoData.getVideo_type().equalsIgnoreCase("4")) {
            Helper.audio_service_close((CourseActivity) activity);

            if (videoData.getLive_status().equalsIgnoreCase("1")) {
                if (videoData.getFile_url() == null ||
                        videoData.getFile_url().equalsIgnoreCase("")) {
                    Toast.makeText(activity, "Url is not found", Toast.LENGTH_SHORT).show();
                } else {

                    if (videoData.getOpen_in_app() != null && videoData.getOpen_in_app().equalsIgnoreCase("1")) {
                        Helper.GoToLiveVideoActivity(videoData.getChat_node(), activity, videoData.getFile_url(), videoData.getVideo_type(), videoData.getId(), videoData.getTitle(), "0", videoData.getThumbnail_url(), videoData.getIs_chat_lock(), videoData.getPayload().getCourse_id(), String.valueOf(position), SingleStudy.parentCourseId, videoData.getPayload().getTile_id(), videoData.getPayload().getTile_type());
                    } else {
                        videodata = videoData;
                        NetworkCall networkCall = new NetworkCall(SingleStudyAdapter.this, activity);
                        networkCall.NetworkAPICall(API.get_video_link, "", true, false);

                        //activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + videoData.getFile_url())));
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

    public class SingleStudyPdfListHolder extends RecyclerView.ViewHolder {
        RelativeLayout studyitemLL, lockRL;
        ImageView imageIcon;
        TextView titleCategory, read_now, share;
        GifImageView liveIV;
        RelativeLayout no_data_found_RL;
        Button backBtn;
        CardView ibt_single_sub_vd_RL;

        public SingleStudyPdfListHolder(View itemView) {
            super(itemView);
            lockRL = itemView.findViewById(R.id.lockRL);
            read_now = itemView.findViewById(R.id.read_now);
            share = itemView.findViewById(R.id.share);
            liveIV = itemView.findViewById(R.id.liveIV);
            studyitemLL = itemView.findViewById(R.id.study_single_itemLL);
            imageIcon = itemView.findViewById(R.id.imageIV);
            titleCategory = itemView.findViewById(R.id.examPrepTitleTV);
            no_data_found_RL = itemView.findViewById(R.id.no_data_found_RL);
            backBtn = itemView.findViewById(R.id.backBtn);
            ibt_single_sub_vd_RL = itemView.findViewById(R.id.ibt_single_sub_vd_RL);
        }

        public void setData(final ArrayList<Lists> list, final int position) {

            if (list != null && list.size() > 0) {
                ibt_single_sub_vd_RL.setVisibility(View.VISIBLE);
                no_data_found_RL.setVisibility(View.GONE);

                if (list.get(position - 1).getIs_live() != null) {
                    if (list.get(position - 1).getIs_live().equals("1")) {
                        liveIV.setVisibility(View.VISIBLE);
                    } else {
                        liveIV.setVisibility(View.GONE);
                    }
                } else {
                    liveIV.setVisibility(View.GONE);
                }

                titleCategory.setText(list.get(position - 1).getTitle());

                if (TextUtils.isEmpty(list.get(position - 1).getIs_locked())) {
                    list.get(position - 1).setIs_locked("0");
                }
                if (singleStudy != null && singleStudy.getData().getCourseDetail() != null) {
                    if (singleStudy.getData().getCourseDetail().getIsPurchased().equals("1")) {
                        list.get(position - 1).setIs_locked("0");
                    }
                }
                if (list.get(position - 1).getIs_locked().equals("0")) {
                    lockRL.setVisibility(View.GONE);
                } else {
                    lockRL.setVisibility(View.VISIBLE);
                }
                if (isSkip.equalsIgnoreCase("3")) {
                    share.setVisibility(View.VISIBLE);
                    read_now.setEnabled(true);
                    studyitemLL.setEnabled(true);
                    read_now.setVisibility(View.VISIBLE);
                } else {
                    share.setVisibility(View.GONE);

                    studyitemLL.setEnabled(true);
                    read_now.setVisibility(View.GONE);
                }
                share.setOnClickListener(v -> {
                    if (is_purchase.equalsIgnoreCase("1")) {
                        if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("8")
                                || examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("7")
                                || examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("1")

                        ) {
                            Helper.sharePdf(activity, examPrepItem.getList().get(position - 1).getPayload().getCourse_id(), examPrepItem.getList().get(position - 1).getId(), examPrepItem.getList().get(position - 1).getPayload().getTopic_id(), examPrepItem.getList().get(position - 1).getPayload().getTile_type(), examPrepItem.getList().get(position - 1).getPayload().getTile_id(), examPrepItem.getList().get(position - 1).getPayload().getRevert_api(), "pdf", examPrepItem.getList().get(position - 1).getThumbnail_url(), examPrepItem.getList().get(position - 1).getTitle(), SingleStudy.parentCourseId);

                        }
                    } else {
                        if (examPrepItem.getList().get(position - 1).getIs_locked().equalsIgnoreCase("1")) {
                           /* Intent intent = new Intent(activity, PurchaseActivity.class);
                            intent.putExtra("single_study", new Gson().toJson(singleStudy));
                            Helper.gotoActivity(intent, activity);*/

                            if (study != null) {
                                study.buynow();
                            }
                        } else {
                            if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("8")
                                    || examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("7")
                                    || examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("1")

                            ) {
                                Helper.sharePdf(activity, examPrepItem.getList().get(position - 1).getPayload().getCourse_id(), examPrepItem.getList().get(position - 1).getId(), examPrepItem.getList().get(position - 1).getPayload().getTopic_id(), examPrepItem.getList().get(position - 1).getPayload().getTile_type(), examPrepItem.getList().get(position - 1).getPayload().getTile_id(), examPrepItem.getList().get(position - 1).getPayload().getRevert_api(), "pdf", examPrepItem.getList().get(position - 1).getThumbnail_url(), examPrepItem.getList().get(position - 1).getTitle(), SingleStudy.parentCourseId);
                            }
                        }
                    }
                });

                read_now.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (list.get(position - 1).getIs_locked().equalsIgnoreCase("1")) {
                           /* Intent intent = new Intent(activity, PurchaseActivity.class);
                            intent.putExtra("single_study", new Gson().toJson(singleStudy));
                            Helper.gotoActivity(intent, activity);*/
                            if (study != null) {
                                study.buynow();
                            }
                            return;
                        }

                        if (is_purchase.equalsIgnoreCase("1")) {
                            if (!examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("7"))
                                if (TextUtils.isEmpty(examPrepItem.getList().get(position - 1).getFile_url()) || TextUtils.isEmpty(examPrepItem.getList().get(position - 1).getId())) {
                                    Toast.makeText(activity, "No pdf found!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("8")) {
                                Intent intent4 = new Intent(activity, WebViewActivty.class);
                                intent4.putExtra("type", examPrepItem.getList().get(position - 1).getTitle());
                                intent4.putExtra("url", examPrepItem.getList().get(position - 1).getFile_url());
                                intent4.putExtra("course_id", SingleStudy.parentCourseId.equalsIgnoreCase("") ? singleStudy.getData().getCourseDetail().getId() + "#" : SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());
                                intent4.putExtra("video_id", examPrepItem.getList().get(position - 1).getId());
                                intent4.putExtra("link", examPrepItem.getList().get(position - 1).getFile_type());
                                Helper.gotoActivity(intent4, activity);
                            } else if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("1")) {
                                boolean isDownload = false;
                                if (!TextUtils.isEmpty(examPrepItem.getList().get(position - 1).getIs_download_available()) && examPrepItem.getList().get(position - 1).getIs_download_available().equalsIgnoreCase("1")) {
                                    isDownload = true;
                                } else {
                                    isDownload = false;
                                }
                                Helper.GoToWebViewPDFActivity(activity, examPrepItem.getList().get(position - 1).getId(), examPrepItem.getList().get(position - 1).getFile_url(), isDownload, examPrepItem.getList().get(position - 1).getTitle(), SingleStudy.parentCourseId.equalsIgnoreCase("") ? singleStudy.getData().getCourseDetail().getId() + "#" : SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());
                            }
                        } else {
                            if (examPrepItem.getList().get(position - 1).getIs_locked().equalsIgnoreCase("1")) {

                                /*Intent intent = new Intent(activity, PurchaseActivity.class);
                                intent.putExtra("single_study", new Gson().toJson(singleStudy));
                                Helper.gotoActivity(intent, activity);*/

                                if (study != null) {
                                    study.buynow();
                                }
                            } else {
                                if (!examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("7"))
                                    if (TextUtils.isEmpty(examPrepItem.getList().get(position - 1).getFile_url()) || TextUtils.isEmpty(examPrepItem.getList().get(position - 1).getId())) {
                                        Toast.makeText(activity, "No pdf found!", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("8")) {
                                    Intent intent4 = new Intent(activity, WebViewActivty.class);
                                    intent4.putExtra("type", examPrepItem.getList().get(position - 1).getTitle());
                                    intent4.putExtra("url", examPrepItem.getList().get(position - 1).getFile_url());
                                    intent4.putExtra("course_id", SingleStudy.parentCourseId.equalsIgnoreCase("") ? singleStudy.getData().getCourseDetail().getId() + "#" : SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());
                                    intent4.putExtra("video_id", examPrepItem.getList().get(position - 1).getId());
                                    intent4.putExtra("link", examPrepItem.getList().get(position - 1).getFile_type());
                                    Helper.gotoActivity(intent4, activity);
                                } else if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("1")) {
                                    boolean isDownload = false;
                                    if (!TextUtils.isEmpty(examPrepItem.getList().get(position - 1).getIs_download_available()) && examPrepItem.getList().get(position - 1).getIs_download_available().equalsIgnoreCase("1")) {
                                        isDownload = true;
                                    } else {
                                        isDownload = false;
                                    }
                                    Helper.GoToWebViewPDFActivity(activity, examPrepItem.getList().get(position - 1).getId(), examPrepItem.getList().get(position - 1).getFile_url(), isDownload, examPrepItem.getList().get(position - 1).getTitle(), SingleStudy.parentCourseId.equalsIgnoreCase("") ? singleStudy.getData().getCourseDetail().getId() + "#" : SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());

                                }
                            }
                        }
                    }
                });

                studyitemLL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (list.get(position - 1).getIs_locked().equalsIgnoreCase("1")) {
                            /*Intent intent = new Intent(activity, PurchaseActivity.class);
                            intent.putExtra("single_study", new Gson().toJson(singleStudy));
                            Helper.gotoActivity(intent, activity);*/

                            if (study != null) {
                                study.buynow();
                            }
                            return;
                        }

                        if (isSkip.equalsIgnoreCase("1") || isSkip.equalsIgnoreCase("2")) {
                            Intent examPrepLayer1 = new Intent(activity, CourseActivity.class);
                         /*   examPrepLayer1.putExtra(Const.SINGLE_STUDY_DATA, new Gson().toJson(singleStudy));
                            examPrepLayer1.putExtra(Const.EXAMPREP, examPrepItem);
*/
                            Constants.examPrepItemNew =examPrepItem;
                            Constants.courseDetail =singleStudy;


                            examPrepLayer1.putExtra(Const.FRAG_TYPE, Const.EXAMPREPLAST);
                            examPrepLayer1.putExtra(Const.LIST_SUBJECT, examPrepItem.getList().get(position - 1));

                            examPrepLayer1.putExtra(Const.IS_COMBO, isCombo);
                            examPrepLayer1.putExtra(Const.TITLE, examPrepItem.getList().get(position - 1).getTitle());
                            examPrepLayer1.putExtra(Const.CONTENT_TYPE, contentType);
                            examPrepLayer1.putExtra(Const.TEST_TYPE, examPrepItem.getList().get(position - 1).getCount());
                            examPrepLayer1.putExtra(Const.LIST, examPrepItem.getList().get(position - 1));
                            examPrepLayer1.putExtra(Const.TILE_ID, tileIdAPI);
                            examPrepLayer1.putExtra(Const.TILE_TYPE, tileTypeAPI);
                            examPrepLayer1.putExtra(Const.REVERT_API, revertAPI);
                            Helper.gotoActivity(examPrepLayer1, activity);
                        } else if (isSkip.equalsIgnoreCase("3")) {
                            if (is_purchase.equalsIgnoreCase("1")) {
                                if (!examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("7"))
                                    if (TextUtils.isEmpty(examPrepItem.getList().get(position - 1).getFile_url()) && TextUtils.isEmpty(examPrepItem.getList().get(position - 1).getId())) {
                                        Toast.makeText(activity, "No pdf found!", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("8")) {
                                    Intent intent4 = new Intent(activity, WebViewActivty.class);
                                    intent4.putExtra("type", examPrepItem.getList().get(position - 1).getTitle());
                                    intent4.putExtra("url", examPrepItem.getList().get(position - 1).getFile_url());
                                    intent4.putExtra("course_id", SingleStudy.parentCourseId.equalsIgnoreCase("") ? singleStudy.getData().getCourseDetail().getId() + "#" : SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());
                                    intent4.putExtra("video_id", examPrepItem.getList().get(position - 1).getId());
                                    intent4.putExtra("link", examPrepItem.getList().get(position - 1).getFile_type());
                                    Helper.gotoActivity(intent4, activity);
                                }
                                if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("7")) {
                                    Intent intent4 = new Intent(activity, WebViewActivty.class);
                                    intent4.putExtra("type", examPrepItem.getList().get(position - 1).getTitle());
                                    intent4.putExtra("url", examPrepItem.getList().get(position - 1).getDescription());
                                    Helper.gotoActivity(intent4, activity);
                                } else if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("1")) {
                                    boolean isDownload = false;
                                    if (!TextUtils.isEmpty(examPrepItem.getList().get(position - 1).getIs_download_available()) && examPrepItem.getList().get(position - 1).getIs_download_available().equalsIgnoreCase("1")) {
                                        isDownload = true;
                                    } else {
                                        isDownload = false;
                                    }
                                    Helper.GoToWebViewPDFActivity(activity, examPrepItem.getList().get(position - 1).getId(), examPrepItem.getList().get(position - 1).getFile_url(), isDownload, examPrepItem.getList().get(position - 1).getTitle(), SingleStudy.parentCourseId.equalsIgnoreCase("") ? singleStudy.getData().getCourseDetail().getId() + "#" : SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());

                                }
                            } else {
                                if (examPrepItem.getList().get(position - 1).getIs_locked().equalsIgnoreCase("1")) {
                                   /* Intent intent = new Intent(activity, PurchaseActivity.class);
                                    intent.putExtra("single_study", new Gson().toJson(singleStudy));
                                    Helper.gotoActivity(intent, activity);*/
                                    if (study != null) {
                                        study.buynow();
                                    }
                                } else {
                                    if (!examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("7"))
                                        if (TextUtils.isEmpty(examPrepItem.getList().get(position - 1).getFile_url()) && TextUtils.isEmpty(examPrepItem.getList().get(position - 1).getId())) {
                                            Toast.makeText(activity, "No pdf found!", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("8")) {
                                        Intent intent4 = new Intent(activity, WebViewActivty.class);
                                        intent4.putExtra("type", examPrepItem.getList().get(position - 1).getTitle());
                                        intent4.putExtra("url", examPrepItem.getList().get(position - 1).getFile_url());
                                        intent4.putExtra("course_id", SingleStudy.parentCourseId.equalsIgnoreCase("") ? singleStudy.getData().getCourseDetail().getId() + "#" : SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());
                                        intent4.putExtra("video_id", examPrepItem.getList().get(position - 1).getId());
                                        intent4.putExtra("link", examPrepItem.getList().get(position - 1).getFile_type());
                                        Helper.gotoActivity(intent4, activity);
                                    }
                                    if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("7")) {
                                        Intent intent4 = new Intent(activity, WebViewActivty.class);
                                        intent4.putExtra("type", examPrepItem.getList().get(position - 1).getTitle());
                                        intent4.putExtra("url", examPrepItem.getList().get(position - 1).getDescription());
                                        Helper.gotoActivity(intent4, activity);
                                    } else if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("1")) {
                                        boolean isDownload = false;
                                        if (!TextUtils.isEmpty(examPrepItem.getList().get(position - 1).getIs_download_available()) && examPrepItem.getList().get(position - 1).getIs_download_available().equalsIgnoreCase("1")) {
                                            isDownload = true;
                                        } else {
                                            isDownload = false;
                                        }
                                        Helper.GoToWebViewPDFActivity(activity, examPrepItem.getList().get(position - 1).getId(), examPrepItem.getList().get(position - 1).getFile_url(), isDownload, examPrepItem.getList().get(position - 1).getTitle(), SingleStudy.parentCourseId.equalsIgnoreCase("") ? singleStudy.getData().getCourseDetail().getId() + "#" : SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());

                                    }
                                }
                            }
                        } else {
                            ExamPrepItem examPrepItemNew = new ExamPrepItem();
                            examPrepItemNew.setList(examPrepItem.getList().get(position - 1).getList());
                            Intent examPrepLayer1 = new Intent(activity, CourseActivity.class);
//                            examPrepLayer1.putExtra(Const.SINGLE_STUDY_DATA, new Gson().toJson(singleStudy));
//                            examPrepLayer1.putExtra(Const.EXAMPREP, examPrepItemNew);


                            Constants.examPrepItemNew =examPrepItemNew;
                            Constants.courseDetail =singleStudy;


                            examPrepLayer1.putExtra(Const.FRAG_TYPE, Const.EXAMPREP);
                            examPrepLayer1.putExtra(Const.IS_COMBO, isCombo);
                            examPrepLayer1.putExtra(Const.CONTENT_TYPE, contentType);
                            examPrepLayer1.putExtra(Const.TITLE, singleStudy.getData().getCourseDetail() != null ? singleStudy.getData().getCourseDetail().getTitle() : "Course");
                            examPrepLayer1.putExtra(Const.LIST, list.get(position - 1));
                            examPrepLayer1.putExtra(Const.TILE_ID, tileIdAPI);
                            examPrepLayer1.putExtra(Const.TILE_TYPE, tileTypeAPI);
                            examPrepLayer1.putExtra(Const.REVERT_API, revertAPI);
                            Helper.gotoActivity(examPrepLayer1, activity);
                        }
                    }
                });
            } else {
                ibt_single_sub_vd_RL.setVisibility(View.GONE);
                no_data_found_RL.setVisibility(View.VISIBLE);

                backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.finish();
                    }
                });
            }
        }
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

    public LinearLayout initSectionListView(TestSectionInst testSectionInst, int tag, String hide_inst_time) {
        List<View> LinearLayoutList = new ArrayList<>();
        LinearLayout v = (LinearLayout) View.inflate(activity, R.layout.layout_option_section_list_view, null);
        TextView secNameTV = (TextView) v.findViewById(R.id.secNameTV);
        TextView totQuesTV = (TextView) v.findViewById(R.id.totQuesTV);
        TextView option_count = (TextView) v.findViewById(R.id.option_count);

        TextView totTimeTV = (TextView) v.findViewById(R.id.totTimeTV);
        TextView maxMarksTV = (TextView) v.findViewById(R.id.maxMarksTV);
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


    public class SingleStudyConceptListHolder extends RecyclerView.ViewHolder {

        RelativeLayout studyitemLL, lockRL, layout_fun;
        ImageView imageIcon;
        TextView titleCategory;
        TextView subItemRV, read_now, share;
        GifImageView liveIV;
        RelativeLayout no_data_found_RL;
        Button backBtn;
        CardView ibt_single_sub_vd_RL;
        ImageView optionMenuImgView;

        public SingleStudyConceptListHolder(View itemView) {
            super(itemView);
            lockRL = itemView.findViewById(R.id.lockRL);
            share = itemView.findViewById(R.id.share);
            liveIV = itemView.findViewById(R.id.liveIV);
            studyitemLL = itemView.findViewById(R.id.study_single_itemLL);
            imageIcon = itemView.findViewById(R.id.profileImage);
            titleCategory = itemView.findViewById(R.id.examPrepTitleTV);
            subItemRV = itemView.findViewById(R.id.subItemRV);
            read_now = itemView.findViewById(R.id.read_now);
            layout_fun = itemView.findViewById(R.id.layout_fun);
            no_data_found_RL = itemView.findViewById(R.id.no_data_found_RL);
            backBtn = itemView.findViewById(R.id.backBtn);
            ibt_single_sub_vd_RL = itemView.findViewById(R.id.ibt_single_sub_vd_RL);
            optionMenuImgView = itemView.findViewById(R.id.optionMenuImgView);
        }

        public void setData(final ArrayList<Lists> list, final int position) {

            if (list != null && list.size() > 0) {
                ibt_single_sub_vd_RL.setVisibility(View.VISIBLE);
                no_data_found_RL.setVisibility(View.GONE);

                if (list.get(position - 1).getIs_live() != null) {
                    if (list.get(position - 1).getIs_live().equals("1")) {
                        liveIV.setVisibility(View.VISIBLE);
                    } else {
                        liveIV.setVisibility(View.GONE);
                    }
                } else {
                    liveIV.setVisibility(View.GONE);
                }

                titleCategory.setText(list.get(position - 1).getTitle());
                if (isSkip.equalsIgnoreCase("3")) {
                    share.setVisibility(View.GONE);
                    if (list.get(position - 1).getFile_type().equalsIgnoreCase("7")) {
                        layout_fun.setVisibility(View.VISIBLE);
                        read_now.setVisibility(View.VISIBLE);
                        optionMenuImgView.setVisibility(View.GONE);
                        // subItemRV.setText("Concept Type");
                        studyitemLL.setEnabled(true);
                        share.setVisibility(View.VISIBLE);

                    } else if (list.get(position - 1).getFile_type().equalsIgnoreCase("12") || list.get(position - 1).getFile_type().equalsIgnoreCase("11") || list.get(position - 1).getFile_type().equalsIgnoreCase("8")) {
                        layout_fun.setVisibility(View.VISIBLE);
                        read_now.setVisibility(View.VISIBLE);
                        optionMenuImgView.setVisibility(View.VISIBLE);
                        optionMenuImgView.setImageResource(R.drawable.delete);
                        subItemRV.setText("Revision Type");
                        studyitemLL.setEnabled(true);

                    } else {
                        layout_fun.setVisibility(View.GONE);
                        read_now.setVisibility(View.GONE);
                        optionMenuImgView.setVisibility(View.GONE);
                    }

                } else {
                    studyitemLL.setEnabled(true);
                    layout_fun.setVisibility(View.GONE);
                    read_now.setVisibility(View.GONE);
                    if (list.get(position - 1).getCount() != null)
                        subItemRV.setText("Total : " + list.get(position - 1).getCount());
                    subItemRV.setVisibility(View.GONE);
                }

                if (!TextUtils.isEmpty(list.get(position - 1).getImage_icon())) {
                    Helper.setThumbnailImage(activity, list.get(position - 1).getImage_icon(), activity.getResources().getDrawable(R.drawable.square_thumbnail), imageIcon);
                } else {
                    imageIcon.setImageResource(R.drawable.square_thumbnail);
                }

                if (TextUtils.isEmpty(list.get(position - 1).getIs_locked())) {
                    list.get(position - 1).setIs_locked("0");
                }
                if (singleStudy != null && singleStudy.getData().getCourseDetail() != null) {
                    if (singleStudy.getData().getCourseDetail().getIsPurchased().equals("1")) {
                        list.get(position - 1).setIs_locked("0");
                    }
                }
                if (list.get(position - 1).getIs_locked().equals("0")) {
                    lockRL.setVisibility(View.GONE);
                } else {
                    lockRL.setVisibility(View.VISIBLE);
                }

                optionMenuImgView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (is_purchase.equalsIgnoreCase("1")) {
                            if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("10") || examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("12") || examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("11")) {
                                videodata = examPrepItem.getList().get(position - 1);
                                position_delete = position - 1;
                                delete_meg(videodata);
                            } else if (!VideoDownloadService.isServiceRunning) {
                                if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("3")) {
                                    download_data(examPrepItem.getList().get(position - 1), position);
                                } else {
                                    Toast.makeText(activity, "Only jw video download...", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(activity, "Please wait downloading is in progress", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (examPrepItem.getList().get(position - 1).getIs_locked().equalsIgnoreCase("1")) {

                              /*  Intent intent = new Intent(activity, PurchaseActivity.class);
                                intent.putExtra("single_study", new Gson().toJson(singleStudy));
                                Helper.gotoActivity(intent, activity);*/

                                if (study != null) {
                                    study.buynow();
                                }
                            } else {
                                if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("10") || examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("12") || examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("11")) {
                                    videodata = examPrepItem.getList().get(position - 1);
                                    position_delete = position - 1;
                                    delete_meg(videodata);
                                } else if (!VideoDownloadService.isServiceRunning) {
                                    if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("3")) {
                                        download_data(examPrepItem.getList().get(position - 1), position);
                                    } else {
                                        Toast.makeText(activity, "Only jw video download...", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(activity, "Please wait downloading is in progress", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });

                read_now.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (list.get(position - 1).getIs_locked().equalsIgnoreCase("1")) {
                            /*Intent intent = new Intent(activity, PurchaseActivity.class);
                            intent.putExtra("single_study", new Gson().toJson(singleStudy));
                            Helper.gotoActivity(intent, activity);*/

                            if (study != null) {
                                study.buynow();
                            }
                            return;
                        }
                        if (isSkip.equalsIgnoreCase("3")) {
                            if (is_purchase.equalsIgnoreCase("1")) {
                                if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("12")) {
                                    if (examPrepItem.getList().size() > 0) {
                                        Intent intent = new Intent(activity, RevisionActivity.class);
                                        intent.putExtra("t_id", tileIdAPI);
                                        intent.putExtra("postion", position);
                                        intent.putExtra("video_id", examPrepItem.getList().get(position - 1).getId());
                                        intent.putExtra("v_list", actual_videolist);
                                        intent.putExtra("f_id", examPrepItem.getList().get(0).getId());
                                        intent.putExtra("c_id", singleStudy.getData().getCourseDetail().getId());
                                        intent.putExtra("title", examPrepItem.getList().get(position - 1).getTitle());
                                        intent.putExtra("url", examPrepItem.getList().get(position - 1).getFile_url());
                                        intent.putExtra("via", "main");
                                        Helper.gotoActivity(intent, activity);
                                    }
                                } else if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("11")) {
                                    Intent intent4 = new Intent(activity, WebViewActivty.class);
                                    intent4.putExtra("type", examPrepItem.getList().get(position - 1).getTitle());
                                    intent4.putExtra("url", examPrepItem.getList().get(position - 1).getFile_url());
                                    Helper.gotoActivity(intent4, activity);
                                } else if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("8")) {
                                    Intent intent4 = new Intent(activity, WebViewActivty.class);
                                    intent4.putExtra("type", examPrepItem.getList().get(position - 1).getTitle());
                                    intent4.putExtra("url", examPrepItem.getList().get(position - 1).getFile_url());
                                    intent4.putExtra("course_id", SingleStudy.parentCourseId.equalsIgnoreCase("") ? singleStudy.getData().getCourseDetail().getId() + "#" : SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());
                                    intent4.putExtra("video_id", examPrepItem.getList().get(position - 1).getId());
                                    intent4.putExtra("link", examPrepItem.getList().get(position - 1).getFile_type());
                                    Helper.gotoActivity(intent4, activity);
                                } else if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("6")) {
                                    if (TextUtils.isEmpty(examPrepItem.getList().get(position - 1).getFile_url()) && TextUtils.isEmpty(examPrepItem.getList().get(position - 1).getId())) {
                                        Toast.makeText(activity, "No image found", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    Intent intent = new Intent(activity, WebViewActivty.class);
                                    intent.putExtra("type", examPrepItem.getList().get(position - 1).getTitle());
                                    intent.putExtra("url", examPrepItem.getList().get(position - 1).getFile_url());
                                    intent.putExtra("file_type", "image");
                                    Helper.gotoActivity(intent, activity);
                                } else {
                                    Intent intent = new Intent(activity, Concept_newActivity.class); // AllCourse List
                                    intent.putExtra("id", examPrepItem.getList().get(position - 1).getId());
                                    intent.putExtra("name", examPrepItem.getList().get(position - 1).getTitle());
                                    intent.putExtra("modified", examPrepItem.getList().get(position - 1).getModified());

                                    intent.putExtra(Const.COURSE_ID, SingleStudy.parentCourseId.equalsIgnoreCase("") ? singleStudy.getData().getCourseDetail().getId() + "#" : SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());
                                    intent.putExtra(Const.TILE_ID, examPrepItem.getList().get(position - 1).getPayload().getTile_id());
                                    Helper.gotoActivity(intent, activity);
                                }
                            } else {
                                if (examPrepItem.getList().get(position - 1).getIs_locked().equalsIgnoreCase("1")) {

                                   /* Intent intent = new Intent(activity, PurchaseActivity.class);
                                    intent.putExtra("single_study", new Gson().toJson(singleStudy));
                                    Helper.gotoActivity(intent, activity);*/

                                    if (study != null) {
                                        study.buynow();
                                    }
                                } else {
                                    if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("12")) {
                                        if (examPrepItem.getList().size() > 0) {
                                            Intent intent = new Intent(activity, RevisionActivity.class);
                                            intent.putExtra("t_id", tileIdAPI);
                                            intent.putExtra("postion", position);
                                            intent.putExtra("video_id", examPrepItem.getList().get(position - 1).getId());
                                            intent.putExtra("v_list", actual_videolist);
                                            intent.putExtra("f_id", examPrepItem.getList().get(0).getId());
                                            intent.putExtra("c_id", singleStudy.getData().getCourseDetail().getId());
                                            intent.putExtra("title", examPrepItem.getList().get(position - 1).getTitle());
                                            intent.putExtra("url", examPrepItem.getList().get(position - 1).getFile_url());
                                            intent.putExtra("via", "main");
                                            Helper.gotoActivity(intent, activity);
                                        }
                                    } else if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("11")) {
                                        Intent intent4 = new Intent(activity, WebViewActivty.class);
                                        intent4.putExtra("type", examPrepItem.getList().get(position - 1).getTitle());
                                        intent4.putExtra("url", examPrepItem.getList().get(position - 1).getFile_url());
                                        Helper.gotoActivity(intent4, activity);
                                    } else if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("8")) {
                                        Intent intent4 = new Intent(activity, WebViewActivty.class);
                                        intent4.putExtra("type", examPrepItem.getList().get(position - 1).getTitle());
                                        intent4.putExtra("url", examPrepItem.getList().get(position - 1).getFile_url());
                                        intent4.putExtra("course_id", SingleStudy.parentCourseId.equalsIgnoreCase("") ? singleStudy.getData().getCourseDetail().getId() + "#" : SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());
                                        intent4.putExtra("video_id", examPrepItem.getList().get(position - 1).getId());
                                        intent4.putExtra("link", examPrepItem.getList().get(position - 1).getFile_type());
                                        Helper.gotoActivity(intent4, activity);
                                    } else if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("6")) {
                                        if (TextUtils.isEmpty(examPrepItem.getList().get(position - 1).getFile_url()) && TextUtils.isEmpty(examPrepItem.getList().get(position - 1).getId())) {
                                            Toast.makeText(activity, "No image found", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        Intent intent = new Intent(activity, WebViewActivty.class);
                                        intent.putExtra("type", examPrepItem.getList().get(position - 1).getTitle());
                                        intent.putExtra("url", examPrepItem.getList().get(position - 1).getFile_url());
                                        intent.putExtra("file_type", "image");
                                        Helper.gotoActivity(intent, activity);
                                    } else {
                                        Intent intent = new Intent(activity, Concept_newActivity.class); // AllCourse List
                                        intent.putExtra("id", examPrepItem.getList().get(position - 1).getId());
                                        intent.putExtra("name", examPrepItem.getList().get(position - 1).getTitle());
                                        intent.putExtra("modified", examPrepItem.getList().get(position - 1).getModified());

                                        intent.putExtra(Const.COURSE_ID, SingleStudy.parentCourseId.equalsIgnoreCase("") ? singleStudy.getData().getCourseDetail().getId() + "#" : SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());
                                        intent.putExtra(Const.TILE_ID, examPrepItem.getList().get(position - 1).getPayload().getTile_id());
                                        Helper.gotoActivity(intent, activity);
                                    }
                                }
                            }
                        }
                    }
                });

                studyitemLL.setOnClickListener(view -> {
                    if (list.get(position - 1).getIs_locked().equalsIgnoreCase("1")) {
                        /*Intent intent = new Intent(activity, PurchaseActivity.class);
                        intent.putExtra("single_study", new Gson().toJson(singleStudy));
                        Helper.gotoActivity(intent, activity);*/

                        if (study != null) {
                            study.buynow();
                        }
                        return;
                    }

                    if (isSkip.equalsIgnoreCase("1") || isSkip.equalsIgnoreCase("2")) {
                        Intent examPrepLayer1 = new Intent(activity, CourseActivity.class);
                       /* examPrepLayer1.putExtra(Const.SINGLE_STUDY_DATA, new Gson().toJson(singleStudy));
                        examPrepLayer1.putExtra(Const.EXAMPREP, examPrepItem);
*/

                        Constants.examPrepItemNew =examPrepItem;
                        Constants.courseDetail =singleStudy;


                        examPrepLayer1.putExtra(Const.FRAG_TYPE, Const.EXAMPREPLAST);
                        examPrepLayer1.putExtra(Const.LIST_SUBJECT, examPrepItem.getList().get(position - 1));

                        examPrepLayer1.putExtra(Const.IS_COMBO, isCombo);
                        examPrepLayer1.putExtra(Const.TITLE, examPrepItem.getList().get(position - 1).getTitle());
                        examPrepLayer1.putExtra(Const.CONTENT_TYPE, contentType);
                        examPrepLayer1.putExtra(Const.TEST_TYPE, examPrepItem.getList().get(position - 1).getCount());
                        examPrepLayer1.putExtra(Const.LIST, examPrepItem.getList().get(position - 1));
                        examPrepLayer1.putExtra(Const.TILE_ID, tileIdAPI);
                        examPrepLayer1.putExtra(Const.TILE_TYPE, tileTypeAPI);
                        examPrepLayer1.putExtra(Const.REVERT_API, revertAPI);
                        Helper.gotoActivity(examPrepLayer1, activity);
                    } else if (isSkip.equalsIgnoreCase("3")) {
                        if (is_purchase.equalsIgnoreCase("1")) {
                            Intent intent = new Intent(activity, Concept_newActivity.class); // AllCourse List
                            intent.putExtra("id", examPrepItem.getList().get(position - 1).getId());
                            intent.putExtra("name", examPrepItem.getList().get(position - 1).getTitle());
                            intent.putExtra("modified", examPrepItem.getList().get(position - 1).getModified());

                            intent.putExtra(Const.COURSE_ID, SingleStudy.parentCourseId.equalsIgnoreCase("") ? singleStudy.getData().getCourseDetail().getId() + "#" : SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());
                            intent.putExtra(Const.TILE_ID, examPrepItem.getList().get(position - 1).getPayload().getTile_id());
                            Helper.gotoActivity(intent, activity);
                        } else {
                            if (examPrepItem.getList().get(position - 1).getIs_locked().equalsIgnoreCase("1")) {
                              /*  Intent intent = new Intent(activity, PurchaseActivity.class);
                                intent.putExtra("single_study", new Gson().toJson(singleStudy));
                                Helper.gotoActivity(intent, activity);
*/
                                if (study != null) {
                                    study.buynow();
                                }
                            } else if (examPrepItem.getList().get(position - 1).getFile_type().equalsIgnoreCase("12")) {
                                if (examPrepItem.getList().size() > 0) {
                                    Intent intent = new Intent(activity, RevisionActivity.class);
                                    intent.putExtra("t_id", tileIdAPI);
                                    intent.putExtra("postion", position);
                                    intent.putExtra("video_id", examPrepItem.getList().get(position - 1).getId());
                                    intent.putExtra("v_list", actual_videolist);
                                    intent.putExtra("f_id", examPrepItem.getList().get(0).getId());
                                    intent.putExtra("c_id", singleStudy.getData().getCourseDetail().getId());
                                    intent.putExtra("title", examPrepItem.getList().get(position - 1).getTitle());
                                    intent.putExtra("url", examPrepItem.getList().get(position - 1).getFile_url());
                                    intent.putExtra("via", "main");
                                    Helper.gotoActivity(intent, activity);
                                }
                            } else {
                                Intent intent = new Intent(activity, Concept_newActivity.class); // AllCourse List
                                intent.putExtra("id", examPrepItem.getList().get(position - 1).getId());
                                intent.putExtra("name", examPrepItem.getList().get(position - 1).getTitle());
                                intent.putExtra(Const.COURSE_ID, SingleStudy.parentCourseId.equalsIgnoreCase("") ? singleStudy.getData().getCourseDetail().getId() + "#" : SingleStudy.parentCourseId + "#" + singleStudy.getData().getCourseDetail().getId());
                                intent.putExtra(Const.TILE_ID, examPrepItem.getList().get(position - 1).getPayload().getTile_id());
                                intent.putExtra("modified", examPrepItem.getList().get(position - 1).getModified());
                                Helper.gotoActivity(intent, activity);
                            }
                        }
                    } else {
                        ExamPrepItem examPrepItemNew = new ExamPrepItem();
                        examPrepItemNew.setList(examPrepItem.getList().get(position - 1).getList());
                        Intent examPrepLayer1 = new Intent(activity, CourseActivity.class);
                    /*    examPrepLayer1.putExtra(Const.SINGLE_STUDY_DATA, new Gson().toJson(singleStudy));
                        examPrepLayer1.putExtra(Const.EXAMPREP, examPrepItemNew);
*/


                        Constants.examPrepItemNew =examPrepItemNew;
                        Constants.courseDetail =singleStudy;



                        examPrepLayer1.putExtra(Const.FRAG_TYPE, Const.EXAMPREP);
                        examPrepLayer1.putExtra(Const.IS_COMBO, isCombo);
                        examPrepLayer1.putExtra(Const.CONTENT_TYPE, contentType);
                        examPrepLayer1.putExtra(Const.TITLE, singleStudy.getData().getCourseDetail() != null ? singleStudy.getData().getCourseDetail().getTitle() : "Course");
                        examPrepLayer1.putExtra(Const.LIST, list.get(position - 1));
                        examPrepLayer1.putExtra(Const.TILE_ID, tileIdAPI);
                        examPrepLayer1.putExtra(Const.TILE_TYPE, tileTypeAPI);
                        examPrepLayer1.putExtra(Const.REVERT_API, revertAPI);
                        Helper.gotoActivity(examPrepLayer1, activity);
                    }
                });

                share.setOnClickListener(new OnSingleClickListener(() -> {
                    Helper.sharePdf(activity, examPrepItem.getList().get(position - 1).getPayload().getCourse_id(), examPrepItem.getList().get(position - 1).getId(), examPrepItem.getList().get(position - 1).getPayload().getTopic_id(), examPrepItem.getList().get(position - 1).getPayload().getTile_type(), examPrepItem.getList().get(position - 1).getPayload().getTile_id(), examPrepItem.getList().get(position - 1).getPayload().getRevert_api(), "pdf", examPrepItem.getList().get(position - 1).getThumbnail_url(), examPrepItem.getList().get(position - 1).getTitle(), SingleStudy.parentCourseId);
                    return null;
                }));

            } else {
                ibt_single_sub_vd_RL.setVisibility(View.GONE);
                no_data_found_RL.setVisibility(View.VISIBLE);

                backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.finish();
                    }
                });
            }
        }
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
}