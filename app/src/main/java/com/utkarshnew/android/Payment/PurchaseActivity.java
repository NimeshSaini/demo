package com.utkarshnew.android.Payment;

import static com.utkarshnew.android.courses.Fragment.SingleStudy.parentCourseId;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.appevents.AppEventsLogger;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.utkarshnew.android.Coupon.Adapter.CouponPurchaseAdapter;
import com.utkarshnew.android.Coupon.Models.CoursesCoupon;
import com.utkarshnew.android.courses.Activity.CourseActivity;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.Model.COURSEDETAIL.CourseDetail;
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
import com.utkarshnew.android.courses.modal.NotesType;
import com.utkarshnew.android.home.model.profileSubmitData.ProfileData;
import com.utkarshnew.android.pojo.Userinfo.Data;
import com.utkarshnew.android.purchasehistory.PurchaseHistory;
import com.utkarshnew.android.purchasehistory.UpdateProfileData;
import com.utkarshnew.android.purchasehistory.UpdateProfileUi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;

public class PurchaseActivity extends AppCompatActivity implements NetworkCall.MyNetworkCallBack, PaymentResultListener , UpdateProfileData {

    RoundedImageView imageIV;
    ImageView img_back;
    long mLastClickTime_frame5;
    String couponCode;
    Dialog dialog;
    RelativeLayout cross_layout;
    String tax = "", price = "";
    TextView coursenameTV, txtPriceValue, have_a_coupon, txtGrandTotalValue, tax_value, coupon_value;
    ImageView cross;
    TextView txtPriceValue1, delivery_chnarges_value_withcoupon, delivery_chnarges_value, tax_value1, txtPricesValue1, txtTaxValue1, txtGrandTotalValue1;
    String address_id = "";
    CourseDetail courseDetail;
    NotesType notesType;
    String coupon_applied = "0";
    TextView coupon_applied_txt, coupon_applied_value, validityTV;
    String pos_txn_id = "", pre_txtid = "", tx_status = "0";
    NetworkCall networkCall;
    Button procceed;
    boolean isfailure = false;
    RelativeLayout dummycoupon_layout, withoutCouponLayout, withCouponLayout;
    static String razorkey = "";
    CoursesCoupon coursesCoupon;
    ArrayList<CoursesCoupon> coursesCouponArrayList = new ArrayList<>();
    AppEventsLogger logger;
    RelativeLayout delivery_chnarges, delivery_chnarges_withcoupon;
    String delivery = "0";
    BottomSheetDialog watchlist;
    CouponPurchaseAdapter extendAdapter;

    // send data of updated profile
    String name="",email="",mobile="",state="",district="";

    Data user;

