package com.utkarshnew.android.courses.Activity;

import static android.os.Build.VERSION.SDK_INT;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.ActionMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.DownloadListener;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.utkarshnew.android.BuildConfig;
import com.utkarshnew.android.Profile.ProfileActivity;
import com.utkarshnew.android.courses.modal.Example;
import com.utkarshnew.android.courses.modal.NotesPDF.NoteData;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.Model.Video;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.table.HtmlTbale;
import com.utkarshnew.android.table.UserHistroyTable;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;
import com.utkarshnew.android.Utils.ReadJSInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import retrofit2.Call;

public class Concept_newActivity extends AppCompatActivity implements NetworkCall.MyNetworkCallBack {

    ObservableWebView mChapterContentWebView;
    private Toolbar mReadChapterContentActionbar;
    int currentSection = 0;
    ImageView action_text_formate;
    RelativeLayout textformatterdialog;
    TextView toolbarTitleTV;
    private int contentBackColor;

    private AppCompatSeekBar brightnessSeekBar;
    private Button brightBackBtn, darkBackBtn;
    private FrameLayout textIncreaseBtn, textDecreaseBtn;
    private View markBlack, markWhite;
    private int oldTextSize;
    private SwipeRefreshLayout swipeRefreshLayout;

    int size = 1;
    private RelativeLayout nextChapterLayout;
    private boolean isLastSection;

    ImageView image_back, refresh;
    ReadJSInterface readJSInterface;
    private boolean isInterract = true;
    private HashMap<String, String> annotationLocalIdsMap;
    JSONObject annotateJson;
    private final boolean isLoggedIn = true;
    private final boolean mShopView = false;
    private boolean annotationTap = false;
    private boolean isFocusRemoved = true;
    private String action = "";

    private final boolean webSearchTap = false;

    private View.OnTouchListener webTouchListener;

    public ValueCallback<Uri[]> uploadMessage;
    private ValueCallback<Uri> mUploadMessage;

    String htmlFilePath = "";
    String final_filename = "";

    public static String somesalthashmap;

    String highlightdata = "[]";

    private String unzipLocation, zipFile, zip_url;
    UtkashRoom utkashRoom;
    Video video;
    String courseId, sendcourseid, parentid;
    String tileId;


