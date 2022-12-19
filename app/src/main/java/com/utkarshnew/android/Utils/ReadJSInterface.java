package com.utkarshnew.android.Utils;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.utkarshnew.android.courses.Activity.Concept_newActivity;

/**
 * Created by nakul on 3/26/2018.
 */

public class ReadJSInterface {
    WebView webview = null;
    private Context context;
    private static final String TAG = "ReadJSInterface";

    public ReadJSInterface() {
    }

    public ReadJSInterface(WebView webview, Context context) {
        this.webview = webview;
        this.context = context;
    }

    @JavascriptInterface
    public void annotationData(String annotationString, String action) {
        ((Concept_newActivity) context).onAnnotationCall(annotationString, action);
    }

    @JavascriptInterface
    public void scrollEnd() {
        ((Concept_newActivity) context).onScrolledBottom();
    }

    @JavascriptInterface
    public void onWebSearchTap() {
//        ((ReadChapterActivity) context).onWebSearchTap();
    }

    @JavascriptInterface
    public void onWebSearchTap(String query) {
        try {
            ((Concept_newActivity) context).onWebSearchTap(query);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @JavascriptInterface
    public void onAnnotationTap(boolean state) {
        ((Concept_newActivity) context).onAnnotationTap(state);
    }

}
