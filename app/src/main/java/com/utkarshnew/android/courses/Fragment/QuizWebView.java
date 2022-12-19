package com.utkarshnew.android.courses.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.utkarshnew.android.courses.Activity.QuizActivity;
import com.utkarshnew.android.home.Constants;
import com.utkarshnew.android.Model.Courses.quiz.QuizModel;
import com.utkarshnew.android.Model.Courses.quiz.ResultTestSeries;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.Progress;
import com.utkarshnew.android.Utils.SharedPreference;

public class QuizWebView extends AppCompatActivity {
    WebView webView;
    Progress progressBar;
    ResultTestSeries resultTestSeries;
    String TAG = QuizWebView.class.getSimpleName();
    NetworkCall networkCall;
    QuizModel quiz;
    String url, lang, quizID, practiceId = "", subjectiveid = "";
    String quizState = "";
    String questionid = null, reportid = null;
    boolean status = false;
    boolean isReattempt = false;
    private TextView errorTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.activity_quiz_web_view);

        webView = (WebView) findViewById(R.id.webView);
        errorTV = (TextView) findViewById(R.id.errorTV);

//        progressBar = ProgressDialog.show(QuizWebView.this, "", "loading...");
        progressBar = new Progress(QuizWebView.this);

        quizID = getIntent().getExtras().getString(Const.QUIZSTATUS);
        quizState = getIntent().getExtras().getString(Const.TESTSTATE);
        resultTestSeries = (ResultTestSeries) getIntent().getExtras().getSerializable(Const.RESULT_SCREEN);
        status = getIntent().getExtras().getBoolean(Const.STATUS);
        isReattempt = getIntent().getExtras().getBoolean(Const.IS_REATTEMPT);
        lang = String.valueOf(getIntent().getExtras().getInt(Const.ID));
        subjectiveid = getIntent().getExtras().getString("subjectiveid");
        if (getIntent().hasExtra(Const.PRACTICE_ID)) {
            practiceId = getIntent().getStringExtra(Const.PRACTICE_ID);
        }
//        if (!Helper.isStringValid(id)) {
//            id = String.valueOf(SharedPreference.getInstance().getInt(Const.LANGUAGE));
//        }

        reportid = getIntent().getStringExtra("report_id");
        questionid = getIntent().getStringExtra("q_id");

//        Log.e(TAG, "id is " + url);

//        final Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
//
//        if (!TextUtils.isEmpty(id)) {
//            toolbar.setTitle("Practice");
//        } else {
//            if (!status)
//                toolbar.setTitle(quiz.getBasic_info().getTest_series_name());
//            else toolbar.setTitle(resultTestSeries.getTest_series_name());
//        }

//        setSupportActionBar(toolbar);
//
//        //Add back button
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back);
//        }

//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!status) {
//                    AlertDialog.Builder alertBuild = new AlertDialog.Builder(QuizWebView.this);
//                    alertBuild
//                            .setTitle(getString(R.string.app_name))
//                            .setMessage("Are you sure you want to quit the Quiz?")
//                            .setCancelable(true)
//                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int whichButton) {
//                                    dialog.dismiss();
//                                    finish();
//                                }
//                            })
//                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int whichButton) {
//                                    dialog.dismiss();
//                                }
//                            });
//                    AlertDialog dialog = alertBuild.create();
//                    dialog.show();
//                } else finish();
//            }
//        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

