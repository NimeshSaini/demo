package com.utkarshnew.android.Webview;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.utkarshnew.android.BuildConfig;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.table.UserHistroyTable;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Progress;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URL;

import static android.webkit.WebView.setWebContentsDebuggingEnabled;

public class PdfHtmlAcivity extends AppCompatActivity {

    private WebView webView;
    private ImageView image_back;
    private static Progress progress;
    TextView toolbarTitleTV;
    String file_type = "", video_id, course_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.webview_html_activity);
        try {
            if (Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
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
            course_id = getIntent().getStringExtra("course_id");
            video_id = video_id == null || video_id.equalsIgnoreCase("") ? getIntent().getStringExtra("type") : getIntent().getStringExtra("video_id");
            progress = new Progress(this);
            startChatBot(getIntent().getStringExtra("url"));


        } catch (Exception e) {
            if (progress.isShowing())
                progress.dismiss();
            e.printStackTrace();
        }
    }

    private void startChatBot(String url) {
        progress.show();
        configureWebView(webView, url);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void configureWebView(final WebView webView, String url) {


        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(true);
        webView.setHorizontalScrollBarEnabled(true);
        webView.setClickable(true);
        webView.setLongClickable(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebChromeClient(new ChatBotChromeClient());
        setWebContentsDebuggingEnabled(BuildConfig.DEBUG);

        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        Resources res = getResources();

        webView.getSettings().setDefaultFontSize((int) res.getDimension(R.dimen.sp12));
        AsyncTask.execute(() -> {
            try {
                URL google = new URL(url);
                BufferedReader in = new BufferedReader(new InputStreamReader(google.openStream()));
                String input;
                StringBuffer stringBuffer = new StringBuffer();
                while ((input = in.readLine()) != null) {
                    stringBuffer.append(input);
                }
                in.close();
                String htmlData = stringBuffer.toString();

                runOnUiThread(() -> {
                    webView.loadDataWithBaseURL(null, htmlData, "text/html; charset=UTF-8", "UTF-8",
                            null);
                });

                try {
                    UserHistroyTable userHistroyTable = new UserHistroyTable();
                    userHistroyTable.setVideo_id(video_id);
                    userHistroyTable.setPdf_name(toolbarTitleTV.getText().toString().trim());
                    userHistroyTable.setType("PDF");
                    userHistroyTable.setPdf_url(url);
                    userHistroyTable.setUser_id(MakeMyExam.userId);
                    userHistroyTable.setCourse_id(course_id);
                    userHistroyTable.setCurrent_time("" + MakeMyExam.getTime_server());
                    UtkashRoom.getAppDatabase(this).getuserhistorydao().addUser(userHistroyTable);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                if (e instanceof FileNotFoundException) {
                    e.printStackTrace();
                    runOnUiThread(() -> {
                        webView.stopLoading();
                        if (progress != null)
                            if (progress.isShowing())
                                progress.dismiss();

                        webView.loadUrl("about:blank");
                    });

                }
            }
        });


    }

    static class ChatBotChromeClient extends WebChromeClient {


        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                if (progress != null)
                    if (progress.isShowing())
                        progress.dismiss();
            }
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            result.cancel();
            return true;
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            result.cancel();
            return true;
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            result.cancel();
            return true;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}