    private ArrayList<Example> example;
    HtmlTbale htmlTbale;
    NetworkCall networkCall;
    ImageView notesIV;
    int selectfile = 0;
    public static final int REQUEST_SELECT_FILE = 100;
    private final static int FILECHOOSER_RESULTCODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.activity_newconcept);
        try {
            if (Build.VERSION.SDK_INT >= 21) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(this.getResources().getColor(R.color.black));
            }
            if (utkashRoom == null) {
                utkashRoom = UtkashRoom.getAppDatabase(Concept_newActivity.this);
                utkashRoom.getOpenHelper().getWritableDatabase().enableWriteAheadLogging();
            }
            video = new Video();
            video.setId(getIntent().getExtras().getString("id"));
            video.setTitle(getIntent().getExtras().getString("name"));
            video.setModified(getIntent().getExtras().getString("modified") == null ? "0" : getIntent().getExtras().getString("modified"));


            courseId = getIntent().getExtras().getString("course_id");
            tileId = getIntent().getExtras().getString("tile_id");


            if (courseId.contains("#")) {
                String courseseprate[] = courseId.split("#");
                if (courseseprate.length == 1) {
                    sendcourseid = courseseprate[0];
                    parentid = "";
                } else {
                    sendcourseid = courseseprate[1];
                    parentid = courseseprate[0];
                }
            } else {
                sendcourseid = courseId;
            }


            networkCall = new NetworkCall(this, this);

            nextChapterLayout = findViewById(R.id.nextChapterLayout);
            notesIV = findViewById(R.id.notesIV);
            toolbarTitleTV = findViewById(R.id.toolbarTitleTV);
            mReadChapterContentActionbar = findViewById(R.id.readchaptertoolbar);
            brightnessSeekBar = findViewById(R.id.brightnessSeekBar);
            textDecreaseBtn = findViewById(R.id.textSizeDecrease);
            textIncreaseBtn = findViewById(R.id.textSizeIncrease);
            refresh = findViewById(R.id.refresh);
            markBlack = findViewById(R.id.markBlack);
            markWhite = findViewById(R.id.markWhite);
            brightBackBtn = findViewById(R.id.whitebackbtn);
            darkBackBtn = findViewById(R.id.darkbackbtn);

            swipeRefreshLayout = findViewById(R.id.read_swipe_refresh);
            swipeRefreshLayout.setOnRefreshListener(this::updateContent);
            refresh.setVisibility(View.GONE);
            //refresh.setOnClickListener(v -> updateContent());

            textformatterdialog = findViewById(R.id.textformatterdialog);

            action_text_formate = findViewById(R.id.action_text_formate);

            textformatterdialog.setVisibility(View.GONE);

            oldTextSize = 12;


            if (getSupportActionBar() == null) {
                setSupportActionBar(mReadChapterContentActionbar);
                if (getSupportActionBar() != null) {
                    toolbarTitleTV.setText(video.getTitle());
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back_black);
                } else {
                    return;
                }
            }

            nextChapterLayout.setVisibility(View.GONE);

            startRequiredRead();

            mChapterContentWebView = findViewById(R.id.chapter_content_webview);

            readJSInterface = new ReadJSInterface(mChapterContentWebView, this);
            mChapterContentWebView.setWebChromeClient(new ChatBotChromeClient());

            action_text_formate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (textformatterdialog.getVisibility() == View.VISIBLE) {
                        textformatterdialog.setVisibility(View.GONE);
                        action_text_formate.getDrawable().clearColorFilter();
                    } else {
                        textformatterdialog.setVisibility(View.VISIBLE);
                        if (mChapterContentWebView.hasFocus())
                            mChapterContentWebView.clearFocus();
                        action_text_formate.getDrawable().mutate().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
                    }
                }
            });
            mReadChapterContentActionbar.setNavigationOnClickListener(view -> onBackPressed());


            mChapterContentWebView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    configureWebView(mChapterContentWebView, 0);

                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    if (!webSearchTap) {
                        setupNextChapterUI();
                    }
                    if (!mChapterContentWebView.canScrollVertically(-1) && !mChapterContentWebView.canScrollVertically(1)) {
                        if (nextChapterLayout.getVisibility() != View.VISIBLE && !isLastSection && currentSection + 1 < size) {
                            nextChapterLayout.setVisibility(View.VISIBLE);

                        }
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        view.evaluateJavascript("window.TextToSpeech.processContent(document.getElementsByTagName('body')[0].innerText, document.getElementsByTagName('body')[0].innerText.length, document.getElementsByTagName('body')[0].innerText.split(' '));", null);
                    } else {
                        view.loadUrl("javascript:window.TextToSpeech.processContent(document.getElementsByTagName('body')[0].innerText, document.getElementsByTagName('body')[0].innerText.length, document.getElementsByTagName('body')[0].innerText.split(' '));");
                    }
                }
            });


            mChapterContentWebView.addJavascriptInterface(readJSInterface, "ReadJSInterface");
            annotationLocalIdsMap = new HashMap<>();
            configureWebView(mChapterContentWebView, 0);
            somesalthashmap = UUID.randomUUID().toString();
            example = new ArrayList<>();


            if (utkashRoom.gethtmllink().is_concept_exit(MakeMyExam.userId, video.getId())) {
                HtmlTbale htmlTbale = utkashRoom.gethtmllink().getconcept(MakeMyExam.userId, video.getId());
                if (htmlTbale != null && htmlTbale.getValid_to() == null) {
                    utkashRoom.gethtmllink().delete_viaconceptid(video.getId(), MakeMyExam.userId);
                    hit_api_for_html();
                } else {
                    if (Long.parseLong(video.getModified()) > Long.parseLong(Objects.requireNonNull(htmlTbale).getValid_to())) {
                        utkashRoom.gethtmllink().delete_viaconceptid(video.getId(), MakeMyExam.userId);
                        hit_api_for_html();
                    } else {
                        highlightdata = htmlTbale.getHighight();
                        example = new Gson().fromJson(highlightdata, new TypeToken<ArrayList<Example>>() {
                        }.getType());
                        try {
                            UserHistroyTable userHistroyTable = new UserHistroyTable();
                            userHistroyTable.setVideo_id(video.getId());
                            userHistroyTable.setVideo_name(video.getTitle());
                            userHistroyTable.setType("CONCEPT");
                            userHistroyTable.setUser_id(MakeMyExam.userId);
                            userHistroyTable.setTileid(tileId);
                            userHistroyTable.setCourse_id(courseId);
                            userHistroyTable.setValid_to(video.getModified());
                            userHistroyTable.setCurrent_time("" + MakeMyExam.getTime_server());
                            UtkashRoom.getAppDatabase(this).getuserhistorydao().addUser(userHistroyTable);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        load_data(htmlTbale.getHtml());
                    }

                }

            } else {
                hit_api_for_html();
            }

            notesIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (example.size() > 0) {
                        ArrayList<NoteData> noteList = new ArrayList<>();

                        for (Example exampledata : example) {
                            NoteData noteData = new NoteData();
                            if (exampledata.getText() != null && !exampledata.getText().equalsIgnoreCase("")) {
                                noteData.setType("note");
                                noteData.setUserId(MakeMyExam.userId);
                                noteData.setTitle(exampledata.getText());
                                noteData.setQueryData(exampledata.getQuote());

                            } else {
                                noteData.setType("");
                                noteData.setUserId(MakeMyExam.userId);
                                noteData.setQueryData(exampledata.getQuote());
                            }
                            noteList.add(noteData);
                        }
                        if (noteList.size() > 0) {
                            ;
                            Intent intent = new Intent(Concept_newActivity.this, MyNotesActivity.class);
                            intent.putExtra(Const.VIDEO, video);
                            intent.putExtra(Const.COURSE_ID, courseId);
                            intent.putExtra("notes_data", new Gson().toJson(noteList));
                            intent.putExtra(Const.TILE_ID, tileId);
                            Helper.gotoActivity(intent, Concept_newActivity.this);
                        } else {
                            Snackbar.make(nextChapterLayout, "No notes found", Snackbar.LENGTH_SHORT).show();

                        }

                    } else {
                        Snackbar.make(nextChapterLayout, "No notes found", Snackbar.LENGTH_SHORT).show();
                        //Toast.makeText(Concept_newActivity.this, "No notes found", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            brightnessSeekBar.setMax(100);
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    brightnessSeekBar.setProgress(Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS), true);
                } else {
                    brightnessSeekBar.setProgress(Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS));
                }
            } catch (Settings.SettingNotFoundException e) {
            }
            brightnessSeekBar.jumpDrawablesToCurrentState();
            brightnessSeekBar.incrementProgressBy(5);

            brightnessSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                    float BackLightValue = (float) progress / 100;
                    WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
                    layoutParams.screenBrightness = BackLightValue;
                    getWindow().setAttributes(layoutParams);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            markWhite.setBackgroundColor(ActivityCompat.getColor(this, R.color.colorPrimaryDark));


            brightBackBtn.setOnClickListener(view -> {
                contentBackColor = 0;
                configureWebView(mChapterContentWebView, contentBackColor);
                markWhite.setBackgroundColor(ActivityCompat.getColor(Concept_newActivity.this, R.color.colorPrimaryDark));
                markBlack.setBackgroundColor(Color.parseColor("#333333"));
                nextChapterLayout.setBackgroundResource(R.color.white);
                ((TextView) nextChapterLayout.findViewById(R.id.nameLabel)).setTextColor(Color.parseColor("#000000"));
                ((TextView) nextChapterLayout.findViewById(R.id.chapterNameTxt)).setTextColor(Color.parseColor("#000000"));
                ((AppCompatImageView) nextChapterLayout.findViewById(R.id.nextImageView)).clearColorFilter();
            });

            darkBackBtn.setOnClickListener(view -> {
                contentBackColor = R.color.black;
                configureWebView(mChapterContentWebView, contentBackColor);
                markBlack.setBackgroundColor(ActivityCompat.getColor(Concept_newActivity.this, R.color.colorPrimaryDark));
                markWhite.setBackgroundColor(Color.parseColor("#F2F2F2"));
                nextChapterLayout.setBackgroundResource(R.color.black);
                ((TextView) nextChapterLayout.findViewById(R.id.nameLabel)).setTextColor(Color.parseColor("#ffffff"));
                ((TextView) nextChapterLayout.findViewById(R.id.chapterNameTxt)).setTextColor(Color.parseColor("#ffffff"));
                ((AppCompatImageView) nextChapterLayout.findViewById(R.id.nextImageView)).setColorFilter(Color.parseColor("#ffffff"));
            });

            textIncreaseBtn.setOnClickListener(v -> {
                if ((oldTextSize + 2) <= 54) {
                    mChapterContentWebView.getSettings().setTextZoom(mChapterContentWebView.getSettings()
                            .getTextZoom() + 2);
                    oldTextSize += 2;
                }
            });

            textDecreaseBtn.setOnClickListener(v -> {
                if ((oldTextSize - 2) >= 12) {
                    mChapterContentWebView.getSettings().setTextZoom(mChapterContentWebView.getSettings()
                            .getTextZoom() - 2);
                    oldTextSize -= 2;
                }
            });

            mChapterContentWebView.setDownloadListener(new DownloadListener() {

                @Override
                public void onDownloadStart(String url, String userAgent,
                                            String contentDisposition, String mimetype,
                                            long contentLength) {
                    DownloadManager.Request request = new DownloadManager.Request(
                            Uri.parse(url));

                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, toolbarTitleTV.getText().toString().trim());
                    DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    dm.enqueue(request);
                    Toast.makeText(getApplicationContext(), "Downloading File", //To notify the Client that the file is being downloaded
                            Toast.LENGTH_LONG).show();
                    Helper.dismissProgressDialog();
                }
            });


        } catch (Exception e) {
            Helper.dismissProgressDialog();
            e.printStackTrace();
        }

    }

    private void updateContent() {
        if (Helper.isNetworkConnected(this)) {
            networkCall.NetworkAPICall(API.get_video_link_concept, "", false, false);

        } else {
            swipeRefreshLayout.setRefreshing(false);
            Snackbar.make(mChapterContentWebView, "No Internet Connection", Snackbar.LENGTH_SHORT).show();
        }
    }


    private void hit_api_for_html() {

        networkCall.NetworkAPICall(API.get_video_link_concept, "", true, false);
    }

    private void setupNextChapterUI() {
       /* if (size!=0 && currentSection <size  && notesAvailable != 1) {

        }*/
        nextChapterLayout.setVisibility(View.GONE);
    }


    @Override
    public void onSupportActionModeStarted(@NonNull androidx.appcompat.view.ActionMode mode) {
        mode.getMenu().clear();
        super.onSupportActionModeStarted(mode);
    }


    private void startRequiredRead() {


        invalidateOptionsMenu();

    }


    @NonNull
    private void configureWebView(final WebView webView, int colorId) {
        if (colorId == 0) {
            webView.evaluateJavascript("document.body.style.backgroundColor=\"white\";document.body.style.color=\"black\";", null);
            webView.evaluateJavascript("document.body.classList.add('blackTheme');", null);
            webView.evaluateJavascript("document.body.classList.remove('whiteTheme');", null);
        } else {
            webView.evaluateJavascript("document.body.style.backgroundColor=\"black\";document.body.style.color=\"white\";", null);
            webView.evaluateJavascript("document.body.classList.add('whiteTheme');", null);
            webView.evaluateJavascript("document.body.classList.remove('blackTheme');", null);
        }
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setVerticalScrollBarEnabled(true);
        webView.setHorizontalScrollBarEnabled(true);
        webView.setClickable(false);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        File imageCacheDir = getCacheDir();
        if (!imageCacheDir.exists()) {
            imageCacheDir.mkdir();
        }
        webView.getSettings().setAppCachePath(imageCacheDir.getPath());

        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        if (BuildConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        webTouchListener = new View.OnTouchListener() {

            private static final int MAX_CLICK_DURATION = 700;

            private static final int MAX_CLICK_DISTANCE = 15;

            private long pressStartTime;
            private float pressedX;
            private float pressedY;
            private boolean stayedWithinClickDistance;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // Go fullscreen
                isInterract = false;
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        pressedX = motionEvent.getX();
                        pressedY = motionEvent.getY();
                        //isFocusRemoved = false;
                        stayedWithinClickDistance = true;
                        if (textformatterdialog.getVisibility() == View.VISIBLE) {
                            action_text_formate.getDrawable().clearColorFilter();
                            textformatterdialog.setVisibility(View.GONE);
                        }

                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        if (stayedWithinClickDistance && distance(pressedX, pressedY, motionEvent.getX(), motionEvent.getY()) > MAX_CLICK_DISTANCE) {
                            stayedWithinClickDistance = false;
                            if (getSupportActionBar().isShowing() || nextChapterLayout.getVisibility() == View.VISIBLE || !mChapterContentWebView.canScrollVertically(1))
//                                hideSystemUI();
                                if (nextChapterLayout.getVisibility() == View.VISIBLE && (mChapterContentWebView.canScrollVertically(-1) || mChapterContentWebView.canScrollVertically(1))) {
                                    isInterract = true;
                                    nextChapterLayout.setVisibility(View.GONE);

                                }
                        }
                        break;
                    }

                }
                return isInterract;
            }
        };

        webView.setOnTouchListener(webTouchListener);

        webView.setWebChromeClient(new ChatBotChromeClient());

