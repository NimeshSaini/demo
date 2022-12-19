package com.utkarshnew.android.courses.Fragment;

import static com.utkarshnew.android.courses.Fragment.SingleStudy.parentCourseId;
import static com.utkarshnew.android.Utils.Helper.buyNowCourses;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.razorpay.Checkout;
import com.utkarshnew.android.courses.Activity.CourseActivity;
import com.utkarshnew.android.courses.adapter.ExamPrepLayer1Adapter;
import com.utkarshnew.android.courses.Interfaces.OnSuccessListner;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.home.Activity.HomeActivity;
import com.utkarshnew.android.home.Constants;
import com.utkarshnew.android.Model.COURSEDETAIL.CourseDetail;
import com.utkarshnew.android.Model.COURSEDETAIL.CourseDetailData;
import com.utkarshnew.android.Model.Courses.ExamPrepItem;
import com.utkarshnew.android.Model.Courses.Lists;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.Payment.PurchaseActivity;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.MainFragment;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;
import com.utkarshnew.android.Utils.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;

public class ExamPrepLayer1 extends MainFragment implements OnSuccessListner {

    public String main_id, contentType, title;
    RecyclerView examPrepLayerRV;
    RelativeLayout parentLL, buttonLow;
    Activity activity;
    Lists list;
    ExamPrepItem prevexamPrepItem, examPrepItem;
    ExamPrepLayer1Adapter examPrepLayer1Adapter;
    TextView price, mrpCutTV;
    TextView buyNowBtn;
    LinearLayout priceLL;
    Button myLibBtn;
    CourseDetail singleStudy;
    private String pre_txtid = "";
    boolean isCombo = false;
    private UtkashRoom utkashRoom;

    String tileTypeAPI;
    String tileIdAPI;
    String revertAPI;
    private String pos_txn_id = "";

    RelativeLayout no_data_found_RL;
    Button backBtn;

    public ExamPrepLayer1() {
        // Required empty public constructor
    }

