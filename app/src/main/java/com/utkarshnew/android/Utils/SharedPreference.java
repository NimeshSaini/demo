package com.utkarshnew.android.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.utkarshnew.android.courses.modal.NotesPDF.NoteList;
import com.utkarshnew.android.home.model.Search.RecentList;
import com.utkarshnew.android.Model.User;
import com.utkarshnew.android.Response.MasterFeedsHitResponse;
import com.utkarshnew.android.Response.MasterRegistrationResponse;
import com.utkarshnew.android.Response.PostResponse;
import com.utkarshnew.android.Response.Registration.StreamResponse;
import com.utkarshnew.android.Response.Registration.SubStreamResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.utkarshnew.android.pojo.Userinfo.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SharedPreference {

    public static final String MY_PREFERENCES = "MY_PREFERENCES";
    public static final int MODE = Context.MODE_PRIVATE;
    private static SharedPreference pref;
    private SharedPreferences sharedPreference;
    private SharedPreferences.Editor editor;

    private SharedPreference() {
        sharedPreference = MakeMyExam.getAppContext().getSharedPreferences(MY_PREFERENCES, MODE);
        editor = sharedPreference.edit();
    }

    public static SharedPreference getInstance() {
        if (pref == null) {
            pref = new SharedPreference();
        }
        return pref;
    }

    public String getString(String key) {
        return sharedPreference.getString(key, "");
    }

    public void putString(String key, String value) {
        editor.putString(key, value).commit();
    }

    public int getInt(String key) {
        return sharedPreference.getInt(key, 1);
    }

    public void putInt(String key, int value) {
        editor.putInt(key, value).commit();
    }


    public long getLong(String key) {
        return sharedPreference.getLong(key, 0l);
    }

    public void putLong(String key, long value) {
        editor.putLong(key, value).commit();
    }

    public float getFloat(String key) {
        return sharedPreference.getFloat(key, 0.5f);
    }

    public void putFloat(String key, float value) {
        editor.putFloat(key, value).commit();
    }

    public boolean getBoolean(String key) {
        //    editor.putBoolean(key, value).commit();
        return sharedPreference.getBoolean(key, false);
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value).commit();
    }

    public void ClearLoggedInUser() {
        editor.putString(Const.USER_LOGGED_IN, new Gson().toJson(new User()));
        editor.commit();
    }

    public Data getLoggedInUser() {
        Data user = null;
        String userJson = sharedPreference.getString(Const.USER_LOGGED_IN, null);
        if (userJson != null && userJson.trim().length() > 0) {
            user = new Gson().fromJson(userJson, Data.class);
        }
        return user;
    }

    // remove after some time
    public void setLoggedInUser(User user) {
        editor.putString(Const.USER_LOGGED_IN, new Gson().toJson(user));
        editor.commit();
    }

    public NoteList getNoteData() {
        NoteList noteList = null;
        String noteJson = sharedPreference.getString(Const.NOTE_DATA, null);
        if (noteJson != null && noteJson.trim().length() > 0) {
            noteList = new Gson().fromJson(noteJson, NoteList.class);
        }
        return noteList;
    }

    public void setNoteData(NoteList noteList) {
        editor.putString(Const.NOTE_DATA, new Gson().toJson(noteList));
        editor.commit();
    }

    // utkarsh
    public void setLoggedInUserr(Data user) {
        editor.putString(Const.USER_LOGGED_IN, new Gson().toJson(user));
        editor.commit();
    }

    public MasterFeedsHitResponse getMasterHitResponse() {
        MasterFeedsHitResponse response = null;
        String responsestr = sharedPreference.getString(Const.MASTER_FEED_HIT_RESPONSE, null);
        if (responsestr != null && responsestr.trim().length() > 0)
            response = new Gson().fromJson(responsestr, MasterFeedsHitResponse.class);
        return response;
    }

    public MasterRegistrationResponse getRegistrationResponse() {
        MasterRegistrationResponse response = null;
        String responsestr = sharedPreference.getString(Const.MASTER_REGISTRATION_HIT_RESPONSE, null);
        if (responsestr != null && responsestr.trim().length() > 0)
            response = new Gson().fromJson(responsestr, MasterRegistrationResponse.class);
        return response;
    }

    public PostResponse getPost() {
        PostResponse post = null;
        String postJson = sharedPreference.getString(Const.POST, null);
        if (postJson != null && postJson.trim().length() > 0)
            post = new Gson().fromJson(postJson, PostResponse.class);
        return post;
    }

    public void setPost(PostResponse post) {
        editor.putString(Const.POST, new Gson().toJson(post));
        editor.commit();
    }

    public boolean contains(String key) {
        return sharedPreference.contains(key);
    }

    public void remove(String key) {
        editor.remove(key).commit();
    }

    public String getString(String s, String name) {
        return sharedPreference.getString(s, name);
    }


    public void saveHashMap(String key, Object obj) {
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }


    public LinkedHashMap<StreamResponse, ArrayList<SubStreamResponse>> getHashMap(String key) {
        Gson gson = new Gson();
        String json = sharedPreference.getString(key, "");
        java.lang.reflect.Type type = new TypeToken<LinkedHashMap<StreamResponse, ArrayList<SubStreamResponse>>>() {
        }.getType();
        LinkedHashMap<StreamResponse, ArrayList<SubStreamResponse>> obj = gson.fromJson(json, type);
        return obj;
    }

    public RecentList getRecentData() {
        RecentList recentList = null;
        String recentJson = sharedPreference.getString(Const.RECENT_DATA, null);
        if (recentJson != null && recentJson.trim().length() > 0) {
            recentList = new Gson().fromJson(recentJson, RecentList.class);
        }
        return recentList;
    }

    public void setRecentData(RecentList recentList) {
        editor.putString(Const.RECENT_DATA, new Gson().toJson(recentList));
        editor.commit();
    }
}
