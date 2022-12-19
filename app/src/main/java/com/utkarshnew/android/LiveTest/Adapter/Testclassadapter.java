package com.utkarshnew.android.LiveTest.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.utkarshnew.android.courses.Activity.QuizActivity;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.home.livetest.LiveTestData;
import com.utkarshnew.android.LiveTest.Activity.LivetestActivity;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.table.TestTable;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;
import com.utkarshnew.android.Utils.SharedPreference;
import com.utkarshnew.android.testmodule.activity.TestBaseActivity;
import com.utkarshnew.android.testmodule.activity.ViewSolutionActivity;
import com.utkarshnew.android.testmodule.model.InstructionData;
import com.utkarshnew.android.testmodule.model.ResultTestSeries_Report;
import com.utkarshnew.android.testmodule.model.TestBasicInst;
import com.utkarshnew.android.testmodule.model.TestSectionInst;
import com.utkarshnew.android.testmodule.model.TestseriesBase;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Testclassadapter extends RecyclerView.Adapter<Testclassadapter.Livetestviewholder> implements NetworkCall.MyNetworkCallBack{
    Activity activity;
    List<LiveTestData> data;
    private String quiz_id;
    private String course_id;
    private String quiz_name;
    private String totalQuestion,first_attempt="1",result_date="",test_submission="";
    Long time;
    LiveTestData liveTestData=new LiveTestData();

    int lang;
    boolean visibilty_status =true;
    Long time2;

    public Testclassadapter(Activity activity, List<LiveTestData> data,boolean visibilty_status,Long time2) {
        this.activity=activity;
        this.data=data;
        this.visibilty_status =visibilty_status;
        time= MakeMyExam.getTime_server();
        this.time2=time2;
    }




    @NonNull
    @Override
    public Livetestviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_class_adapter, parent, false);
        return new Livetestviewholder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Livetestviewholder holder,int position) {
        holder.title.setText(data.get(position).getTestSeriesName());
        holder.c_name.setText(data.get(position).getCourse_name());
        holder.liveIV.setVisibility(View.GONE);

        long end_millis=0;
        long millis=0;
        if (!data.get(position).getStartDate().equalsIgnoreCase("0"))
        {
            DateFormat fs = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.US);
            millis = Long.parseLong(data.get(position).getStartDate())*1000;
            Date ds = new Date(millis);

            String sdateString = fs.format(ds);
            holder.date_tv.setVisibility(View.VISIBLE);
            holder.date_tv.setText("Start: "+Helper.changeAMPM(sdateString));

        }else {
            holder.date_tv.setVisibility(View.GONE);
        }

        if (!data.get(position).getEndDate().equalsIgnoreCase("0"))
        {
            DateFormat fe = new SimpleDateFormat("dd MMM yyyy hh:mm a",Locale.US);

            end_millis = Long.parseLong(data.get(position).getEndDate())*1000;
            Date de = new Date(end_millis);
            String edateString = fe.format(de);
            holder.endDate_tv.setVisibility(View.VISIBLE);
            holder.endDate_tv.setText("End: "+Helper.changeAMPM(edateString));

        }else {
            holder.endDate_tv.setVisibility(View.GONE);
        }

        holder.share.setOnClickListener(v ->{
            sharelivetestolink(holder.getAdapterPosition());
        });

        if (visibilty_status)
        {
            if (data.get(position).getState().equals("1")) {

                UtkashRoom.getAppDatabase(activity).getTestDao().delete_test_data(MakeMyExam.userId,data.get(position).getId());

                if (LivetestActivity.view_pager.getCurrentItem()==2)
                {
                    if (data.get(position).getResultDate().equalsIgnoreCase("1") || data.get(position).getResultDate().equalsIgnoreCase("0") || data.get(position).getResultDate().equalsIgnoreCase(""))
                    {
                        holder.show_rank.setVisibility(View.GONE);
                    }else if (MakeMyExam.getTime_server() >= (Long.parseLong(data.get(position).getResultDate()) * 1000)) {
                        if(!data.get(position).getEndDate().equalsIgnoreCase("0") && Long.parseLong(data.get(position).getEndDate())< 1640066737)
                        {
                            holder.show_rank.setVisibility(View.GONE);
                            holder.show_rank.setText(activity.getResources().getString(R.string.view_rank));
                        }
                        else
                        {
                            holder.show_rank.setVisibility(View.VISIBLE);
                            holder.show_rank.setText(activity.getResources().getString(R.string.view_rank));
                        }
                    }else {
                        holder.show_rank.setVisibility(View.GONE);
                    }

                }else {
                    if (data.get(position).getResultDate().equalsIgnoreCase("1") || data.get(position).getResultDate().equalsIgnoreCase("0") || data.get(position).getResultDate().equalsIgnoreCase(""))
                    {
                        holder.show_rank.setText("VIEW RESULT");
                        holder.show_rank.setVisibility(View.VISIBLE);
                    }else if (MakeMyExam.getTime_server() >= (Long.parseLong(data.get(position).getResultDate()) * 1000)) {
                        holder.show_rank.setVisibility(View.VISIBLE);
                        holder.show_rank.setText("VIEW RESULT");
                    }else {
                        holder.show_rank.setVisibility(View.GONE);
                    }
                }


                if (!data.get(holder.getAdapterPosition()).getIs_reattempt().equalsIgnoreCase("0"))
                {
                    if (LivetestActivity.view_pager.getCurrentItem()>0)
                    {
                        holder.attemp.setVisibility(View.GONE);
                        holder.practice.setVisibility(View.VISIBLE);
                    }else {
                        holder.attemp.setVisibility(View.VISIBLE);
                        holder.attemp.setText("RE-ATTEMPT");
                        holder.practice.setVisibility(View.GONE);
                    }

                } else {
                    if (LivetestActivity.view_pager.getCurrentItem()==0)
                    {
                        holder.attemp.setVisibility(View.VISIBLE);
                        holder.attemp.setText("ATTEMPTED");
                        holder.practice.setVisibility(View.GONE);
                    }else {
                        holder.attemp.setVisibility(View.GONE);
                    }
                }

                if (LivetestActivity.view_pager.getCurrentItem()>0)
                {
                    holder.attemp.setVisibility(View.GONE);
                    holder.practice.setVisibility(View.VISIBLE);
                }else {
                    holder.practice.setVisibility(View.GONE);
                }

                if (end_millis < MakeMyExam.getTime_server()){
                    holder.learn.setVisibility(View.VISIBLE);
                }else {
                    holder.learn.setVisibility(View.GONE);
                }
            }

            else if (data.get(position).getState().equalsIgnoreCase(""))
            {
                if (MakeMyExam.getTime_server() > end_millis ){
                    holder.learn.setVisibility(View.VISIBLE);
                    if (LivetestActivity.view_pager.getCurrentItem()>0)
                    {
                        holder.practice.setVisibility(View.VISIBLE);
                    }else {
                        holder.practice.setVisibility(View.GONE);
                    }

                    if (LivetestActivity.view_pager.getCurrentItem()==2)
                    {
                        if (data.get(position).getResultDate().equalsIgnoreCase("1") || data.get(position).getResultDate().equalsIgnoreCase("0")|| data.get(position).getResultDate().equalsIgnoreCase(""))
                        {
                            holder.show_rank.setVisibility(View.GONE);
                        }else if (MakeMyExam.getTime_server() > (Long.parseLong(data.get(position).getResultDate()) * 1000)) {
                            if(!data.get(position).getEndDate().equalsIgnoreCase("0") && Long.parseLong(data.get(position).getEndDate())< 1640066737)
                            {
                                holder.show_rank.setVisibility(View.GONE);
                                holder.show_rank.setText(activity.getResources().getString(R.string.view_rank));
                            }
                            else
                            {
                                holder.show_rank.setVisibility(View.VISIBLE);
                                holder.show_rank.setText(activity.getResources().getString(R.string.view_rank));
                            }
                        }else {
                            holder.show_rank.setVisibility(View.GONE);
                        }
                    }else {
                        if (data.get(position).getResultDate().equalsIgnoreCase("1") || data.get(position).getResultDate().equalsIgnoreCase("0")|| data.get(position).getResultDate().equalsIgnoreCase(""))
                        {
                            holder.show_rank.setText("VIEW RESULT");
                            holder.show_rank.setVisibility(View.VISIBLE);
                        }else if (MakeMyExam.getTime_server() > (Long.parseLong(data.get(position).getResultDate()) * 1000)) {
                            holder.show_rank.setText("VIEW RESULT");

                            holder.show_rank.setVisibility(View.VISIBLE);
                        }else {
                            holder.show_rank.setVisibility(View.GONE);
                        }
                    }

                    if (LivetestActivity.view_pager.getCurrentItem()>0)
                    {
                        holder.attemp.setVisibility(View.GONE);

                    }else {
                        if (!data.get(holder.getAdapterPosition()).getIs_reattempt().equalsIgnoreCase("0"))
                        {
                            holder.attemp.setVisibility(View.VISIBLE);
                            holder.attemp.setText("RE-ATTEMPT");
                        }else {
                            holder.attemp.setVisibility(View.GONE);
                        }
                    }

                }else if (MakeMyExam.getTime_server()>millis && MakeMyExam.getTime_server()<end_millis) {
                    holder.show_rank.setVisibility(View.GONE);
                    holder.learn.setVisibility(View.GONE);
                    if (LivetestActivity.view_pager.getCurrentItem() == 2) {
                        holder.practice.setVisibility(View.VISIBLE);
                    } else {
                        holder.practice.setVisibility(View.GONE);
                    }
                    TestTable test_data = UtkashRoom.getAppDatabase(activity).getTestDao().test_data( data.get(holder.getAdapterPosition()).getId(),MakeMyExam.userId);
                    if (test_data != null && test_data.getStatus() != null && !test_data.getStatus().equalsIgnoreCase("")) {
//                        if (!data.get(holder.getAdapterPosition()).getIs_reattempt().equalsIgnoreCase("0"))
//                        {
//                            holder.attemp.setText("RE-ATTEMPT");
//                        }else {
//                            holder.attemp.setText(test_data.getStatus());
//
//                        }
                        holder.attemp.setVisibility(View.VISIBLE);
                        holder.attemp.setText(test_data.getStatus());
                    } else {
                        holder.attemp.setVisibility(View.VISIBLE);
                        holder.attemp.setText("ATTEMPT");
                    }
                }else {
                    holder.attemp.setVisibility(View.GONE);
                    holder.learn.setVisibility(View.GONE);
                    holder.show_rank.setVisibility(View.GONE);
                    holder.practice.setVisibility(View.GONE);
                }
            }
            holder.forward.setVisibility(View.GONE);
            holder.startedin.setVisibility(View.GONE);
            holder.time.setVisibility(View.GONE);
        }else {
            holder.startedin.setVisibility(View.VISIBLE);
            holder.time.setVisibility(View.VISIBLE);
            holder.forward.setVisibility(View.VISIBLE);
            holder.show_rank.setVisibility(View.GONE);
            holder.learn.setVisibility(View.GONE);
            holder.practice.setVisibility(View.GONE);
            holder.attemp.setVisibility(View.GONE);


            holder.timerr=0;

            holder.timerr = Long.parseLong(data.get(position).getStartDate())-time2;

            holder.timerr = holder.timerr * holder.timecount;

            holder.timer = new CountDownTimer(holder.timerr, holder.timecount) {
                public void onTick(long millisUntilFinished) {
                    holder.time.setText(concerter(millisUntilFinished));
                }
                public void onFinish() {
                    holder.time.setText("00:00:00");
                    notifyadap(holder,position);
                }
            }.start();
        }


        if (!TextUtils.isEmpty(data.get(position).getImage())) {
            Helper.setThumbnailImage(activity, data.get(position).getImage(), activity.getResources().getDrawable(R.drawable.square_thumbnail), holder.courseImage);
        } else {
            holder.courseImage.setImageResource(R.drawable.square_thumbnail);
        }




        holder.practice.setOnClickListener(v -> {
            course_id = data.get(position).getCourseId();
            SharedPreference.getInstance().putString(Const.ID, course_id);
            quiz_id = data.get(position).getId();
            quiz_name = data.get(position).getTestSeriesName();
            totalQuestion = data.get(position).getTotalQuestions();
            test_submission ="1";
            result_date = data.get(position).getResultDate();
            first_attempt="0";
            liveTestData =data.get(position);
            startTestAPI();
        });

        holder.attemp.setOnClickListener(v -> {
            course_id = data.get(position).getCourseId();
            SharedPreference.getInstance().putString(Const.ID, course_id);
            if (holder.attemp.getText().toString().equalsIgnoreCase("ATTEMPT"))
            {
                quiz_id = data.get(position).getId();
                quiz_name = data.get(position).getTestSeriesName();
                totalQuestion = data.get(position).getTotalQuestions();
                result_date = data.get(position).getResultDate();
                first_attempt="1";
                test_submission =data.get(position).getSubmission_type();
                liveTestData =data.get(position);

                startTestAPI();
            } else  if (holder.attemp.getText().toString().equalsIgnoreCase("RE-ATTEMPT"))
            {
                quiz_id = data.get(position).getId();
                quiz_name = data.get(position).getTestSeriesName();
                totalQuestion = data.get(position).getTotalQuestions();
                result_date = data.get(position).getResultDate();
                test_submission="1";
                first_attempt="0";
                liveTestData =data.get(position);

                startTestAPI();
            }else if (holder.attemp.getText().toString().equalsIgnoreCase("ATTEMPTED")){
                if (data.get(holder.getAdapterPosition()).getResultDate()!=null &&!data.get(holder.getAdapterPosition()).getResultDate().equalsIgnoreCase("0")&& !data.get(holder.getAdapterPosition()).getResultDate().equalsIgnoreCase("1") && !data.get(holder.getAdapterPosition()).getResultDate().equalsIgnoreCase(""))
                {
                    Snackbar.make(holder.itemView,"Your Result will be declare on "+new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(Long.parseLong(data.get(holder.getAdapterPosition()).getResultDate()) * 1000)) ,Snackbar.LENGTH_SHORT).show();

                }else if (UtkashRoom.getAppDatabase(activity).getTestDao().is_test_exit(data.get(holder.getAdapterPosition()).getId(),MakeMyExam.userId))
                {
                    Snackbar.make(holder.itemView,"Your Result is Getting Ready Please refresh your page..",Snackbar.LENGTH_SHORT).show();
                }else
                {
                    Snackbar.make(holder.itemView,"Test is alredy submiited",Snackbar.LENGTH_SHORT).show();
                }

            }
        });
        holder.learn.setOnClickListener(v -> {
            if (data.get(holder.getAdapterPosition()).getState().equalsIgnoreCase("1"))
            {
                course_id = data.get(position).getCourseId();
                SharedPreference.getInstance().putString(Const.ID, course_id);
                quiz_id = data.get(position).getId();
                quiz_name = data.get(position).getTestSeriesName();
                totalQuestion = data.get(position).getTotalQuestions();
                result_without_submit(quiz_id,course_id,"1",quiz_name);
            }
            else {
                course_id = data.get(position).getCourseId();
                SharedPreference.getInstance().putString(Const.ID, course_id);
                quiz_id = data.get(position).getId();
                quiz_name = data.get(position).getTestSeriesName();
                totalQuestion = data.get(position).getTotalQuestions();
                result_without_submit(quiz_id,course_id,"0",quiz_name);
            }
        });
        holder.show_rank.setOnClickListener(v -> {
            if (data.get(holder.getAdapterPosition()).getState().equalsIgnoreCase("1"))
            {
                course_id = data.get(position).getCourseId();
                SharedPreference.getInstance().putString(Const.ID, course_id);
                quiz_id = data.get(position).getId();
                quiz_name = data.get(position).getTestSeriesName();
                totalQuestion = data.get(position).getTotalQuestions();

                if (data.get(holder.getAdapterPosition()).getResultDate().equalsIgnoreCase("0") ||data.get(holder.getAdapterPosition()).getResultDate().equalsIgnoreCase("") || data.get(holder.getAdapterPosition()).getResultDate().equalsIgnoreCase("1"))
                {
                    if (LivetestActivity.view_pager.getCurrentItem()==2)
                    {
                        Intent resultScreen = new Intent(activity, QuizActivity.class);
                        resultScreen.putExtra(Const.FRAG_TYPE, Const.RESULT_SCREEN);
                        resultScreen.putExtra(Const.STATUS, quiz_id);
                        resultScreen.putExtra(Const.NAME, quiz_name);
                        resultScreen.putExtra("first_attempt", "1");
                        Helper.gotoActivity(resultScreen,activity);
                    }else {
                        Intent resultScreen = new Intent(activity, QuizActivity.class);
                        resultScreen.putExtra(Const.FRAG_TYPE, Const.RESULT_SCREEN);
                        resultScreen.putExtra(Const.STATUS, quiz_id);
                        resultScreen.putExtra(Const.NAME, quiz_name);
                       // resultScreen.putExtra("show_leader", "0");
                        resultScreen.putExtra("first_attempt", "1");
                        Helper.gotoActivity(resultScreen,activity);
                    }
                }else {
                    if (LivetestActivity.view_pager.getCurrentItem()==2)
                    {
                        if (MakeMyExam.getTime_server() >= (Long.parseLong(data.get(position).getResultDate()) * 1000)) {
                            Intent resultScreen = new Intent(activity, QuizActivity.class);
                            resultScreen.putExtra(Const.FRAG_TYPE, Const.RESULT_SCREEN);
                            resultScreen.putExtra(Const.STATUS, quiz_id);
                            resultScreen.putExtra(Const.NAME, quiz_name);
                            resultScreen.putExtra("first_attempt", "1");
                            activity.startActivity(resultScreen);
                        }else {
                            Snackbar.make(holder.itemView,"Your Result will be declare on "+new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(Long.parseLong(data.get(holder.getAdapterPosition()).getResultDate()) * 1000)) ,Snackbar.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Intent resultScreen = new Intent(activity, QuizActivity.class);
                        resultScreen.putExtra(Const.FRAG_TYPE, Const.RESULT_SCREEN);
                        resultScreen.putExtra(Const.STATUS, quiz_id);
                        resultScreen.putExtra(Const.NAME, quiz_name);
                        resultScreen.putExtra("first_attempt", "1");
                        activity.startActivity(resultScreen);
                    }
                }
            }
            else {
                course_id = data.get(position).getCourseId();
                SharedPreference.getInstance().putString(Const.ID, course_id);
                quiz_id = data.get(position).getId();
                quiz_name = data.get(position).getTestSeriesName();
                totalQuestion = data.get(position).getTotalQuestions();

                if (LivetestActivity.view_pager.getCurrentItem()==2)
                {
                    if (MakeMyExam.getTime_server() >= (Long.parseLong(data.get(position).getResultDate()) * 1000)) {
                        Intent resultScreen = new Intent(activity, QuizActivity.class);
                        resultScreen.putExtra(Const.FRAG_TYPE, "leader_board");
                        resultScreen.putExtra(Const.STATUS, quiz_id);
                        resultScreen.putExtra(Const.NAME, quiz_name);
                        activity.startActivity(resultScreen);
                    }else {
                        Snackbar.make(holder.itemView,"You have miss the test and  Rank  will be declare on "+new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(Long.parseLong(data.get(holder.getAdapterPosition()).getResultDate()) * 1000)) ,Snackbar.LENGTH_SHORT).show();
                    }
                }else {
                    Intent resultScreen = new Intent(activity, QuizActivity.class);
                    resultScreen.putExtra(Const.FRAG_TYPE, "leader_board");
                    resultScreen.putExtra(Const.STATUS, quiz_id);
                    resultScreen.putExtra(Const.NAME, quiz_name);
                    activity.startActivity(resultScreen);
                }

            }

        });
        holder.study_single_itemLL.setOnClickListener(v -> {
            if (!visibilty_status) {
                course_id = data.get(position).getCourseId();
                SharedPreference.getInstance().putString(Const.ID, course_id);
                if (data.get(position).getState().equals("0")) {
                    if (!data.get(position).getStartDate().equalsIgnoreCase("") || !data.get(position).getEndDate().equalsIgnoreCase("")) {
                        long millis1 = Long.parseLong(data.get(position).getStartDate())*1000;
                        long end_millis1 = Long.parseLong(data.get(position).getEndDate())*1000;
                        Date d = new Date(millis1);
                        @SuppressLint("SimpleDateFormat") DateFormat f = new SimpleDateFormat("dd MMM yyyy hh:mm a");
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

                        System.out.println("Current time : " + MakeMyExam.getTime_server());
                        System.out.println("Current time_start : " + millis1);
                        System.out.println("Current time_end : " + end_millis1);

                        if (MakeMyExam.getTime_server() < millis1) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.CustomAlertDialog);
                            builder.setTitle("This test will be available on " + dateString);
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
                        } else if (end_millis1 < MakeMyExam.getTime_server()) {

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
                        }
                    }
                } else {
                    if (!data.get(position).getStartDate().equalsIgnoreCase("") || !data.get(position).getEndDate().equalsIgnoreCase("")) {
                        long millis1 = Long.parseLong(data.get(position).getStartDate())*1000;
                        long end_millis1 = Long.parseLong(data.get(position).getEndDate())*1000;
                        Date d = new Date(millis1);
                        //Date d = new Date(Long.parseLong("1576837800")*1000);
                        @SuppressLint("SimpleDateFormat") DateFormat f = new SimpleDateFormat("dd MMM yyyy hh:mm a");
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
                        System.out.println("Current time : " + MakeMyExam.getTime_server());
                        System.out.println("Current time_start : " + millis1);
                        System.out.println("Current time_end : " + end_millis1);

                        if (MakeMyExam.getTime_server() < millis1) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.CustomAlertDialog);
                            builder.setTitle("This test will be available on " + dateString);
                            builder.setCancelable(false);

                            builder.setNegativeButton(Html.fromHtml("<font color='#000000'>Close</font>"), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        } else if (end_millis1 < MakeMyExam.getTime_server()) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.CustomAlertDialog);
                            builder.setTitle("Test Date Expired !");
                            builder.setCancelable(false);

                            builder.setNegativeButton(Html.fromHtml("<font color='#000000'>Close</font>"), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    }
                }
            }else {
                if (LivetestActivity.view_pager.getCurrentItem()==0)
                {
                    if (holder.attemp.getVisibility()==View.VISIBLE)
                    {
                        course_id = data.get(position).getCourseId();
                        SharedPreference.getInstance().putString(Const.ID, course_id);
                        if (holder.attemp.getText().toString().equalsIgnoreCase("ATTEMPT"))
                        {

                            quiz_id = data.get(position).getId();
                            quiz_name = data.get(position).getTestSeriesName();
                            totalQuestion = data.get(position).getTotalQuestions();
                            result_date = data.get(position).getResultDate();
                            first_attempt="1";
                            test_submission =data.get(position).getSubmission_type();
                            liveTestData =data.get(position);

                            startTestAPI();
                        } else  if (holder.attemp.getText().toString().equalsIgnoreCase("RE-ATTEMPT"))
                        {
                            quiz_id = data.get(position).getId();
                            quiz_name = data.get(position).getTestSeriesName();
                            totalQuestion = data.get(position).getTotalQuestions();
                            result_date = data.get(position).getResultDate();
                            test_submission="1";
                            first_attempt="0";
                            liveTestData =data.get(position);

                            startTestAPI();
                        }else if (holder.attemp.getText().toString().equalsIgnoreCase("ATTEMPTED")){
                            if (data.get(holder.getAdapterPosition()).getResultDate()!=null &&!data.get(holder.getAdapterPosition()).getResultDate().equalsIgnoreCase("0")&& !data.get(holder.getAdapterPosition()).getResultDate().equalsIgnoreCase("1") && !data.get(holder.getAdapterPosition()).getResultDate().equalsIgnoreCase(""))
                            {
                                Snackbar.make(holder.itemView,"Your Result will be declare on "+new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(Long.parseLong(data.get(holder.getAdapterPosition()).getResultDate()) * 1000)) ,Snackbar.LENGTH_SHORT).show();

                            }else if (UtkashRoom.getAppDatabase(activity).getTestDao().is_test_exit(data.get(holder.getAdapterPosition()).getId(),MakeMyExam.userId))
                            {
                                Snackbar.make(holder.itemView,"Your Result is Getting Ready Please refresh your page..",Snackbar.LENGTH_SHORT).show();
                            }else
                            {
                                Snackbar.make(holder.itemView,"Test is alredy submiited",Snackbar.LENGTH_SHORT).show();
                            }

                        }
                    }
                }else if (LivetestActivity.view_pager.getCurrentItem()==2)
                {
                    if (data.get(holder.getAdapterPosition()).getState().equalsIgnoreCase("1"))
                    {
                        course_id = data.get(position).getCourseId();
                        SharedPreference.getInstance().putString(Const.ID, course_id);
                        quiz_id = data.get(position).getId();
                        quiz_name = data.get(position).getTestSeriesName();
                        totalQuestion = data.get(position).getTotalQuestions();
                        result_without_submit(quiz_id,course_id,"1",quiz_name);
                    }
                    else {
                        course_id = data.get(position).getCourseId();
                        SharedPreference.getInstance().putString(Const.ID, course_id);
                        quiz_id = data.get(position).getId();
                        quiz_name = data.get(position).getTestSeriesName();
                        totalQuestion = data.get(position).getTotalQuestions();
                        result_without_submit(quiz_id,course_id,"0",quiz_name);
                    }
                }
            }
        });
    }

    private void sharelivetestolink(int adapterPosition) {
        Helper.shareTestg(activity, data.get(adapterPosition).getPayload().getCourse_id(), data.get(adapterPosition).getId(), data.get(adapterPosition).getPayload().getTopic_id(), data.get(adapterPosition).getPayload().getTile_type(), data.get(adapterPosition).getPayload().getTile_id(), data.get(adapterPosition).getPayload().getRevert_api(), "Test",data.get(adapterPosition).getImage(),data.get(adapterPosition).getTestSeriesName(),"");
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
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (jsonResponse==null){
                            Helper.showToastSecurity(activity);
                            return;
                        }
                        ResultTestSeries_Report resultTestSeries2 = new Gson().fromJson(jsonResponse.toString(), ResultTestSeries_Report.class);
                        if (resultTestSeries2==null){
                            Helper.showToastSecurity(activity);
                            return;
                        }
                        try {
                            MakeMyExam.setTime_server((Long.parseLong(jsonResponse.optString("time")))*1000);

                            if (resultTestSeries2.getStatus().equalsIgnoreCase("true")) {
                                SharedPreference.getInstance().putString("testresult", new Gson().toJson(resultTestSeries2));

                                Intent intent = new Intent(activity, ViewSolutionActivity.class);
                                intent.putExtra(Const.TESTSEGMENT_ID, quiz_id);
                                intent.putExtra(Const.FRAG_TYPE,  Const.SOLUTIONREPORT);
//                                intent.putExtra(Const.RESULT_SCREEN, resultTestSeries2);
                                intent.putExtra(Const.NAME, quiz_name);
                                intent.putExtra("type", "learn");
                                if (resultTestSeries2.getData().getLang_id().split(",")[0].equals("1")) {
                                    lang = Integer.parseInt(resultTestSeries2.getData().getLang_id().split(",")[0]);
                                } else if (resultTestSeries2.getData().getLang_id().split(",")[0].equals("2")) {
                                    lang = Integer.parseInt(resultTestSeries2.getData().getLang_id().split(",")[0]);
                                }
                                intent.putExtra(Const.LANG,lang);

                                // intent.putExtra(Const.LANG, TextUtils.isEmpty(resultTestSeries2.getData().getLang_id())?1:Integer.parseInt(resultTestSeries2.getData().getLang_id()));
                                Helper.gotoActivity(intent,activity);

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
                        }catch (Exception e){
                        }

                        if (jsonResponse==null){
                            Helper.showToastSecurity(activity);
                            return;
                        }

                        ResultTestSeries_Report resultTestSeries2 = new Gson().fromJson(jsonResponse.toString(), ResultTestSeries_Report.class);

                        if (resultTestSeries2==null){
                            Helper.showToastSecurity(activity);
                            return;
                        }

                        try {
                            MakeMyExam.setTime_server((Long.parseLong(jsonResponse.optString("time")))*1000);

                            if (resultTestSeries2.getStatus().equalsIgnoreCase("true")) {
                                if (resultTestSeries2.getData().getQuestions().size()>0)
                                {
                                    SharedPreference.getInstance().putString("testresult", new Gson().toJson(resultTestSeries2));

                                    Intent intent = new Intent(activity, ViewSolutionActivity.class);
                                    intent.putExtra(Const.TESTSEGMENT_ID, quiz_id);
                                    intent.putExtra(Const.FRAG_TYPE,  Const.SOLUTIONREPORT);
//                                    intent.putExtra(Const.RESULT_SCREEN, resultTestSeries2);
                                    intent.putExtra(Const.NAME, quiz_name);
                                    intent.putExtra("type", "learn");

                                    if (resultTestSeries2.getData().getLang_id().split(",")[0].equals("1")) {
                                        lang = Integer.parseInt(resultTestSeries2.getData().getLang_id().split(",")[0]);
                                    } else if (resultTestSeries2.getData().getLang_id().split(",")[0].equals("2")) {
                                        lang = Integer.parseInt(resultTestSeries2.getData().getLang_id().split(",")[0]);
                                    }
                                    intent.putExtra(Const.LANG,lang);
                                    Helper.gotoActivity(intent,activity);
                                }else {
                                    Toast.makeText(activity, "No Question Found", Toast.LENGTH_SHORT).show();
                                }


                            } else {
                                Helper.dismissProgressDialog();

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

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.API_GET_TEST_INSTRUCTION_DATA:
                EncryptionData masterdatadetailencryptionData=new EncryptionData();
                masterdatadetailencryptionData.setTest_id(quiz_id);
                masterdatadetailencryptionData.setCourse_id(course_id);
                String getmasterdataencryptionDatadoseStr = new Gson().toJson(masterdatadetailencryptionData);
                String masterdatadoseStrScr = AES.encrypt(getmasterdataencryptionDatadoseStr);
                return service.API_GET_TEST_INSTRUCTION_DATA(masterdatadoseStrScr);

            case API.API_GET_INFO_TEST_SERIES:
                EncryptionData masterdatadetailencryptionData1=new EncryptionData();
                masterdatadetailencryptionData1.setTest_id(quiz_id);
                masterdatadetailencryptionData1.setCourse_id(course_id);
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
            case API.API_GET_TEST_INSTRUCTION_DATA:
                if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                    JSONObject data1 = jsonobject.getJSONObject("data");
                    InstructionData instructionData = gson.fromJson(data1.toString(), InstructionData.class);

                    showPopUp(instructionData);

                } else if (jsonobject.optString("status").equals(Const.FALSE)) {
                    RetrofitResponse.GetApiData(activity,jsonobject.has(Const.AUTH_CODE)?jsonobject.getString(Const.AUTH_CODE):"",jsonobject.getString(Const.MESSAGE),false);
                }
                break;

            case API.API_GET_INFO_TEST_SERIES:
                if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                    Long time=   jsonobject.optLong("time");
                    TestseriesBase testseriesBase = null;
                    try {
                        testseriesBase = gson.fromJson(jsonobject.toString(), TestseriesBase.class);

                        if (testseriesBase.getData().getQuestions()!=null &&testseriesBase.getData().getQuestions().size()>0 && lang==1)
                        {
                            Intent quizView = new Intent(activity, TestBaseActivity.class);
                            quizView.putExtra(Const.STATUS, false);
                            quizView.putExtra(Const.TEST_SERIES_ID, quiz_id);
                            quizView.putExtra(Const.TEST_SERIES_Name, quiz_name);
                            SharedPreference.getInstance().putString("test_series",jsonobject.toString());

                            quizView.putExtra("TOTAL_QUESTIONS", totalQuestion);
                            quizView.putExtra("first_attempt", first_attempt);
                            quizView.putExtra("result_date", result_date);
                            quizView.putExtra("test_submission", test_submission);
                            quizView.putExtra("course_id", course_id);
                            quizView.putExtra(Const.LANG, lang);
                            quizView.putExtra("time", time);
                            quizView.putExtra("enddate", liveTestData.getEndDate());
                            Helper.gotoActivity(quizView,activity);
                        }
                        else if (testseriesBase.getData().getQuestionsHindi()!=null &&testseriesBase.getData().getQuestionsHindi().size()>0 ){
                            testseriesBase.getData().setQuestions(testseriesBase.getData().getQuestionsHindi());
                            Intent quizView = new Intent(activity, TestBaseActivity.class);
                            quizView.putExtra(Const.STATUS, false);
                            quizView.putExtra(Const.TEST_SERIES_ID, quiz_id);
                            quizView.putExtra(Const.TEST_SERIES_Name, quiz_name);
                            SharedPreference.getInstance().putString("test_series",jsonobject.toString());
                            quizView.putExtra("TOTAL_QUESTIONS", totalQuestion);
                            quizView.putExtra("first_attempt", first_attempt);
                            quizView.putExtra("result_date", result_date);
                            quizView.putExtra("test_submission", test_submission);
                            quizView.putExtra("course_id", course_id);
                            quizView.putExtra("time", time);
                            quizView.putExtra("enddate", liveTestData.getEndDate());
                            quizView.putExtra(Const.LANG, lang);
                            Helper.gotoActivity(quizView,activity);
                        }
                        else {
                            Toast.makeText(activity, "No Question found", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(activity, "Something went wrong.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    RetrofitResponse.GetApiData(activity,jsonobject.has(Const.AUTH_CODE)?jsonobject.getString(Const.AUTH_CODE):"",jsonobject.getString(Const.MESSAGE),false);
                }
                break;
        }
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {

    }



    public  class Livetestviewholder extends RecyclerView.ViewHolder {
        long timecount = 1000;
        ImageView courseImage;
        GifImageView liveIV;
        ImageView forward;
        public    LinearLayout layout_test;
        TextView title,c_name;
        TextView date_tv, endDate_tv,show_rank,learn,practice,attemp,share;
        public RelativeLayout study_single_itemLL;

        long timerr;
        TextView time,startedin;
        CountDownTimer timer;

        public Livetestviewholder(View itemView) {
            super(itemView);
            courseImage = itemView.findViewById(R.id.courseImage);
            liveIV = itemView.findViewById(R.id.liveIV);
            forward = itemView.findViewById(R.id.forwardIV);
            title = itemView.findViewById(R.id.study_item_titleTV);
            learn = itemView.findViewById(R.id.learn);
            c_name = itemView.findViewById(R.id.c_name);
            time = itemView.findViewById(R.id.time);
            startedin = itemView.findViewById(R.id.startedin);

            show_rank = itemView.findViewById(R.id.show_rank);
            attemp = itemView.findViewById(R.id.attemp);
            practice = itemView.findViewById(R.id.practice);
            share = itemView.findViewById(R.id.share);
            layout_test = itemView.findViewById(R.id.layout_test);
            date_tv = itemView.findViewById(R.id.date_tv);
            if (visibilty_status)
            {
                layout_test.setVisibility(View.VISIBLE);
            }
            else
            {
                layout_test.setVisibility(View.VISIBLE);
                forward.setVisibility(View.VISIBLE);
                show_rank.setVisibility(View.GONE);
                learn.setVisibility(View.GONE);
                practice.setVisibility(View.GONE);
                attemp.setVisibility(View.GONE);
            }
            endDate_tv = itemView.findViewById(R.id.endDate_tv);
            study_single_itemLL = itemView.findViewById(R.id.study_single_itemLL);
        }
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
        final TextView quizTitle, quizQuesNumTv, quizTimeTV, quizCorrectValue,totTimeTV, quizWrongValue, quizTotalMarks, generalInstrValueTV, reAttempt, languageSpinnerTV, quizsectionValueTV;
        ImageView quizImage;
        final CheckBox check_box;
        Button startQuiz;
        LinearLayout sectionListLL,general_layout,section_time;
        final TestBasicInst testBasicInst = instructionData.getTestBasic();

        basicInfoQuizLL = (RelativeLayout) v.findViewById(R.id.basicInfoDialogLL);
        quizTitle = (TextView) v.findViewById(R.id.quizTitleTV);
        quizImage = (ImageView) v.findViewById(R.id.quizImageIV);
        quizCorrectValue = (TextView) v.findViewById(R.id.marksCorrectValueTV);
        quizWrongValue = (TextView) v.findViewById(R.id.marksWrongValueTV);
        quizTotalMarks = (TextView) v.findViewById(R.id.marksTextValueTV);
        section_time = (LinearLayout) v.findViewById(R.id.section_time);
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
        general_layout = (LinearLayout) v.findViewById(R.id.general_layout);

        if (testBasicInst.getTest_assets()!=null)
        {
            if ( testBasicInst.getTest_assets().getHide_inst_time().equalsIgnoreCase("0"))
            {
                section_time.setVisibility(View.VISIBLE);
            }else {
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
      //  quizQuesNumTv.setText(testBasicInst.getTotalQuestions());
        quizTimeTV.setText(testBasicInst.getTimeInMins());
        //quizTotalMarks.setText(testBasicInst.getTotalMarks());
        if (testBasicInst.getDescription().isEmpty())
        {
            general_layout.setVisibility(View.GONE);
        }else
        {
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
                        NetworkCall networkCall = new NetworkCall(Testclassadapter.this, activity);

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
            if (testSectionInst.getOptional_que()==null)
            {
                testSectionInst.setOptional_que("0");
            }
            sectionListLL.addView(initSectionListView(testSectionInst, count,instructionData.getTestBasic().getTest_assets()==null ? "":instructionData.getTestBasic().getTest_assets().getHide_inst_time()));
            count++;
        }
    }

    @SuppressLint("SetTextI18n")
    public LinearLayout initSectionListView(TestSectionInst testSectionInst, int tag, String hide_inst_time) {
        List<View> LinearLayoutList = new ArrayList<>();
        LinearLayout v = (LinearLayout) View.inflate(activity, R.layout.layout_option_section_list_view, null);
        TextView secNameTV = (TextView) v.findViewById(R.id.secNameTV);
        TextView totQuesTV = (TextView) v.findViewById(R.id.totQuesTV);
        TextView totTimeTV = (TextView) v.findViewById(R.id.totTimeTV);
        TextView option_count = (TextView) v.findViewById(R.id.option_count);
        TextView maxMarksTV = (TextView) v.findViewById(R.id.maxMarksTV);
        TextView markPerQuesTV = (TextView) v.findViewById(R.id.markPerQuesTV);
        TextView negMarkPerQuesTV = (TextView) v.findViewById(R.id.negMarkPerQuesTV);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);

        v.setLayoutParams(lp);

        if (!hide_inst_time.equalsIgnoreCase(""))
        {
            if (hide_inst_time.equalsIgnoreCase("0"))
            {
                totTimeTV.setVisibility(View.VISIBLE);
            }else {
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

    public void showPopMenuForLangauge(final View v, TestBasicInst testBasicInst) {
        PopupMenu popup = new PopupMenu(activity, v);

        popup.setOnMenuItemClickListener(item -> {
            ((TextView) v).setText(item.getTitle().toString());
            if (item.getTitle().toString().equals(activity.getString(R.string.hindi)))
                lang = 2;
            else if (item.getTitle().toString().equals(activity.getString(R.string.english)))
                lang = 1;
            Log.e("Language", String.valueOf(lang));
            return false;
        });

        for (int i = 0; i < testBasicInst.getLang_id().split(",").length; i++) {
            if (testBasicInst.getLang_id().split(",")[i].equals("1"))
                popup.getMenu().add(activity.getResources().getStringArray(R.array.dialog_choose_language_array)[0]);
            else if (testBasicInst.getLang_id().split(",")[i].equals("2"))
                popup.getMenu().add(activity.getResources().getStringArray(R.array.dialog_choose_language_array)[1]);

        }
        popup.show();
    }

    private void startTestAPI() {
        NetworkCall networkCall = new NetworkCall(Testclassadapter.this, activity);
        networkCall.NetworkAPICall(API.API_GET_TEST_INSTRUCTION_DATA, "", true,false);
    }

    public String concerter(long time)
    {

        String actualtime=   String.format("%02d:%02d:%02d",
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

    public void notifyadap(Livetestviewholder  holder, int position)
    {
        holder.attemp.setVisibility(View.VISIBLE);
        holder.attemp.setText("ATTEMPT");
        holder.startedin.setVisibility(View.GONE);
        holder.time.setVisibility(View.GONE);
    }
}