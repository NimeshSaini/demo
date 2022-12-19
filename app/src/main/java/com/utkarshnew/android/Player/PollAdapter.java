package com.utkarshnew.android.Player;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.Model.PlayerPojo.Polldata;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;

public class PollAdapter extends RecyclerView.Adapter<PollAdapter.Viewholder> implements NetworkCall.MyNetworkCallBack {
    private Activity con;
    private Handler handler = new Handler();
    private ArrayList<Polldata> polldata;
    private ScheduledFuture updateFuture;
    NetworkCall networkCall;
    int Selectedpos;


    public PollAdapter(Activity con, ArrayList<Polldata> polldata) {
        this.con = con;
        this.polldata = polldata;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(con);
        networkCall = new NetworkCall(this, con);
        View view = layoutInflater.inflate(R.layout.activity_cardview_poll, null);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        if (polldata.get(holder.getAdapterPosition()).getStatus().equalsIgnoreCase("1")) {
            holder.mainlayout.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 180));

            holder.timerr = 0;

            holder.timerr = Long.parseLong(polldata.get(holder.getAdapterPosition()).getValidTill()) - ((System.currentTimeMillis()) / holder.timecount);

            holder.timerr = holder.timerr * holder.timecount;

            holder.timer = new CountDownTimer(holder.timerr, holder.timecount) {
                public void onTick(long millisUntilFinished) {
                    holder.time.setText(concerter(millisUntilFinished));
                }

                public void onFinish() {
                    holder.time.setText("00:00");
                    notifyadap(holder, position);
                }
            }.start();
        } else {
            holder.mainlayout.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }

        if (polldata.get(holder.getAdapterPosition()).getAnswer().equalsIgnoreCase("0")) {
            holder.buyNowBtn.setText("Survey");
            holder.pollNumber.setText("Poll");

        } else {
            holder.buyNowBtn.setText("Quiz");
            holder.pollNumber.setText("Poll");
        }

        holder.takeAPollBtn.setEnabled(true);

        long currenttime = System.currentTimeMillis() / 1000;

        if (Long.parseLong(polldata.get(position).getValidTill()) > currenttime) {
            if (polldata.get(holder.getAdapterPosition()).getMyAnswer().equalsIgnoreCase("0")) {
                if (polldata.get(holder.getAdapterPosition()).getAnswer().equalsIgnoreCase("0")) {
                    holder.takeAPollBtn.setText("Take Survey");

                } else {
                    holder.takeAPollBtn.setText("Take Poll");
                }
            } else {
                holder.takeAPollBtn.setText("Submitted");
//                holder.takeAPollBtn.setEnabled(false);
            }
        } else {
            if (!polldata.get(holder.getAdapterPosition()).getAnswer().equalsIgnoreCase("0")) {
                if (polldata.get(holder.getAdapterPosition()).getDisable_result().equalsIgnoreCase("0")) {
                    holder.takeAPollBtn.setText("Result");
//                holder.takeAPollBtn.setEnabled(true);
                } else {
                    holder.takeAPollBtn.setText("Expired");
                }
            } else {
                if (polldata.get(holder.getAdapterPosition()).getDisable_result().equalsIgnoreCase("0")) {
                    holder.takeAPollBtn.setText("Survey Result");
                } else {
                    holder.takeAPollBtn.setText("Expired");
                }
            }
        }

//        if (holder.timer != null) {
//            holder.timer.cancel();
//        }

        /**/

