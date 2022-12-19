package com.utkarshnew.android.LiveClass.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;
import com.utkarshnew.android.courses.Fragment.SingleStudy;
import com.utkarshnew.android.Download.Audio.AudioPlayerService;
import com.utkarshnew.android.Download.AudioPlayerActivty;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.home.liveclasses.Datum;
import com.utkarshnew.android.Model.UrlObject;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.table.AudioTable;
import com.utkarshnew.android.table.VideoTable;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;

public class Liveclassadapter extends RecyclerView.Adapter<Liveclassadapter.Livevideoviewholder> implements NetworkCall.MyNetworkCallBack {
    Activity activity;
    List<Datum> data;
    Long time;
    Datum datuml;
    boolean is_audio =false;
    UtkashRoom utkashRoom = UtkashRoom.getAppDatabase(MakeMyExam.getAppContext());

    public Liveclassadapter(Activity activity, List<Datum> data, Long time) {
        this.activity = activity;
        this.data = data;
        this.time = time;
    }


    @NonNull
    @Override
    public Livevideoviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.live_class_adapter, parent, false);
        return new Livevideoviewholder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Livevideoviewholder holder, int position) {
        holder.title.setText(data.get(position).getTitle());
        holder.watch_now.setVisibility(View.GONE);
        holder.listne_now.setVisibility(View.GONE);
        holder.maincard.setEnabled(false);
        if (data.get(position).getCourse_name() == null) {
            holder.course_name.setVisibility(View.GONE);
        } else {
            holder.course_name.setText(data.get(position).getCourse_name());
        }

        holder.liveDate.setText("Date: " + getdate(Long.parseLong(String.valueOf((Long.parseLong(data.get(position).getStartdate())) * 1000))));
        if (data.get(position).getLiveStatus()!=null && data.get(position).getLiveStatus().equalsIgnoreCase("3"))
        {
            holder.time.setVisibility(View.GONE);
            holder.watch_now.setEnabled(false);
            holder.listne_now.setEnabled(false);
            holder.timing.setTextColor(activity.getResources().getColor(R.color.red));
            holder.timing.setText("Live Class Has been Cancelled");
        }else {
            holder.watch_now.setEnabled(true);
            holder.listne_now.setEnabled(true);
            holder.timing.setTextColor(activity.getResources().getColor(R.color.blackApp));
            holder.timing.setText("Class Timing: " + getTiming(((Long.parseLong(data.get(position).getStartdate())) * 1000)));

            holder.timerr = 0;
            holder.timerr = Long.parseLong(data.get(position).getStartdate()) * 1000 - time * 1000;
            holder.timer = new CountDownTimer(holder.timerr, holder.timecount) {
                public void onTick(long millisUntilFinished) {
                    holder.time.setText(concerter(millisUntilFinished));
                }

                public void onFinish() {
                    holder.time.setText("00:00:00");
                    notifyadap(holder, position);
                }
            }.start();
        }




        if (data.get(position).getLiveStatus() != null) {
            if (data.get(position).getLiveStatus().equals("1")) {
                holder.liveIV.setVisibility(View.VISIBLE);
            } else {
                holder.liveIV.setVisibility(View.GONE);
            }
        } else {
            holder.liveIV.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(data.get(position).getThumbnailUrl())) {
            Helper.setThumbnailImage(activity, data.get(position).getThumbnailUrl(), activity.getResources().getDrawable(R.mipmap.course_placeholder), holder.courseImage);
        } else {
            holder.courseImage.setImageResource(R.mipmap.square_placeholder);
        }


        holder.watch_now.setOnClickListener(new OnSingleClickListener(() -> {
            is_audio =false;
            Helper.audio_service_close(activity);

            if (data.get(position).getVideoType().equalsIgnoreCase("5")) {
                if (data.get(position).getLiveStatus().equalsIgnoreCase("1")) {
                    if (TextUtils.isEmpty(data.get(position).getFileUrl()) && TextUtils.isEmpty(data.get(position).getId())) {
                        Toast.makeText(activity, "Url is not found", Toast.LENGTH_SHORT).show();
                    } else {

                        datuml = data.get(position);
                        NetworkCall networkCall = new NetworkCall(Liveclassadapter.this, activity);
                        networkCall.NetworkAPICall(API.get_video_link, "", true, false);



                        //  Helper.GoToLiveAwsVideoActivity(data.get(holder.getAdapterPosition()).getVideoType(), data.get(holder.getAdapterPosition()).getChatNode(), activity, data.get(position).getFileUrl(), "5", data.get(position).getId(), data.get(position).getTitle(), "0", data.get(holder.getAdapterPosition()).getThumbnailUrl(), data.get(holder.getAdapterPosition()).getPayload().getCourse_id(), data.get(holder.getAdapterPosition()).getPayload().getTile_id(), data.get(holder.getAdapterPosition()).getPayload().getTile_type(), data.get(holder.getAdapterPosition()).getIschatlock(), "", "");
                    }
                } else if (data.get(position).getLiveStatus().equalsIgnoreCase("0")) {

                    datuml = data.get(position);
                    NetworkCall networkCall = new NetworkCall(Liveclassadapter.this, activity);
                    networkCall.NetworkAPICall(API.get_video_link, "", true, false);



                    //  Helper.GoToLiveAwsVideoActivity(data.get(holder.getAdapterPosition()).getVideoType(), data.get(holder.getAdapterPosition()).getChatNode(), activity, data.get(position).getFileUrl(), "5", data.get(position).getId(), data.get(position).getTitle(), "0", data.get(holder.getAdapterPosition()).getThumbnailUrl(), data.get(holder.getAdapterPosition()).getPayload().getCourse_id(), data.get(holder.getAdapterPosition()).getPayload().getTile_id(), data.get(holder.getAdapterPosition()).getPayload().getTile_type(), data.get(holder.getAdapterPosition()).getIschatlock(), "", "");

                } else if (data.get(position).getLiveStatus().equalsIgnoreCase("2")) {
                    Toast.makeText(activity, "Live class is ended", Toast.LENGTH_SHORT).show();
                } else if (data.get(position).getLiveStatus().equalsIgnoreCase("3")) {
                    Toast.makeText(activity, "Live class is cancelled", Toast.LENGTH_SHORT).show();
                }
            } else if (data.get(position).getVideoType().equalsIgnoreCase("4")) {
                if (data.get(position).getLiveStatus().equalsIgnoreCase("1")) {
                    if (TextUtils.isEmpty(data.get(position).getFileUrl()) && TextUtils.isEmpty(data.get(position).getId())) {
                        Toast.makeText(activity, "Url is not found", Toast.LENGTH_SHORT).show();
                    } else {

                        if (data.get(position).getOpenInApp().equalsIgnoreCase("1")) {
                            Helper.GoToLiveVideoActivity(data.get(holder.getAdapterPosition()).getChatNode(), activity, data.get(position).getFileUrl(), "4", data.get(position).getId(), data.get(position).getTitle(), "0", data.get(holder.getAdapterPosition()).getThumbnailUrl(), data.get(holder.getAdapterPosition()).getIschatlock(), data.get(holder.getAdapterPosition()).getPayload().getCourse_id(), String.valueOf(position), "", data.get(holder.getAdapterPosition()).getPayload().getTile_id(), data.get(holder.getAdapterPosition()).getPayload().getTile_type());
                        } else {

                            datuml = data.get(position);
                            NetworkCall networkCall = new NetworkCall(Liveclassadapter.this, activity);
                            networkCall.NetworkAPICall(API.get_video_link, "", true, false);

                            //activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + data.get(position).getFileUrl())));
                        }
                    }
                } else if (data.get(position).getLiveStatus().equalsIgnoreCase("0")) {
                    if (data.get(position).getOpenInApp().equalsIgnoreCase("1")) {
                        Helper.GoToLiveVideoActivity(data.get(holder.getAdapterPosition()).getChatNode(), activity, data.get(position).getFileUrl(), "4", data.get(position).getId(), data.get(position).getTitle(), "0", data.get(holder.getAdapterPosition()).getThumbnailUrl(), data.get(holder.getAdapterPosition()).getIschatlock(), data.get(holder.getAdapterPosition()).getPayload().getCourse_id(), String.valueOf(position), "", data.get(holder.getAdapterPosition()).getPayload().getTile_id(), data.get(holder.getAdapterPosition()).getPayload().getTile_type());
                    } else {
                        datuml = data.get(position);
                        NetworkCall networkCall = new NetworkCall(Liveclassadapter.this, activity);
                        networkCall.NetworkAPICall(API.get_video_link, "", true, false);

                        // activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + data.get(position).getFileUrl())));
                    }
                } else if (data.get(position).getLiveStatus().equalsIgnoreCase("2")) {
                    Toast.makeText(activity, "Live class is ended", Toast.LENGTH_SHORT).show();
                }
            } else if (data.get(position).getVideoType().equalsIgnoreCase("0")) {


                datuml = data.get(position);
                NetworkCall networkCall = new NetworkCall(Liveclassadapter.this, activity);
                networkCall.NetworkAPICall(API.get_video_link, "", true, false);


                //  Helper.GoToLiveAwsVideoActivity(data.get(holder.getAdapterPosition()).getVideoType(), data.get(holder.getAdapterPosition()).getChatNode(), activity, data.get(holder.getAdapterPosition()).getId(), data.get(holder.getAdapterPosition()).getVideoType(), data.get(holder.getAdapterPosition()).getId(), data.get(holder.getAdapterPosition()).getTitle(), "0", data.get(holder.getAdapterPosition()).getThumbnailUrl(), data.get(holder.getAdapterPosition()).getPayload().getCourse_id(), data.get(holder.getAdapterPosition()).getPayload().getTile_id(), data.get(holder.getAdapterPosition()).getPayload().getTile_type(), data.get(holder.getAdapterPosition()).getIschatlock(), String.valueOf(position), SingleStudy.parentCourseId);
            } else if (data.get(position).getVideoType().equalsIgnoreCase("1")) {
                if (data.get(position).getOpenInApp() != null && data.get(position).getOpenInApp().equalsIgnoreCase("1")) {
                    Helper.GoToLiveVideoActivity(data.get(position).getChatNode(), activity, data.get(position).getFileUrl(), data.get(position).getVideoType(), data.get(position).getId(), data.get(position).getTitle(), "0", data.get(position).getThumbnailUrl(), data.get(position).getIschatlock(), data.get(position).getPayload().getCourse_id(), String.valueOf(position), SingleStudy.parentCourseId, data.get(position).getPayload().getTile_id(), data.get(position).getPayload().getTile_type());
                } else {
                    datuml = data.get(position);
                    NetworkCall networkCall = new NetworkCall(Liveclassadapter.this, activity);
                    networkCall.NetworkAPICall(API.get_video_link, "", true, false);
                    // activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + data.get(position).getFileUrl())));
                }
            } else if (data.get(position).getVideoType().equalsIgnoreCase("6")) {
                datuml = data.get(position);
                NetworkCall networkCall = new NetworkCall(Liveclassadapter.this, activity);
                networkCall.NetworkAPICall(API.get_video_link, "", true, false);
            }

            return null;
        }));

        holder.maincard.setOnClickListener(new OnSingleClickListener(() -> {
            is_audio =false;

            Helper.audio_service_close(activity);

            if (data.get(position).getVideoType().equalsIgnoreCase("5")) {
                if (data.get(position).getLiveStatus().equalsIgnoreCase("1")) {
                    if (TextUtils.isEmpty(data.get(position).getFileUrl()) && TextUtils.isEmpty(data.get(position).getId())) {
                        Toast.makeText(activity, "Url is not found", Toast.LENGTH_SHORT).show();
                    } else {


                        datuml = data.get(position);
                        NetworkCall networkCall = new NetworkCall(Liveclassadapter.this, activity);
                        networkCall.NetworkAPICall(API.get_video_link, "", true, false);


                    }
                } else if (data.get(position).getLiveStatus().equalsIgnoreCase("0")) {

                    datuml = data.get(position);
                    NetworkCall networkCall = new NetworkCall(Liveclassadapter.this, activity);
                    networkCall.NetworkAPICall(API.get_video_link, "", true, false);



                    // Helper.GoToLiveAwsVideoActivity(data.get(holder.getAdapterPosition()).getVideoType(), data.get(holder.getAdapterPosition()).getChatNode(), activity, data.get(position).getFileUrl(), "5", data.get(position).getId(), data.get(position).getTitle(), "0", data.get(holder.getAdapterPosition()).getThumbnailUrl(), data.get(holder.getAdapterPosition()).getPayload().getCourse_id(), data.get(holder.getAdapterPosition()).getPayload().getTile_id(), data.get(holder.getAdapterPosition()).getPayload().getTile_type(), data.get(holder.getAdapterPosition()).getIschatlock(), "", "");

                } else if (data.get(position).getLiveStatus().equalsIgnoreCase("2")) {
                    Toast.makeText(activity, "Live class is ended", Toast.LENGTH_SHORT).show();
                } else if (data.get(position).getLiveStatus().equalsIgnoreCase("3")) {
                    Toast.makeText(activity, "Live class is cancelled", Toast.LENGTH_SHORT).show();
                }
            } else if (data.get(position).getVideoType().equalsIgnoreCase("4")) {
                if (data.get(position).getLiveStatus().equalsIgnoreCase("1")) {
                    if (TextUtils.isEmpty(data.get(position).getFileUrl()) && TextUtils.isEmpty(data.get(position).getId())) {
                        Toast.makeText(activity, "Url is not found", Toast.LENGTH_SHORT).show();
                    } else {

                        if (data.get(position).getOpenInApp().equalsIgnoreCase("1")) {
                            Helper.GoToLiveVideoActivity(data.get(holder.getAdapterPosition()).getChatNode(), activity, data.get(position).getFileUrl(), "4", data.get(position).getId(), data.get(position).getTitle(), "0", data.get(holder.getAdapterPosition()).getThumbnailUrl(), data.get(holder.getAdapterPosition()).getIschatlock(), data.get(holder.getAdapterPosition()).getPayload().getCourse_id(), String.valueOf(position), "", data.get(holder.getAdapterPosition()).getPayload().getTile_id(), data.get(holder.getAdapterPosition()).getPayload().getTile_type());
                        } else {
                            datuml = data.get(position);
                            NetworkCall networkCall = new NetworkCall(Liveclassadapter.this, activity);
                            networkCall.NetworkAPICall(API.get_video_link, "", true, false);

                            //  activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + data.get(position).getFileUrl())));
                        }
                    }
                } else if (data.get(position).getLiveStatus().equalsIgnoreCase("0")) {
                    if (data.get(position).getOpenInApp().equalsIgnoreCase("1")) {
                        Helper.GoToLiveVideoActivity(data.get(holder.getAdapterPosition()).getChatNode(), activity, data.get(position).getFileUrl(), "4", data.get(position).getId(), data.get(position).getTitle(), "0", data.get(holder.getAdapterPosition()).getThumbnailUrl(), data.get(holder.getAdapterPosition()).getIschatlock(), data.get(holder.getAdapterPosition()).getPayload().getCourse_id(), String.valueOf(position), "", data.get(holder.getAdapterPosition()).getPayload().getTile_id(), data.get(holder.getAdapterPosition()).getPayload().getTile_type());
                    } else {
                        datuml = data.get(position);
                        NetworkCall networkCall = new NetworkCall(Liveclassadapter.this, activity);
                        networkCall.NetworkAPICall(API.get_video_link, "", true, false);

                        //  activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + data.get(position).getFileUrl())));
                    }
                } else if (data.get(position).getLiveStatus().equalsIgnoreCase("2")) {
                    Toast.makeText(activity, "Live class is ended", Toast.LENGTH_SHORT).show();
                }
            } else if (data.get(position).getVideoType().equalsIgnoreCase("0")) {

                datuml = data.get(position);
                NetworkCall networkCall = new NetworkCall(Liveclassadapter.this, activity);
                networkCall.NetworkAPICall(API.get_video_link, "", true, false);



                // Helper.GoToLiveAwsVideoActivity(data.get(holder.getAdapterPosition()).getVideoType(), data.get(holder.getAdapterPosition()).getChatNode(), activity, data.get(holder.getAdapterPosition()).getId(), data.get(holder.getAdapterPosition()).getVideoType(), data.get(holder.getAdapterPosition()).getId(), data.get(holder.getAdapterPosition()).getTitle(), "0", data.get(holder.getAdapterPosition()).getThumbnailUrl(), data.get(holder.getAdapterPosition()).getPayload().getCourse_id(), data.get(holder.getAdapterPosition()).getPayload().getTile_id(), data.get(holder.getAdapterPosition()).getPayload().getTile_type(), data.get(holder.getAdapterPosition()).getIschatlock(), String.valueOf(position), SingleStudy.parentCourseId);
            } else if (data.get(position).getVideoType().equalsIgnoreCase("1")) {
                if (data.get(position).getOpenInApp() != null && data.get(position).getOpenInApp().equalsIgnoreCase("1")) {
                    Helper.GoToLiveVideoActivity(data.get(position).getChatNode(), activity, data.get(position).getFileUrl(), data.get(position).getVideoType(), data.get(position).getId(), data.get(position).getTitle(), "0", data.get(position).getThumbnailUrl(), data.get(position).getIschatlock(), data.get(position).getPayload().getCourse_id(), String.valueOf(position), SingleStudy.parentCourseId, data.get(position).getPayload().getTile_id(), data.get(position).getPayload().getTile_type());
                } else {
                    datuml = data.get(position);
                    NetworkCall networkCall = new NetworkCall(Liveclassadapter.this, activity);
                    networkCall.NetworkAPICall(API.get_video_link, "", true, false);

                    //  activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + data.get(position).getFileUrl())));
                }
            } else if (data.get(position).getVideoType().equalsIgnoreCase("6")) {
                datuml = data.get(position);
                is_audio =false;
                NetworkCall networkCall = new NetworkCall(Liveclassadapter.this, activity);
                networkCall.NetworkAPICall(API.get_video_link, "", true, false);

            }

            return null;
        }));

        holder.listne_now.setOnClickListener(new OnSingleClickListener(() -> {
            Helper.audio_service_close(activity);

            if (data.get(position).getVideoType().equalsIgnoreCase("5")) {
                if (data.get(position).getLiveStatus().equalsIgnoreCase("1")) {
                    if (TextUtils.isEmpty(data.get(position).getFileUrl()) && TextUtils.isEmpty(data.get(position).getId())) {
                        Toast.makeText(activity, "Url is not found", Toast.LENGTH_SHORT).show();
                    } else {

                        is_audio =true;
                        datuml = data.get(position);
                        NetworkCall networkCall = new NetworkCall(Liveclassadapter.this, activity);
                        networkCall.NetworkAPICall(API.get_video_link, "", true, false);



                        //   Helper.GoToLiveAwsVideoActivity(data.get(holder.getAdapterPosition()).getVideoType(), data.get(holder.getAdapterPosition()).getChatNode(), activity, data.get(position).getFileUrl(), "5", data.get(position).getId(), data.get(position).getTitle(), "1", data.get(holder.getAdapterPosition()).getThumbnailUrl(), data.get(holder.getAdapterPosition()).getPayload().getCourse_id(), data.get(holder.getAdapterPosition()).getPayload().getTile_id(), data.get(holder.getAdapterPosition()).getPayload().getTile_type(), data.get(holder.getAdapterPosition()).getIschatlock(), "", "");
                    }
                } else if (data.get(position).getLiveStatus().equalsIgnoreCase("0")) {
                    is_audio =true;

                    datuml = data.get(position);
                    NetworkCall networkCall = new NetworkCall(Liveclassadapter.this, activity);
                    networkCall.NetworkAPICall(API.get_video_link, "", true, false);



                    //   Helper.GoToLiveAwsVideoActivity(data.get(holder.getAdapterPosition()).getVideoType(), data.get(holder.getAdapterPosition()).getChatNode(), activity, data.get(position).getFileUrl(), "5", data.get(position).getId(), data.get(position).getTitle(), "1", data.get(holder.getAdapterPosition()).getThumbnailUrl(), data.get(holder.getAdapterPosition()).getPayload().getCourse_id(), data.get(holder.getAdapterPosition()).getPayload().getTile_id(), data.get(holder.getAdapterPosition()).getPayload().getTile_type(), data.get(holder.getAdapterPosition()).getIschatlock(), "", "");

                } else if (data.get(position).getLiveStatus().equalsIgnoreCase("2")) {
                    Toast.makeText(activity, "Live class is ended", Toast.LENGTH_SHORT).show();
                } else if (data.get(position).getLiveStatus().equalsIgnoreCase("3")) {
                    Toast.makeText(activity, "Live class is cancelled", Toast.LENGTH_SHORT).show();
                }
            } else if (data.get(position).getVideoType().equalsIgnoreCase("4")) {
                if (data.get(position).getLiveStatus().equalsIgnoreCase("1")) {
                    if (TextUtils.isEmpty(data.get(position).getFileUrl()) && TextUtils.isEmpty(data.get(position).getId())) {
                        Toast.makeText(activity, "Url is not found", Toast.LENGTH_SHORT).show();
                    } else {
                        Helper.GoToLiveVideoActivity(data.get(holder.getAdapterPosition()).getChatNode(), activity, data.get(position).getFileUrl(), "4", data.get(position).getId(), data.get(position).getTitle(), "1", data.get(holder.getAdapterPosition()).getThumbnailUrl(), data.get(holder.getAdapterPosition()).getIschatlock(), data.get(holder.getAdapterPosition()).getPayload().getCourse_id(), String.valueOf(position), "", data.get(holder.getAdapterPosition()).getPayload().getTile_id(), data.get(holder.getAdapterPosition()).getPayload().getTile_type());
                    }
                } else if (data.get(position).getLiveStatus().equalsIgnoreCase("0")) {
                    Helper.GoToLiveVideoActivity(data.get(holder.getAdapterPosition()).getChatNode(), activity, data.get(position).getFileUrl(), "4", data.get(position).getId(), data.get(position).getTitle(), "1", data.get(holder.getAdapterPosition()).getThumbnailUrl(), data.get(holder.getAdapterPosition()).getIschatlock(), data.get(holder.getAdapterPosition()).getPayload().getCourse_id(), String.valueOf(position), "", data.get(holder.getAdapterPosition()).getPayload().getTile_id(), data.get(holder.getAdapterPosition()).getPayload().getTile_type());
                } else if (data.get(position).getLiveStatus().equalsIgnoreCase("2")) {
                    Toast.makeText(activity, "Live class is ended", Toast.LENGTH_SHORT).show();
                }
            } else if (data.get(position).getVideoType().equalsIgnoreCase("0")) {

                is_audio =true;
                datuml = data.get(position);
                NetworkCall networkCall = new NetworkCall(Liveclassadapter.this, activity);
                networkCall.NetworkAPICall(API.get_video_link, "", true, false);



                //  Helper.GoToLiveAwsVideoActivity(data.get(holder.getAdapterPosition()).getVideoType(), data.get(holder.getAdapterPosition()).getChatNode(), activity, data.get(holder.getAdapterPosition()).getId(), data.get(holder.getAdapterPosition()).getVideoType(), data.get(holder.getAdapterPosition()).getId(), data.get(holder.getAdapterPosition()).getTitle(), "1", data.get(holder.getAdapterPosition()).getThumbnailUrl(), data.get(holder.getAdapterPosition()).getPayload().getCourse_id(), data.get(holder.getAdapterPosition()).getPayload().getTile_id(), data.get(holder.getAdapterPosition()).getPayload().getTile_type(), data.get(holder.getAdapterPosition()).getIschatlock(), String.valueOf(position), SingleStudy.parentCourseId);
            } else if (data.get(position).getVideoType().equalsIgnoreCase("1")) {
                if (data.get(position).getOpenInApp() != null && data.get(position).getOpenInApp().equalsIgnoreCase("1")) {
                    Helper.GoToLiveVideoActivity(data.get(position).getChatNode(), activity, data.get(position).getFileUrl(), data.get(position).getVideoType(), data.get(position).getId(), data.get(position).getTitle(), "1", data.get(position).getThumbnailUrl(), data.get(position).getIschatlock(), data.get(position).getPayload().getCourse_id(), String.valueOf(position), SingleStudy.parentCourseId, data.get(position).getPayload().getTile_id(), data.get(position).getPayload().getTile_type());
                } else {

                    datuml = data.get(position);
                    NetworkCall networkCall = new NetworkCall(Liveclassadapter.this, activity);
                    networkCall.NetworkAPICall(API.get_video_link, "", true, false);

                    //   activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + data.get(position).getFileUrl())));
                }
            } else if (data.get(position).getVideoType().equalsIgnoreCase("6")) {

                is_audio =true;
                datuml = data.get(position);
                NetworkCall networkCall = new NetworkCall(Liveclassadapter.this, activity);
                networkCall.NetworkAPICall(API.get_video_link, "", true, false);

            }

            return null;
        }));

        holder.share.setOnClickListener(new OnSingleClickListener((() -> {
            sharelivevideolink(holder.getAdapterPosition());
            return null;
        })));
    }

    public void extractJWPlayerUrl(String mediaUrl, AudioTable audioTable, Datum videoData) {
        if (mediaUrl != null && !mediaUrl.equalsIgnoreCase("")) {
            if (AudioPlayerService.player != null) {
                AudioPlayerService.player.release();
                AudioPlayerService.player = null;
            }
            if (AudioPlayerService.isAudioPlaying) {
                Intent audioPlayerIntent = new Intent(activity, AudioPlayerService.class);
                audioPlayerIntent.setAction("Stop_Service");
                Util.startForegroundService(activity, audioPlayerIntent);
                AudioPlayerService.video_currentpos = 0L;
                AudioPlayerService.media_id = "";
            }
            Intent intent = new Intent(activity, AudioPlayerActivty.class);
            AudioPlayerService.videoid = "";
            AudioPlayerService.media_id = "";
            intent.putExtra("url", mediaUrl);
            intent.putExtra("videoid", videoData.getId());
            intent.putExtra("currentpos", audioTable.getAudio_currentpos());
            intent.putExtra("video_name", videoData.getTitle());
            intent.putExtra("image_url", videoData.getThumbnailUrl());
            intent.putExtra("course_id", videoData.getPayload().getCourse_id() + "#");
            intent.putExtra("tile_id", videoData.getPayload().getTile_id());
            intent.putExtra("tile_type", videoData.getPayload().getTile_type());
            Helper.gotoActivity(intent, activity);
        }
    }


    private void sharelivevideolink(int adapterPosition) {
        Helper.shareLiveClass(activity, data.get(adapterPosition).getPayload().getCourse_id(), data.get(adapterPosition).getId(), data.get(adapterPosition).getPayload().getTopic_id(), data.get(adapterPosition).getPayload().getTile_type(), data.get(adapterPosition).getPayload().getTile_id(), data.get(adapterPosition).getPayload().getRevert_api(), "video", data.get(adapterPosition).getThumbnailUrl(), data.get(adapterPosition).getTitle(), "");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.get_video_link:
                EncryptionData encryptionData = new EncryptionData();
                encryptionData.setName(datuml.getId() + "_" + "0" + "_" + "0");
                encryptionData.setCourse_id(datuml.getPayload().getCourse_id());
                encryptionData.setTile_id(datuml.getPayload().getTile_id());
                encryptionData.setType(datuml.getPayload().getTile_type());
                String  device_id =   (Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID));
                String    device_name = android.os.Build.MANUFACTURER + android.os.Build.MODEL;
                device_id = device_id ==null && device_id.equalsIgnoreCase("") ? "1234567890":device_id;
                encryptionData.setDevice_id(device_id);
                encryptionData.setDevice_name(device_name);

                String data1 = new Gson().toJson(encryptionData);
                String encryptjson = AES.encrypt(data1);
                return service.getVideoLink(encryptjson);}
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonstring, String apitype, String typeApi, boolean showprogress) throws JSONException {
        Gson gson = new Gson();
        switch (apitype) {

            case API.get_video_link:
                try {
                    if (jsonstring.getString("status").equalsIgnoreCase("true")) {
                        long expire_time=0;
                        JSONObject jsonObject = new JSONObject(jsonstring.toString());
                        if (jsonObject.has("data")) {
                            JSONObject dataJsonObject = jsonObject.getJSONObject(Const.DATA);
                            String link = jsonObject.getJSONObject("data").getString("link");
                            String content_type =     dataJsonObject.optString("content_type");

                            if (datuml.getVideoType().equalsIgnoreCase("5") && datuml.getLiveStatus().equalsIgnoreCase("1")) {
                                if (is_audio)
                                {
                                    if (content_type.equalsIgnoreCase("2"))
                                    {
                                        Helper.GoToLiveDrmVideoActivity(datuml.getVideoType(), datuml.getChatNode(), activity, datuml.getFileUrl(), "5", datuml.getId(), datuml.getTitle(), "1", datuml.getThumbnailUrl(), datuml.getPayload().getCourse_id(), datuml.getPayload().getTile_id(), datuml.getPayload().getTile_type(), datuml.getIschatlock(), "", "",jsonstring.toString());

                                    }else {
                                        Helper.GoToLiveAwsVideoActivity(datuml.getVideoType(), datuml.getChatNode(), activity, datuml.getFileUrl(), "5", datuml.getId(), datuml.getTitle(), "1", datuml.getThumbnailUrl(), datuml.getPayload().getCourse_id(), datuml.getPayload().getTile_id(), datuml.getPayload().getTile_type(), datuml.getIschatlock(), "", "",jsonstring.toString());

                                    }

                                    is_audio =false;
                                }else {
                                    if (content_type.equalsIgnoreCase("2"))
                                    {
                                        Helper.GoToLiveDrmVideoActivity(datuml.getVideoType(), datuml.getChatNode(), activity, datuml.getFileUrl(), "5", datuml.getId(), datuml.getTitle(), "0", datuml.getThumbnailUrl(), datuml.getPayload().getCourse_id(), datuml.getPayload().getTile_id(), datuml.getPayload().getTile_type(), datuml.getIschatlock(), "", "",jsonstring.toString());

                                    }else {
                                        Helper.GoToLiveAwsVideoActivity(datuml.getVideoType(), datuml.getChatNode(), activity, datuml.getFileUrl(), "5", datuml.getId(), datuml.getTitle(), "0", datuml.getThumbnailUrl(), datuml.getPayload().getCourse_id(), datuml.getPayload().getTile_id(), datuml.getPayload().getTile_type(), datuml.getIschatlock(), "", "",jsonstring.toString());

                                    }

                                }


                            } else if (datuml.getVideoType().equalsIgnoreCase("5") && datuml.getLiveStatus().equalsIgnoreCase("0")) {
                                if (is_audio)
                                {
                                    if (content_type.equalsIgnoreCase("2"))
                                    {
                                        Helper.GoToLiveDrmVideoActivity(datuml.getVideoType(), datuml.getChatNode(), activity, datuml.getFileUrl(), "5", datuml.getId(), datuml.getTitle(), "1", datuml.getThumbnailUrl(), datuml.getPayload().getCourse_id(), datuml.getPayload().getTile_id(), datuml.getPayload().getTile_type(), datuml.getIschatlock(), "", "",jsonstring.toString());

                                    }else {
                                        Helper.GoToLiveAwsVideoActivity(datuml.getVideoType(), datuml.getChatNode(), activity, datuml.getFileUrl(), "5", datuml.getId(), datuml.getTitle(), "1", datuml.getThumbnailUrl(), datuml.getPayload().getCourse_id(), datuml.getPayload().getTile_id(), datuml.getPayload().getTile_type(), datuml.getIschatlock(), "", "",jsonstring.toString());

                                    }
                                    is_audio =false;
                                }else {
                                    if (content_type.equalsIgnoreCase("2"))
                                    {
                                        Helper.GoToLiveDrmVideoActivity(datuml.getVideoType(), datuml.getChatNode(), activity, datuml.getFileUrl(), "5", datuml.getId(), datuml.getTitle(), "0", datuml.getThumbnailUrl(), datuml.getPayload().getCourse_id(), datuml.getPayload().getTile_id(), datuml.getPayload().getTile_type(), datuml.getIschatlock(), "", "",jsonstring.toString());

                                    }else {
                                        Helper.GoToLiveAwsVideoActivity(datuml.getVideoType(), datuml.getChatNode(), activity, datuml.getFileUrl(), "5", datuml.getId(), datuml.getTitle(), "0", datuml.getThumbnailUrl(), datuml.getPayload().getCourse_id(), datuml.getPayload().getTile_id(), datuml.getPayload().getTile_type(), datuml.getIschatlock(), "", "",jsonstring.toString());

                                    }

                                }


                            }else if (datuml.getVideoType().equalsIgnoreCase("0")) {
                                if (is_audio)
                                {
                                    if (content_type.equalsIgnoreCase("2"))
                                    {
                                        Helper.GoToLiveDrmVideoActivity(datuml.getVideoType(), datuml.getChatNode(), activity, datuml.getId(), datuml.getVideoType(), datuml.getId(), datuml.getTitle(), "1", datuml.getThumbnailUrl(), datuml.getPayload().getCourse_id(), datuml.getPayload().getTile_id(), datuml.getPayload().getTile_type(), datuml.getIschatlock(), String.valueOf(0), SingleStudy.parentCourseId,jsonstring.toString());

                                    }else {
                                        Helper.GoToLiveAwsVideoActivity(datuml.getVideoType(), datuml.getChatNode(), activity, datuml.getId(), datuml.getVideoType(), datuml.getId(), datuml.getTitle(), "1", datuml.getThumbnailUrl(), datuml.getPayload().getCourse_id(), datuml.getPayload().getTile_id(), datuml.getPayload().getTile_type(), datuml.getIschatlock(), String.valueOf(0), SingleStudy.parentCourseId,jsonstring.toString());

                                    }
                                    is_audio =false;
                                }else {
                                    if (content_type.equalsIgnoreCase("2"))
                                    {
                                        Helper.GoToLiveDrmVideoActivity(datuml.getVideoType(), datuml.getChatNode(), activity, datuml.getId(), datuml.getVideoType(), datuml.getId(), datuml.getTitle(), "0", datuml.getThumbnailUrl(), datuml.getPayload().getCourse_id(), datuml.getPayload().getTile_id(), datuml.getPayload().getTile_type(), datuml.getIschatlock(), String.valueOf(0), SingleStudy.parentCourseId,jsonstring.toString());

                                    }else {
                                        Helper.GoToLiveAwsVideoActivity(datuml.getVideoType(), datuml.getChatNode(), activity, datuml.getId(), datuml.getVideoType(), datuml.getId(), datuml.getTitle(), "0", datuml.getThumbnailUrl(), datuml.getPayload().getCourse_id(), datuml.getPayload().getTile_id(), datuml.getPayload().getTile_type(), datuml.getIschatlock(), String.valueOf(0), SingleStudy.parentCourseId,jsonstring.toString());

                                    }
                                }


                            }else if (datuml!=null &&  datuml.getVideoType().equalsIgnoreCase("4") )
                            {
                                if ((datuml.getLiveStatus().equalsIgnoreCase("1") || datuml.getLiveStatus().equalsIgnoreCase("0"))  && datuml.getOpenInApp().equalsIgnoreCase("0"))
                                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + link)));

                            }else if (datuml!=null &&  datuml.getVideoType().equalsIgnoreCase("1"))
                            {
                                if (datuml.getOpenInApp().equalsIgnoreCase("0"))
                                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + link)));

                            }
                            else {
                                String audio_url =   dataJsonObject.optString("audio_url");
                                if (dataJsonObject.optLong("expiry_seconds")!=0)
                                {
                                    expire_time = (Long.parseLong(jsonstring.optString("time"))) +dataJsonObject.optLong("expiry_seconds");
                                }
                                if (dataJsonObject.has("bitrate_urls") ) {
                                    JSONArray arrJson = dataJsonObject.optJSONArray("bitrate_urls");
                                    if (arrJson != null && arrJson.length() > 0) {
                                        ArrayList<UrlObject> urlObjects = new ArrayList<>();
                                        for (int i = 0; i < Objects.requireNonNull(arrJson).length(); i++) {
                                            JSONObject dataObj = arrJson.optJSONObject(i);
                                            UrlObject questionBank = new Gson().fromJson(dataObj.toString(), UrlObject.class);
                                            urlObjects.add(questionBank);
                                        }
                                        datuml.setBitrate_urls(urlObjects);
                                    }
                                }
                                if (is_audio)
                                {
                                    is_audio =false;
                                    if (!utkashRoom.getaudiodao().isvideo_exit(datuml.getId(), MakeMyExam.userId)) {
                                        AudioTable audioTable = new AudioTable();
                                        audioTable.setVideo_id(datuml.getId());
                                        audioTable.setJw_url(datuml.getThumbnailUrl());
                                        audioTable.setVideo_name(datuml.getTitle());
                                        audioTable.setAudio_currentpos(0L);
                                        audioTable.setUser_id(MakeMyExam.userId);
                                        audioTable.setCourse_id(datuml.getPayload().getCourse_id() +"#");
                                        utkashRoom.getaudiodao().addUser(audioTable);
                                        extractJWPlayerUrl(audio_url, audioTable,datuml);
                                    } else {
                                        AudioTable audioTable = new AudioTable();
                                        audioTable.setVideo_id(datuml.getId());
                                        audioTable.setJw_url(datuml.getThumbnailUrl());
                                        audioTable.setVideo_name(datuml.getTitle());
                                        audioTable.setAudio_currentpos(utkashRoom.getaudiodao().getuser(datuml.getId(), MakeMyExam.userId).getAudio_currentpos());
                                        extractJWPlayerUrl(audio_url, audioTable,datuml);

                                    }
                                }else {
                                    if (utkashRoom.getvideoDao().isvideo_exit(datuml.getId(), MakeMyExam.userId)) {
                                        Helper.GoToJWVideo_Params_newarray(activity, link, datuml.getId(), datuml.getTitle(), utkashRoom.getvideoDao().getuser(datuml.getId(), MakeMyExam.userId).getVideo_currentpos(), datuml.getPayload().getCourse_id() + "#", datuml.getPayload().getTile_id(), datuml.getPayload().getTile_type(),datuml.getBitrate_urls(),expire_time);
                                    } else {
                                        VideoTable videoTable = new VideoTable();
                                        videoTable.setVideo_id(datuml.getId());
                                        videoTable.setVideo_name(datuml.getTitle());
                                        videoTable.setJw_url(link);
                                        videoTable.setVideo_currentpos(0L);
                                        videoTable.setUser_id(MakeMyExam.userId);
                                        videoTable.setCourse_id(datuml.getPayload().getCourse_id() + "#");
                                        Helper.GoToJWVideo_Params_newarray(activity, link, datuml.getId(), datuml.getTitle(), 0L, datuml.getPayload().getCourse_id() + "#", datuml.getPayload().getTile_id(), datuml.getPayload().getTile_type(),datuml.getBitrate_urls(),expire_time);
                                        utkashRoom.getvideoDao().addUser(videoTable);

                                    }
                                }
                            }
                        }

                    }
                    is_audio =false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {
        is_audio =false;
    }

    public class Livevideoviewholder extends RecyclerView.ViewHolder {
        ImageView courseImage;
        long timecount = 1;
        GifImageView liveIV;
        ImageView forward;
        TextView title, watch_now, listne_now, share, liveDate, timing, course_name;
        LinearLayout study_single_itemLL;
        CardView maincard;
        long timerr;
        TextView time, startedin;
        CountDownTimer timer;

        public Livevideoviewholder(View itemView) {
            super(itemView);
            courseImage = itemView.findViewById(R.id.courseImage);
            time = itemView.findViewById(R.id.time);
            course_name = itemView.findViewById(R.id.course_name);
            share = itemView.findViewById(R.id.share);
            liveIV = itemView.findViewById(R.id.liveIV);
            watch_now = itemView.findViewById(R.id.watch_now);
            startedin = itemView.findViewById(R.id.startedin);
            listne_now = itemView.findViewById(R.id.listne_now);
            forward = itemView.findViewById(R.id.forwardIV);
            title = itemView.findViewById(R.id.study_item_titleTV);
            study_single_itemLL = itemView.findViewById(R.id.study_single_itemLL);
            maincard = itemView.findViewById(R.id.maincard);
            liveDate = itemView.findViewById(R.id.liveDate);
            timing = itemView.findViewById(R.id.timing);
            forward.setVisibility(View.GONE);
        }
    }


    public String getdate(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getDefault();
        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        java.util.Date currenTimeZone = new java.util.Date((long) Long.parseLong(String.valueOf(timestamp)));
        return Helper.changeAMPM(sdf.format(currenTimeZone));
    }

    public String getTiming(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getDefault();
        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        java.util.Date currenTimeZone = new java.util.Date((long) Long.parseLong(String.valueOf(timestamp)));
        return sdf.format(currenTimeZone);
    }

    public String concerter(long time) {

        String actualtime = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(time),
                TimeUnit.MILLISECONDS.toMinutes(time) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time)), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(time) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)));


//        long hr = TimeUnit.MILLISECONDS.toHours(time)
//                - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(time));
//        long min = TimeUnit.MILLISECONDS.toMinutes(time)
//                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time));
//        long sec = TimeUnit.MILLISECONDS.toSeconds(time)
//                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time));
////        String actualtime=String.valueOf(hr)+":"+String.valueOf(min)+":"+String.valueOf(sec);
//        String actualtime=String.format("%02d:%02d:%02d",hr,(min),sec);
        return actualtime;
    }

    public void notifyadap(Livevideoviewholder holder, int position) {
        holder.watch_now.setVisibility(View.VISIBLE);
        holder.listne_now.setVisibility(View.VISIBLE);
        holder.startedin.setVisibility(View.GONE);
        holder.time.setVisibility(View.GONE);
        holder.maincard.setEnabled(true);
//        holder.liveIV.setVisibility(View.VISIBLE);
    }
}