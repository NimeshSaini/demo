package com.utkarshnew.android.Login.Fragment;


import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.utkarshnew.android.Login.Activity.SignInActivity;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Progress;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommonWebViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommonWebViewFragment extends Fragment {

    private String strUrl = "";
    private boolean showToolbar = false;
    WebView webview;
    private Progress progress;

    public CommonWebViewFragment() {
        // Required empty public constructor
    }

    public static CommonWebViewFragment newInstance(String url, boolean showToolbar) {
        CommonWebViewFragment fragment = new CommonWebViewFragment();
        Bundle args = new Bundle();
        args.putString(Const.PRIVACYURL, url);
        args.putBoolean("show_toolbar", showToolbar);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            strUrl = getArguments().getString(Const.PRIVACYURL);
            //strUrl = "https://www.lipsum.com/";
            showToolbar = getArguments().getBoolean("show_toolbar");
        }
        try {
            progress = new Progress(getActivity());
            if (progress != null && !progress.isShowing()) {
                progress.show();
            }
        } catch (Exception e) {
            Log.d("TAG", "onCreate: " + e.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_common_web_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() instanceof SignInActivity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            }
            if (showToolbar) {
                ((SignInActivity) getActivity()).toolbarLinearLayout.setVisibility(View.VISIBLE);
            } else {
                ((SignInActivity) getActivity()).toolbarLinearLayout.setVisibility(View.GONE);
            }
            //((SignInActivity) getActivity()).signLL.setBackground(null);
        }
        webview = view.findViewById(R.id.web_view);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.setHorizontalScrollBarEnabled(true);
        webview.setVerticalScrollBarEnabled(true);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webview.setWebChromeClient(new WebChromeClient());
        webview.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webview.setVisibility(View.VISIBLE);
                if (progress.isShowing())
                    progress.dismiss();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                webview.setVisibility(View.GONE);
                if (progress.isShowing())
                    progress.dismiss();
                Log.e("TAG", "onReceivedError: " + view + " " + errorCode + " " + description + " " + failingUrl);
            }
        });
        webview.loadUrl(strUrl);
    }

    public void reloadWebView() {
        webview.reload();
    }

    public WebView getWebview() {
        return webview;
    }
}