//        if (holder.timer != null) {
//            holder.timer.cancel();
//        }
        try {
            holder.expireddate.setText(getdate(polldata.get(position).getValidTill()));
        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.takeAPollBtn.setOnClickListener(new OnSingleClickListener(() -> {

            if (holder.takeAPollBtn.getText().equals("Take Poll") || holder.takeAPollBtn.getText().equals("Take Survey")) {
                Selectedpos = holder.getAdapterPosition();
                openwatchlist_dailog_resource(con, polldata.get(holder.getAdapterPosition()));
            } else if (holder.takeAPollBtn.getText().equals("Expired")) {
                Toast.makeText(con, "Poll is Expired", Toast.LENGTH_SHORT).show();
            } else if (holder.takeAPollBtn.getText().equals("Survey Result")) {
                openwatchlist_dailog_servey_result(con, polldata.get(holder.getAdapterPosition()));
            } else if (holder.takeAPollBtn.getText().equals("Result")) {
                openwatchlist_dailog_resource_result(con, polldata.get(holder.getAdapterPosition()));
//                }
//                else
//                {
//                    Toast.makeText(con,"Poll result will display soon",Toast.LENGTH_SHORT).show();
//
//                }

            } else if (holder.takeAPollBtn.getText().equals("Submitted")) {
                if (!polldata.get(holder.getAdapterPosition()).getAnswer().equalsIgnoreCase("0") && polldata.get(holder.getAdapterPosition()).getDisable_result().equalsIgnoreCase("0")) {
                    Toast.makeText(con, "Poll result will display soon", Toast.LENGTH_SHORT).show();
                }
//               else
//               {
//                   Toast.makeText(con,"Survey result will display soon",Toast.LENGTH_SHORT).show();
//               }
            }

//            long currenttime=System.currentTimeMillis()/1000;
//
//            if(Long.parseLong(polldata.get(position).getValidTill())>currenttime)
//            {
//                Selectedpos = position;
//                if(!polldata.get(position).getMyAnswer().equalsIgnoreCase("0"))
//                {
//                    openwatchlist_dailog_resource_result(con, polldata.get(position));
//                }
//                else
//                {
//                    openwatchlist_dailog_resource(con, polldata.get(position));
//                }
//            }
//            else
//            {
//                Toast.makeText(con,"Poll is expired",Toast.LENGTH_SHORT).show();
//            }
            return null;
        }));
    }

    public String concerter(long time) {
        long hr = TimeUnit.MILLISECONDS.toHours(time)
                - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(time));
        long min = TimeUnit.MILLISECONDS.toMinutes(time)
                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time));
        long sec = TimeUnit.MILLISECONDS.toSeconds(time)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time));