//        webView.setWebChromeClient(new WebChromeClient() {
//            public boolean onConsoleMessage(ConsoleMessage cm) {
//                if (cm != null && cm.sourceId().length() > 0) {
//
//                } else if (cm != null) {
//
//                }
//                return true;
//            }
//        });

        webView.getSettings().setDefaultFontSize(14);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setOnLongClickListener(view -> {
            if (!isInterract) {
            }
            return !isLoggedIn || isInterract || mShopView;
        });


    }

    private float pxToDp(float px) {
        return px / getResources().getDisplayMetrics().density;
    }


    private float distance(float x1, float y1, float x2, float y2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        float distanceInPx = (float) Math.sqrt(dx * dx + dy * dy);
        return pxToDp(distanceInPx);
    }


    private String getOfflineKatexConfig(String formulaString) {
        if (formulaString != null) {
            String offline_config = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "    <head>\n" +
                    "        <meta charset=\"UTF-8\">\n" +
                    "        <title>Auto-render test</title>\n" +
                    "        <link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/katex/katex.min.css\">\n" +
                    "        <link rel=\"stylesheet\" type=\"text/css\" href                                                                    =\"file:///android_asset/annotator/annotator.touch.css\"/>\n" +
                    "        <script type=\"text/javascript\" src=\"file:///android_asset/annotator/jquery-1.11.2.min.js\"></script>\n" +
                    "        <script type=\"text/javascript\" src=\"file:///android_asset/katex/katex.min.js\"></script>\n" +
                    "        <script type=\"text/javascript\" src=\"file:///android_asset/annotator/annotator-full.min.js\"/></script>\n" +
                    "        <script type=\"text/javascript\" src=\"file:///android_asset/annotator/annotator.touch.min.js\"/></script>\n" +
                    "        <script type=\"text/javascript\" src=\"file:///android_asset/katex/contrib/auto-render.min.js\"></script>\n" +
                    " <style type='text/css'>" +
                    "body {" +
                    "margin: 5px;" +
                    "padding: 10px;" +
                    "font-size:12px" +
                    "color:#EEEEEE;" +
                    " }" +
                    " </style>" +
                    "    </head>\n" +
                    "    <body id='highlightPos'>\n" +
                    "<div id=\"htmlreadingcontent\">\n" +
                    "        <div id=\"htmlContent\">\n" +
                    "        {formula}\n" +
                    "</div>\n" +
                    "</div>\n" +
                    "        <script>\n" +
                    "          renderMathInElement(\n" +
                    "              document.body\n" +
                    "          );\n" +
                    "var annotation;\n" +
                    "var json =" + highlightdata + ";\n" +
                    "annotation = $('#htmlreadingcontent').annotator();\n" +
                    "    annotation.annotator('addPlugin', 'Touch', {\n" +
                    "      force: location.search.indexOf('force') > -1,\n" +
                    "      useHighlighter: location.search.indexOf('highlighter') > -1\n" +
                    "    });\n" +
                    "     Annotator.Plugin.StoreLogger = function (element) {\n" +
                    "        return {\n" +
                    "            pluginInit: function () {   \n" +
                    "                this.annotator\n" +
                    "\n" +
                    "                .subscribe(\"annotationCreated\", function (annotation) {\n" +
                    "                    console.log(\"after created=\"+JSON.stringify(annotation));\n" +
                    "                    ReadJSInterface.annotationData(JSON.stringify(annotation),\"create\");\n" +
                    "                                  \n" +
                    "                })\n" +
                    "                .subscribe(\"annotationUpdated\", function (annotation) {\n" +
                    "                    console.log(\"after updated=\"+JSON.stringify(annotation));\n" +
                    "                    ReadJSInterface.annotationData(JSON.stringify(annotation),\"update\");\n" +
                    "                                  \n" +
                    "                })\n" +
                    "                .subscribe(\"annotationDeleted\", function (annotation) {\n" +
                    "                    console.log(\"after deleted=\"+JSON.stringify(annotation));\n" +
                    "                    ReadJSInterface.annotationData(JSON.stringify(annotation),\"delete\");\n" +
                    "                                  \n" +
                    "                })\n" +
                    "               \n" +
                    "            }\n" +
                    "        } \n" +
                    "    };    \n" +
                    "  \n" +
                    "\n" +
                    "window.onscroll = function(ev) {\n" +
                    "if ((window.innerHeight + window.pageYOffset) >= document.body.offsetHeight) {\n" +
                    "       ReadJSInterface.scrollEnd(); \n" +
                    "    }\n" +
                    "};\n" +
                    "annotation.annotator('loadAnnotations', json);\n" +
                    "annotation.annotator('addPlugin', 'StoreLogger');\n" +
//                    "$('#test').on('touchstart',function(){\n" +
//                    "       alert('test');\n" +
//                    "       console.log('touch wrkd');\n" +
//                    "   });\n" +
//                    "   $('#tapTest').on('tap',function(){\n" +
//                    "       alert('test1');\n" +
//                    "       console.log('tap');\n" +
//                    "   });"+
                    "var customPosition = document.getElementById('highlightPos');\n" +
                    "\n" +
                    //TODO do correctly for annotator position for android 6.0
                    /*"   highlightPos.addEventListener('touchstart', function(e) {\n" +
                    "       annotatorPosition(e);\n" +
                    "   },false);\n" +
                    "   function annotatorPosition(e) {\n" +
                    "       e.preventDefault();\n" +
                    "       var touch = e.changedTouches[0];\n" +
                    "       var posX = touch.clientX;\n" +
                    "       var posY = touch.clientY + 20;\n" +
                    "\n" +
                    "       console.log(posX);\n" +
                    "if(posX > 180)"+
                    "{"+

                    "var posX=185;"+
                    "       console.log(posX);\n" +
                    "}"+
                    "       $('#annotateView').css({\"top\":posY});\n" +
                    "       $('#annotateView').css({\"left\":posX});\n" +
                    "   }"+*/
                    "        </script>\n" +
                    "    </body>\n" +
                    "</html>";

            /*Log.d(TAG, "katex response: " + offline_config.replace("{formula}", formulaString));*/

            return offline_config.replace("{formula}", formulaString);
        } else {

            return "";
        }
    }

    public void onScrolledBottom() {
        runOnUiThread(() -> {
            nextChapterLayout.setVisibility(View.GONE);

        });
    }

    public void onAnnotationCall(String annotationString, String action) {
        JSONObject jsonObject = null;
        if (!annotationString.isEmpty())
            try {
                jsonObject = new JSONObject(annotationString);
                if (jsonObject.has("quote") && !jsonObject.getString("quote").isEmpty()) {
                    if (!jsonObject.has("id")) {
                        if (!getLocalId(jsonObject.getString("ranges")).isEmpty())
                            jsonObject.put("id", getLocalId(jsonObject.getString("ranges")));
                    }
                    handleAnnotation(jsonObject, action);
                }
            } catch (JSONException e) {
            }
    }

    public void onAnnotationTap(boolean state) {
        annotationTap = state;
    }

    public void onWebSearchTap(String query) {
        if (getSupportActionBar() != null) {
            runOnUiThread(() -> {

                Helper.GoWebViewPDFActivity(this, "Web Search", "https://www.google.com/search?q=" + query);
                invalidateOptionsMenu();

            });
        }
    }

    private String getLocalId(String key) {
        String id = "";
        if (annotationLocalIdsMap.containsKey(key))
            id = annotationLocalIdsMap.get(key);

        return id;
    }

    @Override
    public void onActionModeStarted(ActionMode mode) {
        isFocusRemoved = false;
        super.onActionModeStarted(mode);
    }

    private void handleAnnotation(final JSONObject annotateJson, String action) {
        isFocusRemoved = true;
        annotationTap = false;
        switch (action) {
            case "create":
                if (Helper.isNetworkConnected(this)) {
                    add_anitaion(annotateJson, action);
                }
                break;

            case "update":
                try {
                    if (Helper.isNetworkConnected(this)) {
                        add_anitaion(annotateJson, action);
                    }
                } catch (Exception e) {
                }
                break;

            case "delete":
                try {
                    if (Helper.isNetworkConnected(this)) {
                        delete_annotation(annotateJson, action);
                    }
                } catch (Exception e) {
                }
                break;

        }

    }

    private void add_anitaion(JSONObject annotateJson, String action) {
        this.annotateJson = annotateJson;
        this.action = action;
        networkCall.NetworkAPICall(API.create_annotation, "", false, false);
    }

    private void delete_annotation(JSONObject annotateJson, String action) {
        this.annotateJson = annotateJson;
        this.action = action;
        networkCall.NetworkAPICall(API.delete_annotation, "", false, false);
    }


    @Override
    public void onBackPressed() {

        if (textformatterdialog != null && textformatterdialog.getVisibility() == View.VISIBLE) {
            textformatterdialog.setVisibility(View.GONE);
            action_text_formate.getDrawable().clearColorFilter();
        } else if (mChapterContentWebView != null && mChapterContentWebView.hasFocus() && annotationTap) {
            mChapterContentWebView.clearFocus();
            isFocusRemoved = true;
            annotationTap = false;
        } else {
            super.onBackPressed();
            finish();
        }
    }


    private ProgressDialog mProgressDialog;

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.get_video_link_concept:
                EncryptionData videoencryptionData = new EncryptionData();
                videoencryptionData.setName(video.getId() + "_0_0");
                videoencryptionData.setCourse_id(sendcourseid);
                videoencryptionData.setParent_id(parentid);
                videoencryptionData.setTile_id(tileId);
                videoencryptionData.setType("concept");
                String device_id = (Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
                String device_name = android.os.Build.MANUFACTURER + android.os.Build.MODEL;
                device_id = device_id == null && device_id.equalsIgnoreCase("") ? "1234567890" : device_id;
                videoencryptionData.setDevice_id(device_id);
                videoencryptionData.setDevice_name(device_name);

                String metavideoencryptionDatadoseStr = new Gson().toJson(videoencryptionData);
                String videodatadoseStrScr = AES.encrypt(metavideoencryptionDatadoseStr);
                return service.get_video_link_concept(videodatadoseStrScr);
            case API.delete_annotation:
                try {
                    EncryptionData delete = new EncryptionData();
                    delete.setAnnotation_id(annotateJson.getString("id"));
                    String json_delete = new Gson().toJson(delete);
                    String delete_encryot = AES.encrypt(json_delete);
                    return service.delete_annotation(delete_encryot);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            case API.create_annotation:
                EncryptionData create = new EncryptionData();
                try {
                    if (action.equalsIgnoreCase("create")) {
                        create.setId("");
                    } else {
                        create.setId(annotateJson.getString("id"));
                    }
                    create.setFile_id(video.getId());

                    if (annotateJson.has("text")) {
                        create.setText(annotateJson.getString("text"));
                    } else {
                        create.setText("");
                    }
                    create.setQuote(annotateJson.getString("quote"));
                    create.setRanges(annotateJson.getString("ranges"));
                    String create_json = new Gson().toJson(create);
                    String encrypt = AES.encrypt(create_json);
                    return service.create_annotation(encrypt);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonobject, String apitype, String typeApi, boolean showprogress) throws JSONException {
        switch (apitype) {
            case API.delete_annotation:
                try {
                    if (jsonobject.getString("status").equalsIgnoreCase("true")) {
                        JSONObject jsonObject = new JSONObject(jsonobject.toString());
                        Snackbar.make(nextChapterLayout, "" + jsonObject.getString(Const.MESSAGE), Snackbar.LENGTH_SHORT).show();
                        for (int i = 0; i < example.size(); i++) {
                            try {
                                if (!getLocalId(annotateJson.getString("ranges")).isEmpty())
                                    annotationLocalIdsMap.remove(annotateJson.getString("ranges"));
                            } catch (JSONException e) {
                            } catch (NullPointerException e) {
                            }

                            if (example.get(i).getId().equalsIgnoreCase(annotateJson.getString("id"))) {
                                example.remove(i);
                                utkashRoom.gethtmllink().update_highlight(new Gson().toJson(example), MakeMyExam.userId, video.getId());

                                break;
                            }

                        }
                    } else {
                        this.ErrorCallBack(jsonobject.getString(Const.MESSAGE), apitype, typeApi);
                        RetrofitResponse.GetApiData(this, jsonobject.getString(Const.AUTH_CODE), jsonobject.getString(Const.MESSAGE), false);
                    }
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            case API.create_annotation:
                try {
                    if (jsonobject.getString("status").equalsIgnoreCase("true")) {
                        JSONObject jsonObject = new JSONObject(jsonobject.toString());
                        Snackbar.make(nextChapterLayout, "" + jsonObject.getString(Const.MESSAGE), Snackbar.LENGTH_SHORT).show();
                        if (action.equalsIgnoreCase("create")) {
                            String id = jsonObject.getJSONObject("data").getString("id");

                            if (jsonObject != null) {
                                annotationLocalIdsMap.put(annotateJson.getString("ranges"), id);
                            }

                            Example data = new Gson().fromJson(annotateJson.toString(), Example.class);
                            data.setId(id);
                            example.add(data);
                            highlightdata = new Gson().toJson(example);

                        } else {
                            String id = jsonObject.getJSONObject("data").getString("id");
                            for (int i = 0; i < example.size(); i++) {
                                try {
                                    if (jsonObject != null) {
                                        annotationLocalIdsMap.put(annotateJson.getString("ranges"), id);
                                    }
                                } catch (JSONException e) {
                                } catch (NullPointerException e) {
                                }
                                if (example.get(i).getId().equalsIgnoreCase(id)) {
                                    Example data = new Gson().fromJson(annotateJson.toString(), Example.class);
                                    data.setId(id);
                                    example.set(i, data);
                                    break;
                                }
                            }
                        }
                        utkashRoom.gethtmllink().update_highlight(new Gson().toJson(example), MakeMyExam.userId, video.getId());
                    } else {
                        this.ErrorCallBack(jsonobject.getString(Const.MESSAGE), apitype, typeApi);
                        RetrofitResponse.GetApiData(this, jsonobject.getString(Const.AUTH_CODE), jsonobject.getString(Const.MESSAGE), false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case API.get_video_link_concept:
                try {
                    if (swipeRefreshLayout.isRefreshing()) {
                        utkashRoom.gethtmllink().deletedata();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    if (jsonobject.getString("status").equalsIgnoreCase("true")) {
                        JSONObject jsonObject = new JSONObject(jsonobject.toString());
                        if (jsonObject.has("data")) {
                            String html = jsonObject.getJSONObject("data").getString("description");
                            highlightdata = jsonObject.getJSONObject("data").getString("annotations");
                            if (highlightdata.equalsIgnoreCase("[]")) {
                                highlightdata = "[]";
                            } else {
                                example = new Gson().fromJson(highlightdata, new TypeToken<ArrayList<Example>>() {
                                }.getType());
                            }
                            htmlTbale = new HtmlTbale();
                            htmlTbale.setHtml(html);
                            htmlTbale.setUser_id(MakeMyExam.userId);
                            htmlTbale.setHighight(highlightdata);
                            htmlTbale.setConcept_id(video.getId());
                            htmlTbale.setCourse_id(courseId);
                            htmlTbale.setTile_id(tileId);
                            htmlTbale.setValid_to(video.getModified());
                            utkashRoom.gethtmllink().addUser(htmlTbale);


                            try {
                                UserHistroyTable userHistroyTable = new UserHistroyTable();
                                userHistroyTable.setVideo_id(video.getId());
                                userHistroyTable.setVideo_name(video.getTitle());
                                userHistroyTable.setType("CONCEPT");
                                userHistroyTable.setUser_id(MakeMyExam.userId);
                                userHistroyTable.setTileid(tileId);
                                userHistroyTable.setValid_to(video.getModified());
                                userHistroyTable.setCourse_id(courseId);
                                userHistroyTable.setCurrent_time("" + MakeMyExam.getTime_server());
                                UtkashRoom.getAppDatabase(this).getuserhistorydao().addUser(userHistroyTable);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            load_data(html);
                        } else {
                            Toast.makeText(this, "url not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        this.ErrorCallBack(jsonobject.getString(Const.MESSAGE), apitype, typeApi);
                        RetrofitResponse.GetApiData(this, jsonobject.getString(Const.AUTH_CODE), jsonobject.getString(Const.MESSAGE), false);
                    }
                } catch (Exception e) {
                }
        }
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {
        Toast.makeText(this, jsonstring, Toast.LENGTH_SHORT).show();
    }

    class DownloadMapAsync extends AsyncTask<String, String, String> {
        String result = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(Concept_newActivity.this);
            mProgressDialog.setMessage("Please Wait...Extracting zip file ... ");
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... aurl) {
            int count;
            try {
                URL url = new URL(aurl[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();
                int lenghtOfFile = conexion.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(zipFile);
                byte[] data = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    output.write(data, 0, count);
                }
                output.close();
                input.close();
                result = "true";

            } catch (Exception e) {

                result = "false";
            }
            return null;

        }

        @Override
        protected void onPostExecute(String unused) {
            if (result.equalsIgnoreCase("true")) {
                try {
                    unzip();
                } catch (IOException e) {
                    mProgressDialog.dismiss();
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                mProgressDialog.dismiss();
            }
        }
    }

    public void unzip() throws IOException {
        new UnZipTask().execute(zipFile, unzipLocation);
    }

    private class UnZipTask extends AsyncTask<String, Void, Boolean> {
        @SuppressWarnings("rawtypes")
        @Override
        protected Boolean doInBackground(String... params) {
            String filePath = params[0];
            String destinationPath = params[1];

            File archive = new File(filePath);
            try {
                ZipFile zipfile = new ZipFile(archive);
                for (Enumeration e = zipfile.entries(); e.hasMoreElements(); ) {
                    ZipEntry entry = (ZipEntry) e.nextElement();
                    unzipEntry(zipfile, entry, destinationPath);
                }
                UnzipUtil d = new UnzipUtil(zipFile, unzipLocation);
                d.unzip();
            } catch (Exception e) {

                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            mProgressDialog.dismiss();
            if (result) {

                if (new File(final_filename).exists() && new File(final_filename).getName().contains(".html")) {
                    boolean data_img_exist = false;
                    String html = getHtmlContent(final_filename);
                    htmlTbale = new HtmlTbale();
                    htmlTbale.setHtml(html);
                    htmlTbale.setCourse_id(zip_url);
                    htmlTbale.setUser_id(MakeMyExam.userId);
                    htmlTbale.setHighight(highlightdata);
                    utkashRoom.gethtmllink().addUser(htmlTbale);
                    loadHtml(html, data_img_exist);
                }
            }
        }

        private void unzipEntry(ZipFile zipfile, ZipEntry entry,
                                String outputDir) throws IOException {

            if (entry.isDirectory()) {
                createDir(new File(outputDir, entry.getName()));
                return;
            }

            File outputFile = new File(outputDir, entry.getName());
            if (!outputFile.getParentFile().exists()) {
                createDir(outputFile.getParentFile());
            }

            BufferedInputStream inputStream = new BufferedInputStream(zipfile.getInputStream(entry));
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));

            try {

            } finally {
                outputStream.flush();
                outputStream.close();
                inputStream.close();


            }
        }

        private void createDir(File dir) {
            if (dir.exists()) {
                return;
            }
            if (!dir.mkdirs()) {
                throw new RuntimeException("Can not create dir " + dir);
            }
        }
    }

    private void loadHtml(String html, boolean data_img_exist) {
        if (html.contains("../Images")) {

            html = html.replace("../Images", zip_url.substring(0, zip_url.lastIndexOf('/')) + "/extract/OEBPS/Images");
            if (html.contains("../Styles/")) {
                html = html.replace("../Styles", zip_url.substring(0, zip_url.lastIndexOf('/')) + "/extract/OEBPS/Styles");
            }
            if (html.contains("fonts/")) {
                html = html.replace("fonts/", zip_url.substring(0, zip_url.lastIndexOf('/')) + "/extract/OEBPS/fonts/");
            }
            load_data(html);


        } else {
            if (html.contains("Images/")) {
                String imgRegex = "(?i)<img[^>]+?src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>";
                Pattern p = Pattern.compile(imgRegex);
                Matcher m = p.matcher(html);
                String imag_url = "";
                while (m.find()) {
                    data_img_exist = true;
                    String imgSrc = m.group(1);
                    if (Objects.requireNonNull(imgSrc).contains("OEBPS")) {
                        imag_url = imgSrc.substring(0, imgSrc.lastIndexOf('/'));
                        break;
                    }
                }
                if (data_img_exist) {
                    if (!imag_url.equalsIgnoreCase("")) {
                        html = html.replace(imag_url, "Images");
                        html = html.replace("Images/", imag_url + "/");
                        if (html.contains("/funlearn/downloadEpubImage?source=upload/")) {
                            html = html.replace("/funlearn/downloadEpubImage?source=upload/", "https://utkarsh-efs.s3.ap-south-1.amazonaws.com/v1/");
                        }
                        load_data(html);
                    }
                } else {
                    load_data(html);
                }
            } else {
                load_data(html);
            }
        }
    }

    public class UnzipUtil {
        private final String _zipFile;
        private final String _location;

        public UnzipUtil(String zipFile, String location) {
            _zipFile = zipFile;
            _location = location;

            _dirChecker("");
        }

        public void unzip() {
            try {
                FileInputStream fin = new FileInputStream(_zipFile);
                ZipInputStream zin = new ZipInputStream(fin);
                ZipEntry ze = null;
                while ((ze = zin.getNextEntry()) != null) {
                    Log.v("Decompress", "Unzipping " + ze.getName());

                    if (ze.isDirectory()) {
                        _dirChecker(ze.getName());
                    } else {
                        if (ze.getName().contains(".html")) {
                            FileOutputStream fout = new FileOutputStream(_location + ze.getName());
                            byte[] buffer = new byte[8192];
                            int len;
                            while ((len = zin.read(buffer)) != -1) {
                                fout.write(buffer, 0, len);
                            }
                            fout.close();
                            zin.closeEntry();
                            final_filename = _location + ze.getName();
                            break;
                        }

                    }

                }
                zin.close();
            } catch (Exception e) {
                Log.e("Decompress", "unzip", e);
            }


        }

        private void _dirChecker(String dir) {
            File f = new File(_location + dir);

            if (!f.isDirectory()) {
                f.mkdirs();
            }
        }
    }

    private String getHtmlContent(String htmlFilePath) {
        StringBuilder htmlStringBuilder = new StringBuilder();
        try {

            File htmlFile = new File(htmlFilePath);

            if (htmlFile.exists()) {
                BufferedReader in = new BufferedReader(new FileReader(htmlFile));

                String str;

                while ((str = in.readLine()) != null) {
                    htmlStringBuilder.append(str);
                }
                in.close();
            }

            // htmlFile.delete();

            return htmlStringBuilder.toString();
        } catch (IOException e) {
            return "";
        }
    }


    private void load_data(String data) {
        String stringToBeRendered = getOfflineKatexConfig(data);
        stringToBeRendered = stringToBeRendered.replaceAll("</head>", "<style>\n" +
                "@font-face { font-family: 'Work Sans Regular'; src: url('file:///android_asset/fonts/WorkSans-Regular.ttf');}" +
                "@font-face { font-family: 'Work Sans Medium'; src: url('file:///android_asset/fonts/WorkSans-Medium.ttf');}" +
                "@font-face { font-family: 'Work Sans Light'; src: url('file:///android_asset/fonts/WorkSans-Light.ttf');}" +
                "@font-face { font-family: 'Work Sans Bold'; src: url('file:///android_asset/fonts/WorkSans-Bold.ttf');}" +
                "@font-face { font-family: 'Work Sans SemiBold'; src: url('file:///android_asset/fonts/WorkSans-SemiBold.ttf');}" +
                "html, body {\n" +
                "  font-family: 'Work Sans Light' !important; \n" +
                "  font-size: 16px;\n" +
                "  line-height: 24px;\n" +
                "  letter-spacing: 0.01em;\n" +
                "  color: #1B2733;\n" +
                "  font-weight: 300;\n" +
                "  overflow: auto;\n" +
                "  overflow-x: hidden;\n" +
                "  -webkit-overflow-scrolling: touch;\n" +
                "}" +
                ".table-responsive {\n" +
                "   display: block;\n" +
                "   width: 100%;\n" +
                "   overflow-x: auto;\n" +
                "   -webkit-overflow-scrolling: touch;\n" +
                "   -ms-overflow-style: -ms-autohiding-scrollbar;\n" +
                "}" +
                "body a, p, span, ul, li {\n" +
                /*"  font-weight: 300 !important;\n" +*/
                "}" +
                ".blackTheme {\n" +
                "color:black !important;\n" +
                "}\n" +

                ".whiteTheme {\n" +
                "color:white !important;\n" +
                "}\n" +
                ".whiteTheme p span{\n" +
                "color:white !important;\n" +
                "}\n" +

                "#htmlreadingcontent img {\n" +
                "   max-width: 100%;\n" +
                "   height: auto;\n" +
                "   border: 0; }\n" +
                " #htmlreadingcontent h1 {\n" +
                "   font-family: “Work Sans”, sans-serif;\n" +
                "   font-size: 30px !important;\n" +
                "   line-height: 36px !important;\n" +
                "   letter-spacing: 0.04em !important;\n" +
                "   color: #1B2733 !important; }\n" +
                " #htmlreadingcontent h2 {\n" +
                "   font-family: “Work Sans”, sans-serif;\n" +
                "   font-size: 24px !important;\n" +
                "   line-height: 30px !important;\n" +
                "   letter-spacing: 0.02em !important;\n" +
                "   color: #1B2733 !important; }\n" +
                " #htmlreadingcontent h3 {\n" +
                "   font-family: “Work Sans”, sans-serif;\n" +
                "   font-size: 18px !important;\n" +
                "   line-height: 28px !important;\n" +
                "   letter-spacing: 0.01em !important;\n" +
                "   color: #1B2733 !important; }\n" +
                " #htmlreadingcontent p, #htmlreadingcontent span {\n" +
                "   font-family: “Work Sans”, sans-serif; }\n" +
                " #htmlreadingcontent span.annotator-hlh {\n" +
                "   font-size: inherit !important;\n" +
                "   font-family: inherit; }\n" +
                " #htmlreadingcontent span.annotator-hl {\n" +
                "   font-size: inherit !important;\n" +
                "   font-family: inherit; }\n" +
                "\n" +
                "#htmlreadingcontent table {\n" +
                "   width: 100% !important;\n" +
                "    border-collapse: collapse;\n" +
                "}\n" +
                "#htmlreadingcontent table td p {\n" +
                "text-align:unset !important;\n" +
                "}\n" +
                " </style>");


        mChapterContentWebView.getSettings().setLoadWithOverviewMode(false);
        mChapterContentWebView.getSettings().setUseWideViewPort(false);
        mChapterContentWebView.getSettings().setSupportZoom(true);

        if (stringToBeRendered.contains("<table")) {
            stringToBeRendered = stringToBeRendered.replace("<table", "<div class=table-responsive><table");
            stringToBeRendered = stringToBeRendered.replace("</table>", "</table></div>");
        }


        mChapterContentWebView.loadDataWithBaseURL("file:///android_asset/font/", stringToBeRendered,
                "text/html", "UTF-8", "about:blank");

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (textformatterdialog != null && textformatterdialog.getVisibility() == View.VISIBLE) {
            textformatterdialog.setVisibility(View.GONE);
            action_text_formate.getDrawable().clearColorFilter();
        }

    }


    class ChatBotChromeClient extends WebChromeClient {
        // For 3.0+ Devices (Start)
        // onActivityResult attached before constructor
        protected void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            selectfile = 1;
            imageorResult.launch(Intent.createChooser(i, "File Chooser"));

            // startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
        }

        // For Lollipop 5.0+ Devices
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            if (uploadMessage != null) {
                uploadMessage.onReceiveValue(null);
                uploadMessage = null;
            }

            uploadMessage = filePathCallback;

            ////check
            fileChooserParams.getAcceptTypes();

            Intent intent = fileChooserParams.createIntent();
            try {
                selectfile = 100;
                imageorResult.launch(intent);

                // startActivityForResult(intent, REQUEST_SELECT_FILE);
            } catch (ActivityNotFoundException e) {
                uploadMessage = null;
                Toast.makeText(Concept_newActivity.this, "Cannot Open File Chooser", Toast.LENGTH_LONG).show();
                return false;
            }
            return true;
        }

        //For Android 4.1 only
        protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            mUploadMessage = uploadMsg;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            selectfile = 1;
            imageorResult.launch(Intent.createChooser(intent, "File Chooser"));

            //  startActivityForResult(Intent.createChooser(intent, "File Chooser"), FILECHOOSER_RESULTCODE);
        }

        protected void openFileChooser(ValueCallback<Uri> uploadMsg) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            selectfile = 1;
            imageorResult.launch(Intent.createChooser(i, "File Chooser"));

            //  startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
        }

        // Need to accept permissions to use the camera
        @Override
        public void onPermissionRequest(final PermissionRequest request) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                request.grant(request.getResources());
            } else {
                Toast.makeText(Concept_newActivity.this, "This is not supported on this Android Version.", Toast.LENGTH_SHORT).show();
            }
        }

        public boolean onConsoleMessage(ConsoleMessage cm) {
            if (cm != null && cm.sourceId().length() > 0) {

            } else if (cm != null) {

            }
            return true;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

    }

    ActivityResultLauncher<Intent> imageorResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {

                    try {
                        Intent data = result.getData();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            if (selectfile == REQUEST_SELECT_FILE) {
                                if (uploadMessage == null)
                                    return;
                                uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(result.getResultCode(), data));
                                uploadMessage = null;
                            }
                        } else if (selectfile == FILECHOOSER_RESULTCODE) {
                            if (null == mUploadMessage)
                                return;
                            // Use MainActivity.RESULT_OK if you're implementing WebView inside Fragment
                            // Use RESULT_OK only if you're implementing WebView inside an Activity
                            Uri uriresult = data == null ? null : data.getData();
                            mUploadMessage.onReceiveValue(uriresult);
                            mUploadMessage = null;
                        } else
                            Toast.makeText(Concept_newActivity.this, "Failed to Upload Image", Toast.LENGTH_LONG).show();


                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }

            });
    /*   @Override
       public void onActivityResult(int requestCode, int resultCode, Intent intent) {
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
               if (requestCode == REQUEST_SELECT_FILE) {
                   if (uploadMessage == null)
                       return;
                   uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
                   uploadMessage = null;
               }
           }
           else if (requestCode == FILECHOOSER_RESULTCODE) {
               if (null == mUploadMessage)
                   return;
               // Use MainActivity.RESULT_OK if you're implementing WebView inside Fragment
               // Use RESULT_OK only if you're implementing WebView inside an Activity
               Uri result = intent == null || resultCode != Activity.RESULT_OK ? null : intent.getData();
               mUploadMessage.onReceiveValue(result);
               mUploadMessage = null;
           } else
               Toast.makeText(Concept_newActivity.this, "Failed to Upload Image", Toast.LENGTH_LONG).show();

           super.onActivityResult(requestCode, resultCode, intent);
       }
   */

}