package com.utkarshnew.android.courses.Activity;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Helper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class WebFragActivity extends AppCompatActivity {

    private Context context;
    private WebView myWebView;
    //private BootstrapProgressBar progressBar;
    private FrameLayout webframe;
    String title;
    String url;
    ImageView imageIV;
    TextView toolbartitleTV;
    String from = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = WebFragActivity.this;
        Helper.enableScreenShot(this);
        setContentView(R.layout.activity_web_frag);
        Toolbar toolbar = (Toolbar) findViewById(R.id.myProgress_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.arrow_back_black);
        //getData
       /* Bundle bundle=getIntent().getExtras();
        title=bundle.getString("title");
        url=bundle.getString("url");*/

        if (getIntent() != null) {
            title = getIntent().getStringExtra("title");
            url = getIntent().getStringExtra("url");
            from = getIntent().getStringExtra("from");
        }

        imageIV = findViewById(R.id.imageIV);
        imageIV.setVisibility(View.GONE);
        toolbartitleTV = findViewById(R.id.toolbartitleTV);
        webframe = (FrameLayout) findViewById(R.id.webframe);
        //progressBar=(BootstrapProgressBar) findViewById(R.id.progressBar);
        myWebView = (WebView) findViewById(R.id.webView);

        imageIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadWebView();
            }
        });

        // Configure related browser settings
        myWebView.getSettings().setLoadsImagesAutomatically(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        myWebView.clearCache(true);
        myWebView.clearHistory();
        myWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        myWebView.getSettings().setUseWideViewPort(true); // Zoom out if the content width is greater than the width of the viewport
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setSupportZoom(true);
        myWebView.getSettings().setBuiltInZoomControls(true); // allow pinch to zooom
        myWebView.getSettings().setDisplayZoomControls(false); // disable the default zoom controls on the page
        myWebView.setWebViewClient(new MyBrowser());
        myWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                webframe.setVisibility(View.VISIBLE);
                //progressBar.setProgress(newProgress);

                //setToolBarTitle("Loading...");

                if (newProgress == 100) {
                    webframe.setVisibility(view.GONE);
                    //setToolBarTitle(title);
                }
                super.onProgressChanged(view, newProgress);
            }
        });

        if (from == null || from.equalsIgnoreCase("")) {
            Resources res = getResources();
            myWebView.getSettings().setDefaultFontSize((int) res.getDimension(R.dimen.sp18));
            myWebView.loadUrl(url);
            setToolBarTitle(title);
        } else {
            setToolBarTitle("Notification");
            Resources res = getResources();
            myWebView.getSettings().setDefaultFontSize((int) res.getDimension(R.dimen.sp18));
            try {
                myWebView.loadData(URLEncoder.encode(url, "utf-8").replaceAll("\\+"," "), "text/html; charset=UTF-8", null);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            myWebView.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url != null && (url.startsWith("http://") || url.startsWith("https://"))) {
                        view.getContext().startActivity(
                                new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        }

        /*myWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                DownloadManager.Request request = new DownloadManager.Request(
                        Uri.parse(url));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
                //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Name of your downloadble file goes here, example: Mathematics II ");
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimetype));
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(context, "Downloading File", Toast.LENGTH_LONG).show();
            }
        });*/


    }

    private class MyBrowser extends WebViewClient {
        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.endsWith(".pdf") || url.endsWith(".PDF")) {
                //view.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url="+url);
                Uri path = Uri.parse(url);
                Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                pdfIntent.setDataAndType(path, "application/pdf");
                pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                try {
                    context.startActivity(pdfIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
                }
            } else {
                view.loadUrl(url);
            }
            //view.loadUrl("https://docs.google.com/viewer?url="+url);
            webframe.setVisibility(View.VISIBLE);
            return true;
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (request.getUrl().toString().endsWith(".pdf") || request.getUrl().toString().endsWith(".PDF")) {
                //view.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url="+request.getUrl().toString());
                Uri path = Uri.parse(request.getUrl().toString());
                Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                pdfIntent.setDataAndType(path, "application/pdf");
                pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                try {
                    context.startActivity(pdfIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
                }
            } else {
                view.loadUrl(request.getUrl().toString());
            }
            //view.loadUrl((request.getUrl().toString()));
            webframe.setVisibility(View.VISIBLE);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
    }

    public void setToolBarTitle(String title) {
        toolbartitleTV.setText(title);
    }

    public void reloadWebView() {
        if (myWebView != null) {
            myWebView.reload();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        //onBackPressed();
        supportFinishAfterTransition();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (myWebView.canGoBack()) {
            myWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Helper.checkXposed(this);
    }
}