package com.utkarshnew.android.Utils.Service;

import android.util.Log;
import android.widget.Toast;

import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.retrofit.WebInterface;
import com.utkarshnew.android.Utils.Progress;
import com.utkarshnew.android.Utils.SharedPreference;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();
    String refreshedToken;

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        storeRegIdInPref(refreshedToken);
        //sendRegistrationToServer(refreshedToken);
    }



    private void storeRegIdInPref(String token) {
        SharedPreference.getInstance().putString(Const.FIREBASE_TOKEN_ID, token);
    }
}