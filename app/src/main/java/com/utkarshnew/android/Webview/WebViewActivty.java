package com.utkarshnew.android.Webview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.DownloadListener;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.utkarshnew.android.BuildConfig;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.table.UserHistroyTable;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Progress;

import java.util.regex.Pattern;

import static android.webkit.WebView.setWebContentsDebuggingEnabled;

public class WebViewActivty extends AppCompatActivity {

    private WebView webView;
    private ImageView image_back;
    private static Progress progress;
    TextView toolbarTitleTV;
    public static final String TAG_START = "<\\w+((\\s+\\w+(\\s*=\\s*(?:\".*?\"|'.*?'|[^'\">\\s]+))?)+\\s*|\\s*)>";
    public static final String TAG_END = "</\\w+>";
    public static final String TAG_SELF_CLOSING = "<\\w+((\\s+\\w+(\\s*=\\s*(?:\".*?\"|'.*?'|[^'\">\\s]+))?)+\\s*|\\s*)/>";
    public static final String HTML_ENTITY = "&[a-zA-Z][a-zA-Z0-9]+;";
    public static final Pattern htmlPattern = Pattern
            .compile("(" + TAG_START + ".*" + TAG_END + ")|(" + TAG_SELF_CLOSING + ")|(" + HTML_ENTITY + ")", Pattern.DOTALL);
    public ValueCallback<Uri[]> uploadMessage;
    private ValueCallback<Uri> mUploadMessage;
    int selectfile = 0;

    public static final int REQUEST_SELECT_FILE = 100;
    private final static int FILECHOOSER_RESULTCODE = 1;


