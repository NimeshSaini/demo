package com.utkarshnew.android.Utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class GetIP extends AsyncTask<Void, Void, Void> {

    private String ip = "N/A";
    Progress progress;
    Activity activity;

    public GetIP(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ip = null;
      /*  progress = new Progress(activity);
        progress.show();*/
    }

    @Override
    protected Void doInBackground(Void... voids) {
        URL url;
        URLConnection connection;
        String data = "";
        InputStream inputStream;
        JSONObject object = null;
        int temp;
        try {
            //url  = new URL("https://api.myip.com/");
            url = new URL("https://api.ipify.org/?format=json");
            connection = url.openConnection();
            inputStream = connection.getInputStream();
            while ((temp = inputStream.read()) != -1) {
                data += (char) temp;
            }
            object = new JSONObject(data);
            ip = object.getString("ip");
        } catch (MalformedURLException mue) {
            Log.wtf("Error", mue.getMessage());
            ip = "N/A";
        } catch (IOException ioe) {
            Log.e("Error", ioe.getMessage());
            ip = "N/A";
        } catch (JSONException jsone) {
            Log.wtf("Error", jsone.getMessage());
            ip = "N/A";
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        // progress.dismiss();
        SharedPreference.getInstance().putString(Const.IP_ADDRESS, "" + ip);
    }
}
