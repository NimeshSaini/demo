package com.utkarshnew.android.courses.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.home.adapters.TileDataAdapter;
import com.utkarshnew.android.home.model.CourseResponse;

import com.utkarshnew.android.home.model.Search.RecentData;
import com.utkarshnew.android.home.model.Search.RecentList;
import com.utkarshnew.android.Model.Courselist;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;
import com.utkarshnew.android.Utils.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;

public class SearchActivity extends AppCompatActivity implements NetworkCall.MyNetworkCallBack {

    ImageView imageBack, clearIV;
    AutoCompleteTextView et_search;
    RecyclerView searchListRV;
    ImageView errorLL;
    ArrayAdapter<String> adapter;
    NetworkCall networkCall;
    private int pagecount = 1;
    String contentType_id = "", allsubcatindex_id = "";
    ArrayList<Courselist> courselists = new ArrayList<>();
    TileDataAdapter tileDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.activity_search);
        networkCall = new NetworkCall(this, this);
        imageBack = findViewById(R.id.imageBack);
        clearIV = findViewById(R.id.clearIV);
        et_search = findViewById(R.id.et_search);
        searchListRV = findViewById(R.id.searchListRV);
        errorLL = findViewById(R.id.no_data);

        searchListRV.setVisibility(View.GONE);
        errorLL.setVisibility(View.VISIBLE);

        if (getIntent() != null) {
            contentType_id = getIntent().getStringExtra("couse_type");
            allsubcatindex_id = getIntent().getStringExtra("allsubcatindex");
        }

        adapter = new ArrayAdapter<String>(this, R.layout.item_view, getRecentList());
        et_search.setThreshold(1);
        et_search.setAdapter(adapter);

        et_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
                if (arg2.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if (et_search.getText().toString().trim().equalsIgnoreCase("")) {
                        Toast.makeText(SearchActivity.this, "Please enter search value", Toast.LENGTH_SHORT).show();
                    } else {
                        setRecentList(et_search.getText().toString());
                        getCourseData();
                        Helper.hideSoftKeyboard(SearchActivity.this);
                    }
                }
                return false;
            }
        });

        clearIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search.setText("");
            }
        });

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getCourseData() {
        networkCall.NetworkAPICall(API.get_courses, "", true, false);
    }

    public ArrayList<String> getRecentList() {
        ArrayList<String> finalRecentData = new ArrayList<>();
        RecentList recentList = SharedPreference.getInstance().getRecentData();
        if (recentList != null) {
            ArrayList<RecentData> recentDataArrayList = SharedPreference.getInstance().getRecentData().getRecentList();
            if (recentDataArrayList != null && recentDataArrayList.size() > 0) {
                for (RecentData recentData : recentDataArrayList) {
                    if (recentData.getUserId().equalsIgnoreCase(SharedPreference.getInstance().getLoggedInUser().getId())) {
                        finalRecentData.add(recentData.getQueryData());
                    }
                }
            }
        }
        return finalRecentData;
    }

    public void setRecentList(String queryData) {
        try {
            if (!TextUtils.isEmpty(queryData)) {
                RecentData recentData = new RecentData(SharedPreference.getInstance().getLoggedInUser().getId(), queryData);

                RecentList recentList = SharedPreference.getInstance().getRecentData();
                if (recentList != null) {
                    ArrayList<RecentData> recentDataArrayList = SharedPreference.getInstance().getRecentData().getRecentList();
                    if (recentDataArrayList != null && recentDataArrayList.size() > 0) {
                        boolean isUpdate = false;
                        for (int i = 0; i < recentDataArrayList.size(); i++) {
                            if (recentDataArrayList.get(i).getUserId().equalsIgnoreCase(SharedPreference.getInstance().getLoggedInUser().getId()) && recentDataArrayList.get(i).getQueryData().equalsIgnoreCase(queryData)) {
                                isUpdate = true;
                                recentDataArrayList.set(i, recentData);
                            }
                        }
                        if (!isUpdate) {
                            recentDataArrayList.add(recentData);
                            SharedPreference.getInstance().setRecentData(new RecentList(recentDataArrayList));
                        } else {
                            SharedPreference.getInstance().setRecentData(new RecentList(recentDataArrayList));
                        }
                    } else {
                        ArrayList<RecentData> recentDataArrayList1 = new ArrayList<>();
                        recentDataArrayList1.add(recentData);
                        SharedPreference.getInstance().setRecentData(new RecentList(recentDataArrayList1));
                    }
                } else {
                    ArrayList<RecentData> recentDataArrayList = new ArrayList<>();
                    recentDataArrayList.add(recentData);
                    SharedPreference.getInstance().setRecentData(new RecentList(recentDataArrayList));
                }

                adapter = new ArrayAdapter<String>(this, R.layout.item_view, getRecentList());
                et_search.setThreshold(1);
                et_search.setAdapter(adapter);
                et_search.dismissDropDown();
            }
        } catch (Exception e) {
            Log.d("TAG", "updateSeekTime: " + e.getMessage());
        }
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {

        switch (apitype) {
            case API.get_courses:
                EncryptionData getcoursedataencryptionData = new EncryptionData();
                getcoursedataencryptionData.setCourse_type(contentType_id);
                getcoursedataencryptionData.setSub_cat(allsubcatindex_id);
                getcoursedataencryptionData.setPage("" + 1);
                getcoursedataencryptionData.setSearch(et_search.getText().toString().trim());
                String getcoursedataencryptionDatadoseStr = new Gson().toJson(getcoursedataencryptionData);
                String getcoursedatadoseStrScr = AES.encrypt(getcoursedataencryptionDatadoseStr);
                return service.get_courses(getcoursedatadoseStrScr);
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonstring, String apitype, String typeApi, boolean showprogress) throws JSONException {

        switch (apitype) {
            case API.get_courses:
                try {
                    if (jsonstring.optString(Const.STATUS).equals(Const.TRUE)) {
                        CourseResponse courseResponse = new Gson().fromJson(jsonstring.toString(), CourseResponse.class);
                        if (courseResponse.getData().size() > 0) {
                            ArrayList<Courselist> courseltemp_courselist = new ArrayList<>();
                            courseltemp_courselist.clear();
                            int oldarrysize = 0;
                            searchListRV.setVisibility(View.VISIBLE);
                            errorLL.setVisibility(View.GONE);

                            if (courseResponse.getData().size() > 0) {
                                if (pagecount == 1) {
                                    courselists = new ArrayList<>();

                                    courselists.addAll(courseResponse.getData());
                                    ArrayList<Courselist> newcourselist = new ArrayList<>();
                                    newcourselist = removeDuplicates(courselists);
                                    courselists.clear();
                                    courselists.addAll(newcourselist);

                                } else {
                                    oldarrysize = courselists.size();
                                    courselists.addAll(courseResponse.getData());
                                    ArrayList<Courselist> newcourselist = new ArrayList<>();
                                    newcourselist = removeDuplicates(courselists);
                                    courselists.clear();
                                    courselists.addAll(newcourselist);
                                }
                                if (pagecount == 1) {
                                    tileDataAdapter = new TileDataAdapter(SearchActivity.this, courselists);
                                    searchListRV.setLayoutManager(new GridLayoutManager(SearchActivity.this, 2, GridLayoutManager.VERTICAL, false));
                                    searchListRV.setAdapter(tileDataAdapter);
                                    searchListRV.setNestedScrollingEnabled(false);

                                } else {

                                    tileDataAdapter.notifyItemRangeInserted(oldarrysize - 1, courselists.size() - oldarrysize);
                                }
                            }
                        } else {
                            if (courselists != null && pagecount == 1) {
                                courselists.clear();
                                searchListRV.setVisibility(View.GONE);
                                errorLL.setVisibility(View.VISIBLE);
                            }
                        }
                    } else {
                        if (courselists != null && pagecount == 1) {
                            courselists.clear();
                            searchListRV.setVisibility(View.GONE);
                            errorLL.setVisibility(View.VISIBLE);
                        }
                        RetrofitResponse.GetApiData(this, jsonstring.has(Const.AUTH_CODE) ? jsonstring.getString(Const.AUTH_CODE) : "", jsonstring.getString(Const.MESSAGE), false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {

    }


    public <Courselist> ArrayList<com.utkarshnew.android.Model.Courselist> removeDuplicates(ArrayList<com.utkarshnew.android.Model.Courselist> list) {
        ArrayList<com.utkarshnew.android.Model.Courselist> newList = new ArrayList<com.utkarshnew.android.Model.Courselist>();
        for (com.utkarshnew.android.Model.Courselist element : list) {
            if (!newList.isEmpty()) {
                boolean isfind = false;
                for (int i = 0; i < newList.size(); i++) {
                    if (newList.get(i).getId().equalsIgnoreCase(element.getId())) {
                        isfind = true;
                        break;
                    } else {
                        isfind = false;
                    }
                }
                if (!isfind) {
                    newList.add(element);
                }
            } else {
                newList.add(element);
            }
        }
        // return the new list
        return newList;
    }
}