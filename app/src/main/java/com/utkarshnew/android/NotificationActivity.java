package com.utkarshnew.android;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.utkarshnew.android.Utils.Service.MyFirebaseMessagingService;

public class NotificationActivity extends Activity {

    public static final String NOTIFICATION_ID = "NOTIFICATION_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //MyFirebaseMessagingService.cancelNotifxation(getIntent().getIntExtra(NOTIFICATION_ID, -1));
        finish();
    }



}