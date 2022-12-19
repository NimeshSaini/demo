package com.utkarshnew.android.courses.Fragment;

import static com.utkarshnew.android.DownloadServices.VideoDownloadService.RES_ID;
import static com.utkarshnew.android.DownloadServices.VideoDownloadService.VIDEO_DOWNLOAD_SUCCESSFUL;
import static com.utkarshnew.android.Utils.Helper.setMargins;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.razorpay.Checkout;
import com.utkarshnew.android.home.model.profileSubmitData.ProfileData;
import com.utkarshnew.android.purchasehistory.UpdateProfileData;
import com.utkarshnew.android.purchasehistory.UpdateProfileUi;
import com.utkarshnew.android.table.AddressTable;
import com.utkarshnew.android.address.model.Address;
import com.utkarshnew.android.courses.Activity.CourseActivity;
import com.utkarshnew.android.courses.Interfaces.NotesTypeItemClick;
import com.utkarshnew.android.courses.adapter.NotesTypeAdapter;
import com.utkarshnew.android.courses.adapter.SingleStudyAdapter;
import com.utkarshnew.android.courses.Interfaces.OnSuccessListner;
import com.utkarshnew.android.courses.modal.NotesType;
import com.utkarshnew.android.DownloadServices.VideoDownloadService;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.address.adapter.AddressAdapter;
import com.utkarshnew.android.address.interfaces.onItemSelected;
import com.utkarshnew.android.home.Constants;
import com.utkarshnew.android.Model.COURSEDETAIL.Author;
import com.utkarshnew.android.Model.COURSEDETAIL.CourseDetail;
import com.utkarshnew.android.Model.COURSEDETAIL.CourseDetailData;
import com.utkarshnew.android.Model.COURSEDETAIL.TilesItem;
import com.utkarshnew.android.Model.Courselist;
import com.utkarshnew.android.Model.Courses.ExamPrepItem;
import com.utkarshnew.android.Model.Courses.Lists;
import com.utkarshnew.android.Model.FAQs.FaqData;
import com.utkarshnew.android.Model.Overview.Data;
import com.utkarshnew.android.Model.Overview.OverviewData;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.Payment.PurchaseActivity;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.table.CourseDetailTable;
import com.utkarshnew.android.table.UserWiseCourseTable;
import com.utkarshnew.android.table.VideosDownload;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.GenericUtils;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.MainFragment;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;
import com.utkarshnew.android.Utils.SharedPreference;
import com.utkarshnew.android.pojo.Userinfo.StatesCities.StatesCities;
import com.utkarshnew.android.pojo.Userinfo.StatesCities.StatesCitiesData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;

public class SingleStudy extends MainFragment implements SingleStudyAdapter.onButtonClicked, View.OnClickListener, OnSuccessListner, onItemSelected, NotesTypeItemClick, PopupMenu.OnMenuItemClickListener , UpdateProfileData {
    RecyclerView studyCourseRV;
    Activity activity;
    CourseDetail cousedetail;
    long mLastClickTime_frame5;
    TextView state, city;
    EncryptionData address = null;
    private UtkashRoom utkashRoom;
    ExamPrepItem examPrepItem;
    OverviewData overviewData;
    StatesCities states;
    StatesCities cities;

    ArrayList<FaqData> faqData;
    com.utkarshnew.android.address.model.Data addressdata;
    ArrayList<com.utkarshnew.android.address.model.Data> addresslist = new ArrayList<>();
    ArrayList<Courselist> courseDataArrayList;
    int tilePos = 0;
    RecyclerView recyclerViewaddress;
    String typeApi;
    BottomSheetDialog watchlist = null;


    String tileTypeAPI;
    String tileIdAPI;
    String revertAPI;
    String clickType = "";
    String state_id = "", state_name = "";
    String deleivery_charges = "0", city_name = "", city_id = "";
    TextView price, mrpCutTV;
    RelativeLayout buttonLow;
    LinearLayout priceLL;
    LinearLayout notes_layout;
    RecyclerView notes_recycler_view;
    Button buyNowBtn;
    Button myLibBtn;

    SingleStudyAdapter singleStudyAdapter;
    LinearLayoutManager linearLayoutManager;
    String mainCourseId;
    public static String parentCourseId = "";
    String course_name = "";
    boolean isCombo = false;
    boolean is_update = false;
    private String pre_txtid = "", pos_txn_id = "";
    public static String valid_to = "";
    RelativeLayout no_data_found_RL;
    Button backBtn;
    public NotesType notesType;
    com.utkarshnew.android.pojo.Userinfo.Data user;
    String name="",email="",mobile="",selectedstate="",district="";

    public SingleStudy() {
        // Required empty public constructor
    }