//        progressBar.setCancelable(false);
//        progressBar.show();
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setDefaultTextEncodingName("utf-8");
//        webSettings.setPluginState(WebSettings.PluginState.ON);
//        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
//        webView.getSettings().setAllowFileAccess(true);
//        webView.getSettings().setDomStorageEnabled(true);
//        webView.addJavascriptInterface(new MyJavaScriptInterface(webView), "HtmlViewer");
//        webView.loadUrl("javascript:window.HtmlViewer.processHTML" +
//                "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
//
//        //innerHTML is the name of method used in MyJavaScriptInterface
//
//        webView.clearHistory();
//        webView.clearFormData();
//        webView.clearCache(true);
//        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        progressBar.setCancelable(false);
        progressBar.show();
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        webView.setWebViewClient(new WebViewClient() {
            boolean returnVal = false;

            public void onPageFinished(WebView view, String url) {
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //Log.e("onPageStartedUrl", url);
            }

            @Override
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                //Log.e("doUpdateUrl", url);
                if (url.contains("back_true")) {
                    Constants.REFRESHFEED = "true";
                    finish();
                    /*Intent intent1=new Intent(QuizWebView.this,CourseActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent1.putExtra(Const.FRAG_TYPE,Const.EXAMPREPLAST);
                  //  intent1.putExtra(Const.CONTENT_TYPE, CourseActivity.contentType);
                    startActivity(intent1);*/
                }
                super.doUpdateVisitedHistory(view, url, isReload);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!status) {
                    if (url.contains("leaderboard")) {
                        Uri uri = Uri.parse(url);
                        String orderId = uri.getQueryParameter("test_segment_id");
                        String userId = uri.getQueryParameter("user_id");
                        returnVal = true;
                        if (progressBar.isShowing()) {
                            progressBar.dismiss();
                        }
                        Intent leaderBoard = new Intent(QuizWebView.this, QuizActivity.class);
                        leaderBoard.putExtra(Const.FRAG_TYPE, Const.LEADERBOARD);
                        leaderBoard.putExtra(Const.PRACTICE, orderId);
                        startActivity(leaderBoard);
//                        finish();
                    } else if (url.endsWith(".pdf")) {
                        Log.e("url", url);
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        // if want to download pdf manually create AsyncTask here
                        // and download file
                        return true;
                    }
                }
                return returnVal;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                if (progressBar != null && progressBar.isShowing()) {
                    progressBar.dismiss();
                }
                webView.setVisibility(View.GONE);
                errorTV.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                //Log.e("onLoadResourceUrl", url + "  .... " + view.getUrl());
            }
        });

        if (TextUtils.isEmpty(subjectiveid)) {
            if (!Helper.isStringValid(lang) || !TextUtils.isEmpty(practiceId)) {
                webView.loadUrl(String.format("%sdata_model/courses/test_web/view?test_series_id=%s&user_id=%s&test_type=practice&lang=%s&Jwt=%s",
                        API.MAIN_SERVER_URL1, practiceId, SharedPreference.getInstance().getLoggedInUser().getId(), SharedPreference.getInstance().getInt(Const.LANGUAGE),
                        SharedPreference.getInstance().getString(Const.JWT)));

            } else {
                if (!status && reportid == null) {
                    if (isReattempt) {
                        webView.loadUrl(String.format("%sdata_model/courses/test_web/view?test_series_id=%s&user_id=%s&lang=%s&Jwt=%s&re_attempt=%s",
                                API.MAIN_SERVER_URL1, quizID, SharedPreference.getInstance().getLoggedInUser().getId(), lang,
                                SharedPreference.getInstance().getString(Const.JWT), "1"));
                    } else {
                        if (!TextUtils.isEmpty(quizState)) {
                            if (quizState.equalsIgnoreCase("1")) {
                                webView.loadUrl(String.format("%sdata_model/courses/test_web/view?test_series_id=%s&user_id=%s&lang=%s&Jwt=%s",
                                        API.MAIN_SERVER_URL1, quizID, SharedPreference.getInstance().getLoggedInUser().getId(), lang,
                                        SharedPreference.getInstance().getString(Const.JWT)));
                            } else if (quizState.equalsIgnoreCase("0")) {
                                webView.loadUrl(String.format("%sdata_model/courses/test_web/view?test_series_id=%s&user_id=%s&lang=%s&Jwt=%s&state=%s",
                                        API.MAIN_SERVER_URL1, quizID, SharedPreference.getInstance().getLoggedInUser().getId(), lang,
                                        SharedPreference.getInstance().getString(Const.JWT), "resume"));
                            } else {
                                webView.loadUrl(String.format("%sdata_model/courses/test_web/view?test_series_id=%s&user_id=%s&lang=%s&Jwt=%s&state=%s",
                                        API.MAIN_SERVER_URL1, quizID, SharedPreference.getInstance().getLoggedInUser().getId(), lang,
                                        SharedPreference.getInstance().getString(Const.JWT), "done"));
                            }
                        } else {
                            webView.loadUrl(String.format("%sdata_model/courses/test_web/view?test_series_id=%s&user_id=%s&lang=%s&Jwt=%s&state=%s",
                                    API.MAIN_SERVER_URL1, quizID, SharedPreference.getInstance().getLoggedInUser().getId(), lang,
                                    SharedPreference.getInstance().getString(Const.JWT), "done"));
                        }
                    }

                } else if (reportid != null) {
                    webView.loadUrl(String.format(API.MAIN_SERVER_URL1 + "data_model/courses/test_web/open_view_from_quesion?" +
                            "report_id=" + reportid + "&q_id=" + questionid + "&Jwt=" + SharedPreference.getInstance().getString(Const.JWT)
                            + "&user_id=" + SharedPreference.getInstance().getLoggedInUser().getId()) + "&lang=" + lang);
                }
            }
        } else {
            webView.loadUrl(String.format("%sdata_model/courses/test_web/subjective_result?report_id=%s&user_id=%s&lang=%s&Jwt=%s",
                    API.MAIN_SERVER_URL, subjectiveid, SharedPreference.getInstance().getLoggedInUser().getId(), lang,
                    SharedPreference.getInstance().getString(Const.JWT)));
        }

        Log.d("UrlWeb", "" + webView.getUrl());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        if (Helper.isNetworkConnected(this)) {
                            if (webView.getUrl().contains("test_web/result")) {
                                Constants.REFRESHFEED = "true";
                                finish();
                            /*Intent intent1=new Intent(QuizWebView.this,CourseActivity.class);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent1.putExtra(Const.FRAG_TYPE,Const.EXAMPREPLAST);
                         //   intent1.putExtra(Const.CONTENT_TYPE, CourseActivity.contentType);
                            startActivity(intent1);*/
                            } else {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                    webView.evaluateJavascript("back_pressed();", null);
                                } else
                                    webView.loadUrl("javascript:back_pressed();");
                            }
