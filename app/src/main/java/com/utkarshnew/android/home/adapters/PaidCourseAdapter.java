package com.utkarshnew.android.home.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.razorpay.Checkout;
import com.utkarshnew.android.courses.Activity.CourseActivity;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.home.Activity.MyLibraryActivty;
import com.utkarshnew.android.home.interfaces.onButtonClicked;
import com.utkarshnew.android.Model.Courselist;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.table.VideosDownload;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;
import com.utkarshnew.android.Utils.SharedPreference;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;

import static com.utkarshnew.android.home.Activity.MyLibraryActivty.razorkey;

public class PaidCourseAdapter extends RecyclerView.Adapter<PaidCourseAdapter.MyViewHolder> implements NetworkCall.MyNetworkCallBack {

    private List<Courselist> courselists;
    private Context context;
    private Activity activity;
    int containerWidth = 0;
    @SuppressLint("StaticFieldLeak")
    static BottomSheetDialog watchlist;
    public String contentType;
    int delet_pos = 0;
    onButtonClicked buttonClicked;
    int server_time;
    private ExtendAdapter extendAdapter;
    public static Courselist course;
    public static String pre_transaction_id = "";
    private String price = "", id = "", post_txt = "";
    UtkashRoom utkashRoom;

    public PaidCourseAdapter(Context context, List<Courselist> courselists, int server_time) {
        this.courselists = courselists;
        this.context = context;
        this.activity = (Activity) context;
        this.server_time = Integer.parseInt(String.valueOf(MakeMyExam.getTime_server() / 1000));
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.remove_course:
                EncryptionData encryptionData = new EncryptionData();
                encryptionData.setCourse_id(course.getId());
                encryptionData.setTxn_id(course.getTxn_id());
                String remove_course = new Gson().toJson(encryptionData);
                String remove_course_encrypt = AES.encrypt(remove_course);
                return service.remove_course(remove_course_encrypt);

            case API.int_payment:
                if (post_txt.equalsIgnoreCase("")) {
                    EncryptionData getcoursedataencryptionData = new EncryptionData();
                    getcoursedataencryptionData.setType("3");
                    getcoursedataencryptionData.setCourse_id(course.getId());
                    getcoursedataencryptionData.setPay_via("3");
                    getcoursedataencryptionData.setTxn_id(course.getTxn_id());
                    getcoursedataencryptionData.setExtender_id(id);

                    getcoursedataencryptionData.setDelivery_price("0");
                    getcoursedataencryptionData.setPurchase_type("0");
                    getcoursedataencryptionData.setAddress_id("0");

                    String getcoursedataencryptionDatadoseStr = new Gson().toJson(getcoursedataencryptionData);
                    String getcoursedatadoseStrScr = AES.encrypt(getcoursedataencryptionDatadoseStr);
                    return service.int_payment(getcoursedatadoseStrScr);
                } else {
                    if (post_txt.contains("~!@#$%^&")) {
                        post_txt = "";
                        EncryptionData getcoursedataencryptionData = new EncryptionData();
                        getcoursedataencryptionData.setPre_transaction_id(pre_transaction_id);
                        getcoursedataencryptionData.setTransaction_status("2");
                        getcoursedataencryptionData.setPost_transaction_id("");
                        getcoursedataencryptionData.setCourse_id(course.getId());
                        getcoursedataencryptionData.setType("4");

                        getcoursedataencryptionData.setDelivery_price("0");
                        getcoursedataencryptionData.setPurchase_type("0");
                        getcoursedataencryptionData.setAddress_id("0");
                        getcoursedataencryptionData.setTxn_id(course.getTxn_id());
                        String getcoursedataencryptionDatadoseStr = new Gson().toJson(getcoursedataencryptionData);
                        String getcoursedatadoseStrScr = AES.encrypt(getcoursedataencryptionDatadoseStr);
                        return service.int_payment(getcoursedatadoseStrScr);
                    } else {
                        EncryptionData getcoursedataencryptionData = new EncryptionData();
                        getcoursedataencryptionData.setPre_transaction_id(pre_transaction_id);
                        getcoursedataencryptionData.setTransaction_status("1");
                        getcoursedataencryptionData.setPost_transaction_id(post_txt);
                        getcoursedataencryptionData.setCourse_id(course.getId());
                        getcoursedataencryptionData.setType("4");

                        getcoursedataencryptionData.setDelivery_price("0");
                        getcoursedataencryptionData.setPurchase_type("0");
                        getcoursedataencryptionData.setAddress_id("0");
                        getcoursedataencryptionData.setTxn_id(course.getTxn_id());
                        String getcoursedataencryptionDatadoseStr = new Gson().toJson(getcoursedataencryptionData);
                        String getcoursedatadoseStrScr = AES.encrypt(getcoursedataencryptionDatadoseStr);
                        return service.int_payment(getcoursedatadoseStrScr);
                    }

                }

        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonstring, String apitype, String typeApi, boolean showprogress) throws JSONException {

        switch (apitype) {
            case API.remove_course:
                try {
                    if (jsonstring.optString(Const.STATUS).equals(Const.TRUE)) {


                        utkashRoom.getuserhistorydao().delete(PaidCourseAdapter.course.getId() + "#", MakeMyExam.userId);
                        List<VideosDownload> videosDownloads = utkashRoom.getvideoDownloadao().getallcourse_id(PaidCourseAdapter.course.getId() + "#", MakeMyExam.userId);
                        if (videosDownloads != null && videosDownloads.size() > 0) {
                            for (VideosDownload videosDownload : videosDownloads) {


                              /*  EncryptionData encryptionData =new EncryptionData();
                                encryptionData.setCourse_id(videosDownload.getCourse_id());
                                encryptionData.setVideo_id(videosDownload.getVideo_id());
                                encryptionData.setUser_id(MakeMyExam.userId);
                                encryptionData.setServercourseid(PaidCourseAdapter.course.getId());
                                encryptionData.setType("inMyLibrarysection_mannualdelete");
                                ((MyLibraryActivty)activity).firebaseDatabase.push().setValue(new Gson().toJson(encryptionData));

*/

                                File file = new File(activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.Videos/" + videosDownload.getVideo_history() + ".mp4");
                                File file1 = new File(activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.processing/" + videosDownload.getVideo_history() + ".mp4");
                                if (file.exists()) {
                                    file.delete();
                                }
                                if (file1.exists()) {
                                    file1.delete();
                                }
                                utkashRoom.getvideoDownloadao().delete(videosDownload.getVideo_id(), videosDownload.getCourse_id(), MakeMyExam.userId);
                            }
                        }

                        ((MyLibraryActivty) activity).myDBClass.getMyCourseDao().delete(PaidCourseAdapter.course.getId(), PaidCourseAdapter.course.getTxn_id());
                        courselists.remove(delet_pos);
                        utkashRoom.getCourseDetaildata().deletecoursedetail(PaidCourseAdapter.course.getId(), MakeMyExam.userId);
                        notifyItemRemoved(delet_pos);
                        if (courselists.size() == 0) {
                            notifyDataSetChanged();
                        } else {
                            notifyItemRangeChanged(delet_pos, courselists.size());
                        }

                    } else {
                        RetrofitResponse.GetApiData(context, jsonstring.has(Const.AUTH_CODE) ? jsonstring.getString(Const.AUTH_CODE) : "", jsonstring.getString(Const.MESSAGE), false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case API.int_payment:
                try {
                    if (jsonstring.optString(Const.STATUS).equals(Const.TRUE)) {
                        if (post_txt.equalsIgnoreCase("")) {
                            JSONObject data = jsonstring.getJSONObject(Const.DATA);
                            pre_transaction_id = data.optString("pre_transaction_id");
                            if (!razorkey.equalsIgnoreCase("")) {
                                launch_paymentGateway(pre_transaction_id, price);
                            }
                        } else {
                            if (post_txt.contains("~!@#$%^&")) {
                                post_txt = "";
                            } else
                                Toast.makeText(context, "" + jsonstring.optString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
                            ((MyLibraryActivty) context).finish();
                        }

                    } else {
                        RetrofitResponse.GetApiData(context, jsonstring.has(Const.AUTH_CODE) ? jsonstring.getString(Const.AUTH_CODE) : "", jsonstring.getString(Const.MESSAGE), false);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
    }

    private void launch_paymentGateway(String preTransactionId, String price) {

        Checkout checkout = new Checkout();
        checkout.setKeyID(razorkey);
        ;
        checkout.setImage(R.mipmap.ic_launcher);
        JSONObject object = new JSONObject();
        try {
            object.put("name", "Utkarsh Classes and Edutech Pvt. Ltd.");
            object.put("theme.color", "#FED500");
            object.put("description", course.getTitle() + " #(" + course.getId() + "~" + id + ")");
            object.put("currency", "INR");
            object.put("amount", Math.round(Float.parseFloat(price) * 100));
            object.put("image", course.getDescHeaderImage());
            object.put("order_id", pre_transaction_id);
            JSONObject ReadOnly = new JSONObject();
            ReadOnly.put("email", "true");
            ReadOnly.put("contact", "true");
            object.put("readonly", ReadOnly);

            JSONObject preFill = new JSONObject();
            preFill.put("email", SharedPreference.getInstance().getLoggedInUser().getEmail());
            preFill.put("contact", SharedPreference.getInstance().getLoggedInUser().getMobile());
            object.put("prefill", preFill);

            checkout.open(((MyLibraryActivty) context), object);
        } catch (Exception e) {
            e.toString();
        }

    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {

    }

    public void update_payment(String pot_txt_id) {
        dismissCalculatorDialog(watchlist);
        post_txt = pot_txt_id;
        NetworkCall networkCall = new NetworkCall(this, context);
        networkCall.NetworkAPICall(API.int_payment, "", true, false);
    }

    public void notifidata(List<Courselist> courselists) {
        this.courselists = courselists;
        notifyDataSetChanged();
    }

    public void notifidata(List<Courselist> courselists, String type) {
        if (type.equalsIgnoreCase("Last Read")) {
            Collections.reverse(courselists);
        }
        this.courselists = courselists;
        notifyDataSetChanged();
    }

    public void filterList(ArrayList<Courselist> courselists) {
        this.courselists = courselists;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout mycourse_layout, course__validty, progess_layout, days_layout;
        TextView course_name,order_id, alert_text, start_date, progrees_value_text, extend_validy, total_days, remaing_days;
        ImageView ibt_single_vd_iv, delete;
        ProgressBar progress_value;

        public MyViewHolder(View view) {
            super(view);
            course_name = view.findViewById(R.id.course_name);
            order_id = view.findViewById(R.id.order_id);
            course__validty = view.findViewById(R.id.course__validty);
            progess_layout = view.findViewById(R.id.progess_layout);
            days_layout = view.findViewById(R.id.days_layout);
            ibt_single_vd_iv = view.findViewById(R.id.ibt_single_vd_iv);
            start_date = view.findViewById(R.id.start_date);
            remaing_days = view.findViewById(R.id.remaing_days);
            total_days = view.findViewById(R.id.total_days);
            progress_value = view.findViewById(R.id.progress_value);
            progrees_value_text = view.findViewById(R.id.progrees_value_text);
            alert_text = view.findViewById(R.id.alert_text);
            extend_validy = view.findViewById(R.id.extend_validy);
            delete = view.findViewById(R.id.delete);
            mycourse_layout = view.findViewById(R.id.mycourse_layout);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        utkashRoom = UtkashRoom.getAppDatabase(activity);
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.paid_course_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NotNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        try {
            holder.course_name.setText(courselists.get(holder.getAdapterPosition()).getTitle());
            if (courselists.get(holder.getAdapterPosition()).getTxn_id()==null || courselists.get(holder.getAdapterPosition()).getTxn_id().equalsIgnoreCase(""))
            {
                holder.order_id.setVisibility(View.GONE);
            }else {
                holder.order_id.setVisibility(View.VISIBLE);
                holder.order_id.setText("UID:- "+courselists.get(holder.getAdapterPosition()).getTxn_id());

            }
            holder.start_date.setText(TextUtils.isEmpty(courselists.get(holder.getAdapterPosition()).getPurchase_date()) ? "Added on: N/A" : "Added on: " + Helper.changeAMPM(new SimpleDateFormat("dd MMM yyyy").format(new Date(Long.parseLong(courselists.get(holder.getAdapterPosition()).getPurchase_date()) * 1000))));
            holder.alert_text.setVisibility(View.GONE);

            if (courselists.get(holder.getAdapterPosition()).getDelete() == 1) {
                holder.delete.setVisibility(View.VISIBLE);
            } else {
                holder.delete.setVisibility(View.GONE);
            }

            if (Integer.parseInt(courselists.get(holder.getAdapterPosition()).getExpiry_date()) != 0) {
                if (Integer.parseInt(courselists.get(holder.getAdapterPosition()).getExpiry_date()) > Integer.parseInt(courselists.get(holder.getAdapterPosition()).getPurchase_date())) {
                    int toal_course_actual_days = (Integer.parseInt(courselists.get(holder.getAdapterPosition()).getExpiry_date()) - Integer.parseInt(courselists.get(holder.getAdapterPosition()).getPurchase_date())) / 86400;

                    if (server_time >= (Integer.parseInt(courselists.get(holder.getAdapterPosition()).getPurchase_date()))) {
                        holder.progess_layout.setVisibility(View.VISIBLE);
                        holder.course__validty.setVisibility(View.VISIBLE);
                        holder.progrees_value_text.setVisibility(View.VISIBLE);


                        holder.total_days.setText("Total Days : " + toal_course_actual_days + " Days");
                        int course_use_time_days = ((server_time - Integer.parseInt(courselists.get(holder.getAdapterPosition()).getPurchase_date())) / 86400);
                        holder.remaing_days.setText("Remaining Days : " + (toal_course_actual_days - course_use_time_days));
                        if (toal_course_actual_days != 0) {

                            if (((course_use_time_days * 100) / toal_course_actual_days) > 80 && ((course_use_time_days * 100) / toal_course_actual_days) < 90) {
                                holder.mycourse_layout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.round_mycourse_remaining));
                                holder.progress_value.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_drawable_remaining));
                            } else if (((course_use_time_days * 100) / toal_course_actual_days) > 90) {
                                holder.mycourse_layout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.round_mycourse));
                                holder.progress_value.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_drawable_red));
                            } else {
                                holder.mycourse_layout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.round_mycourse));
                                holder.progress_value.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_drawable));
                            }
                            holder.progress_value.setProgress(((course_use_time_days * 100) / toal_course_actual_days));
                            holder.progrees_value_text.setText("" + ((course_use_time_days * 100) / toal_course_actual_days) + "% Days Over");


                            if (courselists.get(holder.getAdapterPosition()).getPrices() != null && courselists.get(holder.getAdapterPosition()).getPrices().size() > 0) {
                                holder.alert_text.setVisibility(View.VISIBLE);
                                int validate_percenatge = ((course_use_time_days * 100) / toal_course_actual_days);
                                if (validate_percenatge < 80) {
                                    holder.extend_validy.setBackground(context.getResources().getDrawable(R.drawable.btn_back_with_ripple));

                                } else if (validate_percenatge > 80 && validate_percenatge < 90) {
                                    holder.extend_validy.setBackground(context.getResources().getDrawable(R.drawable.orange_extend));

                                } else {
                                    holder.extend_validy.setBackground(context.getResources().getDrawable(R.drawable.range_extend));
                                }
                                holder.extend_validy.setVisibility(View.VISIBLE);

                            } else {
                                holder.extend_validy.setVisibility(View.GONE);
                            }
                        } else {
                            holder.alert_text.setVisibility(View.GONE);
                            holder.extend_validy.setVisibility(View.GONE);
                        }
                    }
                }
            } else {
                holder.progess_layout.setVisibility(View.GONE);
                holder.course__validty.setVisibility(View.GONE);
                holder.progrees_value_text.setVisibility(View.GONE);
            }

            Glide.with(context.getApplicationContext())
                    .load(courselists.get(holder.getAdapterPosition()).getCover_image().replaceAll(" ", "%20"))
                    .apply(new RequestOptions().placeholder(R.drawable.book_logo).error(R.drawable.book_logo).diskCacheStrategy(DiskCacheStrategy.DATA).dontAnimate())
                    .into(Objects.requireNonNull(holder.ibt_single_vd_iv));

            holder.mycourse_layout.setOnClickListener(new OnSingleClickListener(() -> {
                if (TextUtils.isEmpty(courselists.get(holder.getAdapterPosition()).getMaintenanceText())) {
                    courselists.get(holder.getAdapterPosition()).setLastread("" + MakeMyExam.getTime_server());
                    ((MyLibraryActivty) context).myDBClass.getMyCourseDao().update_course_lastread("" + MakeMyExam.getTime_server(), courselists.get(holder.getAdapterPosition()).getId(), MakeMyExam.userId);
                    Bundle bundle = new Bundle();
                    bundle.putString(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                    bundle.putString(Const.COURSE_ID_MAIN, courselists.get(holder.getAdapterPosition()).getId());
                    bundle.putString("valid_to", courselists.get(holder.getAdapterPosition()).getExpiry_date());
                    bundle.putString(Const.COURSE_PARENT_ID, "");
                    bundle.putBoolean(Const.IS_COMBO, false);
                    bundle.putString("course_name", courselists.get(holder.getAdapterPosition()).getTitle());
                    Helper.gotoActivityWithBundle(activity, CourseActivity.class, bundle);


                } else {
                    Helper.getCourseMaintanaceDialog((Activity) context, "", courselists.get(holder.getAdapterPosition()).getMaintenanceText());
                }
                return null;
            }));

            holder.order_id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager cm = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText(courselists.get(position).getTxn_id());
                    Toast.makeText(activity, "UID Copied", Toast.LENGTH_SHORT).show();
                }
            });

            holder.extend_validy.setOnClickListener(new OnSingleClickListener(() -> {
                if (courselists.get(holder.getAdapterPosition()).getPrices() != null && courselists.get(holder.getAdapterPosition()).getPrices().size() > 0) {
                    openwatchlist_dailog_resource(context, courselists.get(holder.getAdapterPosition()));
                }
                return null;
            }));

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delet_pos = position;
                    alert_dialog();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void alert_dialog() {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle("Delete Course");
        alertDialogBuilder.setMessage("Are you sure,You want to delete the free course ?");
        alertDialogBuilder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        alertDialogBuilder.setPositiveButton("Yes", (dialog, which) -> {
            delete_free_course(courselists.get(delet_pos));
            dialog.dismiss();
            dialog.cancel();
        });
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void delete_free_course(Courselist course) {

        PaidCourseAdapter.course = course;
        NetworkCall networkCall = new NetworkCall(this, context);
        networkCall.NetworkAPICall(API.remove_course, "", true, false);

    }

    public void openwatchlist_dailog_resource(Context context, Courselist course) {
        try {
            for (int j = 0; j < course.getPrices().size(); j++) {
                course.getPrices().get(j).setIs_select(false);
            }
            watchlist = new BottomSheetDialog(context, R.style.videosheetDialogTheme);
            watchlist.setContentView(R.layout.top_up);
            Objects.requireNonNull(watchlist.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            watchlist.setCancelable(false);
            watchlist.setCanceledOnTouchOutside(true);
            ImageView ibt_single_vd_iv = watchlist.findViewById(R.id.ibt_single_vd_iv);
            TextView buy_now = watchlist.findViewById(R.id.buy_now);
            TextView cname = watchlist.findViewById(R.id.cname);
            RecyclerView recyclerView = watchlist.findViewById(R.id.recycler_view_validy);
            cname.setText(course.getTitle());

            if (course.getCover_image() != null) {
                Glide.with(context.getApplicationContext())
                        .load(course.getCover_image().replaceAll(" ", "%20"))
                        .apply(new RequestOptions().placeholder(R.mipmap.course_placeholder).error(R.mipmap.course_placeholder).diskCacheStrategy(DiskCacheStrategy.DATA).dontAnimate())
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(Objects.requireNonNull(ibt_single_vd_iv));
            }

            if (course.getPrices() != null) {
                if (course.getPrices().size() > 0) {
                    course.getPrices().get(0).setIs_select(true);
                    extendAdapter = new ExtendAdapter(context, course.getPrices(), watchlist);
                    Objects.requireNonNull(recyclerView).setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(extendAdapter);
                }
            }

            if (!watchlist.isShowing()) {
                watchlist.show();
            }


            Objects.requireNonNull(buy_now).setOnClickListener(v -> {
                String planselect = "";
                for (int j = 0; j < course.getPrices().size(); j++) {
                    if (course.getPrices().get(j).isIs_select()) {
                        planselect = "" + j;
                        break;
                    }
                }

                if (planselect.equalsIgnoreCase("")) {
                    Toast.makeText(context, "Select extend validity plan", Toast.LENGTH_SHORT).show();
                } else {
                    API_INIT_PAYMENT(course.getPrices().get(Integer.parseInt(planselect)).getId(), course.getPrices().get(Integer.parseInt(planselect)).getPrice(), course);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void API_INIT_PAYMENT(String id, String price, Courselist course) {
        this.course = course;
        this.price = price;
        this.id = id;
        NetworkCall networkCall = new NetworkCall(this, context);
        networkCall.NetworkAPICall(API.int_payment, "", true, false);

    }

    private void dismissCalculatorDialog(BottomSheetDialog watchlist) {
        if (watchlist != null && watchlist.isShowing()) {
            watchlist.dismiss();
            watchlist.cancel();
            watchlist = null;
        }
    }

    @Override
    public int getItemCount() {
        return courselists.size();
    }

}