    String file_type = "", video_id, link = "", course_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.webview_activity);
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
            webView = findViewById(R.id.webresourcewebview);
            webView.setHapticFeedbackEnabled(false);
            webView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return true;
                }
            });
            webView.setLongClickable(false);

            toolbarTitleTV = findViewById(R.id.toolbarTitleTV);
            image_back = findViewById(R.id.image_back);
            image_back.setOnClickListener(new OnSingleClickListener(() -> {
                finish();
                return null;
            }));
            file_type = getIntent().getStringExtra("file_type") == null ? "" : getIntent().getStringExtra("file_type");
            toolbarTitleTV.setText(getIntent().getStringExtra("type"));
            video_id = getIntent().getStringExtra("video_id");
            link = getIntent().getStringExtra("link");

            course_id = getIntent().getStringExtra("course_id");
            course_id = course_id == null ? "" : course_id;

            link = link == null ? "" : getIntent().getStringExtra("link");
            video_id = video_id == null || video_id.equalsIgnoreCase("") ? getIntent().getStringExtra("type") : getIntent().getStringExtra("video_id");
            progress = new Progress(this);
            startChatBot(getIntent().getStringExtra("url"));

            if (file_type != null && file_type.equalsIgnoreCase("image")) {
                webView.getSettings().setSupportZoom(true);
                webView.getSettings().setBuiltInZoomControls(true);
                webView.getSettings().setDisplayZoomControls(false);
            }
        } catch (Exception e) {
            if (progress.isShowing())
                progress.dismiss();
            e.printStackTrace();
        }
    }

    private void startChatBot(String url) {
        Log.d("prince", "" + url);
        progress.show();
        configureWebView(webView);
        if (url != null) {
            if (!link.equalsIgnoreCase("") && link.equalsIgnoreCase("8")) {
                AsyncTask.execute(() -> {
                    try {
                        UserHistroyTable userHistroyTable = new UserHistroyTable();
                        userHistroyTable.setVideo_id(video_id);
                        userHistroyTable.setPdf_name(toolbarTitleTV.getText().toString().trim());
                        userHistroyTable.setType("8");
                        userHistroyTable.setPdf_url(url);
                        userHistroyTable.setUser_id(MakeMyExam.userId);
                        userHistroyTable.setCurrent_time("" + MakeMyExam.getTime_server());
                        userHistroyTable.setCourse_id(course_id);
                        UtkashRoom.getAppDatabase(this).getuserhistorydao().addUser(userHistroyTable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            if (htmlPattern.matcher(url).find()) {
                Resources res = getResources();
                webView.loadData(url, "text/html", "UTF-8");
            } else {
                Resources res = getResources();
                webView.loadUrl(url);

                webView.setDownloadListener((s, s1, s2, s3, l) -> {
                    if (s3.toLowerCase().contains("PDF".toLowerCase())) {
                        Uri uri = Uri.parse(url);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    } else {
                        Uri uri = Uri.parse(s);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
                if (!url.contains("drive.google.com")) {
                    webView.setDownloadListener(new DownloadListener() {

                        @Override
                        public void onDownloadStart(String url, String userAgent,
                                                    String contentDisposition, String mimetype,
                                                    long contentLength) {
                            DownloadManager.Request request = new DownloadManager.Request(
                                    Uri.parse(url));

                            request.allowScanningByMediaScanner();
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, toolbarTitleTV.getText().toString().equals("") ? "Utkarsh" + System.currentTimeMillis() + ".pdf" : url.substring(url.lastIndexOf('/') + 1));
                            DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                            dm.enqueue(request);
                            Toast.makeText(getApplicationContext(), "Downloading File", //To notify the Client that the file is being downloaded
                                    Toast.LENGTH_LONG).show();
                            if (progress.isShowing())
                                progress.dismiss();
                        }
                    });

                }

            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void configureWebView(final WebView webView) {
        webView.getSettings().setJavaScriptEnabled(true);
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
        webView.setWebViewClient(new CustomWebViewClient());

        webView.getSettings().setAllowContentAccess(true);
        webView.setWebChromeClient(new ChatBotChromeClient());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // When user clicks a hyperlink, load in the existing WebView
            if (!progress.isShowing()) {
                progress.show();
            }
            view.loadUrl(url);
            return true;
        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }


        @Override
        public void onReceivedError(WebView view, WebResourceRequest request,
                                    WebResourceError error) {

            if (!isFinishing()) {
                try {
                    webView.stopLoading();
                } catch (Exception e) {
                }

                if (webView.canGoBack()) {
                    webView.goBack();
                }
                if (progress != null)
                    if (progress.isShowing())
                        progress.dismiss();
                webView.loadUrl("about:blank");
                AlertDialog alertDialog = new AlertDialog.Builder(WebViewActivty.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Check your internet connection and try again.");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Try Again", (dialog, which) -> {
                    finish();
                    startActivity(getIntent());
                });

                alertDialog.show();
            }


            super.onReceivedError(view, request, error);

        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onReceivedHttpError(WebView view,
                                        WebResourceRequest request, WebResourceResponse errorResponse) {

            super.onReceivedHttpError(view, request, errorResponse);
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            if (progress != null)
                if (progress.isShowing())
                    progress.dismiss();
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
                Toast.makeText(WebViewActivty.this, "Cannot Open File Chooser", Toast.LENGTH_LONG).show();
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
                Toast.makeText(WebViewActivty.this, "This is not supported on this Android Version.", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(WebViewActivty.this, "Failed to Upload Image", Toast.LENGTH_LONG).show();


                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }

            });

/*
    class ChatBotChromeClient extends WebChromeClient {
        // For 3.0+ Devices (Start)
        // onActivityResult attached before constructor
        protected void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
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
                startActivityForResult(intent, REQUEST_SELECT_FILE);
            } catch (ActivityNotFoundException e) {
                uploadMessage = null;
                Toast.makeText(WebViewActivty.this, "Cannot Open File Chooser", Toast.LENGTH_LONG).show();
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
            startActivityForResult(Intent.createChooser(intent, "File Chooser"), FILECHOOSER_RESULTCODE);
        }

        protected void openFileChooser(ValueCallback<Uri> uploadMsg) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
        }

        // Need to accept permissions to use the camera
        @Override
        public void onPermissionRequest(final PermissionRequest request) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                request.grant(request.getResources());
            } else {
                Toast.makeText(WebViewActivty.this, "This is not supported on this Android Version.", Toast.LENGTH_SHORT).show();
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
*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode == REQUEST_SELECT_FILE) {
                if (uploadMessage == null)
                    return;
                uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
                uploadMessage = null;
            }
        } else if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            // Use MainActivity.RESULT_OK if you're implementing WebView inside Fragment
            // Use RESULT_OK only if you're implementing WebView inside an Activity
            Uri result = intent == null || resultCode != Activity.RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        } else
            Toast.makeText(WebViewActivty.this, "Failed to Upload Image", Toast.LENGTH_LONG).show();

        super.onActivityResult(requestCode, resultCode, intent);
    }
}
