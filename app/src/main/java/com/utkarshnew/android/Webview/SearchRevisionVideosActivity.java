package com.utkarshnew.android.Webview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.clans.fab.FloatingActionButton;
import com.github.jorgecastilloprz.FABProgressCircle;
import com.github.jorgecastilloprz.listeners.FABProgressListener;
import com.google.gson.Gson;
import com.utkarshnew.android.BuildConfig;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.home.Constants;
import com.utkarshnew.android.Model.Video;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;

import static android.webkit.WebView.setWebContentsDebuggingEnabled;

public class SearchRevisionVideosActivity extends AppCompatActivity implements View.OnClickListener, FABProgressListener, NetworkCall.MyNetworkCallBack {

    private static final String TAG = "SearchRevisionVideosActivity";

    private WebView webResourceWebView;
    private Toolbar webResourceToolbar, webResourceBottomBar;
    private boolean mShopView;
    private WSWebChromeClient webChromeClient;
    private String videoUrl = "", tile_id = "", course_id = "";
    private String searchString, mChapterId = "", chapterName, resourceContext, resourceIntent,
            urlString, bookId, chapterDesc, level, grade, subject, syllabus;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;
    private ValueCallback<Uri[]> mUploadMessage;
    private String mCameraPhotoPath = null;

    private String SelectedChapter;
    private View mCustomView;
    private RelativeLayout mContentView;
    private FrameLayout mCustomViewContainer;

    private FloatingActionButton browserAddContent;
    private ProgressBar webresourceloader;
    private FABProgressCircle fabProgressCircle;
    ArrayList<Video> videoArrayList = new ArrayList<>();
    ArrayList<Video> seleced_tile_list = new ArrayList<>();
    private boolean showAddResourceBtn, videoResourceLocal, solutionAdded, videoAdded, isFromDeepLink;