    public static ExamPrepLayer1 newInstance(ExamPrepItem examPrepItem, Lists main_id, String contentType, String title, CourseDetail singlestudyModel, boolean isCombo, String tileIdAPI, String tileTypeAPI, String revertAPI) {
        ExamPrepLayer1 examPrepLayer1 = new ExamPrepLayer1();
        Bundle args = new Bundle();
        args.putSerializable(Const.SINGLE_STUDY_DATA, singlestudyModel);
        args.putSerializable(Const.EXAMPREP, examPrepItem);
        args.putSerializable(Const.LIST, main_id);
        args.putString(Const.TITLE, title);
        args.putString(Const.CONTENT_TYPE, contentType);
        args.putBoolean(Const.IS_COMBO, isCombo);
        args.putString(Const.TILE_ID, tileIdAPI);
        args.putString(Const.TILE_TYPE, tileTypeAPI);
        args.putString(Const.REVERT_API, revertAPI);
        examPrepLayer1.setArguments(args);
        return examPrepLayer1;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        if (getArguments() != null) {
            isCombo = getArguments().getBoolean(Const.IS_COMBO);
            list = (Lists) getArguments().getSerializable(Const.LIST);

            contentType = getArguments().getString(Const.CONTENT_TYPE);
            title = getArguments().getString(Const.TITLE);
            tileIdAPI = getArguments().getString(Const.TILE_ID);
            tileTypeAPI = getArguments().getString(Const.TILE_TYPE);
            revertAPI = getArguments().getString(Const.REVERT_API);
            prevexamPrepItem = (ExamPrepItem) getArguments().getSerializable(Const.EXAMPREP);
            singleStudy = (CourseDetail) getArguments().getSerializable(Const.SINGLE_STUDY_DATA);
            if (singleStudy == null) {
                singleStudy = new CourseDetail();
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        utkashRoom = UtkashRoom.getAppDatabase(MakeMyExam.getAppContext());
        if (utkashRoom == null) {
            utkashRoom = UtkashRoom.getAppDatabase(MakeMyExam.getAppContext());
        }
        initView(view);

        if (!TextUtils.isEmpty(title))
            ((CourseActivity) activity).setToolbarTitle(TextUtils.isEmpty(list.getTitle()) ? singleStudy.getData().getCourseDetail().getTitle() : list.getTitle());
    }

    private void initView(View view) {
        parentLL = view.findViewById(R.id.parentLL);
        examPrepLayerRV = view.findViewById(R.id.examPrepLayerRV);

        mrpCutTV = view.findViewById(R.id.mrpCutTV);
        price = view.findViewById(R.id.priceTV);
        buyNowBtn = view.findViewById(R.id.buyNowBtn);
        buttonLow = view.findViewById(R.id.buttonLow);
        myLibBtn = view.findViewById(R.id.myLibBtn);
        priceLL = view.findViewById(R.id.priceLL);

        no_data_found_RL = view.findViewById(R.id.no_data_found_RL);
        backBtn = view.findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new OnSingleClickListener(() -> {
            activity.finish();
            return null;
        }));

        if (SharedPreference.getInstance().getBoolean(Const.IS_PAYMENT_DONE)) {
            Constants.IS_PURCHASED = "0";
            buttonLow.setVisibility(View.GONE);
            SharedPreference.getInstance().putBoolean(Const.SINGLE_STUDY, true);
        } else {
            if (singleStudy != null && singleStudy.getData().getCourseDetail() != null) {
                initButton(singleStudy.getData().getCourseDetail());

            } else {
                activity.finish();
            }
        }

        if (buttonLow.getVisibility() == View.VISIBLE) {
            examPrepLayerRV.setPadding(0, 0, 0, Helper.getValueInDP(activity, 70));
        } else {
            examPrepLayerRV.setPadding(0, 0, 0, 0);
        }

        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, PurchaseActivity.class);
                intent.putExtra("single_study", singleStudy);
                activity.startActivity(intent);

            }
        });
        myLibBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkAPICall(API.free_transaction, "", true, false, false);

            }
        });

        examPrepLayerRV.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));

        //RefreshDataList();
        InitListAdapter(prevexamPrepItem);
    }

    private void initButton(CourseDetailData course) {

        if (!isCombo) {
            if (course.getIsPurchased().equalsIgnoreCase("1")) {
                buttonLow.setVisibility(View.GONE);
            } else {
                buttonLow.setVisibility(View.VISIBLE);
                float total_amount = Float.parseFloat(course.getMrp()) + Float.parseFloat(course.getTax());
                if ((int) total_amount == 0) {
                    mrpCutTV.setVisibility(View.GONE);
                    priceLL.setVisibility(View.GONE);
                    buyNowBtn.setVisibility(View.GONE);
                    myLibBtn.setVisibility(View.VISIBLE);
                } else {
                    priceLL.setVisibility(View.VISIBLE);
                    myLibBtn.setVisibility(View.GONE);

                    price.setText(String.format("%s %s %s", activity.getResources().getString(R.string.rs), "" + ((int) total_amount), "/-"));
                    if (Integer.parseInt(course.getCourseSp()) > 0) {
                        mrpCutTV.setText(String.format("%s %s %s", activity.getResources().getString(R.string.rs), course.getCourseSp().trim(), "/-"), TextView.BufferType.SPANNABLE);
                        StrikethroughSpan STRIKE_THROUGH_SPAN = new StrikethroughSpan();
                        Spannable spannable = (Spannable) mrpCutTV.getText();
                        mrpCutTV.setVisibility(View.VISIBLE);
                        spannable.setSpan(STRIKE_THROUGH_SPAN, 2, new String(course.getCourseSp()).length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else {
                        mrpCutTV.setVisibility(View.GONE);
                    }
                }
            }
        } else {
            buttonLow.setVisibility(View.GONE);
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((CourseActivity) getActivity()).onSuccessListner = this;

    }

    private void RefreshDataList() {
        NetworkAPICall(API.API_GET_MASTER_DATA, contentType, true, false, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exam_prep_layer1, container, false);

    }


    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.free_transaction:
                EncryptionData encryptionData = new EncryptionData();
                encryptionData.setCourse_id(singleStudy.getData().getCourseDetail().getId());
                encryptionData.setCoupon_applied("0");
                encryptionData.setParent_id(parentCourseId);

                String data = new Gson().toJson(encryptionData);
                String free = AES.encrypt(data);
                return service.free_transaction(free);



            case API.API_GET_MASTER_DATA:
                EncryptionData masterdatadetailencryptionData = new EncryptionData();
                masterdatadetailencryptionData.setTile_id(tileIdAPI);
                masterdatadetailencryptionData.setType(tileTypeAPI);
                masterdatadetailencryptionData.setRevert_api(revertAPI);
                masterdatadetailencryptionData.setCourse_id(singleStudy.getData().getCourseDetail().getId());
                masterdatadetailencryptionData.setLayer("2");
                masterdatadetailencryptionData.setParent_id(parentCourseId.equals("") ? singleStudy.getData().getCourseDetail().getId() : parentCourseId);

                masterdatadetailencryptionData.setPage("1");
                masterdatadetailencryptionData.setSubject_id(list.getId() == null ? "0" : list.getId());
                String getmasterdataencryptionDatadoseStr = new Gson().toJson(masterdatadetailencryptionData);
                String masterdatadoseStrScr = AES.encrypt(getmasterdataencryptionDatadoseStr);
                return service.getMasterDataVideoTwo(masterdatadoseStrScr);


        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonobject, String apitype, String typeApi, boolean showprogress) throws JSONException {
        Gson gson = new Gson();
        switch (apitype) {
            case API.free_transaction:
                try {
                    if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {

                        if (!parentCourseId.equalsIgnoreCase("")) {
                            utkashRoom.getCourseDetaildata().deletecoursedetail(parentCourseId, MakeMyExam.userId);
                        } else if (!singleStudy.getData().getCourseDetail().getId().equalsIgnoreCase("")) {
                            utkashRoom.getCourseDetaildata().deletecoursedetail(singleStudy.getData().getCourseDetail().getId(), MakeMyExam.userId);
                        }

                        Toast.makeText(activity, "" + jsonobject.optString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        RetrofitResponse.GetApiData(activity, jsonobject.has(Const.AUTH_CODE) ? jsonobject.getString(Const.AUTH_CODE) : "", jsonobject.getString(Const.MESSAGE), false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case API.int_payment:
                try {
                    if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                        if (((CourseActivity) activity).pos_txn_id.equalsIgnoreCase("")) {
                            JSONObject data = jsonobject.getJSONObject(Const.DATA);
                            pre_txtid = data.optString("pre_transaction_id");
                            launch_paymentGateway(pre_txtid);
                        } else {
                            if (!parentCourseId.equalsIgnoreCase("")) {
                                utkashRoom.getCourseDetaildata().deletecoursedetail(parentCourseId, MakeMyExam.userId);


                            } else if (!singleStudy.getData().getCourseDetail().getId().equalsIgnoreCase("")) {
                                utkashRoom.getCourseDetaildata().deletecoursedetail(singleStudy.getData().getCourseDetail().getId(), MakeMyExam.userId);

                            }

                            //  utkashRoom.getCourseDetaildata().deletecoursedetail(singleStudy.getData().getCourseDetail().getId(), MakeMyExam.userId);
                            Toast.makeText(activity, "" + jsonobject.optString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
                            activity.finish();
                        }
                    } else {
                        this.ErrorCallBack(jsonobject.getString(Const.MESSAGE), apitype, typeApi);
                        RetrofitResponse.GetApiData(activity, jsonobject.has(Const.AUTH_CODE) ? jsonobject.getString(Const.AUTH_CODE) : "", jsonobject.getString(Const.MESSAGE), false);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            case API.API_GET_MASTER_DATA:
                if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                    examPrepLayerRV.setVisibility(View.VISIBLE);
                    no_data_found_RL.setVisibility(View.GONE);
                    examPrepItem = gson.fromJson(jsonobject.optString(Const.DATA), ExamPrepItem.class);
                    InitListAdapter(examPrepItem);
                } else {
                    examPrepLayerRV.setVisibility(View.GONE);
                    no_data_found_RL.setVisibility(View.VISIBLE);
                    RetrofitResponse.GetApiData(activity, jsonobject.optString(Const.AUTH_CODE), jsonobject.optString(Const.MESSAGE), false);
                }
                break;


        }
    }

    private void launch_paymentGateway(String preTransactionId) {

        Checkout checkout = new Checkout();
        checkout.setKeyID(API.live_key_id_razor);
        checkout.setImage(R.mipmap.ic_launcher);
        JSONObject object = new JSONObject();
        try {
            object.put("name", activity.getResources().getString(R.string.app_name));
            object.put("theme.color", activity.getResources().getColor(R.color.colorPrimary));
            object.put("description", "Test Payment");
            object.put("currency", "INR");
            object.put("order_id", preTransactionId);

            float total_amount = Float.parseFloat(singleStudy.getData().getCourseDetail().getMrp()) + Float.parseFloat(singleStudy.getData().getCourseDetail().getTax());
            object.put("amount", Math.round(total_amount * 100));
            object.put("prefill.email", SharedPreference.getInstance().getLoggedInUser().getEmail());
            object.put("prefill.contact", SharedPreference.getInstance().getLoggedInUser().getMobile());
            checkout.open(activity, object);
        } catch (Exception e) {
            e.toString();
        }

    }

    @Override
    public void onResume() {
        if (SharedPreference.getInstance().getBoolean(Const.IS_PAYMENT_DONE)) {
            buttonLow.setVisibility(View.GONE);
            SharedPreference.getInstance().putBoolean(Const.SINGLE_STUDY, true);
        }
        if (singleStudy != null && singleStudy.getData().getCourseDetail() != null) {
            initButton(singleStudy.getData().getCourseDetail());
        }
        super.onResume();
    }

    public void InitListAdapter(ExamPrepItem examPrepItem) {
        if (parentLL.getVisibility() == View.GONE) parentLL.setVisibility(View.VISIBLE);
        examPrepLayer1Adapter = new ExamPrepLayer1Adapter(activity, examPrepItem, list, ExamPrepLayer1.this, singleStudy, isCombo, tileIdAPI, tileTypeAPI, revertAPI);
        examPrepLayerRV.setAdapter(examPrepLayer1Adapter);
        examPrepLayerRV.setNestedScrollingEnabled(false);
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {
        if (apitype.equalsIgnoreCase(API.API_GET_MASTER_DATA)) {
            if (examPrepLayerRV != null && no_data_found_RL != null) {
                examPrepLayerRV.setVisibility(View.GONE);
                no_data_found_RL.setVisibility(View.VISIBLE);
            }
        }
    }

    public void successpayment(String pos_txn_id) {
        this.pos_txn_id = pos_txn_id;
    }

    @Override
    public void onSuccess(String pos_txn_id) {
        successpayment(pos_txn_id);
    }

    @Override
    public void onFailure(String pot_txt_id) {

    }

}