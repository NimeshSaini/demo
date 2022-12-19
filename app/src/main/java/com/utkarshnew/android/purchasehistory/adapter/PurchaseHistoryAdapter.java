package com.utkarshnew.android.purchasehistory.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.razorpay.Checkout;
import com.utkarshnew.android.Webview.WebViewActivty;
import com.utkarshnew.android.courses.Interfaces.OnSuccessListner;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.purchasehistory.PurchaseHistoryDetail;
import com.utkarshnew.android.home.adapters.ExtendAdapter;
import com.utkarshnew.android.purchasehistory.model.Data;
import com.utkarshnew.android.purchasehistory.PurchaseHistory;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;
import com.utkarshnew.android.Utils.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;

import static com.utkarshnew.android.purchasehistory.PurchaseHistory.razorkey;

public class PurchaseHistoryAdapter extends RecyclerView.Adapter<PurchaseHistoryAdapter.Livetestviewholder> implements NetworkCall.MyNetworkCallBack, OnSuccessListner {
    Activity activity;
    ArrayList<Data> data;
    static BottomSheetDialog watchlist;
    private String price = "", id = "", post_txt = "", pre_transaction_id = "";
    public static String validty = "";
    Data course;
    private ExtendAdapter extendAdapter;
    public OnSuccessListner onSuccessListner;
    int server_time;
    ProgressDialog mProgressDialog;

    public PurchaseHistoryAdapter(Activity activity, ArrayList<Data> data, int server_time) {
        this.activity = activity;
        this.data = data;
        onSuccessListner = this;
        this.server_time = server_time;
    }


    @NonNull
    @Override
    public Livetestviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.purchase_history_adapter, parent, false);
        return new Livetestviewholder(itemView);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables", "SimpleDateFormat"})
    @Override
    public void onBindViewHolder(@NonNull Livetestviewholder holder, int position) {
        holder.title.setText(data.get(position).getTitle());

        holder.liveIV.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(data.get(position).getCover_image())) {

            Glide.with(activity.getApplicationContext())
                    .load(data.get(position).getCover_image().replaceAll(" ", "%20"))
                    .apply(new RequestOptions().placeholder(R.drawable.book_logo).error(R.drawable.book_logo).diskCacheStrategy(DiskCacheStrategy.DATA))
                    .into(Objects.requireNonNull(holder.courseImage));

        } else {
            holder.courseImage.setImageResource(R.drawable.book_logo);
        }

        if (data.get(position).getTrack_url_1()!=null &&  !data.get(position).getTrack_url_1().equals(""))
        {
            holder.track_url.setVisibility(View.VISIBLE);
        }else {
            holder.track_url.setVisibility(View.GONE);
        }

        holder.track_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(activity, WebViewActivty.class);
                intent4.putExtra("type", "Track");
                intent4.putExtra("url", data.get(position).getTrack_url_1());
                activity.startActivity(intent4);
            }
        });


        holder.purchase_date.setText(TextUtils.isEmpty(data.get(position).getPurchase_date()) ? "N/A" : "" + Helper.changeAMPM(new SimpleDateFormat("dd MMM yyyy ").format(new Date(Long.parseLong(data.get(position).getPurchase_date()) * 1000))));
        holder.expiry_date.setText(TextUtils.isEmpty(data.get(position).getExpiry_date()) ? "N/A" : "" + Helper.changeAMPM(new SimpleDateFormat("dd MMM yyyy ").format(new Date(Long.parseLong(data.get(position).getExpiry_date()) * 1000))));
        holder.expiry_date.setTypeface(Typeface.DEFAULT_BOLD);
        holder.purchase_date.setTypeface(Typeface.DEFAULT_BOLD);
        holder.orderIdTxt.setText(TextUtils.isEmpty(data.get(position).getTxn_id()) ? "N/A" : data.get(position).getTxn_id());

        holder.orderIdTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager)activity.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(data.get(position).getTxn_id());
                Toast.makeText(activity, "UID Copied", Toast.LENGTH_SHORT).show();
            }
        });


        if (TextUtils.isEmpty(data.get(position).getMrp()) || data.get(position).getMrp().equalsIgnoreCase("0")) {
            holder.paidLabelTxt.setVisibility(View.GONE);
            holder.paidAmountTxt.setText("Free Course");
        } else {
            holder.paidLabelTxt.setVisibility(View.VISIBLE);
            holder.paidAmountTxt.setText(String.format("%s %s %s", activity.getResources().getString(R.string.rs), "" + data.get(position).getMrp(), "/-"));
        }

        if (TextUtils.isEmpty(data.get(position).getMrp()) || data.get(position).getMrp().equalsIgnoreCase("0")) {
            holder.payment_layout.setVisibility(View.GONE);
        } else {
            holder.payment_layout.setVisibility(View.VISIBLE);
            holder.paymentIdTxt.setText(TextUtils.isEmpty(data.get(position).getPayment_id()) ? "N/A" : data.get(position).getPayment_id());
        }

        if (server_time > Integer.parseInt(data.get(position).getExpiry_date())) {
            holder.transer_text.setVisibility(View.VISIBLE);
            holder.transer_text.setText("EXPIRED");
            holder.extend_validy.setVisibility(View.GONE);

        } else if (Integer.parseInt(data.get(position).getExpiry_date()) > Integer.parseInt(data.get(position).getPurchase_date())) {
            int toal_course_actual_days = (Integer.parseInt(data.get(position).getExpiry_date()) - Integer.parseInt(data.get(position).getPurchase_date())) / 86400;

            if (server_time >= (Integer.parseInt(data.get(position).getPurchase_date()))) {
                int course_use_time_days = ((server_time - Integer.parseInt(data.get(position).getPurchase_date())) / 86400);

                if (data.get(position).getTransaction_status().equalsIgnoreCase("1")) {

                    holder.transer_text.setVisibility(View.GONE);

                    if (data.get(position).getPrices() != null && data.get(position).getPrices().size() > 0) {
                        if (toal_course_actual_days != 0) {
                            int validate_percenatge = ((course_use_time_days * 100) / toal_course_actual_days);
                            if (validate_percenatge < 80) {
                                holder.extend_validy.setBackground(activity.getResources().getDrawable(R.drawable.btn_back_with_ripple));

                            } else if (validate_percenatge > 80 && validate_percenatge < 90) {
                                holder.extend_validy.setBackground(activity.getResources().getDrawable(R.drawable.orange_extend));

                            } else {
                                holder.extend_validy.setBackground(activity.getResources().getDrawable(R.drawable.range_extend));
                            }
                            holder.extend_validy.setVisibility(View.VISIBLE);

                        } else {
                            holder.extend_validy.setVisibility(View.GONE);
                        }

                    } else {
                        holder.extend_validy.setVisibility(View.GONE);
                    }
                }else if (data.get(position).getTransaction_status().equalsIgnoreCase("4")) {

                    holder.transer_text.setVisibility(View.VISIBLE);
                    holder.transer_text.setText("REFUNDED");
                    holder.extend_validy.setVisibility(View.GONE);
                }
                else {
                    holder.transer_text.setVisibility(View.VISIBLE);
                    holder.extend_validy.setVisibility(View.GONE);

                }

            }
        }

