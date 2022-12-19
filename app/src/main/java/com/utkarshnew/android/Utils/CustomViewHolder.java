package com.utkarshnew.android.Utils;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Network.APIInterface;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class CustomViewHolder extends RecyclerView.ViewHolder {

    public Progress mprogress;

    public Activity activity;
    String id;

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void setContext(Activity context) {
        this.activity = context;
        mprogress = new Progress(activity);
        mprogress.setCancelable(false);
    }

    public void NetworkAPICall(final String apiType, final boolean showprogress) {

        Log.e(((Activity) activity).getLocalClassName(), "++++++++++++++++" + apiType);

        APIInterface service = MakeMyExam.getRetrofitInstance().create(APIInterface.class);

        if (Helper.isConnected(activity)) {

            if (showprogress) mprogress.show();

            Call<String> call = getAPIB(apiType, service);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (showprogress) mprogress.dismiss();
                    if (response.body() != null && response.isSuccessful()) {
                        //String jsonString = response.body();
                        String jsonString;
                        try {
                            jsonString = AES.decrypt(response.body(), AES.generatekeyAPI(), AES.generateVectorAPI());
                        } catch (Exception e) {
                            jsonString = response.body();
                        }
                        try {
                            if (jsonString != null && !jsonString.isEmpty()) {
                                JSONObject jsonObject = new JSONObject(jsonString);
                                MakeMyExam.setTime_server((Long.parseLong(jsonObject.optString("time")))*1000);

                                SuccessCallBack(jsonObject, apiType);

                            } else {
                                Toast.makeText(activity, activity.getResources().getString(R.string.jsonparsing_error_message), Toast.LENGTH_SHORT).show();
                                ErrorCallBack(activity.getResources().getString(R.string.jsonparsing_error_message), apiType);
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();

                        }
                    } else {
                        Toast.makeText(activity, activity.getResources().getString(R.string.exception_api_error_message), Toast.LENGTH_SHORT).show();
                        ErrorCallBack(activity.getResources().getString(R.string.exception_api_error_message), apiType);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    if (showprogress) mprogress.dismiss();
                    Toast.makeText(activity, activity.getResources().getString(R.string.jsonparsing_error_message), Toast.LENGTH_SHORT).show();
                    ErrorCallBack(activity.getResources().getString(R.string.exception_api_error_message), apiType);
                }
            });
        } else {

            if (activity != null && !activity.isDestroyed())
                ErrorCallBack(activity.getResources().getString(R.string.internet_error_message), apiType);
        }
    }


    public void onSessionExpired() {
        Helper.SignOutUser(activity);
    }

    public abstract Call<String> getAPIB(String apitype, APIInterface service);

    public abstract void SuccessCallBack(JSONObject jsonstring, String apitype) throws JSONException;

    public abstract void ErrorCallBack(String jsonstring, String apitype);

}
