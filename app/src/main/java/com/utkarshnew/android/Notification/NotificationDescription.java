package com.utkarshnew.android.Notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.utkarshnew.android.courses.Activity.WebFragActivity;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Helper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class NotificationDescription extends AppCompatActivity {


    private Context context;
    String url, title, description, urlType, time;
    ImageView image_back, description_imageView;
    ConstraintLayout layout;

    TextView description_textView, description_urlTV, toolbarTitleTV, markasread, date_time;
   WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.activity_notification_description);

        context = NotificationDescription.this;

        if (getIntent() != null) {
            title = getIntent().getStringExtra("title");
            url = getIntent().getStringExtra("url");
            description = getIntent().getStringExtra("description");
            urlType = getIntent().getStringExtra("urlType");
            time = getIntent().getStringExtra("time");
        }


        //getUrl();

        description_textView = findViewById(R.id.description_textView);
        description_urlTV = findViewById(R.id.description_urlTV);
        toolbarTitleTV = findViewById(R.id.toolbarTitleTV);
        markasread = findViewById(R.id.markasread);
        myWebView = findViewById(R.id.image_webview);
        markasread.setVisibility(View.GONE);
        image_back = findViewById(R.id.image_back);
        description_imageView = findViewById(R.id.description_imageView);
        layout = findViewById(R.id.layout);
        date_time = findViewById(R.id.date_time);
//        description_textView.setMovementMethod(new ScrollingMovementMethod());


        if (urlType.equalsIgnoreCase("URL")) {
            description_urlTV.setText(url);
            description_textView.setText(description);
            date_time.setText(time);

            if (description != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    description_textView.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT));
                } else {
                    description_textView.setText(Html.fromHtml(description));
                }
            }
            layout.setVisibility(View.GONE);

        } else if (urlType.equalsIgnoreCase("IMAGE")) {
            //description_textView.setText(description);
            date_time.setVisibility(View.GONE);
            description_textView.setVisibility(View.GONE);
            myWebView.setVisibility(View.VISIBLE);
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
            myWebView.getSettings().setDisplayZoomControls(false);
            myWebView.setWebViewClient(new MyBrowser());
            Resources res = getResources();
            myWebView.getSettings().setDefaultFontSize((int) res.getDimension(R.dimen.sp18));
            try {
                myWebView.loadData(URLEncoder.encode(description, "utf-8").replaceAll("\\+"," "), "text/html; charset=UTF-8", null);
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

           /* if (description != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    description_textView.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT));
                } else {
                    description_textView.setText(Html.fromHtml(description));
                }
            }*/

            layout.setVisibility(View.VISIBLE);
            description_urlTV.setVisibility(View.GONE);

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round);
            Glide.with(this).load(url).apply(options).into(description_imageView);

        } else if (urlType.equalsIgnoreCase("GENERAL")) {
            description_urlTV.setVisibility(View.GONE);
            description_urlTV.setText(url);
            description_textView.setText(description);
            date_time.setText(time);

            if (description != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    description_textView.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT));
                } else {
                    description_textView.setText(Html.fromHtml(description));
                }
            }

            layout.setVisibility(View.GONE);
        }

        description_urlTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(context, WebFragActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });

        image_back.setOnClickListener(new OnSingleClickListener(() -> {
            finish();
            return null;
        }));
        setToolBarTitle(title);
    }

    public void setToolBarTitle(String title) {
        toolbarTitleTV.setText(title);
    }

    private class MyBrowser extends WebViewClient {
        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.endsWith(".pdf") || url.endsWith(".PDF")) {
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
            return true;
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (request.getUrl().toString().endsWith(".pdf") || request.getUrl().toString().endsWith(".PDF")) {
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

}