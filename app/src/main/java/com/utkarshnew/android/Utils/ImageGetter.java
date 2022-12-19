package com.utkarshnew.android.Utils;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.Html;

public class ImageGetter implements Html.ImageGetter {

    Activity activity;

    public ImageGetter(Activity activity) {
        this.activity = activity;
    }

    public Drawable getDrawable(String source) {
        int id;

        id = activity.getResources().getIdentifier(source, "drawable", activity.getPackageName());

        if (id == 0) {
            // the drawable resource wasn't found in our package, maybe it is a stock android drawable?
            id = activity.getResources().getIdentifier(source, "drawable", "android");
        }

        if (id == 0) {
            // prevent a crash if the resource still can't be found
            return null;
        } else {
            Drawable d = activity.getResources().getDrawable(id);
            d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            return d;
        }
    }

}