    public static SingleStudy newInstance(String mainCourseId, boolean isCombo, String parentCourseId, String course_name, String valid_to) {
        SingleStudy singleCourse = new SingleStudy();
        Bundle args = new Bundle();
        args.putString(Const.COURSE_ID_MAIN, mainCourseId);
        args.putString(Const.COURSE_PARENT_ID, parentCourseId);
        args.putBoolean(Const.IS_COMBO, isCombo);
        args.putString("valid_to", valid_to);
        args.putString("course_name", course_name);
        singleCourse.setArguments(args);
        return singleCourse;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((CourseActivity) requireActivity()).onSuccessListner = this;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        utkashRoom = UtkashRoom.getAppDatabase(MakeMyExam.getAppContext());
        studyCourseRV = view.findViewById(R.id.studyCourseRV);
        mrpCutTV = view.findViewById(R.id.mrpCutTV);
        price = view.findViewById(R.id.priceTV);
        buyNowBtn = view.findViewById(R.id.buyNowBtn);
        myLibBtn = view.findViewById(R.id.myLibBtn);
        buttonLow = view.findViewById(R.id.buttonLow);
        priceLL = view.findViewById(R.id.priceLL);
        notes_layout = view.findViewById(R.id.notes_layout);
        notes_recycler_view = view.findViewById(R.id.notes_recycler_view);

        no_data_found_RL = view.findViewById(R.id.no_data_found_RL);

     /*   physical_note = view.findViewById(R.id.physical_note);
        e_book = view.findViewById(R.id.e_book);
        both = view.findViewById(R.id.both);*/

        backBtn = view.findViewById(R.id.backBtn);

        linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        studyCourseRV.setLayoutManager(linearLayoutManager);
        studyCourseRV.setHasFixedSize(true);
        SharedPreference.getInstance().remove(Const.SINGLE_STUDY);
        user= SharedPreference.getInstance().getLoggedInUser();
        buyNowBtn.setOnClickListener(this);
        backBtn.setOnClickListener(new OnSingleClickListener(() -> {
            activity.finish();
            return null;
        }));


        ((CourseActivity) activity).shareIV.setOnClickListener(new OnSingleClickListener(() -> {
            Helper.shareCourse(activity, mainCourseId, String.valueOf(isCombo), parentCourseId, course_name, cousedetail.getData().getCourseDetail().getDescHeaderImage(), cousedetail.getData().getCourseDetail().getTitle());
            return null;
        }));

        if (utkashRoom == null) {
            utkashRoom = UtkashRoom.getAppDatabase(MakeMyExam.getAppContext());
        }
        if (utkashRoom.getCourseDetaildata() != null) {
            if (!utkashRoom.getCourseDetaildata().isRecordExistsUserId(MakeMyExam.userId, parentCourseId + "_" + mainCourseId)) {
                NetworkAPICall(API.CourseDetail_JS, "", true, false, false);
            } else {
                List<CourseDetailTable> courseDetailTable = utkashRoom.getCourseDetaildata().getcoursedetail(parentCourseId + "_" + mainCourseId, MakeMyExam.userId);
                CourseDetailData courseDetailData = new CourseDetailData();
                if (courseDetailTable.size() > 0) {
                    if (courseDetailTable.get(0).getNotes_type() != null && courseDetailTable.get(0).getNotes_type().size() > 0) {
                        courseDetailData.setNotes_type(courseDetailTable.get(0).getNotes_type());
                    }
                    courseDetailData.setTitle(courseDetailTable.get(0).getCourse_title());
                    courseDetailData.setCourseSp(courseDetailTable.get(0).getCourse_sp());
                    Author author = new Author();
                    author.setTitle(courseDetailTable.get(0).getAuthor_title());
                    courseDetailData.setAuthor(author);
                 /*   courseDetailData.setMrp(courseDetailTable.get(0).getMrp());
                    courseDetailData.setTax(courseDetailTable.get(0).getTax());*/
                    courseDetailData.setValidity(courseDetailTable.get(0).getValidity());
                    String couse[] = courseDetailTable.get(0).getCourse_id().split("_");
                    courseDetailData.setId(couse[1]);
                    //  courseDetailData.setCourseSp(courseDetailTable.get(0).getCourse_sp());
                    courseDetailData.setDescHeaderImage(courseDetailTable.get(0).getDesc_header_image());
                    courseDetailData.setIsPurchased(courseDetailTable.get(0).getIs_purchased());
                    courseDetailData.setViewType(courseDetailTable.get(0).getView_type());
                    cousedetail = new CourseDetail();
                    com.utkarshnew.android.Model.COURSEDETAIL.Data data = new com.utkarshnew.android.Model.COURSEDETAIL.Data();
                    data.setCourseDetail(courseDetailData);
                    List<TilesItem> tilesItem = new ArrayList<>();
                    for (int i = 0; i < courseDetailTable.size(); i++) {
                        TilesItem tilesItems = new TilesItem(courseDetailTable.get(i).getTile_revert(), courseDetailTable.get(i).getTile_title(), courseDetailTable.get(i).getTile_id(), courseDetailTable.get(i).getType(), courseDetailTable.get(i).getTile_meta());
                        tilesItem.add(tilesItems);
                    }
                    data.setTiles(tilesItem);
                    cousedetail.setData(data);
                    if (((CourseActivity) activity).is_coupon) {
                        Intent intent = new Intent(activity, PurchaseActivity.class);
                        intent.putExtra("single_study", cousedetail);
                        Helper.gotoActivity(intent, activity);
                    } else {
                        initButton(cousedetail.getData().getCourseDetail());
                        callForData(cousedetail.getData().getTiles(), cousedetail.getData().getCourseDetail().getIsPurchased());
                    }

                } else {
                    utkashRoom.getCourseDetaildata().deletecoursedetail(parentCourseId, MakeMyExam.userId);

                    NetworkAPICall(API.CourseDetail_JS, "", true, false, false);
                }

            }
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        Constants.revison_set = false;
        if (getArguments() != null) {
            isCombo = getArguments().getBoolean(Const.IS_COMBO);
            parentCourseId = getArguments().getString(Const.COURSE_PARENT_ID);
            course_name = getArguments().getString("course_name");
            valid_to = getArguments().getString("valid_to");
            mainCourseId = getArguments().getString(Const.COURSE_ID_MAIN);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(videoDownloadReceiver, new IntentFilter(VideoDownloadService.VIDEO_DOWNLOAD_ACTION));

        if (Constants.revison_set) {
            NetworkAPICall(API.API_GET_MASTER_DATA, typeApi, true, false, false);
            Constants.revison_set = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(videoDownloadReceiver);
    }

    private BroadcastReceiver videoDownloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int serviceStatus = intent.getIntExtra("result", -1);
            String res_id = intent.getStringExtra(RES_ID);
            if (serviceStatus == VIDEO_DOWNLOAD_SUCCESSFUL) {
                if (singleStudyAdapter != null && res_id != null && !res_id.equals("")) {
                    if (singleStudyAdapter.contentType.contains("video")) {
                        for (Lists video : examPrepItem.getList()) {
                            if (video.getId().equalsIgnoreCase(res_id)) {
                                video.setVideo_download(true);
                                video.setVideo_status("Downloaded");
                                singleStudyAdapter.notifyDataSetChanged();
                                break;
                            }

                        }
                    }
                }

            }

        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_single_study, container, false);
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {

        switch (apitype) {
            case API.delete_my_address:
                EncryptionData encryptionDatadelete = new EncryptionData();
                encryptionDatadelete.setId(addressdata.getId());
                return service.delete_my_address(AES.encrypt(new Gson().toJson(encryptionDatadelete)));
            case API.get_delivery_charges:
                EncryptionData get_delivery_charges = new EncryptionData();
                get_delivery_charges.setState_id(addressdata.getAddress().getState_id());
                get_delivery_charges.setCity_id(addressdata.getAddress().getCity_id());
                return service.get_delivery_charges(AES.encrypt(new Gson().toJson(get_delivery_charges)));


            case API.API_CITY:
                EncryptionData cityencryptionData = new EncryptionData();
                cityencryptionData.setState_id(state_id);
                String cityencryptionDatadoseStr = new Gson().toJson(cityencryptionData);
                String citydoseStrScr = AES.encrypt(cityencryptionDatadoseStr);
                return service.GetCity(citydoseStrScr);


            case API.API_STATE:
                EncryptionData stateencryptionData = new EncryptionData();
                stateencryptionData.setUser_id(MakeMyExam.getUserId());
                String stateencryptionDatadoseStr = new Gson().toJson(stateencryptionData);
                String statedoseStrScr = AES.encrypt(stateencryptionDatadoseStr);
                return service.GetState(statedoseStrScr);

            case API.get_my_addresses:
                EncryptionData encryptionData = new EncryptionData();
                encryptionData.setUser_id("");
                return service.get_my_addresses(AES.encrypt(new Gson().toJson(encryptionData)));

            case API.add_update_address:
                EncryptionData encryptionData_new = new EncryptionData();
                encryptionData_new.setAddress(new Gson().toJson(address));
                encryptionData_new.setId(address.getId());
                String address_dataencrypt = AES.encrypt(new Gson().toJson(encryptionData_new));
                return service.add_update_address(address_dataencrypt);


            case API.int_payment:
                if (pos_txn_id.equalsIgnoreCase("")) {
                    EncryptionData getcoursedataencryptionData = new EncryptionData();
                    getcoursedataencryptionData.setType("1");
                    getcoursedataencryptionData.setCourse_id(cousedetail.getData().getCourseDetail().getId());
                    getcoursedataencryptionData.setCourse_price(cousedetail.getData().getCourseDetail().getMrp());
                    getcoursedataencryptionData.setTax(cousedetail.getData().getCourseDetail().getTax());
                    getcoursedataencryptionData.setPay_via("3");
                    getcoursedataencryptionData.setCoupon_applied("0");


                    if(user.getName()==null || user.getName().equals(""))
                    {
                        // update profile data
                        ProfileData profileData=new ProfileData();
                        profileData.setName(name);
                        profileData.setEmail(email);
                        profileData.setState(selectedstate);
                        profileData.setCity(district);
                        ////
                        getcoursedataencryptionData.setProfileData(profileData);
                    }


                    String getcoursedataencryptionDatadoseStr = new Gson().toJson(getcoursedataencryptionData);
                    String getcoursedatadoseStrScr = AES.encrypt(getcoursedataencryptionDatadoseStr);
                    return service.int_payment(getcoursedatadoseStrScr);
                } else {
                    EncryptionData complete = new EncryptionData();
                    complete.setType("2");
                    complete.setCourse_id(cousedetail.getData().getCourseDetail().getId());
                    complete.setPre_transaction_id(pre_txtid);
                    complete.setTransaction_status("1");
                    complete.setPost_transaction_id(pos_txn_id);

                    if(user.getName()==null || user.getName().equals("")) {
                        // update profile data
                        ProfileData profileData=new ProfileData();
                        profileData.setName(name);
                        profileData.setEmail(email);
                        profileData.setState(selectedstate);
                        profileData.setCity(district);
                        ////
                        complete.setProfileData(profileData);
                    }

                    String completedoseStr = new Gson().toJson(complete);
                    String conpelete_response = AES.encrypt(completedoseStr);
                    return service.int_payment(conpelete_response);
                }

            case API.free_transaction:
                EncryptionData getcoursedataencryptionData = new EncryptionData();
                getcoursedataencryptionData.setCourse_id(cousedetail.getData().getCourseDetail().getId());
                getcoursedataencryptionData.setCoupon_applied("0");
                getcoursedataencryptionData.setParent_id(parentCourseId);
                if (addressdata != null) {
                    getcoursedataencryptionData.setAddress_id(addressdata.getId());
                } else {
                    getcoursedataencryptionData.setAddress_id("0");
                }

                if(user.getName()==null || user.getName().equals(""))
                {
                    // update profile data
                    ProfileData profileData=new ProfileData();
                    profileData.setName(name);
                    profileData.setEmail(email);
                    profileData.setState(selectedstate);
                    profileData.setCity(district);
                    ////
                    getcoursedataencryptionData.setProfileData(profileData);
                }

                if (notesType != null && notesType.getId() != null) {
                    getcoursedataencryptionData.setPurchase_type(notesType.getId());
                }
                getcoursedataencryptionData.setDelivery_price("0");

                String getcoursedataencryptionDatadoseStr = new Gson().toJson(getcoursedataencryptionData);
                String getcoursedatadoseStrScr = AES.encrypt(getcoursedataencryptionDatadoseStr);
                return service.free_transaction(getcoursedatadoseStrScr);


            case API.CourseDetail_JS:

                EncryptionData getcoursedetailencryptionData = new EncryptionData();
                getcoursedetailencryptionData.setCourse_id(mainCourseId);
                getcoursedetailencryptionData.setParent_id(parentCourseId);
                String getcoursedetailencryptionDatadoseStr = new Gson().toJson(getcoursedetailencryptionData);
                String getcoursedetaildoseStrScr = AES.encrypt(getcoursedetailencryptionDatadoseStr);
                return service.getCourseData(getcoursedetaildoseStrScr);


            case API.API_GET_MASTER_DATA:
                String skipStr = revertAPI;
                String[] separated = skipStr.split("\\#");
                String isSkip = separated[1];

                if (typeApi.equalsIgnoreCase(Const.OVERVIEW + tileIdAPI) || typeApi.equalsIgnoreCase(Const.FAQ + tileIdAPI) || typeApi.equalsIgnoreCase(Const.COMBO + tileIdAPI)) {

                    EncryptionData masterdatadetailencryptionData = new EncryptionData();
                    masterdatadetailencryptionData.setTile_id(tileIdAPI);
                    masterdatadetailencryptionData.setType(tileTypeAPI);
                    masterdatadetailencryptionData.setRevert_api(revertAPI);
                    masterdatadetailencryptionData.setCourse_id(mainCourseId);
                    masterdatadetailencryptionData.setParent_id(parentCourseId.equals("") ? cousedetail.getData().getCourseDetail().getId() : parentCourseId);

                    String getmasterdataencryptionDatadoseStr = new Gson().toJson(masterdatadetailencryptionData);
                    String masterdatadoseStrScr = AES.encrypt(getmasterdataencryptionDatadoseStr);
                    return service.getMasterDataOverviewFAQ(masterdatadoseStrScr);
                } else {
                    if (isSkip.equalsIgnoreCase("1")) {
                        EncryptionData masterdatadetailencryptionData = new EncryptionData();
                        masterdatadetailencryptionData.setTile_id(tileIdAPI);
                        masterdatadetailencryptionData.setType(tileTypeAPI);
                        masterdatadetailencryptionData.setRevert_api(revertAPI);
                        masterdatadetailencryptionData.setCourse_id(mainCourseId);
                        masterdatadetailencryptionData.setParent_id(parentCourseId.equals("") ? cousedetail.getData().getCourseDetail().getId() : parentCourseId);

                        masterdatadetailencryptionData.setLayer("2");
                        masterdatadetailencryptionData.setSubject_id("0");
                        masterdatadetailencryptionData.setPage("1");
                        String getmasterdataencryptionDatadoseStr = new Gson().toJson(masterdatadetailencryptionData);
                        String masterdatadoseStrScr = AES.encrypt(getmasterdataencryptionDatadoseStr);
                        return service.getMasterDataVideoTwo(masterdatadoseStrScr);

                    } else if (isSkip.equalsIgnoreCase("3")) {

                        EncryptionData masterdatadetailencryptionData = new EncryptionData();
                        masterdatadetailencryptionData.setTile_id(tileIdAPI);
                        masterdatadetailencryptionData.setType(tileTypeAPI);
                        masterdatadetailencryptionData.setRevert_api(revertAPI);
                        masterdatadetailencryptionData.setCourse_id(mainCourseId);
                        masterdatadetailencryptionData.setParent_id(parentCourseId.equals("") ? cousedetail.getData().getCourseDetail().getId() : parentCourseId);

                        masterdatadetailencryptionData.setLayer("3");
                        masterdatadetailencryptionData.setSubject_id("0");
                        masterdatadetailencryptionData.setTopic_id("0");
                        masterdatadetailencryptionData.setPage("1");
                        String getmasterdataencryptionDatadoseStr = new Gson().toJson(masterdatadetailencryptionData);
                        String masterdatadoseStrScr = AES.encrypt(getmasterdataencryptionDatadoseStr);
                        return service.getMasterDataVideoThree(masterdatadoseStrScr);


                    } else {

                        EncryptionData masterdatadetailencryptionData = new EncryptionData();
                        masterdatadetailencryptionData.setTile_id(tileIdAPI);
                        masterdatadetailencryptionData.setType(tileTypeAPI);
                        masterdatadetailencryptionData.setRevert_api(revertAPI);
                        masterdatadetailencryptionData.setCourse_id(mainCourseId);
                        masterdatadetailencryptionData.setParent_id(parentCourseId.equals("") ? cousedetail.getData().getCourseDetail().getId() : parentCourseId);

                        masterdatadetailencryptionData.setLayer("1");
                        masterdatadetailencryptionData.setPage("1");
                        String getmasterdataencryptionDatadoseStr = new Gson().toJson(masterdatadetailencryptionData);
                        String masterdatadoseStrScr = AES.encrypt(getmasterdataencryptionDatadoseStr);
                        return service.getMasterDataVideo(masterdatadoseStrScr);

                    }
                }
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonobject, String apitype, String typeApi, boolean showprogress) throws JSONException {
        Gson gson = new Gson();
        switch (apitype) {
            case API.get_delivery_charges:
                if (jsonobject.getBoolean("status")) {

                    JSONObject jsonObject = jsonobject.optJSONObject(Const.DATA);
                    if (jsonObject != null) {
                        deleivery_charges = jsonObject.optString("delivery_charge");
                    }
                } else {
                    RetrofitResponse.GetApiData(activity, jsonobject.has(Const.AUTH_CODE) ? jsonobject.getString(Const.AUTH_CODE) : "", jsonobject.getString(Const.MESSAGE), false);

                }
                break;
            case API.delete_my_address:
                if (jsonobject.getBoolean("status")) {
                    utkashRoom.getAddress().delete_address(addressdata.getId());
                    addresslist.remove(addressdata);
                    addressdata = null;
                    if (recyclerViewaddress.getAdapter() == null) {
                        AddressAdapter addressAdapter = new AddressAdapter(activity, addresslist, this);
                        recyclerViewaddress.setAdapter(addressAdapter);
                    } else {
                        AddressAdapter addressAdapter = (AddressAdapter) recyclerViewaddress.getAdapter();
                        addressAdapter.updateItem(addresslist);
                    }

                } else {
                    RetrofitResponse.GetApiData(activity, jsonobject.has(Const.AUTH_CODE) ? jsonobject.getString(Const.AUTH_CODE) : "", jsonobject.getString(Const.MESSAGE), false);

                }
                break;
            case API.API_STATE:
                if (jsonobject.getBoolean("status")) {
                    states = new Gson().fromJson(jsonobject.toString(), StatesCities.class);
                    if (!state_name.equals("")) {
                        NetworkAPICall(API.API_CITY, "", false, false, false);

                    }
                }
                break;
            case API.API_CITY:
                if (jsonobject.getBoolean("status")) {
                    cities = new Gson().fromJson(jsonobject.toString(), StatesCities.class);
                    if (city_id.equals("")) {
                        city.setText(cities.getData().get(0).getName());
                        city_id = cities.getData().get(0).getId();
                        city_name = cities.getData().get(0).getName();
                    }

                } else {
                    RetrofitResponse.GetApiData(activity, jsonobject.has(Const.AUTH_CODE) ? jsonobject.getString(Const.AUTH_CODE) : "", jsonobject.getString(Const.MESSAGE), false);

                }
                break;

            case API.free_transaction:
                try {
                    if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {

                        if(user.getName()==null || user.getName().equals("")) {
                            user.setName(name);
                            user.setEmail(email);
                            user.setCity(district);
                            user.setState(selectedstate);
                            SharedPreference.getInstance().setLoggedInUserr(user);
                        }

                        utkashRoom.getCourseDetaildata().deletecoursedetail(cousedetail.getData().getCourseDetail().getId(), MakeMyExam.userId);
                        Toast.makeText(activity, "" + jsonobject.optString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
                        Intent courseList = new Intent(activity, CourseActivity.class);//FRAG_TYPE, Const.SINGLE_COURSE AllCoursesAdapter
                        courseList.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                        courseList.putExtra(Const.COURSE_ID_MAIN, !parentCourseId.equalsIgnoreCase("") ? parentCourseId : cousedetail.getData().getCourseDetail().getId());
                        courseList.putExtra(Const.COURSE_PARENT_ID, "");
                        courseList.putExtra(Const.IS_COMBO, false);
                        courseList.putExtra("course_name", cousedetail.getData().getCourseDetail().getTitle());
                        Helper.gotoActivity_finish(courseList, activity);
                    } else {
                        RetrofitResponse.GetApiData(activity, jsonobject.has(Const.AUTH_CODE) ? jsonobject.getString(Const.AUTH_CODE) : "", jsonobject.getString(Const.MESSAGE), false);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case API.add_update_address:
                try {
                    if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                        Toast.makeText(activity, jsonobject.getString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
                        JSONObject data = jsonobject.getJSONObject(Const.DATA);
                        com.utkarshnew.android.address.model.Data address = new Gson().fromJson(data.toString(), com.utkarshnew.android.address.model.Data.class);
                        if (is_update) {
                            for (int i = 0; i < addresslist.size(); i++) {
                                if (addresslist.get(i).getId().equals(addressdata.getId())) {
                                    addresslist.set(i, address);
                                    utkashRoom.getAddress().update_address(addressdata.getId(),
                                            address.getAddress().getAddressOne(),
                                            address.getAddress().getAddressTwo(),
                                            address.getAddress().getState(),
                                            address.getAddress().getCity(),
                                            address.getAddress().getName(),
                                            address.getAddress().getPincode(),
                                            address.getAddress().getPhone(),
                                            address.getAddress().getDelivery_price(),
                                            address.getAddress().getState_id(),
                                            address.getAddress().getCity_id()
                                    );
                                    break;
                                }
                            }
                            is_update = false;
                        } else {
                            AddressTable addressTable = new AddressTable();
                            addressTable.setPincode(address.getAddress().getPincode());
                            addressTable.setAddress_id(address.getId());
                            addressTable.setAddress_one(address.getAddress().getAddressOne());
                            addressTable.setAddress_two(address.getAddress().getAddressTwo());
                            addressTable.setCity(address.getAddress().getCity());
                            addressTable.setState(address.getAddress().getState());
                            addressTable.setName(address.getAddress().getName());
                            addressTable.setPhone(address.getAddress().getPhone());
                            addressTable.setCountry("INDIA");
                            addressTable.setState_id(state_id);
                            addressTable.setCity_id(city_id);
                            addressTable.setDelivery_price(address.getAddress().getDelivery_price().equals("") ? "0" : address.getAddress().getDelivery_price());
                            utkashRoom.getAddress().addAddress(addressTable);
                            addresslist.add(address);
                        }

                        state_id = "";
                        city_id = "";
                        city_name = "";
                        state_name="";
                    } else {
                        RetrofitResponse.GetApiData(activity, jsonobject.has(Const.AUTH_CODE) ? jsonobject.getString(Const.AUTH_CODE) : "", jsonobject.getString(Const.MESSAGE), false);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case API.get_my_addresses:
                try {
                    if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                        JSONArray jsonArray = jsonobject.getJSONArray(Const.DATA);
                        addresslist.clear();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            com.utkarshnew.android.address.model.Data address = new Gson().fromJson(jsonArray.get(i).toString(), com.utkarshnew.android.address.model.Data.class);
                            addresslist.add(address);

                            if (!utkashRoom.getAddress().is_address_exit(address.getId())) {
                                AddressTable addressTable = new AddressTable();
                                addressTable.setAddress_id(address.getId() == null ? "" : address.getId());
                                addressTable.setAddress_one(address.getAddress().getAddressOne() == null ? "" : address.getAddress().getAddressOne());
                                addressTable.setAddress_two(address.getAddress().getAddressTwo() == null ? "" : address.getAddress().getAddressTwo());
                                addressTable.setName(address.getAddress().getName() == null ? "" : address.getAddress().getName());
                                addressTable.setCity(address.getAddress().getCity() == null ? "" : address.getAddress().getCity());
                                addressTable.setState(address.getAddress().getState() == null ? "" : address.getAddress().getState());
                                addressTable.setPhone(address.getAddress().getPhone() == null ? "" : address.getAddress().getPhone());
                                addressTable.setCountry("India");
                                addressTable.setCity_id(address.getAddress().getCity_id() == null ? "" : address.getAddress().getCity_id());
                                addressTable.setState_id(address.getAddress().getState_id() == null ? "" : address.getAddress().getState_id());
                                addressTable.setPincode(address.getAddress().getPincode() == null ? "" : address.getAddress().getPincode());
                                addressTable.setDelivery_price(address.getAddress().getDelivery_price().equals("") ? "0" : address.getAddress().getDelivery_price());
                                utkashRoom.getAddress().addAddress(addressTable);
                            }
                        }
                        bottomsheetAddress(activity, addresslist);

                    } else {
                        if (jsonobject.has(Const.AUTH_CODE)) {
                            RetrofitResponse.GetApiData(activity, jsonobject.has(Const.AUTH_CODE) ? jsonobject.getString(Const.AUTH_CODE) : "", jsonobject.getString(Const.MESSAGE), false);
                        } else {
                            addressDialog(addressdata);
                            RetrofitResponse.GetApiData(activity, jsonobject.has(Const.AUTH_CODE) ? jsonobject.getString(Const.AUTH_CODE) : "", jsonobject.getString(Const.MESSAGE), false);

                        }

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
                            user.setState(selectedstate);
                            SharedPreference.getInstance().setLoggedInUserr(user);
                        }

                        if (pos_txn_id.equalsIgnoreCase("")) {
                            JSONObject data = jsonobject.getJSONObject(Const.DATA);
                            pre_txtid = data.optString("pre_transaction_id");
                            launch_paymentGateway(pre_txtid);
                        } else {
                            utkashRoom.getCourseDetaildata().deletecoursedetail(cousedetail.getData().getCourseDetail().getId(), MakeMyExam.userId);
                            Intent courseList = new Intent(activity, CourseActivity.class);//FRAG_TYPE, Const.SINGLE_COURSE AllCoursesAdapter
                            courseList.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                            courseList.putExtra(Const.COURSE_ID_MAIN, !parentCourseId.equalsIgnoreCase("") ? parentCourseId : cousedetail.getData().getCourseDetail().getId());
                            courseList.putExtra(Const.COURSE_PARENT_ID, "");
                            courseList.putExtra(Const.IS_COMBO, false);
                            courseList.putExtra("course_name", cousedetail.getData().getCourseDetail().getTitle());
                            Helper.gotoActivity_finish(courseList, activity);
                            Toast.makeText(activity, "" + jsonobject.optString(Const.MESSAGE), Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        RetrofitResponse.GetApiData(activity, jsonobject.has(Const.AUTH_CODE) ? jsonobject.getString(Const.AUTH_CODE) : "", jsonobject.getString(Const.MESSAGE), false);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case API.CourseDetail_JS:
                if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                    studyCourseRV.setVisibility(View.VISIBLE);
                    no_data_found_RL.setVisibility(View.GONE);
                    JSONObject jsonObject = jsonobject.optJSONObject(Const.DATA);

                    if (!utkashRoom.getuserwisecourse().is_api_code_exits(MakeMyExam.userId, jsonObject.getJSONObject("course_detail").getString("id"))) {
                        UserWiseCourseTable userWiseCourseTable = new UserWiseCourseTable();
                        userWiseCourseTable.setUserid(MakeMyExam.userId);
                        userWiseCourseTable.setCode("ut_011");
                        userWiseCourseTable.setVersion("0.000");
                        userWiseCourseTable.setExp(String.valueOf(MakeMyExam.getTime_server()));
                        userWiseCourseTable.setMeta_id(jsonObject.getJSONObject("course_detail").getString("id"));
                        utkashRoom.getuserwisecourse().addUser(userWiseCourseTable);
                    }

                    if (!utkashRoom.getCourseDetaildata().isRecordExistsUserId(MakeMyExam.userId, parentCourseId + "_" + jsonObject.getJSONObject("course_detail").getString("id"))) {
                        for (int i = 0; i < jsonObject.getJSONArray("tiles").length(); i++) {
                            CourseDetailTable courseDetailTable = new CourseDetailTable();
                            courseDetailTable.setCourse_title(jsonObject.getJSONObject("course_detail").getString("title"));
                            courseDetailTable.setCourse_id(parentCourseId + "_" + jsonObject.getJSONObject("course_detail").getString("id"));
                            courseDetailTable.setDesc_header_image(jsonObject.getJSONObject("course_detail").getString("desc_header_image"));
                            //  courseDetailTable.setMrp(jsonObject.getJSONObject("course_detail").getString("mrp"));
                            //  courseDetailTable.setCourse_sp(jsonObject.getJSONObject("course_detail").getString("course_sp"));
                            courseDetailTable.setValidity(jsonObject.getJSONObject("course_detail").getString("validity"));
                            courseDetailTable.setIs_purchased(jsonObject.getJSONObject("course_detail").getString("is_purchased"));
                            //    courseDetailTable.setTax(jsonObject.getJSONObject("course_detail").getString("tax"));
                            courseDetailTable.setView_type(jsonObject.getJSONObject("course_detail").getString("view_type"));
                            courseDetailTable.setAuthor_title(jsonObject.getJSONObject("course_detail").getJSONObject("author").getString("title"));
                            courseDetailTable.setTile_id(jsonObject.getJSONArray("tiles").getJSONObject(i).getString("id"));
                            courseDetailTable.setTile_meta(jsonObject.getJSONArray("tiles").getJSONObject(i).getString("meta"));
                            courseDetailTable.setUser_id(MakeMyExam.userId);
                            courseDetailTable.setTile_revert(jsonObject.getJSONArray("tiles").getJSONObject(i).getString("revert_api"));
                            courseDetailTable.setTile_title(jsonObject.getJSONArray("tiles").getJSONObject(i).getString("tile_name"));
                            courseDetailTable.setType(jsonObject.getJSONArray("tiles").getJSONObject(i).getString("type"));
                            if (jsonObject.getJSONObject("course_detail").has("pricing")) {
                                Type listType = new TypeToken<List<NotesType>>() {
                                }.getType();
                                List<NotesType> notesTypes = new Gson().fromJson(jsonObject.getJSONObject("course_detail").getJSONArray("pricing").toString(), listType);
                                courseDetailTable.setNotes_type(notesTypes);
                            }
                            utkashRoom.getCourseDetaildata().addCoursedetail(courseDetailTable);
                        }
                    }

                    List<CourseDetailTable> courseDetailTable = utkashRoom.getCourseDetaildata().getcoursedetail(parentCourseId + "_" + mainCourseId, MakeMyExam.userId);
                    if (courseDetailTable != null && courseDetailTable.size() > 0) {
                        CourseDetailData courseDetailData = new CourseDetailData();
                        courseDetailData.setTitle(courseDetailTable.get(0).getCourse_title());
                        courseDetailData.setCourseSp(courseDetailTable.get(0).getCourse_sp());
                        if (courseDetailTable.get(0).getNotes_type() != null && courseDetailTable.get(0).getNotes_type().size() > 0) {
                            courseDetailData.setNotes_type(courseDetailTable.get(0).getNotes_type());
                        }
                        Author author = new Author();
                        author.setTitle(courseDetailTable.get(0).getAuthor_title());
                        courseDetailData.setAuthor(author);
                        //  courseDetailData.setMrp(courseDetailTable.get(0).getMrp());
                        // courseDetailData.setTax(courseDetailTable.get(0).getTax());
                        courseDetailData.setValidity(courseDetailTable.get(0).getValidity());
                        String couse[] = courseDetailTable.get(0).getCourse_id().split("_");
                        courseDetailData.setId(couse[1]);
                        //   courseDetailData.setCourseSp(courseDetailTable.get(0).getCourse_sp());
                        courseDetailData.setDescHeaderImage(courseDetailTable.get(0).getDesc_header_image());
                        courseDetailData.setIsPurchased(courseDetailTable.get(0).getIs_purchased());
                        courseDetailData.setViewType(courseDetailTable.get(0).getView_type());
                        cousedetail = new CourseDetail();
                        com.utkarshnew.android.Model.COURSEDETAIL.Data data = new com.utkarshnew.android.Model.COURSEDETAIL.Data();
                        data.setCourseDetail(courseDetailData);
                        List<TilesItem> tilesItem = new ArrayList<>();
                        for (int i = 0; i < courseDetailTable.size(); i++) {
                            TilesItem tilesItems = new TilesItem(courseDetailTable.get(i).getTile_revert(), courseDetailTable.get(i).getTile_title(), courseDetailTable.get(i).getTile_id(), courseDetailTable.get(i).getType(), courseDetailTable.get(i).getTile_meta());
                            tilesItem.add(tilesItems);
                        }
                        data.setTiles(tilesItem);
                        cousedetail.setData(data);

                        if (((CourseActivity) activity).is_coupon) {
                            Intent intent = new Intent(activity, PurchaseActivity.class);
                            intent.putExtra("single_study", cousedetail);
                            Helper.gotoActivity(intent, activity);
                        } else {
                            initButton(cousedetail.getData().getCourseDetail());
                            callForData(cousedetail.getData().getTiles(), cousedetail.getData().getCourseDetail().getIsPurchased());

                        }

                    }

                } else {
                    studyCourseRV.setVisibility(View.GONE);
                    no_data_found_RL.setVisibility(View.VISIBLE);
                    RetrofitResponse.GetApiData(activity, jsonobject.optString(Const.AUTH_CODE), jsonobject.optString(Const.MESSAGE), false);
                }
                break;

            case API.API_GET_MASTER_DATA:
                String skipStr = revertAPI;
                String[] separated = skipStr.split("\\#");
                String isSkip = separated[1];

                if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                    if (typeApi.equalsIgnoreCase(Const.OVERVIEW + tileIdAPI)) {
                        Data data = gson.fromJson(jsonobject.optString(Const.DATA), Data.class);
                        overviewData = new OverviewData();
                        overviewData.setData(data);
                        examPrepItem = new ExamPrepItem();
                        courseDataArrayList = new ArrayList<>();
                        faqData = new ArrayList<>();
                        InitTestAdapter(cousedetail, examPrepItem, overviewData, courseDataArrayList, faqData, typeApi, isSkip);
                    } else if (typeApi.equalsIgnoreCase(Const.FAQ + tileIdAPI)) {
                        JSONArray jsonArray = jsonobject.optJSONArray(Const.DATA);
                        faqData = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            FaqData faqDatas = gson.fromJson(jsonArray.opt(i).toString(), FaqData.class);
                            faqData.add(faqDatas);
                        }
                        overviewData = new OverviewData();
                        examPrepItem = new ExamPrepItem();
                        courseDataArrayList = new ArrayList<>();
                        InitTestAdapter(cousedetail, examPrepItem, overviewData, courseDataArrayList, faqData, typeApi, isSkip);
                    } else if (typeApi.equalsIgnoreCase(Const.COMBO + tileIdAPI)) {
                        JSONArray comboCourse = jsonobject.optJSONArray(Const.DATA);
                        courseDataArrayList = new ArrayList<>();
                        for (int i = 0; i < comboCourse.length(); i++) {
                            Courselist courselist = gson.fromJson(comboCourse.opt(i).toString(), Courselist.class);
                            courseDataArrayList.add(courselist);
                        }
                        faqData = new ArrayList<>();
                        examPrepItem = new ExamPrepItem();
                        overviewData = new OverviewData();
                        InitTestAdapter(cousedetail, examPrepItem, overviewData, courseDataArrayList, faqData, typeApi, isSkip);
                    } else {
                        examPrepItem = gson.fromJson(jsonobject.optString(Const.DATA), ExamPrepItem.class);
                        overviewData = new OverviewData();
                        courseDataArrayList = new ArrayList<>();
                        faqData = new ArrayList<>();
                        if (isSkip.equalsIgnoreCase("3")) {

                            for (Lists lists : examPrepItem.getList()) {
                                if (lists.getFile_type() != null && lists.getFile_type().equalsIgnoreCase("3") ) {
                                    if (!utkashRoom.getOpenHelper().getWritableDatabase().isDbLockedByCurrentThread()) {
                                        if (utkashRoom.getvideoDownloadao().isvideo_exit(lists.getId(), MakeMyExam.userId)) {
                                            VideosDownload videoDownload = utkashRoom.getvideoDownloadao().getvideo_byuserid(lists.getId(), MakeMyExam.userId);
                                            if (videoDownload.getToal_downloadlocale() != null) {
                                                if (lists.getIs_download_available().equalsIgnoreCase("1") && videoDownload.getVideo_status().equalsIgnoreCase("")) {
                                                    lists.setVideo_status("Download");
                                                } else {
                                                    lists.setVideo_status(videoDownload.getVideo_status());
                                                }
                                                lists.setVideo_time(videoDownload.getVideotime());
                                                lists.setVideo_download(videoDownload.getIs_complete().equalsIgnoreCase("1"));
                                                lists.setVideo_currentpos(videoDownload.getVideoCurrentPosition());
                                            }
                                        } else {
                                            if (lists.getIs_download_available().equalsIgnoreCase("1")) {
                                                lists.setVideo_status("Download");
                                                lists.setVideo_download(false);
                                                lists.setVideo_currentpos(0L);
                                            } else {
                                                lists.setVideo_status("");
                                                lists.setVideo_download(false);
                                                lists.setVideo_currentpos(0L);
                                            }
                                        }
                                    }
                                }
                            }

                        }
                        InitTestAdapter(cousedetail, examPrepItem, overviewData, courseDataArrayList, faqData, typeApi, isSkip);
                    }
                } else {
                    if (!isSkip.equalsIgnoreCase("3")) {
                        faqData = new ArrayList<>();
                        overviewData = new OverviewData();
                        examPrepItem = new ExamPrepItem();
                        courseDataArrayList = new ArrayList<>();
                        InitTestAdapter(cousedetail, examPrepItem, overviewData, courseDataArrayList, faqData, typeApi, isSkip);
                    }
                    if (!GenericUtils.isEmpty(jsonobject.optString(Const.AUTH_CODE)))
                        RetrofitResponse.GetApiData(activity, jsonobject.optString(Const.AUTH_CODE), jsonobject.optString(Const.MESSAGE), false);
                }
                break;
        }
    }

    public void bottomsheetAddress(Context context, ArrayList<com.utkarshnew.android.address.model.Data> addresslist) {
        try {
            if (watchlist == null) {
                watchlist = new BottomSheetDialog(context, R.style.videosheetDialogTheme);
                watchlist.setContentView(R.layout.address_recyclerview);
            }
            Objects.requireNonNull(watchlist.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            watchlist.setCancelable(true);
            FrameLayout bottomSheet = watchlist.findViewById(R.id.design_bottom_sheet);

            BottomSheetBehavior.from(Objects.requireNonNull(bottomSheet)).setState(BottomSheetBehavior.STATE_EXPANDED);
            BottomSheetBehavior.from(bottomSheet).setDraggable(false);

            RelativeLayout layout_address = watchlist.findViewById(R.id.layout_address);

            recyclerViewaddress = watchlist.findViewById(R.id.address_reyccler);
            Button raddaddress = watchlist.findViewById(R.id.addaddress);
            Button proceed = watchlist.findViewById(R.id.proceed);
            if (recyclerViewaddress != null) {
                Objects.requireNonNull(recyclerViewaddress).setHasFixedSize(true);
            }
            AddressAdapter addressAdapter = new AddressAdapter(context, addresslist, this);
            recyclerViewaddress.setAdapter(addressAdapter);

            Objects.requireNonNull(raddaddress).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addressdata = null;
                    if (addresslist.size() > 0 && addresslist.size() >= 7) {
                        Toast.makeText(activity, "No More add address", Toast.LENGTH_SHORT).show();
                    } else {
                        for (com.utkarshnew.android.address.model.Data addressdats : addresslist) {
                            addressdats.setSelected(false);
                        }
                        if (recyclerViewaddress.getAdapter() == null) {
                            AddressAdapter addressAdapter = new AddressAdapter(context, addresslist, (onItemSelected) context);
                            recyclerViewaddress.setAdapter(addressAdapter);
                        } else {
                            AddressAdapter addressAdapter = (AddressAdapter) recyclerViewaddress.getAdapter();
                            addressAdapter.updateItem(addresslist);
                        }
                        watchlist.dismiss();
                        watchlist.cancel();
                        addressDialog(addressdata);
                    }

                }
            });
            Objects.requireNonNull(proceed).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddressAdapter addressAdapter = (AddressAdapter) recyclerViewaddress.getAdapter();
                    if (addressAdapter != null && addressAdapter.getOldpos() != -1) {
                        if (addressdata != null) {
                            watchlist.dismiss();
                            watchlist.cancel();
                            addressdata.setSelected(true);
                            Intent intent = new Intent(activity, PurchaseActivity.class);
                            intent.putExtra("single_study", new Gson().toJson(cousedetail));
                            intent.putExtra("address_id", addressdata.getId());
                            intent.putExtra("notes_type", new Gson().toJson(notesType));
                            intent.putExtra("deleivery_charges", deleivery_charges);

                            Helper.gotoActivity(intent, activity);
                        }
                    } else {
                        if (addresslist.size() == 0) {
                            Toast.makeText(context, "Please add address", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(context, "Please select address", Toast.LENGTH_SHORT).show();

                        }
                    }
                  /*  if (addressdata != null && addressdata.getSelected()) {
                        watchlist.dismiss();
                        watchlist.cancel();
                        Intent intent = new Intent(activity, PurchaseActivity.class);
                        intent.putExtra("single_study", new Gson().toJson(cousedetail));
                        intent.putExtra("address_id", addressdata.getId());
                        intent.putExtra("notes_type", new Gson().toJson(notesType));
                        Helper.gotoActivity(intent, activity);
                    } else {
                        Toast.makeText(context, "Please select address", Toast.LENGTH_SHORT).show();
                    }*/

                }
            });
            Objects.requireNonNull(layout_address).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    watchlist.dismiss();
                    watchlist.cancel();
                }
            });

            watchlist.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    watchlist.dismiss();
                    watchlist.cancel();
                }
            });

            if (!watchlist.isShowing()) {
                watchlist.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
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
            float total_amount = Float.parseFloat(cousedetail.getData().getCourseDetail().getMrp()) + Float.parseFloat(cousedetail.getData().getCourseDetail().getTax());
            object.put("amount", Math.round(total_amount * 100));

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
        if (apitype.equalsIgnoreCase(API.CourseDetail_JS)) {
            if (studyCourseRV != null && no_data_found_RL != null) {
                studyCourseRV.setVisibility(View.GONE);
                no_data_found_RL.setVisibility(View.VISIBLE);
            }
        }
    }

    public void callForData(List<TilesItem> tiles, String ispurchased) {
        if (ispurchased.equalsIgnoreCase("0")) {
            if (tiles != null && tiles.size() >= 1) {
                String param = "";
                int pos = 0;
                for (int i = 0; i < tiles.size(); i++) {
                    if (tiles.get(i).getType().equals(Const.OVERVIEW)) {
                        pos = i;
                        param = tiles.get(i).getType() + tiles.get(i).getId();
                        break;
                    }
                }
                if (param.equalsIgnoreCase("")) {
                    callAdapterData(tiles, null, 0);
                } else {
                    callAdapterData(tiles, null, pos);
                }
            } else {
                faqData = new ArrayList<>();
                overviewData = new OverviewData();
                examPrepItem = new ExamPrepItem();
                courseDataArrayList = new ArrayList<>();
                InitTestAdapter(cousedetail, examPrepItem, overviewData, courseDataArrayList, faqData, Const.NO_DATA, "0");
            }
        } else {
            if (tiles != null && tiles.size() >= 1) {
                String param = "";
                int pos = 0;
                for (int i = 0; i < tiles.size(); i++) {
                    if (tiles.get(i).getType().equals(Const.COMBO)) {
                        param = tiles.get(i).getType() + tiles.get(i).getId();
                        pos = i;
                        break;
                    } else if (tiles.get(i).getType().equals(Const.CONTENT)) {
                        param = tiles.get(i).getType() + tiles.get(i).getId();
                        pos = i;
                        break;
                    }
                }
                if (param.equalsIgnoreCase("")) {
                    callAdapterData(tiles, null, 0);
                } else {
                    callAdapterData(tiles, null, pos);
                }
            } else {
                faqData = new ArrayList<>();
                overviewData = new OverviewData();
                examPrepItem = new ExamPrepItem();
                courseDataArrayList = new ArrayList<>();
                InitTestAdapter(cousedetail, examPrepItem, overviewData, courseDataArrayList, faqData, Const.NO_DATA, "0");
            }
        }
    }

    public void callForData(List<TilesItem> tiles) {
        if (tiles != null && tiles.size() >= 1) {
            callAdapterData(tiles, null, 0);
        } else {
            faqData = new ArrayList<>();
            overviewData = new OverviewData();
            examPrepItem = new ExamPrepItem();
            courseDataArrayList = new ArrayList<>();
            InitTestAdapter(cousedetail, examPrepItem, overviewData, courseDataArrayList, faqData, Const.NO_DATA, "0");
        }
    }

    @Override
    public void onTitleClicked(TilesItem cards, List<TilesItem> tiles, int tilePos) {
        callAdapterData(tiles, cards, tilePos);
    }

    public void callAdapterData(List<TilesItem> tiles, TilesItem cards, int pos) {
        Gson gson = new Gson();
        TilesItem currentTileItem;
        tilePos = pos;
        if (cards != null) {
            tileTypeAPI = cards.getType();
            tileIdAPI = cards.getId();
            revertAPI = cards.getRevertApi();
            typeApi = cards.getType() + cards.getId();
            currentTileItem = cards;
        } else {
            tileTypeAPI = tiles.get(pos).getType();
            tileIdAPI = tiles.get(pos).getId();
            revertAPI = tiles.get(pos).getRevertApi();
            typeApi = tiles.get(pos).getType() + tiles.get(pos).getId();
            currentTileItem = tiles.get(pos);
        }

        String skipStr = revertAPI;
        String[] separated = skipStr.split("\\#");
        String isSkip = separated[1];

        if (typeApi.equalsIgnoreCase(Const.OVERVIEW + tileIdAPI)) {
            Data data = gson.fromJson(currentTileItem.getMeta(), Data.class);
            overviewData = new OverviewData();
            overviewData.setData(data);
            examPrepItem = new ExamPrepItem();
            courseDataArrayList = new ArrayList<>();
            faqData = new ArrayList<>();
            InitTestAdapter(cousedetail, examPrepItem, overviewData, courseDataArrayList, faqData, typeApi, isSkip);
        } else if (typeApi.equalsIgnoreCase(Const.COMBO + tileIdAPI)) {
            try {
                JSONObject json = new JSONObject(currentTileItem.getMeta());
                JSONArray comboCourse = json.getJSONArray("list");
                courseDataArrayList = new ArrayList<>();
                for (int i = 0; i < comboCourse.length(); i++) {
                    Courselist courselist = gson.fromJson(comboCourse.opt(i).toString(), Courselist.class);
                    courseDataArrayList.add(courselist);
                }

           /*     Log.d("prince",""+(SingleStudy.parentCourseId.equalsIgnoreCase("") ? cousedetail.getData().getCourseDetail().getId()+"#" : SingleStudy.parentCourseId+"#"));

                List<String>   coursedownlaodtable = utkashRoom.getvideoDownloadao().getlikehistiry( MakeMyExam.userId, SingleStudy.parentCourseId.equalsIgnoreCase("") ? cousedetail.getData().getCourseDetail().getId()+"#" : SingleStudy.parentCourseId+"#");

                Log.d("prince",""+new Gson().toJson(coursedownlaodtable));


                if (coursedownlaodtable.size()>0)
                {
                    for (String courids:coursedownlaodtable)
                    {
                        boolean found=false;

                        for (Courselist courselist:courseDataArrayList)
                        {
                            Log.d("prince",""+courids);

                            if (courids.equalsIgnoreCase(SingleStudy.parentCourseId.equalsIgnoreCase("") ? cousedetail.getData().getCourseDetail().getId() +"#"+courselist.getId() :SingleStudy.parentCourseId+"#"+courselist.getId()))
                            {
                                found=true;
                                Log.d("prince",""+found);
                                break;
                            }
                            else
                            {
                                found=false;
                                Log.d("prince",""+found);

                            }

                        }
                        if(!found)
                        {
                            List<VideosDownload> videosDownloads = utkashRoom.getvideoDownloadao().getallcourse_id(courids,MakeMyExam.userId);
                            if (videosDownloads!=null && videosDownloads.size()>0)
                            {
                                for (VideosDownload videosDownload:videosDownloads)
                                {
                                    File file = new File(activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.Videos/" + videosDownload.getVideo_history() + ".mp4");
                                    File file1 = new File(activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.processing/" + videosDownload.getVideo_history() + ".mp4");
                                    if (file.exists())
                                    {
                                        file.delete();
                                    }
                                    if (file1.exists())
                                    {
                                        file1.delete();
                                    }
                                    utkashRoom.getvideoDownloadao().delete(videosDownload.getVideo_id(),videosDownload.getCourse_id());
                                }
                            }
                        }
                    }

                }*/

              /*  List<String>   userHistroyTables = utkashRoom.getuserhistorydao().getlikehistiry( MakeMyExam.userId, SingleStudy.parentCourseId.equalsIgnoreCase("") ? cousedetail.getData().getCourseDetail().getId()+"#" : SingleStudy.parentCourseId+"#");
                if (userHistroyTables.size()>0)
                {
                    for (String courids:userHistroyTables)
                    {
                        boolean found=false;

                        for (Courselist courselist:courseDataArrayList)
                        {
                            if (courids.equalsIgnoreCase(SingleStudy.parentCourseId.equalsIgnoreCase("") ? cousedetail.getData().getCourseDetail().getId() +"#"+courselist.getId() :SingleStudy.parentCourseId+"#"+courselist.getId()))
                            {
                                found=true;
                                break;
                            }
                            else
                            {
                                found=false;
                            }
                        }
                        if(!found)
                        {
                            utkashRoom.getuserhistorydao().delete(courids,MakeMyExam.userId);

                            List<VideosDownload> videosDownloads = utkashRoom.getvideoDownloadao().getallcourse_id(courids,MakeMyExam.userId);
                            if (videosDownloads!=null && videosDownloads.size()>0)
                            {
                                for (VideosDownload videosDownload:videosDownloads)
                                {
                                    File file = new File(activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.Videos/" + videosDownload.getVideo_history() + ".mp4");
                                    File file1 = new File(activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.processing/" + videosDownload.getVideo_history() + ".mp4");
                                    if (file.exists())
                                    {
                                        file.delete();
                                    }
                                    if (file1.exists())
                                    {
                                        file1.delete();
                                    }
                                    utkashRoom.getvideoDownloadao().delete(videosDownload.getVideo_id(),videosDownload.getCourse_id());
                                }
                            }
                        }
                    }

                }

                List<String>   coursedownlaodtable = utkashRoom.getvideoDownloadao().getlikehistiry( MakeMyExam.userId, SingleStudy.parentCourseId.equalsIgnoreCase("") ? cousedetail.getData().getCourseDetail().getId()+"#" : SingleStudy.parentCourseId+"#");
                if (coursedownlaodtable.size()>0)
                {
                    for (String courids:coursedownlaodtable)
                    {
                        boolean found=false;

                        for (Courselist courselist:courseDataArrayList)
                        {
                            if (courids.equalsIgnoreCase(SingleStudy.parentCourseId.equalsIgnoreCase("") ? cousedetail.getData().getCourseDetail().getId() +"#"+courselist.getId() :SingleStudy.parentCourseId+"#"+courselist.getId()))
                            {
                                found=true;
                                break;
                            }
                            else
                            {
                                found=false;
                            }
                        }
                        if(!found)
                        {
                            List<VideosDownload> videosDownloads = utkashRoom.getvideoDownloadao().getallcourse_id(courids,MakeMyExam.userId);
                            if (videosDownloads!=null && videosDownloads.size()>0)
                            {
                                for (VideosDownload videosDownload:videosDownloads)
                                {
                                    File file = new File(activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.Videos/" + videosDownload.getVideo_history() + ".mp4");
                                    File file1 = new File(activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.processing/" + videosDownload.getVideo_history() + ".mp4");
                                    if (file.exists())
                                    {
                                        file.delete();
                                    }
                                    if (file1.exists())
                                    {
                                        file1.delete();
                                    }
                                    utkashRoom.getvideoDownloadao().delete(videosDownload.getVideo_id(),videosDownload.getCourse_id());
                                }
                            }
                        }
                    }

                }
                */


                faqData = new ArrayList<>();
                examPrepItem = new ExamPrepItem();
                overviewData = new OverviewData();
                InitTestAdapter(cousedetail, examPrepItem, overviewData, courseDataArrayList, faqData, typeApi, isSkip);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (typeApi.equalsIgnoreCase(Const.FAQ + tileIdAPI)) {
            JSONObject json = null;
            try {
                json = new JSONObject(currentTileItem.getMeta());
                JSONArray jsonArray = json.getJSONArray("list");
                faqData = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    FaqData faqDatas = gson.fromJson(jsonArray.opt(i).toString(), FaqData.class);
                    faqData.add(faqDatas);
                }
                overviewData = new OverviewData();
                examPrepItem = new ExamPrepItem();
                courseDataArrayList = new ArrayList<>();
                InitTestAdapter(cousedetail, examPrepItem, overviewData, courseDataArrayList, faqData, typeApi, isSkip);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            if (isSkip.equalsIgnoreCase("3")) {
                NetworkAPICall(API.API_GET_MASTER_DATA, typeApi, true, false, false);
            } else {
                examPrepItem = new ExamPrepItem();
                overviewData = new OverviewData();
                courseDataArrayList = new ArrayList<>();
                faqData = new ArrayList<>();
                if (isSkip.equalsIgnoreCase("3")) {
                    for (Lists lists : examPrepItem.getList()) {
                        if (lists.getFile_type() != null && lists.getFile_type().equalsIgnoreCase("3")) {
                            if (!utkashRoom.getOpenHelper().getWritableDatabase().isDbLockedByCurrentThread()) {
                                if (utkashRoom.getvideoDownloadao().isvideo_exit(lists.getId(), MakeMyExam.userId)) {
                                    VideosDownload videoDownload = utkashRoom.getvideoDownloadao().getvideo_byuserid(lists.getId(), MakeMyExam.userId);
                                    if (videoDownload.getToal_downloadlocale() != null) {
                                        if (lists.getIs_download_available().equalsIgnoreCase("1") && videoDownload.getVideo_status().equalsIgnoreCase("")) {
                                            lists.setVideo_status("Download");
                                        } else {
                                            lists.setVideo_status(videoDownload.getVideo_status());
                                        }
                                        lists.setVideo_time(videoDownload.getVideotime());
                                        lists.setVideo_download(videoDownload.getIs_complete().equalsIgnoreCase("1"));
                                        lists.setVideo_currentpos(videoDownload.getVideoCurrentPosition());
                                    }
                                } else {
                                    if (lists.getIs_download_available().equalsIgnoreCase("1")) {
                                        lists.setVideo_status("Download");
                                        lists.setVideo_download(false);
                                        lists.setVideo_currentpos(0L);
                                    } else {
                                        lists.setVideo_status("");
                                        lists.setVideo_download(false);
                                        lists.setVideo_currentpos(0L);
                                    }
                                }
                            }
                        }
                    }
                }
                if (isSkip.equalsIgnoreCase("1")) {
                    try {
                        JSONObject json = new JSONObject(currentTileItem.getMeta());
                        if (json.getJSONArray("list").length() > 0) {
                            JSONArray testseries = json.getJSONArray("list");
                            ArrayList<Lists> testlist = new ArrayList<>();
                            for (int i = 0; i < testseries.length(); i++) {
                                if (testseries.optJSONObject(i).getJSONArray("list").length() > 0) {
                                    for (int j = 0; j < testseries.optJSONObject(i).getJSONArray("list").length(); j++) {
                                        Lists testdata = gson.fromJson(testseries.optJSONObject(i).getJSONArray("list").get(j).toString(), Lists.class);
                                        testlist.add(testdata);
                                    }
                                }
                            }
                            examPrepItem.setList(testlist);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        JSONObject json = new JSONObject(currentTileItem.getMeta());
                        JSONArray testseries = json.getJSONArray("list");
                        ArrayList<Lists> testlist = new ArrayList<>();
                        for (int i = 0; i < testseries.length(); i++) {
                            Lists testdata = gson.fromJson(testseries.opt(i).toString(), Lists.class);
                            testlist.add(testdata);
                        }
                        examPrepItem.setList(testlist);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (isSkip.equalsIgnoreCase("0")) {
                        try {
                            JSONObject json = new JSONObject(currentTileItem.getMeta());
                            if (json.getJSONArray("list").length() > 0) {
                                JSONArray testseries = json.getJSONArray("list");
                                for (int i = 0; i < testseries.length(); i++) {
                                    ArrayList<Lists> testlist = new ArrayList<>();
                                    if (testseries.optJSONObject(i).getJSONArray("list").length() > 0) {
                                        for (int j = 0; j < testseries.optJSONObject(i).getJSONArray("list").length(); j++) {
                                            Lists testdata = gson.fromJson(testseries.optJSONObject(i).getJSONArray("list").get(j).toString(), Lists.class);
                                            testlist.add(testdata);
                                        }
                                    }
                                    examPrepItem.getList().get(i).setList(testlist);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                InitTestAdapter(cousedetail, examPrepItem, overviewData, courseDataArrayList, faqData, typeApi, isSkip);
            }
        }
    }

    private void InitTestAdapter(CourseDetail cousedetail, ExamPrepItem examPrepItem, OverviewData overviewData, ArrayList<Courselist> courseDataArrayList, ArrayList<FaqData> faqData, String type, String isSkip) {
        try {
            singleStudyAdapter = new SingleStudyAdapter(activity, cousedetail, examPrepItem, overviewData, courseDataArrayList, faqData, SingleStudy.this, parentCourseId, isCombo, isSkip, tilePos, tileIdAPI, tileTypeAPI, revertAPI, this);
            singleStudyAdapter.contentType = type;
            studyCourseRV.setAdapter(singleStudyAdapter);
            studyCourseRV.setNestedScrollingEnabled(false);

            ((CourseActivity) activity).setToolbarTitle(course_name);
            ((CourseActivity) activity).shareIV.setVisibility(View.VISIBLE);
            setMargins(((CourseActivity) activity).shareIV, 0, 0, 16, 0);
        } catch (Exception ex) {
            Log.d("ex", "InitTestAdapter: " + ex.getMessage());
        }
    }

    private void initButton(CourseDetailData course) {


        if (!isCombo) {
            if (course.getIsPurchased().equalsIgnoreCase("1")) {
                buttonLow.setVisibility(View.GONE);
            } else {
                buttonLow.setVisibility(View.VISIBLE);
                float total_amount = 0;

                if (course.getNotes_type() != null && course.getNotes_type().size() > 1) {

                    course.getNotes_type().get(0).setIs_select(true);
                    notesType = course.getNotes_type().get(0);

                    NotesTypeAdapter notesTypeAdapter = new NotesTypeAdapter(activity, course.getNotes_type(), this);
                    notes_recycler_view.setAdapter(notesTypeAdapter);

                    total_amount = Float.parseFloat(notesType.getMrp()) + Float.parseFloat(notesType.getTax());

                    notes_recycler_view.setVisibility(View.VISIBLE);
                    if ((int) total_amount == 0) {
                        buyNowBtn.setVisibility(View.GONE);
                        mrpCutTV.setVisibility(View.GONE);
                        priceLL.setVisibility(View.GONE);
                        myLibBtn.setVisibility(View.VISIBLE);
                       // myLibBtn.setOnClickListener(this::onClick);
                    } else {
                        myLibBtn.setVisibility(View.GONE);
                        buyNowBtn.setVisibility(View.VISIBLE);
                        priceLL.setVisibility(View.VISIBLE);
                        price.setText(String.format("%s %s %s", activity.getResources().getString(R.string.rs), "" + ((int) total_amount), "/-"));
                        setPrice(notesType);
                    }

                } else if (course.getNotes_type() != null && course.getNotes_type().size() == 1) {
                    notesType = course.getNotes_type().get(0);

                    total_amount = Float.parseFloat(notesType.getMrp()) + Float.parseFloat(notesType.getTax());

                    notes_recycler_view.setVisibility(View.GONE);

                    if ((int) total_amount == 0) {
                        buyNowBtn.setVisibility(View.GONE);
                        mrpCutTV.setVisibility(View.GONE);
                        priceLL.setVisibility(View.GONE);
                        myLibBtn.setVisibility(View.VISIBLE);
                        //myLibBtn.setOnClickListener(this::onClick);
                    } else {
                        myLibBtn.setVisibility(View.GONE);
                        buyNowBtn.setVisibility(View.VISIBLE);
                        priceLL.setVisibility(View.VISIBLE);
                        price.setText(String.format("%s %s %s", activity.getResources().getString(R.string.rs), "" + ((int) total_amount), "/-"));
                        setPrice(notesType);
                    }
                }
              /*  if ((int) total_amount == 0) {
                    buyNowBtn.setVisibility(View.GONE);
                    mrpCutTV.setVisibility(View.GONE);
                    priceLL.setVisibility(View.GONE);
                    notes_recycler_view.setVisibility(View.GONE);
                    notes_layout.setVisibility(View.GONE);
                    myLibBtn.setVisibility(View.VISIBLE);
                    myLibBtn.setOnClickListener(this::onClick);
                } else {
                    buyNowBtn.setVisibility(View.VISIBLE);
                    priceLL.setVisibility(View.VISIBLE);
                    notes_layout.setVisibility(View.GONE);
                    notes_recycler_view.setVisibility(View.VISIBLE);
                    if (course.getNotes_type() != null && course.getNotes_type().size() > 0) {
                        course.getNotes_type().get(0).setIs_select(true);
                        type = course.getNotes_type().get(0).getId();

                        price.setText(String.format("%s %s %s", activity.getResources().getString(R.string.rs), "" + ((int) Float.parseFloat(course.getNotes_type().get(0).getMrp()) + Float.parseFloat(course.getNotes_type().get(0).getTax())), "/-"));

                        NotesTypeAdapter notesTypeAdapter = new NotesTypeAdapter(activity, course.getNotes_type(), this);
                        notes_recycler_view.setAdapter(notesTypeAdapter);
                    } else {
                        price.setText(String.format("%s %s %s", activity.getResources().getString(R.string.rs), "" + ((int) total_amount), "/-"));
                    }
                    setPrice(course);

                    myLibBtn.setVisibility(View.GONE);

                }*/
            }
        } else {
            buttonLow.setVisibility(View.GONE);
        }


        myLibBtn.setOnClickListener(new OnSingleClickListener(() -> {
            if(user.getName()==null || user.getName().equals(""))
            {
                UpdateProfileUi updateProfileUi=new UpdateProfileUi(activity,this);
                updateProfileUi.ShowLayout();
            }
            else
            {
                NetworkAPICall(API.free_transaction, "", true, false, false);
            }

            return null;
        }));


    }

    private void setPrice(NotesType course) {
        if (Integer.parseInt(course.getCourse_sp()) > 0) {
            mrpCutTV.setText(String.format("%s %s %s", activity.getResources().getString(R.string.rs), course.getCourse_sp().trim(), "/-"), TextView.BufferType.SPANNABLE);
            StrikethroughSpan STRIKE_THROUGH_SPAN = new StrikethroughSpan();
            Spannable spannable = (Spannable) mrpCutTV.getText();
            mrpCutTV.setVisibility(View.VISIBLE);
            spannable.setSpan(STRIKE_THROUGH_SPAN, 2, new String(course.getCourse_sp()).length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            mrpCutTV.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buyNowBtn:
                buynow();
                break;
            case R.id.myLibBtn:

                if(user.getName()==null || user.getName().equals(""))
                {
                    UpdateProfileUi updateProfileUi=new UpdateProfileUi(activity,this);
                    updateProfileUi.ShowLayout();
                }
                else
                {
                    NetworkAPICall(API.free_transaction, "", true, false, false);
                }
                break;
        }
    }

    public void buynow() {
        if (notesType.getAdd_required().equals("1")) {
            if (addressdata != null && addressdata.getSelected()) {
                Intent intent = new Intent(activity, PurchaseActivity.class);
                intent.putExtra("single_study", new Gson().toJson(cousedetail));
                intent.putExtra("address_id", addressdata.getId());
                intent.putExtra("deleivery_charges", deleivery_charges);

                intent.putExtra("notes_type", new Gson().toJson(notesType));
                Helper.gotoActivity(intent, activity);
            } else {
                if (addresslist.size() > 0) {
                    if (recyclerViewaddress != null) {
                        if (recyclerViewaddress.getAdapter() != null && recyclerViewaddress.getAdapter() instanceof AddressAdapter) {
                            for (com.utkarshnew.android.address.model.Data addressdats : addresslist) {
                                addressdats.setSelected(false);
                            }
                            bottomsheetAddress(activity, addresslist);
                        } else {
                            AddressAdapter addressAdapter = new AddressAdapter(activity, addresslist, this);
                            recyclerViewaddress.setAdapter(addressAdapter);
                        }
                    } else {
                        bottomsheetAddress(activity, addresslist);

                    }

                } else {
                    if (utkashRoom.getAddress().getAllAddress() != null && utkashRoom.getAddress().getAllAddress().size() > 0) {
                        List<AddressTable> addressTables = utkashRoom.getAddress().getAllAddress();
                        for (AddressTable addressTable : addressTables) {
                            Address address = new Address(addressTable.getAddress_one(), addressTable.getAddress_two(), addressTable.getCity(), "INDIA", addressTable.getName(), addressTable.getPhone(), addressTable.getPincode(), addressTable.getState(), addressTable.getDelivery_price(), addressTable.getState_id(), addressTable.getCity_id());
                            com.utkarshnew.android.address.model.Data data = new com.utkarshnew.android.address.model.Data(address, addressTable.getAddress_id(), false);
                            addresslist.add(data);
                        }
                        bottomsheetAddress(activity, addresslist);
                    } else {
                        NetworkAPICall(API.get_my_addresses, "", true, false, false);
                    }
                }
            }
        } else {
            Intent intent = new Intent(activity, PurchaseActivity.class);
            intent.putExtra("single_study", new Gson().toJson(cousedetail));
            intent.putExtra("notes_type", new Gson().toJson(notesType));
            intent.putExtra("deleivery_charges", "0");

            Helper.gotoActivity(intent, activity);
        }
    }

    private void addressDialog(com.utkarshnew.android.address.model.Data data) {
        try {

            Dialog addressdailog;

            if (SystemClock.elapsedRealtime() - mLastClickTime_frame5 < 1000) {
                return;
            }
            mLastClickTime_frame5 = SystemClock.elapsedRealtime();

            addressdailog = new Dialog(activity);
            addressdailog.setContentView(R.layout.address_dailog);
            ImageView cancel = addressdailog.findViewById(R.id.cancel);
            TextView btn_submit = addressdailog.findViewById(R.id.btn_submit);
            EditText address1 = addressdailog.findViewById(R.id.address1);
            EditText name = addressdailog.findViewById(R.id.name);
            EditText address2 = addressdailog.findViewById(R.id.address2);
            city = addressdailog.findViewById(R.id.city);
            state = addressdailog.findViewById(R.id.state);
            EditText pincode = addressdailog.findViewById(R.id.pincode);
            EditText mobile = addressdailog.findViewById(R.id.mobile);


            state.setOnClickListener(view -> {
                if (states != null && states.getData().size() > 0) {
                    clickType = "1";
                    PopupMenu popupMenu = new PopupMenu(activity, state, Gravity.LEFT);
                    for (int i = 0; i < states.getData().size(); i++) {
                        popupMenu.getMenu().add(states.getData().get(i).getName());
                    }
                    popupMenu.setOnMenuItemClickListener(this);
                    popupMenu.show();
                } else {
                    NetworkAPICall(API.API_STATE, "", true, false, false);

                }
            });
            city.setOnClickListener(view -> {
                if (cities != null && cities.getData().size() > 0) {
                    clickType = "2";
                    PopupMenu popupMenu = new PopupMenu(activity, city, Gravity.LEFT);
                    for (int i = 0; i < cities.getData().size(); i++) {
                        popupMenu.getMenu().add(cities.getData().get(i).getName());
                    }
                    popupMenu.setOnMenuItemClickListener(this);
                    popupMenu.show();
                } else {
                    if (!state_id.equals("")) {
                        NetworkAPICall(API.API_CITY, "", true, false, false);

                    }

                }
            });

            addressdailog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            addressdailog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
            addressdailog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            addressdailog.getWindow().setGravity(Gravity.CENTER);
            addressdailog.setCancelable(false);
            addressdailog.setCanceledOnTouchOutside(false);

            if (data != null) {
                if (data.getAddress().getAddressOne() != null) {
                    address1.setText(data.getAddress().getAddressOne());

                }
                if (data.getAddress().getName() != null) {
                    name.setText(data.getAddress().getName());

                }
                if (data.getAddress().getAddressTwo() != null) {
                    address2.setText(data.getAddress().getAddressTwo());

                }
                if (data.getAddress().getCity() != null && !data.getAddress().getCity().equals("")) {
                    city_name = data.getAddress().getCity();
                    city.setText(data.getAddress().getCity());
                }
                if (data.getAddress().getCity_id() != null && !data.getAddress().getCity_id().equals("")) {
                    city_id = data.getAddress().getCity_id();
                }    if (data.getAddress().getState_id() != null && !data.getAddress().getState_id().equals("")) {
                    state_id = data.getAddress().getState_id();
                }
                if (data.getAddress().getState() != null && !data.getAddress().getState().equals("")) {
                    state_name = data.getAddress().getState();
                    state.setText(data.getAddress().getState());
                }
                if (data.getAddress().getPincode() != null) {
                    pincode.setText(data.getAddress().getPincode());
                }
                if (data.getAddress().getPhone() != null && !data.getAddress().getPhone().equals("null")) {
                    mobile.setText(data.getAddress().getPhone());
                }

            }
            NetworkAPICall(API.API_STATE, "", true, false, false);


            Objects.requireNonNull(btn_submit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (TextUtils.isEmpty(name.getText().toString())) {
                        Toast.makeText(activity, "Name field is required", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(address1.getText().toString())) {
                        Toast.makeText(activity, "Address field is required", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(state_id)) {
                        Toast.makeText(activity, "State field is required", Toast.LENGTH_SHORT).show();

                    } else if (TextUtils.isEmpty(city_id)) {
                        Toast.makeText(activity, "City field is required", Toast.LENGTH_SHORT).show();

                    } else if (TextUtils.isEmpty(pincode.getText().toString())) {
                        Toast.makeText(activity, "Pincode field is required", Toast.LENGTH_SHORT).show();
                    }
                    else if (pincode.getText().toString().length()<6) {
                        Toast.makeText(activity, "Please enter valid pincode", Toast.LENGTH_SHORT).show();
                    }
                    else if (TextUtils.isEmpty(mobile.getText().toString().trim())) {
                        Toast.makeText(activity, "Mobile field is required", Toast.LENGTH_SHORT).show();

                    } else if (!Helper.isValidMobile(mobile.getText().toString().trim())) {
                        Toast.makeText(activity, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
                    } else {
                        addressdailog.dismiss();
                        addressdailog.cancel();
                        address = new EncryptionData();
                        address.setCity(city.getText().toString().trim());
                        address.setPhone(mobile.getText().toString().trim());
                        address.setName(name.getText().toString().trim());
                        address.setState(state.getText().toString().toLowerCase(Locale.ROOT));
                        address.setPincode(pincode.getText().toString());
                        address.setAddressOne(address1.getText().toString().trim());
                        address.setAddressTwo(address2.getText().toString().trim());
                        address.setDelivery_price(deleivery_charges);
                        address.setState_id(state_id);
                        address.setCity_id(city_id);

                        if (data != null && !data.getId().equals("")) {
                            address.setId(data.getId());
                        } else {
                            address.setId("");
                        }
                        address.setCountry("INDIA");

                        NetworkAPICall(API.add_update_address, "", true, false, false);

                    }


                }
            });
            Objects.requireNonNull(cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    is_update = false;
                    addressdailog.dismiss();
                    addressdailog.cancel();

                    city_name = "";
                    state_name = "";

                }
            });

            addressdailog.show();
            addressdailog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    addressdailog.dismiss();
                    addressdailog.cancel();
                }
            });
        } catch (Exception e) {
            is_update = false;
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(String pos_txn_id) {
        this.pos_txn_id = pos_txn_id;
        NetworkAPICall(API.int_payment, "", true, false, false);
    }

    @Override
    public void onFailure(String pot_txt_id) {

    }


    @Override
    public void itemSelect(int pos, @NonNull com.utkarshnew.android.address.model.Data data) {
        addressdata = data;
        NetworkAPICall(API.get_delivery_charges, "", true, false, false);

    }

    @Override
    public void itemClick(int pos, @NonNull com.utkarshnew.android.address.model.Data data) {
        is_update = true;
        addressdata = data;
        if (watchlist != null) {
            watchlist.dismiss();
            watchlist.cancel();
        }
        addressDialog(data);
    }

    @Override
    public void ItemClick(int pos, @NonNull NotesType notesTypedata) {
        notesType = notesTypedata;

        float total_amount = Float.parseFloat(notesType.getMrp()) + Float.parseFloat(notesType.getTax());

        if ((int) total_amount == 0) {
            buyNowBtn.setVisibility(View.GONE);
            mrpCutTV.setVisibility(View.GONE);
            priceLL.setVisibility(View.GONE);
            myLibBtn.setVisibility(View.VISIBLE);
           // myLibBtn.setOnClickListener(this::onClick);

        } else {

            myLibBtn.setVisibility(View.GONE);
            buyNowBtn.setVisibility(View.VISIBLE);
            priceLL.setVisibility(View.VISIBLE);
            price.setText(String.format("%s %s %s", activity.getResources().getString(R.string.rs), "" + ((int) total_amount), "/-"));
            setPrice(notesType);

        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item != null) {
            if (clickType.equals("1")) {
                for (StatesCitiesData statesCitiesData : states.getData()) {
                    if (item.getTitle().equals(statesCitiesData.getName())) {
                        state_id = "" + statesCitiesData.getId();
                        state_name = "" + statesCitiesData.getName();
                        state.setText(state_name);
                        city.setText("");
                        city_name = "";
                        city_id = "";
                        NetworkAPICall(API.API_CITY, "", true, false, false);
                        break;
                    }
                }


            } else if (clickType.equals("2")) {
                for (StatesCitiesData statesCitiesData : cities.getData()) {
                    if (item.getTitle().equals(statesCitiesData.getName())) {
                        city_name = "" + statesCitiesData.getName();
                        city_id = "" + statesCitiesData.getId();
                        city.setText(city_name);
                        break;
                    }
                }
            }

        }
        return false;
    }

    @Override
    public void delete_address(int pos, @NonNull com.utkarshnew.android.address.model.Data data) {
        addressdata = data;
        NetworkAPICall(API.delete_my_address, "", true, false, false);

    }

    @Override
    public void getUpdatedData(String name, String emailid, String mobile, String state, String district)
    {
        this.name=name;
        this.email=emailid;
        this.mobile=mobile;
        this.selectedstate=state;
        this.district=district;
        NetworkAPICall(API.free_transaction, "", true, false, false);
    }
}