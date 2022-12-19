package com.utkarshnew.android.Coupon.Activity;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.appevents.AppEventsLogger;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.utkarshnew.android.Coupon.Models.CoursesCoupon;
import com.utkarshnew.android.courses.Activity.CourseActivity;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;
import com.utkarshnew.android.Utils.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;

public class CouponPurchaseActivity extends AppCompatActivity implements NetworkCall.MyNetworkCallBack, PaymentResultListener {

    RoundedImageView imageIV;
    long mLastClickTime_frame5;
    ImageView img_back;
    TextView coursenameTV, txtPriceValue, coupon_applied, txtGrandTotalValue, tax_value, txtTaxValue, validityTV, txtAmountValue, txtPricesValue;
    String pos_txn_id = "", pre_txtid = "", tx_status = "0";
    boolean isfailure = false;
    Button procceed;
    String discount, id;
    NetworkCall networkCall;
    String razorKey = "";
    CoursesCoupon coursesCoupon;
    AppEventsLogger logger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        logger = AppEventsLogger.newLogger(this);
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.activity_coupon_purchase);
        networkCall = new NetworkCall(this, this);
        getIntentData();
        initViews();

        if (SharedPreference.getInstance().getString("key") != null && !SharedPreference.getInstance().getString("key").equalsIgnoreCase("")) {
            razorKey = SharedPreference.getInstance().getString("key");
        } else {
            networkCall.NetworkAPICall(API.payment_gateway_credentials, "", true, false);
        }
    }

    private void getIntentData() {
        if (getIntent() != null) {
            if (getIntent().hasExtra(Const.COURSE_DATA)) {
                coursesCoupon = (CoursesCoupon) getIntent().getSerializableExtra(Const.COURSE_DATA);
                discount = getIntent().getStringExtra(Const.DISCOUNT);
                id = getIntent().getStringExtra(Const.ID);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void initViews() {
        imageIV = findViewById(R.id.imageIV);
        coursenameTV = findViewById(R.id.coursenameTV);
        txtPriceValue = findViewById(R.id.txtPriceValue);
        txtGrandTotalValue = findViewById(R.id.txtGrandTotalValue);
        coupon_applied = findViewById(R.id.coupon_applied);
        tax_value = findViewById(R.id.tax_value);
        procceed = findViewById(R.id.procceed);
        img_back = findViewById(R.id.image_back);
        txtTaxValue = findViewById(R.id.txtTaxValue);
        validityTV = findViewById(R.id.validityTV);
        txtAmountValue = findViewById(R.id.txtAmountValue);
        txtPricesValue = findViewById(R.id.txtPricesValue);

        Glide.with(this)
                .load(coursesCoupon.getCover_image())
                .apply(new RequestOptions().placeholder(R.mipmap.course_placeholder).error(R.mipmap.course_placeholder))
                .into(imageIV);
        coursenameTV.setText(coursesCoupon.getTitle());

        validityTV.setText("Validity: " + coursesCoupon.getValidity());
        if (!discount.contains("INR"))
            coupon_applied.setText("Coupon Applied (" + discount.split(" ")[0] + " " + discount.split(" ")[1] + ")");
        else coupon_applied.setText("Coupon Applied (" + discount + ")");
        tax_value.setText("- \u20B9 " + Float.parseFloat(coursesCoupon.getDiscount()));


        float a = Float.parseFloat(coursesCoupon.getMrp());
        float b = Float.parseFloat(coursesCoupon.getTax());
        float d = Float.parseFloat(coursesCoupon.getDiscount());
        float c = a + b + d;
        float e = a + b;

        txtPriceValue.setText("\u20B9 " + c);
        txtAmountValue.setText("\u20B9 " + e);


        float fTaxValue = Float.parseFloat(coursesCoupon.getDiscount());

        if (fTaxValue >= c) {
            txtPricesValue.setText("\u20B9 0");
            txtTaxValue.setText("\u20B9 0");
            txtGrandTotalValue.setText("\u20B9 0");
        } else {
            txtTaxValue.setText("+ \u20B9 " + Float.parseFloat(coursesCoupon.getTax()));
            txtPricesValue.setText("\u20B9 " + Float.parseFloat(coursesCoupon.getMrp()));
            txtGrandTotalValue.setText("\u20B9 " + Float.parseFloat(coursesCoupon.getFinal_mrp()));
        }


        float flaotvalue = Float.parseFloat(coursesCoupon.getFinal_mrp());
        if ((int) flaotvalue <= 0) {
            procceed.setText(getResources().getString(R.string.open_in_my_lib));
        } else {
            procceed.setText("Proceed");
        }

        procceed.setOnClickListener(new OnSingleClickListener(() -> {
            if (procceed.getText().toString().equalsIgnoreCase("Proceed")) {
                networkCall.NetworkAPICall(API.int_payment, "", true, false);
            } else {
                networkCall.NetworkAPICall(API.free_transaction, "", true, false);
            }
            //networkCall.NetworkAPICall(API.int_payment, "", true, false);
            return null;
        }));

        img_back.setOnClickListener(new OnSingleClickListener(() -> {
            finish();
            return null;
        }));
    }

    @Override
    public void onPaymentSuccess(String s) {
        pos_txn_id = s;
        // NetworkCall networkCall = new NetworkCall(PurchaseActivity.this, PurchaseActivity.this);
        networkCall.NetworkAPICall(API.int_payment, "", true, false);

    }

    @Override
    public void onPaymentError(int i, String s) {
        tx_status = "" + i;
        isfailure = true;
        networkCall.NetworkAPICall(API.int_payment, "", true, false);

    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {

        switch (apitype) {
            case API.payment_gateway_credentials:
                EncryptionData encryptionData1 = new EncryptionData();
                encryptionData1.setUser_id(MakeMyExam.userId);

                String datajson = new Gson().toJson(encryptionData1);
                String getweay = AES.encrypt(datajson);
                return service.payment_gateway_credentials(getweay);

            case API.free_transaction:
                EncryptionData encryptionData = new EncryptionData();
                encryptionData.setCourse_id(coursesCoupon.getId());
                encryptionData.setCoupon_applied(id);
                encryptionData.setParent_id("0");
                String data = new Gson().toJson(encryptionData);
                String free = AES.encrypt(data);
                return service.free_transaction(free);

            case API.int_payment:
                if (isfailure) {
                    EncryptionData complete = new EncryptionData();
                    complete.setType("2");
                    complete.setCourse_id(coursesCoupon.getId());
                    complete.setParent_id("0");
                    complete.setPre_transaction_id(pre_txtid);
                    complete.setTransaction_status("2");
                    complete.setPost_transaction_id("");
                    complete.setCoupon_applied(id);
                    String completedoseStr = new Gson().toJson(complete);
                    String conpelete_response = AES.encrypt(completedoseStr);
                    return service.int_payment(conpelete_response);
                } else {
                    if (pos_txn_id.equalsIgnoreCase("")) {
                        EncryptionData getcoursedataencryptionData = new EncryptionData();
                        getcoursedataencryptionData.setType("1");
                        getcoursedataencryptionData.setCourse_id(coursesCoupon.getId());
                        getcoursedataencryptionData.setCourse_price("" + Float.parseFloat(coursesCoupon.getMrp()));
                        getcoursedataencryptionData.setParent_id("0");
                        getcoursedataencryptionData.setTax("" + Float.parseFloat(coursesCoupon.getTax()));
                        getcoursedataencryptionData.setPay_via("3");
                        getcoursedataencryptionData.setCoupon_applied(id);
                        String getcoursedataencryptionDatadoseStr = new Gson().toJson(getcoursedataencryptionData);
                        String getcoursedatadoseStrScr = AES.encrypt(getcoursedataencryptionDatadoseStr);
                        return service.int_payment(getcoursedatadoseStrScr);
                    } else {
                        EncryptionData complete = new EncryptionData();
                        complete.setType("2");
                        complete.setCourse_id(coursesCoupon.getId());
                        complete.setParent_id("0");
                        complete.setPre_transaction_id(pre_txtid);
                        complete.setTransaction_status("1");
                        complete.setPost_transaction_id(pos_txn_id);
                        String completedoseStr = new Gson().toJson(complete);
                        String conpelete_response = AES.encrypt(completedoseStr);
                        return service.int_payment(conpelete_response);
                    }
                }
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonobject, String apitype, String typeApi, boolean showprogress) throws JSONException {
        switch (apitype) {
            case API.payment_gateway_credentials:
                try {
                    if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                        JSONObject data = jsonobject.getJSONObject(Const.DATA);
                        JSONObject rzpdata = data.getJSONObject("rzp");
                        razorKey = rzpdata.optString("key");
                        SharedPreference.getInstance().putString("key", razorKey);

                    } else {
                        RetrofitResponse.GetApiData(CouponPurchaseActivity.this, jsonobject.has(Const.AUTH_CODE) ? jsonobject.getString(Const.AUTH_CODE) : "", jsonobject.getString(Const.MESSAGE), false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case API.free_transaction:
                try {
                    if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                        Toast.makeText(CouponPurchaseActivity.this, "" + jsonobject.optString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
                        if (!coursesCoupon.getId().equalsIgnoreCase("")) {
                            UtkashRoom.getAppDatabase(MakeMyExam.getAppContext()).getCourseDetaildata().deletecoursedetail(coursesCoupon.getId(), MakeMyExam.userId);
                        }

                        logBuyNowFreeEvent(coursesCoupon.getTitle());

                        Intent courseList = new Intent(this, CourseActivity.class);//FRAG_TYPE, Const.SINGLE_COURSE AllCoursesAdapter
                        courseList.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                        courseList.putExtra(Const.ISMOVED, "Success");
                        courseList.putExtra(Const.COURSE_ID_MAIN, coursesCoupon.getId());
                        courseList.putExtra(Const.COURSE_PARENT_ID, "");
                        courseList.putExtra(Const.IS_COMBO, false);
                        courseList.putExtra("course_name", coursesCoupon.getTitle());
                        courseList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        Helper.gotoActivity_finish(courseList, this);
                    } else {
                        RetrofitResponse.GetApiData(CouponPurchaseActivity.this, jsonobject.has(Const.AUTH_CODE) ? jsonobject.getString(Const.AUTH_CODE) : "", jsonobject.getString(Const.MESSAGE), false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case API.int_payment:
                try {
                    if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                        if (isfailure) {
                            isfailure = false;
                            pos_txn_id = "";

                        } else if (pos_txn_id.equalsIgnoreCase("")) {
                            JSONObject data = jsonobject.getJSONObject(Const.DATA);
                            pre_txtid = data.optString("pre_transaction_id");
                            launch_paymentGateway();
                        } else {
                            if (!coursesCoupon.getId().equalsIgnoreCase("")) {
                                UtkashRoom.getAppDatabase(MakeMyExam.getAppContext()).getCourseDetaildata().deletecoursedetail(coursesCoupon.getId(), MakeMyExam.userId);
                            }
                            logBuySuccessEvent(coursesCoupon.getTitle());
                            success_dailog();
                            Toast.makeText(CouponPurchaseActivity.this, "" + jsonobject.optString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (isfailure) {
                            isfailure = false;
                            pos_txn_id = "";
                        }

                        RetrofitResponse.GetApiData(CouponPurchaseActivity.this, jsonobject.has(Const.AUTH_CODE) ? jsonobject.getString(Const.AUTH_CODE) : "", jsonobject.getString(Const.MESSAGE), false);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void launch_paymentGateway() {

        Checkout checkout = new Checkout();
        checkout.setKeyID(razorKey);
        checkout.setImage(R.mipmap.ic_launcher);
        JSONObject object = new JSONObject();
        try {
            object.put("name", "Utkarsh Classes and Edutech Pvt. Ltd.");
            object.put("theme.color", "#FED500");
            object.put("description", coursesCoupon.getTitle() + " #(" + coursesCoupon.getId() + ")");
            object.put("currency", "INR");
            object.put("image", coursesCoupon.getCover_image());
            object.put("order_id", pre_txtid);
            // float total_amount = Float.parseFloat(price) + Float.parseFloat(tax);
            Float total_amount = Float.parseFloat(coursesCoupon.getFinal_mrp());
            object.put("amount", Math.round(total_amount * 100));

            JSONObject ReadOnly = new JSONObject();
            ReadOnly.put("email", "true");
            ReadOnly.put("contact", "true");
            object.put("readonly", ReadOnly);

            JSONObject preFill = new JSONObject();
            preFill.put("email", SharedPreference.getInstance().getLoggedInUser().getEmail());
            preFill.put("contact", SharedPreference.getInstance().getLoggedInUser().getMobile());
            object.put("prefill", preFill);
            checkout.open(CouponPurchaseActivity.this, object);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {

    }


    public void logBuySuccessEvent(String bookTitle) {
        Bundle params = new Bundle();
        params.putString("username", SharedPreference.getInstance().getLoggedInUser().getUsername());
        params.putString("usermobile", SharedPreference.getInstance().getLoggedInUser().getMobile());
        params.putString("useremail", SharedPreference.getInstance().getLoggedInUser().getEmail());
        params.putString("booktype", "paid");
        params.putString("bookname", bookTitle);
        logger.logEvent("BuyPaidBook", params);

    }

    public void logBuyNowFreeEvent(String bookTitle) {
        Bundle params = new Bundle();
        params.putString("username", SharedPreference.getInstance().getLoggedInUser().getUsername());
        params.putString("usermobile", SharedPreference.getInstance().getLoggedInUser().getMobile());
        params.putString("useremail", SharedPreference.getInstance().getLoggedInUser().getEmail());
        params.putString("booktype", "free");
        params.putString("bookname", bookTitle);
        logger.logEvent("BuyFreeBook", params);
    }


    private void success_dailog() {
        try {

            if (SystemClock.elapsedRealtime() - mLastClickTime_frame5 < 1000) {
                return;
            }
            mLastClickTime_frame5 = SystemClock.elapsedRealtime();
            final Dialog dialog = new Dialog(CouponPurchaseActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.success_dialog);
            dialog.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
            );

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
            dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);

            EditText et_order_id = dialog.findViewById(R.id.et_order_id);
            EditText et_transaction_id = dialog.findViewById(R.id.et_transaction_id);
            TextView course_name = dialog.findViewById(R.id.course_name);
            et_order_id.setText(pre_txtid);
            et_transaction_id.setText(pos_txn_id);
            course_name.setText(coursesCoupon.getTitle());
            Button btn_my_course = dialog.findViewById(R.id.btn_my_course);
            btn_my_course.setOnClickListener(v -> {


                Intent courseList = new Intent(this, CourseActivity.class);//FRAG_TYPE, Const.SINGLE_COURSE AllCoursesAdapter
                courseList.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                courseList.putExtra(Const.ISMOVED, "Success");
                courseList.putExtra(Const.COURSE_ID_MAIN, coursesCoupon.getId());
                courseList.putExtra(Const.COURSE_PARENT_ID, "");
                courseList.putExtra(Const.IS_COMBO, false);
                courseList.putExtra("course_name", coursesCoupon.getTitle());
                courseList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Helper.gotoActivity_finish(courseList, this);


            });
            dialog.show();
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    dialog.dismiss();
                    dialog.cancel();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}