package com.utkarshnew.android.Notification.Adapter;

import static com.utkarshnew.android.Notification.Notification.server_time;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.utkarshnew.android.Coupon.Activity.CouponActivity;
import com.utkarshnew.android.Model.NotificationModel.Extras;
import com.utkarshnew.android.courses.Activity.Concept_newActivity;
import com.utkarshnew.android.courses.Activity.CourseActivity;
import com.utkarshnew.android.courses.Activity.QuizActivity;
import com.utkarshnew.android.courses.Activity.WebFragActivity;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.Model.NotificationModel.Datum;
import com.utkarshnew.android.Model.UrlObject;
import com.utkarshnew.android.Model.Video;
import com.utkarshnew.android.Notification.NotificationDescription;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.home.Activity.HomeActivity;
import com.utkarshnew.android.table.TestTable;
import com.utkarshnew.android.table.VideoTable;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;
import com.utkarshnew.android.Utils.SharedPreference;
import com.utkarshnew.android.Webview.WebViewActivty;
import com.utkarshnew.android.testmodule.activity.TestBaseActivity;
import com.utkarshnew.android.testmodule.model.InstructionData;
import com.utkarshnew.android.testmodule.model.TestBasicInst;
import com.utkarshnew.android.testmodule.model.TestSectionInst;
import com.utkarshnew.android.testmodule.model.TestseriesBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import me.leolin.shortcutbadger.ShortcutBadger;
import retrofit2.Call;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.Notification> implements NetworkCall.MyNetworkCallBack {
    List<Datum> notificationlist;
    Activity activity;
    RecyclerView recyclerView;
    NetworkCall networkCall;
    public String selectedpositionid = "0";
    public int selectedposition = 0;
    UtkashRoom utkashRoom;
    public  Video datuml;
    public String type="";
    String first_attempt = "", result_date = "", submition_type = "",  quiz_id = "";;
    String testname = "";
    String testquestion = "";
    private Video videodata;
    int lang;  // 0 for Hindi , 1 for English


    public NotificationAdapter(Activity activity, List<Datum> data) {
        this.activity = activity;
        this.notificationlist = data;
    }

    @NonNull
    @Override
    public Notification onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        networkCall = new NetworkCall(this, activity);

        utkashRoom = UtkashRoom.getAppDatabase(activity);
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_adapter, parent, false);
        return new Notification(itemView);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull Notification holder, @SuppressLint("RecyclerView") int position) {

        holder.notification_title.setText(notificationlist.get(position).getTitle());

        if (notificationlist.get(position).getMessage() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.descriptionTV.setText(Html.fromHtml(notificationlist.get(position).getMessage(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                holder.descriptionTV.setText(Html.fromHtml(notificationlist.get(position).getMessage()));
            }
        }


        holder.date.setText(getdate(String.valueOf(Long.parseLong(notificationlist.get(position).getCreated()) * 1000)));
        holder.date2.setText(getdate(String.valueOf(Long.parseLong(notificationlist.get(position).getCreated()) * 1000)));

        if (notificationlist.get(position).getViewState().equalsIgnoreCase("1")) {
            holder.rl1.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.notifi_unselected_color));
        } else {

            holder.rl1.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.notifi_selected_color));
        }

        if(notificationlist.get(position).getActionElement().equalsIgnoreCase("2") ||
                notificationlist.get(position).getActionElement().equalsIgnoreCase("8")   || notificationlist.get(position).getActionElement().equalsIgnoreCase("4")
        )
        {
            holder.seemore.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.seemore.setVisibility(View.GONE);
        }

        holder.seemore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notificationlist.get(position).getMessage() != null) {
                    holder.descriptionTVfull.setVisibility(View.VISIBLE);
                    holder.descriptionTV.setVisibility(View.GONE);
                    holder.seemore.setVisibility(View.GONE);
                    holder.seeless.setVisibility(View.VISIBLE);
                    holder.date2.setVisibility(View.VISIBLE);
                    holder.date.setVisibility(View.GONE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        holder.descriptionTVfull.setText(Html.fromHtml(notificationlist.get(position).getMessage(), Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        holder.descriptionTVfull.setText(Html.fromHtml(notificationlist.get(position).getMessage()));
                    }
                }
            }
        });


        holder.seeless.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notificationlist.get(position).getMessage() != null) {
                    holder.descriptionTVfull.setVisibility(View.GONE);
                    holder.descriptionTV.setVisibility(View.VISIBLE);
                    holder.seemore.setVisibility(View.VISIBLE);
                    holder.seeless.setVisibility(View.GONE);
                    holder.date2.setVisibility(View.GONE);
                    holder.date.setVisibility(View.VISIBLE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        holder.descriptionTV.setText(Html.fromHtml(notificationlist.get(position).getMessage(), Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        holder.descriptionTV.setText(Html.fromHtml(notificationlist.get(position).getMessage()));
                    }
                }
            }
        });


        holder.removeNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedpositionid = notificationlist.get(position).getId();
                selectedposition = position;
                delete_notification_api();
            }
        });

        holder.rl1.setOnClickListener(new OnSingleClickListener(() -> {
            if (notificationlist.get(position).getViewState().equalsIgnoreCase("0")) {
                selectedpositionid = notificationlist.get(position).getId();
                selectedposition = position;
                hit_read_api();
            }

            if (notificationlist.get(position).getActionElement() != null) {
                if (notificationlist.get(position).getActionElement().equalsIgnoreCase("7")) {
                    if (notificationlist.get(position).getExtra()!=null && notificationlist.get(position).getExtra().getMain_cat()!=null)
                    {
                        Extras extras =notificationlist.get(position).getExtra();

                        Intent intent = new Intent(activity, HomeActivity.class);
                        intent.putExtra("master_cat",extras.getMaster_cat());
                        intent.putExtra(Const.NOTIFICATION_CODE,90001);
                        intent.putExtra("target","7");
                        intent.putExtra("main_cat",extras.getMain_cat());
                        intent.putExtra("sub_cat",extras.getSub_cat());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(intent);
                        activity.finish();
                    }


                    // activity.finish();
                }else
                if (notificationlist.get(position).getActionElement().equalsIgnoreCase("2")) {
                    Intent courseList = new Intent(activity, CourseActivity.class);//FRAG_TYPE, Const.SINGLE_COURSE AllCoursesAdapter
                    courseList.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                    courseList.putExtra(Const.COURSE_ID_MAIN, notificationlist.get(position).getActionElementId());
                    courseList.putExtra(Const.COURSE_PARENT_ID, "0");
                    courseList.putExtra(Const.IS_COMBO, false);
                    activity.startActivity(courseList);
                }
                else if (notificationlist.get(position).getActionElement().equalsIgnoreCase("5"))
                {
                    Intent intent;
                    intent = new Intent(activity, NotificationDescription.class);
                    intent.putExtra("urlType", "IMAGE");
                    intent.putExtra("title", notificationlist.get(position).getTitle());
                    if (notificationlist.get(position).getExtra().getImage_2()!=null && !notificationlist.get(position).getExtra().getImage_2().isEmpty())
                    {
                        intent.putExtra("url", notificationlist.get(position).getExtra().getImage_2());
                    }else {
                        intent.putExtra("url", notificationlist.get(position).getExtra().getImage());
                    }
                    intent.putExtra("description", notificationlist.get(position).getMessage());
                    intent.putExtra("time", getdate(String.valueOf(Long.parseLong(notificationlist.get(position).getCreated()) * 1000)));
                /*intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(notificationlist.get(position).getExtra().getNotificationText()), "image/*");*/
                    Helper.gotoActivity(intent,activity);
                }
                else if (notificationlist.get(position).getActionElement().equalsIgnoreCase("6")) {
                    Intent intent;
                    // intent = new Intent(activity, WebFragActivity.class);
                    intent = new Intent(activity, NotificationDescription.class);
                    intent.putExtra("urlType", "URL");
                    intent.putExtra("title", notificationlist.get(position).getTitle());
                    intent.putExtra("url", notificationlist.get(position).getExtra().getUrl());
                    intent.putExtra("description", notificationlist.get(position).getMessage());
                    intent.putExtra("time", getdate(String.valueOf(Long.parseLong(notificationlist.get(position).getCreated()) * 1000)));
                    Helper.gotoActivity(intent,activity);
                }
                else if (notificationlist.get(position).getActionElement().equalsIgnoreCase("4")) {
                    selectedposition = position;
                    type=notificationlist.get(position).getExtra().getTiletype();
                    hit_api_for_data();
                }
                else if (notificationlist.get(position).getActionElement().equalsIgnoreCase("1")) {
                    Intent intent;
                    intent = new Intent(activity, WebFragActivity.class);
                    intent.putExtra("title", notificationlist.get(position).getTitle());
                    intent.putExtra("url", notificationlist.get(position).getMessage());
                    intent.putExtra("from", "noti");
                    activity.startActivity(intent);
                }else if (notificationlist.get(position).getActionElement().equalsIgnoreCase("8")) {
                    if (notificationlist.get(position).getExtra().getStart_date()!=null)
                    {
                        if (server_time > (Long.parseLong(notificationlist.get(position).getExtra().getStart_date()) * 1000) && server_time < (Long.parseLong(notificationlist.get(position).getExtra().getEnd_date()) * 1000)) {
                            if (notificationlist.get(position).getExtra().getCoupon_for().equalsIgnoreCase("1")) {
                                Toast.makeText(activity, "Eligible For All Courses", Toast.LENGTH_SHORT).show();
                                activity.finish();
                            } else {
                                Intent intent = new Intent(activity, CouponActivity.class);
                                Helper.gotoActivity_finish(intent, activity);
                            }
                        } else if ((Long.parseLong(notificationlist.get(position).getExtra().getStart_date()) * 1000) > server_time) {
                            DateFormat fe = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.US);
                            long end_millis = (Long.parseLong(notificationlist.get(position).getExtra().getStart_date()) * 1000);
                            Date de = new Date(end_millis);
                            String edateString = fe.format(de);
                            Toast.makeText(activity, "This Coupon Is Available On " + Helper.changeAMPM(edateString), Toast.LENGTH_SHORT).show();
                        } else if ((Long.parseLong(notificationlist.get(position).getExtra().getEnd_date()) * 1000) < server_time) {
                            Toast.makeText(activity, "This Coupon Is Expired", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
            return null;
        }));
    }

    private void hit_api_for_data() {
        networkCall.NetworkAPICall(API.API_GET_MASTER_DATA, type, true, false);    }


    private void hit_read_api() {
        networkCall.NetworkAPICall(API.mark_as_read, "", false, false);
    }

    public void delete_notification_api()
    {
        networkCall.NetworkAPICall(API.delete_notification, "", false, false);
    }

    @Override
    public int getItemCount()
    {
        return notificationlist.size();
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service)
    {
        switch (apitype)
        {

            case API.API_GET_INFO_TEST_SERIES:
                EncryptionData masterdatadetailencryptionData1 = new EncryptionData();
                masterdatadetailencryptionData1.setTest_id(quiz_id);
                masterdatadetailencryptionData1.setCourse_id(notificationlist.get(selectedposition).getExtra().getCourse_id());
                String getmasterdataencryptionDatadoseStr1 = new Gson().toJson(masterdatadetailencryptionData1);
                String masterdatadoseStrScr1test = AES.encrypt(getmasterdataencryptionDatadoseStr1);
                return service.API_GET_INFO_TEST_SERIES(masterdatadoseStrScr1test);



            case API.API_GET_TEST_INSTRUCTION_DATA:
                EncryptionData masterdatadetailencryptionDatatest = new EncryptionData();
                masterdatadetailencryptionDatatest.setTest_id(quiz_id);
                masterdatadetailencryptionDatatest.setCourse_id(notificationlist.get(selectedposition).getExtra().getCourse_id());

                String getmasterdataencryptionDatadoseStrtest = new Gson().toJson(masterdatadetailencryptionDatatest);
                String masterdatadoseStrScrtest = AES.encrypt(getmasterdataencryptionDatadoseStrtest);
                return service.API_GET_TEST_INSTRUCTION_DATA(masterdatadoseStrScrtest);


            case API.get_video_link:
                EncryptionData encryptionData = new EncryptionData();
                encryptionData.setName(datuml.getId() + "_" + "0" + "_" + "0");
                encryptionData.setCourse_id(datuml.getPayloadData().getCourse_id());
                encryptionData.setTile_id(datuml.getPayloadData().getTile_id());
                encryptionData.setType(datuml.getPayloadData().getTile_type());

                String  device_id =   (Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID));
                String    device_name = android.os.Build.MANUFACTURER + android.os.Build.MODEL;
                device_id = device_id ==null && device_id.equalsIgnoreCase("") ? "1234567890":device_id;
                encryptionData.setDevice_id(device_id);
                encryptionData.setDevice_name(device_name);

                String data1 = new Gson().toJson(encryptionData);
                String encryptjson = AES.encrypt(data1);
                return service.getVideoLink(encryptjson);

            case API.delete_notification:
                EncryptionData readnotification = new EncryptionData();
                readnotification.setId(selectedpositionid);
                String getreadnotification = new Gson().toJson(readnotification);
                String getnotificationdatadoseStrScr = AES.encrypt(getreadnotification);
                return service.DeleteNotification(getnotificationdatadoseStrScr);


            case API.mark_as_read:
                EncryptionData readnotification1 = new EncryptionData();
                readnotification1.setId(selectedpositionid);
                String getreadnotification1 = new Gson().toJson(readnotification1);
                String getnotificationdatadoseStrScr1 = AES.encrypt(getreadnotification1);
                return service.setread(getnotificationdatadoseStrScr1);


            case API.API_GET_MASTER_DATA:
                EncryptionData masterdatadetailencryptionData = new EncryptionData();
                masterdatadetailencryptionData.setTile_id(notificationlist.get(selectedposition).getExtra().getTile_id());

                masterdatadetailencryptionData.setType(type);
                masterdatadetailencryptionData.setRevert_api("0#0#0");
                masterdatadetailencryptionData.setCourse_id(notificationlist.get(selectedposition).getExtra().getCourse_id());
                masterdatadetailencryptionData.setLayer("3");
                masterdatadetailencryptionData.setPage("" + 1);
                masterdatadetailencryptionData.setFile_id(notificationlist.get(selectedposition).getExtra().getFile_id());
                masterdatadetailencryptionData.setSubject_id("");
                masterdatadetailencryptionData.setTopic_id(notificationlist.get(selectedposition).getExtra().getTopic_id());
                String getmasterdataencryptionDatadoseStr = new Gson().toJson(masterdatadetailencryptionData);
                String masterdatadoseStrScr11 = AES.encrypt(getmasterdataencryptionDatadoseStr);
                return service.getMasterDataVideoThree(masterdatadoseStrScr11);
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonstring, String apitype, String typeApi, boolean showprogress) throws JSONException {
        switch (apitype) {
            case API.API_GET_TEST_INSTRUCTION_DATA:
                if (jsonstring.optString(Const.STATUS).equals(Const.TRUE)) {
                    Gson gson = new Gson();
                    JSONObject data1 = jsonstring.getJSONObject("data");
                    InstructionData instructionData = gson.fromJson(data1.toString(), InstructionData.class);
                    showPopUp(instructionData);

                } else if (jsonstring.optString("status").equals(Const.FALSE)) {
                    if (jsonstring.optString(Const.AUTH_CODE) != null) {
                        if (jsonstring.optString(Const.AUTH_CODE).equalsIgnoreCase(Const.EXPIRY_AUTH_CODE)) {
                            return;
                        }
                    }
                    RetrofitResponse.GetApiData(activity, jsonstring.has(Const.AUTH_CODE) ? jsonstring.getString(Const.AUTH_CODE) : "", jsonstring.getString(Const.MESSAGE), false);
                }
                break;

            case API.delete_notification:
                try {
                    if (jsonstring.getString("status").equalsIgnoreCase("true")) {
                        notificationlist.remove(selectedposition);
                        if (notificationlist.size() == 0) {
                            notifyDataSetChanged();
                        }
                        else {
                            notifyItemRemoved(selectedposition);
                            notifyItemRangeChanged(selectedposition, notificationlist.size());
                        }
                        if (notificationlist.get(selectedposition).getViewState().equalsIgnoreCase("0")) {
                            SharedPreference.getInstance().putInt(Const.NOTIFICATION_COUNT, SharedPreference.getInstance().getInt(Const.NOTIFICATION_COUNT) - 1);
                            ShortcutBadger.applyCount(activity, SharedPreference.getInstance().getInt(Const.NOTIFICATION_COUNT));
                        }
                    } else {
                        notifyItemChanged(selectedposition);
                        RetrofitResponse.GetApiData(activity, jsonstring.has(Const.AUTH_CODE) ? jsonstring.getString(Const.AUTH_CODE) : "", jsonstring.getString(Const.MESSAGE), false);
                    }
                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
                    itemTouchHelper.attachToRecyclerView(recyclerView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case API.get_video_link:
                try {
                    if (jsonstring.getString("status").equalsIgnoreCase("true")) {
                        JSONObject dataJsonObject = jsonstring.getJSONObject(Const.DATA);
                        String link = dataJsonObject.optString("link");


                        ////////////////////////////////video//////////
                        if (dataJsonObject.optString("content_type").equalsIgnoreCase("")) {
                            long expire_time=0;
                            JSONObject  objectcookie=null;
                            if (jsonstring.getJSONObject("data").has("cookie") && jsonstring.getJSONObject("data").optJSONObject("cookie")!=null)
                            {
                                objectcookie = jsonstring.getJSONObject("data").optJSONObject("cookie");
                            }else {
                                objectcookie =new JSONObject("{}");
                            }

                            if (dataJsonObject.optLong("expiry_seconds")!=0)
                            {
                                expire_time = (Long.parseLong(jsonstring.optString("time"))) +dataJsonObject.optLong("expiry_seconds");
                            }
                            if (dataJsonObject.has("bitrate_urls") )
                            {
                                JSONArray arrJson = dataJsonObject.optJSONArray("bitrate_urls");
                                if (arrJson!=null && arrJson.length()>0) {
                                    ArrayList<UrlObject> urlObjects = new ArrayList<>();
                                    for (int i = 0; i < Objects.requireNonNull(arrJson).length(); i++) {
                                        JSONObject dataObj = arrJson.optJSONObject(i);
                                        UrlObject urlObject = new UrlObject();
                                        urlObject.setTitle(dataObj.optString("title"));
                                        urlObject.setUrl(dataObj.optString("url"));
                                        urlObjects.add(urlObject);
                                    }
                                    datuml.setBitrate_urls(urlObjects);
                                }
                            }
                            if (datuml != null && datuml.getVideo_type().equalsIgnoreCase("4")) {
                                if ((datuml.getLive_status().equalsIgnoreCase("1") || datuml.getLive_status().equalsIgnoreCase("0")) && datuml.getOpen_in_app().equalsIgnoreCase("0"))
                                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + link)));
                            } else if (datuml != null && datuml.getVideo_type().equalsIgnoreCase("1")) {
                                if (datuml.getOpen_in_app().equalsIgnoreCase("0"))
                                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + link)));
                            }else
                            if (utkashRoom.getvideoDao().isvideo_exit(datuml.getId(), MakeMyExam.userId)) {
                                Helper.GoToJWVideo_Params_newarrayobject(activity, link, datuml.getId(), datuml.getTitle(), utkashRoom.getvideoDao().getuser(datuml.getId(), MakeMyExam.userId).getVideo_currentpos(), datuml.getPayloadData().getCourse_id() + "#", datuml.getPayloadData().getTile_id(), datuml.getPayloadData().getTile_type(),datuml.getBitrate_urls(),expire_time,objectcookie.toString());
                            } else {
                                VideoTable videoTable = new VideoTable();
                                videoTable.setVideo_id(datuml.getId());
                                videoTable.setVideo_name(datuml.getTitle());
                                videoTable.setJw_url(link);
                                videoTable.setVideo_currentpos(0L);
                                videoTable.setUser_id(MakeMyExam.userId);
                                videoTable.setCourse_id(datuml.getPayloadData().getCourse_id() + "#");
                                Helper.GoToJWVideo_Params_newarrayobject(activity, link, datuml.getId(), datuml.getTitle(), 0L, datuml.getPayloadData().getCourse_id() + "#", datuml.getPayloadData().getTile_id(), datuml.getPayloadData().getTile_type(),datuml.getBitrate_urls(),expire_time,objectcookie.toString());
                                utkashRoom.getvideoDao().addUser(videoTable);
                            }
                        }
                       else  if (dataJsonObject.optString("content_type").equalsIgnoreCase("2"))
                        {
                            if (datuml.getVideo_type().equalsIgnoreCase("5") && datuml.getLive_status().equalsIgnoreCase("1")) {
                                Helper.GoToLiveDrmVideoActivity(datuml.getVideo_type(), datuml.getChat_node(), activity, datuml.getFile_url(), datuml.getVideo_type(), datuml.getId(), datuml.getTitle(), "0", datuml.getThumbnail_url(), datuml.getPayloadData().getCourse_id(), datuml.getPayloadData().getTile_id(), datuml.getPayloadData().getTile_type(), datuml.getIs_chat_lock(), "", "",jsonstring.toString());
                            } else if (datuml.getVideo_type().equalsIgnoreCase("0")) {
                                Helper.GoToLiveDrmVideoActivity(datuml.getVideo_type(), datuml.getChat_node(), activity, datuml.getFile_url(), datuml.getVideo_type(), datuml.getId(), datuml.getTitle(), "0", datuml.getThumbnail_url(), datuml.getPayloadData().getCourse_id(), datuml.getPayloadData().getTile_id(), datuml.getPayloadData().getTile_type(), datuml.getIs_chat_lock(), "", "",jsonstring.toString());
                            }
                        }else if (datuml.getVideo_type().equalsIgnoreCase("5") && datuml.getLive_status().equalsIgnoreCase("1")) {
                            Helper.GoToLiveAwsVideoActivity(datuml.getVideo_type(), datuml.getChat_node(), activity, datuml.getFile_url(), datuml.getVideo_type(), datuml.getId(), datuml.getTitle(), "0", datuml.getThumbnail_url(), datuml.getPayloadData().getCourse_id(), datuml.getPayloadData().getTile_id(), datuml.getPayloadData().getTile_type(), datuml.getIs_chat_lock(), "", "",jsonstring.toString());
                        } else if (datuml.getVideo_type().equalsIgnoreCase("0")) {
                            Helper.GoToLiveAwsVideoActivity(datuml.getVideo_type(), datuml.getChat_node(), activity, datuml.getFile_url(), datuml.getVideo_type(), datuml.getId(), datuml.getTitle(), "0", datuml.getThumbnail_url(), datuml.getPayloadData().getCourse_id(), datuml.getPayloadData().getTile_id(), datuml.getPayloadData().getTile_type(), datuml.getIs_chat_lock(), "", "",jsonstring.toString());
                        }
                        else if (datuml!=null && datuml.getVideo_type().equalsIgnoreCase("4") )
                        {
                            if ((datuml.getLive_status().equalsIgnoreCase("1") || datuml.getLive_status().equalsIgnoreCase("0"))  && datuml.getOpen_in_app().equalsIgnoreCase("0"))
                                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + link)));
                        }else if (datuml!=null && datuml.getVideo_type().equalsIgnoreCase("1"))
                        {
                            if (datuml.getOpen_in_app().equalsIgnoreCase("0"))
                                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + link)));
                        }else
                        {
                            long expire_time=0;
                            JSONObject  objectcookie=null;
                            if (jsonstring.getJSONObject("data").has("cookie") && jsonstring.getJSONObject("data").optJSONObject("cookie")!=null)
                            {
                                objectcookie = jsonstring.getJSONObject("data").optJSONObject("cookie");
                            }else {
                                objectcookie =new JSONObject("{}");
                            }

                            if (dataJsonObject.optLong("expiry_seconds")!=0)
                            {
                                expire_time = (Long.parseLong(jsonstring.optString("time"))) +dataJsonObject.optLong("expiry_seconds");
                            }
                            if (dataJsonObject.has("bitrate_urls") )
                            {
                                JSONArray arrJson = dataJsonObject.optJSONArray("bitrate_urls");
                                if (arrJson!=null && arrJson.length()>0) {
                                    ArrayList<UrlObject> urlObjects = new ArrayList<>();
                                    for (int i = 0; i < Objects.requireNonNull(arrJson).length(); i++) {
                                        JSONObject dataObj = arrJson.optJSONObject(i);
                                        UrlObject urlObject = new UrlObject();
                                        urlObject.setTitle(dataObj.optString("title"));
                                        urlObject.setUrl(dataObj.optString("url"));
                                        urlObjects.add(urlObject);
                                    }
                                    datuml.setBitrate_urls(urlObjects);
                                }
                            }
                            if (utkashRoom.getvideoDao().isvideo_exit(datuml.getId(), MakeMyExam.userId)) {
                                Helper.GoToJWVideo_Params_newarrayobject(activity, link, datuml.getId(), datuml.getTitle(), utkashRoom.getvideoDao().getuser(datuml.getId(), MakeMyExam.userId).getVideo_currentpos(), datuml.getPayloadData().getCourse_id() + "#", datuml.getPayloadData().getTile_id(), datuml.getPayloadData().getTile_type(),datuml.getBitrate_urls(),expire_time,objectcookie.toString());
                            } else {
                                VideoTable videoTable = new VideoTable();
                                videoTable.setVideo_id(datuml.getId());
                                videoTable.setVideo_name(datuml.getTitle());
                                videoTable.setJw_url(link);
                                videoTable.setVideo_currentpos(0L);
                                videoTable.setUser_id(MakeMyExam.userId);
                                videoTable.setCourse_id(datuml.getPayloadData().getCourse_id() + "#");
                                Helper.GoToJWVideo_Params_newarrayobject(activity, link, datuml.getId(), datuml.getTitle(), 0L, datuml.getPayloadData().getCourse_id() + "#", datuml.getPayloadData().getTile_id(), datuml.getPayloadData().getTile_type(),datuml.getBitrate_urls(),expire_time,objectcookie.toString());
                                utkashRoom.getvideoDao().addUser(videoTable);
                            }
                        }
                    } else {
                        RetrofitResponse.GetApiData(activity, jsonstring.has(Const.AUTH_CODE) ? jsonstring.getString(Const.AUTH_CODE) : "", jsonstring.getString(Const.MESSAGE), false);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;




            case API.API_GET_INFO_TEST_SERIES:
                if (jsonstring.optString(Const.STATUS).equals(Const.TRUE)) {
                    Long time = jsonstring.optLong("time");

                    TestseriesBase testseriesBase = null;
                    try {
                        Gson gson = new Gson();
                        testseriesBase = gson.fromJson(jsonstring.toString(), TestseriesBase.class);
                        if (testseriesBase.getData().getQuestions() != null && testseriesBase.getData().getQuestions().size() > 0 && lang == 1) {
                            Intent quizView = new Intent(activity, TestBaseActivity.class);
                            quizView.putExtra(Const.STATUS, false);
                            quizView.putExtra(Const.TEST_SERIES_ID, quiz_id);
                            quizView.putExtra(Const.TEST_SERIES_Name, testname);
                            SharedPreference.getInstance().putString("test_series", jsonstring.toString());

                            quizView.putExtra("course_id", notificationlist.get(selectedposition).getExtra().getCourse_id());
                            quizView.putExtra("TOTAL_QUESTIONS", testquestion);
                            quizView.putExtra("first_attempt", first_attempt);
                            quizView.putExtra("result_date", result_date);
                            quizView.putExtra("test_submission", submition_type);
                            quizView.putExtra("time", time);
                            quizView.putExtra("enddate", videodata.getEnd_date());
                            quizView.putExtra(Const.LANG, lang);
                            Helper.gotoActivity_finish(quizView, activity);
                        } else if (testseriesBase.getData().getQuestionsHindi() != null && testseriesBase.getData().getQuestionsHindi().size() > 0 && lang == 2) {

                            testseriesBase.getData().setQuestions(testseriesBase.getData().getQuestionsHindi());

                            Intent quizView = new Intent(activity, TestBaseActivity.class);
                            quizView.putExtra(Const.STATUS, false);
                            quizView.putExtra(Const.TEST_SERIES_ID, quiz_id);
                            quizView.putExtra(Const.TEST_SERIES_Name, testname);
                            SharedPreference.getInstance().putString("test_series", jsonstring.toString());

                            quizView.putExtra("course_id", notificationlist.get(selectedposition).getExtra().getCourse_id());
                            quizView.putExtra("TOTAL_QUESTIONS", testquestion);

                            quizView.putExtra("first_attempt", first_attempt);
                            quizView.putExtra("result_date", result_date);
                            quizView.putExtra("test_submission", submition_type);
                            quizView.putExtra(Const.LANG, lang);
                            quizView.putExtra("time", time);
                            quizView.putExtra("enddate", videodata.getEnd_date());
                            Helper.gotoActivity_finish(quizView, activity);
                        } else {
                            Toast.makeText(activity, "No Question Found", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(activity, "Something went wrong.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (jsonstring.optString(Const.AUTH_CODE) != null) {
                        if (jsonstring.optString(Const.AUTH_CODE).equalsIgnoreCase(Const.EXPIRY_AUTH_CODE)) {
                            return;
                        }
                    }
                    RetrofitResponse.GetApiData(activity, jsonstring.has(Const.AUTH_CODE) ? jsonstring.getString(Const.AUTH_CODE) : "", jsonstring.getString(Const.MESSAGE), false);
                }


            case API.mark_as_read:
                try {
                    if (jsonstring.getString("status").equalsIgnoreCase("true")) {
                        notificationlist.get(Integer.valueOf(selectedposition)).setViewState("1");
                        notifyItemChanged(Integer.valueOf(selectedposition));
                        SharedPreference.getInstance().putInt(Const.NOTIFICATION_COUNT, SharedPreference.getInstance().getInt(Const.NOTIFICATION_COUNT) - 1);
                        ShortcutBadger.applyCount(activity, SharedPreference.getInstance().getInt(Const.NOTIFICATION_COUNT));
                    } else {
                        RetrofitResponse.GetApiData(activity, jsonstring.has(Const.AUTH_CODE) ? jsonstring.getString(Const.AUTH_CODE) : "", jsonstring.getString(Const.MESSAGE), false);
                    }
                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
                    itemTouchHelper.attachToRecyclerView(recyclerView);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;

            case API.API_GET_MASTER_DATA:
                try {
                    if (jsonstring.optString(Const.STATUS).equals(Const.TRUE))
                    {
                        Gson gson1 = new Gson();
                        JSONObject data1 = jsonstring.getJSONObject(Const.DATA);
                        ArrayList<Video> videoArrayList = new ArrayList<>();
                        for (int i = 0; i < data1.optJSONArray(Const.LIST).length(); i++) {
                            Video video = gson1.fromJson(data1.optJSONArray(Const.LIST).get(i).toString(), Video.class);
                            videoArrayList.add(video);
                        }
                        if (videoArrayList.size() != 0)
                            initLivevideo(videoArrayList.get(0),videoArrayList);
                    }
                    else {
                        //ErrorCallBack(jsonobject.optString(Const.MESSAGE), apitype);
                        RetrofitResponse.GetApiData(activity, jsonstring.optString(Const.AUTH_CODE), jsonstring.optString(Const.MESSAGE), false);
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
                break;
        }
    }


    public void getintotestseries()
    {
        NetworkCall networkCall = new NetworkCall(this, activity);
        networkCall.NetworkAPICall(API.API_GET_INFO_TEST_SERIES, "", true, false);
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
                Log.e("Language", String.valueOf(lang));
                return false;
            }
        });

        for (int i = 0; i < testBasicInst.getLang_id().split(",").length; i++) {

            if (testBasicInst.getLang_id().split(",")[i].equals("1"))
                popup.getMenu().add(activity.getResources().getStringArray(R.array.dialog_choose_language_array)[0]);
            else if (testBasicInst.getLang_id().split(",")[i].equals("2"))
                popup.getMenu().add(activity.getResources().getStringArray(R.array.dialog_choose_language_array)[1]);
        }
        popup.show();
    }


    private void showPopUp(final InstructionData instructionData) {
        LayoutInflater li = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = li.inflate(R.layout.popup_basicinfo_quiz_career, null, false);
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
        general_layout = (LinearLayout) v.findViewById(R.id.general_layout);
        section_time = (LinearLayout) v.findViewById(R.id.section_time);
        if (testBasicInst.getTest_assets() != null) {
            if (testBasicInst.getTest_assets().getHide_inst_time().equalsIgnoreCase("0")) {
                section_time.setVisibility(View.VISIBLE);
            } else {
                section_time.setVisibility(View.INVISIBLE);
            }
        }
        int total_ques =0;
        float totalmarks =0;
        for (TestSectionInst testSectionInst : instructionData.getTestSections()) {
            if (testSectionInst.getOptional_que()==null)
            {
                testSectionInst.setOptional_que("0");
            }
            total_ques =total_ques+(Integer.parseInt(testSectionInst.getTotalQuestions())-Integer.parseInt(testSectionInst.getOptional_que()));
            totalmarks =totalmarks+((Integer.parseInt(testSectionInst.getTotalQuestions())-Integer.parseInt(testSectionInst.getOptional_que()))* Float.parseFloat(testSectionInst.getMarksPerQuestion()));
        }
        quizQuesNumTv.setText(""+total_ques);
        quizTotalMarks.setText(""+totalmarks);


        addSectionView(sectionListLL, instructionData);

        if (SharedPreference.getInstance().getBoolean(Const.RE_ATTEMPT)) {
            reAttempt.setVisibility(View.GONE);
        } else
            reAttempt.setVisibility(View.GONE);
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
      //  quizTotalMarks.setText(testBasicInst.getTotalMarks());
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
                        getintotestseries();
                    } else {
                        Toast.makeText(activity, "Please check following instructions.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        quizBasicInfoDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    quizBasicInfoDialog.dismiss();
                }
                return true;
            }
        });

    }

    private void addSectionView(LinearLayout sectionListLL, InstructionData instructionData) {
        int count = 0;
        for (TestSectionInst testSectionInst : instructionData.getTestSections()) {
            if (testSectionInst.getOptional_que()==null)
            {
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
        option_count.setText(""+(Integer.parseInt(testSectionInst.getTotalQuestions())- Integer.parseInt(testSectionInst.getOptional_que())));
        maxMarksTV.setText(String.format("%.2f",(Float.parseFloat(testSectionInst.getMarksPerQuestion()) * (Integer.parseInt(testSectionInst.getTotalQuestions()) -Integer.parseInt(testSectionInst.getOptional_que())))));
        markPerQuesTV.setText(testSectionInst.getMarksPerQuestion());
        negMarkPerQuesTV.setText("" + Float.parseFloat(testSectionInst.getNegativeMarks()));

        v.setTag(tag);
        LinearLayoutList.add(v);

        return v;
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {

    }

    public class Notification extends RecyclerView.ViewHolder {
        RelativeLayout rl1;
        ImageView removeNoti;
        TextView date, notification_title,date2, descriptionTV,descriptionTVfull,seeless,seemore;

        public Notification(@NonNull View itemView) {
            super(itemView);
            rl1 = itemView.findViewById(R.id.rl1);
            removeNoti = itemView.findViewById(R.id.removeNoti);
            removeNoti.setVisibility(View.GONE);
            date = itemView.findViewById(R.id.date);
            date2 = itemView.findViewById(R.id.date2);
            notification_title = itemView.findViewById(R.id.notification_title);
            descriptionTV = itemView.findViewById(R.id.descriptionTV);
            descriptionTVfull = itemView.findViewById(R.id.descriptionTVfull);
            seeless = itemView.findViewById(R.id.seeless);
            seemore = itemView.findViewById(R.id.seemore);
        }
    }

    public String getdate(String timestamp) {
        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getDefault();
        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault());
        java.util.Date currenTimeZone = new java.util.Date((long) Long.parseLong(timestamp));
        return Helper.changeAMPM(sdf.format(currenTimeZone));
    }

    private void initLivevideo(Video video, ArrayList<Video> videoArrayList) {

        if(type.equals("video")) {
            if (TextUtils.isEmpty(video.getFile_url())) {
                Toast.makeText(activity, "No video found!", Toast.LENGTH_SHORT).show();
            } else {
                if (video.getVideo_type().equalsIgnoreCase("5")) {
                    if (video.getIs_locked().equalsIgnoreCase("1")) {
                        Intent intent = new Intent(activity, CourseActivity.class);
                        intent.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                        intent.putExtra(Const.COURSE_ID_MAIN, video.getPayloadData().getCourse_id());
                        intent.putExtra(Const.COURSE_PARENT_ID, "");
                        intent.putExtra(Const.IS_COMBO, false);
                        SharedPreference.getInstance().putString(Const.ID, video.getPayloadData().getCourse_id());
                        activity.startActivity(intent);
                    } else {
                        if (video.getLive_status().equalsIgnoreCase("0")) {
                            Toast.makeText(activity, "Live Class will start on " + new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(Long.parseLong(video.getStart_date()) * 1000)), Toast.LENGTH_SHORT).show();
                        } else if (video.getLive_status().equalsIgnoreCase("1")) {
                            datuml =video;
                            NetworkCall networkCall = new NetworkCall(this, activity);
                            networkCall.NetworkAPICall(API.get_video_link, "", true, false);


                          //  Helper.GoToLiveAwsVideoActivity(video.getVideo_type(), video.getChat_node(), activity, video.getFile_url(), video.getVideo_type(), video.getId(), video.getTitle(), "0", video.getThumbnail_url(), video.getPayloadData().getCourse_id(), video.getPayloadData().getTile_id(), video.getPayloadData().getTile_type(), video.getIs_chat_lock(), "", "");
                        } else if (video.getLive_status().equalsIgnoreCase("2")) {
                            Toast.makeText(activity, "Live Class is ended", Toast.LENGTH_SHORT).show();
                        } else if (video.getLive_status().equalsIgnoreCase("3")) {
                            Toast.makeText(activity, "Live Class is cancelled", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else if (video.getVideo_type().equalsIgnoreCase("0")) {
                    datuml =video;
                    NetworkCall networkCall = new NetworkCall(this, activity);
                    networkCall.NetworkAPICall(API.get_video_link, "", true, false);


                   // Helper.GoToLiveAwsVideoActivity(video.getVideo_type(), video.getChat_node(), activity, video.getFile_url(), video.getVideo_type(), video.getId(), video.getTitle(), "0", video.getThumbnail_url(), video.getPayloadData().getCourse_id(), video.getPayloadData().getTile_id(), video.getPayloadData().getTile_type(), video.getIs_chat_lock(), "", "");
                } else if (video.getVideo_type().equalsIgnoreCase("6")) {
                    if (video.getIs_locked().equalsIgnoreCase("1")) {
                        Intent intent = new Intent(activity, CourseActivity.class);
                        intent.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                        intent.putExtra(Const.COURSE_ID_MAIN, video.getPayloadData().getCourse_id());
                        intent.putExtra(Const.COURSE_PARENT_ID, "");
                        intent.putExtra(Const.IS_COMBO, false);
                        SharedPreference.getInstance().putString(Const.ID, video.getPayloadData().getCourse_id());
                        Helper.gotoActivity(intent, activity);
                    } else {
                        jwvideo(video);
                    }
                } else {
                    if (video.getIs_locked().equalsIgnoreCase("1")) {
                        Intent intent = new Intent(activity, CourseActivity.class);
                        intent.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                        intent.putExtra(Const.COURSE_ID_MAIN, video.getPayloadData().getCourse_id());
                        intent.putExtra(Const.COURSE_PARENT_ID, "");
                        intent.putExtra(Const.IS_COMBO, false);
                        SharedPreference.getInstance().putString(Const.ID, video.getPayloadData().getCourse_id());
                        activity.startActivity(intent);
                    } else {
                        if (video.getOpen_in_app() != null && video.getOpen_in_app().equalsIgnoreCase("1")) {

                            if (video.getVideo_type().equalsIgnoreCase("4")) {
                                if (video.getLive_status().equalsIgnoreCase("0")) {
                                    Toast.makeText(activity, "Live Class will start on " + new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(Long.parseLong(video.getStart_date()) * 1000)), Toast.LENGTH_SHORT).show();
                                } else if (video.getLive_status().equalsIgnoreCase("1")) {
                                    Helper.GoToLiveVideoActivity(video.getChat_node(), activity, video.getFile_url(), video.getVideo_type(), video.getId(), video.getTitle(), "0", video.getThumbnail_url(), video.getIs_chat_lock(), video.getPayloadData().getCourse_id(), "", "", video.getPayloadData().getTile_id(), video.getPayloadData().getTile_type());
                                } else if (video.getLive_status().equalsIgnoreCase("2")) {
                                    Toast.makeText(activity, "Live Class is ended", Toast.LENGTH_SHORT).show();
                                } else if (video.getLive_status().equalsIgnoreCase("3")) {
                                    Toast.makeText(activity, "Live Class is cancelled", Toast.LENGTH_SHORT).show();
                                }
                            } else if (video.getVideo_type().equalsIgnoreCase("1")) {
                                Helper.GoToLiveVideoActivity(video.getChat_node(), activity, video.getFile_url(), video.getVideo_type(), video.getId(), video.getTitle(), "0", video.getThumbnail_url(), video.getIs_chat_lock(), video.getPayloadData().getCourse_id(), "", "", video.getPayloadData().getTile_id(), video.getPayloadData().getTile_type());
                            }
                        } else {

                            datuml =video;
                            NetworkCall networkCall = new NetworkCall(this, activity);
                            networkCall.NetworkAPICall(API.get_video_link, "", true, false);


                            //  activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + video.getFile_url())));
                        }
                    }
                }
            }
        }

        else
        if(type.equals("pdf") || type.equals("concept") || type.equals("link")) {
            if (video.getIs_locked().equalsIgnoreCase("1")) {
                Intent intent = new Intent(activity, CourseActivity.class);
                intent.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                intent.putExtra(Const.COURSE_ID_MAIN, video.getPayloadData().getCourse_id());
                intent.putExtra(Const.COURSE_PARENT_ID, "");
                intent.putExtra(Const.IS_COMBO, false);
                SharedPreference.getInstance().putString(Const.ID, video.getPayloadData().getCourse_id());
                Helper.gotoActivity(intent, activity);
            } else {
                if (!video.getFile_type().equalsIgnoreCase("7"))
                    if (TextUtils.isEmpty(video.getFile_url())) {
                        Toast.makeText(activity, "No pdf found!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                if (video.getFile_type().equalsIgnoreCase("8")) {
                    Intent intent4 = new Intent(activity, WebViewActivty.class);
                    intent4.putExtra("type", video.getTitle());
                    intent4.putExtra("url", video.getFile_url());
                    intent4.putExtra("video_id", video.getId());
                    intent4.putExtra("link", video.getFile_type());
                    intent4.putExtra("course_id", video.getPayloadData().getCourse_id());

                    Helper.gotoActivity(intent4, activity);
                }
                if (video.getFile_type().equalsIgnoreCase("7")) {
//

                    Intent intent = new Intent(activity, Concept_newActivity.class); // AllCourse List
                    intent.putExtra("id", video.getId());
                    intent.putExtra("name", video.getTitle());
                    intent.putExtra(Const.COURSE_ID, video.getPayloadData().getCourse_id() + "#");
                    intent.putExtra("modified", video.getModified());
                    intent.putExtra(Const.TILE_ID, video.getPayloadData().getTile_id());
                    Helper.gotoActivity(intent, activity);
                } else if (video.getFile_type().equalsIgnoreCase("1")) {
                    boolean isDownload = false;
                    if (!TextUtils.isEmpty(video.getIs_download_available()) && video.getIs_download_available().equalsIgnoreCase("1")) {
                        isDownload = true;
                    } else {
                        isDownload = false;
                    }
                    Helper.GoToWebViewPDFActivity(activity, video.getId(), video.getFile_url(), isDownload, video.getTitle(), video.getPayloadData().getCourse_id());
                }
            }
        }
        else
        if(type.equals("test"))
        {
            inittest(videoArrayList);
        }

        type="";
    }

    private void inittest(ArrayList<Video> videoArrayList) {
        if (videoArrayList.get(0).getIs_locked().equalsIgnoreCase("1")) {

            Intent intent = new Intent(activity, CourseActivity.class);
            intent.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
            intent.putExtra(Const.COURSE_ID_MAIN, videoArrayList.get(0).getPayloadData().getCourse_id());
            intent.putExtra(Const.COURSE_PARENT_ID, "");
            intent.putExtra(Const.IS_COMBO, false);
            SharedPreference.getInstance().putString(Const.ID, videoArrayList.get(0).getPayloadData().getCourse_id());
            activity.startActivity(intent);
        } else {
            if (videoArrayList.get(0).getState().equals("1")) {
                if (videoArrayList.get(0).getStart_date().equalsIgnoreCase("") || videoArrayList.get(0).getStart_date().equalsIgnoreCase("0")  && videoArrayList.get(0).getEnd_date().equalsIgnoreCase("") ||videoArrayList.get(0).getEnd_date().equalsIgnoreCase("0")) {
                    first_attempt = "1";
                    Intent resultScreen = new Intent(activity, QuizActivity.class);
                    resultScreen.putExtra(Const.FRAG_TYPE, Const.RESULT_SCREEN);
                    resultScreen.putExtra(Const.STATUS, videoArrayList.get(0).getId());
                    resultScreen.putExtra(Const.NAME, videoArrayList.get(0).getTest_series_name());
                    resultScreen.putExtra("first_attempt", first_attempt);
                    Helper.gotoActivity(resultScreen, activity);
                }else
                if (MakeMyExam.getTime_server() >= Long.parseLong(videoArrayList.get(0).getEnd_date()) * 1000) {
                    if (MakeMyExam.getTime_server() > Long.parseLong(videoArrayList.get(0).getResult_date()) * 1000) {
                        if (Long.parseLong(videoArrayList.get(0).getEnd_date()) < 1640066737) {
                            submition_type = "1";
                            quiz_id = videoArrayList.get(0).getId();
                            first_attempt = "0";
                            result_date = videoArrayList.get(0).getResult_date();
                            SharedPreference.getInstance().putString(Const.ID, videoArrayList.get(0).getPayloadData().getCourse_id());
                            testname = videoArrayList.get(0).getTest_series_name();
                            testquestion = videoArrayList.get(0).getTotal_questions();
                            videodata = videoArrayList.get(0);
                            hit_api_for_iniializetest();
                        } else {
                            first_attempt = "1";
                            Intent resultScreen = new Intent(activity, QuizActivity.class);
                            resultScreen.putExtra(Const.FRAG_TYPE, Const.RESULT_SCREEN);
                            resultScreen.putExtra(Const.STATUS, videoArrayList.get(0).getId());
                            resultScreen.putExtra(Const.NAME, videoArrayList.get(0).getTest_series_name());
                            resultScreen.putExtra("first_attempt", first_attempt);
                            Helper.gotoActivity(resultScreen, activity);
                        }

                    } else {
                        Toast.makeText(activity, "You have already Attempted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (!videoArrayList.get(0).getIs_reattempt().equalsIgnoreCase("0")) {
                        submition_type = "1";
                        inittest(videoArrayList.get(0));

                    } else {
                        Toast.makeText(activity, "You have already Attempted the test", Toast.LENGTH_SHORT).show();

                    }
                }
            } else {
                if (videoArrayList.get(0).getStart_date().equalsIgnoreCase("") || videoArrayList.get(0).getStart_date().equalsIgnoreCase("0")  && videoArrayList.get(0).getEnd_date().equalsIgnoreCase("") ||videoArrayList.get(0).getEnd_date().equalsIgnoreCase("0")) {

                    TestTable test_data = utkashRoom.getTestDao().test_data(videoArrayList.get(0).getId(), MakeMyExam.userId);
                    if (test_data != null && test_data.getStatus() != null && !test_data.getStatus().equalsIgnoreCase("")) {

                        Toast.makeText(activity,"You have already Attempted the test",Toast.LENGTH_SHORT).show();


                    } else {
                        first_attempt = "1";
                        submition_type = videoArrayList.get(0).getSubmission_type();
                        inittest(videoArrayList.get(0));

                    }
                }
                else if (MakeMyExam.getTime_server() >= Long.parseLong(videoArrayList.get(0).getEnd_date()) * 1000) {
                    if (Long.parseLong(videoArrayList.get(0).getResult_date()) * 1000 > MakeMyExam.getTime_server()) {

                        Toast.makeText(activity,"Your Result will be declare on " + new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(Long.parseLong(videoArrayList.get(0).getResult_date()) * 1000)),Toast.LENGTH_SHORT).show();
                    } else if (MakeMyExam.getTime_server() > Long.parseLong(videoArrayList.get(0).getResult_date()) * 1000) {
                        if (Long.parseLong(videoArrayList.get(0).getEnd_date()) < 1640066737) {
                            submition_type = "1";
                            quiz_id = videoArrayList.get(0).getId();
                            first_attempt = "0";
                            result_date = videoArrayList.get(0).getResult_date();
                            SharedPreference.getInstance().putString(Const.ID, videoArrayList.get(0).getPayloadData().getCourse_id());
                            testname = videoArrayList.get(0).getTest_series_name();
                            testquestion = videoArrayList.get(0).getTotal_questions();
                            videodata = videoArrayList.get(0);
                            hit_api_for_iniializetest();
                        } else {
                            Intent resultScreen = new Intent(activity, QuizActivity.class);
                            resultScreen.putExtra(Const.FRAG_TYPE, "leader_board");
                            resultScreen.putExtra(Const.STATUS, videoArrayList.get(0).getId());
                            resultScreen.putExtra(Const.NAME, videoArrayList.get(0).getTest_series_name());
                            Helper.gotoActivity(resultScreen, activity);
                        }

                    }
                } else if (MakeMyExam.getTime_server() < Long.parseLong(videoArrayList.get(0).getStart_date()) * 1000) {
                    Toast.makeText(activity,"Test will start on " + new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(Long.parseLong(videoArrayList.get(0).getStart_date()) * 1000)),Toast.LENGTH_SHORT).show();
                } else {
                    TestTable test_data = utkashRoom.getTestDao().test_data(videoArrayList.get(0).getId(), MakeMyExam.userId);
                    if (test_data != null && test_data.getStatus() != null && !test_data.getStatus().equalsIgnoreCase("")) {
                        Toast.makeText(activity,"You have already Attempted the test",Toast.LENGTH_SHORT).show();
                    } else {
                        first_attempt = "1";
                        submition_type = videoArrayList.get(0).getSubmission_type();
                        inittest(videoArrayList.get(0));

                    }
                }
            }
        }
    }
    private void inittest(Video video) {

        if (video.getIs_locked().equalsIgnoreCase("1")) {
            Intent intent = new Intent(activity, CourseActivity.class);
            intent.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
            intent.putExtra(Const.COURSE_ID_MAIN, video.getPayloadData().getCourse_id());
            intent.putExtra(Const.COURSE_PARENT_ID, "");
            intent.putExtra(Const.IS_COMBO, false);
            SharedPreference.getInstance().putString(Const.ID, video.getPayloadData().getCourse_id());
            activity.startActivity(intent);
        }
        else {
            quiz_id = video.getId();
            first_attempt = "1";
            result_date = video.getResult_date();
            SharedPreference.getInstance().putString(Const.ID, video.getPayloadData().getCourse_id());
            testname = video.getTest_series_name();
            testquestion = video.getTotal_questions();
            videodata = video;
            hit_api_for_iniializetest();
        }
    }

/*
    private void initLivevideo(Video video, ArrayList<Video> videoArrayList) {
        if(video.getVideo_type().equalsIgnoreCase("5"))
        {
            if (video.getIs_locked().equalsIgnoreCase("1")) {
                Intent intent = new Intent(activity, CourseActivity.class);
                intent.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                intent.putExtra(Const.COURSE_ID_MAIN, video.getPayloadData().getCourse_id());
                intent.putExtra(Const.COURSE_PARENT_ID, "");
                intent.putExtra(Const.IS_COMBO, false);
                SharedPreference.getInstance().putString(Const.ID, video.getPayloadData().getCourse_id());
                activity.startActivity(intent);
            }
            else {
                if(video.getLive_status().equalsIgnoreCase("0"))
                {
                    Toast.makeText(activity,"Live Class will start on "+new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(Long.parseLong(video.getStart_date()) * 1000)),Toast.LENGTH_SHORT).show();
                }
                else
                if(video.getLive_status().equalsIgnoreCase("1"))
                {
                    Helper.GoToLiveAwsVideoActivity(video.getVideo_type(),video.getChat_node(), activity, video.getFile_url(), video.getVideo_type(), video.getId(), video.getTitle(), "0", video.getThumbnail_url(),video.getPayloadData().getCourse_id(),video.getPayloadData().getTile_id(),video.getPayloadData().getTile_type(),video.getIs_chat_lock(),"","");
                }
                else if(video.getLive_status().equalsIgnoreCase("2"))
                {
                    Toast.makeText(activity,"Live Class is ended",Toast.LENGTH_SHORT).show();
                }
                else
                if(video.getLive_status().equalsIgnoreCase("3"))
                {
                    Toast.makeText(activity,"Live Class is cancelled",Toast.LENGTH_SHORT).show();
                }
            }
        }
        else
        if(video.getVideo_type().equalsIgnoreCase("0"))
        {
            Helper.GoToLiveAwsVideoActivity(video.getVideo_type(),video.getChat_node(), activity, video.getFile_url(), video.getVideo_type(), video.getId(), video.getTitle(), "0", video.getThumbnail_url(),video.getPayloadData().getCourse_id(),video.getPayloadData().getTile_id(),video.getPayloadData().getTile_type(),video.getIs_chat_lock(),"","");
        }
        else
        if(video.getVideo_type().equalsIgnoreCase("6"))
        {
            if (video.getIs_locked().equalsIgnoreCase("1")) {
                Intent intent = new Intent(activity, CourseActivity.class);
                intent.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                intent.putExtra(Const.COURSE_ID_MAIN, video.getPayloadData().getCourse_id());
                intent.putExtra(Const.COURSE_PARENT_ID, "");
                intent.putExtra(Const.IS_COMBO, false);
                SharedPreference.getInstance().putString(Const.ID, video.getPayloadData().getCourse_id());
                Helper.gotoActivity(intent,activity);
            } else {
                jwvideo(video);
            }
        }
        else
        {
            if (video.getIs_locked().equalsIgnoreCase("1")) {
                Intent intent = new Intent(activity, CourseActivity.class);
                intent.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                intent.putExtra(Const.COURSE_ID_MAIN, video.getPayloadData().getCourse_id());
                intent.putExtra(Const.COURSE_PARENT_ID, "");
                intent.putExtra(Const.IS_COMBO, false);
                SharedPreference.getInstance().putString(Const.ID, video.getPayloadData().getCourse_id());
                activity.startActivity(intent);
            }
            else {
                if (video.getOpen_in_app() != null && video.getOpen_in_app().equalsIgnoreCase("1")) {

                    if(video.getVideo_type().equalsIgnoreCase("4"))
                    {
                        if(video.getLive_status().equalsIgnoreCase("0"))
                        {
                            Toast.makeText(activity,"Live Class will start on "+new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(Long.parseLong(video.getStart_date()) * 1000)),Toast.LENGTH_SHORT).show();
                        }
                        else
                        if(video.getLive_status().equalsIgnoreCase("1"))
                        {
                            Helper.GoToLiveVideoActivity(video.getChat_node(), activity, video.getFile_url(), video.getVideo_type(), video.getId(), video.getTitle(), "0", video.getThumbnail_url(),video.getIs_chat_lock(),video.getPayloadData().getCourse_id(),"","",video.getPayloadData().getTile_id(),video.getPayloadData().getTile_type());
                        }
                        else if(video.getLive_status().equalsIgnoreCase("2"))
                        {
                            Toast.makeText(activity,"Live Class is ended",Toast.LENGTH_SHORT).show();
                        }
                        else
                        if(video.getLive_status().equalsIgnoreCase("3"))
                        {
                            Toast.makeText(activity,"Live Class is cancelled",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if(video.getVideo_type().equalsIgnoreCase("1"))
                    {
                        Helper.GoToLiveVideoActivity(video.getChat_node(), activity, video.getFile_url(), video.getVideo_type(), video.getId(), video.getTitle(), "0", video.getThumbnail_url(),video.getIs_chat_lock(),video.getPayloadData().getCourse_id(),"","",video.getPayloadData().getTile_id(),video.getPayloadData().getTile_type());
                    }
                } else {
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + video.getFile_url())));
                }
            }
        }
    }
*/

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            Toast.makeText(recyclerView.getContext(), "on Move", Toast.LENGTH_SHORT).show();
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            Toast.makeText(activity.getApplicationContext(), "on Swiped ", Toast.LENGTH_SHORT).show();
            //Remove swiped item from list and notify the RecyclerView
            int position = viewHolder.getAdapterPosition();

            selectedpositionid = notificationlist.get(position).getId();
            selectedposition = position;
            delete_notification_api();
        }
    };


    private void hit_api_for_iniializetest() {
        NetworkCall networkCall = new NetworkCall(this, activity);
        networkCall.NetworkAPICall(API.API_GET_TEST_INSTRUCTION_DATA, "", true, false);
    }

    private void jwvideo(Video videoData) {
        datuml =videoData;
        networkCall.NetworkAPICall(API.get_video_link, "", true, false);

       /* String mediaId = "https://cdn.jwplayer.com/v2/media/" + videoData.getFile_url().substring(videoData.getFile_url().lastIndexOf("/") + 1, videoData.getFile_url().indexOf("-"));
        if (utkashRoom.getvideoDownloadao().isvideo_exit(videoData.getId(), MakeMyExam.userId)) {
            VideosDownload videoDownload = utkashRoom.getvideoDownloadao().getvideo_byuserid(videoData.getId(), MakeMyExam.userId);
            if (videoDownload.getIs_complete().equalsIgnoreCase("1")) {
                Intent i = new Intent(activity, DownloadVideoPlayer.class);
                i.putExtra("video_name", videoDownload.getVideo_name());
                i.putExtra("video_id", videoDownload.getVideo_id());
                i.putExtra("current_pos", videoDownload.getVideoCurrentPosition());
                i.putExtra("video", videoDownload.getVideo_history());
                i.putExtra("video_time", videoDownload.getVideotime());
                i.putExtra("video_time", videoDownload.getCourse_id());
                Helper.gotoActivity(i,activity);

            } else {
                if (utkashRoom.getvideoDao().isvideo_exit(videoData.getId(), MakeMyExam.userId)) {
                    Helper.GoToJWVideo_Params(activity, mediaId, videoData.getId(), videoData.getTitle(), utkashRoom.getvideoDao().getuser(videoData.getId(), MakeMyExam.userId).getVideo_currentpos(),videoData.getPayloadData().getCourse_id());
                } else {
                    VideoTable videoTable = new VideoTable();
                    videoTable.setVideo_id(videoData.getId());
                    videoTable.setVideo_name(videoData.getTitle());
                    videoTable.setJw_url(mediaId);
                    videoTable.setVideo_currentpos(0L);
                    videoTable.setUser_id(MakeMyExam.userId);
                    videoTable.setCourse_id(videoData.getPayloadData().getCourse_id());

                    utkashRoom.getvideoDao().addUser(videoTable);
                    Helper.GoToJWVideo_Params(activity, mediaId, videoData.getId(), videoData.getTitle(), 0L,videoData.getPayloadData().getCourse_id());
                }
            }
        } else {
            if (utkashRoom.getvideoDao().isvideo_exit(videoData.getId(), MakeMyExam.userId)) {
                Helper.GoToJWVideo_Params(activity, mediaId, videoData.getId(), videoData.getTitle(), utkashRoom.getvideoDao().getuser(videoData.getId(), MakeMyExam.userId).getVideo_currentpos(),videoData.getPayloadData().getCourse_id());
            } else {
                VideoTable videoTable = new VideoTable();
                videoTable.setVideo_id(videoData.getId());
                videoTable.setVideo_name(videoData.getTitle());
                videoTable.setJw_url(mediaId);
                videoTable.setVideo_currentpos(0L);
                videoTable.setUser_id(MakeMyExam.userId);
                videoTable.setCourse_id(videoData.getPayloadData().getCourse_id());
                utkashRoom.getvideoDao().addUser(videoTable);
                Helper.GoToJWVideo_Params(activity, mediaId, videoData.getId(), videoData.getTitle(), 0L,videoData.getPayloadData().getCourse_id());
            }
        }*/
    }
}