//                        else if (webView.getUrl().contains("back_true"))
//                            webView.goBack();
                        } else {
                            Constants.REFRESHFEED = "true";
                            finish();
                        }


                    } else {
                        if (Helper.isNetworkConnected(this)) {
//                        String fragment = "back_1_testing";
//                        webView.loadUrl("javascript:window.location.hash=\"#" + fragment + "\"");
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                webView.evaluateJavascript("back_pressed();", null);
                            } else {
                                webView.loadUrl("javascript:back_pressed();");
                            }
                        } else {
                            Constants.REFRESHFEED = "true";
                            finish();
                        }
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    private class MyJavaScriptInterface {
        WebView webView;

        public MyJavaScriptInterface(WebView webView) {
            this.webView = webView;
        }

        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processHTML(String html) {
            Log.d("HTMLRESPONSE", html + "");

            /*webView.loadUrl(
                    "javascript:(function() { " +
                            "document.getElementById('appusername').value = '" +
                            PreManager.getFName(QRScannerActivity.this) +
                            "' " +
                            "document.getElementById('appuserid').value = '" +
                            PreManager.getUserId(QRScannerActivity.this) +
                            "'" +
                            "document.getElementById('appuserlastname').value = '" +
                            PreManager.getLNmae(QRScannerActivity.this) +
                            "'" +
                            "document.getElementById('appuseremail').value = '" +
                            PreManager.getEmail(QRScannerActivity.this) +
                            "})()");*/

           /* webView.loadUrl(
                    "javascript:(function() { " +
                            "document.getElementById(\"appusername\").value = \"Johnny Bravo\"" +
                            "' " +
                            "document.getElementById('appuserid').value = '" +
                            PreManager.getUserId(QRScannerActivity.this) +
                            "'" +
                            "document.getElementById('appuserlastname').value = '" +
                            PreManager.getLNmae(QRScannerActivity.this) +
                            "'" +
                            "document.getElementById('appuseremail').value = '" +
                            PreManager.getEmail(QRScannerActivity.this) +
                            "})()");*/

        }
    }

    public class WebAppInterface {
        Activity mContext;

        public WebAppInterface(Activity c) {
            mContext = c;
        }

        @JavascriptInterface
        public void getURL(final String url) {
            mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    //do your scheme with variable "url" in UIThread side. Over here you can call any method inside your activity/fragment
                }
            });

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
//        Helper.checkXposed(this);
    }
}
