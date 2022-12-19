package com.utkarshnew.android.UserHistory;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;
import com.utkarshnew.android.courses.Activity.Concept_newActivity;
import com.utkarshnew.android.Download.Audio.AudioPlayerService;
import com.utkarshnew.android.Download.AudioPlayerActivty;
import com.utkarshnew.android.Download.DownloadVideoPlayer;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.Model.UrlObject;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.table.AudioTable;
import com.utkarshnew.android.table.UserHistroyTable;
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
import com.utkarshnew.android.Webview.WebViewActivty;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;

/**
 * Created by appsquadz on 25/9/17.
 */

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements NetworkCall.MyNetworkCallBack {
    private List<UserHistroyTable> historyvideos;
    private UtkashRoom utkashRoom;
    Activity context;
    UserHistroyTable userHistroyTable;
    public boolean is_audio =false;

    public HistoryAdapter(Context context, List<UserHistroyTable> historyvideos, UtkashRoom utkashRoom) {
        this.historyvideos = historyvideos;
        this.context = (Activity) context;
        this.utkashRoom = utkashRoom;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_adapter, parent, false);
        return new StreamHolder(view);

    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder sholder, final int position) {

        try {
            final StreamHolder holder = (StreamHolder) sholder;
            holder.video_time.setText(Helper.changeAMPM(new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(Long.parseLong(historyvideos.get(holder.getAdapterPosition()).getCurrent_time())))));
            if (historyvideos.get(holder.getAdapterPosition()).getType().equalsIgnoreCase("video")) {
                holder.video_name.setText(historyvideos.get(holder.getAdapterPosition()).getVideo_name());

                holder.courseImage.setImageResource(R.drawable.play_icon);
            }
            if (historyvideos.get(holder.getAdapterPosition()).getType().equalsIgnoreCase("concept")) {
                holder.video_name.setText(historyvideos.get(holder.getAdapterPosition()).getVideo_name());

                holder.courseImage.setImageResource(R.mipmap.pdf);
            } else if (historyvideos.get(holder.getAdapterPosition()).getType().equalsIgnoreCase("audio")) {
                holder.video_name.setText(historyvideos.get(holder.getAdapterPosition()).getVideo_name());

                holder.courseImage.setImageResource(R.drawable.audio);
            } else if (historyvideos.get(holder.getAdapterPosition()).getType().equalsIgnoreCase("PDF")) {
                holder.video_name.setText(historyvideos.get(holder.getAdapterPosition()).getPdf_name());

                holder.courseImage.setImageResource(R.mipmap.pdf);
            } else if (historyvideos.get(holder.getAdapterPosition()).getType().equalsIgnoreCase("Youtube Video")) {
                holder.video_name.setText(historyvideos.get(holder.getAdapterPosition()).getVideo_name());
                holder.courseImage.setImageResource(R.drawable.play_icon);

            } else if (historyvideos.get(holder.getAdapterPosition()).getType().equalsIgnoreCase("8")) {
                holder.video_name.setText(historyvideos.get(holder.getAdapterPosition()).getPdf_name());
                holder.courseImage.setImageResource(R.drawable.square_thumbnail);

            } else if (historyvideos.get(holder.getAdapterPosition()).getType().equalsIgnoreCase("Youtube Audio")) {
                holder.video_name.setText(historyvideos.get(holder.getAdapterPosition()).getVideo_name());
                holder.courseImage.setImageResource(R.drawable.audio);

            } else if (historyvideos.get(holder.getAdapterPosition()).getType().equalsIgnoreCase("AWS Audio")) {
                holder.video_name.setText(historyvideos.get(holder.getAdapterPosition()).getVideo_name());
                holder.courseImage.setImageResource(R.drawable.audio);

            } else if (historyvideos.get(holder.getAdapterPosition()).getType().equalsIgnoreCase("AWS Video")) {
                holder.video_name.setText(historyvideos.get(holder.getAdapterPosition()).getVideo_name());
                holder.courseImage.setImageResource(R.drawable.play_icon);

            }

            holder.study_single_itemLL.setOnClickListener(new OnSingleClickListener(() -> {
                if (AudioPlayerService.player != null) {
                    if (AudioPlayerService.type.equalsIgnoreCase("youtube")) {
                        if (historyvideos.get(holder.getAdapterPosition()).getYoutube_url() != null && !historyvideos.get(holder.getAdapterPosition()).getYoutube_url().equalsIgnoreCase("")) {
                            utkashRoom.getyoutubedata().updateTime(AudioPlayerService.player.getCurrentPosition(), historyvideos.get(holder.getAdapterPosition()).getVideo_id(), MakeMyExam.userId, "1");
                        }
                    } else {
                        utkashRoom.getaudiodao().update_audio_currentpos(AudioPlayerService.videoid, MakeMyExam.userId, AudioPlayerService.player.getCurrentPosition());

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
                if (historyvideos.get(holder.getAdapterPosition()).getType().equalsIgnoreCase("Concept")) {
                    Intent intent = new Intent(context, Concept_newActivity.class); // AllCourse List
                    intent.putExtra("id", historyvideos.get(holder.getAdapterPosition()).getVideo_id());
                    intent.putExtra("name", historyvideos.get(holder.getAdapterPosition()).getVideo_name());
                    intent.putExtra("course_id", historyvideos.get(holder.getAdapterPosition()).getCourse_id());
                    intent.putExtra("tile_id", historyvideos.get(holder.getAdapterPosition()).getTileid());
                    intent.putExtra("modified", historyvideos.get(holder.getAdapterPosition()).getValid_to());
                    Helper.gotoActivity(intent, context);
                } else if (historyvideos.get(holder.getAdapterPosition()).getType().equalsIgnoreCase("Youtube Audio")) {
                    String parentid[] = new String[0];
                    String img_url = "http://img.youtube.com/vi/" + historyvideos.get(holder.getAdapterPosition()).getYoutube_url() + "/0.jpg";
                    if (historyvideos.get(holder.getAdapterPosition()).getCourse_id().contains("#")) {
                        parentid = historyvideos.get(holder.getAdapterPosition()).getCourse_id().split("#");
                        if (parentid.length == 1) {
                            parentid[1] = "";
                        }
                    } else {
                        parentid[0] = historyvideos.get(holder.getAdapterPosition()).getCourse_id();
                        parentid[1] = historyvideos.get(holder.getAdapterPosition()).getCourse_id();
                    }
                    Helper.GoToLiveVideoActivity("", (UserHistoryActivity) context, historyvideos.get(holder.getAdapterPosition()).getYoutube_url(), "1", historyvideos.get(holder.getAdapterPosition()).getVideo_id(), historyvideos.get(holder.getAdapterPosition()).getVideo_name(), "1", img_url, "1", parentid[1], String.valueOf(position), parentid[0], historyvideos.get(holder.getAdapterPosition()).getTileid(), "video");
                } else if (historyvideos.get(holder.getAdapterPosition()).getType().equalsIgnoreCase("Youtube Video")) {
                    String parentid[] = new String[0];
                    if (historyvideos.get(holder.getAdapterPosition()).getCourse_id().contains("#")) {
                        parentid = historyvideos.get(holder.getAdapterPosition()).getCourse_id().split("#");
                        if (parentid.length == 1) {
                            parentid[1] = "";
                        }
                    } else {
                        parentid[0] = historyvideos.get(holder.getAdapterPosition()).getCourse_id();
                        parentid[1] = historyvideos.get(holder.getAdapterPosition()).getCourse_id();
                    }
                    Helper.GoToLiveVideoActivity("", (UserHistoryActivity) context, historyvideos.get(holder.getAdapterPosition()).getYoutube_url(), "1", historyvideos.get(holder.getAdapterPosition()).getVideo_id(), historyvideos.get(holder.getAdapterPosition()).getVideo_name(), "0", "", "1", parentid[1], String.valueOf(position), parentid[0], historyvideos.get(holder.getAdapterPosition()).getTileid(), "video");
                }
                else  if (historyvideos.get(holder.getAdapterPosition()).getType().equalsIgnoreCase("AWS Video"))
                {
                    userHistroyTable =historyvideos.get(holder.getAdapterPosition());
                    NetworkCall networkCall = new NetworkCall(HistoryAdapter.this, context);
                    networkCall.NetworkAPICall(API.get_video_link, "", true, false);



                 //   Helper.GoToLiveAwsVideoActivity("0","", context, historyvideos.get(holder.getAdapterPosition()).getVideo_id(), "0", historyvideos.get(holder.getAdapterPosition()).getVideo_id(), historyvideos.get(holder.getAdapterPosition()).getVideo_name(), "0", "",historyvideos.get(holder.getAdapterPosition()).getCourse_id(), historyvideos.get(holder.getAdapterPosition()).getTileid(),"video","1","", historyvideos.get(holder.getAdapterPosition()).getCourse_id());

                } else if (historyvideos.get(holder.getAdapterPosition()).getType().equalsIgnoreCase("AWS Audio"))
                {
                    userHistroyTable =historyvideos.get(holder.getAdapterPosition());
                    NetworkCall networkCall = new NetworkCall(HistoryAdapter.this, context);
                    networkCall.NetworkAPICall(API.get_video_link, "", true, false);


                 //   Helper.GoToLiveAwsVideoActivity("0","", context, historyvideos.get(holder.getAdapterPosition()).getVideo_id(), "0", historyvideos.get(holder.getAdapterPosition()).getVideo_id(), historyvideos.get(holder.getAdapterPosition()).getVideo_name(), "1", historyvideos.get(holder.getAdapterPosition()).getUrl(),historyvideos.get(holder.getAdapterPosition()).getCourse_id(),historyvideos.get(holder.getAdapterPosition()).getTileid(),"video","1","",historyvideos.get(holder.getAdapterPosition()).getCourse_id());

                }


                else if (historyvideos.get(holder.getAdapterPosition()).getType().equalsIgnoreCase("PDF")) {
                    Helper.GoToWebViewPDFActivity(context, historyvideos.get(holder.getAdapterPosition()).getVideo_id(), historyvideos.get(holder.getAdapterPosition()).getPdf_url(), false, historyvideos.get(holder.getAdapterPosition()).getPdf_name(), historyvideos.get(holder.getAdapterPosition()).getCourse_id());
                } else if (historyvideos.get(holder.getAdapterPosition()).getType().equalsIgnoreCase("8")) {
                    if (historyvideos.get(holder.getAdapterPosition()).getType().equalsIgnoreCase("8")) {
                        Intent intent4 = new Intent(context, WebViewActivty.class);
                        intent4.putExtra("type", historyvideos.get(holder.getAdapterPosition()).getPdf_name());
                        intent4.putExtra("url", historyvideos.get(holder.getAdapterPosition()).getPdf_url());
                        intent4.putExtra("video_id", historyvideos.get(holder.getAdapterPosition()).getVideo_id());
                        intent4.putExtra("link", historyvideos.get(holder.getAdapterPosition()).getType());
                        intent4.putExtra("course_id", historyvideos.get(holder.getAdapterPosition()).getCourse_id());
                        // context.startActivity(intent4);
                        Helper.gotoActivity(intent4, context);
                    }
                } else if (historyvideos.get(holder.getAdapterPosition()).getType().equalsIgnoreCase("audio")) {

                    if (utkashRoom.getaudiodao().isvideo_exit(historyvideos.get(holder.getAdapterPosition()).getVideo_id(), MakeMyExam.userId)) {
                        userHistroyTable =historyvideos.get(holder.getAdapterPosition());
                        is_audio =true;
                        NetworkCall networkCall = new NetworkCall(HistoryAdapter.this, context);
                        networkCall.NetworkAPICall(API.get_video_link, "", true, false);

                    } else {
                        Toast.makeText(context, "History not exist", Toast.LENGTH_SHORT).show();
                    }
                } else if (historyvideos.get(holder.getAdapterPosition()).getType().equalsIgnoreCase("Video")) {
                    if (utkashRoom.getvideoDownloadao().isvideo_exit(historyvideos.get(holder.getAdapterPosition()).getVideo_id(), MakeMyExam.userId)) {
                        VideosDownload videoDownload = utkashRoom.getvideoDownloadao().getvideo_byuserid(historyvideos.get(holder.getAdapterPosition()).getVideo_id(), MakeMyExam.userId);
                        if (videoDownload.getIs_complete().equalsIgnoreCase("1")) {
                            Intent i = new Intent(context, DownloadVideoPlayer.class);
                            i.putExtra("video_name", videoDownload.getVideo_name());
                            i.putExtra("video_id", videoDownload.getVideo_id());
                            i.putExtra("current_pos", videoDownload.getVideoCurrentPosition());
                            i.putExtra("video", videoDownload.getVideo_history());
                            i.putExtra("video_time", videoDownload.getVideotime());
                            i.putExtra("course_id", videoDownload.getCourse_id());
                            i.putExtra("token", videoDownload.getVideo_token());
                            Helper.gotoActivity(i, context);

                        } else {
                            userHistroyTable =historyvideos.get(holder.getAdapterPosition());
                            NetworkCall networkCall = new NetworkCall(HistoryAdapter.this, context);
                            networkCall.NetworkAPICall(API.get_video_link, "", true, false);

                           // Helper.GoToJWVideo_Params(context, videoDownload.getJw_url(), videoDownload.getVideo_id(), videoDownload.getVideo_name(), utkashRoom.getvideoDao().getuser(videoDownload.getVideo_id(), MakeMyExam.userId).getVideo_currentpos(), videoDownload.getCourse_id());
                        }
                    } else {
                        userHistroyTable =historyvideos.get(holder.getAdapterPosition());
                        NetworkCall networkCall = new NetworkCall(HistoryAdapter.this, context);
                        networkCall.NetworkAPICall(API.get_video_link, "", true, false);

                       /* if (utkashRoom.getvideoDao().isvideo_exit(historyvideos.get(holder.getAdapterPosition()).getVideo_id(), MakeMyExam.userId)) {
                            VideoTable videoTable = utkashRoom.getvideoDao().getuser(historyvideos.get(holder.getAdapterPosition()).getVideo_id(), MakeMyExam.userId);
                            Helper.GoToJWVideo_Params(context, videoTable.getJw_url(), videoTable.getVideo_id(), videoTable.getVideo_name(), videoTable.getVideo_currentpos(), videoTable.getCourse_id());
                        }*/
                    }
                }
                return null;
            }));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void play_audio(UserHistroyTable userHistroyTable, String link, String objectcookie) {
        if (link != null && !link.equalsIgnoreCase("")) {


            AudioTable audioTable = utkashRoom.getaudiodao().getuser(userHistroyTable.getVideo_id(), MakeMyExam.userId);
            Intent intent = new Intent(context, AudioPlayerActivty.class);
            AudioPlayerService.videoid = "";
            intent.putExtra("url", link);
            intent.putExtra("videoid", userHistroyTable.getVideo_id());
            intent.putExtra("currentpos", audioTable.getAudio_currentpos());
            intent.putExtra("video_name", userHistroyTable.getVideo_name());
            intent.putExtra("image_url", userHistroyTable.getUrl());
            intent.putExtra("tile_id", userHistroyTable.getTileid());
            intent.putExtra("tile_type",userHistroyTable.getTopicname());
            intent.putExtra("course_id", userHistroyTable.getCourse_id());
            intent.putExtra("course_id", userHistroyTable.getCourse_id());
            intent.putExtra("objectcookie", objectcookie);

            Helper.gotoActivity(intent, context);
        }




    }


    @Override
    public int getItemCount() {
        if (historyvideos != null && historyvideos.size() > 0)
            return historyvideos.size();
        else return 0;
    }

    public void chnagelist(List<UserHistroyTable> historyvideos) {
        this.historyvideos = historyvideos;
        notifyDataSetChanged();
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.get_video_link:
                EncryptionData encryptionData = new EncryptionData();
                encryptionData.setName(userHistroyTable.getVideo_id() + "_" + "0" + "_" + "0");
                if (userHistroyTable.getCourse_id()!=null && userHistroyTable.getCourse_id().contains("#"))
                {
                    encryptionData.setCourse_id(userHistroyTable.getCourse_id().split("#")[0]);
                }
                encryptionData.setTile_id(userHistroyTable.getTileid());
                encryptionData.setType(userHistroyTable.getTopicname()==null ? "video":userHistroyTable.getTopicname());

                String  device_id =   (Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));
                String    device_name = android.os.Build.MANUFACTURER + android.os.Build.MODEL;
                device_id = device_id ==null && device_id.equalsIgnoreCase("") ? "1234567890":device_id;
                encryptionData.setDevice_id(device_id);
                encryptionData.setDevice_name(device_name);

                String data1 = new Gson().toJson(encryptionData);
                String encryptjson = AES.encrypt(data1);
                return service.getVideoLink(encryptjson);
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonobject, String apitype, String typeApi, boolean showprogress) throws JSONException {
        Gson gson = new Gson();
        switch (apitype) {

            case API.get_video_link:
                try {
                    if (jsonobject.getString("status").equalsIgnoreCase("true")) {

                        JSONObject jsonObject = new JSONObject(jsonobject.toString());
                        if (jsonObject.has("data")) {
                            String link = jsonObject.getJSONObject("data").getString("link");
                            JSONObject dataJsonObject = jsonObject.getJSONObject(Const.DATA);
                            String audio_url =   dataJsonObject.optString("audio_url");
                            long expire_time=0;
                            JSONObject  objectcookie=null;
                            if (dataJsonObject.has("cookie") && dataJsonObject.optJSONObject("cookie")!=null)
                            {
                                objectcookie = dataJsonObject.optJSONObject("cookie");
                            }else {
                                objectcookie =new JSONObject("{}");
                            }
                            if (dataJsonObject.optLong("expiry_seconds")!=0)
                            {
                                expire_time = (Long.parseLong(jsonobject.optString("time"))) +dataJsonObject.optLong("expiry_seconds");
                            }
                            String c_type =   dataJsonObject.optString("content_type");
                            if (c_type.equalsIgnoreCase(""))
                            {
                                is_audio =false;
                                ArrayList<UrlObject> urlObjects = new ArrayList<>();

                                if (dataJsonObject.has("bitrate_urls") ) {
                                    JSONArray arrJson = dataJsonObject.optJSONArray("bitrate_urls");
                                    if (arrJson != null && arrJson.length() > 0) {
                                        for (int i = 0; i < Objects.requireNonNull(arrJson).length(); i++) {
                                            JSONObject dataObj = arrJson.optJSONObject(i);
                                            UrlObject urlObject = new UrlObject();
                                            urlObject.setTitle(dataObj.optString("title"));
                                            urlObject.setUrl(dataObj.optString("url"));

                                            urlObject.setToken("");
                                            urlObjects.add(urlObject);
                                           /* UrlObject questionBank = new Gson().fromJson(dataObj.toString(), UrlObject.class);
                                            urlObjects.add(questionBank);*/
                                        }
                                    }
                                }

                                if (utkashRoom.getvideoDao().isvideo_exit(userHistroyTable.getVideo_id(), MakeMyExam.userId)) {
                                    VideoTable videoTable = utkashRoom.getvideoDao().getuser(userHistroyTable.getVideo_id(), MakeMyExam.userId);
                                    Helper.GoToJWVideo_Params_newarrayobject(context, link, videoTable.getVideo_id(), videoTable.getVideo_name(), videoTable.getVideo_currentpos(), videoTable.getCourse_id(),userHistroyTable.getTileid(),userHistroyTable.getTopicname(),urlObjects,expire_time,objectcookie.toString());
                                }else {
                                    Helper.GoToJWVideo_Params_newarrayobject(context, link, userHistroyTable.getVideo_id(), userHistroyTable.getVideo_name(), utkashRoom.getvideoDao().getuser(userHistroyTable.getVideo_id(), MakeMyExam.userId).getVideo_currentpos(), userHistroyTable.getCourse_id(),userHistroyTable.getTileid(),userHistroyTable.getTopicname(),urlObjects,expire_time, Objects.requireNonNull(objectcookie).toString());
                                }
                            }else
                            if (userHistroyTable!=null && userHistroyTable.getType().equalsIgnoreCase("AWS video"))
                            {
                                if (c_type.equalsIgnoreCase("2"))
                                {
                                    Helper.GoToLiveDrmVideoActivity("0","", context, userHistroyTable.getVideo_id(), "0", userHistroyTable.getVideo_id(), userHistroyTable.getVideo_name(), "0", "",userHistroyTable.getCourse_id(), userHistroyTable.getTileid(),"video","1","", userHistroyTable.getCourse_id(),jsonobject.toString());

                                }else {
                                    Helper.GoToLiveAwsVideoActivity("0","", context, userHistroyTable.getVideo_id(), "0", userHistroyTable.getVideo_id(), userHistroyTable.getVideo_name(), "0", "",userHistroyTable.getCourse_id(), userHistroyTable.getTileid(),"video","1","", userHistroyTable.getCourse_id(),jsonobject.toString());
                                }

                            }
                            else if (userHistroyTable!=null && userHistroyTable.getType().equalsIgnoreCase("AWS audio"))
                            {
                                if (c_type.equalsIgnoreCase("2"))
                            {
                                Helper.GoToLiveDrmVideoActivity("0","", context, userHistroyTable.getVideo_id(), "0", userHistroyTable.getVideo_id(), userHistroyTable.getVideo_name(), "1", userHistroyTable.getUrl(),userHistroyTable.getCourse_id(),userHistroyTable.getTileid(),"video","1","",userHistroyTable.getCourse_id(),jsonobject.toString());
                            }else {
                                   Helper.GoToLiveAwsVideoActivity("0","", context, userHistroyTable.getVideo_id(), "0", userHistroyTable.getVideo_id(), userHistroyTable.getVideo_name(), "1", userHistroyTable.getUrl(),userHistroyTable.getCourse_id(),userHistroyTable.getTileid(),"video","1","",userHistroyTable.getCourse_id(),jsonobject.toString());
                            }

                            }else {
                                if (is_audio)
                                {
                                    is_audio =false;
                                    play_audio(userHistroyTable,audio_url,objectcookie.toString());

                                }else {
                                    is_audio =false;
                                    ArrayList<UrlObject> urlObjects = new ArrayList<>();

                                    if (dataJsonObject.has("bitrate_urls") ) {
                                        JSONArray arrJson = dataJsonObject.optJSONArray("bitrate_urls");
                                        if (arrJson != null && arrJson.length() > 0) {
                                            for (int i = 0; i < Objects.requireNonNull(arrJson).length(); i++) {
                                                JSONObject dataObj = arrJson.optJSONObject(i);
                                                UrlObject urlObject = new UrlObject();
                                                urlObject.setTitle(dataObj.optString("title"));
                                                urlObject.setUrl(dataObj.optString("url"));
                                                urlObjects.add(urlObject);
                                            }
                                        }
                                    }

                                    if (utkashRoom.getvideoDao().isvideo_exit(userHistroyTable.getVideo_id(), MakeMyExam.userId)) {
                                        VideoTable videoTable = utkashRoom.getvideoDao().getuser(userHistroyTable.getVideo_id(), MakeMyExam.userId);
                                        Helper.GoToJWVideo_Params_newarrayobject(context, link, videoTable.getVideo_id(), videoTable.getVideo_name(), videoTable.getVideo_currentpos(), videoTable.getCourse_id(),userHistroyTable.getTileid(),userHistroyTable.getTopicname(),urlObjects,expire_time,objectcookie.toString());
                                    }else {
                                        Helper.GoToJWVideo_Params_newarrayobject(context, link, userHistroyTable.getVideo_id(), userHistroyTable.getVideo_name(), utkashRoom.getvideoDao().getuser(userHistroyTable.getVideo_id(), MakeMyExam.userId).getVideo_currentpos(), userHistroyTable.getCourse_id(),userHistroyTable.getTileid(),userHistroyTable.getTopicname(),urlObjects,expire_time, Objects.requireNonNull(objectcookie).toString());
                                    }
                                }
                            }


                        } else {
                            Toast.makeText(context, "url not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        this.ErrorCallBack(jsonobject.getString(Const.MESSAGE), apitype, typeApi);
                        RetrofitResponse.GetApiData(context, jsonobject.getString(Const.AUTH_CODE), jsonobject.getString(Const.MESSAGE), false);
                    }
                } catch (Exception e) {
                }
                break;
        }
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {

    }


    public class StreamHolder extends RecyclerView.ViewHolder {
        TextView video_name, video_time, percentageTxt, pauseTextView;
        RelativeLayout pauseLayout, cancel_progress_layout, delete_layout;
        ImageView courseImage, download_icon;
        RelativeLayout study_single_itemLL;
        TextView file_mb;
        CheckBox selected_video;
        ProgressBar loadingProgress;

        public StreamHolder(View itemView) {
            super(itemView);

            video_time = itemView.findViewById(R.id.history_time);

            video_name = itemView.findViewById(R.id.video_name);
            courseImage = itemView.findViewById(R.id.courseImage);

            study_single_itemLL = itemView.findViewById(R.id.study_single_itemLL);
        }
    }

}