    @SuppressLint({"SetTextI18n", "DefaultLocale", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        logger = AppEventsLogger.newLogger(PurchaseActivity.this);
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.activity_purchase);
        try {
            if (Build.VERSION.SDK_INT >= 21) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));

            }
            user= SharedPreference.getInstance().getLoggedInUser();

            networkCall = new NetworkCall(PurchaseActivity.this, PurchaseActivity.this);

            imageIV = findViewById(R.id.imageIV);

            tax_value = findViewById(R.id.tax_value);
            validityTV = findViewById(R.id.validityTV);

            txtPriceValue1 = findViewById(R.id.txtPriceValue1);
            tax_value1 = findViewById(R.id.tax_value1);
            txtPricesValue1 = findViewById(R.id.txtPricesValue);
            txtTaxValue1 = findViewById(R.id.txtTaxValue);
            txtGrandTotalValue1 = findViewById(R.id.txtGrandTotalValue1);

            delivery_chnarges = findViewById(R.id.delivery_chnarges);
            delivery_chnarges_value = findViewById(R.id.delivery_chnarges_value);

            delivery_chnarges_withcoupon = findViewById(R.id.delivery_chnarges_withcoupon);
            delivery_chnarges_value_withcoupon = findViewById(R.id.delivery_chnarges_value_withcoupon);

            img_back = findViewById(R.id.image_back);
            coupon_applied_txt = findViewById(R.id.coupon_applied_txt);

            coupon_applied_value = findViewById(R.id.coupon_applied);

            cross_layout = findViewById(R.id.cross_layout);
            coupon_value = findViewById(R.id.coupon_value);
            txtGrandTotalValue = findViewById(R.id.txtGrandTotalValue);
            coursenameTV = findViewById(R.id.coursenameTV);
            procceed = findViewById(R.id.procceed);
            txtPriceValue = findViewById(R.id.txtPriceValue);
            have_a_coupon = findViewById(R.id.apply_coupon);
            cross = findViewById(R.id.cross);
            withCouponLayout = findViewById(R.id.withCouponLayout);
            withoutCouponLayout = findViewById(R.id.withoutCouponLayout);


            dummycoupon_layout = findViewById(R.id.dummycoupon_layout);
            dummycoupon_layout.setVisibility(View.GONE);
            delivery = getIntent().getStringExtra("deleivery_charges");
            if (delivery == null || delivery.equals("")) {
                delivery = "0";
            }
            address_id = getIntent().getStringExtra("address_id");
            if (address_id == null || address_id.equals("")) {
                address_id = "0";
            }


            if (getIntent().getStringExtra("notes_type") != null) {
                notesType = new Gson().fromJson(getIntent().getStringExtra("notes_type"), NotesType.class);
            }

            if (getIntent().getStringExtra("single_study") != null) {
                courseDetail = new Gson().fromJson(getIntent().getStringExtra("single_study"), CourseDetail.class);
            } else
                courseDetail = (CourseDetail) getIntent().getSerializableExtra("single_study");

            if (courseDetail != null && courseDetail.getData() != null) {

                networkCall.NetworkAPICall(API.GET_COUPON_OVER_COURSE, "", true, false);

                Helper.setThumbnailImage(this, courseDetail.getData().getCourseDetail().getDescHeaderImage(), getDrawable(R.drawable.book_logo), imageIV);
                coursenameTV.setText(courseDetail.getData().getCourseDetail().getTitle());

                have_a_coupon.setOnClickListener(new OnSingleClickListener(() -> {
                    if (coursesCouponArrayList.size() > 0) {
                        openwatchlist_dailog_resource(this, coursesCouponArrayList);
                    }
                    return null;
                }));

                cross_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        procceed.setText("Proceed");
                        coupon_applied = "0";
                        cross_layout.setVisibility(View.GONE);
                        cross.setVisibility(View.GONE);
                        coupon_applied_txt.setText("Apply Coupon");
                        coupon_applied_txt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                        coupon_applied_txt.setTextColor(Color.parseColor("#545454"));
                        have_a_coupon.setText(getResources().getString(R.string.have_a_coupon));
                        have_a_coupon.setTextColor(getResources().getColor(R.color.blue));
                        have_a_coupon.setEnabled(true);
                        price_update();
                    }
                });
            }


            procceed.setOnClickListener(new OnSingleClickListener(() -> {
                if(user.getName()==null || user.getName().equals(""))
                {
                    UpdateProfileUi updateProfileUi=new UpdateProfileUi(this,this);
                    updateProfileUi.ShowLayout();
                }
                else
                {
                    if (procceed.getText().toString().equalsIgnoreCase("Proceed"))
                    {
                        networkCall.NetworkAPICall(API.int_payment, "", true, false);
                    }
                    else
                    {
                        networkCall.NetworkAPICall(API.free_transaction, "", true, false);
                    }
                }

                return null;
            }));


            img_back.setOnClickListener(v -> finish());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("SetTextI18n")
    public void openwatchlist_dailog_resource(Context context, ArrayList<CoursesCoupon> coursesCouponArrayList) {
        try {
            for (int j = 0; j < coursesCouponArrayList.size(); j++) {
                coursesCouponArrayList.get(j).setIs_select(false);
            }
            watchlist = new BottomSheetDialog(context, R.style.videosheetDialogTheme);
            watchlist.setContentView(R.layout.choose_coupun_layout);
            Objects.requireNonNull(watchlist.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            watchlist.setCancelable(false);
            watchlist.setCanceledOnTouchOutside(true);
            ImageView ibt_single_vd_iv = watchlist.findViewById(R.id.ibt_single_vd_iv);
            TextView view2 = watchlist.findViewById(R.id.view2);
            TextView view3 = watchlist.findViewById(R.id.view3);
            TextView apply_coupon = watchlist.findViewById(R.id.apply_coupon);
            TextView cname = watchlist.findViewById(R.id.cname);
            TextView cancel_coupon = watchlist.findViewById(R.id.cancel_coupon);
            RecyclerView recyclerView = watchlist.findViewById(R.id.recycler_view_validy);
            Objects.requireNonNull(cname).setText(courseDetail.getData().getCourseDetail().getTitle());
            Objects.requireNonNull(view3).setVisibility(View.GONE);
            Objects.requireNonNull(view2).setText("Choose Coupon");
            Helper.setThumbnailImage(this, courseDetail.getData().getCourseDetail().getDescHeaderImage(), getDrawable(R.drawable.book_logo), ibt_single_vd_iv);
            if (coursesCouponArrayList.size() > 0) {
                extendAdapter = new CouponPurchaseAdapter(context, coursesCouponArrayList, watchlist);
                Objects.requireNonNull(recyclerView).setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(extendAdapter);
            }

            if (!watchlist.isShowing()) {
                watchlist.show();
            }
            Objects.requireNonNull(cancel_coupon).setOnClickListener(v -> {
                for (CoursesCoupon coupun : coursesCouponArrayList) {
                    coupun.setIs_select(false);
                }
                dismissCalculatorDialog(watchlist);
            });


            Objects.requireNonNull(apply_coupon).setOnClickListener(v -> {
                String planselect = "";
                for (int j = 0; j < coursesCouponArrayList.size(); j++) {
                    if (coursesCouponArrayList.get(j).isIs_select()) {
                        planselect = "" + j;
                        break;
                    }
                }

                if (planselect.equalsIgnoreCase("")) {
                    Toast.makeText(context, "Select any coupon", Toast.LENGTH_SHORT).show();
                } else {
                    dismissCalculatorDialog(watchlist);

                    withoutCouponLayout.setVisibility(View.GONE);
                    dummycoupon_layout.setVisibility(View.GONE);
                    withCouponLayout.setVisibility(View.VISIBLE);

                    if (notesType != null && notesType.getId() != null) {
                        if (!delivery.equals("0")) {
                            delivery_chnarges_withcoupon.setVisibility(View.VISIBLE);
                            delivery_chnarges_value_withcoupon.setText("₹ " + delivery);
                        } else {
                            delivery_chnarges_withcoupon.setVisibility(View.GONE);
                        }
                    }


                    coursesCoupon = coursesCouponArrayList.get(Integer.parseInt(planselect));
                    if (coursesCoupon.getCoupon().getCoupon_type().equalsIgnoreCase("1")) {
                        coupon_applied_value.setText("Coupon Applied (" + coursesCoupon.getCoupon().getCoupon_value() + " Off)");
                    } else {
                        coupon_applied_value.setText("Coupon Applied (" + coursesCoupon.getCoupon().getCoupon_value() + "% Off)");
                    }


                    validityTV.setText("Validity: " + coursesCoupon.getValidity());
                    tax_value1.setText("- \u20B9 " + Float.parseFloat(coursesCoupon.getDiscount()));
                    float a = Float.parseFloat(coursesCoupon.getMrp());
                    float b = Float.parseFloat(coursesCoupon.getTax());
                    float d = Float.parseFloat(coursesCoupon.getDiscount());
                    float c = a + b + d;
                    txtPriceValue1.setText("\u20B9 " + c);
                    float fTaxValue = Float.parseFloat(coursesCoupon.getDiscount());

                    if (fTaxValue >= c) {
                        txtPricesValue1.setText("\u20B9 0");
                        txtTaxValue1.setText("\u20B9 0");
                        txtGrandTotalValue1.setText("\u20B9 " + Float.parseFloat(delivery));
                    } else {
                        txtTaxValue1.setText("+ \u20B9 " + Float.parseFloat(coursesCoupon.getTax()));
                        txtPricesValue1.setText("\u20B9 " + Float.parseFloat(coursesCoupon.getMrp()));
                        txtGrandTotalValue1.setText("\u20B9 " + (Float.parseFloat(coursesCoupon.getFinal_mrp()) + Float.parseFloat(delivery)));
                    }


                    float flaotvalue = Float.parseFloat(coursesCoupon.getFinal_mrp()) + Float.parseFloat(delivery);
                    if ((int) flaotvalue <= 0) {
                        procceed.setText(getResources().getString(R.string.open_in_my_lib));
                    } else {
                        procceed.setText("Proceed");
                        if (SharedPreference.getInstance().getString("key") != null && !SharedPreference.getInstance().getString("key").equalsIgnoreCase("")) {
                            razorkey = SharedPreference.getInstance().getString("key");
                        } else {
                            networkCall.NetworkAPICall(API.payment_gateway_credentials, "", true, false);
                        }
                    }

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void price_update() {
        float total_amount = Float.parseFloat(courseDetail.getData().getCourseDetail().getMrp()) + Float.parseFloat(courseDetail.getData().getCourseDetail().getTax());

        if ((int) total_amount == 0) {
            procceed.setText(getResources().getString(R.string.open_in_my_lib));
            price = "0";
            tax = "0";
            tax_value.setText("Free");
            txtGrandTotalValue.setText("Free");
            txtPriceValue.setText("Free");
        } else {
            txtPriceValue.setText("₹" + courseDetail.getData().getCourseDetail().getMrp());
            tax_value.setText("₹" + courseDetail.getData().getCourseDetail().getTax());

            price = "" + Float.parseFloat(courseDetail.getData().getCourseDetail().getMrp());
            tax = "" + Float.parseFloat(courseDetail.getData().getCourseDetail().getTax());

            txtGrandTotalValue.setText("₹ " + total_amount);
        }


    }

    private void showCouponCodePopup() {
        try {

            if (SystemClock.elapsedRealtime() - mLastClickTime_frame5 < 1000) {
                return;
            }
            mLastClickTime_frame5 = SystemClock.elapsedRealtime();
            dialog = new Dialog(PurchaseActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.coupon_dialog);
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

            RelativeLayout calcpager = dialog.findViewById(R.id.calcpagerl);
            Button cancel = dialog.findViewById(R.id.btn__cancel);
            EditText couponvalue = dialog.findViewById(R.id.writeCouponET);
            Button submit = dialog.findViewById(R.id.btn_submit);

            submit.setOnClickListener(v -> {
                if (!couponvalue.getText().toString().trim().equalsIgnoreCase("")) {
                    couponCode = Helper.removeHTML(couponvalue.getText().toString());
                    networkCall.NetworkAPICall(API.apply_coupon, "", true, false);
                } else {
                    Toast.makeText(PurchaseActivity.this, "Enter coupon code to get Discount", Toast.LENGTH_SHORT).show();
                }
            });


            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissCalculatorDialog(dialog);
                }
            });

            Objects.requireNonNull(calcpager).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissCalculatorDialog(dialog);
                }
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


    private void dismissCalculatorDialog(Dialog watchlist) {
        if (watchlist != null && watchlist.isShowing()) {
            watchlist.dismiss();
            watchlist.cancel();
            watchlist = null;
        }
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {

        switch (apitype) {
            case API.GET_COUPON_OVER_COURSE:
                EncryptionData encryptionData2 = new EncryptionData();
                encryptionData2.setCourse_id(courseDetail.getData().getCourseDetail().getId());
                encryptionData2.setParent_id(parentCourseId);
                encryptionData2.setAddress_id(address_id);

                if (notesType != null && notesType.getId() != null) {
                    encryptionData2.setType(notesType.getId());
                }
                String data1 = new Gson().toJson(encryptionData2);
                String free1 = AES.encrypt(data1);
                return service.GET_COUPON_OVER_COURSE(free1);

            case API.payment_gateway_credentials:
                EncryptionData encryptionData1 = new EncryptionData();
                encryptionData1.setUser_id(MakeMyExam.userId);

                String datajson = new Gson().toJson(encryptionData1);
                String getweay = AES.encrypt(datajson);
                return service.payment_gateway_credentials(getweay);


            case API.free_transaction:
                if (withCouponLayout.isShown()) {
                    EncryptionData encryptionData = new EncryptionData();
                    encryptionData.setCourse_id(coursesCoupon.getId());
                    encryptionData.setCoupon_applied(coursesCoupon.getCoupon().getId());
                    encryptionData.setParent_id("0");
                    encryptionData.setAddress_id(address_id);
                    if (notesType != null && notesType.getId() != null) {
                        encryptionData.setPurchase_type(notesType.getId());
                    }

                    if(user.getName()==null || user.getName().equals("")) {
                        // update profile data
                        ProfileData profileData = new ProfileData();
                        profileData.setName(name);
                        profileData.setEmail(email);
                        profileData.setState(state);
                        profileData.setCity(district);
                        ////
                        encryptionData.setProfileData(profileData);
                    }

                    encryptionData.setDelivery_price(delivery);

                    String data = new Gson().toJson(encryptionData);
                    String free = AES.encrypt(data);
                    return service.free_transaction(free);
                } else {
                    EncryptionData encryptionData = new EncryptionData();
                    encryptionData.setCourse_id(courseDetail.getData().getCourseDetail().getId());
                    encryptionData.setCoupon_applied(coupon_applied);
                    encryptionData.setParent_id(parentCourseId);
                    encryptionData.setAddress_id(address_id);
                    encryptionData.setDelivery_price(delivery);

                    if(user.getName()==null || user.getName().equals("")) {
                        // update profile data
                        ProfileData profileData = new ProfileData();
                        profileData.setName(name);
                        profileData.setEmail(email);
                        profileData.setState(state);
                        profileData.setCity(district);
                        ////
                        encryptionData.setProfileData(profileData);
                    }

                    if (notesType != null && notesType.getId() != null) {
                        encryptionData.setPurchase_type(notesType.getId());
                    }
                    String data = new Gson().toJson(encryptionData);
                    String free = AES.encrypt(data);
                    return service.free_transaction(free);
                }


            case API.apply_coupon:

                EncryptionData coupondata = new EncryptionData();
                coupondata.setCourse_id(courseDetail.getData().getCourseDetail().getId());
                coupondata.setCoupon_code(couponCode);
                coupondata.setParent_id(parentCourseId);
                String json_coupondata = new Gson().toJson(coupondata);
                String coupomdataencrypt = AES.encrypt(json_coupondata);
                return service.apply_coupon(coupomdataencrypt);

            case API.int_payment:
                if (withCouponLayout.isShown()) {
                    if (isfailure) {
                        EncryptionData complete = new EncryptionData();
                        complete.setType("2");
                        complete.setCourse_id(coursesCoupon.getId());
                        complete.setParent_id(parentCourseId);
                        complete.setPre_transaction_id(pre_txtid);
                        complete.setDelivery_price(delivery);

                        complete.setAddress_id(address_id);
                        if (notesType != null && notesType.getId() != null) {
                            complete.setPurchase_type(notesType.getId());

                        }

                        if(user.getName()==null || user.getName().equals("")) {
                            ProfileData profileData = new ProfileData();
                            profileData.setName(name);
                            profileData.setEmail(email);
                            profileData.setState(state);
                            profileData.setCity(district);
                            ////
                            complete.setProfileData(profileData);
                        }

                        complete.setTransaction_status("2");
                        complete.setPost_transaction_id("");
                        complete.setCoupon_applied(coursesCoupon.getCoupon().getId());
                        String completedoseStr = new Gson().toJson(complete);
                        String conpelete_response = AES.encrypt(completedoseStr);
                        return service.int_payment(conpelete_response);
                    } else {
                        if (pos_txn_id.equalsIgnoreCase("")) {
                            EncryptionData getcoursedataencryptionData = new EncryptionData();
                            getcoursedataencryptionData.setType("1");
                            if (notesType != null && notesType.getId() != null) {
                                getcoursedataencryptionData.setPurchase_type(notesType.getId());
                            }
                            getcoursedataencryptionData.setDelivery_price(delivery);

                            if(user.getName()==null || user.getName().equals("")) {
                                // update profile data
                                ProfileData profileData = new ProfileData();
                                profileData.setName(name);
                                profileData.setEmail(email);
                                profileData.setState(state);
                                profileData.setCity(district);
                                ////
                                getcoursedataencryptionData.setProfileData(profileData);
                            }

                            getcoursedataencryptionData.setCourse_id(coursesCoupon.getId());
                            getcoursedataencryptionData.setCourse_price("" + Float.parseFloat(coursesCoupon.getMrp()));
                            getcoursedataencryptionData.setParent_id(parentCourseId);
                            getcoursedataencryptionData.setTax("" + Float.parseFloat(coursesCoupon.getTax()));
                            getcoursedataencryptionData.setPay_via("3");
                            getcoursedataencryptionData.setAddress_id(address_id);

                            getcoursedataencryptionData.setCoupon_applied(coursesCoupon.getCoupon().getId());
                            String getcoursedataencryptionDatadoseStr = new Gson().toJson(getcoursedataencryptionData);
                            String getcoursedatadoseStrScr = AES.encrypt(getcoursedataencryptionDatadoseStr);
                            return service.int_payment(getcoursedatadoseStrScr);
                        } else {
                            EncryptionData complete = new EncryptionData();
                            complete.setType("2");
                            complete.setCourse_id(coursesCoupon.getId());
                            complete.setParent_id(parentCourseId);
                            complete.setPre_transaction_id(pre_txtid);
                            complete.setDelivery_price(delivery);

                            if(user.getName()==null || user.getName().equals("")) {
                                // update profile data
                                ProfileData profileData = new ProfileData();
                                profileData.setName(name);
                                profileData.setEmail(email);
                                profileData.setState(state);
                                profileData.setCity(district);
                                ////
                                complete.setProfileData(profileData);
                            }


                            complete.setTransaction_status("1");
                            complete.setAddress_id(address_id);
                            if (notesType != null && notesType.getId() != null) {
                                complete.setPurchase_type(notesType.getId());

                            }

                            complete.setPost_transaction_id(pos_txn_id);
                            String completedoseStr = new Gson().toJson(complete);
                            String conpelete_response = AES.encrypt(completedoseStr);
                            return service.int_payment(conpelete_response);
                        }
                    }

                } else {
                    if (isfailure) {
                        EncryptionData complete = new EncryptionData();
                        complete.setType("2");
                        complete.setCourse_id(courseDetail.getData().getCourseDetail().getId());
                        complete.setParent_id(parentCourseId);
                        complete.setPre_transaction_id(pre_txtid);
                        complete.setTransaction_status("2");
                        complete.setAddress_id(address_id);
                        complete.setDelivery_price(delivery);

                        if(user.getName()==null || user.getName().equals("")) {
                            // update profile data
                            ProfileData profileData = new ProfileData();
                            profileData.setName(name);
                            profileData.setEmail(email);
                            profileData.setState(state);
                            profileData.setCity(district);
                            ////
                            complete.setProfileData(profileData);
                        }

                        if (notesType != null && notesType.getId() != null) {
                            complete.setPurchase_type(notesType.getId());

                        }

                        complete.setPost_transaction_id("");
                        String completedoseStr = new Gson().toJson(complete);
                        String conpelete_response = AES.encrypt(completedoseStr);
                        return service.int_payment(conpelete_response);
                    } else {
                        if (pos_txn_id.equalsIgnoreCase("")) {
                            EncryptionData getcoursedataencryptionData = new EncryptionData();
                            getcoursedataencryptionData.setType("1");
                            getcoursedataencryptionData.setCourse_id(courseDetail.getData().getCourseDetail().getId());
                            getcoursedataencryptionData.setCourse_price(price);
                            getcoursedataencryptionData.setParent_id(parentCourseId);
                            getcoursedataencryptionData.setTax(tax);
                            getcoursedataencryptionData.setDelivery_price(delivery);

                            if(user.getName()==null || user.getName().equals("")) {

                                // update profile data
                                ProfileData profileData = new ProfileData();
                                profileData.setName(name);
                                profileData.setEmail(email);
                                profileData.setState(state);
                                profileData.setCity(district);
                                ////
                                getcoursedataencryptionData.setProfileData(profileData);
                            }

                            getcoursedataencryptionData.setAddress_id(address_id);
                            if (notesType != null && notesType.getId() != null) {
                                getcoursedataencryptionData.setPurchase_type(notesType.getId());
                            }
                            getcoursedataencryptionData.setPay_via("3");
                            getcoursedataencryptionData.setCoupon_applied(coupon_applied);
                            String getcoursedataencryptionDatadoseStr = new Gson().toJson(getcoursedataencryptionData);
                            String getcoursedatadoseStrScr = AES.encrypt(getcoursedataencryptionDatadoseStr);
                            return service.int_payment(getcoursedatadoseStrScr);
                        } else {
                            EncryptionData complete = new EncryptionData();
                            complete.setType("2");
                            complete.setCourse_id(courseDetail.getData().getCourseDetail().getId());
                            complete.setParent_id(parentCourseId);
                            complete.setPre_transaction_id(pre_txtid);
                            complete.setTransaction_status("1");
                            complete.setDelivery_price(delivery);

                            if(user.getName()==null || user.getName().equals("")) {
                                // update profile data
                                ProfileData profileData = new ProfileData();
                                profileData.setName(name);
                                profileData.setEmail(email);
                                profileData.setState(state);
                                profileData.setCity(district);
                                ////
                                complete.setProfileData(profileData);
                            }

                            complete.setAddress_id(address_id);
                            if (notesType != null && notesType.getId() != null) {
                                complete.setPurchase_type(notesType.getId());
                            }

                            complete.setPost_transaction_id(pos_txn_id);
                            String completedoseStr = new Gson().toJson(complete);
                            String conpelete_response = AES.encrypt(completedoseStr);
                            return service.int_payment(conpelete_response);
                        }
                    }
                }


        }
        return null;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void SuccessCallBack(JSONObject jsonobject, String apitype, String typeApi, boolean showprogress) throws JSONException {
        switch (apitype) {

            case API.GET_COUPON_OVER_COURSE:
                if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {

                    Gson gson = new Gson();
                    coursesCouponArrayList.clear();
                    JSONArray arrJson = jsonobject.optJSONArray(Const.DATA);
                    for (int i = 0; i < Objects.requireNonNull(arrJson).length(); i++) {
                        JSONObject dataObj = arrJson.optJSONObject(i);
                        CoursesCoupon questionBank = gson.fromJson(dataObj.toString(), CoursesCoupon.class);
                        coursesCouponArrayList.add(questionBank);
                    }
                    if (coursesCouponArrayList != null && coursesCouponArrayList.size() > 0) {

                        if (coursesCouponArrayList.size() > 1) {
                            withCouponLayout.setVisibility(View.GONE);

                            withoutCouponLayout.setVisibility(View.VISIBLE);

                            courseDetail.getData().getCourseDetail().setMrp(notesType.getMrp());
                            courseDetail.getData().getCourseDetail().setCourseSp(notesType.getCourse_sp());
                            courseDetail.getData().getCourseDetail().setTax(notesType.getTax());
                            float total_amount = Float.parseFloat(courseDetail.getData().getCourseDetail().getMrp()) + Float.parseFloat(courseDetail.getData().getCourseDetail().getTax());

                            if (notesType != null && notesType.getId() != null) {
                                if (!delivery.equals("0")) {
                                    delivery_chnarges.setVisibility(View.VISIBLE);
                                    delivery_chnarges_value.setText("₹ " + delivery);
                                    total_amount = total_amount + Float.parseFloat(delivery);

                                } else {
                                    delivery_chnarges.setVisibility(View.GONE);
                                }
                            }

                            validityTV.setText("Validity: " + courseDetail.getData().getCourseDetail().getValidity());
                            dummycoupon_layout.setVisibility(View.VISIBLE);
                            if ((int) total_amount == 0) {
                                procceed.setText(getResources().getString(R.string.open_in_my_lib));
                                price = "0";
                                tax = "0";
                                tax_value.setText("Free");
                                txtGrandTotalValue.setText("Free");
                                txtPriceValue.setText("Free");
                                logBuyPreviewEvent(courseDetail.getData().getCourseDetail().getTitle(), "free");
                            } else {
                                txtPriceValue.setText("₹ " + courseDetail.getData().getCourseDetail().getMrp());
                                tax_value.setText("₹ " + courseDetail.getData().getCourseDetail().getTax());
                                price = "" + Float.parseFloat(courseDetail.getData().getCourseDetail().getMrp());
                                tax = "" + Float.parseFloat(courseDetail.getData().getCourseDetail().getTax());
                                txtGrandTotalValue.setText("₹ " + String.format("%.2f", total_amount));
                                logBuyPreviewEvent(courseDetail.getData().getCourseDetail().getTitle(), "paid");
                                if (SharedPreference.getInstance().getString("key") != null && !SharedPreference.getInstance().getString("key").equalsIgnoreCase("")) {
                                    razorkey = SharedPreference.getInstance().getString("key");
                                } else {
                                    networkCall.NetworkAPICall(API.payment_gateway_credentials, "", true, false);
                                }
                            }
                        } else {

                            withCouponLayout.setVisibility(View.VISIBLE);

                            dummycoupon_layout.setVisibility(View.GONE);
                            withoutCouponLayout.setVisibility(View.GONE);

                            if (notesType != null && notesType.getId() != null) {
                                if (!delivery.equals("0")) {
                                    delivery_chnarges_withcoupon.setVisibility(View.VISIBLE);
                                    delivery_chnarges_value_withcoupon.setText("₹ " + delivery);
                                } else {
                                    delivery_chnarges_withcoupon.setVisibility(View.GONE);
                                }
                            }

                            coursesCoupon = Objects.requireNonNull(coursesCouponArrayList).get(0);

                            if (coursesCoupon.getCoupon().getCoupon_type().equalsIgnoreCase("1")) {
                                coupon_applied_value.setText("Coupon Applied (" + coursesCoupon.getCoupon().getCoupon_value() + " Off)");
                            } else {
                                coupon_applied_value.setText("Coupon Applied (" + coursesCoupon.getCoupon().getCoupon_value() + "% Off)");
                            }

                            validityTV.setText("Validity: " + coursesCoupon.getValidity());
                            tax_value1.setText("- \u20B9 " + Float.parseFloat(coursesCoupon.getDiscount()));

                            float a = Float.parseFloat(coursesCoupon.getMrp());
                            float b = Float.parseFloat(coursesCoupon.getTax());
                            float d = Float.parseFloat(coursesCoupon.getDiscount());
                            float c = a + b + d;
                            txtPriceValue1.setText("\u20B9 " + c);
                            float fTaxValue = Float.parseFloat(coursesCoupon.getDiscount());

                            if (fTaxValue >= c) {
                                txtPricesValue1.setText("\u20B9 0");
                                txtTaxValue1.setText("\u20B9 0");
                                txtGrandTotalValue1.setText("\u20B9 " + Float.parseFloat(delivery));
                            } else {
                                txtTaxValue1.setText("+ \u20B9 " + Float.parseFloat(coursesCoupon.getTax()));
                                txtPricesValue1.setText("\u20B9 " + Float.parseFloat(coursesCoupon.getMrp()));
                                txtGrandTotalValue1.setText("\u20B9 " + (Float.parseFloat(coursesCoupon.getFinal_mrp()) + Float.parseFloat(delivery)));
                            }


                            float flaotvalue = Float.parseFloat(coursesCoupon.getFinal_mrp()) + Float.parseFloat(delivery);
                            if ((int) flaotvalue <= 0) {
                                procceed.setText(getResources().getString(R.string.open_in_my_lib));
                            } else {
                                procceed.setText("Proceed");
                                if (SharedPreference.getInstance().getString("key") != null && !SharedPreference.getInstance().getString("key").equalsIgnoreCase("")) {
                                    razorkey = SharedPreference.getInstance().getString("key");
                                } else {
                                    networkCall.NetworkAPICall(API.payment_gateway_credentials, "", true, false);
                                }
                            }
                        }

                    } else {
                        withCouponLayout.setVisibility(View.GONE);
                        withoutCouponLayout.setVisibility(View.VISIBLE);
                        courseDetail.getData().getCourseDetail().setMrp(notesType.getMrp());
                        courseDetail.getData().getCourseDetail().setCourseSp(notesType.getCourse_sp());
                        courseDetail.getData().getCourseDetail().setTax(notesType.getTax());
                        float total_amount = Float.parseFloat(courseDetail.getData().getCourseDetail().getMrp()) + Float.parseFloat(courseDetail.getData().getCourseDetail().getTax());

                        if (notesType != null && notesType.getId() != null) {
                            if (!delivery.equals("0")) {
                                delivery_chnarges.setVisibility(View.VISIBLE);
                                delivery_chnarges_value.setText("₹ " + delivery);
                                total_amount = total_amount + Float.parseFloat(delivery);
                            } else {
                                delivery_chnarges.setVisibility(View.GONE);
                            }
                        }

                        validityTV.setText("Validity: " + courseDetail.getData().getCourseDetail().getValidity());
                        dummycoupon_layout.setVisibility(View.GONE);
                        if ((int) total_amount == 0) {
                            procceed.setText(getResources().getString(R.string.open_in_my_lib));
                            price = "0";
                            tax = "0";
                            tax_value.setText("Free");
                            txtGrandTotalValue.setText("Free");
                            txtPriceValue.setText("Free");
                            logBuyPreviewEvent(courseDetail.getData().getCourseDetail().getTitle(), "free");
                        } else {
                            txtPriceValue.setText("₹ " + courseDetail.getData().getCourseDetail().getMrp());
                            tax_value.setText("₹ " + courseDetail.getData().getCourseDetail().getTax());
                            price = "" + Float.parseFloat(courseDetail.getData().getCourseDetail().getMrp());
                            tax = "" + Float.parseFloat(courseDetail.getData().getCourseDetail().getTax());
                            txtGrandTotalValue.setText("₹ " + String.format("%.2f", total_amount));
                            logBuyPreviewEvent(courseDetail.getData().getCourseDetail().getTitle(), "paid");
                            if (SharedPreference.getInstance().getString("key") != null && !SharedPreference.getInstance().getString("key").equalsIgnoreCase("")) {
                                razorkey = SharedPreference.getInstance().getString("key");
                            } else {
                                networkCall.NetworkAPICall(API.payment_gateway_credentials, "", true, false);
                            }
                        }
                    }


                } else {
                    if (!jsonobject.has(Const.AUTH_CODE)) {
                        withoutCouponLayout.setVisibility(View.VISIBLE);
                        if (notesType != null && notesType.getId() != null) {
                            courseDetail.getData().getCourseDetail().setMrp(notesType.getMrp());
                            courseDetail.getData().getCourseDetail().setCourseSp(notesType.getCourse_sp());
                            courseDetail.getData().getCourseDetail().setTax(notesType.getTax());

                            float total_amount = Float.parseFloat(courseDetail.getData().getCourseDetail().getMrp()) + Float.parseFloat(courseDetail.getData().getCourseDetail().getTax());

                            if (!delivery.equals("0") && !delivery.equals("")) {
                                delivery_chnarges.setVisibility(View.VISIBLE);
                                delivery_chnarges_value.setText("₹ " + delivery);
                                total_amount = total_amount + Float.parseFloat(delivery);
                            } else {
                                delivery_chnarges.setVisibility(View.GONE);
                            }

                            validityTV.setText("Validity: " + courseDetail.getData().getCourseDetail().getValidity());
                            dummycoupon_layout.setVisibility(View.GONE);
                            if ((int) total_amount == 0) {
                                procceed.setText(getResources().getString(R.string.open_in_my_lib));
                                price = "0";
                                tax = "0";
                                tax_value.setText("Free");
                                txtGrandTotalValue.setText("Free");
                                txtPriceValue.setText("Free");
                                logBuyPreviewEvent(courseDetail.getData().getCourseDetail().getTitle(), "free");
                            } else {
                                txtPriceValue.setText("₹ " + courseDetail.getData().getCourseDetail().getMrp());
                                tax_value.setText("₹ " + courseDetail.getData().getCourseDetail().getTax());
                                price = "" + Float.parseFloat(courseDetail.getData().getCourseDetail().getMrp());
                                tax = "" + Float.parseFloat(courseDetail.getData().getCourseDetail().getTax());
                                txtGrandTotalValue.setText("₹ " + String.format("%.2f", total_amount));
                                logBuyPreviewEvent(courseDetail.getData().getCourseDetail().getTitle(), "paid");
                                if (SharedPreference.getInstance().getString("key") != null && !SharedPreference.getInstance().getString("key").equalsIgnoreCase("")) {
                                    razorkey = SharedPreference.getInstance().getString("key");
                                } else {
                                    networkCall.NetworkAPICall(API.payment_gateway_credentials, "", true, false);
                                }

                            }
                        }

                    } else {
                        RetrofitResponse.GetApiData(this, jsonobject.has(Const.AUTH_CODE) ? jsonobject.getString(Const.AUTH_CODE) : "", jsonobject.getString(Const.MESSAGE), false);
                    }
                }
                break;

            case API.payment_gateway_credentials:
                try {
                    if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                        JSONObject data = jsonobject.getJSONObject(Const.DATA);
                        JSONObject rzpdata = data.getJSONObject("rzp");
                        razorkey = rzpdata.optString("key");
                        SharedPreference.getInstance().putString("key", razorkey);

                    } else {
                        RetrofitResponse.GetApiData(PurchaseActivity.this, jsonobject.has(Const.AUTH_CODE) ? jsonobject.getString(Const.AUTH_CODE) : "", jsonobject.getString(Const.MESSAGE), false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case API.free_transaction:
                try {
                    if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                        if(user.getName()==null || user.getName().equals("")) {
                            user.setName(name);
                            user.setEmail(email);
                            user.setCity(district);
                            user.setState(state);
                            SharedPreference.getInstance().setLoggedInUserr(user);
                        }
                        Toast.makeText(PurchaseActivity.this, "" + jsonobject.optString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
                        if (!parentCourseId.equalsIgnoreCase("")) {
                            UtkashRoom.getAppDatabase(MakeMyExam.getAppContext()).getCourseDetaildata().deletecoursedetail(parentCourseId, MakeMyExam.userId);

                        } else if (!courseDetail.getData().getCourseDetail().getId().equalsIgnoreCase("")) {
                            UtkashRoom.getAppDatabase(MakeMyExam.getAppContext()).getCourseDetaildata().deletecoursedetail(courseDetail.getData().getCourseDetail().getId(), MakeMyExam.userId);
                        }

                        logBuyNowFreeEvent(courseDetail.getData().getCourseDetail().getTitle());

                        Intent courseList = new Intent(this, CourseActivity.class);//FRAG_TYPE, Const.SINGLE_COURSE AllCoursesAdapter
                        courseList.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                        courseList.putExtra(Const.COURSE_ID_MAIN, !parentCourseId.equalsIgnoreCase("") ? parentCourseId : courseDetail.getData().getCourseDetail().getId());
                        courseList.putExtra(Const.COURSE_PARENT_ID, "");
                        courseList.putExtra(Const.IS_COMBO, false);
                        courseList.putExtra("course_name", courseDetail.getData().getCourseDetail().getTitle());
                        courseList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        Helper.gotoActivity_finish(courseList, this);
                    } else {
                        RetrofitResponse.GetApiData(PurchaseActivity.this, jsonobject.has(Const.AUTH_CODE) ? jsonobject.getString(Const.AUTH_CODE) : "", jsonobject.getString(Const.MESSAGE), false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case API.int_payment:
                try {
                    if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {

                        if(user.getName()==null || user.getName().equals("")) {
                            user.setName(name);
                            user.setEmail(email);
                            user.setCity(district);
                            user.setState(state);
                            SharedPreference.getInstance().setLoggedInUserr(user);
                        }


                        if (isfailure) {
                            isfailure = false;
                            pos_txn_id = "";

                        } else if (pos_txn_id.equalsIgnoreCase("")) {
                            JSONObject data = jsonobject.getJSONObject(Const.DATA);
                            pre_txtid = data.optString("pre_transaction_id");
                            launch_paymentGateway();
                        } else {
                            if (!parentCourseId.equalsIgnoreCase("")) {
                                UtkashRoom.getAppDatabase(MakeMyExam.getAppContext()).getCourseDetaildata().deletecoursedetail(parentCourseId, MakeMyExam.userId);

                            } else if (!courseDetail.getData().getCourseDetail().getId().equalsIgnoreCase("")) {
                                UtkashRoom.getAppDatabase(MakeMyExam.getAppContext()).getCourseDetaildata().deletecoursedetail(courseDetail.getData().getCourseDetail().getId(), MakeMyExam.userId);

                            }
                            logBuySuccessEvent(courseDetail.getData().getCourseDetail().getTitle());
                            success_dailog();
                            Toast.makeText(PurchaseActivity.this, "" + jsonobject.optString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (isfailure) {
                            isfailure = false;
                            pos_txn_id = "";
                        }

                        RetrofitResponse.GetApiData(PurchaseActivity.this, jsonobject.has(Const.AUTH_CODE) ? jsonobject.getString(Const.AUTH_CODE) : "", jsonobject.getString(Const.MESSAGE), false);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case API.apply_coupon:
                try {
                    if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                        dismissCalculatorDialog(dialog);
                        JSONObject data = jsonobject.getJSONObject(Const.DATA);

                        have_a_coupon.setText(couponCode);
                        have_a_coupon.setEnabled(false);
                        coupon_applied_txt.setText("Coupon Applied");

                        cross_layout.setVisibility(View.VISIBLE);
                        cross.setVisibility(View.VISIBLE);
                        coupon_value.setVisibility(View.VISIBLE);
                        coupon_applied = data.optString("id");
                        if (data.optString("coupon_type").equalsIgnoreCase("1")) {
                            flat_percentage(data.optString("coupon_value"), couponCode);

                        } else if (data.optString("coupon_type").equalsIgnoreCase("2")) {
                            percentage_calculate(data.optString("coupon_value"));
                        }
                    } else {
                        if (jsonobject.has((Const.AUTH_CODE))) {
                            RetrofitResponse.GetApiData(PurchaseActivity.this, jsonobject.has(Const.AUTH_CODE) ? jsonobject.getString(Const.AUTH_CODE) : "", jsonobject.getString(Const.MESSAGE), false);

                        } else {
                            this.ErrorCallBack(jsonobject.getString(Const.MESSAGE), apitype, typeApi);

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    @SuppressLint("SetTextI18n")
    private void percentage_calculate(String coupondis) {
        if (courseDetail != null && courseDetail.getData() != null) {
            coupon_value.setText(coupondis + "%");

            if (Integer.parseInt(coupondis) >= 100) {
                procceed.setText(getResources().getString(R.string.open_in_my_lib));
                price = "0";
                tax = "0";
                tax_value.setText("Free");
                txtGrandTotalValue.setText("Free");
                txtPriceValue.setText("Free");

            } else {
                procceed.setText("Proceed");
                float total_amount = Float.parseFloat(courseDetail.getData().getCourseDetail().getMrp()) + Float.parseFloat(courseDetail.getData().getCourseDetail().getTax());
                double amount = Double.parseDouble(String.valueOf(total_amount));

                double percetange_mrp = (amount / 100.0f) * Integer.parseInt(coupondis);
                float final_amount = total_amount - Float.parseFloat(String.valueOf(percetange_mrp));

                double taxvalue = Double.parseDouble(new DecimalFormat("##.#").format((18 / 100.0f) * final_amount));


                // double taxvalue =Double.parseDouble(String.valueOf((int)(18/100.0f)*final_amount));
                tax = String.valueOf(taxvalue);
                price = String.valueOf(final_amount - taxvalue);

                txtPriceValue.setText("₹ " + (final_amount - taxvalue));
                tax_value.setText("₹ " + (tax));
                txtGrandTotalValue.setText("₹ " + (final_amount));
            }


        }
    }

    @SuppressLint("SetTextI18n")
    private void flat_percentage(String coupon_amount, String couponCode) {
        if (courseDetail != null && courseDetail.getData() != null) {
            have_a_coupon.setText(" - ₹ " + coupon_amount);
            have_a_coupon.setTextColor(Color.parseColor("#545454"));
            have_a_coupon.setEnabled(false);
            coupon_applied_txt.setTypeface(Typeface.DEFAULT_BOLD);
            coupon_applied_txt.setTextColor(Color.parseColor("#545454"));
            coupon_applied_txt.setText("Coupon Applied");
            coupon_value.setText("" + couponCode);
            coupon_value.setTextColor(getResources().getColor(R.color.blue));

            procceed.setText("Proceed");
            float total_amount = Float.parseFloat(courseDetail.getData().getCourseDetail().getMrp()) + Float.parseFloat(courseDetail.getData().getCourseDetail().getTax());

            if (Integer.parseInt(coupon_amount) >= total_amount) {
                price = "0";
                tax = "0";
                procceed.setText(getResources().getString(R.string.open_in_my_lib));
                tax_value.setText("Free");
                txtGrandTotalValue.setText("Free");
                txtPriceValue.setText("Free");
            } else if (total_amount > Integer.parseInt(coupon_amount)) {
                float amount = total_amount - Float.parseFloat(coupon_amount);
                double taxvalue = Double.parseDouble(new DecimalFormat("##.#").format((18 / 100.0f) * amount));
                // double taxvalue =Float.parseFloat(String.valueOf((18/100.0f)*amount));
                tax = String.valueOf(taxvalue);
                price = String.valueOf(amount - taxvalue);

                txtPriceValue.setText("₹ " + (amount - taxvalue));
                tax_value.setText("₹ " + (taxvalue));
                txtGrandTotalValue.setText("₹ " + (amount));
            }

        }

    }

    private void launch_paymentGateway() {
        if (withCouponLayout.isShown()) {

            Checkout checkout = new Checkout();
            checkout.setKeyID(razorkey);
            checkout.setImage(R.mipmap.ic_launcher);
            JSONObject object = new JSONObject();
            try {
                object.put("name", "Utkarsh Classes and Edutech Pvt. Ltd.");
                object.put("theme.color", "#FED500");
                object.put("description", coursesCoupon.getTitle() + " #(" + (!parentCourseId.equalsIgnoreCase("") ? parentCourseId : coursesCoupon.getId()) + ")");

                // object.put("description", coursesCoupon.getTitle() + " #(" + coursesCoupon.getId() + ")");
                object.put("currency", "INR");
                object.put("image", coursesCoupon.getCover_image());
                object.put("order_id", pre_txtid);
                // float total_amount = Float.parseFloat(price) + Float.parseFloat(tax);
                float total_amount = Float.parseFloat(coursesCoupon.getFinal_mrp()) + Float.parseFloat(delivery);
                object.put("amount", Math.round(total_amount * 100));

                JSONObject ReadOnly = new JSONObject();
                ReadOnly.put("email", "true");
                ReadOnly.put("contact", "true");
                object.put("readonly", ReadOnly);

                JSONObject preFill = new JSONObject();
                preFill.put("email", SharedPreference.getInstance().getLoggedInUser().getEmail());
                preFill.put("contact", SharedPreference.getInstance().getLoggedInUser().getMobile());
                object.put("prefill", preFill);
                checkout.open(PurchaseActivity.this, object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Checkout checkout = new Checkout();
            checkout.setKeyID(razorkey);
            checkout.setImage(R.mipmap.ic_launcher);
            JSONObject object = new JSONObject();
            try {
                object.put("name", "Utkarsh Classes and Edutech Pvt. Ltd.");
                object.put("theme.color", "#FED500");
                object.put("description", courseDetail.getData().getCourseDetail().getTitle() + " #(" + (!parentCourseId.equalsIgnoreCase("") ? parentCourseId : courseDetail.getData().getCourseDetail().getId()) + ")");
                object.put("currency", "INR");
                object.put("image", courseDetail.getData().getCourseDetail().getDescHeaderImage());
                object.put("order_id", pre_txtid);
                float total_amount = Float.parseFloat(price) + Float.parseFloat(tax) + Float.parseFloat(delivery);
                object.put("amount", Math.round(total_amount * 100));

                JSONObject ReadOnly = new JSONObject();
                ReadOnly.put("email", "true");
                ReadOnly.put("contact", "true");
                object.put("readonly", ReadOnly);

                JSONObject preFill = new JSONObject();
                preFill.put("email", SharedPreference.getInstance().getLoggedInUser().getEmail());
                preFill.put("contact", SharedPreference.getInstance().getLoggedInUser().getMobile());
                object.put("prefill", preFill);
                checkout.open(PurchaseActivity.this, object);

            } catch (Exception e) {
                e.toString();
            }
        }

    }


    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {
        Toast.makeText(this, "" + jsonstring, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onPaymentSuccess(String s) {
        pos_txn_id = s;
        // NetworkCall networkCall = new NetworkCall(PurchaseActivity.this, PurchaseActivity.this);
        networkCall.NetworkAPICall(API.int_payment, "", true, false);


    }

    @Override
    public void onPaymentError(int i, String s) {
        //      pos_txn_id = s;
        tx_status = "" + i;
        isfailure = true;
        networkCall.NetworkAPICall(API.int_payment, "", true, false);

        //   Toast.makeText(this, "Payment Failed Please try again", Toast.LENGTH_SHORT).show();
    }


    @SuppressLint("SetTextI18n")
    private void success_dailog() {
        try {

            if (SystemClock.elapsedRealtime() - mLastClickTime_frame5 < 1000) {
                return;
            }
            mLastClickTime_frame5 = SystemClock.elapsedRealtime();
            final Dialog dialog = new Dialog(PurchaseActivity.this);
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
            course_name.setText(courseDetail.getData().getCourseDetail().getTitle());
            Button btn_my_course = dialog.findViewById(R.id.btn_my_course);
            if (notesType != null && notesType.getId().equals("1")) {
                btn_my_course.setText("GoTo PurchaseHistory");
            }
            btn_my_course.setOnClickListener(v -> {

                if (notesType != null && notesType.getId().equals("1")) {
                    Intent courseList = new Intent(this, PurchaseHistory.class);//FRAG_TYPE, Const.SINGLE_COURSE AllCoursesAdapter
                    courseList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Helper.gotoActivity_finish(courseList, this);
                } else {
                    Intent courseList = new Intent(this, CourseActivity.class);//FRAG_TYPE, Const.SINGLE_COURSE AllCoursesAdapter
                    courseList.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                    courseList.putExtra(Const.COURSE_ID_MAIN, !parentCourseId.equalsIgnoreCase("") ? parentCourseId : courseDetail.getData().getCourseDetail().getId());
                    courseList.putExtra(Const.COURSE_PARENT_ID, "");
                    courseList.putExtra(Const.IS_COMBO, false);
                    courseList.putExtra("course_name", courseDetail.getData().getCourseDetail().getTitle());
                    courseList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Helper.gotoActivity_finish(courseList, this);
                }


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

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void logBuyNowFreeEvent(String bookTitle) {
        Bundle params = new Bundle();
        params.putString("userid", SharedPreference.getInstance().getLoggedInUser().getId());
        params.putString("coursename", coursenameTV.getText().toString());
        params.putString("usermobile", SharedPreference.getInstance().getLoggedInUser().getMobile());

        params.putString("booktype", "free");
        params.putString("bookname", bookTitle);
        logger.logEvent("BuyFreeBook", params);
    }

    public void logBuySuccessEvent(String bookTitle) {
        Bundle params = new Bundle();
        params.putString("userid", SharedPreference.getInstance().getLoggedInUser().getId());
        params.putString("coursename", coursenameTV.getText().toString());
        params.putString("usermobile", SharedPreference.getInstance().getLoggedInUser().getMobile());
        params.putString("booktype", "paid");
        params.putString("bookname", bookTitle);
        logger.logEvent("BuyPaidBook", params);

    }

    public void logBuyPreviewEvent(String bookTitle, String bookType) {
        Bundle params = new Bundle();
        params.putString("coursename", coursenameTV.getText().toString());
        params.putString("usermobile", SharedPreference.getInstance().getLoggedInUser().getMobile());
        params.putString("userid", SharedPreference.getInstance().getLoggedInUser().getId());
        params.putString("booktype", bookType);
        params.putString("bookname", bookTitle);
        logger.logEvent("BookPreview", params);

    }


    @Override
    public void getUpdatedData(String name, String emailid, String mobile, String state, String district) {
        this.name=name;
        this.email=emailid;
        this.mobile=mobile;
        this.state=state;
        this.district=district;

        if (procceed.getText().toString().equalsIgnoreCase("Proceed"))
        {
            networkCall.NetworkAPICall(API.int_payment, "", true, false);
        } else {
            networkCall.NetworkAPICall(API.free_transaction, "", true, false);
        }
    }
}