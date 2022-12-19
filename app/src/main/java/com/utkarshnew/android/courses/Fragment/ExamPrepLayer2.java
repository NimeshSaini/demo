package com.utkarshnew.android.courses.Fragment;

import static com.utkarshnew.android.courses.Fragment.SingleStudy.parentCourseId;
import static com.utkarshnew.android.DownloadServices.VideoDownloadService.RES_ID;
import static com.utkarshnew.android.DownloadServices.VideoDownloadService.VIDEO_DOWNLOAD_SUCCESSFUL;
import static com.utkarshnew.android.Utils.Helper.convertDpToPixel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.razorpay.Checkout;
import com.utkarshnew.android.courses.Activity.CourseActivity;
import com.utkarshnew.android.courses.adapter.ExamPrepLayer3Adapter;
import com.utkarshnew.android.courses.Interfaces.OnSuccessListner;
import com.utkarshnew.android.Download.Audio.AudioPlayerService;
import com.utkarshnew.android.DownloadServices.VideoDownloadService;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.home.Constants;
import com.utkarshnew.android.Model.COURSEDETAIL.CourseDetail;
import com.utkarshnew.android.Model.COURSEDETAIL.CourseDetailData;
import com.utkarshnew.android.Model.COURSEDETAIL.TilesItem;
import com.utkarshnew.android.Model.Courses.ExamPrepItem;
import com.utkarshnew.android.Model.Courses.Lists;
import com.utkarshnew.android.Model.Courses.quiz.Quiz_Basic_Info;
import com.utkarshnew.android.Model.PoJoModel.FreeCourseOnePOJO;
import com.utkarshnew.android.Model.Video;
import com.utkarshnew.android.Payment.PurchaseActivity;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.home.liveclasses.PayloadData;
import com.utkarshnew.android.table.VideosDownload;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.GenericUtils;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.LinearLayoutManagerWithSmoothScroller;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.MainFragment;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;
import com.utkarshnew.android.Utils.SharedPreference;
import com.utkarshnew.android.Webview.RevisionActivity;
import com.utkarshnew.android.Webview.SearchRevisionVideosActivity;
import com.utkarshnew.android.helpChat.HelpQueryActivity;
import com.utkarshnew.android.helpChat.HelpSupportActivity;
import com.utkarshnew.android.helpChat.model.HelpSupportChatModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Timer;

import retrofit2.Call;

public class ExamPrepLayer2 extends MainFragment implements OnSuccessListner {
    public String contentType, contentTypeTile, title, search_title = "";
    public String tile_id = "";
    String file_type_attributes = "";
    Activity activity;
    Lists list;
    String total;
    ExamPrepItem prevexamPrepItem;
    TextView price, mrpCutTV;
    RecyclerView tileRv;
    FrameLayout parentLL;
    RelativeLayout buttonLow;
    TextView buyNowBtn;
    LinearLayout priceLL;
    private String pre_txtid = "";
    ProgressBar paginationLoader;
    public UtkashRoom utkashRoom = UtkashRoom.getAppDatabase(MakeMyExam.getAppContext());

    private ArrayList<Video> seleced_tile_list = new ArrayList<>();
    private ArrayList<Video> custom_video_list = new ArrayList<>();
    private ArrayList<Video> custom_link_list = new ArrayList<>();
    ArrayList<TilesItem> cardsArrayList;
    Button myLibBtn;
    CourseDetail singleStudy;

    ExamPrepLayer3Adapter examPrepLayer3Adapter;
    RecyclerView examPrepLayerRV;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Quiz_Basic_Info> quizBasicInfoArrayList;
    ArrayList<Video> videoArrayList;
    public static ArrayList<Video> actual_videolist = new ArrayList<>();

    boolean isCombo = false;
    String tileTypeAPI;
    String tileIdAPI;
    String revertAPI;
    public String pos_txn_id = "";
    Lists listSubject;
    String is_purchase = "";
    //////////////
    TileItemsAdapter tileItemsAdapter;
    FloatingActionMenu allResourceAddMenu;
    FloatingActionButton addRelatedVideoBtn, addRelatedReferenceBtn, addRevisionBtn, addSupport;

    private int mPage = 1;
    boolean status = false;
    private NestedScrollView nestedScrollView;
    private boolean isPaginationAvailable = true;
    RelativeLayout no_data_found_RL;
    Button backBtn;

    public UtkashRoom myDBClass = UtkashRoom.getAppDatabase(MakeMyExam.getAppContext());

    Timer myTimer;

    public ExamPrepLayer2() {

    }