    private String query = "", f_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.activity_searchvideo_revision);

        searchString = getIntent().getStringExtra("search_query");
        tile_id = getIntent().getStringExtra("t_id");
        course_id = getIntent().getStringExtra("c_id");
        f_id = getIntent().getStringExtra("f_id");


        seleced_tile_list = (ArrayList<Video>) getIntent().getSerializableExtra("s_list");
        videoArrayList = (ArrayList<Video>) getIntent().getSerializableExtra("v_list");


        if (getIntent().getStringExtra("resourceContext") != null &&
                !getIntent().getStringExtra("resourceContext").equalsIgnoreCase("null")) {
            resourceContext = getIntent().getStringExtra("resourceContext");
            showAddResourceBtn = true;
        } else {
            resourceContext = "";
        }
        if (getIntent().getStringExtra("intent") != null &&
                !getIntent().getStringExtra("intent").equalsIgnoreCase("null")) {
            resourceIntent = getIntent().getStringExtra("intent");
        } else {
            resourceIntent = "";
        }


        webResourceWebView = findViewById(R.id.webresourcewebview);
        webResourceToolbar = findViewById(R.id.webresourcetoolbar);

        webresourceloader = findViewById(R.id.webresourceloader);
        fabProgressCircle = findViewById(R.id.fabProgressCircle);
        fabProgressCircle.attachListener(this);

        webresourceloader.setVisibility(View.VISIBLE);
        browserAddContent = findViewById(R.id.add_weblink);
        if (showAddResourceBtn) {
            if (isResourceList(searchString)) {
                fabProgressCircle.setVisibility(View.GONE);
            } else {
                fabProgressCircle.setVisibility(View.VISIBLE);
            }
        } else {
            fabProgressCircle.setVisibility(View.GONE);
        }

        browserAddContent.setOnClickListener(this);

        if (getSupportActionBar() == null) {
            setSupportActionBar(webResourceToolbar);
            if (getSupportActionBar() != null) {
                if (resourceIntent != null && !resourceIntent.isEmpty()) {
                    if (resourceIntent.equalsIgnoreCase("videos")) {
                        getSupportActionBar().setTitle("Video References");
                    } else if (resourceIntent.equalsIgnoreCase("webref")) {
                        getSupportActionBar().setTitle("Web References");
                    } else {
                        getSupportActionBar().setTitle("References");
                    }
                } else {
                    getSupportActionBar().setTitle("References");
                }
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back_black);
            } else {
                return;
            }
        }

        if (mChapterId.isEmpty() && mShopView) {
            browserAddContent.setVisibility(View.GONE);
        }
        configureWebView(webResourceWebView);

        if (searchString != null && !searchString.equalsIgnoreCase("")) {
            webResourceWebView.loadUrl(searchString);
        } else if (urlString != null && !urlString.equalsIgnoreCase("")) {
            webResourceWebView.loadUrl(urlString);
        } else {
            Toast.makeText(this, "URL not valid", Toast.LENGTH_SHORT).show();
            finish();
        }


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (resourceIntent.equalsIgnoreCase("videos")) {
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                if (webResourceToolbar != null) {
                    webResourceToolbar.setVisibility(View.GONE);
                }
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            } else {
                if (webResourceToolbar != null) {
                    webResourceToolbar.setVisibility(View.VISIBLE);
                }
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        }
    }


    private void configureWebView(final WebView webView) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setVerticalScrollBarEnabled(true);
        webView.setHorizontalScrollBarEnabled(true);
        webView.setClickable(true);
        webView.setLongClickable(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        /*String newUA= "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
        webView.getSettings().setUserAgentString(newUA);*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
        }

        webChromeClient = new WSWebChromeClient();

        webView.setWebChromeClient(webChromeClient);


        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView webView, String url, Bitmap favicon) {

                super.onPageStarted(webView, url, favicon);
                webresourceloader.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                view.loadUrl("about:blank");
                Toast.makeText(SearchRevisionVideosActivity.this, "Links to some external content might not open here.", Toast.LENGTH_SHORT).show();
                super.onReceivedError(view, errorCode, description, failingUrl);

            }
        });
        webView.getSettings().setDisplayZoomControls(false);

        webView.setDownloadListener((s, s1, s2, s3, l) -> {
            if (s3.toLowerCase().contains("PDF".toLowerCase())) {
                Uri uri = Uri.parse(urlString);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                SearchRevisionVideosActivity.this.startActivity(intent);
            } else {
                Uri uri = Uri.parse(s);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                SearchRevisionVideosActivity.this.startActivity(intent);
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        //Handle Close Button CLicks
        if (getSupportActionBar() != null) {
            onBackPressed();
            return false;
        } else {
            return true;
        }
    }

    private boolean isResourceList(String link) {
        if (resourceContext != null && !resourceContext.isEmpty() && resourceContext.equalsIgnoreCase("web")) {
            return link == null || link.contains("www.google.com/search?");
        } else if (resourceContext != null && !resourceContext.isEmpty() && resourceContext.equalsIgnoreCase("video")) {
            String videoId;

            String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";

            Pattern compiledPattern = Pattern.compile(pattern);
            Matcher matcher = compiledPattern.matcher(link); //url is youtube url for which you want to extract the id.
            if (matcher.find()) {
                videoId = matcher.group();
            } else {
                videoId = "";
            }

            return videoId == null || videoId.isEmpty();
        } else {
            return false;
        }
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {

        switch (apitype) {
            case API.video_link:
                if (resourceContext.contains("web")) {
                    EncryptionData masterdataencryptionData = new EncryptionData();
                    masterdataencryptionData.setCourse_id(course_id);
                    masterdataencryptionData.setType("2");
                    masterdataencryptionData.setFile_id(f_id);
                    masterdataencryptionData.setTile_id(tile_id);
                    masterdataencryptionData.setLink(videoUrl);
                    String masterdataencryptionDatadoseStr = new Gson().toJson(masterdataencryptionData);
                    String masterdatadoseStrScr = AES.encrypt(masterdataencryptionDatadoseStr);
                    return service.video_link(masterdatadoseStrScr);
                } else {
                    EncryptionData masterdataencryptionData = new EncryptionData();
                    masterdataencryptionData.setCourse_id(course_id);
                    masterdataencryptionData.setType("1");
                    masterdataencryptionData.setFile_id(f_id);
                    masterdataencryptionData.setTile_id(tile_id);
                    masterdataencryptionData.setLink(videoUrl);

                    String masterdataencryptionDatadoseStr = new Gson().toJson(masterdataencryptionData);
                    String masterdatadoseStrScr = AES.encrypt(masterdataencryptionDatadoseStr);
                    return service.video_link(masterdatadoseStrScr);
                }

        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonstring, String apitype, String typeApi, boolean showprogress) throws JSONException {

        switch (apitype) {
            case API.video_link:
                try {
                    if (jsonstring.optString(Const.STATUS).equals(Const.TRUE)) {
                        JSONObject data = jsonstring.getJSONObject(Const.DATA);
                        if (data.optString("file_type") != null && data.optString("file_type").equalsIgnoreCase("10") || data.optString("file_type").equalsIgnoreCase("11")) {
                            if (seleced_tile_list.get(0).getFile_type() != null && seleced_tile_list.get(0).getFile_type().equalsIgnoreCase("3")) {
                                videoArrayList.add(videoArrayList.size(), new Gson().fromJson(data.toString(), Video.class));
                                seleced_tile_list.add(seleced_tile_list.size(), new Gson().fromJson(data.toString(), Video.class));
                            } else {
                                videoArrayList.add(videoArrayList.size(), new Gson().fromJson(data.toString(), Video.class));
                            }
                        }
                        Constants.revison_set = true;

                        MakeMyExam.videoArrayList = videoArrayList;
                        MakeMyExam.seleced_tile_list = seleced_tile_list;

                        fabProgressCircle.beginFinalAnimation();
                        Toast.makeText(this, "" + jsonstring.optString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
                        browserAddContent.setEnabled(false);
                    } else {
                        browserAddContent.setEnabled(true);
                        fabProgressCircle.hide();
                        RetrofitResponse.GetApiData(this, jsonstring.has(Const.AUTH_CODE) ? jsonstring.getString(Const.AUTH_CODE) : "", jsonstring.getString(Const.MESSAGE), false);
                    }
                } catch (Exception ex) {
                    browserAddContent.setEnabled(true);
                    fabProgressCircle.hide();
                    this.ErrorCallBack(ex.getMessage() + " : " + ex.getLocalizedMessage(), apitype, typeApi);
                    ex.printStackTrace();
                }
                break;
        }

    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {
        browserAddContent.setEnabled(true);
        fabProgressCircle.hide();
    }

    public class WSWebChromeClient extends WebChromeClient {
        FrameLayout.LayoutParams LayoutParameters = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);

        @SuppressLint("LongLogTag")
        public boolean onConsoleMessage(ConsoleMessage cm) {
            if (cm != null && cm.sourceId().length() > 0) {
                Log.e(TAG, cm.message() + " -- From line "
                        + cm.lineNumber() + " of "
                        + cm.sourceId().substring(10));
            } else if (cm != null) {
                Log.e(TAG, cm.message() + " -- From line "
                        + cm.lineNumber());
            }
            return true;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                webresourceloader.setVisibility(View.GONE);

                if (showAddResourceBtn) {
                    if (isResourceList(view.getUrl())) {
                        fabProgressCircle.setVisibility(View.GONE);
                    } else {
                        fabProgressCircle.setVisibility(View.VISIBLE);
                    }
                } else {
                    fabProgressCircle.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            if (resourceIntent.equalsIgnoreCase("videos")) {
                // if a view already exists then immediately terminate the new one
                if (mCustomView != null) {
                    callback.onCustomViewHidden();
                    return;
                }
                mContentView = findViewById(R.id.webresourceviewlayout);
                mContentView.setVisibility(View.GONE);
                mCustomViewContainer = new FrameLayout(SearchRevisionVideosActivity.this);
                mCustomViewContainer.setLayoutParams(LayoutParameters);
                mCustomViewContainer.setBackgroundResource(android.R.color.black);
                view.setLayoutParams(LayoutParameters);
                mCustomViewContainer.addView(view);
                mCustomView = view;
                mCustomViewCallback = callback;
                mCustomViewContainer.setVisibility(View.VISIBLE);
                setContentView(mCustomViewContainer);
            }
        }

        @Override
        public void onHideCustomView() {
            if (resourceIntent.equalsIgnoreCase("videos")) {
                if (mCustomView == null) {
                    return;
                } else {
                    // Hide the custom view.
                    mCustomView.setVisibility(View.GONE);
                    // Remove the custom view from its container.
                    mCustomViewContainer.removeView(mCustomView);
                    mCustomView = null;
                    mCustomViewContainer.setVisibility(View.GONE);
                    mCustomViewCallback.onCustomViewHidden();
                    // Show the content view.
                    mContentView.setVisibility(View.VISIBLE);
                    setContentView(mContentView);
                }
            }
        }

        // For Android 5.0+
        @SuppressLint("LongLogTag")
        @Override
        public boolean onShowFileChooser(WebView view, ValueCallback<Uri[]> filePath, WebChromeClient.FileChooserParams fileChooserParams) {
            // Double check that we don't have any existing callbacks
            if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(null);
            }
            mUploadMessage = filePath;
            Log.e("FileCooserParams => ", filePath.toString());

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                    takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
                } catch (IOException ex) {
                    // Error occurred while creating the File
                    Log.e(TAG, "Unable to create Image File", ex);
                }

                // Continue only if the File was successfully created
                if (photoFile != null) {
                    mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                } else {
                    takePictureIntent = null;
                }
            }

            Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
            contentSelectionIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            contentSelectionIntent.setType("*/*");

            Intent[] intentArray;
            if (takePictureIntent != null) {
                intentArray = new Intent[]{takePictureIntent};
            } else {
                intentArray = new Intent[2];
            }

            Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
            chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
            chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);

          //  startActivityForResult(Intent.createChooser(chooserIntent, "Select images"), 1);

            return true;

        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return imageFile;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.web_resource_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.add_weblink:
                browserAddContent.setEnabled(false);
                fabProgressCircle.show();
                if (searchString.contains("youtube")) {
                    browserAddContent.setEnabled(false);
                    if (resourceContext != null && resourceContext.equalsIgnoreCase("video")) {
                        String webLink = webResourceWebView.getUrl();
                        String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";
                        Pattern compiledPattern = Pattern.compile(pattern);
                        Matcher matcher = compiledPattern.matcher(webLink);
                        if (matcher.find()) {
                            fabProgressCircle.beginFinalAnimation();
                            videoUrl = matcher.group();
                            NetworkCall networkCall = new NetworkCall(this, this);
                            networkCall.NetworkAPICall(API.video_link, "", false, false);
                        } else {
                            browserAddContent.setEnabled(true);
                            fabProgressCircle.hide();
                            Toast.makeText(this, "This videos will not attach ", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        browserAddContent.setEnabled(true);
                    }
                } else if (searchString.contains("google")) {
                    browserAddContent.setEnabled(false);
                    if (resourceContext != null && resourceContext.equalsIgnoreCase("web")) {
                        final String webLink = webResourceWebView.getUrl();
                        if (webLink != null && !webLink.contains("www.google.com/search?")) {
                            fabProgressCircle.beginFinalAnimation();

                            videoUrl = webLink;
                            NetworkCall networkCall = new NetworkCall(this, this);
                            networkCall.NetworkAPICall(API.video_link, "", false, false);
                        } else {
                            browserAddContent.setEnabled(true);
                            fabProgressCircle.hide();
                            Toast.makeText(this, "This link will not attach ", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "This link will not attach ", Toast.LENGTH_SHORT).show();
                        fabProgressCircle.hide();
                        browserAddContent.setEnabled(true);
                    }
                }

                break;
            default:
                break;
        }
    }


    @Override
    public void onFABProgressAnimationEnd() {
        if (solutionAdded) {
            Toast.makeText(this, "Your selected weblink successfully added to the chapter.", Toast.LENGTH_SHORT).show();

        }

        if (videoAdded) {
            Toast.makeText(this, "Your selected video has been successfully added to the chapter.", Toast.LENGTH_SHORT).show();

        }
    }

/*
    public class AddWebResource extends AsyncTask<String, Void, JSONObject> {

        @SuppressLint("LongLogTag")
        protected JSONObject doInBackground(String... webLinkUrl) {
            JSONObject respJson;
            String resName;
            try {
                String webLink = webLinkUrl[0];
                //String videoId;

               if (resourceContext != null && !resourceContext.isEmpty() && resourceContext.equalsIgnoreCase("video")) {
                    String videoId;

                    URL newUrl = new URL("http://www.youtube.com/oembed?url=" + webLink + "&format=json");

                    String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";

                    Pattern compiledPattern = Pattern.compile(pattern);
                    Matcher matcher = compiledPattern.matcher(webLink); //url is youtube url for which you want to extract the id.
                    if (matcher.find()) {
                        videoId = matcher.group();
                        Log.d(TAG, "video link: " + webLink);
                    } else {
                        videoId = "";
                    }

                    if (videoId != null && !videoId.isEmpty()) {

                        HashMap<String, String> dictionary = new HashMap();
                        dictionary.put("link", videoId);
                        dictionary.put("resourceType", "Reference Videos");
                        dictionary.put("chapterId", mChapterId);
                        if (webLinkUrl[1] != null && !webLinkUrl[1].isEmpty()) {
                            dictionary.put("resourceName", webLinkUrl[1]);
                        } else {
                            dictionary.put("resourceName", chapterName);
                        }
                        dictionary.put("from", "app");
                        dictionary.put("siteId", "1");

                        WPHttpClient httpClient = new WPHttpClient(
                                WSAPIManager.SERVICE_ADD_RESOURCE,
                                dictionary, WPHttpClient.HTTP_METHOD_POST);

                        httpClient.run();
                        respJson = httpClient.getResponseJson();
                        Log.e(TAG, "respJson: " + respJson);

                        return respJson;
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } catch (Exception e) {
                return null;
            }
        }

        protected void onPostExecute(JSONObject resp) {
            Log.d(TAG, "web link add resp: " + resp);
            if (resp != null && resp.has("resId")) {
                try {
                    if (resourceContext != null && resourceContext.equalsIgnoreCase("web")) {
                        if (!WebLinksFragment.webLinkLocal) {
                            Bundle bundle = new Bundle();
                            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "ResId: " + resp.getString("resId"));
                            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "add_solution");
                            mFirebaseAnalytics.logEvent("Add_Solution_Clicked", bundle);

                            fabProgressCircle.beginFinalAnimation();
                            if (BuildConfig.FLAVOR.contains("utkarsh")) {
                                customSnackBar.show("Your selected weblink successfully added to the chapter.", CustomSnackBar.LENGTH_SHORT);
                            } else {
                                customSnackBar.show("Solution added to chapter", CustomSnackBar.LENGTH_SHORT);
                            }
                            WebLinksFragment.webLinkRefresh = true;
                            AllElementsFragment.allFragmentRefresh = true;
                            browserAddContent.setEnabled(false);
                        }

                        //Add resId to local json
                        WonderPubSharedPrefs sharedPrefs = WonderPubSharedPrefs.getInstance(Wonderslate.getInstance().getContext());

                        String detailsJsonStr = sharedPrefs.getSharedPrefsUserChapDetails(mChapterId);

                        JSONObject detailsObject = new JSONObject(detailsJsonStr);

                        JSONArray resultArray = detailsObject.getJSONArray("results");

                        for (int i = 0; i < resultArray.length(); i++) {

                            if (resultArray.getJSONObject(i).getString("resType").equalsIgnoreCase("Reference Web Links")) {
                                if (resultArray.getJSONObject(i).getString("resLink").equalsIgnoreCase(webUrl)) {
                                    resultArray.getJSONObject(i).put("id", resp.getString("resId"));
                                }
                            }
                        }

                        sharedPrefs.setSharedPrefsUserChapDetails(mChapterId, detailsObject.toString());
                    } else if (resourceContext != null && resourceContext.equalsIgnoreCase("video")) {

                        if (!VideoLinksFragment.videoLinkLocal) {
                            Bundle bundle = new Bundle();
                            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "ResId: " + resp.getString("resId"));
                            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "add_video_link");
                            mFirebaseAnalytics.logEvent("Add_Video_Link_Clicked", bundle);

                            VideoLinksFragment.videoLinksRefresh = true;
                            AllElementsFragment.allFragmentRefresh = true;
                            fabProgressCircle.beginFinalAnimation();
                            customSnackBar.show("Your selected video has been successfully added to the chapter.", CustomSnackBar.LENGTH_SHORT);
                            browserAddContent.setEnabled(false);
                        }

                        //Add resId to local json
                        WonderPubSharedPrefs sharedPrefs = WonderPubSharedPrefs.getInstance(Wonderslate.getInstance().getContext());

                        String detailsJsonStr = sharedPrefs.getSharedPrefsUserChapDetails(mChapterId);

                        JSONObject detailsObject = new JSONObject(detailsJsonStr);

                        JSONArray resultArray = detailsObject.getJSONArray("results");

                        for (int i = 0; i < resultArray.length(); i++) {

                            if (resultArray.getJSONObject(i).getString("resType").equalsIgnoreCase("Reference Videos")) {
                                if (resultArray.getJSONObject(i).getString("resLink").equalsIgnoreCase(videoUrl)) {
                                    resultArray.getJSONObject(i).put("id", resp.getString("resId"));
                                }
                            }
                        }

                        sharedPrefs.setSharedPrefsUserChapDetails(mChapterId, detailsObject.toString());
                    } else {
                        Utils.showErrorToast(WebLinkResourceWebView.this, 500);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "JSON Exception value:", e);
                }
            } else {
                fabProgressCircle.hide();
                customSnackBar.show("URL not valid", CustomSnackBar.LENGTH_SHORT);
                browserAddContent.setEnabled(true);
            }
        }
    }
*/


}