//        String actualtime=String.valueOf(hr)+":"+String.valueOf(min)+":"+String.valueOf(sec);
        String actualtime = String.format("%02d:%02d", (min), sec);
        return actualtime;
    }

    @Override
    public int getItemCount() {
        return polldata.size();
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.submitpoll:
                EncryptionData metadataencryptionData = new EncryptionData();
                metadataencryptionData.setAnswer(select);
                metadataencryptionData.setPoll_id(polldata.get(Selectedpos).getId());
                String metadataencryptionDatadoseStr = new Gson().toJson(metadataencryptionData);
                String metadatadoseStrScr = AES.encrypt(metadataencryptionDatadoseStr);
                return service.sendpoll(metadatadoseStrScr);
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonstring, String apitype, String typeApi, boolean showprogress) throws JSONException {

        switch (apitype) {
            case API.submitpoll:
                if (jsonstring.getString("status").equalsIgnoreCase("true")) {
                    try {
                        if (watchlist.isShowing()) {
                            watchlist.dismiss();
                        }

                        if (con instanceof CustomMediaPlayer) {
                            ((CustomMediaPlayer) con).setpollcount(polldata.get(Selectedpos).getRendomkey(), select);
                        } else if (con instanceof Liveawsactivity) {
                            ((Liveawsactivity) con).setpollcount(polldata.get(Selectedpos).getRendomkey(), select);
                        }

                        polldata.get(Selectedpos).setMyAnswer(select);
                        select = "";
                        notifyItemChanged(Selectedpos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    this.ErrorCallBack(jsonstring.getString(Const.MESSAGE), apitype, typeApi);
                    RetrofitResponse.GetApiData(con, jsonstring.getString(Const.AUTH_CODE), jsonstring.getString(Const.MESSAGE), false);
                }
                break;
        }
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {
        Toast.makeText(con, "Error in submit poll", Toast.LENGTH_SHORT).show();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        long timecount = 1000;
        long singlecount = 1;
        CountDownTimer timer;
        TextView time, expireddate, pollNumber;
        Button takeAPollBtn, buyNowBtn;
        RelativeLayout mainlayout;
        long timerr;

        public Viewholder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            takeAPollBtn = itemView.findViewById(R.id.takeAPollBtn);
            expireddate = itemView.findViewById(R.id.expireddate);
            mainlayout = itemView.findViewById(R.id.mainlayout);
            pollNumber = itemView.findViewById(R.id.pollNumber);
            buyNowBtn = itemView.findViewById(R.id.buyNowBtn);
        }
    }

    BottomSheetDialog watchlist;
    View typeA, typeB, typeC, typeD;
    String select = "";

    public void openwatchlist_dailog_resource(Context context, Polldata polldata) {
        try {
            watchlist = new BottomSheetDialog(context, R.style.videosheetDialogTheme);
            watchlist.setContentView(R.layout.pollquestion);
            Objects.requireNonNull(watchlist.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            watchlist.setCancelable(false);
            watchlist.setCanceledOnTouchOutside(true);
            TextView txt1 = watchlist.findViewById(R.id.txt1);
            Button cancel = watchlist.findViewById(R.id.cancel);
            Button submit = watchlist.findViewById(R.id.submit);
            Button buyNowBtn = watchlist.findViewById(R.id.buyNowBtn);
            typeA = watchlist.findViewById(R.id.typeA);
            typeB = watchlist.findViewById(R.id.typeB);
            typeC = watchlist.findViewById(R.id.typeC);
            typeD = watchlist.findViewById(R.id.typeD);

            if (polldata.getAnswer().equalsIgnoreCase("0")) {
                buyNowBtn.setText("Survey");
            } else {
                buyNowBtn.setText("Quiz");
            }

            submit.setOnClickListener(new OnSingleClickListener(() -> {
                if (select.equalsIgnoreCase("")) {
                    Toast.makeText(con, "Please select answer", Toast.LENGTH_SHORT).show();
                } else {
                    long currenttime = System.currentTimeMillis() / 1000;
                    if (Long.parseLong(polldata.getValidTill()) > currenttime) {
                        networkCall.NetworkAPICall(API.submitpoll, "", false, false);
                    } else {
                        Toast.makeText(con, "Expired", Toast.LENGTH_SHORT).show();
                        notifyItemChanged(Selectedpos);
                    }
                }
                return null;
            }));


            TextView typea = typeA.findViewById(R.id.options);
            TextView txtOptionNumbera = typeA.findViewById(R.id.txtOptionNumber);
            typea.setText(Html.fromHtml(polldata.getOption1()));
            txtOptionNumbera.setText("A");

            if (polldata.getOption1().equalsIgnoreCase("")) {
                typeA.setVisibility(View.GONE);
            } else {
                typeA.setVisibility(View.VISIBLE);
            }

            TextView typeb = typeB.findViewById(R.id.options);
            TextView txtOptionNumberb = typeB.findViewById(R.id.txtOptionNumber);
            typeb.setText(Html.fromHtml(polldata.getOption2()));
            txtOptionNumberb.setText("B");

            if (polldata.getOption2().equalsIgnoreCase("")) {
                typeB.setVisibility(View.GONE);
            } else {
                typeB.setVisibility(View.VISIBLE);
            }

            TextView typec = typeC.findViewById(R.id.options);
            TextView txtOptionNumberc = typeC.findViewById(R.id.txtOptionNumber);
            typec.setText(Html.fromHtml(polldata.getOption3()));
            txtOptionNumberc.setText("C");


            if (polldata.getOption3().equalsIgnoreCase("")) {
                typeC.setVisibility(View.GONE);
            } else {
                typeC.setVisibility(View.VISIBLE);
            }

            TextView typed = typeD.findViewById(R.id.options);
            TextView txtOptionNumberd = typeD.findViewById(R.id.txtOptionNumber);
            typed.setText(Html.fromHtml(polldata.getOption4()));
            txtOptionNumberd.setText("D");

            if (polldata.getOption4().equalsIgnoreCase("")) {
                typeD.setVisibility(View.GONE);
            } else {
                typeD.setVisibility(View.VISIBLE);
            }

            typeA.setOnClickListener(new OnSingleClickListener(() -> {
                select = "1";
                selectedpoll(typeA);
                return null;
            }));

            typeB.setOnClickListener(new OnSingleClickListener(() -> {
                select = "2";
                selectedpoll(typeB);
                return null;
            }));

            typeC.setOnClickListener(new OnSingleClickListener(() -> {
                select = "3";
                selectedpoll(typeC);
                return null;
            }));

            typeD.setOnClickListener(new OnSingleClickListener(() -> {
                select = "4";
                selectedpoll(typeD);
                return null;
            }));


            txt1.setText(Html.fromHtml(polldata.getQuestion()));

            cancel.setOnClickListener(new OnSingleClickListener(() -> {
                watchlist.dismiss();
                return null;
            }));

            if (!watchlist.isShowing()) {
                watchlist.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void openwatchlist_dailog_servey_result(Context context, Polldata polldata) {
        try {
            watchlist = new BottomSheetDialog(context, R.style.videosheetDialogTheme);
            watchlist.setContentView(R.layout.serveyresult);
            Objects.requireNonNull(watchlist.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            watchlist.setCancelable(false);
            watchlist.setCanceledOnTouchOutside(true);
            TextView txt1 = watchlist.findViewById(R.id.txt1);
            Button cancel = watchlist.findViewById(R.id.cancel);
            Button submit = watchlist.findViewById(R.id.submit);
            typeA = watchlist.findViewById(R.id.typeA);
            typeB = watchlist.findViewById(R.id.typeB);
            typeC = watchlist.findViewById(R.id.typeC);
            typeD = watchlist.findViewById(R.id.typeD);

            submit.setOnClickListener(new OnSingleClickListener(() -> {
                watchlist.dismiss();
                return null;
            }));

            txt1.setText(Html.fromHtml(polldata.getQuestion()));
            if (con instanceof Liveawsactivity) {
                ((Liveawsactivity) context).getServeyData(polldata);
            } else {
                ((CustomMediaPlayer) context).getServeyData(polldata);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void openwatchlist_dailog_resource_result(Context context, Polldata polldata) {
        try {
            watchlist = new BottomSheetDialog(context, R.style.videosheetDialogTheme);
            watchlist.setContentView(R.layout.serveyresult);
            Objects.requireNonNull(watchlist.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            watchlist.setCancelable(false);
            watchlist.setCanceledOnTouchOutside(true);
            TextView txt1 = watchlist.findViewById(R.id.txt1);
            Button cancel = watchlist.findViewById(R.id.cancel);
            Button submit = watchlist.findViewById(R.id.submit);
            Button buyNowBtn = watchlist.findViewById(R.id.buyNowBtn);
            typeA = watchlist.findViewById(R.id.typeA);
            typeB = watchlist.findViewById(R.id.typeB);
            typeC = watchlist.findViewById(R.id.typeC);
            typeD = watchlist.findViewById(R.id.typeD);

            buyNowBtn.setText("Quiz Result");


            txt1.setText(Html.fromHtml(polldata.getQuestion()));
            if (con instanceof Liveawsactivity) {
                ((Liveawsactivity) context).getServeyData(polldata);
            } else {
                ((CustomMediaPlayer) context).getServeyData(polldata);
            }

            submit.setOnClickListener(new OnSingleClickListener(() -> {
                watchlist.dismiss();
                return null;
            }));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showres() {
        progressBar.setProgressDrawable(con.getResources().getDrawable(R.drawable.bg_round_corners_green_back));
        progressBar2.setProgressDrawable(con.getResources().getDrawable(R.drawable.bg_round_corners_red_back));
        progressBar3.setProgressDrawable(con.getResources().getDrawable(R.drawable.progress_bar_grey));
        progressBar4.setProgressDrawable(con.getResources().getDrawable(R.drawable.progress_bar_blue));
    }

    private void showpollresult(String myanswer, String correntone) {
        if (correntone.equalsIgnoreCase("")) {
            if (myanswer.equalsIgnoreCase("1")) {

                progressBar.setProgressDrawable(con.getResources().getDrawable(R.drawable.bg_round_corners_green_back));
                progressBar2.setProgressDrawable(con.getResources().getDrawable(R.drawable.bg_round_corners_white_back));
                progressBar3.setProgressDrawable(con.getResources().getDrawable(R.drawable.bg_round_corners_white_back));
                progressBar4.setProgressDrawable(con.getResources().getDrawable(R.drawable.bg_round_corners_white_back));
            } else if (myanswer.equalsIgnoreCase("2")) {
                progressBar.setProgressDrawable(con.getResources().getDrawable(R.drawable.bg_round_corners_white_back));
                progressBar2.setProgressDrawable(con.getResources().getDrawable(R.drawable.bg_round_corners_green_back));
                progressBar3.setProgressDrawable(con.getResources().getDrawable(R.drawable.bg_round_corners_white_back));
                progressBar4.setProgressDrawable(con.getResources().getDrawable(R.drawable.bg_round_corners_white_back));
            } else if (myanswer.equalsIgnoreCase("3")) {
                progressBar.setProgressDrawable(con.getResources().getDrawable(R.drawable.bg_round_corners_white_back));
                progressBar2.setProgressDrawable(con.getResources().getDrawable(R.drawable.bg_round_corners_white_back));
                progressBar3.setProgressDrawable(con.getResources().getDrawable(R.drawable.bg_round_corners_green_back));
                progressBar4.setProgressDrawable(con.getResources().getDrawable(R.drawable.bg_round_corners_white_back));
            } else if (myanswer.equalsIgnoreCase("4")) {
                progressBar.setProgressDrawable(con.getResources().getDrawable(R.drawable.bg_round_corners_white_back));
                progressBar2.setProgressDrawable(con.getResources().getDrawable(R.drawable.bg_round_corners_white_back));
                progressBar3.setProgressDrawable(con.getResources().getDrawable(R.drawable.bg_round_corners_white_back));
                progressBar4.setProgressDrawable(con.getResources().getDrawable(R.drawable.bg_round_corners_green_back));
            }
        } else {
            progressBar.setProgressDrawable(con.getResources().getDrawable(R.drawable.bg_round_corners_white_back));
            progressBar2.setProgressDrawable(con.getResources().getDrawable(R.drawable.bg_round_corners_white_back));
            progressBar3.setProgressDrawable(con.getResources().getDrawable(R.drawable.bg_round_corners_white_back));
            progressBar4.setProgressDrawable(con.getResources().getDrawable(R.drawable.bg_round_corners_white_back));

            if (correntone.equalsIgnoreCase("1")) {
                progressBar.setProgressDrawable(con.getResources().getDrawable(R.drawable.bg_round_corners_green_back));
            } else if (correntone.equalsIgnoreCase("2")) {
                progressBar2.setProgressDrawable(con.getResources().getDrawable(R.drawable.bg_round_corners_green_back));
            } else if (correntone.equalsIgnoreCase("3")) {
                progressBar3.setProgressDrawable(con.getResources().getDrawable(R.drawable.bg_round_corners_green_back));
            } else if (correntone.equalsIgnoreCase("4")) {
                progressBar4.setProgressDrawable(con.getResources().getDrawable(R.drawable.bg_round_corners_green_back));
            }

            if (myanswer.equalsIgnoreCase("1")) {
                progressBar.setProgressDrawable(con.getResources().getDrawable(R.drawable.bg_round_corners_red_back));
            } else if (myanswer.equalsIgnoreCase("2")) {
                progressBar2.setProgressDrawable(con.getResources().getDrawable(R.drawable.bg_round_corners_red_back));
            } else if (myanswer.equalsIgnoreCase("3")) {
                progressBar3.setProgressDrawable(con.getResources().getDrawable(R.drawable.bg_round_corners_red_back));
            } else if (myanswer.equalsIgnoreCase("4")) {
                progressBar4.setProgressDrawable(con.getResources().getDrawable(R.drawable.bg_round_corners_red_back));
            }
        }

    }


    private void selectedpoll(View selectedtype) {
        if (selectedtype == typeA) {
            typeA.setBackgroundResource(R.drawable.bg_round_corners_green_back);
            typeB.setBackgroundResource(R.drawable.bg_round_corners_white_back);
            typeC.setBackgroundResource(R.drawable.bg_round_corners_white_back);
            typeD.setBackgroundResource(R.drawable.bg_round_corners_white_back);
        } else if (selectedtype == typeB) {
            typeA.setBackgroundResource(R.drawable.bg_round_corners_white_back);
            typeB.setBackgroundResource(R.drawable.bg_round_corners_green_back);
            typeC.setBackgroundResource(R.drawable.bg_round_corners_white_back);
            typeD.setBackgroundResource(R.drawable.bg_round_corners_white_back);
        } else if (selectedtype == typeC) {
            typeA.setBackgroundResource(R.drawable.bg_round_corners_white_back);
            typeB.setBackgroundResource(R.drawable.bg_round_corners_white_back);
            typeC.setBackgroundResource(R.drawable.bg_round_corners_green_back);
            typeD.setBackgroundResource(R.drawable.bg_round_corners_white_back);
        } else if (selectedtype == typeD) {
            typeA.setBackgroundResource(R.drawable.bg_round_corners_white_back);
            typeB.setBackgroundResource(R.drawable.bg_round_corners_white_back);
            typeC.setBackgroundResource(R.drawable.bg_round_corners_white_back);
            typeD.setBackgroundResource(R.drawable.bg_round_corners_green_back);
        }
    }


    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    public String getdate(String timestamp) {
        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getDefault();
        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault());
        java.util.Date currenTimeZone = new java.util.Date((long) Long.parseLong(timestamp) * 1000);
        return Helper.changeAMPM(sdf.format(currenTimeZone));
    }

    ProgressBar progressBar, progressBar2, progressBar3, progressBar4;

    public void SetServeyresult(Polldata polldata, HashMap<String, Float> servaydata) {
        TextView typea = typeA.findViewById(R.id.options);
        progressBar = typeA.findViewById(R.id.progresscolor);
        TextView txtOptionNumbera = typeA.findViewById(R.id.txtOptionNumber);
        TextView pertile = typeA.findViewById(R.id.percenttile);
        typea.setText(Html.fromHtml(polldata.getOption1()));
        txtOptionNumbera.setText("A");
//            progressBar.setProgressDrawable(con.getResources().getDrawable(R.drawable.progress_bar_red));
        progressBar.setProgress((int) Float.parseFloat(String.valueOf(servaydata.get("perA"))));
        pertile.setText(String.format("%.2f", servaydata.get("perA")) + "%");
        progressBar.setMax(100);
        if (polldata.getOption1().equalsIgnoreCase("")) {
            typeA.setVisibility(View.GONE);
        } else {
            typeA.setVisibility(View.VISIBLE);
        }


        TextView typeb = typeB.findViewById(R.id.options);
        TextView txtOptionNumberb = typeB.findViewById(R.id.txtOptionNumber);
        typeb.setText(Html.fromHtml(polldata.getOption2()));
        txtOptionNumberb.setText("B");
        progressBar2 = typeB.findViewById(R.id.progresscolor);
        TextView pertileB = typeB.findViewById(R.id.percenttile);
//            progressBar2.setProgressDrawable(con.getResources().getDrawable(R.drawable.progress_bar_green));
        progressBar2.setProgress((int) Float.parseFloat(String.valueOf(servaydata.get("perB"))));
        progressBar2.setMax(100);
        pertileB.setText(String.format("%.2f", servaydata.get("perB")) + "%");
        if (polldata.getOption2().equalsIgnoreCase("")) {
            typeB.setVisibility(View.GONE);
        } else {
            typeB.setVisibility(View.VISIBLE);
        }


        TextView typec = typeC.findViewById(R.id.options);
        TextView txtOptionNumberc = typeC.findViewById(R.id.txtOptionNumber);
        typec.setText(Html.fromHtml(polldata.getOption3()));
        txtOptionNumberc.setText("C");
        progressBar3 = typeC.findViewById(R.id.progresscolor);
        TextView pertileC = typeC.findViewById(R.id.percenttile);
//            progressBar3.setProgressDrawable(con.getResources().getDrawable(R.drawable.progress_bar_blue));
        progressBar3.setProgress((int) Float.parseFloat(String.valueOf(servaydata.get("perC"))));
        progressBar3.setMax(100);
        pertileC.setText(String.format("%.2f", servaydata.get("perC")) + "%");
        if (polldata.getOption3().equalsIgnoreCase("")) {
            typeC.setVisibility(View.GONE);
        } else {
            typeC.setVisibility(View.VISIBLE);
        }

        TextView typed = typeD.findViewById(R.id.options);
        TextView txtOptionNumberd = typeD.findViewById(R.id.txtOptionNumber);
        typed.setText(Html.fromHtml(polldata.getOption4()));
        txtOptionNumberd.setText("D");
        progressBar4 = typeD.findViewById(R.id.progresscolor);
        TextView pertileD = typeD.findViewById(R.id.percenttile);
//            progressBar4.setProgressDrawable(con.getResources().getDrawable(R.drawable.progress_bar_grey));
        progressBar4.setProgress((int) Float.parseFloat(String.valueOf(servaydata.get("perD"))));
        progressBar4.setMax(100);
        pertileD.setText(String.format("%.2f", servaydata.get("perD")) + "%");
        if (polldata.getOption4().equalsIgnoreCase("")) {
            typeD.setVisibility(View.GONE);
        } else {
            typeD.setVisibility(View.VISIBLE);
        }
        if (polldata.getAnswer().equalsIgnoreCase(polldata.getMyAnswer())) {
            showpollresult(polldata.getAnswer(), "");
        } else if (polldata.getAnswer().equalsIgnoreCase("0")) {
            showres();
        } else {
            showpollresult(polldata.getMyAnswer(), polldata.getAnswer());
        }


        if (!watchlist.isShowing()) {
            watchlist.show();
        }
    }

    public void notifyadap(Viewholder holder, int position) {
        if (!polldata.get(position).getAnswer().equalsIgnoreCase("0")) {
            if (polldata.get(position).getDisable_result().equalsIgnoreCase("0")) {
                holder.takeAPollBtn.setText("Result");
//                holder.takeAPollBtn.setEnabled(true);
            } else {
                holder.takeAPollBtn.setText("Expired");
            }
        } else {
            if (polldata.get(position).getDisable_result().equalsIgnoreCase("0")) {
                holder.takeAPollBtn.setText("Survey Result");
            } else {
                holder.takeAPollBtn.setText("Expired");
            }
        }
    }
}