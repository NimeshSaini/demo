package com.utkarshnew.android.Utils.Network.retrofit;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.DialogUtils;
import com.utkarshnew.android.Utils.Helper;


/*public class RetrofitResponse {

    public static void GetApiData(Context context, String auth_code){
        if (auth_code!=null){
            if (auth_code.equals(Const.EXPIRY_AUTH_CODE)){
                Toast.makeText(context, "You are already logged in with some other devices, So you are logged out from this device.", Toast.LENGTH_LONG).show();
                Helper.SignOutUser(context);
            }

        }

    }
}*/

public class RetrofitResponse {
    private static final String TAG = "RetrofitResponse";

    public static void GetApiData(Context context, String auth_code, String msg, boolean showMaintanance) {
        try {
            if (auth_code != null) {
                if (auth_code.equalsIgnoreCase(Const.EXPIRY_AUTH_CODE)) {
                    Toast.makeText(context, "" + msg, Toast.LENGTH_LONG).show();
                    Helper.SignOutUser(context);
                } else {
                    if (showMaintanance) {
                        DialogUtils.makeSingleButtonDialog((Activity) context, context.getString(R.string.maintain_app_dialog_title), context.getString(R.string.maintain_app_dialog_message),
                                context.getResources().getString(R.string.close), false, new DialogUtils.onDialogUtilsOkClick() {
                                    @Override
                                    public void onOKClick() {
                                        //context.finish();
                                    }
                                });
                    } else {
                        Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "GetApiData: " + e.getMessage());
        }

    }
}
