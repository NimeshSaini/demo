package com.utkarshnew.android.home.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.utkarshnew.android.BuildConfig;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.SharedPreference;

import static android.webkit.WebView.setWebContentsDebuggingEnabled;

public class ChatBotActvity extends AppCompatActivity {

    WebView chatbotwebview;
    ImageView image_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.activity_chat_bot_actvity);
        try {
            if (Build.VERSION.SDK_INT >= 21) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(this.getResources().getColor(R.color.black));
            }
            chatbotwebview = findViewById(R.id.chatbotwebview);
            image_back = findViewById(R.id.image_back);
            image_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            startChatBot();
        } catch (Exception e) {
            Helper.dismissProgressDialog();
            e.printStackTrace();
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
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);


        webView.getSettings().setJavaScriptEnabled(true);

        // Other webview options
        webView.getSettings().setLoadWithOverviewMode(true);

        //webView.getSettings().setUseWideViewPort(true);

        //Other webview settings
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setDomStorageEnabled(true);

        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        webView.setWebViewClient(new CustomWebViewClient());

        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
    }


    private void startChatBot() {
        Helper.showProgressDialog(this);
        configureWebView(chatbotwebview);
        String url = "https://ailifebot.com/utkarsh-classes.html?username=" + SharedPreference.getInstance().getLoggedInUser().getMobile();
        chatbotwebview.loadUrl(url);
    }

    public class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // When user clicks a hyperlink, load in the existing WebView
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
            try {
                chatbotwebview.stopLoading();
            } catch (Exception e) {
            }

            if (chatbotwebview.canGoBack()) {
                chatbotwebview.goBack();
            }

            chatbotwebview.loadUrl("about:blank");
            AlertDialog alertDialog = new AlertDialog.Builder(ChatBotActvity.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Check your internet connection and try again.");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Try Again", (dialog, which) -> {
                finish();
                startActivity(getIntent());
            });

            alertDialog.show();
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

        }
    }
}