/*
        if (data.get(position).getPrices() != null && data.get(position).getPrices().size() > 0) {
            holder.extend_validy.setVisibility(View.VISIBLE);
        } else {
            holder.extend_validy.setVisibility(View.GONE);
        }*/

        holder.details_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(activity, PurchaseHistoryDetail.class);
                intent.putExtra("data",data.get(position));
                Helper.gotoActivity(intent,activity);
            }
        });
        holder.more.setOnClickListener(v -> {
            course = data.get(position);
            final PopupMenu popup = new PopupMenu(activity, holder.more);
            if (course.getInvoice_url()!=null&& !course.getInvoice_url().equals(""))
            {
                popup.inflate(R.menu.history_options_menu_invoice);
            }else {
                popup.inflate(R.menu.history_options_menu);
            }

            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.helpOption:
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", activity.getResources().getString(R.string.support_email), null));
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, data.get(position).getTitle());
                        activity.startActivity(Intent.createChooser(emailIntent, "Send email..."));
                        break;
                    case R.id.downloadoptipon:

                        Helper.GoToWebViewPDFActivity(activity, course.getId(), course.getInvoice_url(), true, course.getTitle(),  course.getId()+"#"+course.getId());
                        break;
                }
                return false;
            });
            popup.show();
        });


        holder.extend_validy.setOnClickListener(v -> {

            if (data.get(position).getPrices() != null && data.get(position).getPrices().size() > 0 && data.get(position).getTransaction_status().equalsIgnoreCase("1")) {
                openwatchlist_dailog_resource(activity, data.get(position));
            } else {
                Snackbar.make(holder.extend_validy.getRootView(), "Course Has been trasfer", Snackbar.LENGTH_LONG).show();

            }


        });

    }
    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private final Context context;

        public DownloadTask(Context context) {
            this.context = context;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mProgressDialog.dismiss();
            if (result != null)
                Toast.makeText(context,"Download error: "+result, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context,"File downloaded", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0].replaceAll(" ", "%20"));
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                String fileName = url.toString().substring(url.toString().lastIndexOf('/') + 1);

                output = new FileOutputStream(context.getFilesDir() +"/"+fileName);

                byte[] data = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }
    }

    public void openwatchlist_dailog_resource(Context context, Data course) {
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
                    API_INIT_PAYMENT(course.getPrices().get(Integer.parseInt(planselect)).getValidity(), course.getPrices().get(Integer.parseInt(planselect)).getId(), course.getPrices().get(Integer.parseInt(planselect)).getPrice(), course);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void API_INIT_PAYMENT(String validty, String id, String price, Data course) {
        this.course = course;
        this.price = price;
        PurchaseHistoryAdapter.validty = validty;
        this.id = id;
        NetworkCall networkCall = new NetworkCall(this, activity);
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
        return data.size();
    }


    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
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
            case API.int_payment:
                try {
                    if (jsonstring.optString(Const.STATUS).equals(Const.TRUE)) {
                        server_time = Integer.parseInt(jsonstring.optString("time"));
                        if (post_txt.equalsIgnoreCase("")) {
                            JSONObject data = jsonstring.getJSONObject(Const.DATA);
                            pre_transaction_id = data.optString("pre_transaction_id");
                            launch_paymentGateway();
                        } else {
                            if (post_txt.contains("~!@#$%^&")) {
                                post_txt = "";
                                Toast.makeText(activity, "" + jsonstring.optString(Const.MESSAGE), Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(activity, "" + jsonstring.optString(Const.MESSAGE), Toast.LENGTH_SHORT).show();

                                ((PurchaseHistory) activity).refreshdata();
                            }

                        }
                    } else {
                        RetrofitResponse.GetApiData(activity, jsonstring.has(Const.AUTH_CODE) ? jsonstring.getString(Const.AUTH_CODE) : "", jsonstring.getString(Const.MESSAGE), false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
    }

    private void launch_paymentGateway() {

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
            object.put("image", course.getCover_image());
            object.put("order_id", pre_transaction_id);
            JSONObject ReadOnly = new JSONObject();
            ReadOnly.put("email", "true");
            ReadOnly.put("contact", "true");
            object.put("readonly", ReadOnly);

            JSONObject preFill = new JSONObject();
            preFill.put("email", SharedPreference.getInstance().getLoggedInUser().getEmail());
            preFill.put("contact", SharedPreference.getInstance().getLoggedInUser().getMobile());
            object.put("prefill", preFill);

            checkout.open(activity, object);
        } catch (Exception e) {
            e.toString();
        }

    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {

    }

    @Override
    public void onSuccess(String pot_txt_id) {
        update_payment(pot_txt_id);
    }

    @Override
    public void onFailure(String pot_txt_id) {
        update_payment(pot_txt_id);

    }

    public void update_payment(String pot_txt_id) {

        dismissCalculatorDialog(watchlist);
        post_txt = pot_txt_id;
        NetworkCall networkCall = new NetworkCall(this, activity);
        networkCall.NetworkAPICall(API.int_payment, "", true, false);
    }

    public void change_time(int server_time) {
        this.server_time = server_time;
    }

    public class Livetestviewholder extends RecyclerView.ViewHolder {
        ImageView courseImage;
        GifImageView liveIV;
        ImageView forward;
        ImageView more;
        LinearLayout details_layout;
        TextView title, purchase_date, expiry_date, extend_validy, transer_text,track_url;
        TextView orderIdTxt, paymentIdTxt, paidAmountTxt, paidLabelTxt;
        RelativeLayout study_single_itemLL, payment_layout;

        public Livetestviewholder(View itemView) {
            super(itemView);
            courseImage = itemView.findViewById(R.id.courseImage);
            liveIV = itemView.findViewById(R.id.liveIV);
            paidLabelTxt = itemView.findViewById(R.id.paidLabelTxt);
            forward = itemView.findViewById(R.id.forwardIV);
            track_url = itemView.findViewById(R.id.track_url);
            more = itemView.findViewById(R.id.more);
            details_layout = itemView.findViewById(R.id.details_layout);
            title = itemView.findViewById(R.id.study_item_titleTV);
            study_single_itemLL = itemView.findViewById(R.id.study_single_itemLL);
            purchase_date = itemView.findViewById(R.id.purchase_date);
            transer_text = itemView.findViewById(R.id.transer_text);
            extend_validy = itemView.findViewById(R.id.extend_validy);
            expiry_date = itemView.findViewById(R.id.expiry_date);
            orderIdTxt = itemView.findViewById(R.id.orderIdTxt);
            paymentIdTxt = itemView.findViewById(R.id.paymentIdTxt);
            paidAmountTxt = itemView.findViewById(R.id.paidAmountTxt);
            payment_layout = itemView.findViewById(R.id.payment_layout);
            //forward.setVisibility(View.VISIBLE);
        }
    }

}