    public static ExamPrepLayer2 newInstance(ExamPrepItem examPrepItem, Lists main_id, Lists listSubject, String contentType, String title, String totals, CourseDetail singleStudy, boolean isCombo, String tileIdAPI, String tileTypeAPI, String revertAPI, String serach_title) {
        Log.d("responsee", "ExamPrepLayer2:contentType:" + contentType);
        ExamPrepLayer2 examPrepLayer2 = new ExamPrepLayer2();
        Bundle args = new Bundle();
        args.putSerializable(Const.SINGLE_STUDY_DATA, singleStudy);
        args.putSerializable(Const.EXAMPREP, examPrepItem);
        args.putSerializable(Const.LIST, main_id);
        args.putSerializable(Const.LIST_SUBJECT, listSubject);
        args.putSerializable(Const.TEST_TYPE, totals);
        args.putString(Const.TITLE, title);
        args.putString(Const.CONTENT_TYPE, contentType);
        args.putBoolean(Const.IS_COMBO, isCombo);
        args.putString(Const.TILE_ID, tileIdAPI);
        args.putString(Const.TILE_TYPE, tileTypeAPI);
        args.putString(Const.REVERT_API, revertAPI);
        args.putString("search_title", serach_title);
        examPrepLayer2.setArguments(args);
        return examPrepLayer2;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exam_prep_layer2, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        Constants.revison_set = false;
        Constants.REFRESHPAGE = "false";
        Constants.pos = "";
        quizBasicInfoArrayList = new ArrayList<>();
        videoArrayList = new ArrayList<>();
        if (getArguments() != null) {
            isCombo = getArguments().getBoolean(Const.IS_COMBO);
            list = (Lists) getArguments().getSerializable(Const.LIST);
            if (getArguments().getSerializable(Const.TEST_TYPE) != null) {
                total = getArguments().getString(Const.TEST_TYPE);
            } else {
                total = "0";
            }
            listSubject = (Lists) getArguments().getSerializable(Const.LIST_SUBJECT);

            contentType = getArguments().getString(Const.CONTENT_TYPE);
            search_title = getArguments().getString("search_title");
            title = getArguments().getString(Const.TITLE);
            tileIdAPI = getArguments().getString(Const.TILE_ID);
            tileTypeAPI = getArguments().getString(Const.TILE_TYPE);
            revertAPI = getArguments().getString(Const.REVERT_API);
            prevexamPrepItem = (ExamPrepItem) getArguments().getSerializable(Const.EXAMPREP);
            singleStudy = (CourseDetail) getArguments().getSerializable(Const.SINGLE_STUDY_DATA);
            if (singleStudy == null) {
                singleStudy = new CourseDetail();
            } else {
                is_purchase = singleStudy.getData().getCourseDetail().getIsPurchased();
            }
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


      /*  myTimer = new Timer ();
        TimerTask myTask = new TimerTask () {
            @Override
            public void run () {
                try {
                    File root;
                    root = new File(activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                            + "/UTKARSH_TEST/", MakeMyExam.userId);
                    if (!root.exists()) {
                        root.mkdirs();
                    }
                    if (Objects.requireNonNull(root.listFiles()).length>0)
                    {
                        ArrayList<MediaFile> mediaFileArrayList = new ArrayList<>();
                        MediaFile mediaFile = new MediaFile();
                        mediaFile.setFile_name(Objects.requireNonNull(root.listFiles())[0].getName());
                        mediaFile.setFile_type(Const.json);
                        mediaFile.setFile(Objects.requireNonNull(root.listFiles())[0].getAbsolutePath());
                        mediaFileArrayList.add(mediaFile);
                        if (Helper.isConnected(activity))
                        {
                            if (!TestService.is_uploading)
                            {
                                Intent i = new Intent(activity,TestService.class);
                                i.putExtra("mediaFileArrayList",mediaFileArrayList);
                                i.putExtra("test_file_name",Objects.requireNonNull(root.listFiles())[0].getName());
                                i.putExtra("test_id", Objects.requireNonNull(root.listFiles())[0].getName().split("_")[1].substring(1));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    activity.startForegroundService(i);
                                } else {
                                    activity.startService(i);
                                }
                            }

                        }else {
                            activity.runOnUiThread(() -> {
                                Snackbar.make(view,"No Internet Connection",Snackbar.LENGTH_SHORT).show();

                            });
                        }
                    }else {
                        myTimer.cancel();
                    }
                } catch (Exception e) {
                    myTimer.cancel();
                    e.printStackTrace();
                }
            }};
        myTimer.scheduleAtFixedRate(myTask , 0L, 20000);

*/

        ((CourseActivity) activity).setToolbarTitle(TextUtils.isEmpty(list.getTitle()) ? singleStudy.getData().getCourseDetail().getTitle() : list.getTitle());

        ((CourseActivity) activity).quizNavigatorIV.setVisibility(View.GONE);
        initView(view);

        if (singleStudy.getData().getCourseDetail().getIsPurchased().equalsIgnoreCase("1")) {
            tileRv.setVisibility(View.VISIBLE);
            cardsArrayList = new ArrayList<>();
            cardsArrayList.add(new TilesItem("0#0#0#0", "All", "0", "all", ""));


            for (TilesItem tilesItem : singleStudy.getData().getTiles()) {
                if (tilesItem.getType().equalsIgnoreCase(Const.OVERVIEW) || tilesItem.getType().equalsIgnoreCase(Const.FAQ) || tilesItem.getType().equalsIgnoreCase(Const.CONTENT) || tilesItem.getType().equalsIgnoreCase(Const.COMBO)) {
                } else {
                    cardsArrayList.add(tilesItem);
                }
            }
            file_type_attributes = "0";
            contentTypeTile = cardsArrayList.get(0).getType() + cardsArrayList.get(0).getId();

            if (cardsArrayList.size() > 0)
                tile_id = cardsArrayList.get(0).getId();

            tileItemsAdapter = new TileItemsAdapter(activity, cardsArrayList, tileRv);
            tileRv.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManagerWithSmoothScroller.HORIZONTAL, false));
            tileRv.setAdapter(tileItemsAdapter);
            tileRv.setNestedScrollingEnabled(false);
        } else {
            tile_id = tileIdAPI;
            tileRv.setVisibility(View.GONE);
        }

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY > oldScrollY) {
                    allResourceAddMenu.setVisibility(View.GONE);
                } else {
                    if (singleStudy.getData().getCourseDetail().getIsPurchased().equalsIgnoreCase("1"))
                        allResourceAddMenu.setVisibility(View.VISIBLE);
                }


                if (v.getChildAt(v.getChildCount() - 1) != null) {
                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) && scrollY > oldScrollY) {
                        if (isPaginationAvailable) {
                            paginationLoader.setVisibility(View.VISIBLE);
                            ++mPage;
                            status = true;
                            hit_api_for_data(false);
                        }
                    }
                }
            }
        });

        /*backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });*/

        hit_api_for_data(true);

    }

    private void hit_api_for_data(boolean showProgress) {
        NetworkAPICall(API.API_GET_MASTER_DATA, contentType, showProgress, false, false);
    }

    private void initView(View view) {
        parentLL = view.findViewById(R.id.parentLL);
        tileRv = view.findViewById(R.id.tileRv);
        examPrepLayerRV = view.findViewById(R.id.examPrepLayerRV);
        nestedScrollView = view.findViewById(R.id.nested_scroll);

        mrpCutTV = view.findViewById(R.id.mrpCutTV);
        price = view.findViewById(R.id.priceTV);
        buyNowBtn = view.findViewById(R.id.buyNowBtn);
        buttonLow = view.findViewById(R.id.buttonLow);
        myLibBtn = view.findViewById(R.id.myLibBtn);
        priceLL = view.findViewById(R.id.priceLL);
        paginationLoader = view.findViewById(R.id.progressBar);
        allResourceAddMenu = view.findViewById(R.id.allResourceAddMenu);
        addRelatedVideoBtn = view.findViewById(R.id.addRelatedVideoBtn);
        addRevisionBtn = view.findViewById(R.id.addRevisionBtn);
        addSupport = view.findViewById(R.id.addSupport);
        addRelatedReferenceBtn = view.findViewById(R.id.addRelatedReferenceBtn);
        allResourceAddMenu.setClosedOnTouchOutside(true);
        allResourceAddMenu.setVisibility(View.VISIBLE);

        no_data_found_RL = view.findViewById(R.id.no_data_found_RL);
        backBtn = view.findViewById(R.id.backBtn);
        backBtn.setVisibility(View.GONE);

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
        allResourceAddMenu.setOnMenuButtonClickListener(v -> toggleIfInternetPresent(allResourceAddMenu, "add_resource"));

       /* examPrepLayerRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    allResourceAddMenu.setVisibility(View.GONE);
                } else {
                    if (singleStudy.getData().getCourseDetail().getIsPurchased().equalsIgnoreCase("1"))
                        allResourceAddMenu.setVisibility(View.VISIBLE);
                }

            }
        });*/

        addRelatedReferenceBtn.setOnClickListener(v -> addRelatedWebRefsDialog());
        addRevisionBtn.setOnClickListener(v -> {
            addRevisionset();
        });
        addSupport.setOnClickListener(v -> {
            getmyQuires();
        });
        addRelatedVideoBtn.setOnClickListener(v -> searchRelatedVideos());
        initButton(singleStudy.getData().getCourseDetail());

        if (buttonLow.getVisibility() == View.VISIBLE) {
            examPrepLayerRV.setPadding(0, 0, 0, Helper.getValueInDP(activity, 70));
        } else {
            examPrepLayerRV.setPadding(0, 0, 0, 0);
        }
        ((CourseActivity) activity).quizNavigatorIV.setOnClickListener(v -> {

        });


        buyNowBtn.setOnClickListener(view1 -> {
                    Intent intent = new Intent(activity, PurchaseActivity.class);
                    intent.putExtra("single_study", singleStudy);
                    Helper.gotoActivity(intent, activity);
                }

        );
        myLibBtn.setOnClickListener(view12 -> NetworkAPICall(API.free_transaction, "", true, false, false));

    }

    private void addRevisionset() {
        if (file_type_attributes.equalsIgnoreCase("0")) {
            if (videoArrayList.size() > 0) {
                toggleIfInternetPresent(allResourceAddMenu, "add_resource");
                Intent intent = new Intent(activity, RevisionActivity.class);
                intent.putExtra("t_id", tile_id);
                intent.putExtra("s_list", videoArrayList);
                intent.putExtra("v_list", videoArrayList);
                intent.putExtra("f_id", videoArrayList.get(0).getId());
                intent.putExtra("c_id", singleStudy.getData().getCourseDetail().getId());
                intent.putExtra("via", "main");
                Helper.gotoActivity(intent, activity);
            } else {
                Toast.makeText(activity, "Atleast One item must be available", Toast.LENGTH_SHORT).show();
            }
        } else if (seleced_tile_list.size() > 0) {
            toggleIfInternetPresent(allResourceAddMenu, "add_resource");
            Intent intent = new Intent(activity, RevisionActivity.class);
            intent.putExtra("t_id", tile_id);
            intent.putExtra("s_list", seleced_tile_list);
            intent.putExtra("v_list", videoArrayList);
            intent.putExtra("f_id", seleced_tile_list.get(0).getId());
            intent.putExtra("c_id", singleStudy.getData().getCourseDetail().getId());
            intent.putExtra("via", "main");
            Helper.gotoActivity(intent, activity);
        } else {
            Toast.makeText(activity, "Atleast One item must be available", Toast.LENGTH_SHORT).show();
        }

    }

    private void searchRelatedVideos() {
        if (file_type_attributes.equalsIgnoreCase("0")) {
            if (videoArrayList.size() > 0) {
                search_title = ((CourseActivity) activity).title;
                toggleIfInternetPresent(allResourceAddMenu, "add_resource");
                String youtubeServiceUrl = "https://www.youtube.com/results?search_query=" + search_title;
                youtubeServiceUrl = youtubeServiceUrl + "&app=mobile";
                Intent openRelatedRefsIntent = new Intent(activity, SearchRevisionVideosActivity.class);
                openRelatedRefsIntent.putExtra("search_query", youtubeServiceUrl);
                openRelatedRefsIntent.putExtra("resourceContext", "video");
                openRelatedRefsIntent.putExtra("intent", "videos");
                openRelatedRefsIntent.putExtra("t_id", tile_id);
                openRelatedRefsIntent.putExtra("s_list", videoArrayList);
                openRelatedRefsIntent.putExtra("v_list", videoArrayList);
                openRelatedRefsIntent.putExtra("f_id", videoArrayList.get(0).getId());
                openRelatedRefsIntent.putExtra("c_id", singleStudy.getData().getCourseDetail().getId());
                startActivity(openRelatedRefsIntent);
            } else {
                Toast.makeText(activity, "Atleast One item must be available", Toast.LENGTH_SHORT).show();
            }
        } else if (seleced_tile_list.size() > 0) {
            search_title = ((CourseActivity) activity).title;
            toggleIfInternetPresent(allResourceAddMenu, "add_resource");
            String youtubeServiceUrl = "https://www.youtube.com/results?search_query=" + search_title;
            youtubeServiceUrl = youtubeServiceUrl + "&app=mobile";
            Intent openRelatedRefsIntent = new Intent(activity, SearchRevisionVideosActivity.class);
            openRelatedRefsIntent.putExtra("search_query", youtubeServiceUrl);
            openRelatedRefsIntent.putExtra("resourceContext", "video");
            openRelatedRefsIntent.putExtra("intent", "videos");
            openRelatedRefsIntent.putExtra("t_id", tile_id);
            openRelatedRefsIntent.putExtra("s_list", seleced_tile_list);
            openRelatedRefsIntent.putExtra("v_list", videoArrayList);
            openRelatedRefsIntent.putExtra("f_id", seleced_tile_list.get(0).getId());
            openRelatedRefsIntent.putExtra("c_id", singleStudy.getData().getCourseDetail().getId());
            startActivity(openRelatedRefsIntent);
        } else {
            Toast.makeText(activity, "Atleast One item must be available", Toast.LENGTH_SHORT).show();
        }
    }

    private void addRelatedWebRefsDialog() {
        if (file_type_attributes.equalsIgnoreCase("0")) {
            if (videoArrayList.size() > 0) {
                search_title = ((CourseActivity) activity).title;
                toggleIfInternetPresent(allResourceAddMenu, "add_resource");
                String youtubeServiceUrl = "https://www.google.com/search?q=" + search_title;
                Intent openRelatedRefsIntent = new Intent(activity, SearchRevisionVideosActivity.class);
                openRelatedRefsIntent.putExtra("search_query", youtubeServiceUrl);
                openRelatedRefsIntent.putExtra("resourceContext", "web");
                openRelatedRefsIntent.putExtra("intent", "webref");
                openRelatedRefsIntent.putExtra("t_id", tile_id);
                openRelatedRefsIntent.putExtra("s_list", videoArrayList);
                openRelatedRefsIntent.putExtra("v_list", videoArrayList);
                openRelatedRefsIntent.putExtra("f_id", videoArrayList.get(0).getId());
                openRelatedRefsIntent.putExtra("c_id", singleStudy.getData().getCourseDetail().getId());
                startActivity(openRelatedRefsIntent);
            } else {
                Toast.makeText(activity, "Atleast One item must be available", Toast.LENGTH_SHORT).show();
            }
        } else if (seleced_tile_list.size() > 0) {
            search_title = ((CourseActivity) activity).title;

            toggleIfInternetPresent(allResourceAddMenu, "add_resource");
            String youtubeServiceUrl = "https://www.google.com/search?q=" + search_title;
            Intent openRelatedRefsIntent = new Intent(activity, SearchRevisionVideosActivity.class);
            openRelatedRefsIntent.putExtra("search_query", youtubeServiceUrl);
            openRelatedRefsIntent.putExtra("resourceContext", "web");
            openRelatedRefsIntent.putExtra("intent", "webref");
            openRelatedRefsIntent.putExtra("t_id", tile_id);
            openRelatedRefsIntent.putExtra("s_list", seleced_tile_list);
            openRelatedRefsIntent.putExtra("v_list", videoArrayList);
            openRelatedRefsIntent.putExtra("f_id", seleced_tile_list.get(0).getId());
            openRelatedRefsIntent.putExtra("c_id", singleStudy.getData().getCourseDetail().getId());
            startActivity(openRelatedRefsIntent);
        } else {
            Toast.makeText(activity, "Atleast One item must be available", Toast.LENGTH_SHORT).show();
        }
    }

    private void toggleIfInternetPresent(FloatingActionMenu floatingActionMenu, String dialogToShow) {
        if (!floatingActionMenu.isOpened()) {
            if (!Helper.isNetworkConnected(activity)) {
                showNoNetDialog(dialogToShow);
            } else {
                floatingActionMenu.toggle(true);
            }
        } else {
            floatingActionMenu.toggle(true);
        }
    }


    public void showNoNetDialog(String action) {
        // Show no net dialog box
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            builder = new AlertDialog.Builder(activity, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(activity);
        }

        if (action != null && action.equalsIgnoreCase("add_resource")) {
            builder.setTitle("No Internet")
                    .setMessage("Adding resources will work only when you are online.")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> dialog.dismiss())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setCancelable(false)
                    .show();
        } else if (action != null && action.equalsIgnoreCase("add_revision")) {
            builder.setTitle("No Internet")
                    .setMessage("Adding revisions will work only when you are online.")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> dialog.dismiss())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setCancelable(false)
                    .show();
        } else if (action != null && action.equalsIgnoreCase("add_video")) {
            builder.setTitle("No Internet")
                    .setMessage("Adding videos will work only when you are online.")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> dialog.dismiss())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setCancelable(false)
                    .show();
        } else if (action != null && action.equalsIgnoreCase("add_solution")) {
            builder.setTitle("No Internet")
                    .setMessage("Adding " + getSolutionsOrWeblinks(false) + " will work only when you are online.")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> dialog.dismiss())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setCancelable(false)
                    .show();
        } else if (action != null && action.equalsIgnoreCase("view_video")) {
            builder.setTitle("No Internet")
                    .setMessage("Adding videos will work only when you are online.")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> dialog.dismiss())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setCancelable(false)
                    .show();
        } else if (action != null && action.equalsIgnoreCase("view_solution")) {
            builder.setTitle("No Internet")
                    .setMessage("Adding " + getSolutionsOrWeblinks(false) + " will work only when you are online.")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> dialog.dismiss())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setCancelable(false)
                    .show();
        }
    }

    private String getSolutionsOrWeblinks(boolean camelCase) {
        if (getResources().getString(R.string.app_name).equalsIgnoreCase("Utkarsh")) {
            if (camelCase)
                return "Weblinks";
            else
                return "weblinks";
        } else {
            if (camelCase)
                return "Solutions";
            else
                return "solutions";
        }
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
                    //  buttonLow.setVisibility(View.GONE);
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
    public void onResume() {
        super.onResume();

        // LocalBroadcastManager.getInstance(Objects.requireNonNull(getActivity())).registerReceiver(broadcastReceiver, new IntentFilter(TestService.action));

        if (SharedPreference.getInstance().getBoolean(Const.IS_PAYMENT_DONE)) {
            buttonLow.setVisibility(View.GONE);
            SharedPreference.getInstance().putBoolean(Const.SINGLE_STUDY, true);
        }
        if (singleStudy != null && singleStudy.getData().getCourseDetail() != null) {
            initButton(singleStudy.getData().getCourseDetail());
        }

        LocalBroadcastManager.getInstance(requireActivity())
                .registerReceiver(videoDownloadReceiver, new IntentFilter(VideoDownloadService.VIDEO_DOWNLOAD_ACTION));

        if (Constants.revison_set) {
            videoArrayList = MakeMyExam.videoArrayList;
            contentTypeTile = cardsArrayList.get(0).getType() + cardsArrayList.get(0).getId();
            if (cardsArrayList.size() > 0)
                tile_id = cardsArrayList.get(0).getId();
            //allResourceAddMenu.setVisibility(View.GONE);
            file_type_attributes = "0";
            examPrepLayer3Adapter.sendlist(videoArrayList);
            tileItemsAdapter.notifyDataSetChanged();
            Constants.revison_set = false;
        }

        if (Constants.REFRESHPAGE.equals("true")) {
            Constants.REFRESHPAGE = "false";
            if (cardsArrayList != null) {
                contentTypeTile = cardsArrayList.get(0).getType() + cardsArrayList.get(0).getId();
                if (cardsArrayList.size() > 0)
                    tile_id = cardsArrayList.get(0).getId();
                file_type_attributes = "0";
                mPage = 1;
                status = false;
                hit_api_for_data(true);
            }
        }

        if (AudioPlayerService.isAudioPlaying && !Constants.pos.equalsIgnoreCase("")) {
            examPrepLayer3Adapter.changedata(Integer.parseInt(Constants.pos));
            Constants.pos = "";
        } else if (!Constants.pos.equalsIgnoreCase("")) {
            examPrepLayer3Adapter.changedata(Integer.parseInt(Constants.pos));
            Constants.pos = "";
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(videoDownloadReceiver);
        // LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);

        if (myTimer != null) {
            myTimer.cancel();
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Helper.dismissProgressDialog();

        }
    };


    private BroadcastReceiver videoDownloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int serviceStatus = intent.getIntExtra("result", -1);
            String video_time = intent.getStringExtra("video_time");
            String res_id = intent.getStringExtra(RES_ID);
            if (serviceStatus == VIDEO_DOWNLOAD_SUCCESSFUL) {
                for (Video video : videoArrayList) {
                    if (video.getId().equalsIgnoreCase(res_id)) {
                        video.setVideo_download(true);
                        video.setVideo_status("Downloaded");
                        video.setVideotime(video_time);
                        examPrepLayer3Adapter.notifyDataSetChanged();
                        break;
                    }

                }

            }


        }
    };

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.GET_MY_QUIRES:
                EncryptionData masterdataencryptionData1 = new EncryptionData();
                masterdataencryptionData1.setPage("1");
                masterdataencryptionData1.setCourse_id(singleStudy.getData().getCourseDetail().getId());
                String masterdataencryptionDatadoseStr1 = new Gson().toJson(masterdataencryptionData1);
                String masterdatadoseStrScr1 = AES.encrypt(masterdataencryptionDatadoseStr1);
                return service.getMyHelpQuires(masterdatadoseStrScr1);

            case API.free_transaction:
                EncryptionData encryptionData = new EncryptionData();
                encryptionData.setCourse_id(singleStudy.getData().getCourseDetail().getId());
                encryptionData.setCoupon_applied("0");
                encryptionData.setParent_id(parentCourseId);
                String data = new Gson().toJson(encryptionData);
                String free = AES.encrypt(data);
                return service.free_transaction(free);


            case API.int_payment:
                if (((CourseActivity) activity).pos_txn_id.equalsIgnoreCase("")) {
                    EncryptionData getcoursedataencryptionData = new EncryptionData();
                    getcoursedataencryptionData.setType("1");
                    getcoursedataencryptionData.setCourse_id(singleStudy.getData().getCourseDetail().getId());
                    getcoursedataencryptionData.setCourse_price(singleStudy.getData().getCourseDetail().getMrp());
                    getcoursedataencryptionData.setTax(singleStudy.getData().getCourseDetail().getTax());
                    getcoursedataencryptionData.setPay_via("3");
                    getcoursedataencryptionData.setCoupon_applied("0");
                    String getcoursedataencryptionDatadoseStr = new Gson().toJson(getcoursedataencryptionData);
                    String getcoursedatadoseStrScr = AES.encrypt(getcoursedataencryptionDatadoseStr);
                    return service.int_payment(getcoursedatadoseStrScr);
                } else {
                    EncryptionData complete = new EncryptionData();
                    complete.setType("2");
                    complete.setCourse_id(singleStudy.getData().getCourseDetail().getId());
                    complete.setPre_transaction_id(pre_txtid);
                    complete.setTransaction_status("1");
                    complete.setPost_transaction_id(((CourseActivity) activity).pos_txn_id);
                    String completedoseStr = new Gson().toJson(complete);
                    String conpelete_response = AES.encrypt(completedoseStr);
                    return service.int_payment(conpelete_response);
                }

            case API.API_GET_MASTER_DATA:

                String skipStr = revertAPI;
                String[] separated = skipStr.split("\\#");
                String isSkip = separated[1];
                String subjectId = "0";
                String topicId = "0";
                if (isSkip.equalsIgnoreCase("1")) {
                    subjectId = "0";
                    topicId = list.getId() == null ? "0" : list.getId();
                } else if (isSkip.equalsIgnoreCase("2")) {
                    subjectId = listSubject.getId() == null ? "0" : listSubject.getId();
                    topicId = "0";
                } else if (isSkip.equalsIgnoreCase("3")) {
                    subjectId = "0";
                    topicId = "0";
                } else {
                    subjectId = listSubject.getId() == null ? "0" : listSubject.getId();
                    topicId = list.getId() == null ? "0" : list.getId();
                }

                if (typeApi.equalsIgnoreCase(Const.PAYMENT)) {
                    FreeCourseOnePOJO doseMenuPOJO = new FreeCourseOnePOJO(singleStudy.getData().getCourseDetail().getId());
                    String doseStr = new Gson().toJson(doseMenuPOJO);
                    String doseStrScr = AES.encrypt(doseStr);
                    return service.makeFreeCourseTransaction(doseStrScr);
                } else {
                    EncryptionData masterdatadetailencryptionData = new EncryptionData();
                    masterdatadetailencryptionData.setTile_id(tileIdAPI);
                    masterdatadetailencryptionData.setType(tileTypeAPI);
                    masterdatadetailencryptionData.setRevert_api(revertAPI);
                    masterdatadetailencryptionData.setCourse_id(singleStudy.getData().getCourseDetail().getId());
                    masterdatadetailencryptionData.setParent_id(parentCourseId.equals("") ? singleStudy.getData().getCourseDetail().getId() : parentCourseId);

                    masterdatadetailencryptionData.setLayer("3");
                    masterdatadetailencryptionData.setPage("" + mPage);
                    masterdatadetailencryptionData.setSubject_id(subjectId);
                    masterdatadetailencryptionData.setTopic_id(topicId);
                    String getmasterdataencryptionDatadoseStr = new Gson().toJson(masterdatadetailencryptionData);
                    String masterdatadoseStrScr = AES.encrypt(getmasterdataencryptionDatadoseStr);
                    return service.getMasterDataVideoThree(masterdatadoseStrScr);
                }
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonobject, String apitype, String typeApi, boolean showprogress) throws JSONException {
        Gson gson = new Gson();
        switch (apitype) {
            case API.GET_MY_QUIRES:
                if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                    JSONArray jsonArray = jsonobject.getJSONArray("data");
                    ArrayList<HelpSupportChatModel.DataBean> list = new Gson().fromJson(jsonArray.toString(), new TypeToken<ArrayList<HelpSupportChatModel.DataBean>>() {
                    }.getType());
                    if (list.size() > 0) {
                        Intent helpSupportIntent = new Intent(activity, HelpQueryActivity.class);
                        helpSupportIntent.putExtra("isCourse", true);
                        helpSupportIntent.putExtra("courseDetail", singleStudy);
                        startActivity(helpSupportIntent);
                    } else {
                        Intent helpSupportIntent = new Intent(activity, HelpSupportActivity.class);
                        helpSupportIntent.putExtra("isCourse", true);
                        helpSupportIntent.putExtra("courseDetail", singleStudy);
                        startActivity(helpSupportIntent);
                    }

                } else {
                    Intent helpSupportIntent = new Intent(activity, HelpSupportActivity.class);
                    helpSupportIntent.putExtra("isCourse", true);
                    helpSupportIntent.putExtra("courseDetail", singleStudy);
                    startActivity(helpSupportIntent);
                }
                break;

            case API.free_transaction:
                try {
                    if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                        if (!parentCourseId.equalsIgnoreCase("")) {
                            utkashRoom.getCourseDetaildata().deletecoursedetail(parentCourseId, MakeMyExam.userId);
                        } else if (!singleStudy.getData().getCourseDetail().getId().equalsIgnoreCase("")) {
                            utkashRoom.getCourseDetaildata().deletecoursedetail(singleStudy.getData().getCourseDetail().getId(), MakeMyExam.userId);
                        }
                        Toast.makeText(activity, "" + jsonobject.optString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
                        Intent courseList = new Intent(activity, CourseActivity.class);//FRAG_TYPE, Const.SINGLE_COURSE AllCoursesAdapter
                        courseList.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                        courseList.putExtra(Const.COURSE_ID_MAIN, !parentCourseId.equalsIgnoreCase("") ? parentCourseId : singleStudy.getData().getCourseDetail().getId());
                        courseList.putExtra(Const.COURSE_PARENT_ID, "");
                        courseList.putExtra(Const.IS_COMBO, false);
                        courseList.putExtra("course_name", singleStudy.getData().getCourseDetail().getTitle());
                        Helper.gotoActivity_finish(courseList, activity);

                    } else {
                        this.ErrorCallBack(jsonobject.getString(Const.MESSAGE), apitype, typeApi);
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
                            launch_paymentGateway();
                        } else {
                            if (!parentCourseId.equalsIgnoreCase("")) {
                                utkashRoom.getCourseDetaildata().deletecoursedetail(parentCourseId, MakeMyExam.userId);

                            } else if (!singleStudy.getData().getCourseDetail().getId().equalsIgnoreCase("")) {
                                utkashRoom.getCourseDetaildata().deletecoursedetail(singleStudy.getData().getCourseDetail().getId(), MakeMyExam.userId);
                            }
                            Toast.makeText(activity, "" + jsonobject.optString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
                            Intent courseList = new Intent(activity, CourseActivity.class);//FRAG_TYPE, Const.SINGLE_COURSE AllCoursesAdapter
                            courseList.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                            courseList.putExtra(Const.COURSE_ID_MAIN, !parentCourseId.equalsIgnoreCase("") ? parentCourseId : singleStudy.getData().getCourseDetail().getId());
                            courseList.putExtra(Const.COURSE_PARENT_ID, "");
                            courseList.putExtra(Const.IS_COMBO, false);
                            courseList.putExtra("course_name", singleStudy.getData().getCourseDetail().getTitle());
                            Helper.gotoActivity_finish(courseList, activity);
                            Toast.makeText(activity, "" + jsonobject.optString(Const.MESSAGE), Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        this.ErrorCallBack(jsonobject.getString(Const.MESSAGE), apitype, typeApi);
                        RetrofitResponse.GetApiData(activity, jsonobject.has(Const.AUTH_CODE) ? jsonobject.getString(Const.AUTH_CODE) : "", jsonobject.getString(Const.MESSAGE), false);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case API.API_GET_MASTER_DATA:
                if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                    if (paginationLoader != null && paginationLoader.isShown()) {
                        paginationLoader.setVisibility(View.GONE);
                    }
                    isPaginationAvailable = true;
                    if (status) {
                        JSONObject data = jsonobject.getJSONObject(Const.DATA);
                        ArrayList<Video> videos_basic_infos = new ArrayList<>();
                        for (int i = 0; i < Objects.requireNonNull(data.optJSONArray(Const.LIST)).length(); i++) {
                            Video video = gson.fromJson(Objects.requireNonNull(data.optJSONArray(Const.LIST)).get(i).toString(), Video.class);
                            videos_basic_infos.add(video);
                        }

                        if (videos_basic_infos.size() > 0) {
                            int oldarrysize = videoArrayList.size();
                            videoArrayList.addAll(videos_basic_infos);
                            actual_videolist = videoArrayList;
                            for (Video video : videoArrayList) {
                                if (video.getFile_type() != null && video.getFile_type().equalsIgnoreCase("3")) {
                                    if (!myDBClass.getOpenHelper().getWritableDatabase().isDbLockedByCurrentThread()) {
                                        if (myDBClass.getvideoDownloadao().isvideo_exit(video.getId(), MakeMyExam.userId)) {
                                            VideosDownload videoDownload = myDBClass.getvideoDownloadao().getvideo_byuserid(video.getId(), MakeMyExam.userId);
                                            if (videoDownload.getToal_downloadlocale() != null) {
                                                if (video.getIs_Download().equalsIgnoreCase("1") && videoDownload.getVideo_status().equalsIgnoreCase("")) {
                                                    video.setVideo_status("Download");
                                                } else {
                                                    video.setVideo_status(videoDownload.getVideo_status());
                                                }
                                                video.setVideotime(videoDownload.getVideotime());
                                                video.setVideo_download(videoDownload.getIs_complete().equalsIgnoreCase("1"));
                                                video.setVideo_currentpos(videoDownload.getVideoCurrentPosition());
                                            }
                                        } else {
                                            if (video.getIs_Download().equalsIgnoreCase("1")) {
                                                video.setVideo_status("Download");
                                                video.setVideo_download(false);
                                                video.setVideo_currentpos(0L);
                                            } else {
                                                video.setVideo_status("");
                                                video.setVideo_download(false);
                                                video.setVideo_currentpos(0L);
                                            }
                                        }
                                    }
                                }
                            }

                            if (examPrepLayer3Adapter != null) {
                                examPrepLayer3Adapter.notifyItemRangeInserted(videoArrayList.size() - 1, videoArrayList.size() - oldarrysize);
                            }
                        }
                    }
                    else {
                        if (videoArrayList != null && videoArrayList.size() != 0) {
                            videoArrayList.clear();
                        }

                        JSONObject data = jsonobject.getJSONObject(Const.DATA);
                        ArrayList<Video> videos_basic_infos = new ArrayList<>();

                        for (int i = 0; i < Objects.requireNonNull(data.optJSONArray(Const.LIST)).length(); i++) {
                            Video video = gson.fromJson(Objects.requireNonNull(data.optJSONArray(Const.LIST)).get(i).toString(), Video.class);
                            videos_basic_infos.add(video);
                        }

                        if (videos_basic_infos.size() > 0) {
                            videoArrayList.addAll(videos_basic_infos);
                            actual_videolist = videoArrayList;
                            for (Video video : videoArrayList) {
                                if (video.getFile_type() != null && video.getFile_type().equalsIgnoreCase("3")) {
                                    if (!myDBClass.getOpenHelper().getWritableDatabase().isDbLockedByCurrentThread()) {
                                        if (myDBClass.getvideoDownloadao().isvideo_exit(video.getId(), MakeMyExam.userId)) {
                                            VideosDownload videoDownload = myDBClass.getvideoDownloadao().getvideo_byuserid(video.getId(), MakeMyExam.userId);
                                            if (videoDownload.getToal_downloadlocale() != null) {
                                                if (video.getIs_Download() != null && video.getIs_Download().equalsIgnoreCase("1") && videoDownload.getVideo_status().equalsIgnoreCase("")) {
                                                    video.setVideo_status("Download");
                                                } else {
                                                    video.setVideo_status(videoDownload.getVideo_status());
                                                }
                                                video.setVideotime(videoDownload.getVideotime());
                                                video.setVideo_download(videoDownload.getIs_complete().equalsIgnoreCase("1"));
                                                video.setVideo_currentpos(videoDownload.getVideoCurrentPosition());
                                            }
                                        } else {
                                            if (video.getIs_Download() != null && video.getIs_Download().equalsIgnoreCase("1")) {
                                                video.setVideo_status("Download");
                                                video.setVideo_download(false);
                                                video.setVideo_currentpos(0L);
                                            } else {
                                                video.setVideo_status("");
                                                video.setVideo_download(false);
                                                video.setVideo_currentpos(0L);
                                            }
                                        }
                                    }
                                }

                            }
                            no_data_found_RL.setVisibility(View.GONE);
                            examPrepLayerRV.setVisibility(View.VISIBLE);
                            InitTestAdapter(videoArrayList);
                        } else {
                            no_data_found_RL.setVisibility(View.VISIBLE);
                            examPrepLayerRV.setVisibility(View.GONE);
                        }
                    }

                } else {
                    if (!status) {
                        no_data_found_RL.setVisibility(View.VISIBLE);
                        examPrepLayerRV.setVisibility(View.GONE);
                    }
                    if (paginationLoader != null && paginationLoader.isShown()) {
                        paginationLoader.setVisibility(View.GONE);
                    }
                    isPaginationAvailable = false;
                    if (!GenericUtils.isEmpty(jsonobject.optString(Const.AUTH_CODE)))
                        RetrofitResponse.GetApiData(activity, jsonobject.optString(Const.AUTH_CODE), jsonobject.optString(Const.MESSAGE), false);
                }
        }
    }

    private void launch_paymentGateway() {

        Checkout checkout = new Checkout();
        checkout.setKeyID(API.live_key_id_razor);
        checkout.setImage(R.mipmap.ic_launcher);
        JSONObject object = new JSONObject();
        try {
            object.put("name", activity.getResources().getString(R.string.app_name));
            object.put("theme.color", activity.getResources().getColor(R.color.colorPrimary));
            object.put("description", "Test Payment");
            object.put("currency", "INR");
            float total_amount = Float.parseFloat(singleStudy.getData().getCourseDetail().getMrp()) + Float.parseFloat(singleStudy.getData().getCourseDetail().getTax());
            object.put("amount", Math.round(total_amount * 100));
            object.put("order_id", pre_txtid);
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


    private void InitTestAdapter(ArrayList<Video> videoArrayList) {
        if (videoArrayList != null && videoArrayList.size() > 0) {
            if (examPrepLayerRV != null && no_data_found_RL != null) {
                examPrepLayerRV.setVisibility(View.VISIBLE);
                no_data_found_RL.setVisibility(View.GONE);
            }

            if (parentLL.getVisibility() == View.GONE) parentLL.setVisibility(View.VISIBLE);

            linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
            examPrepLayerRV.setLayoutManager(linearLayoutManager);
            examPrepLayerRV.setItemAnimator(new DefaultItemAnimator());
            examPrepLayer3Adapter = new ExamPrepLayer3Adapter(activity, singleStudy, videoArrayList, tileIdAPI, tileTypeAPI, revertAPI, tile_id, is_purchase);

            examPrepLayerRV.setAdapter(examPrepLayer3Adapter);
        } else {
            if (examPrepLayerRV != null && no_data_found_RL != null) {
                examPrepLayerRV.setVisibility(View.GONE);
                no_data_found_RL.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {
        if (paginationLoader != null && paginationLoader.isShown()) {
            paginationLoader.setVisibility(View.GONE);
        }
        if (apitype.equalsIgnoreCase(API.API_GET_MASTER_DATA)) {
            if (examPrepLayerRV != null && no_data_found_RL != null) {
                examPrepLayerRV.setVisibility(View.GONE);
                no_data_found_RL.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((CourseActivity) getActivity()).onSuccessListner = this;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void successpayment(String pos_txn_id) {
        this.pos_txn_id = pos_txn_id;
        NetworkAPICall(API.int_payment, "", true, false, false);
    }

    @Override
    public void onSuccess(String pos_txn_id) {
        successpayment(pos_txn_id);
    }

    @Override
    public void onFailure(String pot_txt_id) {

    }

    public class TileItemsAdapter extends RecyclerView.Adapter<TileItemsAdapter.MyViewHolder> {

        private List<TilesItem> cards;
        private Context context;
        private RecyclerView tileRv;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public LinearLayout parent;
            public LinearLayout tour_ll;
            public TextView tilesText;

            public MyViewHolder(View view) {
                super(view);
                tilesText = (TextView) view.findViewById(R.id.tilesTextTv);
                parent = (LinearLayout) view.findViewById(R.id.parentBottom);
                tour_ll = (LinearLayout) view.findViewById(R.id.tour_ll);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(6, 0, 6, 0);
                parent.setLayoutParams(layoutParams);
                tilesText.setPadding(Math.round(convertDpToPixel(16, context)), Math.round(convertDpToPixel(8, context)), Math.round(convertDpToPixel(16, context)), Math.round(convertDpToPixel(8, context)));
            }
        }

        public TileItemsAdapter(Context context, ArrayList<TilesItem> cards, RecyclerView tileRv) {
            this.cards = cards;
            this.context = context;
            this.tileRv = tileRv;
        }

        @Override
        public TileItemsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.single_item_tiles, parent, false);
            return new TileItemsAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final TileItemsAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            final TilesItem card = cards.get(position);

            holder.tilesText.setText(card.getTileName());

            if (contentTypeTile.equals(card.getType() + card.getId())) {
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setColor(Color.parseColor("#000000"));
                gradientDrawable.setCornerRadius(60);
                holder.parent.setBackground(gradientDrawable);
                holder.tilesText.setTextColor(Color.WHITE);
            } else {
                holder.parent.setBackground(activity.getResources().getDrawable(R.drawable.bg_tile_unselected));
                holder.tilesText.setTextColor(context.getResources().getColor(R.color.black));
            }

            holder.parent.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View v) {
                    file_type_attributes = "";
                    if (singleStudy.getData().getCourseDetail().getIsPurchased().equalsIgnoreCase("1"))
                        //  allResourceAddMenu.setVisibility(View.VISIBLE);

                        contentTypeTile = card.getType() + card.getId();
                    tile_id = card.getId();
                    String fileType = "0";
                    if (card.getType().equalsIgnoreCase(Const.PDF)) {
                        fileType = Const.CONTENT_PDF;
                    } else if (card.getType().equalsIgnoreCase(Const.PPT)) {
                        fileType = Const.CONTENT_PPT;
                    } else if (card.getType().equalsIgnoreCase(Const.VIDEO)) {
                        fileType = Const.CONTENT_VIDEO;
                    } else if (card.getType().equalsIgnoreCase(Const.EPUB)) {
                        fileType = Const.CONTENT_EPUB;
                    } else if (card.getType().equalsIgnoreCase(Const.DOC)) {
                        fileType = Const.CONTENT_DOC;
                    } else if (card.getType().equalsIgnoreCase(Const.IMAGE)) {
                        fileType = Const.CONTENT_IMAGE;
                    } else if (card.getType().equalsIgnoreCase(Const.CONCEPT)) {
                        fileType = Const.CONTENT_CONCEPT;
                    } else if (card.getType().equalsIgnoreCase(Const.LINK)) {
                        fileType = Const.CONTENT_LINK;
                    } else if (card.getType().equalsIgnoreCase(Const.TEST)) {
                        //      allResourceAddMenu.setVisibility(View.GONE);
                        file_type_attributes = "t";
                        fileType = Const.CONTENT_TEST;
                    } else {
                        //    allResourceAddMenu.setVisibility(View.GONE);
                        file_type_attributes = "0";
                        fileType = Const.CONTENT_ALL;
                    }
                    tileRv.scrollToPosition(position);
                    notifyDataSetChanged();

                    seleced_tile_list.clear();
                    custom_video_list.clear();
                    custom_link_list.clear();


                    for (Video video : videoArrayList) {
                        if (video.getPayloadData() != null) {
                            if (fileType.equalsIgnoreCase(video.getFile_type()) && tile_id.equalsIgnoreCase(video.getPayloadData().getTile_id())) {
                                video.setIs_deleted(false);
                                seleced_tile_list.add(video);
                            }
                        }

                        if ("10".equalsIgnoreCase(video.getFile_type())) {
                            video.setIs_deleted(true);
                            custom_video_list.add(video);

                        }
                        if ("11".equalsIgnoreCase(video.getFile_type())) {
                            video.setIs_deleted(true);
                            custom_link_list.add(video);
                        }
                    /*    if (""video.getFile_type().equalsIgnoreCase("10")) {
                            video.setIs_deleted(true);
                            custom_video_list.add(video);
                        }*/
                    }

                    if (fileType.equalsIgnoreCase("0")) {
                        InitTestAdapter(videoArrayList);
                    } else if (fileType.equalsIgnoreCase("3")) {
                        seleced_tile_list.addAll(custom_video_list);
                        InitTestAdapter(seleced_tile_list);
                    } else if (fileType.equalsIgnoreCase("8")) {
                        seleced_tile_list.addAll(custom_link_list);
                        InitTestAdapter(seleced_tile_list);
                    } else {
                        InitTestAdapter(seleced_tile_list);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return cards.size();
        }

    }

    private void getmyQuires() {
        NetworkAPICall(API.GET_MY_QUIRES, "", true, false, false);
    }


}