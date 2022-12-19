package com.utkarshnew.android.Utils;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.utkarshnew.android.R;
import com.wang.avi.AVLoadingIndicatorView;


/**
 * Created by Cbc-03 on 05/24/17.
 */

public class Progress extends Dialog {

    AVLoadingIndicatorView progressIV;

    public Progress(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        /*//hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.progress_layout);

        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);


        progressIV = findViewById(R.id.testLoader);
        super.setCancelable(false);
    }

    @Override
    public void show() {
        try {
            super.show();
        } catch (Exception e) {
            Log.d("Progress", "show: " + e.getMessage());
        }

    }

    @Override
    public void hide() {
        try {
            super.hide();
        } catch (Exception e) {
            Log.d("Progress", "hide: " + e.getMessage());
        }
    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
        } catch (Exception e) {
            Log.d("Progress", "dismiss: " + e.getMessage());
        }
    }
}

