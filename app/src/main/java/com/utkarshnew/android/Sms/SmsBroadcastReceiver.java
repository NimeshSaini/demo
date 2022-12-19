package com.utkarshnew.android.Sms;

import static android.content.Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_PREFIX_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;

import java.util.Objects;

public class SmsBroadcastReceiver extends BroadcastReceiver {

    public SmsBroadcastReceiverListener smsBroadcastReceiverListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), SmsRetriever.SMS_RETRIEVED_ACTION)) {

            Bundle extras = intent.getExtras();

            Status smsRetreiverStatus = (Status) extras.get(SmsRetriever.EXTRA_STATUS);

            switch (smsRetreiverStatus.getStatusCode()) {
                case CommonStatusCodes.SUCCESS:
                    Intent consentIntent = extras.getParcelable(SmsRetriever.EXTRA_CONSENT_INTENT);
                    try {

                        ComponentName name = consentIntent.resolveActivity(context.getPackageManager());

                        Log.e("Shantanu", "onReceive: "+name.getPackageName() + " " + name.getClassName());

                        if (name.getPackageName().equalsIgnoreCase("com.google.android.gms") &&
                                name.getClassName().equalsIgnoreCase("com.google.android.gms.auth.api.phone.ui.UserConsentPromptActivity")) {

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                consentIntent.removeFlags(FLAG_GRANT_READ_URI_PERMISSION);
                                consentIntent.removeFlags(FLAG_GRANT_WRITE_URI_PERMISSION);
                                consentIntent.removeFlags(FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                                consentIntent.removeFlags(FLAG_GRANT_PREFIX_URI_PERMISSION);
                            }

                            smsBroadcastReceiverListener.onSuccess(consentIntent);

                            // someActivityResultLauncher.launch(consentIntent);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        // Handle the exception ...
                    }
                    break;
                case CommonStatusCodes.TIMEOUT:
                    smsBroadcastReceiverListener.onFailure();
                    break;
            }
        }
        /*if (Objects.equals(intent.getAction(), SmsRetriever.SMS_RETRIEVED_ACTION)) {

            Bundle extras = intent.getExtras();

            Status smsRetreiverStatus = (Status) extras.get(SmsRetriever.EXTRA_STATUS);

            switch (smsRetreiverStatus.getStatusCode()) {
                case CommonStatusCodes.SUCCESS:
                    Intent messageIntent = extras.getParcelable(SmsRetriever.EXTRA_CONSENT_INTENT);
                    smsBroadcastReceiverListener.onSuccess(messageIntent);
                    break;
                case CommonStatusCodes.TIMEOUT:
                    smsBroadcastReceiverListener.onFailure();
                    break;
            }
        }*/
    }

    public interface SmsBroadcastReceiverListener {
        void onSuccess(Intent intent);

        void onFailure();
    }
}
